package com.atech.update.startup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import com.atech.data.user_data_dir.UserDataDirectory;
import com.atech.update.config.ComponentCustomApp;
import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.files.*;
import com.atech.update.startup.os.OSType;

/**
 *  This file is part of ATech Tools library.
 *  
 *  StartupFilesCreator - For creating startup files (more)
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

public class StartupFilesCreator
{

    String os_name;
    UpdateConfiguration uc;

    String separator = ";";
    String root = "..";
    String extension = "";

    // StartupOSAbstract startupOS = null;
    // int startupType = 1;
    UserDataDirectory userDataDirectory;
    OSType startupOS;


    /**
     * Constructor
     * 
     * @param uc
     */
    public StartupFilesCreator(UpdateConfiguration uc, UserDataDirectory userDataDirectory)
    {
        this.uc = uc;
        this.userDataDirectory = userDataDirectory;
        // this.startupType = startupType;
        init();
    }


    protected void init()
    {

    }


    /**
     * Get OS Abstract class instance
     * @return
     */
    public OSType getOSAbstract()
    {
        return this.startupOS;
    }


    /**
     * Create Files
     * 
     * @throws Exception
     */
    public void createFiles() throws Exception
    {
        // System.out.println("Create Files DbTool: ");

        if (this.uc.db_apps.get("db_tool").enabled)
        {
            createFile(new DbTool(this.uc, this.startupOS));
        }

        // System.out.println("Create Files Ch: ");

        if (this.uc.db_apps.get("db_check").enabled)
        {
            createFile(new DbCheck(this.uc, this.startupOS));
        }

        // System.out.println("Create Files App: ");

        if (this.uc.db_apps.get("db_application").enabled)
        {
            createFile(new DbApplication(this.uc, this.startupOS));
        }

        // System.out.println("Create Files Db Init: ");

        if (this.uc.db_apps.get("db_init").enabled)
        {
            createFile(new DbInit(this.uc, this.startupOS));
        }

        // System.out.println("Create Files Dm Impo: ");

        if (this.uc.db_apps.get("db_import").enabled)
        {
            createFile(new DbImport(this.uc, this.startupOS));
        }

        // System.out.println("Create Files Bef Custom: ");

        if (this.uc.custom_apps.size() > 0)
        {
            createCustomFiles();
        }
    }


    /**
     * Create Custom Files
     * 
     * @throws Exception
     */
    public void createCustomFiles() throws Exception
    {
        for (int i = 1; i <= this.uc.custom_apps.size(); i++)
        {
            ComponentCustomApp cca = this.uc.custom_apps.get("" + i);
            createFile(new ApplicationFile(this.uc, this.startupOS, cca));
        }
    }


    /**
     * Create File 
     * 
     * @param file_ab abstract file instance
     * 
     * @throws Exception
     */
    public void createFile(StartupFileAbstract file_ab) throws Exception
    {
        // System.out.println("CreateFile: " + file_ab);

        try
        {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(file_ab.getFullFilename())));
            br.write(file_ab.getFileContent());
            br.flush();
            br.close();
        }
        catch (Exception ex)
        {
            System.out.println("createFile failed: " + ex);
            ex.printStackTrace();

            throw ex;
        }
    }


    private void printNotSupported()
    {
        System.out.println("This Operating System (" + os_name + ") is not supported "
                + "\nby ATech's Startup/Update Manager.");
        System.out.println("If you wish to help us with support for your OS please contact us");
        System.out.println("on our email (support@atech-software.com).");

    }

}
