package com.atech.mobile.utils; //ggc.meter.util;

//import ggc.util.DataAccess;

import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneUtil
{
	
	//DataAccess m_da = DataAccess.getInstance();
	TimeZone tzi;
	
	int winter_time_change = 0;
	int summer_time_change = 0;

	public static TimeZoneUtil s_timezoneutil;
	
	TimeZone empty_tzi;
	
	
	public static TimeZoneUtil getInstance()
	{
		if (TimeZoneUtil.s_timezoneutil==null)
			TimeZoneUtil.s_timezoneutil = new TimeZoneUtil();

		return TimeZoneUtil.s_timezoneutil;
	}
	
	
	private TimeZoneUtil()
	{

        TimeZone tz = TimeZone.getDefault();
        //this.empty_tzi = 
        this.empty_tzi = new SimpleTimeZone(tz.getRawOffset(),
                    tz.getID(), 0, 0, 0, 0, 0, 0, 0, 0);		

        this.tzi = tz;

	}
	
	public void setTimeZone(String time_zone_id)
	{
		tzi = TimeZone.getTimeZone(time_zone_id);
		
		
		//this.empty_tzi = 
		this.empty_tzi = new SimpleTimeZone(tzi.getRawOffset(),
					time_zone_id,
	                0,
	                0,
	                0,
	                0,
	                0,
	                0,
	                0,
	                0);		
		
	}
	
	public void setWinterTimeChange(int val)
	{
		this.winter_time_change = val;
	}
	

	public TimeZone getEmptyTimeZone()
	{
		return this.empty_tzi;
	}
	
	
	public void setSummerTimeChange(int val)
	{
		this.summer_time_change = val;
	}
	
	
	public void setValues(String timeZoneId, int winter_time_change, int summer_time_change)
	{
		setTimeZone(timeZoneId);
		setWinterTimeChange(winter_time_change);
		setSummerTimeChange(summer_time_change);
	}

	
	
	public boolean IsWinterTime(GregorianCalendar gc)
	{
		return (!tzi.inDaylightTime(gc.getTime()));
	}
	
	public boolean IsSummerTime(GregorianCalendar gc)
	{
		return (tzi.inDaylightTime(gc.getTime()));
	}
	
	
	
	public ATechDate GetCorrectedDateTime(ATechDate atdate)
	{
		GregorianCalendar gc = atdate.getGregorianCalendar();
		
		if (this.IsWinterTime(gc))
		{
			// winter time
			if (this.winter_time_change==0)
				return atdate;
			else
			{
				atdate.add(GregorianCalendar.HOUR_OF_DAY, this.winter_time_change);
				return atdate;
			}
			
		}
		else
		{
			// summer time
			if (this.summer_time_change==0)
				return atdate;
			else
			{
				atdate.add(GregorianCalendar.HOUR_OF_DAY, this.summer_time_change);
				return atdate;
			}
			
		}
		
		//return null;
	}

    // FIX
	
	/*
    public Vector<String> getTimeZoneDescs()
    {
        Vector<String> vec = new Vector<String>();

        for(Enumeration<String> en = m_da.timeZones.keys(); en.hasMoreElements(); )
        {
        	String key = en.nextElement();
            System.out.println("Key: " + key + " Value: " + m_da.timeZones.get(key));
            vec.add(key);
        }

        Collections.sort(vec, new TimeZoneComparator());

        return vec;
    }
	
	
	
    public String getTimeZoneKeyFromValue(String value)
    {
        for(Enumeration<String> en = m_da.timeZones.keys(); en.hasMoreElements(); )
        {
            String key = en.nextElement();

            System.out.println(m_da.timeZones.get(key) + " = " + value);

            if (m_da.timeZones.get(key).contains(value))
            {
                return key;
            }
        }

        return "(GMT) Greenwich Mean Time : Dublin, Edinburgh, Lisbon, London";
    }
*/

    @SuppressWarnings("unused")
    private class TimeZoneComparator implements Comparator<String>
    {
     /**
       * Compare two TimeZones. 
       *
       *  GMT- (12->1) < GMT < GMT+ (1->12)
       *
       * @return +1 if less, 0 if same, -1 if greater
       */
       public final int compare ( String pFirst, String pSecond )
       {

           //System.out.println(pFirst + " " + pSecond);

           if (areSameType(pFirst, pSecond))
           {
               return ((pFirst.compareTo(pSecond)) * typeChanger(pFirst, pSecond));
           }
           else
           {
               if ((pFirst.startsWith("(GMT-"))) // && (second.contains("(GMT)")))
               {
                   return -1;
               }
               else if ((pFirst.startsWith("(GMT)"))) // && (second.contains("(GMT)")))
               {
                   if (pSecond.startsWith("(GMT-"))
                       return 1;
                   else
                       return -1;
               }
               else
               {
                   return 1;
               }

           }
       } // end compare

       public int typeChanger(String first, String second)
       {
           if ((first.startsWith("(GMT-")) && (second.startsWith("(GMT-")))
               return -1;
           else
               return 1;
       }
       

       public boolean areSameType(String first, String second)
       {
           if (((first.startsWith("(GMT+")) && (second.startsWith("(GMT+"))) ||
               ((first.startsWith("(GMT)")) && (second.startsWith("(GMT)"))) ||
               ((first.startsWith("(GMT-")) && (second.startsWith("(GMT-"))))
           {
               return true;
           }
           else
               return false;

       }


    }



}
