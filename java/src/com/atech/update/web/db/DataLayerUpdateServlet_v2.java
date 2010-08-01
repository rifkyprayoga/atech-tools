package com.atech.update.web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.atech.db.datalayer.DataLayerJDBCAbstract;

public class DataLayerUpdateServlet_v2 extends DataLayerJDBCAbstract
{

    
    /**
     * The s_dl.
     */
    public static DataLayerUpdateServlet_v2 s_dl;
    
    //public static final String DB_CLASS_MS_ACCESS_MDB_TOOLS = "mdbtools.jdbc.Driver";
    //public static final String DB_CLASS_MS_ACCESS_JDBC_ODBC_BRIDGE = "sun.jdbc.odbc.JdbcOdbcDriver";
    //public static final String URL_MS_ACCESS_MDB_TOOLS = "mdbtools.jdbc.Driver";
    //public static final String URL_MS_ACCESS_JDBC_ODBC_BRIDGE = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=%FILENAME%";
    

    /**
     * Gets the single instance of DataLayerUpdateServlet.
     * 
     * @return single instance of DataLayerUpdateServlet
     */
    public static DataLayerUpdateServlet_v2 getInstance()
    {
        if (DataLayerUpdateServlet_v2.s_dl==null)
            DataLayerUpdateServlet_v2.s_dl = new DataLayerUpdateServlet_v2();
        
        return DataLayerUpdateServlet_v2.s_dl;
    }
    
    
    /**
     * Constructor
     */
    private DataLayerUpdateServlet_v2()
    {
        this.setJDBCConnection("org.postgresql.Driver", 
            "jdbc:postgresql://localhost:5432/update_server_v2?user=upd_srv&password=upd_srv", "upd_srv", "upd_srv");
        init();
    }
    
    
    public void init()
    {
        
    }
    
    
    public String getNextVersionInfo(String product_id, int current_version, int current_db) 
    {
        StringBuilder result = new StringBuilder();
        
        try
        {
            int next_version = 0;
            String next_version_string = null;
            int next_version_db = 0;
            String next_version_db_string = null;
            int max_db_version = 0;
            
            String sql = " SELECT MAX(version_num) as max_id " +
                         " FROM upd_app_version " + 
                         " WHERE product_id='" + product_id + "' and db_version=" + current_db;
            
            ResultSet rs = this.executeQuery(sql);
            
            if (!rs.next())
                return "ERR_NO_SUCH_APP";
            
            next_version = rs.getInt("max_id");
            
            sql = " SELECT MAX(db_version) as max_db_version " + 
                  " FROM upd_app_version " +
                  " WHERE product_id='" + product_id + "'";

            rs = this.executeQuery(sql);

            rs.next();
            max_db_version = rs.getInt("max_db_version");
            

            sql = " SELECT id, version_num, version_name " +
            " FROM upd_app_version " + 
            " WHERE product_id='" + product_id + "' and version_num=" + next_version;

            rs = this.executeQuery(sql);
            rs.next();
            
            result.append("NEXT_VERSION_ID=" + rs.getLong("id"));
            result.append(";NEXT_VERSION=" + rs.getLong("version_num"));
            result.append(";NEXT_VERSION_STR=" + rs.getString("version_name"));
            
            
            if (max_db_version > current_db)
            {
                sql = " SELECT MAX(version_num) as max_id " +
                      " FROM upd_app_version " + 
                      " WHERE product_id='" + product_id + "' and db_version=" + max_db_version;
   
                rs = this.executeQuery(sql);
                rs.next();
   
                next_version_db = rs.getInt("max_id");
                
                sql = " SELECT id, version_num, version_name, db_version " +
                      " FROM upd_app_version " + 
                      " WHERE product_id='" + product_id + "' and version_num=" + next_version_db;

                rs = this.executeQuery(sql);
                rs.next();
                
                result.append(";NEXT_DB_VERSION_ID=" + rs.getLong("id"));
                result.append(";NEXT_DB_VERSION=" + rs.getLong("version_num"));
                result.append(";NEXT_DB_VERSION_STR=" + rs.getString("version_name"));
                result.append(";NEXT_DB_VERSION_DB=" + rs.getLong("db_version"));
                
            }
        
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
            return "ERR_INTERNAL_ERROR";
        }
        
        
        return result.toString();
        
    }
    
    
    public static void main(String[] args)
    {
        DataLayerUpdateServlet_v2 dl = DataLayerUpdateServlet_v2.getInstance();
        
        dl.getNextVersionInfo("ggc", 7, 7);
    }
    
    
    
}
