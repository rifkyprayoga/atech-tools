package com.atech.graphics.components.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

// TODO: Auto-generated Javadoc
/*
 * @version 1.1 01/15/99
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

public class CheckNodeTree extends JTree
{

    private static final long serialVersionUID = -7832116437447066164L;

    /**
     * Instantiates a new check node tree.
     * 
     * @param root_node the root_node
     * @param check_type the check_type
     */
    public CheckNodeTree(CheckBoxTreeNodeInterface root_node, int check_type)
    {
        this(root_node, check_type, new CheckRenderer());
        /*
         * super(CheckNodeUtil.buildTree(root_node, check_type));
         * this.setCellRenderer(new CheckRenderer());
         * this.getSelectionModel().setSelectionMode(TreeSelectionModel.
         * SINGLE_TREE_SELECTION);
         * this.putClientProperty("JTree.lineStyle", "Angled");
         * this.addMouseListener(new NodeSelectionListener(this));
         */
    }

    /**
     * Instantiates a new check node tree.
     * 
     * @param root_node the root_node
     * @param check_type the check_type
     */
    public CheckNodeTree(CheckBoxTreeNodeInterface root_node, int check_type, TreeCellRenderer renderer)
    {
        super(CheckNodeUtil.buildTree(root_node, check_type));

        this.setCellRenderer(renderer);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.putClientProperty("JTree.lineStyle", "Angled");
        this.addMouseListener(new NodeSelectionListener(this));

    }

    /**
     * Prints the tree.
     */
    public void printTree()
    {
        printTreeTraverse((CheckNode) this.getModel().getRoot());
    }

    /**
     * Prints the tree traverse.
     * 
     * @param nd the nd
     */
    public void printTreeTraverse(CheckNode nd)
    {
        // System.out.println("Node [" + nd.toString() + "]: " +
        // nd.isSelected());

        for (int i = 0; i < nd.getChildCount(); i++)
        {
            printTreeTraverse((CheckNode) nd.getChildAt(i));
        }

    }

    /**
     * Gets the root node.
     * 
     * @return the root node
     */
    public CheckNode getRootNode()
    {
        return (CheckNode) this.getModel().getRoot();
    }

    /**
     * The temp_selected.
     */
    boolean temp_selected = false;

    /**
     * Gets the value for node.
     * 
     * @param node_name the node_name
     * 
     * @return the value for node
     */
    public boolean getValueForNode(String node_name)
    {
        boolean found = traverseNodes(this.getRootNode(), node_name);

        if (found)
            return temp_selected;
        else
            return false;

    }

    /**
     * Traverse nodes.
     * 
     * @param node the node
     * @param search_item the search_item
     * 
     * @return true, if successful
     */
    public boolean traverseNodes(CheckNode node, String search_item)
    {
        if (node.getName() == search_item)
        {
            this.temp_selected = node.isSelected();
            return true;
        }

        for (int i = 0; i < node.getChildCount(); i++)
        {
            boolean found = traverseNodes((CheckNode) node.getChildAt(i), search_item);

            if (found)
                return true;

        }

        return false;
    }

    /**
     * The listener interface for receiving nodeSelection events.
     * The class that is interested in processing a nodeSelection
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addNodeSelectionListener<code> method. When
     * the nodeSelection event occurs, that object's appropriate
     * method is invoked.
     */
    class NodeSelectionListener extends MouseAdapter
    {

        /**
         * The tree.
         */
        JTree tree;

        /**
         * Instantiates a new node selection listener.
         * 
         * @param tree the tree
         */
        NodeSelectionListener(JTree tree)
        {
            this.tree = tree;
        }

        /** 
         * mouseClicked
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            int row = tree.getRowForLocation(x, y);
            TreePath path = tree.getPathForRow(row);
            // TreePath path = tree.getSelectionPath();
            if (path != null)
            {
                CheckNode node = (CheckNode) path.getLastPathComponent();
                boolean isSelected = !node.isSelected();
                node.setSelected(isSelected);
                if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION)
                {
                    if (isSelected)
                    {
                        tree.expandPath(path);
                    }
                    else
                    {
                        tree.collapsePath(path);
                    }
                }

                ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
                // I need revalidate if node is root. but why?
                // if (row == 0) {
                tree.revalidate();
                tree.repaint();
                // }
                // */
                ((CheckNodeTree) tree).printTree();

            }
        }
    }

    /**
     * The listener interface for receiving buttonAction events.
     * The class that is interested in processing a buttonAction
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addButtonActionListener<code> method. When
     * the buttonAction event occurs, that object's appropriate
     * method is invoked.
     */
    class ButtonActionListener implements ActionListener
    {

        /**
         * The root.
         */
        CheckNode root;

        /**
         * The text area.
         */
        JTextArea textArea;

        /**
         * Instantiates a new button action listener.
         * 
         * @param root the root
         * @param textArea the text area
         */
        ButtonActionListener(final CheckNode root, final JTextArea textArea)
        {
            this.root = root;
            this.textArea = textArea;
        }

        /** 
         * actionPerformed
         */
        public void actionPerformed(ActionEvent e)
        {
            Enumeration<?> en = root.breadthFirstEnumeration();
            while (en.hasMoreElements())
            {
                CheckNode node = (CheckNode) en.nextElement();
                if (node.isSelected())
                {
                    TreeNode[] nodes = node.getPath();
                    textArea.append("\n" + nodes[0].toString());
                    for (int i = 1; i < nodes.length; i++)
                    {
                        textArea.append("/" + nodes[i].toString());
                    }
                }
            }
        }
    }

}
