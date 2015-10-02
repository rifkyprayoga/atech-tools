package com.atech.graphics.dialogs.selector;

import java.util.List;

import com.atech.graphics.components.jtable.TableModelWithToolTip;

/**
 * Created by andy on 26.09.15.
 */
public class SelectorTableModel extends TableModelWithToolTip
{

    SelectableInterface selectorTypeObject;
    List<SelectableInterface> listOfElements;


    public SelectorTableModel(SelectableInterface selectorTypeObject, List<SelectableInterface> listOfElements)
    {
        this.selectorTypeObject = selectorTypeObject;
        this.listOfElements = listOfElements;
    }


    public String getToolTipValue(int row, int column)
    {
        if (selectorTypeObject instanceof SelectableInterfaceV2)
        {
            SelectableInterfaceV2 sel = (SelectableInterfaceV2) listOfElements.get(row);
            return sel.getToolTipValue(column + 1);
        }
        else
            return (String) getValueAt(row, column);

    }


    public int getRowCount()
    {
        if (listOfElements == null)
            return 0;
        else
            return listOfElements.size();
    }


    public int getColumnCount()
    {
        return this.selectorTypeObject.getColumnCount();
    }


    public Object getValueAt(int rowIndex, int columnIndex)
    {
        SelectableInterface sel = listOfElements.get(rowIndex);
        return sel.getColumnValue(columnIndex + 1);
    }
}
