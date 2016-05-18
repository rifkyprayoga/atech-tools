package com.atech.graphics.components.jtable;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Created by andy on 13.02.16.
 */
public class JTableUtil
{

    public static void reconfigureTableHeader(JTable table, final Color backgroundColor, final Color foregroundColor,
            final Color borderColor)
    {

        Border cellBorderX = null;
        Border lastCellBorderX = null;
        Border firstCellBorderX = null;

        if (borderColor != null)
        {
            // firstCellBorderX = BorderFactory.createMatteBorder(1, 0, 1, 0,
            // borderColor);
            cellBorderX = BorderFactory.createMatteBorder(1, 0, 1, 1, borderColor);
            lastCellBorderX = BorderFactory.createMatteBorder(1, 0, 1, 1, borderColor);
        }

        final Border cellBorder = cellBorderX;
        final Border lastCellBorder = lastCellBorderX;
        final Border firstCellBorder = firstCellBorderX;

        final int lastColumn = table.getTableHeader().getColumnModel().getColumnCount();

        // setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

        // table.getTableHeader().getDefaultRenderer();

        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
        {

            private static final long serialVersionUID = -7335138964193508211L;


            {
                // you need to set it to opaque
                // setOpaque(true);
            }


            @Override
            public Component getTableCellRendererComponent(final JTable table, final Object value,
                    final boolean isSelected, final boolean hasFocus, final int row, final int column)
            {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);

                // System.out.println("Componeht: row=" + row + ", column: " +
                // column);

                if (backgroundColor != null)
                {
                    component.setBackground(backgroundColor);
                }

                if (foregroundColor != null)
                {
                    component.setForeground(foregroundColor);
                }

                if (cellBorder != null)
                {
                    JComponent jComponent = (JComponent) component;

                    if (column == lastColumn)
                        jComponent.setBorder(lastCellBorder);
                    // else if (column == 0)
                    // jComponent.setBorder(firstCellBorder);
                    else
                        jComponent.setBorder(cellBorder);
                }

                return component;

            }
        });

        if (borderColor != null)
        {
            // table.getTableHeader().setBorder(new LineBorder(borderColor));
            // table.getTableHeader()
            // table.getTableHeader().setBorder(BorderFactory.createMatteBorder(2,
            // 0, 0, 0, Color.BLACK));
        }

    }

}
