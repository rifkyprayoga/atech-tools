package com.atech.db.hibernate;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
import com.atech.i18n.I18nControlAbstract;

public abstract class HibernateBackupObject extends HibernateObject implements BackupRestoreObject
{

    private static final long serialVersionUID = 425248014017307063L;
    boolean selectedItem = false;

    protected DataDefinitionEntry typeDisplayDefintion;
    protected I18nControlAbstract i18nControl;

    public static final String DELIMITER = "$#|#$";


    public HibernateBackupObject()
    {
        if (typeDisplayDefintion == null)
        {
            initTypeDisplayDefintion();
        }

    }


    public HibernateBackupObject(I18nControlAbstract i18nControl)
    {
        this.i18nControl = i18nControl;
        if (typeDisplayDefintion == null)
        {
            initTypeDisplayDefintion();
        }
    }


    protected abstract void initTypeDisplayDefintion();


    public String getBackupClassName()
    {
        return this.getClass().getName();
    }


    public String getBackupFile()
    {
        return this.getClass().getSimpleName();
    }


    public String getClassName()
    {
        return getBackupClassName();
    }


    public ArrayList<CheckBoxTreeNodeInterface> getNodeChildren()
    {
        return null;
    }


    public boolean hasNodeChildren()
    {
        return false;
    }


    public boolean isCollection()
    {
        return false;
    }


    public void setSelected(boolean selected)
    {
        this.selectedItem = selected;
    }


    public boolean isSelected()
    {
        return selectedItem;
    }


    /**
     * {@inheritDoc}
     */
    public void dbImport(int table_version, String value_entry) throws Exception
    {
        dbImport(getTableVersion(), value_entry, (Object[]) null);
    }


    /**
     * {@inheritDoc}
     */
    public void dbImport(int table_version, String value_entry, Object[] parameters) throws Exception
    {
    }


    /**
     * {@inheritDoc}
     */
    public String dbExport() throws Exception
    {
        return dbExport(getTableVersion());
    }


    /**
     * {@inheritDoc}
     */
    public String dbExportHeader()
    {
        return dbExportHeader(getTableVersion());
    }


    /**
     * {@inheritDoc}
     */
    @Deprecated
    public boolean isNewImport()
    {
        return true;
    }


    /**
     * {@inheritDoc}
     */
    public boolean hasToBeCleaned()
    {
        return true;
    }


    public String getDelimiter()
    {
        return DELIMITER;
    }


    public void writeDelimiter(StringBuilder sb)
    {
        sb.append(DELIMITER);
    }


    @Deprecated
    public String dbExportHeader(int tableVersion)
    {
        return "; Columns: " + getColumnNames(tableVersion) + "\n" + //
                "; Table version: " + tableVersion + "\n";
    }


    /**
     * Get Column Names for Export header
     * 
     * @param tableVersion table version
     * @return
     *
     * @Deprecated
     */
    public String getColumnNames(int tableVersion)
    {
        return this.typeDisplayDefintion.getColumnNames();
    }


    /**
     * {@inheritDoc}
     */
    public String getTargetName()
    {
        return i18nControl.getMessage(this.typeDisplayDefintion.getBackupTargetName());
    }

    // Copied from BackupSelectableObject


    /**
     * {@inheritDoc}
     */
    public int getTableVersion()
    {
        return this.typeDisplayDefintion.getTableVersion();
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

}
