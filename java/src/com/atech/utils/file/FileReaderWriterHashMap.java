package com.atech.utils.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

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
 * @param <K> 
 * @param <V> 
 *
*/

public abstract class FileReaderWriterHashMap<K, V> extends HashMap<K, V>
{

    private static final long serialVersionUID = 8988247925694117522L;

    protected boolean file_read = false;
    protected boolean required = true;

    /**
     * Filename
     */
    public String filename;

    /**
     * Constructor
     *
     * @param filename
     */
    public FileReaderWriterHashMap(String filename)
    {
        this.filename = filename;
        readFile();
    }

    public FileReaderWriterHashMap(String filename, boolean _required)
    {
        this.filename = filename;
        this.required = _required;
        readFile();
    }

    /**
     * Empty Constructor
     */
    public FileReaderWriterHashMap()
    {
    }

    /**
     * Read File
     */
    public void readFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            String line = null;

            this.file_read = true;

            while ((line = br.readLine()) != null)
            {
                processFileEntry(line);
            }

            br.close();

        }
        catch (Exception ex)
        {
            System.out.println("FileReaderList: Error reading file: " + this.filename);
            this.file_read = false;
        }
    }

    /**
     * Process File Entry
     * 
     * @param line
     */
    public abstract void processFileEntry(String line);

    /**
     * Was File Read 
     * 
     * @return
     */
    public boolean wasFileRead()
    {
        return this.file_read;
    }

}
