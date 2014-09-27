package com.atech.graphics.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  This file is part of ATech Tools library.
 *  
 *  CalendarEvent - Event for CalendarListeners
 *  Copyright (C) 2002  Dieter Schultschik
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
 *  Author:   schultd (taken from ggc project)
 *  Author:   Andy
 */

public class CalendarEvent
{
    /**
     * Calendar Event: Day
     */
    public static final int DAY_CHANGED = 101;

    /**
     * Calendar Event: Month
     */
    public static final int MONTH_CHANGED = 102;

    /**
     * Calendar Event: Year
     */
    public static final int YEAR_CHANGED = 103;

    /**
     * Calendar Event: Date
     */
    public static final int DATE_CHANGED = 105;

    GregorianCalendar date;
    private int event = 0;

    /**
     * Constructor
     * 
     * @param date
     * @param event
     */
    public CalendarEvent(GregorianCalendar date, int event)
    {
        this.date = date;
        this.event = event;
    }

    /**
     * Get New Month
     * 
     * @return
     */
    public int getNewMonth()
    {
        return date.get(Calendar.MONTH);
    }

    /**
     * Get New Year
     * 
     * @return
     */
    public int getNewYear()
    {
        return date.get(Calendar.YEAR);
    }

    /**
     * Get New Day
     * 
     * @return
     */
    public int getNewDay()
    {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get New Date
     * 
     * @return
     */
    public long getNewDate()
    {
        return date.getTime().getTime();
    }

    /**
     * Get New Calendar
     * 
     * @return
     */
    public GregorianCalendar getNewCalendar()
    {
        return date;
    }

    /**
     * Get Event
     * 
     * @return
     */
    public int getEvent()
    {
        return event;
    }
}
