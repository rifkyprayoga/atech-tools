package com.atech.db.hibernate.tool.data;

import java.util.List;

public interface DatabaseVersionConfiguration
{

    List<DatabaseTableConfiguration> getTablesForDatabase(String key);


    String getVersion();

}
