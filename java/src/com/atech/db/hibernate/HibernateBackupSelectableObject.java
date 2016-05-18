package com.atech.db.hibernate;

import org.apache.commons.lang.StringUtils;

import com.atech.graphics.dialogs.selector.ColumnSorter;
import com.atech.graphics.dialogs.selector.SelectableInterface;

/**
 * @author Hibernate CodeGenerator 
 */
public abstract class HibernateBackupSelectableObject extends HibernateBackupObject implements SelectableInterface
{

    private static final long serialVersionUID = 2376980068384414745L;


    /**
     * {@inheritDoc}
     */
    public boolean isFound(String text)
    {
        if (StringUtils.isBlank(text))
        {
            return true;
        }
        else
        {
            return isFoundString(text);
        }
    }


    /**
     * Is Found for string (just real search part)
     *
     * @param findString string to find
     * @return
     */
    public abstract boolean isFoundString(String findString);


    /**
     * {@inheritDoc}
     */
    public boolean isFound(int value)
    {
        return true;
    }


    /**
     * {@inheritDoc}
     */
    public boolean isFound(int from, int till, int state)
    {
        return true;
    }


    /**
     * {@inheritDoc}
     */
    public void setSearchContext()
    {

    }


    /**
     * {@inheritDoc}
     */
    public void setColumnSorter(ColumnSorter cs)
    {

    }


    /**
     * {@inheritDoc}
     */
    public long getItemId()
    {
        return this.getId();
    }


    /**
     * {@inheritDoc}
     */
    public String getShortDescription()
    {
        return null;
    }

}
