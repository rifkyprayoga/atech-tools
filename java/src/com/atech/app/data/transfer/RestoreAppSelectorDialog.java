package com.atech.app.data.transfer;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.atech.app.AbstractApplicationContext;
import com.atech.db.hibernate.transfer.RestoreSelectorDialog;
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
 *  Filename:     RestoreGGCSelectorDialog
 *  Description:  Restore GGC SelectorDialog (forst dialog for restore, where you select
 *                files).
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

public class RestoreAppSelectorDialog extends RestoreSelectorDialog
{

    private static final long serialVersionUID = 3536165659702725457L;
    AbstractApplicationContext m_aac;

    /**
     * Constructor
     * 
     * @param parent
     * @param da
     * @param aac 
     */
    public RestoreAppSelectorDialog(JDialog parent, ATDataAccessAbstract da, AbstractApplicationContext aac)
    {
        super(parent, da);
        this.m_aac = aac;
        this.enableHelp(aac.getHelpKeyword("Tools_Restore_File_Selector", "Tools_Restore_File_Selector")); // "GGC_Tools_Restore_File_Selector");
    }

    /**
     * Constructor
     * 
     * @param parent
     * @param da
     * @param aac 
     */
    public RestoreAppSelectorDialog(JFrame parent, ATDataAccessAbstract da, AbstractApplicationContext aac)
    {
        super(parent, da);
        this.m_aac = aac;
        this.enableHelp(aac.getHelpKeyword("Tools_Restore_File_Selector", "Tools_Restore_File_Selector")); // "GGC_Tools_Restore_File_Selector");
    }

    /**
     * Get Browse Startup Directory
     * 
     * @see com.atech.db.hibernate.transfer.RestoreSelectorDialog#getBrowseStartupDirectory()
     */
    @Override
    public String getBrowseStartupDirectory()
    {
        return "../data/export/";
    }

    /**
     * Command Next Step
     * 
     * @see com.atech.db.hibernate.transfer.RestoreSelectorDialog#cmdNextStep()
     */
    @Override
    public void cmdNextStep()
    {
        // System.out.println("Res Coll: " +
        // this.m_da.getBackupRestoreCollection());
        RestoreAppDialog rgd = new RestoreAppDialog((JFrame) this.my_parent, this.m_da,
                this.m_da.getBackupRestoreCollection(), this.tf_file.getText(), this.m_aac);
        rgd.enableHelp(m_aac.getHelpKeyword("Tools_Restore", "Tools_Restore")); // "GGC_Tools_Restore_File_Selector");
        rgd.showDialog();
    }

    /*
     * public static void main(String args[])
     * {
     * JFrame fr = new JFrame();
     * fr.setSize(800,600);
     * RestoreGGCSelectorDialog rsd = new RestoreGGCSelectorDialog(new
     * JDialog(), DataAccess.getInstance());
     * rsd.showDialog();
     * }
     */

}
