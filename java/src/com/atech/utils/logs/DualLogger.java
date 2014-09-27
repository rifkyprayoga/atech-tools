package com.atech.utils.logs;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

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

public class DualLogger
{
    // private static Log log = LogFactory.getLog(DualLogger.class);
    /**
     * The logger.
     */
    static Logger logger = Logger.getLogger(DualLogger.class);

    /**
     * The settings.
     */
    LogSettings settings = null;

    /**
     * Instantiates a new dual logger.
     */
    public DualLogger()
    {
        settings = new LogSettings();
        // logger.log("com.atech.utils.logs.DualLogger.", level, message, t)
        // logger.lo//
    }

    /**
     * Trace.
     * 
     * @param lsi the lsi
     * @param msg the msg
     * @param t the t
     */
    public void trace(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.TRACE, msg, t);
    }

    /**
     * Trace.
     * 
     * @param lsi the lsi
     * @param msg the msg
     */
    public void trace(LogSenderInterface lsi, String msg)
    {
        this.trace(lsi, msg, null);
    }

    /**
     * Info.
     * 
     * @param lsi the lsi
     * @param msg the msg
     * @param t the t
     */
    public void info(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.INFO, msg, t);
    }

    /**
     * Info.
     * 
     * @param lsi the lsi
     * @param msg the msg
     */
    public void info(LogSenderInterface lsi, String msg)
    {
        this.info(lsi, msg, null);
    }

    /**
     * Debug.
     * 
     * @param lsi the lsi
     * @param msg the msg
     * @param t the t
     */
    public void debug(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.DEBUG, msg, t);
    }

    /**
     * Debug.
     * 
     * @param lsi the lsi
     * @param msg the msg
     */
    public void debug(LogSenderInterface lsi, String msg)
    {
        this.debug(lsi, msg, null);
    }

    /**
     * Error.
     * 
     * @param lsi the lsi
     * @param msg the msg
     * @param t the t
     */
    public void error(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.ERROR, msg, t);
    }

    /**
     * Error.
     * 
     * @param lsi the lsi
     * @param msg the msg
     */
    public void error(LogSenderInterface lsi, String msg)
    {
        this.error(lsi, msg, null);
    }

    /**
     * Warn.
     * 
     * @param lsi the lsi
     * @param msg the msg
     * @param t the t
     */
    public void warn(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.WARNING, msg, t);
    }

    /**
     * Warn.
     * 
     * @param lsi the lsi
     * @param msg the msg
     */
    public void warn(LogSenderInterface lsi, String msg)
    {
        this.warn(lsi, msg, null);
    }

    /**
     * Log.
     * 
     * @param lsi the lsi
     * @param type the type
     * @param msg the msg
     * @param t the t
     */
    public void log(LogSenderInterface lsi, int type, String msg, Throwable t)
    {
        logger.log("com.atech.utils.logs.DualLogger.", getPriority(type), msg, t);

        // TODO my logger

    }

    private Priority getPriority(int type)
    {

        return null;
    }

}
