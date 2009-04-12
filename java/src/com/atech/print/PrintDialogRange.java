package com.atech.print;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.atech.graphics.components.DateComponent;
import com.atech.graphics.dialogs.ActionExceptionCatchDialog;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     PrintingDialog  
 *  Description:  Dialog for Printing
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

// fix this

public abstract class PrintDialogRange extends ActionExceptionCatchDialog // extends
// JDialog
// implements
// ActionListener
// , HelpCapable
{

    
    
    private static final long serialVersionUID = 2693207247071685559L;
    protected I18nControlAbstract m_ic = null;
    protected ATDataAccessAbstract m_da = null;

    private boolean m_actionDone = false;

    protected JTextField tfName;
    protected JComboBox cb_template = null;
    // x private String[] schemes_names = null;

    protected DateComponent dc_to, dc_from;
    
    GregorianCalendar gc = null;
    JSpinner sl_year = null, sl_month = null;
    JButton help_button;

    /*
    private String[] report_types_1 = { m_ic.getMessage("SIMPLE_MONTHLY_REPORT"),
                                      m_ic.getMessage("EXTENDED_MONTHLY_REPORT") };

    private String[] report_types_2 = { m_ic.getMessage("FOOD_MENU_BASE"),
                                       m_ic.getMessage("FOOD_MENU_EXT_I"),
                                       m_ic.getMessage("FOOD_MENU_EXT_II"),
//                                       m_ic.getMessage("FOOD_MENU_EXT_III")
                                       };
*/
    Font font_normal, font_normal_bold;
    boolean enable_help;

    /**
     * Dialog Options: Year and Month Option
     */
    public static final int PRINT_DIALOG_YEAR_MONTH_OPTION = 1;

    /**
     * Dialog Options: Range with day option
     */
    public static final int PRINT_DIALOG_RANGE_DAY_OPTION = 2;

//    private int master_type = 1;

    /**
     * Constructor 
     * 
     * @param frame
     * @param type
     * @param da 
     * @param _enable_help 
     */
    public PrintDialogRange(JFrame frame, int type, ATDataAccessAbstract da, boolean _enable_help) // throws
                                                                   // Exception
    {
        super(da, "printing_dialog");
        // super(frame, "", true);
        /*
         * Rectangle rec = frame.getBounds(); int x = rec.x + (rec.width / 2);
         * int y = rec.y + (rec.height / 2);
         */
        this.setLayout(null);

        this.m_da = da;
        this.m_ic = da.getI18nControlInstance();
        ATSwingUtils.initLibrary();
        this.enable_help = _enable_help;
        
        font_normal = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        font_normal_bold = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);

        gc = new GregorianCalendar();
        setTitle(m_ic.getMessage("PRINTING"));

        initRange();

        this.cb_template.setSelectedIndex(type - 1);

        this.setVisible(true);
    }


    private void initRange() // throws Exception
    {

        setSize(350, 420);
        this.m_da.centerJDialog(this);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 350, 400);
        panel.setLayout(null);

        this.getContentPane().add(panel);

        JLabel label = new JLabel(m_ic.getMessage("PRINTING"));
        label.setFont(ATSwingUtils.getFont(ATSwingUtils.FONT_BIG_BOLD));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 20, 350, 35);
        panel.add(label);

        label = new JLabel(m_ic.getMessage("TYPE_OF_REPORT") + ":");
        label.setFont(this.font_normal_bold);
        label.setBounds(40, 75, 280, 25);
        panel.add(label);

        cb_template = new JComboBox(getReportTypes());
        cb_template.setFont(this.font_normal);
        cb_template.setBounds(40, 105, 230, 25);
        panel.add(cb_template);


        label = new JLabel(m_ic.getMessage("SELECT_STARTING_RANGE") + ":");
        label.setFont(this.font_normal_bold);
        label.setBounds(40, 155, 180, 25);
        panel.add(label);

        dc_from = new DateComponent(m_ic);
        dc_from.setBounds(40, 180, 120, 25);
        panel.add(dc_from);

        label = new JLabel(m_ic.getMessage("SELECT_ENDING_RANGE") + ":");
        label.setFont(this.font_normal_bold);
        label.setBounds(40, 225, 180, 25);
        panel.add(label);

        dc_to = new DateComponent(m_ic);
        dc_to.setBounds(40, 250, 120, 25);
        panel.add(dc_to);

        JButton button = new JButton("   " + m_ic.getMessage("OK"));
        // button.setFont(m_da.getFont(DataAccess.FONT_NORMAL));
        button.setActionCommand("ok");
        button.addActionListener(this);
        button.setIcon(m_da.getImageIcon_22x22("ok.png", this));
        button.setBounds(40, 340, 125, 25);
        panel.add(button);

        button = new JButton("   " + m_ic.getMessage("CANCEL"));
        // button.setFont(m_da.getFont(DataAccess.FONT_NORMAL));
        button.setActionCommand("cancel");
        button.setIcon(m_da.getImageIcon_22x22("cancel.png", this));
        button.addActionListener(this);
        button.setBounds(175, 340, 125, 25);
        panel.add(button);

        help_button = m_da.createHelpButtonByBounds(185, 310, 115, 25, this);
        panel.add(help_button);

        if (this.enable_help)
            m_da.enableHelp(this);

        /*
         * new JButton(m_ic.getMessage("CANCEL"));
         * button.setFont(m_da.getFont(DataAccess.FONT_NORMAL));
         * button.setActionCommand("cancel"); button.addActionListener(this);
         * button.setBounds(190, 240, 110, 25); panel.add(button);
         */

    }

    /*
     * Invoked when an action occurs.
     */
    /*
     * public void actionPerformed(ActionEvent e) { String action =
     * e.getActionCommand();
     * 
     * if (action.equals("cancel")) { m_actionDone = false; this.dispose(); }
     * else if (action.equals("ok")) { int yr =
     * ((Integer)sl_year.getValue()).intValue(); int mnth =
     * ((Integer)sl_month.getValue()).intValue();
     * 
     * 
     * MonthlyValues mv = m_da.getDb().getMonthlyValues(yr, mnth);
     * 
     * if (this.cb_template.getSelectedIndex()==0) { PrintSimpleMonthlyReport
     * psm = new PrintSimpleMonthlyReport(mv); displayPDF(psm.getName());
     * 
     * } else { PrintExtendedMonthlyReport psm = new
     * PrintExtendedMonthlyReport(mv); displayPDF(psm.getName()); }
     * 
     * 
     * / if (this.tfName.getText().trim().equals("")) {
     * JOptionPane.showMessageDialog(this, m_ic.getMessage("TYPE_NAME_BEFORE"),
     * m_ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE); return; }
     * m_actionDone = true;
     */
    /*
     * this.dispose(); } else
     * System.out.println("PrintingDialog: Unknown command: " + action);
     * 
     * }
     */

    /**
     * performAction
     */
    @Override
    public void performAction(ActionEvent e) throws Exception
    {
        String action = e.getActionCommand();
        if (action.equals("cancel"))
        {
            m_actionDone = false;
            this.dispose();
        }
        else if (action.equals("ok"))
        {
            this.startPrintingAction();
        }
            
            /*
            {

                System.out.println(this.dc_from.getDate() + " " + this.dc_to.getDate());
                
                DayValuesData dvd = m_da.getDb().getDayValuesData(this.dc_from.getDate(), this.dc_to.getDate()); //.getMonthlyValues(yr, mnth);
                
                
                PrintAbstract pa = null;
                
                if (this.cb_template.getSelectedIndex() == 0)
                {
                    pa = new PrintFoodMenuBase(dvd);
                }
                else if (this.cb_template.getSelectedIndex() == 1)
                {
                    pa = new PrintFoodMenuExt1(dvd);
                }
                else if (this.cb_template.getSelectedIndex() == 2)
                {
                    pa = new PrintFoodMenuExt2(dvd);
                }
                else if (this.cb_template.getSelectedIndex() == 3)
                {
                    pa = new PrintFoodMenuExt3(dvd);
                }
                
                
                
                displayPDF(pa.getNameWithPath());
            }
        } */
    }

    /**
     * Display PDF
     * 
     * @param name name must be full path to file name (not just name as it was in previous versions)
     * @throws Exception
     */
    public void displayPDF(String name) throws Exception
    {
        //Thread.sleep(2000);
        
        //File f = new File(".");
        //File f2 = f.getParentFile();
        
        //System.out.println("Parent: " + f2);
        
        //File fl = new File(".." + File.separator + "data" + File.separator + "temp" + File.separator);
        File file = new File(name);

        String pdf_viewer = this.getPdfViewer(); 
        //m_da.getSettings().getPdfVieverPath().replace('\\', '/');
        //String file_path = fl.getAbsolutePath().replace('\\', '/');

        this.setErrorMessages(m_ic.getMessage("PRINTING_SETTINGS_NOT_SET"), m_ic
                .getMessage("PRINTING_SETTINGS_NOT_SET_SOL"));

        if (pdf_viewer.equals(""))
        {
            throw new Exception(m_ic.getMessage("PRINTING_SETTINGS_NOT_SET"));
        }

        File acr = new File(pdf_viewer);

        if (!acr.exists())
        {
            throw new Exception(m_ic.getMessage("PRINTING_SETTINGS_NOT_SET"));
        }

        try
        {
            if (System.getProperty("os.name").toUpperCase().contains("WIN"))
            {
                //System.out.println("Windows found");
                Runtime.getRuntime().exec(
                    acr.getAbsoluteFile() + " \"" + file.getAbsolutePath() + "\"");
//                Runtime.getRuntime().exec(
//                    acr.getAbsoluteFile() + " \"" + fl.getAbsolutePath() + File.separator + name + "\"");
            }
            else
            {
                //System.out.println("Non-Windows found");
                Runtime.getRuntime().exec(
                    acr.getAbsoluteFile() + " " + file.getAbsolutePath() );
//                Runtime.getRuntime().exec(
//                    acr.getAbsoluteFile() + " " + fl.getAbsolutePath() + File.separator + name);
            }
            
            /*
            Runtime.getRuntime().exec(
                acr.getAbsoluteFile() + " \"" + fl.getAbsolutePath() + File.separator + name + "\""); */
            //System.out.println(pdf_viewer + " " + file_path + File.separator + name);
            System.out.println(pdf_viewer + " " + file.getAbsolutePath());
        }
        catch (RuntimeException ex)
        {
            this.setErrorMessages(m_ic.getMessage("PDF_VIEVER_RUN_ERROR"), null);
            System.out.println("RE running AcrobatReader: " + ex);
            throw ex;
        }
        catch (Exception ex)
        {
            this.setErrorMessages(m_ic.getMessage("PDF_VIEVER_RUN_ERROR"), null);
            System.out.println("Error running AcrobatReader: " + ex);
            throw ex;

        }
    }

    /**
     * Display PDF External (static method)
     * 
     * @param name
     */
/*    public static void displayPDFExternal(String name)
    {
        I18nControl ic = I18nControl.getInstance();

        File fl = new File(".." + File.separator + "data" + File.separator + "temp" + File.separator);

        String pdf_viewer = DataAccess.getInstance().getSettings().getPdfVieverPath().replace('\\', '/');
        String file_path = fl.getAbsolutePath().replace('\\', '/');

        if (pdf_viewer.equals(""))
        {
            System.out.println(ic.getMessage("PRINTING_SETTINGS_NOT_SET"));
            return;
        }

        File acr = new File(pdf_viewer);

        if (!acr.exists())
        {
            System.out.println(ic.getMessage("PRINTING_SETTINGS_NOT_SET"));
            return;
        }

        try
        {
            Runtime.getRuntime().exec(
                acr.getAbsoluteFile() + " \"" + fl.getAbsolutePath() + File.separator + name + "\"");
            System.out.println(pdf_viewer + " " + file_path + File.separator + name);
        }
        catch (RuntimeException ex)
        {
            // this.setErrorMessages(m_ic.getMessage("PDF_VIEVER_RUN_ERROR"),
            // null);
            System.out.println("RE running AcrobatReader: " + ex);
            // throw ex;
        }
        catch (Exception ex)
        {
            // this.setErrorMessages(m_ic.getMessage("PDF_VIEVER_RUN_ERROR"),
            // null);
            System.out.println("Error running AcrobatReader: " + ex);
            // throw ex;

        }
    }
*/
    
    /**
     * Was Action Successful
     * 
     * @return true if action was successful (dialog closed with OK)
     */
    public boolean actionSuccessful()
    {
        return m_actionDone;
    }

    
    /**
     * Get Action Results
     * 
     * @return String array of results
     */
    public String[] getActionResults()
    {
        String[] res = new String[3];

        if (m_actionDone)
            res[0] = "1";
        else
            res[0] = "0";

        res[1] = this.tfName.getText();
        res[2] = this.cb_template.getSelectedItem().toString();

        return res;
    }

    // ****************************************************************
    // ****** HelpCapable Implementation *****
    // ****************************************************************

    /**
     * getComponent - get component to which to attach help context
     */
    public Component getComponent()
    {
        return this.getRootPane();
    }

    /**
     * getHelpButton - get Help button
     */
    public JButton getHelpButton()
    {
        return this.help_button;
    }


    /**
     * getObject
     */
    @Override
    public Object getObject()
    {
        return this;
    }

    
    /**
     * getHelpId - get id for Help
     */
    public abstract String getHelpId();
    
    
    /**
     * Get Pdf Viewer (path to software)
     * 
     * @return
     */
    public abstract String getPdfViewer();

    
    /**
     * Get Report Types
     * 
     * @return
     */
    public abstract String[] getReportTypes();
    
    
    /**
     * Start Printing Action
     * 
     * @throws Exception 
     */
    public abstract void startPrintingAction() throws Exception;
    
    
}