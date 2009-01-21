package com.atech.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import com.atech.i18n.I18nControlAbstract;


/**
 *  This file is part of ATech Tools library.
 *  
 *  SplashAbstract - this class for creating your own Splash screen
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


public abstract class SplashAbstract extends JWindow
{


    private static final long serialVersionUID = -8160100676472934164L;

    protected I18nControlAbstract m_ic = null;
    protected JProgressBar pnlJobs = null;
    String m_version = "";
    private static final String SPLASH_FONT = "SanSerif"; 


    /**
     * Create a splash window
     * @param version 
     * @param ic 
     */
    public SplashAbstract(String version, I18nControlAbstract ic)
    {
        super();

        m_version = version;
        m_ic = ic;

        init();
    }

    
    /**
     * Get Image filename for display in Splash screen
     * 
     * @return
     */
    public abstract String getImageFilename();
    
    
    /**
     * Get Title displayed in Splash screen
     * 
     * @return
     */
    public abstract String getTitle();
    
    /**
     * Get first text item displayed in progress bar
     * @return
     */
    public abstract String getProgressStartupItem();
    
    
    /**
     * Init the graphics
     */
    private void init()
    {
        URL url = getClass().getResource(getImageFilename());
        Icon icon = null;
        if (url != null)
        {
            icon = new ImageIcon(url);
        }

        System.out.println("init");

        JLabel lblPicture = new JLabel();
        lblPicture.setBackground(Color.WHITE);
        lblPicture.setOpaque(true);
        lblPicture.setIcon(icon);
        lblPicture.setBorder(null);
        lblPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        
        JLabel lblText = new JLabel();
        lblText.setFont(new Font(SPLASH_FONT, Font.BOLD, 22));
        lblText.setForeground(Color.black);//Color.blue.darker());
	    //new Color(0x99, 0x66, 0xAA));
        lblText.setOpaque(false);
        lblText.setVerticalAlignment(SwingConstants.BOTTOM);
        lblText.setHorizontalAlignment(SwingConstants.RIGHT);
        lblText.setText(getTitle());
        
        

        JPanel pnlDisplay = new JPanel();
        pnlDisplay.setLayout(new GridBagLayout());
        pnlDisplay.add(lblText, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
        pnlDisplay.add(lblPicture, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        JLabel lblInfo = new JLabel();
        lblInfo.setBorder(null);
        lblInfo.setFont(new Font(SPLASH_FONT, Font.PLAIN, 10));
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setBackground(Color.BLACK);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        lblInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblInfo.setText(m_version); //Msg.getVersionInfo()+" "); //$NON-NLS-1$
        lblInfo.setOpaque(true);

        JPanel pnlInfo = new JPanel();
        //JobsProgressBar pnlJobs = new JobsProgressBar(false);

        pnlJobs = new JProgressBar();

        pnlJobs.setValue(0);
        this.setSplashProgress(0, getProgressStartupItem());
        pnlJobs.setForeground(Color.white);
        pnlJobs.setStringPainted(true);

        pnlInfo.setLayout(new BorderLayout(5, 0));
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.setOpaque(true);
        //pnlInfo.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        pnlJobs.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pnlInfo.add(lblInfo, BorderLayout.CENTER);
        pnlInfo.add(pnlJobs, BorderLayout.SOUTH);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pnlInfo, BorderLayout.SOUTH);
        this.getContentPane().add(pnlDisplay, BorderLayout.CENTER);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = lblPicture.getPreferredSize();
        this.setLocation(screen.width / 2 - (window.width / 2), screen.height / 2 - (window.height / 2));

        this.pack();
        this.setVisible(true);
    }

    /**
     * Shut up shop
     */
    public void close()
    {
        setVisible(false);
        dispose();
    }


    /**
     * Set splash progress. 
     * @param proc procent of progress
     * @param text progress text
     */
    public void setSplashProgress(int proc, String text)
    {
        pnlJobs.setValue(proc);
        pnlJobs.setString(proc + "%  (" + text + ")");
    }



}
