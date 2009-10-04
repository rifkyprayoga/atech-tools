package com.atech.i18n.tool.simple.data;

import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.utils.file.FileReaderList;

public class MasterFileReader extends FileReaderList<DataEntryRaw>
{

    DataAccessTT m_da = null;
    
    public MasterFileReader(String filename)
    {
        super(filename);
    }

    public void specialInit()
    {
        this.add(new DataEntryRaw(1));
        
        this.m_da = DataAccessTT.getInstance();
        //System.out.println("m_da: " + m_da);
    }
    
    
    @Override
    public void processFileEntry(String line)
    {
        //System.out.println("Process: " + line);
        line = line.trim();
        if (line.startsWith("#"))
        { 
            // comments
            // TODO
            
            
            
            
        }
        else if (line.contains("="))
        {
            // keywords
            int idx = line.indexOf("=");
            
            String key = line.substring(0, idx);
            String val = line.substring(idx+1);
            
            this.add(new DataEntryRaw(DataEntryRaw.DATA_ENTRY_TRANSLATION, key));
            
            DataEntry de = new DataEntry(key, val);
            
            m_da.getTranslationData().addTranslationData(de);
        }
        // else ignore
        
    }

}
