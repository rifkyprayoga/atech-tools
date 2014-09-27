package com.atech.db.datalayer;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class DatabaseObject
{

    // public String m_objectName = "DatabaseObject";

    // public long m_id = -1;

    /**
     * DbAdd - Add this object to database
     */
    public abstract PreparedStatement DbAdd(Connection conn) throws Exception;

    /**
     * DbEdit - Edit this object in database
     */
    public abstract PreparedStatement DbEdit(Connection conn) throws Exception;

    /**
     * DbDelete - Delete this object in database
     */
    public abstract PreparedStatement DbDelete(Connection conn) throws Exception;

    /**
     * DbHasChildren - Shows if this entry has any children object, this is needed for delete
     */
    public abstract boolean DbHasChildren(Connection conn) throws Exception;

    /**
     * DbGet - Loads this object. Id must be set.
     */
    public abstract PreparedStatement DbGet(Connection conn) throws Exception;

    /**
     * DbCreateTable - Creates table object (this is unreliable feature)
     */
    public abstract PreparedStatement DbCreateTable(Connection conn);

    /**
     * getObjectName - returns name of DatabaseObject
     */
    public abstract String getObjectName();
    /*
     * {
     * return m_objectName;
     * }
     */

    /**
     * getID - returns ID
     */
    /*
     * public long getID()
     * {
     * return m_id;
     * }
     */

    /**
     * setID - sets ID
     */
    /*
     * public void setID(long id)
     * {
     * m_id = id;
     * }
     */

}
