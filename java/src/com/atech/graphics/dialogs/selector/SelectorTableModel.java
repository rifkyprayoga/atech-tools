package com.atech.graphics.dialogs.selector;

import java.util.List;

import com.atech.db.hibernate.HibernateSelectableObject;
import com.atech.graphics.components.jtable.TableModelWithToolTip;

/**
 * Created by andy on 26.09.15.
 */
public class SelectorTableModel extends TableModelWithToolTip
{

    private static final long serialVersionUID = -4578214502176113799L;
    SelectableInterface selectorTypeObject;
    List<SelectableInterface> listOfElements;
    public boolean columnStartsAtZero = false;


    public SelectorTableModel(SelectableInterface selectorTypeObject, List<SelectableInterface> listOfElements)
    {
        this.selectorTypeObject = selectorTypeObject;
        this.listOfElements = listOfElements;

        if (selectorTypeObject instanceof HibernateSelectableObject)
        {
            this.columnStartsAtZero = true;
        }

    }


    public String getToolTipValue(int row, int columnIndex)
    {
        if (selectorTypeObject instanceof SelectableInterfaceV2)
        {
            SelectableInterfaceV2 sel = (SelectableInterfaceV2) listOfElements.get(row);
            return sel.getToolTipValue(this.columnStartsAtZero ? columnIndex : columnIndex + 1);
        }
        else
            return (String) getValueAt(row, columnIndex);

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
        return sel.getColumnValue(this.columnStartsAtZero ? columnIndex : columnIndex + 1);
    }
}
