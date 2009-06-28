package com.atech.update.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import com.atech.help.ComponentHelpCapable;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.update.config.ComponentInterface;
import com.atech.update.config.UpdateConfiguration;
import com.atech.utils.ATDataAccess;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;


/**
 *  This file is part of ATech Tools library.
 *  
 *  UpdateDialog - Dialog for updates
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
 *  @author Andy {andy@atech-software.com}
 *
*/

// NOT FULLY IMPLEMENTED YET.


public class UpdateDialog extends JDialog implements ActionListener, HelpCapable, ComponentHelpCapable
{

    private static final long serialVersionUID = -8822530996424234341L;

    UpdateSystemModel model = null;

    ATDataAccessAbstract m_da = null;
    I18nControlAbstract ic = null;


    JTabbedPane tabbedPane;
    ArrayList<ComponentInterface> list;

    /*
     *  Globaly used variables
     */
    JPanel panel;
    JLabel label, label_title, status_label;
    JButton button, help_button;
    Font font_big, font_normal, font_normal_b;






    // new


    int m_error = 0;


    UpdateConfiguration update_config = null;
    
    int lastAction = 0;  // no event



    /**
     * Constructor
     * 
     * @param parent
     * @param uconf
     * @param da
     */
    public UpdateDialog(JDialog parent, UpdateConfiguration uconf, ATDataAccessAbstract da)
    {
    	super(parent, "", true);
    	m_da = da;
    	ic = m_da.getI18nControlInstance();

    	this.update_config = uconf;
    	
    	init();
    }
    
    
    
    /*
    public UpdateDialog(JFrame parent, UpdateSystem usys)
    {
    	super(parent, "", true);
    	m_da = ATDataAccess.getInstance();
    	
    	m_da.setParent(parent);
    	this.update_system = usys;
    	init();
    }*/

    
    
    /**
     * Constructor
     * 
     * @param parent
     * @param uconf
     * @param da
     */
    public UpdateDialog(JFrame parent, UpdateConfiguration uconf, ATDataAccessAbstract da)
    {
    	super(parent, "", true);

    	m_da = da;
        ic = m_da.getI18nControlInstance();

    	this.update_config = uconf;
    	
    	init();
    }
    

    /**
     * Constructor
     * 
     * @param parent
     * @param da
     */
    public UpdateDialog(JFrame parent, ATDataAccessAbstract da)
    {
        this(parent, da.getUpdateConfiguration(), da);
    }
    
    
    
    
    
    /**
     *   Constructor for PersonAddress
     *   Builds starting UI (all menus with pictures and all other frames)
     */
//    public MassDialog(DataAccess da, News nws, int type) 
/*    public UpdateDialog(ATDataAccess da, UpdateSystem usys) 
    {

        super((Frame)null, "", true);
        //super.setModal(true);

        
        //new JDialog()
        

        m_da = da; 
    	this.update_system = usys;


        init();
    }
*/

    public void init()
    {
    	
        this.setBounds(130, 50, 650, 450); // 360

        font_big = m_da.getFont(ATDataAccess.FONT_BIG_BOLD); 
        font_normal = m_da.getFont(ATDataAccess.FONT_NORMAL);
        font_normal_b = m_da.getFont(ATDataAccess.FONT_NORMAL_BOLD);

    	//m_mass = mse;
    
        ATSwingUtils.initLibrary();
        
    	//this.loadNeededData();

        //this.uo_root = uo_root;
        //this.update_system = usys;
        this.list = this.update_config.getUpdateTable(); 
            //update_system.getUpdateTable();
        
        

        this.cmdUpdate();
    	//this.loadMass();
    
    	this.setResizable(false);
    	this.m_da.centerJDialog(this, m_da.getParent());


        this.m_da.addComponent(this);

        //this.setVisible(true);
    	
    	
    }
    
    
    
    
    /**
     *   Displays title for dialog
     */
    public void showTitle()
    {
        //label_title
        String ev = ic.getMessage("UPDATE_APPLICATION");

        this.setTitle(ev);
        label_title.setText(ev);

    }








    /**
     *   Displays GUI
     */
    public void cmdUpdate()
    {

        Container dgPane = this.getContentPane();

        panel = new JPanel();
        panel.setBounds(5, 5, 620, 400);
        panel.setLayout(null);              // 600 450
        dgPane.add(panel);

        
        label_title = new JLabel();
        label_title.setBounds(30, 20, 450, 40);
        label_title.setFont(font_big); 
        label_title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label_title, null);
        
        showTitle();
        
        
/*        
        StringBuffer sb = new StringBuffer();
        sb.append(ic.getMessage("LEGEND"));
        sb.append(":                  ");
        sb.append(ic.getMessage("NEWEST"));
        sb.append("                  ");
        sb.append(ic.getMessage("NOT_UPDATED"));
        sb.append("                  ");
        sb.append(ic.getMessage("NOT_UPDATABLE"));
        sb.append("                  ");
        sb.append(ic.getMessage("UNKNOWN_STATUS"));
        
        this.label = new JLabel(sb.toString());
        label.setBounds(30, 340, 600, 30);
        panel.add(label, null);
  */
        
        
        this.label = ATSwingUtils.getLabel(ic.getMessage("SERVER_STATUS") + ":", 30, 70, 120, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD); 
        
        this.status_label = ATSwingUtils.getLabel(ic.getMessage("SERVER_STATUS"), 150, 70, 320, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD); 
        
        
        this.label = new JLabel(ic.getMessage("LEGEND") +":");
        label.setBounds(30, 340, 100, 30);
        panel.add(label, null);
        
        
        int[] x_kors = { 105, 200, 330, 470 };
        int[] widths = { 100, 150, 150, 200 };
        String[] pictures = { "dot_green.gif", "dot_red.gif", "dot_orange.gif", "dot_blue.gif" };
        String[] leg_label = {  "  " + ic.getMessage("NEWEST"), 
                            "  " + ic.getMessage("NOT_UPDATED"),
                            "  " + ic.getMessage("NOT_UPDATABLE"),
                            "  " + ic.getMessage("UNKNOWN_STATUS")
                            };
        
        for(int i=0; i<x_kors.length; i++)
        {
            label = new JLabel(leg_label[i]);
            label.setIcon(m_da.getImageIcon("/icons/", pictures[i], this)); //width, height, comp)new ImageIcon(m_da.getImage("/icons/", pictures[i], this)));
            label.setBounds(x_kors[i], 344, widths[i], 25);
            panel.add(label);
        }
        
        this.model = new UpdateSystemModel(this.list, m_da);
        
        JTable table = new JTable(this.model);

/*
            new AbstractTableModel()
                           {

                               //Object work[] = alist.toArray();

                               public int getColumnCount() 
                               {
                                   UpdateObject uo = new UpdateObject();

                                   return 5; //uo.getColumnCount();
                                       //select_typeObj.getColumnCount();
                                   //return 2;
                               }


                               public int getRowCount() 
                               {

                                   if (list==null)
                                       return 0;
                                   else
                                       return list.size();
                               }


                               public Object getValueAt(int rowIndex, int columnIndex) 
                               {
                                   String result= "";

                                   //Post pst = list.get(rowIndex);

                                   //Selectable sel = list.get(rowIndex);

                                   return list.get(rowIndex).getColumnValue(columnIndex); //..getColumnValue(columnIndex+1);

                               }
                           });  // table

  */      
        //table.setDefaultRenderer(new UpdateTableCellRenderer(m_da));

        //int procent = (int)((panel.getWidth()-50)/100);

//        int twidth = panel.getWidth()-50;

        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDoubleBuffered(true);

        UpdateTableCellRenderer utcr = new UpdateTableCellRenderer(m_da);

        TableColumnModel cm = table.getColumnModel();
        for (int i=0; i<model.getColumnCount(); i++) 
        {
            cm.getColumn(i).setCellRenderer(utcr);
            cm.getColumn(i).setPreferredWidth(model.getColumnWidth(i, 580));
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 100, 580, 240);
        panel.add(scroll);
        scroll.repaint();

        scroll.updateUI();




        

        // ---
        // --- OK Command
        // ---
        button = new JButton("   " + ic.getMessage("CHECK_SERVER"));
        button.setBounds(455,30,150,25);
        button.setIcon(m_da.getImageIcon("/icons/", "up_down_question.png", 22, 22, this));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("check_server");
        panel.add(button);

        
        button = new JButton("   " + ic.getMessage("RUN_UPDATE"));
        button.setBounds(455,60,150,25);
        button.addActionListener(this);
        button.setIcon(m_da.getImageIcon("/icons/", "download.png", 22, 22, this));
        button.setEnabled(false);
        button.setFont(font_normal);
        button.setActionCommand("run_update");
        panel.add(button);
        
        
        // ---
        // --- Cancel Command
        // ---
        button = new JButton("   " + ic.getMessage("CLOSE"));
        button.setBounds(350,385,120,25);
        button.setIcon(m_da.getImageIcon("/icons/", "cancel.png", 22, 22, this));
        button.addActionListener(this);
        button.setFont(font_normal);
        button.setActionCommand("close");
        panel.add(button);
    
        // ---
        // --- Help command
        // ---
        
        this.help_button = m_da.createHelpButtonByBounds(485, 385, 120, 25, this);
        this.help_button.setFont(font_normal);
        panel.add(help_button);

        /*
        button = new JButton(ic.getMessage("HELP"));
        button.setBounds(325, 380, 80, 25);
        button.setFont(font_normal);
        //button.addActionListener(this);
        //button.setActionCommand("help");

//    	PISMainHelp.mainHelpBroker.enableHelpOnButton(button, "pages.PISMassAdd", null);
//    	PISMainHelp.mainHelpBroker.enableHelpKey(this.getRootPane(), "pages.PISMassAdd", null);
*/
  //      panel.add(button);
    
    }





    /**
     * Show Dialog
     */
    public void showDialog()
    {
        this.setVisible(true);
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
        }
        else if (action.equals("run_update"))
        {
            this.runUpdate();
        }
        else if (action.equals("check_server"))
        {
            this.checkServer();
        }
        else if (action.equals("close"))
        {
            lastAction = 0;
            this.m_da.removeComponent(this);
            this.dispose();
        }
        /*else if (action.equals("help"))
        {
            this.dispose();
        }*/
        else
            System.out.println("UpdateDialog::Unknown command: " + action);

    }


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


    /**
     * Help Id
     */
    private String help_id;

    
    /** 
     * enableHelp
     */
    public void enableHelp(String _help_id)
    {
        this.help_id = _help_id;
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
        return this.help_button;
    }


    /** 
     * getHelpId
     */
    public String getHelpId()
    {
        return this.help_id;
    }

    
    /**
     * Check Server
     */
    public void checkServer()
    {
        
        //JOptionPane.showMessageDialog(this, ic.getMessage("UPDATE_SERVER_NA"), ic.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
        
        try
        {
            // TODO
            URL url = new URL("http://localhost:8080/ATechUpdateInfo?application=ggc&version=0.3");



            
            //HTMLDocument tt = new HTMLDocument();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                                url.openStream()));

            String iLine , iLine2;
            iLine2 = null;

            while ((iLine = in.readLine()) != null)
            {
                iLine2 += iLine;
                System.out.println(iLine);
            } // while file
            
            in.close();
        
            
            // resolve result
            
            String error_nr = getParameter("error_nr", iLine2);
            String error_code = getParameter("error_code", iLine2);
            
            if (error_nr.equals("0"))
            {
                
                
            }
            else
            {
                this.status_label.setText(ic.getMessage(error_code));
            }
            
            /*
            out.println("  <error_nr>" + this.error_id + "</error_nr>");
            out.println("  <error_code>" + this.error_code + "</error_code>");
            
            if (this.error_id==0)
            {
                out.println("  <versions>");
                out.println("    <latest_version>" + latest_ver+ "</latest_version>\n");
                
                if (!latest_ver)
                {
                    
                    out.println("  <newer_version_our_db>" + (never_ver!=-1)+ "</newer_version_our_db>");
                    out.println("  <newer_version_our_db_number>" + never_ver+ "</newer_version_our_db_number>");
                    out.println("  <newer_version_higher_db>" + new_for_new_db+ "</newer_version_higher_db>");
                    out.println("  <newer_version_higher_db_number>" + this.getLatestVersion(app).version_num + "</newer_version_higher_db_number>\n");
                }
                out.println("  </versions>");
                
                
            }
            out.println("</server_report>");
            */
            
            
            
            
        }
        catch(Exception ex)
        {
            this.status_label.setText(ic.getMessage("UPD_ERROR_CONTACTING_SERVER"));
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    
    private String getParameter(String parameter, String text)
    {
        String start_tag = "<" + parameter + ">";
        
        if (!text.contains(start_tag))
            return "";
        
        String end_tag = "</" + parameter + ">";
        
        
        String par = text.substring(text.indexOf(start_tag) + start_tag.length(), text.indexOf(end_tag));        
        
        return par;
    }
    
    
    /**
     * Run Update
     */
    public void runUpdate()
    {
        
    }


}