package com.atech.update.web.data;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import com.atech.utils.ATDataAccess;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentVersion.
 */
public class ComponentVersion
{
    
    /**
     * The id.
     */
    public long id = 0L;
    
    /**
     * The component_id.
     */
    public long component_id = 0L;
    
    /**
     * The version.
     */
    public long version = 0L;
    
    /**
     * The version_desc.
     */
    public String version_desc = null;
    
    /**
     * The files.
     */
    public String files = null;
    
    /**
     * The dependencies.
     */
    public String dependencies = null;
    
    /**
     * The platforms_specific.
     */
    public boolean platforms_specific = false;
    
    /**
     * The platforms_type.
     */
    public int platforms_type = 1;
    
    /**
     * The platforms_suported.
     */
    public String platforms_suported = null;
    
    /**
     * The comment.
     */
    public String comment = null;
    
    /**
     * The component_version_special.
     */
    public ArrayList<ComponentVersionSpecial> component_version_special = null;
    
    /**
     * The version_dependencies.
     */
    public Hashtable<Long,Long> version_dependencies = new Hashtable<Long,Long>(); 
    
    /**
     * The Constant SQL_SELECT.
     */
    public static final String SQL_SELECT = "select id, component_id, version_desc, version, files, comment, " +
            " dependencies, platforms_specific, platforms_type, platforms_supported from component_version";


    
    
    /**
     * Instantiates a new component version.
     */
    public ComponentVersion()
    {
    }
 
    
    /**
     * Instantiates a new component version.
     * 
     * @param rs the rs
     */
    public ComponentVersion(ResultSet rs)
    {
        loadDbEntry(rs);
    }
    
    
    /**
     * Load db entry.
     * 
     * @param rs the rs
     */
    public void loadDbEntry(ResultSet rs) 
    {
        try
        {
            this.id = rs.getLong("id");
            this.component_id = rs.getLong("component_id"); 
            this.version_desc = rs.getString("version_desc"); 
            this.version = rs.getLong("version");
            this.files = rs.getString("files");
            this.comment = rs.getString("comment");
            this.dependencies = rs.getString("dependencies"); 
            this.platforms_specific = rs.getBoolean("platforms_specific"); 
            this.platforms_type = rs.getInt("platforms_type"); 
            this.platforms_suported = rs.getString("platforms_supported");
        }
        catch(Exception ex)
        {
            System.out.println("Error reading ComponentVersion object. Ex: " + ex);
        }
    }
    
    
    /**
     * Resolve dependencies.
     */
    public void resolveDependencies()
    {
        String[] elements = this.dependencies.split(";");
        ATDataAccess da = ATDataAccess.getInstance(); 
        
        
        for(int i=0; i<elements.length; i++)
        {
            String[] parts = elements[i].split("=");
            version_dependencies.put(da.getLongValueFromString(parts[0]), da.getLongValueFromString(parts[1]));
        }
    }
    
    
    
    
    /**
     * Load component version special.
     * 
     * @param lst the lst
     */
    public void loadComponentVersionSpecial(ArrayList<ComponentVersionSpecial> lst)
    {
        for(int i=0; i<lst.size(); i++)
        {
            ComponentVersionSpecial cvs = lst.get(i); 
            if (cvs.component_version_id==id)
            {
                addComponentVersionSpecial(cvs);
            }
        }

    }
    
    
    /**
     * Adds the component version special.
     * 
     * @param cvs the cvs
     */
    public void addComponentVersionSpecial(ComponentVersionSpecial cvs)
    {
        if (this.component_version_special ==null)
            this.component_version_special = new ArrayList<ComponentVersionSpecial>();
        
        this.component_version_special.add(cvs);
    }
    
    
}
