package com.atech.graphics.dialogs;

public class ButtonDef
{
    public String text;
    public String action;
    public String desc;
    public String icon_name;
    
    public ButtonDef(String _text, String _action, String _desc, String _icon_name)
    {
        this.text = _text;
        this.action = _action;
        this.desc = _desc;
        this.icon_name = _icon_name;
    }
}
