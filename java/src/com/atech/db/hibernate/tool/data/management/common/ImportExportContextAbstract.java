package com.atech.db.hibernate.tool.data.management.common;

import com.atech.db.hibernate.tool.data.DatabaseVersionConfiguration;
import com.atech.db.hibernate.tool.data.management.export.DbExportConverter;

/**
 * Created by andy on 20/12/16.
 */
public class ImportExportContextAbstract implements ImportExportContext
{

    String exportLocation;
    DbExportConverter dbExportConverter;
    DatabaseVersionConfiguration databaseVersionConfiguration;


    public ImportExportContextAbstract(String exportLocation, DbExportConverter dbExportConverter,
            DatabaseVersionConfiguration databaseVersionConfiguration)
    {
        this.exportLocation = exportLocation;
        this.dbExportConverter = dbExportConverter;
        this.databaseVersionConfiguration = databaseVersionConfiguration;
    }


    public String getExportLocation()
    {
        return this.exportLocation;
    }


    public DbExportConverter getDbExportConverter()
    {
        return this.dbExportConverter;
    }


    public DatabaseVersionConfiguration getDatabaseVersionConfiguration()
    {
        return this.databaseVersionConfiguration;
    }
}
