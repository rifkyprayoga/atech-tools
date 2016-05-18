package com.atech.db.hibernate;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class HibernateUtil
{

    // I18nControlAbstract i18nControl = null;
    private Session m_session = null;

    /**
     * The hconfig.
     */
    HibernateConfiguration hconfig;
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtil.class);
    @SuppressWarnings("unused")
    private int m_errorCode = 0;
    @SuppressWarnings("unused")
    private String m_errorDesc = "";
    @SuppressWarnings("unused")
    private String m_addId = "";


    /**
     * Instantiates a new hibernate util.
     * 
     * @param hconfig the hconfig
     * @param type the type
     */
    public HibernateUtil(HibernateConfiguration hconfig, int type)
    {
        this(hconfig, type, true);
    }


    /**
     * Instantiates a new hibernate util.
     * 
     * @param hconfig the hconfig
     * @param type the type
     * @param init the init
     */
    public HibernateUtil(HibernateConfiguration hconfig, int type, boolean init)
    {
        this.hconfig = hconfig;

        if (init)
        {
            this.hconfig.reInitConfiguration(type);
        }
    }


    /*
     * public HibernateUtil(Configuration cfg)
     * {
     * this(cfg, true);
     * }
     * public HibernateUtil(Configuration cfg, boolean do_init)
     * {
     * this.m_cfg = cfg;
     * if (do_init)
     * init();
     * }
     * public void init()
     * {
     * sessions = m_cfg.buildSessionFactory();
     * m_session = sessions.openSession();
     * }
     */

    /**
     * Sets the session.
     */
    public void setSession()
    {
        setSession(1);
    }


    /**
     * Sets the session.
     * 
     * @param session_nr the new session
     */
    public void setSession(int session_nr)
    {
        this.m_session = this.hconfig.getSession(session_nr);
    }


    /**
     * Gets the session.
     * 
     * @return the session
     */
    public Session getSession()
    {
        return this.m_session;
    }


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

            LOG.info(doh.getObjectName() + "::DbAdd");

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
            setError(-2, "Object is not DatabaseObjectHibernate instance", "GGCDb");

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

        LOG.info("addHibernate::" + obj.toString());

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

            LOG.info(doh.getObjectName() + "::DbEdit");

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
            setError(-2, "Object is not DatabaseObjectHibernate instance", "GGCDb");
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

        LOG.info("editHibernate::" + obj.toString());

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

            LOG.info(doh.getObjectName() + "::DbDelete");

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
            setError(-2, "Object is not DatabaseObjectHibernate instance", "GGCDb");
            LOG.error("Internal error on delete: " + obj);
            return false;
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

        LOG.info("deleteHibernate::" + obj.toString());

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

}
