package com.atech.data.install_migration;

import java.util.ArrayList;

/**
 * Created by andy on 07.04.2024.
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
public class InstallRecipe extends ArrayList<RecipeEntryDto> {


    public void addUnzipArchive(String archiveName) {
        add(RecipeEntryDto.builder()
                .installOperation(InstallOperation.UNZIP_ARCHIVE)
                .parameter1(archiveName)
                .build());
    }

    public void addUnzipArchive(String archiveName, String targetDirectory) {
        add(RecipeEntryDto.builder()
                .installOperation(InstallOperation.UNZIP_ARCHIVE)
                .parameter1(archiveName)
                .parameter2(targetDirectory)
                .build());
    }

    public void addMakeDirectory(String targetDirectory) {
        add(RecipeEntryDto.builder()
                .installOperation(InstallOperation.MAKE_DIRECTORY)
                .parameter1(targetDirectory)
                .build());
    }
}
