package com.atech.graphics.components.jtreetable;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

// TODO: Auto-generated Javadoc
/**
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended for
 * use in the design, construction, operation or maintenance of any nuclear
 * facility.
 */

// import com.sun.java.swing.tree.*;
// import com.sun.java.swing.event.*;

/**
 * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 * 
 */

/*
 * * An abstract implementation of the TreeTableModel interface, handling the
 * list of listeners.
 * @version %I% %G%
 * @author Philip Milne
 */

public abstract class AbstractTreeTableModel implements TreeTableModel
{

    /**
     * The root.
     */
    protected Object root;

    /**
     * The listener list.
     */
    protected EventListenerList listenerList = new EventListenerList();

    /**
     * Instantiates a new abstract tree table model.
     * 
     * @param root the root
     */
    public AbstractTreeTableModel(Object root)
    {
        this.root = root;
    }

    //
    // Default implmentations for methods in the TreeModel interface.
    //

    /** 
     * getRoot
     */
    public Object getRoot()
    {
        return root;
    }

    /** 
     * isLeaf
     */
    public boolean isLeaf(Object node)
    {
        return getChildCount(node) == 0;
    }

    /** 
     * valueForPathChanged
     */
    public void valueForPathChanged(TreePath path, Object newValue)
    {
    }

    // This is not called in the JTree's default mode: use a naive
    // implementation.
    /** 
     * getIndexOfChild
     */
    public int getIndexOfChild(Object parent, Object child)
    {
        for (int i = 0; i < getChildCount(parent); i++)
        {
            if (getChild(parent, i).equals(child))
                return i;
        }
        return -1;
    }

    /** 
     * addTreeModelListener
     */
    public void addTreeModelListener(TreeModelListener l)
    {
        listenerList.add(TreeModelListener.class, l);
    }

    /** 
     * removeTreeModelListener
     */
    public void removeTreeModelListener(TreeModelListener l)
    {
        listenerList.remove(TreeModelListener.class, l);
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type. The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    /**
     * Fire tree nodes changed.
     * 
     * @param source the source
     * @param path the path
     * @param childIndices the child indices
     * @param children the children
     */
    protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                {
                    e = new TreeModelEvent(source, path, childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type. The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    /**
     * Fire tree nodes inserted.
     * 
     * @param source the source
     * @param path the path
     * @param childIndices the child indices
     * @param children the children
     */
    protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                {
                    e = new TreeModelEvent(source, path, childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type. The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    /**
     * Fire tree nodes removed.
     * 
     * @param source the source
     * @param path the path
     * @param childIndices the child indices
     * @param children the children
     */
    protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                {
                    e = new TreeModelEvent(source, path, childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
            }
        }
    }

    /*
     * Notify all listeners that have registered interest for
     * notification on this event type. The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @see EventListenerList
     */
    /**
     * Fire tree structure changed.
     * 
     * @param source the source
     * @param path the path
     * @param childIndices the child indices
     * @param children the children
     */
    protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] == TreeModelListener.class)
            {
                // Lazily create the event:
                if (e == null)
                {
                    e = new TreeModelEvent(source, path, childIndices, children);
                }
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }

    //
    // Default impelmentations for methods in the TreeTableModel interface.
    //

    /** 
     * getColumnClass
     */
    public Class<?> getColumnClass(int column)
    {
        return Object.class;
    }

    /** By default, make the column with the Tree in it the only editable one. 
     *  Making this column editable causes the JTable to forward mouse 
     *  and keyboard events in the Tree column to the underlying JTree. 
     */
    public boolean isCellEditable(Object node, int column)
    {
        return getColumnClass(column) == TreeTableModel.class;
    }

    /** 
     * setValueAt
     */
    public void setValueAt(Object aValue, Object node, int column)
    {
    }

    // Left to be implemented in the subclass:

    /*
     * public Object getChild(Object parent, int index)
     * public int getChildCount(Object parent)
     * public int getColumnCount()
     * public String getColumnName(Object node, int column)
     * public Object getValueAt(Object node, int column)
     */

}
