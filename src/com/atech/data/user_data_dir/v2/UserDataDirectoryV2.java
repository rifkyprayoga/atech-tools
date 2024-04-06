package com.atech.data.user_data_dir.v2;

import com.atech.data.user_data_dir.UserDataDirectoryContext;
import com.atech.update.startup.StartupUtil;
import com.atech.update.startup.data.ApplicationStartupConfigDto;
import com.atech.update.startup.data.StartupTypeDefinition;
import com.atech.update.startup.os.OSType;
import lombok.Getter;

import java.io.File;

/**
 * Created by andy on 08/12/17.
 */

public class UserDataDirectoryV2
{

    //static String fallbackDirectory = "../data";

    static String userDirectory;

    private static UserDataDirectoryV2 sUserDataDirectory; // = new UserDataDirectoryV2();
    private final UserDataDirectoryContextV2 userDataDirectoryContextV2;
    //@Getter
    //ApplicationStartupConfigDto applicationStartupConfig;
    //String updateConfig;
    OSType osType;

    private StartupUtil startupUtil = new StartupUtil();


    public static UserDataDirectoryV2 createInstance(UserDataDirectoryContextV2 applicationUDDContext) {
        sUserDataDirectory = new UserDataDirectoryV2(applicationUDDContext);
        return sUserDataDirectory;
    }


    public static UserDataDirectoryV2 getInstance() {
        return sUserDataDirectory;
    }


    private UserDataDirectoryV2(UserDataDirectoryContextV2 applicationUDDContext) {
        this.userDataDirectoryContextV2 = applicationUDDContext;
        initializeUserDataDirectory();
    }


    public String getUserDataDirectory() {
        System.out.println("User Data Directory: " + userDirectory);
        return userDirectory;
    }


    private void initializeUserDataDirectory()
    {
        // TODO add USB check, if this is USB installation data folder needs to be subfolder to current directory

//        if (this.applicationStartupConfig.isUsb())
//        {
//            userDirectory = fallbackDirectory;
//        }
//        else
        {
            this.osType = OSType.getOSByType();

            if (this.osType == null) {
                this.osType = OSType.Unix;
            }

            String userDir = System.getProperty("user.home");

            if (osType.getOSSpecificDataDirectory() != null) {
                userDir += "/" + osType.getOSSpecificDataDirectory();
            }

            if (osType.isApplicationNamePrefixedWithDot()) {
                userDir += "/.";
            } else {
                userDir += "/";
            }

            userDir += userDataDirectoryContextV2.getApplicationDataDirectoryName();

            userDirectory = userDir;
        }
    }



    public String getParsedUserDataPath(String path)
    {
        if (path.contains("%USER_DATA_DIR%"))
        {
            path = path.replace("%USER_DATA_DIR%", userDirectory);
        }

        return path;
    }


//    public String getUpdateConfig()
//    {
//        if (updateConfig != null)
//            return updateConfig;
//
//        if (applicationStartupConfig == null)
//            return null;
//
//        String updateConfig = applicationStartupConfig.getUpdateConfigPath();
//
//        if (updateConfig == null)
//            return null;
//
//        if (updateConfig.contains("%USER_DATA_DIR%"))
//        {
//            updateConfig = updateConfig.replace("%USER_DATA_DIR%", userDirectory);
//        }
//
//        this.updateConfig = updateConfig;
//
//        return updateConfig;
//    }

    public void migrateAndValidateData() {

        // TODO

        // 1.Check if target application directory exists
        //   YES - Check that it contains all needed files
        //   NO - go to 2.
        File userDirectoryFile = new File(userDirectory);


        if (userDirectoryFile.exists() && userDirectoryFile.isDirectory()) {

        } else {

        }


        // 2. Try to find data files of previous version (data defined in context, we can search
        //    for specific directory in this directory or in some of the parents)
        //    FOUND - go to 3.
        //    NOT FOUND - go to 4.


        // 3. Migration scenario, copy all the files (directories to check)
        //    from found directory. Do the check afterwards again.
        //    IF ALL FILES THERE... EXIT
        //    IF FILES NOT THERE need to copy them


        // 4. New Instalation scenario
        //    create directory structure, unpack installation files




        // TODO
        //UserDataDirectoryMigration migration = new UserDataDirectoryMigration(this);
        //migration.process();

        //x.GGCUserDataDirectoryExtractorValidator validator = new GGCUserDataDirectoryExtractorValidator("../ddda");
        //userDataDirectoryExtractorValidator.validateAndRecreate();
    }
}
