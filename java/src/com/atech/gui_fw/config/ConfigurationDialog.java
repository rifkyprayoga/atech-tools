package com.atech.gui_fw.config;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ConfigDbPanel - This class is for setting database
 *  
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
 *  @author Andy
 *
*/

/**
 * Application: GGC - GNU Gluco Control
 * 
 * See AUTHORS for copyright information.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Filename: PropertiesDialog Description: Dialog for setting properties for
 * application.
 * 
 * Author: andyrozman {andy@atech-software.com}
 */

public class ConfigurationDialog extends JDialog implements ListSelectionListener, ActionListener, HelpCapable
{

    AbstractConfigurationContext m_acc = null;
    private ATDataAccessAbstract m_da; // = DataAccess.getInstance();
    private I18nControlAbstract m_ic = null; // DataAccess.getInstance().getI18nControlInstance();

    private JList list = null;
    private JPanel prefPane;
    JButton help_button;
    private boolean help_enabled = false;

    private ArrayList<JPanel> panels = null;
    private Hashtable<String, String> panel_id = null;

    int current_index = 0;
    boolean ok_action = false;


    /**
     * Config types
     */
    /*
     * public String config_types[] = { m_ic.getMessage("MODE"),
     * m_ic.getMessage("GENERAL"), m_ic.getMessage("MEDICAL_DATA"),
     * m_ic.getMessage("COLORS_AND_FONTS"),
     * m_ic.getMessage("RENDERING_QUALITY"), //
     * m_ic.getMessage("METER_CONFIGURATION"), m_ic.getMessage("PRINTING"),
     * m_ic.getMessage("LANGUAGE") };
     */

    public ConfigurationDialog(AbstractConfigurationContext acc)
    {
        this(acc, acc.getDataAccessInstance().getMainParent(), acc.getDataAccessInstance());
    }


    /**
     * Constructor
     * 
     * @param acc
     * 
     * @param da
     */
    public ConfigurationDialog(AbstractConfigurationContext acc, JFrame frame, ATDataAccessAbstract da)
    {
        super(frame, "", true);

        this.m_da = da;
        this.m_ic = m_da.getI18nControlInstance();
        this.m_acc = acc;

        this.help_enabled = acc.app_ctx.isHelpEnabled();

        this.m_da.addComponent(this);
        ATSwingUtils.initLibrary();

        /*
         * Rectangle rec = parent.getBounds(); int x = rec.x + (rec.width/2);
         * int y = rec.y + (rec.height/2);
         * setBounds(x-320, y-240, 640, 480);
         */
        setSize(640, 480);
        setTitle(m_ic.getMessage("PREFERENCES"));
        ATSwingUtils.centerJDialog(this, frame);

        help_button = ATSwingUtils.createHelpButtonBySize(120, 25, this, this.m_da.getImagesRoot(), m_ic);
        createPanels();

        init();
        selectPanel(0);
        this.setResizable(false);
        this.setVisible(true);
    }


    private void init()
    {
        Dimension dim = new Dimension(120, 25);

        // createNodes();

        /*
         * private JTree prefTree; private DefaultTreeModel prefTreeModel;
         * private JScrollPane prefTreePane;
         */

        // Configuration icons
        /*
         * public ImageIcon config_icons[] = { new
         * ImageIcon("images/cfg_db.gif"), new ImageIcon("images/cfg_look.gif"),
         * new ImageIcon("images/cfg_myparish.gif"), new
         * ImageIcon("images/cfg_masses.gif"), new
         * ImageIcon("images/cfg_users.gif"), new
         * ImageIcon("images/cfg_lang.gif"), new
         * ImageIcon("images/cfg_web.gif"), null };
         * public String config_types[] = { m_ic.getMessage("GENERAL"),
         * m_ic.getMessage("MEDICAL_DATA"), m_ic.getMessage("COLORS_AND_FONTS"),
         * m_ic.getMessage("RENDERING_QUALITY"),
         * m_ic.getMessage("METER_CONFIGURATION"), m_ic.getMessage("PRINTING")
         * };
         */
        System.out.println("this.m_acc.getConfigOptions(): " + this.m_acc.getConfigOptions());
        m_da.debugArray(this.m_acc.getConfigOptions(), "config_options");

        list = new JList(this.m_acc.getConfigOptions());
        list.addListSelectionListener(this);
        ConfigCellRenderer renderer = new ConfigCellRenderer(this.m_acc);
        renderer.setPreferredSize(new Dimension(100, 75));
        list.setCellRenderer(renderer);
        list.setSelectedIndex(0);

        System.out.println("this.m_acc.getConfigOptions(): " + this.m_acc.getConfigOptions());

        /*
         * DefaultTreeModel prefTreeModel = new DefaultTreeModel(prefNode);
         * prefTree = new JTree(prefTreeModel);
         * prefTree.putClientProperty("JTree.lineStyle", "Angled");
         * prefTree.addTreeSelectionListener(new TreeSelectionListener() {
         * public void valueChanged(TreeSelectionEvent e) {
         * DefaultMutableTreeNode selectedNode =
         * (DefaultMutableTreeNode)prefTree.getLastSelectedPathComponent();
         * if (selectedNode == null) return;
         * selectPanel(selectedNode.toString()); } });
         * JScrollPane prefTreePane = new JScrollPane(prefTree,
         * ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
         * ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
         */

        JScrollPane prefTreePane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        prefPane = new JPanel(new BorderLayout());

        // set the buttons up...
        JButton okButton = new JButton("   " + m_ic.getMessage("OK"));
        okButton.setPreferredSize(dim);
        okButton.setIcon(ATSwingUtils.getImageIcon_22x22("ok.png", this, m_da));
        okButton.setActionCommand("ok");
        okButton.addActionListener(this);

        JButton cancelButton = new JButton("   " + m_ic.getMessage("CANCEL"));
        cancelButton.setPreferredSize(dim);
        cancelButton.setIcon(ATSwingUtils.getImageIcon_22x22("cancel.png", this, m_da));
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);

        JButton applyButton = new JButton("   " + m_ic.getMessage("APPLY"));
        applyButton.setPreferredSize(dim);
        applyButton.setIcon(ATSwingUtils.getImageIcon_22x22("flash.png", this, m_da));
        applyButton.setActionCommand("apply");
        applyButton.addActionListener(this);

        // ...and align them in a row at the buttom.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);

        buttonPanel.add(help_button);

        prefPane.add(buttonPanel, BorderLayout.SOUTH);
        prefPane.add(panels.get(0), BorderLayout.CENTER);

        getContentPane().add(prefTreePane, BorderLayout.WEST);
        // getContentPane().add(list, BorderLayout.WEST);
        getContentPane().add(prefPane, BorderLayout.CENTER);
    }

    // ---
    // --- NODES and PANELS
    // --- (if you add node and panel for configuration options, please change
    // --- following methods and add values)
    // ---

    /*
     * public void createNodes() { prefNode = new
     * DefaultMutableTreeNode(m_ic.getMessage("PREFERENCES")); prefNode.add(new
     * DefaultMutableTreeNode(m_ic.getMessage("GENERAL"))); prefNode.add(new
     * DefaultMutableTreeNode(m_ic.getMessage("MEDICAL_DATA")));
     * prefNode.add(new
     * DefaultMutableTreeNode(m_ic.getMessage("COLORS_AND_FONTS")));
     * prefNode.add(new
     * DefaultMutableTreeNode(m_ic.getMessage("RENDERING_QUALITY")));
     * //DefaultMutableTreeNode dataNode = new
     * DefaultMutableTreeNode(m_ic.getMessage("DATA_STORING"));
     * //dataNode.add(new
     * DefaultMutableTreeNode(m_ic.getMessage("MYSQL_SETUP")));
     * //dataNode.add(new
     * DefaultMutableTreeNode(m_ic.getMessage("TEXTFILE_SETUP")));
     * //prefNode.add(dataNode); prefNode.add(new
     * DefaultMutableTreeNode(m_ic.getMessage("METER_CONFIGURATION")));
     * //prefNode.add(new DefaultMutableTreeNode(m_ic.getMessage("NUTRITION")));
     * prefNode.add(new DefaultMutableTreeNode(m_ic.getMessage("PRINTING")));
     * /*DefaultMutableTreeNode meterNode = new
     * DefaultMutableTreeNode("Meters"); meterNode.add(new
     * DefaultMutableTreeNode("Glucocard")); prefNode.add(meterNode);
     */
    // }

    // private int PANEL_MAIN = 0;
    private int PANEL_MODE = 0;
    private int PANEL_GENERAL = 1;
    private int PANEL_MEDICAL_DATA = 2;
    private int PANEL_COLORS = 3;
    private int PANEL_RENDERING = 4;
    private int PANEL_PRINTING = 5;
    private int PANEL_LANGUAGE = 6;


    // private int PANEL_METER = 4;

    /**
     * Create Panels
     */
    public void createPanels()
    {
        // Each node must have a panel, and panel numbers must be as they are
        // added
        // to. You must add panels in same order as you add, nodes.

        panels = new ArrayList<JPanel>();
        panel_id = new Hashtable<String, String>();

        this.m_acc.createPanels(panels, panel_id);

        /*
         * //addPanel(m_ic.getMessage("PREFERENCES"), this.PANEL_MAIN, new
         * PrefMainPane()); addPanel(m_ic.getMessage("MODE"), PANEL_MODE, new
         * PrefModePane(this)); addPanel(m_ic.getMessage("GENERAL"),
         * this.PANEL_GENERAL, new PrefGeneralPane(this));
         * addPanel(m_ic.getMessage("MEDICAL_DATA"), this.PANEL_MEDICAL_DATA,
         * new PrefMedicalDataPane(this));
         * addPanel(m_ic.getMessage("COLORS_AND_FONTS"), PANEL_COLORS, new
         * PrefFontsAndColorPane(this));
         * addPanel(m_ic.getMessage("RENDERING_QUALITY"), PANEL_RENDERING, new
         * PrefRenderingQualityPane(this));
         * addPanel(m_ic.getMessage("PRINTING"), PANEL_PRINTING, new
         * PrefPrintingPane(this)); addPanel(m_ic.getMessage("LANGUAGE"),
         * PANEL_LANGUAGE, new PrefLanguagePane(this)); //
         * addPanel(m_ic.getMessage("METER_CONFIGURATION"), PANEL_METER, new
         * PrefMeterConfPane(this));
         */
    }


    // ---
    // --- End
    // ---

    /**
     * Select Panel (string)
     * 
     * @param s
     */
    public void selectPanel(String s)
    {

        if (!panel_id.containsKey(s))
        {
            System.out.println("No such panel: " + s);
            return;
        }

        String id = panel_id.get(s);

        prefPane.remove(1);
        prefPane.add(panels.get(Integer.parseInt(id)), BorderLayout.CENTER);
        selected_panel = panels.get(Integer.parseInt(id));
        prefPane.invalidate();
        prefPane.validate();
        prefPane.repaint();

        if (this.help_enabled)
        {
            m_da.enableHelp(this);
        }
    }

    JPanel selected_panel = null;


    /**
     * Select Panel (int)
     * 
     * @param index
     */
    public void selectPanel(int index)
    {
        /*
         * if (!panel_id.containsKey(s)) { System.out.println("No such panel: "
         * + s); return; }
         * String id = panel_id.get(s);
         */

        prefPane.remove(1);
        prefPane.add(panels.get(index), BorderLayout.CENTER);
        selected_panel = panels.get(index);
        prefPane.invalidate();
        prefPane.validate();
        prefPane.repaint();

        if (this.help_enabled)
        {
            m_da.enableHelp(this);
        }
    }


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("ok"))
        {
            save();
            ok_action = true;
            this.m_da.removeComponent(this);
            this.dispose();
        }
        else if (action.equals("cancel"))
        {
            reset();
            this.m_da.removeComponent(this);
            this.dispose();
        }
        else if (action.equals("apply"))
        {
            save();
        }
        else
        {
            System.out.println("PropertiesFrame: Unknown command: " + action);
        }

    }


    /**
     * Was Action Successful
     * 
     * @return true if action was successful (dialog closed with OK)
     */
    public boolean actionSuccessful()
    {
        return ok_action;
    }


    private void save()
    {
        for (int i = 0; i < panels.size(); i++)
        {
            AbstractConfigPanel pn = (AbstractConfigPanel) panels.get(i);
            pn.saveConfig();
        }

        // dataAccess.getSettings().save();

        this.m_acc.saveSettings();

        /*
         * if (dataAccess.getDbConfig().hasChanged()) {
         * dataAccess.getDbConfig().saveConfig(); }
         */
    }


    private void reset()
    {
        this.m_acc.resetSettings();
    }


    /**
     * Called whenever the value of the selection changes.
     * 
     * @param e
     *            the event that characterizes the change.
     */
    public void valueChanged(ListSelectionEvent e)
    {
        if (current_index != list.getSelectedIndex())
        {
            current_index = list.getSelectedIndex();
            selectPanel(current_index);
            // System.out.println(list.getSelectedValue());
        }
    }


    // ****************************************************************
    // ****** HelpCapable Implementation *****
    // ****************************************************************

    /**
     * getComponent - get component to which to attach help context
     */
    public Component getComponent()
    {
        return this.getRootPane();
    }


    /**
     * getHelpButton - get Help button
     */
    public JButton getHelpButton()
    {
        return this.help_button;
    }


    /**
     * getHelpId - get id for Help
     */
    public String getHelpId()
    {
        return ((HelpCapable) this.selected_panel).getHelpId();
    }

}
