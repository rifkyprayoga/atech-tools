package com.atech.graphics.components.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

import com.atech.db.hibernate.transfer.BackupRestoreBase;

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
 * @version 1.1 04/24/99
 */
public class CheckRenderer extends JPanel implements TreeCellRenderer
{
    private static final long serialVersionUID = 3567768460803147725L;

    /**
     * The check.
     */
    protected JCheckBox check;

    /**
     * The label.
     */
    protected TreeLabel label;

    /**
     * Instantiates a new check renderer.
     */
    public CheckRenderer()
    {
        setLayout(null);
        add(check = new JCheckBox());
        add(label = new TreeLabel());
        check.setBackground(UIManager.getColor("Tree.textBackground"));
        label.setForeground(UIManager.getColor("Tree.textForeground"));
    }

    /** 
     * getTreeCellRendererComponent
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
            boolean leaf, int row, boolean hasFocus)
    {
        String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus);
        setEnabled(tree.isEnabled());
        check.setSelected(((CheckNode) value).isSelected());
        label.setFont(tree.getFont());

        if (value instanceof BackupRestoreBase)
        {
            BackupRestoreBase brb = (BackupRestoreBase) value;
            label.setText(brb.getTargetName());
        }
        else
        {
            label.setText(stringValue);
        }

        label.setSelected(isSelected);
        label.setFocus(hasFocus);
        if (leaf)
        {
            label.setIcon(UIManager.getIcon("Tree.leafIcon"));
        }
        else if (expanded)
        {
            label.setIcon(UIManager.getIcon("Tree.openIcon"));
        }
        else
        {
            label.setIcon(UIManager.getIcon("Tree.closedIcon"));
        }
        return this;
    }

    /** 
     * getPreferredSize
     */
    @Override
    public Dimension getPreferredSize()
    {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        return new Dimension(d_check.width + d_label.width, d_check.height < d_label.height ? d_label.height
                : d_check.height);
    }

    /** 
     * doLayout
     */
    @Override
    public void doLayout()
    {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        int y_check = 0;
        int y_label = 0;
        if (d_check.height < d_label.height)
        {
            y_check = (d_label.height - d_check.height) / 2;
        }
        else
        {
            y_label = (d_check.height - d_label.height) / 2;
        }
        check.setLocation(0, y_check);
        check.setBounds(0, y_check, d_check.width, d_check.height);
        label.setLocation(d_check.width, y_label);
        label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
    }

    /** 
     * setBackground
     */
    @Override
    public void setBackground(Color color)
    {
        if (color instanceof ColorUIResource)
        {
            color = null;
        }
        super.setBackground(color);
    }

    /**
     * The Class TreeLabel.
     */
    public class TreeLabel extends JLabel
    {
        private static final long serialVersionUID = -4558401204750233731L;

        /**
         * The is selected.
         */
        boolean isSelected;

        /**
         * The has focus.
         */
        boolean hasFocus;

        /**
         * Instantiates a new tree label.
         */
        public TreeLabel()
        {
        }

        /** 
         * setBackground
         */
        @Override
        public void setBackground(Color color)
        {
            if (color instanceof ColorUIResource)
            {
                color = null;
            }
            super.setBackground(color);
        }

        /** 
         * paint
         */
        @Override
        public void paint(Graphics g)
        {
            String str;
            if ((str = getText()) != null)
            {
                if (0 < str.length())
                {
                    if (isSelected)
                    {
                        g.setColor(UIManager.getColor("Tree.selectionBackground"));
                    }
                    else
                    {
                        g.setColor(UIManager.getColor("Tree.textBackground"));
                    }
                    Dimension d = getPreferredSize();
                    int imageOffset = 0;
                    Icon currentI = getIcon();
                    if (currentI != null)
                    {
                        imageOffset = currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
                    }
                    g.fillRect(imageOffset, 0, d.width - 1 - imageOffset, d.height);
                    if (hasFocus)
                    {
                        g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
                        g.drawRect(imageOffset, 0, d.width - 1 - imageOffset, d.height - 1);
                    }
                }
            }
            super.paint(g);
        }

        /** 
         * getPreferredSize
         */
        @Override
        public Dimension getPreferredSize()
        {
            Dimension retDimension = super.getPreferredSize();
            if (retDimension != null)
            {
                retDimension = new Dimension(retDimension.width + 3, retDimension.height);
            }
            return retDimension;
        }

        /**
         * Sets the selected.
         * 
         * @param isSelected the new selected
         */
        public void setSelected(boolean isSelected)
        {
            this.isSelected = isSelected;
        }

        /**
         * Sets the focus.
         * 
         * @param hasFocus the new focus
         */
        public void setFocus(boolean hasFocus)
        {
            this.hasFocus = hasFocus;
        }
    }
}
