package com.atech.graphics.calendar;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  YearPanel - Currently only a textfield to enter a year. Will be changed to
 *              something better in the future.
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

public class YearPanel extends JPanel implements CalendarListener
{

    private static final long serialVersionUID = -9126539917586584312L;
    // private JTextField yearField;
    private JSpinner yearSpinner;

    /**
     * Constructor
     * 
     * @param cMod
     * @param da 
     */
    public YearPanel(final CalendarModel cMod, ATDataAccessAbstract da)
    {

        SpinnerNumberModel listYearsModel = new SpinnerNumberModel(cMod.getYear(), da.getStartYear(),
                cMod.getYear() + 100, 1);

        yearSpinner = new JSpinner(listYearsModel);
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "#"));
        // yearSpinner.setMinimumSize(new Dimension(100, 25));
        yearSpinner.setSize(100, 25);
        // yearSpinner.setFont(font_normal);
        yearSpinner.addChangeListener(new ChangeListener()
        {

            public void stateChanged(ChangeEvent e)
            {
                cMod.setYear(((Integer) yearSpinner.getValue()).intValue());
            }

        });

        add(yearSpinner);

        /*
         * yearField = new JTextField(cMod.getYear() + "", 4);
         * add(yearField);
         * yearField.addActionListener(new ActionListener()
         * {
         * public void actionPerformed(ActionEvent e)
         * {
         * cMod.setYear((new Integer(yearField.getText())).intValue());
         * }
         * });
         */
    }

    /*
     * public YearPanel(final CalendarModel cMod, boolean old)
     * {
     * yearField = new JTextField(cMod.getYear() + "", 4);
     * add(yearField);
     * yearField.addActionListener(new ActionListener()
     * {
     * public void actionPerformed(ActionEvent e)
     * {
     * cMod.setYear((new Integer(yearField.getText())).intValue());
     * }
     * });
     * }
     */

    /**
     * Date Has Changed
     * 
     * @see com.atech.graphics.calendar.CalendarListener#dateHasChanged(com.atech.graphics.calendar.CalendarEvent)
     */
    public void dateHasChanged(CalendarEvent e)
    {
        // yearField.setText(e.getNewYear() + "");
        this.yearSpinner.setValue(e.getNewYear());
    }
}
