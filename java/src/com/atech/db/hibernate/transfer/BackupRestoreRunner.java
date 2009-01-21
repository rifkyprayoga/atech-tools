package com.atech.db.hibernate.transfer;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.atech.graphics.components.tree.CheckNodeTree;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;


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

    
    protected I18nControlAbstract ic = null;
    protected ATDataAccessAbstract m_da = null;


    /*
     *  Globaly used variables
     */

    JLabel label_date;

    JProgressBar progress_full, progress_current;
    
    CheckNodeTree tree;
    
    int m_error = 0;
    int lastAction = 0;  // no event

    protected int count_of_backup_elements = 0;
    protected int done_backup_elements = 0;
    
    boolean is_backup = false;
    
    

    protected Hashtable<String,BackupRestoreObject> ht_backup_objects;
    Hashtable<String,RestoreFileInfo> ht_restore_objects;
    protected BackupRestoreWorkGiver work_giver;
    
    public BackupRestoreRunner(Hashtable<String,BackupRestoreObject> objects, BackupRestoreWorkGiver work_giver)
    {
        super();
        this.ht_backup_objects = objects;
        this.work_giver = work_giver;
        is_backup = true;
    }

    
    public BackupRestoreRunner(Hashtable<String,RestoreFileInfo> objects, BackupRestoreWorkGiver work_giver, String special)
    {
        super();
        //this.ht_backup_objects = objects;
        this.ht_restore_objects = objects;
        this.work_giver = work_giver;
        is_backup = false;
    }
    
    
    
    public void setTask(String task)
    {
        this.work_giver.setTask(task);
    }
    
    
    public void setStatus(int status)
    {
        this.work_giver.setStatus(status);
    }


    
    public boolean isBackupObjectSelected(String key)
    {
        if (this.ht_backup_objects==null)
            return false;
        
        if (this.ht_backup_objects.containsKey(key))
            return this.ht_backup_objects.get(key).isSelected();
        else
            return false;
    }
    
    
    public boolean isRestoreObjectSelected(String key)
    {
        if (this.ht_restore_objects==null)
            return false;
        
        if (this.ht_restore_objects.containsKey(key))
        {
            return this.ht_restore_objects.get(key).selected;
        }
        else
            return false;
    }
    
    
    public RestoreFileInfo getRestoreObject(String key)
    {
        if (this.ht_restore_objects==null)
            return null;
        
        if (this.ht_restore_objects.containsKey(key))
            return this.ht_restore_objects.get(key);
        else
            return null;
    }
    
    
    public void run()
    {
        if (is_backup)
            executeBackup();
        else
            executeRestore();
    }
    
    
    public abstract void executeBackup();
    
    public abstract void executeRestore();



}


