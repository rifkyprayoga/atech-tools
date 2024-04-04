package com.atech.db.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.app.AbstractApplicationContext;
import com.atech.graphics.SplashAbstract;
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

public abstract class HibernateDb
{

    private static final Logger LOG = LoggerFactory.getLogger(HibernateDb.class);

    protected DataTransformer dataTransformer;
    protected Map<Class, Map<Long, ? extends HibernateObject>> mapOfCachedObjects = new HashMap<Class, Map<Long, ? extends HibernateObject>>();

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
    // x private boolean db_debug = false;

    protected Session m_session = null;
    protected SessionFactory sessions = null;
    protected ATDataAccessAbstract dataAccess;
    protected HibernateConfiguration config = null;
    protected AbstractApplicationContext applicationContext = null;
    protected SplashAbstract m_splash = null;
    protected I18nControlAbstract i18nControl = null;

    /**
     * The m_error code.
     */
    protected int m_errorCode = 0;

    /**
     * The m_error desc.
     */
    protected String m_errorDesc = "";
    private String m_addId = "";

    // private Configuration m_cfg = null;

    protected int m_loadStatus = 0;


    /**
     * The config.
     */

    /**
     * Instantiates a new hibernate db.
     * 
     * @param dataAccess
     */
    public HibernateDb(ATDataAccessAbstract dataAccess)
    {
        config = createConfiguration();
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();
        m_loadStatus = DB_CONFIG_LOADED;
        init();
    }


    // /**
    // * Instantiates a new hibernate db.
    // */
    // public HibernateDb()
    // {
    // config = createConfiguration();
    // m_loadStatus = DB_CONFIG_LOADED;
    // // debugConfig();
    // init();
    // }

    public HibernateDb(AbstractApplicationContext ctx)
    {
        this.applicationContext = ctx;
        this.dataAccess = this.applicationContext.getDataAccess();
        this.i18nControl = dataAccess.getI18nControlInstance();

        if (ctx.hasSplashScreen())
        {
            this.m_splash = ctx.getSplashAbstractObject();
        }

        config = createConfiguration();
        init();
    }


    /**
     * Instantiates a new hibernate db.
     * 
     * @param dataAccess the da
     * @param hd 
     */
    public HibernateDb(ATDataAccessAbstract dataAccess, HibernateDb hd)
    {
        // System.out.println("HibernateDb(): " + dataAccess + ",hib_db_in=" +
        // hd + ",session_factory=" + this.sessions);
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();
        this.config = hd.getHibernateConfiguration();
        this.sessions = this.config.getSessionFactory();
        // System.out.println(
        // "HibernateDb(): " + dataAccess + ",config=" + this.config +
        // ",session_factory=" + this.sessions);
        m_loadStatus = DB_CONFIG_LOADED;
        init();
    }


    public void init()
    {
        initDataTransformer();
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
     * private void debugConfig()
     * {
     * /*
     * System.out.println("Debug Configuration:");
     * //this.m_cfg.g
     * //this.m_cfg.
     * Iterator it = this.m_cfg.getClassMappings();
     * //m_cfg.get
     * while (it.hasNext())
     * {
     * org.hibernate.mapping.RootClass rc =
     * (org.hibernate.mapping.RootClass)it.next();
     * //System.out.println(it.next());
     * // exploreRootClass(rc);
     * }
     */
    // }

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
        return this.m_loadStatus == DB_STARTED;
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
        if (this.config.session_factory == null)
        {
            this.config.createSessionFactory();
            this.sessions = this.config.session_factory;
        }

        // sessions = this.getConfiguration().buildSessionFactory();
        m_session = sessions.openSession();
        m_loadStatus = DB_INITIALIZED;
    }


    /**
     * Open Hibernate without creating new SessionFactory
     */
    public void openHibernateWithSessionFactory()
    {
        if (this.sessions == null)
        {
            if (this.config.session_factory == null)
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

        System.out.println("Exception [" + source + "]: " + ex);
        LOG.error("Exception [" + source + "]: " + ex, ex);

        if (debug)
        {
            System.out.println("Exception [" + source + "]: " + ex.getMessage());
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
        return getSession(1);
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
    // **** DB HANDLING METHODS ****
    // *************************************************************


    // ---
    // --- BASIC METHODS (Hibernate and DataLayer processing)
    // ---

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
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            LOG.trace(doh.getObjectName() + "::DbAdd");

            try
            {
                String id = doh.DbAdd(getSession()); // getSession());
                this.m_addId = id;
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("SQLException on add: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee != null)
                {
                    LOG.error("Nested Exception on add: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("Exception on add: " + ex, ex);
                return false;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());

            LOG.error("Internal error on add: " + obj);
            return false;
        }

    }


    // this method is used for direct use with hibernate objects (unlike use
    // with our
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

        LOG.trace("addHibernate::" + obj.toString());

        try
        {
            Session sess = getSession();
            Transaction tx = sess.beginTransaction();

            Long val = (Long) sess.save(obj);
            tx.commit();

            return val.longValue();
        }
        catch (Exception ex)
        {
            LOG.error("Exception on addHibernate: " + ex, ex);
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
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            LOG.debug(doh.getObjectName() + "::DbEdit");

            try
            {
                doh.DbEdit(getSession());
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("SQLException on edit: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee != null)
                {
                    LOG.error("Nested Exception on edit: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("Exception on edit: " + ex, ex);
                return false;
            }
        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());
            LOG.error("Internal error on edit: " + obj);
            return false;
        }

    }


    // this method is used for direct use with hibernate objects (unlike use
    // with our
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

        LOG.debug("editHibernate::" + obj.toString());

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
            LOG.error("Exception on editHibernate: " + ex, ex);
            // ex.printStackTrace();
            return false;
        }

    }


    /**
     * Edits the hibernate.
     * 
     * @param object the object
     * 
     * @return true, if successful
     */
    public Object getHibernate(Object object, Object id)
    {

        LOG.debug("getHibernate::" + object.toString());

        try
        {
            Session sess = getSession();
            Object o_ret = sess.load(object.getClass(), (Serializable) id);

            return o_ret;
        }
        catch (Exception ex)
        {
            LOG.error("Exception on getHibernate: " + ex, ex);
            // ex.printStackTrace();
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

        LOG.debug("deleteHibernate::" + obj.toString());

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
            LOG.error("Exception on deleteHibernate: " + ex, ex);
            // ex.printStackTrace();
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
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            LOG.debug(doh.getObjectName() + "::DbGet");

            try
            {
                doh.DbGet(getSession());
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("SQLException on get: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee != null)
                {
                    LOG.error("Nested Exception on get: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("Exception on get: " + ex, ex);
                return false;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());
            LOG.error("Internal error on get: " + obj);
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
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            LOG.debug(doh.getObjectName() + "::DbDelete");

            try
            {

                if (doh.DbHasChildren(getSession()))
                {
                    setError(-3, "Object has children object", doh.getObjectName());
                    LOG.error(doh.getObjectName() + " had Children objects");
                    return false;
                }

                doh.DbDelete(getSession());

                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("SQLException on delete: " + ex, ex);
                Exception eee = ex.getNextException();

                if (eee != null)
                {
                    LOG.error("Nested Exception on delete: " + eee.getMessage(), eee);
                }
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                LOG.error("Exception on delete: " + ex, ex);
                return false;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", getApplicationDbName());
            LOG.error("Internal error on delete: " + obj);
            return false;
        }

    }


    public boolean isObjectUsed(HibernateSelectableObject object)
    {
        Criteria criteria = object.getChildrenCriteria(this.getSession(), object);

        if (criteria == null)
            return false;

        criteria.setProjection(Projections.rowCount());

        Object o = criteria.uniqueResult();

        if (o != null)
        {
            long count = dataAccess.getLongValue(o);

            LOG.debug("{} used {} times.", object.toStringDescriptive(), count);

            return count > 0;
        }

        return false;

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
    // **** CACHED DATA and ALL DATA RETRIEVAL ****
    // *************************************************************

    /**
     * Init Data Transformer (each data we retrive can be transformed if we wish it (set Translation or
     * something else).
     */
    protected abstract void initDataTransformer();


    /**
     * Get All Data in Typed List of correct type.
     *
     * @param clazz needs to be instance of HibernateBackupSelectableObject, so your Hibernate object
     *              must implement this.
     * @param <E> type extends HibernateBackupSelectableObject
     * @return typed List
     */
    public <E extends HibernateObject> List<E> getAllTypedHibernateData(Class<E> clazz)
    {
        return getAllTypedHibernateData(clazz, false);
    }


    /**
     * Get All Data in Typed List of correct type.
     *
     * @param clazz needs to be instance of HibernateBackupSelectableObject, so your Hibernate object
     *              must implement this.
     * @param rebuildCache if we want to rebuild current cache
     * @param <E> type extends HibernateBackupSelectableObject
     *
     * @return typed List
     */
    public <E extends HibernateObject> List<E> getAllTypedHibernateData(Class<E> clazz, boolean rebuildCache)
    {
        boolean cachedType = isTypeCached(clazz);
        boolean transformationNeeded = isTransformationRequired(clazz);

        Map<Long, E> mapOfType = null;

        if (cachedType)
        {
            if (!rebuildCache)
            {
                if (mapOfCachedObjects.containsKey(clazz))
                {
                    return getListFromCache(clazz);
                }
            }

            mapOfType = new HashMap<Long, E>();
            mapOfCachedObjects.put(clazz, mapOfType);
        }

        Criteria criteria = this.getSession().createCriteria(clazz);

        specialFilteringOfCriteria(clazz, criteria);

        List results = criteria.list();

        LOG.debug("Read {} - {}", clazz.getSimpleName(), results.size());

        List<E> outList = new ArrayList<E>();

        if (cachedType)
        {
            for (Object o : results)
            {
                E typedObject = (E) o;

                if (transformationNeeded)
                    dataTransformer.transformData(typedObject, clazz);

                outList.add(typedObject);
                mapOfType.put(typedObject.getId(), typedObject);
            }
        }
        else
        {
            for (Object o : results)
            {
                E typedObject = (E) o;
                if (transformationNeeded)
                    dataTransformer.transformData(typedObject, clazz);
                outList.add(typedObject);
            }
        }

        return outList;
    }


    public <E extends HibernateObject> List<E> getHibernateData(Class<E> clazz, //
            List<? extends Criterion> criterionList, //
            int sessionNumber)
    {
        return getHibernateData(clazz, criterionList, null, sessionNumber);
    }


    public <E extends HibernateObject> List<E> getHibernateData(Class<E> clazz, //
            List<? extends Criterion> criterionList)
    {
        return getHibernateData(clazz, criterionList, null, 1);
        // Criteria criteria = this.getSession().createCriteria(clazz);
        //
        // for (Criterion criterion : criterionList)
        // {
        // criteria.add(criterion);
        // }
        //
        // List results = criteria.list();
        //
        // LOG.debug("Read {} - {}", clazz.getSimpleName(), results.size());
        //
        // List<E> outList = new ArrayList<E>();
        //
        // for (Object o : results)
        // {
        // E typedObject = (E) o;
        //
        // outList.add(typedObject);
        // }
        //
        // return outList;
    }


    public <E extends HibernateObject> List<E> getHibernateData(Class<E> clazz, //
            List<? extends Criterion> criterionList, //
            List<Order> orderList)
    {
        return getHibernateData(clazz, criterionList, orderList, 1);
    }


    public <E extends HibernateObject> List<E> getHibernateData(Class<E> clazz, //
            List<? extends Criterion> criterionList, //
            List<Order> orderList, //
            int sessionNumber)
    {
        Criteria criteria = this.getSession(sessionNumber).createCriteria(clazz);

        if (CollectionUtils.isNotEmpty(criterionList))
        {
            for (Criterion criterion : criterionList)
            {
                criteria.add(criterion);
            }
        }

        if (CollectionUtils.isNotEmpty(orderList))
        {
            for (Order order : orderList)
            {
                criteria.addOrder(order);
            }
        }

        List results = criteria.list();

        LOG.debug("Read {} - {}", clazz.getSimpleName(), results.size());

        List<E> outList = new ArrayList<E>();

        for (Object o : results)
        {
            E typedObject = (E) o;

            outList.add(typedObject);
        }

        return outList;

    }


    /**
     * You can do special filtering here depending on the class.
     *
     * @param clazz
     * @param criteria
     * @param <E>
     */
    protected abstract <E extends HibernateObject> void specialFilteringOfCriteria(Class<E> clazz, Criteria criteria);


    private <E extends HibernateObject> List<E> getListFromCache(Class<E> clazz)
    {
        // create selectable list so that we can order it
        List<HibernateSelectableObject> list = new ArrayList<HibernateSelectableObject>();

        Collection<?> values = mapOfCachedObjects.get(clazz).values();

        for (Object entry : values)
        {
            list.add((HibernateSelectableObject) entry);
        }

        Collections.sort(list);

        List<E> outList = new ArrayList<E>();

        for (Object entry : list)
        {
            outList.add((E) entry);
        }

        return outList;
    }


    protected abstract <E extends HibernateObject> boolean isTypeCached(Class<E> clazz);


    protected <E extends HibernateObject> boolean isTransformationRequired(Class<E> clazz)
    {
        return (dataTransformer != null) && (dataTransformer.isTransformationRequired(clazz));

    }


    public <E extends HibernateObject> E getCachedObject(Class<E> clazz, long id)
    {
        if (!mapOfCachedObjects.containsKey(clazz))
            getAllTypedHibernateData(clazz);

        return (E) mapOfCachedObjects.get(clazz).get(id);
    }


    // *************************************************************
    // **** SETTINGS ****
    // *************************************************************

    /**
     * Creates the configuration.
     * 
     * @return the hibernate configuration
     */
    public abstract HibernateConfiguration createConfiguration();

    /*
     * {
     * try
     * {
     * Properties props = new Properties();
     * boolean config_read = false;
     * try
     * {
     * FileInputStream in = new
     * FileInputStream("../data/GGC_Config.properties");
     * props.load(in);
     * in.close();
     * db_num = new Integer(props.getProperty("SELECTED_DB"));
     * db_conn_name = props.getProperty("DB"+db_num+"_CONN_NAME");
     * config_read = true;
     * }
     * catch (Exception ex)
     * {
     * }
     * if (config_read)
     * {
     * LOG.info("GGCDb: Loading Db Configuration #"+ db_num + ": " +
     * db_conn_name);
     * db_hib_dialect = props.getProperty("DB"+db_num+"_HIBERNATE_DIALECT");
     * db_driver_class = props.getProperty("DB"+db_num+"_CONN_DRIVER_CLASS");
     * db_conn_url = props.getProperty("DB"+db_num+"_CONN_URL");
     * db_conn_username = props.getProperty("DB"+db_num+"_CONN_USERNAME");
     * db_conn_password = props.getProperty("DB"+db_num+"_CONN_PASSWORD");
     * }
     * else
     * {
     * // we had trouble reading config so we use default database
     * db_num = 0;
     * db_conn_name = "Internal Database";
     * LOG.info(
     * "GGCDb: Database configuration not found. Using default database."
     * );
     * LOG.info("GGCDb: Loading Db Configuration #"+ db_num + ": " +
     * db_conn_name);
     * db_hib_dialect = "org.hibernate.dialect.HSQLDialect";
     * db_driver_class = "org.hsqldb.jdbcDriver";
     * db_conn_url = "jdbc:hsqldb:file:../data/ggc_db";
     * db_conn_username = "sa";
     * db_conn_password = "";
     * }
     * Configuration cfg = new Configuration()
     * .addResource("GGC_Nutrition.hbm.xml")
     * .addResource("GGC_Main.hbm.xml")
     * .addResource("GGC_Other.hbm.xml")
     * .setProperty("hibernate.dialect", db_hib_dialect)
     * .setProperty("hibernate.connection.driver_class", db_driver_class)
     * .setProperty("hibernate.connection.url", db_conn_url)
     * .setProperty("hibernate.connection.username", db_conn_username)
     * .setProperty("hibernate.connection.password", db_conn_password)
     * .setProperty("hibernate.connection.charSet", "utf-8")
     * .setProperty("hibernate.use_outer_join", "true");
     * // .setProperty("hibernate.show_sql", "true")
     * /* .setProperty("hibernate.c3p0.min_size", "5")
     * .setProperty("hibernate.c3p0.max_size", "20")
     * .setProperty("hibernate.c3p0.timeout", "1800")
     * .setProperty("hibernate.c3p0.max_statements", "50");
     */

    // System.out.println("Config loaded.");
    /*
     * return cfg;
     * }
     * catch (Exception ex)
     * {
     * LOG.error("Loading GGCConfiguration Exception: " + ex.getMessage(), ex);
     * //ex.printStackTrace();
     * }
     * return null;
     * }
     */


    // *************************************************************
    // **** DATABASE INIT METHODS ****
    // *************************************************************

    /**
     * Load static data.
     */
    public void loadStaticData()
    {
        m_loadStatus = DB_STARTED;
    }


    // *************************************************************
    // **** U T I L S ****
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
            {
                out += " ";
            }

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

        t = in.substring(0, 1).toUpperCase();
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

        for (byte element : in)
        {
            System.out.println((char) element + " " + element);
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
        {
            System.out.println("  " + source + "::Exception: " + ex);
        }

        if (debug)
        {
            ex.printStackTrace();
        }

    }


    /**
     * Get Session
     *
     * @param session_nr
     * @return
     */
    public Session getSession(int session_nr)
    {
        return this.config.getSession(session_nr);
    }

}
