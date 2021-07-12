package com.atech.graphics.components.jlist;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CheckBoxList2 extends JList
{

    private static final long serialVersionUID = 738345084947304955L;

    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);


    public CheckBoxList2(ListModel dataModel)
    {
        super(dataModel);
        this.setCellRenderer(new CheckboxListCellRenderer2());
    }


    public CheckBoxList2(final Object[] listData)
    {
        super(listData);
        this.setCellRenderer(new CheckboxListCellRenderer2());
    }


    public CheckBoxList2(final Vector<?> listData)
    {
        super(listData);
        this.setCellRenderer(new CheckboxListCellRenderer2());
    }


    public CheckBoxList2()
    {
        super();
        this.setCellRenderer(new CheckboxListCellRenderer2());
    }

    // public void init()
    // {
    // setCellRenderer(new CellRenderer());
    //
    // addMouseListener(new MouseAdapter()
    // {
    //
    // public void mousePressed(MouseEvent e)
    // {
    // int index = locationToIndex(e.getPoint());
    //
    // if (index != -1)
    // {
    // JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
    // checkbox.setSelected(!checkbox.isSelected());
    // repaint();
    // }
    // }
    // });
    //
    // setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //
    // }

    public class CheckboxListCellRenderer2 extends JCheckBox implements ListCellRenderer
    {

        private static final long serialVersionUID = -2083595632445144694L;


        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                boolean cellHasFocus)
        {

            setComponentOrientation(list.getComponentOrientation());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setSelected(isSelected);
            setEnabled(list.isEnabled());

            setText(value == null ? "" : " " + value.toString());

            return this;
        }
    }

}
