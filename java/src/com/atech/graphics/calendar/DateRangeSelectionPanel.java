/*
 *  GGC - GNU Gluco Control
 *
 *  A pure java app to help you manage your diabetes.
 *
 *  See AUTHORS for copyright information.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Filename: DateRangeSelectionPanel
 *  
 *  Purpose:  Panel for selecting a date range.
 *
 *  Author:   schutld
 */

package com.atech.graphics.calendar; 
//ggc.gui.calendar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;


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


public class DateRangeSelectionPanel extends JPanel implements ChangeListener
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
    
    private JSpinner spinnerEnd;
    private JSpinner spinnerStart;

    private GregorianCalendar gc_end = null;
    private GregorianCalendar gc_start = null;

    private SpinnerDateModel endSpinnerDateModel = null;
    private SpinnerDateModel startSpinnerDateModel = null;

    private int iRadioGroupState = 0;


    // private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Constructor
     * 
     * @param da
     */
    public DateRangeSelectionPanel(ATDataAccessAbstract da)
    {
        this(da, new GregorianCalendar());
    }

    /**
     * Constructor
     * 
     * @param da
     * @param flag
     */
    public DateRangeSelectionPanel(ATDataAccessAbstract da, int flag)
    {
        this(da, new GregorianCalendar(), flag);
    }

    /**
     * Constructor
     * 
     * @param da
     * @param endDate
     */
    public DateRangeSelectionPanel(ATDataAccessAbstract da, GregorianCalendar endDate)
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
    public DateRangeSelectionPanel(ATDataAccessAbstract da, GregorianCalendar endDate, int flag)
    {
        this(da, endDate, null, flag);
    }

    /*
     * public DateRangeSelectionPanel(Date endDate, Date startDate) {
     * endSpinnerDateModel.setValue(endDate);
     * startSpinnerDateModel.setValue(startDate); iRadioGroupState = CUSTOM; }
     */

    /**
     * Constructor
     * 
     * @param da
     * @param endDate
     * @param startDate
     */
    public DateRangeSelectionPanel(ATDataAccessAbstract da, GregorianCalendar endDate, GregorianCalendar startDate)
    {
        this(da, endDate, startDate, DateRangeData.RANGE_CUSTOM);
    }

    private DateRangeSelectionPanel(ATDataAccessAbstract da, GregorianCalendar endDate, GregorianCalendar startDate, int flag)
    {
        m_da = da;
        m_ic = da.getI18nControlInstance();
        
        endSpinnerDateModel = new SpinnerDateModel();
        startSpinnerDateModel = new SpinnerDateModel();

        endSpinnerDateModel.setCalendarField(Calendar.DAY_OF_WEEK);
        startSpinnerDateModel.setCalendarField(Calendar.DAY_OF_WEEK);

        if (endDate != null)
        {
            this.gc_end = (GregorianCalendar) endDate.clone();
            this.gc_end.set(Calendar.HOUR_OF_DAY, 0);
            this.gc_end.set(Calendar.MINUTE, 0);
            this.gc_end.set(Calendar.SECOND, 0);
            endSpinnerDateModel.setValue(this.gc_end.getTime());
        }

        if (startDate != null)
        {
            this.gc_start = (GregorianCalendar) startDate.clone();
            this.gc_start.set(Calendar.HOUR_OF_DAY, 0);
            this.gc_start.set(Calendar.MINUTE, 0);
            this.gc_start.set(Calendar.SECOND, 0);
            startSpinnerDateModel.setValue(this.gc_start.getTime());
        }

        iRadioGroupState = flag;
        init();
        calcStartDate();
    }

    private void init()
    {
        JPanel a = new JPanel();
        a.setLayout(new GridLayout(0, 1));

        // Box a = Box.createVerticalBox();
        a.add(new JLabel(m_ic.getMessage("ENDING_DATE") + ":"));
        a.add(spinnerEnd = new JSpinner(endSpinnerDateModel));
        ((JSpinner.DateEditor) spinnerEnd.getEditor()).getFormat().applyPattern("dd.MM.yyyy");

        a.add(new JLabel(m_ic.getMessage("STARTING_DATE") + ":"));
        a.add(spinnerStart = new JSpinner(startSpinnerDateModel));
        ((JSpinner.DateEditor) spinnerStart.getEditor()).getFormat().applyPattern("dd.MM.yyyy");

        spinnerEnd.addChangeListener(this);
        spinnerStart.addChangeListener(this);

        /*
         * Box a = Box.createVerticalBox(); a.add(new
         * JLabel(m_ic.getMessage("ENDING_DATE")+":")); a.add(spinnerEnd = new
         * JSpinner(endSpinnerDateModel));
         * ((JSpinner.DateEditor)spinnerEnd.getEditor
         * ()).getFormat().applyPattern("dd.MM.yyyy");
         * 
         * a.add(new JLabel(m_ic.getMessage("STARTING_DATE")+":"));
         * a.add(spinnerStart = new JSpinner(startSpinnerDateModel));
         * ((JSpinner.
         * DateEditor)spinnerStart.getEditor()).getFormat().applyPattern
         * ("dd.MM.yyyy");
         * 
         * spinnerEnd.addChangeListener(this);
         * spinnerStart.addChangeListener(this);
         */

        /*
         * new ChangeListener() { public void stateChanged(ChangeEvent e) {
         * calcDateAndUpdateFields(); } });
         */

        /*
         * new ChangeListener() { public void stateChanged(ChangeEvent e) {
         * calcDateAndUpdateFields(); } });
         */

        JRadioButton rbOneWeek = new JRadioButton("  " + m_ic.getMessage("1_WEEK"), iRadioGroupState == DateRangeData.RANGE_ONE_WEEK);
        rbOneWeek.setIconTextGap(8);
        // rbOneWeek.se

        JRadioButton rbOneMonth = new JRadioButton("  " + m_ic.getMessage("1_MONTH"), iRadioGroupState == DateRangeData.RANGE_ONE_MONTH);
        JRadioButton rbThreeMonths = new JRadioButton("  " + m_ic.getMessage("3_MONTHS"),
                iRadioGroupState == DateRangeData.RANGE_THREE_MONTHS);
        JRadioButton rbCustom = new JRadioButton("  " + m_ic.getMessage("CUSTOM"), iRadioGroupState == DateRangeData.RANGE_CUSTOM);

        ButtonGroup group = new ButtonGroup();
        group.add(rbOneWeek);
        group.add(rbOneMonth);
        group.add(rbThreeMonths);
        group.add(rbCustom);

        rbOneWeek.addActionListener(new RadioListener(DateRangeData.RANGE_ONE_WEEK));
        rbOneMonth.addActionListener(new RadioListener(DateRangeData.RANGE_ONE_MONTH));
        rbThreeMonths.addActionListener(new RadioListener(DateRangeData.RANGE_THREE_MONTHS));
        rbCustom.addActionListener(new RadioListener(DateRangeData.RANGE_CUSTOM));

        Box b = Box.createVerticalBox();
        b.add(rbOneWeek);
        b.add(rbOneMonth);
        b.add(rbThreeMonths);
        b.add(rbCustom);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(m_ic.getMessage("DATE_RANGE_SELECTOR")));

        JPanel c = new JPanel();
        c.setLayout(new GridLayout());
        c.add(new JLabel("  "));

        add(a, BorderLayout.WEST);
        add(c, BorderLayout.CENTER);
        add(b, BorderLayout.EAST);

        if (iRadioGroupState != DateRangeData.RANGE_CUSTOM)
            spinnerStart.setEnabled(false);
    }

    private void calcDateAndUpdateFields()
    {
        calcStartDate();
    }

    private void calcStartDate()
    {
        if (iRadioGroupState == DateRangeData.RANGE_CUSTOM)
            return;

        // if (gc_start==null)
        gc_start = (GregorianCalendar) gc_end.clone();

        switch (iRadioGroupState)
        {
        case DateRangeData.RANGE_ONE_WEEK:
            gc_start.add(Calendar.WEEK_OF_YEAR, -1);
            break;
        case DateRangeData.RANGE_THREE_MONTHS:
            gc_start.add(Calendar.MONTH, -3);
            break;
        case DateRangeData.RANGE_ONE_MONTH:
        default:
            gc_start.add(Calendar.MONTH, -1);
            break;

        }

        // System.out.println(gc_start);
        startSpinnerDateModel.setValue(gc_start.getTime());
    }

    /**
     * Get Date Range Data
     * 
     * @return
     */
    public DateRangeData getDateRangeData()
    {
        DateRangeData drd = new DateRangeData();
        drd.setRange(this.iRadioGroupState, this.gc_start, this.gc_end);
        
        return drd;
    }
    
    
    private class RadioListener extends AbstractAction
    {
        /**
         * 
         */
        private static final long serialVersionUID = -2806660966659688717L;
        private int stat = 1;

        public RadioListener(int flag)
        {
            this.stat = flag;
        }

        public void actionPerformed(ActionEvent e)
        {
            iRadioGroupState = stat;

            if (stat == DateRangeData.RANGE_CUSTOM)
            {
                spinnerStart.setEnabled(true);
            }
            else
                spinnerStart.setEnabled(false);

            calcStartDate();
        }
    }

    /**
     * Get End Date
     * 
     * @return
     */
    public Date getEndDate()
    {
        return endSpinnerDateModel.getDate();
    }

    /**
     * Get Start Date
     * 
     * @return
     */
    public Date getStartDate()
    {
        return startSpinnerDateModel.getDate();
    }

    /**
     * Get End Calendar
     * 
     * @return
     */
    public GregorianCalendar getEndCalendar()
    {
        return m_da.getGregorianCalendar(endSpinnerDateModel.getDate());
    }

    /**
     * Get Start Calendar
     * 
     * @return
     */
    public GregorianCalendar getStartCalendar()
    {
        return m_da.getGregorianCalendar(startSpinnerDateModel.getDate());
    }

    /**
     * State Changed
     * 
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e)
    {
        calcDateAndUpdateFields();
    }

}
