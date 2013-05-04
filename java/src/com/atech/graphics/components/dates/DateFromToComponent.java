package com.atech.graphics.components.dates;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.atech.graphics.components.DateComponent;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccess;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;


/**
 * This file is part of ATech Tools library.
 * 
 * DateFromToComponent - Date From To Component (has predefined entry and from and to date) 
 * Copyright (C) 2013 Andy (Aleksander) Rozman (Atech-Software)
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

public class DateFromToComponent extends JPanel implements /*ChangeListener,*/ ItemListener
{

    private static final long serialVersionUID = 1594106831030686885L;
    int component_width = 315;
    int component_height = 95;

    I18nControlAbstract ic;
    ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
    String m_note;    
    String action_command;
    
    ATDataAccessAbstract m_da;
    
    DateComponent dc_till, dc_from;
    @SuppressWarnings("rawtypes")
    JComboBox cmb_predefined;    
    
    /**
     * Orientation - Vertical
     */
    public static int ORIENTATION_VERTICAL = 1;
    
    /**
     * Orientation - Horizontal
     */
    public static int ORIENTATION_HORIZONTAL = 2;
    
    boolean prefefined_same_line = false;
    int orientation = ORIENTATION_VERTICAL;
    
    
    /**
     * Constructor
     * 
     * @param da 
     */
    public DateFromToComponent(ATDataAccessAbstract da)
    {
        this(1800, 5, da, ORIENTATION_VERTICAL, false, 0, 0);
        System.out.println("Minimal Constructor");
        /*super();
        m_da = da;
        this.ic = da.getI18nControlInstance();
        //initMonths();

        initComponent(1800, 5);*/
    }

    /**
     * Constructor
     * 
     * @param lower_year
     * @param da
     * @param orientation_ 
     * @param prefefined_same_line_ 
     * @param label_gap_ 
     * @param component_gap_ 
     */
    public DateFromToComponent( 
            ATDataAccessAbstract da, 
            int orientation_, boolean prefefined_same_line_,
            int label_gap_, int component_gap_)
    {
        this(1970, 2, 
                 da, 
                orientation_, prefefined_same_line_,
                label_gap_, component_gap_);

    }    
    
    
    /**
     * Constructor
     * 
     * @param lower_year
     * @param higher_year_diff
     * @param da 
     * @param orientation_ 
     * @param prefefined_same_line_ 
     * @param label_gap_ 
     * @param component_gap_ 
     * @param ic
     */
    public DateFromToComponent(int lower_year, int higher_year_diff, 
            ATDataAccessAbstract da, 
            int orientation_, boolean prefefined_same_line_,
            int label_gap_, int component_gap_)
    {
        super();

        //System.out.println("Full Constructor");
        
        m_da = da;
        this.ic = da.getI18nControlInstance();
        //initMonths();
        
        this.orientation = orientation_;
        
        // ignored in vertical
        this.prefefined_same_line = prefefined_same_line_;
        
        this.label_gap = label_gap_;
        this.component_gap = component_gap_;
        ATSwingUtils.initLibrary();
        
        if (orientation == ORIENTATION_VERTICAL)
        {
            this.component_width += this.label_gap;
            this.component_height += (this.component_gap *2);
            initComponentVertical(lower_year, higher_year_diff);
        }
        else
        {
            // FIXME
            initComponentHorizontal(lower_year, higher_year_diff);
        } 
    }

    
    
    int label_gap = 0;
    int component_gap = 0;
    
    
    
    private void initComponentVertical(int lower_year, int higher_year_diff)
    {
        System.out.println(component_width + ", " + component_height + ", Vertical");

        this.setSize(component_width, component_height);
        //this.setBackground(Color.cyan);
        this.setLayout(null);

        ATSwingUtils.getLabel(ic.getMessage("DTCMP_PREDEFINED") + ":", 5, 5, 110, 25, this, ATSwingUtils.FONT_NORMAL_BOLD);
        
        cmb_predefined = ATSwingUtils.getComboBox(getPredefinedFilterValues(), 105+label_gap, 3, 205, 25, this, ATSwingUtils.FONT_NORMAL);
        cmb_predefined.addItemListener(this); // 5
        
        ATSwingUtils.getLabel(ic.getMessage("DTCMP_FROM") + ":", 5, 35 + component_gap, 100, 25, this, ATSwingUtils.FONT_NORMAL_BOLD);

        dc_from = new DateComponent(1800, 5, m_da);
        dc_from.setBounds(105+label_gap, 35+ component_gap, 120, 25);
        dc_from.setEnabled(false);
        //dc_from.setForeground(Color.black);
        this.add(dc_from);
        
        ATSwingUtils.getLabel(ic.getMessage("DTCMP_TILL") + ":", 5, 65+ (2*component_gap), 150, 25, this, ATSwingUtils.FONT_NORMAL_BOLD);
        
        dc_till = new DateComponent(1800, 5, m_da);
        dc_till.setBounds(105+label_gap, 65+ (2*component_gap), 120, 25);
        dc_till.setEnabled(false);
        this.add(dc_till);
        
        this.cmb_predefined.setSelectedIndex(0);
        
        //System.out.println("Init success");


        this.dc_from.setDate(gc_test);
        this.dc_till.setDate(gc_test);
        
        /*
        Random generator2 = new Random( 19580427 );
        this.object_id = generator2.nextLong();
        */
    }
    
    
    private void initComponentHorizontal(int lower_year, int higher_year_diff)
    {
        // FIXME NOT IMPLEMENTED YET
        
        System.out.println(component_width + ", " + component_height + ", Horizontal");

        this.setSize(component_width, component_height);
        this.setBackground(Color.cyan);
        this.setLayout(null);

        ATSwingUtils.getLabel(ic.getMessage("DTCMP_PREDEFINED") + ":", 5, 5, 110, 25, this, ATSwingUtils.FONT_NORMAL_BOLD);
        
        cmb_predefined = ATSwingUtils.getComboBox(getPredefinedFilterValues(), 
                                                  103+label_gap, 5, 205, 25, this, ATSwingUtils.FONT_NORMAL); // 105
        
        ATSwingUtils.getLabel(ic.getMessage("DTCMP_FROM") + ":", 5, 35, 100, 25, this, ATSwingUtils.FONT_NORMAL_BOLD);

        dc_from = new DateComponent(1800, 5, m_da);
        dc_from.setBounds(105+label_gap, 35, 120, 25);
        dc_from.setEnabled(false);
        this.add(dc_from);
        
        ATSwingUtils.getLabel(ic.getMessage("DTCMP_TILL") + ":", 5, 65, 150, 25, this, ATSwingUtils.FONT_NORMAL_BOLD);
        
        dc_till = new DateComponent(1800, 5, m_da);
        dc_till.setBounds(105+label_gap, 65, 120, 25);
        dc_till.setEnabled(false);
        this.add(dc_till);
        
        //System.out.println("Init success");
        
        this.cmb_predefined.setSelectedIndex(1);
        this.cmb_predefined.setSelectedIndex(0);
        
    }

    private String[] getPredefinedFilterValues()
    {
        String[] cmb_data = { ic.getMessage("DTCMP_FILTER_TODAY"),
                              ic.getMessage("DTCMP_FILTER_YESTERDAY"),
                              ic.getMessage("DTCMP_FILTER_THIS_WEEK"),
                              ic.getMessage("DTCMP_FILTER_LAST_WEEK"),
                              ic.getMessage("DTCMP_FILTER_PREVIOUS_WEEK"),
                              ic.getMessage("DTCMP_FILTER_LAST_2_WEEKS"),
                              ic.getMessage("DTCMP_FILTER_THIS_MONTH"), 
                              ic.getMessage("DTCMP_FILTER_LAST_MONTH"), 
                              ic.getMessage("DTCMP_FILTER_PREVIOUS_MONTH"),
                              ic.getMessage("DTCMP_FILTER_TWO_MONTHS"),
                              ic.getMessage("DTCMP_FILTER_LAST_TWO_MONTHS"),
                              ic.getMessage("DTCMP_FILTER_LAST_HALF_YEAR"), 
                              ic.getMessage("DTCMP_FILTER_THIS_YEAR"), 
                              ic.getMessage("DTCMP_FILTER_LAST_YEAR"), 
                              ic.getMessage("DTCMP_FILTER_PREVIOUS_YEAR"),
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
                              ic.getMessage("DECEMBER"), 
                              ic.getMessage("DTCMP_FILTER_CUSTOM") };
        return cmb_data;
    }
    
    
    
    
    
    private void cbPredefined_SelectedIndexChanged() //object sender, EventArgs e)
    {
        GregorianCalendar now = new GregorianCalendar();
        now.setTimeInMillis(System.currentTimeMillis());
        GregorianCalendar from = new GregorianCalendar();
        GregorianCalendar to = new GregorianCalendar();

        String pred_item = this.cmb_predefined.getSelectedItem().toString();
        
        System.out.println(pred_item);
        
        
        if (pred_item.equals(ic.getMessage("DTCMP_FILTER_TODAY")))
        {
            //System.out.println("Today");
            from = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_YESTERDAY")))
        {
            //System.out.println("Yesterday");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.DAY_OF_YEAR, -1);
            to = from;
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_THIS_WEEK")))
        {
            //System.out.println("This Week");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            
            GregorianCalendar[] gcs = determineByWeek(to, 1, 1);
            
            from = gcs[0];
            to = gcs[1];
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_LAST_WEEK")))
        {
            //System.out.println("Last Week");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.WEEK_OF_YEAR, -1);
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_PREVIOUS_WEEK")))
        {
            //System.out.println("Prev. Week");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            to.add(Calendar.WEEK_OF_YEAR, -1);
            
            GregorianCalendar[] gcs = determineByWeek(to, 1, 1);
            
            from = gcs[0];
            to = gcs[1];
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_LAST_2_WEEKS")))
        {
            //System.out.println("Last 2 weeks");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.WEEK_OF_YEAR, -2);
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_THIS_MONTH")))
        {
            //System.out.println("This Month");
            from = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), this.getDaysInMonth(now.get(Calendar.MONTH), now.get(Calendar.YEAR)));
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_LAST_MONTH")))
        {
            //System.out.println("Last Month");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.MONTH, -1);
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_PREVIOUS_MONTH")))
        {
//            System.out.println("Prev. Months");
            from = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from.add(Calendar.MONTH, -1);
            from.set(Calendar.DAY_OF_MONTH, 1);
            
            to = (GregorianCalendar)from.clone();
            to.set(Calendar.DAY_OF_MONTH, this.getDaysInMonth(to.get(Calendar.MONTH), to.get(Calendar.YEAR)));
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_TWO_MONTHS")))
        {
            //System.out.println("Two Months");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.MONTH, -2);
            from.set(Calendar.DAY_OF_MONTH, 1);
            
            to.set(Calendar.DAY_OF_MONTH, this.getDaysInMonth(to.get(Calendar.MONTH), to.get(Calendar.YEAR)));
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_LAST_TWO_MONTHS")))
        {
            //System.out.println("Last 2 Months");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.MONTH, -2);
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_LAST_HALF_YEAR")))
        {
            //System.out.println("Last Half Year");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.MONTH, -6);
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_THIS_YEAR")))
        {
            //System.out.println("This Year");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 0, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 11, 31);
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_LAST_YEAR")))
        {
            //System.out.println("Last Year");
            to = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            from = (GregorianCalendar)to.clone();
            from.add(Calendar.YEAR, -1);
        }
        else if (pred_item.equals(ic.getMessage("DTCMP_FILTER_PREVIOUS_YEAR")))
        {
            //System.out.println("Prev. Year");
            from = new GregorianCalendar(now.get(Calendar.YEAR)-1, 0, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR)-1, 11, 31);
        }
        else if (pred_item.equals(ic.getMessage("JANUARY")))
        {
            //System.out.println("Jan");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 0, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 0, 31);
        }
        else if (pred_item.equals(ic.getMessage("FEBRUARY")))
        {
            //System.out.println("Feb");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 1, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 1, this.getDaysInMonth(1, now.get(Calendar.YEAR)));
        }
        else if (pred_item.equals(ic.getMessage("MARCH")))
        {
            //System.out.println("Mar");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 2, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 2, 31);
        }
        else if (pred_item.equals(ic.getMessage("APRIL")))
        {
            //System.out.println("Apr");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 3, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 3, 30);
        }
        else if (pred_item.equals(ic.getMessage("MAY")))
        {
            //System.out.println("May");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 4, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 4, 31);
        }
        else if (pred_item.equals(ic.getMessage("JUNE")))
        {
            //System.out.println("Jun");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 5, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 5, 30);
        }
        else if (pred_item.equals(ic.getMessage("JULY")))
        {
            //System.out.println("Jul");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 6, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 6, 31);
        }
        else if (pred_item.equals(ic.getMessage("AUGUST")))
        {
            //System.out.println("Aug");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 7, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 7, 31);
        }
        else if (pred_item.equals(ic.getMessage("SEPTEMBER")))
        {
            //System.out.println("Sep");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 8, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 8, 30);
        }
        else if (pred_item.equals(ic.getMessage("OCTOBER")))
        {
            //System.out.println("oct");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 9, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 9, 31);
        }
        else if (pred_item.equals(ic.getMessage("NOVEMBER")))
        {
            //System.out.println("Nov");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 10, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 10, 30);
        }
        else if (pred_item.equals(ic.getMessage("DECEMBER")))
        {
            //System.out.println("Dec");
            from = new GregorianCalendar(now.get(Calendar.YEAR), 11, 1);
            to = new GregorianCalendar(now.get(Calendar.YEAR), 11, 31);
        }
        else
        {
            if (!pred_item.equals(ic.getMessage("DTCMP_FILTER_CUSTOM")))
            {
                System.out.println("Unknown");
                return;
            }
        }
            

        
        
        if (pred_item.equals(ic.getMessage("DTCMP_FILTER_CUSTOM")))
        {
            this.dc_from.setEnabled(true);
            this.dc_till.setEnabled(true);
        }
        else
        {
            this.dc_from.setEnabled(false);
            this.dc_from.setDate(from);
            this.dc_till.setEnabled(false);
            this.dc_till.setDate(to);
        }
        
        //this.cbPredefined_SelectedIndexChanged();
/*
        in_change = true;

        this.deFrom.EditValue = from;
        this.deTill.EditValue = to;

        in_change = false;

        OnChanged(EventArgs.Empty);
*/
    }
    
    
    
    private GregorianCalendar[] determineByWeek(GregorianCalendar gc, int weekddd, int difference)
    {
        GregorianCalendar[] gc_out = new GregorianCalendar[2];
        
        int week = gc.get(Calendar.WEEK_OF_YEAR);
        
        gc.add(Calendar.DAY_OF_YEAR, -9);
        boolean begin = false;
        
        //GregorianCalendar gc_end = null;
        
        for(int i=0; i<18; i++)
        {
            gc.add(Calendar.DAY_OF_YEAR, 1);
            
            if ((gc.get(Calendar.WEEK_OF_YEAR)==week)) 
            {
                if (!begin)
                {
                  begin = true;
                  gc_out[0] = (GregorianCalendar)gc.clone();
                }
                /*else
                {
                    gc_end = (GregorianCalendar)gc.clone();
                }*/
                
            }
            else
            {
                if (begin)
                {
                    gc_out[1] = (GregorianCalendar)gc.clone();
                    gc_out[1].add(Calendar.DAY_OF_YEAR, -1);
                    break;
                }
            }
        }
        
        //System.out.println("GcOut: " + gc_out);
        
        //System.out.println("GcOut 1: " + gc_out[0]);
        //System.out.println("GcOut 2: " + gc_out[1]);
        
        return gc_out;
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
/*    public void initMonths()
    {
        if (months == null)
        {
            String m[] = { ic.getMessage("JANUARY"), ic.getMessage("FEBRUARY"), ic.getMessage("MARCH"),
                          ic.getMessage("APRIL"), ic.getMessage("MAY"), ic.getMessage("JUNE"), ic.getMessage("JULY"),
                          ic.getMessage("AUGUST"), ic.getMessage("SEPTEMBER"), ic.getMessage("OCTOBER"),
                          ic.getMessage("NOVEMBER"), ic.getMessage("DECEMBER") };

            months = m;
        }


    }*/

    /**
     * Add Action Listener
     * 
     * @param list
     */
    /*public void addActionListener(ActionListener list)
    {
        this.listeners.add(list);
    }*/

    /**
     * Set Date Interval
     * 
     * @param input
     */
    public void setDateInterval(int input)
    {
        /*
        if (input == DATE_FULL)
            year.setVisible(true);
        else
            year.setVisible(false);
            */
    }


    
    static int days_months[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    static GregorianCalendar gc_test = new GregorianCalendar();

    
    /**
     * Get Days in Month
     * 
     * @param month
     * @param year
     * @return
     */
    public int getDaysInMonth(int month, int year)
    {
        if (month != 1)
        {
            return days_months[month];
        }
        else
        {
            if (gc_test.isLeapYear(year))
                return 29;
            else
                return 28;
        }
        
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
        /*
        day.setEnabled(isEnabled);
        month.setEnabled(isEnabled);
        year.setEnabled(isEnabled);*/
    }

    
    /**
     * Get From Date
     * 
     * @return
     */
    public int getFromDate()
    {
        return this.dc_from.getDate();
    }
    
    /**
     * Get From Date Object (Gregorian Calendar)
     * @return
     */
    public GregorianCalendar getFromDateObject()
    {
        return this.dc_from.getDateObject();
    }
    

    
    /**
     * Get To Date 
     * 
     * @return
     */
    public int getTillDate()
    {
        return this.dc_till.getDate();
    }
    
    /**
     * Get To Date Object (Gregorian Calendar)
     * 
     * @return
     */
    public GregorianCalendar getTillDateObject()
    {
        return this.dc_till.getDateObject();
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
        
        JFrame dialog = new JFrame();
        
        dialog.setBounds(200, 200, 400, 300);
        dialog.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 400, 200);
        //panel.setBackground(Color.red);
        panel.setLayout(null);

        
        
        //this(1800, 5, da, ORIENTATION_VERTICAL, false, 0, 0);

        
        // 0, 0
        panel.add(new DateFromToComponent(1800, 5, ATDataAccess.getInstance(), ORIENTATION_VERTICAL, false, 50, 25 ));
        
        
        
        
        dialog.getContentPane().add(panel, null);
        //dialog.add(panel, null);
        
        dialog.setVisible(true);
        
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
/*    public void stateChanged(ChangeEvent e)
    {

        // System.out.println("DateComponent date changed");
        if (listeners.size() == 0)
            return;

        ChangeEvent ae = new ChangeEvent(this);

        for (int i = 0; i < this.listeners.size(); i++)
        {
            ChangeListener al = (ChangeListener) listeners.get(i);
            al.stateChanged(ae);
        }

    }
*/
    
    /**
     * Add Change Listener
     * 
     * @param clin
     */
   /*public void addChangeListener(ChangeListener clin)
    {
        this.listeners.add(clin);
    }
    

    public boolean equals(Object o)
    {
        if (o instanceof DateComponent)
        {
            DateComponent dc = (DateComponent)o;
            return dc.object_id == this.object_id;
        }
        else
            return false;
    }*/

    
    

/*
    public void stateChanged(ChangeEvent e)
    {
        this.getDate();
        
        ActionEvent ae = new ActionEvent(this, (int) serialVersionUID, this.action_command);
        ae.setSource(this);
        notifyListeners(ae);
        
    }
  */  
    
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

    /*
    public void stateChanged(ChangeEvent e)
    {
        //  Auto-generated method stub
        
    }*/
    
    


    long lastchange = 0;
    
    
    public void itemStateChanged(ItemEvent e)
    {
        if (lastchange == 0)
        {
//            lastchange = System.currentTimeMillis();
        }
        else
        {
            if ((lastchange+1000) > System.currentTimeMillis())
            {
                return;
            }
        }
        
        lastchange = System.currentTimeMillis();
        cbPredefined_SelectedIndexChanged();
    }
    
}
