package com.atech.db.hibernate.transfer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
import com.atech.graphics.components.tree.CheckNode;
import com.atech.graphics.components.tree.CheckNodeTree;
import com.atech.help.ComponentHelpCapable;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  BackupRestoreDialog - Dialog for doing backup/restore. This needs to be
 *                        sub-classes.
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
 *  @author Andy  {andy@atech-software.com}
 *
 */

public abstract class BackupRestoreDialog extends JDialog
        implements ActionListener, BackupRestoreWorkGiver, HelpCapable, ComponentHelpCapable
{

    private static final long serialVersionUID = 4209248421335758364L;

    protected I18nControlAbstract ic = null;
    protected ATDataAccessAbstract m_da = null;

    /*
     * Globaly used variables
     */
    JPanel panel;
    JLabel label_title;
    JButton button, button_backup, button_help;
    Font font_big, font_normal, font_normal_b;

    JProgressBar progressFull, progressCurrent;

    CheckNodeTree tree;

    int m_error = 0;
    int lastAction = 0; // no event

    protected int count_of_backup_elements = 0;
    protected int done_backup_elements = -1;

    JLabel label_total_progress, label_current_progress;

    protected BackupRestoreCollection backupRestoreRoot;
    protected Map<String, BackupRestoreObject> backupObjects;

    Container my_parent = null;


    /**
     * Constructor 
     * 
     * @param parent
     * @param da
     * @param br_coll
     */
    public BackupRestoreDialog(JDialog parent, ATDataAccessAbstract da, BackupRestoreCollection br_coll)
    {
        super(parent, "", true);

        my_parent = parent;
        m_da = da;
        this.ic = m_da.getI18nControlInstance();

        backupRestoreRoot = br_coll;

        System.out.println("BRC: " + da.getBackupRestoreCollection());

        init();
    }


    /**
     * Constructor 
     * 
     * @param parent
     * @param da
     * @param br_coll
     */
    public BackupRestoreDialog(JFrame parent, ATDataAccessAbstract da, BackupRestoreCollection br_coll)
    {
        super(parent, "", true);
        my_parent = parent;
        m_da = da;
        this.ic = m_da.getI18nControlInstance();

        backupRestoreRoot = br_coll;

        System.out.println("BRC: " + da.getBackupRestoreCollection());

        init();
    }


    private void init()
    {
        ATSwingUtils.initLibrary();

        this.setBounds(130, 50, 450, 440); // 360

        backupObjects = new HashMap<String, BackupRestoreObject>();

        font_big = ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD);
        font_normal = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        font_normal_b = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);

        this.cmdUpdate();

        this.setResizable(false);
        ATSwingUtils.centerJDialog(this, this.my_parent); // dataAccess.getParent());

        this.m_da.addComponent(this);

    }


    /**
     * Show Dialog
     */
    public void showDialog()
    {
        this.setVisible(true);
    }


    /**
     * Displays title for dialog
     * 
     * @param backup 
     */
    public void showTitle(boolean backup)
    {
        if (backup)
        {
            this.setTitle(ic.getMessage("BACKUP_DB_TITLE"));
            label_title.setText(ic.getMessage("BACKUP_DB_TITLE_SHORT"));
        }
        else
        {
            this.setTitle(ic.getMessage("RESTORE_DB_TITLE"));
            label_title.setText(ic.getMessage("RESTORE_DB_TITLE_SHORT"));
        }
    }


    /**
     *   Displays GUI
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

        JLabel label = new JLabel(ic.getMessage("SELECT_ELEMENTS_TO_BACKUP"));
        label.setBounds(25, 70, 500, 25);
        label.setFont(this.font_normal_b);
        panel.add(label);

        tree = new CheckNodeTree(this.backupRestoreRoot, CheckNode.DIG_IN_SELECTION, new BackupRestoreTreeRenderer());
        /*
         * tree.setCellRenderer(new TreeCellRenderer() {
         * public Component getTreeCellRendererComponent(JTree arg0, Object
         * value, boolean arg2, boolean arg3,
         * boolean arg4, int arg5, boolean arg6)
         * {
         * CheckNode cn = (CheckNode)value;
         * BackupRestoreBase bro = (BackupRestoreBase)cn.getObject();
         * return new JLabel(bro.getTargetName());
         * }
         * });
         */

        JScrollPane scroll = new JScrollPane(tree);
        scroll.setBounds(25, 100, 250, 150);
        panel.add(scroll);
        scroll.repaint();
        scroll.updateUI();

        // ---
        // --- Backup Command
        // ---
        button_backup = new JButton("  " + ic.getMessage("BACKUP"));
        button_backup.setToolTipText(ic.getMessage("BACKUP"));
        button_backup.setBounds(290, 100, 130, 30);
        button_backup.setIcon(ATSwingUtils.getImageIcon_22x22("export1.png", this, m_da));
        button_backup.addActionListener(this);
        button_backup.setFont(font_normal);
        button_backup.setActionCommand("backup");
        panel.add(button_backup);

        // ---
        // --- Close Command
        // ---
        button = new JButton("  " + ic.getMessage("CLOSE"));
        button.setBounds(290, 140, 130, 30);
        button.setIcon(ATSwingUtils.getImageIcon_22x22("cancel.png", this, m_da));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("close");
        panel.add(button);

        // ---
        // --- Help command
        // ---
        button_help = ATSwingUtils.createHelpButtonByBounds(290, 180, 130, 30, this, ATSwingUtils.FONT_NORMAL, m_da);
        button_help.setFont(font_normal);
        panel.add(button_help);

        label_total_progress = new JLabel(); // "<html><b>" +
                                             // i18nControl.getMessage("TOTAL_PROGRESS")
                                             // +
                                             // ":</b>&nbsp;&nbsp;&nbsp;&nbsp; "
                                             // +
                                             // i18nControl.getMessage("BACKUP_NOT_STARTED_YET")
                                             // + "</html>");
        label_total_progress.setBounds(25, 325, 380, 25);
        label_total_progress.setFont(this.font_normal);
        panel.add(label_total_progress);

        this.progressFull = new JProgressBar(0, 100);
        this.progressFull.setBounds(25, 350, 380, 20);
        this.progressFull.setStringPainted(true);
        // this.progressFull.setIndeterminate(true);
        panel.add(this.progressFull);

        label_current_progress = new JLabel(); // i18nControl.getMessage("CURRENT_TASK")
                                               // + ": " +
                                               // i18nControl.getMessage("NO_TASK_STARTED"));
        label_current_progress.setBounds(25, 265, 380, 25); // 265
        label_current_progress.setFont(this.font_normal);
        panel.add(label_current_progress);

        // this.label_current_progress.

        this.progressCurrent = new JProgressBar(0, 100);
        this.progressCurrent.setBounds(25, 290, 380, 20); // 290
        this.progressCurrent.setStringPainted(true);
        // this.progressCurrent.setString(null);
        // this.progressCurrent.setIndeterminate(true);
        panel.add(this.progressCurrent);

        // this.progressCurrent.setValue(n)
        setTask(null);

    }


    /**
     * Set Task
     * 
     * @see com.atech.db.hibernate.transfer.BackupRestoreWorkGiver#setTask(java.lang.String)
     */
    public void setTask(String task)
    {
        if (task == null)
        {
            label_total_progress.setText("<html><b>" + ic.getMessage("TOTAL_PROGRESS") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;"
                    + ic.getMessage("BACKUP_NOT_STARTED_YET") + "</html>");
            label_current_progress.setText("<html><b>" + ic.getMessage("CURRENT_TASK") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;"
                    + ic.getMessage("NO_TASK_STARTED") + "</html>");
        }
        else
        {
            this.done_backup_elements++;

            int tsk = this.done_backup_elements;
            tsk++;

            // System.out.println("Task: " + tsk);
            label_total_progress.setText("<html><b>" + ic.getMessage("TOTAL_PROGRESS") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;"
                    + ic.getMessage("TASK") + " (" + tsk + "/" + this.count_of_backup_elements + ")</html>");
            label_current_progress.setText(
                "<html><b>" + ic.getMessage("CURRENT_TASK") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;" + task + "</html>");
        }
    }


    /**
     * Set Status
     * 
     * @see com.atech.db.hibernate.transfer.BackupRestoreWorkGiver#setStatus(int)
     */
    public void setStatus(int procent)
    {
        // System.out.println("setStatus BRD:" + procent);
        int cnt = this.done_backup_elements * 100 + procent;

        cnt /= this.count_of_backup_elements;
        // System.out.println("cnt:" + procent);

        this.progressFull.setValue(cnt);

        // this.progressFull.setString("" + cnt + " %");

        this.progressCurrent.setValue(procent);
        // this.progressCurrent.setString("" + procent + " %");

    }


    /**
     * Action Performed
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();

        if (cmd.equals("backup"))
        {
            cleanBackupDirectory();
            preprocesData();

            if (this.count_of_backup_elements == 0)
                return;

            performBackup();
            button_backup.setEnabled(false);
        }
        else if (cmd.equals("close"))
        {
            this.m_da.removeComponent(this);
            this.dispose();
        }

    }


    private void cleanBackupDirectory()
    {
        File f = new File("../data");

        if (!f.exists())
        {
            f.mkdir();
        }

        f = new File("../data/export");

        if (!f.exists())
        {
            f.mkdir();
        }

        f = new File("../data/export/tmp");

        if (!f.exists())
        {
            f.mkdir();
        }

        File[] all_files = f.listFiles();

        for (File all_file : all_files)
        {
            all_file.delete();
        }
    }


    /**
     * Pack Backup Files
     */
    public void packBackupFiles()
    {

    }


    /*
     * cleanBackupDirectory();
     * packBackupFiles();
     */

    private void preprocesData()
    {
        traverseTree(this.backupRestoreRoot);

        int elements_count = 0;

        for (BackupRestoreObject bro : this.backupObjects.values())
        {
            if (bro.isSelected())
            {
                elements_count++;
            }
        }

        // System.out.println("Elements counts: " + elements_count);
        this.count_of_backup_elements = elements_count;
    }


    /**
     * Is BackupRestore Object Selected
     * @param key
     * @return
     */
    public boolean isBackupRestoreObjectSelected(String key)
    {
        if (this.backupObjects.containsKey(key))
            return this.backupObjects.get(key).isSelected();
        else
            return false;
    }


    /**
     * Traverse Tree
     * 
     * @param cb
     */
    public void traverseTree(BackupRestoreBase cb)
    {

        if (!cb.hasNodeChildren())
        {
            // no children
            cb.setSelected(tree.getValueForNode(cb.getTargetName()));

            if (!cb.isCollection())
            {
                backupObjects.put(cb.getTargetName(), (BackupRestoreObject) cb);
            }

        }
        else
        {
            // children
            List<CheckBoxTreeNodeInterface> lst = cb.getNodeChildren();

            for (int i = 0; i < lst.size(); i++)
            {
                traverseTree((BackupRestoreBase) lst.get(i));
            }
        }
    }


    /**
     * Perform Backup
     */
    public abstract void performBackup();


    /**
     *  Gets info if action was performed.
     * 
     *  @return true if action was done, false if not.
     */
    public boolean wasAction()
    {
        if (lastAction == 1)
            return true;
        else
            return false;
    }


    /**
     *  Returns object saved
     * 
     *  @return object of type of Object
     */
    public Object getObject()
    {
        return null;
    }

    String help_id = null;


    /** 
     * enableHelp
     */
    public void enableHelp(String help_id_p)
    {
        this.help_id = help_id_p;
        m_da.enableHelp(this);
    }


    /** 
     * getComponent
     */
    public Component getComponent()
    {
        return this;
    }


    /** 
     * getHelpButton
     */
    public JButton getHelpButton()
    {
        return button_help;
    }


    /** 
     * getHelpId
     */
    public String getHelpId()
    {
        return this.help_id;
    }

}
