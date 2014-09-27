package com.atech.graphics.calendar;

/*
 *  Filename: CalendarModel
 *  Purpose:  A model for every JComponent which is involved in the Calendar
 *            widgets, to get calendardata.
 *
 *  Author:   schultd (taken from ggc project)
 */

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *  This file is part of ATech Tools library.
 *  
 *  CalendarModel - A model for every JComponent which is involved in the Calendar
 *            widgets, to get calendar data.
 *  Copyright (C) 2002  schultd (taken from ggc project)
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
 *  @author schultd
 *
*/

public class CalendarModel
{
    private GregorianCalendar gc;
    CalendarPane cp;

    /**
     * Constructor
     * 
     * @param date
     * @param cp
     */
    public CalendarModel(GregorianCalendar date, CalendarPane cp)
    {
        this.cp = cp;
        gc = date;
        // gc.setTime(date);
    }

    /**
     * Set Date
     * 
     * @param date
     */
    public void setDate(Date date)
    {
        gc.setTime(date);
        cp.notifyListeners(CalendarEvent.DATE_CHANGED);
    }

    /**
     * Set Year
     * 
     * @param year
     */
    public void setYear(int year)
    {
        gc.set(Calendar.YEAR, year);
        cp.notifyListeners(CalendarEvent.YEAR_CHANGED);
    }

    /**
     * Set Month
     * 
     * @param month
     */
    public void setMonth(int month)
    {
        gc.set(Calendar.MONTH, month);
        cp.notifyListeners(CalendarEvent.MONTH_CHANGED);
    }

    /**
     * Set Day
     * 
     * @param day
     */
    public void setDay(int day)
    {
        gc.set(Calendar.DAY_OF_MONTH, day);
        cp.notifyListeners(CalendarEvent.DAY_CHANGED);
    }

    /**
     * Get Year
     * 
     * @return
     */
    public int getYear()
    {
        return gc.get(Calendar.YEAR);
    }

    /**
     * Get Month
     * 
     * @return
     */
    public int getMonth()
    {
        return gc.get(Calendar.MONTH);
    }

    /**
     * Get Day
     * 
     * @return
     */
    public int getDay()
    {
        return gc.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get Date
     * 
     * @return
     */
    public Date getDate()
    {
        return gc.getTime();
    }

    /**
     * Get First Day In Month
     * 
     * @return
     */
    public int getFirstDayInMonth()
    {
        int old = getDay();
        gc.set(Calendar.DAY_OF_MONTH, 1);
        int r = gc.get(Calendar.DAY_OF_WEEK);
        gc.set(Calendar.DAY_OF_MONTH, old);
        return r;
    }

    /**
     * Get Number of Days in Month
     * 
     * @return
     */
    public int getNumDaysInMonth()
    {
        return gc.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
