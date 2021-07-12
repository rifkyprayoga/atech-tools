package com.atech.data.user_data_dir;

import java.util.List;

import com.atech.data.FileDirectoryDto;

/**
 * Created by andy on 27/01/18.
 */
public interface UserDataDirectoryContext {


    List<FileDirectoryDto> getAllDirectoriesToCheck();

    List<FileDirectoryDto> getAllFileToCheck();
}