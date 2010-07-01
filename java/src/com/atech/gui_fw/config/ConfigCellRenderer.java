package com.atech.gui_fw.config;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;


/**
 *  Filename:     ConfigCellRenderer
 *  Description:  ConfigCellRenderer is class for rendering Config cells
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */


public class ConfigCellRenderer extends DefaultListCellRenderer
{

    private static final long serialVersionUID = 8675942270924718185L;
    AbstractConfigurationContext m_acc = null;

    
    /**
     * Constructor 
     * 
     * @param acc
     */
    public ConfigCellRenderer(AbstractConfigurationContext acc)
    {
        this.m_acc = acc;
    }
    

    /**
     * This is the only method defined by ListCellRenderer.  We just
     * reconfigure the JLabel each time we're called.
     */
    public Component getListCellRendererComponent(
                                                 JList list,
                                                 Object value,   // value to display
                                                 int index,      // cell index
                                                 boolean iss,    // is the cell selected
                                                 boolean chf)    // the list and the cell have the focus
    {
        /* The DefaultListCellRenderer class will take care of
         * the JLabels text property, it's foreground and background
         * colors, and so on.
         */
        super.getListCellRendererComponent(list, value, index, iss, chf);

        /* We additionally set the JLabels icon property here.
         */
        //String s = value.toString();

        //DataAccess da = DataAccess.getInstance();

        //int idx = da.getSelectedConfigTypePart(s);

        setIcon(m_acc.getConfigIcons()[index]);

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);

        return this;
    }
    
}
