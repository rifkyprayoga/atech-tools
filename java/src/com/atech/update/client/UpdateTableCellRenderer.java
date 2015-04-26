package com.atech.update.client;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.atech.update.config.ComponentEntryStatus;
import com.atech.update.config.ComponentInterface;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  UpdateTableCellRenderer - Update Table Cell Renderer
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy {andy@atech-software.com}
 *
*/

public class UpdateTableCellRenderer extends DefaultTableCellRenderer
{

    private static final long serialVersionUID = -1163229361235496043L;
    ATDataAccessAbstract m_da;
    static Font treeHeaderFont;
    static Font treeItemFont;


    /**
     * Constructor
     * 
     * @param da
     */
    public UpdateTableCellRenderer(ATDataAccessAbstract da)
    {
        super();

        m_da = da;

        // ATSwingUtils
        ATSwingUtils.initLibrary();

        if (treeHeaderFont == null)
        {
            treeHeaderFont = getFont().deriveFont(Font.BOLD, 13.0f);
        }

        if (treeItemFont == null)
        {
            treeItemFont = getFont().deriveFont(Font.ITALIC, 13.0f);
        }
    }


    /**
     *
     * Returns the default table cell renderer.
     * <p>
     * During a printing operation, this method will be called with
     * <code>isSelected</code> and <code>hasFocus</code> values of
     * <code>false</code> to prevent selection and focus from appearing
     * in the printed output. To do other customization based on whether
     * or not the table is being printed, check the return value from
     *
     * @param table  the <code>JTable</code>
     * @param value  the value to assign to the cell at
     *			<code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus true if cell has focus
     * @param row  the row of the cell to render
     * @param column the column of the cell to render
     * @return the default table cell renderer
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        ComponentInterface uo = (ComponentInterface) table.getModel().getValueAt(row, -1);

        if (column == 3)
        {

            if (uo.isGroup())
            {
                setBackground(Color.lightGray);
                setForeground(Color.black);
                setFont(treeHeaderFont);
                setOpaque(true);
            }
            else
            {
                JLabel lab = new JLabel();

                ComponentEntryStatus ces = ComponentEntryStatus.getByCodeString(value.toString());

                lab.setIcon(ces.getImageIcon());
                lab.setToolTipText(m_da.getI18nControlInstance().getMessage(ces.getI18nKeyword()));
                lab.setHorizontalAlignment(JLabel.CENTER);

                return lab;
            }

        }
        /*
         * else if (column==4)
         * {
         * JPanel panel = new JPanel();
         * JCheckBox cb = new JCheckBox();
         * if (value.toString().toUpperCase().equals("TRUE"))
         * cb.setSelected(true);
         * else
         * cb.setSelected(false);
         * if (!uo.isLeaf())
         * panel.setBackground(Color.lightGray);
         * else
         * panel.setBackground(Color.white);
         * panel.add(cb);
         * return panel;
         * }
         */
        else
        {
            if (!uo.isGroup())
            {
                if (isSelected)
                {
                    setBackground(table.getSelectionBackground());
                }
                else
                {
                    setBackground(table.getBackground());
                }
                setForeground(Color.black);
                setFont(treeItemFont);
            }
            else
            {
                setBackground(Color.lightGray);
                setForeground(Color.black);
                setFont(treeHeaderFont);
                setOpaque(true);
            }

        }

        return this;
    }
}
