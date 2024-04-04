package com.atech.update.v3.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.db.datalayer.DataLayerJDBCAbstract;
import com.atech.update.v3.dto.ApplicationNoteDTO;
import com.atech.update.v3.dto.ApplicationUpdateDTO;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <>
 *  Copyright (C) 2010  Andy (Aleksander) Rozman (Atech-Software)
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

public class DataLayerUpdateServletV3 extends DataLayerJDBCAbstract
{

    private static Log LOG = LogFactory.getLog(DataLayerUpdateServletV3.class);

    /**
     * The s_dl.
     */
    public static DataLayerUpdateServletV3 s_dl;


    // public static final String DB_CLASS_MS_ACCESS_MDB_TOOLS =
    // "mdbtools.jdbc.Driver";
    // public static final String DB_CLASS_MS_ACCESS_JDBC_ODBC_BRIDGE =
    // "sun.jdbc.odbc.JdbcOdbcDriver";
    // public static final String URL_MS_ACCESS_MDB_TOOLS =
    // "mdbtools.jdbc.Driver";
    // public static final String URL_MS_ACCESS_JDBC_ODBC_BRIDGE =
    // "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=%FILENAME%";

    /**
     * Gets the single instance of DataLayerUpdateServlet.
     * 
     * @return single instance of DataLayerUpdateServlet
     */
    public static DataLayerUpdateServletV3 getInstance()
    {
        if (s_dl == null)
        {
            s_dl = new DataLayerUpdateServletV3();
        }

        return s_dl;
    }


    /**
     * Constructor
     */
    private DataLayerUpdateServletV3()
    {
        this.setJDBCConnection("org.postgresql.Driver",
            "jdbc:postgresql://localhost:5432/update_server_v3?user=upd_server&password=upd_server123456",
            "upd_server", "upd_server123456");
        init();
    }


    /**
     * Init
     */
    public void init()
    {

    }


    // /**
    // * Get Next Version Info
    // *
    // * @param product_id
    // * @param current_version
    // * @param current_db
    // * @return
    // */
    // public String getNextVersionInfo(String product_id, int current_version,
    // int current_db)
    // {
    // StringBuilder result = new StringBuilder();
    //
    // try
    // {
    // int next_version = 0;
    // // String next_version_string = null;
    // int next_version_db = 0;
    // // String next_version_db_string = null;
    // int max_db_version = 0;
    //
    // String sql = " SELECT MAX(version_num) as max_id " +
    // " FROM upd_app_version " + " WHERE product_id='"
    // + product_id + "' and db_version=" + current_db;
    //
    // ResultSet rs = this.executeQuery(sql);
    //
    // if (!rs.next())
    // return "ERR_NO_SUCH_APP";
    //
    // next_version = rs.getInt("max_id");
    //
    // sql = " SELECT MAX(db_version) as max_db_version " +
    // " FROM upd_app_version " + " WHERE product_id='"
    // + product_id + "'";
    //
    // rs = this.executeQuery(sql);
    //
    // rs.next();
    // max_db_version = rs.getInt("max_db_version");
    //
    // sql = " SELECT id, version_num, version_name " + " FROM upd_app_version "
    // + " WHERE product_id='"
    // + product_id + "' and version_num=" + next_version;
    //
    // rs = this.executeQuery(sql);
    // rs.next();
    //
    // result.append("NEXT_VERSION_ID=" + rs.getLong("id"));
    // result.append(";NEXT_VERSION=" + rs.getLong("version_num"));
    // result.append(";NEXT_VERSION_STR=" + rs.getString("version_name"));
    //
    // if (max_db_version > current_db)
    // {
    // sql = " SELECT MAX(version_num) as max_id " + " FROM upd_app_version " +
    // " WHERE product_id='"
    // + product_id + "' and db_version=" + max_db_version;
    //
    // rs = this.executeQuery(sql);
    // rs.next();
    //
    // next_version_db = rs.getInt("max_id");
    //
    // sql = " SELECT id, version_num, version_name, db_version " +
    // " FROM upd_app_version "
    // + " WHERE product_id='" + product_id + "' and version_num=" +
    // next_version_db;
    //
    // rs = this.executeQuery(sql);
    // rs.next();
    //
    // result.append(";NEXT_DB_VERSION_ID=" + rs.getLong("id"));
    // result.append(";NEXT_DB_VERSION=" + rs.getLong("version_num"));
    // result.append(";NEXT_DB_VERSION_STR=" + rs.getString("version_name"));
    // result.append(";NEXT_DB_VERSION_DB=" + rs.getLong("db_version"));
    //
    // }
    //
    // }
    // catch (Exception ex)
    // {
    // System.out.println(ex);
    // ex.printStackTrace();
    // return "ERR_INTERNAL_ERROR";
    // }
    //
    // return result.toString();
    //
    // }

    // /**
    // * Get Product Xml
    // *
    // * @param product_id
    // * @param current_version
    // * @return
    // */
    // public String getProductXml(String product_id, int current_version)
    // {
    // try
    // {
    // String sql = " SELECT xml_def " + " FROM upd_app_version " +
    // " WHERE product_id='" + product_id
    // + "' and version_num=" + current_version;
    //
    // ResultSet rs = this.executeQuery(sql);
    //
    // if (rs.next())
    // return rs.getString("xml_def");
    // else
    // return null;
    // }
    // catch (Exception ex)
    // {
    // LOG.debug("getProductXml. Ex.: " + ex, ex);
    // return null;
    // }
    //
    // }

    /**
     * Get Product Update List
     * 
     * @param product_id
     * @param current_version
     * @param next_version
     * @return
     */
    // public String getProductUpdateList(String product_id, long
    // current_version, long next_version)
    // {
    //
    // System.out.println("getProductUpdateList");
    //
    // try
    // {
    // String sql =
    // " SELECT upd_mod_version.id as id, upd_mod_version.module_id as module_id, upd_mod_version.version_num as version_num, upd_mod_version.archive_name as archive_name, upd_mod_version.archive_crc as archive_crc, upd_mod_version.archive_length as archive_length, upd_module.module_name as module_name "
    // + " FROM upd_mod_version "
    // +
    // " INNER JOIN upd_app_ver_modules on upd_app_ver_modules.module_id=upd_mod_version.module_id "
    // +
    // "    AND upd_app_ver_modules.module_version=upd_mod_version.version_num "
    // +
    // " INNER JOIN upd_module on upd_module.module_id = upd_mod_version.module_id "
    // + " AND upd_app_ver_modules.id IN "
    // + "    ( "
    // + "        SELECT upd_app_ver_modules.id "
    // + "        FROM upd_app_ver_modules "
    // + "        LEFT OUTER JOIN upd_app_ver_modules mod2 on mod2.version_num="
    // + current_version
    // +
    // " and mod2.product_id = upd_app_ver_modules.product_id AND mod2.module_id = upd_app_ver_modules.module_id "
    // + "        WHERE upd_app_ver_modules.product_id = '"
    // + product_id
    // + "' AND upd_app_ver_modules.version_num = "
    // + next_version
    // + " AND mod2.module_version < upd_app_ver_modules.module_version "
    // + "        UNION "
    // + "        SELECT upd_app_ver_modules.id "
    // + "        FROM upd_app_ver_modules "
    // + "        LEFT OUTER JOIN upd_app_ver_modules mod2 on mod2.version_num="
    // + current_version
    // +
    // " and mod2.product_id = upd_app_ver_modules.product_id AND mod2.module_id = upd_app_ver_modules.module_id "
    // + "        WHERE upd_app_ver_modules.product_id = '"
    // + product_id
    // + "' AND upd_app_ver_modules.version_num = " + next_version +
    // " AND mod2 is null " + "     ) ";
    //
    // ResultSet rs = this.executeQuery(sql);
    //
    // System.out.println("sql: " + sql);
    //
    // StringBuilder sb = new StringBuilder();
    // sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
    // sb.append("<update_detailed_file>\n");
    // sb.append("\t<components>\n");
    //
    // System.out.println("sb");
    //
    // while (rs.next())
    // {
    // System.out.print("xxx");
    // sb.append("\t\t<component>\n");
    //
    // // id
    // sb.append("\t\t\t<id>");
    // sb.append(rs.getLong("id"));
    // sb.append("</id>\n");
    //
    // // module_id
    // sb.append("\t\t\t<module_id>");
    // sb.append(rs.getString("module_id"));
    // sb.append("</module_id>\n");
    //
    // // module_name
    // sb.append("\t\t\t<module_name>");
    // sb.append(rs.getString("module_name"));
    // sb.append("</module_name>\n");
    //
    // // version_num
    // sb.append("\t\t\t<version_num>");
    // sb.append(rs.getLong("version_num"));
    // sb.append("</version_num>\n");
    //
    // // archive_name
    // sb.append("\t\t\t<archive_name>");
    // sb.append(rs.getString("archive_name"));
    // sb.append("</archive_name>\n");
    //
    // // archive_crc
    // sb.append("\t\t\t<archive_crc>");
    // sb.append(rs.getLong("archive_crc"));
    // sb.append("</archive_crc>\n");
    //
    // // archive_length
    // sb.append("\t\t\t<archive_length>");
    // sb.append(rs.getLong("archive_length"));
    // sb.append("</archive_length>\n");
    //
    // sb.append("\t\t</component>\n");
    //
    // System.out.print(".");
    //
    // }
    //
    // sb.append("\t</components>\n");
    // sb.append("</update_detailed_file>\n");
    //
    // return sb.toString();
    //
    // }
    // catch (Exception ex)
    // {
    // LOG.error("getProductUpdateList. Ex.: " + ex, ex);
    // return null;
    // }
    //
    // }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        // dl.getNextVersionInfo("ggc", 7, 7);
    }


    public boolean isApplicationPresent(String applicationName)
    {
        LOG.debug(String.format("is Application Present check [applicationName=%s]", applicationName));

        try
        {
            String sql = "select count(*) " + //
                    " from application_updates " + //
                    " where application_name = ? and active = true";

            PreparedStatement ps = this.prepareStatement(sql);
            ps.setString(1, applicationName);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                Integer count = rs.getInt(1);
                return count > 0;
            }

        }
        catch (Exception ex)
        {
            System.out.println("Error getting application data: " + ex);
            LOG.error("Error getting application data: " + ex, ex);
        }

        return false;
    }


    public boolean isVersionPresent(String applicationName, int version)
    {
        LOG.debug(String.format("is Version Present check [applicationName=%s, version%s]", applicationName, version));

        try
        {
            String sql = "select count(*) " + //
                    " from application_updates " + //
                    " where application_name = ? and version_number= ? and active = true ";

            PreparedStatement ps = this.prepareStatement(sql);
            ps.setString(1, applicationName);
            ps.setInt(2, version);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                Integer count = rs.getInt(1);
                return count > 0;
            }

        }
        catch (Exception ex)
        {
            // System.out.println("Error getting version data: " + ex);
            LOG.error("Error getting version data: " + ex, ex);
        }

        return false;

    }


    public List<ApplicationNoteDTO> getNotes(String applicationName, int version)
    {
        List<ApplicationNoteDTO> notes = new ArrayList<ApplicationNoteDTO>();

        // FIXME
        try
        {
            String sql = "select * " + //
                    " from application_notes " + //
                    " where application_name = ? and version_number >= ? and active = true" + //
                    " order by dt_info desc";

            PreparedStatement ps = this.prepareStatement(sql);
            ps.setString(1, applicationName);
            ps.setInt(2, version);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {

                ApplicationNoteDTO note = new ApplicationNoteDTO(rs);

                notes.add(note);
            }

        }
        catch (Exception ex)
        {
            System.out.println("Error getting application notes: " + ex);
            LOG.error("Error getting application notes: " + ex, ex);
        }
        finally
        {
            System.out.println("Notes: " + notes.size());
            return notes;
        }

    }


    public int getNotesCount(String applicationName, int version)
    {
        try
        {
            String sql = "select count(*) " + //
                    " from application_notes " + //
                    " where application_name = ? and version_number >= ? and active = true";

            PreparedStatement ps = this.prepareStatement(sql);
            ps.setString(1, applicationName);
            ps.setInt(2, version);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                Integer count = rs.getInt(1);
                return count;
            }
        }
        catch (Exception ex)
        {
            // System.out.println("Error getting application notes: " + ex);
            LOG.error("Error getting application notes: " + ex, ex);
        }

        return 0;

    }


    public ApplicationUpdateDTO getApplicationUpdateForVersion(String applicationName, int version)
    {

        try
        {
            String sql = "select " + ApplicationUpdateDTO.getColumns() + //
                    " from application_updates " + //
                    " where application_name = ? and version_number= ? and active = true";

            PreparedStatement ps = this.prepareStatement(sql);
            ps.setString(1, applicationName);
            ps.setInt(2, version);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new ApplicationUpdateDTO(rs);
            }

        }
        catch (Exception ex)
        {
            System.out.println("Error getting version data: " + ex);
            LOG.error("Error getting version data: " + ex, ex);
        }

        return null;
    }


    public ApplicationUpdateDTO getApplicationUpdateLatest(String applicationName)
    {
        try
        {
            String sql = "select " + ApplicationUpdateDTO.getColumns() + //
                    " from application_updates " + //
                    " where application_name = ? and active = true order by version_number desc";

            PreparedStatement ps = this.prepareStatement(sql);
            ps.setString(1, applicationName);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new ApplicationUpdateDTO(rs);
            }

        }
        catch (Exception ex)
        {
            System.out.println("Error getting version data: " + ex);
            LOG.error("Error getting version data: " + ex, ex);
        }

        return null;

    }
}
