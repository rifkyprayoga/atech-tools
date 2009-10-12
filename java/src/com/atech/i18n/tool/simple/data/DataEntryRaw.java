package com.atech.i18n.tool.simple.data;

// TODO: Auto-generated Javadoc
/**
 * The Class DataEntryRaw.
 */
public class DataEntryRaw
{
    
    /**
     * The Constant DATA_ENTRY_HEADER.
     */
    public static final int DATA_ENTRY_HEADER = 1;
    
    /**
     * The Constant DATA_ENTRY_GROUP.
     */
    public static final int DATA_ENTRY_GROUP = 2;
    
    /**
     * The Constant DATA_ENTRY_SUBGROUP.
     */
    public static final int DATA_ENTRY_SUBGROUP = 3;
    
    /**
     * The Constant DATA_ENTRY_TRANSLATION.
     */
    public static final int DATA_ENTRY_TRANSLATION = 4;
    
    /**
     * The type.
     */
    public int type = 0;

    /**
     * The value_str.
     */
    public String value_str = "";
    
    /**
     * The value_int.
     */
    public int value_int;
    
    /**
     * Instantiates a new data entry raw.
     * 
     * @param _type the _type
     */
    public DataEntryRaw(int _type)
    {
        this.type = _type;
    }

    
    /**
     * Instantiates a new data entry raw.
     * 
     * @param _type the _type
     * @param value the value
     */
    public DataEntryRaw(int _type, String value)
    {
        this.type = _type;
        this.value_str = value;
    }
    
}
