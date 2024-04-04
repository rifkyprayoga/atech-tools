package com.atech.db.hibernate.transfer;

import java.util.Map;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/

// this one should be extended, we have several variables that need to be filled

public interface BackupRestoreObject extends BackupRestoreBase
{

    /**
     * Get Target Name
     * 
     * @see com.atech.db.hibernate.transfer.BackupRestoreBase#getTargetName()
     */
    String getTargetName();


    /**
     * Get Table Version - returns version of table
     * 
     * @return version information
     */
    int getTableVersion();


    /**
     * dbExport - returns export String, for current version 
     * 
     * @param table_version 
     * @return line that will be exported
     * @throws Exception if export for table is not supported
     */
    @Deprecated
    String dbExport(int table_version) throws Exception;


    /**
     * dbExport - returns export String, for current version 
     *
     * @return line that will be exported
     * @throws Exception if export for table is not supported
     */
    @Deprecated
    String dbExport() throws Exception;


    /**
     * dbExportHeader - header for export file
     * 
     * @param table_version
     * @return
     */
    @Deprecated
    String dbExportHeader(int table_version);


    /**
     * dbExportHeader - header for export file
     * 
     * @return
     */
    @Deprecated
    String dbExportHeader();


    /**
     * dbImport - processes input entry to right fields
     * 
     * @param tableVersion version of table
     * @param valueEntry whole import line
     * @throws Exception if import for selected table version is not supported or it fails
     * @deprecated should use dbImport(int tableVersion, String valueEntry, Map<String, String> headers) instead
     *    (if implemented). When implemented IsNewImport returns true
     */
    @Deprecated
    void dbImport(int tableVersion, String valueEntry) throws Exception;


    /**
     * dbImport - processes input entry to right fields
     * 
     * @param tableVersion version of table
     * @param valueEntry whole import line
     * @param parameters parameters
     * @throws Exception if import for selected table version is not supported or it fails
     * @deprecated should use dbImport(int tableVersion, String valueEntry, Map<String, String> headers) instead
     *    (if implemented). When implemented IsNewImport returns true
     */
    @Deprecated
    void dbImport(int tableVersion, String valueEntry, Object[] parameters) throws Exception;


    /**
     * dbImport - processes input entry to right fields
     *
     * @param tableVersion version of table
     * @param valueEntry whole import line
     * @param headers headers map
     * @throws Exception if import for selected table version is not supported or it fails
     */
    @Deprecated
    void dbImport(int tableVersion, String valueEntry, Map<String, String> headers) throws Exception;


    /**
     * Is New Import (with headers handling)
     * 
     * @return
     */
    @Deprecated
    boolean isNewImport();


    /**
     * getBackupFile - name of backup file (base part)
     * 
     * @return
     */
    String getBackupFile();


    /**
     * getBackupClassName - name of class which will be updated/restored
     * 
     * @return
     */
    String getBackupClassName();


    /**
     * Has To Be Clean - if table needs to be cleaned before import
     * 
     * @return true if we need to clean
     */
    @Deprecated
    boolean hasToBeCleaned();

}
