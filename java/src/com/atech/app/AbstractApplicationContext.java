package com.atech.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import com.atech.db.cfg.DbConfig;
import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.hdb_object.User;
import com.atech.db.hibernate.tool.app.DbToolApplicationAbstract;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.db.hibernate.transfer.BackupRestoreRunner;
import com.atech.graphics.SplashAbstract;
import com.atech.graphics.components.menu.DynMenuItem;
import com.atech.gui_fw.CustomDataAccess;
import com.atech.gui_fw.MainAppFrame;
import com.atech.gui_fw.MenuContext;
import com.atech.gui_fw.config.AbstractConfigurationContext;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.I18nControlRunner;
import com.atech.i18n.mgr.LanguageManager;
import com.atech.utils.ATDataAccessLMAbstract;
import com.atech.utils.ATSwingUtils;
import com.atech.utils.DataAccessApp;
import com.atech.utils.file.ClassFinder;

public abstract class AbstractApplicationContext implements ActionListener
{

    public Dimension app_size = null;
    public Dimension app_min_size = new Dimension(800, 600);
    public Point app_location = null;
    protected DataAccessApp data_access = null;
    protected I18nControlAbstract m_ic = null;
    protected MainAppFrame frame = null;
    protected JMenuBar menu_bar = null;
    protected boolean developer_editon = false;
    protected boolean help_enabled = false;
    protected CustomDataAccess custom_da = null;
    protected AbstractConfigurationContext config_ctx = null;

    protected Hashtable<String, JMenu> menus = null;
    protected Hashtable<String, JMenuItem> actions = null;
    protected DbToolApplicationAbstract dbtool_app = null;
    protected Hashtable<String, String> help_keywords = new Hashtable<String, String>();
    protected AbstractCompanyContext company_context = null;

    protected Hashtable<String, MenuContext> menu_contexts = null;
    protected Hashtable<String, MenuContext> menu_item_contexts = null;

    public static final int DB_ACTION_RESTORE = 2;
    public static final int DB_ACTION_BACKUP = 1;
    Vector<Class<?>> plugins_classes = null;
    // ArrayList<String> plugins_db = null;
    protected ArrayList<String> db_files = new ArrayList<String>();
    protected DbConfig jdbc_config = null;
    protected boolean hasDbToolApplication = true;

    protected boolean is_demo = false;

    /**
     * Splash progress.
     * We have predefined splash objects (first int is id, second is progress), like for
     * example DataAccess initialization. Now if we have a lot of custom progress 
     * stuff to add, this original splash progress objects, can happen at differnt % of 
     * progress. We can fix this spash happenings at init of Splash.
     */
    public Hashtable<Integer, Integer> splash_progress = new Hashtable<Integer, Integer>();

    /**
     * The backupRestoreCollection.
     */
    protected BackupRestoreCollection backup_restore_collection = null;


    /**
     * Constructor
     * 
     * @param dev_edition
     */
    public AbstractApplicationContext(boolean dev_edition)
    {
        this.developer_editon = dev_edition;
        // initDataAccess();

        initSplashContext();
        initContext();

    }


    /**
     * Constructor
     * 
     * @param dev_edition
     */
    public AbstractApplicationContext(boolean dev_edition, AbstractCompanyContext cmp_ctx)
    {
        this.developer_editon = dev_edition;
        this.company_context = cmp_ctx;
        // initDataAccess();
        initSplashContext();
        initContext();
    }


    private void initSplashContext()
    {
        this.splash_progress.put(1, 5);
        this.splash_progress.put(2, 10);
        this.splash_progress.put(3, 15);
        this.splash_progress.put(4, 70);
        this.splash_progress.put(5, 75);
        this.splash_progress.put(6, 80);
        this.splash_progress.put(7, 85);
        this.splash_progress.put(8, 90);
        this.splash_progress.put(9, 95);
    }


    /**
     * Set Frame
     * 
     * @param frame_
     */
    public void setFrame(MainAppFrame frame_)
    {
        this.frame = frame_;
        this.setSplashProgress(false, 2, "DATA_CONTEXT");
        this.initDataAccess();
        // this.discoverPlugins();
        this.setSplashProgress(false, 3, "INIT_DB");
        this.initDb();
        this.setSplashProgress(false, 4, "LOAD_PLUGINS");
        this.loadPlugIns();

        this.frame.setTitle(getTitle());

    }


    /**
     * Get Frame
     * 
     * @return
     */
    public MainAppFrame getFrame()
    {
        return this.frame;
    }


    /**
     * Init Context
     */
    public abstract void initContext();


    public void discoverPlugins()
    {

        ClassFinder finder = new ClassFinder();
        // Vector<Class<?>> v = null;
        List<Throwable> errors = null;

        finder = new ClassFinder();
        plugins_classes = finder.findSubclasses("com.atech.business.plugin.BusinessPlugInAbstract");
        errors = finder.getErrors();

        for (int i = 0; i < plugins_classes.size(); i++)
        {
            Class c = plugins_classes.get(i);
            System.out.println("Class: " + c);
        }

    }


    public void setDemoMode(boolean demo)
    {
        this.is_demo = demo;
    }


    public abstract void initMenus();


    public void actionPerformed(ActionEvent ae)
    {
        actionRunner(ae);
    }


    public DbToolApplicationAbstract getDbToolApplication()
    {
        if (this.dbtool_app == null)
        {
            loadDbToolApplication();
        }

        return this.dbtool_app;
    }


    public abstract void loadDbToolApplication();


    public boolean hasDbToolApplication()
    {
        return hasDbToolApplication;
    }


    /**
     * Has Splash Screen
     * 
     * @return
     */
    public abstract boolean hasSplashScreen();


    /**
     * Init Splash Screen
     */
    public abstract void initSplashScreen();


    /**
     * Dispose Splash Screen
     */
    public abstract void disposeSplashScreen();


    public void setSplashProgress(boolean custom, int param_id, String description)
    {
        if (custom)
        {
            this.setInternalSplashProgress(param_id, description);
        }
        else
        {
            if (this.splash_progress.containsKey(param_id))
            {
                this.setInternalSplashProgress(this.splash_progress.get(param_id), description);
            }
            else
            {
                this.setInternalSplashProgress(0, description);
            }
        }
    }


    public abstract void setInternalSplashProgress(int progress, String description);


    public abstract SplashAbstract getSplashAbstractObject();


    public void initDataAccess()
    {
        // System.out.println("init Da6ta ACcess");
        this.data_access = DataAccessApp.createInstance(this.frame, this);
        this.data_access.jdbc_config = this.jdbc_config;
        this.m_ic = this.data_access.getI18nControlInstance();
        this.menu_bar = new JMenuBar();

        this.actions = new Hashtable<String, JMenuItem>();
        this.menus = new Hashtable<String, JMenu>();
    }


    public ATDataAccessLMAbstract getDataAccess()
    {
        return this.data_access;
    }


    public abstract void initAppSpecific();


    public abstract void initAppGUI();


    public abstract void createMenus();


    public abstract void createToolBar();


    public abstract String getTitle();


    public void setLoadingStatus(int status)
    {
        this.setMenusByDbLoad(status);
        this.setToolbarByDbLoad(status);
    }


    public void specialRestoreInit()
    {
    }


    public abstract BackupRestoreRunner getBackupRestoreRunner(int action);


    public abstract void loadBackupCollection();


    public BackupRestoreCollection getBackupRestoreCollection()
    {
        if (this.backup_restore_collection == null)
        {
            this.loadBackupCollection();
        }

        return this.backup_restore_collection;
    }


    // public abstract String getSpecialRestoreParameter();

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


    public I18nControlAbstract getI18nControl()
    {
        if (this.data_access == null)
        {
            this.initDataAccess();
        }

        return this.data_access.getI18nControlInstance();
    }


    public abstract void initDb();


    public abstract HibernateDb getDb();


    public Dimension getInitialSize()
    {
        return this.app_size;
    }


    // public ATDataAccessLMAbstract(LanguageManager lm, I18nControlRunner icr)
    public abstract LanguageManager getLanguageManager();


    public abstract I18nControlRunner getI18nControlRunner();

    public static Font menu_font = null;


    protected JMenu createMenu(String name, String tool_tip)
    {
        JMenu item = new JMenu(m_ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(m_ic.getMnemonic(name));

        if (menu_font == null)
        {
            menu_font = item.getFont().deriveFont(Font.PLAIN);
        }

        item.setFont(menu_font);

        if (tool_tip != null)
        {
            item.setToolTipText(tool_tip);
        }

        // System.out.println("Item: " + item);

        this.menu_bar.add(item);

        return item;
    }


    protected JMenu createMenu(JMenu parent, String name, String tool_tip)
    {
        JMenu item = new JMenu(m_ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(m_ic.getMnemonic(name));

        if (menu_font == null)
        {
            menu_font = item.getFont().deriveFont(Font.PLAIN);
        }

        item.setFont(menu_font);

        if (tool_tip != null)
        {
            item.setToolTipText(m_ic.getMessage(tool_tip));
        }

        parent.add(item);

        return item;
    }


    protected JMenuItem createMenuItem(JMenu menu_parent, DynMenuItem dmi)
    {
        // JMenu menu, String name, String tip, String action_command, String
        // icon_small, String keyword, int min_level, boolean process
        // this.createMenuItem(m, mi.getActionCommand(), mi.getTooltip(),
        // mi.getActionCommand(), "logon.png", "LOGIN", 0, false);

        return createMenuItem(menu_parent, dmi.getName(), dmi.getTooltip(), dmi.getActionCommand(), dmi.getIconSmall(),
            dmi.getKeyword(), dmi.getMinLevel(), dmi.getProcess(), dmi.getActionListener());
    }


    protected JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command, String icon_small,
            String keyword, int min_level, boolean process)
    {
        return createMenuItem(menu, name, tip, action_command, icon_small, keyword, min_level, process, this);
    }


    protected JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command, String icon_small,
            String keyword, int min_level, boolean process, ActionListener al)
    {

        JMenuItem mi = new JMenuItem();
        mi.setName(m_ic.getMessageWithoutMnemonic(name));
        mi.setText(m_ic.getMessageWithoutMnemonic(name));

        if (menu_font == null)
        {
            menu_font = mi.getFont().deriveFont(Font.PLAIN);
        }

        mi.setFont(menu_font);

        if (m_ic.hasMnemonic(name))
        {
            char ch = m_ic.getMnemonic(name);
            mi.setAccelerator(KeyStroke.getKeyStroke(ch, Event.CTRL_MASK));
        }

        if (tip != null)
        {
            mi.setToolTipText(m_ic.getMessage(tip));
        }

        if (action_command != null)
        {
            mi.setActionCommand(action_command);
        }

        if (icon_small != null)
        {
            mi.setIcon(ATSwingUtils.getImageIcon(icon_small, 15, 15, this.frame, data_access));
        }

        if (menu != null)
        {
            menu.add(mi);
        }

        mi.addActionListener(al);

        this.actions.put(action_command, mi);

        this.menu_item_contexts.put(keyword, new MenuContext(keyword, mi, min_level, process));

        return mi;

    }


    public boolean isHelpEnabled()
    {
        return help_enabled;
    }


    public abstract void actionRunner(ActionEvent ae);


    public JPanel getMainPanel()
    {
        if (this.company_context != null && this.company_context.hasCustomMainPanel())
            return this.company_context.getCustomMainPanel();
        else
            return this.createMainPanel();
    }


    public abstract JPanel createMainPanel();


    public CustomDataAccess getCustomDataAccess()
    {
        return this.custom_da;
    }


    /**
     * Get Users
     * 
     * @return
     */
    public ArrayList<User> getUsers()
    {
        return new ArrayList<User>();
    }


    public abstract void quitApplication();


    public abstract void loadHelpKeywords();


    public String getHelpKeyword(String key, String not_found_default)
    {
        if (help_keywords.containsKey(key))
            return help_keywords.get(key);
        else
            return not_found_default;
    }


    public abstract void loadPlugIns();

}
