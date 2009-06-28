package com.atech.db.hibernate.transfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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


public class RestoreFileInfo
{
    
    /**
     * The name.
     */
    public String name = "";
    
    /**
     * The class_name.
     */
    public String class_name = "";
    
    /**
     * The element_count.
     */
    public int element_count = 0;
    
    /**
     * The db_version.
     */
    public String db_version = "";
    
    /**
     * The file.
     */
    File file;
    
    /**
     * The selected.
     */
    public boolean selected = false;

    /**
     * Instantiates a new restore file info.
     * 
     * @param file the file
     */
    public RestoreFileInfo(File file)
    {
        this.file = file;
        processFile();
    }

    /**
     * Process file.
     */
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

    /** 
     * toString
     */
    public String toString()
    {
        return "RestoreFileInfo [filename=" + this.name + ",class_name=" + this.class_name + ",db_ver="
                + this.db_version + ",data=" + this.element_count + "]";
    }

}
