package com.atech.utils.logs;

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

public class LogEntry
{

    private long time_ms;

    private int type;

    private String message;

    private Object object;

    /**
     * Instantiates a new log entry.
     * 
     * @param type_ the type_
     * @param message_ the message_
     */
    public LogEntry(int type_, String message_)
    {
        this(type_, message_, null);
    }

    /**
     * Instantiates a new log entry.
     * 
     * @param type_ the type_
     * @param message_ the message_
     * @param obj the obj
     */
    public LogEntry(int type_, String message_, Object obj)
    {
        this.type = type_;
        this.message = message_;
        this.object = obj;
        this.time_ms = System.currentTimeMillis();
    }

    /**
     * Sets the time ms.
     * 
     * @param time_ms the new time ms
     */
    public void setTimeMs(long time_ms)
    {
        this.time_ms = time_ms;
    }

    /**
     * Gets the time ms.
     * 
     * @return the time ms
     */
    public long getTimeMs()
    {
        return time_ms;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * Sets the message.
     * 
     * @param message the new message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Gets the message.
     * 
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets the object.
     * 
     * @param object the new object
     */
    public void setObject(Object object)
    {
        this.object = object;
    }

    /**
     * Gets the object.
     * 
     * @return the object
     */
    public Object getObject()
    {
        return object;
    }

}
