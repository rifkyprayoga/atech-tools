package com.atech.utils;


import com.atech.i18n.I18nControlAbstract;


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
 *  This is abstract class for controling I18N. You need to extend this class, and set all variables. With setting 
 *  of variables half of work is done. Next half is way to create this class. You need to make constructor. Sample
 *  constructor for Singelton is in this source file.
 */
public class ATI18nControl extends I18nControlAbstract
{

    //private static Log s_logger = LogFactory.getLog(ATI18nControl.class); 



    //protected String languages[] = null;
/*
    }
	{
        "English",
        "German",
        "Slovene",
        "Simp. Chinese"
    };
*/

    //protected Locale lcls[] = null;
/*	{
        Locale.ENGLISH,
        Locale.GERMAN,
        new Locale("SI"),
        Locale.SIMPLIFIED_CHINESE
    };
*/

    //private Collator langaugeCollator = null;

    static private ATI18nControl m_i18n = null;   // This is handle to unique 
                                                    // singelton instance
                                               

    //   Constructor:  I18nControl
    /**
     *
     *  This is I18nControl constructor; Since classes use Singleton Pattern,
     *  constructor is protected and can be accessed only with getInstance() 
     *  method. 
     *  This constructor should be implemented by implementing class<br><br>
     *
     */ 
    private ATI18nControl()
    {
        init();
        getSelectedLanguage();
        setLanguage();


//        setLanguage("EN");
    } 
    


    public void init()
    {
        def_language = "en";
        lang_file_root = "AtechTools";
    }


    
    //  Method:       getInstance
    //  Author:       Andy
    /**
     *
     *  This method returns reference to OmniI18nControl object created, or if no 
     *  object was created yet, it creates one.<br><br>
     *  This method should be implemented by implementing class, if we want to use singelton<br><br>
     *
     *  @return Reference to OmniI18nControl object
     * 
     */ 
    static public ATI18nControl getInstance()
    {
        if (m_i18n == null)
            m_i18n = new ATI18nControl();
        return m_i18n;
    }






    //  Method:       deleteInstance
    /**
     *
     *  This method sets handle to OmniI18NControl to null and deletes the instance. <br><br>
     *
     */ 
    public void deleteInstance()
    {
        m_i18n=null;
    }



    private void getSelectedLanguage()
    {
        this.selected_language = this.def_language;
/*
        try
        {
            Properties props = new Properties();

            FileInputStream in = new FileInputStream("../data/GGC_Config.properties");
            props.load(in);

            String tempLang = (String)props.get("SELECTED_LANG");

            if (tempLang != null)
            this.selected_language = tempLang;
        }
        catch(Exception ex)
        {
            System.out.println("I18nControl: Configuration file not found. Using default langauge ('en')");
            s_logger.warn("Configuration file not found. Using default langauge ('en')");
        }
*/
    }



    /**
     * This method sets the language according to the preferences.<br>
     */
    public void setLanguage() 
    {
        if (selected_language!=null)
            setLanguage(selected_language);
        else
            setLanguage(def_language);
    }



}


