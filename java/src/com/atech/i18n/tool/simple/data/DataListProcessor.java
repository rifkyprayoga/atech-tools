package com.atech.i18n.tool.simple.data;

import java.util.Enumeration;

import com.atech.i18n.tool.simple.util.DataAccessTT;


public class DataListProcessor
{

    DataAccessTT m_da = DataAccessTT.getInstance();
    TranslationData tra_data;
    
    
    private String master_file_name = "GGC_en.properties";
    
    MasterFileReader mfr = null;
    
    public DataListProcessor(String _main_config)
    {

        tra_data = new TranslationData();
        this.m_da.translation_data = tra_data ;

        System.out.println("this.m_da.translation_data" + this.m_da.translation_data);
        
        readConfiguration(_main_config);
        
        
        readMasterFile();
        readMasterFileConfig();
        
        this.tra_data = m_da.getTranslationData(); 
        readTranslationTarget();
        readTranslationTargetConfig();
        
        System.out.println(this.tra_data.size());
    }

    
    public void readConfiguration(String main_config)
    {
        System.out.println("DataListProcessor.readConfiguration() NOT implemented !");
        //PropertiesFile pp = new PropertiesFile("GGC_en.properties");
    }
    
    
    
    public void readMasterFile()
    {
        
        mfr = new MasterFileReader(master_file_name);
        
        
        /*
        System.out.println("DataListProcessor.readMasterFile()");
        
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(master_file_name));
            String line = null;
            
            while((line = br.readLine()) != null)
            {
                line = line.trim();
                if (line.startsWith("#"))
                { 
                    // comments
                    
                }
                else if (line.contains("="))
                {
                    // keywords
                }
                // else ignore
                
                
            }
            
            
        }
        catch(Exception ex)
        {
            System.out.println("Error reading master file: " + "");
        }
        */
        
    }

    
    public void readMasterFileConfig()
    {
        System.out.println("DataListProcessor.readMasterFileConfig() NOT implemented !");
    }
    
    
    
    public void readTranslationTarget()
    {
        PropertiesFile pp = new PropertiesFile("GGC_si.properties");
        
        for(Enumeration<String> en = pp.keys(); en.hasMoreElements(); )
        {
            String key = en.nextElement();
            
            if (this.tra_data.containsKey(key))
            {
               DataEntry de = this.tra_data.get(key);
               de.target_translation = pp.get(key);
            }
            
            
        }
        
        
        
        
        //System.out.println("DataListProcessor.readTranslationTarget() NOT implemented !");
        
    }
    
    public void readTranslationTargetConfig()
    {
        System.out.println("DataListProcessor.readTranslationTargetConfig() NOT implemented !");
    }
    
    
    int current_index;
    
    
    public DataEntry getCurrentEntry()
    {
        return this.tra_data.get(this.current_index);
    }
    
    
    public boolean moveFirst()
    {
        current_index = 0;
        return !(this.tra_data.isEmpty());
    }
    
    
    public boolean moveNext()
    {
        if (this.tra_data.isEmpty())
            return false;
        
        
        if (current_index == (this.tra_data.size()-1))
        {
            this.current_index = 0;
            return true;
        }
        else
        {
            this.current_index++;
            return true;
        }
            
    }
    
    public boolean moveNextUntranslated()
    {
        if (this.tra_data.isEmpty())
            return false;

        System.out.println("DataListProcessor.moveNextUntranslated()");
        return false;
    }
    
    
    public boolean movePrev()
    {
        if (this.tra_data.isEmpty())
            return false;

        if (current_index == 0)
        {
            this.current_index = this.tra_data.size()-1;
            return true;
        }
        else
        {
            this.current_index--;
            return true;
        }
    }
    
    public boolean movePrevUntranslated()
    {
        if (this.tra_data.isEmpty())
            return false;

        System.out.println("DataListProcessor.movePrevUntranslated()");
        return false;
    }
    
    
    public void saveTranslation()
    {
        this.tra_data.save();
    }
    
    

}
