package com.atech.gui_fw.config;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     PrefGeneralPane
 *  Description:  General Preferences: Name, Database selection, Look & Feel
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

public class ConfigLAFPanel extends AbstractConfigPanel implements HelpCapable
{
    private static final long serialVersionUID = 6927307265371709344L;
    private JComboBox cb_lf_type, cb_lf_type_class;
    private JTextField tf_lf;
    private JButton b_browse;
    DbToolApplicationAbstract m_dbc = null; // m_da.getDbConfig();

    /**
     * Constructor
     * 
     * @param acc 
     */
    public ConfigLAFPanel(AbstractConfigurationContext acc)
    {
        super(acc);

        this.m_dbc = acc.getDataAccessInstance().getDbToolAbstract();
        this.m_dbc.loadConfig();

        init();
    }

    private void init()
    {
        this.setLayout(null);

        ATSwingUtils.getTitleLabel(m_ic.getMessage("LAF_SETTINGS"), 0, 25, 520, 36, this, ATSwingUtils.FONT_BIG_BOLD);

        /*
         * JPanel p3 = new JPanel(/* new GridLayout(2, 1)
         *//*
            * );
            * p3.setBorder(new TitledBorder(m_ic.getMessage("LAF_SETTINGS")));
            * p3.setBounds(10, 280, 490, 135);
            * p3.setLayout(null);
            */

        ATSwingUtils.getLabel(m_ic.getMessage("LAF_SETTINGS_DESC"), 50, 90, 400, 30, this, ATSwingUtils.FONT_NORMAL);

        // addLabel(p3, m_ic.getMessage("LAF_SETTINGS_DESC"), 20, 10, 400, 30);

        ATSwingUtils.getLabel(m_ic.getMessage("SELECTED_LAF_TYPE_NAME"), 50, 130, 400, 30, this,
            ATSwingUtils.FONT_NORMAL_BOLD);

        // addLabel(p3, m_ic.getMessage("SELECTED_LAF_TYPE_NAME") + ":", 20, 40,
        // 150, 25);

        this.cb_lf_type = new JComboBox(m_dbc.getAvailableLFs());
        this.cb_lf_type.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL));
        this.cb_lf_type.setBounds(50, 160, 400, 25);
        this.cb_lf_type.addItemListener(this);
        this.add(this.cb_lf_type);

        ATSwingUtils.getLabel(m_ic.getMessage("SELECTED_LAF_TYPE_CLASS"), 50, 200, 400, 30, this,
            ATSwingUtils.FONT_NORMAL_BOLD);

        // addLabel(p3, m_ic.getMessage("SELECTED_LAF_TYPE_CLASS") + ":", 20,
        // 70, 150, 25);
        this.cb_lf_type_class = new JComboBox(m_dbc.getAvailableLFsClass());
        this.cb_lf_type_class.setBounds(50, 230, 400, 23);
        this.cb_lf_type_class.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL));
        this.cb_lf_type_class.addItemListener(this);
        this.add(this.cb_lf_type_class);

        // this.cb_lf_type_class.setSelectedIndex(idx);

        ATSwingUtils.getLabel(m_ic.getMessage("SELECTED_SKINLF_DEF"), 50, 270, 400, 30, this,
            ATSwingUtils.FONT_NORMAL_BOLD);

        // addLabel(p3, m_ic.getMessage("SELECTED_SKINLF_DEF") + ":", 20, 100,
        // 150, 25);

        this.tf_lf = new JTextField();
        this.tf_lf.setBounds(50, 300, 400, 23);
        this.tf_lf.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL));
        this.tf_lf.setText(this.m_dbc.getSelectedLFSkin());
        this.tf_lf.setEditable(false);
        this.add(this.tf_lf);

        this.b_browse = new JButton(m_ic.getMessage("BROWSE") + "...");
        this.b_browse.setBounds(330, 275, 120, 22);
        this.b_browse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                File f = new File("./../data/skinlf_themes");

                System.out.println(f);
                System.out.println(f.getAbsolutePath());

                JFileChooser jfc = new JFileChooser();
                jfc.setCurrentDirectory(f);
                jfc.setDialogTitle(m_ic.getMessage("SELECT_SKINLF_SKIN"));
                jfc.setDialogType(JFileChooser.CUSTOM_DIALOG);
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

                processJFileChooser(jfc);

                int res = jfc.showDialog(parent, m_ic.getMessage("SELECT"));

                if (res == JFileChooser.APPROVE_OPTION)
                {
                    tf_lf.setText(jfc.getSelectedFile().getName());
                }
            }
        });
        this.add(this.b_browse);

        int idx = this.m_dbc.getSelectedLFIndex();
        this.cb_lf_type.setSelectedIndex(idx);

        // this.add(p3);

    }

    boolean in_change = false;

    /**
     * Item State Changed
     * 
     * @see ggc.gui.panels.prefs.AbstractPrefOptionsPanel#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent e)
    {

        if (in_change)
            return;
        else
        {
            in_change = true;
        }

        JComboBox cb = (JComboBox) e.getSource();

        int index = cb.getSelectedIndex();

        if (this.cb_lf_type.equals(cb))
        {
            this.cb_lf_type_class.setSelectedIndex(index);
        }
        else
        {
            this.cb_lf_type.setSelectedIndex(index);
        }

        boolean skin = m_dbc.isSkinLFSelected(index);

        // this.tf_lf.setEnabled(skin);
        this.b_browse.setEnabled(skin);

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
        /*
         * settings.setUserName(fieldUserName.getText());
         * settings.setLanguage(langBox.getSelectedItem().toString());
         * this.m_dbc.setSelectedDatabaseIndex(this.cb_database.getSelectedIndex(
         * ));
         * this.m_dbc.setSelectedLF(this.cb_lf_type.getSelectedIndex(),
         * this.tf_lf.getText());
         */
        this.m_dbc.setSelectedLF(this.cb_lf_type.getSelectedIndex(), this.tf_lf.getText());
    }

    private void processJFileChooser(Container c)
    {
        Component[] comps = c.getComponents();

        for (Component comp : comps)
        {

            if (comp instanceof JPanel)
            {
                processJFileChooser((Container) comp);
            }

            if (comp instanceof JButton)
            {
                JButton b = (JButton) comp;

                String ttText = b.getToolTipText();
                // x String buttonText = b.getText();

                if (ttText != null
                        && (ttText.equals("Create New Folder") || ttText.equals("Desktop") || ttText
                                .equals("Up One Level")))
                {
                    b.setEnabled(false);
                }
            }

            if (comp instanceof JComboBox)
            {
                JComboBox box = (JComboBox) comp;
                String s = box.getSelectedItem().toString();
                if (s.indexOf("skinlf_themes") != -1)
                {
                    box.setEnabled(false);
                }
            }

        }
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
