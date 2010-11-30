package com.atech.update.client.data;

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

public class UpdateSettings
{
    public String update_server = null;
    public String output_path = "../data/update/files/";
    
    public UpdateSettings()
    {
        this.setUpdateServer("");
    }
    
    
    public void setUpdateServer(String upd_server)
    {
        
        String server_name = "http://192.168.4.3:8080/";
        
        
        
        // check server name
        server_name = server_name.trim();
        
        if (server_name.length()==0)
        {
            server_name = "http://www.atech-software.com/";
        }
        else
        {
            if (!server_name.startsWith("http://"))
                server_name = "http://" + server_name;
            
            if (!server_name.endsWith("/"))
                server_name = server_name + "/";
        }
        
        
        this.update_server = server_name;
        
    }
    
    
}
