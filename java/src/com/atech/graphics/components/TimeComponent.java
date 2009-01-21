package com.atech.graphics.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.atech.i18n.I18nControlAbstract;


/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy
 *
*/


public class TimeComponent extends JPanel
{

    private static final long serialVersionUID = -7073902375512649375L;
    I18nControlAbstract ic = null;

    int component_width = 188;
    int component_height = 25;

    JSpinner day, month, year;

    JSpinner hour, minute;




    public TimeComponent()
    {

        super();

        Font font_normal = new Font("SansSerif", Font.PLAIN, 12);

        this.setSize(component_width,component_height);
        this.setLayout(null);

        SpinnerNumberModel listHourModel = new SpinnerNumberModel(0,0,23,1);
        hour = new JSpinner(listHourModel);
        hour.setEditor(new JSpinner.NumberEditor(hour, "00"));
        hour.setFont(font_normal);

        SpinnerNumberModel listMinModel = new SpinnerNumberModel(0,0,59,1);
        minute = new JSpinner(listMinModel);
        minute.setEditor(new JSpinner.NumberEditor(hour, "00"));
        minute.setFont(font_normal);


        hour.setBounds(0, 0, 40, 25);
        minute.setBounds(44, 0, 40, 25);

        this.add(hour, null);
        this.add(minute, null);
    }




    public void setTime(int date)
    {

        int hours = date/100;

        int minutes = date-(hours*100);

        hour.setValue(hours);
        minute.setValue(minutes);

    }


    public int getTime()
    {

        int h = ((SpinnerNumberModel)hour.getModel()).getNumber().intValue();
        int m = ((SpinnerNumberModel)minute.getModel()).getNumber().intValue();

        return(h*100) + m;

    }



    public void setBackground(Color bg)
    {
        super.setBackground(bg);
    }


    public void setEnabled(boolean isEnabled)
    {
        hour.setEnabled(isEnabled);
        minute.setEnabled(isEnabled);
    }




    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x,y,component_width,component_height);
    }




}


