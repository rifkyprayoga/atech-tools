
using System;
using ATechTools.I18n;

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

namespace ATechTools.Util
{


public class ATechDate
{

    /**
     * The i18n_control.
     */
    public static I18nControlAbstract i18n_control = null;


    /**
     * The days_month.
     */
    public int[] days_month = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        

    // ********************************************************
    // ******      Constructors and Access methods        *****    
    // ********************************************************


    /**
     * The Constant FORMAT_DATE_ONLY.
     */
    public const int FORMAT_DATE_ONLY            = 1;
    
    /**
     * The Constant FORMAT_TIME_ONLY_MIN.
     */
    public const int FORMAT_TIME_ONLY_MIN        = 2;
    
    /**
     * The Constant FORMAT_TIME_ONLY_S.
     */
    public const int FORMAT_TIME_ONLY_S          = 3;
    
    /**
     * The Constant FORMAT_TIME_ONLY_MS.
     */
    public const int FORMAT_TIME_ONLY_MS         = 4;
    
    /**
     * The Constant FORMAT_DATE_AND_TIME_MIN.
     */
    public const int FORMAT_DATE_AND_TIME_MIN    = 5;
    
    /**
     * The Constant FORMAT_DATE_AND_TIME_S.
     */
    public const int FORMAT_DATE_AND_TIME_S      = 6;
    
    /**
     * The Constant FORMAT_DATE_AND_TIME_MS.
     */
    public const int FORMAT_DATE_AND_TIME_MS     = 7;


    public const int DATETIME_YEAR = 1;
    public const int DATETIME_MONTH = 2;
    public const int DATETIME_DAY_OF_MONTH = 3;
    public const int DATETIME_HOUR_OF_DAY = 4;
    public const int DATETIME_MINUTE = 5;
    public const int DATETIME_SECOND = 6;
    public const int DATETIME_MILISECOND = 7;



    /**
     * The desc.
     */
    public String[] desc = { "",
        "Date only",
        "Time only (min)",
        "Time only (s)",
        "Time only (ms)",
        "Date and Time (minute)",
        "Date and Time (second)",
        "Date and Time (mili second)"
    };


    /**
     * The day_of_month.
     */
    public int day_of_month;
    
    /**
     * The month.
     */
    public int month;
    
    /**
     * The year.
     */
    public int year;
    
    /**
     * The hour_of_day.
     */
    public int hour_of_day;
    
    /**
     * The minute.
     */
    public int minute;
    
    /**
     * The second.
     */
    public int second = 0;
    
    /**
     * The msecond.
     */
    public int msecond = 0;

    /**
     * The atech_datetime_type.
     */
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
     * Instantiates a new a tech date.
     * 
     * @param type the type
     */
    public ATechDate(int type)
    {
        this.atech_datetime_type = type;
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


    /**
     * Instantiates a new a tech date.
     * 
     * @param _day the _day
     * @param _month the _month
     * @param _year the _year
     * @param _hour the _hour
     * @param _minute the _minute
     * @param type the type
     */
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
    
    
    

    /**
     * Instantiates a new a tech date.
     * 
     * @param type the type
     * @param gc the gc
     */
    public ATechDate(int type, DateTime gc)
    {
        atech_datetime_type = type;
        
        switch(type)
        {
            case ATechDate.FORMAT_DATE_ONLY:
                {
                    this.day_of_month = gc.Day;
                    this.month = gc.Month;
                    this.year = gc.Year;
                } break;

            case ATechDate.FORMAT_DATE_AND_TIME_S:
                {
                    this.day_of_month = gc.Day;
                    this.month = gc.Month;
                    this.year = gc.Year;
                    this.hour_of_day = gc.Hour;
                    this.minute = gc.Minute;
                    this.second = gc.Second;
                } break;

            case ATechDate.FORMAT_DATE_AND_TIME_MS:
                {
                    this.day_of_month = gc.Day;
                    this.month = gc.Month;
                    this.year = gc.Year;
                    this.hour_of_day = gc.Hour;
                    this.minute = gc.Minute;
                    this.second = gc.Second;
                    this.msecond = gc.Millisecond;
                } break;

            default:
            case ATechDate.FORMAT_DATE_AND_TIME_MIN:
                {
                    this.day_of_month = gc.Day;
                    this.month = gc.Month;
                    this.year = gc.Year;

                    this.hour_of_day = gc.Hour;
                    this.minute = gc.Minute;

                } break;

        }

    }



    /**
     * Process.
     * 
     * @param type the type
     * @param date the date
     */
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
        else if (type == ATechDate.FORMAT_TIME_ONLY_MIN)
        {

            this.hour_of_day = (int)(date/100L);
            date -= this.hour_of_day*100L;
            
            this.minute = (int)date;
            
            checkAndCorrectValues();

        }
        else
        {
            Console.WriteLine("*****************************************************************");
            Console.WriteLine("Not possible to handle. Type not suppported yet: " + this.desc[type]);
            Console.WriteLine("*****************************************************************");
        }

    }

    
    /**
     * Gets the time string.
     * 
     * @param type the type
     * @param date the date
     * 
     * @return the time string
     */
    public static String getTimeString(int type, long date)
    {
        ATechDate dt = new ATechDate(type, date);
        return dt.getTimeString();
    }
    

    /**
     * Gets the time string.
     * 
     * @param type the type
     * @param gc the gc
     * 
     * @return the time string
     */
    public static String getTimeString(int type, DateTime gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getTimeString();
    }
    
    
    /**
     * Gets the date time string.
     * 
     * @param type the type
     * @param date the date
     * 
     * @return the date time string
     */
    public static String getDateTimeString(int type, long date)
    {
        ATechDate dt = new ATechDate(type, date);
        return dt.getDateTimeString();
    }


    /**
     * Gets the date time string.
     * 
     * @param type the type
     * @param gc the gc
     * 
     * @return the date time string
     */
    public static String getDateTimeString(int type, DateTime gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getDateTimeString();
    }


    /**
     * Gets the date string.
     * 
     * @param type the type
     * @param date the date
     * 
     * @return the date string
     */
    public static String getDateString(int type, long date)
    {
        ATechDate dt = new ATechDate(type, date);
        return dt.getDateString();
    }

    /**
     * Gets the date string.
     * 
     * @param type the type
     * @param gc the gc
     * 
     * @return the date string
     */
    public static String getDateString(int type, DateTime gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getDateString();
    }


    private void checkAndCorrectValues()
    {
        if (this.minute>59)
            this.minute=59;
        
        if (this.hour_of_day>23)
            this.minute=23;
        
        if (this.second>59)
            this.second=59;
    }
    

    /**
     * Gets the date string.
     * 
     * @return the date string
     */
    public String getDateString()
    {
        if (this.year==0)
            return getLeadingZero(this.day_of_month,2) + "/" + getLeadingZero(this.month,2);
        else
            return getLeadingZero(this.day_of_month,2) + "/" + getLeadingZero(this.month,2) + "/" + this.year;
    }


    /**
     * Gets the date filename string.
     * 
     * @return the date filename string
     */
    public String getDateFilenameString()
    {
        if (this.year==0)
            return getLeadingZero(this.day_of_month,2) + "_" + getLeadingZero(this.month,2);
        else
            return getLeadingZero(this.day_of_month,2) + "_" + getLeadingZero(this.month,2) + "_" + this.year;
    }
    
    
    
    /**
     * Gets the time filename string
     * 
     * @return time filename string
     */
    public String getTimeFilenameString()
    {
        if (this.second>0)
            return getLeadingZero(this.hour_of_day, 2) + "_" + getLeadingZero(this.minute, 2) + "_" + getLeadingZero(this.second, 2); 
        else
            return getLeadingZero(this.hour_of_day, 2) + "_" + getLeadingZero(this.minute, 2);

    }
    
    

    /**
     * Gets the time string.
     * 
     * @return the time string
     */
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

    
    /**
     * Gets the time.
     * 
     * @return the time
     */
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
    
    
    /**
     * Gets the date time string.
     * 
     * @return the date time string
     */
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

//    public static const int DATE_TIME_ATECH_DATETIME = 1;
//    public static const int DATE_TIME_ATECH_DATE = 2;
//    public static const int DATE_TIME_ATECH_TIME = 3;



    /**
     * Gets the aT date time from gc.
     * 
     * @param gc the gc
     * @param type the type
     * 
     * @return the aT date time from gc
     */
    public static long GetATDateTimeFromDT(DateTime gc, int type) // throws Exception
    {
    	long dt = 0L;
    
    	if (type==FORMAT_DATE_AND_TIME_MIN)
    	{
    	    dt += gc.Year *100000000L;
    	    dt += gc.Month *1000000L;
            dt += gc.Day * 10000L;
    	    dt += gc.Hour *100L;
    	    dt += gc.Minute;
    	} 
    	else if (type==FORMAT_DATE_ONLY)
    	{
            dt += gc.Year * 10000L;
            dt += gc.Month * 100L;
    	    dt += gc.Day;
    	}
    	else if (type==ATechDate.FORMAT_TIME_ONLY_MIN)
    	{
            dt += gc.Hour * 100L;
            dt += gc.Minute;
        }
        else
        {
            Console.WriteLine("getATDateTimeFromGC: Unallowed type");
            //throw new Exception("getATDateTimeFromGC: Unallowed type");
        }
    
    	return dt;
    }


    /**
     * Convert at date.
     * 
     * @param dt the dt
     * @param input_format the input_format
     * @param output_format the output_format
     * 
     * @return the long
     */
    public static long convertATDate(long dt, int input_format, int output_format)
    {
        ATechDate atd = new ATechDate(input_format, dt);
        return atd.getATDateTimeAsInt64();
    }
    
    

    /**
     * Gets the aT date time from parts.
     * 
     * @param _day the _day
     * @param _month the _month
     * @param _year the _year
     * @param _hour the _hour
     * @param _minute the _minute
     * @param type the type
     * 
     * @return the aT date time from parts
     */
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
            Console.WriteLine("getATDateTimeFromParts: Unallowed type");
        }
    
    	return dt;
    }


    
    /**
     * Gets the aT date time as long.
     * 
     * @return the aT date time as long
     */
    public long getATDateTimeAsInt64() //throws Exception
    {
        return this.getATDateTimeAsInt64(this.atech_datetime_type);
    }



    /**
     * Gets the aT date time as long.
     * 
     * @param output_format the output_format
     * 
     * @return the aT date time as long
     */
    public long getATDateTimeAsInt64(int output_format) //throws Exception
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
            Console.WriteLine("ERROR: getATDateTimeAsInt64: Unsupported type [" + output_format + "]");
        }

        return dt;

    }



    /**
     * Gets the date from at date.
     * 
     * @param data the data
     * 
     * @return the date from at date
     */
    public long getDateFromATDate(long data)
    {
        if (this.atech_datetime_type == FORMAT_DATE_AND_TIME_MIN)
        {
    	// 200701011222
    	int d2 = (int)(data/10000);
    
    	//long dd = data%10000;
    	//data -= dd;
    
    	//Console.WriteLine("D2: " +d2);
    
    	//Console.WriteLine(data);
    	return d2;
        }
        return -1;
    }



    /**
     * Gets the date time as time string.
     * 
     * @param date the date
     * 
     * @return the date time as time string
     */
    public String getDateTimeAsTimeString(long date)
    {
        return getDateTimeString(date, 3);
    }


    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)

    private const int DT_DATETIME = 1;
    private const int DT_DATE = 2;
    //private const static int DT_TIME = 3;



    /**
     * Gets the date time string.
     * 
     * @param dt the dt
     * @param ret_type the ret_type
     * 
     * @return the date time string
     */
    public String getDateTimeString(long dt, int ret_type)
    {

        //Console.WriteLine("DT process: " + dt);
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
    public String getGCObjectFromDateTimeInt64(long dt)
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

        /**
     * Gets the gregorian calendar.
     * 
     * @param format the format
     * @param dt the dt
     * 
     * @return the gregorian calendar
     */
    public static DateTime GetDateTime(int format, long dt)
    {
        ATechDate atd = new ATechDate(format, dt);
        return atd.GetDateTime();
    }

    /**
     * Gets the gregorian calendar.
     * 
     * @return the gregorian calendar
     */
    public DateTime GetDateTime()
    {
        return new DateTime(this.year, this.month, this.day_of_month, this.hour_of_day, this.minute, this.second, this.msecond);


//        gc.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());
    }


    /**
     * Sets the Gregorian Calendar
     * 
     * @param gc 
     */
    public void SetDateTime(DateTime gc)
    {
        this.year = gc.Year;
        this.month = gc.Month;
        this.day_of_month = gc.Day;
        this.hour_of_day = gc.Hour;
        this.minute = gc.Minute;
        this.second = gc.Second;
        this.msecond = gc.Millisecond;
    }


    /**
     * Adds the.
     * 
     * @param date_field the date_field
     * @param value the value
     */
    public void add(int date_field, int value)
    {
        DateTime gc = GetDateTime();

        switch (date_field)
        { 

            case DATETIME_YEAR:
                gc = gc.AddYears(value);
                break;

            case DATETIME_MONTH:
                gc = gc.AddMonths(value);
                break;

            case DATETIME_DAY_OF_MONTH:
                gc = gc.AddDays(value);
                break;

            case DATETIME_HOUR_OF_DAY:
                gc = gc.AddHours(value);
                break;

            case DATETIME_MINUTE:
                gc = gc.AddMinutes(value);
                break;

            case DATETIME_SECOND:
                gc = gc.AddSeconds(value);
                break;

            case DATETIME_MILISECOND:
                gc = gc.AddMilliseconds(value);
                break;
            
        }

        SetDateTime(gc);
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


    /**
     * Gets the leading zero.
     * 
     * @param number the number
     * @param places the places
     * 
     * @return the leading zero
     */
    public String getLeadingZero(int number, int places)
    {

        String nn = ""+number;

        while (nn.Length<places)
        {
            nn = "0"+nn;
        }

        return nn;

    }





    /**
     * Checks if is leap year.
     * 
     * @param _year the _year
     * 
     * @return true, if is leap year
     */
    public bool isLeapYear(int _year)
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


    /** 
     * To String
     */
    public String toString()
    {
        return this.getDateString() + " " + this.getTimeString();
    }


}

}