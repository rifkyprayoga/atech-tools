package com.atech.i18n.tool.simple.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.tool.simple.util.DataAccessTT;


/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  TranslationData - Collection of translation entries
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

public class TranslationData
{

    private static Log log = LogFactory.getLog(TranslationData.class);
    
    /**
     * The list_tra.
     */
    public ArrayList<DataEntry> list_tra = null;
    
    /**
     * The dt_tra.
     */
    public Hashtable<String,DataEntry> dt_tra = null;

    /**
     * The statuses_info.
     */
    public int statuses_info[] = { 0, 0, 0};
    
    
    DataAccessTT m_da = DataAccessTT.getInstance();
    
    /**
     * Instantiates a new translation data.
     */
    public TranslationData()
    {
        list_tra = new ArrayList<DataEntry>();
        dt_tra = new Hashtable<String,DataEntry>();
        
    }

    
    /**
     * Adds the translation data.
     * 
     * @param de the de
     */
    public void addTranslationData(DataEntry de)
    {
        this.list_tra.add(de);
        this.dt_tra.put(de.key, de);
    }
    

    /**
     * Checks if is empty.
     * 
     * @return true, if is empty
     */
    public boolean isEmpty()
    {
        return (this.list_tra.size()==0);
    }
    
    /**
     * Gets the.
     * 
     * @param key the key
     * 
     * @return the data entry
     */
    public DataEntry get(String key)
    {
        return this.dt_tra.get(key);
    }


    /**
     * Elements.
     * 
     * @return the enumeration< data entry>
     */
    public Enumeration<DataEntry> elements()
    {
        return this.dt_tra.elements();

    }
    
    
    /**
     * Contains key.
     * 
     * @param key the key
     * 
     * @return true, if successful
     */
    public boolean containsKey(String key)
    {
        return this.dt_tra.containsKey(key);
    }
    
    
    /**
     * Gets the.
     * 
     * @param index the index
     * 
     * @return the data entry
     */
    public DataEntry get(int index)
    {
        return this.list_tra.get(index);
    }
    
    
    /**
     * Size.
     * 
     * @return the int
     */
    public int size()
    {
        return this.list_tra.size();
    }
    
    /**
     * Save.
     */
/*    public void save()
    {
        saveTranslation();
        saveSettings();
    } */

    /**
     * Save
     * 
     * @param dlp 
     */
    public void save(DataListProcessor dlp)
    {
        saveTranslation(dlp, false, null);
        saveSettings(dlp, false, null);
    }
    
    /**
     * Save Backup
     * 
     * @param dlp 
     */
    public void saveBackup(DataListProcessor dlp)
    {
        GregorianCalendar gc = new GregorianCalendar();
        saveTranslation(dlp, true, gc);
        saveSettings(dlp, true, gc);
    }

    
    /**
     * Save translation.
     */
    public void saveTranslation_Oldxxx()
    {
        try
        {
            //BufferedWriter bw = new BufferedWriter(new FileWriter("./files/translation/GGC_si.properties"));
        
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("../files/translation/GGC_si.properties")),"ASCII"));
            //String unicode = "Unicode: \u30e6\u30eb\u30b3\u30fc\u30c9";
            //byte[] bytes = String.getBytes("US_ASCII");

            
            for(int i=0; i<this.list_tra.size(); i++)
            {
                DataEntry de = this.list_tra.get(i);
                
                bw.write(de.key + "=");
 
                //  TODO: if no translation - English
                bw.write(getTranslationEncoded(de.target_translation));
                
                /*
                byte bb[] = de.target_translation.getBytes("ASCII");
                
                for(int j=0; j<bb.length; j++)
                {
                    bw.write(bb[j]);
                }
                */
                bw.newLine();
                bw.flush();
                //bw.write(de.target_translation.getBytes("US_ASCII"));
                
                
                //+ de.target_translation + "\n");
                
            }
            
            bw.close();
            
        }
        catch(Exception ex)
        {
            log.error("TranslationData.saveTranslation(). Exception: "+ ex, ex);
        }
    }

    
    /**
     * Save Translation
     * 
     * @param dlp
     * @param backup
     */
    public void saveTranslation(DataListProcessor dlp, boolean backup, GregorianCalendar gc)
    {
        
        String add_path = "";
        String filename = dlp.getTargetFileRoot();
        
        if (backup)
        {
            add_path = "backup/";
            filename += "_" + getDateTimeFilename(gc);
            log.debug("Save Translation (backup: " + m_da.getCurrentDateTimeString() + ")");
        }
        else
        {
            log.debug("Save Translation");
        }
        
        try
        {
            //BufferedWriter bw = new BufferedWriter(new FileWriter("./files/translation/GGC_si.properties"));
        
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("../files/translation/" + add_path + filename + ".properties")),"ASCII"));
            //String unicode = "Unicode: \u30e6\u30eb\u30b3\u30fc\u30c9";
            //byte[] bytes = String.getBytes("US_ASCII");

            MasterFileReader mfr = dlp.mfr;
            
            //dlp.getHeader();
            
            for(int i=0; i<mfr.size(); i++)
            {
                DataEntryRaw der = mfr.get(i);
                
                String v = der.getValue();
                
                if (v==null)
                {
                    if (der.type==DataEntryRaw.DATA_ENTRY_HEADER)
                    {
                        bw.write(dlp.getFullHeader());
                        //bw.write("#\n#\n#  Header is still missing !!!\n#\n#\n#\n");
                    }
                    else if (der.type==DataEntryRaw.DATA_ENTRY_TRANSLATION)
                    {
                        String key = der.value_str;
                        
                        DataEntry de = this.get(key);
                        
                        bw.write(de.key + "=");
         
                        if ((de.target_translation==null) || (de.target_translation.trim().length()==0))
                        {
                            bw.write(getTranslationEncoded(de.master_file_translation));
                        }
                        else
                        {
                            bw.write(getTranslationEncoded(de.target_translation));
                        }
                        
                        bw.newLine();
                        bw.flush();
                    }
                    
                }
                else
                    bw.write(v);
                
            }
            
            
            
            /*
            for(int i=0; i<this.list_tra.size(); i++)
            {
                DataEntry de = this.list_tra.get(i);
                
                bw.write(de.key + "=");
 
                //  TODO: if no translation - English
                bw.write(getTranslationEncoded(de.target_translation));
                
                bw.newLine();
                bw.flush();
                //bw.write(de.target_translation.getBytes("US_ASCII"));
                
                
                //+ de.target_translation + "\n");
                
            } */
            
            bw.close();
            
        }
        catch(Exception ex)
        {
            log.error("TranslationData.saveTranslation(). Exception: "+ ex, ex);
        }
    }
    
    
    private String getDateTimeFilename(GregorianCalendar gc)
    {
        return "" + gc.get(Calendar.YEAR) + "_" + m_da.getLeadingZero(gc.get(Calendar.MONTH), 2) + "_" + m_da.getLeadingZero(gc.get(Calendar.DAY_OF_MONTH), 2) + "_" + m_da.getLeadingZero(gc.get(Calendar.HOUR_OF_DAY), 2) + "_" + m_da.getLeadingZero(gc.get(Calendar.MINUTE), 2);   

    }
    
    
    /**
     * Save settings
     * 
     * @param dlp 
     * @param backup 
     */
    public void saveSettings(DataListProcessor dlp, boolean backup, GregorianCalendar gc)
    {
        
        String add_path = "";
        String filename = dlp.getTargetFileRoot();
        
        if (backup)
        {
            add_path = "backup/";
            filename += "_" + getDateTimeFilename(gc);
            log.debug("Save Settings (backup: " + m_da.getCurrentDateTimeString() + ")");
        }
        else
        {
            log.debug("Save Settings");
        }
        
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("../files/translation/" + add_path + filename + ".config")),"ASCII"));
            
            for(int i=0; i<this.list_tra.size(); i++)
            {
                DataEntry de = this.list_tra.get(i);
                
                if (de.invalidated>0)
                    bw.write(de.key + "__INVALIDATED=" + de.invalidated+ "\n");
                
                bw.write(de.key + "__STATUS=" + de.status);
                bw.newLine();
                bw.flush();
            }
            
            bw.close();
            
        }
        catch(Exception ex)
        {
            log.error("TranslationData.saveTranslation(). Exception: "+ ex, ex);
        }
    }
    
    
    /**
     * Reset status.
     */
    public void resetStatus()
    {
        this.statuses_info[0] = 0;
        this.statuses_info[1] = 0;
        this.statuses_info[2] = 0;

        for(int i=0; i<this.list_tra.size(); i++)
        {
            DataEntry de = this.list_tra.get(i);
            this.statuses_info[de.status] = this.statuses_info[de.status]+1;
        }
    }
    
    /**
     * Gets the statuses.
     * 
     * @return the statuses
     */
    public int[] getStatuses()
    {
        return this.statuses_info;
    }
    
    
    private String getTranslationEncoded(String value)
    {
        value = value.replace("\n", "\\n");
        value = value.replace("\r", " ");

        return unicodeToASCII(value);
    }
    

    private String unicodeToASCII(String value)
    {
        StringBuffer sb = new StringBuffer();
        
        for(int i=0; i<value.length(); i++)
        {
            //if ((!Character.isUnicodeIdentifierPart(bb[i])) && (bb[i] < 0) )//|| (Character.i)
            if (isNotRegularAscii(value.charAt(i)))
                sb.append("\\u" + charToHex(value.charAt(i)).toUpperCase());
            else
                sb.append(value.charAt(i));
        }
        
        return sb.toString();
        
    }
    
    
    
    
    private String byteToHex(byte b) 
    {
        // Returns hex String representation of byte b
        char hexDigit[] = {
           '0', '1', '2', '3', '4', '5', '6', '7',
           '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
     }

    
    private boolean isNotRegularAscii(char c)
    {
        byte hi = (byte) (c >>> 8);
        return (hi!=0);
    }
    
    private String charToHex(char c) 
    {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
     }
    

    
    /*
    private String decode_v2(String str)
    {
    
    byte[] b = str.getBytes();
    Charset def = Charset.defaultCharset(); //  default encoding 
    Charset cs = Charset.forName( "ASCII"); // encoding 
    ByteBuffer bb = ByteBuffer.wrap( b );
    CharBuffer cb = cs.decode( bb );
    String s = cb.toString();
    return s;
    }
    
    private String decode_v1( String str )
    {
        System.out.println(str);
        byte[] input = str.getBytes();
    char[] output = new char[input.length];
    // index input[]
    int i = 0;
    // index output[]
    int j = 0;
    while ( i < input.length )
        {
        // get next byte unsigned
        int b = input[ i++ ] & 0xff;
        // classify based on the high order 3 bits
        switch ( b >>> 5 )
            {
            default:
                // one byte encoding
                // 0xxxxxxx
                // use just low order 7 bits
                // 00000000 0xxxxxxx
                output[ j++ ] = ( char ) ( b & 0x7f );
                break;
            case 6:
                // two byte encoding
                // 110yyyyy 10xxxxxx
                // use low order 6 bits
                int y = b & 0x1f;
                // use low order 6 bits of the next byte
                // It should have high order bits 10, which we don't check.
                int x = input[ i++ ] & 0x3f;
                // 00000yyy yyxxxxxx
                output[ j++ ] = ( char ) ( y << 6 | x );
                break;
            case 7:
                // three byte encoding
                // 1110zzzz 10yyyyyy 10xxxxxx
                assert ( b & 0x10 )
                       == 0 : "UTF8Decoder does not handle 32-bit characters";
                // use low order 4 bits
                int z = b & 0x0f;
                // use low order 6 bits of the next byte
                // It should have high order bits 10, which we don't check.
                y = input[ i++ ] & 0x3f;
                // use low order 6 bits of the next byte
                // It should have high order bits 10, which we don't check.
                x = input[ i++ ] & 0x3f;
                // zzzzyyyy yyxxxxxx
                int asint = ( z << 12 | y << 6 | x );
                output[ j++ ] = ( char ) asint;
                break;
            }// end switch
        }// end while
    return new String( output, 0
    // offset 
     , j
     // count  
      );
      
    }
    */
    
    
    
}
