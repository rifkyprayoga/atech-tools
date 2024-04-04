package com.atech.i18n.tool.simple.internal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.atech.data.enums.InternalSetting;
import com.atech.graphics.layout.TableLayoutUtil;
import com.atech.help.HelpCapable;
import com.atech.i18n.tool.simple.data.TranslationToolConfigurationDto;
import com.atech.i18n.tool.simple.data.TranslationToolSettingsDto;
import com.atech.utils.ATDataAccessLMAbstract;
import com.atech.utils.ATSwingUtils;

import info.clearthought.layout.TableLayout;

/**
 * Created by andy on 30/06/17.
 */
public class TranslationToolInternalWizard1_ConfigurationCheck extends JDialog implements ActionListener, HelpCapable
{

    private static final long serialVersionUID = 4522778453835330629L;

    JFrame parent;
    ATDataAccessLMAbstract dataAccess;

    JButton buttonNext, helpButton;
    TranslationToolConfigurationDto configuration;


    public TranslationToolInternalWizard1_ConfigurationCheck(JFrame frame, ATDataAccessLMAbstract dataAccess)
    {
        super(frame);
        parent = frame;
        this.dataAccess = dataAccess;

        TranslationToolConfigurationDto configuration = dataAccess.getTranslationToolConfiguration();

        if (configuration != null && configuration.getSettings() != null)
        {
            TranslationToolInternalWizard2_LanguageSelector languageSelector = new TranslationToolInternalWizard2_LanguageSelector(
                    frame, dataAccess, configuration);

            this.dispose();
            languageSelector.setVisible(true);

            return;
        }

        if (configuration == null)
        {
            ATSwingUtils.initLibrary();
            ATSwingUtils.showErrorDialog(this, //
                "Your application isn't prepared for Translation Tool<br>"
                        + "contact developers of it, and ask for this function.<br>",
                dataAccess.getI18nControlInstance());

            return;
        }

        dataAccess.addComponent(this);

        initGUI();

        setVisible(true);
    }


    private void initGUI()
    {
        int width = 580;
        int height = 300;

        ATSwingUtils.initLibrary();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        // panel.setBorder(new LineBorder(Color.black));
        panel.setLayout(TableLayoutUtil.createVerticalLayout(60, TableLayout.FILL, 10, 30, 10));

        getContentPane().add(panel);

        String titleMessage = "Translation Configuration";

        this.setTitle(titleMessage);
        panel.add(ATSwingUtils.getTitleLabel(titleMessage, ATSwingUtils.FONT_BIG_BOLD), "0,0");

        JPanel bodyPanel = new JPanel();
        // bodyPanel.setBorder(new LineBorder(Color.red));

        double[][] sizes = { { 0.10, TableLayout.FILL, 0.10 }, //
                             { 0.08, TableLayout.FILL, 0.08, 30, 0.08 } };

        bodyPanel.setLayout(new TableLayout(sizes));

        JLabel label = new JLabel("<html>Since this is your first time running Translation tool, "
                + "we need to do some initial configuration. Please click on button and fill "
                + "all required data and then close it. If everything was filled, you will be "
                + "get option to go to next step. This window won't be visible, after initial"
                + "configuration, and if you need to change something use Configuration option"
                + "in the Translation Tool itself.</html>");

        bodyPanel.add(label, "1, 1");

        JButton configButton = new JButton("Initial Configuration");
        configButton.setActionCommand("config");
        configButton.addActionListener(this);

        bodyPanel.add(configButton, "1, 3");

        panel.add(bodyPanel, "0, 1");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new LineBorder(Color.green));
        buttonsPanel.setLayout(TableLayoutUtil.createHorizontalLayout(20, 120, 6, 120, TableLayout.FILL, 120, 20));

        helpButton = ATSwingUtils.createHelpButton(this, dataAccess, "help.png");
        buttonsPanel.add(helpButton, "1, 0");

        JButton button = new JButton("  Cancel");
        button.setIcon(ATSwingUtils.getImageIcon_22x22("cancel.png", this, dataAccess));
        button.setActionCommand("cancel");
        button.addActionListener(this);
        buttonsPanel.add(button, "3, 0");

        buttonNext = new JButton(" Next");
        buttonNext.setIcon(ATSwingUtils.getImageIcon_22x22("next.png", this, dataAccess));
        buttonNext.setActionCommand("next");
        buttonNext.addActionListener(this);
        buttonNext.setEnabled(false);
        buttonsPanel.add(buttonNext, "5, 0");

        panel.add(buttonsPanel, "0, 3");

        setBounds(100, 100, width, height + 20);

        dataAccess.enableHelp(this);
        // this.dataAccess.centerJDialog(this, parent);
    }


    private void createNameLabel(String keyword, JPanel panel, String layoutConstraint)
    {
        ATSwingUtils.getLabel(keyword + ":", panel, layoutConstraint, ATSwingUtils.FONT_NORMAL_BOLD);
    }


    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("next"))
        {
            TranslationToolInternalWizard2_LanguageSelector wizard = new TranslationToolInternalWizard2_LanguageSelector(
                    parent, dataAccess, configuration);
            this.dispose();
            dataAccess.removeComponent(this);
            wizard.setVisible(true);
        }
        else if (actionCommand.equals("config"))
        {
            System.out.println("Unhandled action: " + actionCommand);

            TTIConfiguration configDialog = new TTIConfiguration(this, this.dataAccess);

            if (configDialog.wasOperationSuccessful())
            {
                this.configuration.setSettings((TranslationToolSettingsDto) configDialog.getEditedObject());
                this.buttonNext.setEnabled(true);
            }

            // FIXME

        }
        else
            System.out.println("Unknown action: " + actionCommand);
    }


    public String getHelpId()
    {
        return dataAccess.getInternalSetting(InternalSetting.Help_TranslationTool_Wizard1);
    }


    public JButton getHelpButton()
    {
        return this.helpButton;
    }


    public Component getComponent()
    {
        return this;
    }
}
