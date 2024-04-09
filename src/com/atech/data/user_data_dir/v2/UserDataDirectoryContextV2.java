package com.atech.data.user_data_dir.v2;

import com.atech.data.FileDirectoryDto;
import com.atech.data.install_migration.InstallRecipe;
import com.atech.data.install_migration.MigrationRecipe;

import java.util.List;

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
public interface UserDataDirectoryContextV2 {

    String getApplicationName();

    String getApplicationDataDirectoryName();

    String getFallbackDirectory();

    List<FileDirectoryDto> getAllDirectoriesToCheck();

    List<FileDirectoryDto> getAllFilesToCheck();

    /**
     * Get Legacy Application Data Folder (folder under which all other configurations are stored))
     * @return
     */
    String getLegacyApplicationDataFolderName();

    /**
     * How far into parents structure should we delve (this number will tell us how many parents down
     * we will go from current running directory to find data folder). For example 0 means that we
     * will start in current folder and just look at children of this folder and all other children
     * folders.
     * @return
     */
    int howManyParentsToTraverseForFindingLegacyDataFolder();

    MigrationRecipe getMigrationRecipe();

    InstallRecipe getInstallRecipe();

}