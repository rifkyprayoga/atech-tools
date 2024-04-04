package com.atech.upgrade.test;

import org.junit.Assert;
import org.junit.Test;

import com.atech.upgrade.client.gui.UpgradeProgressIndicator;
import com.atech.upgrade.client.task.BackupFilesTask;
import com.atech.upgrade.client.task.UnpackFile;

/**
 * Created by andy on 25.11.15.
 */
public class TestUpgradeSteps
{

    @Test
    public void testUnpackFile()
    {
        TestUpgradeAppContext context = new TestUpgradeAppContext();

        context.setUpgradeFileName("GGC_Update_0.6.1.zip");
        context.setUpgradeVersion(20);

        // /u01/GGC/upd_image_01/data/upgrade

        UnpackFile handler = new UnpackFile(context);

        UpgradeProgressIndicator progressIndicator = new UpgradeProgressIndicator();

        Assert.assertNotNull(handler.executeTask(progressIndicator));

        BackupFilesTask handlerBackup = new BackupFilesTask(context);

        handlerBackup.executeTask(progressIndicator);

    }

}
