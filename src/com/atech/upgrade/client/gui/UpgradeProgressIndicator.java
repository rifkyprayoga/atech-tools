package com.atech.upgrade.client.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.upgrade.client.task.UpgradeTask;

/**
 * Created by andy on 02.12.15.
 */
public class UpgradeProgressIndicator
{

    Logger LOG = LoggerFactory.getLogger(UpgradeProgressIndicator.class);

    int progress = 0;
    String sourceClazz;


    public void setProgress(int percent)
    {
        LOG.debug("[{}]  Progress: {} %", sourceClazz, percent);
        this.progress = percent;
    }


    public void addToProgress(int percent)
    {
        setProgress(this.progress + percent);
    }


    public void setProgressFinished()
    {
        setProgress(100);
    }


    public void setProgressStart()
    {
        setProgress(0);
    }


    public void setTaskClass(UpgradeTask task)
    {
        this.sourceClazz = task.getClass().getSimpleName();
    }
}
