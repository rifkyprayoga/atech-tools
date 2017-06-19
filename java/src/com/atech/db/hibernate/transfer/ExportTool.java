package com.atech.db.hibernate.transfer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.Value;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.tool.data.DatabaseTableConfiguration;

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

@Deprecated
public abstract class ExportTool extends ImportExportAbstract
{

    public static final String DELIMITER = "$#|#$";


    // /**
    // * Instantiates a new export tool.
    // *
    // * @param cfg the cfg
    // * @param some the some
    // */
    // public ExportTool(Configuration cfg, int some)
    // {
    // super(cfg);
    // }

    /**
     * Instantiates a new export tool.
     * 
     * @param hib_conf the hib_conf
     */
    public ExportTool(HibernateConfiguration hib_conf)
    {
        super(hib_conf);
    }


    /**
     * Process configuration.
     */
    public void processConfiguration()
    {
        System.out.println("Debug Configuration:");

        // this.configuration.
        // this.configuration.

        Iterator<?> it = this.getConfiguration().getClassMappings();

        while (it.hasNext())
        {
            org.hibernate.mapping.RootClass rc = (org.hibernate.mapping.RootClass) it.next();

            // System.out.println(it.next());

            if (rc.getClassName().equals("ggc.core.db.hibernate.DayValueH"))
            {
                exploreRootClass(rc);
            }
        }

    }


    /**
     * Gets the root class.
     * 
     * @param cls_name the cls_name
     * 
     * @return the root class
     */
    public RootClass getRootClass(String cls_name)
    {
        Iterator<?> it = this.getConfiguration().getClassMappings();

        while (it.hasNext())
        {
            org.hibernate.mapping.RootClass rc = (org.hibernate.mapping.RootClass) it.next();

            if (rc.getClassName().equals(cls_name))
                return rc;
        }

        return null;

    }


    /**
     * Explore root class.
     * 
     * @param rc the rc
     */
    public void exploreRootClass(RootClass rc)
    {
        System.out.println("Class Name: " + rc.getClassName());
        exploreTable(rc.getTable());
    }


    private void exploreTable(Table tbl)
    {
        println("Table name: " + tbl.getName());

        Iterator<?> it = tbl.getColumnIterator();

        while (it.hasNext())
        {
            Column cl = (Column) it.next();

            exploreColumn(cl);

        }

    }


    /**
     * Explore column.
     * 
     * @param cl the cl
     */
    public void exploreColumn(Column cl)
    {

        println(cl.getQuotedName() + " (" + cl.getName() + "), "); // length=" +

        // println("Name: " + cl.getName());
        // println("Quoted Name: " + cl.getQuotedName());
        // cl.getPrecision()
        println("Length: " + cl.getLength());
        // println(cl.getName());
        println("TypeIndex: " + cl.getTypeIndex());
        println("Can.Name: " + cl.getCanonicalName());
        // println("Alias: " +cl.ge.getAlias());
        println("Check const.: " + cl.getCheckConstraint());
        println("Comment: " + cl.getComment());
        println("Def.Value: " + cl.getDefaultValue());
        println("SqlType: " + cl.getSqlType());
        println("SQltypeCode: " + cl.getSqlTypeCode());
        println("Text: " + cl.getText());
        Value v = cl.getValue();

        println("Type: " + v.getType());

        // println("Scale: " + cl.getScale());
        // println("Precision: " + cl.getPrecision());
        // println("Value: " + cl.getValue());

    }


    /**
     * Export.
     */
    public void export()
    {
        exportClass("ggc.core.db.hibernate.DayValueH");
    }


    /**
     * Export class.
     * 
     * @param cls_name the cls_name
     */
    public void exportClass(String cls_name)
    {
        // String cls = "ggc.core.db.hibernate.DayValueH";

        ArrayList<String> clms = getColumnsNames(cls_name);

        Random rd = new Random();

        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./export/" + cls_name + rd.nextLong())));

            /*
             * BufferedWriter out = new BufferedWriter(new
             * OutputStreamWriter(new FileOutputStream(new File(
             * "./export/" + cls_name + rd.nextLong()))
             * file),"UTF8"));
             */

            bw.write("; Class: " + cls_name + "\n");
            bw.write("; Date of export: " + getCurrentDate() + "\n");

            bw.write("; Exported by ATechTools - Hibernate Exporter 0.1\n");
            bw.write(";\n");
            bw.write("; Columns: ");

            for (int i = 0; i < clms.size(); i++)
            {
                if (i != 0)
                {
                    bw.write(",");
                }

                bw.write(clms.get(i));
            }
            bw.write("\n");
            bw.write(";\n");

            List<?> l = getData(cls_name);

            Iterator<?> it = l.iterator();

            while (it.hasNext())
            {
                Object o = it.next();
                String dt = "";

                for (int i = 0; i < clms.size(); i++)
                {
                    if (i != 0)
                    {
                        dt += "|";
                    }

                    dt += getDataFromColumnForObject(o, clms.get(i));
                }

                bw.write(dt);
                bw.newLine();
                // System.out.println(dt);
            }

            bw.flush();
            bw.close();
        }
        catch (Exception ex)
        {
            println("Exception on writing: " + ex);
        }

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


    public void writeHeader(DataDefinitionEntry dataDefinitionEntry, String dbVersion)
    {
        try
        {
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Class: " + dataDefinitionEntry.getClazz().getName() + "\n");
            bufferedWriter.write("; Date of export: " + getCurrentDate() + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Exported by ATechTools - Hibernate Exporter 0.4.1\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Columns: " + dataDefinitionEntry.getDbColumns() + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write("; Database version: " + dbVersion + "\n");
            bufferedWriter.write(";\n");
            bufferedWriter.write(
                String.format("; DbToolInfo.0 [classShort=%s, databaseVersion=%s, tableVersion=%s, delimiter=%s]\n",
                    dataDefinitionEntry.getClazz().getSimpleName(), dbVersion, dataDefinitionEntry.getTableVersion(),
                    "$#|#$"));
            bufferedWriter.write(";\n");
            bufferedWriter.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }
    }


    public void writeHeader(DatabaseTableConfiguration tableConfiguration, int dbVersion)
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
                String.format("; DbToolInfo [classShort=%s, databaseVersion=%s, tableVersion=%s, delimiter=%s]\n",
                    tableConfiguration.getObjectName(), dbVersion, tableConfiguration.getTableVersion(), "$#|#$"));
            bufferedWriter.write(";\n");
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


    /**
     * Gets the data from column for object.
     * 
     * @param obj the obj
     * @param column_name the column_name
     * 
     * @return the data from column for object
     */
    public String getDataFromColumnForObject(Object obj, String column_name)
    {

        String method_name = "get" + column_name.substring(0, 1).toUpperCase() + column_name.substring(1);

        // x String result = null;
        Object res2 = null;
        Class<?> c = obj.getClass();
        // Class[] parameterTypes = new Class[] {String.class};
        Method method;
        // Object[] arguments = new Object[] {secondWord};
        try
        {
            method = c.getMethod(method_name);
            // c.getMethod(
            res2 = method.invoke(obj);

            if (res2 == null)
                // System.out.println("We got null");
                return "null";
        }
        catch (NoSuchMethodException e)
        {
            System.out.println(e);
        }
        catch (IllegalAccessException e)
        {
            System.out.println(e);
        }
        catch (InvocationTargetException e)
        {
            System.out.println(e);
        }

        String rr = res2.toString();

        if (rr.contains("|"))
            return rr.replaceAll("|", "$#|#$");
        else
            return rr;

    }


    /*
     * public static String append(String firstWord, String secondWord) { String
     * result = null; Class c = String.class; Class[] parameterTypes = new
     * Class[] {String.class}; Method concatMethod; Object[] arguments = new
     * Object[] {secondWord}; try { concatMethod = c.getMethod("concat",
     * parameterTypes); result = (String) concatMethod.invoke(firstWord,
     * arguments); } catch (NoSuchMethodException e) { System.out.println(e); }
     * catch (IllegalAccessException e) { System.out.println(e); } catch
     * (InvocationTargetException e) { System.out.println(e); } return result; }
     */

    /**
     * Gets the columns names.
     * 
     * @param cls_name the cls_name
     * 
     * @return the columns names
     */
    public ArrayList<String> getColumnsNames(String cls_name)
    {
        RootClass rc = getRootClass(cls_name);

        Table tbl = rc.getTable();

        ArrayList<String> clms = new ArrayList<String>();

        Iterator<?> it = tbl.getColumnIterator();

        while (it.hasNext())
        {
            Column c = (Column) it.next();
            clms.add(c.getQuotedName());
        }

        return clms;

    }


    public Configuration getConfiguration()
    {
        // FIXME
        return null;
    }


    /**
     * Gets the data.
     * 
     * @param clas_name the clas_name
     * 
     * @return the data
     */
    public List<?> getData(String clas_name)
    {
        Query q = getSession().createQuery("select smth from " + clas_name + " as smth");
        return q.list();
    }

    /*
     * public static void main(String args[]) { GGCDb db = new GGCDb();
     * ExportTool tool = new ExportTool(db.getConfiguration()); tool.export(); }
     */

}
