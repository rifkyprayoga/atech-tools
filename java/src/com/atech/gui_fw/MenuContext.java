package com.atech.gui_fw;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuContext
{

    public ArrayList<MenuContext> children = null;
    
    
    public static final int MENU_TYPE_NONE = 0;
    public static final int MENU_TYPE_MENU = 1;
    public static final int MENU_TYPE_ITEM = 2;
    
    public int menu_type = 0;
    
    public JMenu menu = null;
    public JMenuItem menu_item = null;
    
    
    String item_name = null;
    
    public String parent_name = null;
    public int required_access_level = 0;
    public boolean access_level_traverse = false;
    public boolean access_level_invisible = false;
    public boolean should_be_processed = false;
    public boolean is_base_context = true;
    
    public MenuContext(String keyword, JMenuItem mi, int min_level, boolean process)
    {
        this.item_name = keyword;
        this.menu_item = mi;
        this.required_access_level = min_level;
        this.should_be_processed = process;
        this.menu_type = MENU_TYPE_ITEM;
    }
    
    
    
    
}
