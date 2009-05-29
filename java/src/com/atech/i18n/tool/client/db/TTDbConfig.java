package com.atech.i18n.tool.client.db;

import com.atech.db.hibernate.HibernateConfiguration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TTDbConfig extends HibernateConfiguration
{

    private static Log log = LogFactory.getLog(TTDbConfig.class);

    private String[] db_files = { 
                   "GGC_Main.hbm.xml", 
                   "GGC_Nutrition.hbm.xml", 
                   "GGC_Other.hbm.xml",
                   "GGC_Pump.hbm.xml", 
                   "GGC_CGM.hbm.xml" 
    };

    /**
     * Constructor
     * 
     * @param val
     */
    public TTDbConfig(boolean val)
    {
        super(val);
    }

    /**
     * Get Db Name
     * 
     * @return
     */
    public String getDbName()
    {
        return "TTDb";
    }

    /**
     * Get Configuration File
     */
    @Override
    public String getConfigurationFile()
    {
        return "../data/GGC_Config.properties";
    }

    /**
     * Get Resource Files
     */
    @Override
    public String[] getResourceFiles()
    {
        return db_files;
    }

    /**
     * Load Default Database
     * 
     * @param config_found
     */
    @Override
    public void loadDefaultDatabase(boolean config_found)
    {
        //System.out.println("load");
        db_num = 0;
        db_conn_name = "Internal Db (H2)";

        if (!config_found)
            log.info("GGCDb: Database configuration not found. Using default database.");
        log.info("GGCDb: Loading Db Configuration #" + db_num + ": " + db_conn_name);

        db_hib_dialect = "org.hibernate.dialect.H2Dialect";
        db_driver_class = "org.h2.Driver";
        db_conn_url = "jdbc:h2:../data/db/ggc_db";
        db_conn_username = "sa";
        db_conn_password = "";
    }

    /**
     * Is Check Enabled
     * 
     * @see com.atech.db.hibernate.check.DbCheckInterface#isCheckEnabled()
     */
    @Override
    public boolean isCheckEnabled()
    {
        return false;
    }

    /**
     * Get DbInfo Configuration
     * 
     * @see com.atech.db.hibernate.check.DbCheckInterface#getDbInfoConfiguration()
     */
    public String getDbInfoReportFilename()
    {
        return "../data/db_info.txt";
    }

    /**
     * Get Number Of Sessions
     * 
     * @see com.atech.db.hibernate.HibernateConfiguration#getNumberOfSessions()
     */
    @Override
    public int getNumberOfSessions()
    {
        return 2;
    }

}
