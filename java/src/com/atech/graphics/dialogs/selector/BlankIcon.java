package com.atech.graphics.dialogs.selector;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

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

/**
 * @version 1.0 02/26/99
 */
public class BlankIcon implements Icon
{
    private Color fillColor;
    private int size;

    /**
     * Instantiates a new blank icon.
     */
    public BlankIcon()
    {
        this(null, 11);
    }

    /**
     * Instantiates a new blank icon.
     * 
     * @param color the color
     * @param size the size
     */
    public BlankIcon(Color color, int size)
    {
        // UIManager.getColor("control")
        // UIManager.getColor("controlShadow")
        fillColor = color;

        this.size = size;
    }

    /** 
     * paintIcon
     */
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        if (fillColor != null)
        {
            g.setColor(fillColor);
            g.drawRect(x, y, size - 1, size - 1);
        }
    }

    /** 
     * getIconWidth
     */
    public int getIconWidth()
    {
        return size;
    }

    /** 
     * getIconHeight
     */
    public int getIconHeight()
    {
        return size;
    }
}
