package com.atech.update.web.data;

import java.util.ArrayList;
import java.util.Hashtable;

public class ComponentElement
{
    public long id = 0L;
    
    public String name = null;
    
    public String target_dir = null;
    
    Hashtable<Long,ComponentVersion> versions = new Hashtable<Long,ComponentVersion>();
    
    public void loadComponentVersions(ArrayList<ComponentVersion> list)
    {
        for(int i=0; i<list.size(); i++)
        {
            if (list.get(i).component_id==id)
            {
                ComponentVersion cv = list.get(i);
                this.versions.put(cv.version, cv);
            }
        }
        
        
        
    }
    
    
}
