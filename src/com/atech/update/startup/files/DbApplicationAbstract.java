package com.atech.update.startup.files;

import java.util.StringTokenizer;

import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.StartupUtil;
import com.atech.update.startup.os.OSType;

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
    public DbApplicationAbstract(UpdateConfiguration uc, OSType osa)
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

        if (this.upd_conf.dbClasspathComponents != null)
        {
            StringTokenizer strtok = new StringTokenizer(this.upd_conf.dbClasspathComponents, ";");

            while (strtok.hasMoreTokens())
            {
                String cp = getClasspathForComponent(strtok.nextToken());

                if (cp != null)
                {
                    sb.append(cp);

                    if (strtok.hasMoreTokens())
                        sb.append(";");
                }
            }

        }
        else
        {
            sb.append(getClasspathForComponent("Atech Tools"));
            sb.append(";");
            sb.append(getClasspathForComponent("Hibernate Framework"));
            sb.append(";");
            sb.append(getClasspathForComponent("GGC Core"));
            sb.append(";");
            sb.append(getClasspathForComponent("Log4j"));
            sb.append(";");
            sb.append(getClasspathForComponent("Apache Commons Logging"));

            // GGC Core should be removed, actually this whole things should be
            // removed, but since this parameter was not available in
            // old versions it must stay
        }

        return StartupUtil.replaceExpression(sb.toString(), ";", this.osType.getPathSeparator());
    }


    /**
     * Needs JDBC Drivers
     * 
     * @return true if application needs JDBC drivers
     */
    @Override
    public boolean needsJdbcDrivers()
    {
        return true;
    }

}
