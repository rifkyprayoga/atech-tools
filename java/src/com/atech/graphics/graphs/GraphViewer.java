package com.atech.graphics.graphs;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  GraphViewer - Dialog for vieweing graphs
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


public class GraphViewer extends JDialog
{
    private static final long serialVersionUID = 1508401731783922689L;
    GraphViewInterface gvi;
    ATDataAccessAbstract m_da;

    
    /**
     * Constructor if we don't need modal dialog
     * 
     * @param gvi
     * @param da
     */
    public GraphViewer(GraphViewInterface gvi, ATDataAccessAbstract da)
    {
        super();
        
        this.m_da = da;
        this.gvi = gvi;
        this.gvi.setParent(this);
        init();
    }
    
    /**
     * Constructor for modal dialogs
     * 
     * @param gvi
     * @param da
     * @param parent
     * @param modal
     */
    public GraphViewer(GraphViewInterface gvi, ATDataAccessAbstract da, JDialog parent, boolean modal)
    {
        super(parent, "", modal);
        
        this.m_da = da;
        this.gvi = gvi;
        this.gvi.setParent(this);

        init();
    
    }
    
    
    /**
     * Constructor for modal dialogs
     * 
     * @param gvi
     * @param da
     * @param parent
     * @param modal
     */
    public GraphViewer(GraphViewInterface gvi, ATDataAccessAbstract da, JFrame parent, boolean modal)
    {
        super(parent, "", modal);
       
        this.m_da = da;
        this.gvi = gvi;
        this.gvi.setParent(this);

        init();
    
    }
    
    
    
    
    private void init()
    {

        m_da.addComponent(this);
        
        this.addWindowListener(new WindowEventHandler());
        
        initGraph();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        ChartPanel cp = gvi.getChartPanel();
        panel.add(cp, BorderLayout.CENTER);
        
        GraphViewControlerInterface gvcont = this.gvi.getControler();
        
        if (gvcont!=null)
            panel.add(gvcont.getPanel(), BorderLayout.SOUTH);
        
        this.getContentPane().add(panel);
        this.setTitle(this.gvi.getTitle());
        this.setBounds(this.gvi.getViewerDialogBounds());
        
        //cp.repaint();
        //gvi.repaint();

        m_da.centerJDialog(this);
        this.setVisible(true);
        
    }
    
    private void initGraph()
    {
        gvi.getProcessor().loadData();
        gvi.getProcessor().preprocessData();
    }

    /*
    public void repaint()
    {
        super.repaint();
        gvi.repaint();
    }*/
    

    /**
     * Close
     */
    public void close()
    {
        m_da.removeComponent(this);
        this.dispose();
    }
    
    private class WindowEventHandler extends WindowAdapter 
    {
        public void windowClosing(WindowEvent evt) 
        {
            close();
        }
      }
    
    
}

