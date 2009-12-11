package com.atech.i18n.tool.simple.util;

import java.util.Hashtable;

import com.atech.i18n.I18nControlAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  I18nControlTT - for translation (this one is fake)
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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

public class I18nControlTT extends I18nControlAbstract
{

    //x private static Log s_logger = LogFactory.getLog(I18nControlDbT.class); 



    //protected String lang_file_root = "DbTool";
    //protected String def_language = "en";
    //protected String selected_language = null;


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


    static private I18nControlTT m_i18n = null;   // This is handle to unique 
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
    private I18nControlTT()
    {
        init();
        getSelectedLanguage();
        setLanguage();

        loadWords();
//        setLanguage("EN");
    } 
    


    /**
     * 
     */
    public void init()
    {
        def_language = "en";
        lang_file_root = "TranslationTool";
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
    static public I18nControlTT getInstance()
    {
        if (m_i18n == null)
            m_i18n = new I18nControlTT();
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


/*
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
  //  }



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



    @Override
    protected String getLanguageConfigFile()
    {
        // TODO Auto-generated method stub
        return null;
    }


    Hashtable<String, String> words = new Hashtable<String, String>(); 
    
    
    private void loadWords()
    {
        this.words.put("LICENCE", "Licence");
        this.words.put("ABOUT", "About...");
        this.words.put("SYSTEM_PROPERTIES", "System properties");
        
        this.words.put("PROPERTY", "Property");
        this.words.put("VALUE", "Value");
        this.words.put("ABOUT", "About...");
        this.words.put("ABOUT", "About...");
        this.words.put("ABOUT", "About...");
        
        
    }

    
    public String getMessage(String key)
    {
        if (this.words.containsKey(key))
            return this.words.get(key);
        else
            return key.toUpperCase();
    }
    
    


}


