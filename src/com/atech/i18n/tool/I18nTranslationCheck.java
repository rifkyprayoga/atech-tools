package com.atech.i18n.tool;

import java.io.File;
import java.util.*;

import com.atech.utils.ATDataAccess;

/**
 * This file is part of ATech Tools library.
 * 
 * I18nTranslationCheck - check if translation is complete (we check that translation is not same as source)
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 * 
 */

public class I18nTranslationCheck
{

    /**
     * Resource bundle identificator
     */
    ResourceBundle res;

    // Hashtable of Resource Bundles for different langauges
    /**
     * The resource bundles.
     */
    Hashtable<String, ResourceBundle> resourceBundles;

    // Reversed Hashtable of Resource Bundles for different langauges
    // Hashtable reverseRBs;

    // public String[] lang_short = null;

    private ArrayList<String> lang_short = null;

    private String m_prefix = null;
    private String default_lang = null;
    private String check_lang = null;
    private int keyword_count_full = 0;


    /**
     * 
     * I18nTranslationCheck constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.<br>
     * <br>
     * 
     * @param prefix
     * @param default_lang
     * @param checked
     * 
     */
    public I18nTranslationCheck(String prefix, String default_lang, String checked)
    {
        resourceBundles = new Hashtable<String, ResourceBundle>();
        lang_short = new ArrayList<String>();
        this.m_prefix = prefix;
        this.default_lang = default_lang.toUpperCase();
        this.check_lang = checked;

        getAvailableFiles();

        if (lang_short.size() < 2)
        {
            System.out.println("  WARNING: You need two language files to translate.");
            return;
        }

        loadLanguages();
        // checkLanguages();
        checkTranslation();
        // checkLanguages(lang);

    }


    /**
     * Gets the available files.
     */
    public void getAvailableFiles()
    {
        File fl = new File(".");

        String[] files = fl.list();

        for (String file : files)
        {
            if (file.startsWith(this.m_prefix + "_") && file.indexOf(".properties") != -1)
            {
                String sub = file.substring(this.m_prefix.length() + 1, file.indexOf(".properties"));

                if (sub.length() <= 3)
                {
                    this.lang_short.add(sub);
                    System.out.println(sub);
                }
            }
        }

    }


    /**
     * Load languages.
     */
    public void loadLanguages()
    {

        System.out.println(" --- Load Resource Bundles ---");
        for (int i = 0; i < lang_short.size(); i++)
        {
            String lang = this.lang_short.get(i);
            String name = this.m_prefix + "_" + lang + ".properties";

            // Locale l = new Locale(lang_short[i]);

            ResourceBundle rb = getResourceBundle(lang);

            if (rb == null)
            {
                System.out.println("Error loading: " + name);
                // rb = getResourceBundle(lcls[0]);
            }
            else
            {
                System.out.println("Resource File " + name + " loaded succesfully");
            }

            // System.out.println("RB Full: " + resourceBundles + " loaded RB: "
            // +rb);

            System.out.println("Def: " + this.default_lang);

            if (lang.toUpperCase().equals(this.default_lang))
            {
                res = rb;
                this.keyword_count_full = countKeys(rb);
                System.out.println("Default language");
            }
            else
            {
                resourceBundles.put(lang, rb);
            }

        }
        System.out.println(" --- End of loading of Resource Bundles ---");

    }


    private int countKeys(ResourceBundle rb)
    {
        int count = 0;

        for (Enumeration<String> en = rb.getKeys(); en.hasMoreElements();)
        {
            String key = en.nextElement();

            if (!key.equals("TRANSLATION_CHECK_SAME_WORDS"))
            {
                count++;
            }

        }

        return count;

    }


    /**
     * Gets the resource bundle.
     * 
     * @param lcl the lcl
     * 
     * @return the resource bundle
     */
    public ResourceBundle getResourceBundle(Locale lcl)
    {

        ResourceBundle rb = null;
        try
        {
            rb = ResourceBundle.getBundle(this.m_prefix, lcl);
        }
        catch (MissingResourceException mre)
        {
            System.out.println("Couldn't find resource file for Locale: " + lcl);
        }

        return rb;

    }


    /**
     * Gets the resource bundle.
     * 
     * @param lcl_str the lcl_str
     * 
     * @return the resource bundle
     */
    public ResourceBundle getResourceBundle(String lcl_str)
    {

        Locale lcl = new Locale(lcl_str);

        ResourceBundle rb = null;
        try
        {
            rb = ResourceBundle.getBundle(this.m_prefix, lcl);
        }
        catch (MissingResourceException mre)
        {
            System.out.println("Couldn't find resource file for Locale: " + lcl);
        }

        return rb;

    }


    /**
     * Check translation.
     */
    public void checkTranslation()
    {
        System.out.println("==========================================================================");
        System.out.println("Translated Language " + this.m_prefix + "_" + this.check_lang + ".properties");
        System.out.println("==========================================================================");

        System.out.println(this.resourceBundles);

        int i = 0;

        int same = 0;

        for (Enumeration<String> en = res.getKeys(); en.hasMoreElements();)
        {
            String key = en.nextElement();

            String def = res.getString(key);

            if (key.equals("TRANSLATION_CHECK_SAME_WORDS"))
            {
                same = ATDataAccess.getIntValueFromString(def, 0, null);
            }
            else
            {
                String tra = this.resourceBundles.get(this.check_lang).getString(key);

                if (def.equals(tra))
                {
                    System.out.println(key + " = " + def + " == " + tra);
                    i++;
                }
            }

        }

        // System.out.println("We have probably " + i +
        // " untranslated expressions.");

        System.out.println("=================================================");
        System.out.println("===   Short Report                            ===");
        System.out.println("===   Total words:  " + this.keyword_count_full);
        System.out.println("===   Same words:   " + same); // ===========================================");
        System.out.println("===   Translated:   " + (100 - (i - same) / (this.keyword_count_full * 1.0) * 100));
        System.out.println("===   UnTranslated: " + i);

    }


    /**
     * Check languages.
     */
    public void checkLanguages()
    {

        for (Enumeration<String> en = this.resourceBundles.keys(); en.hasMoreElements();)
        {
            String key = en.nextElement();
            ResourceBundle rb = this.resourceBundles.get(key);

            System.out.println("==================================================================");
            System.out.println("Language " + this.m_prefix + "_" + key + ".properties");
            System.out.println("==================================================================");
            checkIfLangFileHasAllKeywords(rb);
            checkIfLangHasNoLongerUsedKeywords(rb);
        }

        /*
         * for (int i=0; i<lang_short.size(); i++) { String lang =
         * (String)this.lang_short.get(i);
         * if (!lang_short.get(i)[i].equals(lang_def)) { //
         * System.out.println("Checking " + this.languages[i] + " language.");
         * //this.checkIfLangFileHasAllKeywords(lang_short[i]);
         * System.out.println("\n\n"); }
         * }
         */

    }


    /**
     * Check if lang file has all keywords.
     * 
     * @param short_lang the short_lang
     */
    public void checkIfLangFileHasAllKeywords(String short_lang)
    {
        ResourceBundle rb = resourceBundles.get(short_lang);
        checkIfLangFileHasAllKeywords(rb);
    }


    /**
     * Check if lang file has all keywords.
     * 
     * @param rb the rb
     */
    public void checkIfLangFileHasAllKeywords(ResourceBundle rb)
    {

        // ResourceBundle rb = (ResourceBundle)resourceBundles.get(short_lang);

        StringBuffer sb = new StringBuffer();

        for (Enumeration<String> en = res.getKeys(); en.hasMoreElements();)
        {
            String key = en.nextElement();

            try
            {
                if (rb.getString(key) == null)
                {
                    sb.append(key + "\n");
                    // System.out.println(key);
                }
            }
            catch (Exception ex)
            {
                // System.out.println(key);
                sb.append(key + "\n");
            }
        }

        if (sb.length() == 0)
        {
            System.out.println("\nNO MISSING KEYWORDS !");
            System.out.println("---------------------");
        }
        else
        {
            System.out.println("\nMISSING KEYWORDS:");
            System.out.println("-----------------");
            System.out.println(sb);
        }

    }


    /**
     * Check if lang has no longer used keywords.
     * 
     * @param rb the rb
     */
    public void checkIfLangHasNoLongerUsedKeywords(ResourceBundle rb)
    {

        // ResourceBundle rb = (ResourceBundle)resourceBundles.get(short_lang);

        StringBuffer sb = new StringBuffer();

        for (Enumeration<String> en = rb.getKeys(); en.hasMoreElements();)
        {
            String key = en.nextElement();

            try
            {
                if (res.getString(key) == null)
                {
                    sb.append(key + "\n");
                    // System.out.println(key);
                }
            }
            catch (Exception ex)
            {
                // System.out.println(key);
                sb.append(key + "\n");
            }
        }

        if (sb.length() == 0)
        {}
        else
        {
            System.out.println("KEYWORDS NO LONGER USED:");
            System.out.println("------------------------");
            System.out.println(sb);
        }

    }


    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String args[])
    {
        if (args.length != 3)
        {
            System.out.println(
                " Usage:  java I18nTranslationCheck <lang_file_prefix> <default_language_short> <language_we_translate>");
            System.exit(1);
        }

        /* I18nTranslationCheck i1 = */new I18nTranslationCheck(args[0], args[1], args[2]);

    }

}
