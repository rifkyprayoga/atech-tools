package com.atech.db.hibernate.tool.app;

import java.util.List;

import org.hibernate.cfg.Configuration;

import com.atech.db.hibernate.HibernateUtil;
import com.atech.db.hibernate.tool.data.dto.DbInitTaskDto;
import com.atech.db.hibernate.tool.data.management.DbToolDbHandler;
import com.atech.db.hibernate.tool.data.management.init.DbToolInitDbRunner;
import com.atech.db.hibernate.transfer.BackupRestoreWorkGiver;

/**
 * Created by andy on 28/11/16.
 */
public interface DbToolApplicationInitDb extends BackupRestoreWorkGiver
{

    DbInitType getDbInitType();


    boolean fillDatabase(Configuration hibernateConfigurationExternal);


    DbToolInitDbRunner getInitDbRunner(DbToolDbHandler.DbInitPreStatus dbInitPreStatus, HibernateUtil hibernateUtil);


    List<DbInitTaskDto> getCustomTasks();


    DbToolApplicationInterface getDbToolApplication();

}
