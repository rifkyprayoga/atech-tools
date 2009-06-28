package com.atech.db.hibernate.tool;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.utils.ATSwingUtils;


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


// WORK IN PROGRESS, PLEASE DO NOT TOUCH
// andyrozman


public class PanelDbAction extends JPanel implements ActionListener
{

    private static final long serialVersionUID = -7332134335611323091L;
    I18nControlDbT ic = I18nControlDbT.getInstance();
    DbToolAccess m_da = null;
    private static Log log = LogFactory.getLog(PanelDbAction.class);

    DbToolApplicationInterface m_application = null;

    Font font_big, font_normal, font_normal_b;
    JLabel label;
    JButton button;

    //NutritionTreeDialog m_dialog = null;
    
    PanelDatabaseSet pds;
    DbTool m_dialog;

    /**
     * Constructor
     * 
     * @param pds 
     * @param dia
     */
    public PanelDbAction(PanelDatabaseSet pds, DbTool dia)
    {

        super();

        this.pds = pds;
        
        m_dialog = dia;
        m_da = DbToolAccess.getInstance();

        //font_big = m_da.getFont(DbToolAccess.FONT_BIG_BOLD);
        //font_normal_b = m_da.getFont(DbToolAccess.FONT_NORMAL_BOLD);
        //font_normal = m_da.getFont(DbToolAccess.FONT_NORMAL);

        ATSwingUtils.initLibrary();
        
        createPanel();

    }



    private void createPanel()
    {

        this.setSize(700, 70);
        this.setLayout(null);
        this.setBorder(new TitledBorder("Database Tools"));

/*
        button = new JButton(ic.getMessage("ADD_"));
        button.setBounds(110, 190, 170, 25);
        button.setFont(font_normal);
        button.addActionListener(this);
        button.setActionCommand("add_");
        this.add(button);
*/
        
        ATSwingUtils.getButton(ic.getMessage("TEST_CONNECTION"), 20, 25, 140, 25, 
                               this, ATSwingUtils.FONT_NORMAL, null, "test_connection", this, m_da);
        
        ATSwingUtils.getButton(ic.getMessage("DB_STATUS"), 170, 25, 140, 25, 
            this, ATSwingUtils.FONT_NORMAL, null, "db_status", this, m_da);
        
        ATSwingUtils.getButton(ic.getMessage("INIT_DB"), 320, 25, 140, 25, 
            this, ATSwingUtils.FONT_NORMAL, null, "init_db", this, m_da);
        
/*        button = new JButton(ic.getMessage("TEST_CONNECTION"));
        button.setBounds(0, 10, 120, 25);
        button.setFont(font_normal);
        button.setActionCommand("test_connection");
        button.addActionListener(this);
        this.add(button);

        button = new JButton(ic.getMessage("STATUS"));
        button.setBounds(140, 10, 120, 25);
        button.setFont(font_normal);
        button.setActionCommand("add_p");
        button.addActionListener(this);
        this.add(button);

        button = new JButton(ic.getMessage("INIT_DB"));
        button.setBounds(280, 10, 120, 25);
        button.setFont(font_normal);
        button.setActionCommand("add_p");
        button.addActionListener(this);
        this.add(button);
*/
        return;
    }


    /**
     * Set Data
     * 
     * @param intr
     */
    public void setData(DbToolApplicationInterface intr)
    {
        m_application = intr;
        reDraw();
    }

    /**
     * Re Draw
     */
    public void reDraw()
    {
        System.out.println("Redraw Not working");
    }



    /**
     *  Action Listener
     */
    public void actionPerformed(ActionEvent e) 
    {

        String action = e.getActionCommand();
        
        if (action.equals("test_connection"))
        {
            testConnection();
        }
        else
            System.out.println("PanelDatabaseSet::Unknown command: " + action);
  
    }

    
    private void testConnection()
    {
        DatabaseSettings ds = this.pds.getDatabaseSettings();
        
        try
        {
            Class.forName(ds.driver_class);
            /*Connection con_ =*/ DriverManager.getConnection(this.pds.getJDBCUrl(), ds.username, ds.password); 
            System.out.println("Connection Tested !");
            
            JOptionPane.showMessageDialog(m_dialog, ic.getMessage("CONNECTION_SUCCESFULL"), ic.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE, null);
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(m_dialog, ic.getMessage("CONNECTION_PROBLEM"), ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE, null);
            System.out.println("Exception: " + ex);
            log.error("Exception: " + ex, ex);
        }
    }
    
    
    @SuppressWarnings("unused")
    private void showStatus()
    {
        // TODO
    }
    
    @SuppressWarnings("unused")
    private void initDb()
    {
        // TODO
    }
    
    
}