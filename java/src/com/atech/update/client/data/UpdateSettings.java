package com.atech.update.client.data;

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
