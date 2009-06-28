package com.atech.db.hibernate.transfer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.Value;

import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.HibernateUtil;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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

// this one should be extended, we have several variables that need to be filled

public abstract class ImportTool extends ImportExportAbstract
{

    /**
     * The class def.
     */
    public Hashtable<String, String> classDef = null;

    //Configuration m_cfg = null;
    /**
     * The m_session.
     */
    //Session m_session = null;
    
    /**
     * The restore_file.
     */
    protected File restore_file = null;
    //protected BufferedReader file_reader;
    /**
     * The hibernate_util.
     */
    protected HibernateUtil hibernate_util = null;
    
/*
    public ImportTool(Configuration cfg, int i)
    {
        super(cfg,i);
    }
*/
    
    /**
 * Instantiates a new import tool.
 * 
 * @param hib_conf the hib_conf
 */
public ImportTool(HibernateConfiguration hib_conf)
    {
        super(hib_conf);
        createHibernateUtil();
    }
    
    /**
     * Instantiates a new import tool.
     * 
     * @param hib_conf the hib_conf
     * @param res the res
     */
    public ImportTool(HibernateConfiguration hib_conf, RestoreFileInfo res)
    {
        super(hib_conf);
        createHibernateUtil();
        
        setRestoreFileInfo(res);
    }
    
    
    /**
     * Sets the restore file info.
     * 
     * @param res the new restore file info
     */
    public void setRestoreFileInfo(RestoreFileInfo res)
    {
        this.statusSetMaxEntry(res.element_count);
        this.restore_file = res.file;
    }
    
    
    
    /**
     * Instantiates a new import tool.
     */
    public ImportTool()
    {
        super();
    }
    

    /**
     * Creates the hibernate util.
     */
    public void createHibernateUtil()
    {
        this.hibernate_util = new HibernateUtil(this.hibernate_conf, HibernateConfiguration.DB_CONTEXT_FULL, false);
        this.hibernate_util.setSession(this.getActiveSession());
    }
    
    
    
    
    /**
     * Process configuration.
     */
    public void processConfiguration()
    {
        //System.out.println("Debug Configuration:");

        // this.m_cfg.g
        // this.m_cfg.

        Iterator<?> it = this.m_cfg.getClassMappings();

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
        Iterator<?> it = this.m_cfg.getClassMappings();

        while (it.hasNext())
        {
            org.hibernate.mapping.RootClass rc = (org.hibernate.mapping.RootClass) it.next();

            if (rc.getClassName().equals(cls_name))
            {
                return rc;
            }
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
        // rc.
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

    /*
     * 
     * <class name="ggc.core.db.hibernate.DayValueH" table="ggc_main_dayvalues"
     * > <id name="id" type="long" unsaved-value="0"> <generator
     * class="org.hibernate.id.AssignedIncrementGenerator"/> </id> <property
     * name="dt_info" type="long" not-null="true"/> <property name="bg"
     * type="float" /> <property name="ins1" type="float" /> <property
     * name="ins2" type="float" /> <property name="ch" type="float" /> <property
     * name="meals_ids" type="string" length="1000" /> <property name="act"
     * type="int" /> <property name="comment" type="string" length="2000" />
     * </class>
     */


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

        //String result = null;
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
            {
                // System.out.println("We got null");
                return "null";
            }
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

        // return null;
    }

    /**
     * Gets the int.
     * 
     * @param input the input
     * 
     * @return the int
     */
    public int getInt(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.length() == 0)
            return 0;
        else
            return Integer.parseInt(input);

    }

    /**
     * Gets the short.
     * 
     * @param input the input
     * 
     * @return the short
     */
    public short getShort(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.length() == 0)
            return 0;
        else
            return Short.parseShort(input);

    }

    /**
     * Gets the long.
     * 
     * @param input the input
     * 
     * @return the long
     */
    public long getLong(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.length() == 0)
            return 0;
        else
            return Long.parseLong(input);

    }

    /**
     * Gets the float.
     * 
     * @param input the input
     * 
     * @return the float
     */
    public float getFloat(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.length() == 0)
            return 0;
        else
            return (float) Double.parseDouble(input.replace(',', '.'));

        // return Float.parseFloat(input);

    }

    /**
     * Gets the string.
     * 
     * @param input the input
     * 
     * @return the string
     */
    public String getString(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.trim().length() == 0)
            return null;

        if (input.equals("null"))
            return null;

        return input;

    }

    
    /**
     * Clear existing data.
     * 
     * @param class_name the class_name
     */
    public void clearExistingData(String class_name)
    {
        Query q = getSession().createQuery("delete from " + class_name );
        q.executeUpdate();
    }
    
    
    /*
     * public static void main(String args[]) { GGCDb db = new GGCDb();
     * 
     * ExportTool tool = new ExportTool(db.getConfiguration()); tool.export(); }
     */

}
