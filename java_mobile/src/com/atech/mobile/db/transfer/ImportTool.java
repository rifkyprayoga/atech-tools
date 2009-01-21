package com.atech.mobile.db.transfer;

import java.io.File;
import java.sql.Connection;
import java.util.Hashtable;

import com.atech.mobile.db.objects.DummyDAO;


// this one should be extended, we have several variables that need to be filled

public abstract class ImportTool extends ImportExportAbstract
{

    public Hashtable<String, String> classDef = null;

    protected File restore_file = null;
    //protected BufferedReader file_reader;
    
/*
    public ImportTool(Configuration cfg, int i)
    {
        super(cfg,i);
    }
*/

    public ImportTool()
    {
        super();
    }
    
    
    public ImportTool(Connection conn)
    {
        super(conn);
    }
    
    public ImportTool(Connection conn, RestoreFileInfo res)
    {
        super(conn);
        
        setRestoreFileInfo(res);
    }
    
    
    public void setRestoreFileInfo(RestoreFileInfo res)
    {
        this.statusSetMaxEntry(res.element_count);
        this.restore_file = res.file;
    }
    
    
    
    

    
    
    
    
    
    

    public int getInt(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.length() == 0)
            return 0;
        else
            return Integer.parseInt(input);

    }

    public short getShort(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.length() == 0)
            return 0;
        else
            return Short.parseShort(input);

    }

    public long getLong(String input)
    {

        if (input.startsWith("~"))
            input = input.substring(1, input.length() - 1);

        if (input.length() == 0)
            return 0;
        else
            return Long.parseLong(input);

    }

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

    
    public void clearExistingData(String table_name)
    {
        //DayValueDAO dvd = new DayValueDAO();
        DummyDAO dd = new DummyDAO();
        
        try
        {
        dd.executeDb(this.connection, "delete from " + table_name);
        }
        catch(Exception ex)
        {
            System.out.println("Error clearing data: " + ex);
        }
        
        
//        Query q = getSession().createQuery("delete from " + class_name );
//        q.executeUpdate();
    }
    
    
    /*
     * public static void main(String args[]) { GGCDb db = new GGCDb();
     * 
     * ExportTool tool = new ExportTool(db.getConfiguration()); tool.export(); }
     */

}
