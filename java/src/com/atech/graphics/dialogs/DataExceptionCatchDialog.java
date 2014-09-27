package com.atech.graphics.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

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

public abstract class DataExceptionCatchDialog extends JDialog implements ActionListener
{

    private static final long serialVersionUID = -5749317262612446570L;
    private int m_action_done = 0;
    private int m_error = 0;

    /**
     * The m_da.
     */
    ATDataAccessAbstract m_da;

    /**
     * Instantiates a new data exception catch dialog.
     */
    public DataExceptionCatchDialog()
    {
        m_da.addComponent(this);
    }

    /**
     * When disposing dialog, we need to remove dialog from list so we override
     * original method. 
     */
    @Override
    public void dispose()
    {
        m_da.removeComponent(this);
    }

    /**
     * Inits the dialog.
     */
    public abstract void initDialog();

    /**
     * Load data.
     * 
     * @return true, if successful
     */
    public abstract boolean loadData();

    /**
     * Save data.
     * 
     * @return true, if successful
     */
    public abstract boolean saveData();

    /**
     * Show title.
     */
    public abstract void showTitle();

    /**
     * Part of action that is done, outside of normal action
     * @param e 
     */
    public abstract void performAction(ActionEvent e);

    /**
     * Gets the error.
     * 
     * @return the error
     */
    public int getError()
    {
        return m_error;
    }

    /**
     * Sets the error.
     * 
     * @param error_code the new error
     */
    public void setError(int error_code)
    {
        this.m_error = error_code;
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        try
        {

            String action = e.getActionCommand();

            if (action.equals("ok"))
            {

                if (saveData())
                {
                    m_action_done = 1;
                    this.dispose();
                }

                if (getError() == 0)
                {
                    m_action_done = 1;
                    this.dispose();
                }

            }
            else if (action.equals("cancel"))
            {
                m_action_done = 0;
                this.dispose();
            }

            performAction(e);

        }
        catch (Exception ex)
        {

        }
    }

    /**
     *  Gets info if action was performed.
     * 
     *  @return true if action was done, false if not.
     */
    public boolean wasAction()
    {
        return m_action_done == 0;
    }

    /**
     *  Gets info if action was performed.
     * 
     *  @return true if action was done, false if not.
     */
    public int wasActionCode()
    {
        return m_action_done;
    }

    /**
     *  Returns object of this dialog or null
     * 
     *  @return object of type of Object
     */
    public abstract Object getObject();

}
