package com.atech.upgrade.client.task;

import com.atech.upgrade.client.data.UpgradeApplicationContext;
import com.atech.upgrade.client.gui.UpgradeProgressIndicator;

/**
 * Created by andy on 24.11.15.
 */
public class DownloadFileFromServer extends UpgradeTaskAbstract
{

    public DownloadFileFromServer(UpgradeApplicationContext applicationUpgradeContext)
    {
        super(applicationUpgradeContext);
    }


    // check if file exists (root + fileName),
    // - yes: set it into context (check CRC to see if file is downloaded
    // succesfully),
    // - no: download file, check CRC, set into context, else fail

    public Object executeTask(UpgradeProgressIndicator progressIndicator)
    {
        return null;
    }


    public boolean isFinished()
    {
        return false;
    }


    public boolean hasError()
    {
        return false;
    }
}
