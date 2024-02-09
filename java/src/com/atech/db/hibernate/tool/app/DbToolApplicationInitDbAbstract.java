package com.atech.db.hibernate.tool.app;

import java.util.ArrayList;
import java.util.List;

import com.atech.db.hibernate.tool.data.defs.DbInitTaskType;
import com.atech.db.hibernate.tool.data.dto.DbInitTaskDto;

/**
 * Created by andy on 29/11/16.
 */
public abstract class DbToolApplicationInitDbAbstract implements DbToolApplicationInitDb
{

    protected List<DbInitTaskDto> customTasks;

    DbToolApplicationInterface dbToolApplicationInterface;


    public DbToolApplicationInitDbAbstract(DbToolApplicationInterface dbToolApplicationInterface)
    {
        this.dbToolApplicationInterface = dbToolApplicationInterface;

    }


    public DbToolApplicationInitDbAbstract()
    {
        createCustomTasks();
    }


    public void addCustomTask(String taskName, DbInitTaskType initType, int maxCount)
    {
        if (customTasks == null)
        {
            customTasks = new ArrayList<DbInitTaskDto>();
        }

        System.out.println("Task: " + taskName + " maxCount: " + maxCount);

        customTasks.add(new DbInitTaskDto(taskName, initType, maxCount));
    }


    public abstract void createCustomTasks();


    public List<DbInitTaskDto> getCustomTasks()
    {
        return customTasks;
    }


    public DbToolApplicationInterface getDbToolApplication()
    {
        return this.dbToolApplicationInterface;
    }

}
