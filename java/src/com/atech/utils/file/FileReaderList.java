package com.atech.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *  This file is part of ATech Tools library.
 *  
 *  
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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
 * @param <E> 
 *
*/

public abstract class FileReaderList<E> extends ArrayList<E>
{

    
    private static final long serialVersionUID = -4359104200059285861L;
    /**
     * The filename.
     */
    public String filename;
    private boolean file_exists = false;
    private boolean file_read = false;
    
    /**
     * Instantiates a new file reader list.
     * 
     * @param _filename the _filename
     */
    public FileReaderList(String _filename)
    {
        this.filename = _filename;
        specialInit();
        readFile();
    }
    
    /**
     * Special init.
     */
    public abstract void specialInit();
    
    
    /**
     * Does file exists.
     * 
     * @return true, if successful
     */
    public boolean doesFileExists()
    {
        //this.file_exists = (new File(this.filename)).exists();
        return this.file_exists;
    }

    
    /**
     * Was file read.
     * 
     * @return true, if successful
     */
    public boolean wasFileRead()
    {
        //this.file_exists = (new File(this.filename)).exists();
        return this.file_read;
    }
    
    
    /**
     * Read file.
     */
    public void readFile()
    {
        try
        {
            if (!(new File(this.filename)).exists())
                return;
            
            this.file_exists = true;
            
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            String line = null;
            
            file_read = true;
            
            while((line = br.readLine()) != null)
            {
                processFileEntry(line);
            }
            
            br.close();
        }
        catch(Exception ex)
        {
            System.out.println("FileReaderList: Error reading file: " + this.filename);
            ex.printStackTrace();
        }
    }
    
    
    /**
     * Process file entry.
     * 
     * @param line the line
     */
    public abstract void processFileEntry(String line);
    

}

