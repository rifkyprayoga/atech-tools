package com.atech.graphics.components;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

// TODO: Auto-generated Javadoc
/*
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

/**
 * The Class ATTableModel.
 */
public class ATTableModel extends AbstractTableModel
{

    private static final long serialVersionUID = -8909561742968102872L;

    /**
     * The list.
     */
    ArrayList<?> list = null;

    /**
     * The object_type.
     */
    ATTableData object_type = null;

    /**
     * Instantiates a new aT table model.
     * 
     * @param list the list
     * @param object_type the object_type
     */
    public ATTableModel(ArrayList<?> list, ATTableData object_type)
    {
        this.list = list;
        this.object_type = object_type;

        // System.out.println(this.object_type);

    }

    /**
     * Sets the array list.
     * 
     * @param list the new array list
     */
    public void setArrayList(ArrayList<?> list)
    {
        this.list = list;
        // System.out.println("set AL: " + list.size());
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    public int getColumnCount()
    {
        // System.out.println(this.object_type);
        return this.object_type.getColumnsCount();
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount()
    {
        // System.out.println("RC: " +this.list.size());
        return this.list.size();
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return ((ATTableData) this.list.get(rowIndex)).getColumnValue(columnIndex);
    }
}
