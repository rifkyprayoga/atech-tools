package com.atech.graphics.components.about;

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

public class CreditsEntry
{
    private String name;
    private String email;
    private String what_does;

    /**
     * Instantiates a new credits entry.
     * 
     * @param name the name
     * @param email the email
     * @param what_does the what_does
     */
    public CreditsEntry(String name, String email, String what_does)
    {
        this.name = name;
        this.email = email;
        this.what_does = what_does;
    }

    /**
     * Gets the hTML code.
     * 
     * @return the hTML code
     */
    public String getHTMLCode()
    {
        if (this.email == null || this.email.trim().length() == 0)
            return "<table width=\"100%\" border=\"0\"><tr><td width=\"20%\">&nbsp;</td><td><font color=\"#006600\">"
                    + this.name
                    + "</font></td></tr></table>"
                    + "<table width=\"100%\" border=\"0\"><tr><td width=\"20%\">&nbsp;</td><td><font color=\"#990000\">"
                    + this.what_does + "</font></td></tr></table><br>";
        else
            return "<table width=\"100%\" border=\"0\"><tr><td width=\"20%\">&nbsp;</td><td><font color=\"#006600\">"
                    + this.name
                    + "</font></td></tr></table>"
                    + "<table width=\"100%\" border=\"0\"><tr><td width=\"20%\">&nbsp;</td><td><font color=\"#000099\">"
                    + this.email
                    + "</font></td></tr></table>"
                    + "<table width=\"100%\" border=\"0\"><tr><td width=\"20%\">&nbsp;</td><td><font color=\"#990000\">"
                    + this.what_does + "</font></td></tr></table><br>";
    }

}
