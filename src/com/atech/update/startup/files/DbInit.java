package com.atech.update.startup.files;

import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.os.OSType;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DbInit - batch file for initializing Db
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

public class DbInit extends DbApplicationAbstract
{

    /**
     * Constructor
     * 
     * @param uc
     * @param osa
     */
    public DbInit(UpdateConfiguration uc, OSType osa)
    {
        super(uc, osa);
    }


    /**
     * Has Application Parameters (something %1 %2)
     * 
     * @return true if application is called with parameters 
     */
    @Override
    public boolean hasApplicationParameters()
    {
        return true;
    }


    /**
     * Get Application Parameters
     * 
     * @return string with all parameters
     */
    @Override
    public String getApplicationParameters()
    {
        return this.getApplicationParameters(1);
    }


    /**
     * Get Class Name
     * 
     * @return get class for application
     */
    @Override
    public String getClassName()
    {
        return this.upd_conf.db_apps.get("db_init").app_class;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameOfFile()
    {
        return "db_init";
    }

}
