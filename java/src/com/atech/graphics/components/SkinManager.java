package com.atech.graphics.components;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

import com.atech.graphics.components.jtable.JTableUtil;

/**
 * Created by andy on 09/02/17.
 */
public class SkinManager
{

    private Map<String, Object> skinLfOverrides;


    public Map<String, Object> getSkinLfOverrides()
    {
        return skinLfOverrides;
    }


    public void setSkinLfOverrides(Map<String, Object> skinLfOverrides)
    {
        this.skinLfOverrides = skinLfOverrides;
    }


    public void reSkinifyComponent(JTable table)
    {
        if ((skinLfOverrides != null) && (skinLfOverrides.containsKey("JTableHeader.backgroundColor")))
        {
            JTableUtil.reconfigureTableHeader(table, (Color) skinLfOverrides.get("JTableHeader.backgroundColor"),
                (Color) skinLfOverrides.get("JTableHeader.foregroundColor"),
                (Color) skinLfOverrides.get("JTableHeader.borderColor"));
        }
    }

}
