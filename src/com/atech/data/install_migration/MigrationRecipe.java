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
public class MigrationRecipe extends ArrayList<RecipeEntryDto> {

    public MigrationRecipe() {
        super();
    }

    public void addRenameBefore(String sourceFile, String targetFile) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.RENAME_BEFORE)
                .parameter1(sourceFile)
                .parameter2(targetFile)
                .build());
    }

    public void addCopyContents(String sourceDirectory) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.COPY_CONTENTS)
                .parameter1(sourceDirectory)
                .build());
    }

    public void addCopyDirectory(String directoryName) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.COPY_DIRECTORY)
                .parameter1(directoryName)
                .build());
    }

    public void addUnzipArchive(String installFile, String targetDirectory) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.UNZIP_ARCHIVE)
                .parameter1(installFile)
                .parameter2(targetDirectory)
                .build());
    }

    public void addCopyDirectoryOrInstall(String directory, String installFile) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.COPY_DIRECTORY_OR_INSTALL)
                .parameter1(directory)
                .parameter2(installFile)
                .build());
    }

    public void addDeleteFileFromTarget(String fileName) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.DELETE_FILE_FROM_TARGET)
                .parameter1(fileName)
                .build());
    }

    public void addCopyFile(String fileToCopy) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.COPY_FILE)
                .parameter1(fileToCopy)
                .build());
    }

    public void addRenameSourceAfter() {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.RENAME_SOURCE_AFTER)
                .build());
    }

    public void addEditFileInTargetReplaceLine(String file, String startsWith, String newLine) {
        add(RecipeEntryDto.builder()
                .migrationOperation(MigrationOperation.EDIT_FILE_IN_TARGET_REPLACE_LINE)
                .parameter1(file)
                .parameter2(startsWith)
                .parameter3(newLine)
                .build());
    }

}
