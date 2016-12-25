package com.atech.db.hibernate.tool.data.management.common;

import com.atech.db.hibernate.tool.data.DatabaseVersionConfiguration;
import com.atech.db.hibernate.tool.data.management.export.DbExportConverter;

public interface ImportExportContext
{

    /**
     * getExportLocation - export location without trailing /
     * 
     * @return path as String
     */
    String getExportLocation();


    /**
     * getDbExportConverter - converter for exports
     * 
     * @return DbExportConverter instance
     */
    DbExportConverter getDbExportConverter();


    /**
     * getDatabaseVersionConfiguration - return DatabaseVersionConfiguration for Application
     *
     * @return DatabaseVersionConfiguration instance
     */
    DatabaseVersionConfiguration getDatabaseVersionConfiguration();
}
