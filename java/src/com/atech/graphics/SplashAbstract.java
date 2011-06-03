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
    protected JProgressBar progress_bar = null;
    String m_version = "";
    private static final String SPLASH_FONT = "SanSerif"; 
    protected JLabel lbl_picture;
    protected JLabel lbl_title;
    protected JLabel lbl_version_info;
    
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
        postInit();
        this.setVisible(true);
    }

    
    /**
     * Get Image filename for display in Splash screen
     * 
     * @return
     */
    public abstract String getImageFilename();
    
    
    public abstract void postInit();
    
    
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

        //System.out.println("init");

        lbl_picture = new JLabel();
        lbl_picture.setBackground(Color.WHITE);
        lbl_picture.setOpaque(true);
        lbl_picture.setIcon(icon);
        lbl_picture.setBorder(null);
        lbl_picture.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        
        lbl_title = new JLabel();
        lbl_title.setFont(new Font(SPLASH_FONT, Font.BOLD, 22));
        lbl_title.setForeground(Color.black);
        lbl_title.setOpaque(false);
        lbl_title.setVerticalAlignment(SwingConstants.BOTTOM);
        lbl_title.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_title.setText(getTitle());
        
        

        JPanel panel_display = new JPanel();
        panel_display.setLayout(new GridBagLayout());
        panel_display.add(lbl_title, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
        panel_display.add(lbl_picture, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        lbl_version_info = new JLabel();
        lbl_version_info.setBorder(null);
        lbl_version_info.setFont(new Font(SPLASH_FONT, Font.PLAIN, 10));
        lbl_version_info.setForeground(Color.WHITE);
        lbl_version_info.setBackground(Color.BLACK);
        lbl_version_info.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        lbl_version_info.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_version_info.setText(m_version); //Msg.getVersionInfo()+" "); //$NON-NLS-1$
        lbl_version_info.setOpaque(true);

        JPanel panel_info = new JPanel();
        //JobsProgressBar pnlJobs = new JobsProgressBar(false);

        progress_bar = new JProgressBar();

        progress_bar.setValue(0);
        this.setSplashProgress(0, getProgressStartupItem());
        progress_bar.setForeground(new Color(16,78,139));
        progress_bar.setStringPainted(true);

        panel_info.setLayout(new BorderLayout(5, 0));
        panel_info.setBackground(Color.WHITE);
        panel_info.setOpaque(true);
        //pnlInfo.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        progress_bar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel_info.add(lbl_version_info, BorderLayout.CENTER);
        panel_info.add(progress_bar, BorderLayout.SOUTH);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panel_info, BorderLayout.SOUTH);
        this.getContentPane().add(panel_display, BorderLayout.CENTER);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = lbl_picture.getPreferredSize();
        this.setLocation(screen.width / 2 - (window.width / 2), screen.height / 2 - (window.height / 2));

        this.pack();
        
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
        progress_bar.setValue(proc);
        progress_bar.setString(proc + "%  (" + text + ")");
    }



}
