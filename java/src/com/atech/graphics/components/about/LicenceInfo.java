package com.atech.graphics.components.about;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.atech.i18n.I18nControlAbstract;

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

public class LicenceInfo extends AboutPanel
{

    private static final long serialVersionUID = 674574877740779181L;

    /**
     * The licence_text.
     */
    String licence_text = null;

    /**
     * The licence_html.
     */
    boolean licence_html = true;

    /**
     * The licence_text_id.
     */
    int licence_text_id = 0;

    /**
     * The Constant NO_LICENCE.
     */
    public static final int NO_LICENCE = 0;

    /**
     * The Constant LICENCE_LGPL_v3.
     */
    public static final int LICENCE_LGPL_v3 = 1;

    /**
     * The Constant LICENCE_LGPL_v2_1.
     */
    public static final int LICENCE_LGPL_v2_1 = 2;

    /**
     * The Constant LICENCE_GPL_v3.
     */
    public static final int LICENCE_GPL_v3 = 3;

    /**
     * The Constant LICENCE_GPL_v2_0.
     */
    public static final int LICENCE_GPL_v2_0 = 4;

    /**
     * The files.
     */
    public String[] files = { "", "lgpl-v3.html", "lgpl-v2.1.html", "gpl-v3.html", "gpl-v2.0.html" };

    /**
     * Instantiates a new licence info.
     * 
     * @param ic the ic
     * @param licence the licence
     */
    public LicenceInfo(I18nControlAbstract ic, int licence)
    {
        super(ic);
        this.licence_text_id = licence;
        // setLicenceText(licence);
        loadLicenceText();
        // this.licence_text_id = licence;
        init();
        // loadLicenceText();
    }

    /**
     * Instantiates a new licence info.
     * 
     * @param ic the ic
     * @param licence_txt the licence_txt
     * @param is_html the is_html
     */
    public LicenceInfo(I18nControlAbstract ic, String licence_txt, boolean is_html)
    {
        super(ic);
        this.licence_html = is_html;
        this.licence_text = licence_txt;
        init();
    }

    /**
     * Inits the.
     */
    public void init()
    {
        this.setLayout(new java.awt.BorderLayout());

        JEditorPane jEditorPane1 = new JEditorPane();
        JScrollPane jScrollPane1 = new JScrollPane(jEditorPane1);
        // jScrollPane1.setPreferredSize(new java.awt.Dimension(13, 1200));

        jEditorPane1.setEditable(false);
        jEditorPane1.setContentType("text/html");
        jScrollPane1.setViewportView(jEditorPane1);
        jEditorPane1.setText(this.licence_text);

        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jEditorPane1.select(0, 0);

    }

    /**
     * Load licence text.
     */
    public void loadLicenceText()
    {
        /*
         * if (licence_text != null)
         * {
         * this.jEditorPane1.setText(this.licence_text);
         * return;
         * }
         */

        // System.out.println("Set licence - Start");

        try
        {
            URL url = this.getClass().getResource("/licences/" + this.files[this.licence_text_id]);
            InputStreamReader ins = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(ins);
            String line;
            StringBuffer sb = new StringBuffer();

            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }

            // System.out.println("Sb: " + sb);

            this.licence_text = sb.toString();
            // this.jEditorPane1.setText(sb.toString());
        }
        catch (Exception ex)
        {
            System.out.println("LicenceInfo::error reading. Ex: " + ex);
            ex.printStackTrace();
        }
        // System.out.println("Set licence - End");

    }

    /** 
     * getTabName
     */
    @Override
    public String getTabName()
    {
        return this.ic.getMessage("LICENCE");
    }

    /** 
     * getTabPanel
     */
    @Override
    public JPanel getTabPanel()
    {
        return this;
    }

}
