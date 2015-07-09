package com.atech.graphics.graphs;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

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

public class GraphViewer extends JDialog implements ComponentListener
{

    private static final long serialVersionUID = 1508401731783922689L;
    GraphViewInterface gvi;
    ATDataAccessAbstract m_da;
    Rectangle init_bounds;
    Rectangle init_bounds_controler;

    JPanel controler_panel = null;
    JPanel chart_panel = null;
    GraphViewControlerInterface controler_instance = null;

    // boolean was_inited = false;
    private boolean in_resize = true;
    String help_id = null;


    /**
     * Constructor if we don't need modal dialog
     * 
     * @param gvi
     * @param da
     */
    /*
     * public GraphViewer(GraphViewInterface gvi, ATDataAccessAbstract da)
     * {
     * super();
     * this.dataAccess = da;
     * this.gvi = gvi;
     * this.gvi.setParent(this);
     * init();
     * }
     */

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
        init();
    }


    private void init()
    {

        this.gvi.setParent(this);
        this.addComponentListener(this);

        m_da.addComponent(this);

        this.addWindowListener(new WindowEventHandler());

        initGraph();

        init_bounds = this.gvi.getViewerDialogBounds();
        System.out.println("init bounds: " + init_bounds);

        JPanel panel = new JPanel();
        // s panel.setLayout(new BorderLayout());
        panel.setLayout(null);
        panel.setBounds(0, 0, init_bounds.width, init_bounds.height);

        int bnds_height = init_bounds.height;
        int bnds_width = init_bounds.width;

        // ChartPanel cp = gvi.getChartPanel();
        // panel.add(cp, BorderLayout.CENTER);

        // int size_minus = 0;

        if (gvi instanceof AbstractGraphViewControler)
        {
            System.out.println("Fix N/A:: AbstractGraphViewControler ");
            // System.out.println("GraphViewer: AbstractGraphViewControler. " +
            // gvi);
            AbstractGraphViewControler gvcont = (AbstractGraphViewControler) this.gvi;

            if (gvcont != null)
            {
                chart_panel = gvcont.getGraphView().getChartPanel();
                chart_panel.setBounds(0, 0, init_bounds.width, init_bounds.height);
                panel.add(chart_panel);

                controler_panel = gvcont.getGraphView().getControler().getControlerPanel();

                System.out.println("Controler panel: " + controler_panel.getBounds());
                init_bounds_controler = gvcont.getGraphView().getControler().getControlerBounds();

                // panel.add(gvcont.getPanel(), BorderLayout.SOUTH);
                // this.help_id = gvcont.getHelpId();

                // if ((help_id != null) && (help_id.length()>3))
                m_da.enableHelp(gvcont);
            }
        }
        else if (gvi instanceof AbstractGraphViewAndProcessor)
        {
            System.out.println("Fix N/A:: AbstractGraphViewAndProcessor ");
            // System.out.println("GraphViewer: AbstractGraphViewAndProcessor. "
            // + gvi);
            AbstractGraphViewAndProcessor gvcont = (AbstractGraphViewAndProcessor) this.gvi;

            controler_instance = gvcont.getControler();
            // JPanel cont_panel= null;

            /*
             * this.help_id = gvcont.getHelpId();
             * if ((help_id != null) && (help_id.length()>3))
             * dataAccess.enableHelp(this);
             */
            // dataAccess.enableHelp(gvcont);

            if (controler_instance != null)
            {
                controler_panel = gvcont.getControler().getControlerPanel();
                // System.out.println("Controler panel: " +
                // controler_panel.getBounds());
                // System.out.println("Controler panel: " +
                // controler_instance.getControlerBounds());
                init_bounds_controler = gvcont.getControler().getControlerBounds();

                // controler_instance..getHelpId();
                m_da.enableHelp(controler_instance);
            }

            if (controler_panel == null)
            {
                System.out.println("Controler panel NULL");
                chart_panel = gvcont.getChartPanel();
                chart_panel.setBounds(0, 0, init_bounds.width, init_bounds.height);

                bnds_height = init_bounds.height;

                panel.add(chart_panel);
            }
            else
            {
                System.out.println("Controler panel != NULL");
                chart_panel = gvcont.getChartPanel();
                chart_panel.setBounds(0, 0, init_bounds.width,
                    init_bounds.height - controler_instance.getControlerBounds().height);
                panel.add(chart_panel);

                controler_panel.setBounds(0, init_bounds.height - controler_instance.getControlerBounds().height,
                    controler_instance.getControlerBounds().width, controler_instance.getControlerBounds().height);

                panel.add(controler_panel);
            }

            /*
             * if (gvcont!=null)
             * {
             * panel.add(gvcont.getChartPanel(), BorderLayout.SOUTH);
             * //dataAccess.enableHelp(gvcont);
             * }
             */
            // else
            // System.out.println("GraphViewer: AbstractGraphViewAndProcessor not found");

        }
        else
        {
            System.out.println("GraphViewer: Unsupported graph view type. " + gvi);
        }

        /*
         * AbstractGraphViewControler gvcont =
         * (AbstractGraphViewControler)this.gvi.getControler();
         * if (gvcont!=null)
         * {
         * panel.add(gvcont.getPanel(), BorderLayout.SOUTH);
         * dataAccess.enableHelp(gvcont);
         * }
         * else
         * System.out.println("GraphViewer: AbstractGraohViewControler not found"
         * );
         */

        this.getContentPane().add(panel);
        this.setTitle(this.gvi.getTitle());
        this.setBounds(0, 0, bnds_width, bnds_height + 28); // panel.getBounds());
                                                            // //this.gvi.getViewerDialogBounds());

        // cp.repaint();
        // gvi.repaint();

        m_da.centerJDialog(this);

        // this.was_inited = true;
        in_resize = false;
        this.setVisible(true);

    }


    private void initGraph()
    {
        gvi.getProcessor().loadData();
        gvi.getProcessor().preprocessData();
    }


    /*
     * public void repaint()
     * {
     * super.repaint();
     * gvi.repaint();
     * }
     */

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

        @Override
        public void windowClosing(WindowEvent evt)
        {
            close();
        }

    }


    public void componentHidden(ComponentEvent ce)
    {
    }


    public void componentMoved(ComponentEvent ce)
    {
    }


    public void componentResized(ComponentEvent ce)
    {
        if (in_resize)
            return;

        in_resize = true;

        Rectangle cur = this.getBounds();

        if (init_bounds.width > this.getBounds().width || init_bounds.height > this.getBounds().height)
        {
            Rectangle r = this.getBounds();

            // r.x = cur.x;
            // r.y = cur.y;

            if (init_bounds.width > cur.width)
            {
                r.width = init_bounds.width;
            }

            if (init_bounds.height > cur.height)
            {
                r.height = init_bounds.height;
            }

            this.setBounds(r);

            cur = r;

        }

        if (this.controler_panel == null)
        {
            chart_panel.setBounds(0, 0, cur.width - 8, cur.height - 28);
        }
        else
        {
            chart_panel.setBounds(0, 0, cur.width - 8, cur.height - controler_instance.getControlerBounds().height);
            controler_panel.setBounds(0, cur.height - controler_instance.getControlerBounds().height,
                controler_instance.getControlerBounds().width - 8, controler_instance.getControlerBounds().height);
            this.controler_instance.resizeController(cur.width - 8);
        }

        System.out.println("Component size: " + this.getBounds());

        // System.out.println("Insets: " + this.getInsets()..left);

        in_resize = false;
    }


    public void componentShown(ComponentEvent ce)
    {
    }

}
