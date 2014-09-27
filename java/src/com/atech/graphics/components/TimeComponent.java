package com.atech.graphics.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atech.i18n.I18nControlAbstract;

// TODO: Auto-generated Javadoc
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

public class TimeComponent extends JPanel implements ChangeListener
{

    private static final long serialVersionUID = -7073902375512649375L;
    private String action_command = null;

    /**
     * The ic.
     */
    I18nControlAbstract ic = null;

    /**
     * The component_width.
     */
    int component_width = 105;

    /**
     * The component_height.
     */
    int component_height = 25;

    /**
     * The year.
     */
    JSpinner day, month, year;

    /**
     * The minute.
     */
    JSpinner hour, minute;

    ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

    /**
     * Constructor
     */
    public TimeComponent()
    {

        super();

        Font font_normal = new Font("SansSerif", Font.PLAIN, 12);

        this.setSize(component_width, component_height);
        this.setLayout(null);

        SpinnerNumberModel listHourModel = new SpinnerNumberModel(0, 0, 23, 1);
        hour = new JSpinner(listHourModel);
        hour.setEditor(new JSpinner.NumberEditor(hour, "00"));
        hour.addChangeListener(this);
        hour.setFont(font_normal);

        SpinnerNumberModel listMinModel = new SpinnerNumberModel(0, 0, 59, 1);
        minute = new JSpinner(listMinModel);
        minute.setEditor(new JSpinner.NumberEditor(minute, "00"));
        minute.addChangeListener(this);
        minute.setFont(font_normal);

        hour.setBounds(0, 0, 45, 25);
        minute.setBounds(50, 0, 45, 25);

        this.add(hour, null);
        this.add(minute, null);
    }

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
     * Set the time 
     * 
     * @param date
     */
    public void setTime(int date)
    {

        int hours = date / 100;

        int minutes = date - hours * 100;

        hour.setValue(hours);
        minute.setValue(minutes);

    }

    /**
     * Gets the time.
     * 
     * @return the time
     */
    public int getTime()
    {

        int h = ((SpinnerNumberModel) hour.getModel()).getNumber().intValue();
        int m = ((SpinnerNumberModel) minute.getModel()).getNumber().intValue();

        return h * 100 + m;

    }

    /**
     * Gets the time string.
     * 
     * @return the time string
     */
    public String getTimeString()
    {

        StringBuffer sb = new StringBuffer();
        String h = "" + ((SpinnerNumberModel) hour.getModel()).getNumber().intValue();
        String m = "" + ((SpinnerNumberModel) minute.getModel()).getNumber().intValue();

        if (h.length() == 1)
        {
            sb.append("0");
        }

        sb.append(h);
        sb.append(":");

        if (m.length() == 1)
        {
            sb.append("0");
        }

        sb.append(m);

        return sb.toString();

    }

    /** 
     * setBackground
     */
    @Override
    public void setBackground(Color bg)
    {
        super.setBackground(bg);
    }

    /** 
     * setEnabled
     */
    @Override
    public void setEnabled(boolean isEnabled)
    {
        hour.setEnabled(isEnabled);
        minute.setEnabled(isEnabled);
    }

    /** 
     * setBounds
     */
    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, component_width, component_height);
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

    public void stateChanged(ChangeEvent e)
    {
        ActionEvent ae = new ActionEvent(e.getSource(), (int) serialVersionUID, this.action_command);
        ae.setSource(e.getSource());
        notifyListeners(ae);
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

}
