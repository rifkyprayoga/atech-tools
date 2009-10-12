package com.atech.i18n.tool.simple.data;

import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.utils.file.FileReaderList;

// TODO: Auto-generated Javadoc
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
 *
*/
public class MasterFileReader extends FileReaderList<DataEntryRaw>
{

    private static final long serialVersionUID = 1402194555133545330L;
 
    /**
     * The m_da.
     */
    DataAccessTT m_da = null;
    
    /**
     * Instantiates a new master file reader.
     * 
     * @param filename the filename
     */
    public MasterFileReader(String filename)
    {
        super(filename);
    }

    /** 
     * specialInit
     */
    public void specialInit()
    {
        this.add(new DataEntryRaw(1));
        
        this.m_da = DataAccessTT.getInstance();
        //System.out.println("m_da: " + m_da);
    }
    
    
    /** 
     * processFileEntry
     */
    @Override
    public void processFileEntry(String line)
    {
        //System.out.println("Process: " + line);
        line = line.trim();
        if (line.startsWith("#"))
        { 
            // comments
            // TODO
            
            
            
            
        }
        else if (line.contains("="))
        {
            // keywords
            int idx = line.indexOf("=");
            
            String key = line.substring(0, idx);
            String val = line.substring(idx+1);
            
            this.add(new DataEntryRaw(DataEntryRaw.DATA_ENTRY_TRANSLATION, key));
            
            DataEntry de = new DataEntry(key, val);
            
            m_da.getTranslationData().addTranslationData(de);
        }
        // else ignore
        
    }

}
