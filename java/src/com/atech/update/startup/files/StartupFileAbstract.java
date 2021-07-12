package com.atech.update.startup.files;

import com.atech.data.user_data_dir.UserDataDirectory;
import com.atech.update.config.ComponentEntry;
import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.StartupUtil;
import com.atech.update.startup.data.StartupTypeDefinition;
import com.atech.update.startup.os.OSType;

/**
 *  This file is part of ATech Tools library.
 *  
 *  StartupFileAbstract - this is abstract class for all batch files
 *  Copyright (C) 2008-18  Andy (Aleksander) Rozman (Atech-Software)
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

public abstract class StartupFileAbstract
{

    UpdateConfiguration upd_conf;
    OSType osType;


    // UserDataDirectory userDataDirectory;

    /**
     * Constructor
     * 
     * @param uc
     * @param osType
     */
    public StartupFileAbstract(UpdateConfiguration uc, OSType osType)
    {
        this.osType = osType;
        this.upd_conf = uc;
    }


    /**
     * Get Class Path
     * 
     * @return classpath as string
     */
    public abstract String getClassPath();


    /**
     * Get Class Name
     * 
     * @return get class for application
     */
    public abstract String getClassName();


    /**
     * Does Need Binary Path (if application will run with libraries that have binaries)
     * 
     * @return true if binary path is needed
     */
    public boolean doesNeedBinaryPath()
    {
        return false;
    }


    /**
     * Get File Content
     * @return
     */
    public String getFileContent()
    {
        StringBuffer sb = new StringBuffer();

        sb.append(this.osType.getHeader());

        sb.append(this.upd_conf.java_exe);

        if (this.hasJavaParameters())
        {
            sb.append(" " + getSpecialJavaParameters());
        }

        if (this.doesNeedBinaryPath())
        {
            sb.append(" " + this.getBinaryPath());
        }

        sb.append(" -classpath ");
        sb.append("." + this.osType.getPathSeparator());
        sb.append(this.getClassPath());

        if (this.needsJdbcDrivers())
        {
            sb.append(this.osType.getPathSeparator());

            String ret = StartupUtil.replaceExpression(this.upd_conf.jdbc_files, ";", this.osType.getPathSeparator());
            sb.append(ret);
        }

        sb.append(" ");
        sb.append(this.getClassName());

        if (this.hasApplicationParameters())
        {
            sb.append(" ");
            sb.append(this.getApplicationParameters());
        }

        // sb.append(this.osType.getFooter());

        return sb.toString();
    }


    /**
     * Has Application Parameters (something %1 %2)
     * 
     * @return true if application is called with parameters 
     */
    public boolean hasApplicationParameters()
    {
        return false;
    }


    /**
     * Get Application Parameters
     * 
     * @return string with all parameters
     */
    public String getApplicationParameters()
    {
        return "";
    }


    /**
     * Get Application Parameters
     * 
     * @param cnt number of parameters
     * @return string with all parameters
     */
    public String getApplicationParameters(int cnt)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = 1; i <= cnt; i++)
        {
            sb.append(this.osType.getCustomParameter() + i);
            sb.append(" ");
        }

        return sb.toString();
    }


    /**
     * Needs JDBC Drivers
     * 
     * @return true if application needs JDBC drivers
     */
    public boolean needsJdbcDrivers()
    {
        return false;
    }


    protected String parseRoot(String path, String full_string)
    {
        full_string = full_string.replaceAll(";", this.osType.getPathSeparator());
        return full_string.replaceAll("%ROOT%", path);
    }


    protected String getClasspathForComponent(String component_name)
    {
        if (this.upd_conf.components_ht.containsKey(component_name))
        {

            ComponentEntry ce = this.upd_conf.components_ht.get(component_name);
            String path = upd_conf.root + ce.root_dir;

            return parseRoot(path, ce.files);
        }
        else
            return null;
    }


    /**
     * Get Binary Path
     * 
     * @return binary path for application
     */
    public String getBinaryPath()
    {
        return "-Djava.library.path=" + this.upd_conf.root + "/lib/native/" + this.osType.getShortOSName();
    }


    /**
     * Has Java Parameters
     * 
     * @return true if application needs java parameters to start
     */
    public boolean hasJavaParameters()
    {
        return false;
    }


    /**
     * Get Special Java Parameters
     * 
     * @return string with special parameters
     */
    public String getSpecialJavaParameters()
    {
        return "";
    }


    /**
     * Get Full Filename (with prefix if required)
     *
     * @return
     */
    public String getFullFilename()
    {
        String prefix = "";

        if (UserDataDirectory.getInstance().getApplicationStartupConfig().getStartupType() != StartupTypeDefinition.LegacyStartup)
        {
            prefix = "./ext/";
        }

        return prefix + getNameOfFile() + "." + this.osType.getBatchFileExtension();
    }


    /**
     * Name of file
     *
     * @return
     */
    public abstract String getNameOfFile();

}
