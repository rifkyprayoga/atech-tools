package com.atech.data.user_data_dir;

import com.atech.update.startup.StartupUtil;
import com.atech.update.startup.data.ApplicationStartupConfigDto;
import com.atech.update.startup.data.StartupTypeDefinition;
import com.atech.update.startup.os.OSType;

/**
 * Created by andy on 08/12/17.
 */
public class UserDataDirectory
{

    static String fallbackDirectory = "../data";

    static String userDirectory;

    private static UserDataDirectory sUserDataDirectory = new UserDataDirectory();
    ApplicationStartupConfigDto applicationStartupConfig;
    String updateConfig;
    OSType osType;

    private StartupUtil startupUtil = new StartupUtil();

    public static UserDataDirectory getInstance()
    {
        return sUserDataDirectory;
    }


    private UserDataDirectory()
    {
        initializeUserDataDirectory();
    }


    public String getUserDataDirectory()
    {
        System.out.println("User Data Directory: " + userDirectory);
        return userDirectory;
    }


    private void initializeUserDataDirectory()
    {
        this.applicationStartupConfig = startupUtil.getApplicationStartupConfig();

        if (this.applicationStartupConfig == null)
        {
            System.out
                    .println("StartupConfig.properties couldn't be found. Please contact support for more help. Exiting!");
            System.exit(1);
        }

        if (this.applicationStartupConfig.isUsb())
        {
            userDirectory = fallbackDirectory;
        }
        else
        {
            if (applicationStartupConfig.getStartupType() != StartupTypeDefinition.ExtendedStartupWithDataDirectory && applicationStartupConfig.getStartupType() != StartupTypeDefinition.InternalStartup) {
                userDirectory = fallbackDirectory;
            }
            else {
                this.osType = OSType.getOSByType();

                if (this.osType == null)
                {
                    this.osType = OSType.Unix;
                }

                String userDir = System.getProperty("user.home");

                if (osType.getOSSpecificDataDirectory() != null)
                {
                    userDir += "/" + osType.getOSSpecificDataDirectory();
                }

                if (osType.isApplicationNamePrefixedWithDot())
                {
                    userDir += "/.";
                }
                else
                {
                    userDir += "/";
                }

                userDir += applicationStartupConfig.getUserDirectoryApplicationName();

                userDirectory = userDir;

            }
        }
    }


    public ApplicationStartupConfigDto getApplicationStartupConfig()
    {
        return applicationStartupConfig;
    }


    public String getParsedUserDataPath(String path)
    {
        if (path.contains("%USER_DATA_DIR%"))
        {
            path = path.replace("%USER_DATA_DIR%", userDirectory);
        }

        return path;
    }


    public String getUpdateConfig()
    {
        if (updateConfig != null)
            return updateConfig;

        if (applicationStartupConfig == null)
            return null;

        String updateConfig = applicationStartupConfig.getUpdateConfigPath();

        if (updateConfig == null)
            return null;

        if (updateConfig.contains("%USER_DATA_DIR%"))
        {
            updateConfig = updateConfig.replace("%USER_DATA_DIR%", userDirectory);
        }

        this.updateConfig = updateConfig;

        return updateConfig;
    }

    public void migrateAndValidateData(/*UserDataDirectoryExtractorValidator*/ Object userDataDirectoryExtractorValidator) {

        // TODO
        //UserDataDirectoryMigration migration = new UserDataDirectoryMigration(this);
        //migration.process();

        //x.GGCUserDataDirectoryExtractorValidator validator = new GGCUserDataDirectoryExtractorValidator("../ddda");
        //userDataDirectoryExtractorValidator.validateAndRecreate();
    }
}
