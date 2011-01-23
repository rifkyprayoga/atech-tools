package com.atech.utils.data;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import com.atech.utils.ATDataAccessAbstract;


public class TimeRangesData
{

    public static final int RANGE_TODAY = 1;
    public static final int RANGE_THIS_WEEK = 2;
    public static final int RANGE_PREV_WEEK = 3;
    public static final int RANGE_THIS_MONTH = 4;
    public static final int RANGE_PREV_MONTH = 5;
    
    
    public static final int RANGE_ALL = 100;
    
    
    Hashtable<Integer,Hashtable<String, Long>> data_procesor = null;
    
    GregorianCalendar current_gc = null;
    int[] range_selector= null;
    int datetime_type = ATechDate.FORMAT_DATE_ONLY;
    ATDataAccessAbstract m_da;
    
    public TimeRangesData(GregorianCalendar curr_gc, int[] rnge_selector, int dt_type, ATDataAccessAbstract da)
    {
        this.m_da = da;
        this.current_gc = curr_gc;
        data_procesor = new Hashtable<Integer,Hashtable<String, Long>>();
        this.datetime_type = dt_type;
        this.range_selector = rnge_selector;
        
        for(int i=0; i<range_selector.length; i++)
        {
            this.data_procesor.put(range_selector[i], getRange(range_selector[i]));
        }
    }
    
    
    public Hashtable<String,Long> getRange(int range_type)
    {
        Hashtable<String,Long> d = new Hashtable<String,Long>();
        long xa = 0;
        
        switch(range_type)
        {
            case RANGE_TODAY:
                {
                   xa = ATechDate.getATDateTimeFromGC(current_gc, ATechDate.FORMAT_DATE_ONLY);
                   d.put("FROM", xa);
                   d.put("TO", xa);
                }
                break;
                
            case RANGE_THIS_WEEK:
            case RANGE_PREV_WEEK:
                {
                    
                    long[] l = findRange(range_type);
                    
                    d.put("FROM", l[0]);
                    d.put("TO", l[1]);
                    
                }
                break;
                
                
            case  RANGE_THIS_MONTH:
            case  RANGE_PREV_MONTH:
                {
                    int y = this.current_gc.get(Calendar.YEAR);
                    int m = this.current_gc.get(Calendar.MONTH) + 1;
                    
                    if (range_type==RANGE_PREV_MONTH)
                    {
                        m = m-1;
                        
                        if (m==0)
                        {
                            m = 12;
                            y = y-1;
                        }
                    }

                    //System.out.println("Month: From="  + ATechDate.getATDateTimeFromDateParts(1, m, y) + ", To=" + ATechDate.getATDateTimeFromDateParts(31, m, y));
                    
                    
                    d.put("FROM", ATechDate.getATDateTimeFromDateParts(1, m, y));
                    d.put("TO", ATechDate.getATDateTimeFromDateParts(31, m, y));
                    
                }
                break;

                
            case RANGE_ALL: 
                {
                    d.put("FROM", 0L);
                    d.put("TO", Long.MAX_VALUE);
                }
                break;
        }
        
        
        
        return d;
    }
    

    public long[] findRange(int range_type)
    {
        long[] l = new long[2];
        
        GregorianCalendar gc = (GregorianCalendar)this.current_gc.clone();
        
        int week = gc.get(Calendar.WEEK_OF_YEAR);
        int y = gc.get(Calendar.YEAR);
        
        if (range_type == RANGE_THIS_WEEK)
        {
            gc.add(Calendar.DAY_OF_YEAR, -8);
        }
        else
        {
            week--;
            
            if (week==0)
            {
                week = 52;
                y = y-1;
            }
            
            gc.add(Calendar.DAY_OF_YEAR, -16);
            
        }
     
        boolean found = false;
        
        for(int i=0; i<35; i++)
        {
            if ((gc.get(Calendar.WEEK_OF_YEAR)==week) && (!found))
            {
                found = true;
                l[0] = ATechDate.getATDateTimeFromGC(gc, ATechDate.FORMAT_DATE_ONLY);
            }
            
            if ((found) && (gc.get(Calendar.WEEK_OF_YEAR)!=week))
            {
                gc.add(Calendar.DAY_OF_YEAR, -1);
                l[1] = ATechDate.getATDateTimeFromGC(gc, ATechDate.FORMAT_DATE_ONLY);
                break;
            }
            
            gc.add(Calendar.DAY_OF_YEAR, 1);
            
        }
        
        
        
        return l;
    }
    
    
    
    public boolean isWithinRange(int index, long adt)
    {
        int range_type = this.range_selector[index];
        if ((adt >= this.data_procesor.get(range_type).get("FROM")) && 
            (adt <= this.data_procesor.get(range_type).get("TO")))
            return true;
        else
            return false;
    }
    
    
    
}
