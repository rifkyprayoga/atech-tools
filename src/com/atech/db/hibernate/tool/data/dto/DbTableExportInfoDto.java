package com.atech.db.hibernate.tool.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.atech.db.hibernate.tool.data.DatabaseImportStrategy;

/**
 * Created by andy on 27/02/17.
 */
public class DbTableExportInfoDto
{

    List<String> headers = new ArrayList<String>();
    private DatabaseImportStrategy databaseImportStrategy;


    public void addHeaderLine(String line)
    {

        headers.add(line);

        if (line.contains(""))
        {

        }

    }


    public void setDatabaseImportStrategy(DatabaseImportStrategy databaseImportStrategy)
    {
        this.databaseImportStrategy = databaseImportStrategy;
    }


    public DatabaseImportStrategy getDatabaseImportStrategy()
    {
        return databaseImportStrategy;
    }
}
