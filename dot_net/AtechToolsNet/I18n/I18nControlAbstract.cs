

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

using System;
using log4net;
using System.Text;
using System.Collections.Generic;
using ATechTools.File;
namespace ATechTools.I18n
{


public abstract class I18nControlAbstract
{

    private ILog s_logger = LogManager.GetLogger(typeof(I18nControlAbstract));
    //private Collator langaugeCollator = null;


    /**
     *  Resource bundle identificator
     */
//    ResourceBundle res;


    I18nControlRunner control_runner = null;


    /**
     * The resource bundles.
     */
    Dictionary<String,String> resourceBundles;


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
//    public Locale selected_language_locale = null;
    
    
    /**
     * The selected_language_locale.
     */
//    public Locale selected_language_locale_real = null;


    /**
     * The languages.
     */
    protected String[] languages = null;
/*
    }
	{
        "English",
        "German",
        "Slovene",
        "Simp. Chinese"
    };
*/

    /**
 * The lcls.
 */
//protected Locale[] lcls = null;
/*	{
        Locale.ENGLISH,
        Locale.GERMAN,
        new Locale("SI"),
        Locale.SIMPLIFIED_CHINESE
    };
*/


//    protected Locale[] lcls_real = null;


//    static private I18nControl m_i18n = null;   // This is handle to unique 
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
    public I18nControlAbstract(I18nControlRunner crunner)
    {
        this.control_runner = crunner;
        SetLanguage("en");
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
/*    static public I18nControlAbstract getInstance()
    {
        if (m_i18n == null)
            m_i18n = new I18nControlAbstract();
        return m_i18n;
    }
*/





    //  Method:       deleteInstance
    /**
     *
     *  This method sets handle to OmniI18NControl to null and deletes the instance. <br><br>
     *
     */ 
/*    public void deleteInstance()
    {
        m_i18n=null;
    }
*/








    //  Method:       setLanguage (String language)
    /**
     *
     *  This is helper method for setting language.<br><br>
     *
     *  @param language language which we want to use
     */ 
    public void SetLanguage(String language)
    {

        string f = this.control_runner.FilesRoot + this.control_runner.LanguageFileRoot + "_" + language + ".properties";

        s_logger.Debug("Filename: " + f);

        FileReaderConfig frc = new FileReaderConfig(f);

        s_logger.Warn("File found: " + frc.FileFound());
        
        
        frc.ReadConfigFile();
        resourceBundles = frc.GetData();


        s_logger.Warn("Set Language " + resourceBundles.Count);



        //Console.WriteLine("setLanguage(String lang): " + language);
        /*
        Locale l = new Locale(language);
        selected_language = language;
        setLanguage(l);
        createCollationDefintion(); */
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
        //Console.WriteLine("setLanguage(String lang, String cnt): " + language + ", " + country);
/* C#
        Locale l = new Locale(language, country);
        selected_language = language;
        setLanguage(l); */
    }



    //  Method:       setLanguage (Locale)
    /**
     *
     *  This method sets language for control instance. If none is found, english is defaulted.
     *  if none is found, application will exit.<br><br>
     *
     *  @param lcl locale that will choose which language will be set
     */ 
    public void setLanguage(object lcl)
    {
        //Console.WriteLine("setLanguage(Locale): " + lcl);
        /* C#
        try
        {
            //Console.WriteLine("setLang: " + lang_file_root);
            res = ResourceBundle.getBundle(lang_file_root, lcl);

            if (selected_language ==null)
            {
                selected_language = lcl.getCountry();
            }
        }
        catch (MissingResourceException mre)
        {
            Console.WriteLine("Couldn't find resource file(1): " + lang_file_root + "." + selected_language + ".properties");
            try
            {
                res = ResourceBundle.getBundle(lang_file_root, new Locale(this.def_language));
            }
            catch(MissingResourceException ex)
            {
                Console.WriteLine("Exception on reading resource file. Exiting application.");
                //System.exit(2);
            }
        }
        catch (Exception mre)
        {
            Console.WriteLine("Exception on reading resource file. Exiting application.");
            Console.WriteLine("Exception: " + mre);
            mre.printStackTrace();
            System.exit(2);
        }
        
        this.selected_language_locale = lcl;
        */
    }



    /**
     * Gets the selected language locale.
     * 
     * @return the selected language locale
     */
/* c#    public Locale getSelectedLanguageLocale()
    {
        return this.selected_language_locale;
    } */





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
    private String Htmlize(String input)
    {

        StringBuilder buffer = new StringBuilder("<HTML>");

        input = input.Replace("\n", "<BR>");
        input = input.Replace("&", "&amp;");

        buffer.Append(input);
        buffer.Append("</HTML>");

        return buffer.ToString();
        
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
    public String GetMessageHTML(String msg)
    {

        String mm = this.GetMessage(msg);

        return Htmlize(mm);

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
    public String GetString(String msg)
    {
        return this.GetMessage(msg);
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
    private String ReturnSameValue(String msg)
    {
        // If we return same msg back, without beeing resolved, we put spaces before %, so
        // that it is much easier readable.
        if (msg.IndexOf("%")==-1)
            return msg;

        StringBuilder outx=new StringBuilder();
        int idx;
        while ((idx=msg.IndexOf("%"))!=-1)
        {
            outx.Append(msg.Substring(0, idx));
            outx.Append("|%");
            
            msg = msg.Substring(idx+1);
                
        }

        outx.Append(msg);

        return outx.ToString();

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
    private Object[] ResolveMnemonics(String msg)
    {

        if (msg.IndexOf("&")==-1)
            return null;


        Object[] back = new Object[2];
        int msg_length = msg.Length;
        int[] code = new int[msg_length];
//x        boolean foundDouble=false;
        bool foundMnemonic=false;


        for (int i=0;i<msg_length;i++)
        {
            if (msg.ToCharArray()[i]=='&')
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


        StringBuilder returnStr = new StringBuilder();

        int lastChange=0;

        for (int i=0; i<msg_length;i++)
        {
            
            if (code[i]==1)  // all & (tagged 1) are removed
            {
                returnStr.Append(msg.Substring(lastChange, i));
                lastChange=i+1;
                
            } 
            else if (code[i]==2) // all && are replaced with one &
            {
                returnStr.Append(msg.Substring(lastChange, i));
                returnStr.Append("&");
                lastChange=i+2; // was 2
                i=i+1;
            }
            else if (code[i]==3) // this is mnemonic
            {
                back[0] = msg.ToCharArray()[i + 1]; 
                returnStr.Append(msg.Substring(lastChange, i));
                lastChange=i+1;
            }
        }

        returnStr.Append(msg.Substring(lastChange));

        back[1] = returnStr.ToString();

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
    public bool HasMnemonic(String msg_id)
    {
        try
        {
            Object[] back = ResolveMnemonics(GetMessageFromCatalog(msg_id));

            if ((back!=null) && (back[0]!=null))
               return true;

        }
        catch //(Exception e)
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
    public Char GetMnemonic(String msg_id)
    {
        try
        {
            Object[] back = ResolveMnemonics(GetMessageFromCatalog(msg_id));

            if ((back==null) || (back[0]==null))
               return Char.MinValue;
        
            return ((Char)back[0]);
        }
        catch (Exception e)
        {
            return Char.MinValue;
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
    public String GetMessageWithoutMnemonic(String msg_id)
    {
        try
        {

            String ret = GetMessageFromCatalog(msg_id);

            Object[] back = ResolveMnemonics(ret);

            if (back==null)
                return ret;
            else
                return (String)back[1];
        }
        catch //(Exception)  ex)
        {
            return ReturnSameValue(msg_id);
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
    public String GetMessageFromCatalog(String msg)
    {
        //lock
        //{
        try
        {
            
            if (msg==null)
                return "null";

            if (this.resourceBundles.ContainsKey(msg))
            {
                return this.resourceBundles[msg];
            }
            else
            {
                s_logger.Warn("I18nControl(" + this.selected_language + "): Couldn't find message: " + msg);
                return ReturnSameValue(msg);
            }


            /*
            String ret = res.GetString(msg);

            if (ret==null)
            {
                s_logger.Warn("I18nControl(" + this.selected_language +"): Couldn't find message: " + msg);
                return ReturnSameValue(msg);
            }
            else
                return ret;
            */
        }
        catch //(Exception ex)
        {
            return ReturnSameValue(msg);
        }
    //}

    }



    //  Method:       getMessage (String)
    /**
     * 
     *  Helper method to get message from Bundle.
     * 
     *  @param msg id of message we want
     *  @return value for code, or same code back
     */    
    public String GetMessage(String msg)
    {
        return GetMessageFromCatalog(msg);
    }


    
    
    //this.m_collator = this.m_i18n.getCollationDefintion();

    
    
    /**
     * Creates the collation defintion.
     */
    public void CreateCollationDefintion()
    {
    	/*

        String col_def = this.getMessage("COLLATION_RULES");

        if (col_def.equals("COLLATION_RULES"))
        {
            //Console.WriteLine("Default collation rule !");
            this.langaugeCollator = Collator.getInstance(Locale.ENGLISH);
        }
        else
        {
            try
            {
                //Console.WriteLine(col_def);
                this.langaugeCollator =  new RuleBasedCollator(col_def);
            }
            catch(Exception ex)
            {
                //Console.WriteLine("Exception creating collator: " + ex);
                s_logger.Error("Exception creating collator: " + ex, ex);
                //log.error("Exception creating collator: " + ex, ex);
                //XA this.langaugeCollator = Collator.getInstance(Locale.ENGLISH);
            }
        }
        */
        //testCollation();

    }
    
    
    /**
     * Gets the collation defintion.
     * 
     * @return the collation defintion
     */
/* c#    public Collator GetCollationDefintion()
    {
        return this.langaugeCollator;
    }*/
   

}

}