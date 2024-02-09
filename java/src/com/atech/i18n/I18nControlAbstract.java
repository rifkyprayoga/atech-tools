package com.atech.i18n;

import java.io.FileInputStream;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class I18nControlAbstract
{

    private static final Logger LOG = LoggerFactory.getLogger(I18nControlAbstract.class);
    private Collator langaugeCollator = null;

    /**
     *  Resource bundle identificator
     */
    ResourceBundle res;

    /**
     * The resource bundles.
     */
    Hashtable<String, String> resourceBundles;

    /**
     * The lang_file_root.
     */
    public String lang_file_root = null;

    /**
     * The def_language.
     */
    public String def_language = "en";

    /**
     * The selected_language.
     */
    public String selected_language = null;

    /**
     * The selected_language_locale.
     */
    public Locale selected_language_locale = null;

    /**
     * The selected_language_locale.
     */
    public Locale selected_language_locale_real = null;

    /**
     * The languages.
     */
    protected String languages[] = null;
    /*
     * }
     * {
     * "English",
     * "German",
     * "Slovene",
     * "Simp. Chinese"
     * };
     */

    /**
    * The lcls.
    */
    protected Locale lcls[] = null;
    /*
     * {
     * Locale.ENGLISH,
     * Locale.GERMAN,
     * new Locale("SI"),
     * Locale.SIMPLIFIED_CHINESE
     * };
     */

    protected Locale lcls_real[] = null;

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
    /*
     * static public I18nControlAbstract getInstance()
     * {
     * if (m_i18n == null)
     * m_i18n = new I18nControlAbstract();
     * return m_i18n;
     * }
     */


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

    protected abstract String getLanguageConfigFile();


    // Method: setLanguage (String language)
    /**
     *
     *  This is helper method for setting language.<br><br>
     *
     *  @param language language which we want to use
     */
    public void setLanguage(String language)
    {
        // System.out.println("setLanguage(String lang): " + language);

        Locale l = new Locale(language);
        selected_language = language;
        setLanguage(l);
        createCollationDefintion();
    }


    /**
     * Init - This method is used to set default language and language root file
     */
    public abstract void init();


    /**
     * Get Selected Language
     * @return
     */
    public String getSelectedLanguage()
    {

        if (this.selected_language != null)
            return this.selected_language;

        try
        {
            Properties props = new Properties();

            FileInputStream in = new FileInputStream(getLanguageConfigFile());
            props.load(in);

            String tempLang = (String) props.get("SELECTED_LANG");

            if (tempLang != null)
            {
                this.selected_language = tempLang;
            } else {
                this.selected_language = "en";
            }

            this.selected_language_locale = new Locale(this.selected_language);

            return this.selected_language;
            // System.out.println("selected language: " +
            // this.selected_language);
        }
        catch (Exception ex)
        {
            System.out.println("I18nControl: Configuration file not found. Using default langauge ('en')");
            LOG.warn("Configuration file not found. Using default langauge ('en')");
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Gets the selected langauge.
     * 
     * @return the selected langauge
     */
    /*
     * public String getSelectedLangauge()
     * {
     * return this.selected_language;
     * }
     */


    // Method: setLanguage (String language, String country)
    /**
     *
     *  This is helper method for setting language.<br><br>
     *
     *  @param language language which we want to use
     *  @param country country that uses this language
     */
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
    public void setLanguage(Locale lcl)
    {
        // System.out.println("setLanguage(Locale): " + lcl);

        try
        {
            // System.out.println("setLang: " + lang_file_root);
            res = ResourceBundle.getBundle(lang_file_root, lcl);

            if (selected_language == null)
            {
                selected_language = lcl.getCountry();
            }
        }
        catch (MissingResourceException mre)
        {
            System.out.println(
                "Couldn't find resource file(1): " + lang_file_root + "." + selected_language + ".properties");
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
     * Init Library
     */
    public void initLibrary()
    {
        init();
        getSelectedLanguage();
        setLanguage();
    }


    /**
     * Gets the selected language locale.
     * 
     * @return the selected language locale
     */
    public Locale getSelectedLanguageLocale()
    {
        if (this.selected_language_locale == null)
        {
            System.out.println("sel_language=" + this.selected_language);

            this.selected_language_locale = new Locale(this.selected_language);

            System.out.println("Force created locale: " + this.selected_language_locale);
        }

        return this.selected_language_locale;
    }


    /**
     * This method sets the language according to the preferences.<br>
     */
    public void setLanguage()
    {
        if (selected_language != null)
        {
            setLanguage(selected_language);
        }
        else
        {
            setLanguage(def_language);
        }
    }


    // Method: hmmlize
    /**
     *  
     * Converts text from bundle into HTML. This must be used if we have control, which has
     * formated text in HTML or is multilined (some of basic java swing components don't 
     * support \n). 
     * 
     * @param input input text we wish to HTMLize
     * @return HTMLized text
     * 
     */
    protected String htmlize(String input)
    {

        StringBuffer buffer = new StringBuffer("<HTML>");

        input = input.replaceAll("\n", "<BR>");
        input = input.replaceAll("&", "&amp;");

        buffer.append(input);
        buffer.append("</HTML>");

        return buffer.toString();

    }


    // Method: getMessageHTML(String)
    /**
     * 
     *  Helper method to get HTMLized message from Bundle
     * 
     * @param msg non-htmlized (or partitialy HTMLized tekst)
     * @return fully HTMLized message 
     * 
     */
    public String getMessageHTML(String msg)
    {

        String mm = this.getMessage(msg);

        return htmlize(mm);

    }


    // Method: getString
    /**
     * 
     *  This helper method calls getMessage(String) and returns message that is
     *  associated with inserted code. It is implemented mainly, because some 
     *  programmers are used that resource nsg is returned with this command.
     * 
     *  @param msg id of message we want
     *  @return value for code, or same code back
     */
    public String getString(String msg)
    {
        return this.getMessage(msg);
    }


    // Method: returnSameValue (String)
    /**
     * 
     *  Returns same value as it was sent to catalog in case that catalog entry was not
     *  found. This message has inserted spaces so that is easier readable.
     * 
     *  @param msg id of message we want
     *  @return same code back (formated)
     */
    protected String returnSameValue(String msg)
    {
        // If we return same msg back, without beeing resolved, we put spaces
        // before %, so
        // that it is much easier readable.
        if (msg.indexOf("%") == -1)
            return msg;

        StringBuffer out = new StringBuffer();
        int idx;
        while ((idx = msg.indexOf("%")) != -1)
        {
            out.append(msg.substring(0, idx));
            out.append("|%");

            msg = msg.substring(idx + 1);

        }

        out.append(msg);

        return out.toString();

    }


    // Method: resolveMnemonic(String)
    /**
     *  This method extracts mnemonics from message string. Each such string can
     *  contain several & characters, even double &. Last & in String is mnemonic
     *  while other are discarded. Double && is resolved to single & in text. We 
     *  return array of Object. First entry contains mnemonic if this is null, we 
     *  didn't find any mnemonic. Second entry is text without mnemonic and changed 
     *  && substrings. If whole object is returned as null, then String didn't 
     *  contain any & signs.
     * 
     *  @param msg message from message catalog
     *  @return array of Object, containg max. two elements, null can also be returned
     *
     */
    protected Object[] resolveMnemonics(String msg)
    {

        if (msg.indexOf("&") == -1)
            return null;

        Object back[] = new Object[2];
        int msg_length = msg.length();
        int code[] = new int[msg_length];
        // x boolean foundDouble=false;
        boolean foundMnemonic = false;

        for (int i = 0; i < msg_length; i++)
        {
            if (msg.charAt(i) == '&')
            {
                // we found mnemonic sign
                code[i] = 1; // 1 if & sign
                if (i != 0)
                {
                    // check for double &
                    if (code[i - 1] == 1) // double & are marked 2
                    {
                        code[i - 1] = 2;
                        code[i] = 2;
                        // x foundDouble=true;
                    }
                }
            }
            else
            {
                code[i] = 0;
            }
        }

        // now we find real menmonic
        for (int i = msg_length - 1; i > -1; i--)
        {
            if (code[i] == 1)
            {
                code[i] = 3;
                if (i == msg_length - 1) // if & is last char we ignore it
                {
                    code[i] = 1;
                }
                else
                {
                    foundMnemonic = true;
                    break;
                }
            }
        }

        StringBuffer returnStr = new StringBuffer();

        int lastChange = 0;

        for (int i = 0; i < msg_length; i++)
        {

            if (code[i] == 1) // all & (tagged 1) are removed
            {
                returnStr.append(msg.substring(lastChange, i));
                lastChange = i + 1;

            }
            else if (code[i] == 2) // all && are replaced with one &
            {
                returnStr.append(msg.substring(lastChange, i));
                returnStr.append("&");
                lastChange = i + 2; // was 2
                i = i + 1;
            }
            else if (code[i] == 3) // this is mnemonic
            {
                back[0] = new Character(msg.charAt(i + 1));
                returnStr.append(msg.substring(lastChange, i));
                lastChange = i + 1;
            }
        }

        returnStr.append(msg.substring(lastChange));

        back[1] = returnStr.toString();

        if (!foundMnemonic)
        {
            back[0] = null;
        }

        return back;

    }


    // temporary only - this is not core
    /**
     * Checks for mnemonic.
     * 
     * @param msg_id the msg_id
     * 
     * @return true, if successful
     */
    public boolean hasMnemonic(String msg_id)
    {
        try
        {
            Object[] back = resolveMnemonics(getMessageFromCatalog(msg_id));

            if (back != null && back[0] != null)
                return true;

        }
        catch (Exception e)
        {}

        return false;
    }


    // Method: getMnemonic
    /**
     *  Returns mnemonic of String that is stored in bundle as msg_id. If mnemonic is
     *  not found 0 is returned. Calls private method resolveMnemonics.
     * 
     *  @param msg_id id of message in bundle
     *  @return msg_id int representation of char that is mnemonic, 0 if none found
     */
    public char getMnemonic(String msg_id)
    {
        try
        {
            Object[] back = resolveMnemonics(getMessageFromCatalog(msg_id));

            if (back == null || back[0] == null)
                return 0;

            return ((Character) back[0]).charValue();
        }
        catch (Exception e)
        {
            return 0;
        }

    }


    /**
     *  Get Message Without Mnemonic
     *  
     *  Returns String that is stored in bundle as msg_id. It also removes  
     *  mnemonic signs and removed double &. Calls private method resolveMnemonics.
     * 
     *  @param msg_id id of message in bundle
     *  @return String message from catalog, without mnemonic and double &
     */
    public String getMessageWithoutMnemonic(String msg_id)
    {
        try
        {

            String ret = getMessageFromCatalog(msg_id);

            Object[] back = resolveMnemonics(ret);

            if (back == null)
                return ret;
            else
                return (String) back[1];
        }
        catch (Exception ex)
        {
            return returnSameValue(msg_id);
        }
    }


    // Method: getMessageFromCatalog
    /**
     *  Looks into bundle and returns correct message. This method is syncronized, so only one
     *  message at the time can be returned.
     * 
     *  @param msg id of message in bundle
     *  @return String message from catalog.
     */
    public synchronized String getMessageFromCatalog(String msg)
    {
        try
        {

            if (msg == null)
                return "null";

            if (!checkIfValidMessageKey(msg))
                return msg;

            String ret = res.getString(msg);

            if (ret == null)
            {
                LOG.warn("I18nControl(" + this.selected_language + "): Couldn't find message: " + msg);
                return returnSameValue(msg);
            }
            else
                return ret;

        }
        catch (Exception ex)
        {
            return returnSameValue(msg);
        }

    }


    public synchronized String getMessageFromCatalogOrNull(String msg)
    {
        try
        {

            if (msg == null)
                return null;

            if (!checkIfValidMessageKey(msg))
                return null;

            String ret = res.getString(msg);

            if (ret == null)
            {
                return null;
            }
            else
                return ret;

        }
        catch (Exception ex)
        {
            return null;
        }
    }


    public synchronized boolean isKeywordTranslated(String keyword)
    {
        try
        {
            if (keyword == null)
                return false;

            if (!checkIfValidMessageKey(keyword))
                return false;

            String ret = res.getString(keyword);
            return (ret != null);
        }
        catch (Exception ex)
        {
            return false;
        }
    }


    protected boolean checkIfValidMessageKey(String key)
    {
        if (key.trim().length() == 0)
            return false;

        try
        {
            Double.parseDouble(key);
            return false;
        }
        catch (Exception ex)
        {}

        return true;

    }


    // Method: getMessage (String)
    /**
     * 
     *  Helper method to get message from Bundle.
     * 
     *  @param msg id of message we want
     *  @return value for code, or same code back
     */
    public String getMessage(String msg)
    {
        return getMessageFromCatalog(msg);
    }


    // this.m_collator = this.m_i18n.getCollationDefintion();

    /**
     * Creates the collation defintion.
     */
    public void createCollationDefintion()
    {
        // System.out.println("!!!!!!!!! CreateCollactionDefinition");

        String col_def = this.getMessage("COLLATION_RULES");

        if (col_def.equals("COLLATION_RULES"))
        {
            // System.out.println("Default collation rule !");
            this.langaugeCollator = Collator.getInstance(Locale.ENGLISH);
        }
        else
        {
            try
            {
                // System.out.println(col_def);
                this.langaugeCollator = new RuleBasedCollator(col_def);
            }
            catch (Exception ex)
            {
                // System.out.println("Exception creating collator: " + ex);
                LOG.error("Exception creating collator: " + ex, ex);
                // log.error("Exception creating collator: " + ex, ex);
                this.langaugeCollator = Collator.getInstance(Locale.ENGLISH);
            }
        }

        // testCollation();

    }


    /**
     * Gets the collation defintion.
     * 
     * @return the collation defintion
     */
    public Collator getCollationDefintion()
    {
        if (this.langaugeCollator == null)
        {
            createCollationDefintion();
        }

        return this.langaugeCollator;
    }


    /**
     * Get Partitial Translation
     * 
     * @param keyword
     * @param default_delim
     * @return
     */
    public String getPartitialTranslation(String keyword, String default_delim)
    {

        StringTokenizer strtok = new StringTokenizer(keyword, default_delim);
        StringBuffer sb = new StringBuffer();

        while (strtok.hasMoreTokens())
        {
            String k = strtok.nextToken();

            char[] chars = { ',', '(', ')' };
            boolean checked = false;
            int i = 0;

            while (!checked)
            {
                checked = checkStartEnd(k, chars[i], sb);
                i++;

                if (i == chars.length)
                {
                    break;
                }
            }

            // boolean found = checkStartEnd(k, ',', sb);

            // if (!found

            if (!checked)
            {
                sb.append(this.getMessage(k));
            }

            sb.append(" ");
        }

        return sb.toString();

    }


    private boolean checkStartEnd(String key, char char_el, StringBuffer sb)
    {
        if (key.charAt(0) == char_el)
        {
            sb.append(char_el);

            key = key.substring(1, key.length());

            sb.append(this.getMessage(key));
            return true;
        }
        else if (key.charAt(key.length() - 1) == char_el)
        {

            key = key.substring(0, key.length() - 1);

            sb.append(this.getMessage(key));
            sb.append(char_el);
            return true;

        }

        return false;

    }


    /**
     *  Get Name
     *   
     *  Helper method to get message that is name from Bundle. If name is found in 
     *  bundle, we use it, or else we reformat input string.
     * 
     *  @param msg id of message we want
     *  @return value for code, or same code back
     */
    public String getName(String msg)
    {

        // String nm = getMessageFromCatalog(msg);
        String nm = getMessage(msg);

        if (nm.equals(msg))
        {
            nm = nm.replace('_', ' ');

            nm = changeCase(nm);

            return nm;

        }
        else
            return nm;

    }


    /**
     * @param in
     * @return
     */
    public String changeCase(String in)
    {

        StringTokenizer stok = new StringTokenizer(in, " ");

        boolean first = true;
        String out = "";

        while (stok.hasMoreTokens())
        {

            if (!first)
            {
                out += " ";
            }

            // String tmp = stok.nextToken();

            out += changeCaseWord(stok.nextToken());

            first = false;
        }

        return out;
    }


    /**
     * Change case of word (something -> Something)
     * 
     * @param in
     * @return
     */
    public String changeCaseWord(String in)
    {
        String t = "";

        t = in.substring(0, 1).toUpperCase();
        t += in.substring(1).toLowerCase();

        return t;
    }

}
