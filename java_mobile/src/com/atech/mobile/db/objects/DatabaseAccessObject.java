package com.atech.mobile.db.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class DatabaseAccessObject 
{
    
    int changed_what = 0;
    int changed_object = 0;
    
    
    public static final int OBJECT_NO_ACTION = 0;
    public static final int OBJECT_ADD = 1;
    public static final int OBJECT_EDIT = 2;
    public static final int OBJECT_DELETE = 4;
    
    public static final int CHANGED_NOTHING = 0;

    
    /**
     * dbAdd - add entry to database
     * 
     * @param conn
     * @return
     * @throws Exception
     */
    public abstract boolean dbAdd(Connection conn) throws Exception;
    
    /**
     * dbEdit - edit entry to database
     * 
     * @param conn
     * @return
     * @throws Exception
     */
    public abstract boolean dbEdit(Connection conn) throws Exception;

    /**
     * dbHasChildren - if entry has children
     * 
     * @param conn
     * @return
     * @throws Exception
     */
    public abstract boolean dbHasChildren(Connection conn) throws Exception;
    
    /**
     * dbDelete - delete database entry
     * 
     * @param conn
     * @throws Exception
     */
    public boolean dbDelete(Connection conn) throws Exception
    {
        return this.executeDb(conn, 
                       "delete from " + this.getTableName() + " where " + this.getIdColumnName() + 
                       "=" + this.getObjectUniqueId());
    }

    
    /**
     * dbGet - get data from database entry
     * 
     * @param conn
     * @throws Exception
     */
    public void dbGet(Connection conn) throws Exception
    {
        try
        {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from "+ this.getTableName() + " where " + this.getIdColumnName() + "=" + this.getObjectUniqueId());
            
            if (rs.next())
            {
                this.dbGet(rs);
            }
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    
    /**
     * dbGet - get data from database entry
     * 
     * @param rs
     * @throws Exception
     */
    public abstract void dbGet(ResultSet rs) throws Exception;

    
    /**
     * dbCreate - create table in database
     * 
     * @param conn
     * @return
     * @throws Exception
     */
    public abstract boolean dbCreate(Connection conn) throws Exception;
    
    
    /**
     * getTableName - get name of table
     * 
     * @return get name of table
     */
    public abstract String getTableName();
    
    
    /** 
     * getIdColumnName - get name of column containing id
     * @return
     */
    public abstract String getIdColumnName();
    
    
    public String getStringForDb(String val)
    {
        if (val==null)
        {
            return "null";
        }
        else
        {
            return "'" + val + "'";
        }
        
    }
    
    
    
    
    public String getNextId(Connection conn)
    {
        if ((this.getObjectUniqueId()==null) ||
            (this.getObjectUniqueId().length()==0))
        {
            return "" + getNextIdInternal(conn);
        }
        else
        {
            long id;
            try
            {
                id = Long.parseLong(this.getObjectUniqueId());
                
                if (id<=0)
                {
                    return "" + getNextIdInternal(conn);
                }

                return this.getObjectUniqueId();
            }
            catch(Exception ex)
            {
            }
            
            return "" + getNextIdInternal(conn);
        }
        
    }


    public long getNextIdInternal(Connection conn)
    {
        
        try
        {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select max(" + this.getIdColumnName() + ") from "+ this.getTableName());
            
            if (rs.next())
            {
                long id = rs.getLong(1);
                id++;
                
                return id;
            }
            else
                return 1;
            
        }
        catch(Exception ex)
        {
            return 1;
        }
        
        
    }
    
    /**
     * getObjectUniqueId - get unique id of this object
     * 
     * @return id as String
     */
    public abstract String getObjectUniqueId();
    
    
    public boolean executeDb(Connection conn, String sql) throws Exception
    {
        try
        {
            Statement st = conn.createStatement();
            st.execute(sql);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        
        return true;
        
    }
    
    
    /**
     * getObjectName - name of object
     * 
     * @return
     */
    public abstract String getObjectName();
    
    
    public int getWhatChanged()
    {
        return changed_what;
    }
    

    public void setWhatChanged(int change)
    {
        this.changed_what = change;
        this.changed_object = DatabaseAccessObject.OBJECT_EDIT;
    }
    
    
    
    public boolean hasChanged()
    {
        return (this.changed_what !=0);
    } 
    
    
    
    
    /// <summary>
    /// Sets tags if variable changed
    /// </summary>
    /// <param name="changeValue"></param>
    public void SetChangedTag(int changeValue)
    {
        this.changed_what |= changeValue;
    }


    /// <summary>
    /// Check if value changed, sets tags and return value (either old or new if new value is 
    /// different. (For Int32 types)
    /// </summary>
    /// <param name="current"></param>
    /// <param name="newone"></param>
    /// <param name="changeValue"></param>
    /// <returns></returns>
    public int setChangedValueInt(int current, int newone, int changeValue)
    {
        if (current != newone)
        {
            this.SetChangedTag(changeValue);
            return newone;
        }
        else
            return current;
    }

    /// <summary>
    /// Check if value changed, sets tags and return value (either old or new if new value is 
    /// different. (For Int32 types)
    /// </summary>
    /// <param name="current"></param>
    /// <param name="newone"></param>
    /// <param name="changeValue"></param>
    /// <returns></returns>
    /*public DateTime SetChangedValueDateTime(DateTime current, DateTime newone, int changeValue)
    {
        if (current != newone)
        {
            //current = newone;
            this.SetChangedTag(changeValue);
            return newone;
        }
        else
            return current;
    }*/



    /// <summary>
    /// Check if value changed, sets tags and return value (either old or new if new value is 
    /// different. (For Int64 types)
    /// </summary>
    /// <param name="current"></param>
    /// <param name="newone"></param>
    /// <param name="changeValue"></param>
    /// <returns></returns>
    public long setChangedValueLong(long current, long newone, int changeValue)
    {
        if (current != newone)
        {
            this.SetChangedTag(changeValue);
            return newone;
        }
        else
            return current;
    }


    /// <summary>
    /// Check if value changed, sets tags and return value (either old or new if new value is 
    /// different. (For string types)
    /// </summary>
    /// <param name="current"></param>
    /// <param name="newone"></param>
    /// <param name="changeValue"></param>
    /// <returns></returns>
    public String setChangedValueString(String current, String newone, int changeValue)
    {
        if (current != newone)
        {
            this.SetChangedTag(changeValue);
            return newone;
        }
        else
            return current;
    }


    /// <summary>
    /// Check if value changed, sets tags and return value (either old or new if new value is 
    /// different. (For string types)
    /// </summary>
    /// <param name="current"></param>
    /// <param name="newone"></param>
    /// <param name="changeValue"></param>
    /// <returns></returns>
    public boolean setChangedValueBool(boolean current, boolean newone, int changeValue)
    {
        if (current != newone)
        {
            //current = newone;
            this.SetChangedTag(changeValue);
            return newone;
        }
        else
            return current;
    }

    
    /// <summary>
    /// Returns true if value for specified variable changed.
    /// </summary>
    /// <param name="changeValue"></param>
    /// <returns></returns>
    public boolean hasValueChanged(int changeValue)
    {
        if ((this.changed_what & changeValue) == changeValue)
            return true;
        else
            return false;
    }
    
}
