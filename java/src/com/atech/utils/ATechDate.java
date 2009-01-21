
package com.atech.utils;

import java.util.GregorianCalendar;

import com.atech.i18n.I18nControlAbstract;

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

public class ATechDate
{

    public static I18nControlAbstract i18n_control = null;


    public int[] days_month = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        

    // ********************************************************
    // ******      Constructors and Access methods        *****    
    // ********************************************************


    public final static int FORMAT_DATE_ONLY            = 1;
    public final static int FORMAT_TIME_ONLY_MIN        = 2;
    public final static int FORMAT_TIME_ONLY_S          = 3;
    public final static int FORMAT_TIME_ONLY_MS         = 4;
    public final static int FORMAT_DATE_AND_TIME_MIN    = 5;
    public final static int FORMAT_DATE_AND_TIME_S      = 6;
    public final static int FORMAT_DATE_AND_TIME_MS     = 7;


    public String[] desc = {
        "Date only",
        "Date and Time (minute)",
        "Date and Time (second)",
        "Date and Time (mili second)"
    };


    public int day_of_month;
    public int month;
    public int year;
    public int hour_of_day;
    public int minute;
    public int second = 0;
    public int msecond = 0;

    public int atech_datetime_type;

    //public int m_type;

    /**
     * ATechDate (long)
     * 
     * Default constructor, with one parameter, where type is DATE_AND_TIME.
     * @param entry 
     * 
     */
    public ATechDate(long entry)
    {
        process(ATechDate.FORMAT_DATE_AND_TIME_MIN, entry);
    }

    /**
     * ATechDate (long)
     * 
     * Default constructor, with one parameter, where type is DATE_AND_TIME.
     * @param type 
     * @param entry 
     * 
     */
    public ATechDate(int type, long entry)
    {
        process(type, entry);
    }


    public ATechDate(int _day, int _month, int _year, int _hour, int _minute, int type)
    {
        this.day_of_month = _day;
        this.month = _month;
        this.year = _year;
        this.hour_of_day = _hour;
        this.minute = _minute;
        this.second = 0; 
        this.msecond = 0; 
        
        atech_datetime_type = type;
    }
    
    
    

    public ATechDate(int type, GregorianCalendar gc)
    {
        atech_datetime_type = type;
        
        switch(type)
        {
            case ATechDate.FORMAT_DATE_ONLY:
                {
                    this.day_of_month = gc.get(GregorianCalendar.DAY_OF_MONTH);
                    this.month = gc.get(GregorianCalendar.MONTH) +1;
                    this.year = gc.get(GregorianCalendar.YEAR);
                } break;

            case ATechDate.FORMAT_DATE_AND_TIME_S:
                {
                    this.day_of_month = gc.get(GregorianCalendar.DAY_OF_MONTH);
                    this.month = gc.get(GregorianCalendar.MONTH) +1;
                    this.year = gc.get(GregorianCalendar.YEAR);
                    this.hour_of_day = gc.get(GregorianCalendar.HOUR_OF_DAY);
                    this.minute = gc.get(GregorianCalendar.MINUTE);
                    this.second = gc.get(GregorianCalendar.SECOND);
                } break;

            case ATechDate.FORMAT_DATE_AND_TIME_MS:
                {
                    this.day_of_month = gc.get(GregorianCalendar.DAY_OF_MONTH);
                    this.month = gc.get(GregorianCalendar.MONTH) +1;
                    this.year = gc.get(GregorianCalendar.YEAR);
                    this.hour_of_day = gc.get(GregorianCalendar.HOUR_OF_DAY);
                    this.minute = gc.get(GregorianCalendar.MINUTE);
                    this.second = gc.get(GregorianCalendar.SECOND);
                    this.msecond = gc.get(GregorianCalendar.MILLISECOND);
                } break;

            default:
            case ATechDate.FORMAT_DATE_AND_TIME_MIN:
                {
                    this.day_of_month = gc.get(GregorianCalendar.DAY_OF_MONTH);
                    this.month = gc.get(GregorianCalendar.MONTH) +1;
                    this.year = gc.get(GregorianCalendar.YEAR);

                    this.hour_of_day = gc.get(GregorianCalendar.HOUR_OF_DAY);
                    this.minute = gc.get(GregorianCalendar.MINUTE);

                } break;

        }

    }



    public void process(int type, long date) 
    {
        atech_datetime_type = type;

        if (type == ATechDate.FORMAT_DATE_ONLY)
        {
            // 20051012

            this.year = (int)date/10000;
            date -= this.year*10000;

            this.month = (int)date/100;
            date -= this.month*100;

            this.day_of_month = (int)date;
        }
        else if (type == ATechDate.FORMAT_DATE_AND_TIME_MIN)
        {

            this.year = (int)(date/100000000L);
            date -= this.year*100000000L;

            this.month = (int)(date/1000000L);
            date -= this.month*1000000L;

            this.day_of_month = (int)(date/10000L);
            date -= this.day_of_month*10000L;

            this.hour_of_day = (int)(date/100L);
            date -= this.hour_of_day*100L;

            this.minute = (int)date;

        }
        else if (type == ATechDate.FORMAT_DATE_AND_TIME_S)
        {

            this.year = (int)(date/10000000000L);
            date -= this.year*10000000000L;

            this.month = (int)(date/100000000L);
            date -= this.month*100000000L;

            this.day_of_month = (int)(date/1000000L);
            date -= this.day_of_month*1000000L;

            this.hour_of_day = (int)(date/10000L);
            date -= this.hour_of_day*10000L;

            this.minute = (int)(date/100L);
            date -= this.minute*100L;
            
            this.second = (int)date;

        }
        else
        {
            System.out.println("*****************************************************************");
            System.out.println("Not possible to handle. Type not suppported yet: " + this.desc[type]);
            System.out.println("*****************************************************************");
        }

    }

    
    public static String getTimeString(int type, long date)
    {
        ATechDate dt = new ATechDate(type, date);
        return dt.getTimeString();
    }
    

    public static String getTimeString(int type, GregorianCalendar gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getTimeString();
    }
    
    
    public static String getDateTimeString(int type, long date)
    {
        ATechDate dt = new ATechDate(type, date);
        return dt.getDateTimeString();
    }


    public static String getDateTimeString(int type, GregorianCalendar gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getDateTimeString();
    }


    public static String getDateString(int type, long date)
    {
        ATechDate dt = new ATechDate(type, date);
        return dt.getDateString();
    }

    public static String getDateString(int type, GregorianCalendar gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getDateString();
    }



    

    public String getDateString()
    {
        if (this.year==0)
            return getLeadingZero(this.day_of_month,2) + "/" + getLeadingZero(this.month,2);
        else
            return getLeadingZero(this.day_of_month,2) + "/" + getLeadingZero(this.month,2) + "/" + this.year;
    }



    public String getTimeString()
    {
        if ((this.atech_datetime_type==ATechDate.FORMAT_DATE_AND_TIME_MIN) ||
            (this.atech_datetime_type==ATechDate.FORMAT_TIME_ONLY_MIN))
        {
            return getLeadingZero(this.hour_of_day,2) + ":" + getLeadingZero(this.minute,2);
        }
        else if ((this.atech_datetime_type==ATechDate.FORMAT_DATE_AND_TIME_S) ||
                 (this.atech_datetime_type==ATechDate.FORMAT_TIME_ONLY_S))
        {
            return getLeadingZero(this.hour_of_day,2) + ":" + getLeadingZero(this.minute,2) + ":" + getLeadingZero(this.second,2);
        }
        else
        {
            return "? " + getLeadingZero(this.hour_of_day,2) + ":" + getLeadingZero(this.minute,2);
             
        }
    }

    
    public int getTime()
    {
        if ((this.atech_datetime_type==ATechDate.FORMAT_DATE_AND_TIME_MIN) ||
            (this.atech_datetime_type==ATechDate.FORMAT_TIME_ONLY_MIN))
        {
         //   return getLeadingZero(this.hour_of_day,2) + ":" + getLeadingZero(this.minute,2);
            return this.hour_of_day *100 + this.minute;
        }
        else if ((this.atech_datetime_type==ATechDate.FORMAT_DATE_AND_TIME_S) ||
                 (this.atech_datetime_type==ATechDate.FORMAT_TIME_ONLY_S))
        {
            //return getLeadingZero(this.hour_of_day,2) + ":" + getLeadingZero(this.minute,2) + ":" + getLeadingZero(this.second,2);
            return -1;
        }
        else
        {
            //return "? " + getLeadingZero(this.hour_of_day,2) + ":" + getLeadingZero(this.minute,2);
             return -1;
        }
    }
    
    
    public String getDateTimeString()
    {
        return getDateString() + " " + getTimeString();
    }



    /*
    public String getDateTimeString(long date)
    {
        return getDateTimeString(date, 1);
    }


    public String getDateTimeAsDateString(long date)
    {
        return getDateTimeString(date, 2);
    }
*/

//    public static final int DATE_TIME_ATECH_DATETIME = 1;
//    public static final int DATE_TIME_ATECH_DATE = 2;
//    public static final int DATE_TIME_ATECH_TIME = 3;



    public static long getATDateTimeFromGC(GregorianCalendar gc, int type) // throws Exception
    {
    	long dt = 0L;
    
    	if (type==FORMAT_DATE_AND_TIME_MIN)
    	{
    	    dt += gc.get(GregorianCalendar.YEAR) *100000000L;
    	    dt += (gc.get(GregorianCalendar.MONTH)+1)*1000000L;
    	    dt += gc.get(GregorianCalendar.DAY_OF_MONTH) *10000L;
    	    dt += gc.get(GregorianCalendar.HOUR_OF_DAY) *100L;
    	    dt += gc.get(GregorianCalendar.MINUTE);
    	} 
    	else if (type==FORMAT_DATE_ONLY)
    	{
    	    dt += gc.get(GregorianCalendar.YEAR) *10000L;
    	    dt += (gc.get(GregorianCalendar.MONTH)+1)*100L;
    	    dt += gc.get(GregorianCalendar.DAY_OF_MONTH);
    	}
    	else if (type==ATechDate.FORMAT_TIME_ONLY_MIN)
    	{
    	    dt += gc.get(GregorianCalendar.HOUR_OF_DAY) *100L;
    	    dt += gc.get(GregorianCalendar.MINUTE);
    	}
        else
        {
            System.out.println("getATDateTimeFromGC: Unallowed type");
            //throw new Exception("getATDateTimeFromGC: Unallowed type");
        }
    
    	return dt;
    }


    public static long convertATDate(long dt, int input_format, int output_format)
    {
        ATechDate atd = new ATechDate(input_format, dt);
        return atd.getATDateTimeAsLong();
    }
    
    

    public long getATDateTimeFromParts(int _day, int _month, int _year, int _hour, int _minute, int type)  //throws Exception
    {
    	long dt = 0L;
    
    	if (type==FORMAT_DATE_AND_TIME_MIN)
    	{
    	    dt += _year *100000000L;
    	    dt += _month *1000000L;
    	    dt += _day *10000L;
    	    dt += _hour *100L;
    	    dt += _minute;
    	} 
    	else if (type==FORMAT_DATE_ONLY)
    	{
    	    dt += _year *10000L;
    	    dt += _month *100L;
    	    dt += _day;
    	}
    	else if (type==ATechDate.FORMAT_TIME_ONLY_MIN)
    	{
    	    dt += _hour *100L;
    	    dt += _minute;
    	}
        else
        {
            System.out.println("getATDateTimeFromParts: Unallowed type");
        }
    
    	return dt;
    }


    
    public long getATDateTimeAsLong() //throws Exception
    {
        return this.getATDateTimeAsLong(this.atech_datetime_type);
    }



    public long getATDateTimeAsLong(int output_format) //throws Exception
    {
        long dt = 0L;

        if (output_format==FORMAT_DATE_AND_TIME_MIN)
        {
            dt += year *100000000L;
            dt += month *1000000L;
            dt += this.day_of_month *10000L;
            dt += this.hour_of_day *100L;
            dt += minute;
        } 
        else if (output_format==FORMAT_DATE_AND_TIME_S)
        {
            dt += year *10000000000L;
            dt += month *100000000L;
            dt += this.day_of_month *1000000L;
            dt += this.hour_of_day *10000L;
            dt += minute*100L;
            dt += this.second;
        } 
        else if (output_format==FORMAT_DATE_ONLY)
        {
            dt += year *10000L;
            dt += month *100L;
            dt += this.day_of_month;
        }
        else if (output_format==ATechDate.FORMAT_TIME_ONLY_MIN)
        {
            dt += this.hour_of_day *100L;
            dt += minute;
        }
        else
        {
            System.out.println("ERROR: getATDateTimeAsLong: Unsupported type [" + output_format + "]");
        }

        return dt;

    }



    public long getDateFromATDate(long data)
    {
        if (this.atech_datetime_type == FORMAT_DATE_AND_TIME_MIN)
        {
    	// 200701011222
    	int d2 = (int)(data/10000);
    
    	//long dd = data%10000;
    	//data -= dd;
    
    	//System.out.println("D2: " +d2);
    
    	//System.out.println(data);
    	return d2;
        }
        return -1;
    }



    public String getDateTimeAsTimeString(long date)
    {
        return getDateTimeString(date, 3);
    }


    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)

    private final static int DT_DATETIME = 1;
    private final static int DT_DATE = 2;
    //private final static int DT_TIME = 3;



    public String getDateTimeString(long dt, int ret_type)
    {

        //System.out.println("DT process: " + dt);
        /*
        int y = (int)(dt/10000000L);
        dt -= y*10000000L;

        int m = (int)(dt/1000000L);
        dt -= m*1000000L;

        int d = (int)(dt/10000L);
        dt -= d*10000L;

        int h = (int)(dt/100L);
        dt -= h*100L;

        int min = (int)dt;
*/
        

// 200612051850
        int y = (int)(dt/100000000L);
        dt -= y*100000000L;

        int m = (int)(dt/1000000L);
        dt -= m*1000000L;

        int d = (int)(dt/10000L);
        dt -= d*10000L;

        int h = (int)(dt/100L);
        dt -= h*100L;

        int min = (int)dt;

        
        if (ret_type==DT_DATETIME)
        {
            return getLeadingZero(d,2) + "/" + getLeadingZero(m,2) + "/" + y + "  " + getLeadingZero(h,2) + ":" + getLeadingZero(min,2);
        }
        else if (ret_type==DT_DATE)
        {
            return getLeadingZero(d,2) + "/" + getLeadingZero(m,2) + "/" + y;
        }
        else
            return getLeadingZero(h,2) + ":" + getLeadingZero(min,2);

    }


/*
    public String getGCObjectFromDateTimeLong(long dt)
    {

    	int y = (int)(dt/100000000L);
    	dt -= y*100000000L;
    
    	int m = (int)(dt/1000000L);
    	dt -= m*1000000L;
    
    	int d = (int)(dt/10000L);
    	dt -= d*10000L;
    
    	int h = (int)(dt/100L);
    	dt -= h*100L;
    
    	int min = (int)dt;
    
    	GregorianCalendar gc1 = new GregorianCalendar();
    	//gc1.set(GregorianCalendar.
    
    	return null;

    }
*/

    public static GregorianCalendar getGregorianCalendar(int format, long dt)
    {
        ATechDate atd = new ATechDate(format, dt);
        return atd.getGregorianCalendar();
    }

    public GregorianCalendar getGregorianCalendar()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());
        gc.set(GregorianCalendar.YEAR, this.year);
        gc.set(GregorianCalendar.MONTH, this.month-1);
        gc.set(GregorianCalendar.DAY_OF_MONTH, this.day_of_month);
        gc.set(GregorianCalendar.HOUR_OF_DAY, this.hour_of_day);
        gc.set(GregorianCalendar.MINUTE, this.minute);
        gc.set(GregorianCalendar.SECOND, this.second);
        gc.set(GregorianCalendar.MILLISECOND, this.msecond);

        return gc;
    }


    public void setGregorianCalendar(GregorianCalendar gc)
    {
        this.year = gc.get(GregorianCalendar.YEAR);
        this.month = gc.get(GregorianCalendar.MONTH)+1;
        this.day_of_month = gc.get(GregorianCalendar.DAY_OF_MONTH);
        this.hour_of_day = gc.get(GregorianCalendar.HOUR_OF_DAY);
        this.minute = gc.get(GregorianCalendar.MINUTE);
        this.second = gc.get(GregorianCalendar.SECOND);
        this.msecond = gc.get(GregorianCalendar.MILLISECOND);
    }


    public void add(int date_field, int value)
    {
        GregorianCalendar gc = getGregorianCalendar();
        gc.add(date_field, value);

        setGregorianCalendar(gc);
    }


    /*
    public void addHour(int hours)
    {
        GregorianCalendar gc = getGregorianCalendar();

//        gc.add(

/*        

        if (hours==0)
        {
        }
        else if (hours <0)
        {
            // negative
            this.hour_of_day += hours;

            if (this.hour_of_day<0)
            {
                this.hour_of_day = 23 + this.hour_of_day;

                this.day_of_month--;

                if (this.day_of_month<1)
                {
                    if (this.month==1)
                    {
                        this.month=12;
                        this.year--;
                    }
                    else
                    {
                        this.month--;
                    }

                    if (month==2)
                    {
                        if (isLeapYear(this.year))
                            this.day_of_month = 29;
                        else
                            this.day_of_month = 28;
                    }
                    else
                        this.day_of_month = this.days_month[month];

                }
            }
        }
        else // add +
        {
            this.hour_of_day += hours;

            if (this.hour_of_day> 23)
            {
                int change = this.hour_of_day - 23;

                this.hour_of_day = change;

                this.day_of_month++;

                if (this.month==2)
                {
                    if (isLeapYear(year))
                    {
                        if (this.day_of_month>29)
                        {
                        }

                    }

                }
                else
                {
                }


                if (this.days_month[this.month] < this.da)
                {
                }

                
            }
        }
*/
   // }




/*
    public String getDateTimeString(int date, int time)
    {

        return getDateString(date)+" " + getTimeString(time);

    }
*/


    public String getLeadingZero(int number, int places)
    {

        String nn = ""+number;

        while (nn.length()<places)
        {
            nn = "0"+nn;
        }

        return nn;

    }





    public boolean isLeapYear(int _year)
    {
        if (_year%4!=0)
        {
            return false;
        }
        else
        {
            if ((_year%100==0) && (_year%400!=0))
                return false;
            else
                return true;
        }

        //A leap year is any year divisible by four except 
        //    years both divisible by 100 and not divisible by 400. 


    }




}

