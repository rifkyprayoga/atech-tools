package com.atech.upgrade.client.task;

import java.io.File;

import com.atech.upgrade.client.data.UpgradeApplicationContext;

/**
 * Created by andy on 24.11.15.
 */
public abstract class UpgradeTaskAbstract implements UpgradeTask
{

    protected boolean finished;
    protected Exception error;
    protected UpgradeApplicationContext applicationUpgradeContext;


    public UpgradeTaskAbstract(UpgradeApplicationContext applicationUpgradeContext)
    {
        this.applicationUpgradeContext = applicationUpgradeContext;
    }


    public boolean isFinished()
    {
        return finished;
    }


    public boolean hasError()
    {
        return (error != null);
    }


    public String getTitle()
    {
        return applicationUpgradeContext.getTitle(this.getClass().getSimpleName());
    }


    protected String removeSeparatorFromFilename(String fileName)
    {
        if (fileName.charAt(fileName.length() - 1) == File.separatorChar)
        {
            fileName = fileName.substring(0, fileName.length() - 1);
            System.out.println("removeSeparatorFromFilename: " + fileName);
        }

        return fileName;
    }


    protected String addSeparatorToFilename(String fileName)
    {
        // System.out.println("" + File.separator);
        // System.out.println("" + File.separatorChar);
        // System.out.println("" + File.pathSeparator);
        // System.out.println("" + File.pathSeparatorChar);

        if (fileName.charAt(fileName.length() - 1) != File.separatorChar)
        {
            fileName = fileName + File.separatorChar;
            System.out.println("addSeparatorToFilename: " + fileName);
        }

        return fileName;
    }

}
