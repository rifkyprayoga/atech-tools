package com.atech.graphics.components;

import com.atech.i18n.I18nControlAbstract;

// TODO: Auto-generated Javadoc
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

/**
 * The Class ATTableData.
 */
public abstract class ATTableData
{

    /**
     * The i18nControl.
     */
    protected I18nControlAbstract ic = null;

    /**
     * The columns.
     */
    protected String[] columns = null; // = null; //{ /*"ID",*/ "WEIGHT_TYPE",
                                       // "AMOUNT", "WEIGHT" };

    /**
     * The column_size.
     */
    protected float[] column_size = null; // = null; //{ /*0.1f,*/ 0.5f, 0.25f,
                                          // 0.25f };


    /**
     * Instantiates a new aT table data.
     * 
     * @param ic the i18nControl
     */
    public ATTableData(I18nControlAbstract ic)
    {
        this.ic = ic;
        init();
    }


    /**
     * Inits the.
     */
    public abstract void init();


    /**
     * Inits the.
     * 
     * @param cols the cols
     * @param cols_vals the cols_vals
     */
    public void init(String[] cols, float[] cols_vals)
    {
        this.columns = cols;
        this.column_size = cols_vals;

        // System.out.println("Cols in: " + cols);
        // System.out.println("Cols: " + columns);

    }


    /**
     * Gets the column header.
     * 
     * @param column the column
     * 
     * @return the column header
     */
    public String getColumnHeader(int column)
    {
        return ic.getMessage(columns[column]);
    }


    /**
     * Gets the column width.
     * 
     * @param column the column
     * @param full_size the full_size
     * 
     * @return the column width
     */
    public int getColumnWidth(int column, int full_size)
    {
        return (int) (this.column_size[column] * full_size);
    }


    /**
     * Gets the columns count.
     * 
     * @return the columns count
     */
    public int getColumnsCount()
    {
        // System.out.println(this.columns);
        return this.columns.length;
    }


    /**
     * Gets the column value.
     * 
     * @param column the column
     * 
     * @return the column value
     */
    public abstract String getColumnValue(int column);

}
