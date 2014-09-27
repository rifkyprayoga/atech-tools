package com.atech.update.startup.os;

import java.io.File;

/**
 *  This file is part of ATech Tools library.
 *  
 *  OSUtil - Util for creating startup files
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

public class OSUtil
{

    /**
     * Constructor
     */
    public OSUtil()
    {

    }

    /**
     * Get Short OS Name
     * @return
     */
    public static String getShortOSName()
    {
        String os_name = System.getProperty("os.name");
        String short_name = "unknown";

        // System.out.println("Found OS: " + os_name);

        if (os_name.contains("Linux"))
        {
            short_name = "linux";
        }
        else if (os_name.contains("Mac"))
        {
            short_name = "mac";
        }
        else if (os_name.contains("Win"))
        {
            short_name = "win";
        }
        else if (os_name.contains("FreeBSD"))
        {
            short_name = "freebsd";
        }

        return short_name;
    }

    /**
     * Get OS Specific Configuration File
     * 
     * @return filename of configuration file
     */
    public static String getOSSpecificConfigurationFile()
    {
        String def_filename = "StartupConfig.properties";

        String os_name = OSUtil.getShortOSName();
        String full_os_name = "StartupConfig_" + os_name + ".properties";

        File f = new File(full_os_name);

        if (f.exists())
            return full_os_name;
        else
            return def_filename;

    }

}
