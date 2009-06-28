package com.atech.graphics.components.tree;

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



/**
 * @version 1.0 01/11/99
 */
public class CheckNodeUtil  
{

	/**
	 * Builds the tree.
	 * 
	 * @param cb the cb
	 * @param check_type the check_type
	 * 
	 * @return the check node
	 */
	public static CheckNode buildTree(CheckBoxTreeNodeInterface cb, int check_type)
	{
		return CheckNodeUtil.traverseTree(cb, check_type);
	}
	
	/**
	 * Traverse tree.
	 * 
	 * @param cb the cb
	 * @param check_type the check_type
	 * 
	 * @return the check node
	 */
	public static CheckNode traverseTree(CheckBoxTreeNodeInterface cb, int check_type)
	{
		CheckNode node = new CheckNode(cb, cb.getTargetName(), cb.hasNodeChildren(), check_type);
		
		if (cb.hasNodeChildren())
		{
			for(int i=0; i<cb.getNodeChildren().size(); i++)
			{
				node.add(CheckNodeUtil.traverseTree(cb.getNodeChildren().get(i), check_type));
			}
		}
		
		return node;
	}
	
	

}

