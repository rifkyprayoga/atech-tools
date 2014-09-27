package com.atech.i18n.tool.simple.data;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  DataEntry - Data Entry in application
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

public class DataEntry
{

    /**
     * Status: Untranslated
     */
    public static final int STATUS_UNTRANSLATED = 0;

    /**
     * Status: Need Check
     */
    public static final int STATUS_NEED_CHECK = 1;

    /**
     * Status: Translated
     */
    public static final int STATUS_TRANSLATED = 2;

    /**
     * Key
     */
    public String key = null;

    /**
     * Group
     */
    DataEntryRaw group = null;

    /**
     * Master File Translation
     */
    public String master_file_translation;

    /**
     * Description 
     */
    public String description = null;

    /**
     * Target translation 
     */
    public String target_translation = "";

    /**
     * Status of translation 
     */
    public int status = 0;

    /**
     * Invalidated
     */
    public long invalidated = 0L;

    /**
     * Data Entry Raw
     */
    public DataEntryRaw der;

    /**
     * Constructor
     * 
     * @param _key
     * @param mast_translation
     * @param _der
     */
    public DataEntry(String _key, String mast_translation, DataEntryRaw _der)
    {
        this.key = _key;
        this.master_file_translation = mast_translation;
        this.der = _der;
    }

    /**
     * Get Group Info
     * @return
     */
    public String getGroupInfo()
    {
        return this.der.getGroupInfo();
    }

    /**
     * Get Sub Group Info
     * @return
     */
    public String getSubGroupInfo()
    {
        return this.der.getSubGroupInfo();
    }

    /**
     * Get Priority
     * 
     * @return
     */
    public String getPriority()
    {
        return this.der.getPriority();
    }

}
