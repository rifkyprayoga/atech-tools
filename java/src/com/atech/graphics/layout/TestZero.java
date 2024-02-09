package com.atech.graphics.layout;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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

public class TestZero extends JFrame
{

    // resize, menu's, bookmarks, support for better HTML, comments

    private static final long serialVersionUID = -2187488225174254498L;
    /* private allListener listener = new allListener(); */
    // x private static JTextField textLocation;
    private int maxX = 640;
    private int maxY = 480;

    // x private JEditorPane webPage;

    // private ArrayList historyList = new ArrayList();
    // x private int indexListCurrent=-1;
    // x private int indexListAll=-1;

    // ---

    /**
     * Constructor
     */
    public TestZero()
    {
        this.setTitle("Andy's Simple Java Html Browser 0.1");
        this.setSize(640, 480);
        this.setLocation(20, 20);
        this.setVisible(true);
        this.getContentPane().setLayout(new ZeroLayout(this.getSize()));

        JPanel allPanel = new JPanel();
        this.getContentPane().add(allPanel, "true");
        // allPanel.setLayout(new ZeroLayout());
        allPanel.setBackground(Color.cyan);
        allPanel.setBounds(0, 0, maxX, maxY);
        allPanel.setLayout(new ZeroLayout(allPanel.getSize()));
        JButton bu = new JButton();
        bu.setBounds(100, 100, 100, 100);
        // this.getContentPane().add(allPanel, "true");

        // bu.addActionListener(new ActionListener(){
        /*
         * public void actionPerformed(ActionEvent e) {
         * repaint();
         * }
         * });
         * allPanel.add(bu, "true");
         */
        allPanel.add(bu, ZeroLayout.STATIC);
        this.getContentPane().add(allPanel, "true");

    }

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String args[])
    {
        new TestZero();
    }
}
