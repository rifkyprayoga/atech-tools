package com.atech.i18n.tool.dev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class DuplicateMasterFileKeywordsFinder
{

    ArrayList<String> list_keywords = null;
    Hashtable<String, String> list_duplicates = null;
    String filename = null;
    
    public DuplicateMasterFileKeywordsFinder(String filename_)
    {
        list_keywords = new ArrayList<String>();
        list_duplicates = new Hashtable<String, String>();
        this.filename = filename_;
        this.readFile(filename);
        checkForDuplicates();
        reportResults();
    }
    
    
    public void readFile(String filename)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            
            while((line = br.readLine()) != null)
            {
                line = line.trim();
                
                if (!line.startsWith("#"))
                {
                    if (line.contains("="))
                    {
                        line = line.substring(0, line.indexOf("="));
                        this.list_keywords.add(line);
                    }
                }
            }
            
        }
        catch(Exception ex)
        {
            System.out.println("Exception: " + ex);
        }
    }
    
    
    private void checkForDuplicates()
    {
        ArrayList<String> lst_cp = (ArrayList<String>)this.list_keywords.clone();
        
        for(int i=0; i<lst_cp.size(); i++)
        {
            String key_check = lst_cp.get(i);
            
            for(int j=i+1; j<this.list_keywords.size(); j++)
            {
                String key_2 = this.list_keywords.get(j);
                
                if ((key_2.equals(key_check)) && (!this.list_duplicates.containsKey(key_2)))
                {
                    this.list_duplicates.put(key_2, key_2);
                }
            }
        }
        
    }
    
    private void reportResults()
    {
        System.out.println("Results for " + this.filename);
        
        if (this.list_duplicates.size()==0)
        {
            System.out.println(" No duplicated found !");
        }
        else
        {
            for(Enumeration<String> en = this.list_duplicates.keys(); en.hasMoreElements(); )
            {
                System.out.println(" " + en.nextElement());
            }
        }
    }
    
    
    
    public static void main(String[] args)
    {
        new DuplicateMasterFileKeywordsFinder(args[0]);
    }
    
}
