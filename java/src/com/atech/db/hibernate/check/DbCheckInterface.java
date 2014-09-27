package com.atech.db.hibernate.check;

import org.hibernate.cfg.Configuration;

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

public interface DbCheckInterface
{
    /**
     * Is Check Enabled
     * 
     * @return 
     */
    public boolean isCheckEnabled();

    /**
     * Get DbInfo Resource
     * 
     * @return 
     */
    public String[] getDbInfoResource();

    /**
     * Get DbInfo Configuration
     * 
     * @return 
     */
    public Configuration getDbInfoConfiguration();

    /**
     * Get Db Name
     * 
     * @return
     */
    public String getDbName();

    /**
     * Get DbInfo Report Filename
     * 
     * @return
     */
    public String getDbInfoReportFilename();

}
