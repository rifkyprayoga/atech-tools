package com.atech.update.startup.files;

import com.atech.update.config.ComponentCustomApp;
import com.atech.update.config.ComponentEntry;
import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.os.OSType;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ApplicationFile - for creating application batch file
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

public class ApplicationFile extends StartupFileAbstract
{

    ComponentCustomApp custom_app;


    /**
     * Constructor
     * 
     * @param uc
     * @param osa
     * @param custom_app
     */
    public ApplicationFile(UpdateConfiguration uc, OSType osa, ComponentCustomApp custom_app)
    {
        super(uc, osa);
        this.custom_app = custom_app;
    }


    /**
     * Does Need Binary Path (if application will run with libraries that have binaries)
     * 
     * @return true if binary path is needed
     */
    @Override
    public boolean doesNeedBinaryPath()
    {
        // System.out.println("Does need binary: " + custom_app.binary_needed);
        return this.custom_app.binary_needed;
    }


    /**
     * Get Class Name
     * 
     * @return get class for application
     */
    @Override
    public String getClassName()
    {
        return this.custom_app.main_class;
    }


    /**
     * Get Class Path
     * 
     * @return classpath as string
     */
    @Override
    public String getClassPath()
    {
        StringBuffer files = new StringBuffer();

        int count = this.upd_conf.getComponents().size() - 1;

        // System.out.println("Componets: " + this.upd_conf.getComponents());

        for (int i = 0; i <= count; i++)
        {
            ComponentEntry ce = this.upd_conf.getComponents().get(i);

            if (!ce.enabled)
                continue;

            String path = upd_conf.root + ce.root_dir;

            String componentsParts = parseRoot(path, ce.getFiles(this.osType));

            // System.out.println("Component part: " + componentsParts);

            if (componentsParts.trim().length() > 0)
            {

                files.append(componentsParts);

                if (count != i)
                {
                    files.append(this.osType.getPathSeparator());
                }
            }
        }

        // System.out.println("CP: " + files.toString());

        return files.toString();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameOfFile()
    {
        return this.custom_app.filename;
    }


    /**
     * Has Java Parameters
     * 
     * @return true if application needs java parameters to start
     */
    @Override
    public boolean hasJavaParameters()
    {
        return this.custom_app.special_parameters != null && this.custom_app.special_parameters.length() > 2;
    }


    /**
     * Get Special Java Parameters
     * 
     * @return string with special parameters
     */
    @Override
    public String getSpecialJavaParameters()
    {
        return this.custom_app.special_parameters;
    }


    /**
     * Needs JDBC Drivers
     * 
     * @return true if application needs JDBC drivers
     */
    @Override
    public boolean needsJdbcDrivers()
    {
        return this.custom_app.needs_jdbc;
    }

}
