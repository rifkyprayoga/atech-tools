package com.atech.update.startup;

import java.util.Hashtable;

import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.os.OSUtil;

/**
 *  This file is part of ATech Tools library.
 *  
 *  BuildStartupFile - Build Startup File
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

public class BuildStartupFile
{

    UpdateConfiguration upd_conf;
    StartupFilesCreator sfc; // = new StartupFileCreator(UpdateConfiguration uc)


    // StartupUtil dataAccess;

    /**
     * Constructor
     */
    public BuildStartupFile()
    {

        if (!StartupUtil.shouldStartupFilesBeCreated())
        {
            System.out.println("============================================================");
            System.out.println("===             Startup/Update Manager                   ===");
            System.out.println("===   Startupfiles were not marked to be created. OK.    ===");
            System.out.println("============================================================");

            StartupUtil.writeStartupWithOldCopy(2);

            return;
        }

        Hashtable<String, String> cfg = StartupUtil.getConfiguration(OSUtil.getOSSpecificConfigurationFile());

        if (cfg.containsKey("UPDATE_CONFIG"))
        {
            this.upd_conf = new UpdateConfiguration(cfg.get("UPDATE_CONFIG"), cfg.get("JAVA_EXE"));
            this.sfc = new StartupFilesCreator(this.upd_conf);

            if (this.sfc.getOSAbstract() == null)
            {
                System.out.println("=============================================================================");
                System.out.println("===             Startup/Update Manager  FAILED on startup                 ===");
                System.out.println("===   Operating system not supported YET. Please contact Atech support    ===");
                System.out.println("===   (support@atech-software.com).                                       ===");
                System.out.println("=============================================================================");

                StartupUtil.writeStartupWithOldCopy(2);

                return;
            }

            if (buildStartupFiles())
            {
                System.out.println("=============================================================================");
                System.out.println("===                         Startup/Update Manager                        ===");
                System.out.println("===   Startup file created succesfully.                                   ===");
                System.out.println("=============================================================================");
            }
            else
            {
                System.out.println("=============================================================================");
                System.out.println("===             Startup/Update Manager  FAILED on startup                 ===");
                System.out.println("===   Error on startup creation. Please contact Atech support             ===");
                System.out.println("===   (support@atech-software.com).                                       ===");
                System.out.println("=============================================================================");
            }

        }
        else
        {
            System.out.println("=============================================================================");
            System.out.println("===             Startup/Update Manager  FAILED on startup                 ===");
            System.out.println("===   If this error persists application might not work correctly.        ===");
            System.out.println("===   Please contact Atech support (support@atech-software.com).          ===");
            System.out.println("===   Error: StartupConfig.properties is missing or missconfigured.       ===");
            System.out.println("=============================================================================");
        }

        StartupUtil.writeStartupWithOldCopy(2);
    }


    private boolean buildStartupFiles()
    {

        try
        {
            this.sfc.createFiles();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

        /*
         * String[] str = this.sfc.getStartupFileBody();
         * if (str==null)
         * {
         * return false;
         * }
         * else
         * {
         * try
         * {
         * BufferedWriter br = new BufferedWriter(new FileWriter(new
         * File(this.upd_conf.run_filename + "." + str[0])));
         * br.write(str[1]);
         * br.flush();
         * br.close();
         * }
         * catch(Exception ex)
         * {
         * return false;
         * }
         * return true;
         * }
         */

    }


    /**
     * Get Configuration
     * 
     * @return UpdateConfiguration object
     */
    public UpdateConfiguration getConfiguration()
    {
        return this.upd_conf;
    }


    /**
     * Main startup method
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        new BuildStartupFile();
    }

}
