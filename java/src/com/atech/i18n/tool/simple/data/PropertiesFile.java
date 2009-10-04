package com.atech.i18n.tool.simple.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Properties;

import com.atech.utils.file.FileReaderHashtable;

public class PropertiesFile extends FileReaderHashtable<String,String>
{

    public PropertiesFile(String filename)
    {
        super(filename);
    }
    
    
    public void readFile()
    {
        try
        {

            Properties props = new Properties();
            FileInputStream in = new FileInputStream(filename);
            props.load(in);
            
            /*
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            String line = null;
            
            while((line = br.readLine()) != null)
            {
                processFileEntry(line);
            }*/
            
            for(Enumeration<Object> en = props.keys(); en.hasMoreElements(); )
            {
                String key = (String)en.nextElement();
                this.put(key, props.getProperty(key));
            }
            //this.putAll(props.)
            
        }
        catch(Exception ex)
        {
            System.out.println("PropertiesFile: Error reading file: " + this.filename);
        }
    }
    
    
    

    @Override
    public void processFileEntry(String line)
    {
        line = line.trim();
        if ((!line.startsWith("#")) && (line.contains("=")))
        { 
            int idx = line.indexOf("=");
            this.put(line.substring(0, idx), line.substring(idx+1));
        }
    }


}
