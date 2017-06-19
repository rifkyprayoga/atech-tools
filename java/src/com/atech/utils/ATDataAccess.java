package com.atech.utils;

import java.awt.*;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import com.atech.db.hibernate.HibernateDb;

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

public class ATDataAccess extends ATDataAccessAbstract
{

    /*
     * checkPrerequisites
     */
    /** 
     * checkPrerequisites
     */
    @Override
    public void checkPrerequisites()
    {
        // TODO Auto-generated method stub

    }


    /** 
     * getApplicationName
     */
    @Override
    public String getApplicationName()
    {
        return "Atech-Tools";
    }


    /** 
     * getImagesRoot
     */
    @Override
    public String getImagesRoot()
    {
        return "/icons/";
    }


    /** 
     * loadBackupRestoreCollection
     */
    @Override
    public void loadBackupRestoreCollection()
    {
    }

    // db administrator
    // private boolean m_db_administrator = false;

    /**
     * The m_settings.
     */
    public Hashtable<String, String> m_settings = null;

    /**
     * The printing_plugin_installed.
     */
    public boolean printing_plugin_installed = false;

    // public ATI18nControl m_i18n = ATI18nControl.getInstance();

    static private ATDataAccess m_da = null; // This is handle to unique
                                             // singelton instance
                                             // YYY
    // public PISDbBase m_db_base = null;
    // public PISDb m_db = null;
    // public PISMain m_main = null;

    // database loading status
    /**
                                              * The Constant DB_NOT_LOADED.
                                              */
    public static final int DB_NOT_LOADED = 0;

    /**
     * The Constant DB_BASE.
     */
    public static final int DB_BASE = 1;

    /**
     * The Constant DB_DIOCESE.
     */
    public static final int DB_DIOCESE = 2;

    /**
     * The Constant DB_DIOCESE_PERSONAL.
     */
    public static final int DB_DIOCESE_PERSONAL = 3;

    /**
     * The Constant DB_LOAD_COMPLETE.
     */
    public static final int DB_LOAD_COMPLETE = 10;

    private int db_loading_status = 0;

    /**
     * The yes_no_combo.
     */
    public Object[] yes_no_combo = null;

    // Configuration icons

    /**
     * The config_icons.
     */
    public ImageIcon config_icons[] = null;
    /*
     * {
     * ATSwingUtils.getImage(new ImageIcon("images/cfg_db.gif"),
     * new ImageIcon("images/cfg_look.gif"),
     * new ImageIcon("images/cfg_myparish.gif"),
     * new ImageIcon("images/cfg_masses.gif"),
     * new ImageIcon("images/cfg_users.gif"),
     * new ImageIcon("images/cfg_lang.gif"),
     * // new ImageIcon("images/cfg_web.gif"),
     * null
     * };
     */

    /*
     * public ImageIcon config_icons[] = {
     * new ImageIcon("images/cfg_db.gif"),
     * new ImageIcon("images/cfg_look.gif"),
     * new ImageIcon("images/cfg_myparish.gif"),
     * new ImageIcon("images/cfg_masses.gif"),
     * new ImageIcon("images/cfg_users.gif"),
     * new ImageIcon("images/cfg_lang.gif"),
     * // new ImageIcon("images/cfg_web.gif"),
     * null
     * };
     */

    // Container parent = null;

    // Civil Events

    // 20 = Marridge
    // 21 = Divorce

    /**
    * The gender_minus.
    */
    public String gender_minus[] = { m_i18n.getMessage("GENDER_M"), // 1
                                     m_i18n.getMessage("GENDER_F") };

    /**
     * The gender.
     */
    public String gender[] = { m_i18n.getMessage("SELECT"), m_i18n.getMessage("GENDER_M"), // 1
                               m_i18n.getMessage("GENDER_F") };

    /**
     * The contact_types.
     */
    public String contact_types[] = { m_i18n.getMessage("SELECT"), m_i18n.getMessage("PHONE"), m_i18n.getMessage("GSM"),
                                      m_i18n.getMessage("FAX"), m_i18n.getMessage("EMAIL"),
                                      m_i18n.getMessage("WEB_PAGE"), m_i18n.getMessage("ICQ_MSNG"),
                                      m_i18n.getMessage("YAHOO_MSNG"), m_i18n.getMessage("AIM_MSNG"),
                                      m_i18n.getMessage("JABBER_MSNG"), m_i18n.getMessage("MSN_MSNG"),
                                      m_i18n.getMessage("SKYPE_MSNG"), m_i18n.getMessage("OTHER") };

    /**
     * The contact_icons.
     */
    public ImageIcon contact_icons[] = { null, new ImageIcon("images/c_phone.gif"), // m_i18n.getMessage("PHONE"),
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
     * The config_types.
     */
    public String config_types[] = {

                                     m_i18n.getMessage("DB_SETTINGS"), m_i18n.getMessage("LOOK"),
                                     m_i18n.getMessage("MY_PARISHES"), m_i18n.getMessage("MASSES"),
                                     m_i18n.getMessage("USERS"), m_i18n.getMessage("LANGUAGE"),
            // m_i18n.getMessage("WEB_CONFIG")
    };

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
    private ATDataAccess()
    {
        super(ATI18nControl.getInstance());
    }


    /** 
     * initSpecial
     */
    @Override
    public void initSpecial()
    {
        loadColors();
    }


    // Method: getInstance
    // Author: Andy
    /**
     *
     *  This method returns reference to OmniI18nControl object created, or if no 
     *  object was created yet, it creates one.<br><br>
     *
     *  @return Reference to OmniI18nControl object
     * 
     */
    static public ATDataAccess getInstance()
    {
        if (m_da == null)
        {
            m_da = new ATDataAccess();
        }
        return m_da;
    }


    /** 
     * getHibernateDb
     */
    @Override
    public HibernateDb getHibernateDb()
    {
        return null;
    }


    // Method: deleteInstance
    /**
     *  This method sets handle to DataAccess to null and deletes the instance. <br><br>
     */
    public void deleteInstance()
    {

        m_i18n = null;

    }


    // ********************************************************
    // ****** Database Loading Status *****
    // ********************************************************

    /** 
     * setDbLoadingStatus
     */
    @Override
    public void setDbLoadingStatus(int status)
    {
        this.db_loading_status = status;
    }


    /** 
     * getDbLoadingStatus
     */
    @Override
    public int getDbLoadingStatus()
    {
        return this.db_loading_status;
    }


    /** 
     * isDbLoadedForStatus
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
    // ****** Demo stuff *****
    // ********************************************************


    // JDialog m_dialog = null;

    /** 
     * getOpenedDialog
     */
    @Override
    public JDialog getOpenedDialog()
    {
        return this.m_dialog;
    }


    /** 
     * setOpenedDialog
     */
    @Override
    public void setOpenedDialog(JDialog dialog)
    {
        this.m_dialog = dialog;
    }

    // ********************************************************
    // ****** Help stuff *****
    // ********************************************************

    // ********************************************************
    // ****** Fonts *****
    // ********************************************************


    // ********************************************************
    // ****** Parent handling (for UIs) *****
    // ********************************************************

    /** 
     * setParent
     */
    @Override
    public void setParent(Container component)
    {
        this.parent = component;
    }


    /** 
     * getParent
     */
    @Override
    public Container getParent()
    {
        return this.parent;
    }

    // ********************************************************
    // ****** Sorting algorithms *****
    // ********************************************************

    // ********************************************************
    // ****** GUI *****
    // ********************************************************

    // ********************************************************
    // ****** Look and Feel *****
    // ********************************************************


    // ********************************************************
    // ****** Colors *****
    // ********************************************************

    /** 
     * loadColors
     */
    @Override
    public void loadColors()
    {
        ColorUIResource cui = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("textText");
        this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui.getBlue(), cui.getAlpha());

        ColorUIResource cui2 = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("Label.background");
        this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2.getBlue(), cui2.getAlpha());

        this.border_line = new LineBorder(this.color_foreground);
    }

    // ********************************************************
    // ****** Languages *****
    // ********************************************************

    /*
     * public Object[] getAvailableLanguages()
     * {
     * return new Object[] = { "en" };;
     * }
     * public int getSelectedLangIndex()
     * {
     * return 1;
     * //return m_lang_info.findInLocale(this.m_config_file.selected_lang);
     * }
     * public static String getSelectedLocale()
     * {
     * String locale = "en";
     * try
     * {
     * Properties props = new Properties();
     * FileInputStream in = new FileInputStream(pathPrefix +
     * "../data/PIS_Config.properties");
     * props.load(in);
     * int sel_lang = 1;
     * if (props.containsKey("SELECTED_LANG"))
     * {
     * sel_lang = Integer.parseInt((String)props.get("SELECTED_LANG"));
     * System.out.println("Sel lang: " + sel_lang);
     * }
     * //props = new Properties();
     * props.clear();
     * in = null;
     * in = new FileInputStream(pathPrefix +
     * "/data/lang/PIS_Languages.properties");
     * props.load(in);
     * if (props.containsKey("LANG_" + sel_lang + "_LOCALE"))
     * {
     * locale = (String)props.get("LANG_" + sel_lang + "_LOCALE");
     * }
     * // System.out.println("Locale: " + locale);
     * }
     * catch(Exception ex)
     * {
     * System.out.println("DataAccess::getSelectedLocale::Exception> " + ex);
     * }
     * return locale;
     * }
     */

    // ********************************************************
    // ****** Config File Handling *****
    // ********************************************************

    /*
     * public String[] getAvailableDbs()
     * {
     * //this.m_config_file.
     * //return allDbs;
     * return this.m_config_file.getAllDatabasesNamesAsArray();
     * }
     * public int getSelectedDbIndex()
     * {
     * return this.m_config_file.getSelectedDatabaseIndex();
     * /*
     * for (int i=0; i<allDbs.length; i++)
     * {
     * if (allDbs[i].startsWith(this.selected_db + " - "))
     * return i;
     * }
     * return 0;
     */
    // }

    /*
     * public void loadComboOptions()
     * {
     * yes_no_combo = new Object[2];
     * yes_no_combo[0] = m_i18n.getMessage("OPTION_YES");
     * yes_no_combo[1] = m_i18n.getMessage("OPTION_NO");
     * Hashtable ht = m_db.getProductType(-1);
     * typesAll = new Object[ht.size()];
     * int i = 0;
     * for(Enumeration en=ht.keys(); en.hasMoreElements(); )
     * {
     * String key = (String)en.nextElement();
     * String key2 = "";
     * if (key.length()==1)
     * {
     * key2 = "0"+key;
     * }
     * else
     * key2 = key;
     * typesAll[i] = key2 + " - " +
     * ((ProductType)ht.get(key)).path.substring(1);
     * i++;
     * }
     * Arrays.sort(typesAll);
     * }
     */

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


    /*
     * public String getGCObjectFromDateTimeLong(long dt)
     * {
     * int y = (int)(dt/100000000L);
     * dt -= y*100000000L;
     * int m = (int)(dt/1000000L);
     * dt -= m*1000000L;
     * int d = (int)(dt/10000L);
     * dt -= d*10000L;
     * int h = (int)(dt/100L);
     * dt -= h*100L;
     * int min = (int)dt;
     * // GregorianCalendar gc1 = new GregorianCalendar();
     * //gc1.set(GregorianCalendar.
     * return null;
     * }
     */

    /** 
     * getStartYear
     */
    @Override
    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }


    /**
     * Gets the gender combo.
     * 
     * @return the gender combo
     */
    public Object[] getGenderCombo()
    {
        return gender;

    }

    /*
     * public static final int USER_NORMAL = 1;
     * public static final int USER_WORKER = 2;
     * public static final int USER_ADMINISTRATOR = 3;
     * public static final int USER_SUPERADMINISTRATOR = 4;
     * public int user_type = 3;
     */

    /**
    * The user types.
    */
    public String[] userTypes = { m_i18n.getMessage("SELECT"), m_i18n.getMessage("USER_NORMAL"),
                                  m_i18n.getMessage("USER_WORKER"), m_i18n.getMessage("USER_ADMINISTRATOR"),
                                  m_i18n.getMessage("USER_SUPERADMIN"), };


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


    /*
     * public static void main(String args[])
     * {
     * System.out.println("DT: 20051012");
     * DataAccess da = DataAccess.getInstance(null);
     * System.out.println(da.getDateString(20051012));
     * }
     */

    /**
     * Load Graph Config Properties
     * 
     * @see com.atech.utils.ATDataAccessAbstract#loadGraphConfigProperties()
     */
    @Override
    public void loadGraphConfigProperties()
    {

    }


    /** 
     * loadSpecialParameters
     */
    @Override
    public void loadSpecialParameters()
    {
        // TODO Auto-generated method stub

    }


    /**
     * This method is intended to load additional Language info. Either special langauge configuration
     * or special data required for real Locale handling.
     */
    @Override
    public void loadLanguageInfo()
    {
        // TODO Auto-generated method stub
    }


    @Override
    public void initObserverManager()
    {

    }


    /** 
     * loadPlugIns
     */
    @Override
    public void loadPlugIns()
    {
        // TODO Auto-generated method stub

    }


    /**
     * Get Max Decimals that will be used by DecimalHandler
     * 
     * @return
     */
    @Override
    public int getMaxDecimalsUsedByDecimalHandler()
    {
        return 1;
    }

}
