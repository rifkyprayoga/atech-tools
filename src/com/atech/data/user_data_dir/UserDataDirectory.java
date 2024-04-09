package com.atech.data.user_data_dir;

import com.atech.data.user_data_dir.v2.UserDataDirectoryContextV2;
import com.atech.data.user_data_dir.v2.UserDataDirectoryV2CreatorOrMigration;
import com.atech.data.user_data_dir.v2.UserDataDirectoryV2Validator;
import com.atech.update.startup.StartupUtil;
import com.atech.update.startup.data.ApplicationStartupConfigDto;
import com.atech.update.startup.data.StartupTypeDefinition;
import com.atech.update.startup.os.OSType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Created by andy on 08/12/17.
 */
/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2017-24  Andy (Aleksander) Rozman (Atech-Software)
 *
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 *  For additional information about this project please visit our project site on
 *  https://github.com/andyrozman/atech-tools or contact us via this email:
 *  andy@atech-software.com
 *
 *  @author Andy
 *
 */
@Slf4j
public class UserDataDirectory
{

    static String fallbackDirectory = "../data";

    static String userDirectory;

    private static UserDataDirectory sUserDataDirectory; // = new UserDataDirectory(applicationUDDContext);
    @Getter
    private UserDataDirectoryContextV2 userDataDirectoryContextV2;
    @Getter
    ApplicationStartupConfigDto applicationStartupConfig;
    String updateConfig;
    OSType osType;

    private StartupUtil startupUtil = new StartupUtil();

    public static UserDataDirectory createInstance(UserDataDirectoryContextV2 applicationUDDContext) {
        sUserDataDirectory = new UserDataDirectory(applicationUDDContext);
        return sUserDataDirectory;
    }

    public static UserDataDirectory createInstance() {
        sUserDataDirectory = new UserDataDirectory();
        return sUserDataDirectory;
    }

    public static UserDataDirectory getInstance() {
        return sUserDataDirectory;
    }

    private UserDataDirectory() {
        initializeUserDataDirectory();
    }

    private UserDataDirectory(UserDataDirectoryContextV2 applicationUDDContext)
    {
        this.userDataDirectoryContextV2 = applicationUDDContext;
        initializeUserDataDirectoryV2();
    }




    public String getUserDataDirectory()
    {
        //System.out.println("User Data Directory: " + userDirectory);
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


    private void initializeUserDataDirectoryV2() {
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


    public String getParsedUserDataPath(String path) {
        if (path.contains("%USER_DATA_DIR%")) {
            path = path.replace("%USER_DATA_DIR%", userDirectory);
        }

        return path;
    }


    public String getUpdateConfig() {
        if (this.userDataDirectoryContextV2!=null) {
            log.warn("UserDataDirectoryContextV2 doesn't use UpdateConfig V2/V3 anymore");
            return null;
        }

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

    public void migrateAndValidateData(UserDataDirectoryContext userDataDirectoryContext) {
        log.error("migrateAndValidateData not implemented for UserDataDirectory V1.");
    }


    public void migrateAndValidateData() {

        UserDataDirectoryV2Validator validator = new UserDataDirectoryV2Validator(this);

        if (!validator.isValidInstallation()) {
            log.warn("Installation doesn't seem to be valid. Running creation or migration of data directories");
            UserDataDirectoryV2CreatorOrMigration creatorOrMigration = new UserDataDirectoryV2CreatorOrMigration(this, validator.getUserApplicationDirectory());
            creatorOrMigration.runInstallOrMigration();
        } else {
            log.info("Installation is valid. Continuing start of application.");
        }
    }

}
