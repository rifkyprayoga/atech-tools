package com.atech.app.data.about;

// TODO: Auto-generated Javadoc

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2024  Andy (Aleksander) Rozman (Atech-Software)
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleInfoEntry {

    public String name;
    public String version;
    public String description;


    /**
     * Gets the HTML code.
     * 
     * @return the HTML code
     */
    public String getHTMLCode()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("<br><table width=\"100%\" border=\"0\"><tr><td width=\"10%\">&nbsp;</td><td><font color=\"#006600\">" + this.name +
                " (v" + this.version + ")</font></td></tr></table>");
        sb.append("<table width=\"100%\" border=\"0\"><tr><td width=\"10%\">&nbsp;</td><td><font color=\"#000099\">"
                + this.description + "</font></td></tr></table>");

        return sb.toString();
    }

}
