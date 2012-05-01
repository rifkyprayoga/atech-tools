package com.atech.graphics.dialogs.guilist;

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

public class ButtonDef
{
    /**
     * Button Text
     */
    public String text;
    
    /**
     * Action 
     */
    public String action;
    
    /**
     * Description 
     */
    public String desc;
    
    /**
     * Icon Name 
     */
    public String icon_name;
    
    
    public ButtonDef()
    {
    }
    
    
    /**
     * Constructor
     * 
     * @param _text
     * @param _action
     * @param _desc
     * @param _icon_name
     */
    public ButtonDef(String _text, String _action, String _desc, String _icon_name)
    {
        this.text = _text;
        this.action = _action;
        this.desc = _desc;
        this.icon_name = _icon_name;
    }
}
