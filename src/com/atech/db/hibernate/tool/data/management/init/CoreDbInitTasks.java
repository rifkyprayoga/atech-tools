package com.atech.db.hibernate.tool.data.management.init;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.db.hibernate.check.DbInfoH;
import com.atech.db.hibernate.tool.data.defs.DbInitTaskType;
import com.atech.db.hibernate.tool.data.management.common.ImportExportContext;

public class CoreDbInitTasks extends InitDbTool implements Runnable
{

    private static final Logger LOG = LoggerFactory.getLogger(CoreDbInitTasks.class);

    private DbInitTaskType taskType;
    private Exception error;


    public CoreDbInitTasks(DbInitTaskType taskType, DbToolInitDbRunner runner, ImportExportContext importExportContext)
    {
        super(runner, importExportContext);
        this.taskType = taskType;
    }


    @Override
    public int getActiveSession()
    {
        return 1;
    }


    /**
     * Creates the tables.
     *
     * @return true, if successful
     */
    public void createDatabase()
    {
        try
        {
            new SchemaExport(hibernateUtil.getConfiguration()).create(true, true);
            this.runner.tablesCreated = true;
        }
        catch (Exception ex)
        {
            LOG.error("createDatabase exception: " + ex, ex);
            this.runner.tablesCreated = false;
        }
    }


    /**
     * Drop Tables
     *
     * @return
     */
    public void deleteDatabase()
    {
        try
        {
            new SchemaExport(hibernateUtil.getConfiguration()).drop(true, true);
            this.runner.tablesDeleted = true;
        }
        catch (Exception ex)
        {
            LOG.error("deleteDatabase exception: " + ex, ex);
            this.runner.tablesDeleted = true;
        }
    }


    private void createDbInfo()
    {
        DbInfoH dbi = new DbInfoH();

        dbi.setId(1);
        dbi.setKey("DB_INFO");
        dbi.setValue(this.runner.applicationInterface.getCurrentDatabaseVersion());
        dbi.setType(2);
        dbi.setDescription("Db Version (must be positive integer)");

        // 1-string, 2-int, 3=float

        if (hibernateUtil.addHibernate(dbi) == -1)
        {
            this.runner.dbInfoFilled = false;
            this.error = hibernateUtil.getError();
            return;
        }

        dbi = new DbInfoH();

        dbi.setId(2);
        dbi.setKey("DB_APP");
        dbi.setValue(this.runner.applicationInterface.getApplicationName());
        dbi.setType(1);
        dbi.setDescription("Application Name");

        if (hibernateUtil.addHibernate(dbi) == -1)
        {
            this.runner.dbInfoFilled = false;
            this.error = hibernateUtil.getError();
            return;
        }

        this.runner.dbInfoFilled = true;
    }


    public Exception getError()
    {
        return error;
    }


    public void setError(Exception error)
    {
        this.error = error;
    }


    public void run()
    {
        switch (taskType)
        {
            case DeleteDatabase:
                deleteDatabase();
                break;
            case CreateDatabase:
                createDatabase();
                break;

            default:
            case FillDbInfo:
                createDbInfo();
                break;
        }
    }

}
