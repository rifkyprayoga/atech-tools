package com.atech.app.data.transfer;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.atech.app.AbstractApplicationContext;
import com.atech.db.hibernate.transfer.BackupRestoreDialog;
import com.atech.db.hibernate.transfer.BackupRestoreRunner;
import com.atech.utils.ATDataAccessAbstract;

/**
 *  Application:   GGC - GNU Gluco Control
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
 *  Filename:     BackupDialog  
 *  Description:  Backup Dialog for GGC
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

public class BackupAppDialog extends BackupRestoreDialog
{

    private static final long serialVersionUID = -9066907986768713200L;
    AbstractApplicationContext m_aac;

    /**
     * Constructor
     * 
     * @param parent
     * @param da
     */
    public BackupAppDialog(JDialog parent, ATDataAccessAbstract da /*
                                                                    * ,
                                                                    * BackupRestoreCollection
                                                                    * br_coll
                                                                    */, AbstractApplicationContext aac)
    {
        super(parent, da, da.getBackupRestoreCollection());
        this.m_aac = aac;
        this.enableHelp(aac.getHelpKeyword("Tools_Backup", "Tools_Backup"));
        showDialog();
    }

    /**
     * Constructor
     * 
     * @param parent
     * @param da
     */
    public BackupAppDialog(JFrame parent, ATDataAccessAbstract da, AbstractApplicationContext aac /*
                                                                                                   * ,
                                                                                                   * BackupRestoreCollection
                                                                                                   * br_coll
                                                                                                   */)
    {
        super(parent, da, da.getBackupRestoreCollection());
        this.m_aac = aac;
        this.enableHelp(aac.getHelpKeyword("Tools_Backup", "Tools_Backup"));
        showDialog();
    }

    /** 
     * Perform Backup
     */
    @Override
    public void performBackup()
    {
        BackupRestoreRunner brr = this.m_aac.getBackupRestoreRunner(AbstractApplicationContext.DB_ACTION_BACKUP); // this.restore_files,
                                                                                                                  // this,
                                                                                                                  // this.m_aac.getSpecialRestoreParameter());
        brr.start();
    }

}
