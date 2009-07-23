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
using System;

using NHibernate;
using NHibernate.Cfg;
using NHibernate.Util;



namespace ATechTools.Db.NHibernate
{

public abstract class HibernateDb : HibernateUtil
{
    
    /**
     * The Constant DB_CONFIG_LOADED.
     */
    public static const int DB_CONFIG_LOADED = 1;
    
    /**
     * The Constant DB_INITIALIZED.
     */
    public static const int DB_INITIALIZED = 2;
    
    /**
     * The Constant DB_STARTED.
     */
    public static const int DB_STARTED = 3;


    /**
     * The Constant DB_STARTED.
     */
    public static const int DB_CLOSED = 3;


    private boolean debug = true;
//x    private boolean db_debug = false;
    
    private ILog log = LogManager.GetLogger(typeof(HibernateDb));
    
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
    
    private ATDataAccessAbstract m_da; 

    private int m_loadStatus = 0;


    /**
     * The config.
     */
    HibernateConfiguration config = null;

    




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
     * Gets the configuration.
     * 
     * @return the configuration
     */
    /*public Configuration getConfiguration()
    {
        return this.hibernateConfig.GetConfiguration();
    }*/

    
    /**
     * Gets the hibernate configuration.
     * 
     * @return the hibernate configuration
     */
    public HibernateConfiguration getHibernateConfiguration()
    {
        return this.HibernateConfiguration;
    }
    
    


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
    public void CloseDb()
    {
        this.HibernateConfiguration.CloseDb();
        m_loadStatus = DB_CLOSED;
    }


    /**
     * Open hibernate simple.
     */
    public void openHibernateSimple()
    {
        this.HibernateConfiguration = CreateHibernateConfiguration();
        this.HibernateConfiguration.LoadConfiguration(-1);
        m_loadStatus = DB_INITIALIZED;
    }


    public abstract HibernateConfiguration CreateHibernateConfiguration();
    



    /**
     * Gets the load status.
     * 
     * @return the load status
     */
    public int LoadStatus
    {
        get
        {
            return m_loadStatus;
        }
    }


    

    /**
     * Display error.
     * 
     * @param source the source
     * @param ex the ex
     */
    public void displayError(String source, Exception ex)
    {

        Console.WriteLine("Exception ["+ source + "]: " + ex);
        log.Error("Exception [" + source + "]: " + ex, ex);

        if (debug)
        {
            Console.WriteLine("Exception ["+ source +"]: " + ex.Message);
            Console.WriteLine(ex.StackTrace);
        }

    }


    /**
     * Gets the session.
     * 
     * @return the session
     */
    public ISession GetSession()
    {
        m_session.clear();
        return m_session;
    }


    /**
     * Creates the database.
     */
    public void CreateDatabase()
    {
        //new SchemaExport(this.getConfiguration()).create(true, true);
    }

    
    /**
     * Gets the application db name.
     * 
     * @return the application db name
     */
    public abstract String GetApplicationDbName();
    
    

    // *************************************************************
    // ****              DB HANDLING METHODS                    ****
    // *************************************************************

    //---
    //---  BASIC METHODS (Hibernate and DataLayer processing)
    //---










    
    

    

    

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
    public String changeCase(String inp)
    {

        StringTokenizer stok = new StringTokenizer(inp, " ");

        bool first = true;
        String outp = "";

        while (stok.hasMoreTokens())
        {
            if (!first)
                outp += " ";

            outp += changeCaseWord(stok.nextToken());
            first = false;
        }

        return outp;

    }

    /**
     * Change case word.
     * 
     * @param in the in
     * 
     * @return the string
     */
    public String changeCaseWord(String inp)
    {

        String t = "";

        t = inp.Substring(0,1).ToUpper();
        t += inp.Substring(1).ToLower();

        return t;

    }


    /**
     * Show byte.
     * 
     * @param in the in
     */
    public void showByte(byte[] inp)
    {

        for (int i=0;i<inp.length; i++)
        {
            Console.WriteLine((char)inp[i] + " " + inp[i]);
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
        this.m_errorDesc = ex.Message;

        if (debug)
            Console.WriteLine("  " + source + "::Exception: "+ex);

        if (debug)
        {
            Console.WriteLine(ex.StackTrace);
        }


    }


}

}
