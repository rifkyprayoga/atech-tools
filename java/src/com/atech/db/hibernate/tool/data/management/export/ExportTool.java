package com.atech.db.hibernate.tool.data.management.export;

import org.hibernate.cfg.Configuration;

import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.tool.data.DatabaseTableConfiguration;
import com.atech.db.hibernate.tool.data.management.common.ImportExportAbstract;
import com.atech.db.hibernate.tool.data.management.common.ImportExportContext;
import com.atech.db.hibernate.transfer.BackupRestoreObject;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ExportTool - Export Tool
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

public abstract class ExportTool extends ImportExportAbstract
{

    public static final String DELIMITER = "$#|#$";


    /**
     * Instantiates a new export tool.
     * 
     * @param cfg the cfg
     * @param some the some
     */
    public ExportTool(Configuration cfg, ImportExportContext context)
    {
        super(cfg, context);
    }


    /**
     * Instantiates a new export tool.
     * 
     * @param hib_conf the hib_conf
     */
    public ExportTool(HibernateConfiguration hib_conf, ImportExportContext importExportContext)
    {
        super(hib_conf);
    }


    /**
     * Write header.
     * 
     * @param class_name the class_name
     * @param columns the columns
     * @param db_version the db_version
     */
    @Deprecated
    public void writeHeader(String class_name, String columns, String db_version)
    {
        try
        {
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Class: " + class_name + "\n");
            bufferedWriter.write("; Date of export: " + getCurrentDate() + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Exported by ATechTools - Hibernate Exporter 0.2\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Columns: " + columns + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Database version: " + db_version + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }
    }


    public void writeHeader(DatabaseTableConfiguration tableConfiguration, String dbVersion)
    {
        try
        {
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Class: " + tableConfiguration.getObjectFullName() + "\n");
            bufferedWriter.write("; Date of export: " + getCurrentDate() + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Exported by ATechTools - Hibernate Exporter 0.5 (DbTool v1.0)\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Columns: " + tableConfiguration.getColumns() + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Database version: " + dbVersion + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write(
                String.format("; DbToolInfo [classShort=%s, databaseVersion=%s, tableVersion=%s, delimiter=%s]",
                    tableConfiguration.getObjectName(), dbVersion, tableConfiguration.getTableVersion(), "$#|#$"));
            bufferedWriter.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }
    }


    /**
     * Write header.
     * 
     * @param bro the bro
     * @param db_version the db_version
     */
    @Deprecated
    public void writeHeader(BackupRestoreObject bro, String db_version)
    {
        try
        {

            bufferedWriter.write(";\n");
            bufferedWriter.write("; Class: " + bro.getBackupClassName() + "\n");
            bufferedWriter.write("; Date of export: " + getCurrentDate() + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Exported by ATechTools - Hibernate Exporter 0.4\n");
            bufferedWriter.write(";\n");
            // bufferedWriter.write("; Columns: " + bro.dbExportHeader() +
            // "\n");
            bufferedWriter.write("; Database version: " + db_version + "\n");
            bufferedWriter.write("; Delimiter: " + (bro.isNewImport() ? "$#|#$" : "|") + "\n");
            bufferedWriter.write(bro.dbExportHeader());
            bufferedWriter.write(";\n");

            bufferedWriter.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }
    }

}
