package com.atech.upgrade.client.task;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.upgrade.client.data.UpgradeApplicationContext;
import com.atech.upgrade.client.gui.UpgradeProgressIndicator;
import com.atech.upgrade.data.UpgradeStepStatus;

/**
 * Created by andy on 24.11.15.
 */
public class BackupFilesTask extends UpgradeTaskAbstract
{

    private static final Logger LOG = LoggerFactory.getLogger(BackupFilesTask.class);


    public BackupFilesTask(UpgradeApplicationContext applicationUpgradeContext)
    {
        super(applicationUpgradeContext);
    }


    public Object executeTask(UpgradeProgressIndicator progressIndicator)
    {
        progressIndicator.setTaskClass(this);
        progressIndicator.setProgressStart();

        File upgradeInstance = applicationUpgradeContext.getUpgradeInstanceDirectory();

        String upgInstanceName = upgradeInstance.getAbsolutePath();
        // System.out.println("Upg instance name 1: " + upgInstanceName);
        //
        // System.out.println("upgInstanceName.charAt(upgInstanceName.length() - 1)"
        // + upgInstanceName.charAt(upgInstanceName.length() - 1));

        upgInstanceName = removeSeparatorFromFilename(upgInstanceName);

        File upgradeBackupInstance = new File(upgInstanceName + "_backup/");

        String upgradeBackupInstanceString = addSeparatorToFilename(upgradeBackupInstance.getAbsolutePath());

        progressIndicator.setProgress(10);

        this.applicationUpgradeContext.setUpgradeInstanceBackupDirectory(upgradeBackupInstance);

        upgradeBackupInstance.mkdirs();

        progressIndicator.setProgress(20);

        int step = 80 / applicationUpgradeContext.getBackupFileSet().size();

        for (String fileName : applicationUpgradeContext.getBackupFileSet())
        {
            File sourceFile = new File(applicationUpgradeContext.getApplicationRootDirectoryAsString() + fileName);
            File targetFile = new File(upgradeBackupInstanceString + fileName);

            try
            {
                if (sourceFile.isDirectory())
                {
                    FileUtils.copyDirectory(sourceFile, targetFile, true);
                }
                else
                {
                    FileUtils.copyFile(sourceFile, targetFile, true);
                }
            }
            catch (Exception ex)
            {
                LOG.debug("Problem copying {}: [name={},ex={}]", sourceFile.isDirectory() ? "directory" : "file",
                    fileName, ex.getMessage(), ex);
                this.error = ex;
                break;
            }

            progressIndicator.addToProgress(step);
        }

        progressIndicator.setProgressFinished();

        return this.error == null ? UpgradeStepStatus.Done : UpgradeStepStatus.Error;

    }

}
