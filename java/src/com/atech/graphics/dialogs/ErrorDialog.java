package com.atech.graphics.dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.help.CSH;
import javax.swing.*;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;
import com.atech.utils.data.ClipboardUtil;
import com.atech.utils.data.HtmlUtils;

// TODO: Auto-generated Javadoc
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

public class ErrorDialog extends JDialog implements ActionListener
{

    private static final long serialVersionUID = 202192475384598913L;

    private static Log log = LogFactory.getLog(ActionExceptionCatchDialog.class);

    /**
     * The isOpened.
     */
    public boolean isOpened = true;

    /**
     * The last action.
     */
    public int lastAction = 0;

    /**
     * The ic.
     */
    protected I18nControlAbstract ic; // = I18nControl.getInstance();

    /**
     * The da.
     */
    protected ATDataAccessAbstract da;

    /**
     * Help Button
     */
    JButton help_button = null;

    /**
     * The panel.
     */
    JPanel panel;

    /**
     * The button4.
     */
    JButton button4;

    /**
     * The font_normal_bold.
     */
    Font font_normal, font_normal_bold;

    /**
     * The name.
     */
    String name;
    /**
     * The exception.
     */
    Exception exception;

    /**
     * The errorMessage.
     */
    String errorMessage;

    String errorMessageToolTip;

    /**
     * The errorSolution.
     */
    String errorSolution;

    String errorSolutionToolTip;
    int posYDifference = 0;
    /**
     * Help id
     */
    private String help_id = "";


    // CHANGE

    /**
    * Instantiates a new error dialog.
    * 
    * @param parent the parent
    * @param da the da
    * @param application the application
    * @param module the module
    * @param action the action
    * @param exception the exception
    * @param errorMessage the errorMessage
    */
    public ErrorDialog(JFrame parent, ATDataAccessAbstract da, String application, String module, String action,
            Exception exception, String errorMessage)
    {
        this(parent, da, application, module, action, exception, errorMessage, errorMessage, null, null);
    }


    /**
     * Instantiates a new error dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param application the application
     * @param module the module
     * @param action the action
     * @param exception the exception
     * @param errorMessage the errorMessage
     */
    public ErrorDialog(JDialog parent, ATDataAccessAbstract da, String application, String module, String action,
            Exception exception, String errorMessage)
    {
        this(parent, da, application, module, action, exception, errorMessage, errorMessage, null, null);
    }


    /**
     * Instantiates a new error dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param application the application
     * @param module the module
     * @param action the action
     * @param exception the exception
     * @param errorMessage the errorMessage
     * @param solutionMessage the solutionMessage
     */
    public ErrorDialog(JFrame parent, ATDataAccessAbstract da, String application, String module, String action,
            Exception exception, String errorMessage, String solutionMessage)
    {
        this(parent, da, application, module, action, exception, errorMessage, errorMessage, solutionMessage,
                solutionMessage);
    }


    public ErrorDialog(JFrame parent, ATDataAccessAbstract da, String application, String module, String action,
            Exception exception, String errorMessage, String errorMessageToolTip, String solutionMessage,
            String sollutionMessageToolTip)
    {
        super(parent, "Selector", true);

        this.initDialog(da, application, module, action, exception, errorMessage, errorMessageToolTip, solutionMessage,
            sollutionMessageToolTip);
    }


    /**
     * Instantiates a new error dialog.
     * 
     * @param parent the parent
     * @param da the da
     * @param application the application
     * @param module the module
     * @param action the action
     * @param exception the exception
     * @param errorMessage the errorMessage
     * @param solutionMessage the solutionMessage
     */
    public ErrorDialog(JDialog parent, ATDataAccessAbstract da, String application, String module, String action,
            Exception exception, String errorMessage, String solutionMessage)
    {
        this(parent, da, application, module, action, exception, errorMessage, errorMessage, solutionMessage,
                solutionMessage);
    }


    /**
     * Instantiates a new error dialog.
     *
     * @param parent the parent
     * @param dataAccessAbstract the da
     * @param application the application
     * @param module the module
     * @param action the action
     * @param exception the exception
     * @param errorMessage the errorMessage
     * @param errorMessageToolTip the extended_error_message
     */
    public ErrorDialog(JDialog parent, ATDataAccessAbstract dataAccessAbstract, String application, String module,
            String action, Exception exception, String errorMessage, String errorMessageToolTip, String solutionMessage,
            String sollutionMessageToolTip)
    {
        super(parent, "Error Message", true);

        this.initDialog(da, application, module, action, exception, errorMessage, errorMessageToolTip, solutionMessage,
            sollutionMessageToolTip);
    }


    private void initDialog(ATDataAccessAbstract da, String application, String module, String action,
            Exception exception, String errorMessage, String errorMessageToolTip, String solutionMessage,
            String solutionMessageToolTip)
    {
        this.da = da;
        this.ic = da.getI18nControlInstance();

        this.exception = exception;
        this.errorMessage = errorMessage;
        this.errorMessageToolTip = errorMessageToolTip;
        this.errorSolution = solutionMessage;
        this.errorSolutionToolTip = solutionMessageToolTip;

        System.out.println("errorMsg: " + this.errorMessage + "\n\nToolTip: " + this.errorMessageToolTip);

        init();
    }


    /**
     * Inits the.
     */
    public void init()
    {
        ATSwingUtils.initLibrary();

        name = ic.getMessage("ERROR_DIALOG");

        font_normal = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        font_normal_bold = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);

        this.setLayout(null);
        this.setTitle(name);
        this.setResizable(false);

        if (this.errorSolution == null)
        {
            this.errorSolution = ic.getMessage("NO_SOLUTION_AVAILABLE");
            this.errorSolutionToolTip = ic.getMessage("NO_SOLUTION_AVAILABLE");
        }

        System.out.println("Error Dialog 0.2 ");

        cmdDialog();
    }

    // ---
    // --- Methods
    // ---


    /**
     * Gets the help button.
     * 
     * @return the help button
     */
    public JButton getHelpButton()
    {
        return this.help_button;
    }


    /**
     * Gets the help id.
     * 
     * @return the help id
     */
    public String getHelpId()
    {
        return help_id;
    }


    /**
     * Sets the help string id.
     * 
     * @param id the new help string id
     */
    public void setHelpStringId(String id)
    {
        help_id = id;
        CSH.setHelpIDString(this, id);
    }


    public void cmdDialog()
    {
        name = ic.getMessage("ERROR_DIALOG");

        panel = new JPanel();
        panel.setBounds(0, 5, 520, 440);
        panel.setLayout(null);

        this.getContentPane().add(panel);

        JLabel label;

        ATSwingUtils.getTitleLabel(name, 20, 5, 460, 36, panel, ATSwingUtils.FONT_BIG_BOLD);

        ATSwingUtils.getLabel(ic.getMessage("ERROR") + ":", 35, 45, 295, 25, panel, ATSwingUtils.FONT_NORMAL_BOLD);

        String text = ATSwingUtils.createHtmlTextWithWidth(this.errorMessage, 300);

        label = new JLabel(text);
        label.setFont(this.font_normal);
        label.setVerticalAlignment(SwingConstants.TOP);
        // label.setBorder(new LineBorder(Color.green));
        label.setBounds(35, 70, 300, 50);
        label.setToolTipText(ATSwingUtils.createHtmlTextWithWidth(this.errorMessageToolTip, 500));

        calculateJLabelDifference(label, text, 70);

        panel.add(label);

        ATSwingUtils.getLabel(ic.getMessage("ERROR_SOLUTION") + ":", 35, 115 + posYDifference, 295, 25, panel,
            ATSwingUtils.FONT_NORMAL_BOLD);

        text = ATSwingUtils.createHtmlTextWithWidth(this.errorSolution, 300);

        label = new JLabel(text);
        label.setFont(this.font_normal);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setBounds(35, 135 + posYDifference, 300, 50);
        label.setFont(this.font_normal);

        System.out.println("PosDifference: before sollution: " + posYDifference);

        calculateJLabelDifference(label, text, (135 + posYDifference));

        System.out.println("PosDifference: after sollution: " + posYDifference);

        panel.add(label);

        Color c = (Color) UIManager.get("activeCaption"); // ,
                                                          // Color.ANY_COLOR_YOU_WANT);

        // line
        JPanel pan = new JPanel();
        pan.setBackground(c);
        pan.setBounds(0, 200 + posYDifference, 520, 4);
        panel.add(pan);

        ATSwingUtils.getButton(ic.getMessage("CLOSE"), 365, 50, 110, 25, panel, ATSwingUtils.FONT_NORMAL, null, "close",
            this, da);

        // ---
        // --- Help command
        // ---
        this.help_button = new JButton(ic.getMessage("HELP"));
        this.help_button.setBounds(365, 100, 110, 25);
        this.help_button.setFont(font_normal);
        // button.addActionListener(this);
        // button.setActionCommand("help");
        panel.add(this.help_button);

        ATSwingUtils.getButton(ic.getMessage("DETAILS") + " >>", 365, 130, 110, 25, panel, ATSwingUtils.FONT_NORMAL,
            null, "details", this, da);

        button4 = new JButton(ic.getMessage("COPY_TO_CLIPBOARD"));
        button4.setActionCommand("copy_to_clipboard");
        button4.addActionListener(this);
        button4.setVerticalAlignment(SwingConstants.CENTER);
        button4.setVerticalTextPosition(SwingConstants.CENTER);
        button4.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_SMALLER));
        button4.setBounds(205, 170 + posYDifference, 150, 20);
        panel.add(button4);

        label = new JLabel(ic.getMessage("EXCEPTION_LOG") + ":");
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(this.font_normal_bold);
        label.setBounds(35, 225 + posYDifference, 295, 50);
        panel.add(label);

        button4 = new JButton(ic.getMessage("SEND_TO_SERVER"));
        button4.setBounds(305, 215 + posYDifference, 170, 25);
        button4.setActionCommand("send_to_server");
        button4.addActionListener(this);
        button4.setFont(this.font_normal);
        // panel.add(button4);
        // TODO this is now disabled, we need to add additional functionality at
        // later time.

        JTextArea list_ = new JTextArea(ExceptionUtils.getStackTrace(exception));

        JScrollPane scroll_ = new JScrollPane(list_, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll_.setBounds(30, 250 + posYDifference, 445, 145);
        panel.add(scroll_);

        panel.setBounds(0, 5, 520, 440 + posYDifference);

        this.setBounds(100, 80, 520, 440 + posYDifference);
        this.da.centerJDialog(this);
        this.repaint();

        this.setResizable(false);
        this.setVisible(true);

    }


    private void calculateJLabelDifference(JLabel label, String text, int posY)
    {
        int height = ATSwingUtils.calculateJLabelSizeWithText(label, text).height;

        if (height > 50)
        {
            label.setBounds(35, posY, 300, height);
            posYDifference += (height - 50) + 10;
        }
    }


    /**
     *  Menu item listener, waits for user to issue command through menu.
     *
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("close"))
        {
            this.dispose();
        }
        else if (action.equals("send_to_server"))
        {
            JOptionPane.showMessageDialog(this, ic.getMessage("SEND_TO_SERVER_TEXT"),
                ic.getMessage("SENDING_TO_SERVER"), JOptionPane.ERROR_MESSAGE);
        }
        else if (action.equals("details"))
        {
            int x = (int) this.getBounds().getX();
            int y = (int) this.getBounds().getY();

            if (isOpened)
            {
                this.setBounds(x, y, 520, 232 + posYDifference);
                // this.da.centerJDialog(this);
                isOpened = !isOpened;
                this.repaint();
            }
            else
            {
                this.setBounds(x, y, 520, 440 + posYDifference);
                // this.da.centerJDialog(this);
                isOpened = !isOpened;
                this.repaint();
            }
        }
        else if (action.equals("copy_to_clipboard"))
        {
            StringBuilder sb = new StringBuilder();

            sb.append(ic.getMessage("ERROR") + ":\n");
            sb.append(HtmlUtils.convertToPlainText(this.errorMessageToolTip));
            sb.append("\n\n" + ic.getMessage("ERROR_SOLUTION") + ":\n");
            sb.append(HtmlUtils.convertToPlainText(this.errorSolutionToolTip));
            sb.append("\n\n" + ic.getMessage("EXCEPTION_LOG") + ":\n");
            sb.append(ExceptionUtils.getStackTrace(exception));

            ClipboardUtil.copyToClipboard(sb.toString());
        }
        else
        {
            System.out.println("ErrorDialog:UnknownAction: " + action);
        }

    }

}
