package com.atech.graphics.dialogs.selector;

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


public interface SelectableNoSortInterface
{


    /**
     * getColumnCount - return number of displayable columns
     * 
     * @return number of displayable columns
     */
    public abstract int getColumnCount();


    /**
     * getColumnName - return name of specified column
     * 
     * @param num number of column
     * @return string displaying name of column (usually this is I18N version of string
     */
    public abstract String getColumnName(int num);


    /**
     * getColumnValue - return value of specified column
     * 
     * @param num number of column
     * @return string value of column
     */
    public abstract String getColumnValue(int num);


    /**
     * getColumnWidth - return width of specified column
     * 
     * @param num number of column
     * @param width total width of table
     * @return width in int of column
     */
    public abstract int getColumnWidth(int num, int width);


    /**
     * isFound(String) - if this object is filtered or not.
     * 
     * @param text String we search for
     * @return true if object is correct, fakse if not.
     */
    public abstract boolean isFound(String text);


    /**
     * isFound(int) - if this object is filtered or not.
     * 
     * @param value we are searching for
     * @return true if object is correct, false if not.
     */
    public abstract boolean isFound(int value);


    /**
     * isFound(int,int,int) - if this object is filtered or not.
     * 
     * @param from date received from DateComponent
     * @param till date received from DateComponent
     * @param state 0 = none selected, 1=from used, 2=till used, 3=both used
     * @return true if object is correct, fakse if not.
     */
    public abstract boolean isFound(int from, int till, int state);


    /**
     * setSearchContext - set context for searching
     */
    public abstract void setSearchContext();



}


