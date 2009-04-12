
package com.atech.graphics.dialogs;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.help.CSH;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.graphics.components.DateComponent;
import com.atech.graphics.dialogs.selector.SelectableInterface;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccess;
import com.atech.utils.ATDataAccessAbstract;

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


public class ErrorDialog extends JDialog implements ActionListener //, ItemListener
{


    // CHANGE    this marking is set where you need to implement

    private static Log log = LogFactory.getLog(ActionExceptionCatchDialog.class);

	/**
     * 
     */
    private static final long serialVersionUID = 202192475384598913L;
    
    protected I18nControlAbstract ic; // = I18nControl.getInstance();
    protected ATDataAccessAbstract da;

    //ATDataAccess m_da = null;
    //PISDb m_db = null;


    public ArrayList<SelectableInterface> full_int; 

    //public ArrayList<SelectableInterface> full; 
    protected ArrayList<SelectableInterface> list; 


    public int date_selector_type = 0;

    //public int indexes 

    
    
    //
    // as interface -- start
    //
    public boolean is_opened = true;
    

    //
    // REMOVE
    
    public Hashtable<String,String> descriptions = new Hashtable<String,String>(); 
    
    
    
    
    
    
    boolean column_sorting_enabled = true;
    
    /**
     * Allowed actions (new, edit, select, cancel)
     */
    int allowed_actions = 0;

    

    
    /**
     * Help id
     */
    private String help_id = "";
    
    /**
     * Help Button
     */
    JButton help_button = null;
    
    
    /**
     * Exception string. This is list of ids in form like this (.1.2.20.) to exclude 
     * entries from list (in case we can add only one instance of some element).
     */
    //private String m_except = null;
    
    
    /**
     * Selected object
     */
    protected SelectableInterface selected_object;
    

    /**
     * Selected objects
     */
    //private ArrayList<SelectableInterface> selected_objects;
    
    
    protected JTable table;
    
    //
    // as interface -- end
    //

    /*
     *  Globaly used variables
     */
    //JTable table;
    JScrollPane scroll;    
    
    JPanel panel;
    JCheckBox checkBox1, checkBox2;
    JTextField textField1, textField2; //, textField3, textField4, textField5, textField6,
    //textField7, textField8, textField9;
    JComboBox comboBox1, comboBox2; //, comboBox3;
    JButton button1, button2, button3, button4;
    DateComponent dt_start, dt_end;

    Font font_normal, font_normal_bold;

    AbstractDocument doc; 

    String name;

    public int lastAction = 0;


    Exception exception;
    String error_message;
    String error_message_sollution;
    

// CHANGE


    public ErrorDialog(JFrame parent, ATDataAccessAbstract da, String application,
	           String module, String action, Exception exception, String error_message) 
    {
    	this(parent, da, application, module, action, exception, error_message, null);
    }

    
    public ErrorDialog(JDialog parent, ATDataAccessAbstract da, String application,
	           String module, String action, Exception exception, String error_message) 
    {
    	this(parent, da, application, module, action, exception, error_message, null);
    }
    
    
    public ErrorDialog(JFrame parent, ATDataAccessAbstract da, String application,
    		           String module, String action, Exception exception, String error_message,
    		           String extended_error_message) 
    {
        super(parent, "Selector", true);

        this.da = da; 
        this.ic = da.getI18nControlInstance();

        this.exception = exception;
        this.error_message = error_message;
        this.error_message_sollution = extended_error_message;
        
        init();
    	
    }
    

    
    
    public ErrorDialog(JDialog parent, ATDataAccessAbstract da, String application,
	           String module, String action, Exception exception, String error_message,
	           String extended_error_message) 
    {
        super(parent, "Selector", true);

        this.da = da; 
        this.ic = da.getI18nControlInstance();
        
        this.exception = exception;
        this.error_message = error_message;
        this.error_message_sollution = extended_error_message;
        
        init();
    	
    }
    

    

    public void init()
    {

    	name = ic.getMessage("ERROR_DIALOG");

    	font_normal = da.getFont(ATDataAccess.FONT_NORMAL);
        font_normal_bold = da.getFont(ATDataAccess.FONT_NORMAL_BOLD);
		
		
        this.setLayout(null);
        this.setTitle(name);
        this.setResizable(false);

        if (this.error_message_sollution==null)
            this.error_message_sollution = ic.getMessage("NO_SOLUTION_AVAILABLE");

        
        
        System.out.println("Error Dialog 0.1 ");

        //getInitialValues();
        cmdSelector();
    	

    }
    
    
    
    
    
    
    
    
    //---
    //--- Methods
    //---

    public Hashtable<String,String> getDescriptions()
    {
    	return this.descriptions;
    }
    
    
    public JButton getHelpButton()
    {
    	return this.help_button;
    }
    
    
    public String getHelpId()
    {
    	return help_id;
    }
    
    
    
    public void setSelectorName(String title)
    {
        this.name = title;
    }
    
    
    public void setHelpStringId(String id)
    {
    	help_id = id;
    	CSH.setHelpIDString(this, id);
    }
    
    
    
    
    public boolean isColumnSortingEnabled()
    {
    	return column_sorting_enabled;
    	
    }
    
    public void setColumnSortingEnabled(boolean value)
    {
    	column_sorting_enabled = value;	
    }
    
    
    public void setAllowedActions(int value)
    {
    	this.allowed_actions = value;
    }
    
    public int getAllowedActions()
    {
    	return this.allowed_actions;
    }
    
    public boolean isActionAllowed(int action)
    {
        if ((this.allowed_actions & action) == action)
        	return true;
        else
        	return false;
    }
    
    
    
    
    //---
    //--- Abstract methods
    //---
    
    
    


    
    
    

    /** 
     *
     */
    public void cmdSelector()
    {
    	name = ic.getMessage("ERROR_DIALOG");
    	
        Container dgPane = this.getContentPane();

        panel = new JPanel();
        panel.setBounds(0, 5, 520, 440);
        panel.setLayout(null);   

        dgPane.add(panel);

        JLabel label;
        label = new JLabel(name);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", 3, 22));
        label.setBounds(20, 8, 460, 36);
        panel.add(label);


//x        Font fnt = new Font("Times New Roman",Font.ITALIC|Font.BOLD,14);



        label = new JLabel(ic.getMessage("ERROR")+":");  
        label.setFont(this.font_normal_bold);
        label.setBounds(35,55,295,25);
        panel.add(label);
        
        label = new JLabel("<html><body>" + this.error_message + "</body></html>");  
        label.setFont(this.font_normal);
        label.setVerticalAlignment(JLabel.TOP);
        label.setBounds(35,80,295,50);
        panel.add(label);
        
        label = new JLabel(ic.getMessage("ERROR_SOLUTION")+":");  
        label.setFont(this.font_normal_bold);
        label.setBounds(35,120,295,25);
        panel.add(label);
        
        label = new JLabel("<html><body>" + this.error_message_sollution + "</body></html>");  
        label.setFont(this.font_normal);
        label.setVerticalAlignment(JLabel.TOP);
        label.setBounds(35,145,295,50);
        label.setFont(this.font_normal);
        panel.add(label);

        Color c = (Color)UIManager.get("activeCaption"); //, Color.ANY_COLOR_YOU_WANT);
        
        
        JPanel pan = new JPanel();
        pan.setBackground(c);
        pan.setBounds(0, 200, 520, 4);
        
        
        
        panel.add(pan);
        
        //createTable();

        
        //if (this.isActionAllowed(SelectorAbstractDialog.SELECTOR_ACTION_SELECT))
        {
	        button1 = new JButton(ic.getMessage("CLOSE"));
	        button1.setBounds(365, 60, 110, 25);
	        button1.setActionCommand("close");
	        button1.addActionListener(this);
	        button1.setFont(this.font_normal);
            panel.add(button1);
        }
        

        // ---
        // --- Help command
        // ---
        this.help_button = new JButton(ic.getMessage("HELP"));
        this.help_button.setBounds(365,120,110,25);
        this.help_button.setFont(font_normal);
        //button.addActionListener(this);
        //button.setActionCommand("help");
        panel.add(this.help_button);
        
        
        //if (this.isActionAllowed(SelectorAbstractDialog.SELECTOR_ACTION_CANCEL))
        {
        	button4 = new JButton(ic.getMessage("DETAILS") + " >>");
        	button4.setBounds(365, 150, 110, 25);
        	button4.setActionCommand("details");
        	button4.addActionListener(this);
        	button4.setFont(this.font_normal);
        	panel.add(button4);
        }

        
        
        label = new JLabel(ic.getMessage("EXCEPTION_LOG") + ":");  
        label.setVerticalAlignment(JLabel.TOP);
        label.setFont(this.font_normal_bold);
        label.setBounds(35,225,295,50);
        panel.add(label);
        
        

        button4 = new JButton(ic.getMessage("SEND_TO_SERVER"));
        button4.setBounds(305, 215, 170, 25);
        button4.setActionCommand("send_to_server");
        button4.addActionListener(this);
        button4.setFont(this.font_normal);
        panel.add(button4);
        
        
        
        /*
        // ---
        // --- Help command
        // ---
        this.help_button = new JButton(ic.getMessage("HELP"));
        this.help_button.setBounds(395,122,80,23);
        this.help_button.setFont(font_normal);
        //button.addActionListener(this);
        //button.setActionCommand("help");
        panel.add(this.help_button);
*/

        //initByFilterType();
        
        
        log.error("Error dialog opened: Ex: " + this.exception, this.exception);

        
        JList list = new JList(this.exception.getStackTrace());
        
        //list.setComponents(this.exception.getStackTrace());
        //list.setBounds(20, 240, 300, 160);
        //panel.add(list);
        
        JScrollPane scroll = new JScrollPane(list,
                                             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
                                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(30, 250, 445, 145);
        panel.add(scroll);
        
        
        this.setBounds(100, 80, 520, 440);
        this.repaint();

        //this.setBounds(100, 80, 520, 440);
        
        //this.setBounds(100, 80, 520, 240);
//        ATDataAccess.getInstance().centerJDialog(this, getInternalParent());
        //this.setBounds(100, 80, 520, 232);

        this.setResizable(false);
        this.setVisible(true);

    }

    
    



    /**
     *  Menu item listener, waits for user to issue command through menu.
     *
     */
    public void actionPerformed(ActionEvent e) 
    {
        String action = e.getActionCommand();
        //System.out.println("Action: " + action);
        
        if (action.equals("close"))
        {
        	this.dispose();
        }
        else if (action.equals("send_to_server"))
        {
            JOptionPane.showMessageDialog(this, 
                ic.getMessage("SEND_TO_SERVER_TEXT"), 
                ic.getMessage("SENDING_TO_SERVER"), 
                JOptionPane.ERROR_MESSAGE);
        }
        else if (action.equals("details"))
        {
        	if (is_opened)
        	{
                this.setBounds(100, 80, 520, 232);
                is_opened = !is_opened;
                this.repaint();
        	}
        	else
        	{
                this.setBounds(100, 80, 520, 440);
                is_opened = !is_opened;
                this.repaint();
        	}
        }
        else
            System.out.println("ErrorDialog:UnknownAction: " + action);
        	
        
        //checkAndExecuteAction(action);
    }




    public boolean wasAction()
    {
        return (this.selected_object!=null);
    }






    
    

}


