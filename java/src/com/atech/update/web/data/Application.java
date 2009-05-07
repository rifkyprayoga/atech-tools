package com.atech.update.web.data;

import java.util.ArrayList;
import java.util.Hashtable;

public class Application
{
    public long id = 0L;
    
    public String name = null;
    
    public String desc = null;
    
    public ArrayList<ApplicationVersion> versions = new ArrayList<ApplicationVersion>();
    public Hashtable<Long,ApplicationComponent> app_components = new Hashtable<Long,ApplicationComponent>(); 
    
    
    public void loadApplicationComponents(ArrayList<ApplicationComponent> groups_in)
    {
        
        for(int i=0; i<groups_in.size(); i++)
        {
            ApplicationComponent ac = groups_in.get(i); 
            if (ac.application_id==id)
            {
                this.app_components.put(ac.component_id, ac);
            }
        }
    }

    
    
    public void loadGroups(ArrayList<ApplicationGroup> groups_in)
    {
        ArrayList<ApplicationGroup> groups = new ArrayList<ApplicationGroup>();
        
        for(int i=0; i<groups_in.size(); i++)
        {
            if (groups_in.get(i).application_id==id)
            {
                groups.add(groups_in.get(i));
            }
        }
        
        for(int i=0; i<this.versions.size(); i++)
        {
            this.versions.get(i).loadGroups(groups);
        }

    }
    
    
}
