package com.atech.db.hibernate.tool;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.atech.utils.ATDataAccessAbstract;

/**
 * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 */

// WORK IN PROGRESS, PLEASE DO NOT TOUCH
// andyrozman

// TO-DO LIST:
// --automatic update of url paramater
// actions tab
// settings tab
// full panel
// actions process
// reading of settings handle in library
// writing of settings handle in library
// not read-only

public class PanelDatabaseSet extends JPanel implements ActionListener, /*
                                                                         * DocumentListener
                                                                         * ,
                                                                         */KeyListener
{

    private static final long serialVersionUID = -8415000279643694270L;
    I18nControlDbT ic = I18nControlDbT.getInstance();
    DbToolAccess m_da = null;

    Font font_big, font_normal, font_normal_b;
    JLabel label;
    JButton button;

    JLabel label_class, label_dialect;
    JTextField tf_url;

    JComboBox cb_databases = null;

    // NutritionTreeDialog m_dialog = null;
    DatabaseSettings m_database_settings = null;

    Hashtable<String, String> settings_table = new Hashtable<String, String>();
    ArrayList<String> url_list = new ArrayList<String>();

    Hashtable<String, JLabel> parameters_label = new Hashtable<String, JLabel>();
    Hashtable<String, JTextField> parameters_textfield = new Hashtable<String, JTextField>();

    int posy = 320;

    PanelDbAction db_action = null;
    DbTool m_dialog;

    /**
     * Constructor
     * 
     * @param dia
     */
    public PanelDatabaseSet(DbTool dia)
    {

        super();

        m_dialog = dia;
        m_da = DbToolAccess.getInstance();

        font_big = m_da.getFont(ATDataAccessAbstract.FONT_BIG_BOLD);
        font_normal_b = m_da.getFont(ATDataAccessAbstract.FONT_NORMAL_BOLD);
        font_normal = m_da.getFont(ATDataAccessAbstract.FONT_NORMAL);

        createPanel();

    }

    private void createPanel()
    {

        this.setSize(500, 560);
        this.setLayout(null);

        // x Font fnt_18 = new Font("Times New Roman", Font.BOLD, 18);

        label = new JLabel(ic.getMessage("DB_CONFIGURATION"));
        label.setBounds(0, 25, 500, 40);
        label.setFont(font_big);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label, null);

        label = new JLabel(ic.getMessage("DB_PRESS_HELP")); // "DB_CONFIG_DESC"));
        label.setBounds(40, 90, 410, 25);
        label.setFont(font_normal);
        this.add(label, null);

        label = new JLabel(ic.getMessage("DATABASE_TYPE") + ":");
        label.setBounds(40, 140, 200, 25);
        label.setFont(font_normal);
        this.add(label, null);

        cb_databases = new JComboBox(m_da.getAvailableDatabases());
        cb_databases.setBounds(200, 140, 190, 25);
        cb_databases.setFont(font_normal);
        this.add(cb_databases, null);

        label = new JLabel(ic.getMessage("DATABASE_CLASSNAME") + ":");
        label.setBounds(40, 170, 200, 25);
        label.setFont(font_normal);
        this.add(label, null);

        label_class = new JLabel("");
        label_class.setBounds(200, 170, 200, 25);
        label_class.setFont(font_normal);
        this.add(label_class, null);

        label = new JLabel(ic.getMessage("HIBERNATE_DIALECT") + ":");
        label.setBounds(40, 200, 200, 25);
        label.setFont(font_normal);
        this.add(label, null);

        label_dialect = new JLabel("");
        label_dialect.setBounds(200, 200, 200, 25);
        label_dialect.setFont(font_normal);
        this.add(label_dialect, null);

        label = new JLabel(ic.getMessage("JDBC_URL") + ":");
        label.setBounds(40, 230, 200, 25);
        label.setFont(font_normal);
        this.add(label, null);

        tf_url = new JTextField();
        tf_url.setBounds(40, 255, 400, 25);
        tf_url.setFont(font_normal);
        tf_url.setEditable(false);
        this.add(tf_url, null);

        this.db_action = new PanelDbAction(this, m_dialog);
        this.add(db_action, null);

        // TODO : remove this
        /*
         * label = new JLabel(ic.getMessage("USERNAME") + ":");
         * label.setBounds(40, 290, 200, 25);
         * label.setFont(font_normal);
         * this.add(label, null);
         * JTextField tf_username = new JTextField();
         * tf_username.setBounds(200, 290, 200, 25);
         * tf_username.setFont(font_normal);
         * tf_username.setActionCommand("textfield");
         * tf_username.addActionListener(this);
         * this.add(tf_username, null);
         * this.parameters_textfield.put("<username>", tf_username);
         * label = new JLabel(ic.getMessage("PASSWORD") + ":");
         * label.setBounds(40, 320, 200, 25);
         * label.setFont(font_normal);
         * this.add(label, null);
         * JTextField tf_password = new JTextField();
         * tf_password.setBounds(200, 320, 180, 25);
         * tf_password.setFont(font_normal);
         * tf_password.setActionCommand("textfield");
         * tf_password.addActionListener(this);
         * this.add(tf_password, null);
         * this.parameters_textfield.put("<password>", tf_password);
         */

        /*
         * button = new JButton(ic.getMessage("ADD_"));
         * button.setBounds(110, 190, 170, 25);
         * button.setFont(font_normal);
         * button.addActionListener(this);
         * button.setActionCommand("add_");
         * this.add(button);
         */
        /*
         * button = new JButton(ic.getMessage("ADD_"));
         * button.setBounds(110, 220, 170, 25);
         * button.setFont(font_normal);
         * button.setActionCommand("add_p");
         * button.addActionListener(this);
         * //this.add(button);
         * label = new JLabel(ic.getMessage("EDIT_VIEW"));
         * label.setBounds(40, 280, 300, 30);
         * label.setFont(fnt_18);
         * //this.add(label, null);
         * label = new JLabel(ic.getMessage("EDIT_VIEW_DESC"));
         * label.setBounds(40, 310, 300, 60);
         * label.setFont(font_normal);
         * //this.add(label, null);
         */
        // System.out.println("Pabnel paint");
        return;
    }

    /**
     * Set Data
     * 
     * @param intr
     */
    public void setData(DatabaseSettings intr)
    {
        m_database_settings = intr;
        clearElements();
        reDraw();
        processExistingUrl(intr.url);
        // this.db_action.setDatabaseSettings
    }

    /**
     * Get Database Settings
     * 
     * @return
     */
    public DatabaseSettings getDatabaseSettings()
    {
        return this.m_database_settings;
    }

    /**
     * Get JDBC URL
     * 
     * @return
     */
    public String getJDBCUrl()
    {
        return this.tf_url.getText();
    }

    private void clearElements()
    {
        for (Enumeration<String> en = this.parameters_textfield.keys(); en.hasMoreElements();)
        {
            String key = en.nextElement();
            this.remove(this.parameters_textfield.get(key));
        }

        this.parameters_textfield.clear();

        for (Enumeration<String> en = this.parameters_label.keys(); en.hasMoreElements();)
        {
            String key = en.nextElement();
            this.remove(this.parameters_label.get(key));
        }

        this.parameters_label.clear();

        // this.parameters_textfield
    }

    private void reDraw()
    {

        System.out.println("Redraw Not working fully");

        String dbName = m_database_settings.db_name;
        cb_databases.setEnabled(false);

        Object av[] = m_da.getAvailableDatabases();

        for (int i = 0; i < av.length; i++)
        {
            DatabaseDefObject ddo = (DatabaseDefObject) av[i];
            if (ddo.name.equals(dbName))
            {
                cb_databases.setSelectedIndex(i);
            }
        }

        // System.out.println("m_da: " + m_da);
        // System.out.println("tableOfDb: " + m_da.m_tableOfDatabases);
        // System.out.println("dbName: " + dbName);

        DatabaseDefObject ddo = m_da.m_tableOfDatabases.get(dbName);

        label_class.setText(ddo.driver);
        label_dialect.setText(ddo.short_dialect);

        this.repaint();
        posy = 260;

        unpackURL(ddo.url);

    }

    private void unpackURL(String url)
    {
        StringTokenizer tok = new StringTokenizer(url, "<");
        url_list.clear();

        // ArrayList list = new ArrayList();

        while (tok.hasMoreTokens())
        {
            String str = tok.nextToken();

            if (str.indexOf(">") != -1)
            {
                str = "<" + str;

                String s1 = str.substring(0, str.indexOf(">") + 1);
                // System.out.println("x " + s1);
                url_list.add(s1);

                String s2 = str.substring(str.indexOf(">") + 1);

                if (s2.trim().length() != 0)
                {
                    url_list.add(s2);
                }
            }
            else
            {
                url_list.add(str);
                // System.out.println(tok.nextToken());
            }
        }

        processParameters();

    }

    private void processParameters()
    {

        Iterator<?> it = url_list.iterator();

        while (it.hasNext())
        {

            String par = (String) it.next();

            if (par.indexOf("<") != -1)
            {

                // if ((par.equals("<username>")) || (par.equals("<password>")))
                // continue;

                String p1 = par.substring(1, par.length() - 1).toUpperCase();
                posy += 30;

                JLabel param_label = new JLabel(ic.getMessage(p1) + ":");
                param_label.setBounds(40, posy, 200, 25);
                param_label.setFont(font_normal);
                this.add(param_label, null);
                this.parameters_label.put(par, param_label);

                JTextField textfield = new JTextField();
                textfield.setBounds(200, posy, 180, 25);
                textfield.setFont(font_normal);
                textfield.addKeyListener(this);

                this.add(textfield, null);
                this.parameters_textfield.put(par, textfield);

            }
        }

        this.db_action.setBounds(0, posy + 40, 485, 65);

    }

    private void processExistingUrl(String url)
    {
        String url2 = url;

        // System.out.println("Url current: " + url);

        tf_url.setText(url);

        int sz = this.url_list.size();

        for (int i = 0; i < sz; i += 2)
        {

            if (i + 2 < sz)
            {
                String s1 = this.url_list.get(i);
                String s2 = this.url_list.get(i + 1);
                String s3 = this.url_list.get(i + 2);

                String par = url2.substring(s1.length());
                par = par.substring(0, par.indexOf(s3));

                url2 = url2.substring(url2.indexOf(par) + par.length());

                JTextField tf = this.parameters_textfield.get(s2);
                tf.setText(par);

            }
            else if (i + 1 < sz)
            {
                String s1 = this.url_list.get(i);
                String s2 = this.url_list.get(i + 1);

                String par = url2.substring(s1.length());

                JTextField tf = this.parameters_textfield.get(s2);
                tf.setText(par);
            }
            else
            {
                System.out.println("ERROR: Error in program.");
            }
        }

    }

    /**
     *  Action Listener
     */
    public void actionPerformed(ActionEvent e)
    {

        String action = e.getActionCommand();

        if (action.equals("textfield"))
        {
            System.out.println("Textfield");
        }
        else
        {
            System.out.println("PanelDatabaseSet::Unknown command: " + action);
        }

    }

    /** 
     * keyPressed
     */
    public void keyPressed(KeyEvent arg0)
    {
    }

    /** 
     * Key Released
     */
    public void keyReleased(KeyEvent arg0)
    {
        refreshUrl();
    }

    /** 
     * keyTyped
     */
    public void keyTyped(KeyEvent arg0)
    {
    }

    private void refreshUrl()
    {

        int sz = this.url_list.size();
        // System.out.println("sz=" + sz);

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < sz; i++) // = 2)
        {
            // System.out.println(this.url_list.get(i));

            String k = this.url_list.get(i);

            if (k.startsWith("<"))
            {
                sb.append(this.parameters_textfield.get(k).getText());
            }
            else
            {
                sb.append(k);
            }

        }

        this.tf_url.setText(sb.toString());

        // System.out.println("New URL: " + sb.toString());

    }

}
