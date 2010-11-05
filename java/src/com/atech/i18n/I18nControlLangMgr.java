package com.atech.i18n;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.mgr.LanguageInstance;
import com.atech.i18n.mgr.LanguageManager;

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

/**
 * This is I18nControl class used with conjunction with LanguageManager 
 */

public class I18nControlLangMgr extends I18nControlAbstract
{

    private static Log log = LogFactory.getLog(I18nControlLangMgr.class); 
    private Collator langaugeCollator = null;
    protected LanguageManager language_manager = null;
    private I18nControlRunner i18ncontrol_runner;
    
    
    
    
    
    //protected abstract String getLanguageConfigFile();
    
    /**
     * Constructor
     * 
     * @param lm
     * @param icr
     */
    public I18nControlLangMgr(LanguageManager lm, I18nControlRunner icr)
    {
        this.i18ncontrol_runner = icr;
        this.language_manager = lm;
        this.initLibrary();
    }

    //  Method:       setLanguage (String language)
    /**
     *
     *  This is helper method for setting language.<br><br>
     *
     *  @param language language which we want to use
     */ 
    public void setLanguage(String language)
    {
        //System.out.println("setLanguage(String lang): " + language);

        Locale l = new Locale(language);
        selected_language = language;
        setLanguage(l);
        createCollationDefintion();
    }

    
    /**
     * Init - This method is used to set default language and language root file
     */
    public void init()
    {
        this.lang_file_root = this.i18ncontrol_runner.getLanguageFileRoot();
        this.def_language = this.language_manager.getDefaultLanguage();
    }
    
    
    public String getSelectedLanguage()
    {
        return this.language_manager.getSelectedLanguage();
    }
    
    
    /**
     * Gets the selected langauge.
     * 
     * @return the selected langauge
     */
    public String getSelectedLangauge()
    {
        return this.selected_language;
    }
    

    //  Method:       setLanguage (String language, String country)
    /**
     *
     *  This is helper method for setting language.<br><br>
     *
     *  @param language language which we want to use
     *  @param country country that uses this language
     */ 
    public void setLanguage(String language, String country)
    {
        //System.out.println("setLanguage(String lang, String cnt): " + language + ", " + country);

        Locale l = new Locale(language, country);
        selected_language = language;
        setLanguage(l);
    }



    //  Method:       setLanguage (Locale)
    /**
     *
     *  This method sets language for control instance. If none is found, english is defaulted.
     *  if none is found, application will exit.<br><br>
     * @param li 
     *
     *  @param lcl locale that will choose which language will be set
     */ 
/*    public void setLanguage(Locale lcl)
    {
        //System.out.println("setLanguage(Locale): " + lcl);

        try
        {
            //System.out.println("setLang: " + lang_file_root);
            res = ResourceBundle.getBundle(lang_file_root, lcl);

            if (selected_language ==null)
            {
                selected_language = lcl.getCountry();
            }
        }
        catch (MissingResourceException mre)
        {
            System.out.println("Couldn't find resource file(1): " + lang_file_root + "." + selected_language + ".properties");
            try
            {
                res = ResourceBundle.getBundle(lang_file_root, new Locale(this.def_language));
            }
            catch(MissingResourceException ex)
            {
                System.out.println("Exception on reading resource file. Exiting application.");
                //System.exit(2);
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
*/

 
    /**
     * Set Language
     * 
     * @param li 
     */
    public void setLanguage(LanguageInstance li)
    {
        String prefix = "";
        Locale lcl = null;
        boolean lt_found = false;
        String file_path = null;
        
        System.out.println("setLanguage: (LanguageInstance): " + li);
        
        
        if (li.is_translation_tool)
        {
            prefix = "../data/tools/translation/";
            
            file_path = "../data/tools/translation/"  + lang_file_root + "_" + li.name + ".properties";
            
            
            if (!(new File(file_path).exists()))
            {
                prefix = "";
                lcl = new Locale(this.def_language);
            }
            else
            {
                lcl = new Locale(li.name);
                lt_found = true;
            }
            
        }
        else
        {
            lcl = new Locale(li.name);
        }
        
        
        //System.out.println("setLanguage(Locale): " + lcl);

        
        System.out.println("Trying to load: " + prefix + "" + lang_file_root + ",lcl=" + lcl);
        try
        {
            System.out.println("setLang(): " + li.name + ", Locale: " + lcl);
            
            if (lt_found)
            {
                System.out.println("Lt Found");
                // 1.5
                res = new PropertyResourceBundle(new FileInputStream(file_path)); 
                
                // 1.6 supports this, while 1.5 doesn't
                //res = new PropertyResourceBundle(new FileReader(file_path)); 
            }
            else
            {
                System.out.println("Current Location: " + new File(".").getAbsolutePath());
                
                
                // FIXME Testing
                res = ResourceBundle.getBundle(prefix + "" + lang_file_root, lcl);
                //res = new PropertyResourceBundle(new FileInputStream(prefix + "" + lang_file_root + li.name + ".properties"));
                
                
/*
                try
                {
                    res = ResourceBundle.getBundle(prefix + "" + lang_file_root, lcl);
                }
                catch(Exception ex1)
                {
                    try
                    {
                    res = new PropertyResourceBundle(new FileInputStream(prefix + "" + lang_file_root + li.name + ".properties"));
                    }
                    catch(Exception ex2)
                    {
                        throw ex1;
                    }
                    
                }*/
            }
            
            System.out.println("Res: " + prefix + "" + lang_file_root   + ", Locale: " + lcl + "File:" + res.getString("I18NFILE"));

        }
        catch (MissingResourceException mre)
        {
            System.out.println("Couldn't find resource file(1): " + lang_file_root + "_" + li.name + ".properties");
            log.error("Couldn't find resource file(1): " + lang_file_root + "_" + li.name + ".properties");
            try
            {
                res = ResourceBundle.getBundle(lang_file_root, new Locale(this.def_language));
            }
            catch(MissingResourceException ex)
            {
                log.error("Exception on reading resource file. Exiting application.");
                //System.exit(2);
            }
        }
        catch (FileNotFoundException ex)
        {
            log.error("Couldn't find translation tool file(1): " + file_path );
            try
            {
                res = ResourceBundle.getBundle(lang_file_root, new Locale(this.def_language));
            }
            catch(MissingResourceException ex2)
            {
                log.error("Exception on reading resource file. Exiting application.", ex2);
                //System.exit(2);
            }
        }
        catch (Exception mre)
        {
            log.error("Exception on reading resource file. Exiting application.");
            log.error("Exception: " + mre, mre);
            //mre.printStackTrace();
            System.exit(2);
        }
        
        this.selected_language_locale = lcl;

        createCollationDefintion();
        
        this.setLanguage(this.selected_language_locale);
    }
    
    
    
    
    
    
    public void initLibrary()
    {
        this.initAdditional();
        init();
        //getSelectedLanguage();
        setLanguage();
    }
    
    

    /**
     * Gets the selected language locale.
     * 
     * @return the selected language locale
     */
    public Locale getSelectedLanguageLocale()
    {
        return this.selected_language_locale;
    }


    /**
     * This method sets the language according to the preferences.<br>
     */
    public void setLanguage() 
    {
        LanguageInstance li = this.language_manager.getSelectedLanguageInstance();
        
        //System.out.println("setLanguage: " + li);
        
        
        setLanguage(li);
        
        /*
        if (selected_language!=null)
            setLanguage(selected_language);
        else
            setLanguage(def_language);*/
    }



    //  Method: hmmlize
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
    private String htmlize(String input)
    {
        
        StringBuffer buffer = new StringBuffer("<HTML>");

        input = input.replaceAll("\n", "<BR>");
        input = input.replaceAll("&", "&amp;");

        buffer.append(input);
        buffer.append("</HTML>");

        return buffer.toString();
        
    }



    //  Method: getMessageHTML(String)
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





    //  Method:       getString
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



    //  Method:       returnSameValue (String)
    /**
     * 
     *  Returns same value as it was sent to catalog in case that catalog entry was not
     *  found. This message has inserted spaces so that is easier readable.
     * 
     *  @param msg id of message we want
     *  @return same code back (formated)
     */
    private String returnSameValue(String msg)
    {
        // If we return same msg back, without beeing resolved, we put spaces before %, so
        // that it is much easier readable.
        if (msg.indexOf("%")==-1)
            return msg;

        StringBuffer out=new StringBuffer();
        int idx;
        while ((idx=msg.indexOf("%"))!=-1)
        {
            out.append(msg.substring(0, idx));
            out.append("|%");
            
            msg = msg.substring(idx+1);
                
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
    private Object[] resolveMnemonics(String msg)
    {

        if (msg.indexOf("&")==-1)
            return null;


        Object back[] = new Object[2];
        int msg_length = msg.length();
        int code[] = new int[msg_length];
//x        boolean foundDouble=false;
        boolean foundMnemonic=false;


        for (int i=0;i<msg_length;i++)
        {
            if (msg.charAt(i)=='&')
            {
                // we found mnemonic sign   
                code[i]=1;  // 1 if & sign
                if (i!=0)
                {
                    // check for double &
                    if (code[i-1]==1)  // double & are marked 2
                    {
                        code[i-1]=2;
                        code[i]=2;
//x                        foundDouble=true;
                    }
                }
            }
            else
                code[i]=0;
        }


        // now we find real menmonic
        for (int i=msg_length-1; i>-1; i--)
        {
            if (code[i]==1)
            {
                code[i]=3;
                if (i==msg_length-1)  // if & is last char we ignore it
                {
                    code[i]=1;
                }
                else
                {
                    foundMnemonic=true;
                    break;
                }
            }
        }


        StringBuffer returnStr = new StringBuffer();

        int lastChange=0;

        for (int i=0; i<msg_length;i++)
        {
            
            if (code[i]==1)  // all & (tagged 1) are removed
            {
                returnStr.append(msg.substring(lastChange, i));
                lastChange=i+1;
                
            } 
            else if (code[i]==2) // all && are replaced with one &
            {
                returnStr.append(msg.substring(lastChange, i));
                returnStr.append("&");
                lastChange=i+2; // was 2
                i=i+1;
            }
            else if (code[i]==3) // this is mnemonic
            {
                back[0]=new Character(msg.charAt(i+1)); 
                returnStr.append(msg.substring(lastChange, i));
                lastChange=i+1;
            }
        }

        returnStr.append(msg.substring(lastChange));

        back[1] = returnStr.toString();

        if (!foundMnemonic)
            back[0]=null;

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

            if ((back!=null) && (back[0]!=null))
               return true;

        }
        catch (Exception e)
        {
        }

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

            if ((back==null) || (back[0]==null))
               return 0;
        
            return ((Character)back[0]).charValue();
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

            if (back==null)
                return ret;
            else
                return (String)back[1];
        }
        catch(Exception ex)
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
            
            if (msg==null)
                return "null";
            
            String ret = res.getString(msg);

            if (ret==null)
            {
                log.warn("I18nControl(" + this.selected_language +"): Couldn't find message: " + msg);
                return returnSameValue(msg);
            }
            else
                return ret;

        }
        catch(Exception ex)
        {
            return returnSameValue(msg);
        }

    }



    //  Method:       getMessage (String)
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


    
    
    //this.m_collator = this.m_i18n.getCollationDefintion();

    
    
    /**
     * Creates the collation defintion.
     */
    public void createCollationDefintion()
    {
    	

        String col_def = this.getMessage("COLLATION_RULES");

        if (col_def.equals("COLLATION_RULES"))
        {
            //System.out.println("Default collation rule !");
            this.langaugeCollator = Collator.getInstance(Locale.ENGLISH);
        }
        else
        {
            try
            {
                //System.out.println(col_def);
                this.langaugeCollator =  new RuleBasedCollator(col_def);
            }
            catch(Exception ex)
            {
                //System.out.println("Exception creating collator: " + ex);
                log.error("Exception creating collator: " + ex, ex);
                //log.error("Exception creating collator: " + ex, ex);
                this.langaugeCollator = Collator.getInstance(Locale.ENGLISH);
            }
        }

        //testCollation();

    }
    
    
    /**
     * Gets the collation defintion.
     * 
     * @return the collation defintion
     */
    public Collator getCollationDefintion()
    {
        return this.langaugeCollator;
    }

    @Override
    protected String getLanguageConfigFile()
    {
        // TODO Auto-generated method stub
        return null;
    }
   
    
    /**
     * Init Aditional
     */
    public void initAdditional()
    {
    }
    
    

    
    

}