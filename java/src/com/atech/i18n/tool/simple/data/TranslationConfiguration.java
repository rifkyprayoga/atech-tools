package com.atech.i18n.tool.simple.data;

import java.util.Hashtable;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  TranslationConfiguration - Translation configuration
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

public class TranslationConfiguration extends Hashtable<String, String>
{
    private static final long serialVersionUID = 4284296371402765196L;
    String legend = null;

    /**
     * Constructor
     */
    public TranslationConfiguration()
    {
        super();
    }

    /**
     * get Priorities Legend
     * @return
     */
    public String getPrioritiesLegend()
    {
        if (legend == null)
        {
            loadLegend();
        }

        return this.legend;
    }

    /**
     * Get Setting
     * 
     * @param key
     * @param def_value
     * @return
     */
    public String getSetting(String key, String def_value)
    {
        if (this.containsKey(key))
            return this.get(key);
        else
            return def_value;
    }

    private void loadLegend()
    {
        if (this.containsKey("GROUP_PRIORITY_LAST"))
        {
            // we have legend
            int max = Integer.parseInt(this.get("GROUP_PRIORITY_LAST"));

            this.legend = "Legend of priorities:\n";

            for (int i = 1; i <= max; i++)
            {
                if (this.containsKey("GROUP_PRIORITY_" + i))
                {
                    this.legend += "\t" + i + " = " + this.get("GROUP_PRIORITY_" + i) + "\n";
                }
                else
                {
                    this.legend += "\t" + i + " = Undefined.\n";
                }
            }

        }
        else
        {
            this.legend = "Legend not specified or invalid.";
        }
    }

}
