package com.atech.i18n.tool.client.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Settings;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.HibernateDb;
import com.atech.graphics.dialogs.selector.SelectableInterface;
import com.atech.i18n.tool.client.DataAccessTT;
import com.atech.utils.ATechDate;


/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     GGCDb  
 *  Description:  Class for working with database (Hibernate)
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */


public class TTDb extends HibernateDb // implements DbCheckInterface HibernateDb
{
    //public static final int DB_CONFIG_LOADED = 1;
    //public static final int DB_INITIALIZED = 2;
    //public static final int DB_STARTED = 3;

    private boolean debug = true;
    // x private boolean db_debug = false;

    private static Log log = LogFactory.getLog(TTDb.class);
//    private Session m_session = null;
//    private Session m_session_2 = null;
//    private SessionFactory sessions = null;
//    private int m_errorCode = 0;
//    private String m_errorDesc = "";
//    private String m_addId = "";

    //TTDbConfig hib_config = null;

    private Configuration m_cfg = null;
    private DataAccessTT m_da;

    private int m_loadStatus = 0;

    // GLOBAL DATA

    /*
     * public ArrayList<MeterCompanyH> meter_companies = null; public
     * Hashtable<String,ArrayList<MeterH>> meters_by_cmp = null; public
     * Hashtable<String,MeterH> meters_full = null;
     */

    // ---
    // --- DB Settings
    // ---
    /*
     * protected int db_num = 0; protected String db_hib_dialect = null;
     * protected String db_driver_class = null; protected String db_conn_name =
     * null; protected String db_conn_url = null; protected String
     * db_conn_username = null; protected String db_conn_password = null;
     */

    /**
     * Constructor 
     * 
     * @param da
     */
    public TTDb(DataAccessTT da)
    {
        super(da);
        /*m_cfg =*/ //createConfiguration();
        m_da = da;
        
        //System.out.println("GGCDb");
        //System.out.println("m_da: " + m_da);
        //System.out.println("m_da.getSettings(): " + m_da.getSettings());
        //createDatabase();
        
        m_loadStatus = DB_CONFIG_LOADED;
        // debugConfig();
    }

    
    /**
     * Constructor
     */
    public TTDb()
    {
        super();
        /*m_cfg =*/ 
        createConfiguration();
        m_loadStatus = DB_CONFIG_LOADED;
        // debugConfig();
    }

    /**
     * Get Configuration
     */
    public Configuration getConfiguration()
    {
        return this.m_cfg;
    }

    /*
     * private void debugConfig() { /
     * System.out.println("Debug Configuration:");
     * 
     * //this.m_cfg.g //this.m_cfg.
     * 
     * Iterator it = this.m_cfg.getClassMappings();
     * 
     * //m_cfg.get
     * 
     * while (it.hasNext()) { org.hibernate.mapping.RootClass rc =
     * (org.hibernate.mapping.RootClass)it.next();
     * //System.out.println(it.next()); // exploreRootClass(rc); }
     */
    // }

    /**
     * Init Db
     */
    public void initDb()
    {
        openHibernateSimple();
    }

    /**
     * Is Db Started
     */
    public boolean isDbStarted()
    {
        return (this.m_loadStatus == DB_STARTED);
    }

    /** 
     * Close Db
     */
    /*public void closeDb()
    {
        this.hib_config.closeDb();
        m_loadStatus = DB_CONFIG_LOADED;
    }*/

    /** 
     * Get Hibernate Configuration
     */
/*    public TTDbConfig getHibernateConfiguration()
    {
        return this.hib_config;
    }
*/
    /** 
     * Open Hibernate Simple
     */
/*    public void openHibernateSimple()
    {
        this.hib_config.createSessionFactory();
/*        
        logInfo("openHibernateSimple", "Start");
        // getStartStatus();
        sessions = m_cfg.buildSessionFactory();
        // getStartStatus();

        m_session = sessions.openSession();
        m_session_2 = sessions.openSession();

        m_loadStatus = DB_INITIALIZED;
        logInfo("openHibernateSimple", "End");
        */
  /*      m_loadStatus = DB_INITIALIZED;
    } */


    /**
     * Get Load Status
     */
    public int getLoadStatus()
    {
        return m_loadStatus;
    }

    /** 
     * Display Error
     */
    public void displayError(String source, Exception ex)
    {

        System.out.println("Exception [" + source + "]: " + ex);
        log.error("Exception [" + source + "]: " + ex, ex);

        if (debug)
        {
            System.out.println("Exception [" + source + "]: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    private void logException(String source, Exception ex)
    {
        log.error(source + "::Exception: " + ex.getMessage(), ex);
    }

    private void logDebug(String source, String action)
    {
        log.debug(source + " - " + action);
    }

    private void logInfo(String source, String action)
    {
        log.info(source + " - " + action);
    }

    private void logInfo(String source)
    {
        log.info(source + " - Process");
    }

    /**
     * Get Session
     */
/*    public Session getSession()
    {
        return getSession(1);
    }
*/
    /**
     * Get Session
     * 
     * @param session_nr 
     * @return 
     */
/*    public Session getSession(int session_nr)
    {
        return this.hib_config.getSession(session_nr);
        
    } */

    /**
     * Create Database
     */
    public void createDatabase()
    {
        logInfo("createDatabase", "Process");
        new SchemaExport(this.getHibernateConfiguration().getConfiguration()).create(true, true);
    }

    
    // *************************************************************
    // **** SETTINGS ****
    // *************************************************************

    // ---
    // --- BASIC METHODS (Hibernate and DataLayer processing)
    // ---
/*
    public boolean add(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            log.info(doh.getObjectName() + "::DbAdd");

            try
            {
                String id = doh.DbAdd(getSession()); // getSession());
                this.m_addId = id;
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                log.error("SQLException on add: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee != null)
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
            setError(-2, "Object is not DatabaseObjectHibernate instance", "GGCDb");

            log.error("Internal error on add: " + obj);
            return false;
        }

    }

    
    public long addHibernate(Object obj)
    {
        return addHibernate(obj,1);
    }
    
    
    // this method is used for direct use with hibernate objects (unlike use
    // with our
    // datalayer classes)
    public long addHibernate(Object obj, int session_id)
    {

        log.info("addHibernate::" + obj.toString());

        try
        {
            Session sess = getSession(session_id);
            Transaction tx = sess.beginTransaction();

            Long val = (Long) sess.save(obj);
            tx.commit();

            return val.longValue();
        }
        catch (Exception ex)
        {
            log.error("Exception on addHibernate: " + ex, ex);
            return -1;
        }

    }

    public boolean edit(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            log.info(doh.getObjectName() + "::DbEdit");

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

                if (eee != null)
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
            setError(-2, "Object is not DatabaseObjectHibernate instance", "GGCDb");
            log.error("Internal error on edit: " + obj);
            return false;
        }

    }

    
    public boolean editHibernate(Object obj)
    {
        return editHibernate(obj,1);
    }
    
    
    // this method is used for direct use with hibernate objects (unlike use
    // with our
    // datalayer classes)
    public boolean editHibernate(Object obj, int session_id)
    {

        log.info("editHibernate::" + obj.toString());

        try
        {
            Session sess = getSession(session_id);
            Transaction tx = sess.beginTransaction();

            sess.update(obj);

            tx.commit();

            return true;
        }
        catch (Exception ex)
        {
            log.error("Exception on editHibernate: " + ex, ex);
            // ex.printStackTrace();
            return false;
        }

    }

    public boolean deleteHibernate(Object obj)
    {

        log.info("deleteHibernate::" + obj.toString());

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
            // ex.printStackTrace();
            return false;
        }

    }

    public boolean get(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            log.info(doh.getObjectName() + "::DbGet");

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

                if (eee != null)
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
            setError(-2, "Object is not DatabaseObjectHibernate instance", "GGCDb");
            log.error("Internal error on get: " + obj);
            return false;
        }

    }

    public boolean delete(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            log.info(doh.getObjectName() + "::DbDelete");

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

                if (eee != null)
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
            setError(-2, "Object is not DatabaseObjectHibernate instance", "GGCDb");
            log.error("Internal error on delete: " + obj);
            return false;
        }

    }

    
    public String addGetId()
    {
        return this.m_addId;
    }

    public int getErrorCode()
    {
        return this.m_errorCode;
    }

    public String getErrorDescription()
    {
        return this.m_errorDesc;
    }

    public void setError(int code, String desc, String source)
    {
        this.m_errorCode = code;
        this.m_errorDesc = source + " : " + desc;
    }
*/
    
    // *************************************************************
    // **** SETTINGS ****
    // *************************************************************

    
    /** 
     * Create Configuration
     */
    public HibernateConfiguration createConfiguration()
    {
        logInfo("createConfiguration()");
        TTDbConfig hib_config = new TTDbConfig(true);
        hib_config.getConfiguration();
    
        return hib_config;
    }


    
    
    // *************************************************************
    // **** DATABASE INIT METHODS ****
    // *************************************************************

    /** 
     * Load Static Data
     */
    public void loadStaticData()
    {
        m_loadStatus = DB_STARTED;
    }



/*
    @SuppressWarnings("unchecked")
    private void loadConfigDataEntries()
    {

        logInfo("loadConfigDataEntries()");

        try
        {
            Session sess = getSession(2);

            Hashtable<String, Settings> table = new Hashtable<String, Settings>();

            Query q = sess.createQuery("select cfg from ggc.core.db.hibernate.SettingsH as cfg");

            Iterator it = q.iterate();

            while (it.hasNext())
            {
                SettingsH eh = (SettingsH) it.next();
                table.put(eh.getKey(), new Settings(eh));
            }

            m_da.getConfigurationManager().checkConfiguration(table);
        }
        catch (Exception ex)
        {
            // log.error("Exception on loadConfigDataEntries: " +
            // ex.getMessage(), ex);
            logException("loadConfigDataEntries()", ex);
        }

    }


    @SuppressWarnings("unchecked")
    private void loadColorSchemes(Session sess)
    {
        try
        {

            logInfo("loadColorSchemes()");

            Hashtable<String, ColorSchemeH> table = new Hashtable<String, ColorSchemeH>();

            Query q = sess.createQuery("select pst from ggc.core.db.hibernate.ColorSchemeH as pst");

            Iterator it = q.iterate();

            while (it.hasNext())
            {
                ColorSchemeH eh = (ColorSchemeH) it.next();
                table.put(eh.getName(), eh);
            }

            //System.out.println("m_da: " + m_da);
            //System.out.println("m_da.getSettings(): " + m_da.getSettings());
            
            
            m_da.getSettings().setColorSchemes(table, false);
        }
        catch (Exception ex)
        {
            // log.error("Exception on loadColorSchemes: " + ex.getMessage(),
            // ex);
            logException("loadColorSchemes()", ex);
        }

    }


    // *************************************************************
    // **** NUTRITION DATA ****
    // *************************************************************
    


    
    public FoodGroup getUserFoodGroup(long id)
    {
        logInfo("getUserFoodGroup(id=" + id + ")");

        //ArrayList<FoodGroup> list = new ArrayList<FoodGroup>();

        try
        {

            Query q = getSession(2).createQuery("select pst from ggc.core.db.hibernate.FoodUserGroupH as pst order by pst.name");

            Iterator it = q.iterate();

            while (it.hasNext())
            {
                FoodUserGroupH eh = (FoodUserGroupH) it.next();
                return new FoodGroup(eh);
            }
        }
        catch (Exception ex)
        {
            logException("getUserFoodGroup()", ex);

            // log.error("Exception on getloadConfigData: " + ex.getMessage(),
            // ex);
        }

        return null;
    }
    
    
    
    
    
    
    
    
    
    
    // *************************************************************
    // **** U T I L S ****
    // *************************************************************
/*
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

    public String changeCaseWord(String in)
    {

        String t = "";

        t = in.substring(0, 1).toUpperCase();
        t += in.substring(1).toLowerCase();

        return t;

    }

    public void showByte(byte[] in)
    {

        for (int i = 0; i < in.length; i++)
        {
            System.out.println((char) in[i] + " " + in[i]);
        }

    }
*/
    
    
    /**
     * Debug Out
     */
    public void debugOut(String source, Exception ex)
    {

        this.m_errorCode = 1;
        this.m_errorDesc = ex.getMessage();

        if (debug)
            System.out.println("  " + source + "::Exception: " + ex);

        if (debug)
            ex.printStackTrace();

    }


    /**
     * Get Application Db Name
     */
    @Override
    public String getApplicationDbName()
    {
        return "ggc";
    }

}
