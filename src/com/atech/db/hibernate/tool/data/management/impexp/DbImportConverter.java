package com.atech.db.hibernate.tool.data.management.impexp;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.db.hibernate.HibernateObject;
import com.atech.db.hibernate.tool.data.DatabaseTableConfiguration;
import com.atech.db.hibernate.tool.data.dto.DbTableExportInfoDto;

/**
 * Created by andy on 15/12/16.
 */
public interface DbImportConverter
{

    HibernateObject convert(DatabaseTableConfiguration databaseTableConfiguration, String dataLine,
            DbTableExportInfoDto exportInfoDto);


    HibernateObject convert(DataDefinitionEntry dataDefinitionEntry, String dataLine,
            DbTableExportInfoDto exportInfoDto);

}
