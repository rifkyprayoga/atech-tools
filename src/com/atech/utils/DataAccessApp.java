package com.atech.utils;

import java.awt.*;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import com.atech.app.AbstractApplicationContext;
import com.atech.db.cfg.DbConfig;
import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.gui_fw.MainAppFrame;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.info.LanguageInfo;
import com.atech.i18n.tool.simple.data.TranslationToolConfigurationDto;

// TODO: Auto-generated Javadoc
// LOAD
// SAVE


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

/**
 * The Class DataAccess.
 */
public class DataAccessApp extends ATDataAccessLMAbstract
{

    private static AbstractApplicationContext s_app_context;

    // config file
    /**
     * The config_db_values.
     */
    Hashtable<String, String> config_db_values = null;

    // login

    public DbConfig jdbc_config = null;

    /**
     * The m_settings.
     */
    public Hashtable<String, String> m_settings = null;

    /**
     * The path prefix.
     */
    public static String pathPrefix = "../";

    // public UserDat reg_info = null;

    /**
     * The color_foreground.
     */
    public Color color_background;

    /**
     * 
     */
    public Color color_foreground;

    /**
     * The m_i18n.
     */
    public I18nControlAbstract m_i18n = null; // I18nControl.getInstance();

    static protected DataAccessApp m_da = null; // This is handle to unique
    // singelton instance
    // YYY
    /**
      * The m_db.
      */
    // public MDODb m_db = null;

    /**
     * The m_main.
     */
    public MainAppFrame m_main = null;

    // database loading status

    private int db_loading_status = 0;

    /**
     * The yes_no_combo.
     */
    public Object[] yes_no_combo = null;
    // public Hashtable typesHT = new Hashtable();

    /**
     * The types all.
     */
    public Object[] typesAll = null;

    /**
     * The border_line.
     */
    public LineBorder border_line;

    /**
     * The fonts.
     */
    public Font fonts[] = null;

    /**
     * The contact_types.
     */
    public String contact_types[] = null;
    /*
     * {
     * m_i18n.getMessage("SELECT"),
     * m_i18n.getMessage("PHONE"),
     * m_i18n.getMessage("GSM"),
     * m_i18n.getMessage("FAX"),
     * m_i18n.getMessage("EMAIL"),
     * m_i18n.getMessage("WEB_PAGE"),
     * m_i18n.getMessage("ICQ_MSNG"),
     * m_i18n.getMessage("YAHOO_MSNG"),
     * m_i18n.getMessage("AIM_MSNG"),
     * m_i18n.getMessage("JABBER_MSNG"),
     * m_i18n.getMessage("MSN_MSNG"),
     * m_i18n.getMessage("SKYPE_MSNG"),
     * m_i18n.getMessage("OTHER")
     * };
     */
    /**
     * The contact_icons.
     */
    public ImageIcon contact_icons[] = { null,
                                        new ImageIcon("images/c_phone.gif"), // m_i18n.getMessage("PHONE"),
                                        new ImageIcon("images/c_GSM.gif"), // m_i18n.getMessage("GSM"),
                                        new ImageIcon("images/c_fax.gif"), // m_i18n.getMessage("FAX"),
                                        new ImageIcon("images/c_email.gif"), // m_i18n.getMessage("EMAIL"),
                                        new ImageIcon("images/c_hp.gif"), // m_i18n.getMessage("WEB_PAGE"),
                                        new ImageIcon("images/c_icq.gif"), // m_i18n.getMessage("ICQ_MSNG"),
                                        new ImageIcon("images/c_yahoo.gif"), // m_i18n.getMessage("YAHOO_MSNG"),
                                        new ImageIcon("images/c_aim.gif"), // m_i18n.getMessage("MSN_MSNG")
                                        new ImageIcon("images/c_jabber.gif"), new ImageIcon("images/c_msn.gif"), null,
                                        null };

    /**
     * The mass_status.
     */
    public ImageIcon mass_status[] = { new ImageIcon("images/dot_green.gif"), new ImageIcon("images/dot_orange.gif"), // m_i18n.getMessage("PHONE"),
                                      new ImageIcon("images/dot_blue.gif"), // m_i18n.getMessage("PHONE"),
                                      new ImageIcon("images/dot_red.gif"), // m_i18n.getMessage("PHONE"),
    };

    /**
     * The days.
     */
    // public String days[] = null;
    /*
     * {
     * m_i18n.getMessage("MONDAY"),
     * m_i18n.getMessage("TUESDAY"),
     * m_i18n.getMessage("WEDNESDAY"),
     * m_i18n.getMessage("THURSDAY"),
     * m_i18n.getMessage("FRIDAY"),
     * m_i18n.getMessage("SATURDAY"),
     * m_i18n.getMessage("SUNDAY")
     * };
     */

    /**
     * The months.
     */
    /*
     * public String months[] = null;
     * {
     * m_i18n.getMessage("JANUARY"),
     * m_i18n.getMessage("FEBRUARY"),
     * m_i18n.getMessage("MARCH"),
     * m_i18n.getMessage("APRIL"),
     * m_i18n.getMessage("MAY"),
     * m_i18n.getMessage("JUNE"),
     * m_i18n.getMessage("JULY"),
     * m_i18n.getMessage("AUGUST"),
     * m_i18n.getMessage("SEPTEMBER"),
     * m_i18n.getMessage("OCTOBER"),
     * m_i18n.getMessage("NOVEMBER"),
     * m_i18n.getMessage("DECEMBER")
     * };
     */

    public long selected_customer_id = 0L;


    // ********************************************************
    // ****** Constructors and Access methods *****
    // ********************************************************

    // Constructor: DataAccess
    /**
     *
     *  This is DataAccess constructor; Since classes use Singleton Pattern,
     *  constructor is protected and can be accessed only with getInstance() 
     *  method.<br><br>
     *
     */
    private DataAccessApp(AbstractApplicationContext aac)
    {
        super(aac.getLanguageManager(), aac.getI18nControlRunner());

        this.hib_db = aac.getDb();

        // aac.loadBackupCollection();
        // this.backupRestoreCollection = aac.getBackupRestoreCollection();

        // this.m_app_context = aac;

        // public ATDataAccessLMAbstract(LanguageManager lm, I18nControlRunner
        // icr)

        // m_db = db;
        // reg_info = new UserDat();

        // loadConfig();

        // loadAvailableLFs();
        loadLanguageInfo();
        loadColors();

        m_settings = new Hashtable<String, String>();
        // loadDioceseRoot();

    }


    /*
     * private DataAccess(ClockDb db)
     * {
     * super(I18nControl.getInstance());
     * m_db_c = db;
     * //reg_info = new UserDat();
     * //loadConfig();
     * loadFonts();
     * //loadAvailableLFs();
     * loadLanguageInfo();
     * loadColors();
     * m_settings = new Hashtable<String,String>();
     * // loadDioceseRoot();
     * }
     */

    /**
     * Creates the instance.
     * 
     * @param aac the main
     * 
     * @return the data access
     */
    static public DataAccessApp createInstanceWOFrame(AbstractApplicationContext aac)
    {
        if (m_da == null)
        {
            // if (main.getSplash()!=null)
            // main.getSplash().setSplashProgress(18,
            // aac.getI18nControl().getMessage("CREATE_DATABASE_INSTANCE"));

            s_app_context = aac;
            // aac.setFrame(main);
            // aac.initDb();

            // MDODb db = new MDODb();

            m_da = new DataAccessApp(aac);
            // dataAccess.setParent(main);
            // dataAccess.setMainParent(main);

            m_da.helpEnabled = s_app_context.isHelpEnabled();

        }

        return m_da;
    }


    /**
     * Creates the instance.
     * 
     * @param main the main
     * 
     * @return the data access
     */
    static public DataAccessApp createInstance(MainAppFrame main, AbstractApplicationContext aac)
    {
        if (m_da == null)
        {
            if (main.getSplash() != null)
            {
                main.getSplash().setSplashProgress(18, aac.getI18nControl().getMessage("CREATE_DATABASE_INSTANCE"));
            }

            s_app_context = aac;
            // aac.setFrame(main);
            // aac.initDb();

            // MDODb db = new MDODb();

            m_da = new DataAccessApp(aac);
            m_da.setParent(main);
            m_da.setMainParent(main);

            m_da.helpEnabled = s_app_context.isHelpEnabled();

        }
        else
        {
            m_da.setParent(main);
            m_da.setMainParent(main);
        }

        return m_da;
    }


    /**
     * Gets the single instance of DataAccess.
     * 
     * @return single instance of DataAccess
     */
    static public DataAccessApp getInstance()
    {
        return m_da;
    }


    // Method: deleteInstance
    /**
     * This method sets handle to DataAccess to null and deletes the instance. <br><br>
     */
    public void deleteInstance()
    {

        m_i18n = null;

    }


    /**
     * Gets the db.
     * 
     * @return the db
     */
    public HibernateDb getDb()
    {
        return s_app_context.getDb();
    }


    /*
     * public void loadDioceseRoot()
     * {
     * m_dio_treeroot = new DioceseTreeRoot(1);
     * }
     */

    // ********************************************************
    // ****** Database Loading Status *****
    // ********************************************************

    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#setDbLoadingStatus(int)
     */
    @Override
    public void setDbLoadingStatus(int status)
    {
        this.db_loading_status = status;
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDbLoadingStatus()
     */
    @Override
    public int getDbLoadingStatus()
    {
        return this.db_loading_status;
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#isDbLoadedForStatus(int)
     */
    @Override
    public boolean isDbLoadedForStatus(int status)
    {
        if (this.db_loading_status == status || this.db_loading_status > status)
            return true;
        else
            return false;
    }


    // ********************************************************
    // ****** Fonts *****
    // ********************************************************

    // ********************************************************
    // ****** Parent handling (for UIs) *****
    // ********************************************************

    /**
     * Sets the parent.
     * 
     * @param main the new parent
     */
    public void setParent(MainAppFrame main)
    {
        m_main = main;
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getParent()
     */
    @Override
    public MainAppFrame getParent()
    {
        return m_main;
    }


    // ********************************************************
    // ****** Look and Feel *****
    // ********************************************************

    /**
     * Gets the lF data.
     * 
     * @return the lF data
     */
    /*
     * public static String[] getLFData()
     * {
     * String out[] = new String[2];
     * try
     * {
     * Properties props = new Properties();
     * FileInputStream in = new FileInputStream(pathPrefix +
     * "/data/MSP_Config.properties");
     * props.load(in);
     * out[0] = (String)props.get("LF_CLASS");
     * out[1] = (String)props.get("SKINLF_SELECTED");
     * return out;
     * }
     * catch(Exception ex)
     * {
     * System.out.println("DataAccess::getLFData::Exception> " + ex);
     * return null;
     * }
     * }
     */

    // ********************************************************
    // ****** Colors *****
    // ********************************************************

    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadColors()
     */
    @Override
    public void loadColors()
    {
        if (!System.getProperty("os.name").startsWith("Mac")) // Yields "Mac OS X"
        {
            ColorUIResource cui = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("textText");
            this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui.getBlue(), cui.getAlpha());

            ColorUIResource cui2 = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("Label.background");
            this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2.getBlue(), cui2.getAlpha());

            this.border_line = new LineBorder(this.color_foreground);
        }
    }


    // ********************************************************
    // ****** Languages *****
    // ********************************************************

    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadLanguageInfo()
     */
    @Override
    public void loadLanguageInfo()
    {
        // m_lang_info = new LanguageInfo();
        // reg_info.setCoding("2|4|2|-2|1|12|6|-2|2|1");
        // m_lang_info.loadData();

        /*
         * try
         * {
         * Properties props = new Properties();
         * FileInputStream in = new FileInputStream(pathPrefix +
         * "/data/lang/MSP_Languages.properties");
         * props.load(in);
         * reg_info.setCoding("2|4|2|-2|1|12|6|-2|2|1");
         * m_lang_info = new LanguageInfo();
         * m_lang_info.loadData(props);
         * }
         * catch(Exception ex)
         * {
         * System.out.println("DataAccess::loadLanguageInfo::Exception> " + ex);
         * }
         */

    }


    @Override
    public void initObserverManager()
    {

    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getLanguageInfo()
     */
    @Override
    public LanguageInfo getLanguageInfo()
    {
        return m_lang_info;
    }


    /**
     * Gets the selected locale.
     * 
     * @return the selected locale
     */
    public static String getSelectedLocale()
    {
        String locale = "SI";

        try
        {
            Properties props = new Properties();

            // this.

            FileInputStream in = new FileInputStream(s_app_context.getDbToolApplication()
                    .getApplicationDatabaseConfig()); // pathPrefix
                                                      // +
                                                      // "/data/MSP_Config.properties");
            props.load(in);

            // int sel_lang = 1;
            String sel_lang = "si";

            if (props.containsKey("SELECTED_LANG"))
            {
                sel_lang = (String) props.get("SELECTED_LANG"); // Integer.parseInt((String)props.get("SELECTED_LANG"));
                System.out.println("Sel lang: " + sel_lang);
            }

            // props = new Properties();
            props.clear();

            try
            {

                in = null;
                in = new FileInputStream(pathPrefix + "/data/lang/MDO_Languages.properties");
                props.load(in);

                int av_langs = Integer.parseInt((String) props.get("AVAILABLE_LANGUAGES"));

                for (int i = 1; i <= av_langs; i++)
                {

                    if (props.containsKey("LANG_" + i))
                    {

                        String l = (String) props.get("LANG_" + i);

                        if (l.equals(sel_lang))
                        {
                            if (props.containsKey("LANG_" + i + "_LOCALE"))
                            {
                                locale = (String) props.get("LANG_" + i + "_LOCALE");
                            }
                        }
                    }
                }

            }
            catch (Exception ex)
            {
                System.out.println("Error reading Language Manager file. " + ex);
            }

        }
        catch (Exception ex)
        {
            System.out.println("DataAccessApp::getSelectedLocale::Exception> " + ex);
        }

        return locale;

    }


    // ********************************************************
    // ****** Login/Logout *****
    // ********************************************************

    /**
     * Process login.
     */
    @Override
    public void processLogin()
    {
        if (this.loggedUser == null)
        {
            s_app_context.setLoadingStatus(0);
        }
        else
        {
            s_app_context.setLoadingStatus(this.loggedUser.getUser_access());
        }
    }


    // ********************************************************
    // ****** Config File Handling *****
    // ********************************************************

    // -----------------------

    /**
     * Gets the selected contact type.
     * 
     * @param value the value
     * 
     * @return the selected contact type
     */
    public int getSelectedContactType(String value)
    {

        int i = 0;

        boolean found = false;

        for (i = 0; i < contact_types.length; i++)
        {
            if (value.equals(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        // System.out.println(value + " found: " + found + " " + i);

        if (found)
            return i;
        else
            return 0;

    }


    /**
     * Gets the selected contact type part.
     * 
     * @param value the value
     * 
     * @return the selected contact type part
     */
    @Override
    public int getSelectedContactTypePart(String value)
    {

        int i = 0;

        boolean found = false;

        for (i = 0; i < contact_types.length; i++)
        {
            if (value.startsWith(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        // System.out.println(value + " found: " + found + " " + i);

        if (found)
            return i;
        else
            return 0;

    }


    /**
     * Gets the selected config type part.
     * 
     * @param value the value
     * 
     * @return the selected config type part
     */
    @Override
    public int getSelectedConfigTypePart(String value)
    {

        int i = 0;

        boolean found = false;

        for (i = 0; i < contact_types.length; i++)
        {
            if (value.startsWith(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        // System.out.println(value + " found: " + found + " " + i);

        if (found)
            return i;
        else
            return 0;

    }

    // type: 1=ADD_EVENT,
    // 2=ADD_EVENT_PERSON_CHURCH (ADD PersonEvent)
    // 3=ADD_EVENT
    // 4

    /*
     * public void makeNewConfig()
     * {
     * configStatic.config = new dataConfig();
     * configStatic.config.mainDir="";
     * configStatic.config.shellSelected=1;
     * configStatic.config.shellCommand="cmd /c";
     * configStatic.config.browserEnabled=true;
     * configStatic.config.browserExternal=false;
     * configStatic.config.mailEnabled=false;
     * configStatic.config.ftpEnabled=false;
     * configStatic.config.ftpInternalEnabled=false;
     * saveConfig();
     * }
     * public void loadConfig()
     * {
     * try
     * {
     * ObjectInputStream in = new ObjectInputStream(
     * new FileInputStream("../data/config.dat"));
     * configStatic.config=(dataConfig)in.readObject();
     * in.close();
     * }
     * catch (IOException ex)
     * {
     * makeNewConfig();
     * }
     * catch (ClassNotFoundException ex)
     * {
     * }
     * }
     * public void saveConfig()
     * {
     * try
     * {
     * ObjectOutputStream out = new ObjectOutputStream(
     * new FileOutputStream("../data/config.dat"));
     * out.writeObject(configStatic.config);
     * out.close();
     * }
     * catch (IOException ex)
     * {
     * System.out.println("Error saving configuration.");
     * }
     * }
     * /*
     * public String getShell()
     * {
     * //return configStatic.browserCommand;
     * return "";
     * }
     * public String getBrowserCmd()
     * {
     * // return configStatic.browserPath[configStatic.useBrowserNr];
     * return "";
     * }
     * public String getMailerCmd()
     * {
     * // return configStatic.browserPath[1];
     * return "";
     * }
     */

    /**
     * The Constant DATE_TIME_ATECH_DATETIME.
     */
    public static final int DATE_TIME_ATECH_DATETIME = 1;

    /**
     * The Constant DATE_TIME_ATECH_DATE.
     */
    public static final int DATE_TIME_ATECH_DATE = 2;

    /**
     * The Constant DATE_TIME_ATECH_TIME.
     */
    public static final int DATE_TIME_ATECH_TIME = 3;

    /**
     * The Constant DT_DATETIME.
     */
    public final static int DT_DATETIME = 1;

    /**
     * The Constant DT_DATE.
     */
    public final static int DT_DATE = 2;

    /**
     * The Constant DT_TIME.
     */
    public final static int DT_TIME = 3;


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getStartYear()
     */
    @Override
    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }

    /**
     * The user_type.
     */
    public int user_type = 3;

    /**
     * The user types.
     */
    public String[] userTypes = null;


    /*
     * public int authorizeUser(String username, String password)
     * {
     * System.out.println(username + " " + password);
     * if ((username.equalsIgnoreCase("andy")) && (password.equals("Satja")))
     * {
     * return DataAccess.USER_SUPERADMINISTRATOR;
     * }
     * else
     * {
     * loginType = m_db.authenticateUser(username,password);
     * return loginType;
     * }
     * }
     */

    @Override
    protected void initDataDefinitionManager()
    {

    }


    @Override
    protected void initInternalSettings()
    {

    }


    /**
     * Not implemented.
     * 
     * @param source the source
     */
    public static void notImplemented(String source)
    {
        System.out.println("Not Implemented: " + source);
        // JOptionPane.showMessageDialog(parent, "Not Implemented: \n" +
        // source);
    }


    /**
     * Not implemented.
     * 
     * @param parent the parent
     * @param source the source
     */
    public static void notImplemented(java.awt.Component parent, String source)
    {
        System.out.println("Not Implemented: " + source);
        JOptionPane.showMessageDialog(parent, "Not Implemented: \n" + source);
    }


    /**
     * Checks if is found.
     * 
     * @param text the text
     * @param search_str the search_str
     * 
     * @return true, if is found
     */
    public static boolean isFound(String text, String search_str)
    {

        if (search_str.trim().length() == 0 || text.trim().length() == 0)
            return true;

        return text.trim().indexOf(search_str.trim()) != -1;
    }


    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String args[])
    {
        System.out.println("DT: 20051012");

        // DataAccess da = DataAccess.getInstance(null);
        // System.out.println(da.getDateString(20051012));
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#checkPrerequisites()
     */
    @Override
    public void checkPrerequisites()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public String getApplicationName()
    {
        return s_app_context.getTitle();
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getHibernateDb()
     */
    @Override
    public HibernateDb getHibernateDb()
    {
        // System.out.println("getHibernateDb()");
        // System.out.println("getHibernateDb():s_app_context" + s_app_context);
        // System.out.println("getHibernateDb():s_app_context.getDb():" +
        // DataAccessApp.s_app_context.getDb());
        return DataAccessApp.s_app_context.getDb();
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getImagesRoot()
     */
    @Override
    public String getImagesRoot()
    {
        return "/images/";
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#initSpecial()
     */
    @Override
    public void initSpecial()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void loadBackupRestoreCollection()
    {
    }


    @Override
    public void loadGraphConfigProperties()
    {
    }


    @Override
    public void loadSpecialParameters()
    {
    }


    /*
     * (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadPlugIns()
     */
    @Override
    public void loadPlugIns()
    {
        // TODO Auto-generated method stub

    }


    /*
     * (non-Javadoc)
     * @see
     * com.atech.utils.ATDataAccessAbstract#getMaxDecimalsUsedByDecimalHandler()
     */
    @Override
    public int getMaxDecimalsUsedByDecimalHandler()
    {
        return 2;
    }


    @Override
    public void loadDbApplicationContext()
    {
        System.out.println("DataAccessApp:loadDbApplicationContext: ");

        this.db_tool_app = s_app_context.getDbToolApplication();
        System.out.println("db_tool_app: " + s_app_context);
        System.out.println("db_tool_app: " + this.db_tool_app);

    }


    public DbConfig getJdbcConfig()
    {
        return this.jdbc_config;
    }


    /**
     * Gets the backup restore collection.
     * 
     * @return the backup restore collection
     */
    @Override
    public BackupRestoreCollection getBackupRestoreCollection()
    {
        return DataAccessApp.s_app_context.getBackupRestoreCollection();
    }


    @Override
    public TranslationToolConfigurationDto getTranslationToolConfiguration()
    {
        return null;
    }


    @Override
    public void saveTranslationToolConfiguration(TranslationToolConfigurationDto configuration)
    {

    }
}
