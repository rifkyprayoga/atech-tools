package com.atech.db.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.atech.db.hibernate.check.DbCheckAbstract;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

// TODO: Auto-generated Javadoc
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


public abstract class HibernateConfiguration extends DbCheckAbstract
{
    
    /**
     * The ic.
     */
    I18nControlAbstract ic = null;
    private Log log = LogFactory.getLog(HibernateConfiguration.class);

    /**
     * The m_da.
     */
    protected ATDataAccessAbstract m_da = null;

    private String loading_db_str = "Loading Db Configuration #";
    private String cfg_file_missing_str = "Configuration file is missing. Make sure path to file is correct ";
    private String cfg_file_error_read_exc = "Error reading DB Config::Exception:";

    /**
     * The db_num.
     */
    public int db_num = 0;
    
    /**
     * The db_hib_dialect.
     */
    public String db_hib_dialect = null; 
    
    /**
     * The db_driver_class.
     */
    public String db_driver_class = null;
    
    /**
     * The db_conn_name.
     */
    public String db_conn_name = null;
    
    /**
     * The db_conn_url.
     */
    public String db_conn_url = null;
    
    /**
     * The db_conn_username.
     */
    public String db_conn_username = null;
    
    /**
     * The db_conn_password.
     */
    public String db_conn_password = null;
    
    /**
     * The data_loaded.
     */
    public boolean data_loaded = false;

    /**
     * The str_ldo.
     */
    public String str_ldo = null;
    
    /**
     * The m_cfg.
     */
    protected Configuration m_cfg;
    
    /**
     * The session_factory.
     */
    protected SessionFactory session_factory = null;
    
    /**
     * The sessions.
     */
    protected Hashtable<String,Session> sessions = null;

    /**
     * The Constant DB_CONTEXT_NONE.
     */
    public static final int DB_CONTEXT_NONE = 0;
    
    /**
     * The Constant DB_CONTEXT_FULL.
     */
    public static final int DB_CONTEXT_FULL = 1;
    
    /**
     * The Constant DB_CONTEXT_DBINFO.
     */
    public static final int DB_CONTEXT_DBINFO = 2;
    
    /**
     * The db_context_selected.
     */
    protected int db_context_selected = HibernateConfiguration.DB_CONTEXT_NONE;
    
    

    /**
     * Instantiates a new hibernate configuration.
     */
    public HibernateConfiguration()
    {
        this(-1);
    }

    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param val the val
     */
    public HibernateConfiguration(boolean val)
    {
        loadConfiguration(-1);
    }
    
    
    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param sel_db the sel_db
     */
    public HibernateConfiguration(int sel_db)
    {
        loadConfiguration(sel_db);
    }

    
    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param ic the ic
     */
    public HibernateConfiguration(I18nControlAbstract ic)
    {
        this(ic, null);
    }
    
    
    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param ic the ic
     * @param da the da
     */
    public HibernateConfiguration(I18nControlAbstract ic, ATDataAccessAbstract da)
    {
        this(ic, -1, da);
    }


    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param ic the ic
     * @param sel_db the sel_db
     * @param da the da
     */
    public HibernateConfiguration(I18nControlAbstract ic, int sel_db, ATDataAccessAbstract da)
    {
    	this.ic = ic;
        this.m_da = da; 
    	initI18n();
    	loadConfiguration();
    }


    /**
     * Inits the i18n.
     */
    public void initI18n()
    {
    	this.loading_db_str = setI18nVariable("LOADING_DB_CONFIGURATION", this.loading_db_str);
    	this.cfg_file_missing_str = setI18nVariable("CFG_FILE_MISSING", this.cfg_file_missing_str);
    	this.cfg_file_error_read_exc = setI18nVariable("CFG_FILE_ERROR_READ", this.cfg_file_error_read_exc);
    }


    /**
     * Sets the i18n variable.
     * 
     * @param key the key
     * @param original_value the original_value
     * 
     * @return the string
     */
    public String setI18nVariable(String key, String original_value)
    {
    	if (!ic.getMessage(key).equals(key)) 
    	    return ic.getMessage(key);
    	else
    	    return original_value;
    }

    
    /**
     * Gets the connection name.
     * 
     * @return the connection name
     */
    public String getConnectionName()
    {
        return this.db_conn_name;
    }
    
    
    /**
     * Gets the hibernate dialect.
     * 
     * @return the hibernate dialect
     */
    public String getHibernateDialect()
    {
        return this.db_hib_dialect;
    }


    /**
     *  In this method we need to set configuration_file variable, with path and filename of 
     *  configuration.
     *  
     * @param sel_db 
     */
    public void loadConfiguration(int sel_db)
    {

        try
        {

            Properties props = new Properties();
            boolean config_found = true;
            
            if (!doesConfigurationExist())
            {
                log.error(this.cfg_file_missing_str + "(" + this.getConfigurationFile() + ").");
                config_found = false;
                loadDefaultDatabase(config_found);
                return;
            }

            FileInputStream in = new FileInputStream(this.getConfigurationFile());
            props.load(in);

    	    if (sel_db==-1)
    	    {
        		String num_db = (String)props.get("SELECTED_DB");
        		db_num = Integer.parseInt(num_db);
    	    }
    	    else
    	        db_num = sel_db;

    	    //System.out.println("Loading Db #" + db_num);
    	    log.info(loading_db_str + db_num);

            if (db_num == 0)
            {
                loadDefaultDatabase(config_found);
            }
            else
            {
                db_conn_name = (String)props.get("DB"+db_num+"_CONN_NAME");
                db_hib_dialect = (String)props.get("DB"+db_num+"_HIBERNATE_DIALECT");

                db_driver_class = (String)props.get("DB"+db_num+"_CONN_DRIVER_CLASS");
                db_conn_url = (String)props.get("DB"+db_num+"_CONN_URL");
                db_conn_username = (String)props.get("DB"+db_num+"_CONN_USERNAME");
                db_conn_password = (String)props.get("DB"+db_num+"_CONN_PASSWORD");

                data_loaded = true;
            }

            in.close();

        }
        catch (Exception ex)
        {
            System.out.println(cfg_file_error_read_exc + " " + ex);
            ex.printStackTrace();
        }

    }


    /**
     * Load configuration.
     */
    public void loadConfiguration()
    {
        loadConfiguration(-1);
    }



    /**
     * Does configuration exist.
     * 
     * @return true, if successful
     */
    public boolean doesConfigurationExist()
    {
        //System.out.println(this.getConfigurationFile());
        
        File f = new File(this.getConfigurationFile());
        return (f.exists());
    }




    /**
     * Gets the configuration.
     * 
     * @return the configuration
     */
    public Configuration getConfiguration()
    {
        Configuration cfg = this.getCustomConfiguration(this.getResourceFiles());
        this.db_context_selected = HibernateConfiguration.DB_CONTEXT_FULL;
        return cfg;
    }

    /** 
     * getDbInfoConfiguration
     */
    public Configuration getDbInfoConfiguration()
    {
        Configuration cfg = this.getCustomConfiguration(this.getDbInfoResource());
        this.db_context_selected = HibernateConfiguration.DB_CONTEXT_DBINFO;
        return cfg;
    }
    
    

    private Configuration getCustomConfiguration(String[] res_files)
    {
        Configuration cfg = new Configuration()
                            .setProperty("hibernate.dialect", db_hib_dialect)
                            .setProperty("hibernate.connection.driver_class", db_driver_class)
                            .setProperty("hibernate.connection.url", db_conn_url)
                            .setProperty("hibernate.connection.username", db_conn_username)
                            .setProperty("hibernate.connection.password", db_conn_password)
                            .setProperty("hibernate.connection.charSet", "utf-8")
                            .setProperty("hibernate.use_outer_join", "true");
        
        if (this.getMappingLocation()!=null)
        {
            cfg.setProperty("mappingLocations", this.getMappingLocation());
        }
        

        for(int i=0; i<res_files.length; i++)
        {
            cfg.addResource(res_files[i]);
        }
        
        
        //  .setProperty("hibernate.show_sql", "true")
        //  .setProperty("hibernate.c3p0.min_size", "5")
        //  .setProperty("hibernate.c3p0.max_size", "20")
        //  .setProperty("hibernate.c3p0.timeout", "1800")
        //  .setProperty("hibernate.c3p0.max_statements", "50"); */
        
        //cfg.setProperty("hibernate.cglib.use_reflection_optimizer", "false");

        this.m_cfg = cfg;
        
        return cfg;
    }
    

    
    /**
     * Load Default Database
     * 
     * @param config_found
     */
    public abstract void loadDefaultDatabase(boolean config_found);

    /**
     * Get Configuration File
     * 
     * @return 
     */
    public abstract String getConfigurationFile();
    
    /**
     * Get Resource Files
     * 
     * @return 
     */
    public abstract String[] getResourceFiles();
    
    /**
     * Get Number Of Sessions
     * 
     * @return 
     */
    public abstract int getNumberOfSessions();
    
    /**
     * Creates the session factory.
     */
    public void createSessionFactory()
    {
        this.session_factory = m_cfg.buildSessionFactory();
        this.sessions = new Hashtable<String,Session>();
        
        for(int i=1; i<=this.getNumberOfSessions(); i++)
        {
            this.sessions.put("" +i, this.session_factory.openSession() );
        }
        
    }
    
    
    public String getMappingLocation()
    {
        return null;
    }
    
    
    /**
     * Gets the session.
     * 
     * @param num the num
     * 
     * @return the session
     */
    public Session getSession(int num)
    {
        return getSession(num, false);
    }
    
    /**
     * Gets the session.
     * 
     * @param num the num
     * @param dont_clear the dont_clear
     * 
     * @return the session
     */
    public Session getSession(int num, boolean dont_clear)
    {
        if (this.session_factory==null)
            createSessionFactory();
        
        Session s = this.sessions.get("" + num);
        
        if (!dont_clear)
            s.clear();
        
        return s;
    }
    
    /**
     * Reset configuration.
     */
    public void resetConfiguration()
    {
        closeSessions();
        createSessionFactory();
    }
    
    
    /**
     * Close sessions.
     */
    public void closeSessions()
    {
        if (this.sessions!=null)
        {
            for(int i=1; i<=this.getNumberOfSessions(); i++)
            {
                Session s = this.sessions.get("" +i);
                s.close();
                s=null;
            }
        }
        
        if (this.session_factory!=null)
        {
            this.session_factory.close();
            this.session_factory = null;
        }
    }
    
    
    /**
     * Close db.
     */
    public void closeDb()
    {
        
        if (this.getHibernateDialect().equals("org.hibernate.dialect.HSQLDialect"))
        {
            try
            {
                getSession(1).connection().createStatement().execute("SHUTDOWN");
            }
            catch (Exception ex)
            {
                System.out.println("closeDb:Exception> " + ex);
            }
        }
        
        closeSessions();
        
    }
    
    
    /**
     * Re init configuration.
     * 
     * @param type the type
     */
    public void reInitConfiguration(int type)
    {
        if (this.db_context_selected == type)
        {
            if (this.session_factory==null)
                createSessionFactory();
        }
        else
        {
            this.closeSessions();
            
            if (type == HibernateConfiguration.DB_CONTEXT_DBINFO)
            {
                this.getDbInfoConfiguration();
            }
            else
                this.getConfiguration();

            createSessionFactory();
        }
    }
    
    
}


