package com.atech.graphics.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JPanel;

import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  CalendarPane - The Pane which contains the whole calendar widget.
 *  Copyright (C) 2002 schultd (taken from ggc project)
 *  
 *  Filename: CalendarPane
 *  
 *  Purpose:  The Pane which contains the whole calendar widget.
 *
 *  Author:   schultd (taken from ggc project)
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
 *  @author schultd
 *
*/


public class CalendarPane extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -4850264685726417445L;
    private MonthPanel mPanel;
    private YearPanel yPanel;
    private DayPanel dPanel;
    
    ATDataAccessAbstract m_da;

    CalendarModel cModel;

    Vector<CalendarListener> listeners = new Vector<CalendarListener>();

    /**
     * Constructor
     * 
     * @param da
     */
    public CalendarPane(ATDataAccessAbstract da)
    {
        cModel = new CalendarModel(new GregorianCalendar(), this);
        this.m_da = da;

        //
        //this.setLayout(null);

        mPanel = new MonthPanel(cModel, da);
        yPanel = new YearPanel(cModel, da);

        setLayout(new BorderLayout());

        /*
        JPanel pan_upper = new JPanel();
        pan_upper.setLayout(null);
        pan_upper.setBounds(20, 20, 300, 35);
        
        mPanel.setBounds(20, 0, 100, 25);
        pan_upper.add(mPanel);
        
        yPanel.setBounds(140, 0, 80, 25);
        pan_upper.add(yPanel);
        */
        
        Box a = Box.createHorizontalBox();
        
       
        //a.setBounds(10, 10, 120, 30);

        a.add(mPanel);
        a.add(Box.createRigidArea(new Dimension(20, 30)));
        a.add(yPanel);

        add(a, BorderLayout.NORTH);

//        add(pan_upper, BorderLayout.NORTH);
        
        dPanel = new DayPanel(cModel, da);
        add(dPanel, BorderLayout.CENTER);

        this.addCalendarListener(dPanel);
        this.addCalendarListener(mPanel);
        this.addCalendarListener(yPanel);

        //setBounds(10, 10, 200, 200);
    }

    /**
     * Get Selected Date
     * 
     * @return
     */
    public Date getSelectedDate()
    {
        return cModel.getDate();
    }

    /**
     * Add Calendar Listener
     * 
     * @param l
     */
    public void addCalendarListener(CalendarListener l)
    {
        listeners.addElement(l);
    }

    /**
     * Remove Calendar Listener
     * 
     * @param l
     */
    public void removeCalendarListener(CalendarListener l)
    {
        listeners.removeElement(l);
    }

    /**
     * Notify Listeners
     * 
     * @param event
     */
    public void notifyListeners(int event)
    {
        //System.out.println("notifyListeners: " + cModel.getYear() + " " + cModel.getMonth()+ " " + cModel.getDay());
        notifyListeners(new CalendarEvent(new GregorianCalendar(cModel.getYear(), cModel.getMonth(), cModel.getDay()), event));
    }

    /**
     * Notify Listeners
     * 
     * @param e
     */
    public void notifyListeners(CalendarEvent e)
    {
        for (int i = 0; i < listeners.size(); i++) 
        {
            CalendarListener l = listeners.elementAt(i);
            l.dateHasChanged(e);
        }
    }
}
