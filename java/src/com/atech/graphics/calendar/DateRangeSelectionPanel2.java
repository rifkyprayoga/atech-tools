package com.atech.graphics.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.atech.graphics.components.DateComponent;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;
import com.atech.utils.data.ATechDate;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DateRangeSelectionPanel - Panel with some options to select Date Range
 *  Copyright (C) 2002  Dieter Schultschik
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
 *  Author:   schultd (taken from ggc project)
 *  Author:   Andy (minor changes)
 */

public class DateRangeSelectionPanel2 extends JPanel implements ItemListener, /*
                                                                               * ChangeListener
                                                                               * ,
                                                                               */ActionListener // ChangeListener
{
    // private JTextField fieldStartDate;
    // private JTextField fieldEndDate;
    // private Date endDate;
    // private Date startDate;

    /**
     * 
     */
    private static final long serialVersionUID = 8709379438186554030L;

    private I18nControlAbstract m_ic; // = I18nControl.getInstance();
    ATDataAccessAbstract m_da;

    JComboBox cb_range;

    // private JSpinner spinnerEnd;
    // private JSpinner spinnerStart;

    private GregorianCalendar gc_end = null;
    private GregorianCalendar gc_start = null;

    // private SpinnerDateModel endSpinnerDateModel = null;
    // private SpinnerDateModel startSpinnerDateModel = null;

    // private int iRadioGroupState = 0;

    DateComponent dt_from, dt_till;

    private String[] range = null; // { "ONE_WEEK", "TWO_WEEKS", "1_MONTH",
                                   // "3_MONTHS", "CUSTOM" };

    private int[] range_map = { DateRangeData.RANGE_ONE_WEEK, DateRangeData.RANGE_TWO_WEEKS,
                               DateRangeData.RANGE_ONE_MONTH, DateRangeData.RANGE_THREE_MONTHS,
                               DateRangeData.RANGE_CUSTOM };

    int current_range = 0;

    // private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Constructor
     * 
     * @param da
     */
    public DateRangeSelectionPanel2(ATDataAccessAbstract da)
    {
        this(da, new GregorianCalendar());
        init();

    }

    /**
     * Constructor
     * 
     * @param da
     * @param flag
     */
    public DateRangeSelectionPanel2(ATDataAccessAbstract da, int selected_range)
    {
        this(da, new GregorianCalendar(), selected_range);
    }

    /**
     * Constructor
     * 
     * @param da
     * @param endDate
     */
    public DateRangeSelectionPanel2(ATDataAccessAbstract da, GregorianCalendar endDate)
    {
        this(da, endDate, null, DateRangeData.RANGE_ONE_MONTH);
    }

    /**
     * Constructor
     * 
     * @param da
     * @param endDate
     * @param flag
     */
    public DateRangeSelectionPanel2(ATDataAccessAbstract da, GregorianCalendar endDate, int selected_range)
    {
        this(da, endDate, null, selected_range);
    }

    /**
     * Constructor
     * 
     * @param da
     * @param endDate
     * @param startDate
     */
    public DateRangeSelectionPanel2(ATDataAccessAbstract da, GregorianCalendar endDate, GregorianCalendar startDate)
    {
        this(da, endDate, startDate, DateRangeData.RANGE_CUSTOM);
    }

    private DateRangeSelectionPanel2(ATDataAccessAbstract da, GregorianCalendar endDate, GregorianCalendar startDate,
            int range)
    {
        super();
        m_da = da;
        m_ic = da.getI18nControlInstance();

        // this.setBackground(Color.blue);

        this.gc_end = endDate;
        this.gc_start = startDate;

        /*
         * endSpinnerDateModel = new SpinnerDateModel();
         * startSpinnerDateModel = new SpinnerDateModel();
         * endSpinnerDateModel.setCalendarField(Calendar.DAY_OF_WEEK);
         * startSpinnerDateModel.setCalendarField(Calendar.DAY_OF_WEEK);
         * if (endDate != null)
         * {
         * this.gc_end = (GregorianCalendar) endDate.clone();
         * this.gc_end.set(Calendar.HOUR_OF_DAY, 0);
         * this.gc_end.set(Calendar.MINUTE, 0);
         * this.gc_end.set(Calendar.SECOND, 0);
         * endSpinnerDateModel.setValue(this.gc_end.getTime());
         * }
         * if (startDate != null)
         * {
         * this.gc_start = (GregorianCalendar) startDate.clone();
         * this.gc_start.set(Calendar.HOUR_OF_DAY, 0);
         * this.gc_start.set(Calendar.MINUTE, 0);
         * this.gc_start.set(Calendar.SECOND, 0);
         * startSpinnerDateModel.setValue(this.gc_start.getTime());
         * }
         */
        // iRadioGroupState = flag;
        this.current_range = range;
        init();
        // setRange(range);
        // calcStartDate();
        // this.setSize(800, 200);
    }

    private void init()
    {
        // System.out.println("Ini inbit");
        range = new String[5];

        range[0] = this.m_ic.getMessage("ONE_WEEK");
        range[1] = this.m_ic.getMessage("TWO_WEEKS");
        range[2] = this.m_ic.getMessage("1_MONTH");
        range[3] = this.m_ic.getMessage("3_MONTHS");
        range[4] = this.m_ic.getMessage("CUSTOM");

        /*
         * JPanel a = new JPanel();
         * a.setLayout(null);
         * a.setBackground(Color.red);
         */
        // a.setSize(200, 200);
        // a.setBounds(0, 0, 800, 100);
        // this.

        ATSwingUtils.getLabel(this.m_ic.getMessage("RANGE") + ":", 10, 21, 75, 25, this, ATSwingUtils.FONT_NORMAL_BOLD);
        // this.
        this.cb_range = new JComboBox(range);
        this.cb_range.setBounds(70, 20, 120, 25);
        this.cb_range.setSelectedIndex(this.current_range);
        this.cb_range.addItemListener(this);
        this.add(this.cb_range);

        setLayout(null); // new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(m_ic.getMessage("DATE_RANGE_SELECTOR")));

        // add(a); //, BorderLayout.CENTER);

        ATSwingUtils.getLabel(this.m_ic.getMessage("DATE_TILL") + ":", 10, 48, 120, 25, this,
            ATSwingUtils.FONT_NORMAL_BOLD);

        this.dt_till = new DateComponent(m_da);
        this.dt_till.setBounds(10, 70, 120, 25);
        this.dt_till.setActionCommand("dt_till");
        this.dt_till.addActionListener(this);
        this.add(this.dt_till);

        ATSwingUtils.getLabel(this.m_ic.getMessage("DATE_FROM") + ":", 10, 95, 120, 25, this,
            ATSwingUtils.FONT_NORMAL_BOLD);

        this.dt_from = new DateComponent(m_da);
        this.dt_from.setBounds(10, 118, 120, 25);
        this.dt_from.setActionCommand("dt_from");
        this.dt_from.addActionListener(this);
        // this.dt_from.addChangeListener(this);
        this.add(this.dt_from);

        // this.getContentPane().add(a);
        /*
         * rbOneWeek.addActionListener(new
         * RadioListener(DateRangeData.RANGE_ONE_WEEK));
         * rbOneMonth.addActionListener(new
         * RadioListener(DateRangeData.RANGE_ONE_MONTH));
         * rbThreeMonths.addActionListener(new
         * RadioListener(DateRangeData.RANGE_THREE_MONTHS));
         * rbCustom.addActionListener(new
         * RadioListener(DateRangeData.RANGE_CUSTOM));
         */

        /*
         * JPanel a = new JPanel();
         * a.setLayout(new GridLayout(0, 1));
         * // Box a = Box.createVerticalBox();
         * a.add(new JLabel(m_ic.getMessage("ENDING_DATE") + ":"));
         * a.add(spinnerEnd = new JSpinner(endSpinnerDateModel));
         * ((JSpinner.DateEditor)
         * spinnerEnd.getEditor()).getFormat().applyPattern("dd.MM.yyyy");
         * a.add(new JLabel(m_ic.getMessage("STARTING_DATE") + ":"));
         * a.add(spinnerStart = new JSpinner(startSpinnerDateModel));
         * ((JSpinner.DateEditor)
         * spinnerStart.getEditor()).getFormat().applyPattern("dd.MM.yyyy");
         * spinnerEnd.addChangeListener(this);
         * spinnerStart.addChangeListener(this);
         * JRadioButton rbOneWeek = new JRadioButton("  " +
         * m_ic.getMessage("1_WEEK"), iRadioGroupState ==
         * DateRangeData.RANGE_ONE_WEEK);
         * rbOneWeek.setIconTextGap(8);
         * // rbOneWeek.se
         * JRadioButton rbOneMonth = new JRadioButton("  " +
         * m_ic.getMessage("1_MONTH"), iRadioGroupState ==
         * DateRangeData.RANGE_ONE_MONTH);
         * JRadioButton rbThreeMonths = new JRadioButton("  " +
         * m_ic.getMessage("3_MONTHS"),
         * iRadioGroupState == DateRangeData.RANGE_THREE_MONTHS);
         * JRadioButton rbCustom = new JRadioButton("  " +
         * m_ic.getMessage("CUSTOM"), iRadioGroupState ==
         * DateRangeData.RANGE_CUSTOM);
         * ButtonGroup group = new ButtonGroup();
         * group.add(rbOneWeek);
         * group.add(rbOneMonth);
         * group.add(rbThreeMonths);
         * group.add(rbCustom);
         * rbOneWeek.addActionListener(new
         * RadioListener(DateRangeData.RANGE_ONE_WEEK));
         * rbOneMonth.addActionListener(new
         * RadioListener(DateRangeData.RANGE_ONE_MONTH));
         * rbThreeMonths.addActionListener(new
         * RadioListener(DateRangeData.RANGE_THREE_MONTHS));
         * rbCustom.addActionListener(new
         * RadioListener(DateRangeData.RANGE_CUSTOM));
         * Box b = Box.createVerticalBox();
         * b.add(rbOneWeek);
         * b.add(rbOneMonth);
         * b.add(rbThreeMonths);
         * b.add(rbCustom);
         * setLayout(new BorderLayout());
         * setBorder(BorderFactory.createTitledBorder(m_ic.getMessage(
         * "DATE_RANGE_SELECTOR")));
         * JPanel c = new JPanel();
         * c.setLayout(new GridLayout());
         * c.add(new JLabel("  "));
         * add(a, BorderLayout.WEST);
         * add(c, BorderLayout.CENTER);
         * add(b, BorderLayout.EAST);
         * if (iRadioGroupState != DateRangeData.RANGE_CUSTOM)
         * spinnerStart.setEnabled(false);
         */
    }

    public void setRange(int range)
    {
        current_range = range;
        this.cb_range.setSelectedIndex(current_range - 1);
        calcDateAndUpdateFields(DATE_CMP_TILL);

        // this.dt_from.setEnabled(this.current_range ==
        // DateRangeData.RANGE_CUSTOM);
    }

    private static final int DATE_CMP_FROM = 1;
    private static final int DATE_CMP_TILL = 2;

    private void calcDateAndUpdateFields(int source)
    {

        System.out.println("calcDateAndUpdateFields: ");

        GregorianCalendar gc_source = null, gc_target = null;

        if (source == DATE_CMP_TILL)
        {
            gc_source = this.getEndCalendar();
        }
        else
        {
            System.out.println("NA");
        }

        // gc_end = this.getEndCalendar();

        // gc_start = (GregorianCalendar) gc_end.clone();

        gc_target = (GregorianCalendar) gc_source.clone();

        switch (current_range)
        {
            case DateRangeData.RANGE_ONE_WEEK:
                gc_target.add(Calendar.WEEK_OF_YEAR, -1);
                break;

            case DateRangeData.RANGE_TWO_WEEKS:
                gc_target.add(Calendar.WEEK_OF_YEAR, -2);
                break;

            case DateRangeData.RANGE_THREE_MONTHS:
                gc_target.add(Calendar.MONTH, -3);
                break;
            case DateRangeData.RANGE_CUSTOM:
                {
                    // GregorianCalendar gc = this.getStartCalendar();

                    if (gc_source.before(gc_target))
                    {
                        gc_target.setTimeInMillis(gc_end.getTimeInMillis());
                    }

                    // gc_target.setTimeInMillis(gc.getTimeInMillis());

                }
                break;

            case DateRangeData.RANGE_ONE_MONTH:
            default:
                gc_target.add(Calendar.MONTH, -1);
                break;

        }

        // System.out.println(gc_start);
        // startSpinnerDateModel.setValue(gc_start.getTime());

        System.out.println("calcDateAndUpdateFields:set ");

        // this.dt_from.setEnabled(true);
        // this.dt_from.setDate(gc_start);

        // this.dt_from.setEnabled(this.current_range ==
        // DateRangeData.RANGE_CUSTOM);

        if (source == DATE_CMP_TILL)
        {
            gc_start = gc_target;
            this.dt_from.setDate(gc_start);
        }
        else
        {
            System.out.println("NA");
        }

    }

    /*
     * private void calcDateAndUpdateFields()
     * {
     * gc_end = this.getEndCalendar();
     * calcStartDate();
     * }
     */

    /*
     * private void calcStartDate()
     * {
     * // if (gc_start==null)
     * gc_start = (GregorianCalendar) gc_end.clone();
     * switch (iRadioGroupState)
     * {
     * case DateRangeData.RANGE_ONE_WEEK:
     * gc_start.add(Calendar.WEEK_OF_YEAR, -1);
     * break;
     * case DateRangeData.RANGE_THREE_MONTHS:
     * gc_start.add(Calendar.MONTH, -3);
     * break;
     * case DateRangeData.RANGE_CUSTOM:
     * {
     * GregorianCalendar gc = this.getStartCalendar();
     * if (gc_end.before(gc))
     * {
     * gc.setTimeInMillis(gc_end.getTimeInMillis());
     * }
     * gc_start.setTimeInMillis(gc.getTimeInMillis());
     * } break;
     * case DateRangeData.RANGE_ONE_MONTH:
     * default:
     * gc_start.add(Calendar.MONTH, -1);
     * break;
     * }
     * // System.out.println(gc_start);
     * startSpinnerDateModel.setValue(gc_start.getTime());
     * }
     */

    /**
     * Get Date Range Data
     * 
     * @return
     */
    public DateRangeData getDateRangeData()
    {
        DateRangeData drd = new DateRangeData();
        // drd.setRange(this.iRadioGroupState, this.gc_start, this.gc_end);
        drd.setRange(this.cb_range.getSelectedIndex() + 1, this.getStartCalendar(), this.getEndCalendar());

        return drd;
    }

    /*
     * private class RadioListener extends AbstractAction
     * {
     * private static final long serialVersionUID = -2806660966659688717L;
     * private int stat = 1;
     * public RadioListener(int flag)
     * {
     * this.stat = flag;
     * }
     * public void actionPerformed(ActionEvent e)
     * {
     * iRadioGroupState = stat;
     * if (stat == DateRangeData.RANGE_CUSTOM)
     * {
     * spinnerStart.setEnabled(true);
     * }
     * else
     * spinnerStart.setEnabled(false);
     * calcStartDate();
     * }
     * }
     */

    /**
     * Get End Date
     * 
     * @return
     */
    /*
     * public Date getEndDate()
     * {
     * return this.endSpinnerDateModel.getDate();
     * }
     */
    /**
     * Get Start Date
     * 
     * @return
     */
    /*
     * public Date getStartDate()
     * {
     * return startSpinnerDateModel.getDate();
     * }
     */
    /**
     * Get End Calendar
     * 
     * @return
     */
    public GregorianCalendar getEndCalendar()
    {
        return ATechDate.getGregorianCalendar(ATechDate.FORMAT_DATE_ONLY, this.dt_till.getDate());
    }

    /**
     * Get Start Calendar
     * 
     * @return
     */
    public GregorianCalendar getStartCalendar()
    {
        return ATechDate.getGregorianCalendar(ATechDate.FORMAT_DATE_ONLY, this.dt_from.getDate());
    }

    /**
     * State Changed
     * 
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    /*
     * public void stateChanged(ChangeEvent e)
     * {
     * calcDateAndUpdateFields();
     * }
     */

    public void itemStateChanged(ItemEvent ie)
    {
        // TODO Auto-generated method stub
        JComboBox cb = (JComboBox) ie.getSource();

        System.out.println("Beforew: " + ie.getItem() + " , cb_range: " + cb.getSelectedIndex());

        setChanged(cb.getSelectedIndex() + 1);

        /*
         * if (this.current_range != (this.cb_range.getSelectedIndex() + 1))
         * {
         * this.current_range = this.cb_range.getSelectedIndex() +1;
         * System.out.println("Ch: " + ie.getItem());
         * calcDateAndUpdateFields();
         * this.dt_from.setEnabled(this.current_range ==
         * DateRangeData.RANGE_CUSTOM);
         * // this.setRange(this.current_range);
         * }
         */

    }

    private synchronized void setChanged(int range)
    {
        /*
         * //System.out.println("setChanged(current=" + this.current_range +
         * ",range=" + range);
         * if (this.current_range != range)
         * {
         * this.current_range = range;
         * System.out.println("Ch: " + range);
         * calcDateAndUpdateFields();
         * // this.setRange(this.current_range);
         * }
         */
    }

    public void actionPerformed(ActionEvent ae)
    {
        String cmd = ae.getActionCommand();
        // TODO Auto-generated method stub
        // System.out.println("Set date changed! AP " + cmd);

        if (cmd.equals("dt_till"))
        {
            System.out.println("Set date changed! ap IN " + cmd);
            // this.calcDateAndUpdateFields();
            // this.dt_from.setDate(this.dt_till.getDate());
            // ((DateComponent)ae.getSource()).getDate();
            // this.dt_till.getDate();
            this.dt_from.setDate(((DateComponent) ae.getSource()).getDate());
            this.calcDateAndUpdateFields(DATE_CMP_TILL);
        }
        else
        {
            System.out.println("Set date changed! ap IN " + cmd);
            // this.calcDateAndUpdateFields();
            dt_from.getDate();
            // this.dt_from.setDate(this.dt_till.getDate());
        }

    }

    /*
     * public void stateChanged(ChangeEvent ce)
     * {
     * System.out.println("Set date changed!" + ce.getSource());
     * // TODO Auto-generated method stub
     * if (ce.getSource().equals(this.dt_till))
     * {
     * System.out.println("Set date changed!");
     * //this.calcDateAndUpdateFields();
     * this.dt_from.setDate(this.dt_till.getDate());
     * }
     * }
     */

}
