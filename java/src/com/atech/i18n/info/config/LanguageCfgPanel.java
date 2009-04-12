package com.atech.i18n.info.config;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.info.LanguageInfo;
import com.atech.utils.ATDataAccessAbstract;

/*
 * ConfigurationDialog - Configuration Main
 * 
 * This is main dialog for configurations, from here all configuration is done.
 * It should be used only from within ConfigurationDialog.
 * 
 * This class is part of PIS (Parish Information System) package.
 * 
 * @author Andy (Aleksander) Rozman {andy@triera.net}
 * 
 * @version 1.0
 */

public class LanguageCfgPanel extends JDialog implements ActionListener, /*ListSelectionListener,*/ ItemListener
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


        font_normal = m_da.getFont(1);
        font_normal_b = m_da.getFont(ATDataAccessAbstract.FONT_NORMAL_BOLD);
        font_big = m_da.getFont(ATDataAccessAbstract.FONT_BIG_BOLD);
        langInfo = m_da.getLanguageInfo();
        // this.loadWebSettings();

        createPanelLanguage();

        // Web Config
        // createPanelWebConfig();

        //loadData();
        //cmdConfig();

    }



    /**
     * Creates Db Panel (for selecting database)
     */
/*    public void createPanelDb()
    {

        JPanel panel = new JPanel();
        panel.setBounds(140, 30, 420, 330);
        panel.setLayout(null);

        JLabel label = new JLabel(ic.getMessage("DB_SETTINGS"));
        label.setFont(font_big);
        label.setBounds(0, 05, 420, 36);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        label = new JLabel(ic.getMessage("DB_DESC"));
        label.setFont(font_normal);
        label.setBounds(40, 45, 360, 100);
        panel.add(label);

        label = new JLabel(ic.getMessage("NOTE_RESTART"));
        label.setFont(font_normal);
        label.setBounds(40, 140, 360, 50);
        panel.add(label);

        label = new JLabel(ic.getMessage("SELECT_DATABASE"));
        label.setFont(m_da.getFont(DataAccess.FONT_NORMAL_BOLD));
        label.setBounds(40, 230, 340, 25);
        panel.add(label);

        cb_DBs = new JComboBox(m_da.m_config_file.getAllDatabasesNamesPlusAsArray());
        cb_DBs.setBounds(40, 260, 220, 25);
        cb_DBs.setSelectedIndex(m_da.getSelectedDbIndex());
        cb_DBs.setFont(font_normal);
        cb_DBs.addItemListener(this);
        cb_DBs.setActionCommand("select_db");
        // cb_DBs.setEnabled(!m_da.isDemoVersion());
        panel.add(cb_DBs);

        if (m_da.isDemoVersion())
        {
            cb_DBs.setSelectedIndex(0);
            cb_DBs.setEnabled(false);
        }
        else
            cb_DBs.setEnabled(true);

        panel.setVisible(false);
        panels[0] = panel;
    }*/

    /**
     * Creates Look Panel (for setting Look and Feel)
     */
/*    public void createPanelLook()
    {
        JPanel panel = new JPanel();
        panel.setBounds(140, 30, 420, 330);
        panel.setLayout(null);

        JLabel label = new JLabel(ic.getMessage("LOOK"));
        label.setFont(font_big);
        label.setBounds(0, 05, 420, 36);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        label = new JLabel(ic.getMessage("LOOK_DESC"));
        label.setFont(font_normal);
        label.setBounds(40, 45, 360, 100);
        panel.add(label);

        label = new JLabel(ic.getMessage("NOTE_RESTART"));
        label.setFont(font_normal);
        label.setBounds(40, 140, 360, 50);
        panel.add(label);

        label = new JLabel(ic.getMessage("SELECT_LOOK"));
        label.setFont(m_da.getFont(DataAccess.FONT_NORMAL_BOLD));
        label.setBounds(40, 190, 340, 25);
        panel.add(label);

        cb_LFs = new JComboBox(m_da.m_config_file.getAvailableLFs());
        cb_LFs.setBounds(40, 215, 120, 25);
        cb_LFs.setSelectedItem(m_da.m_config_file.selected_LF_Name);
        cb_LFs.setFont(font_normal);
        cb_LFs.addItemListener(this);
        cb_LFs.setActionCommand("select_lf");
        panel.add(cb_LFs);

        label = new JLabel(ic.getMessage("SELECT_SKINLF_LOOK"));
        label.setFont(m_da.getFont(DataAccess.FONT_NORMAL_BOLD));
        label.setBounds(40, 250, 340, 25);
        panel.add(label);

        tf_selLF = new JTextField();
        tf_selLF.setBounds(40, 275, 260, 25);
        tf_selLF.setText(m_da.m_config_file.skinLFSelected);
        tf_selLF.setEnabled(false);
        tf_selLF.setFont(font_normal);
        panel.add(tf_selLF);

        bt_browse = new JButton(ic.getMessage("BROWSE") + "...");
        bt_browse.setBounds(300, 272, 120, 30);
        bt_browse.setActionCommand("lf_browse");
        bt_browse.addActionListener(this);
        bt_browse.setFont(font_normal);
        panel.add(bt_browse);

        if (!m_da.m_config_file.selected_LF_Name.equals("SkinLF"))
        {
            bt_browse.setEnabled(false);
        }

        panel.setVisible(false);
        panels[1] = panel;
    }*/


    /**
     * Creates Users Panel (for adding Users)
     */
/*    public void createPanelUsers()
    {

        JPanel panel = new JPanel();
        panel.setBounds(140, 30, 420, 330);
        panel.setLayout(null);

        JLabel label = new JLabel(ic.getMessage("USERS"));
        label.setFont(font_big);
        label.setBounds(0, 15, 420, 36);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        list_users = new JList();
        list_users.setFont(font_normal);

        JScrollPane pane = new JScrollPane(list_users);
        pane.setBounds(40, 80, 340, 180);
        panel.add(pane);

        JButton button = new JButton(ic.getMessage("ADD"));
        button.setBounds(60, 270, 100, 35);
        button.setActionCommand("users_add");
        button.setFont(font_normal);
        button.addActionListener(this);
        panel.add(button);

        button = new JButton(ic.getMessage("EDIT"));
        button.setBounds(160, 270, 100, 35);
        button.setActionCommand("users_edit");
        button.setFont(font_normal);
        button.addActionListener(this);
        panel.add(button);

        button = new JButton(ic.getMessage("REMOVE"));
        button.setBounds(260, 270, 100, 35);
        button.setActionCommand("users_remove");
        button.setFont(font_normal);
        button.addActionListener(this);
        panel.add(button);

        panel.setVisible(false);
        panels[4] = panel;

    }
*/
    /**
     * Creates Users Panel (for adding Users)
     */
    public void createPanelLanguage()
    {

        JPanel panel = new JPanel();
        panel.setBounds(140, 30, 420, 330);
        panel.setLayout(null);

        JLabel label = new JLabel(ic.getMessage("LANGUAGE"));
        label.setFont(font_big);
        label.setBounds(0, 05, 420, 36);
        label.setHorizontalAlignment(JLabel.CENTER);
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
        label.setFont(m_da.getFont(ATDataAccessAbstract.FONT_NORMAL_BOLD));
        label.setBounds(40, 255, 340, 25);
        panel.add(label);

        cb_DBs = new JComboBox(langInfo.availableLang);
        cb_DBs.setBounds(40, 280, 300, 25);
        cb_DBs.setSelectedIndex(m_da.getSelectedLangIndex() - 1);
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
             * if (m_da.isDemoVersion()) { m_da.selected_db=0;
             * m_da.saveConfig(); return; }
             */

            int num = cb.getSelectedIndex();
            // String item = (String)cb.getSelectedItem();

            // int num = Integer.parseInt(item.substring(0,1));
            num++;

            if (m_da.getSelectedLangIndex() != num)
            {
                m_da.setSelectedLangIndex(num);
            }
        }

        // select_lang

    }

    
    
    
}
