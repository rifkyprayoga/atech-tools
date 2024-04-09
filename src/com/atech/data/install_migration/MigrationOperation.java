package com.atech.data.install_migration;

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
public enum MigrationOperation {
    COPY_FILE,
    COPY_DIRECTORY,
    DELETE_FILE_FROM_TARGET,
    RENAME_BEFORE, // rename is done in source directory
    COPY_CONTENTS,
    UNZIP_ARCHIVE,
    INSTALL,
    COPY_DIRECTORY_OR_INSTALL,
    RENAME_AFTER, // rename is done in target directory
    RENAME_SOURCE_AFTER, // rename source directory after work is done, so that its never migrated again
    EDIT_FILE_IN_TARGET_REPLACE_LINE,

    ;
}







