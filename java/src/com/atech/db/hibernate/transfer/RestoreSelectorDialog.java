package com.atech.db.hibernate.transfer;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import com.atech.graphics.components.tree.CheckNodeTree;
import com.atech.help.ComponentHelpCapable;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

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

public abstract class RestoreSelectorDialog extends JDialog implements ActionListener, // ,
                                                                                       // BackupRestoreWorkGiver,
        HelpCapable, ComponentHelpCapable
{

    private static final long serialVersionUID = 46466618138619321L;

    /**
     * The ic.
     */
    protected I18nControlAbstract ic = null;

    /**
     * The m_da.
     */
    protected ATDataAccessAbstract m_da = null;

    /*
     * Globaly used variables
     */
    /**
     * The panel.
     */
    JPanel panel;

    /**
     * The label_title.
     */
    JLabel label, label_title;

    /**
     * The button_help.
     */
    JButton button, button_next, button_help;

    /**
     * The font_normal_b.
     */
    Font font_big, font_normal, font_normal_b;

    /**
     * The label_date.
     */
    JLabel label_date;

    /**
     * The progress_current.
     */
    JProgressBar progress_full, progress_current;

    /**
     * The tree.
     */
    CheckNodeTree tree;

    /**
     * The m_error.
     */
    int m_error = 0;

    /**
     * The last action.
     */
    int lastAction = 0; // no event

    /**
     * The count_of_backup_elements.
     */
    protected int count_of_backup_elements = 0;

    /**
     * The done_backup_elements.
     */
    protected int done_backup_elements = -1;

    /**
     * The label_current_progress.
     */
    JLabel label_total_progress, label_current_progress;

    /**
     * The tf_file.
     */
    protected JTextField tf_file;

    /**
     * The backuprestore_root.
     */
    protected BackupRestoreCollection backuprestore_root;

    /**
     * The ht_backup_objects.
     */
    protected Hashtable<String, BackupRestoreObject> ht_backup_objects;

    /**
     * The my_parent.
     */
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

        ht_backup_objects = new Hashtable<String, BackupRestoreObject>();

        font_big = m_da.getFont(ATDataAccessAbstract.FONT_BIG_BOLD);
        font_normal = m_da.getFont(ATDataAccessAbstract.FONT_NORMAL);
        font_normal_b = m_da.getFont(ATDataAccessAbstract.FONT_NORMAL_BOLD);

        this.cmdUpdate();

        this.setResizable(false);
        this.m_da.centerJDialog(this, this.my_parent); // m_da.getParent());
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
        // button_backup.setIcon(m_da.getImageIcon_22x22("export1.png", this));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("select_file");
        panel.add(button);

        button = new JButton(ic.getMessage("CLOSE"));
        button.setBounds(160, 365, 120, 25);
        button.setIcon(m_da.getImageIcon_22x22("cancel.png", this));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("close");
        panel.add(button);

        // ---
        // --- Close Command
        // ---
        button_next = new JButton("   " + ic.getMessage("NEXT_STEP") + " >>> ");
        button_next.setBounds(290, 365, 120, 25);
        // button.setIcon(m_da.getImageIcon_22x22("cancel.png", this));
        button_next.addActionListener(this);
        button_next.setFont(font_normal);
        button_next.setEnabled(false);
        button_next.setActionCommand("next_step");
        panel.add(button_next);

        // ---
        // --- Help command
        // ---
        button_help = m_da.createHelpButtonByBounds(25, 365, 120, 25, this);
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
