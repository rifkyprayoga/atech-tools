package com.atech.mobile.db.transfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RestoreFileInfo
{
    public String name = "";
    public String class_name = "";
    public int element_count = 0;
    public String db_version = "";
    File file;
    public boolean selected = false;

    
    public RestoreFileInfo(File file)
    {
        this.file = file;
        processFile();
    }

    public void processFile()
    {
        this.name = this.file.getName();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(this.file));

            String line;
            int data_line = 0;

            while ((line = br.readLine()) != null)
            {
                if (line.startsWith(";"))
                    processStatusLine(line);
                else if (line.trim().length() > 0)
                {
                    data_line++;
                }
            }

            this.element_count = data_line;

        }
        catch (Exception ex)
        {
            System.out.println("Exception on processFile: Ex: " + ex);
        }

    }

    private void processStatusLine(String line)
    {
        if (line.contains("Class:"))
        {
            this.class_name = this.getSearchedValue("Class:", line);
        }

        if (line.contains("Database version:"))
        {
            this.db_version = this.getSearchedValue("Database version:", line);
        }

    }

    private String getSearchedValue(String keyword, String data)
    {
        int ind = data.indexOf(keyword) + keyword.length() + 1;
        return data.substring(ind);
    }

    public String toString()
    {
        return "RestoreFileInfo [filename=" + this.name + ",class_name=" + this.class_name + ",db_ver="
                + this.db_version + ",data=" + this.element_count + "]";
    }

}
