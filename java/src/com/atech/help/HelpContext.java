package com.atech.help;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JMenuItem;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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


public class HelpContext 
{

	
    // help stuff
    private HelpBroker main_help_broker = null;
    private CSH.DisplayHelpFromSource csh = null;
    private String main_help_set_name = null;
    private JMenuItem help_item = null;
    private HelpSet main_help_set = null;
	

    
	public HelpContext(String main_help_set)
	{
		this.main_help_set_name = main_help_set;
	}
	
    
    public HelpBroker getMainHelpBroker()
    {
    	return this.main_help_broker;
    }
    
    public void setMainHelpBroker(HelpBroker broker)
    {
    	this.main_help_broker = broker;
    }
    
    
    public void setHelpItem(JMenuItem item)
    {
    	this.help_item = item;
    }
    

    public JMenuItem getHelpItem()
    {
    	return this.help_item;
    }
    
    
    public void setMainHelpSetName(String help_set_name)
    {
    	this.main_help_set_name = help_set_name;
    }
    
    public String getMainHelpSetName()
    {
    	return this.main_help_set_name;
    }
    

    public void setDisplayHelpFromSourceInstance(CSH.DisplayHelpFromSource csh)
    {
    	this.csh = csh;
    }

    
    public CSH.DisplayHelpFromSource getDisplayHelpFromSourceInstance()
    {
    	return this.csh;
    }

    
    public void setMainHelpSet(HelpSet help_set)
    {
    	this.main_help_set = help_set;
    }
    
    public HelpSet getMainHelpSet()
    {
    	return this.main_help_set;
    }
    
    
    //public HelpSet main_help_set = null;

    
    
    //public CSH.DisplayHelpFromSource csh = null;
    
    
    
    
}
