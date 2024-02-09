package com.atech.upgrade.client.task;

import com.atech.upgrade.client.data.UpgradeApplicationContext;
import com.atech.upgrade.client.gui.UpgradeProgressIndicator;

/**
 * Created by andy on 24.11.15.
 */
public class CompleteUpdateTask extends UpgradeTaskAbstract
{

    public CompleteUpdateTask(UpgradeApplicationContext applicationUpgradeContext)
    {
        super(applicationUpgradeContext);
    }


    public Object executeTask(UpgradeProgressIndicator progressIndicator)
    {
        return null;
    }

    // change StartupStatus.txt
}
