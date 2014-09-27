package com.atech.web.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

public class I18nWebControl
{

    public String base_lang_file = null;

    /**
     * Resource bundle identificator
     */
    ResourceBundle res;
    private static Log log = LogFactory.getLog(I18nWebControl.class);

    /**
     * 
     * This is I18nWebControl constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.<br>
     * <br>
     * 
     */
    public I18nWebControl(String lang, String base_i18n_name)
    {
        this.base_lang_file = base_i18n_name;
        setLanguage(lang);
        // setLanguage(DataAccess.getSelectedLocale());
        // DataAccess.getInstance().setI18n(this);
    }

    // Method: setLanguage (String language, String country)
    /**
     * 
     * This is helper method for setting language.<br>
     * <br>
     * 
     * @param language
     *            language which we want to use
     * @param country
     *            country that uses this language
     */
    public void setLanguage(String language, String country)
    {
        log.warn("setLanguage(String language, String country) not implemented.");
        // Locale l = new Locale(language, country);
        // setLanguage(l);
    }

    // Method: setLanguage (Locale)
    /**
     * 
     * This method sets language for control instance. If none is found, english
     * is defaulted. if none is found, application will exit.<br>
     * <br>
     * 
     * @param lcl
     *            locale that will choose which language will be set
     */
    public void setLanguage(String lang /* , Locale lcl */)
    {
        lang = lang.toUpperCase();

        try
        {
            // ResourceBundle.
            res = ResourceBundle.getBundle(this.base_lang_file, new Locale(lang));

            log.debug(this.base_lang_file + " Language " + lang + " loaded.");
        }
        catch (Exception ex) // MissingResourceException mre)
        {
            // System.out.println(mre.
            // System.out.println("Exception load1: " + ex.getMessage() + "\n" +
            // ex.getStackTrace());

            log.debug(this.base_lang_file + " Language " + lang + " could NOT be loaded. Trying default (SI).");
            // System.out.println("Couldn't find resource file(1): " +
            // this.base_lang_file + "_xx.properties (for Locale " + lang +
            // ")");
            try
            {
                res = ResourceBundle.getBundle(this.base_lang_file, new Locale("SI"));
                log.debug("PIS Language SI loaded.");
            }
            catch (Exception ex2)
            {
                log.debug("Default " + this.base_lang_file + " Language (SI)" + lang + " could NOT be loaded.");
                // System.out.println("Exception on reading default resource file (ZIS_SI.properties). Exiting application.");
                System.exit(2);
            }
        }

    }

    // Method: hmmlize
    /**
     * 
     * Converts text from bundle into HTML. This must be used if we have
     * control, which has formated text in HTML or is multilined (some of basic
     * java swing components don't support \n).
     * 
     * @param input
     *            input text we wish to HTMLize
     * @return HTMLized text
     * 
     */
    private String htmlize(String input)
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
     * Helper method to get HTMLized message from Bundle
     * 
     * @param msg
     *            non-htmlized (or partitialy HTMLized tekst)
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
     * This helper method calls getMessage(String) and returns message that is
     * associated with inserted code. It is implemented mainly, because some
     * programmers are used that resource nsg is returned with this command.
     * 
     * @param msg
     *            id of message we want
     * @return value for code, or same code back
     */
    public String getString(String msg)
    {
        return this.getMessage(msg);
    }

    // Method: returnSameValue (String)
    /**
     * 
     * Returns same value as it was sent to catalog in case that catalog entry
     * was not found. This message has inserted spaces so that is easier
     * readable.
     * 
     * @param msg
     *            id of message we want
     * @return same code back (formated)
     */
    private String returnSameValue(String msg)
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
     * This method extracts mnemonics from message string. Each such string can
     * contain several & characters, even double &. Last & in String is mnemonic
     * while other are discarded. Double && is resolved to single & in text. We
     * return array of Object. First entry contains mnemonic if this is null, we
     * didn't find any mnemonic. Second entry is text without mnemonic and
     * changed && substrings. If whole object is returned as null, then String
     * didn't contain any & signs.
     * 
     * @param msg
     *            message from message catalog
     * @return array of Object, containg max. two elements, null can also be
     *         returned
     * 
     */
    private Object[] resolveMnemonics(String msg)
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

    // Method: getMnemonic
    /**
     * Returns mnemonic of String that is stored in bundle as msg_id. If
     * mnemonic is not found 0 is returned. Calls private method
     * resolveMnemonics.
     * 
     * @see resolveMnemonic
     * @param msg_id
     *            id of message in bundle
     * @return int representation of char that is mnemonic, 0 if none found
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

    // Method: getMessageWithoutMnemonic
    /**
     * Returns String that is stored in bundle as msg_id. It also removes
     * mnemonic signs and removed double &. Calls private method
     * resolveMnemonics.
     * 
     * @see resolveMnemonic
     * @param msg_id
     *            id of message in bundle
     * @return String message from catalog, woithout mnemonic and double &
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
     * Looks into bundle and returns correct message. This method is
     * syncronized, so only one message at the time can be returned.
     * 
     * @param msg
     *            id of message in bundle
     * @return String message from catalog.
     */
    private synchronized String getMessageFromCatalog(String msg)
    {

        try
        {

            if (msg == null)
                return "null";

            String ret = res.getString(msg);

            if (ret == null)
            {
                System.out.println("Couldn't find message: ");
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

    // Method: getMessage (String)
    /**
     * 
     * Helper method to get message from Bundle.
     * 
     * @param msg
     *            id of message we want
     * @return value for code, or same code back
     */
    public String getMessage(String msg)
    {

        return getMessageFromCatalog(msg);

    }

    // Method: getName (String)
    /**
     * 
     * Helper method to get message that is name from Bundle. If name is found
     * in bundle, we use it, or else we reformat input string.
     * 
     * @param msg
     *            id of message we want
     * @return value for code, or same code back
     */
    public String getName(String msg)
    {

        String nm = getMessageFromCatalog(msg);

        if (nm.equals(msg))
        {
            nm = nm.replace('_', ' ');

            nm = changeCase(nm);

            return nm;

        }
        else
            return nm;

    }

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

    public String changeCaseWord(String in)
    {

        String t = "";

        t = in.substring(0, 1).toUpperCase();
        t += in.substring(1).toLowerCase();

        return t;

    }

}
