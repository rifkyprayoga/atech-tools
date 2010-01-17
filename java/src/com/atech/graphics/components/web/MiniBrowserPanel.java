package com.atech.graphics.components.web;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument;


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


// The Simple Web Browser.
public class MiniBrowserPanel extends JPanel
{


    // These are the buttons for iterating through the page list.
    //private JButton backButton, forwardButton;

    private static final long serialVersionUID = 6557838951692663384L;

    // Page location text field.
    private JTextField locationTextField;

    // Editor pane for displaying pages.
    private JEditorPane displayEditorPane;

    // Browser's list of pages that have been visited.
    private ArrayList<String> pageList = new ArrayList<String>();

    
    JScrollPane scrollPane;
    
    /**
     * Constructor
     */
    public MiniBrowserPanel()
    {
        //this.setLayout(null);
        this.setLayout(new BorderLayout());
        

        // Set up file menu.
        /*
         * // Set up button panel. JPanel buttonPanel = new JPanel(); backButton
         * = new JButton("< Back"); backButton.addActionListener(new
         * ActionListener() { public void actionPerformed(ActionEvent e) {
         * actionBack(); } }); backButton.setEnabled(false);
         * buttonPanel.add(backButton); forwardButton = new
         * JButton("Forward >"); forwardButton.addActionListener(new
         * ActionListener() { public void actionPerformed(ActionEvent e) {
         * actionForward(); } }); forwardButton.setEnabled(false);
         * buttonPanel.add(forwardButton); locationTextField = new
         * JTextField(35); locationTextField.addKeyListener(new KeyAdapter() {
         * public void keyReleased(KeyEvent e) { if (e.getKeyCode() ==
         * KeyEvent.VK_ENTER) { actionGo(); } } });
         * buttonPanel.add(locationTextField); JButton goButton = new
         * JButton("GO"); goButton.addActionListener(new ActionListener() {
         * public void actionPerformed(ActionEvent e) { actionGo(); } });
         * buttonPanel.add(goButton);
         */
        // Set up page display.
        displayEditorPane = new JEditorPane();
        displayEditorPane.setContentType("text/html");
        displayEditorPane.setEditable(false);
        
        // displayEditorPane.addHyperlinkListener(this);

        scrollPane = new JScrollPane(displayEditorPane);
//        this.scrollPane.setBounds(0,0,640,480);
        
        /* getContentPane() */ //this.setLayout(new BorderLayout());
        // getContentPane().add(buttonPanel, BorderLayout.NORTH);
        /* getContentPane() */this.add(this.scrollPane, BorderLayout.CENTER);

        // Set window size.
        setSize(640, 480);
        
        
    }

    /**
     * Set Size
     */
    public void setSize(int width, int height)
    {
        super.setSize(width, height);
//        this.scrollPane.setSize(width, height);
    }
    
    
    // Exit this program.
    /*
     * private void actionExit() { System.exit(0); }
     */

    // Go back to the page viewed before the current page.
    /*
     * private void actionBack() { URL currentUrl = displayEditorPane.getPage();
     * int pageIndex = pageList.indexOf(currentUrl.toString()); try { showPage(
     * new URL((String) pageList.get(pageIndex - 1)), false); } catch (Exception
     * e) {} }
     */

    // Go forward to the page viewed after the current page.
    /*
     * private void actionForward() { URL currentUrl =
     * displayEditorPane.getPage(); int pageIndex =
     * pageList.indexOf(currentUrl.toString()); try { showPage( new URL((String)
     * pageList.get(pageIndex + 1)), false); } catch (Exception e) {} }
     */

    /**
     * Load and show the page specified in the location text field.
     */
    public void actionGo()
    {
        URL verifiedUrl = verifyUrl(locationTextField.getText());
        if (verifiedUrl != null)
        {
            showPage(verifiedUrl, true);
        }
        else
        {
            showError("Invalid URL");
        }
    }

    /**
     * Set Page
     * 
     * @param page
     */
    public void setPage(String page)
    {
        //System.out.println("Set Page: " + page);
        URL verifiedUrl = verifyUrl(page);

        if (verifiedUrl != null)
        {
            showPage(verifiedUrl, false);
        }
        else
        {
            showError("Invalid URL");
        }

    }

    // Show dialog box with error message.
    private void showError(String errorMessage)
    {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Verify URL format.
    private URL verifyUrl(String url)
    {
        // Only allow HTTP URLs.
        if (!url.toLowerCase().startsWith("http://"))
            return null;

        // Verify format of URL.
        URL verifiedUrl = null;
        try
        {
            verifiedUrl = new URL(url);
        }
        catch (Exception e)
        {
            return null;
        }

        return verifiedUrl;
    }

    /**
     * Show the specified page and add it to the page list if specified.
     */
    private void showPage(URL pageUrl, boolean addToList)
    {
        // Show hour glass cursor while crawling is under way.
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try
        {
            // Get URL of page currently being displayed.
            URL currentUrl = displayEditorPane.getPage();

            // Load and display specified page.
            displayEditorPane.setPage(pageUrl);

            
            HTMLDocument d = (HTMLDocument)displayEditorPane.getDocument();
            d.setBase(pageUrl);

            
            
            // Get URL of new page being displayed.
            URL newUrl = displayEditorPane.getPage();

            // Add page to list if specified.
            if (addToList)
            {
                int listSize = pageList.size();
                if (listSize > 0)
                {
                    int pageIndex = pageList.indexOf(currentUrl.toString());
                    if (pageIndex < listSize - 1)
                    {
                        for (int i = listSize - 1; i > pageIndex; i--)
                        {
                            pageList.remove(i);
                        }
                    }
                }
                pageList.add(newUrl.toString());
            }

            // Update location text field with URL of current page.
            // locationTextField.setText(newUrl.toString());

            // Update buttons based on the page being displayed.
            updateButtons();
        }
        catch (Exception e)
        {
            // Show error messsage.
            showError("Unable to load page: \n" + pageUrl);
            //e.printStackTrace();
        }
        finally
        {
            // Return to default cursor.
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /*
     * Update back and forward buttons based on the page being displayed.
     */
    private void updateButtons()
    {
        /*
         * if (pageList.size() < 2) { backButton.setEnabled(false);
         * forwardButton.setEnabled(false); } else { URL currentUrl =
         * displayEditorPane.getPage(); int pageIndex =
         * pageList.indexOf(currentUrl.toString());
         * backButton.setEnabled(pageIndex > 0); forwardButton.setEnabled(
         * pageIndex < (pageList.size() - 1)); }
         */
    }

    // Handle hyperlink's being clicked.
/*    public void hyperlinkUpdate(HyperlinkEvent event)
    {
        HyperlinkEvent.EventType eventType = event.getEventType();
        if (eventType == HyperlinkEvent.EventType.ACTIVATED)
        {
            if (event instanceof HTMLFrameHyperlinkEvent)
            {
                HTMLFrameHyperlinkEvent linkEvent = (HTMLFrameHyperlinkEvent) event;
                HTMLDocument document = (HTMLDocument) displayEditorPane.getDocument();
                document.processHTMLFrameHyperlinkEvent(linkEvent);
            }
            else
            {
                showPage(event.getURL(), true);
            }
        }
    }
*/
  
}
