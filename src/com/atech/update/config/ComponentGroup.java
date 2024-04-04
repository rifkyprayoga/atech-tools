package com.atech.update.config;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ComponentGroup - Component group
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy
 *
*/

public class ComponentGroup implements ComponentInterface
{

    /**
     * Id 
     */
    public int id;

    /**
     * Name
     */
    public String name;

    /**
     * Children
     */
    public List<ComponentEntry> children;

    /**
     * Children By Id 
     */
    public Hashtable<String, ComponentEntry> children_id;

    /**
     * Children map (name -> id) 
     */
    public Hashtable<String, String> children_map_id_name;


    /**
     * Constructor
     */
    public ComponentGroup()
    {
        this.children = new ArrayList<ComponentEntry>();
        this.children_id = new Hashtable<String, ComponentEntry>();
        this.children_map_id_name = new Hashtable<String, String>();
    }


    /**
     * Constructor
     * 
     * @param id
     * @param name
     */
    public ComponentGroup(String id, String name)
    {
        this.id = Integer.parseInt(id);
        this.name = name;

        this.children = new ArrayList<ComponentEntry>();
        this.children_id = new Hashtable<String, ComponentEntry>();
        this.children_map_id_name = new Hashtable<String, String>();
    }


    /**
     * Constructor
     * 
     * @param id
     * @param name
     */
    public ComponentGroup(int id, String name)
    {
        this.id = id;
        this.name = name;

        this.children = new ArrayList<ComponentEntry>();
        this.children_id = new Hashtable<String, ComponentEntry>();
        this.children_map_id_name = new Hashtable<String, String>();
    }


    /**
     * Add Child
     * 
     * @param ce ComponentEntry instance
     */
    public void addChild(ComponentEntry ce)
    {
        this.children.add(ce);
        this.children_id.put("" + ce.id, ce);
        this.children_map_id_name.put(ce.name, "" + ce.id);
    }


    /**
     * Children Count
     * 
     * @return
     */
    public int childrenCount()
    {
        return this.children.size();
    }


    /**
     * Get Child At
     * 
     * @param index
     * @return
     */
    public ComponentEntry getChildAt(int index)
    {
        return this.children.get(index);
    }


    /** 
     * Get Column Value
     * 
     * @param index index of column
     * @return value of column
     */
    public String getColumnValue(int index)
    {
        if (index == 0)
            return this.name;
        else
            return "";
    }


    /** 
     * Is Element Group
     * @return true if element is group
     */
    public boolean isGroup()
    {
        return true;
    }


    @Override
    public String toString()
    {
        return "ComponentGroup [id=" + this.id + ", name=" + this.name + "]";
    }

}
