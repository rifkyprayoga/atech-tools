/*
 *  GGC - GNU Gluco Control
 *
 *  A pure java app to help you manage your diabetes.
 *
 *  See AUTHORS for copyright information.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Filename: prefRenderingQualityPane.java
 *  Purpose:  Setting the rendering quality of the application
 *
 *  Author:   schultd
 */

package com.atech.gui_fw.config;

import java.awt.*;
import java.awt.event.ItemEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.atech.help.HelpCapable;

/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     PrefRenderingQualityPane
 *  Description:  Setting the rendering quality of the application (graphs)
 * 
 *  Author: schultd
 *          andyrozman {andy@atech-software.com}  
 */

public class ConfigGraphRenderingPanel extends AbstractConfigPanel implements HelpCapable
{

    private static final long serialVersionUID = 3756364382393903652L;

    private JComboBox comboAntiAliasing, comboColorRendering, comboDithering, comboFractionalMetrics,
            comboInterpolation, comboTextAntiAliasing, comboRendering;


    // private DailyGraphView dgv;
    // private JPanel testingPanel;

    /**
     * Constructor
     * 
     * @param acc 
     */
    public ConfigGraphRenderingPanel(AbstractConfigurationContext acc)
    {
        super(acc);

        init();
    }


    private void init()
    {

        this.setBorder(new TitledBorder(m_ic.getMessage("RENDERING_QUALITY")));

        JPanel a = new JPanel(new GridLayout(0, 1));
        a.add(new JLabel(m_ic.getMessage("ANTIALIASING") + ":"));
        a.add(new JLabel(m_ic.getMessage("COLOR_RENDERING") + ":"));
        a.add(new JLabel(m_ic.getMessage("DITHERING") + ":"));
        a.add(new JLabel(m_ic.getMessage("FRACTIONAL_METRICS") + ":"));
        a.add(new JLabel(m_ic.getMessage("INTERPOLATION") + ":"));
        a.add(new JLabel(m_ic.getMessage("TEXT_ANTIALIASING") + ":"));
        a.add(new JLabel(m_ic.getMessage("RENDERING") + ":"));

        JPanel b = new JPanel(new GridLayout(0, 1));

        Object[] o1 = { RenderingHints.VALUE_ANTIALIAS_DEFAULT, RenderingHints.VALUE_ANTIALIAS_OFF,
                       RenderingHints.VALUE_ANTIALIAS_ON };
        b.add(comboAntiAliasing = new JComboBox(o1));

        Object[] o2 = { RenderingHints.VALUE_COLOR_RENDER_DEFAULT, RenderingHints.VALUE_COLOR_RENDER_QUALITY,
                       RenderingHints.VALUE_COLOR_RENDER_SPEED };
        b.add(comboColorRendering = new JComboBox(o2));

        Object[] o3 = { RenderingHints.VALUE_DITHER_DEFAULT, RenderingHints.VALUE_DITHER_DISABLE,
                       RenderingHints.VALUE_DITHER_ENABLE };
        b.add(comboDithering = new JComboBox(o3));

        Object[] o4 = { RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_OFF,
                       RenderingHints.VALUE_FRACTIONALMETRICS_ON };
        b.add(comboFractionalMetrics = new JComboBox(o4));

        Object[] o5 = { RenderingHints.VALUE_INTERPOLATION_BICUBIC, RenderingHints.VALUE_INTERPOLATION_BILINEAR,
                       RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR };
        b.add(comboInterpolation = new JComboBox(o5));

        Object[] o6 = { RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF,
                       RenderingHints.VALUE_TEXT_ANTIALIAS_ON };
        b.add(comboTextAntiAliasing = new JComboBox(o6));

        Object[] o7 = { RenderingHints.VALUE_RENDER_DEFAULT, RenderingHints.VALUE_RENDER_QUALITY,
                       RenderingHints.VALUE_RENDER_SPEED };
        b.add(comboRendering = new JComboBox(o7));

        Box myBox = Box.createHorizontalBox();
        myBox.add(a);
        myBox.add(b);

        setLayout(new BorderLayout());

        // FIXME

        /*
         * comboAntiAliasing.setSelectedIndex(dataAccess.getSettings().
         * getAntiAliasing(
         * ));
         * comboColorRendering.setSelectedIndex(dataAccess.getSettings().
         * getColorRendering
         * ());
         * comboDithering.setSelectedIndex(dataAccess.getSettings().getDithering(
         * ));
         * comboFractionalMetrics.setSelectedIndex(dataAccess.getSettings().
         * getFractionalMetrics());
         * comboInterpolation.setSelectedIndex(dataAccess.getSettings().
         * getInterpolation
         * ());
         * comboTextAntiAliasing.setSelectedIndex(dataAccess.getSettings().
         * getTextAntiAliasing());
         * comboRendering.setSelectedIndex(dataAccess.getSettings().getRendering(
         * ));
         */
        comboAntiAliasing.addItemListener(this);
        comboColorRendering.addItemListener(this);
        comboDithering.addItemListener(this);
        comboFractionalMetrics.addItemListener(this);
        comboInterpolation.addItemListener(this);
        comboTextAntiAliasing.addItemListener(this);
        comboRendering.addItemListener(this);

        add(myBox, BorderLayout.NORTH);

        // testingPanel = new JPanel();
        // testingPanel.setBackground(Color.white);
        /*
         * dgv = new
         * DailyGraphView(PrefFontsAndColorPane.createDailyGraphValues());
         * testingPanel.add(dgv, BorderLayout.CENTER);
         * add(dgv, BorderLayout.CENTER);
         */
    }


    /**
     * Save Properties
     * 
     * @see ggc.gui.panels.prefs.AbstractPrefOptionsPanel#saveProps()
     */
    @Override
    public void saveConfig()
    {
        /*
         * this.settings.setAntiAliasing(comboAntiAliasing.getSelectedIndex());
         * this.settings.setColorRendering(comboColorRendering.getSelectedIndex()
         * );
         * this.settings.setDithering(comboDithering.getSelectedIndex());
         * this.settings.setFractionalMetrics(comboFractionalMetrics.
         * getSelectedIndex());
         * this.settings.setInterpolation(comboInterpolation.getSelectedIndex());
         * this.settings.setTextAntiAliasing(comboTextAntiAliasing.getSelectedIndex
         * ());
         * this.settings.setRendering(comboRendering.getSelectedIndex());
         */
    }


    /**
     * Item State Changed
     * 
     * @see ggc.gui.panels.prefs.AbstractPrefOptionsPanel#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        // saveProps();
        changed = true;
        // dgv.settingsChanged();
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
        return this.parent.getHelpButton();
    }


    /**
     * getHelpId - get id for Help
     */
    public String getHelpId()
    {
        return "GGC_Prefs_Rendering";
    }

}
