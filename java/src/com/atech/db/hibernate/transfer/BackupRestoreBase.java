package com.atech.db.hibernate.transfer;

import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;


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


// this one should be extended, we have several variables that need to be filled

public interface BackupRestoreBase extends CheckBoxTreeNodeInterface
{

    /**
     * Get Target Name
     * 
     * @return target name as string
     */
	public String getTargetName();
	
	/**
	 * Is Object Collection
	 * 
	 * @return true if it has children
	 */
	public boolean isCollection();
	
	/**
	 * Set Selected
	 * 
	 * @param selected true if selected
	 */
	public void setSelected(boolean selected);
	
	/**
	 * Is Selected
	 * 
	 * @return true if selected
	 */
	public boolean isSelected();
	
	/**
	 * Get Class Name
	 * 
	 * @return name of class
	 */
	public String getClassName();
	
}
