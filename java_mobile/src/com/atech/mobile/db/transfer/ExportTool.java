package com.atech.mobile.db.transfer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

public abstract class ExportTool extends ImportExportAbstract
{


    public ExportTool(Connection conn)
    {
        super(conn);
    }

    
    




    






    public void writeHeader(String class_name, String columns, String db_version)
    {

        //System.out.println("Exporting " + class_name);

        try
        {
            bw_file.write(";\n");
            bw_file.write("; Class: " + class_name + "\n");
            bw_file.write("; Date of export: " + getCurrentDate() + "\n");
            bw_file.write(";\n");
            bw_file.write("; Exported by ATechTools - Hibernate Exporter 0.2\n");
            bw_file.write(";\n");
            bw_file.write("; Columns: " + columns + "\n");
            bw_file.write(";\n");
            bw_file.write("; Database version: " +  db_version + "\n");
            bw_file.write(";\n");
            bw_file.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }

    }




    public String getDataFromColumnForObject(Object obj, String column_name)
    {

        String method_name = "get" + column_name.substring(0, 1).toUpperCase()
                + column_name.substring(1);

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
     * public static void main(String args[]) { GGCDb db = new GGCDb();
     * 
     * ExportTool tool = new ExportTool(db.getConfiguration()); tool.export(); }
     */

}
