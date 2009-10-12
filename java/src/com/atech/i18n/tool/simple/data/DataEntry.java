package com.atech.i18n.tool.simple.data;

public class DataEntry
{
    
    public static final int STATUS_UNTRANSLATED = 0;
    public static final int STATUS_NEED_CHECK = 1;
    public static final int STATUS_TRANSLATED = 2;
    
    
    
    public String key = null;
    DataEntryRaw group = null;
    public String master_file_translation;
    public String description = null;
    public String target_translation = "";
    public int status = 0;
    public long invalidated = 0L;
    
    public DataEntry(String _key, String mast_translation)
    {
        this.key = _key;
        this.master_file_translation = mast_translation;
    }
    
    
    
}
