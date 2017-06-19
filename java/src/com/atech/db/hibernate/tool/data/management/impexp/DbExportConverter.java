package com.atech.db.hibernate.tool.data.management.impexp;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.db.hibernate.HibernateObject;
import com.atech.db.hibernate.tool.data.DatabaseTableConfiguration;

/**
 * Created by andy on 19/12/16.
 */
public interface DbExportConverter
{

    String convert(DatabaseTableConfiguration databaseTableConfiguration, HibernateObject hibernateObject);


    String convert(DataDefinitionEntry dataDefinitionEntry, HibernateObject hibernateObject);

}
