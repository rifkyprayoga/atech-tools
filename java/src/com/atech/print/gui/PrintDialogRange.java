package com.atech.print.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.GregorianCalendar;

import javax.swing.*;

import com.atech.graphics.GuiAction;
import com.atech.graphics.GuiActionUtil;
import com.atech.graphics.components.dates.DateFromToComponent;
import com.atech.graphics.dialogs.ActionExceptionCatchDialog;
import com.atech.i18n.I18nControlAbstract;
import com.atech.print.engine.PrintProcessor;
import com.atech.print.engine.PrintRequester;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 * This file is part of ATech Tools library.
 * PrintingDialogRange - Dialog for Printing when we have a range of dates
 * (from, till)
 * Copyright (C) 2014 Andy (Aleksander) Rozman (Atech-Software)
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * For additional information about this project please visit our project site
 * on
 * http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * Filename: PrintingDialogRange
 * Description: Dialog for Printing
 *
 * @author Andy {andy@atech-software.com}
 */
public abstract class PrintDialogRange extends ActionExceptionCatchDialog implements PrintRequester
{

    private static final long serialVersionUID = 2693207247071685559L;

    protected I18nControlAbstract i18nControl = null;
    protected ATDataAccessAbstract dataAccess = null;
    protected PrintProcessor printProcessor = null;

    private boolean actionDone = false;

    protected JTextField tfName;
    protected JComboBox cbTemplate = null, cbTemplate2 = null;
    DateFromToComponent dateFromToComponent;

    protected GregorianCalendar gc = null;
    JButton helpButton;

    protected Font fontNormal, fontNormalBold;
    protected boolean enableHelp = true;
    // PrintDialogType type;

    int type;


    /**
     * Constructor
     *
     * @param frame
     * @param type
     * @param da
     * @param _enable_help
     */
    public PrintDialogRange(JFrame frame, int type, ATDataAccessAbstract da, boolean _enable_help)
    {
        this(frame, type, da, da.getI18nControlInstance(), _enable_help);
    }


    public PrintDialogRange(JFrame frame, int type, ATDataAccessAbstract dataAccess, I18nControlAbstract i18nControl,
            boolean _enable_help)
    {
        this(frame, type, dataAccess, i18nControl, _enable_help, true);
    }


    /**
     * Constructor
     *
     * @param frame
     * @param type
     * @param dataAccess
     * @param i18nControl
     * @param _enable_help
     */
    public PrintDialogRange(JFrame frame, int type, ATDataAccessAbstract dataAccess, I18nControlAbstract i18nControl,
            boolean _enable_help, boolean init)
    {
        super(frame, type, dataAccess, "printing_dialog");

        this.enableHelp = _enable_help;
        preInitGUI(dataAccess, i18nControl);
        this.type = type;

        if (init)
        {
            initDialog();
        }
    }


    public void initDialog()
    {
        initRange();
        this.cbTemplate.setSelectedIndex(type - 1);

        this.setVisible(true);
    }


    protected void preInitGUI(ATDataAccessAbstract dataAccess, I18nControlAbstract i18nControl)
    {
        this.setLayout(null);

        this.dataAccess = dataAccess;
        this.i18nControl = i18nControl;
        ATSwingUtils.initLibrary();

        fontNormal = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        fontNormalBold = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);

        gc = new GregorianCalendar();
        setTitle(i18nControl.getMessage(this.getPrintingTitle()));
    }


    protected void initRange()
    {
        setSize(440, 380);
        this.dataAccess.centerJDialog(this);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 440, 390);
        panel.setLayout(null);

        this.getContentPane().add(panel);

        JLabel label = new JLabel(i18nControl.getMessage(this.getPrintingTitle()));
        label.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 20, 430, 35);
        panel.add(label);

        label = new JLabel(i18nControl.getMessage("TYPE_OF_REPORT") + ":");
        label.setFont(this.fontNormalBold);
        label.setBounds(40, 65, 280, 25); // 75
        panel.add(label);

        cbTemplate = new JComboBox(getReportTypes());
        cbTemplate.setFont(this.fontNormal);
        cbTemplate.setBounds(40, 95, 240, 25); // 105
        panel.add(cbTemplate);

        int start_y = 145; // 155

        if (this.weHaveSecondaryType())
        {
            label = ATSwingUtils.getLabel(this.getSecondaryTypeDescription(), 40, start_y, 180, 25, panel,
                ATSwingUtils.FONT_NORMAL_BOLD);
            start_y += 25;

            cbTemplate2 = ATSwingUtils.getComboBox(this.getSecondaryTypes(), 40, start_y, 230, 25, panel,
                ATSwingUtils.FONT_NORMAL);
            start_y += 25;

        }

        dateFromToComponent = new DateFromToComponent(dataAccess, DateFromToComponent.ORIENTATION_VERTICAL, false, 40,
                10);
        dateFromToComponent.setBounds(40, start_y, 220, 25);
        panel.add(dateFromToComponent);

        JButton button = new JButton("   " + i18nControl.getMessage("OK"));
        // button.setFont(dataAccess.getFont(DataAccess.FONT_NORMAL));
        button.setActionCommand("ok");
        button.addActionListener(this);
        button.setIcon(ATSwingUtils.getImageIcon_22x22("ok.png", this, dataAccess));
        button.setBounds(25, start_y + 150, 120, 25); // 145
        panel.add(button);

        button = new JButton("   " + i18nControl.getMessage("CANCEL"));
        // button.setFont(dataAccess.getFont(DataAccess.FONT_NORMAL));
        button.setActionCommand("cancel");
        button.setIcon(ATSwingUtils.getImageIcon_22x22("cancel.png", this, dataAccess));
        button.addActionListener(this);
        button.setBounds(155, start_y + 150, 120, 25);
        panel.add(button);

        helpButton = ATSwingUtils.createHelpButtonByBounds(285, start_y + 150, 120, 25, this, ATSwingUtils.FONT_NORMAL,
            dataAccess);
        panel.add(helpButton);

        if (this.enableHelp)
            dataAccess.enableHelp(this);

        printProcessor = new PrintProcessor(i18nControl, this);

    }


    /**
     * Get From Date Object
     *
     * @return
     */
    public GregorianCalendar getFromDateObject()
    {
        return dateFromToComponent.getFromDateObject();
    }


    /**
     * Get From Date
     *
     * @return
     */
    public int getFromDate()
    {
        return dateFromToComponent.getFromDate();
    }


    /**
     * Get To Date Object
     *
     * @return
     */
    public GregorianCalendar getToDateObject()
    {
        return dateFromToComponent.getTillDateObject();
    }


    /**
     * Get To Date
     *
     * @return
     */
    public int getToDate()
    {
        return dateFromToComponent.getTillDate();
    }


    /**
     * Get Printing Title
     *
     * @return
     */
    public String getPrintingTitle()
    {
        return "PRINTING";
    }

    GuiAction lastAction = new GuiAction();


    /**
     * performAction
     */
    @Override
    public void performAction(ActionEvent e) throws Exception
    {
        String action = e.getActionCommand();
        if (action.equals("cancel"))
        {
            actionDone = false;
            this.dispose();
        }
        else if (action.equals("ok"))
        {
            lastAction = GuiActionUtil.checkLastActionTime(lastAction);

            if (!lastAction.isActionSuccess())
            {
                return;
            }

            this.startPrintingAction();
        }

    }


    public void displayPDF(String name) throws Exception
    {
        printProcessor.displayPDF(name);
    }


    /**
     * Was Action Successful
     *
     * @return true if action was successful (dialog closed with OK)
     */
    public boolean actionSuccessful()
    {
        return actionDone;
    }


    /**
     * Get Action Results
     *
     * @return String array of results
     */
    public String[] getActionResults()
    {
        String[] res = new String[3];

        if (actionDone)
            res[0] = "1";
        else
            res[0] = "0";

        res[1] = this.tfName.getText();
        res[2] = this.cbTemplate.getSelectedItem().toString();

        return res;
    }


    /**
     * We have Secondary Type choice
     *
     * @return
     */
    public boolean weHaveSecondaryType()
    {
        return false;
    }


    /**
     * Get Secondary Types Description
     *
     * @return
     */
    public String getSecondaryTypeDescription()
    {
        return null;
    }


    /**
     * Get Secondary Types
     *
     * @return
     */
    public String[] getSecondaryTypes()
    {
        return null;
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
        return this.helpButton;
    }


    /**
     * getObject
     */
    @Override
    public Object getObject()
    {
        return this;
    }


    /**
     * getHelpId - get id for Help
     */
    public abstract String getHelpId();


    /**
     * Get Pdf Viewer (path to software)
     *
     * @return
     */
    public abstract String getExternalPdfViewer();


    /**
     * Get Report Types
     *
     * @return
     */
    public abstract String[] getReportTypes();


    /**
     * Start Printing Action.
     *
     * @throws Exception
     */
    public abstract void startPrintingAction() throws Exception;


    /**
     * Get Pdf Viewer parameters. If you want name of file we are reading in
     * this
     * parameters, you need to add %PDF_FILE% variable into string. This one is
     * then resolved.
     *
     * @return
     */
    public abstract String getExternalPdfViewerParameters();


    /**
     * Is External PdfViewer Activated
     * Per default we use internal PdfViewer, but user can also use external
     * PdfViewer if he wants.
     */
    public abstract boolean isExternalPdfViewerActivated();


    /**
     * Disable setting of Look And Feel For Internal Pdf Viewer.
     * 
     * @return
     */
    public abstract boolean disableLookAndFeelSettingForInternalPdfViewer();

}
