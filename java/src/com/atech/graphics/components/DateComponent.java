package com.atech.graphics.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atech.i18n.I18nControlAbstract;

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

    private static final long serialVersionUID = 7815993474415035963L;
    
    /**
     * The m_command.
     */
    String m_command = "";
    
    /**
     * The m_note.
     */
    String m_note = "";
    
    /**
     * The ic.
     */
    I18nControlAbstract ic = null; // I18nControl.getInstance();

    /**
     * The component_width.
     */
    int component_width = 188;
    
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

    // DataAccess m_da;

    private static String months[];
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

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
     * @param ic
     */
    public DateComponent(int lower_year, int higher_year_diff, I18nControlAbstract ic)
    {
        super();

        // m_da = da;
        this.ic = ic;
        initMonths();

        Font font_normal = new Font("SansSerif", Font.PLAIN, 12);

        this.setSize(component_width, component_height);
        this.setLayout(null);

        SpinnerNumberModel listDaysModel = new SpinnerNumberModel(1, 1, 31, 1);
        day = new JSpinner(listDaysModel);
        day.addChangeListener(this);
        day.setFont(font_normal);

        SpinnerListModel listMonthsModel = new SpinnerListModel(months);

        month = new JSpinner(listMonthsModel);
        month.addChangeListener(this);
        month.setFont(font_normal);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());
        int yr = gc.get(GregorianCalendar.YEAR);

        SpinnerNumberModel listYearsModel = new SpinnerNumberModel(yr, lower_year, yr + higher_year_diff, 1);

        year = new JSpinner(listYearsModel);
        year.setEditor(new JSpinner.NumberEditor(year, "#"));
        year.setFont(font_normal);
        year.addChangeListener(this);

        day.setBounds(0, 0, 36, 25); // 40
        month.setBounds(40, 0, 85, 25); // 40, 90
        year.setBounds(129, 0, 50, 25); // 138

        this.add(day, null);
        this.add(month, null);
        this.add(year, null);

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
    public void initMonths()
    {
        if (months == null)
        {
            String m[] = { ic.getMessage("JANUARY"), ic.getMessage("FEBRUARY"), ic.getMessage("MARCH"),
                          ic.getMessage("APRIL"), ic.getMessage("MAY"), ic.getMessage("JUNE"), ic.getMessage("JULY"),
                          ic.getMessage("AUGUST"), ic.getMessage("SEPTEMBER"), ic.getMessage("OCTOBER"),
                          ic.getMessage("NOVEMBER"), ic.getMessage("DECEMBER") };

            months = m;
        }

    }

    /**
     * Add Action Listener
     * 
     * @param list
     */
    public void addActionListener(ActionListener list)
    {
        this.listeners.add(list);
    }

    /**
     * Set Date Interval
     * 
     * @param input
     */
    public void setDateInterval(int input)
    {
        if (input == DATE_FULL)
            year.setVisible(true);
        else
            year.setVisible(false);
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
        int y = ((Number) year.getModel().getValue()).intValue();

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
                xx = 29;

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
                tmp = "" + (mo + 1);
            else
                tmp = "" + d;

            if (tmp.length() == 1)
                tmp = "0" + tmp;

            dat += tmp;

        }

        return Integer.parseInt(dat);

    }

    private int findMonth(String se)
    {
        // String[] ms = m_da.getMonthsArray();

        for (int i = 0; i < months.length; i++)
        {
            if (months[i].equals(se))
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
        return months[v - 1];
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
            y = gc.get(GregorianCalendar.YEAR);

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
                m--;

            if (d == 0)
                d = 1;

            if (y == 0)
            {
                GregorianCalendar gc = new GregorianCalendar();
                y = gc.get(GregorianCalendar.YEAR);
            }

        }

        year.setValue(y);
        day.setValue(d);
        month.setValue(months[m]);

    }

    /**
     * Get Date
     * 
     * @return
     */
    public int getDate()
    {
        return checkDate();
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
        gc1.set(GregorianCalendar.DAY_OF_MONTH, d);
        gc1.set(GregorianCalendar.MONTH, mo);
        gc1.set(GregorianCalendar.YEAR, y);

        return gc1;

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
        month.setEnabled(isEnabled);
        year.setEnabled(isEnabled);
    }

    /** 
     * setBounds
     */
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, component_width, component_height);
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
     * Set Action Command
     * 
     * @param command
     */
    public void setActionCommand(String command)
    {
        m_command = command;
    }

    /**
     * Gets the action command.
     * 
     * @return the action command
     */
    public String getActionCommand()
    {
        return m_command;
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
     * Invoked when the target of the listener has changed its state.
     * 
     * @param e
     *            a ChangeEvent object
     */
    public void stateChanged(ChangeEvent e)
    {

        // System.out.println("DateComponent date changed");
        if (listeners.size() == 0)
            return;

        ActionEvent ae = new ActionEvent(this, 1, m_command);

        for (int i = 0; i < this.listeners.size(); i++)
        {
            ActionListener al = (ActionListener) listeners.get(i);
            al.actionPerformed(ae);
        }

    }

}
