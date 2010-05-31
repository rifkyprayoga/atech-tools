package com.atech.servlets.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.servlets.util.DataAccessWeb;
import com.atech.servlets.util.I18nWebControl;

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


public abstract class AppDbWeb extends AppDbWebAbstract
{

    Connection m_connection;
    // Statement stmt;

    private static Log log = LogFactory.getLog(AppDbWeb.class);



    public AppDbWeb(DataAccessWeb daw)
    {
        super(daw);
        //this.m_session_factory = sess_fact;
        //m_session = this.m_session_factory.openSession();

        //this.da = daw;

        createConnection();
        
//        loadSettings();
//        loadUsers();

        //System.out.println("PIS_Web Loaded: " + pis_web_version);
    }

    public void setI18nWebControl(I18nWebControl ic)
    {
        this.ic = ic;
//        initComboSystem();
        initWeekDays();
        initMonths();
    }

    public void closeDb()
    {
    }

    
    public Connection getConnection()
    {
        return this.m_connection;
    }
    
    
    protected void createConnection()
    {
        try
        {
            log.debug("Creating Connection for Context !");
            
            // Load the database driver
            Class.forName(getSetting("JDBC_DRIVER")) ;
    
            //DriverManager.registerDriver("org.postgresql.Driver");
    
            String username = getSetting("JDBC_USERNAME");
            String pass = getSetting("JDBC_PASSWORD");
            
            if (username!=null)
            {
                if (pass==null)
                    pass = "";

                this.m_connection = DriverManager.getConnection(getSetting("JDBC_URL"), username, pass);
            }
            else
            {
                // Get a connection to the database
                this.m_connection = DriverManager.getConnection( getSetting("JDBC_URL"));
            }
    
            // Print all warnings
            for( SQLWarning warn = this.m_connection.getWarnings(); warn != null; warn = warn.getNextWarning() )
               {
                System.out.println( "SQL Warning:" ) ;
                System.out.println( "State  : " + warn.getSQLState()  ) ;
                System.out.println( "Message: " + warn.getMessage()   ) ;
                System.out.println( "Error  : " + warn.getErrorCode() ) ;
               }
            
        }
        catch(Exception ex)
        {
            log.error("Error on createConnection. Ex: " + ex, ex);
        }
        
    }
    
    
    private String getSetting(String key)
    {
        String ret = null;
        if (this.daw.getSettings().containsKey(key))
        {
            String v = this.daw.getSettings().get(key);
            
            if (v!=null)
            {
                if (v.trim().length()>=0)
                    ret=v.trim();
            }
        }
        
        
        return ret;
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


    // /
    // / WEB METHODS
    // /



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
        return false;

/*        
        
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
*/
    }

    public boolean edit(Object obj)
    {
        return false;
        
/*
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
*/
    }

    public boolean get(Object obj)
    {
        return false;
        
/*
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
*/
    }

    public boolean delete(Object obj)
    {
        return false;
/*
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
*/
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
