package com.atech.db.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.graphics.dialogs.selector.ColumnSorter;
import com.atech.graphics.dialogs.selector.SelectableInterface;
import com.atech.i18n.I18nControlAbstract;

/**
 * Created by andy on 24/02/17.
 */
public abstract class HibernateSelectableObject extends HibernateObject implements SelectableInterface
{

    private static final long serialVersionUID = 3290670973437373355L;

    protected DataDefinitionEntry typeDisplayDefintion;
    protected I18nControlAbstract i18nControl;


    public HibernateSelectableObject()
    {
        // if (typeDisplayDefintion == null)
        // {
        // initTypeDisplayDefintion();
        // }

    }


    public HibernateSelectableObject(I18nControlAbstract i18nControl)
    {
        this.i18nControl = i18nControl;
        // if (typeDisplayDefintion == null)
        // {
        // initTypeDisplayDefintion();
        // }
    }


    public void checkIfInitTypeDisplayDefinitionNeeded()
    {
        if (typeDisplayDefintion == null)
        {
            initTypeDisplayDefinition();
        }
    }


    protected abstract void initTypeDisplayDefinition();


    public int getColumnCount()
    {
        checkIfInitTypeDisplayDefinitionNeeded();
        return typeDisplayDefintion.getColumnCount();
    }


    public String getColumnName(int index)
    {
        checkIfInitTypeDisplayDefinitionNeeded();
        return typeDisplayDefintion.getColumnName(index);
    }


    public int getColumnWidth(int index, int width)
    {
        checkIfInitTypeDisplayDefinitionNeeded();
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
        return toStringDescriptive();
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


    public abstract Criteria getChildrenCriteria(Session session, HibernateSelectableObject object);


    /**
     * {@inheritDoc}
     */
    public String getTargetName()
    {
        checkIfInitTypeDisplayDefinitionNeeded();
        return i18nControl.getMessage(this.typeDisplayDefintion.getBackupTargetName());
    }


    /**
     * {@inheritDoc}
     */
    public int getTableVersion()
    {
        checkIfInitTypeDisplayDefinitionNeeded();
        return this.typeDisplayDefintion.getTableVersion();
    }

}
