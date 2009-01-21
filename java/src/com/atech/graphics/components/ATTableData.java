package com.atech.graphics.components;
import com.atech.i18n.I18nControlAbstract;

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

public abstract class ATTableData
{

    protected I18nControlAbstract ic = null;

    protected String[] columns = null; // = null; //{ /*"ID",*/ "WEIGHT_TYPE", "AMOUNT", "WEIGHT" };

    protected float[] column_size = null; // = null; //{ /*0.1f,*/ 0.5f, 0.25f, 0.25f  };



    public ATTableData(I18nControlAbstract ic)
    {
	this.ic = ic;
	init();
    }


    public abstract void init();


    public void init(String[] cols, float[] cols_vals)
    {
	this.columns = cols;
	this.column_size = cols_vals;

	//System.out.println("Cols in: " + cols);
	//System.out.println("Cols: " + columns);

    }


    public String getColumnHeader(int column)
    {
	return ic.getMessage(columns[column]);
    }

    public int getColumnWidth(int column, int full_size)
    {
	return (int)(this.column_size[column] * full_size); 
    }


    public int getColumnsCount()
    {
//	System.out.println(this.columns);
	return this.columns.length;
    }

    public abstract String getColumnValue(int column);

}

