package com.atech.gui_fw.licence;

public abstract class ApplicationLicenceData
{

    protected String licence_file = null;
    protected String data_strings[] = null;
    protected boolean has_plugins = false;

    public ApplicationLicenceData()
    {
        loadApplicationData();
    }

    public abstract void loadApplicationData();

    public String getLicenceFile()
    {
        return this.licence_file;
    }

    public String[] getCoderData()
    {
        return this.data_strings;
    }

    public boolean hasPlugins()
    {
        return this.has_plugins;
    }

}
