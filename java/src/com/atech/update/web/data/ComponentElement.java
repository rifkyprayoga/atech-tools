package com.atech.update.web.data;

import java.util.ArrayList;
import java.util.Hashtable;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentElement.
 */
public class ComponentElement
{
    
    /**
     * The id.
     */
    public long id = 0L;
    
    /**
     * The name.
     */
    public String name = null;
    
    /**
     * The target_dir.
     */
    public String target_dir = null;
    
    /**
     * The versions.
     */
    Hashtable<Long,ComponentVersion> versions = new Hashtable<Long,ComponentVersion>();
    
    /**
     * Load component versions.
     * 
     * @param list the list
     */
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
