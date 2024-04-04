package com.atech.graphics.calendar;

/*
 *  Filename: MonthPanel
 *  
 *  Purpose:  ComboBox to select a month.
 *
 *  Author:   schultd (taken from ggc project)
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  CalendarEvent - Event for CalendarListeners
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
 *  Author:   Andy
 */

public class MonthPanel extends JPanel implements CalendarListener
{

    private static final long serialVersionUID = -1761816913587729300L;

    private ATDataAccessAbstract m_da = null;

    private JComboBox monthCombo;

    /**
     * Constructor
     * 
     * @param cMod
     * @param da
     */
    public MonthPanel(final CalendarModel cMod, ATDataAccessAbstract da)
    {
        this.m_da = da;
        monthCombo = new JComboBox(m_da.getMonthsArray());
        monthCombo.setSelectedIndex(cMod.getMonth());
        add(monthCombo);

        monthCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cMod.setMonth(monthCombo.getSelectedIndex());
            }
        });
    }

    /**
     * Date Has Changed
     * 
     * @see com.atech.graphics.calendar.CalendarListener#dateHasChanged(com.atech.graphics.calendar.CalendarEvent)
     */
    public void dateHasChanged(CalendarEvent e)
    {
        if (e.getEvent() > CalendarEvent.MONTH_CHANGED)
        {
            monthCombo.setSelectedIndex(e.getNewMonth());
        }
    }
}
