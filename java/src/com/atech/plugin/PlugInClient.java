package com.atech.plugin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.graphics.components.StatusReporterInterface;
import com.atech.graphics.dialogs.TransferDialog;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessLMAbstract;

/**
 * This file is part of ATech Tools library.
 * PlugInClient - Abstract class which needs to be implemented by application
 * using certain plugins.
 * Copyright (C) 2008 Andy (Aleksander) Rozman (Atech-Software)
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * For additional information about this project please visit our project site
 * on
 * http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 */

/*
 * This is abstract class which needs to be implemented in application to which
 * we are adding some
 * plug-in functionality. This clinet in will at later time look for server
 * component and execute
 * action. If server is not found it needs to display something to user.
 */

public abstract class PlugInClient implements ActionListener
{

    protected boolean installed = false;
    protected I18nControlAbstract ic = null;
    protected Container parent = null;
    protected PlugInServer m_server;

    protected String commands[] = null;
    protected boolean commands_implemented[] = null;
    protected String commands_will_be_done[] = null;

    protected Object return_data;
    protected HibernateDb db;
    protected ArrayList<TransferDialog> transfer_objects = null;

    protected ATDataAccessLMAbstract da_parent = null;
    private static final Log LOG = LogFactory.getLog(PlugInClient.class);


    /**
     * Constructor
     * 
     * @param parent
     * @param ic
     * @param db
     */
    public PlugInClient(Container parent, I18nControlAbstract ic, HibernateDb db)
    {
        this.parent = parent;
        this.ic = ic;
        this.db = db;
        checkIfInstalled();
        initPlugin();
    }


    /**
     * Constructor
     * 
     * @param parent
     * @param ic
     */
    public PlugInClient(Container parent, I18nControlAbstract ic)
    {
        this.parent = parent;
        this.ic = ic;
        checkIfInstalled();
        initPlugin();
    }


    /**
     * Constructor
     * 
     * @param parent
     * @param da
     */
    public PlugInClient(Container parent, ATDataAccessLMAbstract da)
    {
        this.parent = parent;
        this.da_parent = da;

        // System.out.println("da_parent: " + this.da_parent);
        // System.out.println("da_parent (LM): " +
        // this.da_parent.getLanguageManager());

        this.ic = da.getI18nControlInstance();
        checkIfInstalled();
        initPlugin();
    }


    /**
     * Constructor
     */
    public PlugInClient()
    {
        initPlugin();
    }


    /**
     * Init Plugin
     */
    public abstract void initPlugin();


    /**
     * Check If Installed
     */
    public abstract void checkIfInstalled();


    /**
     * Get Name of plugin
     * 
     * @return
     */
    public String getName()
    {
        return ic.getMessage(getNameBase());
    }


    /**
     * Get Name of plugin
     * 
     * @return
     */
    public abstract String getNameBase();


    /**
     * Is PlugIn Installed
     * 
     * @return true if plugin installed
     */
    public boolean isPlugInInstalled()
    {
        return m_server != null;
    }


    /**
     * Is Command Implemented
     * 
     * @param command
     * @return
     */
    public boolean isCommandImplemented(int command)
    {
        if (this.commands_implemented == null)
            return true;
        else
            return this.commands_implemented[command];
    }


    /**
    * Execute Command
    *
    * @param command
    */
    public void executeCommand(int command)
    {
        executeCommand(command, null);
    }


    /**
    * Execute Command
    *
    * @param command
    * @param command_object
    */
    public void executeCommand(int command, Object command_object)
    {
        if (m_server == null)
        {
            if (this.isCommandImplemented(command))
            {
                this.showMessage(String.format(ic.getMessage("PLUGIN_NOT_INSTALLED"), this.getName()));
            }
            else
            {
                this.featureNotImplemented(commands[command]);
            }
        }
        else
        {
            if (this.isCommandImplemented(command))
            {
                m_server.executeCommand(command);
            }
            else
            {

                // System.out.println("array commands_will_be_done: " +
                // this.commands_will_be_done);
                // System.out.println("array commands_will_be_done[command]: " +
                // this.commands_will_be_done[command]);

                if (this.commands_will_be_done == null || this.commands_will_be_done[command] == null)
                {
                    this.featureNotImplementedInstalled(commands[command]);
                }
                else
                {
                    this.featureNotImplementedInstalled(commands[command], this.commands_will_be_done[command]);
                }

            }
        }
    }


    /**
     * Execute Command
     * 
     * @param _parent
     * @param command
     */
    public void executeCommandDialog(JDialog _parent, int command)
    {
        executeCommandDialog(_parent, command, null);
    }


    /**
     * Execute Command with Dialog as Parent
     * 
     * @param _parent
     * @param command
     * @param command_object
     * @return
     */
    public boolean executeCommandDialog(JDialog _parent, int command, Object command_object)
    {
        if (m_server == null)
        {
            if (this.isCommandImplemented(command))
            {
                this.showMessage(String.format(ic.getMessage("PLUGIN_NOT_INSTALLED"), this.getName()));
            }
            else
            {
                this.featureNotImplemented(commands[command]);
            }
        }
        else
        {
            if (this.isCommandImplemented(command))
                return m_server.executeCommandDialog(_parent, command, command_object);
            else
            {

                // System.out.println("array commands_will_be_done: " +
                // this.commands_will_be_done);
                // System.out.println("array commands_will_be_done[command]: " +
                // this.commands_will_be_done[command]);

                if (this.commands_will_be_done == null || this.commands_will_be_done[command] == null)
                {
                    this.featureNotImplementedInstalled(commands[command]);
                }
                else
                {
                    this.featureNotImplementedInstalled(commands[command], this.commands_will_be_done[command]);
                }

            }
        }
        return false;
    }


    /**
     * Execute Command with Dialog as Parent
     * 
     * @param _parent
     * @param command
     * @param command_object
     * @return
     */
    public Object[] executeCommandDialogReturn(JDialog _parent, int command, Object command_object)
    {
        if (m_server == null)
        {
            if (this.isCommandImplemented(command))
            {
                this.showMessage(String.format(ic.getMessage("PLUGIN_NOT_INSTALLED"), this.getName()));
            }
            else
            {
                this.featureNotImplemented(commands[command]);
            }
        }
        else
        {
            if (this.isCommandImplemented(command))
                return m_server.executeCommandDialogReturn(_parent, command, command_object);
            else
            {

                // System.out.println("array commands_will_be_done: " +
                // this.commands_will_be_done);
                // System.out.println("array commands_will_be_done[command]: " +
                // this.commands_will_be_done[command]);

                if (this.commands_will_be_done == null || this.commands_will_be_done[command] == null)
                {
                    this.featureNotImplementedInstalled(commands[command]);
                }
                else
                {
                    this.featureNotImplementedInstalled(commands[command], this.commands_will_be_done[command]);
                }

            }
        }
        return null;
    }


    /**
     * Action Performed
     */
    public abstract void actionPerformed(ActionEvent e);


    /**
     * Feature Not Implemented display message
     * 
     * @param commandDesc
     */
    public void featureNotImplemented(String commandDesc)
    {
        String text = String.format(ic.getMessage("PLUGIN_NOT_INSTALLED_OR_AVAILABLE"), this.getName());

        text += "\n\n'" + ic.getMessage(commandDesc) + "' ";
        text += String.format(ic.getMessage("IMPLEMENTED_VERSION"), this.getWhenWillBeImplemented());
        text += "!\n\n";

        // JOptionPane.showMessageDialog(this.parent, text,
        // i18nControl.getMessage("INFORMATION"),
        // JOptionPane.INFORMATION_MESSAGE);
        LOG.debug("Feature not implemented: " + commandDesc);

        showMessage(text);
    }


    /**
     * Feature Not Implemented and will be display message
     * 
     * @param commandDesc
     */
    public void featureNotImplementedInstalled(String commandDesc)
    {
        String text = "";

        text += "\n'" + ic.getMessage(commandDesc) + "' ";
        text += String.format(ic.getMessage("IMPLEMENTED_VERSION"), this.getWhenWillBeImplemented());
        text += "!\n\n";

        LOG.debug("Feature not implemented or Installed: " + commandDesc);
        // JOptionPane.showMessageDialog(this.parent, text,
        // i18nControl.getMessage("INFORMATION"),
        // JOptionPane.INFORMATION_MESSAGE);

        showMessage(text);
    }


    /**
     * Feature Not Implemented and will be display message
     * 
     * @param commandDesc
     * @param ver
     */
    public void featureNotImplementedInstalled(String commandDesc, String ver)
    {
        String text = "\n"; // String.format(i18nControl.getMessage("PLUGIN_NOT_INSTALLED_OR_AVAILABLE"),
                            // this.getName());

        text += "'" + ic.getMessage(commandDesc) + "' ";
        text += String.format(ic.getMessage("IMPLEMENTED_VERSION"), ver);
        text += "!\n\n";

        // JOptionPane.showMessageDialog(this.parent, text,
        // i18nControl.getMessage("INFORMATION"),
        // JOptionPane.INFORMATION_MESSAGE);

        LOG.debug("Feature not implemented or Installed: " + commandDesc);

        showMessage(text);
    }


    /**
     * Show Message
     * 
     * @param text
     */
    public void showMessage(String text)
    {
        JOptionPane.showMessageDialog(this.parent, text, ic.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Set Transfer Object (for passing elements to plugin)
     * 
     * @param lst
     */
    public void setTransferObjects(ArrayList<TransferDialog> lst)
    {
        this.transfer_objects = lst;
    }


    /**
     * Get Transfer Object (for getting elements to plugin)
     * 
     * @return
     */
    public ArrayList<TransferDialog> getTransferObjects()
    {
        return this.transfer_objects;
    }


    /**
     * Get When Will Be Implemented
     * 
     * @return
     */
    public abstract String getWhenWillBeImplemented();


    /**
     * Get Short Status
     * 
     * @return
     */
    public abstract String getShortStatus();


    /**
     * Set Return Data (for getting data from plugin - async)
     * 
     * @param return_data
     * @param stat_rep_int
     */
    public abstract void setReturnData(Object return_data, StatusReporterInterface stat_rep_int);


    /**
     * Get Return Object (getting data from plugin - synch)
     * 
     * @param ret_obj_id
     * @return
     */
    public Object getReturnObject(int ret_obj_id)
    {
        if (this.m_server == null)
            return null;
        else
            return this.m_server.getReturnObject(ret_obj_id);
    }


    /**
     * Get Return Object (getting data from plugin - synch)
     * 
     * @param ret_obj_id
     * @param parameters
     * @return
     */
    public Object getReturnObject(int ret_obj_id, Object[] parameters)
    {
        if (this.m_server == null)
            return null;
        else
            return this.m_server.getReturnObject(ret_obj_id, parameters);
    }


    /**
     * Get PlugIn Main Menu
     * This is new way to handle everything, previously we used to pass
     * ActionListener items through
     * plugin framework, but in new way, we will use this one.
     * 
     * @return
     */
    public JMenu getPlugInMainMenu()
    {
        if (m_server == null)
            return null;
        else
            return m_server.getPlugInMainMenu();
    }


    /**
     * Get PlugIn Print Menu
     * Since printing is also PlugIn specific we need to add Printing jobs to
     * application.
     * 
     * @return
     */
    public JMenu[] getPlugInReportMenus()
    {
        if (m_server == null)
            return null;
        else
            return m_server.getPlugInReportMenus();
    }


    /**
     * Get PlugIn Print Menu
     * Since printing is also PlugIn specific we need to add Printing jobs to
     * application.
     *
     * @return
     */
    public JMenu[] getPlugInGraphMenus()
    {
        if (m_server == null)
            return null;
        else
            return m_server.getPlugInGraphMenus();
    }


    /**
     * Is Backup Restore Enabled
     * 
     * @return
     */
    public boolean isBackupRestoreEnabled()
    {
        if (m_server == null)
            return false;
        else
            return m_server.isBackupRestoreEnabled();
    }


    /**
     * Get Backup Restore Handler
     * 
     * @return
     */
    public BackupRestorePlugin getBackupRestoreHandler()
    {
        if (m_server == null)
            return null;
        else
            return m_server.getBackupRestoreHandler();
    }


    /**
     * Get Backup Objects (if available)
     * 
     * @return
     */
    public BackupRestoreCollection getBackupObjects()
    {
        if (m_server == null)
            return null;
        else
            return m_server.getBackupObjects();
    }


    /**
     * Checks if plug-in is active. Plugin is active, when client side is
     * connected to server side and server side was started.
     *
     * @return true if active, false otherwise
     */
    public boolean isActive()
    {
        return (m_server != null && m_server.isPlugInServerStarted());
    }


    /**
     * Checks if plug-in is active. Plugin is active, when client side is
     * connected to server side
     * of it.
     * 
     * @param warn
     * @param dialog
     * @return
     */
    public boolean isActiveWarning(boolean warn, JDialog dialog)
    {
        boolean act = isActive();

        if (!act && warn)
        {
            JOptionPane.showMessageDialog(dialog, String.format(ic.getMessage("PLUGIN_NOT_ACTIVE"), getNameBase()),
                ic.getMessage("WARNING"), JOptionPane.WARNING_MESSAGE);
        }

        return act;

    }


    /**
     * This is method which can be used by server side to do certain action.
     * Mainly this will be used
     * to run refreshes and such actions. This needs to be implemented by Client
     * side, if you wish to use
     * it.
     * 
     * @param action_type
     */
    public void executeReturnAction(int action_type)
    {
    }


    /**
     * This method can be used to transfer data from plugin to other part of application. 
     * Parameters are HashMap<String,Object> and can contain almost anything that is 
     * visible in whole application, but mostly it is intended to be used with JDK java
     * objects. Return is List<Object>, which can also contain anything. If plugin
     * supports this, method should just be overriden in server implementation.
     * 
     * @param parameters
     * @return
     */
    public List<Object> getDataFromPlugin(Map<String, Object> parameters)
    {
        if (m_server == null)
            return null;
        else
            return m_server.getDataFromPlugin(parameters);
    }

}
