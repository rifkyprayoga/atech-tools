package com.atech.graphics.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * 
 * DateComponent - Date Component Copyright (C) 2005 Andy (Aleksander) Rozman
 * (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 * 
 */

public class DateComponent extends JPanel implements ChangeListener
{

    private static final long serialVersionUID = -1894748246277058611L;

    private String action_command = null;

    /**
     * The m_command.
     */
    // String m_command = "";

    /**
     * The m_note.
     */
    String m_note = "";

    /**
     * The i18nControl.
     */
    I18nControlAbstract ic = null; // I18nControl.getInstance();

    /**
     * The component_width.
     */
    int component_width = 205; // 182

    /**
     * The component_height.
     */
    int component_height = 25;

    /**
     * The year.
     */
    JSpinner day, month, year;

    /**
     * The m_lower_year.
     */
    int m_lower_year = 1970;

    // DataAccess dataAccess;

    // private static String months[];
    // private ArrayList<ChangeListener> listeners = new
    // ArrayList<ChangeListener>();
    @SuppressWarnings("unused")
    private long object_id = 0L;

    ATDataAccessAbstract m_da;
    ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
    boolean debug = false;

    public static int LEFT_MARGIN = 0;


    /**
     * Constructor
     * 
     * @param da 
     */
    public DateComponent(ATDataAccessAbstract da)
    {
        this(1800, 5, da);
    }


    /**
     * Constructor
     * 
     * @param lower_year
     * @param da
     */
    public DateComponent(int lower_year, ATDataAccessAbstract da)
    {
        this(lower_year, 5, da);
    }


    /**
     * Constructor
     * 
     * @param lower_year
     * @param higher_year_diff
     * @param da 
     * @param ic
     */
    public DateComponent(int lower_year, int higher_year_diff, ATDataAccessAbstract da)
    {
        super();

        m_da = da;
        this.ic = da.getI18nControlInstance();
        // initMonths();

        initComponent(lower_year, higher_year_diff);
    }


    /**
     * Constructor
     * 
     * @param ic
     */
    public DateComponent(I18nControlAbstract ic)
    {
        this(1800, 5, ic);
    }


    /**
     * Constructor
     * 
     * @param lower_year
     * @param ic
     */
    public DateComponent(int lower_year, I18nControlAbstract ic)
    {
        this(lower_year, 5, ic);
    }


    /**
     * Constructor
     * 
     * @param lower_year
     * @param higher_year_diff
     * @param da 
     * @param ic
     */
    public DateComponent(int lower_year, int higher_year_diff, I18nControlAbstract ic)
    {
        super();

        this.ic = ic;
        // initMonths();

        initComponent(lower_year, higher_year_diff);
    }


    private void initComponent(int lower_year, int higher_year_diff)
    {
        Font font_normal = new Font("SansSerif", Font.PLAIN, 12);

        this.setSize(component_width + LEFT_MARGIN, component_height);
        this.setLayout(null);

        SpinnerNumberModel listDaysModel = new SpinnerNumberModel(1, 1, 31, 1);
        day = new JSpinner(listDaysModel);
        day.addChangeListener(this);
        day.setFont(font_normal);

        // SpinnerListModel listMonthsModel = new SpinnerListModel(months);

        SpinnerListModel listMonthsModel = new SpinnerListModel(m_da.getMonthsArray());

        month = new JSpinner(listMonthsModel);
        month.addChangeListener(this);
        month.setFont(font_normal);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());
        int yr = gc.get(Calendar.YEAR);

        SpinnerNumberModel listYearsModel = new SpinnerNumberModel(yr, lower_year, yr + higher_year_diff, 1);

        year = new JSpinner(listYearsModel);
        year.setEditor(new JSpinner.NumberEditor(year, "#"));
        year.setFont(font_normal);
        year.addChangeListener(this);

        day.setBounds(LEFT_MARGIN + 0, 0, 45, 25);
        month.setBounds(LEFT_MARGIN + 50, 0, 90, 25);
        year.setBounds(LEFT_MARGIN + 145, 0, 60, 25);

        /*
         * day.setBounds(0, 0, 40, 25); // 40
         * month.setBounds(45, 0, 90, 25); // 40, 90
         * year.setBounds(140, 0, 80, 25); // 138
         */
        this.add(day, null);
        this.add(month, null);
        this.add(year, null);

        Random generator2 = new Random(19580427);
        this.object_id = generator2.nextLong();

    }

    /**
     * Date: Day and Month only
     */
    public static final int DATE_DAY_AND_MONTH_ONLY = 1;

    /**
     * Date: Full
     */
    public static final int DATE_FULL = 0;


    /**
     * Init Months
     */
    /*
     * public void initMonths()
     * {
     * if (months == null)
     * {
     * String m[] = { i18nControl.getMessage("JANUARY"),
     * i18nControl.getMessage("FEBRUARY"),
     * i18nControl.getMessage("MARCH"),
     * i18nControl.getMessage("APRIL"), i18nControl.getMessage("MAY"),
     * i18nControl.getMessage("JUNE"),
     * i18nControl.getMessage("JULY"),
     * i18nControl.getMessage("AUGUST"), i18nControl.getMessage("SEPTEMBER"),
     * i18nControl.getMessage("OCTOBER"),
     * i18nControl.getMessage("NOVEMBER"), i18nControl.getMessage("DECEMBER") };
     * months = m;
     * }
     * }
     */

    /**
     * Add Action Listener
     * 
     * @param list
     */
    /*
     * public void addActionListener(ActionListener list)
     * {
     * this.listeners.add(list);
     * }
     */

    /**
     * Set Date Interval
     * 
     * @param input
     */
    public void setDateInterval(int input)
    {
        if (input == DATE_FULL)
        {
            year.setVisible(true);
        }
        else
        {
            year.setVisible(false);
        }
    }


    /**
     * Check Date
     * 
     * @return
     */
    public int checkDate()
    {

        int d = ((Number) day.getModel().getValue()).intValue();
        String m = (String) ((SpinnerListModel) month.getModel()).getValue();

        // String m = (String)((SpinnerListModel)month.getModel()).getValue();

        int y = ((Number) year.getModel().getValue()).intValue();

        // System.out.println("setDate: y=" + y + ",m=" + m + ",d=" + d);

        int mo = findMonth(m);

        int days_months[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        GregorianCalendar gc = new GregorianCalendar();

        if (mo != 1)
        {

            if (d > days_months[mo])
            {
                day.setValue(days_months[mo]);
                d = days_months[mo];
            }
        }
        else
        {

            int xx = 28;

            if (gc.isLeapYear(y))
            {
                xx = 29;
            }

            if (d > xx)
            {
                day.setValue(xx);
                d = xx;
            }

        }

        String dat = "";

        if (year.isVisible())
        {
            dat += y;
        }

        for (int i = 0; i < 2; i++)
        {

            String tmp = "";

            if (i == 0)
            {
                tmp = "" + (mo + 1);
            }
            else
            {
                tmp = "" + d;
            }

            if (tmp.length() == 1)
            {
                tmp = "0" + tmp;
            }

            dat += tmp;

        }

        return Integer.parseInt(dat);

    }


    private int findMonth(String se)
    {
        String[] ms = m_da.getMonthsArray();

        // System.out.println("findMonth [" + se + "]");

        for (int i = 0; i < ms.length; i++)
        {
            if (ms[i].equals(se))
                return i;
        }

        return 0;
    }


    /**
     * Get Month
     * 
     * @param val
     * @return
     */
    public String getMonth(String val)
    {
        int v = Integer.parseInt(val);
        return m_da.getMonthsArray()[v - 1];
    }


    /**
     * Set Date
     * 
     * @param date
     */
    public void setDate(int date)
    {

        int y = 0;
        int m = 0;
        int d = 0;

        // String dt = ""+date;

        if (date <= 0)
        {
            GregorianCalendar gc = new GregorianCalendar();
            y = gc.get(Calendar.YEAR);

            m = 0;
            d = 1;
        }
        else
        {
            // 20051002
            y = date / 10000;

            m = date - y * 10000;
            m = m / 100;

            d = date - y * 10000 - m * 100;

            if (m != 0)
            {
                m--;
            }

            if (d == 0)
            {
                d = 1;
            }

            if (y == 0)
            {
                GregorianCalendar gc = new GregorianCalendar();
                y = gc.get(Calendar.YEAR);
            }

        }

        if (debug)
        {
            System.out.println("setDate: y=" + y + ",m=" + m + ",d=" + d);
        }

        year.setValue(y);
        day.setValue(d);
        month.setValue(m_da.getMonthsArray()[m]);

        if (debug)
        {
            System.out.println("setDate: y=" + year.getValue() + ",m=" + month.getValue() + ",d=" + day.getValue());
        }

    }


    /**
     * Set Date
     * 
     * @param gc
     */
    public void setDate(GregorianCalendar gc)
    {
        int y = 0;
        int m = 0;
        int d = 0;

        y = gc.get(Calendar.YEAR);
        d = gc.get(Calendar.DAY_OF_MONTH);
        m = gc.get(Calendar.MONTH);

        year.setValue(y);
        day.setValue(d);
        month.setValue(m_da.getMonthsArray()[m]);

        if (debug)
        {
            System.out.println("setDate: y=" + y + ",m=" + m + ",d=" + d);
        }

        if (debug)
        {
            System.out.println("setDate: y=" + year.getValue() + ",m=" + month.getValue() + ",d=" + day.getValue());
        }

        this.repaint();
    }


    /**
     * Get Date
     * 
     * @return
     */
    public int getDate()
    {
        int d = ((Number) day.getModel().getValue()).intValue();
        String m = (String) ((SpinnerListModel) month.getModel()).getValue();
        int y = ((Number) year.getModel().getValue()).intValue();

        // month.getValue();

        /*
         * try
         * {
         * month.commitEdit();
         * ///month.
         * }
         * catch (ParseException e)
         * {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * }
         */

        // System.out.println("getDate: y=" + y + ",m=" + month.getValue() +
        // ",d=" + d);

        int mo = findMonth(m) + 1;

        // System.out.println("findMonth: " + mo);

        int out = 0;

        out += y * 10000L;
        out += mo * 100L;
        out += d;

        // System.out.println("getDate: " + out);

        return out;

        // return checkDate();
    }


    /**
     * Get Date Object (GregorianCalendar)
     * 
     * @return
     */
    public GregorianCalendar getDateObject()
    {
        checkDate();

        int d = ((Number) day.getModel().getValue()).intValue();
        String m = (String) ((SpinnerListModel) month.getModel()).getValue();
        int y = ((Number) year.getModel().getValue()).intValue();

        int mo = findMonth(m);

        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.set(Calendar.DAY_OF_MONTH, d);
        gc1.set(Calendar.MONTH, mo);
        gc1.set(Calendar.YEAR, y);

        return gc1;

    }


    /** 
     * setBackground
     */
    @Override
    public void setBackground(Color bg)
    {
        super.setBackground(bg);
    }


    /** 
     * setEnabled
     */
    @Override
    public void setEnabled(boolean isEnabled)
    {
        day.setEnabled(isEnabled);
        month.setEnabled(isEnabled);
        year.setEnabled(isEnabled);

        /*
         * if (isEnabled)
         * {
         * day.setForeground(Color.black);
         * }
         * else
         * {
         * day.setForeground(Color.red);
         * }
         */

    }


    public void setEditable(boolean isEnabled)
    {
        // this.day.setE
        // JSpinner
        // day.se
    }


    /** 
     * setBounds
     */
    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, component_width + LEFT_MARGIN, component_height);
    }


    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String args[])
    {

        String dt = "20051012";

        System.out.println("Day: " + dt.substring(6, 8) + " Month: " + dt.substring(4, 6) + " Year: "
                + dt.substring(0, 4));

        // year.setValue(dt.substring(0,4);
        // day.setValue(dt.substring(6,8));
        // month.setValue(getMonth(dt.substring(4,6));

    }


    /**
     * Sets the note.
     * 
     * @param note the new note
     */
    public void setNote(String note)
    {
        m_note = note;
    }


    /**
     * Gets the note.
     * 
     * @return the note
     */
    public String getNote()
    {
        return m_note;
    }


    /**
     * Add Action Listener
     * 
     * @param al
     */
    public void addActionListener(ActionListener al)
    {
        this.listeners.add(al);
    }


    /**
     * Remove Action Listener
     * 
     * @param al
     */
    public void removeActionListener(ActionListener al)
    {
        this.listeners.remove(al);
    }


    /**
     * Invoked when the target of the listener has changed its state.
     * 
     * @param e
     *            a ChangeEvent object
     */
    /*
     * public void stateChanged(ChangeEvent e)
     * {
     * // System.out.println("DateComponent date changed");
     * if (listeners.size() == 0)
     * return;
     * ChangeEvent ae = new ChangeEvent(this);
     * for (int i = 0; i < this.listeners.size(); i++)
     * {
     * ChangeListener al = (ChangeListener) listeners.get(i);
     * al.stateChanged(ae);
     * }
     * }
     */

    /**
     * Add Change Listener
     * 
     * @param clin
     */
    /*
     * public void addChangeListener(ChangeListener clin)
     * {
     * this.listeners.add(clin);
     * }
     * public boolean equals(Object o)
     * {
     * if (o instanceof DateComponent)
     * {
     * DateComponent dc = (DateComponent)o;
     * return dc.object_id == this.object_id;
     * }
     * else
     * return false;
     * }
     */

    public void stateChanged(ChangeEvent e)
    {
        this.getDate();

        ActionEvent ae = new ActionEvent(this, (int) serialVersionUID, this.action_command);
        ae.setSource(this);
        notifyListeners(ae);

    }


    /**
     * Set Action Command
     * 
     * @param act_command
     */
    public void setActionCommand(String act_command)
    {
        this.action_command = act_command;
    }


    /**
     * Get Action Command
     * 
     * @return
     */
    public String getActionCommand()
    {
        return this.action_command;
    }


    /**
     * Notify Listeners
     * 
     * @param e
     */
    public void notifyListeners(ActionEvent e)
    {
        for (int i = 0; i < listeners.size(); i++)
        {
            ActionListener l = listeners.get(i);
            l.actionPerformed(e);
        }
    }

}
