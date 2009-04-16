package com.atech.utils.logs;




/**
 *  Application:   GGC - GNU Gluco Control
 *  Plug-in:       GGC PlugIn Base (base class for all plugins)
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
 *  Filename:     LogEntryType  
 *  Description:  Log Entry Type (for our personal logging)
 * 
 *  Author: Andy {andy@atech-software.com}
 */


public class LogEntryType
{
    /**
     * Log: Trace
     */
    public static final int TRACE = 1;
    
    /**
     * Log: Debug
     */
    public static final int DEBUG = 2;
    
    /**
     * Log: Info
     */
    public static final int INFO = 3;
    
    /**
     * Log: Warning
     */
    public static final int WARNING = 4;
    
    /**
     * Log: Error
     */
    public static final int ERROR = 5;
    
}
