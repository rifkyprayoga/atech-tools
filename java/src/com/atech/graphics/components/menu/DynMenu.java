package com.atech.graphics.components.menu;

import java.util.ArrayList;

import javax.swing.JMenu;

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
