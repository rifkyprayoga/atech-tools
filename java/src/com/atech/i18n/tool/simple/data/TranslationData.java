package com.atech.i18n.tool.simple.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class TranslationData
{
    
    public ArrayList<DataEntry> list_tra = null;
    public Hashtable<String,DataEntry> dt_tra = null;

    
    
    public TranslationData()
    {
        list_tra = new ArrayList<DataEntry>();
        dt_tra = new Hashtable<String,DataEntry>();
        
    }

    
    public void addTranslationData(DataEntry de)
    {
        this.list_tra.add(de);
        this.dt_tra.put(de.key, de);
    }
    

    public boolean isEmpty()
    {
        return (this.list_tra.size()==0);
    }
    
    public DataEntry get(String key)
    {
        return this.dt_tra.get(key);
    }


    public Enumeration<DataEntry> elements()
    {
        return this.dt_tra.elements();

    }
    
    
    public boolean containsKey(String key)
    {
        return this.dt_tra.containsKey(key);
    }
    
    
    public DataEntry get(int index)
    {
        return this.list_tra.get(index);
    }
    
    
    public int size()
    {
        return this.list_tra.size();
    }
    
    public void save()
    {
        saveTranslation();
        saveSettings();
    }
    
    public void saveTranslation()
    {
        try
        {
            //BufferedWriter bw = new BufferedWriter(new FileWriter("./files/translation/GGC_si.properties"));
        
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("./files/translation/GGC_si.properties")),"ASCII"));
            //String unicode = "Unicode: \u30e6\u30eb\u30b3\u30fc\u30c9";
            //byte[] bytes = String.getBytes("US_ASCII");

            
            for(int i=0; i<this.list_tra.size(); i++)
            {
                DataEntry de = this.list_tra.get(i);
                
                bw.write(de.key + "=");
                
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
            System.out.println("TranslationData.saveTranslation(). Exception: "+ ex);
            ex.printStackTrace();
        }
        
        
        System.out.println("TranslationData.saveTranslation() NOT FULLY implemented !");
    }
    
    public void saveSettings()
    {
        System.out.println("TranslationData.saveSettings() NOT implemented !");
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
            
            //System.out.println(bb[i]);
            //System.out.println(isNotRegularAscii(value.charAt(i)));
            //System.out.println(charToHex(value.charAt(i)));
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
