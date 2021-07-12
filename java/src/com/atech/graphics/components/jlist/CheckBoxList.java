package com.atech.graphics.components.jlist;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CheckBoxList extends JList implements ItemListener
{

    private static final long serialVersionUID = 738345084947304955L;

    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    boolean specialPaint;

    java.util.List<ItemListener> itemListenerList = new ArrayList<ItemListener>();

    ItemListener itemListener;


    public CheckBoxList(ListModel dataModel)
    {
        super(dataModel);
        init();
    }


    public CheckBoxList(final Object[] listData)
    {
        super(listData);
        init();
    }


    public CheckBoxList(final Vector<?> listData)
    {
        super(listData);
        init();
    }


    public CheckBoxList()
    {
        super();
        init();
    }


    public void init()
    {
        specialPaint = false;
        setCellRenderer(new CheckBoxListCellRenderer());
        itemListener = this;

        // System.out.println("Item Listener: " + itemListener);

        addMouseListener(new MouseAdapter()
        {

            public void mousePressed(MouseEvent e)
            {
                int index = locationToIndex(e.getPoint());

                if (index != -1)
                {
                    JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
                    checkbox.setSelected(!checkbox.isSelected());

                    repaint();
                }
            }
        });

        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }


    public void addCheckBoxText(String checkBoxString)
    {
        JCheckBox checkBox = new JCheckBox(" " + checkBoxString);
        checkBox.addItemListener(itemListener);

        addCheckBox(checkBox);
    }


    public void addCheckBoxTexts(java.util.List<String> itemList)
    {
        JCheckBox[] newList = new JCheckBox[itemList.size()];

        int i = 0;

        for (String item : itemList)
        {
            JCheckBox checkBox = new JCheckBox(" " + item);
            checkBox.addItemListener(itemListener);

            newList[i] = checkBox;
            i++;
        }
        setListData(newList);
    }


    public void addCheckBox(JCheckBox checkBox)
    {
        ListModel currentList = this.getModel();
        JCheckBox[] newList = new JCheckBox[currentList.getSize() + 1];
        for (int i = 0; i < currentList.getSize(); i++)
        {
            newList[i] = (JCheckBox) currentList.getElementAt(i);
        }
        newList[newList.length - 1] = checkBox;
        setListData(newList);
    }


    public void selectionNeedsToSpecialPaint(boolean specialPaint)
    {
        this.specialPaint = specialPaint;
    }


    public void addItemListener(ItemListener itemListener)
    {
        this.itemListenerList.add(itemListener);
    }


    private void checkBoxItemStateChanged(ItemEvent e)
    {
        for (ItemListener itemListener : itemListenerList)
        {
            itemListener.itemStateChanged(e);
        }
    }


    public void itemStateChanged(ItemEvent e)
    {
        // ItemEvent ev = new ItemEvent(e.getItemSelectable(), e0L, this,
        // ItemEvent.ITEM_STATE_CHANGED);

        e.setSource(this);

        checkBoxItemStateChanged(e);
    }


    public Set<String> getSelectedItemsSet()
    {
        Set<String> list = new HashSet<String>();

        ListModel currentList = this.getModel();

        for (int i = 0; i < currentList.getSize(); i++)
        {
            JCheckBox cb = (JCheckBox) currentList.getElementAt(i);

            if (cb.isSelected())
            {
                String itemText = cb.getText().substring(1);
                list.add(itemText);
            }
        }

        return list;
    }

    public class CheckBoxListCellRenderer extends JCheckBox implements ListCellRenderer
    {

        private static final long serialVersionUID = -3925741473860541322L;


        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                boolean cellHasFocus)
        {
            JCheckBox checkbox = (JCheckBox) value;
            if (specialPaint)
            {
                checkbox.setBackground(isSelected ? getSelectionBackground() : getBackground());
                checkbox.setForeground(isSelected ? getSelectionForeground() : getForeground());
                checkbox.setBorderPainted(true);
            }
            else
            {
                checkbox.setBackground(isSelected ? getBackground() : getBackground());
                checkbox.setForeground(isSelected ? getForeground() : getForeground());
                checkbox.setBorderPainted(false);
            }
            checkbox.setEnabled(isEnabled());
            checkbox.setFont(getFont());
            checkbox.setFocusPainted(false);

            // checkbox.setOpaque(true);
            checkbox.setBorder(isSelected ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
            return checkbox;
        }
    }
}
