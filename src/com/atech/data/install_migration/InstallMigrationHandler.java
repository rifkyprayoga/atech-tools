package com.atech.data.install_migration;

import com.atech.data.user_data_dir.v2.UserDataDirectoryContextV2;
import com.atech.utils.file.zip.util.ZipJava8Util;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;

import java.io.*;

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
@Slf4j
public class InstallMigrationHandler {

    private final File sourceDirectory;
    private final File targetDirectory;
    private final UserDataDirectoryContextV2 userDataDirectoryContextV2;
    ZipJava8Util zipJava8Util = new ZipJava8Util();

    public InstallMigrationHandler(File sourceDirectory, File targetDirectory, UserDataDirectoryContextV2 userDataDirectoryContextV2) {
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
        this.userDataDirectoryContextV2 = userDataDirectoryContextV2;
    }

    public InstallMigrationHandler(File targetDirectory, UserDataDirectoryContextV2 userDataDirectoryContextV2) {
        this.sourceDirectory = null;
        this.targetDirectory = targetDirectory;
        this.userDataDirectoryContextV2 = userDataDirectoryContextV2;
    }


    public void processInstall(InstallRecipe recipe) throws Exception {
        log.info("Process Install");
        for (RecipeEntryDto recipeEntry : recipe) {
            switch (recipeEntry.getInstallOperation()) {
                case UNZIP_ARCHIVE:
                case INSTALL:
                    unzipArchive(recipeEntry);
                    break;
                case MAKE_DIRECTORY:
                    makeDirectory(recipeEntry);
                    break;
                default:
                    log.error("Unknown operation for install: {}", recipeEntry.getInstallOperation());
            }
        }
    }


    public void processMigration(MigrationRecipe recipe) throws Exception {
        log.info("Process Migration");
        for (RecipeEntryDto recipeEntry : recipe) {
            switch(recipeEntry.getMigrationOperation()) {
                case COPY_FILE:
                    copyFile(recipeEntry);
                    break;
                case COPY_DIRECTORY:
                    copyDirectory(recipeEntry);
                    break;
                case DELETE_FILE_FROM_TARGET:
                    deleteFileFromTarget(recipeEntry);
                    break;
                case RENAME_BEFORE:
                    renameBefore(recipeEntry);
                    break;
                case COPY_CONTENTS:
                    copyContents(recipeEntry);
                    break;
                case RENAME_AFTER:
                    renameAfter(recipeEntry);
                    break;
                case INSTALL:
                case UNZIP_ARCHIVE:
                    unzipArchive(recipeEntry);
                    break;
                case COPY_DIRECTORY_OR_INSTALL:
                    copyDirectoryOrInstall(recipeEntry);
                    break;
                case RENAME_SOURCE_AFTER:
                    renameSourceAfter();
                    break;
                case EDIT_FILE_IN_TARGET_REPLACE_LINE:
                    editFileInTargetReplaceLine(recipeEntry);
                    break;
                default:
                    log.error("Unknown operation for migration: {}", recipeEntry.getMigrationOperation());
            }
        }
    }

    private void editFileInTargetReplaceLine(RecipeEntryDto recipeEntry) throws Exception {
        log.info("Edit file {} in Target, by replacing the line that starts With '{}'.", recipeEntry.getParameter1(), recipeEntry.getParameter2());

        // we move file to file.bak
        File sourceFile = new File(targetDirectory, recipeEntry.getParameter1());
        File targetFile = new File(targetDirectory, recipeEntry.getParameter1()+".bak");

        FileUtils.moveFile(sourceFile, targetFile);

        // now we open .bak file
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(sourceFile));

        String line;

        while((line = br.readLine())!=null) {
            if (line.startsWith(recipeEntry.getParameter2())) {
                bw.write(recipeEntry.getParameter3());
                bw.newLine();
            } else {
                bw.write(line);
                bw.newLine();
            }
        }

        bw.flush();
        bw.close();
        br.close();

    }

    private void renameSourceAfter() throws Exception {
        log.info("Rename source directory {} to {}_migrated", sourceDirectory.getAbsoluteFile(), sourceDirectory.getAbsoluteFile());

        File sourceParent = sourceDirectory.getParentFile();
        String sourceDirName = sourceDirectory.getName();
        sourceDirName += "_migrated";

        FileUtils.moveDirectory(sourceDirectory, new File(sourceParent, sourceDirName));
    }

    private void copyContents(RecipeEntryDto recipeEntry) throws Exception {

        File sourceSubDir = new File(sourceDirectory, recipeEntry.getParameter1());
        File targetSubDir = new File(targetDirectory, recipeEntry.getParameter1());

        targetSubDir.mkdir();

        if (sourceSubDir.exists()) {
            for (File file : sourceSubDir.listFiles()) {
                FileUtils.copyFileToDirectory(file, targetSubDir, true);
            }
        } else {
            log.warn("Source sudirectory {} COULD not be found.", sourceSubDir);
        }
    }

    private void copyDirectoryOrInstall(RecipeEntryDto recipeEntry) throws Exception {
        log.info("copyDirectoryOrInstall: directory={},installFile={}", recipeEntry.getParameter1(), recipeEntry.getParameter2());
        File sourceDirectory = new File(this.sourceDirectory, recipeEntry.getParameter1());

        if (sourceDirectory.exists()) {
            log.info("copyDirectory: {}", recipeEntry.getParameter1());
            FileUtils.copyDirectory(sourceDirectory, new File(this.targetDirectory, recipeEntry.getParameter1()));
        } else {
            unzipArchive(recipeEntry.getParameter2());
        }
    }

    private void unzipArchive(RecipeEntryDto recipeEntry) throws Exception {
        unzipArchive(recipeEntry.getParameter1());
    }

    private void unzipArchive(String sourceArchive) throws Exception {
        log.info("Unzip resource file '{}' to target directory.", sourceArchive);

        InputStream resourceAsStream = InstallMigrationHandler.class.getResourceAsStream(sourceArchive);

        if (resourceAsStream==null) {
            log.error("File {} not found or couldn't be extracted.", sourceArchive);
            throw new RuntimeException(String.format("File %s not found in resource.", sourceArchive));
        }

        try {
            zipJava8Util.unzipFromStream(resourceAsStream, targetDirectory);
        } catch (Exception ex) {
            log.error("Error unzipping from Stream. Ex.: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    private void deleteFileFromTarget(RecipeEntryDto recipeEntry) {
        log.info("Delete file {} from target (it it exists there).", recipeEntry.getParameter1());

        File fileToDelete = new File(targetDirectory, recipeEntry.getParameter1());

        if (fileToDelete.exists()) {
            FileUtils.deleteQuietly(fileToDelete);
        }
    }

    private void copyFile(RecipeEntryDto recipeEntry) throws Exception {
        log.info("Copy file {} to target directory.", recipeEntry.getParameter1());
        FileUtils.copyFileToDirectory(new File(sourceDirectory, recipeEntry.getParameter1()), targetDirectory);
    }

    private void copyDirectory(RecipeEntryDto recipeEntry) throws Exception {
        log.info("Copy directory {} to target directory.", recipeEntry.getParameter1());
        FileUtils.copyDirectoryToDirectory(new File(sourceDirectory, recipeEntry.getParameter1()), targetDirectory);
    }

    private void renameBefore(RecipeEntryDto recipeEntry) throws Exception {
        log.info("Rename file {} to {} before migration.", recipeEntry.getParameter1(), recipeEntry.getParameter2());

        File sourceFile = new File(sourceDirectory, recipeEntry.getParameter1());

        if (sourceFile.exists()) {
            FileUtils.moveFile(sourceFile,
                    new File(sourceDirectory, recipeEntry.getParameter2()));
        }
    }

    private void renameAfter(RecipeEntryDto recipeEntry) throws Exception {
        log.info("Rename file {} to {} after migration.", recipeEntry.getParameter1(), recipeEntry.getParameter2());
        FileUtils.moveFile(new File(targetDirectory, recipeEntry.getParameter1()),
                new File(targetDirectory, recipeEntry.getParameter2()));
    }

    private void makeDirectory(RecipeEntryDto recipeEntry) throws Exception {
        log.info("Make directory {}.", recipeEntry.getParameter1());
        FileUtils.forceMkdir(new File(targetDirectory, recipeEntry.getParameter1()));
    }


}
