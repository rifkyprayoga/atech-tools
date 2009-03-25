package com.atech.plugin;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JOptionPane;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.graphics.components.StatusReporterInterface;
import com.atech.graphics.dialogs.TransferDialog;
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


/*
 * This is abstract class which needs to be implemented in application to which we are adding some
 * plug-in functionality. This clinet in will at later time look for server component and exceute
 * action. If server is not found it needs to display something to user.
 */

public abstract class PlugInServer 
{

    protected boolean installed = false;
    protected I18nControlAbstract ic = null;
    protected Container parent = null;
    protected String selected_lang = "en";
    protected ATDataAccessAbstract m_da;
    PlugInClient client;
    HibernateDb db;
    
    /**
     * Constructor
     */
    public PlugInServer()
    {
    }

    
    /**
     * Constructor
     * 
     * @param cont
     * @param selected_lang
     * @param da
     * @param db
     */
    public PlugInServer(Container cont, String selected_lang, ATDataAccessAbstract da, HibernateDb db)
    {
        init(cont, selected_lang, da, null, db);
    }
    
    
    /**
     * Constructor
     * 
     * @param cont
     * @param selected_lang
     * @param da
     */
    public PlugInServer(Container cont, String selected_lang, ATDataAccessAbstract da)
    {
        init(cont, selected_lang, da, null, null);
    }
    
    
    /**
     * Init PlugIn
     * 
     * @param cont
     * @param selected_lang_in
     * @param da
     * @param client_in
     * @param db_in
     */
    public void init(Container cont, String selected_lang_in, ATDataAccessAbstract da, PlugInClient client_in, HibernateDb db_in)
    {
        this.parent = cont;
        this.selected_lang = selected_lang_in;
        this.m_da = da;
        this.client = client_in;
        this.db = db_in;
        initPlugIn();
    }
    
    
    /**
     * Init PlugIn which needs to be implemented 
     */
    public abstract void initPlugIn();
    
    
    /**
     * Get Name of plugin
     * 
     * @return
     */
    public abstract String getName();
    
    /**
     * Get Version of plugin
     * 
     * @return
     */
    public abstract String getVersion();

    /**
     * Execute Command on Server Side
     * 
     * @param command
     */
    public void executeCommand(int command)
    {
        this.executeCommand(command, null);
    }
    
    /**
     * Execute Command on Server Side, with object parameter
     * 
     * @param command
     * @param data
     */
    public abstract void executeCommand(int command, Object data);
    
    
    /**
     * Feature not implemented message
     * @param command_desc
     */
    public void featureNotImplemented(String command_desc)
    {
        String text = String.format(ic.getMessage("PLUGIN_NOT_INSTALLED_OR_AVAILABLE"), this.getName());
            
        text += "\n\n'" + ic.getMessage(command_desc) +"' ";
        text += String.format(ic.getMessage("IMPLEMENTED_VERSION"), this.getWhenWillBeImplemented());
        text += "!\n\n";
    
        JOptionPane.showMessageDialog(this.parent, text, ic.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE);

    }

    
    /**
     * Get Information When will it be implemented
     * 
     * @return
     */
    public abstract String getWhenWillBeImplemented();
    

    /**
     * Set Return Data
     * 
     * @param data
     * @param stat_rep_int
     */
    public void setReturnData(Object data, StatusReporterInterface stat_rep_int)
    {
        if (this.client!=null)
            this.client.setReturnData(data, stat_rep_int);
        else
            System.out.println("There is not instance of PlugInClient to return data to !");
    }
    
    /**
     * Get Parent
     * 
     * @return
     */
    public Container getParent()
    {
        return this.parent;
    }
    
    

    /**
     * Get Transfer objects for communicating with certain classes in main application
     * @return
     */
    public ArrayList<TransferDialog> getTransferObjects()
    {
        if (this.client==null)
            return null;
        else
            return this.client.getTransferObjects();
    }
    
    
    /**
     * Get Return Object
     * 
     * @param ret_obj_id
     * @return
     */
    public abstract Object getReturnObject(int ret_obj_id);
    
    
    /**
     * Get PlugIn Main Menu 
     * 
     * This is new way to handle everything, previously we used to pass ActionListener items through
     * plugin framework, but in new way, we will use this one.
     *  
     * @return
     */
    public abstract JMenu getPlugInMainMenu();
    
    
    /**
     * Get PlugIn Print Menu 
     * 
     * Since printing is also PlugIn specific we need to add Printing jobs to application.
     *  
     * @return
     */
    public abstract JMenu getPlugInPrintMenu();
    
    
    
    
    /**
     * Get Backup Objects (if available)
     * 
     * @return
     */
    public abstract BackupRestoreCollection getBackupObjects();
    
    
}
