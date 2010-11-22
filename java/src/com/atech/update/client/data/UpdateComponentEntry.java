package com.atech.update.client.data;

import com.atech.update.config.ComponentEntry;

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
        {
            return "ATechUpdateGetFile?" + 
            "file_id="+ this.file_id + "&" +
            "version_requested=" + this.requested_version_id;            
        }
        
        return null;
        
    }
    
    
    public void test()
    {
        //this.
    }
    
}
