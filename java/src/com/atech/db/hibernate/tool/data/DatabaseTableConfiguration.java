package com.atech.db.hibernate.tool.data;

import com.atech.db.hibernate.HibernateObject;

/**
 * Created by andy on 15/12/16.
 */
public interface DatabaseTableConfiguration
{

    // String getSqlForExport();

    Class<? extends HibernateObject> getObjectClass();


    String getObjectName();


    String getObjectFullName();


    int getTableVersion();


    String getColumns();


    /**
     * Get Table Name
     * 
     * @return
     */
    String getTableName();


    /**
     * Get Backup Target Name
     *
     * @return
     */
    String getBackupTargetName();


    /**
     * Get Database Import Strategy (can be either CleanDbFirst or Append)
     *
     * @return
     */
    DatabaseImportStrategy getDatabaseImportStrategy();

}
