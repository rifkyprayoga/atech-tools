package com.atech.upgrade.client.data;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.atech.db.hibernate.transfer.BackupRestoreBase;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.db.hibernate.transfer.BackupRestoreWorkGiver;

/**
 * Created by andy on 24.11.15.
 */
public interface UpgradeApplicationContext
{

    String getTitle(String className);


    boolean isTaskEnabled(String className);


    /**
     * Get root for upgrade (as File instance)
     */
    File getUpgradeDataDirectory();


    /**
     * Get Application Root directory (as File instance)
     */
    File getApplicationRootDirectory();


    /**
     * Get Application Root directory (as File instance)
     */
    String getApplicationRootDirectoryAsString();


    /**
     * Get Short Application Name (used for Prefix for Update instance)
     */
    String getShortApplicationName();


    /**
     * Get Backup file set (it can contain files or directories. If directory is selected, it includes
     * all children.
     */
    List<String> getBackupFileSet();


    /**
     * Get BackupRestoreCollection instance.
     */
    BackupRestoreCollection getBackupRestoreCollection();


    /**
     * Run BackupRestore Runner is used for doing the backup of all tables and on end it returns fileName of
     * backup file.
     */
    String runBackupRestoreRunner(BackupRestoreWorkGiver backupRestoreWorkGiver,
            Map<String, BackupRestoreBase> ht_backup_objects);


    int getUpgradeVersion();


    void setUpgradeVersion(int version);


    File getUpgradeFile();


    // set in DownloadFileFromServer
    void setUpgradeFile(File file);


    String getUpgradeFileName();


    // set with Update Xml
    void setUpgradeFileName(String fileName);


    File getUpgradeRoot();


    // set with UnpackFile
    void setUpgradeRoot(File root);


    File getUpgradeInstanceDirectory();


    // set with UnpackFile
    void setUpgradeInstanceDirectory(File directory);


    File getUpgradeInstanceBackupDirectory();


    // ser BackupFilesTask
    void setUpgradeInstanceBackupDirectory(File directory);

}
