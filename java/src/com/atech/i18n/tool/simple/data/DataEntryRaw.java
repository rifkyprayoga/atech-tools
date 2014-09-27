package com.atech.i18n.tool.simple.data;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  DataEntryRaw - Data Entry Raw (as read from file)
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
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

    int priority = -1;
    DataEntryRaw group = null;
    DataEntryRaw sub_group = null;

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
     * @param _type type of entry
     * @param value value
     * @param _group group  
     * @param _sub_group sub group
     */
    public DataEntryRaw(int _type, String value, DataEntryRaw _group, DataEntryRaw _sub_group)
    {
        this.type = _type;
        this.value_str = value;

        this.group = _group;
        this.sub_group = _sub_group;

    }

    /**
     * Instantiates a new data entry raw.
     * 
     * @param _type type of entry
     * @param value value
     * @param _priority priority
     */
    public DataEntryRaw(int _type, String value, int _priority)
    {
        this.type = _type;
        this.value_str = value;
        this.priority = _priority;
    }

    /**
     * Instantiates a new data entry raw.
     * 
     * @param _type type of entry
     * @param value value
     * @param _group group
     */
    public DataEntryRaw(int _type, String value, DataEntryRaw _group)
    {
        this.type = _type;
        this.value_str = value;

        this.group = _group;
    }

    /**
     * Get Group Info
     * @return
     */
    public String getGroupInfo()
    {
        if (group != null)
            return group.value_str;
        else
            return "Unknown group";

    }

    /**
     * Get SubGroup Info
     * @return
     */
    public String getSubGroupInfo()
    {
        if (this.sub_group != null)
            return this.sub_group.value_str;
        else
            return " - ";
    }

    /**
     * get Priority
     * 
     * @return
     */
    public String getPriority()
    {
        if (this.type == DataEntryRaw.DATA_ENTRY_GROUP)
            return "" + this.priority;
        else if (this.type == DataEntryRaw.DATA_ENTRY_TRANSLATION)
        {
            if (group != null)
                return this.group.getPriority();
            else
                return "0";

        }
        else
            return "-1";
    }

    /**
     * Get Value
     * 
     * @return
     */
    public String getValue()
    {
        if (this.type == DATA_ENTRY_HEADER || this.type == DATA_ENTRY_TRANSLATION)
            return null;
        else if (this.type == DATA_ENTRY_GROUP)
            return "\n#\n#   " + this.value_str + "  [" + this.priority + "]\n#\n";
        else
            return "\n#   " + this.value_str + "\n";
    }

}
