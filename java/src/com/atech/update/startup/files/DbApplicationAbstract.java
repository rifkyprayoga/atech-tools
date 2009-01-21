package com.atech.update.startup.files;

import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.os.StartupOSAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DbApplicationAbstract - abstract class for all Db handling classes
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


public abstract class DbApplicationAbstract extends AtechToolsApplication
{

    /**
     * Constructor
     * 
     * @param uc
     * @param osa
     */
    public DbApplicationAbstract(UpdateConfiguration uc, StartupOSAbstract osa)
    {
        super(uc, osa);
    }
    
    
    /**
     * Get Class Path
     * 
     * @return classpath as string
     */
    @Override
    public String getClassPath()
    {
        StringBuffer sb = new StringBuffer();
        
        sb.append(getClasspathForComponent("Atech Tools"));
        sb.append(";");
        sb.append(getClasspathForComponent("Hibernate Framework"));
        sb.append(";");
        sb.append(getClasspathForComponent("GGC Core"));
        sb.append(";");
        sb.append(getClasspathForComponent("Log4j"));
        
        return sb.toString();
    }

    
    /**
     * Needs JDBC Drivers
     * 
     * @return true if application needs JDBC drivers
     */
    public boolean needsJdbcDrivers()
    {
        return true;
    }
    
    
}