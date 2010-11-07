package com.atech.graphics.components.menu;

import java.awt.event.ActionListener;

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
    

    public DynMenuItem(String _name, String _tooltip, String _action_command, ActionListener al, String _keyword, String _icon_small, int _min_level, boolean _process)
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
    
    
    
    //dmi.getActionCommand(), String icon_small, String keyword, int min_level, boolean process);
    
    
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
