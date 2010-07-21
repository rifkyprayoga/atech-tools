package com.atech.graphics.dialogs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.atech.i18n.I18nControlAbstract;

public abstract class GUIListDefAbstract
{
    
    public static final int ACTION_ADD = 1;
    public static final int ACTION_EDIT = 2;
    public static final int ACTION_DELETE = 3;
    
    protected I18nControlAbstract ic = null;
    protected String translation_root = null;
    
    protected JTable table;
    protected AbstractTableModel model;
    //protected ArrayList full_list;
    //protected ArrayList active_list;
    
    protected String[] filter_options_combo1 = null;
    protected String[] filter_options_combo2 = null;
    
    //protected boolean filter_enabled = false;
    protected String[] filter_texts = null;
    protected int filter_type = FILTER_NONE;
    protected ArrayList<ButtonDef> button_defs;
    
    public static final int FILTER_NONE = 0;
    public static final int FILTER_COMBO = 1;
    public static final int FILTER_COMBO_AND_TEXT = 2;
    public static final int FILTER_COMBO_TWICE = 3;
    
    public GUIListDialog parent_dialog;
    
    protected String[] def_parameters;
    
    // ic, translation root
    public abstract void init();
    
    public abstract String getTitle();
    
    public String getMessage(String keyword)
    {
        return ic.getMessage(this.translation_root + "_" + keyword);
    }

    public String getTranslationRoot()
    {
        return this.translation_root;
    }
    
    public abstract JTable getJTable();

    
    public abstract void doTableAction(String action);
    
    public abstract String getDefName();
    
    public abstract Dimension getWindowSize();
    
    public abstract Rectangle getTableSize(int pos_y);
  
    
    /**
     * In this method you can add additional custom elements for this lister  
     * 
     * @param main_panel 
     */
    public void additionalGUIInit(JPanel main_panel)
    {
    }
    
    public String[] getFilterOptionsCombo1()
    {
        return this.filter_options_combo1;
    }    

    public String[] getFilterOptionsCombo2()
    {
        return this.filter_options_combo2;
    }    
    
    
    public boolean hasFilter()
    {
        return this.filter_type > FILTER_NONE;
    }    
    
    public int getFilterType()
    {
        return this.filter_type;
    }
    
    
    public void setParentInstance(GUIListDialog _parent_dialog)
    {
        this.parent_dialog = _parent_dialog;
    }
    
    
    public String[] getFilterTexts()
    {
        return this.filter_texts;
    }    
    
    public abstract void setFilterCombo(String val);

    public abstract void setFilterCombo_2(String val);
    
    public abstract void setFilterText(String val);
    
    public ArrayList<ButtonDef> getButtonDefinitions()
    {
        return button_defs;
    }
    
    public String[] getDefaultParameters()
    {
        return def_parameters;
    }
    
}
