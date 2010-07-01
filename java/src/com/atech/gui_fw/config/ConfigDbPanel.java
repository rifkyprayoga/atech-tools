package com.atech.gui_fw.config;

import java.awt.Component;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.atech.db.hibernate.tool.DbToolApplicationAbstract;
import com.atech.help.HelpCapable;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ConfigDbPanel - This class is for setting database
 *  
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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

public class ConfigDbPanel extends AbstractConfigPanel implements HelpCapable
{

    private static final long serialVersionUID = 2916997490131345658L;
    private JComboBox cb_database;
    DbToolApplicationAbstract m_dbc = null; //m_da.getDbConfig();

    /**
     * Constructor
     * 
     * @param acc 
     */
    public ConfigDbPanel(AbstractConfigurationContext acc)
    {
        super(acc);
        
        this.m_dbc = m_da.getDbToolAbstract();
        this.m_dbc.loadConfig();

        init();
    }

    private void init()
    {
        this.setLayout(null);
        
        ATSwingUtils.getTitleLabel(m_ic.getMessage("DATABASE_SETTINGS"), 0, 25, 520, 36, this, ATSwingUtils.FONT_BIG_BOLD);
        
        JLabel label;
        label = new JLabel(m_ic.getMessage("DATABASE_SETTINGS_DESC"));
        label.setBounds(50, 90, 410, 150);
        label.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL));
        label.setVerticalAlignment(JLabel.TOP);
        this.add(label); // new
                       // JLabel(m_ic.getMessage("DATABASE_SETTINGS_DESC")));

        label = new JLabel(m_ic.getMessage("SELECTED_DATABASE") + ":");
        label.setBounds(50, 240, 180, 25);
        this.add(label);

        this.cb_database = new JComboBox(this.m_da.getDbToolAbstract().getAllDatabasesNamesPlusAsArray());
        this.cb_database.setBounds(50, 270, 330, 25);
        this.cb_database.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL));
        this.cb_database.setSelectedIndex(this.m_da.getDbToolAbstract().getSelectedDatabaseIndex());
        this.add(this.cb_database);
    }


    boolean in_change = false;

    /**
     * Item State Changed
     * 
     * @see ggc.gui.panels.prefs.AbstractPrefOptionsPanel#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent e)
    {
        if (in_change)
            return;
        else
            in_change = true;
       
        this.changed = (this.m_dbc.getSelectedDatabaseIndex()!=this.cb_database.getSelectedIndex());

        in_change = false;
    }

    
    /**
     * Save Properties
     * 
     * @see ggc.gui.panels.prefs.AbstractPrefOptionsPanel#saveProps()
     */
    @Override
    public void saveConfig()
    {
        this.m_dbc.setSelectedDatabaseIndex(this.cb_database.getSelectedIndex());
    }

    

    // ****************************************************************
    // ****** HelpCapable Implementation *****
    // ****************************************************************

    /**
     * getComponent - get component to which to attach help context
     */
    public Component getComponent()
    {
        return this.getRootPane();
    }

    /**
     * getHelpButton - get Help button
     */
    public JButton getHelpButton()
    {
        return this.parent.getHelpButton();
    }

    /**
     * getHelpId - get id for Help
     */
    public String getHelpId()
    {
        return "GGC_Prefs_General";
    }

}
