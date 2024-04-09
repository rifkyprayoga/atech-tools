package com.atech.data.user_data_dir.v2;

import com.atech.data.FileDirectoryDto;
import com.atech.data.user_data_dir.UserDataDirectory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;


/*
 *  This file is part of ATech Tools library.
 *
 *  Description: Validator for UserDataDirectory V2 (06.04.2024)
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
public class UserDataDirectoryV2Validator {

    @Getter
    private File userApplicationDirectory;
    private final UserDataDirectory userDataDirectory;

    public UserDataDirectoryV2Validator(UserDataDirectory userDataDirectory) {
        this.userDataDirectory = userDataDirectory;
    }

    public boolean isValidInstallation() {
        // 1.Check if target application directory exists
        //   YES - Check that it contains all needed files
        //   NO - go to 2.
        userApplicationDirectory = new File(userDataDirectory.getUserDataDirectory());

        if (userApplicationDirectory.exists() && userApplicationDirectory.isDirectory()) {
            log.info("Directory UserDataDirectory: {} found.", userApplicationDirectory.getAbsolutePath());
            return checkIfAllRequiredFilesExist();
        }

        log.info("Directory UserDataDirectory: {} NOT found.", userApplicationDirectory.getAbsolutePath());
        return false;
    }

    private boolean checkIfAllRequiredFilesExist() {
        log.info("check If All Required Files Exist");

        UserDataDirectoryContextV2 userDataDirectoryContextV2 = userDataDirectory.getUserDataDirectoryContextV2();

        List<FileDirectoryDto> allDirectoriesToCheck = userDataDirectoryContextV2.getAllFilesToCheck();

        boolean validInstall = true;

        for (FileDirectoryDto fileDirectoryDto : allDirectoriesToCheck) {
            File file = new File(this.userApplicationDirectory, fileDirectoryDto.getFileName());

            if (file.exists()) {
                log.debug("File {} exists: {}", file, file.exists());
            } else {
                log.warn("File {} exists: {}", file, file.exists());
            }

            if (!file.exists()) {
                return false;
            }
        }

        return true;
    }
}
