package com.atech.i18n.mgr;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.I18nControlLangMgr;
import com.atech.i18n.I18nControlRunner;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.file.PropertiesFile;

/**
 *  This file is part of ATech Tools library.
 *  
 *  LanguageManager - 
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


public class LanguageManager
{

    //private I18nControlAbstract m_i18n = null; //I18nControl.getInstance();
    private static Log log = LogFactory.getLog(LanguageManager.class);

    /**
     * The lp_version.
     */
    public String lp_version = null;
    
    /**
     * The lp_release.
     */
    public String lp_release = null;
    
    /**
     * The lp_langs_with_help.
     */
    public int lp_langs_with_help = 0;
    
    /**
     * The lp_langs_without_help.
     */
    public int lp_langs_without_help = 0;
    
    /**
     * The langs.
     */
    public String langs;

    
    /**
     * The lang_root.
     */
    public String lang_root;
    
    
    /**
     * The default_help.
     */
    public String default_help = null;

    /**
     * The available lang.
     */
    public Object availableLang[] = null;
    
    /**
     * The available lang web.
     */
    public Object availableLangWeb[] = null;
    
    /**
     * The available lang locale.
     */
    public String availableLangLocale[] = null;
    //public String 

    /**
     * The langs_ht.
     */
    Hashtable<String,LanguageInstance> langs_ht = null;

    /**
     * The available real locale.
     */
    public Locale[] availableRealLocale = null;
    
    
    
    
    
    
    private String selected_language = null;
    
    private Hashtable<String, LanguageInstance> available_languages;
    
    
    private LanguageManagerRunner language_manager_runnner = null;
    
    
    /**
     * Instantiates a new language manager.
     * 
     * @param lmr 
     */
    public LanguageManager(LanguageManagerRunner lmr)
    {
        this.language_manager_runnner = lmr;
        loadData();
        loadSelectedLanguageData();
        this.loadStaticData();
    }
    

    /**
     * Load data.
     */
    public void loadData()
    {

        PropertiesFile props = new PropertiesFile(this.language_manager_runnner.getLangaugeConfigFile());
        
        /*
        Properties props = null;
        try
        {
            props = new Properties();

            //InputStream in = getClass().getResourceAsStream("/PIS_Languages.properties");
            
            InputStream in = getClass().getResourceAsStream(this.getLangaugeConfigFile());
            
                //new FileInputStream("PIS_Languages.properties");
            props.load(in);
        }
        catch(Exception ex)
        {
            System.out.println("LanguageInfo::loadData::Exception> " + ex);
        }
        */
        
//        System.out.println("LoadData: " + this.language_manager_runnner.getLangaugeConfigFile());
        
        
        
        if (!props.wasFileRead())
        {
            log.error("LanguageManager: Configuration file " + this.language_manager_runnner.getLangaugeConfigFile() + " was NOT read !");
        }

       
        
        int le = Integer.parseInt((String)props.get("AVAILABLE_LANGUAGES"));

        availableLang = new Object[le];
        availableLangWeb = new Object[le];
        availableLangLocale = new String[le];

        this.available_languages = new Hashtable<String,LanguageInstance>();
        
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body>");

        this.langs_ht = new Hashtable<String,LanguageInstance>();

        for (int i=1; i<=le; i++ )
        {

            LanguageInstance li = new LanguageInstance();

            String name = (String)props.get("LANG_" + i + "_NAME");
            String help = (String)props.get("LANG_" + i + "_HELP_AVAILABLE");

            //li.name = name;
            
            li.name_description = name;
            li.name = (String)props.get("LANG_" + i);


            if (i!=1)
                sb.append(", ");

            if ((help.equalsIgnoreCase("YES")) || (help.equalsIgnoreCase("TRUE")))
            {
// y               availableLang[i-1] = name + " (" + m_i18n.getMessage("HELP_AVAILABLE") + ")";
// y               sb.append(name);
                li.help_available = true;
            }
            else
            {
// y               String tm = " (" + m_i18n.getMessage("NO_HELP_AVAILABLE")+ ")";
// y               availableLang[i-1] = name + tm;
// y               sb.append(name);
// y               sb.append(tm);
                li.help_available = false;
            }

// y           availableLangWeb[i-1] = name;
// y           availableLangLocale[i-1] = ((String)props.get("LANG_" + i + "_LOCALE")).toLowerCase();

            //li.locale = ((String)props.get("LANG_" + i + "_LOCALE")).toLowerCase();
            //li.file

            //this.langs_ht.put(li.locale, li);
            this.available_languages.put(li.name, li);
        }

//        sb.append("</html></body>");

        langs = sb.toString();

        lp_version = (String)props.get("VERSION");
        lp_release = (String)props.get("LANGPACK_DATE");

        this.default_help = (String)props.get("DEFAULT_HELP");

        this.lang_root = (String)props.get("LANGUAGE_ROOT");
        
        int l_help = Integer.parseInt((String)props.get("AVAILABLE_LANGUAGES_WITH_HELP"));

//        System.out.println(le + " " + l_help);

        lp_langs_with_help = l_help;
        lp_langs_without_help = le - l_help;

        if (this.language_manager_runnner.isTranslationToolLanguageSupported())
            this.loadTranslationData();
        
//        System.out.println("Available: " + this.available_languages);
        
    }

    private void loadSelectedLanguageData()
    {
        PropertiesFile props = new PropertiesFile(this.language_manager_runnner.getLanguageSelectionConfigFile());
        
        
        if (!props.wasFileRead())
        {
            System.out.println("LanguageManager: Configuration file for selected language: " + this.language_manager_runnner.getLanguageSelectionConfigFile() + " was NOT read !");
        }
        else
        {
            this.selected_language = props.get("SELECTED_LANG");
        }
        
        System.out.println("!!!!! " + this.selected_language);
        
    }
    
    
    
    private void loadTranslationData()
    {
        PropertiesFile pf = new PropertiesFile(this.language_manager_runnner.getTranslationToolConfigFile());
        
//        System.out.println("loadTranslationData: " + pf.wasFileRead());
        
        if (!ATDataAccessAbstract.isOptionEnabled(pf.get("LANGUAGE_TOOL_ENABLED")))
            return;
        
        LanguageInstance li = new LanguageInstance();
        
        li.name = pf.get("LANGUAGE_SHORT");
        li.name_description = pf.get("LANGUAGE_NAME");
        li.help_available = ATDataAccessAbstract.isOptionEnabled(pf.get("LANGUAGE_HELP_ENABLED"));
        li.is_translation_tool = true;
        li.language_path = pf.get("LANGUAGE_PATH");

        this.available_languages.put("lt", li);
        
    }
    
    
    
    
    
    /**
     * Find language in locale list.
     * 
     * @param loc the loc
     * 
     * @return the int
     */
    public int findInLocale(String loc)
    {
        loc = loc.toLowerCase();

        if (availableLangLocale!= null)
        {
            for (int i=0; i<availableLangLocale.length; i++)
            {
                if (availableLangLocale[i].toLowerCase().equals(loc))
                {
                    return (i+1);
                }
            }
        }

        return 0;
    }

    
    /**
     * Get help path for naguage.
     * 
     * @param lang the lang
     * 
     * @return the help path for language
     */
    public String getHelpPathForLanguage(String lang)
    {
/*        if (new File("Eclipse_Enviroment.txt").exists())
        {
            return this.getHelpPathForLanguageEclipse(lang);
        }
        else */
            return getHelpPathForLanguageJar(lang);
    }
    

    
    @SuppressWarnings("unused")
    private String getHelpPathForLanguageEclipse(String lang)
    {
        if (this.langs_ht.containsKey(lang))
        {
            if (this.langs_ht.get(lang).help_available)
            {
                return "../data/lang/" + lang + "/" + getHelpSetName();
            }
            else
            {
                return "../data/lang/" + this.default_help + "/" + getHelpSetName();
            }
        }
        else
        {
            //System.out.println("Error this language is not present !!! " + lang);
            return "../data/lang/" + this.default_help + "/" + getHelpSetName();
//            return "jar:file:" + getLanguageJarName() + "!/help/" + this.default_help + "/PIS.hs";
        }
    }
    
    
    
    private String getHelpPathForLanguageJar(String lang)
    {
        if (this.langs_ht.containsKey(lang))
        {
            if (this.langs_ht.get(lang).help_available)
            {
                return "jar:file:" + getLanguageJarName() + "!/help/" + lang + "/" + getHelpSetName();
            }
            else
            {
                return "jar:file:" + getLanguageJarName() + "!/help/" + this.default_help + "/" + getHelpSetName();
            }
        }
        else
        {
            //System.out.println("Error this language is not present !!! " + lang);
            return "jar:file:" + getLanguageJarName() + "!/help/" + this.default_help + "/" + getHelpSetName();
        }
    }


    // jar:file:pis_lang-0.1.jar!/help/PIS.hs

    private String getLanguageJarName()
    {
        return getJarBaseName() + "-" + this.lp_version + ".jar";
    }

    /**
     * Gets the jar base name.
     * 
     * @return the jar base name
     */
    public String getJarBaseName()
    {
        return null;
    }
    
    
    /**
     * Gets the help set name.
     * 
     * @return the help set name
     */
    public String getHelpSetName()
    {
        return null;
    }
    
    


    
    /**
     * Gets the selected language.
     * 
     * @return the selected language
     */
    public String getSelectedLanguage()
    {
        return this.selected_language;
    }
    
    /**
     * Sets the selected language.
     * 
     * @param lang the new selected language
     */
    public void setSelectedLanguage(String lang)
    {
        this.selected_language = lang;
    }
    

    /**
     * Get Selected Language Instance
     * 
     * @return
     */
    public LanguageInstance getSelectedLanguageInstance()
    {
        return this.available_languages.get(this.selected_language);
    }
    
    
    //public 
    
    /**
     * Gets the selected language locale.
     * 
     * @return the selected language locale
     */
    public Locale getSelectedLanguageLocale()
    {
        return this.available_languages.get(this.selected_language).locale;
    }


    /**
     * Gets the language root.
     * 
     * @return the language root
     */
/*    public String getLanguageRoot()
    {
        return this.lang_root;
    }
  */  

    
    public String getDefaultLanguage()
    {
        return this.language_manager_runnner.getDefaultLanguage();
    }

    
    
    
    /**
     * Get I18nControl (main)
     * 
     * @param icr 
     * 
     * @return
     */
    public I18nControlLangMgr getI18nControl(I18nControlRunner icr)
    {
        return new I18nControlLangMgr(this, icr);
        
    }

    
    public String[] av_lang_array = null;
    int av_lang_array_selected = 0;
    
    public void loadStaticData()
    {
        
        av_lang_array = new String[this.available_languages.size()];
        int i=0;
        String sel_name = this.available_languages.get(this.selected_language).name;
        
        for(Enumeration<String> en = this.available_languages.keys(); en.hasMoreElements(); )
        {
            LanguageInstance li = this.available_languages.get(en.nextElement());
            this.av_lang_array[i]= li.name_description;
            
            if (li.name.equals(sel_name))
                this.av_lang_array_selected = i;
            
            i++;
        }
    }
    
    
    
    public String[] getAvailableLanguages()
    {
        return this.av_lang_array;
    }

    
    public int getSelectedLanguageFromArray()
    {
        return this.av_lang_array_selected;
    }
    
    
    public String getLanguageConfigFile()
    {
        return this.language_manager_runnner.getTranslationToolConfigFile();
    }
    
    
    public String getNameFromDescription(String desc)
    {
        for(Enumeration<LanguageInstance> en = this.available_languages.elements(); en.hasMoreElements(); )
        {
            LanguageInstance li = en.nextElement();
            
            if (li.name_description.equals(desc))
                return li.name;
        }
        
        return null;
        
    }
    
    
    public String getDefaultHelp()
    {
        return this.default_help;
    }
    
    
    public String getHelpSet()
    {
        if (this.selected_language.equals("en"))
        {
            return "help/GGC.hs";
        }
        else
        {
            System.out.println("Selected lan: " + this.selected_language);
            
            LanguageInstance li = this.available_languages.get(this.selected_language);

            System.out.println("Selected LI: " + li);
            
            
            if (li.help_available)
                return "help/GGC_" + this.selected_language + ".hs";
            else
                return "help/GGC.hs";
        }
    }
    
    
    
    
}



