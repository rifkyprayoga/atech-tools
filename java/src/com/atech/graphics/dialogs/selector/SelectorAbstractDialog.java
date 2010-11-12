package com.atech.graphics.dialogs.selector;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

import javax.help.CSH;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.graphics.components.DateComponent;
import com.atech.graphics.layout.ZeroLayout;
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

// LEAVE OLD TEXTS INSIDE, THEY ARE THERE TO HELP WITH IMPLEMENTATION AND DEBUGING

public abstract class SelectorAbstractDialog extends JDialog implements ActionListener, DocumentListener, ItemListener, ChangeListener, ResortableColumns, HelpCapable
{

    private static final long serialVersionUID = 3826331169088096885L;

    private Log log = LogFactory.getLog(SelectorAbstractDialog.class);

    // CHANGE    this marking is set where you need to implement


	/**
     * The ic.
     */
    protected I18nControlAbstract ic; // = I18nControl.getInstance();

    //ATDataAccess m_da = null;
    //PISDb m_db = null;


    /**
     * The full_int.
     */
    //public ArrayList<SelectableInterface> full_int; 

    //public ArrayList<SelectableInterface> full; 
    /**
     * The list.
     */
    protected ArrayList<SelectableInterface> list; 
    
    /**
     * The m_da.
     */
    protected ATDataAccessAbstract m_da = null;

    /**
     * The par_selected.
     */
    public boolean par_selected = false;
    
    /**
     * The old_value.
     */
    public boolean old_value = false;


    /**
     * The sort column.
     */
    public int sortColumn = 0;
    
    /**
     * The ascending.
     */
    public boolean ascending = true;


    /**
     * The date_selector_type.
     */
    public int date_selector_type = 0;

    //public int indexes 

    private boolean help_enabled = true;
    
    /**
     * The use_generic_select.
     */
    protected boolean use_generic_select = false;    
    
    //
    // as interface -- start
    //
    

    /**
     * The descriptions.
     */
    public Hashtable<String,String> descriptions = new Hashtable<String,String>(); 
    
    
    /**
     * Full List (unfiltered)
     */
    public ArrayList<SelectableInterface> full; 
    
    
    /**
     * The Constant SELECTOR_ACTION_SELECT.
     */
    public static final int SELECTOR_ACTION_SELECT = 1;
    
    /**
     * The Constant SELECTOR_ACTION_NEW.
     */
    public static final int SELECTOR_ACTION_NEW = 2;
    
    /**
     * The Constant SELECTOR_ACTION_EDIT.
     */
    public static final int SELECTOR_ACTION_EDIT = 4;
    
    /**
     * The Constant SELECTOR_ACTION_CANCEL.
     */
    public static final int SELECTOR_ACTION_CANCEL = 8;
    
    /**
     * The Constant SELECTOR_ACTION_CANCEL_AND_SELECT.
     */
    public static final int SELECTOR_ACTION_CANCEL_AND_SELECT = 9;
    
    /**
     * The Constant SELECTOR_ACTION_ALL.
     */
    public static final int SELECTOR_ACTION_ALL = 15;
    
    
    
    /**
     * The column_sorting_enabled.
     */
    protected boolean column_sorting_enabled = true;
    
    /**
     * Allowed actions (new, edit, select, cancel)
     */
    int allowed_actions = 0;

    
    /**
     * The Constant SELECTOR_FILTER_TEXT.
     */
    public static final int SELECTOR_FILTER_TEXT 			= 1;
    
    /**
     * The Constant SELECTOR_FILTER_COMBO.
     */
    public static final int SELECTOR_FILTER_COMBO 			= 2;
    
    /**
     * The Constant SELECTOR_FILTER_DATE_FROM.
     */
    public static final int SELECTOR_FILTER_DATE_FROM 		= 3;
    
    /**
     * The Constant SELECTOR_FILTER_DATE_BOTH.
     */
    public static final int SELECTOR_FILTER_DATE_BOTH 		= 4;
    
    /**
     * The Constant SELECTOR_FILTER_DATE_BOTH_TEXT.
     */
    public static final int SELECTOR_FILTER_DATE_BOTH_TEXT 	= 5;
    
    
    /*
     * Type of filter (textbox, dates, combo, ...)
     */
    /**
     * The filter_type.
     */
    int filter_type = 0;
    
    
    /**
     * Parent stuff
     */
    private JFrame parent_frame = null;
    private JDialog parent_dialog = null;
    private int parent_type = 0;
    
    /**
     * The Constant PARENT_FRAME.
     */
    public static final int PARENT_FRAME = 1;
    
    /**
     * The Constant PARENT_DIALOG.
     */
    public static final int PARENT_DIALOG = 2;
    
    /**
     * Multiple selection 
     */
    public boolean multiple_selection_enabled = false;


    /**
     * String for new button
     */
    private String new_item_string = "";

    /**
     * Selector type
     */
    protected int selector_type;
    
    /**
     * Selector Type object 
     */
    private SelectableInterface selector_type_object;
    
    /**
     * Help id
     */
    private String help_id = "";
    
    /**
     * Help Button
     */
    JButton help_button = new JButton();
    
    
    /**
     * Exception string. This is list of ids in form like this (.1.2.20.) to exclude 
     * entries from list (in case we can add only one instance of some element).
     */
    protected String m_except = null;
    
    
    /**
     * Selected object
     */
    protected SelectableInterface selected_object;
    

    /**
     * Selected objects
     */
    private ArrayList<SelectableInterface> selected_objects;
    
    
    /**
     * The table.
     */
    protected JTable table;
    
    //
    // as interface -- end
    //

    /*
     *  Globaly used variables
     */
    JScrollPane scroll;    
    JPanel panel;
    JCheckBox checkBox1, checkBox2;
    JTextField textField1, textField2; 
    JComboBox comboBox1, comboBox2; //, comboBox3;
    JButton button1, button2, button3, button4;
    DateComponent dt_start, dt_end;
    Font font_normal = null;
    private ColumnSorter columnSorter = null;
    AbstractDocument doc; 
    String name;

    /**
     * The last action.
     */
    public int lastAction = 0;


    

// CHANGE



    /**
     * Instantiates a new selector abstract dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param type the type
     */
    public SelectorAbstractDialog(JFrame parent, ATDataAccessAbstract da, int type) 
    {
        this(parent, da, type, null, true);
    }

    
    /**
     * Instantiates a new selector abstract dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param type the type
     */
    public SelectorAbstractDialog(JDialog parent, ATDataAccessAbstract da, int type) 
    {
        this(parent, da, type, null, true);
    }
    
    
    /**
     * Instantiates a new selector abstract dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param type the type
     * @param with_init the with_init
     */
    public SelectorAbstractDialog(JFrame parent, ATDataAccessAbstract da, int type, boolean with_init) 
    {
        this(parent, da, type, null, with_init);
    }

    
    /**
     * Instantiates a new selector abstract dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param type the type
     * @param with_init the with_init
     */
    public SelectorAbstractDialog(JDialog parent, ATDataAccessAbstract da, int type, boolean with_init) 
    {
        this(parent, da, type, null, with_init);
    }
    

    /**
     * Instantiates a new selector abstract dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param type the type
     * @param except the except
     */
    public SelectorAbstractDialog(JFrame parent, ATDataAccessAbstract da, int type, String except) 
    {
        this(parent, da, type, except, true);
    }

    /**
     * Instantiates a new selector abstract dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param type the type
     * @param except the except
     */
    public SelectorAbstractDialog(JDialog parent, ATDataAccessAbstract da, int type, String except) 
    {
        this(parent, da, type, except, true);
    }

    
/*    
        public SelectorAbstractDialog(JFrame parent, I18nControlAbstract ic, int type) 
    {
        this(parent, ic, type, null, true);
    }

    
    public SelectorAbstractDialog(JDialog parent, I18nControlAbstract ic, int type) 
    {
        this(parent, ic, type, null, true);
    }
    
    
    public SelectorAbstractDialog(JFrame parent, I18nControlAbstract ic, int type, boolean with_init) 
    {
        this(parent, ic, type, null, with_init);
    }

    
    public SelectorAbstractDialog(JDialog parent, I18nControlAbstract ic, int type, boolean with_init) 
    {
        this(parent, ic, type, null, with_init);
    }
    

    public SelectorAbstractDialog(JFrame parent, I18nControlAbstract ic, int type, String except) 
    {
        this(parent, ic, type, except, true);
    }

    public SelectorAbstractDialog(JDialog parent, I18nControlAbstract ic, int type, String except) 
    {
        this(parent, ic, type, except, true);
    }

*/
    /**
     *   Constructor.
     *   Builds starting UI (all menus with pictures and all other frames)
     *   
     *   init must be called at later time
     *   
     * @param parent 
     * @param da 
     * @param type 
     * @param except 
     * @param with_init 
     *
     */
    public SelectorAbstractDialog(JDialog parent, ATDataAccessAbstract da, int type, String except, boolean with_init)
    {
        super(parent, "Selector", true);

        font_normal = new Font("SansSerif", Font.PLAIN, 12);
        this.m_except = except;

        this.m_da = da;
        this.ic = da.getI18nControlInstance();

        this.selector_type = type;
  
        this.parent_dialog = parent;
        this.parent_type = SelectorAbstractDialog.PARENT_DIALOG;

        /*
        if (m_da.getCurrentComponent()!=parent)
        {
            System.out.println("current!=parent");
            m_da.addComponent(parent);
        }
        else
        {
            System.out.println("current==parent");
        }*/
        
        m_da.addComponent(this);
        
        
        if (with_init)
            init();
        
        
    }
    
    
    
  /*  public SelectorAbstractDialog(JDialog parent, I18nControlAbstract ic, int type, String except, boolean with_init) 
    {
        super(parent, "Selector", true);

        font_normal = new Font("SansSerif", Font.PLAIN, 12);
        this.m_except = except;
        
        this.ic = ic;

        this.selector_type = type;
  
        this.parent_dialog = parent;
        this.parent_type = SelectorAbstractDialog.PARENT_DIALOG;
        m_da.addComponent(this);
        
        if (with_init)
            init();

        //this.setVisible(true);

    }
*/    

    
    /**
   * Instantiates a new selector abstract dialog.
   * 
   * @param parent the parent
   * @param da the da
   * @param type the type
   * @param except the except
   * @param with_init the with_init
   */
  public SelectorAbstractDialog(JFrame parent, ATDataAccessAbstract da, int type, String except, boolean with_init)
    {
        super(parent, "Selector", true);

        font_normal = new Font("SansSerif", Font.PLAIN, 12);
        this.m_except = except;

        this.m_da = da;
        this.ic = da.getI18nControlInstance();

        this.selector_type = type;
  
        this.parent_frame = parent;
        this.parent_type = SelectorAbstractDialog.PARENT_FRAME;
        m_da.addComponent(this);
        
        if (with_init)
            init();
    }
    
    
    /*
    public SelectorAbstractDialog(JFrame parent, I18nControlAbstract ic, int type, String except, boolean with_init) 
    {
        super(parent, "Selector", true);

        font_normal = new Font("SansSerif", Font.PLAIN, 12);
        this.m_except = except;
        
        this.ic = ic;

        this.selector_type = type;
  
        this.parent_frame = parent;
        this.parent_type = SelectorAbstractDialog.PARENT_DIALOG;
        
        if (with_init)
            init();

        //this.setVisible(true);

    }
    */
    

    /**
     * Inits the.
     */
    public void init()
    {

    	this.columnSorter = new ColumnSorter();
    	
    	if (this.ic.getCollationDefintion()!=null)
    	    this.columnSorter.setCollatorDefinition(ic);

		initSelectorValuesForType();

		/*
// CHANGE
        if (type==SELECTOR_POST)
        {
            select_typeObj = new Post();
            name = ic.getMessage("SELECTOR_POST");
            CSH.setHelpIDString(this, "selector.post");
            
            newString = ic.getMessage("NEW_F");
        }
        else if (type==SELECTOR_PERSON)
        {
            select_typeObj = new Person();
            name = ic.getMessage("SELECTOR_PERSON");
            CSH.setHelpIDString(this, "selector.person");

            newString = ic.getMessage("NEW_F");
        }
        else if ((type==SELECTOR_EVENT) ||
                 (type==SELECTOR_EVENT_BY_TYPE))
        {
            select_typeObj = new Events();
            name = ic.getMessage("SELECTOR_EVENT");
            CSH.setHelpIDString(this, "selector.event");

            newString = ic.getMessage("NEW__EVENT");
        }
        else if (type==SELECTOR_COUNTRY)
        {
            select_typeObj = new Country();
            name = ic.getMessage("SELECTOR_COUNTRY");
            CSH.setHelpIDString(this, "selector.country");

            newString = ic.getMessage("NEW__EVENT");
        }
        else if (type==SELECTOR_OCCUPATION)
        {
            select_typeObj = new Profession();
            name = ic.getMessage("SELECTOR_OCCUPATION");
            CSH.setHelpIDString(this, "selector.occupation");

            newString = ic.getMessage("NEW_M");
        }
        else if (type==SELECTOR_RELIGION)
        {
            select_typeObj = new Religion();
            name = ic.getMessage("SELECTOR_RELIGION");
            CSH.setHelpIDString(this, "selector.religion");

            newString = ic.getMessage("NEW_M");
        }
        else if (type==SELECTOR_STREET)
        {
            select_typeObj = new Street();
            name = ic.getMessage("SELECTOR_STREET");
            CSH.setHelpIDString(this, "selector.street");

            newString = ic.getMessage("NEW_F");
        }
        else if (type==SELECTOR_PARISH)
        {
            select_typeObj = new Parish();
            name = ic.getMessage("SELECTOR_PARISH");
            CSH.setHelpIDString(this, "selector.parish");

            newString = ic.getMessage("NEW_F");
        }
        else if (type==SELECTOR_EVENT_PERFORMER)
        {
            select_typeObj = new InternalPerson();
            name = ic.getMessage("SELECTOR_EVENT_PERFORMER");
            CSH.setHelpIDString(this, "selector.pperson");

            newString = ic.getMessage("NEW_F");
        }
        else if (type==SELECTOR_PERSON_MULTIPLE) 
        {
            select_typeObj = new Person();
            name = ic.getMessage("SELECTOR_PERSON_MULTIPLE");
            CSH.setHelpIDString(this, "selector.person_mult");

            newString = ic.getMessage("NEW_F");

            selected_ObjectS = new ArrayList<Selectable>();
        }
        else if(type ==SELECTOR_ADDRESS_SPECIAL)
        {
            select_typeObj = new PersonAddress(m_da);
            name = ic.getMessage("SELECTOR_ADDRESS_SPECIAL");
            CSH.setHelpIDString(this, "selector.address");
            newString = ic.getMessage("NEW__ADDRESS");
        } 
        else if (type ==SELECTOR_PARISH_PERSON)
        {
            select_typeObj = new ParishPerson();
            name = ic.getMessage("SELECTOR_PARISH_PERSON");
            CSH.setHelpIDString(this, "selector.pperson");
            //newString = ic.getMessage("NEW__PPEADDRESS");
        } 
        else if (type ==SELECTOR_INTERNAL_PERSON)
        {
            select_typeObj = new InternalPerson();
            name = ic.getMessage("SELECTOR_INTERNAL_PERSON");
            CSH.setHelpIDString(this, "selector.iperson");
            //newString = ic.getMessage("NEW__PPEADDRESS");
        } 
        else if (type ==SELECTOR_DIOCESE)
        {
            select_typeObj = new Diocese();
            name = ic.getMessage("SELECTOR_DIOCESE");
            CSH.setHelpIDString(this, "selector.diocese");
            //newString = ic.getMessage("NEW__PPEADDRESS");
        } 
	else if (type ==SELECTOR_NEWS)
	{
	    select_typeObj = new News(1);
	    name = ic.getMessage("SELECTOR_NEWS");
	    CSH.setHelpIDString(this, "selector.news");
	    newString = ic.getMessage("NEW__NEWS");
	} 
	else if (type ==SELECTOR_WEEKLY_NEWS)
	{
	    select_typeObj = new News(2);
	    name = ic.getMessage("SELECTOR_WEEKLY_NEWS");
	    CSH.setHelpIDString(this, "selector.weekly_news");
	    newString = ic.getMessage("NEW__WEEKLY_NEWS");
	} 
	else if (type ==SELECTOR_GROUP)
	{
	    select_typeObj = new Group();
	    name = ic.getMessage("SELECTOR_GROUP");
	    CSH.setHelpIDString(this, "selector.group");
	    newString = ic.getMessage("NEW__GROUP");
	} 
	else if (type ==SELECTOR_YOUTH_GROUP)
	{
	    select_typeObj = new YouthGroup();
	    name = ic.getMessage("SELECTOR_YOUTH_GROUP");
	    CSH.setHelpIDString(this, "selector.youth_group");
	    newString = ic.getMessage("NEW__YOUTH_GROUP");
	} 
*/



		if (this.isColumnSortingEnabled())
		{
		    this.columnSorter.setActive(true);
		}
		
/*
	if (
	    //(type != SELECTOR_POST) &&
	    //(type != SELECTOR_COUNTRY ) &&
	//(type != SELECTOR_PERSON ) &&
	//(type != SELECTOR_EVENT ) &&
	//(type != SELECTOR_OCCUPATION ) &&
	(type != SELECTOR_STREET ) &&
	(type != SELECTOR_RELIGION ) &&
	(type != SELECTOR_PARISH ) &&
	(type != SELECTOR_EVENT_PERFORMER ) &&
	(type != SELECTOR_PERSON_MULTIPLE ) &&
	(type != SELECTOR_ADDRESS_SPECIAL ) &&
	(type != SELECTOR_PARISH_PERSON ) &&
	(type != SELECTOR_INTERNAL_PERSON ) &&
	(type != SELECTOR_DIOCESE ) &&
	(type != SELECTOR_EVENT_BY_TYPE ) &&
	//(type != SELECTOR_NEWS ) &&
	//(type != SELECTOR_WEEKLY_NEWS ) &&
	(type != SELECTOR_GROUP ) &&
	(type != SELECTOR_YOUTH_GROUP))
	{
	    //SelectableSort ss = (SelectableSort)select_typeObj;
	    this.columnSorter.setActive(true);
	    //this.columnSorter.setColumnDefinitions(select_typeObj.getColumnDefinitions());
	}
*/

        getFullData();
        setSorterOnFullData();

        this.setLayout(new ZeroLayout(this.getSize()));
        this.setTitle(name);
        this.setResizable(false);

        log.debug("SelectorAbstract 0.2");

        getInitialValues();
        cmdSelector();
    	
    }

    
    public void refresh()
    {
        getFullData();
        setSorterOnFullData();
        getInitialValues();
        ((AbstractTableModel)this.table.getModel()).fireTableDataChanged();
    }
    
    
    
    /**
     * Init Selector Values For Type
     */
    public abstract void initSelectorValuesForType();
    
    
    
    
    //---
    //--- Methods
    //---

    /**
     * Show dialog.
     */
    public void showDialog()
    {
        this.setVisible(true);
    }
    
    
    /**
     * Gets the descriptions.
     * 
     * @return the descriptions
     */
    public Hashtable<String,String> getDescriptions()
    {
    	return this.descriptions;
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
    	return help_id;
    }
    
    
    /** 
     * getComponent
     */
    public Component getComponent()
    {
        return this;
    }
    
    
    /**
     * Sets the help enabled.
     * 
     * @param enabled the new help enabled
     */
    public void setHelpEnabled(boolean enabled)
    {
        this.help_enabled = enabled;
        
        if (enabled)
            m_da.enableHelp(this);
    }
    
    /**
     * Sets the selector object.
     * 
     * @param obj the new selector object
     */
    public void setSelectorObject(SelectableInterface obj)
    {
    	this.selector_type_object = obj;
    }
    
    
    /**
     * Sets the selector name.
     * 
     * @param title the new selector name
     */
    public void setSelectorName(String title)
    {
        this.name = title;
    }
    
    
    /**
     * Sets the help string id.
     * 
     * @param id the new help string id
     */
    public void setHelpStringId(String id)
    {
    	help_id = id;
    	CSH.setHelpIDString(this, id);
    }
    
    
    /**
     * Sets the new item string.
     * 
     * @param value the new new item string
     */
    public void setNewItemString(String value)
    {
    	this.new_item_string = value;
    }
    
    
    
    /**
     * Checks if is column sorting enabled.
     * 
     * @return true, if is column sorting enabled
     */
    public boolean isColumnSortingEnabled()
    {
    	return column_sorting_enabled;
    	
    }
    
    /**
     * Sets the column sorting enabled.
     * 
     * @param value the new column sorting enabled
     */
    public void setColumnSortingEnabled(boolean value)
    {
    	column_sorting_enabled = value;	
    }
    
    
    /**
     * Sets the allowed actions.
     * 
     * @param value the new allowed actions
     */
    public void setAllowedActions(int value)
    {
    	this.allowed_actions = value;
    }
    
    /**
     * Gets the allowed actions.
     * 
     * @return the allowed actions
     */
    public int getAllowedActions()
    {
    	return this.allowed_actions;
    }
    
    /**
     * Checks if is action allowed.
     * 
     * @param action the action
     * 
     * @return true, if is action allowed
     */
    public boolean isActionAllowed(int action)
    {
        if ((this.allowed_actions & action) == action)
        	return true;
        else
        	return false;
    }
    
    
    /**
     * Sets the filter type.
     * 
     * @param value the new filter type
     */
    public void setFilterType(int value)
    {
    	this.filter_type = value;
    }
    
    /**
     * Gets the filter type.
     * 
     * @return the filter type
     */
    public int getFilterType()
    {
    	return this.filter_type;
    }
    
    /**
     * Checks if is multiple selection enabled.
     * 
     * @return true, if is multiple selection enabled
     */
    public boolean isMultipleSelectionEnabled()
    {
    	return this.multiple_selection_enabled;
    	
    }
    
    
    /**
     * Sets the multiple selection enabled.
     * 
     * @param value the new multiple selection enabled
     */
    public void setMultipleSelectionEnabled(boolean value)
    {
    	this.multiple_selection_enabled = value;
    }
    
    
    /**
     * Gets the selector type.
     * 
     * @return the selector type
     */
    public int getSelectorType()
    {
    	return this.selector_type;
    }
    
    
    
    
    
    
    private void setSorterOnFullData()
    {
    	if (columnSorter.isActive())
    	{
    	    for(int i=0; i<full.size(); i++)
    	    {
    	    	full.get(i).setColumnSorter(this.columnSorter);
    	    }
    	}
    	
    }
    
    
    //---
    //--- Abstract methods
    //---
    
    /**
     * Init Selecttor Values For Type
     * @see com.atech.graphics.dialogs.selector.SelectorAbstractDialog#initSelectorValuesForType()
     */
    public abstract void getFullData();
    
    /*
    {

        long st = System.currentTimeMillis();
        
// CHANGE
        if (m_type==SELECTOR_POST)
        {
            full = m_da.m_db.getPosts();
        }
        else if (m_type==SELECTOR_EVENT)
        {
            full = m_da.m_db.getPublicEvents();
        }
        else if (m_type==SELECTOR_EVENT_BY_TYPE)
        {
            full = m_da.m_db.getEventsByType(Integer.parseInt(m_except));
        }
        else if ((m_type==SELECTOR_PERSON) ||
                 (m_type==SELECTOR_PERSON_MULTIPLE))
        {
            full = m_da.m_db.getAllPersonsExcept(Person.getSortParameters(sortColumn,ascending), m_except);
        }
        else if (m_type==SELECTOR_COUNTRY)
        {
            full = m_da.m_db.getCountries();
        }
        else if (m_type==SELECTOR_OCCUPATION)
        {
            full = m_da.m_db.getOccupations();
        }
        else if (m_type==SELECTOR_RELIGION)
        {
            full = m_da.m_db.getReligions();
        }
        else if (m_type==SELECTOR_STREET)
        {
            full = m_da.m_db.getStreets();
        }
        else if (m_type==SELECTOR_PARISH)
        {

            if (full_int==null) 
                full_int = m_da.m_db.getAllParishesExcept(m_except);

            if (!par_selected) 
                full = full_int;
            else
                full = m_da.m_db.getSelectedParishes();
        }
        else if (m_type==SELECTOR_EVENT_PERFORMER)
        {

            if (full_int==null) 
                full_int = m_da.m_db.getEventPerformers();

            if (!par_selected) 
                full = full_int;
            else
                full = m_da.m_db.getSelectedEventPerformers();



            //full = m_da.m_db.getEventPerformers();
        }
        else if(m_type == SELECTOR_ADDRESS_SPECIAL)
        {
            full = m_da.m_db.getSpecialAddresses(m_except);
        }
        else if(m_type == SELECTOR_PARISH_PERSON)
        {
            full = m_da.m_db.getParishPersonsExcept(m_except);
        }
        else if (m_type == SELECTOR_INTERNAL_PERSON)
        {
            full = m_da.m_db.getInternalPersonsExcept(m_except);
        }
        else if (m_type==SELECTOR_DIOCESE)
        {
            full = m_da.m_db.getAllDiocesesExcept(m_except);
        }
        else if (m_type==SELECTOR_NEWS)
        {
            full = m_da.m_db.getAllNews();
        }
        else if (m_type==SELECTOR_WEEKLY_NEWS)
        {
            full = m_da.m_db.getAllWeeklyNews();
        }
        else if (m_type==SELECTOR_GROUP)
        {
            full = m_da.m_db.getAllGroups();
        }
        else if (m_type==SELECTOR_GROUP)
        {
            full = m_da.m_db.getAllYouthGroups();
        }

	if (columnSorter.isActive())
	{
	    for(int i=0; i<full.size(); i++)
	    {
		full.get(i).setColumnSorter(this.columnSorter);
	    }
	}


        System.out.println("Full read: " + (System.currentTimeMillis() - st) + " ms ");
        
    }
*/


    /**
     * Gets the initial values.
     */
    public /*abstract*/ void getInitialValues()
    {

//    	System.out.println("Full: " + full);
    	
        Iterator<SelectableInterface> it = full.iterator();
        list = new ArrayList<SelectableInterface>();

        while (it.hasNext())
        {
        	SelectableInterface si = it.next();
        	
        	if (!this.isIdPresentInExclussion(si.getItemId()))
        		list.add(si);
        }

		if (columnSorter.isActive())
		{
		    Collections.sort(list);
		}

		System.out.println("List: " + list.size());
		
    }

    
    

    private boolean isIdPresentInExclussion(long id)
    {
    	if ((this.m_except==null) || (this.m_except.length()==0))
    	{
    		return false;
    	}
    	else
    	{
    		return (this.m_except.contains("." + id + "."));
    	}
    }
    
    

    /**
     *  Returns this instance, for use with sub-windows.
     */
//    public abstract JFrame getMyParent();
/*    {
        return m_da.getParent();
    }
*/

    
    
    

    /** 
     *
     */
    public void cmdSelector()
    {

        Container dgPane = this.getContentPane();

        panel = new JPanel();
        panel.setBounds(5, 5, 500, 400);
        //panel.setBounds(5, 5, 700, 400);
        panel.setLayout(new ZeroLayout(panel.getSize()));   

        dgPane.add(panel);

        JLabel label;
        label = new JLabel(name);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", 3, 22));
        label.setBounds(20, 9, 460, 36);
        panel.add(label);


        //Font fnt = new Font("Times New Roman",Font.ITALIC|Font.BOLD,14);


//X        String sm = "";

/*
// CHANGE
        if (this.m_type==SelectorDialog.SELECTOR_POST)
        {
            sm = ic.getMessage("POST_NAME");
        }
        else if (this.m_type==SelectorDialog.SELECTOR_EVENT)
        {
            sm = ic.getMessage("TYPE_OF_EVENT");
        }
        else if ((this.m_type==SelectorDialog.SELECTOR_PERSON) ||
                 (m_type==SELECTOR_PERSON_MULTIPLE))
        {
            sm = ic.getMessage("NAME_LNAME_PERSON");
        }
        else if (this.m_type==SelectorDialog.SELECTOR_COUNTRY)
        {
            sm = ic.getMessage("COUNTRY_NAME");
        }
        else if (this.m_type==SelectorDialog.SELECTOR_OCCUPATION)
        {
            sm = ic.getMessage("OCCUPATION_NAME");
        }
        else if (this.m_type==SelectorDialog.SELECTOR_RELIGION)
        {
            sm = ic.getMessage("RELIGION_NAME");
        }
        else if (this.m_type==SelectorDialog.SELECTOR_STREET)
        {
            sm = ic.getMessage("STREET_NAME");
        }
        else if (this.m_type==SelectorDialog.SELECTOR_PARISH)
        {
            sm = ic.getMessage("PARISH_NAME");
        }
        else if (this.m_type==SelectorDialog.SELECTOR_EVENT_PERFORMER)
        {
            sm = ic.getMessage("NAME_OF_PERFORMER");
        }
        else if(m_type == SELECTOR_ADDRESS_SPECIAL)
    {
        sm = ic.getMessage("ADDRESS");
    }
        else if (m_type == SELECTOR_PARISH_PERSON)
            sm = ic.getMessage("PARISH_PERSON");
        else if (m_type == SELECTOR_INTERNAL_PERSON)
            sm = ic.getMessage("INTERNAL_PERSON");
        else if (this.m_type==SelectorDialog.SELECTOR_DIOCESE)
        {
            sm = ic.getMessage("DIOCESE_NAME");
        }
	else if (this.m_type==SelectorDialog.SELECTOR_GROUP)
	{
	    sm = ic.getMessage("GROUP_NAME");
	}
        else
            sm = "";

        label = new JLabel(sm+":");  
        label.setFont(this.font_normal);
        label.setBounds(35,60,200,26);

        boolean tf_needed = false;
*/
// CHANGE
/*        if (this.m_type==SelectorDialog.SELECTOR_PERSON)
        {
            panel.add(label);

            textField1 = new JTextField();
            textField1.setBounds(110, 60, 160, 26);

            Document styledDoc = textField1.getDocument();

            if (styledDoc instanceof AbstractDocument)
            {
                doc = (AbstractDocument)styledDoc;
                //doc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
            }
            else
            {
                System.err.println("Text pane's document isn't an AbstractDocument!");
                System.exit(-1);
            } 


            doc.addDocumentListener(this);
            panel.add(textField1);

            tf_needed = true;

        }
        else*/ 
        
/*        
        if ((this.m_type==SelectorDialog.SELECTOR_COUNTRY) ||
                 (this.m_type==SelectorDialog.SELECTOR_OCCUPATION) ||
                 (this.m_type==SelectorDialog.SELECTOR_RELIGION) ||
                 (this.m_type==SelectorDialog.SELECTOR_STREET) ||
                 (this.m_type==SelectorDialog.SELECTOR_PERSON) ||
                 (this.m_type==SELECTOR_PERSON_MULTIPLE) ||
                 (this.m_type==SELECTOR_ADDRESS_SPECIAL) ||
                 (this.m_type==SELECTOR_DIOCESE) ||
                 (this.m_type==SELECTOR_PARISH_PERSON) ||
                 (this.m_type==SELECTOR_INTERNAL_PERSON) ||
		   (this.m_type==SELECTOR_GROUP) ||
         (this.m_type==SELECTOR_YOUTH_GROUP) ||   
                 (this.m_type==SELECTOR_POST))
        {

            panel.add(label);

            textField1 = new JTextField();
            textField1.setBounds(30, 85, 210, 26);
            
            //textField1.setBounds(110, 60, 160, 26);
    
            Document styledDoc = textField1.getDocument();
    
            if (styledDoc instanceof AbstractDocument)
            {
                doc = (AbstractDocument)styledDoc;
                //doc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
            }
            else
            {
                System.err.println("Text pane's document isn't an AbstractDocument!");
                System.exit(-1);
            } 
    
    
            doc.addDocumentListener(this);
            panel.add(textField1);

            tf_needed = true;
    
        }
        else if ((this.m_type==SelectorDialog.SELECTOR_PARISH) || 
                 (this.m_type==SelectorDialog.SELECTOR_EVENT_PERFORMER))
        {

            label = new JLabel(ic.getMessage("TYPE") + ":");  
            label.setFont(this.font_normal);
            label.setBounds(35,70,100,26);
            panel.add(label);

            Object[] s = { ic.getMessage("ALL__PARISH"), ic.getMessage("SELECTED__PARISH")  };
            comboBox1 = new JComboBox(s);
            comboBox1.setBounds(130, 70, 210, 26);
            comboBox1.addItemListener(this);
            panel.add(comboBox1, null);

            label = new JLabel(sm + ":");  
            label.setFont(this.font_normal);
            label.setBounds(35,90,100,26);
            panel.add(label);

            textField1 = new JTextField();
            textField1.setBounds(30, 115, 210, 26);

            //textField1.setBounds(110, 60, 160, 26);

            Document styledDoc = textField1.getDocument();

            if (styledDoc instanceof AbstractDocument)
            {
                doc = (AbstractDocument)styledDoc;
                //doc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
            }
            else
            {
                System.err.println("Text pane's document isn't an AbstractDocument!");
                System.exit(-1);
            } 


            doc.addDocumentListener(this);
            panel.add(textField1);

            tf_needed = true;

        }
        else if (this.m_type==SelectorDialog.SELECTOR_EVENT)
        {

            panel.add(label);

            comboBox1 = new JComboBox(m_da.getTypesArray(1));
            comboBox1.setBounds(30, 85, 210, 26);
            comboBox1.addItemListener(this);
            panel.add(comboBox1, null);

        }
	else if ((this.m_type==SelectorDialog.SELECTOR_NEWS) ||
		 (this.m_type==SelectorDialog.SELECTOR_WEEKLY_NEWS))   
	{
        //panel.add(label);

        //dt_start, dt_end;
// FIX
	    this.checkBox1 = new JCheckBox();
	    this.checkBox1.setText(" " + ic.getMessage("FROM") +":");
	    this.checkBox1.setBounds(25, 60, 80, 26);  // 85
	    this.checkBox1.addChangeListener(this);
	    panel.add(this.checkBox1, null);
    
	    dt_start = new DateComponent(m_da.m_i18n);
	    dt_start.setBounds(90, 60, 210, 26);
	    dt_start.setEnabled(false);
	    dt_start.addActionListener(this);
	    dt_start.setActionCommand("date_changed");
	    dt_start.setNote("start");
        
	    //comboBox1.addItemListener(this);
	    panel.add(dt_start, null);
    
	    this.checkBox2 = new JCheckBox();
	    this.checkBox2.setText(" " + ic.getMessage("TILL") +":");
	    this.checkBox2.setBounds(25, 85, 80, 26);  // 85
	    this.checkBox2.setSelected(false);
	    this.checkBox2.addChangeListener(this);
	    panel.add(this.checkBox2, null);
    
	    dt_end = new DateComponent(m_da.m_i18n);
	    dt_end.addActionListener(this);
	    dt_end.setBounds(90, 85, 210, 26);
	    dt_end.setEnabled(false);
	    dt_end.setActionCommand("date_changed");
	    dt_end.setNote("end");
	    //comboBox1.addItemListener(this);
	    panel.add(dt_end, null);
    
	    label = new JLabel(ic.getMessage("SUBJECT") + ":");
	    label.setBounds(25, 112, 80, 26);
	    panel.add(label, null);

	    if (this.m_type==SelectorDialog.SELECTOR_WEEKLY_NEWS)
	    {
		label.setText(ic.getMessage("SUB_TITLE") + ":");
	    }

    
	    textField1 = new JTextField();
	    textField1.setBounds(100, 112, 168, 24);
    
    
	    Document styledDoc = textField1.getDocument();
    
	    if (styledDoc instanceof AbstractDocument)
	    {
		doc = (AbstractDocument)styledDoc;
		//doc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
	    }
	    else
	    {
    		System.err.println("Text pane's document isn't an AbstractDocument!");
    		System.exit(-1);
	    } 
    
    
	    doc.addDocumentListener(this);
    
	    panel.add(textField1, null);



    
	}
*/

        createTable();

        
        if (this.isActionAllowed(SelectorAbstractDialog.SELECTOR_ACTION_SELECT))
        {
	        button1 = new JButton(ic.getMessage("SELECT"));
	        button1.setBounds(380, 60, 95, 25);
	        button1.setActionCommand("select");
	        button1.addActionListener(this);
	        button1.setFont(this.font_normal);
            panel.add(button1);
        }

        if (this.isActionAllowed(SelectorAbstractDialog.SELECTOR_ACTION_CANCEL))
        {
        	button4 = new JButton(ic.getMessage("CANCEL"));
        	button4.setBounds(380, 90, 95, 25);
        	button4.setActionCommand("cancel");
        	button4.addActionListener(this);
        	button4.setFont(this.font_normal);
        	panel.add(button4);
        }


/*
        if (tf_needed) 
        {
            textField1.setNextFocusableComponent(button1);
        }
        else
        {
            comboBox1.setNextFocusableComponent(button1);
        }
*/
// CHANGE
/*        if ((this.m_type!=SelectorDialog.SELECTOR_OCCUPATION) &&
            (this.m_type!=SelectorDialog.SELECTOR_COUNTRY) &&
            (this.m_type!=SelectorDialog.SELECTOR_RELIGION) &&
            (this.m_type!=SelectorDialog.SELECTOR_STREET) &&
            (this.m_type!=SelectorDialog.SELECTOR_PARISH) &&
            (this.m_type!=SelectorDialog.SELECTOR_EVENT_PERFORMER) &&
            (this.m_type!=SELECTOR_PERSON_MULTIPLE) &&
            (this.m_type!=SELECTOR_ADDRESS_SPECIAL) &&
            (this.m_type!=SELECTOR_PARISH_PERSON) &&
            (this.m_type!=SELECTOR_DIOCESE) &&
            (this.m_type!=SELECTOR_INTERNAL_PERSON) &&
  //          (this.m_type!=SelectorDialog.SELECTOR_EVENT_BY_TYPE) &&
            (this.m_type!=SelectorDialog.SELECTOR_POST))
          */
        
        if (this.isActionAllowed(SelectorAbstractDialog.SELECTOR_ACTION_NEW))
        {
            button2 = new JButton(this.new_item_string);
            button2.setBounds(280, 60, 95, 25);
            //button2.setBounds(panel.getWidth()-30, 95, 70, 25);
            button2.setActionCommand("new");
            button2.addActionListener(this);
            button2.setFont(this.font_normal);
            panel.add(button2);
        }
        
        
        if (this.isActionAllowed(SelectorAbstractDialog.SELECTOR_ACTION_EDIT))
        {
            button3 = new JButton(ic.getMessage("EDIT"));
            button3.setBounds(280, 90, 95, 25);
            button3.setActionCommand("edit");
            button3.addActionListener(this);
            button3.setFont(this.font_normal);
            panel.add(button3);
        }
        
        
        // ---
        // --- Help command
        // ---
        this.help_button.setText(ic.getMessage("HELP"));
        this.help_button.setBounds(395,122,80,23);
        this.help_button.setFont(font_normal);
        //button.addActionListener(this);
        //button.setActionCommand("help");
        
        if (this.help_enabled)
            panel.add(this.help_button);


        initByFilterType();
        
        
/*        
        else
        {
/*            if (tf_needed) 
            {
                textField1.setNextFocusableComponent(button1);
            }
            else
            {
                comboBox1.setNextFocusableComponent(button1);
            }
*/
  /*          //button1.sel.selectN
            

        } */

        this.setBounds(100, 80, 520, 440);
        
        //ATDataAccess.getInstance().centerJDialog(this, getInternalParent());
        
        //System.out.println("internalparent: " + getInternalParent());
        
        m_da.centerJDialog(this, this.getParent()); //getInternalParent());
        //m_da.centerJDialog(this); //, getInternalParent());

        this.setResizable(false);

    }

    /**
     * Gets the internal parent.
     * 
     * @return the internal parent
     */
    public Container getInternalParent()
    {
    	if (this.parent_type == SelectorAbstractDialog.PARENT_FRAME)
    		return this.parent_frame;
    	else
    		return this.parent_dialog;
    }
    
    
    
    /**
     * Inits the by filter type.
     */
    public void initByFilterType()
    {
    	// implement
    	if (this.filter_type == SelectorAbstractDialog.SELECTOR_FILTER_TEXT)
    	{
    		this.initFilterType_SingleText();
    	}
    	else if (this.filter_type == SelectorAbstractDialog.SELECTOR_FILTER_COMBO)
    	{
    		this.initFilterType_ComboBox();
    	}
        else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_BOTH_TEXT)
        {
        	log.warn("initByFilterType(): Date Both and Text Not Implemented FULLY !!!" );
        	initFilterType_DateBoth_Text();
        }
        else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_FROM)
        {
            log.warn("initByFilterType(): Date From Not Implemented !!!" );
        }
        else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_BOTH)
        {
            //log.warn("initByFilterType(): Date_Both Not Implemented !!!" );
            initFilterType_DateBoth();
        }
    	
    	
    }

    
    /**
     * Inits the filter type_ single text.
     */
    public /*abstract*/ void initFilterType_SingleText()
    {
    	
        JLabel label = new JLabel(this.descriptions.get("DESC_1")+":");  
        label.setFont(this.font_normal);
        label.setBounds(35,65,200,25);
        panel.add(label, null);

	    textField1 = new JTextField();
	    textField1.setBounds(35, 90, 168, 25);
    
	    Document styledDoc = textField1.getDocument();
    
	    if (styledDoc instanceof AbstractDocument)
	    {
	    	doc = (AbstractDocument)styledDoc;
	    }
	    else
	    {
    		System.err.println("Text pane's document isn't an AbstractDocument!");
    		System.exit(-1);
	    } 
    
	    doc.addDocumentListener(this);
    
	    panel.add(textField1, null);
    	
    }
    
    
    /**
     * Inits the filter type_ combo box.
     */
    public /*abstract*/ void initFilterType_ComboBox()
    {
    	
    }
    
    /**
     * Inits the filter type_ date both.
     */
    public void initFilterType_DateBoth()
    {
        this.checkBox1 = new JCheckBox();
        this.checkBox1.setText(" " + ic.getMessage("FROM") +":");
        this.checkBox1.setBounds(25, 60, 80, 26);  // 85
        this.checkBox1.addChangeListener(this);
        this.checkBox1.setName("from");
        panel.add(this.checkBox1, null);
    
        dt_start = new DateComponent(m_da.getI18nControlInstance());
        dt_start.setBounds(90, 60, 210, 26);
        dt_start.setEnabled(false);
        dt_start.addActionListener(this);
        dt_start.setActionCommand("date_changed");
        dt_start.setNote("start");
        
        //comboBox1.addItemListener(this);
        panel.add(dt_start, null);
    
        this.checkBox2 = new JCheckBox();
        this.checkBox2.setText(" " + ic.getMessage("TILL") +":");
        this.checkBox2.setBounds(25, 85, 80, 26);  // 85
        this.checkBox2.setSelected(false);
        this.checkBox2.addChangeListener(this);
        this.checkBox2.setName("till");
        panel.add(this.checkBox2, null);
    
        dt_end = new DateComponent(m_da.getI18nControlInstance());
        dt_end.addActionListener(this);
        dt_end.setBounds(90, 85, 210, 26);
        dt_end.setEnabled(false);
        dt_end.setActionCommand("date_changed");
        dt_end.setNote("end");
        //comboBox1.addItemListener(this);
        panel.add(dt_end, null);
        
    }

    
    /**
     * Inits the filter type_ date both_ text.
     */
    public void initFilterType_DateBoth_Text()
    {
        initFilterType_DateBoth();
        
        
        
        
        
    }
    
    
    

    /**
     * Creates the table.
     */
    public void createTable()
    {

        if (table!=null)
        {
            scroll.remove(table);
            panel.remove(scroll);

            table = null;
            scroll = null;
        }

        table = new JTable(new AbstractTableModel()
                           {

                            private static final long serialVersionUID = 3622576323533270687L;


                            //Object work[] = alist.toArray();



                            public int getColumnCount() 
                               {
                                   return selector_type_object.getColumnCount();
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
                                   SelectableInterface sel = list.get(rowIndex);
                                   return sel.getColumnValue(columnIndex+1);

                               }
                               
                           });  // table

        
        //AbstractTableModel atm = new AbstractTableModel();
        //atm.
        

        table.addMouseListener(new MouseListener()
        {

            /* 
             * mouseClicked
             */
            public void mouseClicked(MouseEvent me)
            {
                if (me.getClickCount()==2)
                {
                    checkAndExecuteActionSelect();
                }
            }

            public void mouseEntered(MouseEvent me) {}
            public void mouseExited(MouseEvent me) {}
            public void mousePressed(MouseEvent  me) {}
            public void mouseReleased(MouseEvent  me) {}
            
        });
        
        
        table.addMouseMotionListener(new MouseMotionAdapter()
        { 
            public void mouseMoved(MouseEvent e)
            { 
                Point p = e.getPoint(); 
                int row = table.rowAtPoint(p); 
                int column = table.columnAtPoint(p); 
                table.setToolTipText(String.valueOf(table.getModel().getValueAt(row,column))); 
            }//end MouseMoved 
        }); // end MouseMotionAdapter         
        
        
        
        //int procent = (int)((panel.getWidth()-50)/100);

        int twidth = panel.getWidth()-50;

        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        TableColumnModel cm = table.getColumnModel();
        int cwidth = 0;

        for (int i=0; i< selector_type_object.getColumnCount(); i++)
        {
        	//System.out.println("i = " + i + "  " + ic.getMessage(selector_type_object.getColumnName(i+1)));
            cm.getColumn(i).setHeaderValue(ic.getMessage(selector_type_object.getColumnName(i+1)));
            
            cwidth = selector_type_object.getColumnWidth(i+1, twidth);
            
            if (cwidth>0)
                cm.getColumn(i).setPreferredWidth(cwidth);        
        }


        table.setRowSelectionAllowed(true);

        /*
        if (m_type==SELECTOR_PERSON_MULTIPLE)
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        else
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        */    
          
        if (this.isMultipleSelectionEnabled())
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        else
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        	
        

        table.setDoubleBuffered(true);

        SortButtonRenderer renderer = new SortButtonRenderer(this);

        TableColumnModel model = table.getColumnModel();
        //int n = headerStr.length;
        for (int i=0;i<this.selector_type_object.getColumnCount();i++) 
        {
        	model.getColumn(i).setHeaderRenderer(renderer);
                //model.getColumn(i).setPreferredWidth(columnWidth[i]);
        }

        JTableHeader header = table.getTableHeader();
        header.addMouseListener(new TableHeaderListener(header,renderer));


        scroll = new JScrollPane(table);
        scroll.setBounds(25, 150, panel.getWidth()-50, panel.getHeight()-170);
        panel.add(scroll);
        scroll.repaint();

        scroll.updateUI();

    }



    /**
     *  Menu item listener, waits for user to issue command through menu.
     *
     */
    public void actionPerformed(ActionEvent e) 
    {
        String action = e.getActionCommand();
        //System.out.println("Action: " + action);
        checkAndExecuteAction(action);
    }
    /*
// CHANGE
        if (action.equals("select"))
        {

            selected_id = -1;
            selected_txt = "";

            if (table!=null)
            {

                //long row = table.getSelectedRow();

                if (table.getSelectedRow()==-1)
                    return;

                if (m_type==SELECTOR_POST)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Post pst = (Post)sl;

                    selected_id = pst.getId();
                    selected_txt = pst.getShortDescription(); 

                    selected_Object = pst;

                }
                else if ((m_type==SELECTOR_EVENT) ||
                         (m_type==SELECTOR_EVENT_BY_TYPE))
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Events ev = (Events)sl;

                    selected_id = ev.getId();
                    selected_txt = ev.getShortDescription(); 

                    selected_Object = ev;

                }
                else if (m_type==SELECTOR_PERSON)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Person prs = (Person)sl;

                    selected_id = prs.getId();
                    selected_txt = prs.getShortDescription(); 

                    selected_Object = prs;
                }
                else if (m_type==SELECTOR_COUNTRY)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Country prs = (Country)sl;

                    selected_id = -2;
                    selected_id_str = prs.getIso();
                    selected_txt = prs.getShortDescription(); 

                    selected_Object = prs;
                }
                else if (m_type==SELECTOR_OCCUPATION)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Profession prs = (Profession)sl;

                    selected_id = prs.getId();
                    selected_txt = prs.getName(); 

                    selected_Object = prs;
                }
                else if (m_type==SELECTOR_RELIGION)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Religion prs = (Religion)sl;

                    selected_id = prs.getId();
                    selected_txt = prs.getName(); 

                    selected_Object = prs;
                }
                else if (m_type==SELECTOR_STREET)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Street prs = (Street)sl;

                    selected_id = prs.getId();
                    selected_txt = prs.getName(); 

                    selected_Object = prs;
                }
                else if (m_type==SELECTOR_PARISH)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    Parish prs = (Parish)sl;

                    selected_id = prs.getId();
                    selected_txt = prs.getName(); 

                    selected_Object = prs;
                }
                else if (m_type==SELECTOR_EVENT_PERFORMER)
                {
                    Selectable sl = list.get(table.getSelectedRow());

                    InternalPerson prs = (InternalPerson)sl;

                    selected_id = -1;
                    //prs.
                    //selected_id = prs..getId();
                    //lected_txt = prs.g.m_linkName; 

                    selected_Object = prs;
                }
                else if (m_type==SELECTOR_PERSON_MULTIPLE)
                {

                    if (table.getSelectedRowCount()>0) 
                    {
                        this.selected_ObjectS.clear();

                        int[] rws = table.getSelectedRows();

                        for (int i=0; i<rws.length; i++) 
                        {
                            Selectable sl = list.get(rws[i]);

                            Person prs = (Person)sl;

                            //selected_id = prs.getId();
                            //selected_txt = prs.getShortDescription(); 

                            //selected_Object = prs;    
                            selected_ObjectS.add(prs);


                        }

                    }

                }
                else if(m_type == SELECTOR_ADDRESS_SPECIAL)
                {
                    Selectable sl = (Selectable)list.get(table.getSelectedRow());
                    PersonAddress prs = (PersonAddress)sl;
                    selected_id = -1L;
// YYY                    selected_txt = prs.getLink_address();
                    selected_Object = prs;
                }
                else if (m_type == SELECTOR_PARISH_PERSON)
                {
                    Selectable sl = (Selectable)list.get(table.getSelectedRow());
                    ParishPerson prs = (ParishPerson)sl;
                    selected_id = -1L;
                    selected_txt = prs.toString();
                    selected_Object = prs;
                }
                else if(m_type == SELECTOR_INTERNAL_PERSON)
                {
                    Selectable sl = (Selectable)list.get(table.getSelectedRow());
                    InternalPerson prs = (InternalPerson)sl;
                    selected_id = -1L;
                    selected_txt = prs.toString();
                    selected_Object = prs;
                }
                else if (m_type == SELECTOR_DIOCESE)
                {
                    Selectable sl = (Selectable)list.get(table.getSelectedRow());
                    Diocese prs = (Diocese)sl;
                    selected_id = prs.getId();
                    selected_txt = prs.toString();
                    selected_Object = prs;
                } 
		else if ((m_type == SELECTOR_NEWS) ||
			(m_type == SELECTOR_WEEKLY_NEWS))
		{
		    Selectable sl = (Selectable)list.get(table.getSelectedRow());
		    News nw = (News)sl;
		    selected_id = nw.getId();
		    selected_txt = nw.toString();
		    selected_Object = nw;
		}
		else if (m_type == SELECTOR_GROUP)
		{
		    Selectable sl = (Selectable)list.get(table.getSelectedRow());
		    Group nw = (Group)sl;
		    selected_id = nw.getId();
		    selected_txt = nw.toString();
		    selected_Object = nw;
		}
		else if (m_type == SELECTOR_YOUTH_GROUP)
		{
		    Selectable sl = (Selectable)list.get(table.getSelectedRow());
		    YouthGroup nw = (YouthGroup)sl;
		    selected_id = nw.getId();
		    selected_txt = nw.toString();
		    selected_Object = nw;
		} 

            }


            lastAction = 1;
            this.dispose();

        }
        else if (action.equals("cancel"))
        {

            selected_id = -1;
            selected_txt = "";
            lastAction = 0;
            this.dispose();

        }
	else if (action.equals("date_changed"))
	{
	    if (this.date_selector_type==0) 
	    {
		return;
	    }
	    else if (this.date_selector_type==3) 
	    {
    
		DateComponent dc = (DateComponent)e.getSource();
		String note = dc.getNote();
	
		if ((note.equals("start")) && 
		    (dt_start.getDate() > dt_end.getDate()))
		{
		    this.dt_end.setDate(this.dt_start.getDate());
		}
	
		if ((note.equals("end")) && 
		    (dt_start.getDate() > dt_end.getDate()))
		{
		    this.dt_start.setDate(this.dt_end.getDate());
		}
    
	    }
	    this.filterEntries();
	}
        else if (action.equals("new"))
        {
            boolean act = false;
// CHANGE
            if ((m_type==SELECTOR_EVENT) ||
                (m_type==SELECTOR_EVENT_BY_TYPE))
            {

                if (m_type==SELECTOR_EVENT_BY_TYPE)
                {
                    m_da.m_settings.put("EVENT_TYPE", m_except);
                }
                
                EventDialog ed = new EventDialog(m_da, -1); //, true, 1);
                act = ed.wasAction();

                m_da.m_settings.remove("EVENT_TYPE");
            }
        else if ((m_type == SELECTOR_NEWS) ||
            (m_type == SELECTOR_WEEKLY_NEWS))
        {

        int type = 0;
        if (m_type == SELECTOR_NEWS)
            type = NewsDialog.NEWS_DAILY_NEWS;
        else
            type = NewsDialog.NEWS_WEEKLY_NEWS;

        NewsDialog nd = new NewsDialog(m_da, type); 
        act = nd.wasAction();

        //Selectable sl = (Selectable)list.get(table.getSelectedRow());
        //News nw = (News)sl;
        //selected_id = nw.getId();
        //selected_txt = nw.toString();
        //selected_Object = nw;
        }
            else if (m_type==SELECTOR_PERSON)
            {
                PersonDialog ed = new PersonDialog(m_da, -1, 0);
                act = ed.wasAction();
            }
            else 
            {
                // NA
                return;
            }


            if (act)
            {
                getFullData();
                getInitialValues();

                createTable();

            }

        }
        else if (action.equals("edit"))
        {
            boolean act = false;

            long id = getCurrentSelectedID();

            System.out.println("Edit id: " + id);

            if (id<0)
                return;

// CHANGE
            if ((m_type==SELECTOR_EVENT) ||
                (m_type==SELECTOR_EVENT_BY_TYPE))
            {

                if (m_type==SELECTOR_EVENT_BY_TYPE)
                {
                    m_da.m_settings.put("EVENT_TYPE", m_except);
                }

                EventDialog ed = new EventDialog(m_da, id); //, true, 1);
                act = ed.wasAction();

                m_da.m_settings.remove("EVENT_TYPE");

            }
        else if ((m_type == SELECTOR_NEWS) ||
            (m_type == SELECTOR_WEEKLY_NEWS))
        {

        int type = 0;

        Selectable sl = (Selectable)list.get(table.getSelectedRow());
        News nw = (News)sl;

        NewsDialog nd = new NewsDialog(m_da, nw); 
        act = nd.wasAction();

        //Selectable sl = (Selectable)list.get(table.getSelectedRow());
        //News nw = (News)sl;
        //selected_id = nw.getId();
        //selected_txt = nw.toString();
        //selected_Object = nw;
        }
            else if (m_type==SELECTOR_PERSON)
            {
                PersonDialog ed = new PersonDialog(m_da, id, 0);
                act = ed.wasAction();
            }
            else 
            {
                // NA
                return;
            }


            if (act)
            {
                getFullData();
                getInitialValues();

                createTable();
            }

        }
        else  // new, edit
            System.out.println("Unknown command: " + action);

// YYYY

    }
*/

    
    public void closeDialog()
    {
        this.m_da.removeComponent(this);
        this.dispose();
    }
    
    
    // Document Event

    /**
     * Gives notification that there was an insert into the document.  The
     * range given by the DocumentEvent bounds the freshly inserted region.
     *
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e)
    {
        filterEntries();
    }

    /**
     * Gives notification that a portion of the document has been
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * @param e the document event
     */
    public void removeUpdate(DocumentEvent e)
    {
        filterEntries();
    }

    /** 
     * changedUpdate
     */
    public void changedUpdate(DocumentEvent e) {}


    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    public abstract void itemStateChanged(ItemEvent e);
    /*
    {
// CHANGE
        if ((m_type==this.SELECTOR_PARISH) ||
            (m_type==this.SELECTOR_EVENT_PERFORMER) )
        {

            old_value = par_selected;

            int x = comboBox1.getSelectedIndex();

            if (((!old_value) && (x==0)) ||
                 (old_value) && (x==1))
            {
                // nothing
            }
            else
            {

                if (x==0) 
                    par_selected = false;
                else
                    par_selected = true;
                
                getFullData();
                getInitialValues();

                createTable();
            }


        }
        else
            filterEntries();
    }
*/


    
    	/**
     * Filter entries.
     */
    public void filterEntries()
        {
            String flt_entry = "";
            int flt_idx= 0;
            int flt_from=0, flt_to=0;
            
            if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_TEXT)
            {
                flt_entry = textField1.getText().toUpperCase();
            }
            else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_COMBO)
            {
                flt_idx = getComboIndexResolved();
            }
            else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_BOTH_TEXT)
            {
	    	    flt_entry = textField1.getText().toUpperCase();
	    	    flt_from = this.dt_start.getDate();
	    	    flt_to = this.dt_end.getDate();
            }
            else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_FROM)
            {
	    	    flt_from = this.dt_start.getDate();
            }
            else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_BOTH)
            {
	    	    flt_from = this.dt_start.getDate();
	    	    flt_to = this.dt_end.getDate();
            }

            Iterator<SelectableInterface> it = full.iterator();

            list.clear();

            while (it.hasNext())
            {

                SelectableInterface p = it.next();
                
            	if (this.isIdPresentInExclussion(p.getItemId()))
            		continue;

            
                //if (this.  p.getItemId())

                
                if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_TEXT)
                {
                    if (p.isFound(flt_entry))
                        list.add(p);
                }
                else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_COMBO)
                {
                    if (p.isFound(flt_idx))
                        list.add(p);
                }
                else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_BOTH_TEXT)
                {
                    if ((p.isFound(flt_entry)) && (p.isFound(flt_from, flt_to, this.date_selector_type)))
                        list.add(p);
                }
                else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_FROM)
                {
                    if ((p.isFound(flt_from, -1, this.date_selector_type)))
                        list.add(p);
                }
                else if (this.getFilterType()==SelectorAbstractDialog.SELECTOR_FILTER_DATE_BOTH)
                {
                    if ((p.isFound(flt_from, flt_to, this.date_selector_type)))
                        list.add(p);
                }
                
            }

            
            System.out.println("Filter: " + list.size());
            
            createTable();

        }
    
        
        /**
         * In case that we don't use ComboBox index as index of collection we resolve 
         * it here.
         * @return 
         */
        public int getComboIndexResolved()
        {
        	return comboBox1.getSelectedIndex();
        }
    
    

// CHANGE
//    public abstract void filterEntries();
/*    {
        long flt = System.currentTimeMillis();
        
        String flt_entry = "";
        int flt_idx= 0;
    int flt_from=0, flt_to=0;

//  System.out.println("Text: " + textField1.getText());
        if ((m_type==SelectorDialog.SELECTOR_POST) ||
            (m_type==SelectorDialog.SELECTOR_COUNTRY) ||
            (m_type==SelectorDialog.SELECTOR_OCCUPATION) ||
            (m_type==SelectorDialog.SELECTOR_RELIGION) ||
            (m_type==SelectorDialog.SELECTOR_STREET) ||
            (m_type==SelectorDialog.SELECTOR_PARISH) ||
            (m_type==SelectorDialog.SELECTOR_EVENT_PERFORMER) ||
            (m_type==SelectorDialog.SELECTOR_PERSON_MULTIPLE) ||
            (m_type==SelectorDialog.SELECTOR_ADDRESS_SPECIAL) ||
            (m_type==SelectorDialog.SELECTOR_PARISH_PERSON) ||
            (m_type==SelectorDialog.SELECTOR_DIOCESE) ||
            (m_type==SelectorDialog.SELECTOR_INTERNAL_PERSON) ||
	    (m_type==SelectorDialog.SELECTOR_YOUTH_GROUP) ||
	    (m_type==SelectorDialog.SELECTOR_GROUP) ||
            (m_type==SelectorDialog.SELECTOR_PERSON))
        {
            flt_entry = textField1.getText().toUpperCase();
        }
        else if (m_type==SelectorDialog.SELECTOR_EVENT)
        {
            flt_idx = m_da.getSelectedType((String)comboBox1.getSelectedItem());
        }
	else if ((m_type==SelectorDialog.SELECTOR_NEWS) ||
         (m_type==SelectorDialog.SELECTOR_WEEKLY_NEWS))
	{
	    flt_entry = textField1.getText().toUpperCase();
	    flt_from = this.dt_start.getDate();
	    flt_to = this.dt_end.getDate();
	}


        Iterator<Selectable> it = full.iterator();

        list.clear();

        while (it.hasNext())
        {

            Selectable p = it.next();

            if ((m_type==SelectorDialog.SELECTOR_POST) ||
                (m_type==SelectorDialog.SELECTOR_COUNTRY) ||
                (m_type==SelectorDialog.SELECTOR_OCCUPATION) ||
                (m_type==SelectorDialog.SELECTOR_STREET) ||
                (m_type==SelectorDialog.SELECTOR_RELIGION) ||
                (m_type==SelectorDialog.SELECTOR_PARISH) ||
                (m_type==SelectorDialog.SELECTOR_EVENT_PERFORMER) ||
                (m_type==SelectorDialog.SELECTOR_PERSON_MULTIPLE) ||
                (m_type==SelectorDialog.SELECTOR_ADDRESS_SPECIAL) ||
                (m_type==SelectorDialog.SELECTOR_PARISH_PERSON) ||
                (m_type==SelectorDialog.SELECTOR_DIOCESE) ||
                (m_type==SelectorDialog.SELECTOR_INTERNAL_PERSON) ||
        (m_type==SelectorDialog.SELECTOR_YOUTH_GROUP) ||
        (m_type==SelectorDialog.SELECTOR_GROUP) ||
                (m_type==SelectorDialog.SELECTOR_PERSON))
            {
                if (p.isFound(flt_entry))
                    list.add(p);

            }
            else if (m_type==this.SELECTOR_EVENT)
            {

                if (p.isFound(flt_idx))
                    list.add(p);

            }
        else if ((m_type==SelectorDialog.SELECTOR_NEWS) ||
             (m_type==SelectorDialog.SELECTOR_WEEKLY_NEWS))
        {
        if ((p.isFound(flt_entry)) && (p.isFound(flt_from, flt_to, this.date_selector_type)))
            list.add(p);
        }


//      if ((p.m_post_name_idx.indexOf(flt_entry)!=-1) || (flt_entry.length()==0))
//      list.add(p);

        }

        System.out.println("Flt: " + (System.currentTimeMillis() - flt) + " ms ");
        
        createTable();

    }
*/

    /**
 * Gets the selected id.
 * 
 * @return the selected id
 */
public long getSelectedID()
    {
        return this.selected_object.getItemId();
    }


    /**
     * Gets the selected id string.
     * 
     * @return the selected id string
     */
    public String getSelectedIDString()
    {
        return "" + this.selected_object.getItemId();
    }


    /**
     * Gets the selected value.
     * 
     * @return the selected value
     */
    public String getSelectedValue()
    {
        return this.selected_object.getShortDescription();
    }


    /**
     * Gets the selected object.
     * 
     * @return the selected object
     */
    public SelectableInterface getSelectedObject()
    {
        return this.selected_object;
    }


    /**
     * Gets the selected objects.
     * 
     * @return the selected objects
     */
    public ArrayList<SelectableInterface> getSelectedObjects()
    {
        return this.selected_objects;
    }



    /**
     * Was action.
     * 
     * @return true, if successful
     */
    public boolean wasAction()
    {
        return (this.selected_object!=null);
    }


    /**
     * Gets the current selected id.
     * 
     * @return the current selected id
     */
    public long getCurrentSelectedID()
    {

        long i = table.getSelectedRow();

        if (i==-1)
        {
            JOptionPane.showMessageDialog(this, ic.getMessage("SELECT_ROW_FIRST"), ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        SelectableInterface sl = list.get(table.getSelectedRow());
        //System.out.println("Selected: " + table.getSelectedRow()+ " type: " + this.selector_type);
        
        return sl.getItemId();

        
/*
// CHANGE
        if (m_type==SelectorDialog.SELECTOR_POST)
        {
            Post p = (Post)sl;
            return p.getId();
        }
        else if ((m_type==SelectorDialog.SELECTOR_EVENT) ||
                 (m_type==SelectorDialog.SELECTOR_EVENT_BY_TYPE))
        {
            Events ev = (Events)sl;
            //System.out.println("Ev ID: "+ ev.getId());
            return ev.getId();
        }
        else if (m_type==SelectorDialog.SELECTOR_PERSON)
        {
            Person p = (Person)sl;
            return p.getId();
        }
        else if (m_type==SelectorDialog.SELECTOR_COUNTRY)
        {
            return -1;
        }
        else if (m_type==SelectorDialog.SELECTOR_OCCUPATION)
        {
            Profession p = (Profession)sl;
            return p.getId();
        }
        else if (m_type==SelectorDialog.SELECTOR_RELIGION)
        {
            Religion p = (Religion)sl;
            return p.getId();
        }
        else if (m_type==SelectorDialog.SELECTOR_STREET)
        {
            Street p = (Street)sl;
            return p.getId();
        }
        else if (m_type==SelectorDialog.SELECTOR_PARISH)
        {
            Parish p = (Parish)sl;
            return p.getId();
        }
        else if (m_type==SelectorDialog.SELECTOR_PERSON_MULTIPLE)
        {
            return -1;
        }
        else if (m_type==SelectorDialog.SELECTOR_PARISH_PERSON)
        {
            ParishPerson p = (ParishPerson)sl;
            return p.getId();
        }
        else if ((m_type==SelectorDialog.SELECTOR_INTERNAL_PERSON) || 
                 (m_type==SelectorDialog.SELECTOR_EVENT_PERFORMER))
        {
            InternalPerson p = (InternalPerson)sl;
            return p.getId();
        }
        else if (m_type==SelectorDialog.SELECTOR_DIOCESE)
        {
            Diocese p = (Diocese)sl;
            return p.getId();
        }
    else if ((m_type==SelectorDialog.SELECTOR_NEWS) ||
         (m_type==SelectorDialog.SELECTOR_WEEKLY_NEWS))
    {
        News n = (News)sl;
        return n.getId();
    }
    else if (m_type==SelectorDialog.SELECTOR_GROUP)
    {
        Group g = (Group)sl;
        return g.getId();
    }
    else if (m_type==SelectorDialog.SELECTOR_YOUTH_GROUP)
    {
        YouthGroup yg = (YouthGroup)sl;
        return yg.getId();
    }
        else
        {
            System.out.println("getCurrentSelectedID:Undefined type");
            return -1;
        }
*/
    }


    /** 
     * resortColumns
     */
    public synchronized void resortColumns(int column, boolean asc)
    {
    	column++;

    	if (!this.columnSorter.isActive())
    		return;

    	if ((this.columnSorter.sortedColumn!=column) || (this.columnSorter.sortedAscending!=asc))
    	{
    		//System.out.println("Resort: column: " + column + " ascending: " + asc);

    		this.columnSorter.sortedColumn = column;
    		this.columnSorter.sortedAscending = asc;

    		Collections.sort(list);
    	}


	/*
        if ((this.sortColumn!=column) || (this.ascending!=asc))
        {
            System.out.println("Resort: column: " + column + " ascending: " + asc);

            this.sortColumn=column;
            this.ascending=asc;

            getFullData();
            getInitialValues();

            //createTable();
        }*/
    }


    /** 
     * stateChanged
     */
    public void stateChanged(javax.swing.event.ChangeEvent ce)
    {
        
        int type = 0;
    
        if (checkBox1.isSelected())
        {
            this.dt_start.setEnabled(true);
            type+=1;
        }
        else
        {
            this.dt_start.setEnabled(false);
        }
    
        if (checkBox2.isSelected())
        {
            this.dt_end.setEnabled(true);
            type+=2;
        }
        else
        {
            this.dt_end.setEnabled(false);
        }
    
        date_selector_type = type;
    
        this.filterEntries();
        //System.out.println("Type: " + type);

    }

    /**
     * This needs to be implemented by end user.
     * @param action 
     */
    public void checkAndExecuteAction(String action)
    {
    	if (action.equals("new"))
    	{
    		checkAndExecuteActionNew();
    	}
    	else if (action.equals("edit"))
    	{
    	    if (this.table.getSelectedRowCount()==0)
    	    {
                JOptionPane.showConfirmDialog(this, m_da.getI18nControlInstance().getMessage("SELECT_ROW_FIRST"), m_da.getI18nControlInstance().getMessage("ERROR"), JOptionPane.CLOSED_OPTION);
                return;
    	    }
    	    else
    	    {
                int index = this.table.getSelectedRow();
                SelectableInterface si = this.list.get(index);
    	        checkAndExecuteActionEdit(si);
    	    }
    	}
    	else if (action.equals("select"))
    	{
    	    if (use_generic_select)
    	    {
                if (this.table.getSelectedRowCount()==0)
                {
                    JOptionPane.showConfirmDialog(this, m_da.getI18nControlInstance().getMessage("SELECT_ROW_FIRST"), m_da.getI18nControlInstance().getMessage("ERROR"), JOptionPane.CLOSED_OPTION);
                    return;
                }
                else
                {
                    if (this.isMultipleSelectionEnabled())
                    {
                        System.out.println("Multiple Generic Selector not implemented YET !!!");
                    }
                    else
                    {
                        int index = this.table.getSelectedRow();
                        SelectableInterface si = this.list.get(index);
                        this.selected_object = si;
                        this.closeDialog();
                    }
                    //checkAndExecuteActionEdit(si);
                }
    	        
    	    }
    	    else
    	        checkAndExecuteActionSelect();
    	}
        else if (action.equals("cancel"))
        {
        	checkAndExecuteActionCancel();
        }
    	
    }
    
    
    
    
    
    
    
    
    
    /**
     * Check and Execute Action: New
     */
    public abstract void checkAndExecuteActionNew();
    
    /**
     * Check and Execute Action: Edit
     * 
     * @param si 
     */
    public abstract void checkAndExecuteActionEdit(SelectableInterface si);
    
    /**
     * Check and Execute Action: Select
     */
    public abstract void checkAndExecuteActionSelect();
  
    /**
     * Check and Execute Action: Cancel
     */
    public void checkAndExecuteActionCancel()
    {
        //selected_id = -1;
        //selected_txt = "";
        lastAction = 0;
        this.closeDialog();
    }
    
    
    

}


