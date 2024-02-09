package com.atech.db.hibernate.tool.app;

import java.util.List;

import org.hibernate.cfg.Configuration;

import com.atech.db.hibernate.HibernateUtil;
import com.atech.db.hibernate.tool.data.dto.DbInitTaskDto;
import com.atech.db.hibernate.tool.data.management.DbToolDbHandler;
import com.atech.db.hibernate.tool.data.management.init.DbToolInitDbRunner;

/**
 * Created by andy on 28/11/16.
 */
public class DbToolApplicationInitDbNoFill implements DbToolApplicationInitDb
{

    public DbInitType getDbInitType()
    {
        return DbInitType.Init;
    }


    public boolean fillDatabase(Configuration hibernateConfigurationExternal)
    {
        return true;
    }


    public DbToolInitDbRunner getInitDbRunner(DbToolDbHandler.DbInitPreStatus dbInitPreStatus,
            HibernateUtil hibernateUtil)
    {
        return null;
    }


    public DbToolInitDbRunner getInitDbRunner()
    {
        return null;
    }


    public List<DbInitTaskDto> getCustomTasks()
    {
        return null;
    }


    public DbToolApplicationInterface getDbToolApplication()
    {
        return null;
    }


    public void setStatus(int procents)
    {

    }


    public void setTask(String task_name)
    {

    }
}
