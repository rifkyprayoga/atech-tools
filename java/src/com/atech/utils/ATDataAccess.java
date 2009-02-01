
package com.atech.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import com.atech.db.hibernate.HibernateDb;


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

    // LF
//    Hashtable<String,String> availableLF_full = null;
//    Object[]  availableLF = null;
//    Object[]  availableLang = null;

    /* 
     * checkPrerequisites
     */
    @Override
    public void checkPrerequisites()
    {
        // TODO Auto-generated method stub
        
    }




    /* 
     * getApplicationName
     */
    @Override
    public String getApplicationName()
    {
        return "Atech-Tools";
    }




    /* 
     * getImagesRoot
     */
    @Override
    public String getImagesRoot()
    {
        return "/icons/";
    }




    /* 
     * loadBackupRestoreCollection
     */
    @Override
    public void loadBackupRestoreCollection()
    {
    }








    // db administrator
    //private boolean m_db_administrator = false;
    



    public Hashtable<String,String> m_settings = null;


    public boolean printing_plugin_installed = false;


//    public ATI18nControl m_i18n = ATI18nControl.getInstance();

    static private ATDataAccess m_da = null;   // This is handle to unique 
                                             // singelton instance
// YYY
//    public PISDbBase m_db_base = null;
//    public PISDb m_db = null;
//    public PISMain m_main = null;


    // database loading status
    public static final int DB_NOT_LOADED = 0;
    public static final int DB_BASE = 1;
    public static final int DB_DIOCESE = 2;
    public static final int DB_DIOCESE_PERSONAL = 3;
    public static final int DB_LOAD_COMPLETE = 10;


    private int db_loading_status = 0;




    public Object[] yes_no_combo = null;



    // Configuration icons


    public ImageIcon config_icons[] = null;
/*        {
        ATSwingUtils.getImage(new ImageIcon("images/cfg_db.gif"), 
        new ImageIcon("images/cfg_look.gif"), 
        new ImageIcon("images/cfg_myparish.gif"), 
        new ImageIcon("images/cfg_masses.gif"), 
        new ImageIcon("images/cfg_users.gif"), 
        new ImageIcon("images/cfg_lang.gif"), 
//        new ImageIcon("images/cfg_web.gif"), 
        null
    };
*/

    /*
    public ImageIcon config_icons[] = {
        new ImageIcon("images/cfg_db.gif"), 
        new ImageIcon("images/cfg_look.gif"), 
        new ImageIcon("images/cfg_myparish.gif"), 
        new ImageIcon("images/cfg_masses.gif"), 
        new ImageIcon("images/cfg_users.gif"), 
        new ImageIcon("images/cfg_lang.gif"), 
//        new ImageIcon("images/cfg_web.gif"), 
        null
    }; */


    //Container parent = null;


    // Civil Events

    // 20 = Marridge
    // 21 = Divorce


    public String gender_minus[] = { 
        m_i18n.getMessage("GENDER_M"),             // 1
        m_i18n.getMessage("GENDER_F")
    };


    public String gender[] = { m_i18n.getMessage("SELECT"),
        m_i18n.getMessage("GENDER_M"),             // 1
        m_i18n.getMessage("GENDER_F")
    };


    public String contact_types[] = { 
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


    public ImageIcon mass_status[] = { 
	new ImageIcon("images/dot_green.gif"),
	new ImageIcon("images/dot_orange.gif"), //m_i18n.getMessage("PHONE"),
	new ImageIcon("images/dot_blue.gif"), //m_i18n.getMessage("PHONE"),
	new ImageIcon("images/dot_red.gif"), //m_i18n.getMessage("PHONE"),
    };




    public String config_types[] = { 
        
        m_i18n.getMessage("DB_SETTINGS"),
        m_i18n.getMessage("LOOK"),
        m_i18n.getMessage("MY_PARISHES"),
        m_i18n.getMessage("MASSES"),
        m_i18n.getMessage("USERS"),
        m_i18n.getMessage("LANGUAGE"),
        //m_i18n.getMessage("WEB_CONFIG")
    };













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
    private ATDataAccess()
    {
        super(ATI18nControl.getInstance());
    } 


    public void initSpecial()
    {
        loadColors();
    }

    


    //  Method:       getInstance
    //  Author:       Andy
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
            m_da = new ATDataAccess();
        return m_da;
    }



    public HibernateDb getHibernateDb()
    {
        return null;
    }






    //  Method:       deleteInstance
    /**
     *  This method sets handle to DataAccess to null and deletes the instance. <br><br>
     */ 
    public void deleteInstance()
    {

        m_i18n=null;

    }







    // ********************************************************
    // ******         Database Loading Status             *****    
    // ********************************************************


    public void setDbLoadingStatus(int status)
    {
        this.db_loading_status = status;
    }

    public int getDbLoadingStatus()
    {
        return this.db_loading_status;
    }


    public boolean isDbLoadedForStatus(int status)
    {
    	if ((this.db_loading_status==status) || 
    	    (this.db_loading_status>status))
    	    return true;
    	else
    	    return false;
    }

    // ********************************************************
    // ******                 Demo stuff                  *****    
    // ********************************************************


    //JDialog m_dialog = null;

    public JDialog getOpenedDialog()
    {
        return this.m_dialog;
    }

    public void setOpenedDialog(JDialog dialog)
    {
        this.m_dialog = dialog;
    }


    // ********************************************************
    // ******                 Help stuff                  *****    
    // ********************************************************


    // ********************************************************
    // ******                   Fonts                     *****    
    // ********************************************************



    public Font getFont(int font_id)
    {
        return fonts[font_id];
    }






    // ********************************************************
    // ******          Parent handling (for UIs)          *****    
    // ********************************************************



/*
    private void loadIcons()
    {
        config_icons = new ImageIcon[7];
        config_icons[0] = new ImageIcon(getImage("/images/cfg_db.gif", m_main));
        config_icons[1] = new ImageIcon(getImage("/images/cfg_look.gif", m_main));
        config_icons[2] = new ImageIcon(getImage("/images/cfg_myparish.gif", m_main));
        config_icons[3] = new ImageIcon(getImage("/images/cfg_masses.gif", m_main));
        config_icons[4] = new ImageIcon(getImage("/images/cfg_users.gif", m_main));
        config_icons[5] = new ImageIcon(getImage("/images/cfg_lang.gif", m_main));
        config_icons[6] = null;

    }
*/

    //public ImageIcon getImageIcon(String image)


    public Image getImage(String filename, Component cmp)
    {
        Image img;

        InputStream is = this.getClass().getResourceAsStream(filename);

        if (is==null)
            System.out.println("Error reading image: "+filename);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            int c;
            while ((c = is.read()) >=0)
            baos.write(c);


            //JDialog.getT
            //JFrame.getToolkit();
            img = cmp.getToolkit().createImage(baos.toByteArray());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return img;
    }

    
    public void setParent(Container component)
    {
    	this.parent = component;
    }
    

    public Container getParent()
    {
    	return this.parent;
    }
    
    
    // ********************************************************
    // ******           Sorting algorithms                *****    
    // ********************************************************

    public int compareUnicodeStrings(String s1, String s2)
    {
        return this.m_collator.compare(s1, s2);
    }




    // ********************************************************
    // ******                     GUI                     *****    
    // ********************************************************

/*
    public void centerJDialog(JDialog dialog, Container parent)
    {
    	if (parent instanceof JDialog)
    	{
    	    centerJDialog(dialog, parent);
    	}
    	else
    	    System.out.println("CenterJDialog failed");
    	
    }
    
    
    
    public void centerJDialog(JDialog dialog, JComponent parent)
    {

    	//System.out.println("centerJDialog: " );
    
    	Rectangle rec = parent.getBounds();
    
    	int x = rec.width/2;
    	x += (rec.x);
    
    	int y = rec.height/2;
    	y += rec.y;
    
    	x -= (dialog.getBounds().width/2);
    	y -= (dialog.getBounds().height/2);
    
    	dialog.getBounds().x = x;
    	dialog.getBounds().y = y;
    
    	dialog.setBounds(x, y, dialog.getBounds().width, dialog.getBounds().height);

    }
*/



    // ********************************************************
    // ******               Look and Feel                 *****    
    // ********************************************************

/*
    public void loadAvailableLFs()
    {

        availableLF_full = new Hashtable<String,String>();
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

        availableLF = new Object[info.length+1];

        //ring selectedLF = null;
        //String subSelectedLF = null;

        int i;
        for (i=0; i<info.length; i++) 
        {
            String name = info[i].getName();
            String className = info[i].getClassName();

            availableLF_full.put(name, className);
            availableLF[i] = name;

            //System.out.println(humanReadableName);
        }     

        availableLF_full.put("SkinLF", "com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
        availableLF[i] = "SkinLF";

    }
*/

    /*
    public Object[] getAvailableLFs()
    {
        return this.m_config_file.getAvailableLFs();
        //return availableLF;
    }


    public static String[] getLFData()
    {
        
        String out[] = new String[2];

        try
        {
            Properties props = new Properties();

            FileInputStream in = new FileInputStream("../data/PIS_Config.properties");
            props.load(in);

            out[0] = (String)props.get("LF_CLASS");
            out[1] = (String)props.get("SKINLF_SELECTED");

            return out;

        }
        catch(Exception ex)
        {
            System.out.println("DataAccess::getLFData::Exception> " + ex);
            ex.printStackTrace();
            return null;
        }
    }
    */

    // ********************************************************
    // ******                    Colors                   *****    
    // ********************************************************

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



/*
    public Object[] getAvailableLanguages()
    {
    	return new Object[] = { "en" };;
    }


    public int getSelectedLangIndex()
    {
    	return 1;
        //return m_lang_info.findInLocale(this.m_config_file.selected_lang);
    }


    public static String getSelectedLocale()
    {
        String locale = "en";
        
        

        try
        {
            Properties props = new Properties();

            FileInputStream in = new FileInputStream(pathPrefix  + "../data/PIS_Config.properties");
            props.load(in);

            int sel_lang = 1;

            if (props.containsKey("SELECTED_LANG"))
            {
                sel_lang = Integer.parseInt((String)props.get("SELECTED_LANG"));
                System.out.println("Sel lang: " + sel_lang);
            }


            //props = new Properties();
            props.clear();

            in = null;
            in = new FileInputStream(pathPrefix  + "/data/lang/PIS_Languages.properties");
            props.load(in);

            if (props.containsKey("LANG_" + sel_lang + "_LOCALE"))
            {
                locale = (String)props.get("LANG_" + sel_lang + "_LOCALE");
            }

//            System.out.println("Locale: " + locale);

        }
        catch(Exception ex)
        {
            System.out.println("DataAccess::getSelectedLocale::Exception> " + ex);
        }

        return locale;

    }
*/







    // ********************************************************
    // ******            Config File Handling             *****    
    // ********************************************************


    /*

    public String[] getAvailableDbs()
    {
        //this.m_config_file.
        //return allDbs;
        return this.m_config_file.getAllDatabasesNamesAsArray();
    }


    public int getSelectedDbIndex()
    {

        return this.m_config_file.getSelectedDatabaseIndex();
/*
        for (int i=0; i<allDbs.length; i++)
        {
            if (allDbs[i].startsWith(this.selected_db + " - "))
                return i;
        }
        return 0; */
//    }








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












    /*
    public void loadComboOptions()
    {

    yes_no_combo = new Object[2];
    
    yes_no_combo[0] = m_i18n.getMessage("OPTION_YES");
    yes_no_combo[1] = m_i18n.getMessage("OPTION_NO");

    Hashtable ht = m_db.getProductType(-1);

    typesAll = new Object[ht.size()];

    int i = 0;


    for(Enumeration en=ht.keys(); en.hasMoreElements(); )
    {

        String key = (String)en.nextElement();

        String key2 = "";

        if (key.length()==1)
        {
        key2 = "0"+key;
        }
        else
        key2 = key;



        typesAll[i] = key2 + " - " + ((ProductType)ht.get(key)).path.substring(1);
        i++;

    }

    Arrays.sort(typesAll);




    }
      */





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

    public String getDateString(int date)
    {

        // 20051012

        int year = date/10000;
        int month = date - (year*10000);

        month = month/100;

        int day = date - (year*10000) - (month*100);

        if (year==0)
        {
            return getLeadingZero(day,2) + "/" + getLeadingZero(month,2);
        }
        else
            return getLeadingZero(day,2) + "/" + getLeadingZero(month,2) + "/" + year;

    }





    public String getTimeString(int time)
    {

        int hours = time/100;

        int min = time - hours*100;

        return getLeadingZero(hours,2) + ":" + getLeadingZero(min,2);

    }

    public String getDateTimeString(long date)
    {
        return getDateTimeString(date, 1);
    }


    public String getDateTimeAsDateString(long date)
    {
        return getDateTimeString(date, 2);
    }



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



    public String getDateTimeAsTimeString(long date)
    {
        return getDateTimeString(date, 3);
    }


    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)




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


/*
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

	int min = (int)dt;

//	GregorianCalendar gc1 = new GregorianCalendar();
	//gc1.set(GregorianCalendar.

	return null;

    }
*/




    public String getDateTimeString(int date, int time)
    {

        return getDateString(date)+" " + getTimeString(time);

    }



    public String getLeadingZero(int number, int places)
    {

        String nn = ""+number;

        while (nn.length()<places)
        {
            nn = "0"+nn;
        }

        return nn;

    }


    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }


    public Object[] getGenderCombo()
    {
        return gender;

    }





/*
    public static final int USER_NORMAL = 1;
    public static final int USER_WORKER = 2;
    public static final int USER_ADMINISTRATOR = 3;
    public static final int USER_SUPERADMINISTRATOR = 4;

    public int user_type = 3;
*/

    public String[] userTypes = {
        m_i18n.getMessage("SELECT"),
        m_i18n.getMessage("USER_NORMAL"),
        m_i18n.getMessage("USER_WORKER"),
        m_i18n.getMessage("USER_ADMINISTRATOR"),
        m_i18n.getMessage("USER_SUPERADMIN"),
    };


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

    public static void notImplemented(String source)
    {
	System.out.println("Not Implemented: " + source);
	//JOptionPane.showMessageDialog(parent, "Not Implemented: \n" + source);
    }


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


    
    


    public static boolean isFound(String text, String search_str)
    {

        if ((search_str.trim().length()==0) || (text.trim().length()==0))
            return true;


        return text.trim().indexOf(search_str.trim()) != -1;
    }







/*
    public static void main(String args[])
    {


        System.out.println("DT: 20051012");


        DataAccess da = DataAccess.getInstance(null);

        System.out.println(da.getDateString(20051012));

    }
*/


    /**
     * Load Graph Config Properties
     * 
     * @see com.atech.utils.ATDataAccessAbstract#loadGraphConfigProperties()
     */
    public void loadGraphConfigProperties()
    {
        
    }




    @Override
    public void loadSpecialParameters()
    {
        // TODO Auto-generated method stub
        
    }



}

