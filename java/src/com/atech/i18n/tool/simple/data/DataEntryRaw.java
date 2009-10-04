package com.atech.i18n.tool.simple.data;

public class DataEntryRaw
{
    
    public static final int DATA_ENTRY_HEADER = 1;
    public static final int DATA_ENTRY_GROUP = 2;
    public static final int DATA_ENTRY_SUBGROUP = 3;
    public static final int DATA_ENTRY_TRANSLATION = 4;
    
    public int type = 0;

    public String value_str = "";
    public int value_int;
    
    public DataEntryRaw(int _type)
    {
        this.type = _type;
    }

    
    public DataEntryRaw(int _type, String value)
    {
        this.type = _type;
        this.value_str = value;
    }
    
}
