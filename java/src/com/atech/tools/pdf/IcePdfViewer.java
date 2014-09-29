package com.atech.tools.pdf;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icepdf.core.util.Defs;
import org.icepdf.ri.common.ViewModel;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;
import org.icepdf.ri.util.URLAccess;



public class IcePdfViewer {

    private static final Log logger = LogFactory.getLog(IcePdfViewer.class);

    private WindowManagerIcePdfViewer windowManager;
    private PropertiesManager propertiesManager;
    private ResourceBundle activeLanguageBundle;
    private boolean useLookAndFeel = false;
    private String customPropertiesFilename = null;
    private Properties additionalSettings = null;


    public IcePdfViewer() {
        this(null, false);
    }


    public IcePdfViewer(Locale locale) {
        this(locale, false);
    }


    public IcePdfViewer(boolean useLAF) {
        this(null, useLAF);
    }


    public IcePdfViewer(Locale locale, boolean useLAF) {
        setResourceBundle(locale);
        this.useLookAndFeel = useLAF;
    }


    private void setResourceBundle(Locale locale) {
        ResourceBundle messageBundle = null;

        if (locale != null) {
            messageBundle = ResourceBundle.getBundle("org.icepdf.ri.resources.MessageBundle", locale);
        }

        if (messageBundle == null) {
            messageBundle = ResourceBundle.getBundle("org.icepdf.ri.resources.MessageBundle");
        }

        this.activeLanguageBundle = messageBundle;
    }


    public void openWithFilename(String contentFilename) {
        open(contentFilename, null, customPropertiesFilename, additionalSettings);
    }


    public void openWithURL(String contentURL) {
        open(null, contentURL, customPropertiesFilename, additionalSettings);
    }


    public void open(String contentFile, String contentURL, String contentProperties, Properties properties) {

        Properties sysProps = System.getProperties();
        propertiesManager = new PropertiesManager(sysProps, contentProperties, activeLanguageBundle);

        if (properties != null) {
            for (Entry<Object, Object> prop : properties.entrySet()) {
                propertiesManager.set(prop.getKey().toString(), prop.getValue().toString());
            }
        }

        propertiesManager.setBoolean("org.icepdf.core.verbose", false);

        new FontPropertiesManager(propertiesManager, sysProps, activeLanguageBundle);

        System.setProperties(sysProps);

        if (this.useLookAndFeel) {
            setupLookAndFeel(activeLanguageBundle);
        }

/**
     // build a component controller
        SwingController controller = new SwingController();
        controller.setIsEmbeddedComponent(true);


//        PropertiesManager properties = new PropertiesManager(
//                System.getProperties(),
//                ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
//
//        properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.75");


        SwingViewBuilder factory = new SwingViewBuilder(controller, propertiesManager);

        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
        JPanel viewerComponentPanel = factory.buildViewerPanel();
        JFrame applicationFrame = new JFrame();
        applicationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //.EXIT_ON_CLOSE);
        applicationFrame.getContentPane().add(viewerComponentPanel);
        // Now that the GUI is all in place, we can try openning a PDF
        controller.openDocument(contentFile);
        // show the component
        applicationFrame.pack();
        applicationFrame.setVisible(true);

*/



        // first try at running icepdf, it seems that it was wrong way

        ViewModel.setDefaultFilePath(propertiesManager.getDefaultFilePath());
        ViewModel.setDefaultURL(propertiesManager.getDefaultURL());


        windowManager = new WindowManagerIcePdfViewer(propertiesManager, activeLanguageBundle);




        if (StringUtils.isNotBlank(contentFile)) {
            windowManager.newWindow(contentFile);
            ViewModel.setDefaultFilePath(contentFile);
        } else if (StringUtils.isNotBlank(contentURL)) {
            URLAccess urlAccess = URLAccess.doURLAccess(contentURL);
            urlAccess.closeConnection();

            if (urlAccess.errorMessage != null) {

                Object[] messageArguments = { urlAccess.errorMessage, urlAccess.urlLocation };

                MessageFormat formatter = new MessageFormat(
                    activeLanguageBundle.getString("viewer.launcher.URLError.dialog.message"));

                JOptionPane.showMessageDialog(null, formatter.format(messageArguments),
                    activeLanguageBundle.getString("viewer.launcher.URLError.dialog.title"), 1);

            } else {

                windowManager.newWindow(urlAccess.url);
            }
            ViewModel.setDefaultURL(urlAccess.urlLocation);
            urlAccess.dispose();
        } else {
            windowManager.newWindow("");
        }

    }


    private void setupLookAndFeel(ResourceBundle messageBundle) {
        if (Defs.sysProperty("mrj.version") != null) {

            Defs.setSystemProperty("apple.laf.useScreenMenuBar", "true");

            String appName = messageBundle.getString("viewer.window.title.default");
            Defs.setSystemProperty("com.apple.mrj.application.apple.menu.about.name", appName);
        }

        String className = propertiesManager.getLookAndFeel("application.lookandfeel", null);

        if (className != null) {
            try {
                UIManager.setLookAndFeel(className);
                return;
            } catch (Exception e) {
                Object[] messageArguments = { propertiesManager.getString("application.lookandfeel") };

                MessageFormat formatter = new MessageFormat(
                    messageBundle.getString("viewer.launcher.URLError.dialog.message"));

                JOptionPane.showMessageDialog(null, formatter.format(messageArguments),
                    messageBundle.getString("viewer.launcher.lookAndFeel.error.message"), 0);
            }
        }

        try {
            String defaultLF = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(defaultLF);
        } catch (Exception e) {
            logger.error("Error setting Swing Look and Feel.", e);
        }
    }


    public boolean isUseLookAndFeel() {
        return useLookAndFeel;
    }


    public void setUseLookAndFeel(boolean useLookAndFeel) {
        this.useLookAndFeel = useLookAndFeel;
    }


    public String getCustomPropertiesFilename() {
        return customPropertiesFilename;
    }


    public void setCustomPropertiesFilename(String customPropertiesFilename) {
        this.customPropertiesFilename = customPropertiesFilename;
    }


    public Properties getAdditionalSettings() {
        return additionalSettings;
    }


    public void setAdditionalSettings(Properties additionalSettings) {
        this.additionalSettings = additionalSettings;
    }

}
