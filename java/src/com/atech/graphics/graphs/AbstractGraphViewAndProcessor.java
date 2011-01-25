package com.atech.graphics.graphs;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

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

// AbstractGraphView + AbstractGWDataProcessor

public abstract class AbstractGraphViewAndProcessor implements GraphViewInterface, GraphViewDataProcessorInterface, HelpCapable
{

    protected ATDataAccessAbstract m_da = null;
    protected I18nControlAbstract  m_ic = null;
    protected JFreeChart chart;
    protected ChartPanel chart_panel;
    protected Component parent;
    
    /**
     * Constructor
     * 
     * @param da
     */
    public AbstractGraphViewAndProcessor(ATDataAccessAbstract da)
    {
        this.m_da = da;
        this.m_ic = da.getI18nControlInstance();
        
        //System.out.println("AbstractGraphViewProcessor: " + da + ",m_ic: " + this.m_ic);
        
    }
    
    
    /**
     * Is Help Enabled
     * 
     * @return true if help is enabled
     */
    public boolean isHelpEnabled()
    {
        return (getHelpId()!=null);
    }
    
    /**
     * Get Chart
     * 
     * @return JFreeChart instance
     */
    public JFreeChart getChart()
    {
        if (chart==null)
        {
            createChart();
            setPlot(chart);
        }
        
        return chart;
    }
    
    
    /**
     * Get Chart Panel
     * 
     * @return ChartPanel instance
     */
    public ChartPanel getChartPanel()
    {
        if (chart_panel==null)
            createChartPanel();
        
        return this.chart_panel;
    }
    

    /**
     * Create Chart
     */
    public abstract void createChart();
    
    
    /**
     * Create Chart Panel
     */
    public abstract void createChartPanel();
    
    
    /**
     * Get Processor
     * 
     * @return GraphViewDataProcessorInterface instance (typed)
     */
    public GraphViewDataProcessorInterface getProcessor()
    {
        return this;
    }
    
    
    /**
     * Reload Data - This is method which should be called after setControllerData is set
     *     with new data. This method should call loadData() and preprocessData(). 
     */
    public void reloadData()
    {
        this.loadData();
        this.preprocessData();
    }
    
    
    /**
     * Repaint
     * 
     * @see com.atech.graphics.graphs.GraphViewInterface#repaint()
     */
    public void repaint()
    {
        //this.preprocessData();
        //this.setPlot(chart);
        this.chart_panel.repaint();
    }
    
    
    /**
     * Set Controler Data - used by controler to change set of data
     * 
     * @param data objects (controler can implement custom class, which is recognized by view instance)
     */
    public void setControlerData(Object data)
    {
        if (this.getProcessor()!=null)
            this.getProcessor().setControllerData(data);
    }
    
    
    /**
     * Get Controler Interface instance
     * 
     * @return GraphViewControlerInterface instance or null
     */
    public GraphViewControlerInterface getControler()
    {
        return null;
    }

    
    /**
     * Get Title (used by GraphViewer)
     * 
     * @return title as string 
     */
    public String getTitle()
    {
        return null;
    }
    

    /**
     * Get Viewer Dialog Bounds (used by GraphViewer)
     * 
     * @return Rectangle object
     */
    public Rectangle getViewerDialogBounds()
    {
        return null; //new Rectangle(100,100,500,400);
    }
    
    
    /**
     * Set Parent
     */
    public void setParent(Component cmp)
    {
        this.parent = cmp;
    }
    
    
    /**
     * Get Parent
     */
    public Component getParent()
    {
        return this.parent;
    }
    
    /**
     * Close
     */
    public void close()
    {
        if (parent==null)
        {
            System.out.println("ERROR: Using close command, without setting parent is not allowed.");
            return;
        }
        
        if (this.parent instanceof JDialog)
        {
            JDialog d = (JDialog)this.parent;
            d.dispose();
        }
        else if (this.parent instanceof JFrame)
        {
            JFrame f = (JFrame)this.parent;
            f.dispose();
        }
        else
        {
            System.out.println("ERROR: Parent is of wrong type. Close command not initiated. Parent must be either JDialog or JFrame.");
        }
        
    }
    
    
    /**
     * Set Controller Data
     * 
     * @see com.atech.graphics.graphs.GraphViewDataProcessorInterface#setControllerData(java.lang.Object)
     */
    public void setControllerData(Object data)
    {
    }
    
    
    
}