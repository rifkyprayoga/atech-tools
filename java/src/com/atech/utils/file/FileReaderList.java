package com.atech.utils.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public abstract class FileReaderList<E> extends ArrayList<E>
{

    //private String master_file_name = "GGC_en.properties";
    
    //ArrayList<Object> list = null;
    public String filename;
    
    public FileReaderList(String _filename)
    {
        this.filename = _filename;
        specialInit();
        readFile();
    }
    
    public abstract void specialInit();
    
    
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
            ex.printStackTrace();
        }
    }
    
    
    public abstract void processFileEntry(String line);
    

}

