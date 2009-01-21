package com.atech.graphics.components;

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


public interface EditablePanel
{

	/**
	 * Is Panel Editable. Not all panels need to be editable. If this is set to false
	 * most of methods don't need to be implemented.
	 * @return
	 */
	public boolean isPanelEditable();
	
	
	/**
	 * Was data in this panel changed.
	 * 
	 * @return true if data changed, false otherwise
	 */
    public boolean hasDataChanged();
    
    /**
     * Get type of action
     *  
     * @return int displaying type of action (usually 0 = add, 1 = edit)
     */
    public int getTypeOfAction();
    

    /**
     * Set type of action
     * 
     * @param action_type displaying type of action (usually 0 = add, 1 = edit)
     */
    public void setTypeOfAction(int action_type);
    
    
    
    /**
     * Returns true if action is add action
     * 
     * @return
     */
    public boolean isAddAction();
    
    
    
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
    public void actionWarningAndExecute();
    
    /**
     * Warning if action type is add (this can be empty, depending on what we want user 
     * to see.
     * 
     * @return true if user has selected to do the action
     */
    public boolean actionAddWarning();

    /**
     * Warning if action type is edit (this can be empty, depending on what we want user 
     * to see.
     * 
     * @return true if user has selected to do the action
     */
    public boolean actionEditWarning();

    /**
     * Save data in panel
     * 
     * @return true if save was successful
     */
    public boolean saveData();


}
    
    

