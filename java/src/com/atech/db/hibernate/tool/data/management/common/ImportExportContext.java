package com.atech.db.hibernate.tool.data.management.common;

import com.atech.db.hibernate.tool.data.DatabaseVersionConfiguration;
import com.atech.db.hibernate.tool.data.management.impexp.DbExportConverter;
import com.atech.db.hibernate.tool.data.management.impexp.DbImportConverter;

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
     * getDbImportConverter - converter for imports (converter needs to handle or versioning funcationality and
     * also import Strategy)
     *
     * @return DbImportConverter instance
     */
    DbImportConverter getDbImportConverter();


    /**
     * getDatabaseVersionConfiguration - return DatabaseVersionConfiguration for Application
     *
     * @return DatabaseVersionConfiguration instance
     */
    DatabaseVersionConfiguration getDatabaseVersionConfiguration();

}
