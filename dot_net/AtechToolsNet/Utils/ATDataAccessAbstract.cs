
// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/

using System;
using log4net;
using System.Collections.Generic;
using ATechTools.I18n;
using System.ComponentModel;
using System.Text;
using AtechTools.Core.Utils;
namespace ATechTools.Util
{



public abstract class ATDataAccessAbstract
{

    private ILog log = LogManager.GetLogger(typeof(ATDataAccessAbstract));

    // LF
    // Dictionary<String,String> availableLF_full = null;
    // Object[] availableLF = null;
    // Object[] availableLang = null;

    /**
     * The selected lf.
     */
    String selectedLF = null;
    
    /**
     * The sub selected lf.
     */
    String subSelectedLF = null;

    // config file
    /**
     * The config_db_values.
     */
    Dictionary<String, String> config_db_values = null;
    // public int selected_db = -1;
    // public int selected_lang = 1;
    // public String selected_LF_Class = null; // class
    // public String selected_LF_Name = null; // name
    // public String skinLFSelected = null;
    // String allDbs[] = null;


    /**
     * The m_settings_ht.
     */
    public Dictionary<String, String> m_settings_ht = null;

    /**
     * The path prefix.
     */
    public static String pathPrefix = ".";

    /**
     * The color_background.
     */
    //public Color color_background; 
    
    /**
     * The color_foreground.
     */
    //public Color color_foreground;

    /**
     * The m_lang_info.
     */
    //protected LanguageInfo m_lang_info;
    
    //public boolean printing_plugin_installed = false;

    /**
     * The m_i18n.
     */
    protected I18nControlAbstract m_i18n = null; // ATI18nControl.getInstance();


    private int db_loading_status = 0;

    /**
     * The options_yes_no.
     */
    public String[] options_yes_no = null;
    // x public Dictionary typesHT = new Dictionary();
    /**
     * The types all.
     */
    public Object[] typesAll = null;
    
    /**
     * The border_line.
     */
    //public LineBorder border_line;

    
    /**
     * The plugins.
     */
    //public Dictionary<String,PlugInClient> plugins;
    
    /**
     * The fonts.
     */
    //public Font[] fonts = null;

    // Configuration icons

    /**
     * The m_collator.
     */
    //protected Collator m_collator = null;
    
    /**
     * The parent.
     */
    //protected Container parent = null;

    
    /**
     * The real_decimal.
     */
    public static char real_decimal;
    
    /**
     * The false_decimal.
     */
    public static char false_decimal;
    
    private static bool decimals_set;
    
    //private UpdateConfiguration update_configuration = null;

    /**
     * The graph_config.
     */
    //protected GraphConfigProperties graph_config = null;
    //private static HibernateDb m_db_hib;
    
    /**
     * The special_parameters.
     */
    protected Dictionary<String, String> special_parameters = null;
    
    /*
     * ABS REMOVED
     * 
     * public String contact_types[] = { m_i18n.getMessage("SELECT"),
     * m_i18n.getMessage("PHONE"), m_i18n.getMessage("GSM"),
     * m_i18n.getMessage("FAX"), m_i18n.getMessage("EMAIL"),
     * m_i18n.getMessage("WEB_PAGE"), m_i18n.getMessage("ICQ_MSNG"),
     * m_i18n.getMessage("YAHOO_MSNG"), m_i18n.getMessage("AIM_MSNG"),
     * m_i18n.getMessage("JABBER_MSNG"), m_i18n.getMessage("MSN_MSNG"),
     * m_i18n.getMessage("SKYPE_MSNG"), m_i18n.getMessage("OTHER") };
     * 
     * public ImageIcon contact_icons[] = { null, new
     * ImageIcon("images/c_phone.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/c_GSM.gif"), //m_i18n.getMessage("GSM"), new
     * ImageIcon("images/c_fax.gif"), //m_i18n.getMessage("FAX"), new
     * ImageIcon("images/c_email.gif"), //m_i18n.getMessage("EMAIL"), new
     * ImageIcon("images/c_hp.gif"), //m_i18n.getMessage("WEB_PAGE"), new
     * ImageIcon("images/c_icq.gif"), //m_i18n.getMessage("ICQ_MSNG"), new
     * ImageIcon("images/c_yahoo.gif"), //m_i18n.getMessage("YAHOO_MSNG"), new
     * ImageIcon("images/c_aim.gif"), //m_i18n.getMessage("MSN_MSNG") new
     * ImageIcon("images/c_jabber.gif"), new ImageIcon("images/c_msn.gif"),
     * null, null };
     * 
     * 
     * public ImageIcon db_status_load[] = { new
     * ImageIcon("images/dot_red.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/dot_orange.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/dot_blue.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/dot_green.gif"), };
     */

    /**
     * The days.
     */
    public String[] days = new String[7];
    /*
     * { m_i18n.getMessage("MONDAY"), m_i18n.getMessage("TUESDAY"),
     * m_i18n.getMessage("WEDNESDAY"), m_i18n.getMessage("THURSDAY"),
     * m_i18n.getMessage("FRIDAY"), m_i18n.getMessage("SATURDAY"),
     * m_i18n.getMessage("SUNDAY") };
     */

    /**
     * 
     */
    public String[] months = new String[12];

    /*
     * { m_i18n.getMessage("JANUARY"), m_i18n.getMessage("FEBRUARY"),
     * m_i18n.getMessage("MARCH"), m_i18n.getMessage("APRIL"),
     * m_i18n.getMessage("MAY"), m_i18n.getMessage("JUNE"),
     * m_i18n.getMessage("JULY"), m_i18n.getMessage("AUGUST"),
     * m_i18n.getMessage("SEPTEMBER"), m_i18n.getMessage("OCTOBER"),
     * m_i18n.getMessage("NOVEMBER"), m_i18n.getMessage("DECEMBER") };
     */
    /*
     * public String[] userTypesBasic = { m_i18n.getMessage("SELECT"),
     * m_i18n.getMessage("USER_EXTERNAL"), m_i18n.getMessage("USER_INTERNAL") };
     */

    
    
    
    // ********************************************************
    // ****** Constructors and Access methods *****
    // ********************************************************

    // Constructor: DataAccess
    /**
     * 
     * This is DataAccess constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.<br>
     * <br>
     * @param ic 
     * 
     */
    public ATDataAccessAbstract(I18nControlAbstract ic)
    {
//        DateTime now = DateTime.Now;

//        log.Debug("         Da [Start]: 0.000"); // + TimeDifference(now, DateTime.Now));
        this.m_i18n = ic;
        loadArraysTranslation();
//        log.Debug("         Da [ArrayTrans]: " + DataUtil.TimeDifference(now, DateTime.Now, 1));
        checkPrerequisites();
//        log.Debug("         Da [Prereq]: " + DataUtil.TimeDifference(now, DateTime.Now, 1));
        //loadFonts();
        m_settings_ht = new Dictionary<String, String>();
        //plugins = new Dictionary<String,PlugInClient>();
        //this.m_collator = this.m_i18n.getCollationDefintion();
        //loadPlugIns();
        //loadBackupRestoreCollection();
        
        //if (!ATDataAccessAbstract.decimals_set)
        //    initDecimals();


        InitSpecial();
//        log.Debug("         Da [InitSpecial]: " + DataUtil.TimeDifference(now, DateTime.Now, 1));
    }

    
    
    /**
     * Inits the special.
     */
    public abstract void InitSpecial();
    
    
    /**
     * Gets the hibernate db.
     * 
     * @return the hibernate db
     */
    public abstract object getHibernateDb();
    
    
    /**
     * Load plug ins.
     */
    public abstract void loadPlugIns();
    
    
    // Method: getInstance
    // Author: Andy
    /**
     * 
     * This method returns reference to OmniI18nControl object created, or if no
     * object was created yet, it creates one.<br>
     * <br>
     * 
     * @return Reference to OmniI18nControl object
     * 
     */
    /*
     * static public ATDataAccessAbstract getInstance() { if (m_da == null) m_da
     * = new ATDataAccessAbstract(); return m_da; }
     */

    // Method: deleteInstance
    /**
     * This method sets handle to DataAccess to null and deletes the instance. <br>
     * <br>
     */
   /* public void deleteInstance()
    {
        //m_i18n = null;

    } */

    // INIT

    public virtual void loadArraysTranslation()
    {
        // months
        months[0] = m_i18n.GetMessage("JANUARY");
        months[1] = m_i18n.GetMessage("FEBRUARY");
        months[2] = m_i18n.GetMessage("MARCH");
        months[3] = m_i18n.GetMessage("APRIL");
        months[4] = m_i18n.GetMessage("MAY");
        months[5] = m_i18n.GetMessage("JUNE");
        months[6] = m_i18n.GetMessage("JULY");
        months[7] = m_i18n.GetMessage("AUGUST");
        months[8] = m_i18n.GetMessage("SEPTEMBER");
        months[9] = m_i18n.GetMessage("OCTOBER");
        months[10] = m_i18n.GetMessage("NOVEMBER");
        months[11] = m_i18n.GetMessage("DECEMBER");

        // days
        days[0] = m_i18n.GetMessage("MONDAY");
        days[1] = m_i18n.GetMessage("TUESDAY");
        days[2] = m_i18n.GetMessage("WEDNESDAY");
        days[3] = m_i18n.GetMessage("THURSDAY");
        days[4] = m_i18n.GetMessage("FRIDAY");
        days[5] = m_i18n.GetMessage("SATURDAY");
        days[6] = m_i18n.GetMessage("SUNDAY");

    }

    
    
    private void initDecimals()
    {
        // FIXME
        /*
        DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
        
        ATDataAccessAbstract.real_decimal = dfs.getDecimalSeparator();
        
        
        
        if (dfs.getDecimalSeparator()=='.')
            ATDataAccessAbstract.false_decimal = ',';
        else
            ATDataAccessAbstract.false_decimal = '.';
        
        
        
        ATDataAccessAbstract.decimals_set = true;
         */
    }
    
    
    
    
    // ********************************************************************
    // ****** Compoment managing *****
    // ****** (needed to have current window for displaying dialog) *****
    // ********************************************************************

    /**
     * The components.
     */
//    protected ArrayList<Component> components = new ArrayList<Component>();

    
    /**
     * Gets the current component parent.
     * 
     * @return the current component parent
     */
/*    public Component getCurrentComponentParent()
    {
        return this.components.get(this.components.size() - 2);
    } */
    
    /**
     * Gets the current component.
     * 
     * @return the current component
     */
/*    public Component getCurrentComponent()
    {
        return this.components.get(this.components.size() - 1);
    } */

    /**
     * Adds the component.
     * 
     * @param cmp the cmp
     */
/*    public void addComponent(Component cmp)
    {
        this.components.add(cmp);
    } */

    /**
     * List components.
     */
/*    public void listComponents()
    {
        Console.WriteLine("Lst: " + this.components);
    } */
    
    /**
     * Removes the component.
     * 
     * @param cmp the cmp
     */
/*    public void removeComponent(Component cmp)
    {
        //int curr = 0;
        ArrayList<Component> cmps_new = new ArrayList<Component>();

        for (int i = 0; i < this.components.size(); i++)
        {
            if (this.components.get(i).equals(cmp))
            {
                break;
            }
            else
            {
                cmps_new.add(this.components.get(i));
            }

        }

        this.components = cmps_new;
        // Console.WriteLine("Remove: " + this.components);
    } */

    // ********************************************************
    // ****** Error handling *****
    // ********************************************************

    /**
     * Creates the error dialog.
     * 
     * @param module the module
     * @param action the action
     * @param ex the ex
     * @param err_msg1 the err_msg1
     */
    public void createErrorDialog(String module, String action, Exception ex,
            String err_msg1)
    {
        createErrorDialog(module, action, ex, err_msg1, null);
    }

    /**
     * Creates the error dialog.
     * 
     * @param module the module
     * @param action the action
     * @param ex the ex
     * @param err_msg1 the err_msg1
     * @param err_msg2 the err_msg2
     */
    public void createErrorDialog(String module, String action, Exception ex,
            String err_msg1, String err_msg2)
    {
        log.Warn("createErrorDialog not implemented yet !");
        // FIXME
/*        if (this.getCurrentComponentParent() is JFrame)
        {
            new ErrorDialog((JFrame)this.getCurrentComponentParent(),
                    this, "GGC", module, action, ex, err_msg1, err_msg2);
        }
        else
        {
             new ErrorDialog((JDialog)this.getCurrentComponentParent(),
                        this, "GGC", module, action, ex, err_msg1, err_msg2);
        }
  */      
        
        
        /*
        if (this.getC.getLastParentType() == ATDataAccessAbstract.PARENT_FRAME)
        {
            new ErrorDialog(this.getLastParentFrame(),
                    this, "GGC", module, action, ex, err_msg1, err_msg2);
        }
        else
        {
         new ErrorDialog(this.getLastParentDialog(),
                    this, "GGC", module, action, ex, err_msg1, err_msg2);
        }*/
    }

    // ********************************************************
    // ****** Parent Deep Handling *****
    // ********************************************************
/*
    // TODO: Move
    public static const int PARENT_FRAME = 1;
    public static const int PARENT_DIALOG = 2;

    public ArrayList<Container> cnt_list = new ArrayList<Container>();

    public void addContainer(Container cont)
    {
        Console.WriteLine("!!!! addContainer: " + this.cnt_list + "\nDataAccess: " + this);
        this.cnt_list.add(cont);
        Console.WriteLine("!!!! addContainer: " + this.cnt_list + "\nDataAccess: " + this);

    }

    public void removeContainer(Container cont)
    {
        Console.WriteLine("!!!! removeContainer: " + this.cnt_list+ "\nDataAccess: " + this);
        this.cnt_list.remove(cont);
        Console.WriteLine("!!!! removeContainer: " + this.cnt_list+ "\nDataAccess: " + this);
    }

    public int getLastContainerType()
    {
        if ((cnt_list.get(cnt_list.size() - 1)) is JFrame)
        {
            return ATDataAccessAbstract.PARENT_FRAME;
        }
        else
            return ATDataAccessAbstract.PARENT_DIALOG;
    }

    public int getLastParentType()
    {
        if (this.cnt_list.size() < 2)
        {
            return ATDataAccessAbstract.PARENT_FRAME;
        }
        else
        {
            if ((cnt_list.get(cnt_list.size() - 2)) is JFrame)
            {
                return ATDataAccessAbstract.PARENT_FRAME;
            }
            else
                return ATDataAccessAbstract.PARENT_DIALOG;

        }

    }

    public Container getLastParent()
    {
        Console.WriteLine("Conatiners: " + this.cnt_list);
        
        if (this.cnt_list.size() == 1)
        {
            return cnt_list.get(0);
        }
        if (this.cnt_list.size() < 2)
        {
            return this.getMainParent();
        }
        else
        {
            return (cnt_list.get(cnt_list.size() - 2));
        }
    }

    public JFrame getLastParentFrame()
    {
        return (JFrame) this.getLastParent();
    }

    public JDialog getLastParentDialog()
    {
        return (JDialog) this.getLastParent();
    }
*/
    // ********************************************************
    // ****** Application Handling *****
    // ********************************************************

    /**
     * The main_parent.
     */
//    JFrame main_parent = null;

    /**
     * Get Application Name
     * 
     * @return
     */
    public abstract String ApplicationName { get; }

    /**
     * Get Images Root (Must have ending back-slash)
     * 
     * @return
     */
    public abstract String ImagesRoot { get; }

    /**
     * The main_parent_type.
     */
    public int main_parent_type = 1;

    /**
     * Sets the main parent.
     * 
     * @param frame the new main parent
     */
    public void setMainParent(object frame)
    {
//        this.main_parent = frame;
//        this.addComponent(this.main_parent);
        //this.addContainer((Container)this.main_parent);
    }

    /**
     * Gets the main parent.
     * 
     * @return the main parent
     */
/*    public JFrame getMainParent()
    {
        return this.main_parent;
    } */

    /**
     * Check Prerequisites
     */
    public abstract void checkPrerequisites();

    // ********************************************************
    // ****** Help *****
    // ********************************************************

    /**
     * The help_context.
     */
//    HelpContext help_context = null;

    /**
     * Gets the help context.
     * 
     * @return the help context
     */
/*    public HelpContext getHelpContext()
    {
        return this.help_context;
    }*/

    /**
     * Sets the help context.
     * 
     * @param hc the new help context
     */
/*    public void setHelpContext(HelpContext hc)
    {
        this.help_context = hc;
    }

    /**
     * Enable help.
     * 
     * @param hc the hc
     */
/*    public void enableHelp(HelpCapable hc)
    {
        this.help_context.getMainHelpBroker().enableHelpOnButton(hc.getHelpButton(), hc.getHelpId(), null);
        this.help_context.getMainHelpBroker().enableHelpKey(hc.getComponent(), hc.getHelpId(), null);
    }

    /**
     * Creates the help button by size.
     * 
     * @param width the width
     * @param height the height
     * @param comp the comp
     * 
     * @return the j button
     */
/*    public JButton createHelpButtonBySize(int width, int height, Container comp)
    {
        JButton help_button = new JButton("    "
                + this.getI18nControlInstance().getMessage("HELP"));
        help_button.setPreferredSize(new Dimension(width, height));
        help_button.setIcon(this.getImageIcon_22x22("help.png", comp));

        // help_button.setIconTextGap(12);

        return help_button;
    }

    
    /**
     * Creates the help button by bounds.
     * 
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param comp the comp
     * 
     * @return the j button
     */
/*    public JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp)
    {
        return createHelpButtonByBounds(x, y, width, height, comp, null);
    }
    
    
    /**
     * Creates the help button by bounds.
     * 
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param comp the comp
     * @param font_id the font_id
     * 
     * @return the j button
     */
/*    public JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp, int font_id)
    {
        return createHelpButtonByBounds(x, y, width, height, comp, this.getFont(font_id));
    }

    /**
     * Creates the help button by bounds.
     * 
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param comp the comp
     * @param font the font
     * 
     * @return the j button
     */
/*    public JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp, Font font)
    {
        JButton help_button = new JButton("    " + this.getI18nControlInstance().getMessage("HELP"));
        help_button.setBounds(x, y, width, height);
        help_button.setIcon(this.getImageIcon_22x22("help.png", comp));
        
        if (font!=null)
            help_button.setFont(font);
        
        return help_button;
    }
    
    
    
    // ********************************************************
    // ****** I18n *****
    // ********************************************************

    /**
     * Gets the i18n control instance.
     * 
     * @return the i18n control instance
     */
    public I18nControlAbstract getI18nControlInstance()
    {
        return this.m_i18n;
    }


    public I18nControlAbstract I18nInstance
    {
        get { return this.m_i18n; }
    }






    /*
     * public void setI18nControlInstance(I18nControlAbstract i18n) { this.i18n
     * = i18n; }
     */

    
    // ********************************************************
    // ******                Plug-ins                     *****
    // ********************************************************
    
    
    /**
     * Adds the plug in.
     * 
     * @param key the key
     * @param plugin the plugin
     */
/*    public void addPlugIn(String key, PlugInClient plugin)
    {
        this.plugins.put(key, plugin);
    }
  */  
    /**
     * Gets the plug in.
     * 
     * @param key the key
     * 
     * @return the plug in
     */
/*    public PlugInClient getPlugIn(String key)
    {
        return this.plugins.get(key);
    }
  */  
    
    
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // ********************************************************
    // ****** Config Loader *****
    // ********************************************************

    /**
     * The config_loaded.
     */
    public bool config_loaded = false;

    /**
     * Was config loaded.
     * 
     * @return true, if successful
     */
    public bool wasConfigLoaded()
    {
        return this.config_loaded;
    }

    /**
     * Load property file.
     * 
     * @param filename the filename
     * 
     * @return the hashtable< string, string>
     */
    public Dictionary<String, String> loadPropertyFile(String filename)
    {

        Dictionary<String, String> config_db_values_ = new Dictionary<String, String>();

        PropertiesFile props = new PropertiesFile();

        this.config_loaded = true;

        try
        {
            //File f = new File(".");
            //Console.WriteLine("File: " + f.getCanonicalPath());

            if (!props.LoadFile(filename))
                throw new Exception("File not found !");

        }
        catch (Exception ex)
        {
            Console.WriteLine("Error loading config file (" + filename + "): "
                    + ex);
            this.config_loaded = false;
        }

        if (this.config_loaded)
        {
            config_db_values_ = props.CloneProperties();
        }
        else
            return null;

        return config_db_values_;

    }

    // ********************************************************
    // ****** Database Loading Status *****
    // ********************************************************

    /**
     * Sets the db loading status.
     * 
     * @param status the new db loading status
     */
    public void setDbLoadingStatus(int status)
    {
        this.db_loading_status = status;
    }

    /**
     * Gets the db loading status.
     * 
     * @return the db loading status
     */
    public int getDbLoadingStatus()
    {
        return this.db_loading_status;
    }

    /**
     * Checks if is db loaded for status.
     * 
     * @param status the status
     * 
     * @return true, if is db loaded for status
     */
    public bool isDbLoadedForStatus(int status)
    {
        if ((this.db_loading_status == status)
                || (this.db_loading_status > status))
            return true;
        else
            return false;
    }


    // ********************************************************
    // ****** Help stuff *****
    // ********************************************************

    // ********************************************************
    // ****** Fonts *****
    // ********************************************************

    /**
     * The Constant FONT_BIG_BOLD.
     */
    public const int FONT_BIG_BOLD = 0;
    
    /**
     * The Constant FONT_NORMAL.
     */
    public const int FONT_NORMAL = 1;
    
    /**
     * The Constant FONT_NORMAL_BOLD.
     */
    public const int FONT_NORMAL_BOLD = 2;
    
    /**
     * The Constant FONT_NORMAL_P2.
     */
    public const int FONT_NORMAL_P2 = 3;
    
    /**
     * The Constant FONT_NORMAL_BOLD_P2.
     */
    public const int FONT_NORMAL_BOLD_P2 = 4;

    /**
     * The Constant FONT_UPDATE_TREE_HEADER.
     */
    public const int FONT_UPDATE_TREE_HEADER = 5;
    
    /**
     * The Constant FONT_UPDATE_TREE_ITEM.
     */
    public const int FONT_UPDATE_TREE_ITEM = 6;

    /**
     * Load fonts.
     */
    public void loadFonts()
    {
        /*
        fonts = new Font[7];
        fonts[0] = new Font("SansSerif", Font.BOLD, 22);
        fonts[1] = new Font("SansSerif", Font.PLAIN, 12);
        fonts[2] = new Font("SansSerif", Font.BOLD, 12);
        fonts[3] = new Font("SansSerif", Font.PLAIN, 14);
        fonts[4] = new Font("SansSerif", Font.BOLD, 14);

        fonts[5] = new Font("SansSerif", Font.BOLD, 12);
        fonts[6] = new Font("SansSerif", Font.PLAIN, 12);
        */
    }

    /**
     * Gets the font.
     * 
     * @param font_id the font_id
     * 
     * @return the font
     */
    /*public Font getFont(int font_id)
    {
        return fonts[font_id];
    }*/

    // ********************************************************
    // ****** Parent handling (for UIs) *****
    // ********************************************************

    /*
     * private void loadIcons() { config_icons = new ImageIcon[7];
     * config_icons[0] = new ImageIcon(getImage("/images/cfg_db.gif", m_main));
     * config_icons[1] = new ImageIcon(getImage("/images/cfg_look.gif",
     * m_main)); config_icons[2] = new
     * ImageIcon(getImage("/images/cfg_myparish.gif", m_main)); config_icons[3]
     * = new ImageIcon(getImage("/images/cfg_masses.gif", m_main));
     * config_icons[4] = new ImageIcon(getImage("/images/cfg_users.gif",
     * m_main)); config_icons[5] = new
     * ImageIcon(getImage("/images/cfg_lang.gif", m_main)); config_icons[6] =
     * null;
     * 
     * }
     */

    // public ImageIcon getImageIcon(String image)

    /**
     * Gets the image.
     * 
     * @param filename the filename
     * @param cmp the cmp
     * 
     * @return the image
     */
/*    public Image getImage(String filename, Component cmp)
    {
        Image img;

        InputStream is = this.getClass().getResourceAsStream(filename);

        if (is == null)
            Console.WriteLine("Error reading image: " + filename);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            int c;
            while ((c = is.read()) >= 0)
                baos.write(c);

            // JDialog.getT
            // JFrame.getToolkit();
            img = cmp.getToolkit().createImage(baos.toByteArray());
        }
        catch (Exception ex)
        {
            Console.WriteLine("Image " + filename + " could not be created.");
            //ex.printStackTrace();
            return null;
        }
        return img;
    }
    */

    /**
     * Sets the parent.
     * 
     * @param component the new parent
     */
/*    public void setParent(Container component)
    {
        this.parent = component;
    }

    /**
     * Gets the parent.
     * 
     * @return the parent
     */
/*    public Component getParent()
    {
        return this.parent;
    }

    /**
     * Gets the image icon_22x22.
     * 
     * @param name the name
     * @param comp the comp
     * 
     * @return the image icon_22x22
     */
/*    public ImageIcon getImageIcon_22x22(String name, Container comp)
    {
        return getImageIcon(name, 22, 22, comp);
    }

    /**
     * Gets the image icon.
     * 
     * @param name the name
     * @param width the width
     * @param height the height
     * @param comp the comp
     * 
     * @return the image icon
     */
/*    public ImageIcon getImageIcon(String name, int width, int height,
            Container comp)
    {
        return getImageIcon(this.getImagesRoot(), name, width, height, comp);
    }

    /**
     * Gets the image icon.
     * 
     * @param root the root
     * @param name the name
     * @param width the width
     * @param height the height
     * @param comp the comp
     * 
     * @return the image icon
     */
/*    public ImageIcon getImageIcon(String root, String name, int width,
            int height, Container comp)
    {
        return new ImageIcon(getImage(root + name, comp).getScaledInstance(
                width, height, Image.SCALE_SMOOTH));
    }

    /**
     * Gets the image icon.
     * 
     * @param name the name
     * @param comp the comp
     * 
     * @return the image icon
     */
/*    public ImageIcon getImageIcon(String name, Container comp)
    {
        return getImageIcon(this.getImagesRoot(), name);
    }

    /**
     * Gets the image icon.
     * 
     * @param root the root
     * @param name the name
     * @param comp the comp
     * 
     * @return the image icon
     */
/*    public ImageIcon getImageIcon(String root, String name, Component comp)
    {
        return new ImageIcon(getImage(root + name, comp));
    }

    /**
     * Gets the image icon.
     * 
     * @param name the name
     * 
     * @return the image icon
     */
    /*    public ImageIcon getImageIcon(String name)
        {
            return getImageIcon(this.getImagesRoot(), name);
        }

        /**
         * Gets the image icon.
         * 
         * @param root the root
         * @param name the name
         * 
         * @return the image icon
         */
    /*    public ImageIcon getImageIcon(String root, String name)
        {
            //File f = new File(".");
        
            //Console.WriteLine("Start path: " + f.getAbsolutePath());
            //Console.WriteLine("Image: " + root + name);
        
            //Console.WriteLine("parent: " + this.getCurrentComponentParent());
        
            return new ImageIcon(getImage(root + name, this.getCurrentComponentParent()));
        }
    */
    // ********************************************************
    // ****** Sorting algorithms *****
    // ********************************************************

    /**
     * Compare unicode strings.
     * 
     * @param s1 the s1
     * @param s2 the s2
     * 
     * @return the int
     */
/*    public int compareUnicodeStrings(String s1, String s2)
    {
        return this.m_collator.compare(s1, s2);
    }

    // ********************************************************
    // ****** GUI *****
    // ********************************************************

    
    /**
     * Center j dialog.
     * 
     * @param dialog the dialog
     */
/*    public void centerJDialog(Component dialog)
    {
        Component cmp = this.getCurrentComponentParent();
        
        //Console.WriteLine("comps: " + this.components);
        
        this.centerJDialog(dialog, cmp);
    }
    
    
    /**
     * Center j dialog.
     * 
     * @param dialog the dialog
     * @param _parent the _parent
     */
/*    public void centerJDialog(Component dialog, Component  _parent)
    {

        Rectangle rec = _parent.getBounds();

        int x = rec.width / 2;
        x += (rec.x);

        int y = rec.height / 2;
        y += rec.y;

        x -= (dialog.getBounds().width / 2);
        y -= (dialog.getBounds().height / 2);

        if (x<0)
            x=0;
        
        if (y<0)
            y=0;
        
        // dialog.getBounds().x = x;
        // dialog.getBounds().y = y;

        dialog.setBounds(x, y, dialog.getBounds().width,
                dialog.getBounds().height);

        /*
         * if (parent is JDialog) { //centerJDialog(dialog,
         * (JDialog)parent); } else Console.WriteLine("CenterJDialog failed");
         */
    //}

    /*
     * public void centerJDialog(JDialog dialog, JComponent parent) {
     * 
     * //Console.WriteLine("centerJDialog: " );
     * 
     * Rectangle rec = parent.getBounds();
     * 
     * int x = rec.width/2; x += (rec.x);
     * 
     * int y = rec.height/2; y += rec.y;
     * 
     * x -= (dialog.getBounds().width/2); y -= (dialog.getBounds().height/2);
     * 
     * //dialog.getBounds().x = x; //dialog.getBounds().y = y;
     * 
     * dialog.setBounds(x, y, dialog.getBounds().width,
     * dialog.getBounds().height);
     * 
     * }
     */

    // ********************************************************
    // ****** Look and Feel *****
    // ********************************************************
    /*
     * public void loadAvailableLFs() {
     * 
     * availableLF_full = new Dictionary<String,String>();
     * UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
     * 
     * availableLF = new Object[info.length+1];
     * 
     * //ring selectedLF = null; //String subSelectedLF = null;
     * 
     * int i; for (i=0; i<info.length; i++) { String name = info[i].getName();
     * String className = info[i].getClassName();
     * 
     * availableLF_full.put(name, className); availableLF[i] = name;
     * 
     * //Console.WriteLine(humanReadableName); }
     * 
     * availableLF_full.put("SkinLF",
     * "com.l2fprod.gui.plaf.skin.SkinLookAndFeel"); availableLF[i] = "SkinLF";
     * 
     * }
     */

    /*
     * public Object[] getAvailableLFs() { return
     * this.m_config_file.getAvailableLFs(); //return availableLF; }
     * 
     * 
     * public static String[] getLFData() {
     * 
     * String out[] = new String[2];
     * 
     * try { Properties props = new Properties();
     * 
     * FileInputStream in = new
     * FileInputStream("../data/PIS_Config.properties"); props.load(in);
     * 
     * out[0] = (String)props.get("LF_CLASS"); out[1] =
     * (String)props.get("SKINLF_SELECTED");
     * 
     * return out;
     * 
     * } catch(Exception ex) {
     * Console.WriteLine("DataAccess::getLFData::Exception> " + ex);
     * ex.printStackTrace(); return null; } }
     */

    // ********************************************************
    // ****** Colors *****
    // ********************************************************
    /**
     * Load colors.
     */
/*    public void loadColors()
    {
        ColorUIResource cui = (ColorUIResource) UIManager.getLookAndFeel()
                .getDefaults().get("textText");
        this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui
                .getBlue(), cui.getAlpha());

        ColorUIResource cui2 = (ColorUIResource) UIManager.getLookAndFeel()
                .getDefaults().get("Label.background");
        this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2
                .getBlue(), cui2.getAlpha());

        this.border_line = new LineBorder(this.color_foreground);
    }
    */
    // ********************************************************
    // ****** Languages *****
    // ********************************************************

    /**
     * This method is intended to load additional Language info. Either special langauge configuration
     * or special data required for real Locale handling.
     */
    public abstract void loadLanguageInfo();
    
    
    /*
     * public Object[] getAvailableLanguages() { return new Object[] = { "en"
     * };; }
     * 
     * 
     * public int getSelectedLangIndex() { return 1; //return
     * m_lang_info.findInLocale(this.m_config_file.selected_lang); }
     * 
     * 
     * public static String getSelectedLocale() { String locale = "en";
     * 
     * 
     * 
     * try { Properties props = new Properties();
     * 
     * FileInputStream in = new FileInputStream(pathPrefix +
     * "../data/PIS_Config.properties"); props.load(in);
     * 
     * int sel_lang = 1;
     * 
     * if (props.containsKey("SELECTED_LANG")) { sel_lang =
     * Int32.parseInt((String)props.get("SELECTED_LANG"));
     * Console.WriteLine("Sel lang: " + sel_lang); }
     * 
     * 
     * //props = new Properties(); props.clear();
     * 
     * in = null; in = new FileInputStream(pathPrefix +
     * "/data/lang/PIS_Languages.properties"); props.load(in);
     * 
     * if (props.containsKey("LANG_" + sel_lang + "_LOCALE")) { locale =
     * (String)props.get("LANG_" + sel_lang + "_LOCALE"); }
     * 
     * // Console.WriteLine("Locale: " + locale);
     * 
     * } catch(Exception ex) {
     * Console.WriteLine("DataAccess::getSelectedLocale::Exception> " + ex); }
     * 
     * return locale;
     * 
     * }
     */

    // ********************************************************
    // ****** JFormatted Text Field *****
    // ********************************************************


    /*
     * 
     * public String[] getAvailableDbs() { //this.m_config_file. //return
     * allDbs; return this.m_config_file.getAllDatabasesNamesAsArray(); }
     * 
     * 
     * public int getSelectedDbIndex() {
     * 
     * return this.m_config_file.getSelectedDatabaseIndex(); / for (int i=0;
     * i<allDbs.length; i++) { if (allDbs[i].startsWith(this.selected_db +
     * " - ")) return i; } return 0;
     */
    // }

    /**
     * Gets the months array.
     * 
     * @return the months array
     */
    public String[] getMonthsArray()
    {
        return this.months;

        /*
         * String arr[] = new String[12];
         * 
         * arr[0] = m_i18n.getMessage("JANUARY"); arr[1] =
         * m_i18n.getMessage("FEBRUARY"); arr[2] = m_i18n.getMessage("MARCH");
         * arr[3] = m_i18n.getMessage("APRIL"); arr[4] =
         * m_i18n.getMessage("MAY"); arr[5] = m_i18n.getMessage("JUNE"); arr[6]
         * = m_i18n.getMessage("JULY"); arr[7] = m_i18n.getMessage("AUGUST");
         * arr[8] = m_i18n.getMessage("SEPTEMBER"); arr[9] =
         * m_i18n.getMessage("OCTOBER"); arr[10] =
         * m_i18n.getMessage("NOVEMBER"); arr[11] =
         * m_i18n.getMessage("DECEMBER");
         * 
         * return arr;
         */

    }

    /*
     * public void loadComboOptions() {
     * 
     * yes_no_combo = new Object[2];
     * 
     * yes_no_combo[0] = m_i18n.getMessage("OPTION_YES"); yes_no_combo[1] =
     * m_i18n.getMessage("OPTION_NO");
     * 
     * Dictionary ht = m_db.getProductType(-1);
     * 
     * typesAll = new Object[ht.size()];
     * 
     * int i = 0;
     * 
     * 
     * for(Enumeration en=ht.keys(); en.hasMoreElements(); ) {
     * 
     * String key = (String)en.nextElement();
     * 
     * String key2 = "";
     * 
     * if (key.length()==1) { key2 = "0"+key; } else key2 = key;
     * 
     * 
     * 
     * typesAll[i] = key2 + " - " +
     * ((ProductType)ht.get(key)).path.substring(1); i++;
     * 
     * }
     * 
     * Arrays.sort(typesAll);
     * 
     * 
     * 
     * 
     * }
     */

    /*
     * 
     * 
     * 
     * public void makeNewConfig() {
     * 
     * configStatic.config = new dataConfig();
     * 
     * 
     * configStatic.config.mainDir=""; configStatic.config.shellSelected=1;
     * configStatic.config.shellCommand="cmd /c";
     * configStatic.config.browserEnabled=true;
     * configStatic.config.browserExternal=false;
     * configStatic.config.mailEnabled=false;
     * configStatic.config.ftpEnabled=false;
     * configStatic.config.ftpInternalEnabled=false; saveConfig();
     * 
     * }
     * 
     * 
     * 
     * public void loadConfig() {
     * 
     * try { ObjectInputStream in = new ObjectInputStream( new
     * FileInputStream("../data/config.dat"));
     * configStatic.config=(dataConfig)in.readObject(); in.close(); } catch
     * (IOException ex) { makeNewConfig(); } catch (ClassNotFoundException ex) {
     * } }
     * 
     * public void saveConfig() {
     * 
     * try { ObjectOutputStream out = new ObjectOutputStream( new
     * FileOutputStream("../data/config.dat"));
     * out.writeObject(configStatic.config); out.close(); } catch (IOException
     * ex) { Console.WriteLine("Error saving configuration."); }
     * 
     * }
     * 
     * 
     * / public String getShell() { //return configStatic.browserCommand; return
     * ""; }
     * 
     * public String getBrowserCmd() { // return
     * configStatic.browserPath[configStatic.useBrowserNr]; return "";
     * 
     * }
     * 
     * public String getMailerCmd() { // return configStatic.browserPath[1];
     * return ""; }
     */

    /**
     * Gets the date string.
     * 
     * @param date the date
     * 
     * @return the date string
     */
    public String getDateString(int date)
    {

        // 20051012

        int year = date / 10000;
        int month = date - (year * 10000);

        month = month / 100;

        int day = date - (year * 10000) - (month * 100);

        if (year == 0)
        {
            return getLeadingZero(day, 2) + "/" + getLeadingZero(month, 2);
        }
        else
            return getLeadingZero(day, 2) + "/" + getLeadingZero(month, 2)
                    + "/" + year;

    }

    /**
     * Gets the time string.
     * 
     * @param time the time
     * 
     * @return the time string
     */
    public String getTimeString(int time)
    {

        int hours = time / 100;

        int min = time - hours * 100;

        return getLeadingZero(hours, 2) + ":" + getLeadingZero(min, 2);

    }

    /**
     * Gets the date time string.
     * 
     * @param date the date
     * 
     * @return the date time string
     */
/*    public String getDateTimeString(long date)
    {
        return getDateTimeString(date, 1);
    }

    /**
     * Gets the date time as date string.
     * 
     * @param date the date
     * 
     * @return the date time as date string
     */
  /*  public String getDateTimeAsDateString(long date)
    {
        return getDateTimeString(date, 2);
    }


    
    /**
     * Gets the start year.
     * 
     * @return the start year
     */
    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }

    /*
     * public static const int USER_NORMAL = 1; public static const int
     * USER_WORKER = 2; public static const int USER_ADMINISTRATOR = 3; public
     * static const int USER_SUPERADMINISTRATOR = 4;
     * 
     * public int user_type = 3;
     */

    /*
     * public int authorizeUser(String username, String password) {
     * 
     * Console.WriteLine(username + " " + password);
     * 
     * if ((username.equalsIgnoreCase("andy")) && (password.equals("Satja"))) {
     * return DataAccess.USER_SUPERADMINISTRATOR; } else { loginType =
     * m_db.authenticateUser(username,password); return loginType; }
     * 
     * }
     */

    /**
     * Not implemented.
     * 
     * @param source the source
     */
    public static void notImplemented(String source)
    {
        Console.WriteLine("Not Implemented: " + source);
        // JOptionPane.showMessageDialog(parent, "Not Implemented: \n" +
        // source);
    }

    /**
     * Not implemented.
     * 
     * @param parent the parent
     * @param source the source
     */
    public static void notImplemented(IContainer parent, String source)
    {
        Console.WriteLine("Not Implemented: " + source);
//        JOptionPane.showMessageDialog(parent, "Not Implemented: \n" + source);
    }


    // **************************************************************************
    // ****                    String handling Methods                       ****
    // **************************************************************************
    
    
    
    /**
     * Gets the leading zero.
     * 
     * @param number the number
     * @param places the places
     * 
     * @return the leading zero
     */
    public String getLeadingZero(int number, int places)
    {
        String nn = "" + number;

        while (nn.Length < places)
        {
            nn = "0" + nn;
        }

        return nn;
    }

    /**
     * Gets the leading zero.
     * 
     * @param number the number
     * @param places the places
     * 
     * @return the leading zero
     */
    public String getLeadingZero(String number, int places)
    {
        number = number.Trim();

        while (number.Length < places)
        {
            number = "0" + number;
        }

        return number;
    }
    
    
    
    /**
     * For replacing strings.<br>
     * 
     * @param input   Input String
     * @param replace What to seatch for.
     * @param replacement  What to replace with.
     * 
     * @return Parsed string.
     */
    public String replaceExpression(String input, String replace,
            String replacement)
    {

        int idx;
        if ((idx = input.IndexOf(replace)) == -1)
        {
            return input;
        }

        bool finished = false;
        
        while(!finished)
        {

            StringBuilder returning = new StringBuilder();
    
            while (idx != -1)
            {
                returning.Append(input.Substring(0, idx));
                returning.Append(replacement);
                input = input.Substring(idx + replace.Length);
                idx = input.IndexOf(replace);
            }
            returning.Append(input);
            
            input = returning.ToString();
            
            if ((idx = input.IndexOf(replace))==-1)
            {
                finished = true;
            }

        }

        return input;

    }
    


    /**
     * Parses the expression.
     * 
     * @param in the in
     * @param expression the expression
     * @param replace the replace
     * 
     * @return the string
     */
    public String parseExpression(String inp, String expression, String replace)
    {

        return inp.Replace(expression, replace);

        /*
        StringBuilder buffer;

        int idx=inp.indexOf(expression);
        
        if (replace==null)
            replace ="";
        
        if (idx==-1)
            return inp;

        buffer = new StringBuffer();
        
        while (idx!=-1)
        {
            buffer.Append(inp.substring(0,idx));
            buffer.Append(replace);

            inp = inp.substring(idx+expression.length());
            
            idx=inp.IndexOf(expression);
        }

        buffer.Append(inp);

        return buffer.toString();
        */
    }



    /**
     * Parses the expression full.
     * 
     * @param in the in
     * @param expression the expression
     * @param replace the replace
     * 
     * @return the string
     */
    public string ParseExpressionFull(String inp, String expression, String replace)
    {

        return inp.Replace(expression, replace);

    }
    
    
    /**
     * Checks if is empty or unset.
     * 
     * @param val the val
     * 
     * @return true, if is empty or unset
     */
    public bool isEmptyOrUnset(String val)
    {
        if ((val == null) || (val.Trim().Length==0))
        {
            return true;
        }
        else
            return false;
    }

    
    /**
     * Checks if is found.
     * 
     * @param text the text
     * @param search_str the search_str
     * 
     * @return true, if is found
     */
    public static bool isFound(String text, String search_str)
    {
        if ((search_str.Trim().Length == 0) || (text.Trim().Length == 0))
            return true;

        return text.Trim().IndexOf(search_str.Trim()) != -1;
    }


    /**
     * Split string.
     * 
     * @param input the input
     * @param delimiter the delimiter
     * 
     * @return the string[]
     */
    public string[] SplitString(String input, String delimiter)
    {
        string[] sep = new string[1];
        sep[0] = delimiter;

        if (!input.Contains(delimiter))
        {
            String[] res = new String[1];
            res[0] = input;
            return res; 
        }
        else
        {
            return input.Split(sep, StringSplitOptions.None);
        }

    }

    // ********************************************************
    // ****** Date/Time *****
    // ********************************************************
    
    
 
    
    /**
     * Gets the as localized date string.
     * 
     * @param gc_value the gc_value
     * @param years_digits the years_digits
     * 
     * @return the as localized date string
     */
 /*   public String getAsLocalizedDateString(GregorianCalendar gc_value, int years_digits)
    {
        if (years_digits==2)
        {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, getI18nControlInstance().getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
        else
        {
            // TODO: fix this
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, getI18nControlInstance().getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
    }
   */ 
    
    /**
     * The Constant GC_COMPARE_DAY.
     */
    public const int GC_COMPARE_DAY = 1;
    
    /**
     * The Constant GC_COMPARE_HOUR.
     */
    public const int GC_COMPARE_HOUR = 2;
    
    /**
     * The Constant GC_COMPARE_MINUTE.
     */
    public const int GC_COMPARE_MINUTE = 3;

    /**
     * Compare gregorian calendars.
     * 
     * @param type the type
     * @param gc1 the gc1
     * @param gc2 the gc2
     * 
     * @return true, if successful
     */
    public bool CompareDateTime(int type, DateTime dt1, DateTime dt2)
    {
        bool found = false;

        if ((dt1.Day == dt2.Day) && (dt1.Month == dt2.Month) && (dt1.Year == dt2.Year))
            found = true;
            
        if ((type == ATDataAccessAbstract.GC_COMPARE_HOUR) ||
            (type == ATDataAccessAbstract.GC_COMPARE_MINUTE))
        {
            if (dt1.Hour!=dt2.Hour)
                found = false;
        }

        if (type == ATDataAccessAbstract.GC_COMPARE_MINUTE)
        {
            if (dt1.Minute != dt2.Minute)
                found = false;
        }

        return found;
    }
    
    
    
    // ********************************************************
    // ****** Get Values From Object *****
    // ********************************************************

    /**
     * Gets the float value.
     * 
     * @param aValue the a value
     * 
     * @return the float value
     */
    public float getFloatValue(object aValue)
    {

        return Convert.ToSingle(aValue);

    }

    /**
     * Gets the int value.
     * 
     * @param aValue the a value
     * 
     * @return the int value
     */
    public int GetIntValue(Object aValue)
    {
        return Convert.ToInt32(aValue);
    }

    /**
     * Gets the long value.
     * 
     * @param aValue the a value
     * 
     * @return the long value
     */
    public long getInt64Value(Object aValue)
    {
        return Convert.ToInt64(aValue);
    }

    // ********************************************************
    // ****** Get Values From String *****
    // ********************************************************

    /**
     * Gets the float value from string.
     * 
     * @param aValue the a value
     * 
     * @return the float value from string
     */
    public float getFloatValueFromString(String aValue)
    {
        return this.getFloatValueFromString(aValue, 0.0f);
    }

    /**
     * Gets the float value from string.
     * 
     * @param aValue the a value
     * @param def_value the def_value
     * 
     * @return the float value from string
     */
    public float getFloatValueFromString(String aValue, float def_value)
    {
        float outx = def_value;
        
        try
        {
            outx = Convert.ToSingle(aValue);
        }
        catch (Exception ex)
        {
            log.Error("Error on parsing string to get float [" + aValue + "]:"
                    + ex, ex);

        }

        return outx;
    }

    /**
     * Gets the int value from string.
     * 
     * @param aValue the a value
     * 
     * @return the int value from string
     */
    public int getIntValueFromString(String aValue)
    {
        return this.getIntValueFromString(aValue, 0);
    }

    /**
     * Gets the int value from string.
     * 
     * @param aValue the a value
     * @param def_value the def_value
     * 
     * @return the int value from string
     */
    public int getIntValueFromString(String aValue, int def_value)
    {
        int outx = def_value;

        try
        {
            outx = Convert.ToInt32(aValue);
        }
        catch (Exception ex)
        {
            log.Error("Error on parsing string to get int [" + aValue + "]:" + ex, ex);
        }

        return outx;
    }

    /**
     * Gets the long value from string.
     * 
     * @param aValue the a value
     * 
     * @return the long value from string
     */
    public long getInt64ValueFromString(String aValue)
    {
        return this.getInt64ValueFromString(aValue, 0L);
    }

    /**
     * Gets the long value from string.
     * 
     * @param aValue the a value
     * @param def_value the def_value
     * 
     * @return the long value from string
     */
    public long getInt64ValueFromString(String aValue, long def_value)
    {
        long outx = def_value;

        try
        {
            outx = Convert.ToInt64(aValue);
        }
        catch (Exception ex)
        {
            log.Error("Error on parsing string to get long [" + aValue + "]:" + ex, ex);
        }

        return outx;
    }

    /**
     * Checks if is value set.
     * 
     * @param val the val
     * 
     * @return true, if is value set
     */
    public bool isValueSet(String val)
    {
        if ((val == null) || (val.Trim().Length == 0) || (val=="null"))
            return false;
        else
            return true;
    }
    
    
    // ********************************************************
    // ****** Backup / Restore *****
    // ********************************************************

    #region Backup Restore [Not Implementes]
    /**
     * The backup_restore_collection.
     */
/*    protected BackupRestoreCollection backup_restore_collection = null;

    /**
     * Checks if is backup restore available.
     * 
     * @return true, if is backup restore available
     */
/*    public boolean isBackupRestoreAvailable()
    {
        return (this.backup_restore_collection != null);
    }

    /**
     * Gets the backup restore collection.
     * 
     * @return the backup restore collection
     */
/*    public BackupRestoreCollection getBackupRestoreCollection()
    {
        // TODO: clone
        return this.backup_restore_collection;
    }
    
    
    /**
     * Load Backup Restore Collection
     */
    //  public abstract void loadBackupRestoreCollection();

    #endregion

    /**
     * Make i18n keyword.
     * 
     * @param input the input
     * 
     * @return the string
     */
    public String makeI18nKeyword(String input)
    {
        String process = input.Replace(" ", "_");
        process = process.ToUpper();

        return process;
    }

    #region Update Configuration [Not Implementes]
    /**
     * Gets the update configuration.
     * 
     * @return the update configuration
     */
    /*public UpdateConfiguration getUpdateConfiguration()
    {
        if (this.update_configuration==null)
        {
            update_configuration = new UpdateConfiguration();
        }
        
        return this.update_configuration;
    }
    

    //public abstract String getUpdateConfigurationFile();
    */
    #endregion
    
    /**
     * Get Configuration - reads properties file and read all entries
     * 
     * @param filename
     * @return
     */
    public Dictionary<String,String> getConfiguration(String filename)
    {

        Dictionary<String,String> config_db_values_ = new Dictionary<String,String>();

        PropertiesFile props = new PropertiesFile();

        config_loaded = true;

        try
        {
            config_loaded = props.LoadFile(filename);
        }
        catch //(Exception ex)
        {
            config_loaded = false;
        }


        if (config_loaded)
        {
            config_db_values_ = props.PropertiesCollection;
        }

        return config_db_values_;
        
    }

    
    /**
     * Is Option Enabled
     * 
     * @param value
     * @return
     */
    public bool isOptionEnabled(String value)
    {
        
        if (value==null)
            return false;
        
        String val = value.ToUpper();
        
        if (val == "ENABLED" || val == "YES" || val=="TRUE" || val=="1")
            return true;
        else
            return false;
    }    
    
    
    
    
    /**
     * Load special parameters.
     */
    public abstract void loadSpecialParameters();
    
    
    /**
     * Gets the special parameters.
     * 
     * @return the special parameters
     */
    public Dictionary<String, String> getSpecialParameters()
    {
        return special_parameters;
    }
    
    
    /**
     * Gets the language info.
     * 
     * @return the language info
     */
/*    public LanguageInfo getLanguageInfo()
    {
        return this.m_lang_info;
    }
 
    
    /**
     * Gets the selected lang index.
     * 
     * @return the selected lang index
     */
    public abstract int getSelectedLangIndex();

    
    /**
     * Sets the selected lang index.
     * 
     * @param index the new selected lang index
     */
    public abstract void setSelectedLangIndex(int index);
 
    
    /**
     * Gets the plugins.
     * 
     * @return the plugins
     */
  /*  public Dictionary<String,PlugInClient> getPlugins()
    {
        return this.plugins;
    } */
    
    
    
}

}