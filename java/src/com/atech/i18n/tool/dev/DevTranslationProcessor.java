package com.atech.i18n.tool.dev;

import java.util.Hashtable;

import com.atech.i18n.tool.simple.data.DataListProcessor;
import com.atech.i18n.tool.simple.data.MasterFileReader;

public class DevTranslationProcessor extends DataListProcessor
{

    public DevTranslationProcessor()
    {
        super(new Hashtable<String,String>());
        
        
        /*
        tra_data = new TranslationData();
        
        master_file_root = pp.get("MASTER_FILE_ROOT");
        master_file_name = master_file_root + "_" + pp.get("MASTER_FILE_LANGUAGE"); 
        module_id = pp.get("MODULE_ID");        
        
        
        readMasterFile();
        readMasterFileConfig();
        
        */
        
        
    }

    
    public void processLangaugeFile(MasterFileReader mfr_)
    {
        /*
        
        
        tra_data = new TranslationData();
        this.mfr = mfr_;
        readMasterFileConfig();
        
        */
    }
    
    
    
    public void temmpp()
    {
     /*   
        // get Master file
        // read master file config
        sb.append("#  ###    Language: " + getTextLine(this.user_config.get("TRANSLATION_LANGUAGE"), 50) + "###\n"); 
        sb.append("#  ###    Created by: " + getTextLine(this.user_config.get("TRANSLATOR_NAME") + " (" + this.user_config.get("TRANSLATOR_EMAIL") + ")", 48) + "###\n");
        sb.append("#  ###    Version: " + getTextLine(m_da.getTranslationConfig().get("MODULE_VERSION"), 51) + "###\n");
        sb.append("#  ###    Last change: " + getTextLine(m_da.getCurrentDateTimeString(), 47) + "###\n");
        sb.append(this.user_config.get("COLLATION_RULES") + "\n");
        
        
        if (this.user_config.containsKey("HEADER_COMMENT_" +part+ i))
        {
            header += "#  " + this.user_config.get("HEADER_COMMENT_" +part+ i) + "\n";
            i++;
        }
        else
            end = false;

        return this.master_file_root + "_" + this.user_config.get("TRANSLATION_LANGUAGE_SHORT");
        
       */ 
        
    }
    
    
}
