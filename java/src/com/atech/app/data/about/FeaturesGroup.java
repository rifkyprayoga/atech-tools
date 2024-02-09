package com.atech.app.data.about;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
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

public class FeaturesGroup
{

    private String name;
    private ArrayList<FeaturesEntry> entries;


    /**
     * Instantiates a new features group.
     * 
     * @param name the name
     */
    public FeaturesGroup(String name)
    {
        this.name = name;
        this.entries = new ArrayList<FeaturesEntry>();
    }


    /**
     * Adds the features entry.
     * 
     * @param ce the ce
     */
    public void addFeaturesEntry(FeaturesEntry ce)
    {
        this.entries.add(ce);
    }


    /**
     * Gets the group start html.
     * 
     * @return the group start html
     */
    public String getGroupStartHTML()
    {
        return "<table width=\"100%\" border=\"0\"><tr><td width=\"5%\">&nbsp;</td><td><font color=\"#0099FF\">"
                + this.name + ":</font></td></tr></table>";
    }


    /**
     * Gets the group end html.
     * 
     * @return the group end html
     */
    public String getGroupEndHTML()
    {
        return "<br>";
    }


    /**
     * Gets the entries.
     * 
     * @return the entries
     */
    public ArrayList<FeaturesEntry> getEntries()
    {
        return this.entries;
    }

}
