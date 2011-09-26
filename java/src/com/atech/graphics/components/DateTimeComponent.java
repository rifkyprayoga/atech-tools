package com.atech.graphics.components;

import java.awt.Color;
import java.awt.Font;
import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.data.ATechDate;

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


public class DateTimeComponent extends JPanel
{

    private static final long serialVersionUID = -7550569466676531315L;
    
    /**
     * The ic.
     */
    I18nControlAbstract ic = null;

    /**
     * The component_width.
     */
    int component_width = 205; //188;
    
    
    int component_width_date = 205;
    
    int component_width_time = 95;
    
    
    
    /**
     * The component_height.
     */
    int component_height = 25;

/*
    int component_width = 188;
    int component_height = 25;
*/

    /**
 * The year.
 */
JSpinner day, month, year;
    
    /**
     * The second.
     */
    JSpinner hour, minute, second;

    //public static String months[];


    /**
     * The Constant ALIGN_HORIZONTAL.
     */
    public static final int ALIGN_HORIZONTAL = 1;
    
    /**
     * The Constant ALIGN_VERTICAL.
     */
    public static final int ALIGN_VERTICAL   = 2;

    /**
     * The m_gap.
     */
    public static int m_gap = 30;

    
    /**
     * The Constant TIME_MAXIMAL_MINUTE.
     */
    public static final int TIME_MAXIMAL_MINUTE = 1;
    
    /**
     * The Constant TIME_MAXIMAL_SECOND.
     */
    public static final int TIME_MAXIMAL_SECOND = 2;
    
    /**
     * The m_da.
     */
    ATDataAccessAbstract m_da;
    
    /**
     * The m_time_type.
     */
    public int m_time_type = TIME_MAXIMAL_MINUTE;

    /**
     * Instantiates a new date time component.
     * 
     * @param da the da
     */
    public DateTimeComponent(ATDataAccessAbstract da)
    {
        //this(1800,5,da, ALIGN_HORIZONTAL, m_gap);
        this(da.getStartYear(), 5, da, ALIGN_HORIZONTAL, m_gap, DateTimeComponent.TIME_MAXIMAL_MINUTE);
    }

    /**
     * Instantiates a new date time component.
     * 
     * @param da the da
     * @param time_type the time_type
     */
    public DateTimeComponent(ATDataAccessAbstract da, int time_type)
    {
        //this(1800,5,da, ALIGN_HORIZONTAL, m_gap);
        this(da.getStartYear(),5,da,ALIGN_HORIZONTAL, m_gap, time_type );
    }
    
    
    


    
    /**
     * Instantiates a new date time component.
     * 
     * @param da the da
     * @param type_align the type_align
     * @param gap the gap
     */
    public DateTimeComponent(ATDataAccessAbstract da, int type_align, int gap)
    {
        this(da.getStartYear(),5, da, type_align, gap, DateTimeComponent.TIME_MAXIMAL_MINUTE);
    }
    
    

    /**
     * Instantiates a new date time component.
     * 
     * @param da the da
     * @param type_align the type_align
     * @param gap the gap
     * @param time_type the time_type
     */
    public DateTimeComponent(ATDataAccessAbstract da, int type_align, int gap, int time_type)
    {
        //this(1800,5,da, type_align, gap);
        this(da.getStartYear(),5, da, type_align, gap, time_type);
    }


    
    /**
     * Instantiates a new date time component.
     * 
     * @param lower_year the lower_year
     * @param da the da
     * @param type_align the type_align
     * @param gap the gap
     */
    public DateTimeComponent(int lower_year, ATDataAccessAbstract da, int type_align, int gap)
    {
        this(lower_year,5, da, type_align, gap, DateTimeComponent.TIME_MAXIMAL_MINUTE);
    }
    
    
    
    /**
     * Instantiates a new date time component.
     * 
     * @param lower_year the lower_year
     * @param da the da
     * @param type_align the type_align
     * @param gap the gap
     * @param time_type the time_type
     */
    public DateTimeComponent(int lower_year, ATDataAccessAbstract da, int type_align, int gap, int time_type)
    {
        //this(lower_year,5, da, type_align, gap);
        this(lower_year,5, da, type_align, gap, time_type);
    }

    
    
    /**
     * Instantiates a new date time component.
     * 
     * @param lower_year the lower_year
     * @param higher_year_diff the higher_year_diff
     * @param da the da
     * @param type_align the type_align
     * @param gap the gap
     * @param time_type the time_type
     */
    public DateTimeComponent(int lower_year, int higher_year_diff, ATDataAccessAbstract da, int type_align, int gap, int time_type)
    {
        super();
        
        this.m_da = da;
        
        this.ic = da.getI18nControlInstance();
        //m_da = da;
        //initMonths();

        int sec_x, sec_y;
        
        this.m_time_type = time_type;

        if (type_align==DateTimeComponent.ALIGN_HORIZONTAL)
        {
            component_width = component_width_date+component_width_time+gap;
            component_height = 25;

            sec_x = component_width_date+gap;
            sec_y = 0;
        }
        else
        {
            component_width = component_width_date;
            component_height = 25+gap+25;

            sec_x = 0;
            sec_y = 25+gap;
        }


        Font font_normal = new Font("SansSerif", Font.PLAIN, 12);

        this.setSize(component_width,component_height);
        this.setLayout(null);

        SpinnerNumberModel listDaysModel = new SpinnerNumberModel(1,1,31,1);
        day = new JSpinner(listDaysModel);
        day.setFont(font_normal);


        SpinnerListModel listMonthsModel = new SpinnerListModel(m_da.getMonthsArray());

        month = new JSpinner(listMonthsModel);
        month.setFont(font_normal);


        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());
        int yr = gc.get(GregorianCalendar.YEAR);

        SpinnerNumberModel listYearsModel = new SpinnerNumberModel(yr,lower_year,yr+higher_year_diff,1);

        year = new JSpinner(listYearsModel);
        year.setEditor(new JSpinner.NumberEditor(year, "#"));
        year.setFont(font_normal);


        day.setBounds(0, 0, 45, 25);
        month.setBounds(50, 0, 90, 25);
        year.setBounds(145, 0, 60, 25);

        /*
        day.setBounds(0, 0, 40, 25); // 40
        month.setBounds(45, 0, 90, 25); // 40, 90
        year.setBounds(140, 0, 80, 25); // 138
        */
        
        // TIME

        SpinnerNumberModel listHourModel = new SpinnerNumberModel(0,0,23,1);
        hour = new JSpinner(listHourModel);
        hour.setEditor(new JSpinner.NumberEditor(hour, "00"));
        hour.setFont(font_normal);

        SpinnerNumberModel listMinModel = new SpinnerNumberModel(0,0,59,1);
        minute = new JSpinner(listMinModel);
        minute.setFont(font_normal);
        minute.setEditor(new JSpinner.NumberEditor(minute, "00"));

        //System.out.println("Time type: " + this.m_time_type);
        
        if (this.m_time_type==DateTimeComponent.TIME_MAXIMAL_SECOND)
        {
            SpinnerNumberModel listSecModel = new SpinnerNumberModel(0,0,59,1);
            second = new JSpinner(listSecModel);
            second.setFont(font_normal);
            second.setEditor(new JSpinner.NumberEditor(second, "00"));

            hour.setBounds(sec_x, sec_y, 45, 25);
            minute.setBounds(sec_x+50, sec_y, 45, 25);
            second.setBounds(sec_x+100, sec_y, 45, 25);

            this.add(hour, null);
            this.add(minute, null);
            this.add(second, null);

            if (type_align==DateTimeComponent.ALIGN_HORIZONTAL)
            {
                component_width += 50;
                this.setSize(component_width,component_height);
            }
        }
        else
        {
            hour.setBounds(sec_x, sec_y, 45, 25);
            minute.setBounds(sec_x+50, sec_y, 45, 25);

            this.add(hour, null);
            this.add(minute, null);
        }
        

        this.add(day, null);
        this.add(month, null);
        this.add(year, null);

    }

/*
    public void initMonths()
    {
    	if (months==null)
    	{
    	    String m[] = { 
    		ic.getMessage("JANUARY"),
    		ic.getMessage("FEBRUARY"),
    		ic.getMessage("MARCH"),
    		ic.getMessage("APRIL"),
    		ic.getMessage("MAY"),
    		ic.getMessage("JUNE"),
    		ic.getMessage("JULY"),
    		ic.getMessage("AUGUST"),
    		ic.getMessage("SEPTEMBER"),
    		ic.getMessage("OCTOBER"),
    		ic.getMessage("NOVEMBER"),
    		ic.getMessage("DECEMBER")
    		}; 
    
    		months = m;
    	}

    }
*/


    
    
    
    /**
 * Check date time.
 * 
 * @return true, if successful
 */
public boolean checkDateTime()
    {

        int d = ((Number)day.getModel().getValue()).intValue();
        String m = (String)((SpinnerListModel)month.getModel()).getValue();
        int y = ((Number)year.getModel().getValue()).intValue();

        //int h = ((Number)hour.getModel().getValue()).intValue();
        //int min = ((Number)minute.getModel().getValue()).intValue();


        int mo = findMonth(m);


        int days_months[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        GregorianCalendar gc = new GregorianCalendar();

        if (mo!=1)
        {

            if (d>days_months[mo])
            {
                day.setValue(days_months[mo]);
                return false;
                //d = days_months[mo];
            }
        }
        else
        {

            int xx = 28;

            if (gc.isLeapYear(y))
                xx = 29;

            if (d>xx)
            {
                return false;
                //day.setValue(xx);
                //d = xx;
            }

        }

        return true;


/*
        String dat = "";
        dat += y;


        for (int i=0; i<2; i++) 
        {

            String tmp = "";

            if (i==0) 
                tmp = "" + (mo+1);
            else
                tmp = ""+d;

            if (tmp.length()==1) 
                tmp = "0"+tmp;

            dat += tmp;

        }
        


        return Integer.parseInt(dat);
*/

    }



    /**
     * Find month.
     * 
     * @param se the se
     * 
     * @return the int
     */
    public int findMonth(String se)
    {
        String[] ms = m_da.getMonthsArray();

        for (int i=0; i<ms.length; i++)
        {
            if (ms[i].equals(se))
                return i;
        }

        return 0;
    }


    /**
     * Gets the month.
     * 
     * @param val the val
     * 
     * @return the month
     */
    public String getMonth(String val)
    {

        int v = Integer.parseInt(val);

        //String[] ms = m_da.getMonthsArray();
        return m_da.getMonthsArray()[v-1];

    }



    /**
     * Sets the date time.
     * 
     * @param dt the new date time
     */
    public void setDateTime(long dt)
    {

        //long dt = 200510122359l;

        int y=0,m=0,d=0,h=0,min=0;

        if (this.m_time_type==DateTimeComponent.TIME_MAXIMAL_MINUTE)
        {
            if (dt<=0)
            {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(System.currentTimeMillis());
    
                y = gc.get(GregorianCalendar.YEAR);
    
                m = 1;
                d = 1;
    
                h = 0;
                min = 0;
            }
            else
            {
                y = (int)(dt/100000000L);
                dt -= y*100000000L;
    
                m = (int)(dt/1000000L);
                dt -= m*1000000L;
    
                d = (int)(dt/10000L);
                dt -= d*10000L;
    
                h = (int)(dt/100L);
                dt -= h*100L;
    
                min = (int)dt;
            }
            
            year.setValue(y);
            day.setValue(d);
            month.setValue(m_da.getMonthsArray()[m-1]);

            hour.setValue(h);
            minute.setValue(min);
            
            
        }
        else
        {

            int sec = 0;
            
            if (dt<=0)
            {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(System.currentTimeMillis());
    
                y = gc.get(GregorianCalendar.YEAR);
    
                m = 1;
                d = 1;
    
                h = 0;
                min = 0;
            }
            else
            {
                y = (int)(dt/10000000000L);
                dt -= y*10000000000L;
    
                m = (int)(dt/100000000L);
                dt -= m*100000000L;
    
                d = (int)(dt/1000000L);
                dt -= d*1000000L;
    
                h = (int)(dt/10000L);
                dt -= h*10000L;
    
                min = (int)(dt/100L);
                dt -= min*100L;
                
                sec = (int)dt;
            }
            
            year.setValue(y);
            day.setValue(d);
            month.setValue(m_da.getMonthsArray()[m-1]);

            hour.setValue(h);
            minute.setValue(min);
            second.setValue(sec);
        }


//        System.out.println(dt + " year: "+ y +  " month: "+ m + " day: " + d+ "  hour: " + h + " min: " + min);

    }




/*
    public void setDate(int date)
    {
    
//        String dt = ""+date;


        // 20051002

        int y = date/10000;

        int m = date - y*10000;

        m = m/100;

        int d = date - y*10000 - m*100;


        System.out.println(date + " year: "+ y +  " month: "+ m + " day: " + d);


        year.setValue(y);
        day.setValue(d);
        month.setValue(m_da.getMonthsArray()[m-1]);

    }
*/

    /**
     *  Sets the date time as current.
     */
    public void setDateTimeAsCurrent()
    {
        int type = 0;
        
        if (this.m_time_type==DateTimeComponent.TIME_MAXIMAL_MINUTE)
            type = ATechDate.FORMAT_DATE_AND_TIME_MIN;
        else
            type = ATechDate.FORMAT_DATE_AND_TIME_S;
        
        this.setDateTime(ATechDate.getATDateTimeFromGC(new GregorianCalendar(), type));
    }
    
    
    /**
     * Get DateTime (long)
     * 
     * @return
     */
    public long getDateTime()
    {

        if (this.m_time_type==DateTimeComponent.TIME_MAXIMAL_MINUTE)
        {
            int d = ((Number)day.getModel().getValue()).intValue();
            String m = (String)((SpinnerListModel)month.getModel()).getValue();
            int y = ((Number)year.getModel().getValue()).intValue();
    
            int h = ((Number)hour.getModel().getValue()).intValue();
            int min = ((Number)minute.getModel().getValue()).intValue();
    
            int mo = findMonth(m)+1;
    
    
            long out = 0;
    
            out += y*100000000L; 
            out += mo*1000000L;
            out += d*10000L;
            out += h*100L;
            out += min;
    
    
            return out;
        }
        else if (this.m_time_type==DateTimeComponent.TIME_MAXIMAL_SECOND)
        {
            int d = ((Number)day.getModel().getValue()).intValue();
            String m = (String)((SpinnerListModel)month.getModel()).getValue();
            int y = ((Number)year.getModel().getValue()).intValue();
    
            int h = ((Number)hour.getModel().getValue()).intValue();
            int min = ((Number)minute.getModel().getValue()).intValue();
            int sec = ((Number)second.getModel().getValue()).intValue();
            
            int mo = findMonth(m)+1;
    
    
            long out = 0;
    
            out += y*10000000000L; 
            out += mo*100000000L;
            out += d*1000000L;
            out += h*10000L;
            out += min * 100L;
            out += sec;
    
            return out;
        }
        else
        {
            System.out.println("getDateTime:: Unknown type");
            return -1;
        }
        

    }

    
    /**
     * Get DateTime Object (ATechDate)
     * 
     * @return
     */
    public ATechDate getDateTimeObject()
    {
        if (this.m_time_type==DateTimeComponent.TIME_MAXIMAL_MINUTE)
        {
            return new ATechDate(ATechDate.FORMAT_DATE_AND_TIME_MIN, getDateTime());
        }
        else if (this.m_time_type==DateTimeComponent.TIME_MAXIMAL_SECOND)
        {
            return new ATechDate(ATechDate.FORMAT_DATE_AND_TIME_S, getDateTime());
        }
        else
        {
            System.out.println("Unhandled time type: " + this.m_time_type);
            return null;
        }
        
    }
    
    

/*
    public void setTime(int date)
    {
    
        int hours = date/100;

        int minutes = date-(hours*100);
        
        hour.setValue(hours);
        minute.setValue(minutes);

    }
*/

    /**
 * Gets the time.
 * 
 * @return the time
 */
public int getTime()
    {

        int h = ((SpinnerNumberModel)hour.getModel()).getNumber().intValue();
        int m = ((SpinnerNumberModel)minute.getModel()).getNumber().intValue();

        return(h*100) + m;

    }




    /** 
     * setBackground
     */
    public void setBackground(Color bg)
    {
        super.setBackground(bg);
    }


    /** 
     * setEnabled
     */
    public void setEnabled(boolean isEnabled)
    {
        day.setEnabled(isEnabled);
        //day.setEditable(true);
        month.setEnabled(isEnabled);
        year.setEnabled(isEnabled);
        hour.setEnabled(isEnabled);
        minute.setEnabled(isEnabled);
        if (this.second!=null)
            this.second.setEnabled(isEnabled);
    }


    /** 
     * setVisible
     */
    public void setVisible(boolean isEnabled)
    {
        day.setVisible(isEnabled);
        //day.setEditable(true);
        month.setVisible(isEnabled);
        year.setVisible(isEnabled);
        hour.setVisible(isEnabled);
        minute.setVisible(isEnabled);
        if (this.second!=null)
            this.second.setVisible(isEnabled);
    }




    /** 
     * setBounds
     */
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x,y,component_width,component_height);
    }


    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String args[])
    {

        //String dt = "200510122359";
        /*
           long dt = 200510122359L;
       
       
           DateTimeComponent.setDateTime(dt);
          */     

        //System.out.println("Day: " + dt.substring(6,8) + " Month: " + dt.substring(4,6) + " Year: " + dt.substring(0,4));

        //year.setValue(dt.substring(0,4);
        //    day.setValue(dt.substring(6,8));
        //    month.setValue(getMonth(dt.substring(4,6));



    }


    /**
     * Sets the date time type.
     * 
     * @param type the new date time type
     */
    public void setDateTimeType(int type)
    {
        m_time_type = type;
    }


}
