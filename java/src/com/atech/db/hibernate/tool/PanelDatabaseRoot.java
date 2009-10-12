package com.atech.db.hibernate.tool;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

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


public class PanelDatabaseRoot extends JPanel implements ActionListener
{

    private static final long serialVersionUID = -7332134335611323091L;
    I18nControlDbT ic = I18nControlDbT.getInstance();
    DbToolAccess m_da = null;

    DbToolApplicationInterface m_application = null;

    Font font_big, font_normal, font_normal_b;
    JLabel label;
    JButton button;

    //NutritionTreeDialog m_dialog = null;
    


    /**
     * Constructor
     * 
     * @param dia
     */
    public PanelDatabaseRoot(DbTool dia)
    {

        super();

        //m_dialog = dia;
        m_da = DbToolAccess.getInstance();

        font_big = m_da.getFont(DbToolAccess.FONT_BIG_BOLD);
        font_normal_b = m_da.getFont(DbToolAccess.FONT_NORMAL_BOLD);
        font_normal = m_da.getFont(DbToolAccess.FONT_NORMAL);

        createPanel();

    }



    private void createPanel()
    {

        this.setSize(500, 560);
        this.setLayout(null);

        Font fnt_18 = new Font("Times New Roman", Font.BOLD, 18);

        ATSwingUtils.initLibrary();
        
        ATSwingUtils.getTitleLabel(ic.getMessage("DB_TOOL"), 0, 35, 500, 40, this, ATSwingUtils.FONT_BIG_BOLD);
        
        
        /*
        label = new JLabel(ic.getMessage("")); //"Database Root");
	    //ic.getMessage("CONFIGURATION"));
        label.setBounds(0, 35, 500, 40);
        label.setFont(font_big); 
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label, null);
*/

        label = new JLabel(ic.getMessage("ADD_VIEW"));
        label.setBounds(40, 100, 100, 30);
        label.setFont(fnt_18); 
        this.add(label, null);

        label = new JLabel(ic.getMessage("ADD_VIEW_DESC"));
        label.setBounds(40, 130, 300, 60);
        label.setFont(font_normal); 
        this.add(label, null);

	JComboBox cb = new JComboBox(m_da.getAvailableDatabases());
	cb.setBounds(110, 190, 170, 25);
	cb.setFont(font_normal);
	this.add(cb, null);


/*
        button = new JButton(ic.getMessage("ADD_"));
        button.setBounds(110, 190, 170, 25);
        button.setFont(font_normal);
        button.addActionListener(this);
        button.setActionCommand("add_");
        this.add(button);
*/
        button = new JButton(ic.getMessage("ADD_DB"));
        button.setBounds(110, 220, 170, 25);
        button.setFont(font_normal);
        button.setActionCommand("add_db");
        button.addActionListener(this);
        this.add(button);

        label = new JLabel(ic.getMessage("EDIT_VIEW"));
        label.setBounds(40, 280, 300, 30);
        label.setFont(fnt_18); 
        this.add(label, null);

        label = new JLabel(ic.getMessage("EDIT_VIEW_DESC"));
        label.setBounds(40, 310, 300, 60);
        label.setFont(font_normal); 
        this.add(label, null);

	//System.out.println("Pabnel paint");

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
        //System.out.println("Redraw Not working");
    }



    /**
     *  Action Listener
     */
    public void actionPerformed(ActionEvent e) 
    {

        String action = e.getActionCommand();
        System.out.println("PanelDatabaseSet::Unknown command: " + action);
  
    }

}
    
    

