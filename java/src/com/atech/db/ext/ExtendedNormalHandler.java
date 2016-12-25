package com.atech.db.ext;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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

public abstract class ExtendedNormalHandler
{

    ATDataAccessAbstract m_da;
    String extended_packed;
    protected Hashtable<Integer, String> ext_mapped_types = null;
    private String extended_divider = ";##;";


    // private int maxParameters = 0;

    /**
     * Constructor
     *
     * @param da
     */
    public ExtendedNormalHandler(ATDataAccessAbstract da)
    {
        initExtended();
    }


    /**
     * Init Extended Values for type
     */
    public abstract void initExtended();


    /**
     * Get Extended Object Name
     * 
     * @return
     */
    public abstract String getExtendedObject();


    /**
     * Load Extended
     * 
     * @param extended
     * @return
     */
    public HashMap<String, String> loadExtended(String extended)
    {
        this.extended_packed = extended;

        HashMap<String, String> ht = new HashMap<String, String>();

        if (this.extended_packed != null && this.extended_packed.trim().length() > 0)
        {
            StringTokenizer strtok = new StringTokenizer(this.extended_packed, this.extended_divider);

            while (strtok.hasMoreTokens())
            {
                String val = strtok.nextToken();
                int idx = val.indexOf("=");
                ht.put(val.substring(0, idx), val.substring(idx + 1));
            }

        }

        return ht;

    }


    public String getExtendedTypeDescription(int type)
    {
        if (this.ext_mapped_types.containsKey(type))
            return this.ext_mapped_types.get(type);
        else
            return null;
    }


    /**
     * Save Extended Entry for database
     * 
     * @param hashMap
     * @return String value containing all set extended
     */
    public String saveExtended(HashMap<String, String> hashMap)
    {
        StringBuffer sb = new StringBuffer();
        boolean found = false;

        for (int i = 0; i <= ext_mapped_types.size(); i++)
        {
            String key = ext_mapped_types.get(i);
            if (hashMap.containsKey(key))
            {
                sb.append(key + "=" + hashMap.get(key) + extended_divider);
                found = true;
            }
        }

        if (found)
        {
            return sb.substring(0, sb.length() - extended_divider.length());
        }
        else
        {
            return null;
        }
    }


    /**
     * Get Extended Value (after resolved)
     * 
     * @param type
     * @param ht 
     * @return
     */
    public String getExtendedValue(int type, HashMap<String, String> ht)
    {
        if (ht.containsKey(this.ext_mapped_types.get(type)))
            return ht.get(this.ext_mapped_types.get(type));
        else
            return "";
    }


    /**
     * Set Extended Value (after resolved)
     * 
     * @param type
     * @param val 
     * @param ht 
     * @return returns true, if value was changed
     */
    public boolean setExtendedValue(int type, String val, HashMap<String, String> ht)
    {
        if (val == null || val.trim().length() == 0)
            return false;

        if (ht.containsKey(this.ext_mapped_types.get(type)))
        {
            if (ht.get(this.ext_mapped_types.get(type)).equals(val))
                return false;
            else
            {
                ht.remove(this.ext_mapped_types.get(type));
            }
        }

        ht.put(this.ext_mapped_types.get(type), val);
        return true;

    }


    /**
     * Is Extended Value Set
     * 
     * @param type
     * @param ht
     * @return
     */
    public boolean isExtendedValueSet(int type, HashMap<String, String> ht)
    {
        return ht.containsKey(this.ext_mapped_types.get(type));
    }

}
