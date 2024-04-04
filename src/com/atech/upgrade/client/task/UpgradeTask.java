package com.atech.upgrade.client.task;

import com.atech.upgrade.client.gui.UpgradeProgressIndicator;

/**
 * Created by andy on 24.11.15.
 */
public interface UpgradeTask
{

    Object executeTask(UpgradeProgressIndicator progressIndicator);


    boolean isFinished();


    boolean hasError();


    String getTitle();

}
