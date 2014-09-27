package com.atech.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.info.LanguageInfo;

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

/**
 *  This is abstract class for controling I18N. You need to extend this class, and set all variables. With setting 
 *  of variables half of work is done. Next half is way to create this class. You need to make constructor. Sample
 *  constructor for Singelton is in this source file.
 */
public class I18nControlLI extends I18nControlAbstract
{

    @SuppressWarnings("unused")
    private static Log s_logger = LogFactory.getLog(I18nControlLI.class);
    // private Collator langaugeCollator = null;

    /**
     * 
     */
    public static I18nControlLI s_i18n = null;

    LanguageInfo m_lang_info = null;

    // static private I18nControl m_i18n = null; // This is handle to unique
    // singelton instance

    // Constructor: I18nControl
    /**
     *
     *  This is I18nControl constructor; Since classes use Singleton Pattern,
     *  constructor is protected and can be accessed only with getInstance() 
     *  method. 
     *  This constructor should be implemented by implementing class<br><br>
     *
     */
    /*
     * private I18nControlAbstract()
     * {
     * setLanguage("EN");
     * }
     */

    // Method: getInstance
    // Author: Andy
    /**
     *
     *  This method returns reference to OmniI18nControl object created, or if no 
     *  object was created yet, it creates one.<br><br>
     *  This method should be implemented by implementing class, if we want to use singelton<br><br>
     *
     *  @return Reference to OmniI18nControl object
     * 
     */
    public static I18nControlLI getInstance()
    {
        if (s_i18n == null)
        {
            s_i18n = new I18nControlLI();
        }
        return s_i18n;
    }

    // Method: deleteInstance
    /**
     *
     *  This method sets handle to OmniI18NControl to null and deletes the instance. <br><br>
     *
     */
    /*
     * public void deleteInstance()
     * {
     * m_i18n=null;
     * }
     */

    // Method: setLanguage (String language)
    /**
     *
     *  This is helper method for setting language.<br><br>
     *
     *  @param language language which we want to use
     */
    @Override
    public void setLanguage(String language)
    {
        this.m_lang_info.setSelectedLanguage(language);

        // Locale l = new Locale(language);
        // selected_language = language;
        setLanguage(m_lang_info.getSelectedLanguageLocale());
        createCollationDefintion();
    }

    /**
     * Gets the selected langauge.
     * 
     * @return the selected langauge
     */
    public String getSelectedLangauge()
    {
        return this.m_lang_info.getSelectedLanguage();
    }

    // Method: setLanguage (String language, String country)
    /**
     *
     *  This is helper method for setting language.<br><br>
     *
     *  @param language language which we want to use
     *  @param country country that uses this language
     */
    @Override
    public void setLanguage(String language, String country)
    {
        // System.out.println("setLanguage(String lang, String cnt): " +
        // language + ", " + country);

        Locale l = new Locale(language, country);
        selected_language = language;
        setLanguage(l);
    }

    // Method: setLanguage (Locale)
    /**
     *
     *  This method sets language for control instance. If none is found, english is defaulted.
     *  if none is found, application will exit.<br><br>
     *
     *  @param lcl locale that will choose which language will be set
     */
    @Override
    public void setLanguage(Locale lcl)
    {
        // System.out.println("setLanguage(Locale): " + lcl);

        try
        {
            // System.out.println("setLang: " + lang_file_root);
            res = ResourceBundle.getBundle(lang_file_root, lcl);

            /*
             * if (selected_language ==null)
             * {
             * selected_language = lcl.getCountry();
             * }
             */
        }
        catch (MissingResourceException mre)
        {
            System.out.println("Couldn't find resource file(1): " + lang_file_root + "." + selected_language
                    + ".properties");
            try
            {
                res = ResourceBundle.getBundle(lang_file_root, new Locale(this.def_language));
            }
            catch (MissingResourceException ex)
            {
                System.out.println("Exception on reading resource file. Exiting application.");
                // System.exit(2);
            }
        }
        catch (Exception mre)
        {
            System.out.println("Exception on reading resource file. Exiting application.");
            System.out.println("Exception: " + mre);
            mre.printStackTrace();
            System.exit(2);
        }

        this.selected_language_locale = lcl;

    }

    /**
     * Gets the selected language locale.
     * 
     * @return the selected language locale
     */
    @Override
    public Locale getSelectedLanguageLocale()
    {
        return this.selected_language_locale;
    }

    @Override
    protected String getLanguageConfigFile()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init()
    {
        // TODO Auto-generated method stub

    }

}
