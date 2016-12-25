package com.atech.db.hibernate.tool.data.management.init;

import java.util.Map;

import com.atech.db.hibernate.HibernateUtil;
import com.atech.db.hibernate.tool.app.DbToolApplicationInterface;
import com.atech.db.hibernate.tool.data.defs.DbInitTaskType;
import com.atech.db.hibernate.tool.data.dto.DbInitTaskDto;
import com.atech.db.hibernate.transfer.BackupRestoreWorkGiver;

public abstract class DbToolInitDbRunner extends Thread implements BackupRestoreWorkGiver
{

    protected HibernateUtil hibernateUtil;
    protected Map<String, DbInitTaskDto> dbInitTasks;
    protected DbToolApplicationInterface applicationInterface;

    protected Boolean tablesDeleted;
    protected Boolean tablesCreated;
    protected Boolean dbInfoFilled;
    private Exception error;


    public DbToolInitDbRunner(Map<String, DbInitTaskDto> dbInitTasks, HibernateUtil hibernateUtil,
            BackupRestoreWorkGiver giver, DbToolApplicationInterface applicationInterface)
    {
        this.dbInitTasks = dbInitTasks;
        this.hibernateUtil = hibernateUtil;
        this.applicationInterface = applicationInterface;
    }


    public void executeDbInit()
    {
        this.executeCoreDbInit();
        this.executeCustomDbInit();
    }


    private void executeCoreDbInit()
    {
        // create (delete) database
        if (dbInitTasks.containsKey(DbInitTaskType.DeleteDatabase.name()))
        {
            CoreDbInitTasks dbInitTask = new CoreDbInitTasks(DbInitTaskType.DeleteDatabase, this);
            dbInitTask.run();

            if (!tablesDeleted)
            {
                this.error = dbInitTask.getError();
                return;
            }
        }

        CoreDbInitTasks dbInitTask = new CoreDbInitTasks(DbInitTaskType.CreateDatabase, this);
        dbInitTask.run();

        if (!tablesCreated)
        {
            this.error = dbInitTask.getError();
            return;
        }

        // dbinfo
        dbInitTask = new CoreDbInitTasks(DbInitTaskType.FillDbInfo, this);
        dbInitTask.run();

        if (!dbInfoFilled)
        {
            this.error = dbInitTask.getError();
        }
    }


    public HibernateUtil getHibernateUtil()
    {
        return this.hibernateUtil;
    }


    public abstract void executeCustomDbInit();


    public void setStatus(int procents)
    {
        System.out.println("Set status: " + procents);
    }


    public void setTask(String task_name)
    {
        System.out.println("Set task: " + task_name);
    }


    public void run()
    {
        this.executeDbInit();
    }

}
