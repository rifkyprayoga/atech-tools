package com.atech.print.gui;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *
 *  PrintingDialogMonth - Monthly printing dialog (deprecated)
 *  Copyright (C) 2014  Andy (Aleksander) Rozman (Atech-Software)
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
 */


public abstract class PrintDialogMonth extends PrintDialogRange
{

    private static final long serialVersionUID = -5516522343993408360L;
    JSpinner spinnerYear = null, spinnerMonth = null;

    /**
     * Constructor
     *
     * @param frame
     * @param type
     * @param da
     */
    public PrintDialogMonth(JFrame frame, int type, ATDataAccessAbstract da)
    {
        super(frame, type, da, false);

        this.preInitGUI(da, da.getI18nControlInstance());
        this.init();
        this.cbTemplate.setSelectedIndex(type - 1);
        this.setVisible(true);
    }

    private void init()
    {
        setSize(350, 320);

        this.dataAccess.centerJDialog(this);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 350, 350);
        panel.setLayout(null);

        this.getContentPane().add(panel);

        JLabel label = new JLabel(i18nControl.getMessage("PRINTING"));
        label.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 20, 350, 35);
        panel.add(label);

        label = new JLabel(i18nControl.getMessage("TYPE_OF_REPORT") + ":");
        label.setFont(this.fontNormalBold);
        label.setBounds(40, 75, 280, 25);
        panel.add(label);

        cbTemplate = new JComboBox(this.getReportTypes());
        cbTemplate.setFont(this.fontNormal);
        cbTemplate.setBounds(40, 105, 230, 25);
        panel.add(cbTemplate);

        int year = gc.get(Calendar.YEAR);
        int month = gc.get(Calendar.MONTH) + 1;

        label = new JLabel(i18nControl.getMessage("SELECT_YEAR_AND_MONTH") + ":");
        label.setFont(this.fontNormalBold);
        label.setBounds(40, 155, 180, 25);
        panel.add(label);

        spinnerYear = new JSpinner();
        SpinnerNumberModel model = new SpinnerNumberModel(year, 1970, year + 1, 1);
        spinnerYear.setModel(model);
        spinnerYear.setEditor(new JSpinner.NumberEditor(spinnerYear, "#"));
        spinnerYear.setFont(this.fontNormal);
        spinnerYear.setBounds(40, 185, 60, 25);
        panel.add(spinnerYear);

        spinnerMonth = new JSpinner();
        SpinnerNumberModel model_m = new SpinnerNumberModel(month, 1, 12, 1);
        spinnerMonth.setModel(model_m);
        // sl_month.setEditor(new JSpinner.NumberEditor(sl_month, "#"));
        spinnerMonth.setFont(this.fontNormal);
        spinnerMonth.setBounds(120, 185, 40, 25);
        panel.add(spinnerMonth);

        JButton button = new JButton("   " + i18nControl.getMessage("OK"));
        // button.setFont(m_da.getFont(DataAccess.FONT_NORMAL));
        button.setActionCommand("ok");
        button.addActionListener(this);
        button.setIcon(this.dataAccess.getImageIcon_22x22("ok.png", this));
        button.setBounds(40, 240, 125, 25);
        panel.add(button);

        button = new JButton("   " + i18nControl.getMessage("CANCEL"));
        // button.setFont(m_da.getFont(DataAccess.FONT_NORMAL));
        button.setActionCommand("cancel");
        button.setIcon(this.dataAccess.getImageIcon_22x22("cancel.png", this));
        button.addActionListener(this);
        button.setBounds(175, 240, 125, 25);
        panel.add(button);

        helpButton = this.dataAccess.createHelpButtonByBounds(185, 210, 115, 25, this);
        panel.add(helpButton);

        if (this.enableHelp)
        {
            this.dataAccess.enableHelp(this);
        }

    }

}


