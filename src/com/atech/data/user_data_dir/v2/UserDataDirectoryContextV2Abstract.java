package com.atech.data.user_data_dir.v2;

import com.atech.data.FileDirectoryDto;
import com.atech.data.user_data_dir.UserDataDirectoryContext;

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
public abstract class UserDataDirectoryContextV2Abstract implements UserDataDirectoryContextV2 {

    protected List<FileDirectoryDto> directoriesToCheck;
    protected List<FileDirectoryDto> filesToCheck;

    public UserDataDirectoryContextV2Abstract() {
        initData();
    }

    protected abstract void initData();

    @Override
    public List<FileDirectoryDto> getAllDirectoriesToCheck() {
        return this.directoriesToCheck;
    }

    @Override
    public List<FileDirectoryDto> getAllFilesToCheck() {
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