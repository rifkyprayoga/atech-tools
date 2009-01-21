package com.atech.update.startup;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 *  This file is part of ATech Tools library.
 *  
 *  StartupUtil - Util for startup part of atech-tools
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
 *  @author Andy {andy@atech-software.com}
 *
*/


public class StartupUtil
{
    
    
    /**
     * Get Configuration - reads properties file and read all entries
     * 
     * @param filename
     * @return
     */
    public static Hashtable<String,String> getConfiguration(String filename)
    {
        Hashtable<String,String> config_db_values = new Hashtable<String,String>();

        Properties props = new Properties();

        boolean config_loaded = true;

        try
        {
            FileInputStream in = new FileInputStream(filename);
            props.load(in);
        }
        catch (Exception ex)
        {
            config_loaded = false;
        }


        if (config_loaded)
        {

            for (Enumeration<Object> en = props.keys(); en.hasMoreElements(); )
            {
                String  key = (String)en.nextElement();
                config_db_values.put(key, props.getProperty(key));
            }
        }

        return config_db_values;
        
    }
    
    
    /**
     * Is Option Enabled
     * 
     * @param value
     * @return
     */
    public static boolean isOptionEnabled(String value)
    {
        
        if (value==null)
            return false;
        
        String val = value.toUpperCase();
        
        if (val.equals("ENABLED") || val.equals("YES") || val.equals("TRUE") || val.equals("1"))
            return true;
        else
            return false;
    }    

    
    
    /**
     * For replacing strings.<br>
     * 
     * @param input   Input String
     * @param replace What to seatch for.
     * @param replacement  What to replace with.
     * 
     * @return Parsed string.
     */
    public static String replaceExpression(String input, String replace,
            String replacement)
    {

        if (replace.equals(replacement))
            return input;
        
        int idx;
        if ((idx = input.indexOf(replace)) == -1)
        {
            return input;
        }

        boolean finished = false;
        
        while(!finished)
        {
        
            StringBuffer returning = new StringBuffer();
    
            while (idx != -1)
            {
                returning.append(input.substring(0, idx));
                returning.append(replacement);
                input = input.substring(idx + replace.length());
                idx = input.indexOf(replace);
            }
            returning.append(input);
            
            input = returning.toString();
            
            if ((idx = returning.indexOf(replace))==-1)
            {
                finished = true;
            }

        }

        return input;

    }
    
    
}
