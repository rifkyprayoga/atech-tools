package com.atech.plugin;

import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.db.hibernate.transfer.BackupRestoreRunner;

/**
 *  Application:   GGC - GNU Gluco Control
 *  Plug-in:       GGC PlugIn Base (base class for all plugins)
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     PluginDb  
 *  Description:  This is master class for using Db instance within plug-in. In most cases, we 
 *                would want data to be handled by outside authority (GGC), but in some cases
 *                we wouldn't want that.
 * 
 *  Author: Andy {andy@atech-software.com}
 */


public interface ImportExportPlugin
{

    

  
    
    
    public abstract void doFullExport(String export_object, BackupRestoreRunner brr);
    

    public static final int IMPORT_ACTION_APPEND = 1;
    public static final int IMPORT_ACTION_OVERWRITE = 2;
    public static final int IMPORT_ACTION_CLEAR = 3;
    
    
    
    public abstract void doFullImport(String import_object, BackupRestoreRunner brr, int action);
    

    /**
     * Export only some data (usually this data will be selected through dialog in this
     * case we create hibernate sql where statement, which will be appended to export)
     * 
     * @param brr
     * @param hsql_where
     */
    public abstract void doPartitialExport(String export_object, BackupRestoreRunner brr, String hsql_where);
    
    
    /**
     * Do partitial import, will import files created by doPartitialExport, enforcing this 
     * rules:
     * - if entry exists, overwrite values
     * - else import data 
     * It's action APPEND, OVERWRITE together
     * 
     * 
     * @param brr
     */
    public abstract void doPartitialImport(String import_object, BackupRestoreRunner brr);
    
    
    

    
    /**
     * Get Backup Restore Object
     * 
     * @param class_name
     * @return
     */
    public abstract BackupRestoreObject getBackupRestoreObject(String class_name);
    
    
    /**
     * Get Backup Restore Object
     * 
     * @param obj
     * @param bro
     * @return
     */
    public abstract BackupRestoreObject getBackupRestoreObject(Object obj, BackupRestoreObject bro);
    

    /**
     * Does Contain Backup Restore Object
     * 
     * @param bro_name
     * @return
     */
    public abstract boolean doesContainBackupRestoreObject(String bro_name);
    
    
    
}