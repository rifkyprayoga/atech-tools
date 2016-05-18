package com.atech.graphics.dialogs.guilist;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

import org.apache.commons.lang.StringUtils;

import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  GUIListDialog - GUI List Dialog for displaying GUIListDefAbstract
 *  Copyright (C) 2010  Andy (Aleksander) Rozman (Atech-Software)
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

// REFACTOR with TableLayout
public class GUIListDialog extends JDialog implements ActionListener, HelpCapable, ItemListener, DocumentListener
{

    private static final long serialVersionUID = -4836539817944656937L;
    private ATDataAccessAbstract dataAccess = null;
    private I18nControlAbstract i18nControl = null;

    private JTable table = null;
    private JComboBox cb_filter1 = null, cb_filter2 = null;

    private String sel_combo = null;
    private String sel_combo2 = null;

    GUIListDefAbstract definition;
    JTextField tf_filter;

    JButton helpButton;

    private boolean dataChanged = false;


    /**
     * Constructor
     * 
     * @param frame
     * @param def 
     * @param da 
     */
    public GUIListDialog(JFrame frame, GUIListDefAbstract def, ATDataAccessAbstract da)
    {
        super(frame, "", true);

        this.dataAccess = da;
        this.i18nControl = da.getI18nControlInstance();

        this.definition = def;

        this.definition.setParentDialog(this);

        this.setSize(this.definition.getWindowSize());
        ATSwingUtils.centerJDialog(this, frame);

        // setBounds(x-175, y-150, 450, 380);
        this.setLayout(null);

        init();

        processDefaultParameters();

        this.setVisible(true);
    }


    /**
     * Constructor
     *
     * @param dialog
     * @param def
     * @param da
     */
    public GUIListDialog(JDialog dialog, GUIListDefAbstract def, ATDataAccessAbstract da)
    {
        super(dialog, "", true);

        this.dataAccess = da;
        this.i18nControl = da.getI18nControlInstance();

        this.definition = def;
        this.definition.setParentDialog(this);

        this.setSize(this.definition.getWindowSize());
        ATSwingUtils.centerJDialog(this, dialog);

        this.setLayout(null);

        init();

        processDefaultParameters();

        this.setVisible(true);
    }


    private void init()
    {

        ATSwingUtils.initLibrary();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        panel.setLayout(null);

        this.getContentPane().add(panel);

        JLabel label = new JLabel(i18nControl.getMessage(this.definition.getTitle()));
        label.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 20, this.getWidth(), 35);
        panel.add(label);

        int y = 80;

        int spaceBetweenTableAndFilter = 40;

        if (this.definition.hasFilter())
        {

            ATSwingUtils.getLabel(this.definition.getFilterTexts()[0], 40, y, 150, 25, panel,
                ATSwingUtils.FONT_NORMAL_BOLD);

            cb_filter1 = ATSwingUtils.getComboBox(this.definition.getFilterOptionsCombo1(), 200, y, 220, 25, panel,
                ATSwingUtils.FONT_NORMAL_BOLD);

            sel_combo = this.definition.getFilterOptionsCombo1()[0];
            cb_filter1.addItemListener(this);

            if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_AND_TEXT)
            {
                y += 30;

                ATSwingUtils.getLabel(this.definition.getFilterTexts()[1], 40, y, 150, 25, panel,
                    ATSwingUtils.FONT_NORMAL_BOLD);

                tf_filter = ATSwingUtils.getTextField("", 200, y, 220, 25, panel);

                Document styledDoc = tf_filter.getDocument();
                AbstractDocument doc = (AbstractDocument) styledDoc;
                doc.addDocumentListener(this);

            }
            else if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_TWICE)
            {
                // cb_filter1
                y += 30;

                ATSwingUtils.getLabel(this.definition.getFilterTexts()[1], 40, y, 150, 25, panel,
                    ATSwingUtils.FONT_NORMAL_BOLD);

                // tf_filter = ATSwingUtils.getTextField("", 200, y, 220, 25,
                // panel);

                cb_filter2 = ATSwingUtils.getComboBox(this.definition.getFilterOptionsCombo2(), 200, y, 220, 25, panel,
                    ATSwingUtils.FONT_NORMAL_BOLD);

                sel_combo2 = this.definition.getFilterOptionsCombo2()[0];
                cb_filter2.addItemListener(this);

            }

            /*
             * label = new JLabel(i18nControl.getMessage("FILTER") + ":" );
             * label.setFont(this.font_normal_bold);
             * label.setBounds(40, 75, 100, 25);
             * panel.add(label);
             */

            /*
             * cb_template = new JComboBox(filter_types);
             * cb_template.setFont(this.font_normal);
             * cb_template.setBounds(120, 75, 80, 25);
             * panel.add(cb_template);
             */
        }
        else if (this.definition.hasCustomDisplayHeader())
        {
            JPanel panelDisplayHeader = this.definition.getCustomDisplayHeader();
            panelDisplayHeader.setBounds(40, y, panelDisplayHeader.getWidth(), panelDisplayHeader.getHeight());
            panel.add(panelDisplayHeader);

            y += panelDisplayHeader.getHeight();

            spaceBetweenTableAndFilter = 20;
        }

        this.table = this.definition.getJTable();

        table.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
                {
                    definition.editTableRow();
                }
            }

        });

        Rectangle r = this.definition.getTableSize(y + spaceBetweenTableAndFilter);

        JScrollPane scp = new JScrollPane(this.table);
        scp.setBounds(r);
        panel.add(scp);

        int pos_x = r.x + r.width + 20;
        int pos_y = r.y;

        int pic_size[] = { 22, 22 };
        // int pos_y = 0;

        for (int i = 0; i < this.definition.getButtonDefinitions().size(); i++)
        {
            ButtonDef bd = this.definition.getButtonDefinitions().get(i);

            if (bd instanceof LabelDef)
            {
                LabelDef ld = (LabelDef) bd;
                JLabel lab = ATSwingUtils.getLabel(ld.label_text, pos_x, pos_y, 120, 25, panel,
                    ATSwingUtils.FONT_NORMAL_BOLD);
                pos_y += 35;
            }
            else if (bd instanceof DividerDef)
            {
                pos_y += 20;
            }
            else
            {
                JButton b = ATSwingUtils.getButton("   " + bd.text, pos_x, pos_y, 120, 30, panel,
                    ATSwingUtils.FONT_NORMAL, bd.icon_name, bd.action, this, dataAccess, pic_size);
                b.setHorizontalAlignment(SwingConstants.LEFT);
                pos_y += 35;
            }
        }

        Dimension d = this.definition.getWindowSize();

        pos_x = d.width - 40 - 220 - 10;

        JButton b = ATSwingUtils.getButton("   " + i18nControl.getMessage("CLOSE"), pos_x, r.y + r.height + 20, 110, 30,
            panel, ATSwingUtils.FONT_NORMAL, "exit.png", "close", this, dataAccess, pic_size);
        b.setHorizontalAlignment(SwingConstants.LEFT);

        helpButton = ATSwingUtils.createHelpButtonByBounds(pos_x + 110 + 10, r.y + r.height + 20, 110, 30, panel,
            ATSwingUtils.FONT_NORMAL, dataAccess.getImagesRoot(), i18nControl);
        helpButton.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(helpButton);

        if (StringUtils.isNotBlank(this.getHelpId()))
        {
            dataAccess.enableHelp(this);
        }

        this.definition.additionalGUIInit(panel);

        this.setTitle(this.definition.getTitle());
        this.setResizable(false);

    }


    public int getSelectedObjectIndexFromTable()
    {
        if (table.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(this, i18nControl.getMessage("SELECT_ROW_FIRST"),
                i18nControl.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        return table.getSelectedRow();
    }


    /**
     * Get All Filter Values
     * 
     * @return
     */
    public String[] getAllFilterValues()
    {
        String[] filters = null;

        if (this.definition.hasFilter())
        {

            if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO)
            {
                filters = new String[1];
            }
            else if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_AND_TEXT
                    || this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_TWICE)
            {
                filters = new String[2];
            }

            // fix this if needed

            filters[0] = (String) this.cb_filter1.getSelectedItem();

            if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_AND_TEXT)
            {
                filters[1] = this.tf_filter.getText();
            }
            else if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_TWICE)
            {
                filters[1] = (String) this.cb_filter2.getSelectedItem();
            }

        }

        return filters;
    }


    private void processDefaultParameters()
    {

        if (this.definition.hasFilter())
        {

            if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO)
            {
                this.cb_filter1.setSelectedItem(this.definition.defaultParameters[0]);
            }
            else if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_AND_TEXT)
            {
                this.cb_filter1.setSelectedItem(this.definition.defaultParameters[0]);
            }
            else if (this.definition.getFilterType() == GUIListDefAbstract.FILTER_COMBO_TWICE)
            {
                this.cb_filter1.setSelectedItem(this.definition.defaultParameters[0]);
                this.cb_filter2.setSelectedItem(this.definition.defaultParameters[1]);
            }
        }

    }


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("close"))
        {
            this.dispose();
        }
        else
        {
            this.definition.doTableAction(action);
        }

    }


    public Component getComponent()
    {
        return this;
    }


    public JButton getHelpButton()
    {
        return this.helpButton;
    }


    public String getHelpId()
    {
        return this.definition.getHelpId();
    }


    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource().equals(this.cb_filter1))
        {
            String s = (String) e.getItem();

            if (!s.equals(sel_combo))
            {
                sel_combo = s;
                this.definition.setFilterCombo(s);
            }
        }
        else if (e.getSource().equals(this.cb_filter2))
        {
            String s = (String) e.getItem();

            if (!s.equals(sel_combo2))
            {
                sel_combo2 = s;
                this.definition.setFilterCombo_2(s);
            }
        }

    }


    public void changedUpdate(DocumentEvent e)
    {
    }


    public void insertUpdate(DocumentEvent e)
    {
        this.definition.setFilterText(this.tf_filter.getText());
    }


    public void removeUpdate(DocumentEvent e)
    {
        this.definition.setFilterText(this.tf_filter.getText());
    }


    /**
     * Get Table
     * 
     * @return
     */
    public JTable getTable()
    {
        return this.table;
    }


    public boolean isDataChanged()
    {
        return dataChanged;
    }


    public void setDataChanged(boolean dataChanged)
    {
        this.dataChanged = dataChanged;
    }
}
