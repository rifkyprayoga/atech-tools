
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


using log4net;
using ATechTools.Db.NHibernate;
using NHibernate;
using NHibernate.Cfg;
using System;
using NHibernate.Tool.hbm2ddl;
using NHibernate.SqlCommand;
public class HibernateUtil
{

    //I18nControlAbstract ic = null;
    private ISession m_session = null;

    private bool is_running = false;




    /**
     * The hconfig.
     */
    private HibernateConfiguration hibernateConfig;

    protected HibernateConfiguration HibernateConfiguration
    {
        get { return hibernateConfig; }
        set { hibernateConfig = value; }
    }
    private ILog log = LogManager.GetLogger(typeof(HibernateUtil));


    protected int m_errorCode = 0;
    protected String m_errorDesc = "";
    protected String m_addId = "";


    public HibernateUtil()
    { 
    }


    /**
     * Instantiates a new hibernate util.
     * 
     * @param hconfig the hconfig
     * @param type the type
     */
    public HibernateUtil(HibernateConfiguration hconfig, int type) : this(hconfig, type, true)
    {
    }
    
    
    /**
     * Instantiates a new hibernate util.
     * 
     * @param hconfig the hconfig
     * @param type the type
     * @param init the init
     */
    public HibernateUtil(HibernateConfiguration hconfig, int type, bool init)
    {
        this.hibernateConfig = hconfig;
        
        if (init)
        {
            this.hibernateConfig.ReInitConfiguration(type);
        }
    }
    
    
    
    /**
     * Sets the session.
     */
    public void SetSession()
    {
        SetSession(1);
    }

    
    /**
     * Sets the session.
     * 
     * @param session_nr the new session
     */
    public void SetSession(int session_nr)
    {
        this.m_session = this.hibernateConfig.GetSession(session_nr);
    }
    
    /**
     * Gets the session.
     * 
     * @return the session
     */
    public ISession GetSession()
    {
        return this.HibernateConfiguration.GetSession(1);
    }


    public ISession GetNewSession()
    {
        return this.HibernateConfiguration.SessionFactory.OpenSession();
    }


    public void DisposeSession(ISession sess)
    {
        sess.Close();
    }


    /**
     * Gets the session.
     * 
     * @return the session
     */
    public ISession GetSession(int num)
    {
        return this.HibernateConfiguration.GetSession(num);
    }



    /**
     * Adds the.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public bool Add(Object obj)
    {

        if (obj is DatabaseObjectNHibernate)
        {
            DatabaseObjectNHibernate doh = (DatabaseObjectNHibernate)obj;

            log.Debug(doh.ObjectName + "::DbAdd");

            try
            {
                String id = doh.DbAdd(GetSession()); //getSession());
                this.m_addId = id;
                return true;
            }
            catch (Exception ex)
            {
                SetError(1, ex.Message, doh.ObjectName);
                log.Error("Exception on add: " + ex, ex);

                Exception eee = ex.InnerException;

                if (eee != null)
                {
                    log.Error("Nested Exception on add: " + eee.Message, eee);
                }

                return false;
            }

        }
        else
        {
            SetError(-2, "Object is not DatabaseObjectHibernate instance", this.ApplicationDbName);

            log.Error("Internal error on add: " + obj);
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
    public object AddHibernate(Object obj)
    {

        log.Info("addHibernate::" + obj.ToString());

        //ITransaction tx = null;
        ISession sess = null;
        try
        {
            sess = GetNewSession();
            //sess.FlushMode = FlushMode.Never;

            //tx = sess.BeginTransaction();

            object val = sess.Save(obj);
            //tx.Commit();

            return val;
        }
        catch (Exception ex)
        {
            //if (tx != null)
            //    tx.Rollback();

            log.Error("Exception on addHibernate: " + ex, ex);
            return -1;
        }
        finally 
        {
            sess.Close();
        }

    }


    /**
     * Edits the.
     * 
     * @param obj the obj
     * 
     * @return true, if successful
     */
    public bool Edit(Object obj)
    {

        if (obj is DatabaseObjectNHibernate)
        {
            DatabaseObjectNHibernate doh = (DatabaseObjectNHibernate)obj;

            log.Debug(doh.ObjectName + "::DbEdit");

            try
            {
                doh.DbEdit(GetSession());
                return true;
            }
            catch (Exception ex)
            {
                SetError(1, ex.Message, doh.ObjectName);
                log.Error("Exception on edit: " + ex, ex);

                Exception eee = ex.InnerException;

                if (eee != null)
                {
                    log.Error("Nested Exception on edit: " + eee.Message, eee);
                }

                return false;
            }
        }
        else
        {
            SetError(-2, "Object is not DatabaseObjectHibernate instance", this.ApplicationDbName);
            log.Error("Internal error on edit: " + obj);
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
    public bool EditHibernate(Object obj)
    {

        log.Info("editHibernate::" + obj.ToString());

        try
        {
            ISession sess = GetSession();
            ITransaction tx = sess.BeginTransaction();

            sess.Update(obj);
            tx.Commit();

            return true;
        }
        catch (Exception ex)
        {
            log.Error("Exception on editHibernate: " + ex, ex);
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
    public bool Delete(Object obj)
    {

        if (obj is DatabaseObjectNHibernate)
        {
            DatabaseObjectNHibernate doh = (DatabaseObjectNHibernate)obj;

            log.Debug(doh.ObjectName + "::DbDelete");

            try
            {

                if (doh.DbHasChildren(GetSession()))
                {
                    SetError(-3, "Object has children object", doh.ObjectName);
                    log.Error(doh.ObjectName + " had Children objects");
                    return false;
                }

                doh.DbDelete(GetSession());

                return true;
            }
            catch (Exception ex)
            {
                SetError(1, ex.Message, doh.ObjectName);
                log.Error("Exception on delete: " + ex, ex);

                Exception eee = ex.InnerException;

                if (eee != null)
                {
                    log.Error("Nested Exception on delete: " + eee.Message, eee);
                }

                return false;
            }

        }
        else
        {
            SetError(-2, "Object is not DatabaseObjectHibernate instance", this.ApplicationDbName);
            log.Error("Internal error on delete: " + obj);
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
    public bool DeleteHibernate(Object obj)
    {

        log.Info("deleteHibernate::" + obj.ToString());

        try
        {
            ISession sess = GetSession();
            ITransaction tx = sess.BeginTransaction();

            sess.Delete(obj);
            tx.Commit();

            return true;
        }
        catch (Exception ex)
        {
            log.Error("Exception on deleteHibernate: " + ex, ex);
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
    public bool Get(Object obj)
    {

        if (obj is DatabaseObjectNHibernate)
        {
            DatabaseObjectNHibernate doh = (DatabaseObjectNHibernate)obj;

            log.Debug(doh.ObjectName + "::DbGet");

            try
            {
                doh.DbGet(GetSession());
                return true;
            }
            catch (Exception ex)
            {
                SetError(1, ex.Message, doh.ObjectName);
                log.Error("Exception on get: " + ex, ex);

                Exception eee = ex.InnerException;

                if (eee != null)
                {
                    log.Error("Nested Exception on get: " + eee.Message, eee);
                }

                return false;
            }

        }
        else
        {
            SetError(-2, "Object is not DatabaseObjectHibernate instance", ApplicationDbName);
            log.Error("Internal error on get: " + obj);
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
    public void SetError(int code, String desc, String source)
    {
        this.m_errorCode = code;
        this.m_errorDesc = source + " : " + desc;
    }



    public string ApplicationDbName { get; set; }




    /**
     * Adds the get id.
     * 
     * @return the string
     */
    public String AddGetId
    {
        get
        {
            return this.m_addId;
        }
    }



    /**
     * Gets the error code.
     * 
     * @return the error code
     */
    public int ErrorCode
    {
        get
        {
            return this.m_errorCode;
        }
    }



    /**
     * Gets the error description.
     * 
     * @return the error description
     */
    public String ErrorDescription
    {
        get
        {
            return this.m_errorDesc;
        }
    }


    /**
     * Creates the database.
     */
    public void CreateDatabase()
    {
        string kw = NHibernate.Cfg.Environment.Hbm2ddlKeyWords;
        
            //Template t = Template.


        //NHibernate.Cfg.Environment.Hbm2ddlKeyWords.Insert(NHibernate.Cfg.Environment.Hbm2ddlKeyWords.Length, ",Primary");
        new SchemaExport(this.HibernateConfiguration.GetConfiguration()).Create(true, true);
    }


    public void UpdateDatabase()
    {
        new SchemaUpdate(this.HibernateConfiguration.GetConfiguration()).Execute(true, true);
    }


    
}


