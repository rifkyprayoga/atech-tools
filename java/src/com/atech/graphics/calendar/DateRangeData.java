package com.atech.graphics.calendar; 

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DateRangeData - Class containing data for Range
 *  
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

public class DateRangeData 
{

    /**
     * Range: None
     */
    public static final int RANGE_NONE = 0;
    
    /**
     * Range: 1 Week
     */
    public static final int RANGE_ONE_WEEK = 1;
    
    /**
     * Range: 2 Week
     */
    public static final int RANGE_TWO_WEEKS = 2;
    
    
    /**
     * Range: 1 Month
     */
    public static final int RANGE_ONE_MONTH = 3;
    
    /**
     * Range: 3 Months
     */
    public static final int RANGE_THREE_MONTHS = 4;

    /**
     * Range: Custom
     */
    public static final int RANGE_CUSTOM = 5;
    
    
    private int daterange_type = 0;
    private GregorianCalendar gc_start = null;
    private GregorianCalendar gc_end = null;
    
    /**
     * Constructor
     */
    public DateRangeData()
    {
        this.gc_end = new GregorianCalendar();
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(GregorianCalendar.MONTH, -1);
        this.gc_start = gc;
    }
    
    /**
     * Constructor
     * 
     * @param type
     * @param gc1
     * @param gc2
     */
    public DateRangeData(int type, GregorianCalendar gc1, GregorianCalendar gc2)
    {
        setRange(type, gc1, gc2);
    }
    
    
    /**
     * Set Range
     * 
     * @param type
     * @param gc1
     * @param gc2
     */
    public void setRange(int type, GregorianCalendar gc1, GregorianCalendar gc2)
    {
        if (type==RANGE_NONE)
        {
            return;
        }
        else if (type==RANGE_ONE_WEEK)
        {
            this.gc_end = gc2;
            GregorianCalendar gc = (GregorianCalendar)gc2.clone();
            gc.add(GregorianCalendar.DAY_OF_MONTH, -7);
            this.gc_start = gc;
            
        }
        else if (type==RANGE_TWO_WEEKS)
        {
            this.gc_end = gc2;
            GregorianCalendar gc = (GregorianCalendar)gc2.clone();
            gc.add(GregorianCalendar.DAY_OF_MONTH, -14);
            this.gc_start = gc;
            
        }
        else if (type==RANGE_ONE_MONTH)
        {
            this.gc_end = gc2;
            GregorianCalendar gc = (GregorianCalendar)gc2.clone();
            gc.add(GregorianCalendar.MONTH, -1);
            this.gc_start = gc;
            
        }
        else if (type==RANGE_THREE_MONTHS)
        {
            this.gc_end = gc2;
            GregorianCalendar gc = (GregorianCalendar)gc2.clone();
            gc.add(GregorianCalendar.MONTH, -3);
            this.gc_start = gc;
        }
        else if (type==RANGE_CUSTOM)
        {
            this.gc_end = gc2;
            this.gc_start = gc1;
        }
        else
            return;
    
        this.daterange_type = type;
    
    }

    /**
     * Get Range Type
     * 
     * @return
     */
    public int getRangeType()
    {
        return this.daterange_type;
    }
    
    
    
    /**
     * Get Range From
     * 
     * @return
     */
    public GregorianCalendar getRangeFrom()
    {
        return this.gc_start;
    }

    
    /**
     * Get Range To
     * 
     * @return
     */
    public GregorianCalendar getRangeTo()
    {
        return this.gc_end;
    }
    

    public String toString()
    {
        return "DateRangeData [start=" + getGCInfo(gc_start) +  ", end= "+ getGCInfo(gc_end);
    }
    
    
    private String getGCInfo(GregorianCalendar gc)
    {
        return "" + gc.get(Calendar.DAY_OF_MONTH ) + "."+ gc.get(Calendar.MONTH) + "."+ gc.get(Calendar.YEAR) + " "+ gc.get(Calendar.HOUR_OF_DAY ) + ":" + gc.get(Calendar.MINUTE );        
    }
    

}
