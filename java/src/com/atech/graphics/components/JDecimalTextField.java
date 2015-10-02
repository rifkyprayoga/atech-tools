package com.atech.graphics.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

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

public class JDecimalTextField extends JFormattedTextField implements KeyListener
{

    private static final long serialVersionUID = -742112156331004021L;

    public static String decimalSeparator;


    /**
     * Instantiates a new j decimal text field.
     * 
     * @param value the value
     * @param decimal_places the decimal_places
     */
    public JDecimalTextField(Object value, int decimal_places)
    {
        super();

        NumberFormat displayFormat, editFormat;

        displayFormat = NumberFormat.getNumberInstance();
        displayFormat.setMinimumFractionDigits(0);
        displayFormat.setMaximumFractionDigits(decimal_places);

        editFormat = NumberFormat.getNumberInstance();
        editFormat.setMinimumFractionDigits(0);
        editFormat.setMaximumFractionDigits(decimal_places);

        createDecimalSeparatorInstance();

        // new JFormatedTextField()

        // JFormattedTextField ftf = new JFormattedTextField(
        this.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(displayFormat), new NumberFormatter(
                displayFormat), new NumberFormatter(editFormat)));

        this.setValue(value);
        this.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        this.addKeyListener(this);

    }


    private void createDecimalSeparatorInstance()
    {
        if (decimalSeparator == null)
        {
            DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
            char real_decimal = dfs.getDecimalSeparator();
            decimalSeparator = (new Character(real_decimal)).toString();
        }
    }


    /**
     * Gets the current value.
     * 
     * @return the current value
     */
    public Object getCurrentValue()
    {
        try
        {
            this.commitEdit();
        }
        catch (Exception ex)
        {}

        return this.getValue();
    }


    /** 
     * setBounds
     */
    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);
    }


    /** 
     * keyPressed
     */
    public void keyPressed(KeyEvent arg0)
    {
    }


    /** 
     * keyReleased
     */
    public void keyReleased(KeyEvent ke)
    {
        if (ke.getKeyCode() == KeyEvent.VK_PERIOD || ke.getKeyCode() == KeyEvent.VK_DECIMAL
                || ke.getKeyCode() == KeyEvent.VK_COMMA)
        {
            String s = this.getText();
            s = s.replace(".", decimalSeparator);
            this.setText(s);
        }
    }


    /** 
     * keyTyped
     */
    public void keyTyped(KeyEvent arg0)
    {
    }

}
