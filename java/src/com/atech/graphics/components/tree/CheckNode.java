/*  (swing1.1) */

package com.atech.graphics.components.tree;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class was taken from site http://www.objects.com.au/home/index.html.
 * 
 * I tried to get some permission to post it, but e-mail was disabled. Since
 * there were many samples there I imagine they were put there for public use.
 * If class posted here is problem, please let me know and it will be removed
 * and rewritten.
 * 
 * There were some minor changes done and some cosmetical changes.
 * 
 * 
 * @version 1.0 01/11/99
 */


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


public class CheckNode extends DefaultMutableTreeNode
{

    private static final long serialVersionUID = -1791091492376349349L;
    public final static int SINGLE_SELECTION = 0;
    public final static int DIG_IN_SELECTION = 4;

    protected int selectionMode;
    protected boolean isSelected;
    protected String name;

    public CheckNode()
    {
        this(null, null);
    }

    public CheckNode(Object userObject, String name)
    {
        this(userObject, name, true, false);
    }

    public CheckNode(Object userObject, String name, boolean allowsChildren,
            boolean isSelected)
    {
        super(userObject, allowsChildren);
        this.isSelected = isSelected;
        setSelectionMode(DIG_IN_SELECTION);
        this.name = name;
    }

    public CheckNode(Object userObject, String name, boolean allowsChildren,
            int selection_mode)
    {
        super(userObject, allowsChildren);
        this.isSelected = false;
        setSelectionMode(selection_mode);
        this.name = name;
    }

    public void setSelectionMode(int mode)
    {
        selectionMode = mode;
    }

    public int getSelectionMode()
    {
        return selectionMode;
    }

    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;

        if ((selectionMode == DIG_IN_SELECTION) && (children != null))
        {
            Enumeration<?> en = children.elements();
            while (en.hasMoreElements())
            {
                CheckNode node = (CheckNode) en.nextElement();
                node.setSelected(isSelected);
            }
        } 
        else
        {

        }
    }

    
    public String getName()
    {
        return this.name;
    }
    
    public boolean isSelected()
    {
        return isSelected;
    }

    // If you want to change "isSelected" by CellEditor,
    /*
     * public void setUserObject(Object obj) { if (obj instanceof Boolean) {
     * setSelected(((Boolean)obj).booleanValue()); } else {
     * super.setUserObject(obj); } }
     */

}
