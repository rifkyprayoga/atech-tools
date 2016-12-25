package com.atech.i18n.mgr;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.i18n.I18nControlLangMgr;
import com.atech.i18n.I18nControlLangMgrDual;
import com.atech.i18n.I18nControlRunner;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATechToolsRuntimeException;
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

    private static final Logger LOG = LoggerFactory.getLogger(LanguageManager.class);

    public String languagePackVersion = null;
    public String languagePackRelease = null;
    public int languagePackLanguagesWithHelp = 0;
    public int languagePackLanguagesWithoutHelp = 0;
    public String languageManagerDataHTML;
    public String languageRoot;
    public String defaultHelp = null;
    public boolean findUntranslatedKeys = false;
    private String selectedLanguage = null;
    private Hashtable<String, LanguageInstance> availableLanguages;
    private LanguageManagerRunner languageManagerRunner = null;


    /**
     * Instantiates a new language manager.
     * 
     * @param lmr 
     */
    public LanguageManager(LanguageManagerRunner lmr)
    {
        this.languageManagerRunner = lmr;
        loadData();
        loadSelectedLanguageData();
        this.loadStaticData();
    }


    /**
     * Load data.
     */
    public void loadData()
    {
        PropertiesFile props = new PropertiesFile(this.languageManagerRunner.getLanguageConfigFile());

        // System.out.println(this.languageManagerRunner.getLanguageConfigFile());

        // File uu = new File(".");
        // System.out.println("ii" + uu.getAbsolutePath());

        if (!props.wasFileRead())
        {
            String errorMsg = "LanguageManager: Configuration file "
                    + this.languageManagerRunner.getLanguageConfigFile() + " was NOT read !";
            LOG.error(errorMsg);
            throw new ATechToolsRuntimeException(errorMsg);
        }

        int le = Integer.parseInt(props.get("AVAILABLE_LANGUAGES"));

        this.availableLanguages = new Hashtable<String, LanguageInstance>();

        StringBuffer sb = new StringBuffer();
        sb.append("<html><body>");

        for (int i = 1; i <= le; i++)
        {

            LanguageInstance li = new LanguageInstance();

            String name = props.get("LANG_" + i + "_NAME");
            String help = props.get("LANG_" + i + "_HELP_AVAILABLE");
            String localeString = props.get("LANG_" + i + "_LOCALE");

            li.name_description = name;
            li.name = props.get("LANG_" + i);

            if (i != 1)
            {
                sb.append(", ");
            }

            if (help.equalsIgnoreCase("YES") || help.equalsIgnoreCase("TRUE"))
            {
                li.help_available = true;
            }
            else
            {
                li.help_available = false;
            }

            try
            {
                if (localeString != null)
                {
                    li.locale = LocaleUtils.toLocale(localeString);
                }
                else
                {
                    li.locale = Locale.getDefault();
                }
            }
            catch (Exception ex)
            {
                System.out.println("Error creating locale: (keyword=" + localeString + "). Ex.: " + ex);
            }

            // this.langs_ht.put(li.locale, li);
            this.availableLanguages.put(li.name, li);
        }

        // sb.append("</html></body>");

        languageManagerDataHTML = sb.toString();

        languagePackVersion = props.get("VERSION");
        languagePackRelease = props.get("LANGPACK_DATE");

        this.defaultHelp = props.get("DEFAULT_HELP");

        this.languageRoot = props.get("LANGUAGE_ROOT");

        int l_help = Integer.parseInt(props.get("AVAILABLE_LANGUAGES_WITH_HELP"));

        languagePackLanguagesWithHelp = l_help;
        languagePackLanguagesWithoutHelp = le - l_help;

        if (this.languageManagerRunner.isTranslationToolLanguageSupported())
        {
            this.loadTranslationData();
        }

        // System.out.println("Available: " + this.availableLanguages);

    }


    private void loadSelectedLanguageData()
    {
        PropertiesFile props = new PropertiesFile(this.languageManagerRunner.getLanguageSelectionConfigFile());

        if (!props.wasFileRead())
        {
            this.selectedLanguage = this.languageManagerRunner.getDefaultLanguage();

            LOG.warn("LanguageManager: Configuration file for selected language: "
                    + this.languageManagerRunner.getLanguageSelectionConfigFile()
                    + " was NOT read ! Using default language (" + this.selectedLanguage + ") !");
        }
        else
        {
            this.selectedLanguage = props.get("SELECTED_LANG");
        }

        // System.out.println("!!!!! " + this.selectedLanguage);

    }


    private void loadTranslationData()
    {
        PropertiesFile pf = new PropertiesFile(this.languageManagerRunner.getTranslationToolConfigFile());

        // System.out.println("loadTranslationData: " + pf.wasFileRead());

        if (!pf.wasFileRead())
            return;

        if (!ATDataAccessAbstract.isOptionEnabled(pf.get("LANGUAGE_TOOL_ENABLED")))
            return;

        LanguageInstance li = new LanguageInstance();

        li.name = pf.get("LANGUAGE_SHORT");
        li.name_description = pf.get("LANGUAGE_NAME");
        li.help_available = ATDataAccessAbstract.isOptionEnabled(pf.get("LANGUAGE_HELP_ENABLED"));
        li.is_translation_tool = true;
        li.language_path = pf.get("LANGUAGE_PATH");

        this.availableLanguages.put("lt", li);

    }


    /**
     * Find language in locale list.
     * 
     * @param loc the loc
     * 
     * @return the int
     */
    // public int findInLocale(String loc)
    // {
    // loc = loc.toLowerCase();
    //
    //
    //
    //
    //
    //
    // if (availableLangLocale != null)
    // {
    // for (int i = 0; i < availableLangLocale.length; i++)
    // {
    // if (availableLangLocale[i].toLowerCase().equals(loc))
    // return i + 1;
    // }
    // }
    //
    // return 0;
    // }

    /**
     * Get help path for naguage.
     * 
     * @param lang the lang
     * 
     * @return the help path for language
     */
    public String getHelpPathForLanguage(String lang)
    {
        /*
         * if (new File("Eclipse_Enviroment.txt").exists())
         * {
         * return this.getHelpPathForLanguageEclipse(lang);
         * }
         * else
         */
        return getHelpPathForLanguageJar(lang);
    }


    @SuppressWarnings("unused")
    private String getHelpPathForLanguageEclipse(String lang)
    {
        if (this.availableLanguages.containsKey(lang))
        {
            if (this.availableLanguages.get(lang).help_available)
                return "../data/lang/" + lang + "/" + getHelpSetName();
            else
                return "../data/lang/" + this.defaultHelp + "/" + getHelpSetName();
        }
        else
            // System.out.println("Error this language is not present !!! " +
            // lang);
            return "../data/lang/" + this.defaultHelp + "/" + getHelpSetName();
        // return "jar:file:" + getLanguageJarName() + "!/help/" +
        // this.defaultHelp + "/PIS.hs";
    }


    private String getHelpPathForLanguageJar(String lang)
    {
        if (this.availableLanguages.containsKey(lang))
        {
            if (this.availableLanguages.get(lang).help_available)
                return "jar:file:" + getLanguageJarName() + "!/help/" + lang + "/" + getHelpSetName();
            else
                return "jar:file:" + getLanguageJarName() + "!/help/" + this.defaultHelp + "/" + getHelpSetName();
        }
        else
            // System.out.println("Error this language is not present !!! " +
            // lang);
            return "jar:file:" + getLanguageJarName() + "!/help/" + this.defaultHelp + "/" + getHelpSetName();
    }


    // jar:file:pis_lang-0.1.jar!/help/PIS.hs

    private String getLanguageJarName()
    {
        return getJarBaseName() + "-" + this.languagePackVersion + ".jar";
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
        return this.selectedLanguage;
    }


    /**
     * Sets the selected language.
     * 
     * @param lang the new selected language
     */
    public void setSelectedLanguage(String lang)
    {
        this.selectedLanguage = lang;
    }


    /**
     * Get Selected Language Instance
     * 
     * @return
     */
    public LanguageInstance getSelectedLanguageInstance()
    {
        return this.availableLanguages.get(this.selectedLanguage);
    }


    // public

    /**
     * Gets the selected language locale.
     * 
     * @return the selected language locale
     */
    public Locale getSelectedLanguageLocale()
    {
        return this.availableLanguages.get(this.selectedLanguage).locale;
    }


    /**
     * Gets the language root.
     * 
     * @return the language root
     */
    /*
     * public String getLanguageRoot()
     * {
     * return this.languageRoot;
     * }
     */

    public String getDefaultLanguage()
    {
        return this.languageManagerRunner.getDefaultLanguage();
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
        if (this.languageManagerRunner instanceof LanguageManagerRunnerDual)
            return new I18nControlLangMgrDual(this, icr);
        else
            return new I18nControlLangMgr(this, icr);
    }

    public String[] av_lang_array = null;
    int av_lang_array_selected = 0;


    public void loadStaticData()
    {

        av_lang_array = new String[this.availableLanguages.size()];
        int i = 0;
        String sel_name = this.availableLanguages.get(this.selectedLanguage).name;

        for (Enumeration<String> en = this.availableLanguages.keys(); en.hasMoreElements();)
        {
            LanguageInstance li = this.availableLanguages.get(en.nextElement());
            this.av_lang_array[i] = li.name_description;

            if (li.name.equals(sel_name))
            {
                this.av_lang_array_selected = i;
            }

            i++;
        }

        if (this.languageManagerRunner instanceof LanguageManagerRunnerDual)
        {
            this.findUntranslatedKeys = ((LanguageManagerRunnerDual) this.languageManagerRunner)
                    .findUntranslatedKeysInLanguage();
        }
    }


    public boolean findUntraslatedKeys()
    {
        return this.findUntranslatedKeys;
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
        return this.languageManagerRunner.getTranslationToolConfigFile();
    }


    public String getNameFromDescription(String desc)
    {
        for (Enumeration<LanguageInstance> en = this.availableLanguages.elements(); en.hasMoreElements();)
        {
            LanguageInstance li = en.nextElement();

            if (li.name_description.equals(desc))
                return li.name;
        }

        return null;

    }


    public String getDefaultHelp()
    {
        return this.defaultHelp;
    }


    public String getHelpSet()
    {
        /*
         * if (this.selectedLanguage.equals("en"))
         * {
         * return "help/GGC.hs";
         * }
         * else
         * {
         * //System.out.println("Selected lan: " + this.selectedLanguage);
         * LanguageInstance li =
         * this.availableLanguages.get(this.selectedLanguage);
         * //System.out.println("Selected LI: " + li);
         * if (li.help_available)
         * return "help/GGC_" + this.selectedLanguage + ".hs";
         * else
         * return "help/GGC.hs";
         * }
         */

        // multi language support
        // we have following directory structure:
        // help
        // |- en
        // | |- GGC.hs
        // |- si
        // | |- GGC.hs
        //

        if (this.selectedLanguage.equals("en"))
            return "help/en/GGC.hs";
        else
        {
            // System.out.println("Selected lan: " + this.selectedLanguage);

            LanguageInstance li = this.availableLanguages.get(this.selectedLanguage);

            // System.out.println("Selected LI: " + li);

            if (li.help_available)
                return "help/" + this.selectedLanguage + "/GGC.hs";
            else
                return "help/en/GGC.hs";
        }

    }

}
