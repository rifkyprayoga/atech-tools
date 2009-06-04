package com.atech.i18n.tool.client.admin;

import java.util.ArrayList;

import com.atech.i18n.tool.client.db.datalayer.AppModuleO;




/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     GGCTreeRoot
 *  Description:  Used for holding tree information for nutrition and meals
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */


public class TranslationTreeRoot 
{

    
    ArrayList<AppModuleO> list = null;
    
    /**
     * Constructor
     * 
     * @param type
     * @param db
     */
    public TranslationTreeRoot()
    {
        list = new ArrayList<AppModuleO>();
    }

    
    /**
     * Debug - printout
     * 
     * @param text
     */
    public void debug(String text)
    {
        System.out.println(text);
    }
    

    /**
     * Returns the child of parent at index index in the parent's child array.
     */
    public AppModuleO getChild(int index) 
    {
        debug("getChild:" + index);
        return this.list.get(index);
    }
    

    /**
     * Returns the number of children of parent.
     */
    public int getChildCount() 
    {
        debug("getChildCount:" );
        return this.list.size();
    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object child) 
    {
        return this.list.indexOf(child);
    }
    
    
    public String toString()
    {
        return "TRANSLATION_ADMIN";
    }
    
}
