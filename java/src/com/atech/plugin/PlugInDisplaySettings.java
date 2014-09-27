package com.atech.plugin;

import java.awt.LayoutManager;
import java.awt.Rectangle;

public class PlugInDisplaySettings
{

    Rectangle m_bounds = null;

    public PlugInDisplaySettings(Rectangle bounds)
    {
        this.m_bounds = bounds;
    }

    public LayoutManager getLayoutManager()
    {
        return null;
    }

    public Rectangle getBounds()
    {
        return this.m_bounds;
    }

}
