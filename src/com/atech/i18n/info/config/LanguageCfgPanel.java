package com.atech.i18n.info.config;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.info.LanguageInfo;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ConfigurationDialog - Configuration Main
 * 
 *  This is main dialog for configurations, from here all configuration is done.
 *  It should be used only from within ConfigurationDialog.
 *  
 *  Copyright (C) 2005  Andy (Aleksander) Rozman (Atech-Software)
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

public class LanguageCfgPanel extends JDialog implements ActionListener, /*
                                                                          * ListSelectionListener
                                                                          * ,
                                                                          */ItemListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 6377345419863400291L;
    /*
     * Variables needed through whole program
     */
    ATDataAccessAbstract m_da;

    Font font_normal = null;
    Font font_normal_b = null;
    Font font_big = null;
    JPanel panels[] = null;

    int cur_index = 0;
    JList list_users, list_parishes, list_ppersons, list_masses;
    JTextField tf_selLF, tf_nameWeb = null;
    JButton bt_browse = null, bt_help;
    JComboBox cb_LFs, cb_DBs = null, cb_WebLangs = null;
    JList list;
    JPanel panel_main;

    I18nControlAbstract ic;
    LanguageInfo langInfo = null;


    /**
     * Constructor
     * @param da instance of DataAccess
     */
    public LanguageCfgPanel(ATDataAccessAbstract da)
    {
        super();

        ic = da.getI18nControlInstance();
        m_da = da;

        ATSwingUtils.initLibrary();

        font_normal = ATSwingUtils.getFont(1);
        font_normal_b = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);
        font_big = ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD);
        langInfo = m_da.getLanguageInfo();
        // this.loadWebSettings();

        createPanelLanguage();

        // Web Config
        // createPanelWebConfig();

        // loadData();
        // cmdConfig();

    }

    /**
     * Creates Db Panel (for selecting database)
     */
    /*
     * public void createPanelDb()
     * {
     * JPanel panel = new JPanel();
     * panel.setBounds(140, 30, 420, 330);
     * panel.setLayout(null);
     * JLabel label = new JLabel(i18nControl.getMessage("DB_SETTINGS"));
     * label.setFont(font_big);
     * label.setBounds(0, 05, 420, 36);
     * label.setHorizontalAlignment(JLabel.CENTER);
     * panel.add(label);
     * label = new JLabel(i18nControl.getMessage("DB_DESC"));
     * label.setFont(font_normal);
     * label.setBounds(40, 45, 360, 100);
     * panel.add(label);
     * label = new JLabel(i18nControl.getMessage("NOTE_RESTART"));
     * label.setFont(font_normal);
     * label.setBounds(40, 140, 360, 50);
     * panel.add(label);
     * label = new JLabel(i18nControl.getMessage("SELECT_DATABASE"));
     * label.setFont(dataAccess.getFont(DataAccess.FONT_NORMAL_BOLD));
     * label.setBounds(40, 230, 340, 25);
     * panel.add(label);
     * cb_DBs = new
     * JComboBox(dataAccess.m_config_file.getAllDatabasesNamesPlusAsArray());
     * cb_DBs.setBounds(40, 260, 220, 25);
     * cb_DBs.setSelectedIndex(dataAccess.getSelectedDbIndex());
     * cb_DBs.setFont(font_normal);
     * cb_DBs.addItemListener(this);
     * cb_DBs.setActionCommand("select_db");
     * // cb_DBs.setEnabled(!dataAccess.isDemoVersion());
     * panel.add(cb_DBs);
     * if (dataAccess.isDemoVersion())
     * {
     * cb_DBs.setSelectedIndex(0);
     * cb_DBs.setEnabled(false);
     * }
     * else
     * cb_DBs.setEnabled(true);
     * panel.setVisible(false);
     * panels[0] = panel;
     * }
     */

    /**
     * Creates Look Panel (for setting Look and Feel)
     */
    /*
     * public void createPanelLook()
     * {
     * JPanel panel = new JPanel();
     * panel.setBounds(140, 30, 420, 330);
     * panel.setLayout(null);
     * JLabel label = new JLabel(i18nControl.getMessage("LOOK"));
     * label.setFont(font_big);
     * label.setBounds(0, 05, 420, 36);
     * label.setHorizontalAlignment(JLabel.CENTER);
     * panel.add(label);
     * label = new JLabel(i18nControl.getMessage("LOOK_DESC"));
     * label.setFont(font_normal);
     * label.setBounds(40, 45, 360, 100);
     * panel.add(label);
     * label = new JLabel(i18nControl.getMessage("NOTE_RESTART"));
     * label.setFont(font_normal);
     * label.setBounds(40, 140, 360, 50);
     * panel.add(label);
     * label = new JLabel(i18nControl.getMessage("SELECT_LOOK"));
     * label.setFont(dataAccess.getFont(DataAccess.FONT_NORMAL_BOLD));
     * label.setBounds(40, 190, 340, 25);
     * panel.add(label);
     * cb_LFs = new JComboBox(dataAccess.m_config_file.getAvailableLFs());
     * cb_LFs.setBounds(40, 215, 120, 25);
     * cb_LFs.setSelectedItem(dataAccess.m_config_file.selected_LF_Name);
     * cb_LFs.setFont(font_normal);
     * cb_LFs.addItemListener(this);
     * cb_LFs.setActionCommand("select_lf");
     * panel.add(cb_LFs);
     * label = new JLabel(i18nControl.getMessage("SELECT_SKINLF_LOOK"));
     * label.setFont(dataAccess.getFont(DataAccess.FONT_NORMAL_BOLD));
     * label.setBounds(40, 250, 340, 25);
     * panel.add(label);
     * tf_selLF = new JTextField();
     * tf_selLF.setBounds(40, 275, 260, 25);
     * tf_selLF.setText(dataAccess.m_config_file.skinLFSelected);
     * tf_selLF.setEnabled(false);
     * tf_selLF.setFont(font_normal);
     * panel.add(tf_selLF);
     * bt_browse = new JButton(i18nControl.getMessage("BROWSE") + "...");
     * bt_browse.setBounds(300, 272, 120, 30);
     * bt_browse.setActionCommand("lf_browse");
     * bt_browse.addActionListener(this);
     * bt_browse.setFont(font_normal);
     * panel.add(bt_browse);
     * if (!dataAccess.m_config_file.selected_LF_Name.equals("SkinLF"))
     * {
     * bt_browse.setEnabled(false);
     * }
     * panel.setVisible(false);
     * panels[1] = panel;
     * }
     */


    /**
     * Creates Users Panel (for adding Users)
     */
    /*
     * public void createPanelUsers()
     * {
     * JPanel panel = new JPanel();
     * panel.setBounds(140, 30, 420, 330);
     * panel.setLayout(null);
     * JLabel label = new JLabel(i18nControl.getMessage("USERS"));
     * label.setFont(font_big);
     * label.setBounds(0, 15, 420, 36);
     * label.setHorizontalAlignment(JLabel.CENTER);
     * panel.add(label);
     * list_users = new CheckBoxList();
     * list_users.setFont(font_normal);
     * JScrollPane pane = new JScrollPane(list_users);
     * pane.setBounds(40, 80, 340, 180);
     * panel.add(pane);
     * JButton button = new JButton(i18nControl.getMessage("ADD"));
     * button.setBounds(60, 270, 100, 35);
     * button.setActionCommand("users_add");
     * button.setFont(font_normal);
     * button.addActionListener(this);
     * panel.add(button);
     * button = new JButton(i18nControl.getMessage("EDIT"));
     * button.setBounds(160, 270, 100, 35);
     * button.setActionCommand("users_edit");
     * button.setFont(font_normal);
     * button.addActionListener(this);
     * panel.add(button);
     * button = new JButton(i18nControl.getMessage("REMOVE"));
     * button.setBounds(260, 270, 100, 35);
     * button.setActionCommand("users_remove");
     * button.setFont(font_normal);
     * button.addActionListener(this);
     * panel.add(button);
     * panel.setVisible(false);
     * panels[4] = panel;
     * }
     */
    /**
     * Creates Users Panel (for adding Users)
     */
    public void createPanelLanguage()
    {
        ATSwingUtils.initLibrary();

        JPanel panel = new JPanel();
        panel.setBounds(140, 30, 420, 330);
        panel.setLayout(null);

        JLabel label = new JLabel(ic.getMessage("LANGUAGE"));
        label.setFont(font_big);
        label.setBounds(0, 05, 420, 36);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        label = new JLabel(ic.getMessage("LANGPACK_VERSION") + ":");
        label.setFont(font_normal_b);
        label.setBounds(40, 50, 360, 25);
        panel.add(label);

        label = new JLabel("v" + langInfo.lp_version + " (" + langInfo.lp_release + ")");
        label.setFont(font_normal);
        label.setBounds(180, 50, 360, 25);
        panel.add(label);

        label = new JLabel(ic.getMessage("LANG_WITH_HELP") + ":");
        label.setFont(font_normal_b);
        label.setBounds(60, 70, 360, 25);
        panel.add(label);

        label = new JLabel("" + langInfo.lp_langs_with_help);
        label.setFont(font_normal);
        label.setBounds(200, 70, 360, 25);
        panel.add(label);

        label = new JLabel(ic.getMessage("LANG_WITHOUT_HELP") + ":");
        label.setFont(font_normal_b);
        label.setBounds(60, 90, 360, 25);
        panel.add(label);

        label = new JLabel("" + langInfo.lp_langs_without_help);
        label.setFont(font_normal);
        label.setBounds(200, 90, 360, 25);
        panel.add(label);

        label = new JLabel(ic.getMessage("LANG_DESC"));
        label.setFont(font_normal);
        label.setBounds(40, 110, 360, 100);
        panel.add(label);

        label = new JLabel(ic.getMessage("NOTE_RESTART"));
        label.setFont(font_normal);
        label.setBounds(40, 200, 360, 50);
        panel.add(label);

        label = new JLabel(ic.getMessage("SELECT_LANGUAGE"));
        label.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD));
        label.setBounds(40, 255, 340, 25);
        panel.add(label);

        cb_DBs = new JComboBox(langInfo.availableLang);
        cb_DBs.setBounds(40, 280, 300, 25);
        cb_DBs.setSelectedIndex(langInfo.getSelectedIndex() - 1);
        cb_DBs.setFont(font_normal);
        cb_DBs.addItemListener(this);
        cb_DBs.setActionCommand("select_lang");
        panel.add(cb_DBs);

        panel.setVisible(false);
        panels[5] = panel;

    }


    /**
     * Action Listener
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("close"))
        {
            this.dispose();
        }
        else if (action.equals("help"))
        {
            System.out.println("Command 'help' N/A");
        }

    }


    /**
     * Invoked when an item has been selected or deselected by the user. The
     * code written for this method performs the operations that need to occur
     * when an item is selected (or deselected).
     */
    public void itemStateChanged(ItemEvent e)
    {

        JComboBox cb = (JComboBox) e.getSource();
        String action = cb.getActionCommand();

        if (action.equals("select_lang"))
        {

            /*
             * if (dataAccess.isDemoVersion()) { dataAccess.selected_db=0;
             * dataAccess.saveConfig(); return; }
             */

            int num = cb.getSelectedIndex();
            // String item = (String)cb.getSelectedItem();

            // int num = Integer.parseInt(item.substring(0,1));
            num++;

            // // FIXME might not work
            // if
            // (!dataAccess.getLanguageInfo().getSelectedLanguage().equals(cb.getSelectedItem()))
            // {
            // dataAccess.getLanguageInfo().setSelectedLanguage((String)
            // cb.getSelectedItem());
            // }

            if (this.langInfo.getSelectedIndex() != num)
            {
                this.langInfo.setSelectedIndex(num);
            }

            // if (dataAccess.getSelectedLangIndex() != num)
            // {
            // dataAccess.setSelectedLangIndex(num);
            // }
        }

        // select_lang

    }

}
