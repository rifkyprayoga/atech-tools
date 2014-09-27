package com.atech.graphics.dialogs;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.atech.app.AbstractApplicationContext;
import com.atech.db.hibernate.hdb_object.User;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  LoginDialog - this class for login/logout
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

public class LoginDialog extends JDialog implements ActionListener, HelpCapable
{

    private static final long serialVersionUID = 2723627844126175317L;
    ATDataAccessAbstract m_da = null;
    I18nControlAbstract ic = null;

    JPasswordField pf_password; // passwordField1;
    JTextField tf_username; // , pf_password; // textField1,

    JPanel panel; // , panel_2;
    JLabel label; // , label_parish, label_pperson;

    int last_event = 0; // no event

    JButton help_button;
    int tries_left = 0;
    AbstractApplicationContext app_ctx;

    /**
     * Constructor
     * 
     * @param da 
     * @param aac 
     */
    public LoginDialog(ATDataAccessAbstract da, AbstractApplicationContext aac)
    {
        super(da.getMainParent(), "", true);

        this.app_ctx = aac;
        m_da = da;
        this.ic = da.getI18nControlInstance();

        String ev = "";
        ev = ic.getMessage("LOGIN");
        this.setTitle(ev);

        System.out.println("Login 0.1 ");

        this.m_da.addComponent(this);

        this.setResizable(false);
        // this.setBounds(130, 50, 200, 200);
        this.setBounds(200, 140, 375, 270);

        this.m_da.centerJDialog(this);

        this.cmdLogin();
        this.setVisible(true);
    }

    private void cmdLogin()
    {
        if (m_da.getUser() != null)
            return;

        ATSwingUtils.initLibrary();

        panel = new JPanel();
        panel.setBounds(5, 5, 150, 150);
        panel.setLayout(null);
        this.getContentPane().add(panel);

        ATSwingUtils.getTitleLabel(ic.getMessage("FILE_LOGIN"), 0, 25, 400, 32, panel, ATSwingUtils.FONT_BIG_BOLD);

        ATSwingUtils.getLabel(ic.getMessage("USERNAME") + ": ", 40, 85, 140, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        this.tf_username = ATSwingUtils.getTextField("", 160, 85, 160, 25, panel);

        ATSwingUtils.getLabel(ic.getMessage("PASSWORD") + ": ", 40, 120, 140, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        this.pf_password = new JPasswordField(); // password
        this.pf_password.setBounds(160, 120, 160, 25);
        panel.add(this.pf_password); // , ZeroLayout.STATIC);

        // login bug on linux (JPassword field is not focused on linux)
        if (System.getProperty("os.name").equals("Linux"))
        {
            this.pf_password.enableInputMethods(true);
        }

        ATSwingUtils.getButton("  " + ic.getMessage("OK"), 40, 180, 110, 30, panel, ATSwingUtils.FONT_NORMAL, "ok.png",
            "ok", this, m_da);

        ATSwingUtils.getButton("  " + ic.getMessage("CANCEL"), 160, 180, 110, 30, panel, ATSwingUtils.FONT_NORMAL,
            "cancel.png", "cancel", this, m_da);

        help_button = m_da.createHelpIconByBounds(280, 180, 50, 30, panel, ATSwingUtils.FONT_NORMAL);
        panel.add(help_button);

        this.m_da.enableHelp(this);
    }

    /*
     * Action Listener
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("ok"))
        {

            if (doLogin())
            {
                this.dispose();
                this.m_da.removeComponent(this);

                if (this.m_da.isDemoVersion())
                {
                    JOptionPane.showMessageDialog(this.m_da.getCurrentComponent(), m_da.getI18nControlInstance()
                            .getMessage("DEMO_LOGIN"), m_da.getI18nControlInstance().getMessage("DEMO_WARNING"),
                        JOptionPane.INFORMATION_MESSAGE);

                }
            }

            return;
        }
        else if (action.equals("cancel"))
        {
            // last_event = 0;
            this.dispose();
            this.m_da.removeComponent(this);
        }
        /*
         * else if (action.equals("help")) { this.dispose(); }
         */
        else
        {
            System.out.println("LoginDialog:Unknown command: " + action);
        }

    }

    @SuppressWarnings("deprecation")
    private boolean doLogin()
    {

        if (this.pf_password.getPassword() != null && this.pf_password.getText().length() != 0
                && this.tf_username.getText().length() != 0)
        {

            User us = getUser(this.tf_username.getText());

            if (us == null)
            {
                // NO SUCH USER
                JOptionPane.showMessageDialog(this, ic.getMessage("LOGIN_NO_SUCH_USER"), ic.getMessage("ERROR"),
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else
            {
                // System.out.println("Us password: " + us.getPassword());

                // String pass = new String(this.pf_password.getPassword());

                if (us.getPassword().equals(pf_password.getText())) // ;pass))
                {
                    // LOGIN SUCCESS
                    m_da.setUser(us);
                    // m_da.processLogin();
                    return true;
                }
                else
                {
                    this.tries_left--;

                    if (tries_left == 0)
                    {
                        // no tries left
                        JOptionPane.showMessageDialog(this, ic.getMessage("LOGIN_NO_TRIES_LEFT"),
                            ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE);
                        m_da.setUser(null);
                        if (this.app_ctx != null)
                        {
                            this.app_ctx.quitApplication();
                        }
                        return false;
                    }
                    else
                    {
                        // WRONG PASSWORD
                        JOptionPane.showMessageDialog(
                            this,
                            String.format(ic.getMessage("LOGIN_WRONG_PASSWORD_USERNAME_COMBINATION"), ""
                                    + this.tries_left), ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE);
                        m_da.setUser(null);
                        return false;
                    }
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, ic.getMessage("LOGIN_NOT_ALL_DATA_ENTERED"), ic.getMessage("ERROR"),
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private User getUser(String username)
    {
        ArrayList<User> all = m_da.getAllUsers();

        for (int i = 0; i < all.size(); i++)
        {
            if (all.get(i).getUsername().equals(username))
                return all.get(i);
            else
            {
                // System.out.println("User: " + all.get(i).getUsername() +
                // " NOT Equals");
            }
        }

        return null;
    }

    /**
     * Gets info if action was performed.
     * 
     * @return true if action was done, false if not.
     */
    public boolean wasAction()
    {
        if (last_event == 1)
            return true;
        else
            return false;
    }

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
        if (this.app_ctx == null)
            return "Application.Login";
        else
            return this.app_ctx.getHelpKeyword("Application.Login", "Application.Login");
    }

    /**
     * Logout
     * 
     * @param da 
     */
    public static void doLogout(ATDataAccessAbstract da)
    {
        I18nControlAbstract ic = da.getI18nControlInstance();

        int result = JOptionPane.showConfirmDialog(da.getMainParent(), ic.getMessage("WANT_LOGOUT"),
            ic.getMessage("LOGOUT"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result)
        {
            case JOptionPane.CLOSED_OPTION:
                break;
            case JOptionPane.YES_OPTION:
                {
                    da.setUser(null);
                    break;
                }
            case JOptionPane.NO_OPTION:
                break;
        }
    }

}
