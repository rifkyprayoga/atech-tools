package com.atech.db.datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// TODO: Auto-generated Javadoc
/**
 *  Application:   GGC - GNU Gluco Control
 *  Plug-in:       GGC PlugIn Base (base class for all plugins)
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
 *  Filename:     DatabaseProtocol  
 *  Description:  This is implementation for Database protocol. It contains methods for looking
 *                through database files
 * 
 *  Author: Andy {andy@atech-software.com}
 */

public class DataLayerJDBCAbstract
{

    /**
     * The jdbc_url.
     */
    protected String jdbc_url = null;

    /**
     * The db_class_name.
     */
    protected String db_class_name = null;

    /**
     * The m_connection.
     */
    Connection m_connection = null;

    /**
     * The username.
     */
    protected String username = null;

    /**
     * The password.
     */
    protected String password = null;

    // public static final String DB_CLASS_MS_ACCESS_MDB_TOOLS =
    // "mdbtools.jdbc.Driver";
    // public static final String DB_CLASS_MS_ACCESS_JDBC_ODBC_BRIDGE =
    // "sun.jdbc.odbc.JdbcOdbcDriver";
    // public static final String URL_MS_ACCESS_MDB_TOOLS =
    // "mdbtools.jdbc.Driver";
    // public static final String URL_MS_ACCESS_JDBC_ODBC_BRIDGE =
    // "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=%FILENAME%";

    /**
     * Set JDBC Connection 
     * @param db_class_name class name for database
     * @param _jdbc_url full jdbc url, if user and password used, they must be part of url
     */
    public void setJDBCConnection(String db_class_name, String _jdbc_url)
    {
        this.db_class_name = db_class_name;
        this.jdbc_url = _jdbc_url;
    }

    /**
     * Set JDBC Connection 
     * @param db_class_name class name for database
     * @param _jdbc_url full jdbc url, if user and password used, they must be part of url
     * @param user 
     * @param pass 
     */
    public void setJDBCConnection(String db_class_name, String _jdbc_url, String user, String pass)
    {
        this.db_class_name = db_class_name;
        this.jdbc_url = _jdbc_url;
        this.username = user;
        this.password = pass;
    }

    private void createConnection()
    {
        try
        {
            Class.forName(this.db_class_name);

            if (username == null && this.password == null)
            {
                this.m_connection = DriverManager.getConnection(this.jdbc_url);
            }
            else
            {
                this.m_connection = DriverManager.getConnection(this.jdbc_url, this.username, this.password);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error creating connection. Ex: " + ex);
            ex.printStackTrace();
        }

    }

    /**
     * Get Connection - returns opened connection, if none exists, new is created.
     * 
     * @return
     */
    public Connection getConnection()
    {
        try
        {
            if (m_connection == null || m_connection.isClosed())
            {
                createConnection();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error on getConnection. Ex: " + ex);
        }

        return this.m_connection;
    }

    /**
     * Execute Query, return ResultSet.
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    public ResultSet executeQuery(String sql) throws Exception
    {
        Statement st = getConnection().createStatement();
        return st.executeQuery(sql);
    }

    /**
     * Execute Update, returns row count, for statments returning something or 0 for thoose 
     *      that return nothing.
     *      
     * @param sql
     * @return
     * @throws Exception
     */
    public int executeUpdate(String sql) throws Exception
    {
        Statement st = getConnection().createStatement();
        return st.executeUpdate(sql);
    }

    /**
     * Show debug.
     */
    public void showDebug()
    {

    }

}
