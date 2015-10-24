package com.atech.gui_fw.config;

import java.awt.*;

import javax.swing.*;

import com.atech.db.hibernate.tool.DbToolApplicationAbstract;
import com.atech.help.HelpCapable;
import com.atech.i18n.mgr.LanguageManager;
import com.atech.utils.ATDataAccessLMAbstract;
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

public class ConfigLanguagePanel extends AbstractConfigPanel implements HelpCapable
{

    private static final long serialVersionUID = 9031582129384616037L;
    // private JTextField fieldUserName;
    // private JComboBox langBox, cb_database, cb_lf_type, cb_lf_type_class;
    // private JTextField tf_lf;
    // private JButton b_browse;
    DbToolApplicationAbstract m_dbc = null; // dataAccess.getDbConfig();
    JComboBox cb_language;
    ATDataAccessLMAbstract da = null;
    LanguageManager lang_mgr = null;


    /**
     * Constructor
     * @param acc 
     * 
     * @param dia
     */
    public ConfigLanguagePanel(AbstractConfigurationContext acc)
    {
        super(acc);

        da = (ATDataAccessLMAbstract) acc.getDataAccessInstance();

        this.m_dbc = acc.getDataAccessInstance().getDbToolAbstract();
        this.m_dbc.loadConfig();

        this.lang_mgr = da.getLanguageManager();

        ATSwingUtils.initLibrary();
        initSimple();
        // init();

    }


    @SuppressWarnings("unused")
    private void init()
    {
        this.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 10, 470, 450);
        panel.setLayout(null);

        // ATSwingUtils

        // ATSwingUtils.getTitleLabel(m_ic.getMessage("LANGUAGE"),
        // 0, 0, 500, 36, panel, ATSwingUtils.FONT_BIG_BOLD);

        JLabel label = ATSwingUtils.getTitleLabel(m_ic.getMessage("CFG_LANG"), 0, 25, 520, 36, this,
            ATSwingUtils.FONT_BIG_BOLD);

        /*
         * JLabel label = new JLabel(i18nControl.getMessage("LANGUAGE"));
         * label.setFont(font_big);
         * label.setBounds(0, 05, 420, 36);
         * label.setHorizontalAlignment(JLabel.CENTER);
         * panel.add(label);
         */

        ATSwingUtils.getLabel(m_ic.getMessage("LANGPACK_VERSION") + ":", 40, 50, 360, 25, panel,
            ATSwingUtils.FONT_NORMAL_BOLD);

        /*
         * XA
         * label = new JLabel(i18nControl.getMessage("LANGPACK_VERSION") + ":");
         * label.setFont(font_normal_b);
         * label.setBounds(40, 50, 360, 25);
         * panel.add(label);
         */
        /*
         * label = new JLabel("v" + langInfo.languagePackVersion + " (" +
         * langInfo.languagePackRelease + ")");
         * label.setFont(font_normal);
         * label.setBounds(180, 50, 360, 25);
         * panel.add(label);
         */

        ATSwingUtils.getLabel(m_ic.getMessage("LANG_WITH_HELP") + ":", 60, 70, 360, 25, panel,
            ATSwingUtils.FONT_NORMAL_BOLD);

        /*
         * label = new JLabel(i18nControl.getMessage("LANG_WITH_HELP") + ":");
         * label.setFont(font_normal_b);
         * label.setBounds(60, 70, 360, 25);
         * panel.add(label);
         */
        /*
         * label = new JLabel("" + langInfo.languagePackLanguagesWithHelp);
         * label.setFont(font_normal);
         * label.setBounds(200, 70, 360, 25);
         * panel.add(label);
         */

        ATSwingUtils.getLabel(m_ic.getMessage("LANG_WITHOUT_HELP") + ":", 60, 90, 360, 25, panel,
            ATSwingUtils.FONT_NORMAL_BOLD);

        /*
         * label = new JLabel(i18nControl.getMessage("LANG_WITHOUT_HELP") +
         * ":");
         * label.setFont(font_normal_b);
         * label.setBounds(60, 90, 360, 25);
         * panel.add(label);
         */
        /*
         * label = new JLabel("" + langInfo.languagePackLanguagesWithoutHelp);
         * label.setFont(font_normal);
         * label.setBounds(200, 90, 360, 25);
         * panel.add(label);
         */

        ATSwingUtils.getLabel(m_ic.getMessage("LANG_DESC"), 40, 100, 430, 200, panel, ATSwingUtils.FONT_NORMAL);

        ATSwingUtils.getLabel(m_ic.getMessage("NOTE_RESTART"), 40, 280, 360, 50, panel, ATSwingUtils.FONT_NORMAL);
        /*
         * ATSwingUtils.getLabel(m_ic.getMessage("SELECT_LANGUAGE"),
         * 40, 345, 340, 25,
         * panel, ATSwingUtils.FONT_NORMAL_BOLD);
         */

        /*
         * cb_DBs = new JComboBox(langInfo.availableLang);
         * cb_DBs.setBounds(40, 280, 300, 25);
         * cb_DBs.setSelectedIndex(dataAccess.getSelectedLangIndex() - 1);
         * cb_DBs.setFont(font_normal);
         * cb_DBs.addItemListener(this);
         * cb_DBs.setActionCommand("select_lang");
         * panel.add(cb_DBs);
         */

        ATSwingUtils.getLabel(m_ic.getMessage("NOTE_LANG_FEATURE_NOT_WORKING"), 40, 325, 430, 70, panel,
            ATSwingUtils.FONT_NORMAL_BOLD);

        this.add(panel);

    }


    private void initSimple()
    {
        this.setLayout(null);

        /*
         * JPanel panel = new JPanel();
         * panel.setBounds(0, 10, 470, 450);
         * panel.setLayout(null);
         */

        // ATSwingUtils

        // ATSwingUtils.getTitleLabel(m_ic.getMessage("LANGUAGE"),
        // 0, 0, 500, 36, panel, ATSwingUtils.FONT_BIG_BOLD);

        ATSwingUtils.getTitleLabel(m_ic.getMessage("LANGUAGE_SETTINGS"), 0, 25, 520, 36, this,
            ATSwingUtils.FONT_BIG_BOLD);

        JLabel label = ATSwingUtils.getLabel(m_ic.getMessage("LANGUAGE_DESC_SIMPLE"), 50, 90, 400, 250, this,
            ATSwingUtils.FONT_NORMAL);
        label.setVerticalAlignment(SwingConstants.TOP);

        ATSwingUtils.getLabel(m_ic.getMessage("SELECT_LANGUAGE") + ":", 50, 280, 400, 25, this,
            ATSwingUtils.FONT_NORMAL_BOLD);

        // da.getLanguageManager().loadData();

        cb_language = ATSwingUtils.getComboBox(this.lang_mgr.getAvailableLanguages(), 50, 310, 400, 25, this,
            ATSwingUtils.FONT_NORMAL);
        cb_language.setSelectedIndex(this.lang_mgr.getSelectedLanguageFromArray());

        // dataAccess.getL
        /*
         * langBox = new JComboBox(dataAccess.getL.getAvailableLanguages());
         * langBox.setSelectedIndex(dataAccess.getSelectedLanguageIndex());
         * langBox.setBounds(120, 50, 150, 25);
         */

        /*
         * label = new JLabel();
         * label.setBounds(20, 50, 100, 25);
         * label.setText(m_ic.getMessage("YOUR_LANGUAGE") + ":");
         * this.add(label);
         */
        // a.add(langBox);

        /*
         * ATSwingUtils.getLabel(m_ic.getMessage("LANGPACK_VERSION") + ":",
         * 40, 50, 360, 25,
         * panel, ATSwingUtils.FONT_NORMAL_BOLD);
         * ATSwingUtils.getLabel(m_ic.getMessage("LANG_WITH_HELP") + ":",
         * 60, 70, 360, 25,
         * panel, ATSwingUtils.FONT_NORMAL_BOLD);
         * ATSwingUtils.getLabel(m_ic.getMessage("LANG_WITHOUT_HELP") + ":",
         * 60, 90, 360, 25,
         * panel, ATSwingUtils.FONT_NORMAL_BOLD);
         * ATSwingUtils.getLabel(m_ic.getMessage("LANG_DESC"),
         * 40, 100, 430, 200,
         * panel, ATSwingUtils.FONT_NORMAL);
         * ATSwingUtils.getLabel(m_ic.getMessage("NOTE_RESTART"),
         * 40, 280, 360, 50,
         * panel, ATSwingUtils.FONT_NORMAL);
         * ATSwingUtils.getLabel(m_ic.getMessage(
         * "NOTE_LANG_FEATURE_NOT_WORKING")
         * ,
         * 40, 325, 430, 70,
         * panel, ATSwingUtils.FONT_NORMAL_BOLD);
         * this.add(panel);
         */

    }


    /**
     * Save Properties
     * 
     * @see ggc.gui.panels.prefs.AbstractPrefOptionsPanel#saveProps()
     */
    @Override
    public void saveConfig()
    {
        this.m_dbc
                .setSelectedLanguage(this.lang_mgr.getNameFromDescription((String) this.cb_language.getSelectedItem()));

        // FIXME
        System.out.println("PrefLanguagePane::saveProps(): N/A");
        // this.m_dbc.setSelectedDatabaseIndex(this.cb_database.getSelectedIndex());

        /*
         * settings.setUserName(fieldUserName.getText());
         * settings.setLanguage(langBox.getSelectedItem().toString());
         * this.m_dbc.setSelectedDatabaseIndex(this.cb_database.
         * getSelectedIndex(
         * ));
         * this.m_dbc.setSelectedLF(this.cb_lf_type.getSelectedIndex(),
         * this.tf_lf.getText());
         */
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
        return "GGC_Prefs_Language";
    }

}
