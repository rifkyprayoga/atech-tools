package com.atech.utils.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.I18nControlAbstract;

/**
 * This file is part of ATech Tools library.
 * ATechDate - ATechDate format parser/util (yyyyMMddhhss for date and time)
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * For additional information about this project please visit our project site
 * on
 * http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 *
 * @author Andy
 */

public class ATechDate
{
    private static Log log = LogFactory.getLog(ATechDate.class);

    /**
     * The i18n_control.
     */
    public static I18nControlAbstract i18n_control = null;

    /**
     * The days_month.
     */
    public int[] days_month = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    // ********************************************************
    // ****** Constructors and Access methods *****
    // ********************************************************

    /**
     * The Constant FORMAT_DATE_ONLY.
     */
    public final static int FORMAT_DATE_ONLY = 1;

    /**
     * The Constant FORMAT_TIME_ONLY_MIN.
     */
    public final static int FORMAT_TIME_ONLY_MIN = 2;

    /**
     * The Constant FORMAT_TIME_ONLY_S.
     */
    public final static int FORMAT_TIME_ONLY_S = 3;

    /**
     * The Constant FORMAT_TIME_ONLY_MS.
     */
    public final static int FORMAT_TIME_ONLY_MS = 4;

    /**
     * The Constant FORMAT_DATE_AND_TIME_MIN.
     */
    public final static int FORMAT_DATE_AND_TIME_MIN = 5;

    /**
     * The Constant FORMAT_DATE_AND_TIME_S.
     */
    public final static int FORMAT_DATE_AND_TIME_S = 6;

    /**
     * The Constant FORMAT_DATE_AND_TIME_MS.
     */
    public final static int FORMAT_DATE_AND_TIME_MS = 7;

    /**
     * The desc.
     */
    public String[] desc = { "", "Date only", "Time only (min)", "Time only (s)", "Time only (ms)",
                            "Date and Time (minute)", "Date and Time (second)", "Date and Time (mili second)" };

    /**
     * Day of month.
     */
    public int dayOfMonth;

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
    public int hourOfDay;

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
    public int atechDatetimeType;

    /**
     * ATechDate (long)
     * Default constructor, with one parameter, where type is DATE_AND_TIME.
     * 
     * @param entry
     */
    public ATechDate(long entry)
    {
        process(ATechDate.FORMAT_DATE_AND_TIME_MIN, entry);
    }

    /**
     * Instantiates a new a tech date.
     *
     * @param type
     *            the type
     */
    public ATechDate(int type)
    {
        this.atechDatetimeType = type;
    }

    /**
     * ATechDate (long)
     * Default constructor, with one parameter, where type is DATE_AND_TIME.
     * 
     * @param type
     * @param entry
     */
    public ATechDate(int type, long entry)
    {
        process(type, entry);
    }

    /**
     * Constructor
     *
     * @param _day
     * @param _month
     * @param _year
     */
    public ATechDate(int _day, int _month, int _year)
    {
        this(_day, _month, _year, 0, 0, ATechDate.FORMAT_DATE_ONLY);
    }

    /**
     * Instantiates a new a tech date.
     *
     * @param _day
     *            the _day
     * @param _month
     *            the _month
     * @param _year
     *            the _year
     * @param _hour
     *            the _hour
     * @param _minute
     *            the _minute
     * @param type
     *            the type
     */
    public ATechDate(int _day, int _month, int _year, int _hour, int _minute, int type)
    {
        this.dayOfMonth = _day;
        this.month = _month;
        this.year = _year;
        this.hourOfDay = _hour;
        this.minute = _minute;
        this.second = 0;
        this.msecond = 0;

        atechDatetimeType = type;
    }

    /**
     * Instantiates a new a tech date.
     *
     * @param type
     *            the type
     * @param gc
     *            the gc
     */
    public ATechDate(int type, Calendar gc) // GregorianCalendar
    {
        atechDatetimeType = type;

        switch (type)
        {
            case ATechDate.FORMAT_DATE_ONLY:
                {
                    this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
                    this.month = gc.get(Calendar.MONTH) + 1;
                    this.year = gc.get(Calendar.YEAR);
                }
                break;

            case ATechDate.FORMAT_DATE_AND_TIME_S:
                {
                    this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
                    this.month = gc.get(Calendar.MONTH) + 1;
                    this.year = gc.get(Calendar.YEAR);
                    this.hourOfDay = gc.get(Calendar.HOUR_OF_DAY);
                    this.minute = gc.get(Calendar.MINUTE);
                    this.second = gc.get(Calendar.SECOND);
                }
                break;

            case ATechDate.FORMAT_DATE_AND_TIME_MS:
                {
                    this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
                    this.month = gc.get(Calendar.MONTH) + 1;
                    this.year = gc.get(Calendar.YEAR);
                    this.hourOfDay = gc.get(Calendar.HOUR_OF_DAY);
                    this.minute = gc.get(Calendar.MINUTE);
                    this.second = gc.get(Calendar.SECOND);
                    this.msecond = gc.get(Calendar.MILLISECOND);
                }
                break;

            default:
            case ATechDate.FORMAT_DATE_AND_TIME_MIN:
                {
                    this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
                    this.month = gc.get(Calendar.MONTH) + 1;
                    this.year = gc.get(Calendar.YEAR);

                    this.hourOfDay = gc.get(Calendar.HOUR_OF_DAY);
                    this.minute = gc.get(Calendar.MINUTE);

                }
                break;

        }

    }

    /**
     * Process.
     *
     * @param type
     *            the type
     * @param date
     *            the date
     */
    public void process(int type, long date)
    {
        atechDatetimeType = type;

        if (type == ATechDate.FORMAT_DATE_ONLY)
        {
            // 20051012

            this.year = (int) date / 10000;
            date -= this.year * 10000;

            this.month = (int) date / 100;
            date -= this.month * 100;

            this.dayOfMonth = (int) date;
        }
        else if (type == ATechDate.FORMAT_DATE_AND_TIME_MIN)
        {

            this.year = (int) (date / 100000000L);
            date -= this.year * 100000000L;

            this.month = (int) (date / 1000000L);
            date -= this.month * 1000000L;

            this.dayOfMonth = (int) (date / 10000L);
            date -= this.dayOfMonth * 10000L;

            this.hourOfDay = (int) (date / 100L);
            date -= this.hourOfDay * 100L;

            this.minute = (int) date;

        }
        else if (type == ATechDate.FORMAT_DATE_AND_TIME_S)
        {

            this.year = (int) (date / 10000000000L);
            date -= this.year * 10000000000L;

            this.month = (int) (date / 100000000L);
            date -= this.month * 100000000L;

            this.dayOfMonth = (int) (date / 1000000L);
            date -= this.dayOfMonth * 1000000L;

            this.hourOfDay = (int) (date / 10000L);
            date -= this.hourOfDay * 10000L;

            this.minute = (int) (date / 100L);
            date -= this.minute * 100L;

            this.second = (int) date;

        }
        else if (type == ATechDate.FORMAT_TIME_ONLY_S)
        {

            this.hourOfDay = (int) (date / 10000L);
            date -= this.hourOfDay * 10000L;

            this.minute = (int) (date / 100L);
            date -= this.minute * 100L;

            this.second = (int) date;

            checkAndCorrectValues();

        }
        else if (type == ATechDate.FORMAT_TIME_ONLY_MIN)
        {

            this.hourOfDay = (int) (date / 100L);
            date -= this.hourOfDay * 100L;

            this.minute = (int) date;

            checkAndCorrectValues();

        }
        else
        {
            System.out.println("*****************************************************************");
            System.out.println("Not possible to handle. Type not suppported yet: " + this.desc[type]);
            System.out.println("*****************************************************************");
        }

    }

    /**
     * Gets the time string.
     *
     * @param type
     *            the type
     * @param date
     *            the date
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
     * @param type
     *            the type
     * @param gc
     *            the gc
     * @return the time string
     */
    public static String getTimeString(int type, GregorianCalendar gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getTimeString();
    }

    /**
     * Gets the date time string.
     *
     * @param type
     *            the type
     * @param date
     *            the date
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
     * @param type
     *            the type
     * @param gc
     *            the gc
     * @return the date time string
     */
    public static String getDateTimeString(int type, GregorianCalendar gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getDateTimeString();
    }

    /**
     * Gets the date string.
     *
     * @param type
     *            the type
     * @param date
     *            the date
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
     * @param type
     *            the type
     * @param gc
     *            the gc
     * @return the date string
     */
    public static String getDateString(int type, GregorianCalendar gc)
    {
        ATechDate dt = new ATechDate(type, gc);
        return dt.getDateString();
    }

    private void checkAndCorrectValues()
    {
        if (this.minute > 59)
        {
            this.minute = 59;
        }

        if (this.hourOfDay > 23)
        {
            this.minute = 23;
        }

        if (this.second > 59)
        {
            this.second = 59;
        }
    }

    /**
     * Gets the date string.
     *
     * @return the date string
     */
    public String getDateString()
    {
        if (this.year == 0)
            return getLeadingZero(this.dayOfMonth, 2) + "/" + getLeadingZero(this.month, 2);
        else
            return getLeadingZero(this.dayOfMonth, 2) + "/" + getLeadingZero(this.month, 2) + "/" + this.year;
    }

    /**
     * Gets the date filename string.
     *
     * @return the date filename string
     */
    public String getDateFilenameString()
    {
        if (this.year == 0)
            return getLeadingZero(this.dayOfMonth, 2) + "_" + getLeadingZero(this.month, 2);
        else
            return getLeadingZero(this.dayOfMonth, 2) + "_" + getLeadingZero(this.month, 2) + "_" + this.year;
    }

    /**
     * Gets the time filename string
     *
     * @return time filename string
     */
    public String getTimeFilenameString()
    {
        if (this.second > 0)
            return getLeadingZero(this.hourOfDay, 2) + "_" + getLeadingZero(this.minute, 2) + "_"
                    + getLeadingZero(this.second, 2);
        else
            return getLeadingZero(this.hourOfDay, 2) + "_" + getLeadingZero(this.minute, 2);

    }

    /**
     * Gets the time string.
     *
     * @return the time string
     */
    public String getTimeString()
    {
        if (this.atechDatetimeType == ATechDate.FORMAT_DATE_AND_TIME_MIN
                || this.atechDatetimeType == ATechDate.FORMAT_TIME_ONLY_MIN)
            return getLeadingZero(this.hourOfDay, 2) + ":" + getLeadingZero(this.minute, 2);
        else if (this.atechDatetimeType == ATechDate.FORMAT_DATE_AND_TIME_S
                || this.atechDatetimeType == ATechDate.FORMAT_TIME_ONLY_S)
            return getLeadingZero(this.hourOfDay, 2) + ":" + getLeadingZero(this.minute, 2) + ":"
                    + getLeadingZero(this.second, 2);
        else
            return "? " + getLeadingZero(this.hourOfDay, 2) + ":" + getLeadingZero(this.minute, 2);
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public int getTime()
    {
        if (this.atechDatetimeType == ATechDate.FORMAT_DATE_AND_TIME_MIN
                || this.atechDatetimeType == ATechDate.FORMAT_TIME_ONLY_MIN)
            // return getLeadingZero(this.hour_of_day,2) + ":" +
            // getLeadingZero(this.minute,2);
            return this.hourOfDay * 100 + this.minute;
        else if (this.atechDatetimeType == ATechDate.FORMAT_DATE_AND_TIME_S
                || this.atechDatetimeType == ATechDate.FORMAT_TIME_ONLY_S)
            // return getLeadingZero(this.hour_of_day,2) + ":" +
            // getLeadingZero(this.minute,2) + ":" +
            // getLeadingZero(this.second,2);
            return -1;
        else
            // return "? " + getLeadingZero(this.hour_of_day,2) + ":" +
            // getLeadingZero(this.minute,2);
            return -1;
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
     * public String getDateTimeString(long date)
     * {
     * return getDateTimeString(date, 1);
     * }
     * public String getDateTimeAsDateString(long date)
     * {
     * return getDateTimeString(date, 2);
     * }
     */

    // public static final int DATE_TIME_ATECH_DATETIME = 1;
    // public static final int DATE_TIME_ATECH_DATE = 2;
    // public static final int DATE_TIME_ATECH_TIME = 3;

    /**
     * Gets the aT date time from gc.
     *
     * @param _day
     * @param _month
     * @param _year
     * @return the aT date time from gc
     */
    public static long getATDateTimeFromDateParts(int _day, int _month, int _year) // throws
                                                                                   // Exception
    {
        ATechDate atd = new ATechDate(_day, _month, _year);
        return atd.getATDateTimeAsLong();
    }

    /**
     * Gets the aT date time from gc.
     *
     * @param _day
     * @param _month
     * @param _year
     * @param hour
     * @param minute
     * @param type
     *            the type
     * @return the aT date time from gc
     */
    public static long getATDateTimeFromDateParts(int _day, int _month, int _year, int hour, int minute, int type) // throws
                                                                                                                   // Exception
    {
        ATechDate atd = new ATechDate(_day, _month, _year, hour, minute, type);
        return atd.getATDateTimeAsLong();
    }

    /**
     * Gets the aT date time from gc.
     *
     * @param gc
     *            the gc
     * @param type
     *            the type
     * @return the aT date time from gc
     */
    public static long getATDateTimeFromGC(GregorianCalendar gc, int type) // throws
                                                                           // Exception
    {
        long dt = 0L;

        if (type == FORMAT_DATE_AND_TIME_S)
        {
            dt += gc.get(Calendar.YEAR) * 10000000000L;
            dt += (gc.get(Calendar.MONTH) + 1) * 100000000L;
            dt += gc.get(Calendar.DAY_OF_MONTH) * 1000000L;
            dt += gc.get(Calendar.HOUR_OF_DAY) * 10000L;
            dt += gc.get(Calendar.MINUTE) * 100L;
            dt += gc.get(Calendar.SECOND);
        }
        else if (type == FORMAT_DATE_AND_TIME_MIN)
        {
            dt += gc.get(Calendar.YEAR) * 100000000L;
            dt += (gc.get(Calendar.MONTH) + 1) * 1000000L;
            dt += gc.get(Calendar.DAY_OF_MONTH) * 10000L;
            dt += gc.get(Calendar.HOUR_OF_DAY) * 100L;
            dt += gc.get(Calendar.MINUTE);
        }
        else if (type == FORMAT_DATE_ONLY)
        {
            dt += gc.get(Calendar.YEAR) * 10000L;
            dt += (gc.get(Calendar.MONTH) + 1) * 100L;
            dt += gc.get(Calendar.DAY_OF_MONTH);
        }
        else if (type == ATechDate.FORMAT_TIME_ONLY_MIN)
        {
            dt += gc.get(Calendar.HOUR_OF_DAY) * 100L;
            dt += gc.get(Calendar.MINUTE);
        }
        else
        {
            System.out.println("getATDateTimeFromGC: Unallowed type");
            // throw new Exception("getATDateTimeFromGC: Unallowed type");
        }

        return dt;
    }

    /**
     * Gets the ATech DateTime from gc.
     *
     * @param dt
     *            the Date instance
     * @param type
     *            the type
     * @return the ATech DateTime from gc
     */
    public static long getATDateTimeFromDate(Date dt, int type) // throws
                                                                // Exception
    {
        GregorianCalendar gc = new GregorianCalendar(); // dt.getYear(),
                                                        // dt.getMonth(),
                                                        // dt.getDay());
        gc.setTime(dt);

        return ATechDate.getATDateTimeFromGC(gc, type);
    }

    /**
     * Convert at date.
     *
     * @param dt
     *            the dt
     * @param input_format
     *            the input_format
     * @param output_format
     *            the output_format
     * @return the long
     */
    public static long convertATDate(long dt, int input_format, int output_format)
    {
        ATechDate atd = new ATechDate(input_format, dt);
        return atd.getATDateTimeAsLong(output_format);
    }

    /**
     * Gets the aT date time from parts.
     *
     * @param _day
     *            the _day
     * @param _month
     *            the _month
     * @param _year
     *            the _year
     * @param _hour
     *            the _hour
     * @param _minute
     *            the _minute
     * @param type
     *            the type
     * @return the aT date time from parts
     */
    public long getATDateTimeFromParts(int _day, int _month, int _year, int _hour, int _minute, int type) // throws
                                                                                                          // Exception
    {
        long dt = 0L;

        if (type == FORMAT_DATE_AND_TIME_MIN)
        {
            dt += _year * 100000000L;
            dt += _month * 1000000L;
            dt += _day * 10000L;
            dt += _hour * 100L;
            dt += _minute;
        }
        else if (type == FORMAT_DATE_ONLY)
        {
            dt += _year * 10000L;
            dt += _month * 100L;
            dt += _day;
        }
        else if (type == ATechDate.FORMAT_TIME_ONLY_MIN)
        {
            dt += _hour * 100L;
            dt += _minute;
        }
        else
        {
            System.out.println("getATDateTimeFromParts: Unallowed type");
        }

        return dt;
    }

    /**
     * Gets the aT date time as long.
     *
     * @return the aT date time as long
     */
    public long getATDateTimeAsLong() // throws Exception
    {
        return this.getATDateTimeAsLong(this.atechDatetimeType);
    }

    /**
     * Gets the aT date time as long.
     *
     * @return the aT date time as long
     */
    public String getATDateTimeAsLongString() // throws Exception
    {
        return this.getATDateTimeAsLongString(this.atechDatetimeType);
    }

    /**
     * Gets the aT date time as long.
     *
     * @param output_format
     *            the output_format
     * @return the aT date time as long
     */
    public long getATDateTimeAsLong(int output_format) // throws Exception
    {
        long dt = 0L;

        if (output_format == FORMAT_DATE_AND_TIME_MIN)
        {
            dt += year * 100000000L;
            dt += month * 1000000L;
            dt += this.dayOfMonth * 10000L;
            dt += this.hourOfDay * 100L;
            dt += minute;
        }
        else if (output_format == FORMAT_DATE_AND_TIME_S)
        {
            dt += year * 10000000000L;
            dt += month * 100000000L;
            dt += this.dayOfMonth * 1000000L;
            dt += this.hourOfDay * 10000L;
            dt += minute * 100L;
            dt += this.second;
        }
        else if (output_format == FORMAT_DATE_ONLY)
        {
            dt += year * 10000L;
            dt += month * 100L;
            dt += this.dayOfMonth;
        }
        else if (output_format == ATechDate.FORMAT_TIME_ONLY_MIN)
        {
            dt += this.hourOfDay * 100L;
            dt += minute;
        }
        else
        {
            System.out.println("ERROR: getATDateTimeAsLong: Unsupported type [" + output_format + "]");
        }

        return dt;

    }

    public String getATDateTimeAsLongString(int output_format) // throws
                                                               // Exception
    {
        String dt_s = "";
        long dt = 0L;

        if (output_format == FORMAT_DATE_AND_TIME_MIN)
        {
            dt += year * 100000000L;
            dt += month * 1000000L;
            dt += this.dayOfMonth * 10000L;
            dt += this.hourOfDay * 100L;
            dt += minute;
            dt_s = "" + dt;
        }
        else if (output_format == FORMAT_DATE_AND_TIME_S)
        {
            dt += year * 10000000000L;
            dt += month * 100000000L;
            dt += this.dayOfMonth * 1000000L;
            dt += this.hourOfDay * 10000L;
            dt += minute * 100L;
            dt += this.second;
            dt_s = "" + dt;
        }
        else if (output_format == FORMAT_DATE_ONLY)
        {
            dt += year * 10000L;
            dt += month * 100L;
            dt += this.dayOfMonth;
            dt_s = "" + dt;
        }
        else if (output_format == ATechDate.FORMAT_TIME_ONLY_MIN)
        {
            dt += this.hourOfDay * 100L;
            dt += minute;

            dt_s = getLeadingZero((int) dt, 4);
        }
        else
        {
            System.out.println("ERROR: getATDateTimeAsLong: Unsupported type [" + output_format + "]");
        }

        return dt_s;

    }

    /**
     * Gets the date from at date.
     *
     * @param data
     *            the data
     * @return the date from at date
     */
    public long getDateFromATDate(long data)
    {
        if (this.atechDatetimeType == FORMAT_DATE_AND_TIME_MIN)
        {
            // 200701011222
            int d2 = (int) (data / 10000);

            // long dd = data%10000;
            // data -= dd;

            // System.out.println("D2: " +d2);

            // System.out.println(data);
            return d2;
        }
        return -1;
    }

    /**
     * Gets the date time as time string.
     *
     * @param date
     *            the date
     * @return the date time as time string
     */
    public String getDateTimeAsTimeString(long date)
    {
        return getDateTimeString(date, 3);
    }

    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)

    private final static int DT_DATETIME = 1;
    private final static int DT_DATE = 2;

    // private final static int DT_TIME = 3;

    /**
     * Gets the date time string.
     *
     * @param dt
     *            the dt
     * @param ret_type
     *            the ret_type
     * @return the date time string
     */
    public String getDateTimeString(long dt, int ret_type)
    {

        // System.out.println("DT process: " + dt);
        /*
         * int y = (int)(dt/10000000L);
         * dt -= y*10000000L;
         * int m = (int)(dt/1000000L);
         * dt -= m*1000000L;
         * int d = (int)(dt/10000L);
         * dt -= d*10000L;
         * int h = (int)(dt/100L);
         * dt -= h*100L;
         * int min = (int)dt;
         */

        // 200612051850
        int y = (int) (dt / 100000000L);
        dt -= y * 100000000L;

        int m = (int) (dt / 1000000L);
        dt -= m * 1000000L;

        int d = (int) (dt / 10000L);
        dt -= d * 10000L;

        int h = (int) (dt / 100L);
        dt -= h * 100L;

        int min = (int) dt;

        if (ret_type == DT_DATETIME)
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y + "  " + getLeadingZero(h, 2) + ":"
                    + getLeadingZero(min, 2);
        else if (ret_type == DT_DATE)
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y;
        else
            return getLeadingZero(h, 2) + ":" + getLeadingZero(min, 2);

    }

    /*
     * public String getGCObjectFromDateTimeLong(long dt)
     * {
     * int y = (int)(dt/100000000L);
     * dt -= y*100000000L;
     * int m = (int)(dt/1000000L);
     * dt -= m*1000000L;
     * int d = (int)(dt/10000L);
     * dt -= d*10000L;
     * int h = (int)(dt/100L);
     * dt -= h*100L;
     * int min = (int)dt;
     * GregorianCalendar gc1 = new GregorianCalendar();
     * //gc1.set(GregorianCalendar.
     * return null;
     * }
     */

    /**
     * Gets the gregorian calendar.
     *
     * @param format
     *            the format
     * @param dt
     *            the dt
     * @return the gregorian calendar
     */
    public static GregorianCalendar getGregorianCalendar(int format, long dt)
    {
        ATechDate atd = new ATechDate(format, dt);
        return atd.getGregorianCalendar();
    }

    /**
     * Gets the gregorian calendar.
     *
     * @return the gregorian calendar
     */
    public GregorianCalendar getGregorianCalendar()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeZone(TimeZoneUtil.getInstance().getEmptyTimeZone());
        gc.set(Calendar.YEAR, this.year);
        gc.set(Calendar.MONTH, this.month - 1);
        gc.set(Calendar.DAY_OF_MONTH, this.dayOfMonth);
        gc.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
        gc.set(Calendar.MINUTE, this.minute);
        gc.set(Calendar.SECOND, this.second);
        gc.set(Calendar.MILLISECOND, this.msecond);

        // System.out.println("getGregorianCalendar: parts   " +
        // this.hour_of_day + ":" + this.minute);
        // System.out.println("getGregorianCalendar: gc      " +
        // gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE));

        return gc;
    }

    /**
     * Sets the Gregorian Calendar
     *
     * @param gc
     */
    public void setGregorianCalendar(GregorianCalendar gc)
    {
        this.year = gc.get(Calendar.YEAR);
        this.month = gc.get(Calendar.MONTH) + 1;
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        this.hourOfDay = gc.get(Calendar.HOUR_OF_DAY);
        this.minute = gc.get(Calendar.MINUTE);
        this.second = gc.get(Calendar.SECOND);
        this.msecond = gc.get(Calendar.MILLISECOND);
    }

    /**
     * Adds the.
     *
     * @param date_field
     *            the date_field
     * @param value
     *            the value
     */
    public void add(int date_field, int value)
    {
        GregorianCalendar gc = getGregorianCalendar();
        gc.add(date_field, value);

        setGregorianCalendar(gc);
    }

    /*
     * public void addHour(int hours)
     * {
     * GregorianCalendar gc = getGregorianCalendar();
     * // gc.add(
     * /*
     * if (hours==0)
     * {
     * }
     * else if (hours <0)
     * {
     * // negative
     * this.hour_of_day += hours;
     * if (this.hour_of_day<0)
     * {
     * this.hour_of_day = 23 + this.hour_of_day;
     * this.day_of_month--;
     * if (this.day_of_month<1)
     * {
     * if (this.month==1)
     * {
     * this.month=12;
     * this.year--;
     * }
     * else
     * {
     * this.month--;
     * }
     * if (month==2)
     * {
     * if (isLeapYear(this.year))
     * this.day_of_month = 29;
     * else
     * this.day_of_month = 28;
     * }
     * else
     * this.day_of_month = this.days_month[month];
     * }
     * }
     * }
     * else // add +
     * {
     * this.hour_of_day += hours;
     * if (this.hour_of_day> 23)
     * {
     * int change = this.hour_of_day - 23;
     * this.hour_of_day = change;
     * this.day_of_month++;
     * if (this.month==2)
     * {
     * if (isLeapYear(year))
     * {
     * if (this.day_of_month>29)
     * {
     * }
     * }
     * }
     * else
     * {
     * }
     * if (this.days_month[this.month] < this.da)
     * {
     * }
     * }
     * }
     */
    // }

    /*
     * public String getDateTimeString(int date, int time)
     * {
     * return getDateString(date)+" " + getTimeString(time);
     * }
     */

    /**
     * Gets the leading zero.
     *
     * @param number
     *            the number
     * @param places
     *            the places
     * @return the leading zero
     */
    public String getLeadingZero(int number, int places)
    {

        String nn = "" + number;

        while (nn.length() < places)
        {
            nn = "0" + nn;
        }

        return nn;

    }

    /**
     * Checks if is leap year.
     *
     * @param _year
     *            the _year
     * @return true, if is leap year
     */
    public boolean isLeapYear(int _year)
    {
        if (_year % 4 != 0)
            return false;
        else
        {
            if (_year % 100 == 0 && _year % 400 != 0)
                return false;
            else
                return true;
        }

        // A leap year is any year divisible by four except
        // years both divisible by 100 and not divisible by 400.

    }

    /**
     * equals
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof ATechDate)
        {
            ATechDate o1 = (ATechDate) o;

            if (this.atechDatetimeType != o1.atechDatetimeType)
                return false;

            switch (this.atechDatetimeType)
            {
                case ATechDate.FORMAT_DATE_ONLY:
                    {
                        if (this.dayOfMonth != o1.dayOfMonth || this.month != o1.month || this.year != o1.year)
                            return false;
                        else
                            return true;
                    }

                case ATechDate.FORMAT_DATE_AND_TIME_S:
                    {
                        if (this.dayOfMonth != o1.dayOfMonth || this.month != o1.month || this.year != o1.year
                                || this.hourOfDay != o1.hourOfDay || this.minute != o1.minute
                                || this.second != o1.second)
                            return false;
                        else
                            return true;

                    }

                case ATechDate.FORMAT_DATE_AND_TIME_MS:
                    {
                        if (this.dayOfMonth != o1.dayOfMonth || this.month != o1.month || this.year != o1.year
                                || this.hourOfDay != o1.hourOfDay || this.minute != o1.minute
                                || this.second != o1.second || this.msecond != o1.msecond)
                            return false;
                        else
                            return true;

                    }

                case ATechDate.FORMAT_DATE_AND_TIME_MIN:
                    {
                        if (this.dayOfMonth != o1.dayOfMonth || this.month != o1.month || this.year != o1.year
                                || this.hourOfDay != o1.hourOfDay || this.minute != o1.minute)
                            return false;
                        else
                            return true;

                    }

                default:
                    log.error("Equals for this type is not implemented !");
                    return false;
            }

        }
        else
            return false;

    }

    public int getDayOfMonth()
    {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth)
    {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getHourOfDay()
    {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay)
    {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute()
    {
        return minute;
    }

    public void setMinute(int minute)
    {
        this.minute = minute;
    }

    public int getSecond()
    {
        return second;
    }

    public void setSecond(int second)
    {
        this.second = second;
    }

    public int getMsecond()
    {
        return msecond;
    }

    public void setMsecond(int msecond)
    {
        this.msecond = msecond;
    }

    public int getAtechDatetimeType()
    {
        return atechDatetimeType;
    }

    public void setAtechDatetimeType(int atechDatetimeType)
    {
        this.atechDatetimeType = atechDatetimeType;
    }

    /**
     * To String
     */
    @Override
    public String toString()
    {
        return this.getDateString() + " " + this.getTimeString();
    }

}
