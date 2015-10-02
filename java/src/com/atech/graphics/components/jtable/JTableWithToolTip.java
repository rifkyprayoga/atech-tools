package com.atech.graphics.components.jtable;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.apache.commons.lang.StringUtils;

/**
 * Created by andy on 26.09.15.
 */
public class JTableWithToolTip extends JTable
{

    protected TableModelWithToolTip model = null;


    /**
     * Constructor
     */
    public JTableWithToolTip()
    {
        super();
    }


    /**
     *  Constructor

     */
    public JTableWithToolTip(TableModelWithToolTip model)
    {
        super();
        setModel(model);
    }


    /**
     * Set Model
     * @param model
     */
    public void setModel(TableModelWithToolTip model)
    {
        super.setModel(model);
        this.model = model;
    }


    /**
     * Get ToolTip Text
     */
    @Override
    public String getToolTipText(MouseEvent e)
    {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);

        tip = model.getToolTipValue(rowIndex, colIndex);

        if (tip == null)
        {
            Object o = getValueAt(rowIndex, realColumnIndex);

            if (o != null)
            {
                if (o instanceof String)
                {
                    tip = (String) getValueAt(rowIndex, realColumnIndex);
                }
                else
                {
                    tip = o.toString();
                }
            }
        }

        if (StringUtils.isBlank(tip))
        {
            tip = null;
        }

        return tip;
    }


    @Override
    public Point getToolTipLocation(MouseEvent event)
    {
        java.awt.Point p = event.getPoint();

        p.setLocation(p.getX() + 20, p.getY() + 10);

        return p;
    }

}
