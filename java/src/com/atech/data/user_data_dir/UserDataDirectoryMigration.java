package com.atech.data.user_data_dir;

import java.io.File;

/**
 * Created by andy on 22/01/18.
 */
public class UserDataDirectoryMigration
{

    // if old data directory exists:
    // - it needs to be migrated
    // - file Migration.txt put in folder data/update to prevent this next time


    //


    public UserDataDirectoryMigration(UserDataDirectory userDataDirectory)
    {

    }


    public void process()
    {
        if (fileExists("../data"))
        {

        }
    }


    private boolean fileExists(String fileName)
    {
        return (new File(fileName)).exists();
    }



    // After migration is run:
    // - call ExtractorValidator, which check that each required file is there, and recreate if not
    // - file Validation.txt put in folder data/update to prevent validation next time

}
