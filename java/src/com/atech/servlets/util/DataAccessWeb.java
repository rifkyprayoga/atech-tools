package com.atech.servlets.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;

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


public class DataAccessWeb
{

    // Config file
    Hashtable<String,String> config_db_values = null;
    public int selected_db = -1;
    public int selected_lang = 1;
    public String selected_LF_Class = null; // class
    public String selected_LF_Name = null; // name
    public String skinLFSelected = null;
    String allDbs[] = null;

    public boolean m_demo = false;

    public Hashtable<String,String> m_settings = null;

    public static String pathPrefix = ".";

//    public UserDat reg_info = null;

    public Color color_background, color_foreground;


    Properties zupnija;

//    public I18nWebControl m_i18n = new I18nWebControl("SI");

    static private DataAccessWeb m_da = null;   // This is handle to unique 
                                             // singelton instance
// YYY
    //public PISDb m_db = null;
    //public PISMain m_main = null;



    public Object[] yes_no_combo = null;

//x    public Hashtable typesHT = new Hashtable();

    public Object[] typesAll = null;


    public int loginType = -4;


/*
    public String gender_minus[] = { 
        m_i18n.getMessage("GENDER_M"),             // 1
        m_i18n.getMessage("GENDER_F")
    };


    public String gender[] = { m_i18n.getMessage("SELECT"),
        m_i18n.getMessage("GENDER_M"),             // 1
        m_i18n.getMessage("GENDER_F")
    };
*/


    public String months[] = null;


/*
        public String months[] = { 
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
    private DataAccessWeb(Hashtable<String,I18nWebControl> i18ns, String lang)
    {
        
        //m_db = db;
        //reg_info = new UserDat();

        //loadConfig();
        //loadFonts();
        //loadAvailableLFs();
        //loadLanguageInfo();
        //loadColors();

        m_settings = new Hashtable<String,String>();
  //      loadDioceseRoot();

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
    static public DataAccessWeb getInstance()
    {
        //if (m_da == null)
        //    m_da = new DataAccessWeb();
        return m_da;
    }



    static public DataAccessWeb createInstance(Hashtable<String,I18nWebControl> i18ns, String lang)
    {
        m_da = new DataAccessWeb(i18ns, lang);
        return m_da;
    }
    
    
    
    







    //  Method:       deleteInstance
    /**
     *  This method sets handle to DataAccess to null and deletes the instance. <br><br>
     */ 
    public void deleteInstance()
    {

        //m_i18n=null;

    }



/*
    public void loadDioceseRoot()
    {
        m_dio_treeroot = new DioceseTreeRoot(1);
    }
*/



    I18nWebControl ic = null; 

    public void setI18nWebControl(I18nWebControl ic)
    {
	this.ic = ic;
    }

    public I18nWebControl getI18nWebControl()
    {
	return this.ic;
    }



// -----------------------

    public void setSettings(Hashtable<String,String> sett)
    {
        this.m_settings = sett;
    }

    public Hashtable<String,String> getSettings()
    {
        return this.m_settings;
    }

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
*/

/*
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
*/








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


    public boolean isDemoVersion()
    {
        return m_demo;
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


    public String getDateTimeAsTimeString(long date)
    {
        return getDateTimeString(date, 3);
    }


    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)

    public final static int DT_DATETIME = 1;
    public final static int DT_DATE = 2;
    public final static int DT_TIME = 3;



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

/*
    public Object[] getGenderCombo()
    {
        return gender;

    }
*/





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


    public ArrayList<String> getSelectedParishesIDs()
    {

        ArrayList<String> l = new ArrayList<String>();

        l.add("62");
        l.add("63");

        return l;

    }



    public static final int DATE_TIME_ATECH_DATETIME = 1;
    public static final int DATE_TIME_ATECH_DATE = 2;
    public static final int DATE_TIME_ATECH_TIME = 3;

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






    public static boolean isFound(String text, String search_str)
    {

        if ((search_str.trim().length()==0) || (text.trim().length()==0))
            return true;

        return text.trim().indexOf(search_str.trim()) != -1;
    }




}

