
package com.atech.graphics.calendar;


/*
 *  Filename: DayPanel
 *  
 *  Purpose:  The button Panel to select a day in a month.
 *
 *  Author:   schultd (taken from ggc project)
 */



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;


/**
 *  This file is part of ATech Tools library.
 *  
 *  DayPanel - The button Panel to select a day in a month. (Taken from GGC project)
 *  Copyright (C) 2003  Dieter Schultschik 
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
 *  @author schultd
 *  @author Andy
 *
*/


public class DayPanel extends JPanel implements CalendarListener
{

    private static final long serialVersionUID = 4627202102470074924L;

    private I18nControlAbstract m_ic = null;
    
    ATDataAccessAbstract m_da = null;

    CalendarModel cMod;

    private String m_days[] = null;

    private GridLayout m_grid67 = null;
    private GridLayout m_grid77 = null;


    /**
     * Constructor
     * 
     * @param cMod
     * @param da
     */
    public DayPanel(CalendarModel cMod, ATDataAccessAbstract da)
    {
        this.m_da = da;
        this.cMod = cMod;
        //setLayout(new GridLayout(6, 7));

        this.m_grid67 = new GridLayout(6, 7);
        this.m_grid77 = new GridLayout(7, 7);
        setLayout(this.m_grid67);

        this.m_ic = this.m_da.getI18nControlInstance(); 
        
        String days[] = { 
            m_ic.getMessage("SU"),
            m_ic.getMessage("MO"),
            m_ic.getMessage("TU"),
            m_ic.getMessage("WE"),
            m_ic.getMessage("TH"),
            m_ic.getMessage("FR"),
            m_ic.getMessage("SA")
        };

        this.m_days = days;
        //setLayout(null);
        //setComponentOrientation(
        //        ComponentOrientation.RIGHT_TO_LEFT);
        doLayoutButtons();
        //this.setBackground(Color.blue);
        //this.setBounds(10, 40, 100,200);
    }

    private void doLayoutButtons()
    {
        int firstday = cMod.getFirstDayInMonth() - 1;
        int numdays = firstday + cMod.getNumDaysInMonth();
        int numdays_m = cMod.getNumDaysInMonth();
        int curday = firstday + cMod.getDay();

        removeAll();

//        System.out.println("first: " + firstday + " num: " + numdays);

        if (((numdays_m==31) && (firstday>4)) ||
            ((numdays_m==30) && (firstday>5)))
            setLayout(this.m_grid77); 
        else
            setLayout(this.m_grid67); 

        this.invalidate();

        for (int i=0; i<7; i++) 
        {
            JLabel label = new JLabel(this.m_days[i]);
            
            if (i==0)
        	label.setForeground(Color.red);
            
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(label);
        }



        for (int i = 0; i < firstday; i++) 
        {
            add(new JLabel(""));
        }

        int lbl = 0;
        for (int i = firstday; i < curday - 1; i++) 
        {
            add(createButton(++lbl));
        }

        JButton button2 = new JButton(++lbl + "");
        button2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        button2.setFont(new Font("Dialog", Font.BOLD, 12));
        add(button2);

        for (int i = curday; i < numdays; i++) 
        {
            add(createButton(++lbl));
        }

        this.validate();
        this.repaint();

    }


    private JButton createButton(final int j)
    {
        JButton button = new JButton(j + "");
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setPreferredSize(new Dimension(20, 18));
        button.setFont(new Font("Dialog", Font.PLAIN, 12));
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cMod.setDay(j);
            }
        });
        return button;
    }

    /**
     * Date Has Changed
     * 
     * @see com.atech.graphics.calendar.CalendarListener#dateHasChanged(com.atech.graphics.calendar.CalendarEvent)
     */
    public void dateHasChanged(CalendarEvent e)
    {
        //if(e.getEvent() > CalendarEvent.DAY_CHANGED)
        doLayoutButtons();
    }


}
