package com.atech.update.startup.data;

import java.util.Map;

/**
 * Created by andy on 11/12/17.
 */
public class ApplicationStartupConfigDto
{

    private String updateConfigPath;
    private String javaExe;
    private StartupTypeDefinition startupType = StartupTypeDefinition.LegacyStartup;
    private String userDirectoryApplicationName;
    private boolean isUsb;


    // private String updateConfig;

    public void loadData(Map<String, String> startupConfiguration)
    {
        this.updateConfigPath = startupConfiguration.get("UPDATE_CONFIG");
        this.javaExe = startupConfiguration.get("JAVA_EXE");

        if (startupConfiguration.containsKey("STARTUP_TYPE"))
        {
            int startupTypeInt = Integer.parseInt(startupConfiguration.get("STARTUP_TYPE"));
            this.startupType = StartupTypeDefinition.getByCode(startupTypeInt);
        }

        if (startupConfiguration.containsKey("USER_DIRECTORY_APPLICATION_NAME"))
        {
            this.userDirectoryApplicationName = startupConfiguration.get("USER_DIRECTORY_APPLICATION_NAME");
        }

        if (startupConfiguration.containsKey("IS_USB"))
        {
            String isUsbString = startupConfiguration.get("IS_USB");

            this.isUsb = (isUsbString.equals("1") || //
                    isUsbString.equalsIgnoreCase("true") || //
            isUsbString.equalsIgnoreCase("yes"));
        }

    }


    public StartupTypeDefinition getStartupType()
    {
        return startupType;
    }


    public String getUpdateConfigPath()
    {
        return updateConfigPath;
    }


    public String getJavaExe()
    {
        return javaExe;
    }


    public String getUserDirectoryApplicationName()
    {
        return userDirectoryApplicationName;
    }


    public boolean isUsb()
    {
        return isUsb;
    }
}
