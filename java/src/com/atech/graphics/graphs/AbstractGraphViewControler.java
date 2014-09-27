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
 *  Filename: CourseGraphFrame.java
 *  Purpose:  Frame for the CourseGraphView and some controls.
 *
 *  Author:   schultd
 */

package com.atech.graphics.graphs;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

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
 *  Filename:     ###--###  
 *  Description:  ###--###
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

// WORK IN PROGRESS - DO NOT EDIT - Andy

public abstract class AbstractGraphViewControler implements GraphViewControlerInterface, ActionListener // ,
                                                                                                        // HelpCapable
// extends JDialog implements ActionListener, HelpCapable // JFrame
{

    protected GraphViewInterface graph_view;
    protected ATDataAccessAbstract m_da = null;
    protected JPanel control_panel = null;
    protected JButton help_button = null;
    protected Object parameters;
    protected I18nControlAbstract m_ic = null;

    /**
     * Constructor
     * 
     * @param da
     * @param _graph_view
     */
    public AbstractGraphViewControler(ATDataAccessAbstract da, GraphViewInterface _graph_view)
    {
        this.graph_view = _graph_view;
        this.m_da = da;
        this.m_ic = da.getI18nControlInstance();
        init();
    }

    /**
     * Constructor
     * 
     * @param da
     * @param _graph_view
     * @param paramters
     */
    public AbstractGraphViewControler(ATDataAccessAbstract da, GraphViewInterface _graph_view, Object paramters)
    {
        this.graph_view = _graph_view;
        this.m_da = da;
        this.m_ic = da.getI18nControlInstance();
        this.parameters = paramters;
        init();
    }

    /**
     * Init
     */
    public abstract void init();

    /*
     * {
     * JPanel cPanel = new JPanel(new BorderLayout());
     * dRS = new DateRangeSelectionPanel(m_da);
     * DataPlotSelectorPanel selectionPanel = new
     * DataPlotSelectorPanel(DataPlotSelectorPanel.BG_AVG_MASK);
     * selectionPanel.disableChoice(DataPlotSelectorPanel.BG_MASK |
     * DataPlotSelectorPanel.CH_MASK
     * | DataPlotSelectorPanel.INS1_MASK | DataPlotSelectorPanel.INS2_MASK
     * | DataPlotSelectorPanel.INS_TOTAL_MASK);
     * //cGV.setData(selectionPanel.getPlotData());
     * JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
     * help_button = m_da.createHelpButtonBySize(120, 25, cPanel);
     * buttonPanel.add(help_button);
     * // Dimension dim = new Dimension(80, 20);
     * Dimension dim = new Dimension(120, 25);
     * JButton drawButton = new JButton("    " + m_ic.getMessage("DRAW"));
     * drawButton.setPreferredSize(dim);
     * drawButton.setIcon(m_da.getImageIcon_22x22("paint.png", cPanel));
     * drawButton.setActionCommand("draw");
     * drawButton.addActionListener(this);
     * JButton closeButton = new JButton("    " + m_ic.getMessage("CLOSE"));
     * closeButton.setPreferredSize(dim);
     * closeButton.setActionCommand("close");
     * closeButton.setIcon(m_da.getImageIcon_22x22("cancel.png", cPanel));
     * closeButton.addActionListener(this);
     * buttonPanel.add(drawButton);
     * buttonPanel.add(closeButton);
     * cPanel.add(dRS, BorderLayout.WEST);
     * cPanel.add(selectionPanel, BorderLayout.CENTER);
     * cPanel.add(buttonPanel, BorderLayout.SOUTH);
     * this.control_panel = cPanel;
     * }
     */

    /**
     * Run Draw - This method should communicate with processor from view, 
     *     and impose command changes  and call load
     */
    public abstract void runDraw();

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        // this.getGraphView().getProcessor()
        String action = e.getActionCommand();

        if (action.equals("draw"))
        {
            runDraw();
        }
        else if (action.equals("close"))
        {
            this.getGraphView().close();
        }
        else
        {
            System.out.println("CourseGraphFrame: Unknown command: " + action);
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
        return this.getGraphView().getParent();
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
        return this.getGraphView().getHelpId();
    }

    /**
     * Get Graph View
     * 
     * @see com.atech.graphics.graphs.GraphViewControlerInterface#getGraphView()
     */
    public GraphViewInterface getGraphView()
    {
        return this.graph_view;
    }

    /**
     * Get Panel (with Controler)
     * @return
     *  
     * @see com.atech.graphics.graphs.GraphViewControlerInterface#getPanel()
     */
    public JPanel getPanel()
    {
        return this.control_panel;
    }

    /**
     * Get Controler Panel

     */
    public JPanel getControlerPanel()
    {
        return this.control_panel;
    }

}
