package com.atech.i18n.tool.simple.util;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.atech.graphics.components.about.AboutCustomPanel;
import com.atech.graphics.components.about.AboutDialog;
import com.atech.graphics.components.about.LicenceInfo;
import com.atech.i18n.tool.simple.TranslationTool;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  TTAboutDialog - About dialog
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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

public class TTAboutDialog extends AboutDialog
{

    private static final long serialVersionUID = 586495485605943204L;
    DataAccessTT m_da = DataAccessTT.getInstance();

    /**
     * Constructor 
     * 
     * @param parent
     * @param da
     */
    public TTAboutDialog(JFrame parent)
    {
        super(parent, true, DataAccessTT.getInstance().getI18nControlInstance());

        // this.m_da = da;

        // licence
        this.setLicenceType(LicenceInfo.LICENCE_GPL_v2_0);

        // credits
        // this.setCredits(m_da.getPlugInDevelopers());

        // set display system properties
        this.setDisplayProperties(true);

        // libraries
        // this.setLibraries(m_da.getPlugInLibraries());

        // features
        // this.setFeatures(m_da.getPlugInFeatures());

        // custom page
        createCustomTab();

        // title
        this.setTitle("Simple Translation Tool About...");

        // finalize
        this.createAbout();
        this.setSize(500, 400);

        this.showAbout();
    }

    /*
     * public void initCustom()
     * {
     * System.out.println("init Custom");
     * this.about_image = "/icons/t_asc_dex.gif";
     * }
     */

    private void createCustomTab()
    {

        AboutCustomPanel acp = new AboutCustomPanel(m_ic);
        acp.setTabName(m_ic.getMessage("ABOUT"));
        acp.setLayout(new BoxLayout(acp, BoxLayout.PAGE_AXIS));

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());

        int[] sz = { 240, 160 };

        JLabel l = new JLabel(ATSwingUtils.getImageIcon("translation_about.jpg", sz[0], sz[1], this, m_da));
        p1.add(l, BorderLayout.CENTER);

        JLabel l2 = new JLabel();
        l2.setPreferredSize(new Dimension(100, 10));

        p1.add(l2, BorderLayout.SOUTH);
        acp.add(p1);

        JEditorPane jEditorPaneAbout = new javax.swing.JEditorPane();
        jEditorPaneAbout.setBackground(new java.awt.Color(204, 204, 204));
        // jEditorPaneAbout.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jEditorPaneAbout.setEditable(false);
        jEditorPaneAbout.setMinimumSize(new java.awt.Dimension(104, 90));
        jEditorPaneAbout.setOpaque(false);
        jEditorPaneAbout.setPreferredSize(new java.awt.Dimension(104, 90));

        jEditorPaneAbout.setContentType("text/html");
        jEditorPaneAbout.setText("<HTML><body><font face=\"SansSerif\" size=\"3\"><center><b>"
                + "Simple Translation Tool v" + TranslationTool.m_version
                + "</b><br>&nbsp;&nbsp;(c) Copyright 2009 by<br>"
                + "Andy (Aleksander) Rozman (andy@atech-software.com)<br>"
                + "<br><A HREF=\"http://atech-tools.sourceforge.net/\">http://atech-tools.sourceforge.net/</A><br>"
                + m_ic.getMessage("LICENCE") + " GPL v2.0<br></font></body></html>");

        acp.add(jEditorPaneAbout); // , BoxLayout.PAGE_AXIS);

        this.addCustomPanel(AboutDialog.PLACEMENT_BEFORE_STATIC_TABS, acp);

    }

}
