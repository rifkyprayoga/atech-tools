package com.atech.graphics.calendar;

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

//    private int month_days[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    
    private String m_days[] = null;

    private GridLayout m_grid57 = null;
    private GridLayout m_grid67 = null;
    private GridLayout m_grid77 = null;

    private Font font_plain = null;
    private Font font_bold = null;
    
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

        this.m_grid57 = new GridLayout(5, 7);
        this.m_grid67 = new GridLayout(6, 7);
        this.m_grid77 = new GridLayout(7, 7);
//        this.m_grid87 = new GridLayout(8, 7);
        setLayout(this.m_grid67);

        
        this.m_ic = this.m_da.getI18nControlInstance(); 
        
        
        font_plain = new Font("Dialog", Font.PLAIN, 12);
        font_bold = new Font("Dialog", Font.BOLD, 12);
        
        
//        m_gc = new GregorianCalendar();
        
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
//        createPanels();
        doLayoutButtons();
        //this.setBackground(Color.blue);
        //this.setBounds(10, 40, 100,200);
    }

    private void doLayoutButtons()
    {
        int firstday = cMod.getFirstDayInMonth() - 1;
        //int numdays = firstday + cMod.getNumDaysInMonth();
        int numdays_m = cMod.getNumDaysInMonth();
        int curday = firstday + cMod.getDay();

        boolean special = false;
        
        removeAll();

//        System.out.println("year: " + cMod.getYear() + " first: " + firstday + " num_days: " + numdays + ", num_days_m: " + numdays_m + ", curr_day=" + curday);

        int entries = numdays_m + firstday;
        
        if (entries>35) 
        //(((numdays_m==31) && (firstday>4)) || ((numdays_m==30) && (firstday>5)))
        {
//            System.out.println("7x7");
            setLayout(this.m_grid77);
        }
        else if (entries==28) 
        //(((numdays_m==28) && (firstday==0)) || ((numdays_m==28) && (firstday==1)) )  
        {
            //System.out.println("7x5");
            setLayout(this.m_grid57);
        }
        else if (entries==29) 
        //(((numdays_m==29) && (firstday==0)) || ((numdays_m==28) && (firstday==1)))
        {
            //System.out.println("7x6");
            setLayout(this.m_grid67); 
            special = true;
        }
        else
        {
            //System.out.println("7x6");
            setLayout(this.m_grid67);
        }


        for (int i=0; i<7; i++) 
        {
            JLabel label = new JLabel(this.m_days[i]);
            
            if (i==0)
                label.setForeground(Color.red);
            
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            add(label);
        }


        //for (int i = 0; i < firstday; i++)
        for (int i = 0; i < firstday; i++) 
        {
            add(new JLabel(""));
        }

        //int lbl = 0;
        JButton b = null;
        int curr_day = curday-1;
        
        //for (int i = firstday; i < numdays; i++)
        
        for (int i = 1; i <= numdays_m; i++)
        {
            //add(b = createButton(++lbl));
            add(b = createButton(i));
            
            if (i==curr_day)
            {
                b.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                b.setFont(this.font_bold); //new Font("Dialog", Font.BOLD, 12));
            }
        }
      
        
        if (special)
        {
            add(new JLabel());
        }
        
        
        //this.validate();
        this.repaint();

    }


    /*
    Hashtable<String,JPanel> list = new Hashtable<String,JPanel>();
    
    private void createPanels()
    {
        for(int i=5; i<9; i++)
        {
            JPanel panel = new JPanel();
            
            
            panel.setLayout(new GridLayout(i, 7));
            
            list.put("" + i, panel);
        }
        
        createSpecialPanel();
        
    }
    
    
    private void createSpecialPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 7));
        
        
        for (int i=0; i<7; i++) 
        {
            JLabel label = new JLabel(this.m_days[i]);
            
            if (i==0)
            label.setForeground(Color.red);
            
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label);
        }
        
        
        list.put("SP", panel);
        
    }
    */
    
    
    private JButton createButton(final int j)
    {
        JButton button = new JButton(j + "");
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setPreferredSize(new Dimension(20, 18));
        button.setFont(this.font_plain); //new Font("Dialog", Font.PLAIN, 12));
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
