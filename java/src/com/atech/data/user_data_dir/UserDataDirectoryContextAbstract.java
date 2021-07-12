package com.atech.data.user_data_dir;

import java.util.List;

import com.atech.data.FileDirectoryDto;

/**
 * Created by andy on 27/01/18.
 */
public abstract class UserDataDirectoryContextAbstract implements UserDataDirectoryContext {

    //UserDataDirectory userDataDirectory;
    //String userDataDirectoryFolder;
    protected List<FileDirectoryDto> directoriesToCheck;
    protected List<FileDirectoryDto> filesToCheck;

    public UserDataDirectoryContextAbstract()
    {
        initData();
    }
//    {
//        this.userDataDirectory = userDataDirectory;
//        this.userDataDirectoryFolder = userDataDirectory.getUserDataDirectory();
//
//    }


//    public UserDataDirectoryContextAbstract(UserDataDirectory userDataDirectory)
//    {
//        this.userDataDirectory = userDataDirectory;
//        this.userDataDirectoryFolder = userDataDirectory.getUserDataDirectory();
//        initData();
//    }

    protected abstract void initData();
    //{


//    }

    @Override
    public List<FileDirectoryDto> getAllDirectoriesToCheck() {
        return this.directoriesToCheck;
    }

    @Override
    public List<FileDirectoryDto> getAllFileToCheck()
    {
        return this.filesToCheck;
    }


//    protected void checkAndRecreateFile(String file, String templateFile, boolean force)
//    {
//        if (!isFilePresent(file) || force)
//        {
//            unpackDataDirectoryTemplate(templateFile);
//        }
//    }
//
//
//    protected boolean isFilePresent(String file)
//    {
//        File f = new File(userDataDirectoryFolder + "/" + file);
//
//        return f.exists();
//    }

} 