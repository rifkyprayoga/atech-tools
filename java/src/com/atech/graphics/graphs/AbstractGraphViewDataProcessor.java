package com.atech.graphics.graphs;

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

// WORK IN PROGRESS - DO NOT EDIT - Andy

public abstract class AbstractGraphViewDataProcessor implements GraphViewDataProcessorInterface
{
    /**
     * Reload Data - This is method which should be called after setControllerData is set
     *     with new data. This method should call loadData() and preprocessData(). 
     */
    public void reloadData()
    {
        this.loadData();
        this.preprocessData();
    }

}
