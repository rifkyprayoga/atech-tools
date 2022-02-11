package com.atech.db.ext;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

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

public class ExtendedEnumHandler<E extends ExtendedEnumType> implements ExtendedHandler
{

    private String extendedDivider = ";##;";
    String extendedObject;
    E enumValuesDefinition;


    /**
     * Constructor
     */
    public ExtendedEnumHandler(E enumValuesDefinition, String extendedObject)
    {
        this.extendedObject = extendedObject;
        this.enumValuesDefinition = enumValuesDefinition;
    }


    public String getExtendedObject()
    {
        return this.extendedObject;
    }


    /**
     * Load Extended
     * 
     * @param extendedPacked packedValue of ExtendedDataType
     * @return
     */
    public Map<E, String> loadExtended(String extendedPacked)
    {
        Map<E, String> extendedMap = new HashMap<E, String>();

        if (StringUtils.isNotBlank(extendedPacked))
        {
            StringTokenizer strtok = new StringTokenizer(extendedPacked, this.extendedDivider);

            while (strtok.hasMoreTokens())
            {
                String val = strtok.nextToken();

                if (!val.contains("="))
                {
                    continue;
                }

                String[] splittedValue = val.split("=");

                if (enumValuesDefinition.useI18nKey())
                    extendedMap.put((E) enumValuesDefinition.getEnumTypeByKey(splittedValue[0]), splittedValue[1]);
                else
                    extendedMap.put((E) enumValuesDefinition.getEnumTypeByName(splittedValue[0]), splittedValue[1]);
            }
        }

        return extendedMap;
    }


    public String getExtendedTypeDescription(ExtendedEnumType type)
    {
        return type.getTranslation();
    }


    /**
     * Save Extended Entry for database
     * 
     * @param extendedMap
     * @return String value containing all set extended
     */
    public String saveExtended(Map<E, String> extendedMap)
    {
        StringBuffer sb = new StringBuffer();
        boolean found = false;

        //System.out.println("all: " + this.enumValuesDefinition.getAllValues());

        for (ExtendedEnumType enumEntry : this.enumValuesDefinition.getAllValues())
        {
            if (extendedMap.containsKey(enumEntry))
            {
                if (enumValuesDefinition.useI18nKey())
                    sb.append(enumEntry.getI18nKey() + "=" + extendedMap.get(enumEntry) + extendedDivider);
                else
                    sb.append(enumEntry.getName() + "=" + extendedMap.get(enumEntry) + extendedDivider);

                found = true;
            }
        }

        //System.out.println("sb: " + this.enumValuesDefinition.getAllValues());

        if (found)
        {
            return sb.substring(0, sb.length() - extendedDivider.length());
        }
        else
        {
            return null;
        }
    }


    /**
     * Get Extended Value (after resolved)
     * 
     * @param extendedType
     * @param extendedMap
     * @return
     */
    public String getExtendedValue(ExtendedEnumType extendedType, Map<E, String> extendedMap)
    {
        if (extendedMap.containsKey(extendedType))
            return extendedMap.get(extendedType);
        else
            return "";
    }


    /**
     * Set Extended Value (after resolved)
     * 
     * @param extendedType
     * @param value
     * @param extendedMap
     * @return returns true, if value was changed
     */
    public boolean setExtendedValue(E extendedType, String value, Map<E, String> extendedMap)
    {
        if (StringUtils.isBlank(value))
        {
            if (extendedMap.containsKey(extendedType))
            {
                extendedMap.remove(extendedType);
            }

            return false;
        }

        if (extendedMap.containsKey(extendedType))
        {
            if (extendedMap.get(extendedType).equals(value))
                return false;
            else
            {
                extendedMap.remove(extendedType);
            }
        }

        extendedMap.put(extendedType, value);
        return true;
    }


    /**
     * Is Extended Value Set
     * 
     * @param extendedType
     * @param extendedMap
     * @return
     */
    public boolean isExtendedValueSet(ExtendedEnumType extendedType, Map<E, String> extendedMap)
    {
        return extendedMap.containsKey(extendedType);
    }

}
