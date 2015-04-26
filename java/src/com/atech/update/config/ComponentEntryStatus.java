package com.atech.update.config;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

/**
 * Created by andy on 16.03.15.
 */
public enum ComponentEntryStatus
{
    None(0, "", ""), //
    WillBeUpdated(1, "led_green.gif", "NEW_VERSION"), //
    CorrectVersion(2, "led_gray.gif", "SAME_VERSION"), //
    New(3, "led_yellow.gif", "NEW_COMPONENT"), //
    Unknown(4, "led_blue.gif", "UNKNOWN"), //
    Removed(5, "led_red.gif", "REMOVED_COMPONENT"), //
    ;

    int code;
    String iconName;
    private ImageIcon imageIcon;
    String i18nKeyword;

    static Map<Integer, ComponentEntryStatus> mapInteger = new HashMap<Integer, ComponentEntryStatus>();
    static Map<String, ComponentEntryStatus> mapString = new HashMap<String, ComponentEntryStatus>();


    ComponentEntryStatus(int code, String iconName, String i18nKeyword)
    {
        this.code = code;
        this.iconName = iconName;
        this.i18nKeyword = i18nKeyword;
    }

    static
    {
        for (ComponentEntryStatus ces : values())
        {
            mapInteger.put(ces.code, ces);
            mapString.put("" + ces.code, ces);
        }
    }


    public String getIconName()
    {
        return iconName;
    }


    public void setImageIcon(ImageIcon imageIcon)
    {
        this.imageIcon = imageIcon;
    }


    public ImageIcon getImageIcon()
    {
        return imageIcon;
    }


    public static ComponentEntryStatus getByCodeString(String stringValue)
    {
        if (mapString.containsKey(stringValue))
            return mapString.get(stringValue);
        else
            return Unknown;
    }


    public String getI18nKeyword()
    {
        return i18nKeyword;
    }
}
