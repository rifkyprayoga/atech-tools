package com.atech.graphics.graphs;

import java.awt.BorderLayout;

import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  GraphViewerPanel - panel for viewing graphs (panel means no controller)
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

public class GraphViewerPanel extends JPanel
{

    private static final long serialVersionUID = 6768148904791944688L;

    /**
     * Constructor
     * 
     * @param gvi
     */
    /*
     * public GraphViewerPanel(GraphViewInterface gvi) //,
     * GraphViewDataProcessorInterface gvdp)
     * {
     * super();
     * gvi.getProcessor().loadData();
     * gvi.getProcessor().preprocessData();
     * setLayout(new BorderLayout());
     * add(gvi.getChartPanel(), BorderLayout.CENTER);
     * }
     */

    public GraphViewerPanel(AbstractGraphView gvi) // ,
                                                   // GraphViewDataProcessorInterface
                                                   // gvdp)
    {
        super();

        gvi.getProcessor().loadData();
        gvi.getProcessor().preprocessData();

        gvi.createChart();

        setLayout(new BorderLayout());
        add(gvi.getChartPanel(), BorderLayout.CENTER);
    }

    /**
     * Instantiates a new graph viewer panel.
     * 
     * @param gvi the gvi
     */
    public GraphViewerPanel(AbstractGraphViewAndProcessor gvi) // ,
                                                               // GraphViewDataProcessorInterface
                                                               // gvdp)
    {
        super();

        gvi.getProcessor().loadData();
        gvi.getProcessor().preprocessData();

        gvi.createChart();

        setLayout(new BorderLayout());
        add(gvi.getChartPanel(), BorderLayout.CENTER);
    }

}
