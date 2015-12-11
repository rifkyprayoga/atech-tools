package com.atech.upgrade.client.data;

import java.io.File;

/**
 * Created by andy on 25.11.15.
 */
public abstract class UpgradeApplicationContextAbstract implements UpgradeApplicationContext
{

    int upgradeVersion;
    File upgradeFile;
    String upgradeFileName;
    File upgradeRoot;
    File upgradeInstanceDirectory;
    File upgradeInstanceBackupDirectory;


    protected File getFileWithRootPath(String subPathAndFilename)
    {
        return new File(getApplicationRootDirectoryAsString() + subPathAndFilename);
    }


    public int getUpgradeVersion()
    {
        return upgradeVersion;
    }


    public void setUpgradeVersion(int version)
    {
        this.upgradeVersion = version;
    }


    public File getUpgradeFile()
    {
        return this.upgradeFile;
    }


    public void setUpgradeFile(File file)
    {
        this.upgradeFile = file;
    }


    public File getUpgradeRoot()
    {
        return upgradeRoot;
    }


    public void setUpgradeRoot(File directory)
    {
        this.upgradeRoot = directory;
    }


    public String getUpgradeFileName()
    {
        return upgradeFileName;
    }


    public void setUpgradeFileName(String fileName)
    {
        this.upgradeFileName = fileName;
    }


    public File getUpgradeInstanceDirectory()
    {
        return upgradeInstanceDirectory;
    }


    public void setUpgradeInstanceDirectory(File directory)
    {
        this.upgradeInstanceDirectory = directory;
    }


    public File getApplicationRootDirectory()
    {
        return new File(getApplicationRootDirectoryAsString());
    }


    public File getUpgradeInstanceBackupDirectory()
    {
        return upgradeInstanceBackupDirectory;
    }


    // ser BackupFilesTask
    public void setUpgradeInstanceBackupDirectory(File directory)
    {
        this.upgradeInstanceBackupDirectory = directory;
    }

}
