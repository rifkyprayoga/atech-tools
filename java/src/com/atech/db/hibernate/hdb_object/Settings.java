package com.atech.db.hibernate.hdb_object;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atech.db.hibernate.DatabaseObjectHibernate;
import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
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


public class Settings extends SettingsH implements DatabaseObjectHibernate, BackupRestoreObject
{

    private static final long serialVersionUID = 5529914498405458725L;

    private boolean debug = false;
    private boolean edited = false;
    private boolean added = false;
    private I18nControlAbstract ic;
    private boolean backup_object = false;

    /**
     * Constructor
     */
    public Settings()
    {
    }

    /**
     * Constructor
     * 
     * @param ic
     */
    public Settings(I18nControlAbstract ic)
    {
        this.ic = ic;
        this.backup_object = true;
    }

    /**
     * Constructor
     * 
     * @param ch
     */
    public Settings(SettingsH ch)
    {
        this.setId(ch.getId());
        this.setKey(ch.getKey());
        this.setValue(ch.getValue());
        this.setType(ch.getType());
        this.setDescription(ch.getDescription());
    }

    /**
     * Get Short Description
     * 
     * @return
     */
    public String getShortDescription()
    {
        return "Settings [Key=" + this.getKey() + ";Value=" + this.getValue() + "]";
    }

    /**
     * To String
     * 
     * @see ggc.core.db.hibernate.SettingsH#toString()
     */
    @Override
    public String toString()
    {
        if (backup_object)
            return this.getTargetName();
        else
            return this.getShortDescription();
    }

    /**
     * Set Element Edited
     */
    public void setElementEdited()
    {
        this.edited = true;
    }

    
    /**
     * Set Element Added
     */
    public void setElementAdded()
    {
        this.added = true;
    }

    /**
     * Is Element Edited
     * @return
     */
    public boolean isElementEdited()
    {
        return this.edited;
    }

    /**
     * Is Element Added
     * 
     * @return
     */
    public boolean isElementAdded()
    {
        return this.added;
    }

    // ---
    // --- DatabaseObjectHibernate
    // ---

    /**
     * DbAdd - Add this object to database
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return id in type of String
     */
    public String DbAdd(Session sess) throws Exception
    {

        Transaction tx = sess.beginTransaction();

        SettingsH ch = new SettingsH();

        ch.setKey(this.getKey());
        ch.setValue(this.getValue());
        ch.setType(this.getType());
        ch.setDescription(this.getDescription());

        Long id = (Long) sess.save(ch);

        tx.commit();

        this.setId(id.longValue());
        
        return "" + id.longValue();

    }

    /**
     * DbEdit - Edit this object in database
     * 
     * @param sess
     *            Hibernate Session object
     * @throws Exception
     *             (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbEdit(Session sess) throws Exception
    {

        Transaction tx = sess.beginTransaction();

        SettingsH ch = (SettingsH) sess.get(SettingsH.class, new Long(this
                .getId()));

        ch.setId(this.getId());
        ch.setKey(this.getKey());
        ch.setValue(this.getValue());
        ch.setType(this.getType());
        ch.setDescription(this.getDescription());

        sess.update(ch);
        tx.commit();

        return true;

    }

    /**
     * DbDelete - Delete this object in database
     * 
     * @param sess
     *            Hibernate Session object
     * @throws Exception
     *             (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbDelete(Session sess) throws Exception
    {

        Transaction tx = sess.beginTransaction();

        SettingsH ch = (SettingsH) sess.get(SettingsH.class, new Long(this
                .getId()));

        sess.delete(ch);
        tx.commit();

        return true;

    }

    /**
     * DbHasChildren - Shows if this entry has any children object, this is
     * needed for delete
     * 
     * 
     * @param sess
     *            Hibernate Session object
     * @throws Exception
     *             (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbHasChildren(Session sess) throws Exception
    {
        return false;
    }

    /**
     * DbGet - Loads this object. Id must be set.
     * 
     * 
     * @param sess
     *            Hibernate Session object
     * @throws Exception
     *             (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbGet(Session sess) throws Exception
    {

        SettingsH ch = (SettingsH) sess.get(SettingsH.class, new Long(this
                .getId()));

        this.setId(ch.getId());
        this.setKey(ch.getKey());
        this.setValue(ch.getValue());
        this.setType(ch.getType());
        this.setDescription(ch.getDescription());

        return true;
    }

    /**
     * getObjectName - returns name of DatabaseObject
     * 
     * @return name of object (not Hibernate object)
     */
    public String getObjectName()
    {
        return "Settings";
    }

    /**
     * isDebugMode - returns debug mode of object
     * 
     * @return true if object in debug mode
     */
    public boolean isDebugMode()
    {
        return debug;
    }

    /**
     * getAction - returns action that should be done on object 0 = no action 1
     * = add action 2 = edit action 3 = delete action This is used mainly for
     * objects, contained in lists and dialogs, used for processing by higher
     * classes (classes calling selectors, wizards, etc...
     * 
     * @return number of action
     */
    public int getAction()
    {
        return 0;
    }

    // ---
    // --- BackupRestoreObject
    // ---

    private boolean selected = false;

    /**
     * getTargetName
     */
    public String getTargetName()
    {
        return ic.getMessage("SETTINGS");
    }

    
    /**
     * Get Class Name
     * 
     * @see com.atech.db.hibernate.transfer.BackupRestoreBase#getClassName()
     */
    public String getClassName()
    {
        return "ggc.core.db.hibernate.SettingsH";
    }
    
    
    
    /**
     * Get Name
     * 
     * @return return name
     */
    public String getName()
    {
        return this.getTargetName();
    }

    // ---
    // --- BackupRestoreObject
    // ---

    /**
     * Get Children
     * 
     * @return 
     */
    public ArrayList<CheckBoxTreeNodeInterface> getChildren()
    {
        return null;
    }

    /**
     * Is Selected
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * Set Selected
     */
    public void setSelected(boolean newValue)
    {
        this.selected = newValue;
    }

    /**
     * Is Collection
     * 
     * @see com.atech.db.hibernate.transfer.BackupRestoreBase#isCollection()
     */
    public boolean isCollection()
    {
        return false;
    }

    /**
     * Has Children
     * 
     * @return 
     */
    public boolean hasChildren()
    {
        return false;
    }

    /**
     * getObjectUniqueId - get id of object
     * 
     * @return unique object id
     */
    public String getObjectUniqueId()
    {
        return "" + this.getId();
    }
    

    /**
     * Table Version
     */
    public int TABLE_VERSION = 1;
    
    
    /**
     * getTableVersion - returns version of table
     * 
     * @return version information
     */
    public int getTableVersion()
    {
        return this.TABLE_VERSION;
    }
    
    
    /**
     * dbExport - returns export String, for current version 
     *
     * @return line that will be exported
     * @throws Exception if export for table is not supported
     */
    public String dbExport(int table_version) throws Exception
    {
        // TODO
        return null;
    }

    
    /**
     * dbExport - returns export String, for current version 
     *
     * @return line that will be exported
     * @throws Exception if export for table is not supported
     */
    public String dbExport() throws Exception
    {
        return dbExport(this.TABLE_VERSION);
    }
    
    
    /**
     * dbExportHeader - header for export file
     * 
     * @param table_version
     * @return
     */
    public String dbExportHeader(int table_version)
    {
        // TODO
        return null;
    }
    

    /**
     * dbExportHeader - header for export file
     * 
     * @return
     */
    public String dbExportHeader()
    {
        return this.dbExportHeader(this.TABLE_VERSION);
    }
    
    
    /**
     * dbImport - processes input entry to right fields
     * 
     * @param table_version version of table
     * @param value_entry whole import line
     * @throws Exception if import for selected table version is not supported or it fails
     */
    public void dbImport(int table_version, String value_entry) throws Exception
    {
        dbImport(table_version, value_entry, null);
    }
    

    /**
     * dbImport - processes input entry to right fields
     * 
     * @param table_version version of table
     * @param value_entry whole import line
     * @param parameters parameters
     * @throws Exception if import for selected table version is not supported or it fails
     */
    public void dbImport(int table_version, String value_entry, Object[] parameters) throws Exception
    {
        // TODO
    }
    
    
    /**
     * getBackupFile - name of backup file (base part)
     * 
     * @return
     */
    public String getBackupFile()
    {
        return "SettingsH";
    }
    
    /**
     * getBackupClassName - name of class which will be updated/restored
     * 
     * @return
     */
    public String getBackupClassName()
    {
        return "ggc.core.db.hibernate.SettingsH";
    }

    
    /**
     * Has To Be Clean - if table needs to be cleaned before import
     * 
     * @return true if we need to clean
     */
    public boolean hasToBeCleaned()
    {
        return true;
    }

    /** 
     * Get Node Children
     */
    public ArrayList<CheckBoxTreeNodeInterface> getNodeChildren()
    {
        return null;
    }

    /** 
     * Has Node Children
     */
    public boolean hasNodeChildren()
    {
        return false;
    }
    
}
