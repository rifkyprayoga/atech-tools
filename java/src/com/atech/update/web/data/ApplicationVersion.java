package com.atech.update.web.data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.atech.update.web.db.DataLayerUpdateServlet;

public class ApplicationVersion
{
    public long id = 0L;
    
    public long application_id = 0L;

    public String version = null;
    
    public long version_num = 0;
    
    public int db_version = 0;
 
    
    public ArrayList<ApplicationGroup> groups = new ArrayList<ApplicationGroup>();
    public ArrayList<ApplicationVersionComponent> main_components = new ArrayList<ApplicationVersionComponent>();
    
    public Hashtable<Long,Long> comp_dependencies = new Hashtable<Long,Long>(); 
    
    public void loadGroups(ArrayList<ApplicationGroup> groups_in)
    {
        for(int i=0; i<groups_in.size(); i++)
        {
            if (groups_in.get(i).first_version >= version_num)
                groups.add(groups_in.get(i));
        }
    }
    
    public void loadApplicationVersionComponents(ArrayList<ApplicationVersionComponent> list, DataLayerUpdateServlet dl)
    {
        for(int i=0; i<list.size(); i++)
        {
            if (list.get(i).version_id==this.id)
            {
                ApplicationVersionComponent avc = list.get(i);
                
                this.main_components.add(avc);
                refreshDependencies(dl.components.get(avc.component_id).versions.get(avc.component_version).version_dependencies, this.comp_dependencies);
            }
        }
    }

    
    public void refreshInternalDependencies(DataLayerUpdateServlet dl)
    {
        Hashtable<Long,Long> deps = (Hashtable<Long,Long>)this.comp_dependencies.clone();
        
        for(Enumeration<Long> en=this.comp_dependencies.keys(); en.hasMoreElements(); )
        {
            long comp_id = en.nextElement().longValue();
            long comp_ver = deps.get(comp_id);
            
            refreshDependencies(dl.components.get(comp_id).versions.get(comp_ver).version_dependencies, deps);
        }
        /*
        for(int i=0; i<list.size(); i++)
        {
            if (list.get(i).version_id==this.id)
            {
                ApplicationVersionComponent avc = list.get(i);
                
                this.main_components.add(avc);
                refreshDependencies(dl.components.get(avc.component_id).versions.get(avc.component_version_id).version_dependencies);
            }
        }*/
    }
    
    
    public void refreshDependencies(Hashtable<Long,Long> deps, Hashtable<Long,Long> deps_old)
    {
        if ((deps==null) || (deps.size()==0))
            return;
        
        for(Enumeration<Long> en = deps.keys(); en.hasMoreElements(); )
        {
            long key = en.nextElement().longValue();
            
            if (deps_old.containsKey(key))
            {
                long old = deps_old.get(key).longValue();
                long new_one = deps.get(key).longValue();
                
                if (old<new_one)
                {
                    deps_old.remove(key);
                    deps_old.put(key, new_one);
                }
            }
            else
                deps_old.put(key, deps.get(key));
        }
    }
    
}
