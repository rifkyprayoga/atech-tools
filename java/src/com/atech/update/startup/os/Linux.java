package com.atech.update.startup.os;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Linux - File for creating batch files in Linux
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


public class Linux extends StartupOSAbstract
{

    /**
     * Get Header for batch file
     * 
     * @return header as string
     */
    @Override
    public String getHeader()
    {
        return "#!/bin/sh\n\n" +
               "#  Run Application\n";
    }

    /**
     * Get Separator - this is path separator 
     * 
     * @return separator as string
     */
    @Override
    public String getSeparator()
    {
        return ":";
    }
    
    /**
     * Get Batch File Extension
     * 
     * @return extension of batch file in this OS
     */
    @Override
    public String getBatchFileExtension()
    {
        return "sh";
    }

    /**
     * Get Short OS Name (this is not official name, but name we use)
     * 
     * @return short os name
     */
    @Override
    public String getShortOSName()
    {
        return "linux";
    }

    /**
     * Get How Parameter is constructed (%1 in windows, $1 in unix)
     * 
     * @return custom parameter 
     */
    @Override
    public String getCustomParameter()
    {
        return "$";
    }

    
}