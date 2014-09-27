package com.atech.graphics.components.jtreetable;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

// TODO: Auto-generated Javadoc
/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

/**
 * This is a wrapper class takes a TreeTableModel and implements 
 * the table model interface. The implementation is trivial, with 
 * all of the event dispatching support provided by the superclass: 
 * the AbstractTableModel. 
 *
 * @version %I% %G%
 *
 * @author Philip Milne
 * @author Scott Violet
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

public class TreeTableModelAdapter extends AbstractTableModel
{

    private static final long serialVersionUID = 7964220491055736055L;

    /**
     * The tree.
     */
    JTree tree;

    /**
     * The tree table model.
     */
    TreeTableModel treeTableModel;

    /**
     * Instantiates a new tree table model adapter.
     * 
     * @param treeTableModel the tree table model
     * @param tree the tree
     */
    public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree)
    {
        this.tree = tree;
        this.treeTableModel = treeTableModel;

        tree.addTreeExpansionListener(new TreeExpansionListener()
        {
            // Don't use fireTableRowsInserted() here;
            // the selection model would get updated twice.
            public void treeExpanded(TreeExpansionEvent event)
            {
                fireTableDataChanged();
            }

            public void treeCollapsed(TreeExpansionEvent event)
            {
                fireTableDataChanged();
            }
        });
    }

    // Wrappers, implementing TableModel interface.

    /** 
     * getColumnCount
     */
    public int getColumnCount()
    {
        return treeTableModel.getColumnCount();
    }

    /** 
     * getColumnName
     */
    @Override
    public String getColumnName(int column)
    {
        return treeTableModel.getColumnName(column);
    }

    /** 
     * getColumnClass
     */
    @Override
    public Class<?> getColumnClass(int column)
    {
        return treeTableModel.getColumnClass(column);
    }

    /** 
     * getRowCount
     */
    public int getRowCount()
    {
        return tree.getRowCount();
    }

    /**
     * Node for row.
     * 
     * @param row the row
     * 
     * @return the object
     */
    protected Object nodeForRow(int row)
    {
        TreePath treePath = tree.getPathForRow(row);
        return treePath.getLastPathComponent();
    }

    /** 
     * getValueAt
     */
    public Object getValueAt(int row, int column)
    {
        return treeTableModel.getValueAt(nodeForRow(row), column);
    }

    /** 
     * isCellEditable
     */
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return treeTableModel.isCellEditable(nodeForRow(row), column);
    }

    /** 
     * setValueAt
     */
    @Override
    public void setValueAt(Object value, int row, int column)
    {
        treeTableModel.setValueAt(value, nodeForRow(row), column);
    }
}
