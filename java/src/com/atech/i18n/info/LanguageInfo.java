package com.atech.i18n.info;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;

import com.atech.i18n.I18nControlAbstract;

/**
 *  Parish Information System (PIS)
 *  ===============================
 * 
 *  Name:          LanguageInfo
 *  Description:   This class reads Language information and helps with creation of Help and usage of corrent
 *                 language.
 *  Copyright:     Copyright (c) 2005-2008 by Andy (ATech Software Ltd.)
 *  
 *  This class is part of PIS/ZIS (Parish Information System) software. PIS is termed as proprietary
 *  (non-free) software and it's use must abide by rules of Licence, which is distributed with 
 *  this software and also displayed within install software. 
 * 
 *  @author      Andy (Aleksander) Rozman {andy@atech-software.com}
 *  @version     1.0
 */


public abstract class LanguageInfo
{

    private I18nControlAbstract m_i18n = null; //I18nControl.getInstance();

    /**
     * 
     */
    public String lp_version = null;
    /**
     * 
     */
    public String lp_release = null;
    /**
     * 
     */
    public int lp_langs_with_help = 0;
    /**
     * 
     */
    public int lp_langs_without_help = 0;
    /**
     * 
     */
    public String langs;

    /**
     * 
     */
    public String default_help = null;

    /**
     * 
     */
    public Object availableLang[] = null;
    /**
     * 
     */
    public Object availableLangWeb[] = null;
    /**
     * 
     */
    public String availableLangLocale[] = null;
    //public String 

    Hashtable<String,LanguageInstance> langs_ht = null;

    public Locale[] availableRealLocale = null;
    
    
    public LanguageInfo(I18nControlAbstract ic)
    {
        this.m_i18n =  ic;
    }
    

    /**
     * Load data
     */
    public void loadData()
    {

        Properties props = null;
        try
        {
            props = new Properties();

            InputStream in = getClass().getResourceAsStream("/PIS_Languages.properties");
                //new FileInputStream("PIS_Languages.properties");
            props.load(in);
        }
        catch(Exception ex)
        {
            System.out.println("LanguageInfo::loadData::Exception> " + ex);
        }



        int le = Integer.parseInt((String)props.get("AVAILABLE_LANGUAGES"));

        availableLang = new Object[le];
        availableLangWeb = new Object[le];
        availableLangLocale = new String[le];

        StringBuffer sb = new StringBuffer();
        sb.append("<html><body>");

        this.langs_ht = new Hashtable<String,LanguageInstance>();

        for (int i=1; i<=le; i++ )
        {

            LanguageInstance li = new LanguageInstance();

            String name = (String)props.get("LANG_" + i + "_NAME");
            String help = (String)props.get("LANG_" + i + "_HELP_AVAILABLE");

            li.name = name;


            if (i!=1)
                sb.append(", ");

            if ((help.equalsIgnoreCase("YES")) || (help.equalsIgnoreCase("TRUE")))
            {
                availableLang[i-1] = name + " (" + m_i18n.getMessage("HELP_AVAILABLE") + ")";
                sb.append(name);
                li.help_available = true;
            }
            else
            {
                String tm = " (" + m_i18n.getMessage("NO_HELP_AVAILABLE")+ ")";
                availableLang[i-1] = name + tm;
                sb.append(name);
                sb.append(tm);
                li.help_available = false;
            }

            availableLangWeb[i-1] = name;
            availableLangLocale[i-1] = ((String)props.get("LANG_" + i + "_LOCALE")).toLowerCase();

            li.locale = ((String)props.get("LANG_" + i + "_LOCALE")).toLowerCase();
            //li.file

            this.langs_ht.put(li.locale, li);
        }

        sb.append("</html></body>");

        langs = sb.toString();

        lp_version = (String)props.get("VERSION");
        lp_release = (String)props.get("LANGPACK_DATE");

        this.default_help = (String)props.get("DEFAULT_HELP");

        int l_help = Integer.parseInt((String)props.get("AVAILABLE_LANGUAGES_WITH_HELP"));

        System.out.println(le + " " + l_help);

        lp_langs_with_help = l_help;
        lp_langs_without_help = le - l_help;


    }

    /**
     * Find language in locale list
     * @param loc
     * @return
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
     * Get help path for naguage
     * @param lang
     * @return
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

    public abstract String getJarBaseName();
    
    
    public abstract String getHelpSetName();
    
    
    public abstract String getLangaugeConfigFile();

    
    private class LanguageInstance
    {
        public String name = "";
        public boolean help_available = false;
        public String file = "";
        public String locale = "";
        
        public String locale_kk = "";
        
        /*
LANG_1_NAME=Slovenski
LANG_1_HELP_AVAILABLE=No
LANG_1_HELP=Yes
LANG_1_FILE=PIS_si.properties
LANG_1_LOCALE=si
*/


    }




}


