package com.atech.db.hibernate;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

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


public abstract class HibernateDb
{
    
    /**
     * The Constant DB_CONFIG_LOADED.
     */
    public static final int DB_CONFIG_LOADED = 1;
    
    /**
     * The Constant DB_INITIALIZED.
     */
    public static final int DB_INITIALIZED = 2;
    
    /**
     * The Constant DB_STARTED.
     */
    public static final int DB_STARTED = 3;

    private boolean debug = true;
//x    private boolean db_debug = false;
    
    private static Log log = LogFactory.getLog(HibernateDb.class); 
    
    /**
     * The m_session.
     */
    protected Session m_session = null;
    
    /**
     * The sessions.
     */
    protected SessionFactory sessions = null;
    
    /**
     * The m_error code.
     */
    protected int m_errorCode = 0;
    
    /**
     * The m_error desc.
     */
    protected String m_errorDesc = "";
    private String m_addId = "";


    //private Configuration m_cfg = null;
    protected ATDataAccessAbstract m_da; 

    protected int m_loadStatus = 0;


    /**
     * The config.
     */
    protected HibernateConfiguration config = null;

    




    /**
     * Instantiates a new hibernate db.
     * 
     * @param da the da
     */
    public HibernateDb(ATDataAccessAbstract da)
    {
        config = createConfiguration();
        m_da = da;
        m_loadStatus = DB_CONFIG_LOADED;
    }


    /**
     * Instantiates a new hibernate db.
     */
    public HibernateDb()
    {
        config = createConfiguration();
        m_loadStatus = DB_CONFIG_LOADED;
//	debugConfig();
    }

    
    /**
     * Instantiates a new hibernate db.
     * 
     * @param da the da
     * @param hd 
     */
    public HibernateDb(ATDataAccessAbstract da, HibernateDb hd)
    {
        //System.out.println("HibernateDb(): " + da + ",config=" + this.config + ",session_factory=" + this.sessions);
        this.m_da = da;
        this.config = hd.getHibernateConfiguration();
        this.sessions = this.config.session_factory;
        System.out.println("HibernateDb(): " + m_da + ",config=" + this.config + ",session_factory=" + this.sessions);
        m_loadStatus = DB_CONFIG_LOADED;
    }
    
    
    
    /**
     * Gets the configuration.
     * 
     * @return the configuration
     */
    public Configuration getConfiguration()
    {
        return this.config.getConfiguration();
    }

    
    /**
     * Gets the hibernate configuration.
     * 
     * @return the hibernate configuration
     */
    public HibernateConfiguration getHibernateConfiguration()
    {
        return this.config;
    }
    
    
/*
    private void debugConfig()
    {
/*
	System.out.println("Debug Configuration:");

        //this.m_cfg.g
        //this.m_cfg.

        Iterator it = this.m_cfg.getClassMappings();

        //m_cfg.get
        
        while (it.hasNext())
        {
            org.hibernate.mapping.RootClass rc = (org.hibernate.mapping.RootClass)it.next();
            //System.out.println(it.next());
//	    exploreRootClass(rc);
        }

*/
//    }



    /**
     * Inits the db.
     */
    public void initDb()
    {
        openHibernateSimple();
    }

    /**
     * Checks if is db started.
     * 
     * @return true, if is db started
     */
    public boolean isDbStarted()
    {
        return(this.m_loadStatus == DB_STARTED);
    }

    /**
     * Close db.
     */
    public void closeDb()
    {
        if (this.getHibernateConfiguration().db_hib_dialect.equals("org.hibernate.dialect.HSQLDialect"))
        {
            try
            {
                getSession().connection().createStatement().execute("SHUTDOWN");
            }
            catch (Exception ex)
            {
                System.out.println("closeDb:Exception> " + ex);
            }
        }
        getSession().close();
        m_session = null;
        m_loadStatus = DB_CONFIG_LOADED;
    }


    /**
     * Open hibernate simple
     */
    public void openHibernateSimple()
    {
        if (this.config.session_factory==null)
        {
            this.config.createSessionFactory();
            this.sessions = this.config.session_factory;
        }
        
        //sessions = this.getConfiguration().buildSessionFactory();
        m_session = sessions.openSession();
        m_loadStatus = DB_INITIALIZED;
    }

    
    
    /**
     * Open Hibernate without creating new SessionFactory
     */
    public void openHibernateWithSessionFactory()
    {
        if (this.sessions==null)
        {
            if (this.config.session_factory==null)
            {
                this.config.createSessionFactory();
                this.sessions = this.config.session_factory;
            }
        }
        
        
        m_session = sessions.openSession();
        m_loadStatus = DB_INITIALIZED;
    }
    
    
    
    
    /**
     * Get Session Factory
     * 
     * @return
     */
    public SessionFactory getSessionFactory()
    {
        return this.sessions;
    }
    

    /**
     * Gets the load status.
     * 
     * @return the load status
     */
    public int getLoadStatus()
    {
        return m_loadStatus;
    }


    

    /**
     * Display error.
     * 
     * @param source the source
     * @param ex the ex
     */
    public void displayError(String source, Exception ex)
    {

        System.out.println("Exception ["+ source + "]: " + ex);
        log.error("Exception [" + source + "]: " + ex, ex);

        if (debug)
        {
            System.out.println("Exception ["+ source +"]: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    /**
     * Gets the session.
     * 
     * @return the session
     */
    public Session getSession()
    {
        return this.config.getSession(1);
        //m_session.clear();
        //return m_session;
    }


    /**
     * Creates the database.
     */
    public void createDatabase()
    {
        new SchemaExport(this.getConfiguration()).create(true, true);
    }

    
    /**
     * Gets the application db name.
     * 
     * @return the application db name
     */
    public abstract String getApplicationDbName();
    
    

    // *************************************************************
    // ****              DB HANDLING METHODS                    ****
    // *************************************************************

    //---
    //---  BASIC METHODS (Hibernate and DataLayer processing)
    //---



    /**
     * Adds the.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public boolean add(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate)obj;

            log.trace(doh.getObjectName()+"::DbAdd");

            try
            {
                String id = doh.DbAdd(getSession()); //getSession());
                this.m_addId = id;
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("SQLException on add: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee!=null)
                {
                    log.error("Nested Exception on add: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("Exception on add: " + ex, ex);
                return false;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());
            
            log.error("Internal error on add: " + obj);
            return false;
        }

    }


    // this method is used for direct use with hibernate objects (unlike use with our 
    // datalayer classes)
    /**
     * Adds the hibernate.
     * 
     * @param obj the obj
     * 
     * @return the long
     */
    public long addHibernate(Object obj)
    {

        log.trace("addHibernate::" + obj.toString());

        try
        {
            Session sess = getSession();
            Transaction tx = sess.beginTransaction();

            Long val = (Long)sess.save(obj);
            tx.commit();

            return val.longValue();
        }
        catch (Exception ex)
        {
            log.error("Exception on addHibernate: " + ex, ex);
            return -1;
        }

    }



    /**
     * Edits the.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public boolean edit(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate)obj;

            log.debug(doh.getObjectName()+"::DbEdit");

            try
            {
                doh.DbEdit(getSession()); 
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("SQLException on edit: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee!=null)
                {
                    log.error("Nested Exception on edit: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("Exception on edit: " + ex, ex);
                return false;
            }
        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());
            log.error("Internal error on edit: " + obj);
            return false;
        }

    }


    // this method is used for direct use with hibernate objects (unlike use with our 
    // datalayer classes)
    /**
     * Edits the hibernate.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public boolean editHibernate(Object obj)
    {

        log.debug("editHibernate::" + obj.toString());

        try
        {
            Session sess = getSession();
            Transaction tx = sess.beginTransaction();

            sess.update(obj);

            tx.commit();

            return true;
        }
        catch (Exception ex)
        {
            log.error("Exception on editHibernate: " + ex, ex);
            //ex.printStackTrace();
            return false;
        }

    }

    
    
    /**
     * Edits the hibernate.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public Object getHibernate(Object object, Object id)
    {

 
        log.debug("getHibernate::" + object.toString());

        try
        {
            Session sess = getSession();
            Object o_ret = sess.load(object.getClass(), (Serializable)id);

            return o_ret;
        }
        catch (Exception ex)
        {
            log.error("Exception on getHibernate: " + ex, ex);
            //ex.printStackTrace();
            return null;
        }

    }
    
    
    
    

    /**
     * Delete hibernate.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public boolean deleteHibernate(Object obj)
    {

        log.debug("deleteHibernate::" + obj.toString());

        try
        {
            Session sess = getSession();
            Transaction tx = sess.beginTransaction();

            sess.delete(obj);

            tx.commit();

            return true;
        }
        catch (Exception ex)
        {
            log.error("Exception on deleteHibernate: " + ex, ex);
            //ex.printStackTrace();
            return false;
        }

    }




    /**
     * Gets the.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public boolean get(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate)obj;

            log.debug(doh.getObjectName()+"::DbGet");

            try
            {
                doh.DbGet(getSession());
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("SQLException on get: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee!=null)
                {
                    log.error("Nested Exception on get: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("Exception on get: " + ex, ex);
                return false;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());
            log.error("Internal error on get: " + obj);
            return false;
        }

    }




    /**
     * Delete.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public boolean delete(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate)obj;

            log.debug(doh.getObjectName()+"::DbDelete");

            try
            {

                if (doh.DbHasChildren(getSession()))
                {
                    setError(-3, "Object has children object", doh.getObjectName());
                    log.error(doh.getObjectName() + " had Children objects");
                    return false;
                }

                doh.DbDelete(getSession());

                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("SQLException on delete: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee!=null)
                {
                    log.error("Nested Exception on delete: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("Exception on delete: " + ex, ex);
                return false;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());
            log.error("Internal error on delete: " + obj);
            return false;
        }

    }






    /**
     * Adds the get id.
     * 
     * @return the string
     */
    public String addGetId()
    {
        return this.m_addId;
    }



    /**
     * Gets the error code.
     * 
     * @return the error code
     */
    public int getErrorCode()
    {
        return this.m_errorCode;
    }



    /**
     * Gets the error description.
     * 
     * @return the error description
     */
    public String getErrorDescription()
    {
        return this.m_errorDesc;
    }



    /**
     * Sets the error.
     * 
     * @param code the code
     * @param desc the desc
     * @param source the source
     */
    public void setError(int code, String desc, String source)
    {
        this.m_errorCode = code;
        this.m_errorDesc = source + " : " + desc;
    }


    // *************************************************************
    // ****                     SETTINGS                        ****
    // *************************************************************


    /**
     * Creates the configuration.
     * 
     * @return the hibernate configuration
     */
    public abstract HibernateConfiguration createConfiguration();
    
    /*
    {
        
        


        try
        {

            Properties props = new Properties();

            boolean config_read = false;

            try
            {
                FileInputStream in = new FileInputStream("../data/GGC_Config.properties");
                props.load(in);
                in.close();

                db_num = new Integer(props.getProperty("SELECTED_DB"));
                db_conn_name = props.getProperty("DB"+db_num+"_CONN_NAME");

                config_read = true;
            }
            catch (Exception ex)
            {

            }



            if (config_read)
            {
                log.info("GGCDb: Loading Db Configuration #"+ db_num + ": " + db_conn_name);

                db_hib_dialect = props.getProperty("DB"+db_num+"_HIBERNATE_DIALECT");


                db_driver_class = props.getProperty("DB"+db_num+"_CONN_DRIVER_CLASS");
                db_conn_url = props.getProperty("DB"+db_num+"_CONN_URL");
                db_conn_username = props.getProperty("DB"+db_num+"_CONN_USERNAME");
                db_conn_password = props.getProperty("DB"+db_num+"_CONN_PASSWORD");
            }
            else
            {
                // we had trouble reading config so we use default database

                db_num = 0;
                db_conn_name = "Internal Database";

                log.info("GGCDb: Database configuration not found. Using default database.");
                log.info("GGCDb: Loading Db Configuration #"+ db_num + ": " + db_conn_name);

                db_hib_dialect = "org.hibernate.dialect.HSQLDialect";
                db_driver_class = "org.hsqldb.jdbcDriver";
                db_conn_url = "jdbc:hsqldb:file:../data/ggc_db";
                db_conn_username = "sa";
                db_conn_password = "";
            }



            Configuration cfg = new Configuration()
                                .addResource("GGC_Nutrition.hbm.xml")
                                .addResource("GGC_Main.hbm.xml")
                                .addResource("GGC_Other.hbm.xml")

                                .setProperty("hibernate.dialect", db_hib_dialect)
                                .setProperty("hibernate.connection.driver_class", db_driver_class)
                                .setProperty("hibernate.connection.url", db_conn_url)
                                .setProperty("hibernate.connection.username", db_conn_username)
                                .setProperty("hibernate.connection.password", db_conn_password)
                                .setProperty("hibernate.connection.charSet", "utf-8")
                                .setProperty("hibernate.use_outer_join", "true");
//	      .setProperty("hibernate.show_sql", "true")
/*                            .setProperty("hibernate.c3p0.min_size", "5")
                            .setProperty("hibernate.c3p0.max_size", "20")
                            .setProperty("hibernate.c3p0.timeout", "1800")
                            .setProperty("hibernate.c3p0.max_statements", "50"); */


//	    System.out.println("Config loaded.");
/*
            return cfg;
        }
        catch (Exception ex)
        {
            log.error("Loading GGCConfiguration Exception: " + ex.getMessage(), ex);
            //ex.printStackTrace();
        }
        return null;
    }
*/

    // *************************************************************
    // ****               DATABASE INIT METHODS                 ****
    // *************************************************************


    /**
     * Load static data.
     */
    public void loadStaticData()
    {
        m_loadStatus = DB_STARTED;
    }
    
    

    

    

    // *************************************************************
    // ****                       U T I L S                     ****
    // *************************************************************


    /**
     * Change case.
     * 
     * @param in the in
     * 
     * @return the string
     */
    public String changeCase(String in)
    {

        StringTokenizer stok = new StringTokenizer(in, " ");

        boolean first = true;
        String out = "";

        while (stok.hasMoreTokens())
        {
            if (!first)
                out += " ";

            out += changeCaseWord(stok.nextToken());
            first = false;
        }

        return out;

    }

    /**
     * Change case word.
     * 
     * @param in the in
     * 
     * @return the string
     */
    public String changeCaseWord(String in)
    {

        String t = "";

        t = in.substring(0,1).toUpperCase();
        t += in.substring(1).toLowerCase();

        return t;

    }


    /**
     * Show byte.
     * 
     * @param in the in
     */
    public void showByte(byte[] in)
    {

        for (int i=0;i<in.length; i++)
        {
            System.out.println((char)in[i] + " " + in[i]);
        }

    }



    /**
     * Debug out.
     * 
     * @param source the source
     * @param ex the ex
     */
    public void debugOut(String source, Exception ex)
    {

        this.m_errorCode = 1;
        this.m_errorDesc = ex.getMessage();

        if (debug)
            System.out.println("  " + source + "::Exception: "+ex);

        if (debug)
            ex.printStackTrace();


    }


}


