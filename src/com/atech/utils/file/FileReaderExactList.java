package com.atech.utils.file;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

public class FileReaderExactList extends FileReaderList<List<String>>
{

    protected long currentLine = 0;
    protected List<String> header = null;
    protected int headerLength = 0;
    protected int bodyLength = 0;
    protected int footerLength = 0;
    private String entryDelimiter = ";";


    /**
     * Instantiates a new file reader list.
     *
     * @param _filename the _filename
     */
    public FileReaderExactList(String _filename, int headerLength, int bodyLength, int footerLength,
            String entryDelimiter)
    {
        super(_filename, false);

        this.headerLength = headerLength;
        this.bodyLength = bodyLength;
        this.footerLength = footerLength;
        this.entryDelimiter = entryDelimiter;

        this.process();
    }


    /**
     * Special init.
     */
    @Override
    public void specialInit()
    {
    }


    /**
     * Process file entry.
     *
     * @param line the line
     */
    @Override
    public void processFileEntry(String line)
    {
        if (currentLine < headerLength)
        {
            if (header == null)
                header = new ArrayList<String>();

            header.add(line);
        }
        else
        {
            StringTokenizer stringTokenizer = new StringTokenizer(line, entryDelimiter);

            ArrayList<String> listEntry = new ArrayList<String>();

            while (stringTokenizer.hasMoreTokens())
            {
                listEntry.add(stringTokenizer.nextToken());
            }

            this.add(listEntry);
        }

    }

}
