package com.atech.db.hibernate.transfer;

import java.util.ArrayList;

import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
import com.atech.i18n.I18nControlAbstract;


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

public class BackupRestoreCollection implements BackupRestoreBase, CheckBoxTreeNodeInterface
{


	private String name = null;
	private ArrayList<BackupRestoreBase> children;
//x	private int count_children_collection;
	private int total_children;
	private boolean selected = false;
	
	private ArrayList<CheckBoxTreeNodeInterface> children_tree;
	
	@SuppressWarnings("unused")
    private I18nControlAbstract ic;
	
	
	
	public BackupRestoreCollection(String name, I18nControlAbstract ic)
	{
	    this.ic = ic;
		this.name = ic.getMessage(name);
		this.children = new ArrayList<BackupRestoreBase>();
		this.children_tree = new ArrayList<CheckBoxTreeNodeInterface>();
	}
	
	
	public void addChild(BackupRestoreBase base)
	{
		this.children.add(base);
		this.children_tree.add((CheckBoxTreeNodeInterface)base);
		
		if (base instanceof BackupRestoreCollection)
		{
			this.total_children += ((BackupRestoreCollection)base).getTotalProcents();
		}
		else
			this.total_children++;
	}
	
	
	public ArrayList<CheckBoxTreeNodeInterface>  getChildren()
	{
		return this.children_tree;
	}
	
	
	public BackupRestoreBase getChild(int index)
	{
	    return this.children.get(index);
	}
	
	
	public void removeChild(int index)
	{
	    this.children_tree.remove(index);
	    this.children.remove(index);
	}

    public int childCount()
    {
        return this.children_tree.size();
    }
	
	
	public int getTotalProcents()
	{
		return (int)(this.total_children * 100);
	}
	
	
	public String getTargetName()
	{
		return this.name;
	}
	
	//public 
	
	public String getClassName()
	{
	    return "";
	}
	
	
	/* 
	 * getName
	 */
	public String getName() 
	{
		return this.getTargetName();
	}


	/* 
	 * isSelected
	 */
	public boolean isSelected() 
	{
		return this.selected;
	}

	
	public boolean hasChildren()
	{
		return (this.children.size()!=0);
		
	}
	

	public String toString()
	{
		return this.getTargetName();
	}
	
    public boolean isCollection()
    {
        return true;
    }

	
	
	/* 
	 * setSelected
	 */
    public void setSelected(boolean selected)
    {
    }
    
/*	public void setSelected(boolean newValue) 
	{
		this.setSelected(newValue);
		
		for(int i=0; i<this.children.size(); i++)
		{
			this.children.get(i).setSelected(newValue);
		}
		
	}
*/

	
	
	
	
	
	
	
}
