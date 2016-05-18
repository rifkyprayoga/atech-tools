package com.atech.graphics.dialogs.guilist;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import com.atech.i18n.I18nControlAbstract;

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

public abstract class GUIListDefAbstract
{

    /** The Constant ACTION_ADD. */
    public static final int ACTION_ADD = 1;

    /** The Constant ACTION_EDIT. */
    public static final int ACTION_EDIT = 2;

    /** The Constant ACTION_DELETE. */
    public static final int ACTION_DELETE = 3;

    /** The i18nControl. */
    protected I18nControlAbstract ic = null;

    /** The translation_root. */
    protected String translation_root = null;

    /** The table. */
    protected JTable table;

    /** The model. */
    protected AbstractTableModel model;
    // protected ArrayList full_list;
    // protected ArrayList active_list;

    /** The filter_options_combo1. */
    protected String[] filter_options_combo1 = null;

    /** The filter_options_combo2. */
    protected String[] filter_options_combo2 = null;

    // protected boolean filter_enabled = false;
    /** The filter_texts. */
    protected String[] filter_texts = null;

    /** The filter_type. */
    protected int filter_type = FILTER_NONE;

    /** The button_defs. */
    protected ArrayList<ButtonDef> button_defs;

    /** 
     * Filter: Filter none
     */
    public static final int FILTER_NONE = 0;

    /** 
     * Filter: Combo
     */
    public static final int FILTER_COMBO = 1;

    /** 
     * Filter: Combo and text
     */
    public static final int FILTER_COMBO_AND_TEXT = 2;

    /** 
     * Filter: Combo twice
     */
    public static final int FILTER_COMBO_TWICE = 3;

    /** The parent_dialog. */
    public GUIListDialog parentDialog;

    /** The defaultParameters. */
    protected String[] defaultParameters;

    private boolean customDisplayHeader = false;


    // i18nControl, translation root
    /**
     * Inits the.
     */
    public abstract void init();


    /**
     * Gets the title.
     * 
     * @return the title
     */
    public abstract String getTitle();


    /**
     * Gets the message.
     * 
     * @param keyword
     *            the keyword
     * @return the message
     */
    public String getMessage(String keyword)
    {
        return ic.getMessage(this.translation_root + "_" + keyword);
    }


    /**
     * Gets the translation root.
     * 
     * @return the translation root
     */
    public String getTranslationRoot()
    {
        return this.translation_root;
    }


    /**
     * Gets the table.
     * 
     * @return the j table
     */
    public abstract JTable getJTable();


    /**
     * Do table action.
     * 
     * @param action
     *            the action
     */
    public abstract void doTableAction(String action);


    /**
     * Gets the def name.
     * 
     * @return the def name
     */
    public abstract String getDefName();


    /**
     * Gets the window size.
     * 
     * @return the window size
     */
    public abstract Dimension getWindowSize();


    /**
     * Gets the table size.
     * 
     * @param pos_y
     *            the pos_y
     * @return the table size
     */
    public abstract Rectangle getTableSize(int pos_y);


    /**
     * In this method you can add additional custom elements for this lister.
     * 
     * @param main_panel
     *            the main_panel
     */
    public void additionalGUIInit(JPanel main_panel)
    {
    }


    /**
     * Gets the filter options combo1.
     * 
     * @return the filter options combo1
     */
    public String[] getFilterOptionsCombo1()
    {
        return this.filter_options_combo1;
    }


    /**
     * Gets the filter options combo2.
     * 
     * @return the filter options combo2
     */
    public String[] getFilterOptionsCombo2()
    {
        return this.filter_options_combo2;
    }


    /**
     * Checks for filter.
     * 
     * @return true, if successful
     */
    public boolean hasFilter()
    {
        return this.filter_type > FILTER_NONE;
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
     * Sets the parent instance.
     * 
     * @param parentDialog
     *            the new parent instance
     */
    public void setParentDialog(GUIListDialog parentDialog)
    {
        this.parentDialog = parentDialog;
    }


    public GUIListDialog getParentDialog()
    {
        return this.parentDialog;
    }


    /**
     * Gets the filter texts.
     * 
     * @return the filter texts
     */
    public String[] getFilterTexts()
    {
        return this.filter_texts;
    }


    /**
     * Sets the filter combo.
     * 
     * @param val
     *            the new filter combo
     */
    public abstract void setFilterCombo(String val);


    /**
     * Sets the filter combo_2.
     * 
     * @param val
     *            the new filter combo_2
     */
    public abstract void setFilterCombo_2(String val);


    /**
     * Sets the filter text.
     * 
     * @param val
     *            the new filter text
     */
    public abstract void setFilterText(String val);


    /**
     * Gets the button definitions.
     * 
     * @return the button definitions
     */
    public ArrayList<ButtonDef> getButtonDefinitions()
    {
        return button_defs;
    }


    /**
     * Gets the default parameters.
     * 
     * @return the default parameters
     */
    public String[] getDefaultParameters()
    {
        return defaultParameters;
    }


    public boolean hasCustomDisplayHeader()
    {
        return customDisplayHeader;
    }


    public void setCustomDisplayHeader(boolean customDisplayHeader)
    {
        this.customDisplayHeader = customDisplayHeader;
    }


    public JPanel getCustomDisplayHeader()
    {
        return null;
    }


    public abstract void editTableRow();


    public abstract String getHelpId();

}
