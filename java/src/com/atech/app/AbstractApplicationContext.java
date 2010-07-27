package com.atech.app;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.hdb_object.User;
import com.atech.db.hibernate.tool.DbToolApplicationAbstract;
import com.atech.gui_fw.CustomDataAccess;
import com.atech.gui_fw.MainAppFrame;
import com.atech.gui_fw.MenuContext;
import com.atech.gui_fw.config.AbstractConfigurationContext;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.I18nControlRunner;
import com.atech.i18n.mgr.LanguageManager;
import com.atech.utils.ATDataAccessLMAbstract;
import com.atech.utils.DataAccessApp;

public abstract class AbstractApplicationContext implements ActionListener
{
    protected Dimension app_size = null;
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
    protected Hashtable<String,String> help_keywords = new Hashtable<String,String>(); 
    protected AbstractCompanyContext company_context = null;
    
    protected Hashtable<String, MenuContext> menu_contexts = null;
    protected Hashtable<String, MenuContext> menu_item_contexts = null;
    
    
    /**
     * Constructor
     * 
     * @param dev_edition
     */
    public AbstractApplicationContext(boolean dev_edition)
    {
        this.developer_editon = dev_edition;
        //initDataAccess();        
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
        //initDataAccess();        
        initContext();
    }
    
    
    
    
    
    /**
     * Set Frame
     * 
     * @param frame_
     */
    public void setFrame(MainAppFrame frame_)
    {
        this.frame = frame_;
        this.initDataAccess();
        this.loadPlugIns();
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
    
    
    
    public abstract void initMenus();
    
    
    public void actionPerformed(ActionEvent ae)
    {
        actionRunner(ae);
    }

    public DbToolApplicationAbstract getDbToolApplication()
    {
        if (this.dbtool_app==null)
            loadDbToolApplication();
        
        return this.dbtool_app;
    }
    
    public abstract void loadDbToolApplication();
    
    public abstract boolean hasSplashScreen();
    
    public abstract void initSplashScreen();

    
    public void initDataAccess()
    {
        //System.out.println("init Da6ta ACcess");
        this.data_access = DataAccessApp.createInstance(this.frame, this);
        this.initDb();
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
        if (this.data_access==null)
            this.initDataAccess();
        
        return this.data_access.getI18nControlInstance();
    }
    
    
    public abstract void initDb();
    
    
    public abstract HibernateDb getDb();
    
    
    
    public Dimension getInitialSize()
    {
        return this.app_size;
    }
    
    //public ATDataAccessLMAbstract(LanguageManager lm, I18nControlRunner icr)
    public abstract LanguageManager getLanguageManager();
    
    public abstract I18nControlRunner getI18nControlRunner();
    
    
    protected JMenu createMenu(String name, String tool_tip)
    {
        JMenu item = new JMenu(m_ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(m_ic.getMnemonic(name));

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

        if (tool_tip != null)
        {
            item.setToolTipText(m_ic.getMessage(tool_tip));
        }

        parent.add(item);

        return item;
    }
    
    
    
    protected JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command, String icon_small, String keyword, int min_level, boolean process)
    {
        
        JMenuItem mi = new JMenuItem();
        mi.setName(m_ic.getMessageWithoutMnemonic(name));
        mi.setText(m_ic.getMessageWithoutMnemonic(name));
        
        
        if (m_ic.hasMnemonic(name))
        {
            char ch = m_ic.getMnemonic(name);
            mi.setAccelerator(KeyStroke.getKeyStroke(ch, Event.CTRL_MASK));
        }
 
        if (tip != null)
            mi.setToolTipText(m_ic.getMessage(tip));

        if (action_command != null)
            mi.setActionCommand(action_command);
        
        
        if (icon_small != null)
        {
            mi.setIcon(this.data_access.getImageIcon(icon_small, 15, 15, this.frame));
        }

        if (menu != null)
            menu.add(mi);

        mi.addActionListener(this);
        
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
        if ((this.company_context!=null) && (this.company_context.hasCustomMainPanel()))
        {
            return this.company_context.getCustomMainPanel();
        }
        else
        {
            return this.createMainPanel();
        }
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
        {
            return help_keywords.get(key);
        }
        else
            return not_found_default;
    }
    
    
    public abstract void loadPlugIns();
    
    
}
