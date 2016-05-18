package com.atech.db.hibernate.transfer;

import java.util.ArrayList;
import java.util.List;

import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
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

// this one should be extended, we have several variables that need to be filled

public class BackupRestoreCollection implements BackupRestoreBase
{

    private String name = null;
    private List<BackupRestoreBase> children;
    // x private int count_children_collection;
    private int childrenCount;
    private boolean selected = false;

    private List<CheckBoxTreeNodeInterface> childrenTree;

    @SuppressWarnings("unused")
    private I18nControlAbstract ic;


    /**
     * Instantiates a new backup restore collection.
     * 
     * @param name the name
     * @param ic the i18nControl
     */
    public BackupRestoreCollection(String name, I18nControlAbstract ic)
    {
        this.ic = ic;
        this.name = ic.getMessage(name);
        this.children = new ArrayList<BackupRestoreBase>();
        this.childrenTree = new ArrayList<CheckBoxTreeNodeInterface>();
    }


    public List<BackupRestoreBase> getChildren()
    {
        return this.children;
    }


    /**
     * Adds the node child.
     * 
     * @param base the base
     */
    public void addNodeChild(BackupRestoreBase base)
    {
        this.children.add(base);
        this.childrenTree.add(base);

        if (base instanceof BackupRestoreCollection)
        {
            this.childrenCount += ((BackupRestoreCollection) base).getTotalProcents();
        }
        else
        {
            this.childrenCount++;
        }
    }


    /** 
     * getNodeChildren
     */
    public List<CheckBoxTreeNodeInterface> getNodeChildren()
    {
        return this.childrenTree;
    }


    /**
     * Gets the node child.
     * 
     * @param index the index
     * 
     * @return the node child
     */
    public BackupRestoreBase getNodeChild(int index)
    {
        return this.children.get(index);
    }


    /**
     * Removes the node child.
     * 
     * @param index the index
     */
    public void removeNodeChild(int index)
    {
        this.childrenTree.remove(index);
        this.children.remove(index);
    }


    /**
     * Node child count.
     * 
     * @return the int
     */
    public int nodeChildCount()
    {
        return this.childrenTree.size();
    }


    /**
     * Gets the total procents.
     * 
     * @return the total procents
     */
    public int getTotalProcents()
    {
        return this.childrenCount * 100;
    }


    /** 
     * getTargetName
     */
    public String getTargetName()
    {
        return this.name;
    }


    // public

    /** 
     * getClassName
     */
    public String getClassName()
    {
        return "";
    }


    /*
     * getName
     */
    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.getTargetName();
    }


    /** 
     * isSelected
     */
    public boolean isSelected()
    {
        return this.selected;
    }


    /** 
     * hasNodeChildren
     */
    public boolean hasNodeChildren()
    {
        return this.children.size() != 0;

    }


    /** 
     * toString
     */
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getTargetName());

        processChildren(stringBuilder, 4, this.children);

        return stringBuilder.toString();
    }


    public void processChildren(StringBuilder stringBuilder, int width, List<BackupRestoreBase> list)
    {
        String spaces = "";

        for (int i = 0; i < width; i++)
        {
            spaces += " ";
        }

        if (children != null)
        {
            System.out.println(
                "Process Children of " + this.getTargetName() + " count: " + children.size() + " array: " + children);
            // for (BackupRestoreBase brb : children)
            // {
            //
            // // System.out.println("BRB-1: " + brb);
            //
            // if (brb instanceof BackupRestoreCollection)
            // {
            // BackupRestoreCollection brc = (BackupRestoreCollection) brb;
            //
            // // stringBuilder.append(spaces + this.getTargetName());
            // //
            // // processChildren(stringBuilder, width + 2,
            // // brc.getChildren());
            //
            // stringBuilder.append(spaces + brb.getTargetName() + " C\n");
            //
            // }
            // else
            // {
            //
            // // System.out.println("BRB: " + brb);
            // stringBuilder.append(spaces + brb.getTargetName() + "\n");
            // }
            // }
        }
    }


    /** 
     * Is Collection
     */
    public boolean isCollection()
    {
        return true;
    }


    /** 
     * Set Selected
     */
    public void setSelected(boolean selected)
    {
    }

    /*
     * public void setSelected(boolean newValue)
     * {
     * this.setSelected(newValue);
     * for(int i=0; i<this.children.size(); i++)
     * {
     * this.children.get(i).setSelected(newValue);
     * }
     * }
     */

}
