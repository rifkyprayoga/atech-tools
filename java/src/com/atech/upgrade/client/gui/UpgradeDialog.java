package com.atech.upgrade.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.help.ComponentHelpCapable;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.update.client.data.UpdateSettings;
import com.atech.update.client.handler.UpgradeHandlerInterface;
import com.atech.update.client.handler.UpgradeHandlerV3;
import com.atech.update.config.ComponentEntryStatus;
import com.atech.update.config.UpdateConfiguration;
import com.atech.upgrade.client.data.UpgradeApplicationContext;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;
import com.atech.utils.xml.XmlUtil;

/**
 *  This file is part of ATech Tools library.
 *  
 *  UpdateDialog - Dialog for updates
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
 *  @author Andy {andy@atech-software.com}
 *
*/

// NOT FULLY IMPLEMENTED YET.

// NOTE !!!!!!!!!!!!!!!!!!!!!
// There are currently two versions of UpgradeSystem V2 and V3. Both are
// incomplete though... Work started
// on new simplified version (V3) and thus version 2 will be discared. When
// implementation of V3 is finished
// and it works accordimg to plan V2 will be discarded, but so far will have
// both of them.

public class UpgradeDialog extends JDialog implements ActionListener, HelpCapable, ComponentHelpCapable
{

    private static final long serialVersionUID = -8822530996424234341L;

    private static Log log = LogFactory.getLog(UpgradeDialog.class);
    UpgradeSystemModel model = null;

    ATDataAccessAbstract dataAccess = null;
    I18nControlAbstract i18nControl = null;

    // JTabbedPane tabbedPane;
    // ArrayList<ComponentInterface> list;

    /*
     * Globaly used variables
     */
    JPanel panel;
    JLabel label, labelTitle, lblStatus;
    JButton btnCheck, btnUpdate, button, help_button;
    Font font_big, font_normal, font_normal_b;
    JTable table;

    UpdateSettings update_settings = null;

    // new

    int m_error = 0;

    UpdateConfiguration update_config = null;

    int lastAction = 0; // no event

    long next_version = 0L;

    UpgradeHandlerInterface upgradeHandler;

    private long nextVersion;
    private JButton btnNotes;


    /**
     * Constructor
     * 
     * @param parent
     * @param uconf
     * @param da
     */
    public UpgradeDialog(JDialog parent, UpdateConfiguration uconf, UpgradeApplicationContext upgradeContext,
            ATDataAccessAbstract da)
    {
        super(parent, "", true);
        dataAccess = da;
        i18nControl = dataAccess.getI18nControlInstance();

        this.update_config = uconf;

        initHandler();

        init();
    }


    private void initHandler()
    {
        if (this.update_config.isConfigurationValid())
        {
            // if (this.update_config.db_update_site_version == 3)
            {
                this.upgradeHandler = UpgradeHandlerV3.createInstance(this, this.update_config, this.dataAccess);
            }
            // else
            // {
            // this.upgradeHandler = new UpgradeHandlerV2(this,
            // this.update_config, this.dataAccess);
            // }
        }
    }


    public UpdateSettings getUpdateSettings()
    {
        return this.update_settings;
    }


    /**
     * Constructor
     * 
     * @param parent
     * @param uconf
     * @param da
     */
    public UpgradeDialog(JFrame parent, UpdateConfiguration uconf, UpgradeApplicationContext upgradeContext,
            ATDataAccessAbstract da)
    {
        super(parent, "", true);

        dataAccess = da;
        i18nControl = dataAccess.getI18nControlInstance();

        this.update_config = uconf;

        initHandler();
        init();
    }


    /**
     * Constructor
     * 
     * @param parent
     * @param da
     */
    public UpgradeDialog(JFrame parent, UpgradeApplicationContext upgradeContext, ATDataAccessAbstract da)
    {
        this(parent, da.getUpdateConfiguration(), upgradeContext, da);
    }


    public void init()
    {
        ATSwingUtils.initLibrary();
        this.setBounds(130, 50, 650, 550); // 360

        font_big = ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD);
        font_normal = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        font_normal_b = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);

        this.cmdUpdate();

        this.setResizable(false);
        ATSwingUtils.centerJDialog(this, dataAccess.getParent());

        this.dataAccess.addComponent(this);

        update_settings = new UpdateSettings();
    }


    /**
     *   Displays title for dialog
     */
    public void showTitle()
    {
        // labelTitle
        String ev = i18nControl.getMessage("UPDATE_APPLICATION");

        this.setTitle(ev);
        labelTitle.setText(ev);

    }


    /**
     *   Displays GUI
     */
    public void cmdUpdate()
    {
        // FIXME

        // - removed components
        // - new components

        Container dgPane = this.getContentPane();

        ATSwingUtils.initLibrary();

        panel = new JPanel();
        panel.setBounds(5, 5, 620, 550);
        panel.setLayout(null); // 600 450
        dgPane.add(panel);

        labelTitle = new JLabel();
        labelTitle.setBounds(30, 20, 450, 40);
        labelTitle.setFont(font_big);
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelTitle, null);

        showTitle();

        ATSwingUtils.getLabel(i18nControl.getMessage("SERVER_STATUS") + ":", 30, 70, 120, 25, panel,
            ATSwingUtils.FONT_NORMAL_BOLD);

        this.lblStatus = ATSwingUtils.getLabel(i18nControl.getMessage("NO_STATUS"), 160, 70, 320, 25, panel,
            ATSwingUtils.FONT_NORMAL);
        this.lblStatus.setBackground(Color.blue);

        ATSwingUtils.getLabel(i18nControl.getMessage("LEGEND"), 30, 435, 100, 30, panel, ATSwingUtils.FONT_NORMAL);

        int[] x_kors = { 105, 200, 350, 470, 105 };
        int[] widths = { 100, 150, 150, 200, 100 };

        ComponentEntryStatus statuses[] = { ComponentEntryStatus.WillBeUpdated, ComponentEntryStatus.CorrectVersion, //
                                            ComponentEntryStatus.New, //
                                            ComponentEntryStatus.Unknown, //
                                            ComponentEntryStatus.Removed };

        // String[] pictures = { "dot_green.gif", "led_gray.gif",
        // "dot_orange.gif", "dot_blue.gif", "dot_red.gif" };
        String[] leg_label = { "  " + i18nControl.getMessage("NEWEST"), "  " + i18nControl.getMessage("NOT_UPDATED"),
                               "  " + i18nControl.getMessage("NEW"), "  " + i18nControl.getMessage("UNKNOWN_STATUS"),
                               " " + i18nControl.getMessage("REMOVED") };
        int y_kor = 440;
        for (int i = 0; i < x_kors.length; i++)
        {

            ComponentEntryStatus ces = statuses[i];

            label = new JLabel(i18nControl.getMessage(ces.getI18nKeyword()));

            ImageIcon imageIcon = null;

            if (ces == ComponentEntryStatus.Unknown)
            {
                imageIcon = ATSwingUtils.getImageIcon("/icons/", statuses[i].getIconName(), 14, 14, this);
            }
            else
            {
                imageIcon = ATSwingUtils.getImageIcon("/icons/", statuses[i].getIconName(), this);
            }
            ces.setImageIcon(imageIcon);

            label.setIcon(imageIcon);

            if (i == 4)
            {
                y_kor = 470;
            }

            label.setBounds(x_kors[i], y_kor, widths[i], 25);
            panel.add(label);
        }

        this.model = new UpgradeSystemModel(this.update_config, dataAccess);

        table = new JTable(this.model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDoubleBuffered(true);

        UpgradeTableCellRenderer utcr = new UpgradeTableCellRenderer(dataAccess);

        TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < model.getColumnCount(); i++)
        {
            cm.getColumn(i).setCellRenderer(utcr);
            cm.getColumn(i).setPreferredWidth(model.getColumnWidth(i, 580));
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 125, 580, 310);
        panel.add(scroll);
        scroll.repaint();

        scroll.updateUI();

        this.btnCheck = ATSwingUtils.getButton("   " + i18nControl.getMessage("CHECK_SERVER"), 455, 30, 150, 25, panel, //
            ATSwingUtils.FONT_NORMAL, "up_down_question.png", "check_server", this, dataAccess, new int[] { 22, 22 });

        this.btnUpdate = ATSwingUtils.getButton("   " + i18nControl.getMessage("RUN_UPDATE"), 455, 60, 150, 25, panel, //
            ATSwingUtils.FONT_NORMAL, //
            "download.png", "run_update", this, dataAccess, new int[] { 22, 22 });
        this.btnUpdate.setEnabled(false);

        this.btnNotes = ATSwingUtils.getButton("   " + i18nControl.getMessage("NOTES") + "...", 455, 90, 150, 25, panel, //
            ATSwingUtils.FONT_NORMAL, "download.png", "notes", this, dataAccess, new int[] { 22, 22 });

        this.btnNotes.setEnabled(false);

        ATSwingUtils.getButton("   " + i18nControl.getMessage("CLOSE"), 350, 475, 120, 25, panel, //
            ATSwingUtils.FONT_NORMAL, "cancel.png", "close", this, dataAccess, new int[] { 22, 22 });

        this.help_button = ATSwingUtils.createHelpButtonByBounds(485, 475, 120, 25, this, ATSwingUtils.FONT_NORMAL,
            dataAccess);

        panel.add(help_button);

        checkConfiguration();

    }


    private void checkConfiguration()
    {
        if (!this.update_config.isConfigurationValid())
        {
            this.setStatusText(i18nControl.getMessage("AT_UPD_ERR_CONFIGURATION_INVALID"));
            this.btnCheck.setEnabled(false);
        }
    }


    public void setStatusText(String text)
    {
        this.lblStatus.setText(text);
    }


    public void setStatusTooltip(String text)
    {
        this.lblStatus.setToolTipText(text);
    }


    /**
     * Show Dialog
     */
    public void showDialog()
    {
        this.setVisible(true);
    }


    /**
     *  Action Listener
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        // if (action.equals("ok"))
        // {
        // lastAction = 0;
        // }
        // else

        if (action.equals("run_update"))
        {
            this.upgradeHandler.runUpdate();
        }
        else if (action.equals("check_server"))
        {
            this.upgradeHandler.checkServer();
        }
        else if (action.equals("close"))
        {
            lastAction = 0;
            this.dataAccess.removeComponent(this);
            this.dispose();
        }
        else if (action.equals("notes"))
        {
            new UpgradeNewsDialog(this, dataAccess);
        }

        /*
         * else if (action.equals("help"))
         * {
         * this.dispose();
         * }
         */
        else
        {
            System.out.println("UpdateDialog::Unknown command: " + action);
        }

    }


    /**
     *  Gets info if action was performed.
     * 
     *  @return true if action was done, false if not.
     */
    public boolean wasAction()
    {
        if (lastAction == 1)
            return true;
        else
            return false;
    }


    /**
     *  Returns object saved
     * 
     *  @return object of type of Object
     */
    public Object getObject()
    {
        return null;
    }

    /**
     * Help Id
     */
    private String help_id;


    /** 
     * enableHelp
     */
    public void enableHelp(String _help_id)
    {
        this.help_id = _help_id;
        dataAccess.enableHelp(this);
    }


    /** 
     * getComponent
     */
    public Component getComponent()
    {
        return this;
    }


    /** 
     * getHelpButton
     */
    public JButton getHelpButton()
    {
        return this.help_button;
    }


    /** 
     * getHelpId
     */
    public String getHelpId()
    {
        return this.help_id;
    }


    /**
     * Update System v2.1
     * 
     * + 1. Check Update version X
     * + 2. Display possible updates 
     * + 3. Get New Version Xml and display updates in list (activate button)  X
     * 4. Get detailed update data x
     * 4. Download files X
     * 5. Apply Update   
     * 
     * 
     * 2. Update::  Check if db_updatable for version of application
     * 
     */

    @SuppressWarnings("unused")
    private String getParameter(String parameter, String text)
    {
        String start_tag = "<" + parameter + ">";

        if (!text.contains(start_tag))
            return "";

        String end_tag = "</" + parameter + ">";

        String par = text.substring(text.indexOf(start_tag) + start_tag.length(), text.indexOf(end_tag));

        return par;
    }


    public void processDetailsData(String xml)
    {
        try
        {
            XmlUtil xu = new XmlUtil(xml);
            this.model.updateServerExtendedSettings(xu.getNodes("update_detailed_file/components/component"));
        }
        catch (Exception ex)
        {
            log.error("processDetailsData(). Ex.: " + ex, ex);
        }

        // System.out.println("Return data: \n" + xml + "\n");

    }


    public long getNextVersion()
    {
        return nextVersion;
    }


    public void setNextVersion(long nextVersion)
    {
        this.nextVersion = nextVersion;
    }


    public UpgradeSystemModel getModel()
    {
        return this.model;
    }


    public JButton getUpdateButton()
    {
        return this.btnUpdate;
    }


    public JButton getBtnNotes()
    {
        return btnNotes;
    }


    public void setBtnNotes(JButton btnNotes)
    {
        this.btnNotes = btnNotes;
    }
}
