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

public abstract class AboutPanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = -5811159426365329265L;

    /**
     * The i18nControl.
     */
    protected I18nControlAbstract i18nControl;


    /**
     * Instantiates a new about panel.
     * 
     * @param i18nControl the i18nControl
     */
    public AboutPanel(I18nControlAbstract i18nControl)
    {
        this.i18nControl = i18nControl;
    }


    /**
     * Gets the tab panel.
     * 
     * @return the tab panel
     */
    public abstract JPanel getTabPanel();


    /**
     * Gets the tab name.
     * 
     * @return the tab name
     */
    public abstract String getTabName();

}
