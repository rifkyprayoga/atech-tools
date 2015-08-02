package com.atech.graphics.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.help.HelpCapable;
import com.atech.utils.ATDataAccessAbstract;

// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
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

/**
 * ActionExceptionCatchDialog is intended to be used with dialogs that perform
 * specific action. This is less restrictive form of ExceptionCatch dialogs.
 * You need to specify your own action and also handle them. Also creation of
 * dialog is up to user.
 * In case that action is not handled, we need to set base error message (with
 * something like 'Unknown exception occured...'. We have base message already
 * set but this is in english.
 * Set static instance of log in each Dialog that extends this one... Use log
 * messages frequently, especially in situations where exception can fall.
 *
 * @author andy
 */

public abstract class ActionExceptionCatchDialog extends JDialog implements ActionListener, HelpCapable
{

    private static final long serialVersionUID = -5125099590281275283L;
    // private static Log log =
    // LogFactory.getLog(ActionExceptionCatchDialog.class);
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(ActionExceptionCatchDialog.class);
    /**
     * The dataAccess.
     */
    ATDataAccessAbstract m_da;
    /**
     * The errorMessage.
     */
    String error_message = null;
    /**
     * The error_message_tip.
     */
    String error_message_tip = null;
    /**
     * The base_error_message.
     */
    String base_error_message = "Unknown exception occured. Please consider sending report to us, so that we can resolve problem";
    private int m_action_done = 0;
    private String m_action_id = "unknown";
    private String component_name;


    /**
     * Constructor
     *
     * @param da
     * @param component_name
     */
    public ActionExceptionCatchDialog(ATDataAccessAbstract da, String component_name)
    {
        this.m_da = da;
        this.m_da.addComponent(this);
        this.component_name = component_name;
    }


    public ActionExceptionCatchDialog(JFrame frame, int type, ATDataAccessAbstract dataAccess, String componentName)
    {
        super(frame);
        this.m_da = dataAccess;
        this.m_da.addComponent(this);
        this.component_name = componentName;
    }


    /**
     * When disposing dialog, we need to remove dialog from list so we override
     * original method.
     */
    @Override
    public void dispose()
    {
        m_da.removeComponent(this);
        super.dispose();
    }


    /**
     * setBaseErrorMessage - set base error message.
     *
     * @param msg
     */
    public void setBaseErrorMessage(String msg)
    {
        this.base_error_message = msg;
    }


    /**
     * Action handling... In this method we handle actions... Each action must
     * call following method. setActionId(). We can handle each exception
     * here, or we can catch exception call setErrorMessages, which will
     * set messages displayed to user and re-throw the exception.
     * 
     * @param e
     * @throws Exception
     */
    public abstract void performAction(ActionEvent e) throws Exception;


    /**
     * Get Action Id
     * 
     * @return
     */
    public String getActionId()
    {
        return this.m_action_id;
    }


    /**
     * Sets the action id.
     *
     * @param action_id
     *            the new action id
     */
    public void setActionId(String action_id)
    {
        this.m_action_id = action_id;
    }


    /**
     * Sets the error messages.
     *
     * @param err_msg
     *            the err_msg
     * @param err_msg_tip
     *            the err_msg_tip
     */
    public void setErrorMessages(String err_msg, String err_msg_tip)
    {
        this.error_message = err_msg;
        this.error_message_tip = err_msg_tip;
    }


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            performAction(e);
        }
        catch (Exception ex)
        {
            if (this.error_message != null)
            {
                m_da.createErrorDialog(this.component_name, this.getActionId(), ex, this.error_message,
                    this.error_message_tip);
            }
            else
            {
                m_da.createErrorDialog(this.component_name, this.getActionId(), ex, this.base_error_message,
                    this.error_message_tip);
            }
        }
    }


    /**
     * Gets info if action was performed.
     *
     * @return true if action was done, false if not.
     */
    public boolean wasAction()
    {
        return m_action_done == 0;
    }


    /**
     * Gets info if action was performed.
     *
     * @return true if action was done, false if not.
     */
    public int wasActionCode()
    {
        return m_action_done;
    }


    /**
     * Returns object of this dialog or null
     *
     * @return object of type of Object
     */
    public abstract Object getObject();

}
