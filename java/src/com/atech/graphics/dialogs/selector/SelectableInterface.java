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


public interface SelectableInterface extends Comparable<SelectableInterface>
{

	
	/**
	 * Get Item Id
	 * 
	 * @return id of item
	 */
	public abstract long getItemId();
	
	
	/**
	 * Get Short Description of object
	 * 
	 * @return short description
	 */
	public abstract String getShortDescription();
	
	
	
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
     * getColumnValueObject - return value of specified column
     * 
     * @param num number of column
     * @return string value of column
     */
    public abstract Object getColumnValueObject(int num);



    /**
     * getColumnWidth - return width of specified column
     * 
     * @param num number of column
     * @param width total width of table
     * @return width in int of column
     */
    public int getColumnWidth(int num, int width);


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
     * @param value we searching for
     * @return true if object is correct, fakse if not.
     */
    public abstract boolean isFound(int value);


    /**
     * isFound(int,int,int) - if this object is filtered or not.
     * 
     * @param from date received from DateComponent
     * @param till date received from DateComponent
     * @param state 0 = none selected, 1=from used, 2=till used, 3=both used
     * @return true if object is correct, false if not.
     */
    public abstract boolean isFound(int from, int till, int state);


    /**
     * setSearchContext - set context for searching
     */
    public abstract void setSearchContext();



    /**
     * getColumnDefinitions - returns types of columns in String
     */
    //public abstract String[] getColumnDefinitions();



    /**
     * setColumnSorter - sets class that will help with column sorting
     * 
     * @param cs ColumnSorter instance
     */
    public abstract void setColumnSorter(ColumnSorter cs);




    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *		is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */
    public abstract int compareTo(SelectableInterface o);




}


