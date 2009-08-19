
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

using System;
using log4net;
using ATechTools.I18n;
using NHibernate;
using NHibernate.Cfg;
using ATechTools.Util;
using System.Data;
using System.Collections.Generic;
using System.IO;
using ATechTools.Db.NHibernate.Check;
using System.Reflection;
using System.Xml;
using System.Text;


namespace ATechTools.Db.NHibernate
{
public abstract class HibernateConfiguration : DbCheckAbstract
{
    
    /**
     * The ic.
     */
    I18nControlAbstract ic = null;
    private ILog log = LogManager.GetLogger(typeof(HibernateConfiguration));

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
    private String db_hib_dialect = null;

    public String DbHibernateDialect
    {
        get { return db_hib_dialect; }
        set { db_hib_dialect = value; }
    } 
    
    /**
     * The db_driver_class.
     */
    private String db_driver_class = null;

    public String DbDriverClass
    {
        get { return db_driver_class; }
        set { db_driver_class = value; }
    }
    
    /**
     * The db_conn_name.
     */
    private String db_conn_name = null;

    public String DbConnectionName
    {
        get { return db_conn_name; }
        set { db_conn_name = value; }
    }
    
    /**
     * The db_conn_url.
     */
    private String db_conn_url = null;

    public String DbConnectionUrl
    {
        get { return db_conn_url; }
        set { db_conn_url = value; }
    }
    
    /**
     * The db_conn_username.
     */
    private String db_conn_username = null;

    public String DbConnectionUsername
    {
        get { return db_conn_username; }
        set { db_conn_username = value; }
    }
    
    /**
     * The db_conn_password.
     */
    private String db_conn_password = null;

    public String DbConnectionPassword
    {
        get { return db_conn_password; }
        set { db_conn_password = value; }
    }
    
    /**
     * The data_loaded.
     */
    public bool data_loaded = false;

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
    private ISessionFactory session_factory = null;

    public ISessionFactory SessionFactory
    {
        get { return session_factory; }
        set { session_factory = value; }
    }
    
    /**
     * The sessions.
     */
    protected Dictionary<String, ISession> sessions = null;

    /**
     * The Constant DB_CONTEXT_NONE.
     */
    public const int DB_CONTEXT_NONE = 0;
    
    /**
     * The Constant DB_CONTEXT_FULL.
     */
    public const int DB_CONTEXT_FULL = 1;
    
    /**
     * The Constant DB_CONTEXT_DBINFO.
     */
    public const int DB_CONTEXT_DBINFO = 2;
    
    /**
     * The db_context_selected.
     */
    protected int db_context_selected = HibernateConfiguration.DB_CONTEXT_NONE;
    
    

    /**
     * Instantiates a new hibernate configuration.
     */
    public HibernateConfiguration()
        : this(-1)
    {
        
    }

    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param val the val
     */
    public HibernateConfiguration(bool val) : this(-1)
    {
    }
    
    
    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param sel_db the sel_db
     */
    public HibernateConfiguration(int sel_db) : base()
    {
        LoadConfiguration(sel_db);
    }

    
    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param ic the ic
     */
    public HibernateConfiguration(I18nControlAbstract ic)
        : this(ic, null)
    {
        
    }
    
    
    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param ic the ic
     * @param da the da
     */
    public HibernateConfiguration(I18nControlAbstract ic, ATDataAccessAbstract da) : this(ic, -1, da)
    {
    }


    /**
     * Instantiates a new hibernate configuration.
     * 
     * @param ic the ic
     * @param sel_db the sel_db
     * @param da the da
     */
    public HibernateConfiguration(I18nControlAbstract ic, int sel_db, ATDataAccessAbstract da) : base()
    {
    	this.ic = ic;
        this.m_da = da; 
    	InitI18n();
    	LoadConfiguration();
    }


    /**
     * Inits the i18n.
     */
    public void InitI18n()
    {
    	this.loading_db_str = SetI18nVariable("LOADING_DB_CONFIGURATION", this.loading_db_str);
    	this.cfg_file_missing_str = SetI18nVariable("CFG_FILE_MISSING", this.cfg_file_missing_str);
    	this.cfg_file_error_read_exc = SetI18nVariable("CFG_FILE_ERROR_READ", this.cfg_file_error_read_exc);
    }


    /**
     * Sets the i18n variable.
     * 
     * @param key the key
     * @param original_value the original_value
     * 
     * @return the string
     */
    public String SetI18nVariable(String key, String original_value)
    {
    	if (!ic.GetMessage(key).Equals(key)) 
    	    return ic.GetMessage(key);
    	else
    	    return original_value;
    }

    
    /**
     * Gets the connection name.
     * 
     * @return the connection name
     */
    public String ConnectionName
    {
        get
        {
            return this.db_conn_name;
        }
    }
    
    
    /**
     * Gets the hibernate dialect.
     * 
     * @return the hibernate dialect
     */
    public String HibernateDialect
    {
        get
        {
            return this.db_hib_dialect;
        }
    }


    /**
     *  In this method we need to set configuration_file variable, with path and filename of 
     *  configuration.
     *  
     * @param sel_db 
     */
    public void LoadConfiguration(int sel_db)
    {
        
        try
        {

            PropertiesFile props = new PropertiesFile();
            bool config_found = true;
            
            if (!DoesConfigurationExist())
            {
                log.Error(this.cfg_file_missing_str + "(" + this.ConfigurationFile + ").");
                config_found = false;
                LoadDefaultDatabase(config_found);
                return;
            }

            props.LoadFile(this.ConfigurationFile);


    	    if (sel_db==-1)
    	    {
        		String num_db = props.Get("SELECTED_DB");
        		db_num = Convert.ToInt32(num_db);
    	    }
    	    else
    	        db_num = sel_db;

    	    //Console.WriteLine("Loading Db #" + db_num);
    	    log.Info(loading_db_str + db_num);

            if (db_num == 0)
            {
                log.Info("Load Default Database");
                LoadDefaultDatabase(config_found);
            }
            else
            {
                log.Info("Load Database #" + db_num + " from Config file");
                db_conn_name = props.Get("DB" + db_num + "_CONN_NAME");
                db_hib_dialect = props.Get("DB"+db_num+"_HIBERNATE_DIALECT");

                db_driver_class = props.Get("DB"+db_num+"_CONN_DRIVER_CLASS");
                db_conn_url = props.Get("DB"+db_num+"_CONN_URL");
                db_conn_username = props.Get("DB"+db_num+"_CONN_USERNAME");
                db_conn_password = props.Get("DB"+db_num+"_CONN_PASSWORD");

                data_loaded = true;
            }

            m_cfg = GetConfiguration();
            CreateSessionFactory();

        }
        catch (Exception ex)
        {
            Console.WriteLine(cfg_file_error_read_exc + " " + ex);
            Console.WriteLine(ex.StackTrace);
        }


    }


    /**
     * Load configuration.
     */
    public void LoadConfiguration()
    {
        LoadConfiguration(-1);
    }



    /**
     * Does configuration exist.
     * 
     * @return true, if successful
     */
    public bool DoesConfigurationExist()
    {
        //Console.WriteLine(this.getConfigurationFile());
        
        FileInfo f = new FileInfo(this.ConfigurationFile);
        return (f.Exists);
    }




    /**
     * Gets the configuration.
     * 
     * @return the configuration
     */
    public Configuration GetConfiguration()
    {
        Configuration cfg = this.GetCustomConfiguration(this.GetResourceFiles());
        this.db_context_selected = HibernateConfiguration.DB_CONTEXT_FULL;
        return cfg;
    }

    /** 
     * getDbInfoConfiguration
     */
    /*public Configuration GetDbInfoConfiguration()
    {
        Configuration cfg = this.GetCustomConfiguration(this.GetDbInfoResource());
        this.db_context_selected = HibernateConfiguration.DB_CONTEXT_DBINFO;
        return cfg;
    }*/
    
    

    private Configuration GetCustomConfiguration(String[] res_files)
    {
        try
        {
            //Configuration cfg = new Configuration();

            Dictionary<string, string> props = new Dictionary<string, string>();

            /*
            props.Add("hibernate.dialect", db_hib_dialect);
            props.Add("hibernate.connection.driver_class", db_driver_class);
            props.Add("hibernate.connection.url", db_conn_url);
            props.Add("hibernate.connection.provider", "NHibernate.Connection.DriverConnectionProvider");

            if ((db_conn_username != null) && (db_conn_username.Length > 0))
                props.Add("hibernate.connection.username", db_conn_username);

            if ((db_conn_password != null) && (db_conn_password.Length > 0))
                props.Add("hibernate.connection.password", db_conn_password);

            props.Add("hibernate.connection.charSet", "utf-8");
            props.Add("hibernate.use_outer_join", "true");
            */

            props.Add("session.name", "Test");
            props.Add("session_name", "Test");
            props.Add("session_factory.name", "Test");
            props.Add("dialect", db_hib_dialect);  //  hibernate.dialect
            props.Add("connection.driver_class", db_driver_class);
            props.Add("connection.connection_string", db_conn_url);

            props.Add("connection.provider", "NHibernate.Connection.DriverConnectionProvider");

            if ((db_conn_username != null) && (db_conn_username.Length > 0))
                props.Add("connection.username", db_conn_username);

            if ((db_conn_password != null) && (db_conn_password.Length > 0))
                props.Add("connection.password", db_conn_password);

            props.Add("connection.charSet", "utf-8");
            //props.Add("use_outer_join", "true");

            props.Add("proxyfactory.factory_class", "NHibernate.ByteCode.LinFu.ProxyFactoryFactory, NHibernate.ByteCode.LinFu");

            //proxyfactory.factory_class
            //log.Debug("Dialect: " + db_hib_dialect);

            //cfg.Properties = props;

            //cfg.AddProperties(props);
            //cfg.Configure(
            


            //XmlWriter x = new XmlTextWriter();
/*
            StringBuilder sb = new StringBuilder();

            sb.Append("<nhibernate>");

            foreach(KeyValuePair<string,string> kvp in props)
            {
                sb.Append("<add key=\"");
                sb.Append(kvp.Key);
                sb.Append("\" value=\"");
                sb.Append(kvp.Value);
                sb.Append("\" />");

                sb.AppendLine();
            }
            sb.Append("</nhibernate>");
*/

            //new StringReader(sb.ToString());

            //cfg.SetProperties(props);

            //cfg.Configure(new XmlTextReader(new StringReader(sb.ToString())));

            //Configuration cfg = new Configuration(



            /*
    <add
      key="hibernate.dialect"
      value="NHibernate.Dialect.MsSql2000Dialect"
    />
    <add
      key="hibernate.connection.driver_class"
      value="NHibernate.Driver.SqlClientDriver"
    />
    <add
      key="hibernate.connection.connection_string"
      value="Server=localhost;initial catalog=nhibernate;Integrated Security=SSPI"
    />*/

//            Console.WriteLine("Properties: " + props.ToString());


//            Console.WriteLine(this.GetResourceAssembly(res_files[0]));

            Configuration cfg = new Configuration()
                .SetProperties(props);

            for (int i = 0; i < res_files.Length; i++)
            {
                cfg.AddResource(res_files[i], this.GetResourceAssembly(res_files[i]));
            }
            
                //.AddResource(res_files[0], this.GetResourceAssembly(res_files[0]));


            //Configuration 
            /*
            for (int i = 0; i < res_files.Length; i++)
            {
                .AddResource(res_files[i], this.GetResourceAssembly(res_files[i]));
                //cfg.AddResource(res_files[i], this.GetResourceAssembly(res_files[i]));
            }*/

            //  .setProperty("hibernate.show_sql", "true")
            //  .setProperty("hibernate.c3p0.min_size", "5")
            //  .setProperty("hibernate.c3p0.max_size", "20")
            //  .setProperty("hibernate.c3p0.timeout", "1800")
            //  .setProperty("hibernate.c3p0.max_statements", "50"); */

            //cfg.setProperty("hibernate.cglib.use_reflection_optimizer", "false");

            this.m_cfg = cfg;

            return cfg;
        }
        catch (Exception ex)
        {
            log.Error("Problem creating NHibernate Configuration: " + ex.Message, ex);
            return null;
        }



    }






    private Dictionary<string, string> GetDatabaseSpecificSettings(string dialect_name)
    {
        Dictionary<string, string> sett = new Dictionary<string, string>();

        if (dialect_name == "")
        {

        }

        return sett;

    }


    public abstract Dictionary<string, string> GetUsersDatabaseSpecificSettings(string dialect_name);



    
    /**
     * Load Default Database
     * 
     * @param config_found
     */
    public abstract void LoadDefaultDatabase(bool config_found);

    /**
     * Get Configuration File
     * 
     * @return 
     */
    public abstract String ConfigurationFile { get; }
    
    /**
     * Get Resource Files
     * 
     * @return 
     */
    public abstract String[] GetResourceFiles();
    
    /**
     * Get Number Of Sessions
     * 
     * @return 
     */
    public abstract int NumberOfSessions { get; }
    
    /**
     * Creates the session factory.
     */
    public void CreateSessionFactory()
    {

        this.session_factory = m_cfg.BuildSessionFactory();
        
        
        this.sessions = new Dictionary<String,ISession>();
        
        for(int i=1; i<=this.NumberOfSessions; i++)
        {
            //int iid = this.session_factory.OpenSession();
            this.sessions.Add("" +i, this.session_factory.OpenSession() );
        }
        
    }
    
    /**
     * Gets the session.
     * 
     * @param num the num
     * 
     * @return the session
     */
    public ISession GetSession(int num)
    {
        return GetSession(num, false);
    }
    
    /**
     * Gets the session.
     * 
     * @param num the num
     * @param dont_clear the dont_clear
     * 
     * @return the session
     */
    public ISession GetSession(int num, bool dont_clear)
    {
        if (this.session_factory==null)
            CreateSessionFactory();
        
        ISession s = this.sessions["" + num];
        
        if (!dont_clear)
            s.Clear();

        return s;
        
        //return this.SessionFactory.OpenSession();

        
    }
    
    /**
     * Reset configuration.
     */
    public void ResetConfiguration()
    {
        CloseSessions();
        CreateSessionFactory();
    }
    
    
    /**
     * Close sessions.
     */
    public void CloseSessions()
    {
        if (this.sessions!=null)
        {
            for(int i=1; i<=this.NumberOfSessions; i++)
            {
                ISession s = this.sessions["" +i];
                s.Close();
                s=null;
            }
        }
        
        if (this.session_factory!=null)
        {
            this.session_factory.Close();
            this.session_factory = null;
        }
    }
    
    
    /**
     * Close db.
     */
    public void CloseDb()
    {
        
        if (this.db_hib_dialect == "org.hibernate.dialect.HSQLDialect")
        {
            try
            {
                IDbCommand cmd = GetSession(1).Connection.CreateCommand();
                cmd.CommandText = "SHUTDOWN";
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Console.WriteLine("closeDb:Exception> " + ex);
            }
        }
        
        CloseSessions();
        
    }
    
    
    /**
     * Re init configuration.
     * 
     * @param type the type
     */
    public void ReInitConfiguration(int type)
    {
        if (this.db_context_selected == type)
        {
            if (this.session_factory==null)
                CreateSessionFactory();
        }
        else
        {
            this.CloseSessions();
            
            if (type == HibernateConfiguration.DB_CONTEXT_DBINFO)
            {
                this.GetDbInfoConfiguration();
            }
            else
                this.GetConfiguration();

            CreateSessionFactory();
        }
    }
    
    
    //public abstract string DbName { get; }





}



}