package com.atech.graphics.dialogs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JTable;

import com.atech.i18n.I18nControlAbstract;

public abstract class GUIListDefAbstract
{
    
    public static final int ACTION_ADD = 1;
    public static final int ACTION_EDIT = 2;
    public static final int ACTION_DELETE = 3;
    
    protected I18nControlAbstract ic = null;
    protected String translation_root = null;
    
    protected JTable table;
    protected ArrayList full_list;
    protected ArrayList active_list;
    
    protected String[] filter_options = null;
    //protected boolean filter_enabled = false;
    protected String[] filter_texts = null;
    protected int filter_type = FILTER_NONE;
    protected ArrayList<ButtonDef> button_defs;
    
    public static final int FILTER_NONE = 0;
    public static final int FILTER_COMBO = 1;
    public static final int FILTER_COMBO_AND_TEXT = 2;
    
    
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
    
    
    
    public String[] getFilterOptions()
    {
        return this.filter_options;
    }    
    
    
    public boolean hasFilter()
    {
        return this.filter_type > FILTER_NONE;
    }    
    
    public int getFilterType()
    {
        return this.filter_type;
    }
    
    
    public String[] getFilterTexts()
    {
        return this.filter_texts;
    }    
    
    public abstract void setFilterCombo(String val);

    public abstract void setFilterText(String val);
    
    public ArrayList<ButtonDef> getButtonDefinitions()
    {
        return button_defs;
    }
    
    
}
