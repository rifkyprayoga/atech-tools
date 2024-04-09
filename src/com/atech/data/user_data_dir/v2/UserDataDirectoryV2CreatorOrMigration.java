package com.atech.data.user_data_dir.v2;

import com.atech.data.FileDirectoryDto;
import com.atech.data.install_migration.InstallMigrationHandler;
import com.atech.data.user_data_dir.UserDataDirectory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by andy on 06.04.2024.
 */
/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2024  Andy (Aleksander) Rozman (Atech-Software)
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
public class UserDataDirectoryV2CreatorOrMigration {

    private final UserDataDirectory userDataDirectory;
    private final File userApplicationDirectory;
    private final UserDataDirectoryContextV2 userDataDirectoryContextV2;
    private FileFilter dataDirectoryFilter;
    private FileFilter directoryFilter;


    public UserDataDirectoryV2CreatorOrMigration(UserDataDirectory userDataDirectory, File userApplicationDirectory) {
        this.userDataDirectory = userDataDirectory;
        this.userApplicationDirectory = userApplicationDirectory;
        this.userDataDirectoryContextV2 = this.userDataDirectory.getUserDataDirectoryContextV2();
    }


    public void runInstallOrMigration() {

        File file = new File("");
        File rootParentForSearch = file.getAbsoluteFile();

        log.debug("Current Directory: {}", file.getAbsolutePath());

        for(int i=0; i<this.userDataDirectoryContextV2.howManyParentsToTraverseForFindingLegacyDataFolder(); i++){
            //log.error("Parent: {}", rootParentForSearch);
            rootParentForSearch = rootParentForSearch.getParentFile();
        }

        log.debug("Last Parent: {}", rootParentForSearch);

        dataDirectoryFilter = pathname -> pathname.isDirectory()
                && pathname.getName().equals(userDataDirectoryContextV2.getLegacyApplicationDataFolderName());

        directoryFilter = pathname -> pathname.isDirectory()
                && !pathname.getName().equals("src")
                && !pathname.getName().equals("target");

        File dataDirectory = findDataDirectory(rootParentForSearch);

        if (dataDirectory==null) {
            installNewDataDirectory();
        } else {
            migrateDataDirectory(dataDirectory);
        }
    }


    private void migrateDataDirectory(File dataDirectory) {
        log.info("Trying to migrateDataDirectory: {}", dataDirectory.getAbsoluteFile());

        try {
            InstallMigrationHandler installMigrationHandler = new InstallMigrationHandler(dataDirectory, userApplicationDirectory, this.userDataDirectoryContextV2);

            installMigrationHandler
                    .processMigration(this.userDataDirectoryContextV2.getMigrationRecipe());

        } catch(Exception ex) {
            log.error("Problem migrating files to target directory: {}", ex.getMessage());
        }
    }

    private void installNewDataDirectory() {
        log.info("Trying to installNewDataDirectory");

        try {
            InstallMigrationHandler installMigrationHandler = new InstallMigrationHandler(userApplicationDirectory, this.userDataDirectoryContextV2);

            installMigrationHandler
                    .processInstall(this.userDataDirectoryContextV2.getInstallRecipe());

        } catch(Exception ex) {
            log.error("Problem installing files to target directory: {}", ex.getMessage());
        }
    }


    public File findDataDirectory(File searchStart) {

        File[] possibleDataDirectory = searchStart.listFiles(dataDirectoryFilter);

        if (possibleDataDirectory!=null && possibleDataDirectory.length==1) {
            if (determineIfThisIsCorrectDataDirectory(possibleDataDirectory[0])) {
                return possibleDataDirectory[0];
            }
        }

        File[] allDirectories = searchStart.listFiles(directoryFilter);

        if (allDirectories!=null) {
            for (File allDirectory : allDirectories) {
                File dataDirectory = findDataDirectory(allDirectory);
                if (dataDirectory!=null)
                    return dataDirectory;
            }
        }

        return null;
    }


    private boolean determineIfThisIsCorrectDataDirectory(File parentDirectory) {
        for (FileDirectoryDto fileDirectoryDto : this.userDataDirectoryContextV2.getAllFilesToCheck()) {
            if (fileDirectoryDto.isCheckNeeded()) {
                File file = new File(parentDirectory, fileDirectoryDto.getFileName());

                if (!file.exists()) {
                    log.warn("determineIfThisIsCorrectDataDirectory: {}: false", parentDirectory.getAbsoluteFile());
                    return false;
                }
            }
        }

        log.info("determineIfThisIsCorrectDataDirectory: {}: true", parentDirectory.getAbsoluteFile());
        return true;
    }

}
