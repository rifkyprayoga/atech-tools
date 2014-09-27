package com.atech.update.client.data;

import com.atech.update.config.ComponentEntry;

/**
 *  This file is part of ATech Tools library.
 *  
 *  UpdateDialog - Dialog for updates
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
 *  @author Andy {andy@atech-software.com}
 *
*/

public class UpdateComponentEntry extends ComponentEntry
{

    public long estimated_crc = 0L;

    public long file_id = 0L;

    public long requested_version_id = 0L;

    public int action = 0;

    public static final int ACTION_GET_FILE_BINARY = 1;

    public String output_file = null;

    public long file_size = 0L;

    public String getFileCommand()
    {
        if (this.action == ACTION_GET_FILE_BINARY)
            return "ATechUpdateGetFile?" + "file_id=" + this.file_id + "&" + "version_requested="
                    + this.requested_version_id;

        return null;

    }

    public void test()
    {
        // this.
    }

}
