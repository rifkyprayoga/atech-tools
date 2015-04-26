package com.atech.update.config;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ComponentInterface - Interface for components
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

public class ConfigurationConverter
{

    public static void writeHeader(String[] args)
    {
        System.out.println("ConfigurationConverter (for converting Upgrade.properties to Upgrade.xml)");
        System.out.println("=========================================================================");
        System.out.println("Source: " + args[0]);
        System.out.println("Target: " + args[1]);
        System.out.println("=========================================================================");
    }


    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length == 2)
        {
            UpdateConfiguration uc = new UpdateConfiguration(args[0], "");

            if (!uc.isConfigurationValid())
            {
                ConfigurationConverter.writeHeader(args);
                System.out.println("Input file is not valid. Conversion stopped.");
                return;
            }

            UpdateConfigurationXml ucx = new UpdateConfigurationXml();
            ucx.writeXml(uc, args[1]);

            ConfigurationConverter.writeHeader(args);
            System.out.println("Conversion complete");
        }
        else
        {
            System.out.println("ConfigurationConverter <input_file> <output_file>");
        }

    }
}
