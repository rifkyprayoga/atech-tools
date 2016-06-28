package com.atech.update.startup;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.atech.update.config.ComponentEntry;
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
    int startupType = 1; // 1 = Default, old one, 2 = New (files in bin/ext)


    // StartupUtil dataAccess;

    /**
     * Constructor
     */
    public BuildStartupFile()
    {
        Hashtable<String, String> startupConfiguration = StartupUtil
                .getConfiguration(OSUtil.getOSSpecificConfigurationFile());

        if (startupConfiguration.containsKey("STARTUP_TYPE"))
        {
            this.startupType = getIntValueFromString(startupConfiguration.get("STARTUP_TYPE"), 1);
            StartupUtil.setStartupType(this.startupType);
        }

        if (!StartupUtil.shouldStartupFilesBeCreated())
        {
            System.out.println("============================================================");
            System.out.println("===             Startup/Update Manager                   ===");
            System.out.println("===   Startupfiles were not marked to be created. OK.    ===");
            System.out.println("============================================================");

            StartupUtil.writeStartupWithOldCopy(2);

            return;
        }

        if (startupConfiguration.containsKey("UPDATE_CONFIG"))
        {
            this.upd_conf = new UpdateConfiguration(startupConfiguration.get("UPDATE_CONFIG"),
                    startupConfiguration.get("JAVA_EXE"));
            this.sfc = new StartupFilesCreator(this.upd_conf, this.startupType);

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


    public BuildStartupFile(String[] parameters)
    {

        try
        {

            System.out.println(" VERIFICATION of xxx_Update.properties");
            System.out.println("========================================\n");

            Hashtable<String, String> cfg = StartupUtil.getConfiguration(OSUtil.getOSSpecificConfigurationFile());

            if (cfg.containsKey("UPDATE_CONFIG"))
            {
                this.upd_conf = new UpdateConfiguration(cfg.get("UPDATE_CONFIG"), cfg.get("JAVA_EXE"));

                List<File> allFiles = new ArrayList<File>();
                boolean fileNotFound = false;

                for (ComponentEntry componentEntry : this.upd_conf.getComponents())
                {
                    List<File> files = componentEntry.getFiles("./");
                    allFiles.addAll(files);
                    boolean firstFileInRelease = false;

                    if (files.size() == 0)
                    {
                        if (componentEntry.enabled)
                        {
                            System.out.println("Component: " + componentEntry.name + " has no files defined.");
                        }
                    }
                    else
                    {
                        for (File f : files)
                        {
                            if (!f.exists())
                            {
                                if (!fileNotFound)
                                {
                                    System.out.println("  Files missing in next release");
                                    System.out.println("=================================");
                                    fileNotFound = true;
                                }

                                if (!firstFileInRelease)
                                {
                                    System.out.println("Component: " + componentEntry.name);
                                    firstFileInRelease = true;
                                }

                                System.out.println("    " + f.getCanonicalPath());

                            }
                        }
                    }

                }

                List<File> filesOnSys = new ArrayList<File>();

                getAllJarFilesOnSystem(filesOnSys, new File("../lib"));

                // System.out.println("List of files that need to be removed:
                // ");
                boolean filesToMuch = false;
                System.out.println("\n");

                Map<String, String> mapFiles = getFilesAsFilenames(allFiles);

                for (File file : filesOnSys)
                {
                    if (!mapFiles.containsKey(file.getCanonicalPath()))
                    {
                        if (!filesToMuch)
                        {
                            System.out.println("  Files that are in this release that shoudn't be");
                            System.out.println("===================================================");
                            filesToMuch = true;

                        }

                        System.out.println("" + file.getCanonicalPath());
                    }
                }

            }
            else
            {
                System.out.println("Wrong Startup configuration.");
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception of files verfication: " + ex);
            ex.printStackTrace();
        }

    }


    private Map<String, String> getFilesAsFilenames(List<File> files)
    {
        Map<String, String> map = new HashMap<String, String>();

        for (File f : files)
        {
            try
            {
                map.put(f.getCanonicalPath(), "");
            }
            catch (IOException e)
            {
                System.out.println("Exception creating map of files: " + e);
            }
        }

        return map;

    }


    private void getAllJarFilesOnSystem(List<File> filesList, File directory)
    {
        File[] files = directory.listFiles();

        for (File file : directory.listFiles())
        {
            if ((file.isFile()) && file.getName().endsWith(".jar"))
            {
                filesList.add(file);
            }
            else if (file.isDirectory())
            {
                getAllJarFilesOnSystem(filesList, file);
            }
        }

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

    }


    private int getIntValueFromString(String valueString, int defaultValue)
    {
        int returnValue = defaultValue;

        if (valueString != null && valueString.trim().length() != 0)
        {
            try
            {
                returnValue = Integer.parseInt(valueString);
            }
            catch (Exception ex)
            {
                System.out.println("Error parsing Statup Type: " + valueString + ", Exception: " + ex.getMessage());
            }
        }

        return returnValue;
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
        if (args.length == 0)
        {
            new BuildStartupFile();
        }
        else
        {
            new BuildStartupFile(args);
        }
    }

}
