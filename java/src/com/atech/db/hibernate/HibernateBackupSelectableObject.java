package com.atech.db.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.graphics.dialogs.selector.ColumnSorter;
import com.atech.graphics.dialogs.selector.SelectableInterface;
import com.atech.i18n.I18nControlAbstract;

/**
 * @author Hibernate CodeGenerator 
 */
public abstract class HibernateBackupSelectableObject extends HibernateBackupObject implements SelectableInterface
{

    private static final long serialVersionUID = 2376980068384414745L;

    protected DataDefinitionEntry typeDisplayDefintion;
    protected I18nControlAbstract i18nControl;


    public HibernateBackupSelectableObject()
    {
        if (typeDisplayDefintion == null)
        {
            initTypeDisplayDefintion();
        }

    }


    public HibernateBackupSelectableObject(I18nControlAbstract i18nControl)
    {
        this.i18nControl = i18nControl;
        if (typeDisplayDefintion == null)
        {
            initTypeDisplayDefintion();
        }
    }


    protected abstract void initTypeDisplayDefintion();


    public int getColumnCount()
    {
        return typeDisplayDefintion.getColumnCount();
    }


    public String getColumnName(int index)
    {
        return typeDisplayDefintion.getColumnName(index);
    }


    public int getColumnWidth(int index, int width)
    {
        return typeDisplayDefintion.getColumnWidth(index, width);
    }


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


    /**
     * To String Descriptive. It shows a little bit of data about object (used for delete). So for example
     * you would description as something like that. DoctorH [id=23,name=John Doe, type=pediatrician].
     */
    public abstract String toStringDescriptive();


    protected String getBaseForDescriptiveString()
    {
        return this.getClass().getSimpleName() + " [id=" + this.getId() + ",%s]";
    }


    public abstract Criteria getChildrenCriteria(Session session, HibernateBackupSelectableObject object);


    /**
     * {@inheritDoc}
     */
    public String getTargetName()
    {
        return i18nControl.getMessage(this.typeDisplayDefintion.getBackupTargetName());
    }


    /**
     * {@inheritDoc}
     */
    public int getTableVersion()
    {
        return this.typeDisplayDefintion.getTableVersion();
    }
}
