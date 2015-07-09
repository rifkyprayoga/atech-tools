package com.atech.i18n.tool.simple.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.utils.file.FileReaderList;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  MasterFileReader - Master File reader
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
 *
 */

public class MasterFileReader extends FileReaderList<DataEntryRaw>
{

    private static final long serialVersionUID = 1402194555133545330L;

    private boolean is_master_file_xa = false;
    private TranslationConfiguration m_config = null;
    private static Log log = LogFactory.getLog(MasterFileReader.class);

    /**
     * The dataAccess.
     */
    DataAccessTT m_da = null;


    /**
     * Instantiates a new master file reader
     * 
     * @param filename the filename
     */
    public MasterFileReader(String filename)
    {
        super(filename);
        log.debug("MasterFileReader: " + this + ", filename=" + filename);
    }


    /** 
     * Special Init
     */
    @Override
    public void specialInit()
    {
        this.add(new DataEntryRaw(1));

        this.m_da = DataAccessTT.getInstance();
        this.m_config = this.m_da.getTranslationConfig();
    }

    DataEntryRaw group = null;
    DataEntryRaw sub_group = null;


    /** 
     * processFileEntry
     */
    @Override
    public void processFileEntry(String line)
    {
        line = line.trim();
        if (line.startsWith("#"))
        {
            if (line.contains("!S!"))
            {
                String desc = line.substring(line.indexOf("!S!") + 4).trim();
                String[] s = m_da.splitString(desc, "=");
                m_config.put(s[0], s[1]);
            }
            else if (line.contains("!MASTER_FILE!"))
            {
                this.is_master_file_xa = true;

                m_da.setIsMasterFileMasterFile(true);
            }
            else if (line.contains("!G!"))
            {
                String desc = line.substring(line.indexOf("!G!") + 4).trim();
                int pri = 0;

                if (desc.contains("["))
                {
                    try
                    {
                        pri = Integer.parseInt(desc.substring(desc.indexOf("[") + 1, desc.indexOf("]")));
                        desc = desc.substring(0, desc.indexOf("[")).trim();
                    }
                    catch (Exception ex)
                    {
                        log.error("Exception on reading prioroty from master file. Ex: " + ex, ex);
                    }

                }

                this.group = new DataEntryRaw(DataEntryRaw.DATA_ENTRY_GROUP, desc, pri);
                this.sub_group = null;
                this.add(group);
            }
            else if (line.contains("!SG!"))
            {
                String desc = line.substring(line.indexOf("!SG!") + 4).trim();
                this.sub_group = new DataEntryRaw(DataEntryRaw.DATA_ENTRY_SUBGROUP, desc, group);
                this.add(sub_group);
            }

        }
        else if (line.contains("="))
        {
            // keywords
            int idx = line.indexOf("=");

            String key = line.substring(0, idx);
            String val = line.substring(idx + 1);

            DataEntryRaw der = new DataEntryRaw(DataEntryRaw.DATA_ENTRY_TRANSLATION, key, group, sub_group);

            this.add(der);

            DataEntry de = new DataEntry(key, val, der);

            m_da.getTranslationData().addTranslationData(de);
        }
        // else ignore

    }


    /**
     * Is Master File
     * 
     * @return
     */
    public boolean isMasterFile()
    {
        return this.is_master_file_xa;
    }

}
