/**
 * 
 */
package com.atech.i18n.tool.simple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.tool.simple.data.DataEntry;
import com.atech.i18n.tool.simple.data.DataListProcessor;
import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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
public class TranslationTool extends JFrame implements ActionListener
{
    
    private static final long serialVersionUID = 8072388083288536444L;
    Hashtable<String,JMenu> menus = null; 
    DataAccessTT m_da = DataAccessTT.getInstance();
    I18nControlAbstract m_ic = null;
    String m_version = "0.0.1";
    DataListProcessor dlp;
    
    JLabel module, group, index, keyword;
    JLabel[] statuses;
    JButton priority;
    JTextArea jt_source, jt_desc, jt_mine;
    JComboBox cmb_status;
    
    
    /**
     * Constructor
     */
    public TranslationTool()
    {
        super();

        m_da = DataAccessTT.createInstance(this);
        m_ic = m_da.getI18nControlInstance();
        //m_da.startDb();
        
        init();
        
        this.dlp = new DataListProcessor("");
        this.dlp.moveFirst();
        this.readData();

        if (!this.dlp.wasMasterFileRead())
        {
            showTypesDialog("Master file was not read correctly.", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
//            init();
        
            this.setSize(520, 640);
            this.setVisible(true);
        }
    }

    
    private void showTypesDialog(String msg, int type)
    {
        String type_desc = "";
        
        if (type ==JOptionPane.ERROR_MESSAGE)
            type_desc = "Error";
        else if (type ==JOptionPane.WARNING_MESSAGE)
            type_desc = "Warning";
        
        JOptionPane.showMessageDialog(this, msg, type_desc, type);
        
    }
    
    
    private void init()
    {
        this.setTitle("Simple Translation Tool (v" + m_version + ")");
        initMenus();
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        
        
        ATSwingUtils.initLibrary();
        
        
        ATSwingUtils.getLabel("Module:", 30, 20, 120, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        module = ATSwingUtils.getLabel("Unknown module", 110, 20, 120, 25, panel, ATSwingUtils.FONT_NORMAL);
        ATSwingUtils.getLabel("Group:", 30, 50, 120, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        group = ATSwingUtils.getLabel("Unknown group", 110, 50, 120, 25, panel, ATSwingUtils.FONT_NORMAL);
        ATSwingUtils.getLabel("Group Priority:", 330, 50, 120, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        priority = ATSwingUtils.getButton("x", 430, 50, 40, 25, panel, ATSwingUtils.FONT_NORMAL, null, "show_group_info", this, m_da);
        

        ATSwingUtils.getLabel("Index:", 30, 75, 60, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        index = ATSwingUtils.getLabel("0000", 80, 75, 120, 25, panel, ATSwingUtils.FONT_NORMAL);
        
        ATSwingUtils.getLabel("Key:", 140, 75, 40, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        keyword = ATSwingUtils.getLabel("No id", 180, 75, 300, 25, panel, ATSwingUtils.FONT_NORMAL);
        //keyword.setBackground(Color.blue);
        

        statuses = new JLabel[3];

        ATSwingUtils.getLabel("Not translated:", 30, 100, 120, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        statuses[0] = ATSwingUtils.getLabel("0000", 130, 100, 60, 25, panel, ATSwingUtils.FONT_NORMAL);
        
        ATSwingUtils.getLabel("Need to be checked:", 180, 100, 120, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        statuses[1] = ATSwingUtils.getLabel("0000", 310, 100, 60, 25, panel, ATSwingUtils.FONT_NORMAL);
        
        ATSwingUtils.getLabel("Translated:", 360, 100, 120, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        statuses[2] = ATSwingUtils.getLabel("0000", 450, 100, 300, 25, panel, ATSwingUtils.FONT_NORMAL);
        
        
        int yst = 150;
        
        ATSwingUtils.getLabel("Master Text File Translation", 30, yst, 300, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        jt_source = ATSwingUtils.getTextArea("Text Master File", 30, yst+30, 450, 60, panel);
        jt_source.setEditable(false);
        
        ATSwingUtils.getLabel("Description", 30, yst+100, 300, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        jt_desc = ATSwingUtils.getTextArea("No Description", 30, yst+130, 450, 60, panel);
        jt_desc.setEditable(false);

        
        ATSwingUtils.getButton("Copy from Master file", 310, yst+200, 170, 25, panel, ATSwingUtils.FONT_NORMAL, null, "copy_text", this, m_da);
        
        ATSwingUtils.getLabel("My Translation", 30, yst+200, 300, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);
        jt_mine = ATSwingUtils.getTextArea("Text", 30, yst+230, 450, 60, panel);
        //jt_mine.setEditable(false);

        
        
        
        
        cmb_status = ATSwingUtils.getComboBox(m_da.status, 330, yst+295, 150, 25, panel, ATSwingUtils.FONT_NORMAL);
        
        int[] sz = { 40, 40 } ;
        
        ATSwingUtils.getButton("", 30, yst+330, 50, 50, panel, ATSwingUtils.FONT_NORMAL, "find_previous.png", "prev_untranslated", this, m_da, sz);
        ATSwingUtils.getButton("", 90, yst+330, 50, 50, panel, ATSwingUtils.FONT_NORMAL, "nav_left_blue.png", "prev", this, m_da, sz);
        ATSwingUtils.getButton("", 150, yst+330, 50, 50, panel, ATSwingUtils.FONT_NORMAL, "nav_right_blue.png", "next", this, m_da, sz);
        ATSwingUtils.getButton("", 210, yst+330, 50, 50, panel, ATSwingUtils.FONT_NORMAL, "find_next.png", "next_untranslated", this, m_da, sz);
        ATSwingUtils.getButton("", 370, yst+330, 50, 50, panel, ATSwingUtils.FONT_NORMAL, "disk_blue.png", "save", this, m_da, sz);
        ATSwingUtils.getButton("", 430, yst+330, 50, 50, panel, ATSwingUtils.FONT_NORMAL, "door2.png", "exit", this, m_da, sz);
        
        this.add(panel);
    }
    
    
    /**
     * Read Data
     */
    public void readData()
    {
        DataEntry de = this.dlp.getCurrentEntry();
        
        if (de==null)
            return;
        
        //System.out.println("master translation: " + de.master_file_translation);
        
        this.jt_source.setText(de.master_file_translation);
        
        if (de.description==null)
            this.jt_desc.setText("No description available.");
        else
            this.jt_desc.setText(de.description);
        
        this.jt_mine.setText(de.target_translation);
        
        this.cmb_status.setSelectedIndex(de.status);
        
        this.index.setText((dlp.current_index+1) + "");
        this.keyword.setText(de.key);
        
        this.dlp.resetStatus();
        
        int[] st = this.dlp.getStatuses();
        
        this.statuses[0].setText("" + st[0]);
        this.statuses[1].setText("" + st[1]);
        this.statuses[2].setText("" + st[2]);
        
        
    }
    

    /**
     * Save Data
     */
    public void saveData()
    {
        DataEntry de = this.dlp.getCurrentEntry();
        
        if (!de.target_translation.equals(this.jt_mine.getText()))
        {
            // changed
            de.target_translation = this.jt_mine.getText(); 
            
            if (this.cmb_status.getSelectedIndex()==0)
            {
                if (de.target_translation.equals(de.master_file_translation))
                {
                    int st = JOptionPane.showOptionDialog(this, "Your translation looks same as translation\n" +
                    		"from master file. This indicates that no\n" +
                    		"translation was made. But this also might not\n" +
                    		"be true. Select status for this translation.", 
                    		"Change Status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, 
                    		null, this.m_da.status, this.m_da.status[2]);
                    
                    de.status = st;
                    this.dlp.resetStatus();
                }
                else
                {
                    de.status = DataEntry.STATUS_TRANSLATED;
                    this.dlp.resetStatus();
                }
            }
            
        }
        else
        {
            // same
            if (de.status != this.cmb_status.getSelectedIndex())
            {
                de.status = this.cmb_status.getSelectedIndex();
                this.dlp.resetStatus();
            }
        }
        
    }
    
    
    private void initMenus()
    {
        JMenuBar mbar = new JMenuBar();
        menus = new Hashtable<String,JMenu>();
        
        JMenu menu = ATSwingUtils.createMenu("File", "File", m_ic);
        
        ATSwingUtils.createMenuItem(menu, "Exit", "Exit Application", "exit", this, null, m_ic, m_da, this);
        
        menus.put("FILE", menu);
        mbar.add(menu);
        
        
        menu = ATSwingUtils.createMenu("Help", "Help", m_ic);
        
        ATSwingUtils.createMenuItem(menu, "About", "About Software", "about", this, null, m_ic, m_da, this);

        menus.put("HELP", menu);
        mbar.add(menu);
        
        this.setJMenuBar(mbar);
        
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new TranslationTool();
        // TODO Auto-generated method stub

    }

    private void cmdQuit()
    {
        System.exit(0);
    }


    /** 
     * Action Performed
     */
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub
        String cmd = e.getActionCommand();
        
        if (cmd.equals("exit"))
        {
            cmdQuit();
        }
        else if (cmd.equals("about"))
        {
            System.out.println("No About !!!!");
        }
        else if (cmd.equals("copy_text"))
        {
            jt_mine.setText(jt_source.getText());
        }
        else if (cmd.equals("next"))
        {
            this.saveData();

            if (this.dlp.moveNext())
                this.readData();
        }
        else if (cmd.equals("prev"))
        {
            this.saveData();

            if (this.dlp.movePrev())
                this.readData();
        }
        else if (cmd.equals("save"))
        {
            this.dlp.saveTranslation();
        }
        else
            System.out.println("Unknown command: " + cmd);
        
        
    }

}
