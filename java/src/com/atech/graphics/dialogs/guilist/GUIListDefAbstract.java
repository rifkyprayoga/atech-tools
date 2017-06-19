package com.atech.graphics.dialogs.guilist;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.HibernateSelectableObject;
import com.atech.graphics.dialogs.DialogCreator;
import com.atech.graphics.dialogs.StandardDialogForObject;
import com.atech.graphics.dialogs.selector.SelectableInterface;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

// TODO: Auto-generated Javadoc

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy
 *
 */

public abstract class GUIListDefAbstract
{

    private static final Logger LOG = LoggerFactory.getLogger(GUIListDefAbstract.class);

    // public static final int ACTION_ADD = 1;
    // public static final int ACTION_EDIT = 2;
    // public static final int ACTION_DELETE = 3;

    protected ATDataAccessAbstract dataAccess;
    protected I18nControlAbstract i18nControl = null;
    protected HibernateDb database;

    protected Class<? extends HibernateSelectableObject> clazz;
    protected HibernateSelectableObject targetObject;
    protected String titleI18nKey;
    protected String listDefinitionName;
    protected String helpId;
    protected Rectangle tableBounds;
    protected Dimension windowDimension;

    protected String[] filterOptionsCombo1 = null;
    protected String[] filterOptionsCombo2 = null;
    protected String[] filterDescriptionTexts = null;
    protected ArrayList<ButtonDef> buttonDefintions;

    protected GuiListFilterType filterType = GuiListFilterType.None;

    protected String translationRoot = null;

    protected JTable table;
    protected AbstractTableModel model;
    public GUIListDialog parentDialog;
    protected java.util.List<? extends HibernateSelectableObject> fullList = null;
    protected java.util.List<? extends HibernateSelectableObject> activeList = null;

    // public static final int FILTER_NONE = 0;
    // public static final int FILTER_COMBO = 1;
    // public static final int FILTER_COMBO_AND_TEXT = 2;
    // public static final int FILTER_COMBO_TWICE = 3;

    protected String[] defaultParameters;

    private JPanel customDisplayHeader = null;

    protected boolean disableFilterRun = false;

    protected String filterComboText = "";
    protected String filterCombo2Text = "";
    protected String filterTextText = "";


    public <E extends HibernateSelectableObject> GUIListDefAbstract(ATDataAccessAbstract dataAccess, E targetObject,
            String titleI18nKey, String listDefinitionName, String helpId, Rectangle tableBounds,
            Dimension windowDimension)
    {
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();
        this.clazz = targetObject.getClass();
        this.targetObject = targetObject;
        this.titleI18nKey = titleI18nKey;
        this.listDefinitionName = listDefinitionName;
        this.helpId = helpId;
        this.tableBounds = tableBounds;
        this.windowDimension = windowDimension;

        database = dataAccess.getHibernateDb();
        init();
        initCustomDisplayPanel();
    }


    // i18nControl, translation root
    /**
     * Inits the.
     */
    public abstract void init();


    /**
     * Gets the message.
     * 
     * @param keyword
     *            the keyword
     * @return the message
     */
    public String getMessage(String keyword)
    {
        return i18nControl.getMessage(this.translationRoot + "_" + keyword);
    }


    /**
     * Gets the translation root.
     * 
     * @return the translation root
     */
    public String getTranslationRoot()
    {
        return this.translationRoot;
    }


    /**
     * In this method you can add additional custom elements for this lister.
     * 
     * @param main_panel
     *            the main_panel
     */
    public void additionalGUIInit(JPanel main_panel)
    {
    }


    /**
     * Gets the filter options combo1.
     * 
     * @return the filter options combo1
     */
    public String[] getFilterOptionsCombo1()
    {
        return this.filterOptionsCombo1;
    }


    /**
     * Gets the filter options combo2.
     * 
     * @return the filter options combo2
     */
    public String[] getFilterOptionsCombo2()
    {
        return this.filterOptionsCombo2;
    }


    /**
     * Checks for filter.
     * 
     * @return true, if successful
     */
    public boolean hasFilter()
    {
        return this.filterType != GuiListFilterType.None;
    }


    /**
     * Gets the filter type.
     * 
     * @return the filter type
     */
    public GuiListFilterType getFilterType()
    {
        return this.filterType;
    }


    /**
     * Sets the parent instance.
     * 
     * @param parentDialog
     *            the new parent instance
     */
    public void setParentDialog(GUIListDialog parentDialog)
    {
        this.parentDialog = parentDialog;
    }


    public GUIListDialog getParentDialog()
    {
        return this.parentDialog;
    }


    /**
     * Gets the filter texts.
     * 
     * @return the filter texts
     */
    public String[] getFilterDescriptionTexts()
    {
        return this.filterDescriptionTexts;
    }


    /**
     * Gets the button definitions.
     * 
     * @return the button definitions
     */
    public ArrayList<ButtonDef> getButtonDefinitions()
    {
        return buttonDefintions;
    }


    /**
     * Gets the default parameters.
     * 
     * @return the default parameters
     */
    public String[] getDefaultParameters()
    {
        return defaultParameters;
    }


    public boolean hasCustomDisplayHeader()
    {
        return customDisplayHeader != null;
    }


    public JPanel getCustomDisplayHeader()
    {
        return this.customDisplayHeader;
    }


    public void setCustomDisplayHeader(JPanel panel)
    {
        this.customDisplayHeader = panel;
    }


    /**
     * Refresh table
     */
    public void refreshTable()
    {
        // System.out.println("Table refresh: " + table);
        // System.out.println("Table refresh: " + table.getModel());

        if ((table != null) && (table.getModel() != null))
        {
            ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            // System.out.println("Table refresh DO");
        }
    }


    /**
     * Enable filter run (when setFilterCombo, setFilterCombo2 or setFilterText) is called
     */
    protected void enableFilterRun()
    {
        disableFilterRun = false;
    }


    /**
     * Disable filter run (when setFilterCombo, setFilterCombo2 or setFilterText) is called
     */
    protected void disableFilterRun()
    {
        disableFilterRun = true;
    }


    protected void loadData()
    {
        this.fullList = database.getAllTypedHibernateData(clazz);
        fireFilterData();
    }


    protected void reloadData()
    {
        this.fullList = database.getAllTypedHibernateData(clazz, true);
        fireFilterData();
    }


    protected void fireFilterData()
    {
        filterData();
        LOG.debug("Filtering {}. [full={}, filtered={}]", this.clazz.getSimpleName(), this.fullList.size(),
            this.activeList.size());
        refreshTable();
    }


    protected abstract void filterData();


    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle()
    {
        return i18nControl.getMessage(titleI18nKey);
    }


    /**
     * Gets the table.
     *
     * @return the j table
     */
    public JTable getJTable()
    {
        if (this.table == null)
        {

            this.table = new JTable(new AbstractTableModel()
            {

                private static final long serialVersionUID = 4761913214056086567L;


                public int getColumnCount()
                {
                    // System.out.println("ColumnCount: " +
                    // targetObject.getColumnCount() + ", object=" +
                    // targetObject);
                    return targetObject.getColumnCount();
                }


                public int getRowCount()
                {
                    // System.out.println("Row count: " + activeList.size());
                    return activeList.size();
                }


                public Object getValueAt(int row, int column)
                {
                    HibernateSelectableObject dataObject = activeList.get(row);
                    // System.out.println("C: " +
                    // dataObject.getColumnValue(column));

                    return dataObject.getColumnValue(column);
                }
            });

            int cwidth = 0;
            int twidth = (int) getTableSize(0).getWidth();

            // System.out.println("Twidth: " + twidth);

            TableColumnModel cm = table.getColumnModel();

            for (int i = 0; i < targetObject.getColumnCount(); i++)
            {
                cm.getColumn(i).setHeaderValue(this.i18nControl.getMessage(targetObject.getColumnName(i)));

                // System.out.println("ColumnName: " +
                // i18nControl.getMessage(targetObject.getColumnName(i)));

                cwidth = targetObject.getColumnWidth(i, twidth);

                // System.out.println("ColumnWidth: " + cwidth);

                if (cwidth > 0)
                {
                    cm.getColumn(i).setPreferredWidth(cwidth);
                }
            }

        }

        // loadData();
        // refreshTable();

        return this.table;
    }


    /**
     * Do table action.
     *
     * @param action
     *            the action
     */
    public void doTableAction(String action)
    {
        if (action.equals("add_object"))
        {
            addTableRow();
        }
        else if (action.equals("edit_object"))
        {
            editTableRow();
        }
        else if (action.equals("delete_object"))
        {
            deleteTableRow();
        }
        else
        {
            if (!doCustomTableAction(action))
            {
                LOG.warn("{} - Table action not supported: {}", this.getClass().getSimpleName(), action);
            }
        }
    }


    /**
     * Execute custom table action.
     *
     * @param action actionCommand
     * @return true if actionCommand supported
     */
    public abstract boolean doCustomTableAction(String action);


    /**
     * Default Add Data to Table (if you override this make sure you call reloadData if operation was Success)
     */
    public void addTableRow()
    {
        StandardDialogForObject dialog = createDialog(this.clazz);

        if (dialog.wasOperationSuccessful())
        {
            this.reloadData();
        }
    }


    /**
     * Default Edit Data from Table (if you override this make sure you call reloadData if operation was Success)
     */
    public void editTableRow()
    {
        int index = this.getParentDialog().getSelectedObjectIndexFromTable();

        if (index > -1)
        {
            HibernateSelectableObject editableObject = this.activeList.get(index);

            StandardDialogForObject dialog = createDialog(this.clazz, editableObject, true);

            if (dialog.wasOperationSuccessful())
            {
                reloadData();
            }
        }
    }


    /**
     * Default Delete Data from Table (if you override this make sure you call reloadData if operation was Success)
     */
    public void deleteTableRow()
    {
        int index = this.getParentDialog().getSelectedObjectIndexFromTable();

        if (index > -1)
        {
            HibernateSelectableObject deletableObject = this.activeList.get(index);

            if (database.isObjectUsed(deletableObject))
            {
                dataAccess.showMessageDialog(this.getParentDialog(), ATSwingUtils.DialogType.Error, String.format(
                    i18nControl.getMessage("OBJECT_IN_USE_CANT_DELETE"), deletableObject.toStringDescriptive()));
            }
            else
            {
                int option_selected = JOptionPane.showOptionDialog(this.getParentDialog(),
                    String.format(i18nControl.getMessage("ARE_YOU_SURE_DELETE_OBJECT"),
                        deletableObject.toStringDescriptive()),
                    i18nControl.getMessage("QUESTION"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    ATDataAccessAbstract.options_yes_no, JOptionPane.YES_OPTION);

                if (option_selected == JOptionPane.NO_OPTION)
                    return;

                if (database.deleteHibernate(deletableObject))
                {
                    reloadData();
                }
                else
                {
                    dataAccess.showMessageDialog(this.getParentDialog(), ATSwingUtils.DialogType.Error,
                        String.format(i18nControl.getMessage("ERROR_DELETING_OBJECT"),
                            deletableObject.toStringDescriptive(), database.getErrorDescription()));
                }
            }
        }
    }


    public StandardDialogForObject createDialog(Class clazz)
    {

        DialogCreator dialogCreator = getDialogCreator(clazz);

        if (dialogCreator != null)
        {
            return dialogCreator.createDialog(clazz, this.getParentDialog());
        }

        return null;
    }


    public StandardDialogForObject createDialog(Class clazz, SelectableInterface selectableObject, boolean edit)
    {
        DialogCreator dialogCreator = getDialogCreator(clazz);

        if (dialogCreator != null)
        {
            return dialogCreator.createDialog(clazz, this.getParentDialog(),
                (HibernateSelectableObject) selectableObject, edit);
        }

        return null;
    }


    public DialogCreator getDialogCreator(Class clazz)
    {

        java.util.List<DialogCreator> dialogCreators = dataAccess.getDialogCreators();

        if (dialogCreators != null)
        {
            for (DialogCreator creator : dataAccess.getDialogCreators())
            {
                if (creator.isApplicable(clazz))
                    return creator;
            }
        }

        LOG.warn("Dialog Creator for {} not implemented.", clazz);

        return null;
    }


    /**
     * Sets the value of filter for first combo and calls filtering.
     *
     * @param value current value of filter (combo1)
     */
    public void setFilterCombo(String value)
    {
        this.filterComboText = value;

        if (!disableFilterRun)
            fireFilterData();
    }


    /**
     * Sets the value of filter for textField and calls filtering.
     *
     * @param value current value of filter (textField)
     */
    public void setFilterText(String value)
    {
        this.filterTextText = value;
        if (!disableFilterRun)
            fireFilterData();
    }


    /**
     * Sets the value of filter for second combo and calls filtering.
     *
     * @param value current value of filter (combo2)
     */
    public void setFilterCombo2(String value)
    {
        this.filterCombo2Text = value;
        if (!disableFilterRun)
            fireFilterData();
    }


    /**
     * Gets the def name.
     *
     * @return the def name
     */
    public String getDefName()
    {
        return listDefinitionName;
    }


    /**
     * Gets the window size.
     *
     * @return the window size
     */
    public Dimension getWindowSize()
    {
        return this.windowDimension;
    }


    /**
     * Gets the table size.
     *
     * @param pos_y
     *            the pos_y
     * @return the table size
     */
    public Rectangle getTableSize(int pos_y)
    {
        this.tableBounds.setLocation((int) this.tableBounds.getX(), pos_y);
        return tableBounds;
    }


    /**
     * Get Help Id for component
     * @return
     */
    public String getHelpId()
    {
        return this.helpId;
    }


    protected void initCustomDisplayPanel()
    {
        // default is no custom display panel
    }

    public enum GuiListFilterType
    {
        None, Combo, ComboAndText, ComboTwice
    }

}
