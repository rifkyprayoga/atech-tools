package com.atech.utils.data; //ggc.meter.util;

//import ggc.util.DataAccess;

import java.util.*;

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

public class TimeZoneUtil
{

    // DataAccess m_da = DataAccess.getInstance();
    /**
     * The tzi.
     */
    TimeZone tzi;

    /**
     * The winter_time_change.
     */
    int winter_time_change = 0;

    /**
     * The summer_time_change.
     */
    int summer_time_change = 0;

    /**
     * The s_timezoneutil.
     */
    public static TimeZoneUtil s_timezoneutil;

    private boolean active = false;

    /**
     * The empty_tzi.
     */
    TimeZone empty_tzi;

    protected static Vector<String> time_zones_vector = null;
    protected static Hashtable<String, String> time_zones = null;

    /**
     * Gets the single instance of TimeZoneUtil.
     * 
     * @return single instance of TimeZoneUtil
     */
    public static TimeZoneUtil getInstance()
    {
        if (TimeZoneUtil.s_timezoneutil == null)
        {
            TimeZoneUtil.s_timezoneutil = new TimeZoneUtil();
        }

        return TimeZoneUtil.s_timezoneutil;
    }

    private TimeZoneUtil()
    {

        TimeZone tz = TimeZone.getDefault();
        // this.empty_tzi =
        this.empty_tzi = new SimpleTimeZone(tz.getRawOffset(), tz.getID(), 0, 0, 0, 0, 0, 0, 0, 0);

        this.tzi = tz;

        this.loadTimeZones();
    }


    public String getTimezoneDescription(String timeZoneId)
    {
        return time_zones.get(timeZoneId);
    }


    /**
     * Sets the time zone.
     * 
     * @param time_zone_id the new time zone
     */
    public void setTimeZone(String time_zone_id)
    {
        tzi = TimeZone.getTimeZone(time_zone_id);

        // this.empty_tzi =
        this.empty_tzi = new SimpleTimeZone(tzi.getRawOffset(), time_zone_id, 0, 0, 0, 0, 0, 0, 0, 0);

        // .time_zone_inew SimpleTimeZone

    }

    /**
     * Sets the winter time change.
     * 
     * @param val the new winter time change
     */
    public void setWinterTimeChange(int val)
    {
        this.winter_time_change = val;
        checkActive();
    }

    private void checkActive()
    {
        if (this.winter_time_change == 0 && this.summer_time_change == 0)
        {
            this.active = false;
        }
        else
        {
            this.active = true;
        }
    }

    /**
     * Gets the empty time zone.
     * 
     * @return the empty time zone
     */
    public TimeZone getEmptyTimeZone()
    {
        return this.empty_tzi;
    }

    /**
     * Sets the summer time change.
     * 
     * @param val the new summer time change
     */
    public void setSummerTimeChange(int val)
    {
        this.summer_time_change = val;
        checkActive();
    }

    /**
     * Sets the values.
     * 
     * @param timeZoneId the time zone id
     * @param winter_time_change the winter_time_change
     * @param summer_time_change the summer_time_change
     */
    public void setValues(String timeZoneId, int winter_time_change, int summer_time_change)
    {
        setTimeZone(timeZoneId);
        setWinterTimeChange(winter_time_change);
        setSummerTimeChange(summer_time_change);
    }

    /**
     * Checks if is winter time.
     * 
     * @param gc the gc
     * 
     * @return true, if is winter time
     */
    public boolean isWinterTime(GregorianCalendar gc)
    {
        return !tzi.inDaylightTime(gc.getTime());
    }

    /**
     * Checks if is summer time.
     * 
     * @param gc the gc
     * 
     * @return true, if is summer time
     */
    public boolean isSummerTime(GregorianCalendar gc)
    {
        return tzi.inDaylightTime(gc.getTime());
    }

    /**
     * Gets the corrected date time.
     * 
     * @param atdate the atdate
     * 
     * @return the corrected date time
     */
    public ATechDate getCorrectedDateTime(ATechDate atdate)
    {
        if (!this.active)
            return atdate;

        GregorianCalendar gc = atdate.getGregorianCalendar();

        if (this.isWinterTime(gc))
        {
            // winter time
            if (this.winter_time_change == 0)
                return atdate;
            else
            {
                atdate.add(Calendar.HOUR_OF_DAY, this.winter_time_change);
                return atdate;
            }

        }
        else
        {
            // summer time
            if (this.summer_time_change == 0)
                return atdate;
            else
            {
                atdate.add(Calendar.HOUR_OF_DAY, this.summer_time_change);
                return atdate;
            }

        }

        // return null;
    }

    // FIX

    /*
     * public Vector<String> getTimeZoneDescs()
     * {
     * Vector<String> vec = new Vector<String>();
     * for(Enumeration<String> en = m_da.timeZones.keys(); en.hasMoreElements();
     * )
     * {
     * String key = en.nextElement();
     * System.out.println("Key: " + key + " Value: " + m_da.timeZones.get(key));
     * vec.add(key);
     * }
     * Collections.sort(vec, new TimeZoneComparator());
     * return vec;
     * }
     * public String getTimeZoneKeyFromValue(String value)
     * {
     * for(Enumeration<String> en = m_da.timeZones.keys(); en.hasMoreElements();
     * )
     * {
     * String key = en.nextElement();
     * System.out.println(m_da.timeZones.get(key) + " = " + value);
     * if (m_da.timeZones.get(key).contains(value))
     * {
     * return key;
     * }
     * }
     * return "(GMT) Greenwich Mean Time : Dublin, Edinburgh, Lisbon, London";
     * }
     */


    /**
     * Load Time Zones
     */
    public void loadTimeZones()
    {
        time_zones = new Hashtable<String, String>();
        time_zones_vector = new Vector<String>();

        // Posible needed enchancment. We should probably list all ID's as
        // values. On windows default ID can be different
        // as in this table. We should add this names, if we encounter problems.

        addTimeZoneEntry("(GMT+13:00) Nuku'alofa", "Pacific/Tongatapu");
        addTimeZoneEntry("(GMT+12:00) Fiji, Kamchatka, Marshall Is.", "Pacific/Fiji");
        addTimeZoneEntry("(GMT+12:00) Auckland, Wellington", "Pacific/Auckland");
        addTimeZoneEntry("(GMT+11:00) Magadan, Solomon Is., New Caledonia", "Asia/Magadan");
        addTimeZoneEntry("(GMT+10:00) Vladivostok", "Asia/Vladivostok");
        addTimeZoneEntry("(GMT+10:00) Hobart", "Australia/Hobart");
        addTimeZoneEntry("(GMT+10:00) Guam, Port Moresby", "Pacific/Guam");
        addTimeZoneEntry("(GMT+10:00) Canberra, Melbourne, Sydney", "Australia/Sydney");
        addTimeZoneEntry("(GMT+10:00) Brisbane", "Australia/Brisbane");
        addTimeZoneEntry("(GMT+09:30) Adelaide", "Australia/Adelaide");
        addTimeZoneEntry("(GMT+09:00) Yakutsk", "Asia/Yakutsk");
        addTimeZoneEntry("(GMT+09:00) Seoul", "Asia/Seoul");
        addTimeZoneEntry("(GMT+09:00) Osaka, Sapporo, Tokyo", "Asia/Tokyo");
        addTimeZoneEntry("(GMT+08:00) Taipei", "Asia/Taipei");
        addTimeZoneEntry("(GMT+08:00) Perth", "Australia/Perth");
        addTimeZoneEntry("(GMT+08:00) Kuala Lumpur, Singapore", "Asia/Kuala_Lumpur");
        addTimeZoneEntry("(GMT+08:00) Irkutsk, Ulaan Bataar", "Asia/Irkutsk");
        addTimeZoneEntry("(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi", "Asia/Hong_Kong");
        addTimeZoneEntry("(GMT+07:00) Krasnoyarsk", "Asia/Krasnoyarsk");
        addTimeZoneEntry("(GMT+07:00) Bangkok, Hanoi, Jakarta", "Asia/Bangkok");
        addTimeZoneEntry("(GMT+06:30) Rangoon", "Asia/Rangoon");
        addTimeZoneEntry("(GMT+06:00) Sri Jayawardenepura", "Asia/Colombo");
        addTimeZoneEntry("(GMT+06:00) Astana, Dhaka", "Asia/Dhaka");
        addTimeZoneEntry("(GMT+06:00) Almaty, Novosibirsk", "Asia/Almaty");
        addTimeZoneEntry("(GMT+05:45) Kathmandu", "Asia/Katmandu");
        addTimeZoneEntry("(GMT+05:30) Chennai, Kolkata, Mumbai, New Delhi", "Asia/Calcutta");
        addTimeZoneEntry("(GMT+05:00) Islamabad, Karachi, Tashkent", "Asia/Karachi");
        addTimeZoneEntry("(GMT+05:00) Ekaterinburg", "Asia/Yekaterinburg");
        addTimeZoneEntry("(GMT+04:30) Kabul", "Asia/Kabul");
        addTimeZoneEntry("(GMT+04:00) Baku, Tbilisi, Yerevan", "Asia/Baku");
        addTimeZoneEntry("(GMT+04:00) Abu Dhabi, Muscat", "Asia/Dubai");
        addTimeZoneEntry("(GMT+03:30) Tehran", "Asia/Tehran");
        addTimeZoneEntry("(GMT+03:00) Nairobi", "Africa/Nairobi");
        addTimeZoneEntry("(GMT+03:00) Moscow, St. Petersburg, Volgograd", "Europe/Moscow");
        addTimeZoneEntry("(GMT+03:00) Kuwait, Riyadh", "Asia/Kuwait");
        addTimeZoneEntry("(GMT+03:00) Baghdad", "Asia/Baghdad");
        addTimeZoneEntry("(GMT+02:00) Jerusalem", "Asia/Jerusalem");
        addTimeZoneEntry("(GMT+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius", "Europe/Helsinki");
        addTimeZoneEntry("(GMT+02:00) Harare, Pretoria", "Africa/Harare");
        addTimeZoneEntry("(GMT+02:00) Cairo", "Africa/Cairo");
        addTimeZoneEntry("(GMT+02:00) Bucharest", "Europe/Bucharest");
        addTimeZoneEntry("(GMT+02:00) Athens, Istanbul, Minsk", "Europe/Athens");
        addTimeZoneEntry("(GMT+01:00) West Central Africa", "Africa/Lagos");
        addTimeZoneEntry("(GMT+01:00) Sarajevo, Skopje, Warsaw, Zagreb", "Europe/Warsaw");
        addTimeZoneEntry("(GMT+01:00) Brussels, Copenhagen, Madrid, Paris", "Europe/Brussels");
        addTimeZoneEntry("(GMT+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague", "Europe/Prague");
        addTimeZoneEntry("(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna", "Europe/Amsterdam");
        addTimeZoneEntry("(GMT) Casablanca, Monrovia", "Africa/Casablanca");
        addTimeZoneEntry("(GMT) Greenwich Mean Time : Dublin, Edinburgh, Lisbon, London", "Europe/Dublin");
        addTimeZoneEntry("(GMT-01:00) Azores", "Atlantic/Azores");
        addTimeZoneEntry("(GMT-01:00) Cape Verde Is.", "Atlantic/Cape_Verde");
        addTimeZoneEntry("(GMT-02:00) Mid-Atlantic", "Atlantic/South_Georgia");
        addTimeZoneEntry("(GMT-03:00) Brasilia", "America/Sao_Paulo");
        addTimeZoneEntry("(GMT-03:00) Buenos Aires, Georgetown", "America/Buenos_Aires");
        addTimeZoneEntry("(GMT-03:00) Greenland", "America/Thule");
        addTimeZoneEntry("(GMT-03:30) Newfoundland", "America/St_Johns");
        addTimeZoneEntry("(GMT-04:00) Atlantic Time (Canada)", "America/Halifax");
        addTimeZoneEntry("(GMT-04:00) Caracas, La Paz", "America/Caracas");
        addTimeZoneEntry("(GMT-04:00) Santiago", "America/Santiago");
        addTimeZoneEntry("(GMT-05:00) Bogota, Lima, Quito", "America/Bogota");
        addTimeZoneEntry("(GMT-05:00) Eastern Time (US & Canada)", " America/New_York");
        addTimeZoneEntry("(GMT-05:00) Indiana (East)", "America/Indianapolis");
        addTimeZoneEntry("(GMT-06:00) Central America", "America/Costa_Rica");
        addTimeZoneEntry("(GMT-06:00) Central Time (US & Canada)", "America/Chicago");
        addTimeZoneEntry("(GMT-06:00) Guadalajara, Mexico City, Monterrey", "America/Mexico_City");
        addTimeZoneEntry("(GMT-06:00) Saskatchewan", "America/Winnipeg");
        addTimeZoneEntry("(GMT-07:00) Arizona", "America/Phoenix");
        addTimeZoneEntry("(GMT-07:00) Chihuahua, La Paz, Mazatlan", "America/Tegucigalpa");
        addTimeZoneEntry("(GMT-07:00) Mountain Time (US & Canada)", "America/Denver");
        addTimeZoneEntry("(GMT-08:00) Pacific Time (US & Canada); Tijuana", "America/Los_Angeles");
        addTimeZoneEntry("(GMT-09:00) Alaska", "America/Anchorage");
        addTimeZoneEntry("(GMT-10:00) Hawaii", "Pacific/Honolulu");
        addTimeZoneEntry("(GMT-11:00) Midway Island, Samoa", "Pacific/Apia");
        addTimeZoneEntry("(GMT-12:00) International Date Line West", "MIT");

    }

    /**
     * Add Time Zone Entry
     *
     * @param long_desc
     * @param keycode
     */
    public void addTimeZoneEntry(String long_desc, String keycode)
    {
        time_zones.put(long_desc, keycode);
        time_zones_vector.add(long_desc);
    }

    public Vector<String> getTimezonesAsVector()
    {
        return time_zones_vector;
    }


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
        public final int compare(String pFirst, String pSecond)
        {

            // System.out.println(pFirst + " " + pSecond);

            if (areSameType(pFirst, pSecond))
                return pFirst.compareTo(pSecond) * typeChanger(pFirst, pSecond);
            else
            {
                if (pFirst.startsWith("(GMT-"))
                    return -1;
                else if (pFirst.startsWith("(GMT)")) // &&
                                                     // (second.contains("(GMT)")))
                {
                    if (pSecond.startsWith("(GMT-"))
                        return 1;
                    else
                        return -1;
                }
                else
                    return 1;

            }
        } // end compare

        public int typeChanger(String first, String second)
        {
            if (first.startsWith("(GMT-") && second.startsWith("(GMT-"))
                return -1;
            else
                return 1;
        }

        public boolean areSameType(String first, String second)
        {
            if (first.startsWith("(GMT+") && second.startsWith("(GMT+") || first.startsWith("(GMT)")
                    && second.startsWith("(GMT)") || first.startsWith("(GMT-") && second.startsWith("(GMT-"))
                return true;
            else
                return false;
        }

    }

}
