package com.atech.db.hibernate.tool;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

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


public class DbToolTreeModel implements TreeModel 
{

    private boolean m_debug = false;

//x    private boolean isRoot = false;
    private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();
    private DbToolTreeRoot rootObj = null;


    public DbToolTreeModel(DbToolTreeRoot rt) 
    {
        //xisRoot = true;
        rootObj = rt;
    }


    public void debug(String deb)
    {
        if (m_debug)
            System.out.println(deb);
    }



    //////////////// Fire events //////////////////////////////////////////////

    /**
     * The only event raised by this model is TreeStructureChanged with the
     * root as path, i.e. the whole tree has changed.
     */
    protected void fireTreeStructureChanged(DbToolTreeRoot oldRoot) 
    {
        int len = treeModelListeners.size();
        TreeModelEvent e = new TreeModelEvent(this, new Object[] {oldRoot});

	for (int i = 0; i < len; i++) 
	{
            (treeModelListeners.elementAt(i)).treeStructureChanged(e);
        }
    }


    //////////////// TreeModel interface implementation ///////////////////////

    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
    public void addTreeModelListener(TreeModelListener l) 
    {
        treeModelListeners.addElement(l);
    }


    /**
     * Removes a listener previously added with addTreeModelListener().
     */
    public void removeTreeModelListener(TreeModelListener l) 
    {
	treeModelListeners.removeElement(l);
    }

    /**
     * Messaged when the user has altered the value for the item
     * identified by path to newValue.  Not used by this model.
     */
    public void valueForPathChanged(TreePath path, Object newValue) 
    {
	System.out.println("*** valueForPathChanged : " + path + " --> " + newValue);
    }






    /**
     * Returns the child of parent at index index in the parent's child array.
     */
    public Object getChild(Object parent, int index) 
    {

        debug("getChild: " + index);

        if (parent instanceof DbToolTreeRoot)
        {
	    if (rootObj.type==DbToolTreeRoot.ROOT_SINGLE)
	    {
		return rootObj.m_app_list.get(index);
	    }
	    else
                return rootObj.m_appGroup.get(index);
        }
	else if (parent instanceof DbToolApplicationInterface)
	{
	    return null;
	}
/*	else if (parent instanceof FoodGroup)
	{
	    FoodGroup fg = (FoodGroup)parent;
	    ArrayList lst = (ArrayList)this.rootObj.m_foodDescByGroup.get(""+fg.getId());
	    return (FoodDescription)lst.get(index);
	} */
	else
	    return null;

    }

    /**
     * Returns the number of children of parent.
     */
    public int getChildCount(Object parent) 
    {

        debug("Parent (getChildCount()): " + parent);

        if (parent instanceof DbToolTreeRoot)
        {
	    if (rootObj.type==DbToolTreeRoot.ROOT_SINGLE)
	    {
		return rootObj.m_app_list.size();
	    }
	    else
		return rootObj.m_appGroup.size();
        }
	else if (parent instanceof DbToolApplicationInterface)
	{
	    return 0;
	}
	else if (parent instanceof DatabaseSettings)
	{
	    return 0;
	}
	else
	    return 0;

    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object parent, Object child) 
    {

        debug("getIndexofChild: ");

        if (parent instanceof DbToolTreeRoot)
        {
            DbToolApplicationInterface dii = (DbToolApplicationInterface)child;
            Iterator<DbToolApplicationInterface> it = rootObj.m_appGroup.iterator();

            int i = -1;

            while (it.hasNext()) 
            {
                i++;

                DbToolApplicationInterface c = (DbToolApplicationInterface)it.next();

		if (dii.equals(c))
		{
		    return i;
		}
            }
        }
	else if (parent instanceof DbToolApplicationInterface)
	{
	    return -1;
	}
	else if (parent instanceof DatabaseSettings)
	{
	    return -1;
	}
	//else

	return -1;
/*
	else if (parent instanceof DatabaseSettings)
	{
/*
	    FoodDescription dii = (FoodDescription)child;
	    ArrayList lst = (ArrayList)this.rootObj.m_foodDescByGroup.get(""+dii.getFood_group_id());
	    Iterator it = lst.iterator();

	    int i = -1;

	    while (it.hasNext()) 
	    {
		i++;

		FoodDescription c = (FoodDescription)it.next();

		if (dii.getId()==c.getId()) 
		    return i;
	    }
	}
*/


    }

    /**
     * Returns the root of the tree.
     */
    public Object getRoot() 
    {
        return rootObj;
    }

    /**
     * Returns true if node is a leaf.
     */
    public boolean isLeaf(Object node) 
    {

        if (node instanceof DbToolTreeRoot)
        {
	    if (rootObj.type==DbToolTreeRoot.ROOT_SINGLE)
		return (rootObj.m_app_list.size()==0);
	    else
		return (rootObj.m_appGroup.size()==0);

        }
	else if (node instanceof DbToolApplicationInterface)
	{
	    return true;
	}
	else if (node instanceof DatabaseSettings)
	{
	    return true;
	}
/*	else if (node instanceof FoodGroup)
	{
	    FoodGroup fg = (FoodGroup)node;
	    ArrayList lst = (ArrayList)this.rootObj.m_foodDescByGroup.get(""+fg.getId());
	    return lst.size() == 0;
	} */
	else
	    return true;

    }

}
