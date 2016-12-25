package com.atech.db.hibernate.tool.data.management;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.HibernateUtil;
import com.atech.db.hibernate.check.DbInfoH;
import com.atech.db.hibernate.tool.app.DbInitType;
import com.atech.db.hibernate.tool.app.DbToolApplicationInitDb;
import com.atech.db.hibernate.tool.app.DbToolApplicationInterface;
import com.atech.db.hibernate.tool.data.DatabaseConfiguration;
import com.atech.db.hibernate.tool.data.management.init.DbToolInitDbRunner;

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

public class DbToolDbHandler
{

    private static final Logger LOG = LoggerFactory.getLogger(DbToolDbHandler.class);

    protected HibernateConfiguration hibernateConfiguration;
    DbToolApplicationInitDb applicationInitDb;

    int errorCode;
    String errorDesc;
    Exception errorException;


    /**
     * Instantiates a new db tool init db.
     */
    public DbToolDbHandler(DbToolApplicationInterface dbToolApplicationInterface)
    {
        this.hibernateConfiguration = dbToolApplicationInterface.getHibernateConfiguration();
        this.applicationInitDb = dbToolApplicationInterface.getInitDbInstance();
    }


    /**
     * Db init.
     * 
     * @return true, if successful (success means operation finished without errors)
     */
    public boolean databaseInit(DatabaseConfiguration databaseConfiguration, DbInitPreStatus preStatus)
    {
        Configuration configuration = this.createConfiguration(databaseConfiguration);

        HibernateUtil hibernateUtil = new HibernateUtil(configuration, databaseConfiguration);
        hibernateUtil.setSession();

        DbInitType initType = this.applicationInitDb.getDbInitType();

        System.out.println("dbInit: " + initType);

        // DbInitPreStatus preStatus =
        // DbInitPreStatus.ExistingDatabaseWithWrongApplication;

        DbToolInitDbRunner runner = this.applicationInitDb.getInitDbRunner(preStatus, hibernateUtil);
        runner.run();

        if (initType == DbInitType.NoInit)
        {
            return true;
        }
        else
        {

            // 1. check if database exists (DbInfo check) get Versison
            // yes - ask what to do:
            // version == same
            // no changes - exit
            // recreate database - drop database, create database
            // version = different
            // recreate database - drop database, create database
            // no - create database

            Integer dbVersion = null;

            // if (initType == INIT_TYPE_INIT_DB)
            // return createTables(databaseConfiguration);
            // else if (initType == INIT_TYPE_INIT_DB_AND_BASE_IMPORT) {
            // boolean res = createTables(databaseConfiguration);
            // if (!res)
            // return false;
            //
            // return this.fillData();
            // }
        }

        return false;
    }


    public DbInitPreStatus getDbStatus(DatabaseConfiguration databaseConfiguration,
            DbToolApplicationInterface dbToolApplication)
    {
        try
        {
            Configuration dbInfoConfiguration = hibernateConfiguration.getDbInfoConfiguration(databaseConfiguration);

            SessionFactory sessionFactory = dbInfoConfiguration.buildSessionFactory();

            Session session = sessionFactory.openSession();

            Criteria criteria = session.createCriteria(DbInfoH.class)
                    .add(Restrictions.or(Restrictions.eq("key", "DB_INFO"), Restrictions.eq("key", "DB_APP")));

            List list = criteria.list();

            if (list.size() == 0)
            {
                LOG.warn("DbInfo object does not exists.");
                return DbInitPreStatus.EmptyDatabase;
            }
            else
            {
                String dbName = null;
                String dbVersion = null;

                for (Object dbObject : list)
                {
                    DbInfoH infoH = (DbInfoH) dbObject;

                    if ("DB_INFO".equals(infoH.getKey()))
                    {
                        dbVersion = infoH.getValue();
                    }
                    else if ("DB_APP".equals(infoH.getKey()))
                    {
                        dbName = infoH.getValue();
                    }

                }

                if ((dbName != null) && (!dbName.equals(dbToolApplication.getApplicationName())))
                {
                    return DbInitPreStatus.ExistingDatabaseWithWrongApplication;
                }

                if (dbToolApplication.getCurrentDatabaseVersion().equals(dbVersion))
                {
                    return DbInitPreStatus.ExistingDatabaseWithCorrectVersionNumber;
                }
                else
                {
                    return DbInitPreStatus.ExistingDatabaseWithWrongVersionNumber;

                }
            }

        }
        catch (Exception ex)
        {
            LOG.error("Error retrieving Db Info object. Ex.: " + ex, ex);
            this.errorException = ex;
            this.errorDesc = ex.getMessage();
            return DbInitPreStatus.EmptyDatabase;
        }
    }


    /**
     * Gets the error code.
     * 
     * @return the error code
     */
    public int getErrorCode()
    {
        return this.errorCode;

    }


    /**
     * Gets the error description.
     * 
     * @return the error description
     */
    public String getErrorDescription()
    {
        return this.errorDesc;
    }


    /**
     * Gets the error exception.
     * 
     * @return the error exception
     */
    public Exception getErrorException()
    {
        return this.errorException;
    }


    /**
     * Creates the tables.
     * 
     * @return true, if successful
     */
    public boolean createTables(Configuration configurationExternal)
    {
        try
        {
            new SchemaExport(configurationExternal).create(true, true);
        }
        catch (Exception ex)
        {
            LOG.error("createTables exception: " + ex, ex);
            return false;
        }

        return true;
    }


    /**
     * Drop Tables
     * 
     * @return
     */
    public boolean dropTables(Configuration configurationExternal)
    {
        try
        {
            new SchemaExport(configurationExternal).drop(true, true);
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }


    /**
     * Fill data.
     * 
     * @return true, if successful
     */
    public boolean fillData(Configuration configuration)
    {
        return false;
    }


    public Configuration createConfiguration(DatabaseConfiguration databaseConfiguration)
    {

        return hibernateConfiguration.getConfiguration(databaseConfiguration);
    }

    public enum DbInitPreStatus
    {
        EmptyDatabase, //
        ExistingDatabaseWithCorrectVersionNumber, //
        ExistingDatabaseWithWrongApplication, //
        ExistingDatabaseWithWrongVersionNumber //
    }

}
