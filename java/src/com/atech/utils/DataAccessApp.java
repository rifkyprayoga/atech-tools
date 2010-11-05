
package com.atech.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import com.atech.app.AbstractApplicationContext;
import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.hdb_object.User;
import com.atech.gui_fw.MainAppFrame;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.info.LanguageInfo;

// TODO: Auto-generated Javadoc
// LOAD
// SAVE


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
    Hashtable<String,String> config_db_values = null;
   
    // login

    



    /**
     * The m_settings.
     */
    public Hashtable<String,String> m_settings = null;

    /**
     * The path prefix.
     */
    public static String pathPrefix = "../";

    //public UserDat reg_info = null;


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
    public I18nControlAbstract m_i18n = null; //I18nControl.getInstance();

    static protected DataAccessApp m_da = null;   // This is handle to unique 
                                             // singelton instance
// YYY
    /**
      * The m_db.
      */
     //public MDODb m_db = null;
    
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
    //public Hashtable typesHT = new Hashtable();

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
/*    { 
        m_i18n.getMessage("SELECT"),
        m_i18n.getMessage("PHONE"),
        m_i18n.getMessage("GSM"),
        m_i18n.getMessage("FAX"),
        m_i18n.getMessage("EMAIL"),
        m_i18n.getMessage("WEB_PAGE"),
        m_i18n.getMessage("ICQ_MSNG"),
        m_i18n.getMessage("YAHOO_MSNG"),
        m_i18n.getMessage("AIM_MSNG"),
        m_i18n.getMessage("JABBER_MSNG"),
        m_i18n.getMessage("MSN_MSNG"),
        m_i18n.getMessage("SKYPE_MSNG"),
        m_i18n.getMessage("OTHER")
    };
*/
    /**
     * The contact_icons.
     */
    public ImageIcon contact_icons[] = { null,
        new ImageIcon("images/c_phone.gif"), //m_i18n.getMessage("PHONE"),
        new ImageIcon("images/c_GSM.gif"), //m_i18n.getMessage("GSM"),
        new ImageIcon("images/c_fax.gif"), //m_i18n.getMessage("FAX"),
        new ImageIcon("images/c_email.gif"), //m_i18n.getMessage("EMAIL"),
        new ImageIcon("images/c_hp.gif"), //m_i18n.getMessage("WEB_PAGE"),
        new ImageIcon("images/c_icq.gif"), //m_i18n.getMessage("ICQ_MSNG"),
        new ImageIcon("images/c_yahoo.gif"), //m_i18n.getMessage("YAHOO_MSNG"),
        new ImageIcon("images/c_aim.gif"), //m_i18n.getMessage("MSN_MSNG")
        new ImageIcon("images/c_jabber.gif"),
        new ImageIcon("images/c_msn.gif"),
        null,
        null
    };


    /**
     * The mass_status.
     */
    public ImageIcon mass_status[] = { 
	new ImageIcon("images/dot_green.gif"),
	new ImageIcon("images/dot_orange.gif"), //m_i18n.getMessage("PHONE"),
	new ImageIcon("images/dot_blue.gif"), //m_i18n.getMessage("PHONE"),
	new ImageIcon("images/dot_red.gif"), //m_i18n.getMessage("PHONE"),
    };







    /**
     * The days.
     */
//    public String days[] = null;
/*	{
	    m_i18n.getMessage("MONDAY"), 
	    m_i18n.getMessage("TUESDAY"), 
	    m_i18n.getMessage("WEDNESDAY"), 
	    m_i18n.getMessage("THURSDAY"),
	    m_i18n.getMessage("FRIDAY"), 
	    m_i18n.getMessage("SATURDAY"), 
	    m_i18n.getMessage("SUNDAY")
	};*/





        /**
         * The months.
         */
/*        public String months[] = null; 
        { 
            m_i18n.getMessage("JANUARY"),
            m_i18n.getMessage("FEBRUARY"),
            m_i18n.getMessage("MARCH"),
            m_i18n.getMessage("APRIL"),
            m_i18n.getMessage("MAY"),
            m_i18n.getMessage("JUNE"),
            m_i18n.getMessage("JULY"),
            m_i18n.getMessage("AUGUST"),
            m_i18n.getMessage("SEPTEMBER"),
            m_i18n.getMessage("OCTOBER"),
            m_i18n.getMessage("NOVEMBER"),
            m_i18n.getMessage("DECEMBER")
        };

*/
        

    // ********************************************************
    // ******      Constructors and Access methods        *****    
    // ********************************************************



    //   Constructor:  DataAccess
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
        
        //this.m_app_context = aac;
        
        //     public ATDataAccessLMAbstract(LanguageManager lm, I18nControlRunner icr)

        
        //m_db = db;
        //reg_info = new UserDat();

        //loadConfig();
        loadFonts();
        //loadAvailableLFs();
        loadLanguageInfo();
        loadColors();

        m_settings = new Hashtable<String,String>();
  //      loadDioceseRoot();

    } 

/*
    private DataAccess(ClockDb db)
    {
        super(I18nControl.getInstance());
        m_db_c = db;
        //reg_info = new UserDat();

        //loadConfig();
        loadFonts();
        //loadAvailableLFs();
        loadLanguageInfo();
        loadColors();

        m_settings = new Hashtable<String,String>();
  //      loadDioceseRoot();

    } 
*/





    /**
     * Creates the instance.
     * 
     * @param main the main
     * 
     * @return the data access
     */
    static public DataAccessApp createInstance(MainAppFrame main, AbstractApplicationContext aac )
    {
        if (m_da == null)
        {
            if (main.getSplash()!=null)
                main.getSplash().setSplashProgress(18, aac.getI18nControl().getMessage("CREATE_DATABASE_INSTANCE"));

            s_app_context = aac;
            //aac.setFrame(main);
            //aac.initDb();
            
            //MDODb db = new MDODb();
            
            
            
            m_da = new DataAccessApp(aac);
            m_da.setParent(main);
            m_da.setMainParent(main);
            
            m_da.help_enabled = s_app_context.isHelpEnabled();
            
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




    //  Method:       deleteInstance
    /**
     * This method sets handle to DataAccess to null and deletes the instance. <br><br>
     */ 
    public void deleteInstance()
    {

        m_i18n=null;

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
    public void loadDioceseRoot()
    {
        m_dio_treeroot = new DioceseTreeRoot(1);
    }
*/



    // ********************************************************
    // ******         Database Loading Status             *****    
    // ********************************************************


    /* (non-Javadoc)
 * @see com.atech.utils.ATDataAccessAbstract#setDbLoadingStatus(int)
 */
public void setDbLoadingStatus(int status)
    {
	this.db_loading_status = status;
    }

    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDbLoadingStatus()
     */
    public int getDbLoadingStatus()
    {
	return this.db_loading_status;
    }


    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#isDbLoadedForStatus(int)
     */
    public boolean isDbLoadedForStatus(int status)
    {
	if ((this.db_loading_status==status) || 
	    (this.db_loading_status>status))
	    return true;
	else
	    return false;
    }




    // ********************************************************
    // ******                   Fonts                     *****    
    // ********************************************************

    /**
     * The Constant FONT_BIG_BOLD.
     */
    public static final int FONT_BIG_BOLD = 0;
    
    /**
     * The Constant FONT_NORMAL.
     */
    public static final int FONT_NORMAL = 1;
    
    /**
     * The Constant FONT_NORMAL_BOLD.
     */
    public static final int FONT_NORMAL_BOLD = 2;
    
    /**
     * The Constant FONT_NORMAL_P2.
     */
    public static final int FONT_NORMAL_P2 = 3;
    
    /**
     * The Constant FONT_NORMAL_BOLD_P2.
     */
    public static final int FONT_NORMAL_BOLD_P2 = 4;

    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadFonts()
     */
    public void loadFonts()
    {

        fonts = new Font[5];
        fonts[0] = new Font("SansSerif", Font.BOLD, 22);
        fonts[1] = new Font("SansSerif", Font.PLAIN, 12);
        fonts[2] = new Font("SansSerif", Font.BOLD, 12);
	fonts[3] = new Font("SansSerif", Font.PLAIN, 14);
	fonts[4] = new Font("SansSerif", Font.BOLD, 14);

    }


    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getFont(int)
     */
    public Font getFont(int font_id)
    {
        return fonts[font_id];
    }






    // ********************************************************
    // ******          Parent handling (for UIs)          *****    
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



    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getParent()
     */
    public MainAppFrame getParent()
    {
        return m_main;
    }




    // ********************************************************
    // ******               Look and Feel                 *****    
    // ********************************************************




    /**
     * Gets the lF data.
     * 
     * @return the lF data
     */
/*    public static String[] getLFData()
    {
        String out[] = new String[2];

        try
        {
            Properties props = new Properties();

            FileInputStream in = new FileInputStream(pathPrefix  + "/data/MSP_Config.properties");
            props.load(in);

            out[0] = (String)props.get("LF_CLASS");
            out[1] = (String)props.get("SKINLF_SELECTED");

            return out;

        }
        catch(Exception ex)
        {
            System.out.println("DataAccess::getLFData::Exception> " + ex);
            return null;
        }
    }
*/
    
    
    
    // ********************************************************
    // ******                    Colors                   *****    
    // ********************************************************

    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadColors()
     */
    public void loadColors()
    {
	ColorUIResource cui = (ColorUIResource)UIManager.getLookAndFeel().getDefaults().get("textText");
	this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui.getBlue(), cui.getAlpha());

	ColorUIResource cui2 = (ColorUIResource)UIManager.getLookAndFeel().getDefaults().get("Label.background");
	this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2.getBlue(), cui2.getAlpha());

	this.border_line = new LineBorder(this.color_foreground);
    }




    // ********************************************************
    // ******                  Languages                  *****    
    // ********************************************************


    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadLanguageInfo()
     */
    public void loadLanguageInfo()
    {
        //m_lang_info = new LanguageInfo();
        //reg_info.setCoding("2|4|2|-2|1|12|6|-2|2|1");
        //m_lang_info.loadData();

        /*
        try
        {

            Properties props = new Properties();

            FileInputStream in = new FileInputStream(pathPrefix + "/data/lang/MSP_Languages.properties");
            props.load(in);
            reg_info.setCoding("2|4|2|-2|1|12|6|-2|2|1");

           m_lang_info = new LanguageInfo();
            m_lang_info.loadData(props);

        }
        catch(Exception ex)
        {
            System.out.println("DataAccess::loadLanguageInfo::Exception> " + ex);
        }*/

    }


    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getLanguageInfo()
     */
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

            //this.
            
            FileInputStream in = new FileInputStream(s_app_context.getDbToolApplication().getApplicationDatabaseConfig()); //pathPrefix  + "/data/MSP_Config.properties");
            props.load(in);

            //int sel_lang = 1;
            String sel_lang = "si";

            if (props.containsKey("SELECTED_LANG"))
            {
                sel_lang = (String)props.get("SELECTED_LANG"); //Integer.parseInt((String)props.get("SELECTED_LANG"));
                System.out.println("Sel lang: " + sel_lang);
            }


            //props = new Properties();
            props.clear();

            
            try
            {
            
                in = null;
                in = new FileInputStream(pathPrefix  + "/data/lang/MDO_Languages.properties");
                props.load(in);

            
                int av_langs = Integer.parseInt((String)props.get("AVAILABLE_LANGUAGES"));
            
                for(int i=1; i<= av_langs; i++)
                {
                    
                    if (props.containsKey("LANG_" + i))
                    {
                     
                        String l = (String)props.get("LANG_" + i);
                        
                        if (l.equals(sel_lang))
                        {
                            if (props.containsKey("LANG_" + i + "_LOCALE"))
                            {
                                locale = (String)props.get("LANG_" + i + "_LOCALE");
                            }
                        }
                    }    
                }
                
            
            } 
            catch(Exception ex)
            {
                System.out.println("Error reading Language Manager file. " + ex);
            }

        }
        catch(Exception ex)
        {
            System.out.println("DataAccessApp::getSelectedLocale::Exception> " + ex);
        }

        return locale;

    }


    // ********************************************************
    // ******                Login/Logout                 *****    
    // ********************************************************



    /**
     * Gets the all users.
     * 
     * @return the all users
     */
    public ArrayList<User> getAllUsers()
    {
    	if (this.all_users==null)
    	{
    	    this.all_users = s_app_context.getUsers();
    	}
    
    	return this.all_users;
    }


    /**
     * Process login.
     */
    public void processLogin()
    {
        if (this.logged_user==null)
        {
            s_app_context.setLoadingStatus(0);
        }
        else
        {
            s_app_context.setLoadingStatus(this.logged_user.getUser_access());
        }
    }




    // ********************************************************
    // ******            Config File Handling             *****    
    // ********************************************************


 

     

 




// -----------------------




    




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getMonthsArray()
     */
    public String[] getMonthsArray()
    {
        return this.months;

        /*
        String arr[] = new String[12];

        arr[0] = m_i18n.getMessage("JANUARY");
        arr[1] = m_i18n.getMessage("FEBRUARY");
        arr[2] = m_i18n.getMessage("MARCH");
        arr[3] = m_i18n.getMessage("APRIL");
        arr[4] = m_i18n.getMessage("MAY");
        arr[5] = m_i18n.getMessage("JUNE");
        arr[6] = m_i18n.getMessage("JULY");
        arr[7] = m_i18n.getMessage("AUGUST");
        arr[8] = m_i18n.getMessage("SEPTEMBER");
        arr[9] = m_i18n.getMessage("OCTOBER");
        arr[10] = m_i18n.getMessage("NOVEMBER");
        arr[11] = m_i18n.getMessage("DECEMBER");

        return arr;*/

    }





    /**
     * Gets the selected contact type.
     * 
     * @param value the value
     * 
     * @return the selected contact type
     */
    public int getSelectedContactType(String value)
    {

        int i=0;



        boolean found = false;

        for (i=0; i<contact_types.length ;i++)
        {
            if (value.equals(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        //System.out.println(value +  " found: " + found + " " + i);

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
    public int getSelectedContactTypePart(String value)
    {

        int i=0;



        boolean found = false;

        for (i=0; i<contact_types.length ;i++)
        {
            if (value.startsWith(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        //System.out.println(value +  " found: " + found + " " + i);

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
    public int getSelectedConfigTypePart(String value)
    {

        int i=0;



        boolean found = false;

        for (i=0; i<contact_types.length ;i++)
        {
            if (value.startsWith(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        //System.out.println(value +  " found: " + found + " " + i);

        if (found)
            return i;
        else
            return 0;

    }







    // type: 1=ADD_EVENT, 
    //       2=ADD_EVENT_PERSON_CHURCH  (ADD PersonEvent)
    //       3=ADD_EVENT
    //       4








/*


    
    public void makeNewConfig()
    {

        configStatic.config = new dataConfig();
        
        
        configStatic.config.mainDir="";
        configStatic.config.shellSelected=1;
        configStatic.config.shellCommand="cmd /c";
        configStatic.config.browserEnabled=true;
        configStatic.config.browserExternal=false;
        configStatic.config.mailEnabled=false;
        configStatic.config.ftpEnabled=false;
        configStatic.config.ftpInternalEnabled=false;
        saveConfig();
    
    }



    public void loadConfig()
    {

        try
        {
            ObjectInputStream in = new ObjectInputStream(
                                     new FileInputStream("../data/config.dat"));
            configStatic.config=(dataConfig)in.readObject();
            in.close();
        }
        catch (IOException ex)
        {
            makeNewConfig();
        }
        catch (ClassNotFoundException ex)
        {
        }
    }
    
    public void saveConfig()
    {
        
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(
                                     new FileOutputStream("../data/config.dat"));
            out.writeObject(configStatic.config);
            out.close();
        }
        catch (IOException ex)
        {
            System.out.println("Error saving configuration.");
        }

    }

    
/*
    public String getShell()
    {
        //return configStatic.browserCommand;
        return "";
    }

    public String getBrowserCmd()
    {
//        return configStatic.browserPath[configStatic.useBrowserNr];
        return "";

    }

    public String getMailerCmd()
    {
//        return configStatic.browserPath[1];
        return "";
    }

*/

    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDateString(int)
     */
    public String getDateString(int date)
    {

        // 20051012

        int year = date/10000;
        int months = date - (year*10000);

        months = months/100;

        int days = date - (year*10000) - (months*100);

        if (year==0)
        {
            return getLeadingZero(days,2) + "/" + getLeadingZero(months,2);
        }
        else
            return getLeadingZero(days,2) + "/" + getLeadingZero(months,2) + "/" + year;

    }





    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getTimeString(int)
     */
    public String getTimeString(int time)
    {

        int hours = time/100;

        int min = time - hours*100;

        return getLeadingZero(hours,2) + ":" + getLeadingZero(min,2);

    }

    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDateTimeString(long)
     */
    public String getDateTimeString(long date)
    {
        return getDateTimeString(date, 1);
    }


    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDateTimeAsDateString(long)
     */
    public String getDateTimeAsDateString(long date)
    {
        return getDateTimeString(date, 2);
    }


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

    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getATDateTimeFromGC(java.util.GregorianCalendar, int)
     */
    public long getATDateTimeFromGC(GregorianCalendar gc, int type)
    {
	long dt = 0L;

	if (type==DATE_TIME_ATECH_DATETIME)
	{
	    dt += gc.get(GregorianCalendar.YEAR) *100000000L;
	    dt += (gc.get(GregorianCalendar.MONTH)+1)*1000000L;
	    dt += gc.get(GregorianCalendar.DAY_OF_MONTH) *10000L;
	    dt += gc.get(GregorianCalendar.HOUR_OF_DAY) *100L;
	    dt += gc.get(GregorianCalendar.MINUTE);
	} 
	else if (type==DATE_TIME_ATECH_DATE)
	{
	    dt += gc.get(GregorianCalendar.YEAR) *10000L;
	    dt += (gc.get(GregorianCalendar.MONTH)+1)*100L;
	    dt += gc.get(GregorianCalendar.DAY_OF_MONTH);
	}
	else if (type==DATE_TIME_ATECH_TIME)
	{
	    dt += gc.get(GregorianCalendar.HOUR_OF_DAY) *100L;
	    dt += gc.get(GregorianCalendar.MINUTE);
	}

	return dt;
    }



    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getATDateTimeFromParts(int, int, int, int, int, int)
     */
    public long getATDateTimeFromParts(int day, int month, int year, int hour, int minute, int type)
    {
	long dt = 0L;

	if (type==DATE_TIME_ATECH_DATETIME)
	{
	    dt += year *100000000L;
	    dt += month *1000000L;
	    dt += day *10000L;
	    dt += hour *100L;
	    dt += minute;
	} 
	else if (type==DATE_TIME_ATECH_DATE)
	{
	    dt += year *10000L;
	    dt += month *100L;
	    dt += day;
	}
	else if (type==DATE_TIME_ATECH_TIME)
	{
	    dt += hour *100L;
	    dt += minute;
	}

	return dt;
    }





    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDateFromATDate(long)
     */
    public long getDateFromATDate(long data)
    {
	// 200701011222
	int d2 = (int)(data/10000);

	//long dd = data%10000;
	//data -= dd;

	//System.out.println("D2: " +d2);

	//System.out.println(data);
	return d2;
    }



    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDateTimeAsTimeString(long)
     */
    public String getDateTimeAsTimeString(long date)
    {
        return getDateTimeString(date, 3);
    }


    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)

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



    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDateTimeString(long, int)
     */
    public String getDateTimeString(long dt, int ret_type)
    {

        //System.out.println("DT process: " + dt);
        /*
        int y = (int)(dt/10000000L);
        dt -= y*10000000L;

        int m = (int)(dt/1000000L);
        dt -= m*1000000L;

        int d = (int)(dt/10000L);
        dt -= d*10000L;

        int h = (int)(dt/100L);
        dt -= h*100L;

        int min = (int)dt;
*/
        

// 200612051850
        int y = (int)(dt/100000000L);
        dt -= y*100000000L;

        int m = (int)(dt/1000000L);
        dt -= m*1000000L;

        int d = (int)(dt/10000L);
        dt -= d*10000L;

        int h = (int)(dt/100L);
        dt -= h*100L;

        int min = (int)dt;

        
        if (ret_type==DT_DATETIME)
        {
            return getLeadingZero(d,2) + "/" + getLeadingZero(m,2) + "/" + y + "  " + getLeadingZero(h,2) + ":" + getLeadingZero(min,2);
        }
        else if (ret_type==DT_DATE)
        {
            return getLeadingZero(d,2) + "/" + getLeadingZero(m,2) + "/" + y;
        }
        else
            return getLeadingZero(h,2) + ":" + getLeadingZero(min,2);

    }



    /**
     * Gets the gC object from date time long.
     * 
     * @param dt the dt
     * 
     * @return the gC object from date time long
     */
    public String getGCObjectFromDateTimeLong(long dt)
    {

	int y = (int)(dt/100000000L);
	dt -= y*100000000L;

	int m = (int)(dt/1000000L);
	dt -= m*1000000L;

	int d = (int)(dt/10000L);
	dt -= d*10000L;

	int h = (int)(dt/100L);
	dt -= h*100L;

	//int min = (int)dt;

	//GregorianCalendar gc1 = new GregorianCalendar();
	//gc1.set(GregorianCalendar.

	return null;

    }





    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getDateTimeString(int, int)
     */
    public String getDateTimeString(int date, int time)
    {

        return getDateString(date)+" " + getTimeString(time);

    }



    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getLeadingZero(int, int)
     */
    public String getLeadingZero(int number, int places)
    {

        String nn = ""+number;

        while (nn.length()<places)
        {
            nn = "0"+nn;
        }

        return nn;

    }


    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getStartYear()
     */
    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }


 


    /**
     * The Constant USER_NORMAL.
     */
    public static final int USER_GUEST = 1;



    /**
     * The Constant USER_NORMAL.
     */
    public static final int USER_NORMAL = 2;
    
    /**
     * The Constant USER_WORKER.
     */
    public static final int USER_WORKER = 3;
    
    /**
     * The Constant USER_ADMINISTRATOR.
     */
    public static final int USER_ADMINISTRATOR = 4;
    
    /**
     * The Constant USER_SUPERADMINISTRATOR.
     */
    public static final int USER_SUPERADMINISTRATOR = 5;

    /**
     * The user_type.
     */
    public int user_type = 3;


    /**
     * The user types.
     */
    public String[] userTypes = null;
/*    {
        m_i18n.getMessage("SELECT"),
        m_i18n.getMessage("USER_NORMAL"),
        m_i18n.getMessage("USER_WORKER"),
        m_i18n.getMessage("USER_ADMINISTRATOR"),
        m_i18n.getMessage("USER_SUPERADMIN"),
    }; */


    /*
    public int authorizeUser(String username, String password)
    {

        System.out.println(username + " " + password);

        if ((username.equalsIgnoreCase("andy")) && (password.equals("Satja")))
        {
            return DataAccess.USER_SUPERADMINISTRATOR;
        }
        else
        {
            loginType = m_db.authenticateUser(username,password);
            return loginType;
        }

    }
    */

    /**
     * Not implemented.
     * 
     * @param source the source
     */
    public static void notImplemented(String source)
    {
	System.out.println("Not Implemented: " + source);
	//JOptionPane.showMessageDialog(parent, "Not Implemented: \n" + source);
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
     * For replacing strings.<br>
     * 
     * @param input   Input String
     * @param replace What to seatch for.
     * @param replacement  What to replace with.
     * 
     * @return Parsed string.
     */
    public String replaceExpression(String input, String replace, String replacement)
    {

        int idx;
        if ((idx=input.indexOf(replace))==-1)
        {
            return input;
        }

        StringBuffer returning = new StringBuffer();

        while (idx!=-1)
        {
            returning.append(input.substring(0, idx));
            returning.append(replacement);
            input = input.substring(idx+replace.length());
            idx = input.indexOf(replace);
        }
        returning.append(input);
        
        return returning.toString();

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

        if ((search_str.trim().length()==0) || (text.trim().length()==0))
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

        //DataAccess da = DataAccess.getInstance(null);
        //System.out.println(da.getDateString(20051012));
    }




    /* (non-Javadoc)
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




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getHibernateDb()
     */
    @Override
    public HibernateDb getHibernateDb()
    {
        //System.out.println("getHibernateDb()");
        //System.out.println("getHibernateDb():s_app_context" + s_app_context);
        //System.out.println("getHibernateDb():s_app_context.getDb():" + DataAccessApp.s_app_context.getDb());
        return (HibernateDb)DataAccessApp.s_app_context.getDb();
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getImagesRoot()
     */
    @Override
    public String getImagesRoot()
    {
        return "/images/";
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#initSpecial()
     */
    @Override
    public void initSpecial()
    {
        // TODO Auto-generated method stub
        
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadBackupRestoreCollection()
     */
    @Override
    public void loadBackupRestoreCollection()
    {
        // TODO Auto-generated method stub
        
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadGraphConfigProperties()
     */
    @Override
    public void loadGraphConfigProperties()
    {
        // TODO Auto-generated method stub
        
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadSpecialParameters()
     */
    @Override
    public void loadSpecialParameters()
    {
        // TODO Auto-generated method stub
        
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#setSelectedLangIndex(int)
     */
    @Override
    public void setSelectedLangIndex(int index)
    {
        // TODO Auto-generated method stub
        
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#loadPlugIns()
     */
    @Override
    public void loadPlugIns()
    {
        // TODO Auto-generated method stub
        
    }




    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getMaxDecimalsUsedByDecimalHandler()
     */
    @Override
    public int getMaxDecimalsUsedByDecimalHandler()
    {
        return 2;
    }


    /* (non-Javadoc)
     * @see com.atech.utils.ATDataAccessAbstract#getSelectedLangIndex()
     */
    @Override
    public int getSelectedLangIndex()
    {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void loadDbApplicationContext()
    {
        System.out.println("DataAccessApp:loadDbApplicationContext: ");

        this.db_tool_app = s_app_context.getDbToolApplication();
        System.out.println("db_tool_app: " + s_app_context);
        System.out.println("db_tool_app: " + this.db_tool_app);
        
        
    }


    /**
     * Enable help.
     * 
     * @param hc the hc
     */
  /*  public void enableHelp(HelpCapable hc)
    {
        if (s_app_context.isHelpEnabled())
        {
            this.help_context.getMainHelpBroker().enableHelpOnButton(hc.getHelpButton(), hc.getHelpId(), null);
            this.help_context.getMainHelpBroker().enableHelpKey(hc.getComponent(), hc.getHelpId(), null);
        }
    }
    */
    
    
    

}

