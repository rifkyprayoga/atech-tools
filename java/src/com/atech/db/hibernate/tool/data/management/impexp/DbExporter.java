package com.atech.db.hibernate.tool.data.management.impexp;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.atech.data.NotImplementedException;
import com.atech.data.mng.DataDefinitionEntry;
import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.HibernateObject;
import com.atech.db.hibernate.tool.data.DatabaseTableConfiguration;
import com.atech.db.hibernate.tool.data.management.common.ImportExportContext;
import com.atech.db.hibernate.tool.data.management.common.ImportExportStatusType;
import com.atech.db.hibernate.transfer.BackupRestoreWorkGiver;

/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     DbExporter
 *  Description:  Db Exporter for DatabaseTableConfiguration / DataDefinitionEntry
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

public class DbExporter extends ExportTool
{

    String databaseVersion = null;


    /**
     * Constructor
     *
     * @param hibernateDb
     * @param backupRestoreWorkGiver BackupRestoreWorkGiver instance (if called from gui)
     * @param importExportContext ImportExportContext of specific app
     */
    public DbExporter(HibernateDb hibernateDb, BackupRestoreWorkGiver backupRestoreWorkGiver,
            ImportExportContext importExportContext)
    {
        this(hibernateDb, backupRestoreWorkGiver, importExportContext, false);
    }


    /**
     * Constructor
     *
     * @param hibernateDb
     * @param backupRestoreWorkGiver BackupRestoreWorkGiver instance (if called from gui)
     * @param importExportContext ImportExportContext of specific app
     * @param exportAll
     */
    public DbExporter(HibernateDb hibernateDb, BackupRestoreWorkGiver backupRestoreWorkGiver,
            ImportExportContext importExportContext, boolean exportAll)
    {
        super(hibernateDb, importExportContext);

        this.databaseVersion = importExportContext.getDatabaseVersionConfiguration().getVersion();
        checkPrerequisites(true);

        this.setStatusReceiver(backupRestoreWorkGiver);
        this.setTypeOfStatus(ImportExportStatusType.Special);

        if (exportAll)
            exportAll();
    }


    /**
     * Constructor
     *
     * @param hibernateConfiguration HibernateConfiguration instance
     * @param importExportContext ImportExportContext of specific app
     */
    public DbExporter(HibernateConfiguration hibernateConfiguration, ImportExportContext importExportContext)
    {
        this(hibernateConfiguration, null, importExportContext, false);
    }


    /**
     * Constructor
     *
     * @param hibernateConfiguration HibernateConfiguration instance
     * @param importExportContext ImportExportContext of specific app
     * @param exportAll true if we are exporting all tables (intended for autobackup)
     */
    public DbExporter(HibernateConfiguration hibernateConfiguration, ImportExportContext importExportContext,
            boolean exportAll)
    {
        this(hibernateConfiguration, null, importExportContext, exportAll);
    }


    /**
     * Constructor
     *
     * @param hibernateConfiguration HibernateConfiguration instance
     * @param backupRestoreWorkGiver BackupRestoreWorkGiver instance (if called from gui)
     * @param importExportContext ImportExportContext of specific app
     */
    public DbExporter(HibernateConfiguration hibernateConfiguration, BackupRestoreWorkGiver backupRestoreWorkGiver,
            ImportExportContext importExportContext)
    {
        this(hibernateConfiguration, backupRestoreWorkGiver, importExportContext, false);
    }


    /**
     * Constructor
     *
     * @param hibernateConfiguration HibernateConfiguration instance
     * @param backupRestoreWorkGiver BackupRestoreWorkGiver instance (if called from gui)
     * @param importExportContext ImportExportContext of specific app
     * @param exportAll true if we are exporting all tables (intended for autobackup)
     */
    public DbExporter(HibernateConfiguration hibernateConfiguration, BackupRestoreWorkGiver backupRestoreWorkGiver,
            ImportExportContext importExportContext, boolean exportAll)
    {
        super(hibernateConfiguration, importExportContext);

        this.databaseVersion = importExportContext.getDatabaseVersionConfiguration().getVersion();

        if (backupRestoreWorkGiver != null)
        {
            this.setStatusReceiver(backupRestoreWorkGiver);
            this.setTypeOfStatus(ImportExportStatusType.Special);
            checkPrerequisites(true);
        }
        else
        {
            this.setTypeOfStatus(ImportExportStatusType.Dot);
            checkPrerequisites(false);
        }

        if (exportAll)
            exportAll();
    }


    public void exportAll()
    {
        throw new NotImplementedException(this.getClass(), "exportAll",
                "Method not implemented yet (will be used for autobackup)");
        // List<DatabaseTableConfiguration> tablesForDatabase =
        // importExportContext.getDatabaseVersionConfiguration()
        // .getTablesForDatabase(this.databaseVersion);
        //
        // for (DatabaseTableConfiguration table : tablesForDatabase)
        // {
        // export(table);
        // }
    }


    private void checkPrerequisites(boolean withAutoBackup)
    {
        String path = this.importExportContext.getExportLocation();

        if (withAutoBackup)
        {
            path += "/tmp/";
        }

        File f = new File(path);

        if (!f.exists())
        {
            f.mkdirs();
        }

        this.setRootPath(path);
        this.setFileLastPart(withAutoBackup ? "" : "_" + getCurrentDateForFile());
    }


    /**
     * Get Active Session
     */
    @Override
    public int getActiveSession()
    {
        return 2;
    }


    public void export(DatabaseTableConfiguration tableConfiguration)
    {
        openFile(this.getRootPath() + tableConfiguration.getObjectName() + this.getFileLastPart() + ".dbe");

        writeHeader(tableConfiguration, this.databaseVersion);

        Criteria criteria = getSession().createCriteria(tableConfiguration.getObjectClass()).addOrder(Order.asc("id"));

        List resultsData = criteria.list();

        this.statusSetMaxEntry(resultsData.size());

        Iterator it = resultsData.iterator();

        int dot_mark = 5;
        int count = 0;

        while (it.hasNext())
        {
            this.writeToFile(this.exportConverter.convert(tableConfiguration, (HibernateObject) it.next()) + "\n");
            count++;
            this.writeStatus(dot_mark, count);
        }

        closeFile();
    }


    public void export(Class clazz, DataDefinitionEntry dataDefinitionEntry)
    {
        openFile(this.getRootPath() + clazz.getSimpleName() + this.getFileLastPart() + ".dbe");

        writeHeader(dataDefinitionEntry, this.databaseVersion);

        Criteria criteria = getSession().createCriteria(clazz).addOrder(Order.asc("id"));

        List resultsData = criteria.list();

        this.statusSetMaxEntry(resultsData.size());

        Iterator it = resultsData.iterator();

        int dot_mark = 5;
        int count = 0;

        while (it.hasNext())
        {
            this.writeToFile(this.exportConverter.convert(dataDefinitionEntry, (HibernateObject) it.next()) + "\n");
            count++;
            this.writeStatus(dot_mark, count);
        }

        closeFile();
    }

}
