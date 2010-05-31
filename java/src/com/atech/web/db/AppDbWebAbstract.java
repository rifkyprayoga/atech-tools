package com.atech.web.db;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.web.db.objects.User;
import com.atech.web.jsp.AppContextAbstract;
import com.atech.web.util.DataAccessWeb;
import com.atech.web.util.I18nWebControl;

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


public abstract class AppDbWebAbstract
{

    // Connection conn;
    // Statement stmt;

    protected boolean debug = false;

    private static Log log = LogFactory.getLog(AppDbWebAbstract.class);
    protected AppContextAbstract m_context;
    //public Session m_session = null;

    protected int m_errorCode = 0;
    protected String m_errorDesc = "";

    protected String m_addId = "";
    //SessionFactory m_session_factory = null;

    protected Hashtable<String, String> config = new Hashtable<String, String>();

    protected User user = null;

    //protected DataAccessWeb da = null;
    protected ArrayList<User> users = null;

    //private String pis_web_version = "v0.5.3";
    I18nWebControl ic = null;

    // WEB

    protected String week_days = "";
    protected String months = "";
    protected DataAccessWeb daw;

    // 0.2 - Phase 0 (visual outlook, main framework)
    // 0.3 - Phase 1 (basic functionality - look, calls of functions, web side)
    // 0.4 - Phase 2 (extended functionality - view data elements, get real
    // elements)

    // 0.5 - Phase 3 (add/delete/edit data)

    // 0.6 - Phase 4 (finish)

    public AppDbWebAbstract(DataAccessWeb daw_, AppContextAbstract context_)
    {
        
        this.daw = daw_;
        this.m_context = context_;
        /*
        this.m_session_factory = sess_fact;
        m_session = this.m_session_factory.openSession();

        this.da = daw;
        */
        
        this.initializeDb();
        
        loadSettings();
        loadUsers();

        //System.out.println("PIS_Web Loaded: " + pis_web_version);
    }

    public abstract void initializeDb();
    
    public abstract void destroyDb();
    
    
    public void setI18nWebControl(I18nWebControl ic)
    {
        this.ic = ic;
        initComboSystem();
        initWeekDays();
        initMonths();
    }

    public void closeDb()
    {
    }

    public void displayError(String source, Exception ex)
    {

        System.out.println("Exception [" + source + "]: " + ex);

        if (debug)
        {
            System.out.println("SQLException [" + source + "]: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    // /
    // / WEB METHODS
    // /

    public abstract void loadSettings();
/*    {
        Query q = getSession()
                .createQuery("select pst from com.atech.inf_sys.pis.db.hibernate.config.SettingsH as pst");

        Iterator it = q.iterate();

        while (it.hasNext())
        {
            SettingsH st = (SettingsH) it.next();
            this.config.put(st.getProperty(), st.getValue());
        }
    }*/

    public abstract void loadUsers();
/*    {
        this.users = new ArrayList<UserH>();

        System.out.println("Load Users");

        Query q = getSession().createQuery("select pst from com.atech.inf_sys.pis.db.hibernate.config.UserH as pst");

        Iterator it = q.iterate();

        while (it.hasNext())
        {
            UserH st = (UserH) it.next();
            this.users.add(st);
        }
    }
*/
    
    public String getMessage(String key)
    {
        return key;
    }

    public String getWebLanguage()
    {
        if (config.containsKey("WEB_LANG"))
        {
            return config.get("WEB_LANG");
        }
        else
            return "SI";
    }

    public String getWebName()
    {
        if (config.containsKey("WEB_NAME"))
        {
            return config.get("WEB_NAME");
        }
        else
            return "Unknown";
    }

    // 0 = OK
    // -1 = username not found
    // -2 = password wrong
    public long doLogin(String user, String pass)
    {

        for (int i = 0; i < this.users.size(); i++)
        {
            User us = this.users.get(i);

            if (us.username.equals(user))
            {
                if (us.password.equals(pass))
                    return us.id;
                else
                    return -2;
            }
        }

        return -1;

    }

    // 0 = OK
    // -1 = username not found
    // -2 = password wrong
    public long checkUser(String user, String pass)
    {
        return doLogin(user, pass);
    }

    
    public User getUser(long id)
    {
        Iterator<User> it = this.users.iterator();

        while (it.hasNext())
        {
            User st = (User) it.next();

            if (st.id == id)
            {
                return st;
            }
        }

        return null;
    }

    //
    // BASIC METHODS
    //

    public abstract boolean add(Object obj);

    public abstract boolean edit(Object obj);

    public abstract boolean get(Object obj);

    public abstract boolean delete(Object obj);

    public String addGetId()
    {
        return this.m_addId;
    }

    public int getErrorCode()
    {
        return this.m_errorCode;
    }

    public String getErrorDescription()
    {
        return this.m_errorDesc;
    }

    public void setError(int code, String desc, String source)
    {
        this.m_errorCode = code;
        this.m_errorDesc = source + " : " + desc;
    }

    // *************************************************************
    // **** DAY AND MONTH HANDLING ****
    // *************************************************************

    public String getWeekDays()
    {
        if (this.week_days == null)
            initWeekDays();

        return this.week_days;
    }

    public void initWeekDays()
    {
        StringBuffer sb_wd = new StringBuffer();

        String days[] = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };

        for (int i = 0; i < 7; i++)
        {
            sb_wd.append(ic.getMessage(days[i]));

            if (i != 6)
                sb_wd.append(",");
        }

        this.week_days = sb_wd.toString();
    }

    public String getMonths()
    {
        if (this.months == null)
            initMonths();

        return this.months;
    }

    public void initMonths()
    {
        StringBuffer sb_mon = new StringBuffer();

        String mon[] = { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER",
                        "OCTOBER", "NOVEMBER", "DECEMBER" };

        for (int i = 0; i < 12; i++)
        {
            sb_mon.append(ic.getMessage(mon[i]));

            if (i != 11)
                sb_mon.append(",");
        }

        this.months = sb_mon.toString();

    }


    public String getEmptyStringNotNull(String value)
    {
        if (value == null)
            return "";
        else
            return value;
    }


    public String getDateTimeString(long time)
    {
        if (time > 0)
            return getDatePickerDate(time);
        else
            return "";
    }

    /*
     * public ArrayList getMyPIS(String time_int, int sort, String sort_way) {
     * return getAllNews(1); }
     */
    /*
     * public ArrayList getMyPisEntries(long user_id, int filter_type) { //
     * TO-DO return getMyPisEntries(user_id, new GregorianCalendar(), new
     * GregorianCalendar()); }
     */

    /*
     * public ArrayList getMyPisEntries(long user_id, GregorianCalendar from,
     * GregorianCalendar till) { // TO-DO ArrayList list = new ArrayList();
     * 
     * long ids[] = { 1718, 1719, 1, 1722, 1724 };
     * 
     * 
     * long time[] = { 200612170900L, 200612171100L, 200612171130L,
     * 200612171500L, 200612171800L };
     * 
     * int type[] = { PlannerWeb.TYPE_MASS, PlannerWeb.TYPE_MASS,
     * PlannerWeb.TYPE_EVENT, PlannerWeb.TYPE_REMINDER, PlannerWeb.TYPE_MASS };
     * 
     * String bodys[] = { "", "", "Krst", "Sestanek", "", "" };
     * 
     * for(int i=0; i<time.length; i++) { PlannerWeb r = new PlannerWeb();
     * r.setId(ids[i]); r.setType(type[i]); r.setBody(bodys[i]);
     * r.setComment(""); r.setDateTime(time[i]);
     * 
     * list.add(r); }
     * 
     * return list;
     * 
     * }
     */


    // **************************************************************
    // ****              C O M B O  C R E A T I O N        ****
    // **************************************************************

    Hashtable<String,ArrayList<String>> sort_by_cb = null;
    Hashtable<String,ArrayList<String>> filter_cb = null;
    ArrayList<String> sort_way = null;
    ArrayList<String> owner = null;



    private String[] types_key = { 
                    "",
                "MY_PIS", 
                "NEWS", 
                "WEEKLY_NEWS"
                };


    public void initComboSystem()
    {
    // sort way
    sort_way = new ArrayList<String>();
    sort_way.add(ic.getMessage("SORT_ASCENDING"));
    sort_way.add(ic.getMessage("SORT_DESCENDING"));

    sort_by_cb = new Hashtable<String,ArrayList<String>>();
    ArrayList<String> list = null; 

    // sort by (my pis)
    list = new ArrayList();
    list.add(ic.getMessage("DATE_AND_TIME"));
    list.add(ic.getMessage("DATE"));
    list.add(ic.getMessage("TIME"));
    list.add(ic.getMessage("TYPE"));

    sort_by_cb.put("MY_PIS", list);

    // sort by (news)
    list = new ArrayList();
    list.add(ic.getMessage("DATE"));
    list.add(ic.getMessage("DATE_OF_EXPIRE"));
    list.add(ic.getMessage("SUBJECT2"));
    sort_by_cb.put("NEWS", list);


    // sort by (weekly news)
    list = new ArrayList();
    list.add(ic.getMessage("DATE"));
    list.add(ic.getMessage("SUB_TITLE"));

    sort_by_cb.put("WEEKLY_NEWS", list);



    filter_cb = new Hashtable<String,ArrayList<String>>();

    // filter by (my pis)
    list = new ArrayList<String>();
    list.add(ic.getMessage("TODAY"));
    list.add(ic.getMessage("TODAY_AND_TOMMOROW"));
    list.add(ic.getMessage("TOMMOROW"));
    list.add(ic.getMessage("THIS_WEEK"));
    list.add(ic.getMessage("THIS_MONTH"));
    list.add(ic.getMessage("ONE_WEEK"));
    list.add(ic.getMessage("ONE_MONTH"));
    filter_cb.put("MY_PIS", list);

    // filter by (news)
    list = new ArrayList<String>();
    list.add(ic.getMessage("ALL"));
    list.add(ic.getMessage("ALL_ACTIVE"));
    list.add(ic.getMessage("TODAY"));
    list.add(ic.getMessage("TODAY_AND_TOMMOROW"));
    list.add(ic.getMessage("TOMMOROW"));
    list.add(ic.getMessage("THIS_WEEK"));
    list.add(ic.getMessage("THIS_MONTH"));
    list.add(ic.getMessage("ONE_WEEK"));
    list.add(ic.getMessage("ONE_MONTH"));
    list.add(ic.getMessage("ARCHIVE"));
    filter_cb.put("NEWS", list);


    // filter by (news_weekly)
    list = new ArrayList<String>();
    list.add(ic.getMessage("ALL"));
    list.add(ic.getMessage("THIS_MONTH"));
    list.add(ic.getMessage("LAST_MONTH"));
    list.add(ic.getMessage("LAST_3_MONTHS"));
    list.add(ic.getMessage("LAST_6_MONTHS"));
    list.add(ic.getMessage("THIS_YEAR"));
    list.add(ic.getMessage("LAST_YEAR"));
    filter_cb.put("WEEKLY_NEWS", list);



    // owner
    owner = new ArrayList<String>();
    owner.add(ic.getMessage("OWNER_ME"));
    owner.add(ic.getMessage("OWNER_ALL"));


    //Hashtable<String,ArrayList<String>> sort_by_cb 
    }

    public String getSortWayCombo(int selection)
    {
    //System.out.println("Sel: " + selection);

    String out = "<select size=\"1\" name=\"sort_way\">";

    for (int i=0; i<sort_way.size(); i++)
    {
        out += "<option value=\"";
        out += i +"\" ";

        if (i==selection)
        {
        out += "selected=\"true\" ";
        }

        out += ">";
        out += sort_way.get(i);
        out += "</option>";

    }

    out += "</select>";

    //System.out.println(out);

    return out;

    }


    public String getSortCombo(int type, int selection)
    {

    ArrayList<String> list = sort_by_cb.get(this.types_key[type]);


    String out = "<select size=\"1\" name=\"sort_type\">";

    for (int i=0; i<list.size(); i++)
    {
        out += "<option value=\"";
        out += i +"\" ";

        if (i==selection)
        {
        out += "selected=\"true\" ";
        }

        out += ">";
        out += list.get(i);
        out += "</option>";

    }

    out += "</select>";

    //System.out.println(out);

    return out;

    }


    public String getFilterCombo(int type, int selection)
    {
    ArrayList<String> list = filter_cb.get(this.types_key[type]);


    String out = "<select size=\"1\" name=\"filter\">";

    for (int i=0; i<list.size(); i++)
    {
        out += "<option value=\"";
        out += i +"\" ";

        if (i==selection)
        {
        out += "selected=\"true\" ";
        }

        out += ">";
        out += list.get(i);
        out += "</option>";

    }

    out += "</select>";

    return out;
    }


    public String getOwnerCombo(int selection)
    {
    //System.out.println("Sel: " + selection);

    String out = "<select size=\"1\" name=\"owner\">";

    for (int i=0; i<owner.size(); i++)
    {
        out += "<option value=\"";
        out += i +"\" ";

        if (i==selection)
        {
        out += "selected=\"true\" ";
        }

        out += ">";
        out += owner.get(i);
        out += "</option>";

    }

    out += "</select>";

    //System.out.println(out);

    return out;

    }




    // **************************************************************
    // **** I N T E R V A L P R O C E S S I N G ****
    // **************************************************************

    public static final int TIME_INTERVAL_TODAY = 1;
    public static final int TIME_INTERVAL_TODAY_AND_TOMMOROW = 2;
    public static final int TIME_INTERVAL_TOMMOROW = 3;
    public static final int TIME_INTERVAL_THIS_WEEK = 4;
    public static final int TIME_INTERVAL_THIS_MONTH = 5;
    public static final int TIME_INTERVAL_ONE_WEEK = 6;
    public static final int TIME_INTERVAL_ONE_MONTH = 7;

    public static final int TIME_INTERVAL_LAST_MONTH = 8;
    public static final int TIME_INTERVAL_LAST_3_MONTHS = 9;
    public static final int TIME_INTERVAL_LAST_6_MONTHS = 10;
    public static final int TIME_INTERVAL_LAST_YEAR = 11;
    public static final int TIME_INTERVAL_THIS_YEAR = 12;

    public long[] getTimeInterval(int interval_type)
    {
        long[] time = new long[2];

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());

        GregorianCalendar gc1 = new GregorianCalendar();
        getGCFrom(gc1, gc, GregorianCalendar.DAY_OF_MONTH);
        getGCFrom(gc1, gc, GregorianCalendar.MONTH);
        getGCFrom(gc1, gc, GregorianCalendar.YEAR);

        if (interval_type == TIME_INTERVAL_TODAY)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_TODAY_AND_TOMMOROW)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.add(GregorianCalendar.DAY_OF_MONTH, 1);
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

        }
        else if (interval_type == TIME_INTERVAL_TOMMOROW)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);
            gc1.add(GregorianCalendar.DAY_OF_MONTH, 1);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_THIS_WEEK)
        {
            gc1 = getFirstDayInWeekAndSet(gc1);
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.add(GregorianCalendar.DAY_OF_MONTH, 6);
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

        }
        else if (interval_type == TIME_INTERVAL_THIS_MONTH)
        {
            gc1.set(GregorianCalendar.DAY_OF_MONTH, 1);
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.DAY_OF_MONTH, gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_ONE_WEEK)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.add(GregorianCalendar.DAY_OF_MONTH, 6);
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_ONE_MONTH)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.add(GregorianCalendar.MONTH, 1);
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_LAST_MONTH)
        {

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            gc1.add(GregorianCalendar.MONTH, -1);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

        }
        else if (interval_type == TIME_INTERVAL_LAST_3_MONTHS)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            gc1.add(GregorianCalendar.MONTH, -3);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_LAST_6_MONTHS)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            gc1.add(GregorianCalendar.MONTH, -6);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_LAST_YEAR)
        {
            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            gc1.add(GregorianCalendar.YEAR, -1);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }
        else if (interval_type == TIME_INTERVAL_THIS_YEAR)
        {
            gc1.set(GregorianCalendar.DAY_OF_MONTH, 1);
            gc1.set(GregorianCalendar.MONTH, 0);
            gc1.set(GregorianCalendar.YEAR, gc.get(GregorianCalendar.YEAR));

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 0);
            gc1.set(GregorianCalendar.MINUTE, 0);

            time[0] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);

            gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
            gc1.set(GregorianCalendar.MINUTE, 59);
            gc1.set(GregorianCalendar.DAY_OF_MONTH, 31);
            gc1.set(GregorianCalendar.MONTH, 11);

            time[1] = daw.getATDateTimeFromGC(gc1, DataAccessWeb.DATE_TIME_ATECH_DATETIME);
        }

        return time;
        /*
         * public static final int TIME_INTERVAL_LAST_MONTH = 8; public static
         * final int TIME_INTERVAL_LAST_3_MONTHS = 9; public static final int
         * TIME_INTERVAL_LAST_6_MONTHS = 10; public static final int
         * TIME_INTERVAL_LAST_YEAR = 11;
         */

    }

    private GregorianCalendar getFirstDayInWeekAndSet(GregorianCalendar gc)
    {

        int day_of_week = gc.get(GregorianCalendar.DAY_OF_WEEK);

        int first = getDayOfWeek(day_of_week);

        while (first != 0)
        {
            gc.add(GregorianCalendar.DAY_OF_YEAR, -1);

            day_of_week = gc.get(GregorianCalendar.DAY_OF_WEEK);
            first = getDayOfWeek(day_of_week);
        }

        return gc;

    }

    public static int[] days = { GregorianCalendar.MONDAY, GregorianCalendar.TUESDAY, GregorianCalendar.WEDNESDAY,
                                GregorianCalendar.THURSDAY, GregorianCalendar.FRIDAY, GregorianCalendar.SATURDAY,
                                GregorianCalendar.SUNDAY };

    public int getDayOfWeek(int dow)
    {
        for (int i = 0; i < 7; i++)
        {
            if (days[i] == dow)
                return i;
        }

        return -1;
    }

    public void getGCFrom(GregorianCalendar target, GregorianCalendar source, int tag)
    {
        target.set(tag, source.get(tag));
    }

    // *************************************************************
    // **** U S E R ****
    // *************************************************************

    // *************************************************************
    // **** U T I L S ****
    // *************************************************************

    private long getLongFromString(String id_str)
    {

        long id = 0;

        if (id_str != null)
        {
            try
            {
                id = Long.parseLong(id_str);
            }
            catch (Exception ex)
            {
            }
        }

        return id;
    }

    private int getIntFromString(String id_str)
    {

        int id = 0;

        if (id_str != null)
        {
            try
            {
                id = Integer.parseInt(id_str);
            }
            catch (Exception ex)
            {
            }
        }

        return id;
    }

    public String getDatePickerDate(long dt)
    {

        int y = (int) (dt / 100000000L);
        dt -= y * 100000000L;

        int m = (int) (dt / 1000000L);
        dt -= m * 1000000L;

        int d = (int) (dt / 10000L);
        dt -= d * 10000L;

        int h = (int) (dt / 100L);
        dt -= h * 100L;

        int min = (int) dt;

        return d + "." + m + "." + y + " " + this.daw.getLeadingZero(h, 2) + ":" + this.daw.getLeadingZero(min, 2)
                + ":00";

    }

    public long getDateTimeFromDatePicker(String date_time)
    {

        if ((date_time == null) || (date_time.length() == 0))
        {
            return 0L;
        }

        // 22.1.2007 18:30:00

        long dt = 0;

        int idx = date_time.indexOf(" ");

        String date = date_time.substring(0, idx);
        String time = date_time.substring(idx + 1);

        StringTokenizer strtok = new StringTokenizer(date, ".");

        dt += getIntFromString(strtok.nextToken()) * 10000L;
        dt += getIntFromString(strtok.nextToken()) * 1000000L;
        dt += getIntFromString(strtok.nextToken()) * 100000000L;

        strtok = new StringTokenizer(time, ":");

        dt += getIntFromString(strtok.nextToken()) * 100L;
        dt += getIntFromString(strtok.nextToken());

        return dt;

    }

    public String changeCase(String in)
    {

        StringTokenizer stok = new StringTokenizer(in, " ");

        boolean first = true;
        String out = "";

        while (stok.hasMoreTokens())
        {

            if (!first)
            {
                out += " ";
            }

            // String tmp = stok.nextToken();

            out += changeCaseWord(stok.nextToken());

            first = false;
        }

        return out;

    }

    public String changeCaseWord(String in)
    {

        String t = "";

        t = in.substring(0, 1).toUpperCase();
        t += in.substring(1).toLowerCase();

        return t;

    }

    public void showByte(byte[] in)
    {

        for (int i = 0; i < in.length; i++)
        {
            System.out.println((char) in[i] + " " + in[i]);
        }

    }

    public void debugOut(String source, Exception ex)
    {

        this.m_errorCode = 1;
        this.m_errorDesc = ex.getMessage();

        if (debug)
            System.out.println("  " + source + "::Exception: " + ex);

        if (debug)
            ex.printStackTrace();

    }

}
