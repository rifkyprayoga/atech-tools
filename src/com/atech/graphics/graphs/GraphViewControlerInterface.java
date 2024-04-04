package com.atech.graphics.graphs;

import java.awt.Rectangle;

import javax.swing.JPanel;

import com.atech.help.HelpCapable;

/**
 *  This file is part of ATech Tools library.
 *  
 *  GraphViewControlerInterface - This class can be implemented for use with GraphViewer.
 *       It displays panel with different controls and sends data to viewer. 
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

public interface GraphViewControlerInterface extends HelpCapable
{

    /**
     * Get Graph View
     * 
     * @return GraphViewInterface instance
     */
    public GraphViewInterface getGraphView();

    /**
     * Get Panel
     * 
     * @return JPanel instance
     */
    public JPanel getControlerPanel();

    // public JPanel getControlerPanel();

    /**
     * Get Controller Bounds (in some cases controler can return false data about size, in that 
     * cases we need to get correct bounds.  
     * @return Rectangle as bounds
     */
    public Rectangle getControlerBounds();

    /**
     * Resize controller component (in some cases resize is not automatical, then we need this method)
     */
    public void resizeController(int width);

}
