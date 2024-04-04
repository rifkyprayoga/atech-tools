package com.atech.graphics.components.jtreetable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;

// TODO: Auto-generated Javadoc
// 
// The renderer used to display the tree nodes, a JTree.
//

/**
 * * This file is part of ATech Tools library.
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
 */

public class TreeTableCellRenderer extends JTree implements TableCellRenderer
{

    private static final long serialVersionUID = -6739868404878590702L;

    /**
     * The visible row.
     */
    public int visibleRow;

    /**
     * The table.
     */
    public JTreeTable table;

    /**
     * Instantiates a new tree table cell renderer.
     * 
     * @param model the model
     * @param table the table
     */
    public TreeTableCellRenderer(TreeTableModel model, JTreeTable table/*
                                                                        * ,
                                                                        * boolean
                                                                        * show_icons
                                                                        */)
    {
        super(model);
        this.table = table;
        putClientProperty("JTree.lineStyle", "None");
    }

    /** 
     * setBounds
     */
    @Override
    public void setBounds(int x, int y, int w, int h)
    {
        if (this.table.getShowGrid())
        {
            super.setBounds(x, 1, w, table.getHeight() - 1);
        }
        else
        {
            super.setBounds(x, 0, w, table.getHeight());
        }
    }

    /** 
     * paint
     */
    @Override
    public void paint(Graphics g)
    {
        g.translate(0, -visibleRow * getRowHeight());
        super.paint(g);
    }

    /**
     * Put client property new.
     * 
     * @param key the key
     * @param value the value
     */
    public void putClientPropertyNew(Object key, Object value)
    {
        putClientProperty(key, value);
    }

    /** 
     * getBackground
     */
    @Override
    public Color getBackground()
    {
        return super.getBackground();
    }

    /** 
     * setBackground
     */
    @Override
    public void setBackground(Color clr)
    {
        super.setBackground(clr);
    }

    /** 
     * isOpaque
     */
    @Override
    public boolean isOpaque()
    {
        return super.isOpaque();
    }

    /** 
     * setOpaque
     */
    @Override
    public void setOpaque(boolean is)
    {
        super.setOpaque(is);
    }

    /** 
     * getTableCellRendererComponent
     */
    public Component getTableCellRendererComponent(JTable table_int, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
    {

        // super.getTreeCellRendererComponent(table_int,value,isSelected,hasFocus,row,column);

        // x boolean grid = this.table.getShowGrid();
        /*
         * if (grid)
         * {
         * //this.set
         * setBorder(new LineBorder(Color.black));
         * }
         */

        if (isSelected)
        {
            setBackground(table.getSelectionBackground());
        }
        else
        {
            setBackground(table.getBackground());
        }

        visibleRow = row;
        return this;
    }

    // public void setBorder(
}
