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
