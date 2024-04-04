package com.atech.graphics.graphs;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.atech.graphics.layout.TableLayoutUtil;
import com.atech.utils.ATDataAccessAbstract;
import info.clearthought.layout.TableLayout;

/**
 *  This file is part of ATech Tools library.
 *  
 *  GraphViewer - Dialog for vieweing graphs (with Table Layout)
 *  
 *  Copyright (C) 2009-2016  Andy (Aleksander) Rozman (Atech-Software)
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

    private static final long serialVersionUID = 1031778882387030181L;

    GraphViewInterface graphViewInterface;
    ATDataAccessAbstract dataAccess;

    JPanel controlerPanel = null;
    JPanel chartPanel = null;
    GraphViewControlerInterface controlerInstance = null;

    boolean debug = false;


    /**
     * Constructor for modal dialogs
     *
     * @param graphViewInterface
     * @param dataAccess
     * @param parent
     * @param modal
     */
    public GraphViewer(GraphViewInterface graphViewInterface, ATDataAccessAbstract dataAccess, JDialog parent,
            boolean modal)
    {
        super(parent, "", modal);

        this.dataAccess = dataAccess;
        this.graphViewInterface = graphViewInterface;
        init();
    }


    /**
     * Constructor for modal dialogs
     *
     * @param graphViewInterface
     * @param dataAccess
     * @param parent
     * @param modal
     */
    public GraphViewer(GraphViewInterface graphViewInterface, ATDataAccessAbstract dataAccess, JFrame parent,
            boolean modal)
    {
        super(parent, "", modal);

        this.dataAccess = dataAccess;
        this.graphViewInterface = graphViewInterface;
        init();
    }


    private void init()
    {
        this.graphViewInterface.setParent(this);

        dataAccess.addComponent(this);

        this.addWindowListener(new WindowEventHandler());

        initGraph();

        JPanel panel = new JPanel();

        if (graphViewInterface instanceof AbstractGraphViewControler)
        {
            debugMsg("GraphViewer: AbstractGraphViewControler. " + graphViewInterface);

            AbstractGraphViewControler gvcont = (AbstractGraphViewControler) this.graphViewInterface;

            if (gvcont != null)
            {
                controlerPanel = gvcont.getGraphView().getControler().getControlerPanel();

                panel.setLayout(TableLayoutUtil.createVerticalLayout(TableLayout.FILL,
                    gvcont.getGraphView().getControler().getControlerBounds().getHeight()));

                chartPanel = gvcont.getGraphView().getChartPanel();

                panel.add(chartPanel, "0, 0");
                panel.add(controlerPanel, "0. 1");

                // System.out.println("Controler panel: " +
                // controlerPanel.getBounds());

                dataAccess.enableHelp(gvcont);
            }
        }
        else if (graphViewInterface instanceof AbstractGraphViewAndProcessor)
        {
            debugMsg("GraphViewer: AbstractGraphViewAndProcessor. " + graphViewInterface);
            AbstractGraphViewAndProcessor gvcont = (AbstractGraphViewAndProcessor) this.graphViewInterface;

            controlerInstance = gvcont.getControler();

            if (controlerInstance != null)
            {
                controlerPanel = gvcont.getControler().getControlerPanel();

                dataAccess.enableHelp(controlerInstance);
            }

            if (controlerPanel == null)
            {
                panel.setLayout(TableLayoutUtil.createVerticalLayout(TableLayout.FILL, 1));

                debugMsg("Controler panel NULL");
                chartPanel = gvcont.getChartPanel();

                panel.add(chartPanel, "0, 0");
            }
            else
            {
                panel.setLayout(TableLayoutUtil.createVerticalLayout(TableLayout.FILL,
                    controlerInstance.getControlerBounds().getHeight()));

                debugMsg("Controler panel != NULL");
                chartPanel = gvcont.getChartPanel();

                panel.add(chartPanel, "0, 0");
                panel.add(controlerPanel, "0, 1");
            }

        }
        else
        {
            System.out.println("GraphViewer: Unsupported graph view type. " + graphViewInterface);
        }

        this.getContentPane().add(panel);
        this.setTitle(this.graphViewInterface.getTitle());
        this.setBounds(this.graphViewInterface.getViewerDialogBounds());

        dataAccess.centerJDialog(this);

        this.setVisible(true);
    }


    private void debugMsg(String msg)
    {
        if (debug)
            System.out.println(msg);
    }


    private void initGraph()
    {
        graphViewInterface.getProcessor().loadData();
        graphViewInterface.getProcessor().preprocessData();
    }


    /**
     * Close
     */
    public void close()
    {
        dataAccess.removeComponent(this);
        this.dispose();
    }

    private class WindowEventHandler extends WindowAdapter
    {

        @Override
        public void windowClosing(WindowEvent evt)
        {
            close();
        }
    }

    private class ComponentEventHandler extends ComponentAdapter
    {

        public void componentResized(ComponentEvent ce)
        {
            /*
             * ?? if (in_resize)
             * return;
             * in_resize = true;
             * Rectangle cur = this.getBounds();
             * if (init_bounds.width > this.getBounds().width ||
             * init_bounds.height > this.getBounds().height)
             * {
             * Rectangle r = this.getBounds();
             * // r.x = cur.x;
             * // r.y = cur.y;
             * if (init_bounds.width > cur.width)
             * {
             * r.width = init_bounds.width;
             * }
             * if (init_bounds.height > cur.height)
             * {
             * r.height = init_bounds.height;
             * }
             * this.setBounds(r);
             * cur = r;
             * }
             * if (this.controler_panel == null)
             * {
             * chart_panel.setBounds(0, 0, cur.width - 8, cur.height - 28);
             * }
             * else
             * {
             * chart_panel.setBounds(0, 0, cur.width - 8, cur.height -
             * controler_instance.getControlerBounds().height);
             * controler_panel.setBounds(0, cur.height -
             * controler_instance.getControlerBounds().height,
             * controler_instance.getControlerBounds().width - 8,
             * controler_instance.getControlerBounds().height);
             * this.controler_instance.resizeController(cur.width - 8);
             * }
             * System.out.println("Component size: " + this.getBounds());
             * // System.out.println("Insets: " + this.getInsets()..left);
             * in_resize = false;
             */
        }

    }

}
