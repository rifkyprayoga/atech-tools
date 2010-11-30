package com.atech.graphics.components.menu;

import java.util.ArrayList;

import javax.swing.JMenu;

/**
 * * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 */


public class DynMenu implements DynMenuInterface
{

    private String name;
    private String name_i18n;
    private String tooltip;
    public ArrayList<DynMenuInterface> children = null;
    JMenu menu;

    public DynMenu(String _name, String _tooltip)
    {
        this.name = _name;
        this.children = new ArrayList<DynMenuInterface>();
        this.tooltip = _tooltip;
    }
    
    
    public DynMenu(String _name, String _name_i18n, String _tooltip)
    {
        this.name = _name;
        this.name_i18n = _name_i18n;
        this.tooltip = _tooltip;
        this.children = new ArrayList<DynMenuInterface>();
    }

    
    public DynMenu(String _name, String _name_i18n, String _tooltip, String parents[])
    {
        this.name = _name;
        this.name_i18n = _name_i18n;
        this.children = new ArrayList<DynMenuInterface>();
        this.tooltip = _tooltip;
    }
    
    
    public String getName()
    {
        return this.name;
    }
    
    public String getName_i18n()
    {
        return this.name_i18n;
    }
    
    public String getTooltip()
    {
        return this.tooltip;
    }
    
    
    public void addChild(DynMenuInterface _menu)
    {
        this.children.add(_menu);
    }
    
    
    public int getType()
    {
        return 1;
    }

    
    public void setMenu(JMenu _menu)
    {
        this.menu = _menu;
    }
    
    
}
