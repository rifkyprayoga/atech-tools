
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
using System.Text;
using System.IO;
using ATechTools.Util;
namespace ATechTools.Db.NHibernate.Check
{


public class DbCheck
{

    // 1. load update configuration
    //     a) get data (db)
    // 2. load db configuration 
    // 3. make check
    // 4. display if error found
    
    //ATDataAccessAbstract da = null;
    
//    UpdateConfiguration upd_conf;
    
    private ILog log = LogManager.GetLogger(typeof(DbCheck));

    private String db_config_instance_class = "";
    
    private String version_db = "0";
    private String version_db_required = "0";
    
    private String report_file = "db_info.txt";
    
    private bool get_successful = false;
    private HibernateConfiguration hc = null; 
    
    /**
     * Constructor
     */
    public DbCheck()
    {
        /*
        if (!StartupUtil.shouldDbCheckBeDone())
            return;
        
        this.upd_conf = new BuildStartupFile().getConfiguration();
        da = ATDataAccess.getInstance();
        //loadApplicationData();
        
        readUpdateSystemData();
        readCurrentDbVersion();
        writeReport();
        
        StartupUtil.writeStartupWithOldCopy(1); */
    }
    
    /**
     * Constructor
     * 
     * @param req_version
     * @param db_class
     */
    public DbCheck(String req_version, String db_class)
    {
        /*
        if (!StartupUtil.shouldDbCheckBeDone())
            return;

        da = ATDataAccess.getInstance();
        this.version_db_required = req_version;
        this.db_config_instance_class = db_class; 

        readCurrentDbVersion();
        writeReport();

        StartupUtil.writeStartupWithOldCopy(1);
         */
    }
    
    
    /**
     * Read Update System Data
     */
    public void readUpdateSystemData()
    {
        /*
        this.version_db_required = "" + this.upd_conf.db_version_required;
        this.db_config_instance_class = this.upd_conf.db_config_class;
         */
    }
    
    /**
     * Read Current Db Version
     */
    public void readCurrentDbVersion()
    {
        /*
        try
        {
            
            Console.WriteLine("*******************************************************");
            Console.WriteLine("*****             Db Check  v0.1                 ******");
            Console.WriteLine("*******************************************************");
            
            //Console.WriteLine("Running class creation: ");
        
            Class<?> c = Class.forName(this.db_config_instance_class);

           
            //Console.WriteLine(this.db_config_instance_class);
            //Console.WriteLine("Found class[1]: " + c);
            
            Constructor<?>[] constructors = c.getConstructors();
            //Console.WriteLine(Arrays.asList(constructors));
            
            //hc = (HibernateConfiguration)c.newInstance();
            hc = (HibernateConfiguration)constructors[0].newInstance(true);
            
            //Configuration cfg = hc.getDbInfoConfiguration();
            //HibernateUtil hu = new HibernateUtil(cfg);
            
            HibernateUtil hu = new HibernateUtil(hc, HibernateConfiguration.DB_CONTEXT_DBINFO);
            hu.setSession(1);
            
            Query q = hu.getSession().createQuery("select inf from com.atech.db.hibernate.check.DbInfoH as inf where inf.key='DB_INFO' ");

            Iterator<?> it = q.iterate();

            if (it.hasNext())
            {
                DbInfoH di = (DbInfoH)it.next();
                this.version_db = di.getValue();
                this.get_successful = true;
            }
            else
            {
                this.get_successful = true;
            }
            
        }
        catch(ClassNotFoundException ex)
        {
            log.error("Class Not Found Exception: " + ex, ex);
//            ex.printStackTrace();
        }
        catch(IllegalAccessException ex)
        {
            log.error("Illegal Access Exception: " + ex, ex);
//            ex.printStackTrace();
        }
        catch(InstantiationException ex)
        {
            log.error("Illegal Access Exception: " + ex, ex);
//            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            log.error("Exception happened: " + ex, ex);
//            ex.printStackTrace();
        }
         */
    }
    

    /**
     * Write Report
     */
    public void writeReport()
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        
        sb.Append("DbInfo for ");
        sb.Append(this.hc.DbName);
        sb.Append(": ");
        
        if (this.get_successful)
        {
            sb.Append("OK");
            sb1.Append("OK");
        }
        else
        {
            sb.Append("Failed");
            sb1.Append("Failed");
        }
        
        sb.Append("\n");
        
        sb.Append("Current Db Version: " + this.version_db + "\n");
        sb.Append("Required Db Version: " + this.version_db_required + "\n");
        
        sb1.Append("|" + this.version_db + "|" + this.version_db_required + "\n");
        
        
        sb.Append(sb1.ToString());
        
        
        
        //Console.WriteLine(sb.toString());
        
        try
        {

            StreamWriter s = File.CreateText(report_file);

            s.Write(sb.ToString());
            s.Close();

        }
        catch(Exception ex)
        {
            log.Error("Error writing report to file: " + ex, ex);
        }
        
    }
    


    /**
     * Startup method
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        /*
        if (args.length==0)
        {
            new DbCheck();
        }
        else
        {
            new DbCheck(args[0], args[1]);
        }*/
    }

    
    
    
    
}


}




