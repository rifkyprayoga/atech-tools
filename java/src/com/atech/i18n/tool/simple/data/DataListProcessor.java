package com.atech.i18n.tool.simple.data;

import java.util.Enumeration;

import com.atech.i18n.tool.simple.util.DataAccessTT;


public class DataListProcessor
{

    DataAccessTT m_da = DataAccessTT.getInstance();
    TranslationData tra_data;
    
    
    private String master_file_name = "GGC_en";
    
    MasterFileReader mfr = null;
    
    private boolean master_file_read = false;
    public boolean translation_file_read = false;
    private int current_index;
    
    private boolean main_configuration_read = false;
    private boolean user_configuration_read = false;
    
    
    public DataListProcessor(String _main_config)
    {

        tra_data = new TranslationData();
        this.m_da.translation_data = tra_data ;

        //System.out.println("this.m_da.translation_data" + this.m_da.translation_data);
        
        readConfiguration(_main_config);
        readUserConfiguration();
        
        
        readMasterFile();
        readMasterFileConfig();
        
        this.tra_data = m_da.getTranslationData(); 
        readTranslationTarget();
        readTranslationTargetConfig();
        
        System.out.println(this.tra_data.size());
    }

    
    public boolean wasMasterFileRead()
    {
        return this.master_file_read;
    }
    
    
    public void readConfiguration(String main_config)
    {
        System.out.println("DataListProcessor.readConfiguration() NOT implemented !");
        //PropertiesFile pp = new PropertiesFile("GGC_en.properties");
        this.main_configuration_read = false;
    }
    
    public void readUserConfiguration()
    {
        System.out.println("DataListProcessor.readUserConfiguration() NOT implemented !");
        this.user_configuration_read = false;
    }
    
    
    
    
    
    public void readMasterFile()
    {
        mfr = new MasterFileReader("files/master_files/" + master_file_name + ".properties");
        master_file_read = mfr.wasFileRead();
        
        //is_master_file_mf = mfr.is_master_file;
        
        System.out.println("Master File: " + mfr.isMasterFile());
        
        
    }

    
    public boolean isMasterFileMasterFile()
    {
        return this.mfr.isMasterFile();
    }
    
    public void readMasterFileConfig()
    {
        //System.out.println("DataListProcessor.readMasterFileConfig() NOT implemented !");
        
        PropertiesFile pp = new PropertiesFile("files/master_files/" + master_file_name + ".config");
        
        //this.translation_file_read = pp.file_read;
        int idx = -1;
        String key_sub;
        
        for(Enumeration<String> en = pp.keys(); en.hasMoreElements(); )
        {
            String key = en.nextElement();
            
            if (key.contains("__DESCRIPTION"))
            {
                idx = key.indexOf("__DESCRIPTION"); 
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);
                de.description = pp.get(key);
            }
            else if (key.contains("__INVALIDATED"))
            {
                idx = key.indexOf("__INVALIDATED"); 
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);
                de.invalidated = Long.parseLong(pp.get(key));
            }
            else
            {
                System.out.println("This key type is unknown !!!");
            }
            
        }
        
        
    }
    
    
    
    private void readTranslationTarget()
    {
        PropertiesFile pp = new PropertiesFile("files/translation/" + "GGC_si.properties");
        
        this.translation_file_read = pp.file_read;
        
        
        for(Enumeration<String> en = pp.keys(); en.hasMoreElements(); )
        {
            String key = en.nextElement();
            
            if (this.tra_data.containsKey(key))
            {
               DataEntry de = this.tra_data.get(key);
               
               //value = value.replace("\n", "\\n");
               de.target_translation = pp.get(key).replace("\n", "\\n");
               //de.target_translation = pp.get(key);
            }
        }
        
    }

    
    
    
    
    private void readTranslationTargetConfig()
    {
        System.out.println("DataListProcessor.readTranslationTargetConfig() NOT implemented !");

        PropertiesFile pp = new PropertiesFile("files/translation/" + "GGC_si" + ".config");
        
        this.translation_file_read = pp.file_read;

        if (!pp.file_read)
        {
            this.processTranslationData(true);
            return;
        }
        
        int idx = -1;
        String key_sub = "";
        
        for(Enumeration<String> en = pp.keys(); en.hasMoreElements(); )
        {
            String key = en.nextElement();
            
            if (key.contains("__STATUS"))
            {
                idx = key.indexOf("__STATUS"); 
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);
                de.status = Integer.parseInt(pp.get(key));
            }
            else if (key.contains("__INVALIDATED"))
            {
                idx = key.indexOf("__INVALIDATED"); 
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);
                
                long inv = Long.parseLong(pp.get(key));
                
                if ((de.invalidated!=0) && (de.invalidated!=inv))
                {
                    if (de.status!=DataEntry.STATUS_UNTRANSLATED)
                    {
                        de.status = DataEntry.STATUS_NEED_CHECK;
                    }
                }
            }
            else
            {
                System.out.println("This key type is unknown !!!");
            }
            
        }
    
        //this.processTranslationData(false);
    
    }
    

    private void processTranslationData(boolean check_status)
    {
        //System.out.println("DataListProcessor.processTranslationData() NOT implemented !");
        
        for(int i=0; i<this.tra_data.size(); i++)
        {
         
            DataEntry de = this.tra_data.get(i);
            
            if (de.status==0)
            {
                if (de.target_translation.equals(de.master_file_translation))
                {
                    de.status = DataEntry.STATUS_NEED_CHECK;
                }
                else if (!de.target_translation.equals(""))
                {
                    de.status = DataEntry.STATUS_TRANSLATED;
                }
            }
            
        }
        
    }
    
    public void resetStatus()
    {
        this.tra_data.resetStatus();
    }
    
    public int[] getStatuses()
    {
        return this.tra_data.getStatuses();
    }
    
    public DataEntry getCurrentEntry()
    {
        if (this.tra_data.size()==0)
            return null;
        else
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
    
    
    public int getCurrentIndex()
    {
        return this.current_index;
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
