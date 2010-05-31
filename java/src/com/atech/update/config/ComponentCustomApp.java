
package com.atech.update.config;

import com.atech.update.startup.StartupUtil;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ComponentCustomApp - Custom Application definition class
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

public class ComponentCustomApp //implements ComponentInterface
{
    
    /**
     * Id
     */
    public String id;
    
	/**
	 * Filename 
	 */
	public String filename;
	
	/**
	 * Main Class 
	 */
	public String main_class;
	
	/**
	 * Is Binary Needed 
	 */
	public boolean binary_needed;
	
	/**
	 * Special Parameters
	 */
	public String special_parameters;
	
	/**
	 * Needs JDBC
	 */
	public boolean needs_jdbc;
	

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param filename
	 * @param main_class
	 * @param binary_needed
	 * @param special_parameters
	 * @param needs_jdbc
	 */
	public ComponentCustomApp(String id, String filename, String main_class, String binary_needed, String special_parameters, String needs_jdbc)
	{
		this.id = id;
		this.filename = filename;
		this.main_class = main_class;
		this.binary_needed = StartupUtil.isOptionEnabled(binary_needed);
		this.special_parameters = special_parameters;
        this.needs_jdbc = StartupUtil.isOptionEnabled(needs_jdbc);
	}


    /**
     * Constructor
     */
    public ComponentCustomApp()
    {
    }

	
    public String toString()
    {
        return "ComponentCustomApp [id=" + this.id + ", filename=" + this.filename + ", main_class=" + this.main_class +
            ", binary_needed=" + this.binary_needed + ", needs_jdbc=" + this.needs_jdbc + 
            ", special_parameters=" + this.special_parameters + "]";
    }
    
    
	
}


