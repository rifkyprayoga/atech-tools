package com.atech.web.db;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.atech.db.hibernate.DatabaseObjectHibernate;
import com.atech.web.jsp.AppContextAbstract;
import com.atech.web.util.DataAccessWeb;
import com.atech.web.util.I18nWebControl;

/**
 * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 * 
 */

public abstract class AppDbWebHibernate extends AppDbWebAbstract
{

    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(AppDbWebHibernate.class);

    private Session m_session = null;

    SessionFactory m_session_factory = null;

    /**
     * Constructor
     * 
     * @param daw
     */
    public AppDbWebHibernate(DataAccessWeb daw, AppContextAbstract aca)
    {
        super(daw, aca);
        // FIXME
        // this.m_session_factory = sess_fact;
        m_session = this.m_session_factory.openSession();

        // this.da = daw;

        loadSettings();
        loadUsers();

        // System.out.println("PIS_Web Loaded: " + pis_web_version);
    }

    public void setI18nWebControl(I18nWebControl ic)
    {
        this.ic = ic;
        initComboSystem();
        initWeekDays();
        initMonths();
    }

    public void closeDb()
    {
    }

    public void displayError(String source, Exception ex)
    {

        System.out.println("Exception [" + source + "]: " + ex);

        if (debug)
        {
            System.out.println("SQLException [" + source + "]: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    /**
     * Get Session
     * 
     * @return
     */
    public Session getSession()
    {
        // TO-DO
        m_session.clear();
        return m_session;
    }

    // /
    // / WEB METHODS
    // /

    /*
     * public void loadSettings(); { Query q =getSession().createQuery(
     * "select pst from com.atech.inf_sys.pis.db.hibernate.config.SettingsH as pst"
     * );
     * 
     * Iterator it = q.iterate();
     * 
     * while (it.hasNext()) { SettingsH st = (SettingsH )it.next();
     * this.config.put(st.getProperty(), st.getValue()); } }
     */

    /*
     * public void loadUsers() { this.users = new ArrayList<UserH>();
     * 
     * System.out.println("Load Users");
     * 
     * 
     * Query q =getSession().createQuery(
     * "select pst from com.atech.inf_sys.pis.db.hibernate.config.UserH as pst"
     * );
     * 
     * Iterator it = q.iterate();
     * 
     * while (it.hasNext()) { UserH st = (UserH)it.next(); this.users.add(st); }
     * }
     */

    public String getMessage(String key)
    {
        return key;
    }

    public String getWebLanguage()
    {
        if (config.containsKey("WEB_LANG"))
        {
            return config.get("WEB_LANG");
        }
        else
            return "SI";
    }

    public String getWebName()
    {
        if (config.containsKey("WEB_NAME"))
        {
            return config.get("WEB_NAME");
        }
        else
            return "Unknown";
    }

    //
    // BASIC METHODS
    //

    public boolean add(Object obj)
    {

        // if (debug)
        // System.out.println("add");

        Session sess = getSession();

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            if (doh.isDebugMode())
                System.out.println(doh.getObjectName() + "::DbAdd");

            try
            {
                String id = doh.DbAdd(sess); // getSession());
                this.m_addId = id;
                return true;
            }
            catch (SQLException ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                System.out.println("SQLException on add: " + ex);
                ex.printStackTrace();
                Exception eee = ex.getNextException();

                if (eee != null)
                {
                    eee.printStackTrace();
                    System.out.println(eee);
                }
                // System.exit(1);
                return false;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                System.out.println("Exception on add: " + ex);
                ex.printStackTrace();
                /*
                 * Exception eee = ex.getNextException();
                 * 
                 * if (eee!=null) { eee.printStackTrace();
                 * System.out.println(eee); }
                 */

                // System.exit(1);
                return false;
            }
            finally
            {
                sess.flush();
                sess.close();
                sess = null;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", "ZisDb");
            System.out.println("Internal error on add: " + obj);
            return false;
        }

    }

    public boolean edit(Object obj)
    {

        // System.out.println("edit");
        Session sess = getSession();

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            if (doh.isDebugMode())
                System.out.println(doh.getObjectName() + "::DbEdit");

            try
            {
                doh.DbEdit(sess);
                return true;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                return false;
            }
            finally
            {
                sess.flush();
                sess.close();
                sess = null;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", "ZisDb");
            System.out.println("Internal error on edit: " + obj);
            return false;
        }

    }

    public boolean get(Object obj)
    {

        Session sess = getSession();

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            if (doh.isDebugMode())
                System.out.println(doh.getObjectName() + "::DbGet");

            try
            {
                doh.DbGet(sess);
                return true;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                return false;
            }
            finally
            {
                sess.flush();
                sess.close();
                sess = null;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", "ZisDb");
            System.out.println("Internal error on get: " + obj);
            return false;
        }

    }

    public boolean delete(Object obj)
    {

        if (obj instanceof DatabaseObjectHibernate)
        {
            DatabaseObjectHibernate doh = (DatabaseObjectHibernate) obj;

            if (doh.isDebugMode())
                System.out.println(doh.getObjectName() + "::DbDelete");

            try
            {

                if (doh.DbHasChildren(getSession()))
                {
                    setError(-3, "Object has children object", doh.getObjectName());
                    return false;
                }

                doh.DbDelete(getSession());

                return true;
            }
            catch (Exception ex)
            {
                setError(1, ex.getMessage(), doh.getObjectName());
                return false;
            }

        }
        else
        {
            setError(-2, "Object is not DatabaseObjectHibernate instance", "ZisDb");
            System.out.println("Internal error on delete: " + obj);
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

    // *************************************************************
    // **** DAY AND MONTH HANDLING ****
    // *************************************************************

    public String getWeekDays()
    {
        if (this.week_days == null)
            initWeekDays();

        return this.week_days;
    }

    public void initWeekDays()
    {
        StringBuffer sb_wd = new StringBuffer();

        String days[] = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };

        for (int i = 0; i < 7; i++)
        {
            sb_wd.append(ic.getMessage(days[i]));

            if (i != 6)
                sb_wd.append(",");
        }

        this.week_days = sb_wd.toString();
    }

    public String getMonths()
    {
        if (this.months == null)
            initMonths();

        return this.months;
    }

    public void initMonths()
    {
        StringBuffer sb_mon = new StringBuffer();

        String mon[] = { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER",
                        "OCTOBER", "NOVEMBER", "DECEMBER" };

        for (int i = 0; i < 12; i++)
        {
            sb_mon.append(ic.getMessage(mon[i]));

            if (i != 11)
                sb_mon.append(",");
        }

        this.months = sb_mon.toString();

    }

}
