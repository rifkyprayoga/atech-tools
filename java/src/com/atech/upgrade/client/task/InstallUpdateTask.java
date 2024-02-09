package com.atech.upgrade.client.task;

import com.atech.upgrade.client.data.UpgradeApplicationContext;
import com.atech.upgrade.client.gui.UpgradeProgressIndicator;

/**
 * Created by andy on 24.11.15.
 */
public class InstallUpdateTask extends UpgradeTaskAbstract
{

    public InstallUpdateTask(UpgradeApplicationContext applicationUpgradeContext)
    {
        super(applicationUpgradeContext);
    }


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
    // make install (copy all from temporary into running)
}
