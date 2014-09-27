package com.atech.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * 
 * 
 * Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * 
 * For additional information about this project please visit our project site on
 * http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 */

public class PropertiesFile extends FileReaderHashtable<String, String>
{

    private static final long serialVersionUID = 4486922769091769152L;
    private boolean in_jar = false;

    private static Log log = LogFactory.getLog(PropertiesFile.class);

    /**
     * Instantiates a new properties file.
     * 
     * @param filename the filename
     */
    public PropertiesFile(String filename)
    {
        super(filename);
    }

    public PropertiesFile(String filename, boolean required)
    {
        super(filename, required);
    }

    /**
     * Instantiates a new properties file.
     * 
     * @param data 
     * @param filename the filename
     */
    public PropertiesFile(Hashtable<String, String> data)
    {
        super();
        this.putAll(data);
    }

    /** 
     * readFile
     */
    @Override
    public void readFile()
    {
        try
        {

            if (!required)
            {
                if (!new File(this.filename).exists())
                {
                    log.warn("File " + filename + " not found.");
                    return;
                }
            }

            Properties props = new Properties();

            if (in_jar)
            {
                InputStream fin = getClass().getResourceAsStream(filename);
                props.load(fin);
                fin.close();
            }
            else
            {
                FileInputStream in = new FileInputStream(filename);
                props.load(in);
                in.close();
            }

            file_read = true;
            /*
             * BufferedReader br = new BufferedReader(new
             * FileReader(this.filename));
             * String line = null;
             * while((line = br.readLine()) != null)
             * {
             * processFileEntry(line);
             * }
             */

            for (Enumeration<Object> en = props.keys(); en.hasMoreElements();)
            {
                String key = (String) en.nextElement();
                this.put(key, props.getProperty(key));
            }
            // this.putAll(props.)

        }
        catch (Exception ex)
        {
            log.error("PropertiesFile: Error reading file: " + this.filename);
            // ex.printStackTrace();
            file_read = false;
        }
    }

    /** 
     * processFileEntry
     */
    @Override
    public void processFileEntry(String line)
    {
        line = line.trim();
        if (!line.startsWith("#") && line.contains("="))
        {
            int idx = line.indexOf("=");
            this.put(line.substring(0, idx), line.substring(idx + 1));
        }
    }

}
