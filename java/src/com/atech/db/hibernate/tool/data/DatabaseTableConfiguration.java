package com.atech.db.hibernate.tool.data;

/**
 * Created by andy on 15/12/16.
 */
public interface DatabaseTableConfiguration
{

    // String getSqlForExport();

    Class getObjectClass();


    String getObjectName();


    String getObjectFullName();


    // int getDbVersion();

    int getTableVersion();


    String getColumns();


    String getTableName();
}
