package com.atech.i18n.tool.client.db.datalayer;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atech.db.hibernate.DatabaseObjectHibernate;
import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.tool.client.DataAccessTT;
import com.atech.i18n.tool.client.db.hibernate.AppModuleH;
import com.atech.i18n.tool.client.db.hibernate.LanguageH;

/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     FoodDescription
 *  Description:  This is DataLayer file (data file, with methods to work with database or in 
 *                this case Hibernate). This one is used for FoodDescriptionH and FoodUserDescriptionH. 
 *                This one is also BackupRestoreObject. 
 *                File is not YET DataLayer File at least not active
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

// TODO: DL

public class AppModuleO extends AppModuleH implements BackupRestoreObject, DatabaseObjectHibernate
{

    private static final long serialVersionUID = -2783864807987398195L;
    private boolean selected = false;
    I18nControlAbstract ic = null; // (I18nControl)DataAccess.getInstance().getI18nControlInstance();
    ArrayList<AppGroupO> list = new ArrayList<AppGroupO>();


    /**
     * Constructor
     */
    public AppModuleO()
    {
    }
    
    
    /**
     * Constructor
     * 
     * @param ch
     */
    public AppModuleO(AppModuleH ch)
    {
        this.setId(ch.getId());
        this.setName(ch.getName());
        this.setDesc(ch.getDesc());
        this.setApp_id(ch.getApp_id());
    }

    
    /**
     * Constructor
     * 
     * @param ic
     */
    public AppModuleO(I18nControlAbstract ic)
    {
        this.ic = ic;
    }
    
    
    //---
    //---  BackupRestoreObject
    //---

    
    
    /**
     * Get Target Name
     * 
     * @see com.atech.db.hibernate.transfer.BackupRestoreBase#getTargetName()
     */
    public String getTargetName()
    {
        return ic.getMessage("PUMP_DATA");
    }

    /** 
     * Get Name
     * @return 
     */
    public String getName()
    {
        return this.getTargetName();
    }

    /**
     * getBackupClassName - name of class which will be updated/restored
     * 
     * @return
     */
    public String getClassName()
    {
        return "ggc.core.db.hibernate.pump.PumpDataH";
    }
    

    /**
     * To String
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return this.getTargetName();
    }
    

    /** 
     * getChildren
     */
    public ArrayList<CheckBoxTreeNodeInterface> getNodeChildren()
    {
        return null;
    }

    /** 
     * isSelected
     */
    public boolean isSelected()
    {
        return selected;
    }

    /** 
     * setSelected
     */
    public void setSelected(boolean newValue)
    {
        this.selected = newValue;
    }
    
    /**
     * Is Object Collection
     * 
     * @return true if it has children
     */
    public boolean isCollection()
    {
        return false;
    }
    
    
    /**
     * Has Children
     */
    public boolean hasNodeChildren()
    {
        return false;
    }
    
    
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

        AppModuleH ch = (AppModuleH) sess.get(AppModuleH.class, new Long(this.getId()));

        ch.setId(this.getId());
        ch.setName(this.getName());
        ch.setDesc(this.getDesc());
        ch.setApp_id(this.getApp_id());

        
        Long id = (Long) sess.save(ch);
        tx.commit();
        ch.setId(id.longValue());
        
        return "" + id.longValue();
    }

    
    /**
     * DbDelete - Delete this object in database
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbDelete(Session sess) throws Exception
    {
        Transaction tx = sess.beginTransaction();

        AppModuleH ch = (AppModuleH) sess.get(AppModuleH.class, new Long(this.getId()));

        sess.delete(ch);
        tx.commit();

        return true;
    }

    
    /**
     * DbEdit - Edit this object in database
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbEdit(Session sess) throws Exception
    {
        Transaction tx = sess.beginTransaction();

        AppModuleH ch = (AppModuleH) sess.get(AppModuleH.class, new Long(this.getId()));

        ch.setId(this.getId());
        ch.setName(this.getName());
        ch.setDesc(this.getDesc());
        ch.setApp_id(this.getApp_id());
        
        sess.update(ch);
        tx.commit();

        return true;
    }

    
    /**
     * DbGet - Loads this object. Id must be set.
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbGet(Session sess) throws Exception
    {
        AppModuleH ch = (AppModuleH) sess.get(AppModuleH.class, new Long(this.getId()));

        this.setId(ch.getId());
        this.setName(ch.getName());
        this.setDesc(ch.getDesc());
        this.setApp_id(ch.getApp_id());
        
        return true;
    }

    
    /**
     * DbHasChildren - Shows if this entry has any children object, this is needed for delete
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbHasChildren(Session sess) throws Exception
    {
        return false;
    }

    /** 
     * getAction
     */
    public int getAction()
    {
        return 0;
    }

    /**
     * Table Version
     */
    public int TABLE_VERSION = 1;
    
    
    /**
     * Get Table Version - returns version of table
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
        // version is ignored for now

        StringBuffer sb = new StringBuffer();

        sb.append(this.getId());
        sb.append("|");
        sb.append(this.getName());
        sb.append("|");
        sb.append(this.getDesc());

        return sb.toString();
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
        return "; Columns: id|name|desc\n" + 
               "; Table version: " + getTableVersion() + "\n";
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
        DataAccessTT da = DataAccessTT.getInstance();

        value_entry = DataAccessTT.getInstance().replaceExpression(value_entry, "||", "| |");
        String[] arr = da.splitString(value_entry, "|");
        
        this.setId(da.getLongValueFromString(arr[0]));
        this.setName(arr[1]);
        this.setDesc(arr[2]);
        
    }
    
    
    
    /**
     * getBackupFile - name of backup file (base part)
     * 
     * @return
     */
    public String getBackupFile()
    {
        return "PumpDataH";
    }
    
    /**
     * getBackupClassName - name of class which will be updated/restored
     * 
     * @return
     */
    public String getBackupClassName()
    {
        return "ggc.core.db.hibernate.pump.PumpDataH";
    }
    
    
    /** 
     * getObjectName
     */
    public String getObjectName()
    {
        return "PumpData";
    }

    /** 
     * isDebugMode
     */
    public boolean isDebugMode()
    {
        return false;
    }
    
    
    
    /**
     * getObjectUniqueId - get id of object
     * @return unique object id
     */
    public String getObjectUniqueId()
    {
        return "";
    }
    
    /**
     * Has To Be Clean - if table needs to be cleaned before import
     * 
     * @return true if we need to clean
     */
    public boolean hasToBeCleaned()
    {
        return false;
    }
   

    /**
     * Returns the child of parent at index index in the parent's child array.
     */
    public AppGroupO getChild(int index) 
    {
        return this.list.get(index);
    }
    

    /**
     * Returns the number of children of parent.
     */
    public int getChildCount() 
    {
        return this.list.size();
    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object child) 
    {
        return this.list.indexOf(child);
    }
    
    
}