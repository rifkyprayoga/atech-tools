package com.atech.data.user_data_dir;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.data.FileDirectoryDto;
import com.atech.utils.file.zip.util.Zip4jUtil;

/**
 * Created by andy on 18/12/17.
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
public class UserDataDirectoryExtractorValidator
{

    private static final Logger LOG = LoggerFactory.getLogger(UserDataDirectoryExtractorValidator.class);

    protected String basePath;
    Zip4jUtil zip4jUtil = new Zip4jUtil();
    UserDataDirectoryContext userDataDirectoryContext;


    public UserDataDirectoryExtractorValidator(String path, UserDataDirectoryContext userDataDirectoryContext)
    {
        this.basePath = path;
    }


    public void validateAndRecreate()
    {
        if (!validateAllFilesPresent())
        {
            recreateFileStructure();
        }

        validateAllDirectoriesPresent();

    }




//    protected void validateDirectoriesPresent(String... directories)
//    {
//        for (String dir : directories)
//        {
//            File f = new File(basePath + "/" + dir);
//
//            if (!f.exists())
//            {
//                f.mkdirs();
//            }
//        }
//    }


    private void validateDirectoriesPresent(List<FileDirectoryDto> directories)
    {
        for (FileDirectoryDto dir : directories)
        {
            File f = new File(basePath + "/" + dir.getFileName());

            if (!f.exists())
            {
                f.mkdirs();
            }
        }
    }


//    protected boolean validateFilesPresent(String... files)
//    {
//        for (String file : files)
//        {
//            if (!isFilePresent(file))
//                return false;
//        }
//
//        return true;
//    }


    private boolean isFilePresent(String file)
    {
        File f = new File(basePath + "/" + file);

        return f.exists();
    }


    private void unpackDataDirectoryTemplate(String fileName)
    {
        zip4jUtil.unzipFileWithoutException(fileName, basePath, null);
    }



    private void validateAllDirectoriesPresent()
    {
        validateDirectoriesPresent(userDataDirectoryContext.getAllDirectoriesToCheck());
    }


    private boolean validateAllFilesPresent()
    {

        return (validateFilesPresent( userDataDirectoryContext.getAllFileToCheck()));
    }

    private boolean validateFilesPresent(List<FileDirectoryDto> allFileToCheck) {

        for (FileDirectoryDto file : allFileToCheck)
        {
            if (file.isCheckNeeded() && !isFilePresent(file.getFileName()))
                return false;
        }

        return true;
    }


    private void recreateFileStructure()
    {
        for (FileDirectoryDto file : userDataDirectoryContext.getAllFileToCheck()) {
            if (isFilePresent(file.getFileName()))
            {
                unpackDataDirectoryTemplate(file.getTemplate());
            }
        }
    }

}
