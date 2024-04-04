package com.atech.graphics.graphs;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;

/**
 *  This file is part of ATech Tools library.
 *  
 *  GraphViewDataProcessorInterface - This class must be implemented for use with GraphViewer 
 *       or GraphViewerPanel. It contains all data processing, data retrieval and date
 *       prepare for graph.
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

public interface GraphViewDataProcessorInterface
{

    /**
     * Set Controller Data - This is method where controller object should be
     *    set, so that data can be reloaded.
     *    
     * @param data
     */
    public void setControllerData(Object data);

    /**
     * Reload Data - This is method which should be called after setControllerData is set
     *     with new data. This method should call loadData() and preprocessData(). 
     */
    public void reloadData();

    /**
     * Load Data
     */
    public void loadData();

    /**
     * Preprocess Data
     */
    public void preprocessData();

    /**
     * Get Data Set
     * 
     * @return AbstractDataset instance
     */
    public AbstractDataset getDataSet();

    /**
     * Set Plot
     * 
     * @param chart JFreeChart instance
     */
    public void setPlot(JFreeChart chart);

}
