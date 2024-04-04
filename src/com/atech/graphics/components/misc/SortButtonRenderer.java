package com.atech.graphics.components.misc;

import java.awt.Component;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.atech.graphics.dialogs.selector.BevelArrowIcon;
import com.atech.graphics.dialogs.selector.BlankIcon;
import com.atech.graphics.dialogs.selector.SelectorAbstractDialog;

public class SortButtonRenderer extends JButton implements TableCellRenderer
{
    /**
     * 
     */
    private static final long serialVersionUID = 4647957917664294957L;
    public static final int NONE = 0;
    public static final int DOWN = 1;
    public static final int UP = 2;

    int pushedColumn;
    Hashtable<Integer, Integer> state;
    JButton downButton, upButton;
    SelectorAbstractDialog m_selDialog = null;

    public SortButtonRenderer(SelectorAbstractDialog selDialog)
    {
        pushedColumn = 0;
        state = new Hashtable<Integer, Integer>();
        state.put(new Integer(0), new Integer(DOWN));
        m_selDialog = selDialog;

        setMargin(new Insets(0, 0, 0, 0));
        setHorizontalTextPosition(LEFT);
        setIcon(new BlankIcon());
        this.setIconTextGap(10);
        this.setHorizontalAlignment(CENTER);

        // perplexed
        // ArrowIcon(SwingConstants.SOUTH, true)
        // BevelArrowIcon (int direction, boolean isRaisedView, boolean
        // isPressedView)

        downButton = new JButton();
        downButton.setMargin(new Insets(0, 0, 0, 0));
        downButton.setHorizontalTextPosition(LEFT);
        downButton.setHorizontalAlignment(CENTER);
        downButton.setIconTextGap(20);
        // downButton.setIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false,
        // false));
        // downButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.DOWN,
        // false, true));
        //

        downButton.setIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, true));
        downButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, true));

        upButton = new JButton();
        upButton.setMargin(new Insets(0, 0, 0, 0));
        upButton.setHorizontalTextPosition(LEFT);
        upButton.setHorizontalAlignment(CENTER);
        upButton.setIconTextGap(20);
        // upButton.se
        // upButton.setIcon(new BevelArrowIcon(BevelArrowIcon.UP, false,
        // false));
        // upButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.UP, false,
        // true));

        upButton.setIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, true));
        upButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, true));

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column)
    {
        JButton button = this;
        Object obj = state.get(new Integer(column));
        if (obj != null)
        {
            if (((Integer) obj).intValue() == DOWN)
            {
                // System.out.println("Column: " + column + " Ascending");
                this.m_selDialog.resortColumns(column, true);
                button = downButton;
            }
            else
            {
                // System.out.println("Column: " + column + " Descending");
                this.m_selDialog.resortColumns(column, false);
                button = upButton;
            }
        }
        button.setText(value == null ? "" : value.toString());
        boolean isPressed = column == pushedColumn;
        button.getModel().setPressed(isPressed);
        button.getModel().setArmed(isPressed);
        return button;
    }

    public void setPressedColumn(int col)
    {
        pushedColumn = col;
    }

    public int getPressedColumn()
    {
        return pushedColumn;
    }

    public void setSelectedColumn(int col)
    {
        if (col < 0)
            return;
        Integer value = null;
        Object obj = state.get(new Integer(col));
        if (obj == null)
        {
            value = new Integer(DOWN);
        }
        else
        {
            if (((Integer) obj).intValue() == DOWN)
            {
                value = new Integer(UP);
            }
            else
            {
                value = new Integer(DOWN);
            }
        }
        state.clear();
        state.put(new Integer(col), value);
    }

    public int getState(int col)
    {
        int retValue;
        Object obj = state.get(new Integer(col));
        if (obj == null)
        {
            retValue = NONE;
        }
        else
        {
            if (((Integer) obj).intValue() == DOWN)
            {
                retValue = DOWN;
            }
            else
            {
                retValue = UP;
            }
        }
        return retValue;
    }
}
