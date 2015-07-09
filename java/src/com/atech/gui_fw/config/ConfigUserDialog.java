package com.atech.gui_fw.config;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.atech.db.hibernate.hdb_object.User;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
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
 * CfgUserDialog - Configuration::Users
 * 
 * This is dialog for add/editing users. It should be used only from within
 * ConfigurationDialog.
 * 
 * This class is part of PIS (Parish Information System) package.
 * 
 * @author      Andy (Aleksander) Rozman {andy@triera.net}
 * @version     1.0
 */

public class ConfigUserDialog extends JDialog implements ActionListener, HelpCapable
{

    private static final long serialVersionUID = 4132176317440765520L;
    I18nControlAbstract ic = null; // I18nControl.getInstance();
    ATDataAccessAbstract m_da = null;
    Font font_big, font_normal, font_normal_b;
    int m_error = 0;

    // int[] db_user = { 0, 1, 4 };

    JPanel panel;
    JLabel label, label_person;
    JButton button, bt_select;
    JTextField tf_username, tf_realname, tf_user_desc;
    JComboBox cb_type, cb_type_basic;
    JPasswordField pf_pass;

    long data_id = -1;
    int lastAction = 0; // no event
    User m_user = null;
    // InternalPerson m_ip = null;
    AbstractConfigurationContext m_acc;


    /**
     * Constructor
     *   
     * @param da 
     * @param usr 
     * @param acc 
     */
    public ConfigUserDialog(ATDataAccessAbstract da, User usr, AbstractConfigurationContext acc)
    {
        super(da.getMainParent(), "", true);

        this.m_acc = acc;
        this.m_da = da;
        this.ic = da.getI18nControlInstance();
        this.m_da.addComponent(this);

        String ev = "";

        if (usr == null)
        {
            this.data_id = -1;
        }
        else
        {
            this.data_id = usr.getId();
        }

        if (this.data_id > 0)
        {
            ev = ic.getMessage("USER_EDIT");
        }
        else
        {
            ev = ic.getMessage("USER_ADD");
        }

        this.setTitle(ev);

        // System.out.println("CfgUserDialog 0.1 ");

        m_da = da;

        this.setResizable(false);
        this.setBounds(130, 50, 430, 465);

        ATSwingUtils.initLibrary();

        font_big = ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD);
        font_normal = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        font_normal_b = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);
        ;

        this.m_user = usr;

        if (m_user == null)
        {
            this.m_user = new User(m_da);
        }

        this.cmdUser();
        this.loadUser();
        this.m_da.centerJDialog(this);

        this.setVisible(true);

    }


    /**
     *  Loads user data into GUI
     */
    public void loadUser()
    {

        tf_username.setText(m_user.getUsername());
        tf_realname.setText(m_user.getReal_name());
        tf_user_desc.setText(m_user.getReal_desc());

        /*
         * if (this.m_user.getUser_type()==1)
         * {
         * this.cb_type.setSelectedIndex(1);
         * }
         * else if (this.m_user.getUser_type()==4)
         * {
         * this.cb_type.setSelectedIndex(2);
         * }
         * else
         * this.cb_type.setSelectedIndex(0);
         */

        cb_type.setSelectedIndex(m_user.getUser_access());
        pf_pass.setText(m_user.getPassword());

        /*
         * if (m_user.getUser_type_id()>0)
         * {
         * m_ip = new InternalPerson();
         * m_ip.setId(m_user.getUser_type_id());
         * dataAccess.getDb().get(m_ip);
         * this.label_person.setText(m_ip.getShortDescriptionWithType());
         * this.bt_select.setEnabled(true);
         * }
         * else
         * {
         * label_person.setText(ic.getMessage("NO_INTERNAL_USER_SELECTED"));
         * }
         */

    }


    /**
     * Save user data, from GUI
     * 
     * @return true if save was succesfull. If global variable m_error is 1 then error was produced. If m_error is 0 then User was not saved, because no data has changed.
     */
    public boolean saveUser()
    {
        boolean change = false;

        m_error = 0;

        if (tf_username.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(this, ic.getMessage("USERNAME_MUST_BE_ENTERED"), ic.getMessage("ERROR"),
                JOptionPane.ERROR_MESSAGE);
            m_error = 1;
            return false;
        }

        if (pf_pass.getPassword().length < 7)
        {
            JOptionPane.showMessageDialog(this, ic.getMessage("PASSWORD_MUST_BE_SET"), ic.getMessage("ERROR"),
                JOptionPane.ERROR_MESSAGE);
            m_error = 1;
            return false;
        }
        /*
         * if (cb_type_basic.getSelectedIndex()==0)
         * {
         * JOptionPane.showMessageDialog(this,
         * ic.getMessage("TYPE_MUST_BE_SET"), ic.getMessage("ERROR"),
         * JOptionPane.ERROR_MESSAGE);
         * m_error = 1;
         * return false;
         * }
         */
        if (cb_type.getSelectedIndex() == 0)
        {
            JOptionPane.showMessageDialog(this, ic.getMessage("ACCESS_MUST_BE_SET"), ic.getMessage("ERROR"),
                JOptionPane.ERROR_MESSAGE);
            m_error = 1;
            return false;
        }

        /*
         * if ((cb_type_basic.getSelectedIndex()==2) && (m_ip==null))
         * {
         * JOptionPane.showMessageDialog(this, ic.getMessage("IP_MUST_BE_SET"),
         * ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE);
         * m_error = 1;
         * return false;
         * }
         */

        if (!tf_username.getText().equals(m_user.getUsername()))
        {
            change = true;
            m_user.setUsername(tf_username.getText());
        }

        String ps = new String(pf_pass.getPassword());
        if (!ps.equals(m_user.getPassword()))
        {
            change = true;
            m_user.setPassword(ps);
        }

        if (!tf_realname.getText().equals(m_user.getReal_name()))
        {
            change = true;
            m_user.setReal_name(tf_realname.getText());
        }

        if (!tf_user_desc.getText().equals(m_user.getReal_desc()))
        {
            change = true;
            m_user.setReal_desc(tf_user_desc.getText());
        }

        /*
         * if
         * (this.db_user[cb_type_basic.getSelectedIndex()]!=m_user.getUser_type
         * ())
         * {
         * change = true;
         * m_user.setUser_type(this.db_user[cb_type_basic.getSelectedIndex()]);
         * }
         */

        if (cb_type.getSelectedIndex() != m_user.getUser_access())
        {
            change = true;
            m_user.setUser_access(cb_type.getSelectedIndex());
        }

        /*
         * if (cb_type_basic.getSelectedIndex()==2)
         * {
         * if (m_user.getUser_type_id()!=m_ip.getId())
         * {
         * change = true;
         * m_user.setUser_type_id(m_ip.getId());
         * }
         * }
         */

        if (change)
        {
            if (data_id <= 0)
            {
                m_da.getHibernateDb().add(m_user);
            }
            else
            {
                m_da.getHibernateDb().edit(m_user);
            }

            return true;
        }

        return false;

    }


    /**
     * Create GUI
     */
    public void cmdUser()
    {

        Container dgPane = this.getContentPane();

        panel = new JPanel();
        panel.setBounds(5, 5, 400, 430);
        panel.setLayout(null); // 600 450
        dgPane.add(panel);

        String ev = "";

        if (this.m_user.getId() > 0)
        {
            ev = ic.getMessage("USER_EDIT");
        }
        else
        {
            ev = ic.getMessage("USER_ADD");
        }

        label = new JLabel(ev);
        label.setBounds(0, 25, 430, 40);
        label.setFont(font_big);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, null);

        // username
        label = new JLabel(ic.getMessage("USERNAME") + ":");
        label.setBounds(45, 90, 150, 25);
        label.setFont(font_normal_b);
        panel.add(label, null);

        tf_username = new JTextField();
        tf_username.setBounds(210, 87, 150, 25);
        tf_username.setFont(font_normal);
        panel.add(tf_username, null);

        // password
        label = new JLabel(ic.getMessage("PASSWORD") + ":");
        label.setBounds(45, 123, 150, 25);
        label.setFont(font_normal_b);
        panel.add(label, null);

        pf_pass = new JPasswordField();
        pf_pass.setBounds(210, 120, 150, 25);
        pf_pass.setFont(font_normal);
        panel.add(pf_pass, null);

        // login bug on linux (JPassword field is not focused on linux)
        if (System.getProperty("os.name").equals("Linux"))
        {
            this.pf_pass.enableInputMethods(true);
        }

        // user realname
        label = new JLabel(ic.getMessage("REAL_NAME") + ":");
        label.setBounds(45, 158, 150, 25);
        label.setFont(font_normal_b);
        panel.add(label, null);

        tf_realname = new JTextField();
        tf_realname.setBounds(210, 155, 160, 25);
        tf_realname.setFont(font_normal);
        panel.add(tf_realname, null);

        // user desc
        label = new JLabel(ic.getMessage("USER_DESC") + ":");
        label.setBounds(45, 193, 150, 25);
        label.setFont(font_normal_b);
        panel.add(label, null);

        tf_user_desc = new JTextField();
        tf_user_desc.setBounds(210, 190, 160, 25);
        tf_user_desc.setFont(font_normal);
        panel.add(tf_user_desc, null);

        /*
         * // type of user
         * label = new JLabel(ic.getMessage("USER_TYPE")+":");
         * label.setBounds(55, 228, 120, 25);
         * label.setFont(font_normal_b);
         * panel.add(label, null);
         * cb_type_basic = new JComboBox(dataAccess.userTypesBasic);
         * cb_type_basic.setBounds(200, 225, 150, 25);
         * cb_type_basic.setFont(font_normal);
         * cb_type_basic.addItemListener(new ItemListener(){
         * /**
         * Invoked when an item has been selected or deselected by the user.
         * The code written for this method performs the operations
         * that need to occur when an item is selected (or deselected).
         */
        /*
         * public void itemStateChanged(ItemEvent e)
         * {
         * bt_select.setEnabled(cb_type_basic.getSelectedIndex()==2);
         * }
         * });
         * panel.add(cb_type_basic, null);
         * // internal user
         * label = new JLabel(ic.getMessage("INTERNAL_USER")+":");
         * label.setBounds(55, 263, 120, 25);
         * label.setFont(font_normal_b);
         * panel.add(label, null);
         * // internal user display
         * label_person = new JLabel("sss  ddddddddddddddd");
         * label_person.setBounds(100, 288, 320, 25);
         * label_person.setFont(font_normal);
         * panel.add(label_person, null);
         * // --- select internal
         * bt_select = new JButton(ic.getMessage("SELECT"));
         * bt_select.setBounds(270,263,80,25);
         * bt_select.addActionListener(this);
         * bt_select.setFont(font_normal);
         * bt_select.setActionCommand("select_person");
         * panel.add(bt_select);
         * bt_select.setEnabled(false);
         */
        // access level
        label = new JLabel(ic.getMessage("ACCESS_LEVEL") + ":");
        label.setBounds(45, 228, 120, 25);
        label.setFont(font_normal_b);
        panel.add(label, null);

        cb_type = new JComboBox(m_da.user_types);
        cb_type.setBounds(210, 225, 160, 25);
        cb_type.setFont(font_normal);
        panel.add(cb_type, null);

        // ---
        // --- OK Command
        // ---
        button = new JButton(ic.getMessage("OK"));
        button.setBounds(150, 370, 80, 30);
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("ok");
        panel.add(button);

        // ---
        // --- Cancel Command
        // ---
        button = new JButton(ic.getMessage("CANCEL"));
        button.setBounds(225, 370, 80, 30);
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("cancel");
        panel.add(button);

        // ---
        // --- Help command
        // ---
        button = new JButton(ic.getMessage("HELP"));
        button.setBounds(300, 370, 80, 30);
        button.setFont(font_normal);
        // button.addActionListener(this);
        // button.setActionCommand("help");
        panel.add(button);

        this.help_button = button;

        if (this.m_acc.app_ctx.isHelpEnabled())
        {
            this.m_da.enableHelp(this);
        }

        // PISMainHelp.mainHelpBroker.enableHelpOnButton(button,
        // "pages.PISSettingsUserAdd", null);
        // PISMainHelp.mainHelpBroker.enableHelpKey(this.getRootPane(),
        // "pages.PISSettingsUserAdd", null);

    }


    /**
     *  Action Listener
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("ok"))
        {

            lastAction = 0;

            if (saveUser())
            {
                lastAction = 1;
                this.dispose();
                this.m_da.removeComponent(this);
            }
            else
            {
                if (m_error == 0)
                {
                    lastAction = 0;
                    this.dispose();
                    this.m_da.removeComponent(this);
                }
            }

        }
        else if (action.equals("cancel"))
        {
            lastAction = 0;
            this.dispose();
            this.m_da.removeComponent(this);
        }
        /*
         * else if (action.equals("select_person"))
         * {
         * SelectorDialog sd;
         * if (this.m_ip==null)
         * {
         * sd = new SelectorDialog(dataAccess,
         * SelectorDialog.SELECTOR_INTERNAL_PERSON);
         * }
         * else
         * {
         * sd = new SelectorDialog(dataAccess,
         * SelectorDialog.SELECTOR_INTERNAL_PERSON, "|"+this.m_ip.getId()+"|");
         * }
         * if (sd.wasAction())
         * {
         * this.m_ip = (InternalPerson)sd.getSelectedObject();
         * this.label_person.setText(m_ip.getShortDescriptionWithType());
         * }
         * sd.dispose();
         * }
         */
        else
        {
            System.out.println("CfgUserDialog:Unknown command: " + action);
        }

    }


    /**
     * Determines if there was change of data
     * @return 
     */
    public boolean wasAction()
    {
        if (lastAction == 1)
            return true;
        else
            return false;
    }


    /** 
     * Returns User object
     * 
     * @return 
     */
    public User getObject()
    {
        return m_user;
    }

    JButton help_button;


    /**
     * getComponent - this method returns instance of this component, for
     * attaching it to help context.
     * 
     * @return instance of this component
     */
    public Component getComponent()
    {
        return this;
    }


    /**
     * getHelpButton - returns help button, for attaching to help context
     * 
     * @return help button instance
     */
    public JButton getHelpButton()
    {
        return help_button;
    }


    /**
     * getHelpId - returns help id, for attachment to help context
     * 
     * @return help id as string
     */
    public String getHelpId()
    {
        return "pages.PISSettingsUserAdd";
    }

}
