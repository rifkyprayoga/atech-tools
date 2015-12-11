package com.atech.upgrade.client.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.atech.db.hibernate.transfer.BackupRestoreBase;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.db.hibernate.transfer.BackupRestoreWorkGiver;
import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
import com.atech.upgrade.client.data.UpgradeApplicationContext;
import com.atech.upgrade.client.gui.UpgradeProgressIndicator;

/**
 * Created by andy on 24.11.15.
 */
public class BackupDatabaseTask extends UpgradeTaskAbstract implements BackupRestoreWorkGiver
{

    protected Map<String, BackupRestoreObject> ht_backup_objects = new HashMap<String, BackupRestoreObject>();
    String taskName;
    float maxPercentCount = 0.0f;
    int currentObject = -1;
    int lastStatus = 0;


    public BackupDatabaseTask(UpgradeApplicationContext applicationUpgradeContext)
    {
        super(applicationUpgradeContext);
    }


    public Object executeTask(UpgradeProgressIndicator progressIndicator)
    {
        // 1. select all entries in backup restore collectioon
        // 2. create backup
        // 3. get backup filename
        // 4. copy backup filename to our backup

        progressIndicator.setProgressStart();

        BackupRestoreCollection brCollection = this.applicationUpgradeContext.getBackupRestoreCollection();

        createFullCollectionTable(brCollection);

        progressIndicator.setProgress(5);

        maxPercentCount = ht_backup_objects.size() * 100.0f;

        String fileName = this.applicationUpgradeContext.runBackupRestoreRunner(this, ht_backup_objects);

        System.out.println("Filename: " + fileName);

        return null;
    }


    public void createFullCollectionTable(BackupRestoreBase cb)
    {

        if (!cb.hasNodeChildren())
        {
            // no children
            cb.setSelected(true);

            if (!cb.isCollection())
            {
                ht_backup_objects.put(cb.getTargetName(), (BackupRestoreObject) cb);
            }

        }
        else
        {
            // children
            ArrayList<CheckBoxTreeNodeInterface> lst = cb.getNodeChildren();

            for (int i = 0; i < lst.size(); i++)
            {
                createFullCollectionTable((BackupRestoreBase) lst.get(i));
            }
        }
    }


    public void setStatus(int percent)
    {
        // System.out.println("Backup status: " + taskName + " - " + percent +
        // " %");

        float vv = ((this.currentObject * 100.0f) + percent) / maxPercentCount;
        vv *= 100;
        vv /= 110;
        vv *= 100;

        int status = (int) vv;

        status += 5;

        if (status != lastStatus)
        {
            this.lastStatus = status;
            System.out.println("Full status: " + lastStatus + " %");
        }
    }


    public void setTask(String task_name)
    {
        if (!StringUtils.equals(taskName, task_name))
        {
            this.currentObject++;
            this.taskName = task_name;
        }
    }
}
