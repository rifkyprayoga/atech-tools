package com.atech.mobile.db.transfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;

import com.atech.mobile.utils.ATMobileDataAccess;

public abstract class ImportExportAbstract
{

    protected int status_type = 0;
    protected int status_max_entry;

    public static int STATUS_NONE = 0;
    public static int STATUS_DOT = 1;
    public static int STATUS_PROCENT = 2;
    public static int STATUS_SPECIAL = 3;

    protected String path_root = null;
    protected String file_2nd_part = null;

    BackupRestoreWorkGiver work_giver = null;

    protected BufferedWriter bw_file = null;
    protected BufferedReader br_file = null;

    protected Connection connection;
    
    public ImportExportAbstract(Connection conn)
    {
        this.connection = conn;
    }
    
    
    public ImportExportAbstract()
    {
    }
    
    


    public void setRootPath(String path)
    {
        this.path_root = path;
    }

    public String getRootPath()
    {
        return this.path_root;
    }

    public String getFileLastPart()
    {
        return this.file_2nd_part;
    }

    public void setFileLastPart(String last_part)
    {
        this.file_2nd_part = last_part;
    }

    
    public abstract int getActiveSession();
    
    
    public void println(String txt)
    {
        System.out.println(txt);
    }

    
    public void setConnection(Connection conn)
    {
        this.connection = conn;
    }
    

    public void openFile(String file)
    {
        try
        {
            //bw_file = new BufferedWriter(new FileWriter(new File(file)));
            
            bw_file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file)),"UTF8"));
        }
        catch (Exception ex)
        {
            println("Exception on openFile: " + ex);
        }

    }

    
    public void openFileForReading(File file)
    {
        try
        {
            //br_file = new BufferedReader(new FileReader(file));
            br_file = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
        }
        catch (Exception ex)
        {
            println("Exception on openFileReading: " + ex);
        }

    }
    
    

    public void setTypeOfStatus(int type)
    {
        this.status_type = type;
    }

    public void setStatusReceiver(BackupRestoreWorkGiver giver)
    {
        this.work_giver = giver;
    }

    public void statusSetMaxEntry(int max_entry)
    {
        //System.out.println("max entries: " + max_entry);
        this.status_max_entry = max_entry;
    }

    public void writeStatus(int every_x_entry, int count)
    {
        if (this.status_type == ExportTool.STATUS_NONE)
            return;
        else if (this.status_type == ExportTool.STATUS_PROCENT)
            writeStatusProcent(every_x_entry, count);
        else if (this.status_type == ExportTool.STATUS_DOT)
            this.writeStatusDots(every_x_entry, count);
        else if (this.status_type == ExportTool.STATUS_SPECIAL)
            this.writeStatusSpecial(every_x_entry, count);
    }

    public void writeStatusDots(int every_x_entry, int count)
    {
        if (count % every_x_entry == 0)
            System.out.println(".");
    }

    public void writeStatusProcent(int every_x_entry, int count)
    {
        if (count % every_x_entry == 0)
        {
            if (this.status_max_entry <= 0)
                return;
            else
            {
                float val = (count * 1.0f) / this.status_max_entry;

                if (val > 1)
                    System.out.println("100% (?)");
                else
                    System.out.println((int) (val * 100) + "%");
            }
        }
    }

    public void writeStatusSpecial(int every_x_entry, int count)
    {
        if (this.status_max_entry <= 0)
        {
            System.out.println("Status main entry problem");
            return;
        }
        else
        {
            float val = (count * 1.0f) / this.status_max_entry;

            if (val > 1)
            {
                System.out.println("Set status 100");
                this.work_giver.setStatus(100);
            }
            else
            {
                // System.out.println("Set Status: " + (int)(val * 100)) ;
                this.work_giver.setStatus((int) (val * 100));
            }
        }

    }

    public void writeToFile(String entry)
    {
        try
        {
            bw_file.write(entry);
            bw_file.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }

    }

    public void closeFile()
    {
        try
        {
            if (bw_file != null)
                bw_file.close();

            if (br_file != null)
                br_file.close();
        }
        catch (Exception ex)
        {
            println("Exception on closeFile: " + ex);
        }

    }

    protected String getCurrentDate()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());

        return gc.get(GregorianCalendar.DAY_OF_MONTH) + "/"
                + (gc.get(GregorianCalendar.MONTH) + 1) + "/"
                + gc.get(GregorianCalendar.YEAR) + "  "
                + gc.get(GregorianCalendar.HOUR_OF_DAY) + ":"
                + gc.get(GregorianCalendar.MINUTE) + ":"
                + gc.get(GregorianCalendar.SECOND);
    }

    public String getCurrentDateForFile()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());

        return gc.get(GregorianCalendar.YEAR) + "_"
                + getLeadingZero((gc.get(GregorianCalendar.MONTH) + 1), 2)
                + "_"
                + getLeadingZero(gc.get(GregorianCalendar.DAY_OF_MONTH), 2);

    }

    public String getLeadingZero(int number, int places)
    {
        return ATMobileDataAccess.getInstance().getLeadingZero(number, places);
    }


    protected boolean dbExecute(Connection conn, String sql) throws Exception
    {
        try
        {
            Statement st = conn.createStatement();
            st.execute(sql);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        
        return true;
        
    }
    

    protected ResultSet dbExecuteQuery(Connection conn, String sql) throws Exception
    {
        try
        {
            Statement st = conn.createStatement();
            return st.executeQuery(sql);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        
        
    }
    
    
    

}
