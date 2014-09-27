package com.atech.update.config;

import java.util.Enumeration;
import java.util.Hashtable;

import org.dom4j.Node;

import com.atech.update.startup.os.StartupOSAbstract;
import com.atech.utils.xml.XmlUtil;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ComponentEntry - Component Entry
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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

public class ComponentEntry implements ComponentInterface
{
    int id = 0;

    /**
     * Group 
     */
    public int group = 0;

    /**
     * Name 
     */
    public String name = "";

    /**
     * Version 
     */
    public String version = "";

    /**
     * Version (num) 
     */
    public int version_num = 1;

    /**
     * Root Directory 
     */
    public String root_dir = "";

    /**
     * Files 
     */
    public String files = "";

    /**
     * Comment 
     */
    public String comment = "";

    /**
     * Server Version
     */
    public String server_version = "";

    /**
     * Server Version (num)
     */
    public int server_version_num = 1;

    /**
     * Status
     */
    public int status = 4;

    // native stuff

    /**
     * Platform Specific
     */
    public boolean platform_specific = false;

    /**
     * Platform Specific Type: None
     */
    public static final int PLATFORM_SPECIFIC_NONE = 0;

    /**
     * Platform Specific Type: No
     */
    public static final int PLATFORM_SPECIFIC_NO = 1;

    /**
     * Platform Specific Type: Native Only
     */
    public static final int PLATFORM_SPECIFIC_NATIVE_ONLY = 2;

    /**
     * Platform Specific Type: Full
     */
    public static final int PLATFORM_SPECIFIC_FULL = 3;

    /**
     * Platform Specific Type
     */
    public int platform_specific_type = 0;

    /**
     * Platform Supported
     */
    public String platform_supported = "";

    /**
     * Files Java Specific
     */
    public Hashtable<String, String> files_java_specific = new Hashtable<String, String>();

    // Update System

    /**
     * Update Data: Module Version Id 
     */
    public long module_version_id = 0L;

    /**
     * Update Data: Archive File 
     */
    public String archive_file = null;

    /**
     * Update Data: Archive CRC 
     */
    public long archive_crc = 0L;

    /**
     * Update Data: Archive Size 
     */
    public long archive_size = 0L;

    /**
     * Update Data: Action 
     */
    public int update_action = 0;

    /**
     * Update Action: Get File Binary 
     */
    public static final int ACTION_GET_FILE_BINARY = 1;

    // public long estimated_crc = 0L;

    // public long file_id = 0L;

    // public long requested_version_id = 0L;

    // public String output_file = null;

    // public long file_size = 0L;

    /**
     * Status: Newest (Up to date)
     */
    public static final int STATUS_NEWEST = 1;

    /**
     * Status: Not Updated
     */
    public static final int STATUS_NOT_UPDATED = 2;

    /**
     * Status: New 
     */
    public static final int STATUS_NEW = 3;

    /**
     * Status: Unknown 
     */
    public static final int STATUS_UNKNOWN = 4;

    /*
     * #COMPONENT_13_PLATFORM_SPECIFIC=true
     * #COMPONENT_13_PLATFORM_SPECIFIC_TYPE=1,2,3 = 1- no,
     * 2-yes,native_only,3-yes,both
     * #COMPONENT_13_PLATFORM_SUPPORTED=win linux
     * #COMPONENT_13_PLATFORM_WIN_JAVA=
     * // #COMPONENT_13_PLATFORM_WIN_NATIVE=
     */

    // 1=ok, 2=not updated, 3=not updatebale, 4=unknown

    String column_names[] = { "NAME", "CURRENT_VERSION", "SERVER_VERSION", "UPDATEABLE", "COMMENT" };

    /** 
     * getColumnValue
     */
    public String getColumnValue(int index)
    {
        switch (index)
        {
            case 0:
                return this.name;
            case 1:
                return this.version;
            case 2:
                return this.server_version;
            case 3:
                return "" + this.status;
            default:
            case 4:
                return "";
        }
        // return "";
    }

    /** 
     * isGroup
     */
    public boolean isGroup()
    {
        return false;
    }

    /**
     * Get Files
     * 
     * @param os_abs StartupOSAbstract instance
     * @return
     */
    public String getFiles(StartupOSAbstract os_abs)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(this.files);

        if (this.platform_specific)
        {

            if (this.platform_specific_type == ComponentEntry.PLATFORM_SPECIFIC_FULL)
            {

                // System.out.println("Is platform specific");
                if (this.files.trim().length() != 0)
                {
                    sb.append(";");
                }

                if (this.files_java_specific.containsKey(os_abs.getShortOSName()))
                {
                    // we get error on Mac for example, because files are not
                    // found.
                    String file = this.files_java_specific.get(os_abs.getShortOSName());

                    if (file == null || file.trim().length() == 0)
                    {
                        // try windows or linux, this are in most cases OSes
                        // that have files
                        file = this.files_java_specific.get("win");
                        if (file == null || file.trim().length() == 0)
                        {
                            sb.append(this.files_java_specific.get("linux"));
                        }
                        else
                        {
                            sb.append(file);
                        }

                    }
                    else
                    {
                        sb.append(file);
                    }
                }
                else
                {
                    System.out.println("***\n" + "*** Native part for Library " + this.name
                            + " is not available !!!!\n"
                            + "**********************************************************************\n"
                            + "*** You should contact authors/site of your application and talk to\n"
                            + "*** them about this problem. Since this library needs native part to \n"
                            + "*** function correctly, this library will have to be updated or \n"
                            + "*** replaced (if there is suitable replacement available), or maybe \n"
                            + "*** just some 3rd party native part added.\n\n");
                }
            }
            else if (this.platform_specific_type == ComponentEntry.PLATFORM_SPECIFIC_NATIVE_ONLY)
            {
                if (!this.files_java_specific.containsKey(os_abs.getShortOSName()))
                {
                    System.out.println("***\n" + "*** Native part for Library " + this.name
                            + " is not available !!!!\n"
                            + "**********************************************************************\n"
                            + "*** You should contact authors/site of your application and talk to\n"
                            + "*** them about this problem. Since this library needs native part to \n"
                            + "*** function correctly, this library will have to be updated or \n"
                            + "*** replaced (if there is suitable replacement available), or maybe \n"
                            + "*** just some 3rd party native part added.\n\n");
                }
            }

        }

        return sb.toString();
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("ComponentEntry [");
        sb.append("id=" + this.id);
        sb.append(", group=" + this.group);
        sb.append(", name=" + this.name);
        sb.append(", version=" + this.version);
        sb.append(", version_num=" + this.version_num);
        sb.append(", root_dir=" + this.root_dir);
        sb.append(", files=" + this.root_dir);

        if (this.platform_specific)
        {
            sb.append("\n");
            sb.append("   platform_specific=" + this.platform_specific);
            sb.append(", platform_specific_type=" + this.platform_specific_type);
            sb.append(", platforms_supported=" + this.platform_supported);

            if (this.platform_specific_type == ComponentEntry.PLATFORM_SPECIFIC_FULL)
            {
                sb.append("\n");
                sb.append(" jars [");

                int i = 0;

                for (Enumeration<String> en = this.files_java_specific.keys(); en.hasMoreElements();)
                {
                    String key = en.nextElement();
                    if (i > 0)
                    {
                        sb.append(";");
                    }

                    sb.append(key + "=" + this.files_java_specific.get(key));

                    i++;
                }

            }
        }

        sb.append("]");

        return sb.toString();

    }

    /**
     * Copy to Server Settings
     */
    public void copyToServerSettings()
    {
        this.server_version = this.version;
        this.server_version_num = this.version_num;

        this.version = "N/A";
        this.version_num = 0;

        setStatus();
    }

    /**
     * Set Server Settings
     * 
     * @param ce
     */
    public void setVersionSettings(ComponentEntry ce)
    {
        this.version = ce.version;
        this.version_num = ce.version_num;

        // System.out.println("Component: " + this.name + "Version: " +
        // this.version_num + ", ServerVersion: " + this.server_version_num);
        setStatus();
    }

    private void setStatus()
    {
        if (this.version_num == 0 && this.server_version_num > 0)
        {
            this.status = ComponentEntry.STATUS_NEW;
        }
        else if (this.version_num < this.server_version_num)
        {
            this.status = ComponentEntry.STATUS_NOT_UPDATED;
        }
        else if (this.version_num == this.server_version_num)
        {
            this.status = ComponentEntry.STATUS_NEWEST;
        }
        else
        {
            this.status = ComponentEntry.STATUS_UNKNOWN;
        }
    }

    /**
     * Set Extended Server Data
     * 
     * @param parent_node
     */
    public void setExtendedServerData(Node parent_node)
    {

        // <id>49</id>
        // <module_id>xml_dom_jaxen</module_id>
        // <module_name>XML (dom4j, jaxen)</module_name>
        // <version_num>1</version_num>
        // <archive_name>xml_dom_jaxen-1.6.1.zip</archive_name>
        // <archive_crc>1031795816</archive_crc>
        // <archive_length>506049</archive_length>

        this.archive_file = XmlUtil.getNodeValueString(parent_node, "archive_name");
        this.archive_size = XmlUtil.getNodeValueLong(parent_node, "archive_length");
        this.archive_crc = XmlUtil.getNodeValueLong(parent_node, "archive_crc");
        this.module_version_id = XmlUtil.getNodeValueLong(parent_node, "id");

        System.out.println("ExtendedData [update_module_id=" + this.module_version_id + ", archive_file="
                + this.archive_file + ", archive_size=" + this.archive_size + ", archive_crc=" + this.archive_crc);

    }

}
