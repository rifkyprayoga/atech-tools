package com.atech.graphics.graphs;

import java.awt.Component;
import java.awt.Rectangle;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *  This file is part of ATech Tools library.
 *  
 *  GraphViewInterface - This class must be implemented for use with GraphViewer or GraphViewerPanel
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

public interface GraphViewInterface
{

    /**
     * Get Title (used by GraphViewer)
     * 
     * @return title as string 
     */
    public String getTitle();

    /**
     * Get Controler Interface instance
     * 
     * @return GraphViewControlerInterface instance or null
     */
    public GraphViewControlerInterface getControler();

    /**
     * Set Controler Data - used by controler to change set of data
     * 
     * @param data objects (controler can implement custom class, which is recognized by view instance)
     */
    public void setControlerData(Object data);

    /**
     * Get Viewer Dialog Bounds (used by GraphViewer)
     * 
     * @return Rectangle object
     */
    public Rectangle getViewerDialogBounds();

    /**
     * Get Help Id
     * 
     * @return
     */
    public String getHelpId();

    /**
     * Is Help Enabled
     * 
     * @return true if help is enabled
     */
    public boolean isHelpEnabled();

    /**
     * Get Chart
     * 
     * @return JFreeChart instance
     */
    public JFreeChart getChart();

    /**
     * Get Chart Panel
     * 
     * @return ChartPanel instance
     */
    public ChartPanel getChartPanel();

    /**
     * Get Processor
     * 
     * @return GraphViewDataProcessorInterface instance (typed)
     */
    public GraphViewDataProcessorInterface getProcessor();

    /**
     * Repaint
     */
    public void repaint();

    /**
     * Set Parent
     * 
     * @param cmp
     */
    public void setParent(Component cmp);

    /**
     * Get Parent
     * 
     * @return
     */
    public Component getParent();

    /**
     * Close
     */
    public void close();

}
