package com.atech.utils.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;



public abstract class FileReaderHashtable<K,V> extends Hashtable<K,V>
{

    //private String master_file_name = "GGC_en.properties";
    
    //ArrayList<Object> list = null;
    public String filename;
    
    public FileReaderHashtable(String _filename)
    {
        this.filename = _filename;
        readFile();
    }
    
    
    
    public void readFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            String line = null;
            
            while((line = br.readLine()) != null)
            {
                processFileEntry(line);
            }
            
            br.close();
            
        }
        catch(Exception ex)
        {
            System.out.println("FileReaderList: Error reading file: " + this.filename);
        }
    }
    
    
    public abstract void processFileEntry(String line);
    

}


