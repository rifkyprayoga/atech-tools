package com.atech.graphics.dialogs.selector;

import java.text.Collator;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccess;

// TODO: Auto-generated Javadoc
/**
 * * This file is part of ATech Tools library.
 * 
 * ColumnSorter - Column Sorter class
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

public class ColumnSorter
{

    /**
     * The sorted column.
     */
    public int sortedColumn = 0;

    /**
     * The sorted ascending.
     */
    public boolean sortedAscending = true;
    @SuppressWarnings("unused")
    private String[] columnDefinitions = null;
    private Collator m_collator = null;

    private boolean active = false;

    /**
     * Checks if is active.
     * 
     * @return true, if is active
     */
    public boolean isActive()
    {
        return this.active;
    }

    /**
     * Sets the active.
     * 
     * @param act the new active
     */
    public void setActive(boolean act)
    {
        this.active = act;
    }

    /**
     * Sets the collator definition.
     * 
     * @param ic the new collator definition
     */
    public void setCollatorDefinition(I18nControlAbstract ic)
    {
        this.m_collator = ic.getCollationDefintion();

    }

    /**
     * setSortColumns(String[]) - set type of columns
     * 
     * @param types Array of types of columns
     */
    public void setColumnDefinitions(String[] types)
    {
        this.columnDefinitions = types;
    }

    /**
     * setSortParameters(int,int) - set sorting parameters
     * 
     * @param column that is sorted
     * @param ascending sorting s ascending
     */
    public void setSortParameters(int column, boolean ascending)
    {
        this.sortedColumn = column;
        this.sortedAscending = ascending;
    }

    /**
     * Compare unicode strings.
     * 
     * @param s1 the s1
     * @param s2 the s2
     * 
     * @return the int
     */
    public int compareUnicodeStrings(String s1, String s2)
    {
        return this.m_collator.compare(s1, s2);
    }

    /**
     * Compare objects.
     * 
     * @param first the first
     * @param second the second
     * 
     * @return the int
     */
    public int compareObjects(SelectableInterface first, SelectableInterface second)
    {

        int res = 0;

        if (first.getColumnValueObject(this.sortedColumn) instanceof String)
        {
            String s1 = (String) first.getColumnValueObject(this.sortedColumn);
            String s2 = (String) second.getColumnValueObject(this.sortedColumn);

            // res = s1.compareToIgnoreCase(s2);

            res = ATDataAccess.getInstance().compareUnicodeStrings(s1, s2);

            // System.out.println("Res: " + res + " s1: " + s1 + " s2: " + s2);

        }
        else if (first.getColumnValueObject(this.sortedColumn) instanceof Integer)
        {
            Integer i1 = (Integer) first.getColumnValueObject(this.sortedColumn);
            Integer i2 = (Integer) second.getColumnValueObject(this.sortedColumn);

            res = i1.compareTo(i2);
        }
        else if (first.getColumnValueObject(this.sortedColumn) instanceof Long)
        {
            Long l1 = (Long) first.getColumnValueObject(this.sortedColumn);
            Long l2 = (Long) second.getColumnValueObject(this.sortedColumn);

            res = l1.compareTo(l2);
        }
        else
            res = 0;

        /*
        	if (first.getColumnValueObject(this.sortedColumn) instanceof String)
        	    //this.columnDefinitions[this.sortedColumn].equals("String"))
        	{
        	    res = first.getColumnValue(this.sortedColumn).compareTo(second.getColumnValue(this.sortedColumn));
        	}
        	else if (this.columnDefinitions[this.sortedColumn].equals("Integer"))
        	{
        	    int f1 = Integer.parseInt(first.getColumnValue(this.sortedColumn));
        	    int f2 = Integer.parseInt(second.getColumnValue(this.sortedColumn));

        	    if (f1<f2)
        		return -1;
        	    else if (f1==f2)
        		return 0;
        	    else
        		return 1;

        	}
        	else if (this.columnDefinitions[this.sortedColumn].equals("Long"))
        	{
        	    long l1 = Long.parseLong(first.getColumnValue(this.sortedColumn));
        	    long l2 = Long.parseLong(second.getColumnValue(this.sortedColumn));

        	    if (l1<l2)
        		return -1;
        	    else if (l1==l2)
        		return 0;
        	    else
        		return 1;
        	}
        */
        if (!this.sortedAscending)
        {
            res = res * (-1);
        }

        return res;

    }

}