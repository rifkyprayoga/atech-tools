package com.atech.graphics.components.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

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

    public CheckNodeTree(CheckBoxTreeNodeInterface root_node, int check_type)
    {
        super(CheckNodeUtil.buildTree(root_node, check_type));

        // this.getModel().getRoot()

        /*
         * super("CheckNode TreeExample"); String[] strs = {"swing", // 0
         * "platf", // 1 "basic", // 2 "metal", // 3 "JTree"}; // 4
         */
        /*
         * CheckNode[] nodes = new CheckNode[strs.length]; for (int
         * i=0;i<strs.length;i++) { nodes[i] = new CheckNode(strs[i]); }
         * nodes[0].add(nodes[1]); nodes[1].add(nodes[2]);
         * nodes[1].add(nodes[3]); nodes[0].add(nodes[4]);
         * nodes[3].setSelected(true);
         * 
         * 
         * 
         * JTree tree = new JTree( nodes[0] );
         */
        this.setCellRenderer(new CheckRenderer());
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.putClientProperty("JTree.lineStyle", "Angled");
        this.addMouseListener(new NodeSelectionListener(this));

        /*
         * JScrollPane sp = new JScrollPane(tree);
         * 
         * //ModePanel mp = new ModePanel(nodes); JTextArea textArea = new
         * JTextArea(3,10); JScrollPane textPanel = new JScrollPane(textArea);
         * JButton button = new JButton("print"); button.addActionListener( new
         * ButtonActionListener(nodes[0], textArea)); JPanel panel = new
         * JPanel(new BorderLayout()); panel.add(mp, BorderLayout.CENTER);
         * panel.add(button, BorderLayout.SOUTH);
         * 
         * getContentPane().add(sp, BorderLayout.CENTER);
         * getContentPane().add(panel, BorderLayout.EAST);
         * getContentPane().add(textPanel, BorderLayout.SOUTH);
         */
    }

    public void printTree()
    {
        printTreeTraverse((CheckNode) this.getModel().getRoot());
    }

    public void printTreeTraverse(CheckNode nd)
    {
        System.out.println("Node [" + nd.toString() + "]: " + nd.isSelected());

        for (int i = 0; i < nd.getChildCount(); i++)
        {
            printTreeTraverse((CheckNode) nd.getChildAt(i));
        }

    }

    public CheckNode getRootNode()
    {
        return (CheckNode) this.getModel().getRoot();
    }

    boolean temp_selected = false;

    public boolean getValueForNode(String node_name)
    {
        boolean found = traverseNodes(this.getRootNode(), node_name);

        if (found)
            return temp_selected;
        else
            return false;

    }

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
            {
                return true;
            }

        }

        return false;
    }

    class NodeSelectionListener extends MouseAdapter
    {
        JTree tree;

        NodeSelectionListener(JTree tree)
        {
            this.tree = tree;
        }

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
                boolean isSelected = !(node.isSelected());
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

    /*
     * class ModePanel extends JPanel implements ActionListener { CheckNode[]
     * nodes; JRadioButton b_single, b_dig_in;
     * 
     * ModePanel(CheckNode[] nodes) { this.nodes = nodes; setLayout(new
     * GridLayout(2,1)); setBorder(new TitledBorder("Check Mode")); ButtonGroup
     * group = new ButtonGroup(); add(b_dig_in = new JRadioButton("DIG_IN  "));
     * add(b_single = new JRadioButton("SINGLE  ")); group.add(b_dig_in);
     * group.add(b_single); b_dig_in.addActionListener(this);
     * b_single.addActionListener(this); b_dig_in.setSelected(true); }
     * 
     * public void actionPerformed(ActionEvent e) { int mode; if (b_single ==
     * e.getSource()) { mode = CheckNode.SINGLE_SELECTION; } else { mode =
     * CheckNode.DIG_IN_SELECTION; } for (int i=0;i<nodes.length;i++) {
     * nodes[i].setSelectionMode(mode); } } }
     */

    class ButtonActionListener implements ActionListener
    {
        CheckNode root;
        JTextArea textArea;

        ButtonActionListener(final CheckNode root, final JTextArea textArea)
        {
            this.root = root;
            this.textArea = textArea;
        }

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
    /*
     * public static void main(String args[]) { CheckNodeTreeExample frame = new
     * CheckNodeTreeExample(); frame.addWindowListener(new WindowAdapter() {
     * public void windowClosing(WindowEvent e) {System.exit(0);} });
     * frame.setSize(300, 200); frame.setVisible(true); }
     */
}
