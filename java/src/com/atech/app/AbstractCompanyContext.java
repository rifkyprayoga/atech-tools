package com.atech.app;

import javax.swing.JPanel;

public abstract class AbstractCompanyContext
{

    protected boolean has_custom_main_panel = false;

    public abstract void initCompanyContext();

    public boolean hasCustomMainPanel()
    {
        return this.has_custom_main_panel;
    }

    public abstract JPanel getCustomMainPanel();

}
