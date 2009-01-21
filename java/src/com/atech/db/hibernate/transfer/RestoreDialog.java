package com.atech.db.hibernate.transfer;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.atech.graphics.components.tree.CheckBoxTreeNodeInterface;
import com.atech.graphics.components.tree.CheckNode;
import com.atech.graphics.components.tree.CheckNodeTree;
import com.atech.help.ComponentHelpCapable;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccess;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.file.UnPackFiles;

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

public abstract class RestoreDialog extends JDialog implements ActionListener, BackupRestoreWorkGiver, HelpCapable, ComponentHelpCapable
{

    private static final long serialVersionUID = -9166774725245737896L;
    protected I18nControlAbstract ic = null;
    protected ATDataAccessAbstract m_da = null;


    /*
     *  Globaly used variables
     */
    protected JPanel panel;
    JLabel label, label_title;
    JButton button, button_backup, button_help;
    Font font_big, font_normal, font_normal_b;
    
    JLabel label_date;

    JProgressBar progress_full, progress_current;
    
    CheckNodeTree tree;
    
    int m_error = 0;
    int lastAction = 0;  // no event

    protected int count_of_backup_elements = 0;
    protected int done_backup_elements = -1;
    

    JLabel label_total_progress, label_current_progress; 
    
    
    

    protected BackupRestoreCollection backuprestore_root;
    protected Hashtable<String,BackupRestoreObject> ht_restore_objects = new Hashtable<String,BackupRestoreObject>();
    protected Hashtable<String,RestoreFileInfo> restore_files = new Hashtable<String,RestoreFileInfo>();
    
    Container my_parent = null;
    String filename;
    
    
    public RestoreDialog(JDialog parent, ATDataAccessAbstract da, BackupRestoreCollection br_coll, String filename)
    {
        super(parent, "", true);
        
        my_parent = parent;
        m_da = da; 
        this.ic = m_da.getI18nControlInstance();
            //ATDataAccess.getInstance();
        //m_da.setParent(parent);
        this.backuprestore_root = br_coll;
        this.filename = filename;
        //System.out.println("BRD ic: " + this.ic);
        
        
        init();
    }

    
    public RestoreDialog(JFrame parent, ATDataAccessAbstract da, BackupRestoreCollection br_coll, String filename)
    {
        super(parent, "", true);
//      m_da = ATDataAccess.getInstance();
//      m_da.setParent(parent);
        my_parent = parent;
        m_da = da; 
        this.ic = m_da.getI18nControlInstance();
        this.backuprestore_root = br_coll;
        this.filename = filename;
      
      //backuprestore_root = br_coll;
      
        init();
    }
    
    
    public abstract void performRestore();
    
    
    public void init()
    {
        
        this.setBounds(130, 50, 450, 450); // 360

        //ht_backup_objects = new Hashtable<String,BackupRestoreObject>(); 
        
        font_big = m_da.getFont(ATDataAccess.FONT_BIG_BOLD); 
        font_normal = m_da.getFont(ATDataAccess.FONT_NORMAL);
        font_normal_b = m_da.getFont(ATDataAccess.FONT_NORMAL_BOLD);

        //m_mass = mse;
    
        //this.loadNeededData();

        //this.uo_root = uo_root;
        //this.update_system = usys;
        //this.list = update_system.getUpdateTable();
        
        this.prepareRestore();

        this.cmdRestore();
    
        this.setResizable(false);
        this.m_da.addComponent(this);
        this.m_da.centerJDialog(this); //m_da.getParent());
        
        //this.setVisible(true);
    }
    
    
    public void showDialog()
    {
        this.setVisible(true);
    }
    
    
    
    
    /**
     *   Displays title for dialog
     * @param backup 
     */
    public void showTitle(boolean backup)
    {
        this.setTitle(ic.getMessage("RESTORE_DB_TITLE"));
        label_title.setText(ic.getMessage("RESTORE_DB_TITLE_SHORT"));
    }


    boolean restore_possible = true;
    
    public void prepareRestore()
    {
        cleanRestoreDirectory();
        unpackRestoreFiles();

        processBackupRestoreCollection();
        
        this.restore_possible = this.backuprestore_root.hasChildren();
        
    }


    private void processBackupRestoreCollection()
    {
        processCollection(this.backuprestore_root);
    }

    private void processCollection(BackupRestoreCollection brc)
    {
        boolean finished = false;
         
        
        
        do
        {
            for(int i=0; i<brc.childCount(); i++)
            {
                if (brc.getChild(i) instanceof BackupRestoreCollection)
                {
                    processCollection((BackupRestoreCollection)brc.getChild(i));
                    // remove empty folder
                    if (!brc.getChild(i).hasChildren())
                    {
                        brc.removeChild(i);
                        break;
                    }
                }
                else
                {
                
                
                    if (!this.restore_files.containsKey(brc.getChild(i).getClassName()))
                    {
                        brc.removeChild(i);
                        break;
                    }
                }
                
                if (i==(brc.childCount()-1))
                {
                    finished = true;
                }
                
            }
            
            if (brc.childCount()==0)
                finished = true;
        } while (!finished);
    }


    /**
     *   Displays GUI
     */
    public void cmdRestore()
    {

        Container dgPane = this.getContentPane();

        panel = new JPanel();
        panel.setBounds(5, 5, 450, 450);
        panel.setLayout(null);              // 600 450
        dgPane.add(panel);

        
        label_title = new JLabel();
        label_title.setBounds(0, 15, 450, 40);
        label_title.setFont(font_big); 
        label_title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label_title, null);
        
        showTitle(true);
        
        JLabel label = new JLabel(ic.getMessage("SELECT_ELEMENTS_TO_RESTORE"));
        label.setBounds(25, 70, 300, 25);
        label.setFont(this.font_normal_b);
        panel.add(label);
        
        tree = new CheckNodeTree(this.backuprestore_root, CheckNode.DIG_IN_SELECTION);
        
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setBounds(25, 100, 250, 150);
        panel.add(scroll);
        scroll.repaint();
        scroll.updateUI();


        // ---
        // --- Backup Command
        // ---
        button_backup = new JButton("   " +ic.getMessage("RESTORE"));
        button_backup.setBounds(290,100,130,30);
        button_backup.setIcon(m_da.getImageIcon_22x22("export1.png", this));
        button_backup.addActionListener(this);
        button_backup.setFont(font_normal);
        button_backup.setActionCommand("restore");
        button_backup.setEnabled(this.restore_possible);
        panel.add(button_backup);
    
        // ---
        // --- Close Command
        // ---
        button = new JButton("   " +ic.getMessage("CLOSE"));
        button.setBounds(290,140,130,30);
        button.setIcon(m_da.getImageIcon_22x22("cancel.png", this));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("close");
        panel.add(button);
    

        // ---
        // --- Help command
        // ---
        button_help = m_da.createHelpButtonByBounds(290, 180, 130, 30, this);
        button_help.setFont(font_normal);
        panel.add(button_help);
        

        label_current_progress = new JLabel(); //ic.getMessage("CURRENT_TASK") + ":    " + ic.getMessage("NO_TASK_STARTED"));
        label_current_progress.setBounds(25, 265, 300, 25);
        label_current_progress.setFont(this.font_normal);
        panel.add(label_current_progress);

        
        this.progress_current = new JProgressBar(0, 100);
        this.progress_current.setBounds(25, 290, 380, 20);
        this.progress_current.setStringPainted(true);
        panel.add(this.progress_current);
        
        
        
        label_total_progress = new JLabel(); //"<html><b>" + ic.getMessage("TOTAL_PROGRESS") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;    " + ic.getMessage("BACKUP_NOT_STARTED_YET") + "</html>");
        label_total_progress.setBounds(25, 330, 300, 25);
        label_total_progress.setFont(this.font_normal);
        panel.add(label_total_progress);
         
        
        this.progress_full = new JProgressBar(0, 100);
        this.progress_full.setBounds(25, 355, 380, 20);
        this.progress_full.setStringPainted(true);
        //this.progress_full.setIndeterminate(true);
        panel.add(this.progress_full);
        
        
        setTask(null);
        
        initSpecial();
        
        
    }
    

    /**
     * If there are any special options you need displayed, you need to put them here, and then add them
     * to panel (called panel). You also need to resize this panel and also whole dialog.
     * 
     *   this.setBounds(130, 50, 450, 450); 
     */
    public abstract void initSpecial();
    
    

    public void setTask(String task)
    {
        if (task == null)
        {
            label_total_progress.setText("<html><b>" + ic.getMessage("TOTAL_PROGRESS") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;" + ic.getMessage("RESTORE_NOT_STARTED_YET") + "</html>");
            label_current_progress.setText("<html><b>" +ic.getMessage("CURRENT_TASK") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;" + ic.getMessage("NO_TASK_STARTED")  + "</html>");       
        }
        else
        {
            this.done_backup_elements++;
            
            int tsk = this.done_backup_elements;
            tsk++;
            
//            System.out.println("Task: " + tsk);
            label_total_progress.setText("<html><b>" + ic.getMessage("TOTAL_PROGRESS") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;" + ic.getMessage("TASK") + " (" + tsk + "/" + this.count_of_backup_elements + ")</html>");
            label_current_progress.setText("<html><b>" +ic.getMessage("CURRENT_TASK") + ":</b>&nbsp;&nbsp;&nbsp;&nbsp;" + task  + "</html>");       
        }
    }


    
    private void cleanRestoreDirectory()
    {
        File f = new File("../data");
    
        if (!f.exists())
            f.mkdir();
    
        f = new File("../data/import");

        if (!f.exists())
            f.mkdir();

        File[] all_files = f.listFiles();
        
        for(int i=0; i<all_files.length; i++)
        {
            all_files[i].delete();
        }
    }
    
    
    public void unpackRestoreFiles()
    {
        //System.out.println("Filename:" + this.filename);
        
        if (filename.endsWith(".dbe"))
        {
            File f = new File(this.filename);
            f.compareTo(new File("../data/import"));
        }
        else
        {
            new UnPackFiles(this.filename, true, "../data/import");
        }
        
        getFilesStatus();
        
    }
    
    
    
    private void getFilesStatus()
    {
        File f = new File("../data/import");
        
        File[] lst = f.listFiles(new FilenameFilter()
                        {
                            public boolean accept(File dir, String name)
                            {
                                return (name.endsWith(".dbe"));
                            }
                            
                        });
        
        for(int i=0; i<lst.length; i++)
        {
            RestoreFileInfo rfi = new RestoreFileInfo(lst[i]);
            
            //System.out.println(rfi);
            
            this.restore_files.put(rfi.class_name, rfi);
            
        }
        
    }
    
    
    
    public void setStatus(int procent)
    {
        //System.out.println("setStatus BRD:" + procent);
        int cnt = (this.done_backup_elements * 100) + procent;
        
        cnt /= this.count_of_backup_elements;
        //System.out.println("cnt:" + procent);
        
        this.progress_full.setValue(cnt);
        
        //this.progress_full.setString("" + cnt + " %");
        
        this.progress_current.setValue(procent);
        //this.progress_current.setString("" + procent + " %");
        
    }
    

    
    

    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        
        if (cmd.equals("restore"))
        {
            preprocesData();
            
            if (this.count_of_backup_elements==0)
                return;
            
            performRestore();
            button_backup.setEnabled(false);
        }
        else if (cmd.equals("close"))
        {
            this.m_da.removeComponent(this);
            this.dispose();
        }
            
        
    }


    
    
    
    
/*    
    cleanBackupDirectory();
    packBackupFiles();
 */   

    
    private void preprocesData()
    {
        traverseTree(this.backuprestore_root);
        
        int elements_count = 0;
        
        for(Enumeration<String> en = this.ht_restore_objects.keys(); en.hasMoreElements(); )
        {
            BackupRestoreObject bro = (BackupRestoreObject)this.ht_restore_objects.get(en.nextElement());
            
            if (this.restore_files.containsKey(bro.getClassName()))
            {
                this.restore_files.get(bro.getClassName()).selected = bro.isSelected();

                if (bro.isSelected())
                    elements_count++;
            }
            
        }
        
        this.count_of_backup_elements = elements_count;
    }
    
    /* a2
    public boolean isBackupRestoreObjectSelected(String key)
    {
        if (this.ht_backup_objects.containsKey(key))
            return this.ht_backup_objects.get(key).isSelected();
        else
            return false;
    }
    */
    
    
    //private void preprocessData(CheckNode node, )
    public void traverseTree(BackupRestoreBase cb)
    {
        
        if (!cb.hasChildren())
        {
            // no children
            cb.setSelected(tree.getValueForNode(cb.getTargetName()));
            
            if (!cb.isCollection())
                ht_restore_objects.put(cb.getTargetName(), (BackupRestoreObject)cb);
            
        }
        else
        {
            // children
            ArrayList<CheckBoxTreeNodeInterface> lst = cb.getChildren();
            
            for(int i=0; i<lst.size(); i++)
            {
                traverseTree((BackupRestoreBase)lst.get(i));
            }
        }
    }
    
    /*
    public void performBackup()
    {
        
    }*/
    
    
    
    /**
     *  Gets info if action was performed.
     * 
     *  @return true if action was done, false if not.
     */
    public boolean wasAction()
    {
        if (lastAction==1)
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
    
    /* 
     * enableHelp
     */
    public void enableHelp(String help_id)
    {
        this.help_id = help_id;
        m_da.enableHelp(this);
    }


    /* 
     * getComponent
     */
    public Component getComponent()
    {
        return this;
    }


    /* 
     * getHelpButton
     */
    public JButton getHelpButton()
    {
        return button_help; 
    }


    /* 
     * getHelpId
     */
    public String getHelpId()
    {
        return this.help_id;
    }

/*
    public static void main(String args[])
    {
        JFrame fr = new JFrame();
        fr.setSize(800,600);
        
        new RestoreDialog(fr, ATDataAccess.getInstance());
    }
*/



}


