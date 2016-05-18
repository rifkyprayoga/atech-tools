package com.atech.db.hibernate;

import java.util.ArrayList;

import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
import com.atech.utils.ATDataAccessAbstract;

/**
 * @author Hibernate CodeGenerator 
 */
public abstract class HibernateBackupObject extends HibernateObject implements BackupRestoreObject
{

    private static final long serialVersionUID = 425248014017307063L;
    boolean selectedItem = false;

    public static final String DELIMITER = "$#|#$";


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
     */
    protected abstract String getColumnNames(int tableVersion);


    /**
     * Get Splitted Values for Import
     * 
     * @param valueEntry value Line
     * @return array of String
     */
    protected String[] getSplittedValues(String valueEntry)
    {
        valueEntry = ATDataAccessAbstract.replaceExpression(valueEntry, DELIMITER + DELIMITER,
            DELIMITER + " " + DELIMITER);
        return ATDataAccessAbstract.splitString(valueEntry, DELIMITER);
    }

}
