package com.atech.db.hibernate.transfer;

import java.util.Hashtable;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ImportExportDefinition - Import Export Definition
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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

// this one should be extended, we have several variables that need to be filled

@Deprecated
public abstract class ImportExportDefinition
{

    /**
     * Allowed classes for export/import
     * 
     * Second parameter gives properties
     */
    public Hashtable<String, Integer> class_defs = null;

    /**
     * The Constant CP_NONE.
     */
    public static final int CP_NONE = 0;

    /**
     * The Constant CP_TYPE_SELF.
     */
    public static final int CP_TYPE_SELF = 1;

    /**
     * The Constant CP_TYPE_EXTENDED.
     */
    public static final int CP_TYPE_EXTENDED = 2;

    /**
     * The Constant CP_TYPE_COLLECTIONS.
     */
    public static final int CP_TYPE_COLLECTIONS = 4;

    /**
     * The Constant CP_IMPORT_DELETE.
     */
    public static final int CP_IMPORT_DELETE = 8;

    /**
     * The Constant CP_IMPORT_UPDATE.
     */
    public static final int CP_IMPORT_UPDATE = 16;


    /**
     * Type of class: 
     *   1=SIMPLE = class contains data that is not linked to anyother
     *   2=SELF = class is only linked to itself
     *   3=EXTENDED = class is linked to other classes
     *   4=EXTENDED_PLUS = class is linked to self and other classes
     *   5=COLLECTIONS (unallowed)
     * @return 
     */
    public int getClassType()
    {
        return 0;

        // if public static final int CP_TYPE_SELF = 2;
        // public static final int CP_TYPE_EXTENDED = 4;
    }

    /**
     * Type of import:
     *   1=DELETE = Delete all old values
     *   2=UPDATE = Update with new values
     */

    /**
     * Type of class: 
     *   SIMPLE = class contains data that is not linked to anyother
     *   SELF = class is only linked to itself
     *   EXTENDED = class is linked to other classes
     *   EXTENDED_PLUS = class is linked to self and other classes
     */
    public Hashtable<String, String> class_type = null;

    /**
     * Type of import:
     *   DELETE = Delete all old values
     *   UPDATE = Update with new values
     */
    public Hashtable<String, String> class_import_type = null;


    /**
     * Inits the definitions.
     */
    public abstract void initDefinitions();


    /*
     * public void initDefinitions()
     * {
     * class_defs = new Hashtable<String,Integer>();
     * class_defs.put("ggc.core.db.hibernate.DayValueH", new
     * Integer(CP_IMPORT_DELETE));
     * }
     */

    /**
     * Checks if is allowed class.
     * 
     * @param cl_name the cl_name
     * 
     * @return true, if is allowed class
     */
    public boolean isAllowedClass(String cl_name)
    {
        return this.class_defs.containsKey(cl_name);
    }


    /**
     * Checks if is unallowed class.
     * 
     * @param cl_name the cl_name
     * 
     * @return true, if is unallowed class
     */
    public boolean isUnallowedClass(String cl_name)
    {
        return !this.class_defs.containsKey(cl_name);
    }

}
