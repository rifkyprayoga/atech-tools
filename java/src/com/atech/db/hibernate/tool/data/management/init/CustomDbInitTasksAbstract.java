package com.atech.db.hibernate.tool.data.management.init;

import com.atech.db.hibernate.tool.data.dto.DbInitTaskDto;
import com.atech.db.hibernate.tool.data.management.common.ImportExportContext;

public abstract class CustomDbInitTasksAbstract extends InitDbTool implements Runnable
{

    protected DbInitTaskDto taskDto;


    public CustomDbInitTasksAbstract(DbInitTaskDto taskDto, DbToolInitDbRunner runner,
            ImportExportContext importExportContext)
    {
        super(runner, importExportContext);
        System.out.println("CustomDbInitTasksAbstract:cnstr() ");
        this.taskDto = taskDto;
    }


    @Override
    public int getActiveSession()
    {
        return 1;
    }
}
