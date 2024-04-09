package com.atech.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@Slf4j
public class PropertiesFile extends FileReaderHashMap<String, String> implements PropertiesWriterInterface
{

    private static final long serialVersionUID = 4486922769091769152L;
    private boolean in_jar = false;

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesFile.class);

    @Getter
    private Properties properties = null;
    //@Getter
    //private boolean fileRead = false;


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

    public PropertiesFile(String filename, boolean required, boolean isStatic)
    {
        super(filename, required, isStatic);
    }

    /**
     * Instantiates a new properties file.
     * 
     * @param data 
     */
    public PropertiesFile(Map<String, String> data) {
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
                    LOG.warn("File " + filename + " not found.");
                    return;
                }
            }

            properties = new Properties();

            if (in_jar || isStaticFile) {
                InputStream fin = getClass().getClassLoader().getResourceAsStream(filename);
                properties.load(fin);
                fin.close();
            } else {
                FileInputStream in = new FileInputStream(filename);
                properties.load(in);
                in.close();
            }

            file_read = true;

            for (Enumeration<Object> en = properties.keys(); en.hasMoreElements();)
            {
                String key = (String) en.nextElement();
                this.put(key, properties.getProperty(key));
            }

        }
        catch (Exception ex) {
            LOG.error("PropertiesFile: Error reading file: " + this.filename);
            ex.printStackTrace();
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

    @Override
    public Map<String, String> getData() {

        Map<String, String> outMap = new HashMap<String, String>();

        outMap.putAll(this);

        return outMap;
    }


}
