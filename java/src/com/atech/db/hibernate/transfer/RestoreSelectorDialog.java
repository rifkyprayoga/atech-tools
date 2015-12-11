package com.atech.db.hibernate.transfer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.atech.help.ComponentHelpCapable;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  RestoreSelectorDialog - Restore Selector Dialog (abstract)
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

public abstract class RestoreSelectorDialog extends JDialog implements ActionListener, HelpCapable,
        ComponentHelpCapable
{

    private static final long serialVersionUID = 46466618138619321L;

    protected I18nControlAbstract ic = null;
    protected ATDataAccessAbstract m_da = null;

    JPanel panel;
    JLabel label, label_title;
    JButton button, button_next, button_help;
    Font font_big, font_normal, font_normal_b;
    protected JTextField tf_file;
    protected Hashtable<String, BackupRestoreObject> ht_backup_objects;
    protected Container my_parent = null;


    /**
     * Instantiates a new restore selector dialog.
     * 
     * @param parent the parent
     * @param da the da
     */
    public RestoreSelectorDialog(JDialog parent, ATDataAccessAbstract da)
    {
        super(parent, "", true);

        my_parent = parent;
        m_da = da;
        this.ic = m_da.getI18nControlInstance();

        init();
    }


    /**
     * Instantiates a new restore selector dialog.
     * 
     * @param parent the parent
     * @param da the da
     */
    public RestoreSelectorDialog(JFrame parent, ATDataAccessAbstract da)
    {
        super(parent, "", true);
        my_parent = parent;
        m_da = da;
        this.ic = m_da.getI18nControlInstance();

        init();
    }


    /**
     * Inits the.
     */
    public void init()
    {

        this.setBounds(130, 50, 450, 450); // 360

        ATSwingUtils.initLibrary();

        ht_backup_objects = new Hashtable<String, BackupRestoreObject>();

        font_big = ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD);
        font_normal = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        font_normal_b = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);

        this.cmdUpdate();

        this.setResizable(false);
        ATSwingUtils.centerJDialog(this, this.my_parent); // dataAccess.getParent());
        this.m_da.addComponent(this);
    }


    /**
     * Show dialog.
     */
    public void showDialog()
    {
        this.setVisible(true);
    }


    /*
     * Displays title for dialog
     */
    /**
     * Show title.
     * 
     * @param backup the backup
     */
    public void showTitle(boolean backup)
    {
        this.setTitle(ic.getMessage("RESTORE_DB_TITLE"));
        label_title.setText(ic.getMessage("RESTORE_DB_TITLE_SHORT"));
    }


    /*
     * Displays GUI
     */
    /**
     * Cmd update.
     */
    public void cmdUpdate()
    {

        Container dgPane = this.getContentPane();

        panel = new JPanel();
        panel.setBounds(5, 5, 450, 450);
        panel.setLayout(null); // 600 450
        dgPane.add(panel);

        label_title = new JLabel();
        label_title.setBounds(0, 15, 450, 40);
        label_title.setFont(font_big);
        label_title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label_title, null);

        showTitle(true);

        label = new JLabel(ic.getMessage("RESTORE_SELECTOR_DESC"));
        label.setBounds(25, 70, 400, 190);
        label.setFont(this.font_normal);
        panel.add(label);

        label = new JLabel(ic.getMessage("SELECT_FILE_TO_RESTORE") + ":");
        label.setBounds(25, 280, 400, 25);
        label.setFont(this.font_normal_b);
        panel.add(label);

        tf_file = new JTextField();
        tf_file.setBounds(25, 305, 250, 25);
        tf_file.setEditable(false);
        panel.add(tf_file);

        // ---
        // --- Browse for file selector
        // ---
        button = new JButton(ic.getMessage("BROWSE") + "...");
        button.setBounds(290, 305, 120, 25);
        // button_backup.setIcon(dataAccess.getImageIcon_22x22("export1.png",
        // this));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("select_file");
        panel.add(button);

        button = new JButton(ic.getMessage("CLOSE"));
        button.setBounds(160, 365, 120, 25);
        button.setIcon(ATSwingUtils.getImageIcon_22x22("cancel.png", this, m_da));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("close");
        panel.add(button);

        // ---
        // --- Close Command
        // ---
        button_next = new JButton("   " + ic.getMessage("NEXT_STEP") + " >>> ");
        button_next.setBounds(290, 365, 120, 25);
        // button.setIcon(dataAccess.getImageIcon_22x22("cancel.png", this));
        button_next.addActionListener(this);
        button_next.setFont(font_normal);
        button_next.setEnabled(false);
        button_next.setActionCommand("next_step");
        panel.add(button_next);

        // ---
        // --- Help command
        // ---
        button_help = ATSwingUtils.createHelpButtonByBounds(25, 365, 120, 25, this, ATSwingUtils.FONT_NORMAL, m_da);
        button_help.setFont(font_normal);
        panel.add(button_help);

    }


    /**
     * Cmd next step.
     */
    public abstract void cmdNextStep();


    /**
     * Gets the browse startup directory.
     * 
     * @return the browse startup directory
     */
    public String getBrowseStartupDirectory()
    {
        return ".";
    }


    /** 
     * actionPerformed
     */
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();

        if (cmd.equals("next_step"))
        {
            this.m_da.removeComponent(this);
            this.dispose();
            cmdNextStep();
        }
        else if (cmd.equals("select_file"))
        {
            JFileChooser file_chooser = new JFileChooser();
            file_chooser.setDialogTitle(ic.getMessage("SELECT_FILE_TO_RESTORE"));
            file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            file_chooser.setMultiSelectionEnabled(false);
            file_chooser.setCurrentDirectory(new File(this.getBrowseStartupDirectory()));

            file_chooser.setFileFilter(new FileFilter()
            {

                @Override
                public boolean accept(File f)
                {
                    if (f.isDirectory())
                        return true;

                    return f.getName().endsWith(".dbe");
                }


                @Override
                public String getDescription()
                {
                    return ic.getMessage("FILE_FILTER_DBE");
                }

            });

            file_chooser.addChoosableFileFilter(new FileFilter()
            {

                @Override
                public boolean accept(File f)
                {
                    if (f.isDirectory())
                        return true;

                    return f.getName().endsWith(".zip");
                }


                @Override
                public String getDescription()
                {
                    return ic.getMessage("FILE_FILTER_ZIP");
                }

            });

            // In response to a button click:
            int res = file_chooser.showOpenDialog(this);

            if (res == JFileChooser.APPROVE_OPTION)
            {
                File f = file_chooser.getSelectedFile();
                this.tf_file.setText(f.getPath());
                this.button_next.setEnabled(true);
            }

        }
        else if (cmd.equals("close"))
        {
            this.m_da.removeComponent(this);
            this.dispose();
        }

    }

    /**
     * The help_id.
     */
    String help_id = null;


    /**
     * Enable Help
     */
    public void enableHelp(String _help_id)
    {
        this.help_id = _help_id;
        m_da.enableHelp(this);
    }


    /**
     * Get Component
     */
    public Component getComponent()
    {
        return this;
    }


    /**
     * Get Help Button
     */
    public JButton getHelpButton()
    {
        return button_help;
    }


    /**
     * Get Help Id
     */
    public String getHelpId()
    {
        return this.help_id;
    }

}
