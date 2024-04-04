package com.atech.gui_fw;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.*;

import com.atech.help.HelpContext;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;

public abstract class MainAppFrameAbstract extends JFrame implements ActionListener
{

    public abstract boolean hasSplashScreen();


    public abstract void initSplashScreen();

    private static final long serialVersionUID = -8971779470148201332L;

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
        MainAppFrameAbstract.setLookAndFeel();
    }


    /**
     * Set Look & Feel
     */
    public static void setLookAndFeel()
    {

        try
        {

            if (System.getProperty("os.name").startsWith("Mac")) // Yields "Mac OS X"
                return;

            String data[] = null; // ATDataAccess.getLFData();

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
    public MainAppFrameAbstract(String title, boolean developer_version)
    {
        // this is the first chance to call this method after an instance of
        // GGCProperties has been created
        // i18nControl.setLanguage();

        initDataAccess();
        // System.out.println("MainFrame before creation");
        // dataAccess = DataAccess.createInstance(this);

        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // dataAccess: "
        // + dataAccess);
        /*
         * i18nControl = dataAccess.getI18nControlInstance();
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
        createMenus();

        createToolBar();

        initAppGUI();

        // dataAccess.addObserver(ATDataAccessAbstract.OBSERVABLE_STATUS, this);

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

        // statusPanel.setStatusMessage(i18nControl.getMessage("INIT"));

        // Information Portal Setup
        // informationPanel = new InfoPanel();
        // getContentPane().add(informationPanel, BorderLayout.CENTER);

        // setDbActions(false);
        // setMenusByDbLoad(StatusBar.DB_STOPPED);

        // initPlugIns();

        // dataAccess.startToObserve();

        initAppSpecific();

        this.setVisible(true);

    }


    public abstract void initDataAccess();


    public abstract void initAppSpecific();


    /*
     * private void initPlugIns()
     * {
     * // TODO: deprecated
     * dataAccess.addPlugIn(DataAccess.PLUGIN_METERS, new MetersPlugIn(this,
     * i18nControl));
     * // dataAccess.getPlugIn(DataAccess.PLUGIN_METERS).checkIfInstalled();
     * dataAccess.addPlugIn(DataAccess.PLUGIN_PUMPS, new PumpsPlugIn(this,
     * i18nControl));
     * // dataAccess.getPlugIn(DataAccess.PLUGIN_PUMPS).checkIfInstalled();
     * dataAccess.addPlugIn(DataAccess.PLUGIN_CGMS, new CGMSPlugIn(this,
     * i18nControl));
     * // dataAccess.getPlugIn(DataAccess.PLUGIN_CGMS).checkIfInstalled();
     * }
     */

    public abstract void initAppGUI();


    public abstract void createMenus();


    public abstract void createToolBar();


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
                System.out.println("Help: Main Help Set present, creating broker");
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
    /**
     * Set menus by Db Loading status
     * 
     * @param status
     */
    public abstract void setMenusByDbLoad(int status);


    /**
     * Set Toolbar by Db Load
     * 
     * @param status
     */
    public abstract void setToolbarByDbLoad(int status);


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
    private void close()
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

            // char ch = i18nControl.getMnemonic(name);

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
            close();
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

}
