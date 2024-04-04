package com.atech.upgrade.client.task;

import java.io.File;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.upgrade.client.data.UpgradeApplicationContext;
import com.atech.upgrade.client.gui.UpgradeProgressIndicator;
import com.atech.upgrade.data.UpgradeException;
import com.atech.upgrade.data.UpgradeStepStatus;
import com.atech.utils.data.ATechDate;
import com.atech.utils.data.ATechDateType;
import com.atech.utils.file.zip.util.Zip4jUtil;

/**
 * Created by andy on 24.11.15.
 */
public class UnpackFile extends UpgradeTaskAbstract
{

    private static final Logger LOG = LoggerFactory.getLogger(UnpackFile.class);

    private static Zip4jUtil zip4jUtil = new Zip4jUtil();


    public UnpackFile(UpgradeApplicationContext applicationUpgradeContext)
    {
        super(applicationUpgradeContext);
    }


    public Object executeTask(UpgradeProgressIndicator progressIndicator)
    {
        progressIndicator.setTaskClass(this);
        progressIndicator.setProgressStart();

        File root = applicationUpgradeContext.getUpgradeDataDirectory();
        String fileName = applicationUpgradeContext.getUpgradeFileName();

        File upgradeFile = new File(root.getAbsolutePath() + "/" + fileName);

        if (upgradeFile.exists())
        {
            progressIndicator.setProgress(20);
            // System.out.println("Upgrade file found.");

            File applicationUpgradeInstance = new File(
                    root.getAbsolutePath() + "/" + applicationUpgradeContext.getShortApplicationName() + "_upgrade_"
                            + applicationUpgradeContext.getUpgradeVersion() + "_" + getDateTimeAsLong());

            if (!applicationUpgradeInstance.exists())
            {
                applicationUpgradeInstance.mkdirs();

                // System.out.println("Created upgrade instance directory.");
                this.applicationUpgradeContext.setUpgradeInstanceDirectory(applicationUpgradeInstance);
            }

            progressIndicator.setProgress(40);

            try
            {
                zip4jUtil.unzipFile(upgradeFile, applicationUpgradeInstance);
                LOG.info("Upgrade file unpacked.");
            }
            catch (Exception ex)
            {
                LOG.error("Error unzipping upgrade file: {}" + ex.getMessage());
                this.error = new UpgradeException("Error unzipping upgrade file: {}" + ex.getMessage(), ex);
            }

            progressIndicator.setProgress(80);
        }
        else
        {
            LOG.error("Upgrade file NOT found.");
            this.error = new UpgradeException("Upgrade file NOT Found.");
        }

        progressIndicator.setProgressFinished();

        return this.error == null ? UpgradeStepStatus.Done : UpgradeStepStatus.Error;
    }


    private long getDateTimeAsLong()
    {
        ATechDate atd = new ATechDate(ATechDateType.DateAndTimeSec, new GregorianCalendar());
        return atd.getATDateTimeAsLong();
    }

}
