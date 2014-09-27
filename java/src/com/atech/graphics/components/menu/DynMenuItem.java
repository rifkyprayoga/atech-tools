package com.atech.graphics.components.menu;

import java.awt.event.ActionListener;

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

public class DynMenuItem implements DynMenuInterface
{

    private String name;
    private String tooltip;
    private String action_command;
    ActionListener action_listener;
    private String keyword;
    int min_level = 0;
    boolean process = false;
    private String icon_small;

    public DynMenuItem(String _name, String _tooltip, String _action_command, ActionListener al, String _keyword)
    {
        this.name = _name;
        this.tooltip = _tooltip;
        this.action_command = _action_command;
        this.action_listener = al;
        this.keyword = _keyword;
    }

    public DynMenuItem(String _name, String _tooltip, String _action_command, ActionListener al, String _keyword,
            String _icon_small, int _min_level, boolean _process)
    {
        this.name = _name;
        this.tooltip = _tooltip;
        this.action_command = _action_command;
        this.action_listener = al;
        this.keyword = _keyword;
        this.min_level = _min_level;
        this.process = _process;
        this.icon_small = _icon_small;
    }

    // dmi.getActionCommand(), String icon_small, String keyword, int min_level,
    // boolean process);

    public String getName()
    {
        return this.name;
    }

    public String getTooltip()
    {
        return this.tooltip;
    }

    public String getActionCommand()
    {
        return this.action_command;
    }

    public ActionListener getActionListener()
    {
        return this.action_listener;
    }

    public int getType()
    {
        return 2;
    }

    public String getKeyword()
    {
        return this.keyword;
    }

    public int getMinLevel()
    {
        return this.min_level;
    }

    public boolean getProcess()
    {
        return this.process;

    }

    public String getIconSmall()
    {
        return this.icon_small;
    }

}
