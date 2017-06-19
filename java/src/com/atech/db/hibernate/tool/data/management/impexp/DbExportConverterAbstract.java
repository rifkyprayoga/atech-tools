package com.atech.db.hibernate.tool.data.management.impexp;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.db.hibernate.HibernateObject;

/**
 * Created by andy on 19/12/16.
 */
public abstract class DbExportConverterAbstract implements DbExportConverter
{

    public String convert(DataDefinitionEntry dataDefinitionEntry, HibernateObject hibernateObject)
    {
        return convert(dataDefinitionEntry.getDatabaseTableConfiguration(), hibernateObject);
    }

}
