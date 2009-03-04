package com.atech.update.config;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

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
    
    //StartupUtil m_da = null;
    
    /**
     * Configuration
     */
	Hashtable<String,String> cfg;
	
	/**
	 * Components.
	 */
	ArrayList<ComponentEntry> components;
	
    /**
     * Components HashTable.
     */
    public Hashtable<String,ComponentEntry> components_ht = new Hashtable<String,ComponentEntry>();
    
    /**
     * Group.
     */
    public Hashtable<String,ComponentGroup> groups = new Hashtable<String,ComponentGroup>();
	
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
    public Hashtable<String,ComponentCustomApp> custom_apps = new Hashtable<String,ComponentCustomApp>(); 
	
    
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
     * JDBC Files.
     */
    public String jdbc_files = "";
    
    /**
     * Db App: Do we want DbTool.
     */
    public boolean db_app_db_tool = false;
    
    /**
     * Db App: Do we want DbCheck.
     */
    public boolean db_app_db_check = false;
    
    /**
     * Db App: Do we want Db Application.
     */
    public boolean db_app_db_application = false;

    /**
     * Db Application class.
     */
    public String db_app_db_application_class = "";
    
    /**
     * Db App: Do we want DbCheck.
     */
    public boolean db_app_db_init = false;
    
    /**
     * DbInit Class.
     */
    public String db_app_db_init_class = "";

    /**
     * Db App: Do we want DbImport.
     */
    public boolean db_app_db_import = false;
    
    /**
     * DbImport Class.
     */
    public String db_app_db_import_class = "";


	/** The needed_params. */
	private String[] needed_params = {
            "PRODUCT_ID",
            "DB_TOOL", 
            "DB_CHECK", 
            "APP_CUSTOM_COUNT",
            "APP_CUSTOM_1_ID", 
            
            "DB_UPDATE_SITE",
            "DB_VERSION_REQUIRED",
            "DB_ENABLED",
            "DB_CONFIGURATION_CLASS",

			"LAST_COMPONENT",
			"GROUP_1_NAME",
			"COMPONENT_1_NAME",
	};
	
	
	/**
	 * Constructor.
	 */
	public UpdateConfiguration()
	{
	    //m_da  = ATDataAccess.getInstance();
		Hashtable<String,String> cfg_1 = StartupUtil.getConfiguration("StartupConfig.properties");
		init(cfg_1.get("UPDATE_CONFIG"), cfg_1.get("JAVA_EXE"));
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

		loadConfiguration(upd_cfg_file);
	}
	
	
	/**
	 * Discover jdbc classes
	 */
	private void discoverJdbcClasses()
	{
	    //System.out.println("disocverJDBC");
	    
	    File f = new File("../lib");
	    
	    File[] fls = f.listFiles();
	    
	    if (fls==null)
	        return;
	    
	    ArrayList<File> lst = new ArrayList<File>();
	    
	    for(int i=0; i<fls.length; i++)
	    {
	        if ((fls[i].getName().contains("jdbc")) || (fls[i].getName().contains("db")))
	            lst.add(fls[i]);
	        
	    }
	    
	    FileFilter fileFilter = new FileFilter() {
	        public boolean accept(File f1) 
	        {
	            if (f1.isDirectory()) 
	            {
	                return false;
	            }

	            String ext = f1.getName();
	            
	            if (ext.endsWith(".jar"))
	            {
	                return true;
	            }

	            return false;
	        }	        
	        
	        
	        
	    };
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i=0; i<lst.size(); i++)
	    {
	        String _root = "../lib/" + lst.get(i).getName() + "/";
	        
	        File[] subFiles = lst.get(i).listFiles(fileFilter);
	        
	        for(int j=0; j<subFiles.length; j++)
	        {
	            sb.append(_root + subFiles[j].getName() + ";");
	        }
	    }
	    
	    String t = sb.toString();
	    
	    if (t.trim().length()==0)
	        this.jdbc_files = "";
	    else
	        this.jdbc_files = t.substring(0, t.length()-1);
	    
	    //System.out.println("Jdbc: " + this.jdbc_files);
	    
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
		this.cfg = StartupUtil.getConfiguration(upd_cfg_file);
		
		boolean found = true;
		
		for(int i=0; i<this.needed_params.length; i++)
		{
			if (!cfg.containsKey(this.needed_params[i]))
			{
			    System.out.println("Following parameter is missing:" + this.needed_params[i]);
				found = false;
			}
		}

		if (found)
		{
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
	

	/**
	 * Gets the Db data
	 */
	private void getDbData()
	{
	    this.db_update_site = cfg.get("DB_UPDATE_SITE");
	    this.db_version_required = Integer.parseInt(cfg.get("DB_VERSION_REQUIRED"));
	    this.db_enabled = StartupUtil.isOptionEnabled(cfg.get("DB_ENABLED"));
	    this.db_config_class = cfg.get("DB_CONFIGURATION_CLASS");
	}
	

	/**
	 * Gets the Db application data
	 */
	private void getDbApplicationData()
	{
        this.db_app_db_check = StartupUtil.isOptionEnabled(cfg.get("DB_CHECK"));
        this.db_app_db_tool = StartupUtil.isOptionEnabled(cfg.get("DB_TOOL"));
        
        this.db_app_db_application = StartupUtil.isOptionEnabled(cfg.get("DB_APPLICATION"));
        this.db_app_db_application_class = cfg.get("DB_APPLICATION_CLASS");

        this.db_app_db_init = StartupUtil.isOptionEnabled(cfg.get("DB_INIT"));
        this.db_app_db_init_class = cfg.get("DB_INIT_CLASS");
    
        this.db_app_db_import = StartupUtil.isOptionEnabled(cfg.get("DB_IMPORT"));
        this.db_app_db_import_class = cfg.get("DB_IMPORT_CLASS");
	}
	
	
	/**
	 * Gets the application data
	 */
	private void getApplicationData()
	{
	    int count = Integer.parseInt(cfg.get("APP_CUSTOM_COUNT"));
	    
        for(int i=1; i<=count; i++)
        {
            ComponentCustomApp cca = new ComponentCustomApp(
                    cfg.get("APP_CUSTOM_" + i +"_ID"), 
                    cfg.get("APP_CUSTOM_" + i +"_FILENAME"), 
                    cfg.get("APP_CUSTOM_" + i +"_MAIN_CLASS"), 
                    cfg.get("APP_CUSTOM_" + i +"_BINARY_NEEDED"),
                    cfg.get("APP_CUSTOM_" + i +"_JAVA_PARAMS"),
                    cfg.get("APP_CUSTOM_" + i +"_NEEDS_JDBC_DRIVERS")
            );
            
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
		
		for(int j=1; j<300; j++)
		{
			if (cfg.containsKey("GROUP_" + j + "_NAME"))
			{
				ComponentGroup cg = new ComponentGroup(j, cfg.get("GROUP_" + j + "_NAME"));
				this.groups.put("" + j, cg);
			}
			else
				break;
		}
		
		
		for(int i=1; i<=cnt; i++)
		{
			ComponentEntry ce = new ComponentEntry();
			
			ce.id = i;
			ce.group = Integer.parseInt(cfg.get("COMPONENT_" + i + "_GROUP"));
			ce.name = cfg.get("COMPONENT_" + i + "_NAME");
			ce.version = cfg.get("COMPONENT_" + i + "_VERSION");
			ce.version_num = Integer.parseInt(cfg.get("COMPONENT_" + i + "_VERSION_NUM"));
			ce.root_dir = cfg.get("COMPONENT_" + i + "_ROOT_DIR");
			ce.files = cfg.get("COMPONENT_" + i + "_FILES");

			ce.platform_specific = StartupUtil.isOptionEnabled(cfg.get("COMPONENT_" + i + "_PLATFORM_SPECIFIC"));

			if (ce.platform_specific)
			{
			    ce.platform_specific_type = getIntParameter("COMPONENT_" + i + "_PLATFORM_SPECIFIC_TYPE"); 

			    String[] str_arr = this.getStringArray("COMPONENT_" + i + "_PLATFORM_SUPPORTED");
			    
			    for(int j=0; j<str_arr.length; j++)
			    {
			        String os = str_arr[j].toLowerCase();
			        
			        if (cfg.containsKey("COMPONENT_" + i + "_PLATFORM_SPECIFIC_JAR_" + str_arr[j]))
			            ce.files_java_specific.put(os, cfg.get("COMPONENT_" + i + "_PLATFORM_SPECIFIC_JAR_" + str_arr[j]));
			        else
			            ce.files_java_specific.put(os, os);
			    }
			}
			
			this.groups.get(""+ce.group).addChild(ce);
			
			this.components.add(ce);
			this.components_ht.put(ce.name, ce);
		}
		
	}
	
	
	/**
	 * Get Components
	 * 
	 * @return the array list< component entry>
	 */
	public ArrayList<ComponentEntry> Components()
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
	    
	    for(int i=1; i<=this.groups.size(); i++)
	    {
	        ComponentGroup cg = this.groups.get("" + i);
	        lst.add(cg);
	        
	        
	        
	        for(int j=0; j<cg.childrenCount(); j++)
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
        catch(Exception ex)
        {
            System.out.println("*** We are either missing parameter or parameter is of \n" +
                               "*** wrong type (key=" + key + "). Please send note to\n" +
                               "*** author/site of your application, so that they can fix this\n" +
                               "*** problem.");
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
        int i=0;
        
        while (strtok.hasMoreTokens())
        {
            ext[i] = strtok.nextToken().toUpperCase();
            //System.out.println(ext[i]);
            i++;
        }

        return ext;
        
    }
    
	
	
}
