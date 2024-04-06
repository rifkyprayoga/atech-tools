package com.atech.data.user_data_dir.v2;

import com.atech.data.FileDirectoryDto;

import java.util.List;

/**
 * Created by andy on 27/01/18.
 */
public interface UserDataDirectoryContextV2 {

    String getApplicationName();

    String getApplicationDataDirectoryName();

    String getFallbackDirectory();

    List<FileDirectoryDto> getAllDirectoriesToCheck();

    List<FileDirectoryDto> getAllFileToCheck();
}