package com.atech.graphics.components.jtreetable;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultTreeSelectionModel;

/**
 * This example shows how to create a simple JTreeTable component, 
 * by using a JTree as a renderer (and editor) for the cells in a 
 * particular column in the JTable.  
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


public class JTreeTable extends JTable
{
	
    private static final long serialVersionUID = 6274675444215143328L;
    protected TreeTableCellRenderer tree;
    private TreeTableModel treeTableModel;
    private boolean show_grid = false;

    public JTreeTable(TreeTableModel treeTableModel) 
    {
        super();

        this.treeTableModel = treeTableModel;
        // Create the tree. It will be used as a renderer and editor. 
        TreeTableCellRenderer tree2 = new TreeTableCellRenderer(treeTableModel, this); 

        setTreeTableCellRenderer(tree2);

        /*
        // Install a tableModel representing the visible rows in the tree. 
        super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

        // Force the JTable and JTree to share their row selection models. 
        tree.setSelectionModel(new DefaultTreeSelectionModel() { 
                                   // Extend the implementation of the constructor, as if: 
                                   /* public this() */ //{
/*                                       setSelectionModel(listSelectionModel); 
                                   } 
                               }); 
        // Make the tree and table row heights the same. 
        tree.setRowHeight(getRowHeight());

        

        // Install the tree editor renderer and editor. 
        setDefaultRenderer(TreeTableModel.class, tree); 
        */
        setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());  

        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));           
    }


    public void setShowGrid(boolean show)
    {
        super.setShowGrid(show);
        this.show_grid = show;
        
        //tree.setBorder(Color.black);
    }

    public boolean getShowGrid()
    {
        return this.show_grid;
    }



    /* Workaround for BasicTableUI anomaly. Make sure the UI never tries to 
     * paint the editor. The UI currently uses different techniques to 
     * paint the renderers and editors and overriding setBounds() below 
     * is not the right thing to do for an editor. Returning -1 for the 
     * editing row in this case, ensures the editor is never painted. 
     */
    public int getEditingRow() {
        return(getColumnClass(editingColumn) == TreeTableModel.class) ? -1 : editingRow;  
    }

    public void setTreeTableCellRenderer(TreeTableCellRenderer renderer)
    {
        this.tree = renderer;

        // Install a tableModel representing the visible rows in the tree. 
        super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

        // Force the JTable and JTree to share their row selection models. 
        tree.setSelectionModel(new DefaultTreeSelectionModel() 
                {

                    private static final long serialVersionUID = 6706842894134298653L;

                    // Extend the implementation of the constructor, as if: 
                                   /* public this() */ 
                    {
                        setSelectionModel(listSelectionModel); 
                    } 
                }); 
        // Make the tree and table row heights the same. 
        tree.setRowHeight(getRowHeight());

        if (this.show_grid)
        {
//            tree.setBorder(Color.black);
        }



        // Install the tree editor renderer and editor. 
        setDefaultRenderer(TreeTableModel.class, tree); 


    }

    public void removeIcons()
    {
        
    }

//        tree = new TreeTableCellRenderer(treeTableModel, this); 

        

    // 
    // The editor used to interact with tree nodes, a JTree.  
    //

    public class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor
    {
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int r, int c) {
            return tree;
        }
    }

}

