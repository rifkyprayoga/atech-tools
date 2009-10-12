package com.atech.update.web.data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.atech.update.web.db.DataLayerUpdateServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationVersion.
 */
public class ApplicationVersion
{
    
    /**
     * The id.
     */
    public long id = 0L;
    
    /**
     * The application_id.
     */
    public long application_id = 0L;

    /**
     * The version.
     */
    public String version = null;
    
    /**
     * The version_num.
     */
    public long version_num = 0;
    
    /**
     * The db_version.
     */
    public int db_version = 0;
 
    
    /**
     * The groups.
     */
    public ArrayList<ApplicationGroup> groups = new ArrayList<ApplicationGroup>();
    
    /**
     * The main_components.
     */
    public ArrayList<ApplicationVersionComponent> main_components = new ArrayList<ApplicationVersionComponent>();
    
    /**
     * The comp_dependencies.
     */
    public Hashtable<Long,Long> comp_dependencies = new Hashtable<Long,Long>(); 
    
    /**
     * Load groups.
     * 
     * @param groups_in the groups_in
     */
    public void loadGroups(ArrayList<ApplicationGroup> groups_in)
    {
        for(int i=0; i<groups_in.size(); i++)
        {
            if (groups_in.get(i).first_version >= version_num)
                groups.add(groups_in.get(i));
        }
    }
    
    /**
     * Load application version components.
     * 
     * @param list the list
     * @param dl the dl
     */
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

    
    /**
     * Refresh internal dependencies.
     * 
     * @param dl the dl
     */
    @SuppressWarnings("unchecked")
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
    
    
    /**
     * Refresh dependencies.
     * 
     * @param deps the deps
     * @param deps_old the deps_old
     */
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
