package com.atech.gui_fw;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.app.AbstractApplicationContext;
import com.atech.graphics.SplashAbstract;
import com.atech.help.HelpContext;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.DataAccessApp;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;

public class MainAppFrame extends JFrame // implements ActionListener
{

    private static final Logger LOG = LoggerFactory.getLogger(MainAppFrame.class);
    AbstractApplicationContext app_context;
    SplashAbstract splash;

    /**
     * Skin Look and Feel
     */
    public static SkinLookAndFeel s_skinlf;

    /**
     * Developer version 
     */
    public static boolean developer_version = false;

    /**
     * Menu Bar
     */
    private JMenuBar menuBar = new JMenuBar();

    /**
     * Tool Bars
     */
    private Hashtable<String, JToolBar> toolbars = null;

    // private JToolBar toolBar = new JToolBar();
    // private JToolBar toolbar_pen = new JToolBar();
    // private JToolBar toolbar_pump = new JToolBar();

    private ATDataAccessAbstract m_da = null;
    private static final String skinLFdir = "../data/skinlf_themes/";
    private I18nControlAbstract m_ic = null;

    // private JMenu menu_file, menu_bgs, /*menu_food,*/ menu_doctor,
    // menu_printing, menu_tools, menu_help, /*menu_meters, menu_pumps,*/
    // menu_data_graphs /*, menu_cgms , menu_misc */;

    private Hashtable<String, JMenu> menus = null;
    private Hashtable<String, GGCAction> actions = null;
    private Hashtable<String, GGCAction> toolbar_pen_items = null;
    private Hashtable<String, GGCAction> toolbar_pump_items = null;
    private int current_toolbar = -1;

    /**
     * Status panels
     */
    // public StatusBar statusPanel;


    /**
     * Information panels
     */
    // public InfoPanel informationPanel;

    /**
     * Static definitions (Look and Feel)
     */
    static
    {
        // MainAppFrame.setLookAndFeel();
    }


    /**
     * Set Look & Feel
     * @param data 
     */
    public void setLookAndFeel(String[] data)
    {

        try
        {

            // String data[] = null; //ATDataAccess.getLFData();

            if (data == null)
                return;
            else
            {
                if (data[0].equals("com.l2fprod.gui.plaf.skin.SkinLookAndFeel"))
                {
                    SkinLookAndFeel.setSkin(SkinLookAndFeel.loadThemePack(skinLFdir + data[1]));

                    s_skinlf = new com.l2fprod.gui.plaf.skin.SkinLookAndFeel();
                    UIManager.setLookAndFeel(s_skinlf);
                }
                else
                {
                    UIManager.setLookAndFeel(data[0]);
                }

                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error loading L&F: " + ex);
        }

    }


    /**
     * Constructor
     * 
     * @param title
     * @param developer_version
     */
    public MainAppFrame(AbstractApplicationContext app_ctx, boolean developer_version)
    {
        // this is the first chance to call this method after an instance of
        // GGCProperties has been created
        // m_ic.setLanguage();

        this.m_da = DataAccessApp.createInstanceWOFrame(app_ctx);

        if (app_ctx.hasSplashScreen())
        {
            app_ctx.initSplashScreen();
        }

        app_ctx.setInternalSplashProgress(1, "APPLICATION_CONTEXT");
        this.app_context = app_ctx;
        this.app_context.setFrame(this);

        // this.app_context.initDataAccess();
        this.app_context.setSplashProgress(false, 5, "SET_LOOK_AND_FEEL");

        setLookAndFeel(this.app_context.getDataAccess().getDbToolAbstract().getLFData());

        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        // this.setTitle(app_ctx.getTitle());

        // System.out.println("MainFrame before creation");
        // dataAccess = DataAccess.createInstance(this);

        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // dataAccess: "
        // + dataAccess);
        /*
         * m_ic = dataAccess.getI18nControlInstance();
         * dataAccess.addComponent(this);
         * dataAccess.developer_version = developer_version;
         * statusPanel = new StatusBar(this);
         * this.actions = new Hashtable<String, GGCAction>();
         * MainFrame.developer_version = developer_version;
         * String title_full = "GGC - GNU Gluco Control (" + GGC.full_version +
         * ")";
         * if (developer_version)
         * title_full += " - Developer edition";
         * setTitle(title_full);
         * setJMenuBar(menuBar);
         * setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
         * addWindowListener(new CloseListener());
         * helpInit();
         * // initPlugIns();
         * // menu_file, menu_bgs, menu_food, menu_doctor, menu_reports,
         * // menu_tools, menu_help;
         * //setTitle("");
         * this.setSoftwareMode();
         */

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new CloseListener());

        this.app_context.setSplashProgress(false, 6, "CREATE_MENUS");
        this.app_context.createMenus();

        this.app_context.setSplashProgress(false, 7, "CREATE_TOOLBAR");
        this.app_context.createToolBar();

        this.app_context.setSplashProgress(false, 8, "INIT_GUI");
        this.app_context.initAppGUI();

        this.addComponentListener(new ComponentListener()
        {

            public void componentResized(ComponentEvent e)
            {
                Dimension d = getSize(); // Rectangle b = getBounds();

                if (d.width < app_context.app_min_size.width || d.height < app_context.app_min_size.height)
                {
                    if (d.width < app_context.app_min_size.width)
                    {
                        d.width = app_context.app_min_size.width;
                    }

                    if (d.height < app_context.app_min_size.height)
                    {
                        d.height = app_context.app_min_size.height;
                    }

                    setSize(d);
                }

                // if getSize()
                // resize other components, refresh, etc
            }


            public void componentMoved(ComponentEvent e)
            {
            }


            public void componentShown(ComponentEvent e)
            {
            }


            public void componentHidden(ComponentEvent e)
            {
            }
        }); // dataAccess.addObserver(ATDataAccessAbstract.OBSERVABLE_STATUS,
            // this);

        /*
         * addToolBarButtonWithName("view_daily");
         * addToolBarButtonWithName("view_course");
         * addToolBarButtonWithName("view_spread");
         * addToolBarButtonWithName("view_freq"); addToolBarSpacer();
         * addToolBarSpacer();
         * addToolBarButtonWithName("view_hba1c"); addToolBarSpacer();
         * addToolBarSpacer(); //addToolBarButtonWithName("view_freq");
         * addToolBarButtonWithName("tools_pref"); addToolBarSpacer();
         * addToolBarSpacer(); addToolBarButtonWithName("hlp_help");
         */
        // this.menu_help.add(GGCHelp.helpItem);

        // getContentPane().remove(this.toolbars.get("TOOLBAR_PEN"));
        // getContentPane().remove(this.toolbars.get("TOOLBAR_PUMP"));

        // getContentPane().add(this.toolbars.get("TOOLBAR_PEN"),
        // BorderLayout.NORTH);
        // getContentPane().add(statusPanel, BorderLayout.SOUTH);

        // dataAccess.startDb(); //statusPanel);

        // statusPanel.setStatusMessage(m_ic.getMessage("INIT"));

        // Information Portal Setup
        // informationPanel = new InfoPanel();
        // getContentPane().add(informationPanel, BorderLayout.CENTER);

        // setDbActions(false);
        // setMenusByDbLoad(StatusBar.DB_STOPPED);

        // initPlugIns();

        // dataAccess.startToObserve();

        getContentPane().add(this.app_context.getMainPanel(), BorderLayout.CENTER);

        this.app_context.setSplashProgress(false, 9, "INIT_APP_SPECIFIC");
        this.app_context.initAppSpecific();

        if (app_context.app_location == null)
        {
            this.setBounds(0, 50, 800, 600);
        }
        else
        {
            this.setBounds(this.app_context.app_location.x, this.app_context.app_location.y, 800, 600);
        }

        this.setSize(this.app_context.getInitialSize());

        if (app_ctx.hasSplashScreen())
        {
            app_ctx.disposeSplashScreen();
        }

        // this.setTitle(app_ctx.getTitle());

        this.setVisible(true);

    }


    /*
     * private void initPlugIns()
     * {
     * // TODO: deprecated
     * dataAccess.addPlugIn(DataAccess.PLUGIN_METERS, new MetersPlugIn(this,
     * m_ic));
     * // dataAccess.getPlugIn(DataAccess.PLUGIN_METERS).checkIfInstalled();
     * dataAccess.addPlugIn(DataAccess.PLUGIN_PUMPS, new PumpsPlugIn(this,
     * m_ic));
     * // dataAccess.getPlugIn(DataAccess.PLUGIN_PUMPS).checkIfInstalled();
     * dataAccess.addPlugIn(DataAccess.PLUGIN_CGMS, new CGMSPlugIn(this, m_ic));
     * // dataAccess.getPlugIn(DataAccess.PLUGIN_CGMS).checkIfInstalled();
     * }
     */

    private void helpInit()
    {
        // HelpContext hc = new HelpContext("../data/help/GGC.hs");
        // dataAccess.setHelpContext(hc);
        boolean help_debug = true;

        if (help_debug)
        {
            System.out.println("Help Init");
        }

        HelpContext hc = m_da.getHelpContext();

        JMenuItem helpItem = new JMenuItem(m_ic.getMessage("HELP") + "...");
        helpItem.setIcon(new ImageIcon(getClass().getResource("/icons/help.gif")));
        hc.setHelpItem(helpItem);

        String mainHelpSetName = "../data/help/en/GGC.hs"; // added en
        mainHelpSetName = mainHelpSetName.replace("/", File.separator);

        hc.setMainHelpSetName(mainHelpSetName);

        // try to find the helpset and create a HelpBroker object
        if (hc.getMainHelpBroker() == null)
        {

            if (help_debug)
            {
                System.out.println("Help init broker");
            }

            HelpSet main_help_set = null;
            // HelpContext.mainHelpSet = null;

            // X ClassLoader cl = MainFrame.class.getClassLoader();
            // String help_url = "jar:file:pis_lang-0.1.jar!/help/PIS.hs";

            String help_url = "jar:file:ggc_help-0.1.jar!/help/en/GGC.hs";

            try
            {
                URL hsURL = new URL(help_url);

                if (hsURL == null)
                {
                    System.out.println("HelpSet " + help_url + " not found.");
                }
                else
                {
                    main_help_set = new HelpSet(null, hsURL);
                }
            }
            catch (HelpSetException ee)
            {
                System.out.println("HelpSet " + help_url + " could not be opened.");
                System.out.println(ee.getMessage());
            }
            catch (MalformedURLException ee)
            {
                System.out.println("Problem with HelpSet path: " + help_url + "\n" + ee);
            }

            HelpBroker main_help_broker = null;

            if (main_help_set != null)
            {
                LOG.debug("Help: Main Help Set present, creating broker");
                main_help_broker = main_help_set.createHelpBroker();
            }

            CSH.DisplayHelpFromSource csh = null;

            if (main_help_broker != null)
            {
                // CSH.DisplayHelpFromSource is a convenience class to display
                // the helpset
                csh = new CSH.DisplayHelpFromSource(main_help_broker);

                if (csh != null)
                {
                    // listen to ActionEvents from the helpItem
                    hc.getHelpItem().addActionListener(csh);
                }
            }

            hc.setDisplayHelpFromSourceInstance(csh);
            hc.setMainHelpBroker(main_help_broker);
            hc.setMainHelpSet(main_help_set);

            CSH.trackCSEvents();

        }

    }


    private JMenu createMenu(String name, String tool_tip)
    {
        JMenu item = new JMenu(m_ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(m_ic.getMnemonic(name));

        if (tool_tip != null)
        {
            item.setToolTipText(tool_tip);
        }

        // System.out.println("Item: " + item);

        this.menuBar.add(item);

        return item;
    }


    private JMenu createMenu(JMenu parent, String name, String tool_tip)
    {
        JMenu item = new JMenu(m_ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(m_ic.getMnemonic(name));

        if (tool_tip != null)
        {
            item.setToolTipText(m_ic.getMessage(tool_tip));
        }

        parent.add(item);

        return item;
    }

    /**
     * ToolBar: Pen/Injection
     */
    public static final int TOOLBAR_PEN_INJECTION = 1;

    /**
     * ToolBar: Pump
     */
    public static final int TOOLBAR_PUMP = 2;


    /*
     * private void createToolbarAction(String name, String tip, String
     * action_command, String icon_small, int toolbar_id)
     * {
     * GGCAction action = new GGCAction(name, tip, action_command);
     * if (icon_small != null)
     * {
     * action.putValue(Action.SMALL_ICON, dataAccess.getImageIcon(icon_small,
     * 24, 24,
     * this));
     * }
     * if (toolbar_id == MainFrame.TOOLBAR_PEN_INJECTION)
     * this.toolbar_pen_items.put(action_command, action);
     * else
     * this.toolbar_pump_items.put(action_command, action);
     * addToolBarButton(action, toolbar_id);
     * }
     * private void createAction(JMenu menu, String name, String tip, String
     * action_command, String icon_small)
     * {
     * GGCAction action = new GGCAction(name, tip, action_command);
     * if (icon_small != null)
     * {
     * action.putValue(Action.SMALL_ICON, dataAccess.getImageIcon(icon_small,
     * 15, 15,
     * this));
     * // new ImageIcon(getClass().getResource("/icons/" + icon_small)));
     * // action.putValue(Action.LARGE_ICON_KEY, new
     * // ImageIcon(getClass().getResource("/icons/" + icon_small)));
     * }
     * if (menu != null)
     * menu.add(action);
     * this.actions.put(action_command, action);
     * // return action;
     * }
     */

    private void setToolBarItemEnabled(String item_name, boolean enabled)
    {
        if (this.toolbar_pump_items.containsKey(item_name))
        {
            this.toolbar_pump_items.get(item_name).setEnabled(enabled);
        }

        if (this.toolbar_pen_items.containsKey(item_name))
        {
            this.toolbar_pen_items.get(item_name).setEnabled(enabled);
        }

    }


    // System.out.println("FIIIIIIIIIIIIIIIIIIIIIIXXXX this");
    // }
    public void close()
    {
        /*
         * if (dataAccess != null)
         * {
         * if (dataAccess.getDb() != null)
         * dataAccess.getDb().closeDb();
         * DataAccess.deleteInstance();
         * }
         */
        dispose();
        System.exit(0);
    }

    /*
     * private JButton addToolBarButton(Action action, int toolbar_id)
     * {
     * final JButton button;
     * if (toolbar_id == MainFrame.TOOLBAR_PEN_INJECTION)
     * button = this.toolbars.get("TOOLBAR_PEN").add(action);
     * else
     * button = this.toolbars.get("TOOLBAR_PUMP").add(action);
     * button.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
     * button.setFocusPainted(false);
     * button.setPreferredSize(new Dimension(28, 28));
     * return button;
     * }
     */

    class GGCAction extends AbstractAction
    {

        /**
         * 
         */
        private static final long serialVersionUID = -1022459758999093522L;


        GGCAction(String name, String command)
        {
            super();
            setName(m_ic.getMessageWithoutMnemonic(name));

            putValue(Action.NAME, m_ic.getMessageWithoutMnemonic(name));

            if (m_ic.hasMnemonic(name))
            {
                char ch = m_ic.getMnemonic(name);
                putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ch, Event.CTRL_MASK));
            }
            else
            {
                putValue(ACCELERATOR_KEY, null);
            }

            if (command != null)
            {
                putValue(ACTION_COMMAND_KEY, command);
            }

            command = name;
        }


        GGCAction(String name, String tooltip, String command)
        {
            super();
            setName(m_ic.getMessageWithoutMnemonic(name));

            putValue(Action.NAME, m_ic.getMessageWithoutMnemonic(name));

            // char ch = m_ic.getMnemonic(name);

            // System.out.println("Char ch: '" + ch + "'");

            // if ((ch != '0') || (ch != ' '))
            if (m_ic.hasMnemonic(name))
            {
                char ch = m_ic.getMnemonic(name);

                putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ch, Event.CTRL_MASK));
                // System.out.println("Found");
            }
            else
            {
                putValue(ACCELERATOR_KEY, null);
                // System.out.println("NOT Found");
            }

            if (tooltip != null)
            {
                putValue(SHORT_DESCRIPTION, m_ic.getMessage(tooltip));
            }

            if (command != null)
            {
                putValue(ACTION_COMMAND_KEY, command);
            }
        }


        public String getName()
        {
            return (String) getValue(Action.NAME);
        }


        public void actionPerformed(ActionEvent e)
        {

        }
    }


    private void featureNotImplemented(String cmd, String version)
    {
        String text = m_ic.getMessage("FEATURE");

        text += " '" + this.actions.get(cmd).getName() + "' ";
        text += String.format(m_ic.getMessage("IMPLEMENTED_VERSION"), version);
        text += "!";

        JOptionPane.showMessageDialog(this, text, m_ic.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE);

    }


    private void featureNotImplementedDescription(String desc, String version)
    {
        String text = m_ic.getMessage("FEATURE");

        text += " '" + desc + "' ";
        text += String.format(m_ic.getMessage("IMPLEMENTED_VERSION"), version);
        text += "!";

        JOptionPane.showMessageDialog(this, text, m_ic.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE);

    }

    private class CloseListener extends WindowAdapter
    {

        @Override
        public void windowClosing(WindowEvent e)
        {
            app_context.quitApplication();
        }
    }


    /**
     * To String
     * 
     * @see java.awt.Component#toString()
     */
    @Override
    public String toString()
    {
        return "MainFrame";
    }

    boolean title_set = false;


    public void actionPerformed(ActionEvent e)
    {

    }


    public SplashAbstract getSplash()
    {
        return this.splash;
    }

}
