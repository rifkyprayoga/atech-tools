package com.atech.update.config;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import com.atech.data.user_data_dir.UserDataDirectory;
import com.atech.update.startup.StartupUtil;

// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * 
 * UpdateConfiguration - This is main data file for configuration file
 * Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * 
 * For additional information about this project please visit our project site on
 * http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 */

public class UpdateConfiguration
{

    /**
     * Configuration
     */
    Map<String, String> cfg;

    /**
     * Components.
     */
    List<ComponentEntry> components = new ArrayList<ComponentEntry>();

    /**
     * Components HashTable.
     */
    public Map<String, ComponentEntry> components_ht = new HashMap<String, ComponentEntry>();

    /**
     * Group.
     */
    public Map<String, ComponentGroup> groups = new HashMap<String, ComponentGroup>();

    /**
     * Product Id
     */
    public String product_id = "";

    /**
     * Version Name
     */
    public String version_name = "";

    /**
     * Version Numeric
     */
    public int version_numeric = 0;

    /**
     * Version Description
     */
    public String version_description = "";

    /**
     * Root.
     */
    public String root = "";

    /**
     * Java Executable.
     */
    public String java_exe = "";

    /**
     * Main Class.
     */
    public String main_class = "";

    /**
     * Run filename for Application.
     */
    public String run_filename = "";

    /**
     * Applications.
     */
    public Hashtable<String, ComponentCustomApp> custom_apps = new Hashtable<String, ComponentCustomApp>();

    /**
     * Database Update Site.
     */
    public String db_update_site = "";

    /**
     * Database Version Required.
     */
    public int db_version_required = 0;

    /**
     * Database Enabled.
     */
    public boolean db_enabled = false;

    /**
     * Db Config Class.
     */
    public String db_config_class = "";

    /**
     * Db Update Site Version
     */
    public int db_update_site_version = 0;

    public String dbClasspathComponents;

    /**
     * JDBC Files.
     */
    public String jdbc_files = "";

    private boolean configurationValid = false;

    /**
     * Applications.
     */
    public Hashtable<String, ComponentDbApp> db_apps = new Hashtable<String, ComponentDbApp>();

    /** The needed_params. */
    private String[] needed_params = { "PRODUCT_ID", "DB_TOOL", "DB_CHECK", "APP_CUSTOM_COUNT", "APP_CUSTOM_1_ID",
                                      "DB_UPDATE_SITE", "DB_UPDATE_SITE_VERSION", "DB_VERSION_REQUIRED", "DB_ENABLED",
                                      "DB_CONFIGURATION_CLASS", "LAST_COMPONENT", "GROUP_1_NAME", "COMPONENT_1_NAME",
                                      "VERSION_NAME", "VERSION_NUMERIC" };


    /**
     * Constructor.
     */
    public UpdateConfiguration()
    {
        // dataAccess = ATDataAccess.getInstance();
        // Map<String, String> cfg_1 =

        // ApplicationStartupConfigDto applicationStartupConfig =
        // StartupUtil.getApplicationStartupConfig();

        UserDataDirectory userDataDirectory = UserDataDirectory.getInstance();

        init(userDataDirectory.getUpdateConfig(), userDataDirectory.getApplicationStartupConfig().getJavaExe());
    }


    /**
     * Constructor
     * 
     * @param as_empty
     */
    public UpdateConfiguration(boolean as_empty)
    {
    }


    /**
     * Constructor.
     * 
     * @param upd_cfg_file the upd_cfg_file
     * @param java_exe the java_exe
     */
    public UpdateConfiguration(String upd_cfg_file, String java_exe)
    {
        init(upd_cfg_file, java_exe);
    }


    /**
     * Init
     * 
     * @param upd_cfg_file the upd_cfg_file
     * @param _java_exe the java_exe
     */
    public void init(String upd_cfg_file, String _java_exe)
    {
        this.java_exe = _java_exe;
        components = new ArrayList<ComponentEntry>();

        discoverJdbcClasses();

        configurationValid = loadConfiguration(upd_cfg_file);
    }


    /**
     * Discover jdbc classes
     */
    private void discoverJdbcClasses()
    {
        // System.out.println("disocverJDBC");

        File f = new File("../lib");

        File[] fls = f.listFiles();

        if (fls == null)
            return;

        ArrayList<File> lst = new ArrayList<File>();

        for (File fl : fls)
        {
            if (fl.getName().contains("jdbc") || fl.getName().contains("db"))
            {
                lst.add(fl);
            }

        }

        FileFilter fileFilter = new FileFilter()
        {

            public boolean accept(File f1)
            {
                if (f1.isDirectory())
                    return false;

                String ext = f1.getName();

                if (ext.endsWith(".jar"))
                    return true;

                return false;
            }

        };

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < lst.size(); i++)
        {
            String _root = "../lib/" + lst.get(i).getName() + "/";

            File[] subFiles = lst.get(i).listFiles(fileFilter);

            for (File subFile : subFiles)
            {
                sb.append(_root + subFile.getName() + ";");
            }
        }

        String t = sb.toString();

        if (t.trim().length() == 0)
        {
            this.jdbc_files = "";
        }
        else
        {
            this.jdbc_files = t.substring(0, t.length() - 1);
        }

        // System.out.println("Jdbc: " + this.jdbc_files);

    }


    /**
     * Load Configuration
     * 
     * @param upd_cfg_file the upd_cfg_file
     * 
     * @return true, if load configuration
     */
    public boolean loadConfiguration(String upd_cfg_file)
    {
        this.cfg = StartupUtil.getConfigurationFile(upd_cfg_file);

        boolean found = true;

        for (int i = 0; i < this.needed_params.length; i++)
        {
            if (!cfg.containsKey(this.needed_params[i]))
            {
                System.out.println("Following parameter is missing:" + this.needed_params[i]);
                found = false;
            }
        }

        if (found)
        {
            getCoreData();
            getDbData();
            getDbApplicationData();
            getApplicationData();
            getComponentsData();
            return true;
        }
        else
        {

            System.out.println("=============================================================================");
            System.out.println("===             Startup/Update Manager  FAILED on startup                 ===");
            System.out.println("===   If this error persists application might not work correctly.        ===");
            System.out.println("===   Please contact Atech support (support@atech-software.com).          ===");
            System.out.println("===   Error: Application update file is invalid                           ===");
            System.out.println("=============================================================================");
            return false;
        }

    }


    private void getCoreData()
    {
        this.product_id = cfg.get("PRODUCT_ID");
        this.root = cfg.get("ROOT");

        if (cfg.containsKey("VERSION_NUMERIC"))
        {
            this.version_numeric = Integer.parseInt(cfg.get("VERSION_NUMERIC"));
        }
        else
        {
            this.version_numeric = 0;
        }

        if (cfg.containsKey("VERSION_NAME"))
        {
            this.version_name = cfg.get("VERSION_NAME");
        }
        else
        {
            this.version_name = "0.0.0";
        }

        if (cfg.containsKey("VERSION_DESCRIPTION"))
        {
            this.version_description = cfg.get("VERSION_DESCRIPTION");
        }
        else
        {
            this.version_name = "No description.";
        }

    }


    /**
     * Gets the Db data
     */
    private void getDbData()
    {
        this.db_update_site = cfg.get("DB_UPDATE_SITE");
        this.db_update_site_version = Integer.parseInt(cfg.get("DB_UPDATE_SITE_VERSION"));
        this.db_version_required = Integer.parseInt(cfg.get("DB_VERSION_REQUIRED"));
        this.db_enabled = StartupUtil.isOptionEnabled(cfg.get("DB_ENABLED"));
        this.db_config_class = cfg.get("DB_CONFIGURATION_CLASS");
    }


    /**
     * Gets the Db application data
     */
    private void getDbApplicationData()
    {

        ComponentDbApp cda = new ComponentDbApp("db_check");
        cda.enabled = StartupUtil.isOptionEnabled(cfg.get("DB_CHECK"));
        this.db_apps.put("db_check", cda);

        cda = new ComponentDbApp("db_tool");
        cda.enabled = StartupUtil.isOptionEnabled(cfg.get("DB_TOOL"));
        this.db_apps.put("db_tool", cda);

        cda = new ComponentDbApp("db_application");
        cda.enabled = StartupUtil.isOptionEnabled(cfg.get("DB_APPLICATION"));
        cda.app_class = cfg.get("DB_APPLICATION_CLASS");
        this.db_apps.put("db_application", cda);

        cda = new ComponentDbApp("db_init");
        cda.enabled = StartupUtil.isOptionEnabled(cfg.get("DB_INIT"));
        cda.app_class = cfg.get("DB_INIT_CLASS");
        this.db_apps.put("db_init", cda);

        cda = new ComponentDbApp("db_import");
        cda.enabled = StartupUtil.isOptionEnabled(cfg.get("DB_IMPORT"));
        cda.app_class = cfg.get("DB_IMPORT_CLASS");
        this.db_apps.put("db_import", cda);

        if (cfg.containsKey("DB_CLASSPATH_COMPONENTS"))
        {
            this.dbClasspathComponents = cfg.get("DB_CLASSPATH_COMPONENTS");

            if ((this.dbClasspathComponents != null) && (this.dbClasspathComponents.trim().length() == 0))
            {
                this.dbClasspathComponents = null;
            }
        }

        /*
         * this.db_app_db_check =
         * StartupUtil.isOptionEnabled(cfg.get("DB_CHECK"));
         * this.db_app_db_tool =
         * StartupUtil.isOptionEnabled(cfg.get("DB_TOOL"));
         * this.db_app_db_application =
         * StartupUtil.isOptionEnabled(cfg.get("DB_APPLICATION"));
         * this.db_app_db_application_class = cfg.get("DB_APPLICATION_CLASS");
         * this.db_app_db_init =
         * StartupUtil.isOptionEnabled(cfg.get("DB_INIT"));
         * this.db_app_db_init_class = cfg.get("DB_INIT_CLASS");
         * this.db_app_db_import =
         * StartupUtil.isOptionEnabled(cfg.get("DB_IMPORT"));
         * this.db_app_db_import_class = cfg.get("DB_IMPORT_CLASS");
         */
    }


    /**
     * Gets the application data
     */
    private void getApplicationData()
    {
        int count = Integer.parseInt(cfg.get("APP_CUSTOM_COUNT"));

        for (int i = 1; i <= count; i++)
        {
            ComponentCustomApp cca = new ComponentCustomApp(cfg.get("APP_CUSTOM_" + i + "_ID"), cfg.get("APP_CUSTOM_"
                    + i + "_FILENAME"), cfg.get("APP_CUSTOM_" + i + "_MAIN_CLASS"), cfg.get("APP_CUSTOM_" + i
                    + "_BINARY_NEEDED"), cfg.get("APP_CUSTOM_" + i + "_JAVA_PARAMS"), cfg.get("APP_CUSTOM_" + i
                    + "_NEEDS_JDBC_DRIVERS"));

            this.custom_apps.put("" + i, cca);
        }

    }


    /**
     * Gets the components data
     */
    private void getComponentsData()
    {
        int cnt = Integer.parseInt(cfg.get("LAST_COMPONENT"));

        this.root = cfg.get("ROOT");
        this.main_class = cfg.get("MAIN_CLASS");
        this.run_filename = cfg.get("RUN_FILE");

        for (int j = 1; j < 300; j++)
        {
            if (cfg.containsKey("GROUP_" + j + "_NAME"))
            {
                ComponentGroup cg = new ComponentGroup(j, cfg.get("GROUP_" + j + "_NAME"));
                this.groups.put("" + j, cg);
            }
            else
            {
                break;
            }
        }

        for (int i = 1; i <= cnt; i++)
        {
            ComponentEntry ce = new ComponentEntry();

            ce.id = i;

            if (!cfg.containsKey("COMPONENT_" + i + "_NAME")) // if entry was
                                                              // deleted
            {
                continue;
            }

            ce.group = Integer.parseInt(cfg.get("COMPONENT_" + i + "_GROUP"));
            ce.name = cfg.get("COMPONENT_" + i + "_NAME");
            ce.version = cfg.get("COMPONENT_" + i + "_VERSION");
            ce.version_num = Integer.parseInt(cfg.get("COMPONENT_" + i + "_VERSION_NUM"));
            ce.root_dir = cfg.get("COMPONENT_" + i + "_ROOT_DIR");
            ce.files = cfg.get("COMPONENT_" + i + "_FILES");

            ce.platform_specific = StartupUtil.isOptionEnabled(cfg.get("COMPONENT_" + i + "_PLATFORM_SPECIFIC"));

            ce.platform_supported = cfg.get("COMPONENT_" + i + "_PLATFORM_SUPPORTED");

            ce.enabled = true;

            if (cfg.containsKey("COMPONENT_" + i + "_ENABLED"))
            {
                // we can disable // component
                ce.enabled = StartupUtil.isOptionEnabled(cfg.get("COMPONENT_" + i + "_ENABLED"));
            }

            if (ce.platform_specific)
            {
                ce.platform_specific_type = getIntParameter("COMPONENT_" + i + "_PLATFORM_SPECIFIC_TYPE");

                String[] str_arr = this.getStringArray("COMPONENT_" + i + "_PLATFORM_SUPPORTED");

                for (String element : str_arr)
                {
                    String os = element.toLowerCase();

                    if (cfg.containsKey("COMPONENT_" + i + "_PLATFORM_SPECIFIC_JAR_" + element))
                    {
                        ce.files_java_specific.put(os, cfg.get("COMPONENT_" + i + "_PLATFORM_SPECIFIC_JAR_" + element));
                    }
                    else
                    {
                        ce.files_java_specific.put(os, os);
                    }
                }
            }

            this.groups.get("" + ce.group).addChild(ce);

            this.components.add(ce);
            this.components_ht.put(ce.name, ce);
        }

    }


    /**
     * Get Components
     * 
     * @return the array list< component entry>
     */
    public List<ComponentEntry> getComponents()
    {
        return this.components;
    }


    /**
     * Get Flat Update List
     * 
     * @return the flat update list
     */
    public ArrayList<ComponentInterface> getFlatUpdateList()
    {
        ArrayList<ComponentInterface> lst = new ArrayList<ComponentInterface>();

        for (int i = 1; i <= this.groups.size(); i++)
        {
            ComponentGroup cg = this.groups.get("" + i);
            lst.add(cg);

            // System.out.println("cg:ch:" + cg.children.size());

            for (int j = 0; j < cg.childrenCount(); j++)
            {
                lst.add(cg.getChildAt(j));
            }
        }

        return lst;

    }


    /**
     * Get Update Table
     * 
     * @return the update table
     */
    public ArrayList<ComponentInterface> getUpdateTable()
    {
        return this.getFlatUpdateList();
    }


    /**
     * Get Int Parameter.
     * 
     * @param key the key
     * 
     * @return the int parameter
     */
    public int getIntParameter(String key)
    {
        try
        {
            return Integer.parseInt(this.cfg.get(key));
        }
        catch (Exception ex)
        {
            System.out.println("*** We are either missing parameter or parameter is of \n" + "*** wrong type (key="
                    + key + "). Please send note to\n"
                    + "*** author/site of your application, so that they can fix this\n" + "*** problem.");
        }
        return 0;
    }


    /**
     * Get String Array.
     * 
     * @param key the key
     * 
     * @return the string array
     */
    public String[] getStringArray(String key)
    {
        String val = this.cfg.get(key);

        StringTokenizer strtok = new StringTokenizer(val, " ");

        String ext[] = new String[strtok.countTokens()];
        int i = 0;

        while (strtok.hasMoreTokens())
        {
            ext[i] = strtok.nextToken().toUpperCase();
            // System.out.println(ext[i]);
            i++;
        }

        return ext;

    }


    public boolean isConfigurationValid()
    {
        return configurationValid;
    }


    public void setConfigurationValid(boolean configurationValid)
    {
        this.configurationValid = configurationValid;
    }


    public static void main(String[] args)
    {
        UpdateConfiguration uc = new UpdateConfiguration(false);
        uc.init(args[0], "java.exe");

        System.out.println("Configuration: " + args[0]);
        System.out.println("Valid: " + uc.isConfigurationValid());
    }

}
