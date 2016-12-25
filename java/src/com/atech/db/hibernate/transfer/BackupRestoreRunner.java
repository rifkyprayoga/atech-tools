package com.atech.db.hibernate.transfer;

import java.util.Map;

import javax.swing.*;

import com.atech.graphics.components.tree.CheckNodeTree;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

// TODO: Auto-generated Javadoc
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

public abstract class BackupRestoreRunner extends Thread implements BackupRestoreWorkGiver
{

    /**
     * The i18nControl.
     */
    protected I18nControlAbstract ic = null;

    /**
     * The dataAccess.
     */
    protected ATDataAccessAbstract m_da = null;

    /*
     * Globaly used variables
     */

    /**
     * The label_date.
     */
    JLabel label_date;

    /**
     * The progressCurrent.
     */
    JProgressBar progress_full, progress_current;

    /**
     * The tree.
     */
    CheckNodeTree tree;

    /**
     * The m_error.
     */
    int m_error = 0;

    /**
     * The last action.
     */
    int lastAction = 0; // no event

    /**
     * The countOfBackupElements.
     */
    protected int count_of_backup_elements = 0;

    /**
     * The doneBackupElements.
     */
    protected int done_backup_elements = 0;

    /**
     * The is_backup.
     */
    boolean is_backup = false;

    /**
     * The backupObjects.
     */
    protected Map<String, BackupRestoreObject> backupObjects;

    /**
     * The restoreObjects.
     */
    Map<String, RestoreFileInfo> restoreObjects;

    /**
     * The workGiver.
     */
    protected BackupRestoreWorkGiver workGiver;


    /**
     * Instantiates a new backup restore runner.
     * 
     * @param objects the objects
     * @param workGiver the workGiver
     */
    public BackupRestoreRunner(Map<String, BackupRestoreObject> objects, BackupRestoreWorkGiver workGiver)
    {
        super();
        this.backupObjects = objects;
        this.workGiver = workGiver;
        is_backup = true;
    }


    /**
     * Instantiates a new backup restore runner.
     * 
     * @param objects the objects
     * @param workGiver the workGiver
     * @param special the special
     */
    public BackupRestoreRunner(Map<String, RestoreFileInfo> objects, BackupRestoreWorkGiver workGiver, String special)
    {
        super();
        // this.backupObjects = objects;
        this.restoreObjects = objects;
        this.workGiver = workGiver;
        is_backup = false;
    }


    /** 
     * setTask
     */
    public void setTask(String task)
    {
        this.workGiver.setTask(task);
    }


    /** 
     * setStatus
     */
    public void setStatus(int status)
    {
        this.workGiver.setStatus(status);
    }


    /**
     * Checks if is backup object selected.
     * 
     * @param key the key
     * 
     * @return true, if is backup object selected
     */
    public boolean isBackupObjectSelected(String key)
    {
        if (this.backupObjects == null)
            return false;

        if (this.backupObjects.containsKey(key))
            return this.backupObjects.get(key).isSelected();
        else
            return false;
    }


    /**
     * Checks if is restore object selected.
     * 
     * @param key the key
     * 
     * @return true, if is restore object selected
     */
    public boolean isRestoreObjectSelected(String key)
    {
        if (this.restoreObjects == null)
            return false;

        if (this.restoreObjects.containsKey(key))
            return this.restoreObjects.get(key).selected;
        else
            return false;
    }


    /**
     * Gets the restore object.
     * 
     * @param key the key
     * 
     * @return the restore object
     */
    public RestoreFileInfo getRestoreObject(String key)
    {
        if (this.restoreObjects == null)
            return null;

        if (this.restoreObjects.containsKey(key))
            return this.restoreObjects.get(key);
        else
            return null;
    }


    /** 
     * run
     */
    @Override
    public void run()
    {
        if (is_backup)
        {
            executeBackup();
        }
        else
        {
            executeRestore();
        }
    }


    /**
     * Execute backup.
     */
    public abstract void executeBackup();


    /**
     * Execute restore.
     */
    public abstract void executeRestore();


    /**
     * Get Last Backup File
     */
    public abstract String getLastBackupFile();

}
