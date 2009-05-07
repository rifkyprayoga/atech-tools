package com.atech.update.web.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.atech.update.web.data.Application;
import com.atech.update.web.data.ApplicationComponent;
import com.atech.update.web.data.ApplicationGroup;
import com.atech.update.web.data.ApplicationVersion;
import com.atech.update.web.data.ApplicationVersionComponent;
import com.atech.update.web.data.ComponentElement;
import com.atech.update.web.data.ComponentVersion;
import com.atech.update.web.data.ComponentVersionSpecial;


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

// STUB ONLY. Not implemented
// Will be used by Animas (Pump) in future

public class DataLayerUpdateServlet 
{

    public Hashtable<String,Application> apps = null;
    public Hashtable<Long,Application> apps_ids = null;
    
    public Hashtable<Long,ComponentElement> components = null;
    
    protected String jdbc_url = null;
    protected String db_class_name = null;
    Connection m_connection = null;
    
    protected String username = null;
    protected String password = null;
    
    public static DataLayerUpdateServlet s_dl;
    
    //public static final String DB_CLASS_MS_ACCESS_MDB_TOOLS = "mdbtools.jdbc.Driver";
    //public static final String DB_CLASS_MS_ACCESS_JDBC_ODBC_BRIDGE = "sun.jdbc.odbc.JdbcOdbcDriver";
    //public static final String URL_MS_ACCESS_MDB_TOOLS = "mdbtools.jdbc.Driver";
    //public static final String URL_MS_ACCESS_JDBC_ODBC_BRIDGE = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=%FILENAME%";
    

    public static DataLayerUpdateServlet getInstance()
    {
        if (DataLayerUpdateServlet.s_dl==null)
            DataLayerUpdateServlet.s_dl = new DataLayerUpdateServlet();
        
        return DataLayerUpdateServlet.s_dl;
    }
    
    
    /**
     * Constructor
     */
    private DataLayerUpdateServlet()
    {
        this.setJDBCConnection("org.postgresql.Driver", 
            "jdbc:postgresql://localhost:5432/update_server?user=upd_srv&password=upd_srv", "upd_srv", "upd_srv");
        init();
    }


    public void init()
    {
        this.loadComponents();
        this.loadComponentVersions();
        
        this.loadApplications();
        this.loadApplicationVersions();
        this.loadApplicationVersionComponents();
        this.loadApplicationGroups();
        this.processDependencies();
        
    }

    
    public void loadApplications()
    {
        
        apps = new Hashtable<String,Application>();
        apps_ids = new Hashtable<Long,Application>();

        String sql = null;
        try
        {
            sql = "select id, name, description from application";
            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                Application app = new Application();
                app.id = rs.getLong("id");
                app.name = rs.getString("name");
                app.desc = rs.getString("description");
                
                apps.put(app.name, app);
                apps_ids.put(app.id, app);
            }
            
            
            
        }
        catch(Exception ex)
        {
            System.out.println("loadApplications()\nSQL: " + sql + "\nException: " + ex);
        }
        
        
    }
    
    public Application getApplication(String name)
    {
        
        
        if (this.apps.containsKey(name))
            return this.apps.get(name);
        else
            return null;
    }
    
    
    public void loadApplicationVersions()
    {
//        Hashtable<Long,Application> components = new Hashtable<Long,Application>(); 

//        Hashtable<Long,Application> components = new Hashtable<Long,Application>(); 
        String sql = null;
        try
        {
            sql = "select id, application_id, version, version_num, db_version from application_version";
            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                ApplicationVersion app_ver = new ApplicationVersion();
                app_ver.id = rs.getLong("id");
                app_ver.application_id = rs.getLong("application_id");
                app_ver.version = rs.getString("version");
                app_ver.version_num = rs.getLong("version_num");
                app_ver.db_version = rs.getInt("db_version");
                
                apps_ids.get(app_ver.application_id).versions.add(app_ver);
            }
            
        }
        catch(Exception ex)
        {
            System.out.println("loadApplicationVersions()\nSQL: " + sql + "\nException: " + ex);
        }
        
    }
    

    public void loadApplicationComponents()
    {
        ArrayList<ApplicationComponent> lst = new ArrayList<ApplicationComponent>();

        String sql = null;
        try
        {
            sql = "select id, application_id, component_id, comp_order, comp_group, first_version from application_component";
            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                ApplicationComponent app_comp = new ApplicationComponent();
                app_comp.id = rs.getLong("id");
                app_comp.application_id = rs.getLong("application_id");
                app_comp.component_id = rs.getLong("component_id");
                app_comp.component_order = rs.getInt("comp_order");
                app_comp.component_group = rs.getInt("comp_group");
                app_comp.first_version = rs.getInt("first_version");
                
                lst.add(app_comp);
            }

            for(Enumeration<Application> en=this.apps.elements(); en.hasMoreElements(); )
            {
                en.nextElement().loadApplicationComponents(lst);
            }
        }
        catch(Exception ex)
        {
            System.out.println("loadApplicationVersions()\nSQL: " + sql + "\nException: " + ex);
        }
        
    }
    
    
    public void loadApplicationVersionComponents()
    {
        ArrayList<ApplicationVersionComponent> lst = new ArrayList<ApplicationVersionComponent>();
        
        String sql = null;
        try
        {
            sql = "select id, version_id, component_id, component_version from application_version_component";
            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                ApplicationVersionComponent app_ver_comp = new ApplicationVersionComponent();
                app_ver_comp.id = rs.getLong("id");
                app_ver_comp.version_id = rs.getLong("version_id");
                app_ver_comp.component_id = rs.getLong("component_id");
                app_ver_comp.component_version = rs.getLong("component_version");
                
                lst.add(app_ver_comp);
            }
            
            for(Enumeration<Application> en=this.apps.elements(); en.hasMoreElements(); )
            {
                Application app = en.nextElement();
                
                for(int i=0; i<app.versions.size(); i++)
                {
                    app.versions.get(i).loadApplicationVersionComponents(lst, this);
                }
            }
            
        }
        catch(Exception ex)
        {
            System.out.println("loadApplicationVersionComponents()\nSQL: " + sql + "\nException: " + ex);
        }
        
    }
    
    
    public void processDependencies()
    {
        // TODO
        /*
        for(Enumeration<Application> en=this.apps.elements(); en.hasMoreElements(); )
        {
            Application app = en.nextElement();
            
            for(int i=0; i<app.versions.size(); i++)
            {
                app.versions.get(i).loadApplicationVersionComponents(lst, this);
            }
        }*/
        
        
    }
    
    public void loadApplicationGroups()
    {
        ArrayList<ApplicationGroup> groups = new ArrayList<ApplicationGroup>();
        
        String sql = null;
        try
        {
            sql = "select id, application_id, group_id, group_name, first_version from application_group";
            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                ApplicationGroup app_grp = new ApplicationGroup();
                app_grp.id = rs.getLong("id");
                app_grp.group_id = rs.getInt("group_id");
                app_grp.group_name = rs.getString("group_name");
                app_grp.first_version = rs.getLong("first_version");
                
                groups.add(app_grp);
            }
            
            for(Enumeration<Application> en=this.apps.elements(); en.hasMoreElements(); )
            {
                en.nextElement().loadGroups(groups);
            }
            
        }
        catch(Exception ex)
        {
            System.out.println("loadApplications()\nSQL: " + sql + "\nException: " + ex);
        }
        
        
    }
    
    

    public void loadComponents()
    {
        components = new Hashtable<Long,ComponentElement>();

        String sql = null;
        try
        {
            sql = "select id, name, target_dir from component";
            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                ComponentElement comp = new ComponentElement();
                comp.id = rs.getLong("id");
                comp.name = rs.getString("name");
                comp.target_dir = rs.getString("target_dir");
                
                this.components.put(comp.id, comp);
            }

        }
        catch(Exception ex)
        {
            System.out.println("loadApplicationVersions()\nSQL: " + sql + "\nException: " + ex);
        }
        
    }
    
    
    
    public void loadComponentVersions()
    {
        
        ArrayList<ComponentVersion> lst = new ArrayList<ComponentVersion>();

        String sql = null;
        try
        {
            sql = ComponentVersion.SQL_SELECT; 

            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                ComponentVersion cv = new ComponentVersion(rs);
                lst.add(cv);
            }

            ArrayList<ComponentVersionSpecial> cvses = getComponentVersionSpecials();
            
            if (cvses!=null)
            {
                for(int i=0; i<lst.size(); i++)
                {
                    lst.get(i).loadComponentVersionSpecial(cvses);
                }
            }
            
            for(Enumeration<ComponentElement> en = this.components.elements(); en.hasMoreElements(); )
            {
                en.nextElement().loadComponentVersions(lst);
            }
            
            
        }
        catch(Exception ex)
        {
            System.out.println("loadComponentVersions()\nSQL: " + sql + "\nException: " + ex);
        }
        
    }
    
    
    
    public ArrayList<ComponentVersionSpecial> getComponentVersionSpecials()
    {
        
        ArrayList<ComponentVersionSpecial> lst = new ArrayList<ComponentVersionSpecial>();

        String sql = null;
        try
        {
            sql = "select id, component_version_id, os_type, jar from component_version_special";
            ResultSet rs = this.executeQuery(sql);
            
            while(rs.next())
            {
                ComponentVersionSpecial cvs = new ComponentVersionSpecial();
                cvs.id = rs.getLong("id");
                cvs.os_type = rs.getString("name");
                cvs.jar = rs.getString("target_dir");
                
                lst.add(cvs);
            }
            
            if (lst.size()==0)
                return null;
            else
                return lst;

        }
        catch(Exception ex)
        {
            System.out.println("loadApplicationVersions()\nSQL: " + sql + "\nException: " + ex);
            return null;
        }
        
    }
    
    
    
    
    
    
    


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
        
            if ((username==null) && (this.password==null))
                this.m_connection = DriverManager.getConnection(this.jdbc_url);
            else
                this.m_connection = DriverManager.getConnection(this.jdbc_url, this.username, this.password);
        }
        catch(Exception ex)
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
            if ((m_connection==null) || 
                (m_connection.isClosed()))
                createConnection();
        }
        catch(Exception ex)
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
    

    public void showDebug()
    {
        
    }
    
    public static void main(String[] args)
    {
        DataLayerUpdateServlet dlus = new DataLayerUpdateServlet();
        dlus.showDebug();
        
        System.out.println("Done !!!");
    }
    
    
}
