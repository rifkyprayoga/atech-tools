package com.atech.update.startup.files;

import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.os.StartupOSAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DbTool - batch file for starting DbTool
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

public class DbTool extends DbApplication
{

    /**
     * Constructor
     * 
     * @param uc
     * @param osa
     */
    public DbTool(UpdateConfiguration uc, StartupOSAbstract osa)
    {
        super(uc, osa);
    }
    

    /**
     * Get Class Name
     * 
     * @return get class for application
     */
    @Override
    public String getClassName()
    {
        return "com.atech.db.hibernate.tool.DbTool";
    }


    /**
     * Get File Name for batch file
     * 
     * @return filename for batch file
     */
    @Override
    public String getFileName()
    {
        return "db_tool." + this.os_abstract.getBatchFileExtension();
    }
	
    
}