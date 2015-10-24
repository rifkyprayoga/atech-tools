package com.atech.app.gui.about;

import javax.swing.*;

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

public class AboutCustomPanel extends JPanel
{

    private static final long serialVersionUID = 6489212022366149587L;

    /**
     * The i18nControl.
     */
    I18nControlAbstract ic;

    /**
     * The tab_name.
     */
    String tab_name = "";


    /**
     * Instantiates a new about custom panel.
     * 
     * @param ic the i18nControl
     */
    public AboutCustomPanel(I18nControlAbstract ic)
    {
        this.ic = ic;
        this.tab_name = ic.getMessage("CUSTOM_TAB");
    }


    /**
     * Gets the tab panel.
     * 
     * @return the tab panel
     */
    public JPanel getTabPanel()
    {
        return this;
    }


    /**
     * Gets the tab name.
     * 
     * @return the tab name
     */
    public String getTabName()
    {
        return tab_name;
    }


    /**
     * Sets the tab name.
     * 
     * @param name the new tab name
     */
    public void setTabName(String name)
    {
        this.tab_name = name;
    }

}
