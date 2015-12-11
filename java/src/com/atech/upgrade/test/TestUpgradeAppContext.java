package com.atech.upgrade.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.db.hibernate.transfer.BackupRestoreWorkGiver;
import com.atech.upgrade.client.data.UpgradeApplicationContextAbstract;

/**
 * Created by andy on 25.11.15.
 */
public class TestUpgradeAppContext extends UpgradeApplicationContextAbstract
{

    public String getTitle(String className)
    {
        return null;
    }


    public boolean isTaskEnabled(String className)
    {
        return false;
    }


    public File getUpgradeDataDirectory()
    {
        return getFileWithRootPath("data/upgrade/");
    }


    public String getApplicationRootDirectoryAsString()
    {
        return "/u01/GGC/upd_image_01/";
    }


    public String getShortApplicationName()
    {
        return "GGC";
    }


    public List<String> getBackupFileSet()
    {
        List<String> fileList = new ArrayList<String>();

        fileList.add("bin/");
        fileList.add("lib/");
        fileList.add("data/update/GGC_Update.properties");
        fileList.add("data/StartupStatus.txt");

        return fileList;
    }


    public BackupRestoreCollection getBackupRestoreCollection()
    {
        return null;
    }


    public String runBackupRestoreRunner(BackupRestoreWorkGiver backupRestoreWorkGiver,
            Map<String, BackupRestoreObject> ht_backup_objects)
    {

        return null;
    }

}
