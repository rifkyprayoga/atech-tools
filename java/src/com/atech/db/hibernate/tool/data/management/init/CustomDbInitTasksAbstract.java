package com.atech.db.hibernate.tool.data.management.init;

import com.atech.db.hibernate.tool.data.dto.DbInitTaskDto;

public abstract class CustomDbInitTasksAbstract extends InitDbTool implements Runnable
{

    protected DbInitTaskDto taskDto;


    public CustomDbInitTasksAbstract(DbInitTaskDto taskDto, DbToolInitDbRunner runner)
    {
        super(runner);
        System.out.println("CustomDbInitTasksAbstract:cnstr() ");
        this.taskDto = taskDto;
    }


    @Override
    public int getActiveSession()
    {
        return 1;
    }
}
