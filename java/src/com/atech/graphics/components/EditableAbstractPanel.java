package com.atech.graphics.components;

import javax.swing.*;

import com.atech.i18n.I18nControlAbstract;

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

public abstract class EditableAbstractPanel extends JPanel implements EditablePanel
{

    private static final long serialVersionUID = -7274432500080261833L;

    /**
     * The Constant ACTION_ADD.
     */
    public static final int ACTION_ADD = 0;

    /**
     * The Constant ACTION_EDIT.
     */
    public static final int ACTION_EDIT = 1;

    /**
     * The is_editable_panel.
     */
    public boolean is_editable_panel = true;

    /**
     * The i18nControl.
     */
    protected I18nControlAbstract ic;

    /**
     * The options.
     */
    public static String[] options = null;


    /**
     * Instantiates a new editable abstract panel.
     * 
     * @param is_editable the is_editable
     * @param ic the i18nControl
     */
    public EditableAbstractPanel(boolean is_editable, I18nControlAbstract ic)
    {
        super();
        this.is_editable_panel = is_editable;
        this.ic = ic;

        if (EditableAbstractPanel.options == null)
        {
            EditableAbstractPanel.options = new String[2];
            EditableAbstractPanel.options[0] = ic.getMessage("YES");
            EditableAbstractPanel.options[1] = ic.getMessage("NO");
        }
    }

    /**
     * The action_type.
     */
    protected int action_type = ACTION_EDIT;


    /**
     * This method is used when we plan to do add action (in tree) to root element.
     * 
     * @param object input object parameter (from tree)
     */
    public abstract void setParentRoot(Object object);


    /**
     * This method is used when we plan to do add action (in tree), by supplying
     * the parent. 
     * 
     * @param object input object parameter (from tree)
     */
    public abstract void setParent(Object object);


    /**
     * This is planned for edit action (in tree), by supplying data entry itself
     * 
     * @param object input object parameter (from tree)
     */
    public abstract void setData(Object object);


    /**
     * Get Warning string. This method returns warning string for either add or edit.
     * If value returned is null, then no warning message box will be displayed.
     * 
     * @param action_type_ type of action (ACTION_ADD, ACTION_EDIT)
     * @return String value as warning string
     */
    public abstract String getWarningString(int action_type_);


    /**
     * Is Panel Editable. Not all panels need to be editable. If this is set to false
     * most of methods don't need to be implemented.
     * @return
     */
    public boolean isPanelEditable()
    {
        return this.is_editable_panel;
    }


    /**
     * Was data in this panel changed.
     * 
     * @return true if data changed, false otherwise
     */
    public abstract boolean hasDataChanged();


    /**
     * Type of action (this is either add or edit)
     *  
     * @return int displaying type of action (usually 0 = add, 1 = edit)
     */
    public int getTypeOfAction()
    {
        return this.action_type;
    }


    /** 
     * setTypeOfAction
     */
    public void setTypeOfAction(int action_type)
    {
        this.action_type = action_type;
    }


    /**
     * Returns true if action is add action
     * 
     * @return
     */
    public boolean isAddAction()
    {
        return this.action_type == EditableAbstractPanel.ACTION_ADD;
    }


    /**
     * Execute correct warning and action (saveData). This is main method, which calls
     * all other methods. It's implementation should be the same for all classes.
     * 
     * <pre>
     *     if (isAddAction())
     *         actionAddWarning();
     *     else
     *         actionEditWarning();
     *         
     *     saveData();
     *  </pre>
     * 
     */
    public void actionWarningAndExecute()
    {
        boolean save = false;

        if (isAddAction())
        {
            save = actionAddWarning();
        }
        else
        {
            save = actionEditWarning();
        }

        if (save)
        {
            saveData();
        }

    }


    /**
     * Warning if action type is add (this can be empty, depending on what we want user 
     * to see.
     * 
     * @return true if user has selected to do the action
     */
    public boolean actionAddWarning()
    {
        return getActionWarningStatus(EditableAbstractPanel.ACTION_ADD);
    }


    /**
     * Warning if action type is edit (this can be empty, depending on what we want user 
     * to see.
     * 
     * @return true if user has selected to do the action
     */
    public boolean actionEditWarning()
    {
        return getActionWarningStatus(EditableAbstractPanel.ACTION_EDIT);
    }


    private boolean getActionWarningStatus(int type)
    {
        String warn = getWarningString(type);

        if (warn != null)
        {
            int ret = JOptionPane.showOptionDialog(this, warn, ic.getMessage("QUESTION"), JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, EditableAbstractPanel.options, EditableAbstractPanel.options[0]);

            if (ret == JOptionPane.YES_OPTION)
                return true;
            else
                return false;
        }
        else
            return true;

    }


    /**
     * Save data in panel
     * 
     * @return true if save was successful
     */
    public abstract boolean saveData();

}
