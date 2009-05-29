package com.atech.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import com.atech.graphics.components.JDecimalTextField;
import com.atech.i18n.I18nControlAbstract;

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

public class ATSwingUtils
{
    public Color color_background, color_foreground;
    LineBorder border_line;
    static Font[] fonts;


    public static I18nControlAbstract i18n_control = null;

    // LF
    Hashtable<String, String> availableLF_full = null;
    //Object[] availableLF = null;
    // Object[] availableLang = null;
    // private LanguageInfo m_lang_info = null;

    //String selectedLF = null;
    //String subSelectedLF = null;

    
    public static void setI18nControl(I18nControlAbstract ic)
    {
        ATSwingUtils.i18n_control = ic;
    }
    
    
    public static void initLibrary()
    {
        loadFonts();
        
    }
    
    
    
    // ********************************************************
    // ****** Fonts *****
    // ********************************************************

    public static final int FONT_BIG_BOLD = 0;
    public static final int FONT_NORMAL = 1;
    public static final int FONT_NORMAL_BOLD = 2;
    public static final int FONT_NORMAL_P2 = 3;
    public static final int FONT_NORMAL_BOLD_P2 = 4;

    public static void loadFonts()
    {
        if (fonts!=null)
            return;
        
        
        fonts = new Font[5];
        fonts[0] = new Font("SansSerif", Font.BOLD, 22);
        fonts[1] = new Font("SansSerif", Font.PLAIN, 12);
        fonts[2] = new Font("SansSerif", Font.BOLD, 12);
        fonts[3] = new Font("SansSerif", Font.PLAIN, 14);
        fonts[4] = new Font("SansSerif", Font.BOLD, 14);
    }

    public static Font getFont(int font_id)
    {
        return fonts[font_id];
    }

    /**
     * Returns image. Used for extracting images from JAR files.
     *  
     * @param filename 
     * @param cmp 
     * @return 
     */
    public Image getImage(String filename, Component cmp)
    {
        Image img;

        InputStream is = this.getClass().getResourceAsStream(filename);

        if (is == null)
            System.out.println("Error reading image: " + filename);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            int c;
            while ((c = is.read()) >= 0)
                baos.write(c);

            // JDialog.getT
            // JFrame.getToolkit();
            img = cmp.getToolkit().createImage(baos.toByteArray());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return img;
    }

    // ********************************************************
    // ****** GUI *****
    // ********************************************************

    public static void centerJDialog(JDialog dialog, Container parent)
    {

        //System.out.println("centerJDialog: ");

        Rectangle rec = parent.getBounds();

        int x = rec.width / 2;
        x += (rec.x);

        int y = rec.height / 2;
        y += rec.y;

        x -= (dialog.getBounds().width / 2);
        y -= (dialog.getBounds().height / 2);

        dialog.getBounds().x = x;
        dialog.getBounds().y = y;

        dialog.setBounds(x, y, dialog.getBounds().width, dialog.getBounds().height);

    }

    // ********************************************************
    // ****** Look and Feel *****
    // ********************************************************
/*
    public void loadAvailableLFs()
    {

        availableLF_full = new Hashtable<String, String>();
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

        availableLF = new Object[info.length + 1];

        // ring selectedLF = null;
        // String subSelectedLF = null;

        int i;
        for (i = 0; i < info.length; i++)
        {
            String name = info[i].getName();
            String className = info[i].getClassName();

            availableLF_full.put(name, className);
            availableLF[i] = name;

            // System.out.println(humanReadableName);
        }

        availableLF_full.put("SkinLF", "com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
        availableLF[i] = "SkinLF";

    }

    public Object[] getAvailableLFs()
    {
        return availableLF;
    }
*/
    /* XX
        public static String[] getLFData()
        {
            String out[] = new String[2];

            try
            {
                Properties props = new Properties();

                FileInputStream in = new FileInputStream(pathPrefix  + "/data/PIS_Config.properties");
                props.load(in);

                out[0] = (String)props.get("LF_CLASS");
                out[1] = (String)props.get("SKINLF_SELECTED");

                return out;

            }
            catch(Exception ex)
            {
                System.out.println("DataAccess::getLFData::Exception> " + ex);
                return null;
            }
        }
    */

    // ********************************************************
    // ****** Colors *****
    // ********************************************************
    public void loadColors()
    {
        ColorUIResource cui = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("textText");
        this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui.getBlue(), cui.getAlpha());

        ColorUIResource cui2 = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("Label.background");
        this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2.getBlue(), cui2.getAlpha());

        this.border_line = new LineBorder(this.color_foreground);
    }

    public String[] getMonthsArray()
    {
        return null;
        // return this.months;
    }

    /*
    public void loadComboOptions()
    {

    yes_no_combo = new Object[2];
    
    yes_no_combo[0] = ATSwingUtils.i18n_control.getMessage("OPTION_YES");
    yes_no_combo[1] = ATSwingUtils.i18n_control.getMessage("OPTION_NO");

    Hashtable ht = m_db.getProductType(-1);

    typesAll = new Object[ht.size()];

    int i = 0;


    for(Enumeration en=ht.keys(); en.hasMoreElements(); )
    {

        String key = (String)en.nextElement();

        String key2 = "";

        if (key.length()==1)
        {
        key2 = "0"+key;
        }
        else
        key2 = key;



        typesAll[i] = key2 + " - " + ((ProductType)ht.get(key)).path.substring(1);
        i++;

    }

    Arrays.sort(typesAll);




    }
      */

    public String getDateString(int date)
    {

        // 20051012

        int year = date / 10000;
        int months = date - (year * 10000);

        months = months / 100;

        int days = date - (year * 10000) - (months * 100);

        if (year == 0)
        {
            return getLeadingZero(days, 2) + "/" + getLeadingZero(months, 2);
        }
        else
            return getLeadingZero(days, 2) + "/" + getLeadingZero(months, 2) + "/" + year;

    }

    public String getTimeString(int time)
    {

        int hours = time / 100;

        int min = time - hours * 100;

        return getLeadingZero(hours, 2) + ":" + getLeadingZero(min, 2);

    }

    public String getDateTimeString(long date)
    {
        return getDateTimeString(date, 1);
    }

    public String getDateTimeAsDateString(long date)
    {
        return getDateTimeString(date, 2);
    }

    public static final int DATE_TIME_ATECH_DATETIME = 1;
    public static final int DATE_TIME_ATECH_DATE = 2;
    public static final int DATE_TIME_ATECH_TIME = 3;

    public long getATDateTimeFromGC(GregorianCalendar gc, int type)
    {
        long dt = 0L;

        if (type == DATE_TIME_ATECH_DATETIME)
        {
            dt += gc.get(GregorianCalendar.YEAR) * 100000000L;
            dt += (gc.get(GregorianCalendar.MONTH) + 1) * 1000000L;
            dt += gc.get(GregorianCalendar.DAY_OF_MONTH) * 10000L;
            dt += gc.get(GregorianCalendar.HOUR_OF_DAY) * 100L;
            dt += gc.get(GregorianCalendar.MINUTE);
        }
        else if (type == DATE_TIME_ATECH_DATE)
        {
            dt += gc.get(GregorianCalendar.YEAR) * 10000L;
            dt += (gc.get(GregorianCalendar.MONTH) + 1) * 100L;
            dt += gc.get(GregorianCalendar.DAY_OF_MONTH);
        }
        else if (type == DATE_TIME_ATECH_TIME)
        {
            dt += gc.get(GregorianCalendar.HOUR_OF_DAY) * 100L;
            dt += gc.get(GregorianCalendar.MINUTE);
        }

        return dt;
    }

    public long getATDateTimeFromParts(int day, int month, int year, int hour, int minute, int type)
    {
        long dt = 0L;

        if (type == DATE_TIME_ATECH_DATETIME)
        {
            dt += year * 100000000L;
            dt += month * 1000000L;
            dt += day * 10000L;
            dt += hour * 100L;
            dt += minute;
        }
        else if (type == DATE_TIME_ATECH_DATE)
        {
            dt += year * 10000L;
            dt += month * 100L;
            dt += day;
        }
        else if (type == DATE_TIME_ATECH_TIME)
        {
            dt += hour * 100L;
            dt += minute;
        }

        return dt;
    }

    public long getDateFromATDate(long data)
    {
        // 200701011222
        int d2 = (int) (data / 10000);

        // long dd = data%10000;
        // data -= dd;

        // System.out.println("D2: " +d2);

        // System.out.println(data);
        return d2;
    }

    public String getDateTimeAsTimeString(long date)
    {
        return getDateTimeString(date, 3);
    }

    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)

    public final static int DT_DATETIME = 1;
    public final static int DT_DATE = 2;
    public final static int DT_TIME = 3;

    public String getDateTimeString(long dt, int ret_type)
    {

        // System.out.println("DT process: " + dt);
        /*
        int y = (int)(dt/10000000L);
        dt -= y*10000000L;

        int m = (int)(dt/1000000L);
        dt -= m*1000000L;

        int d = (int)(dt/10000L);
        dt -= d*10000L;

        int h = (int)(dt/100L);
        dt -= h*100L;

        int min = (int)dt;
        */

        // 200612051850
        int y = (int) (dt / 100000000L);
        dt -= y * 100000000L;

        int m = (int) (dt / 1000000L);
        dt -= m * 1000000L;

        int d = (int) (dt / 10000L);
        dt -= d * 10000L;

        int h = (int) (dt / 100L);
        dt -= h * 100L;

        int min = (int) dt;

        if (ret_type == DT_DATETIME)
        {
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y + "  " + getLeadingZero(h, 2) + ":" + getLeadingZero(min, 2);
        }
        else if (ret_type == DT_DATE)
        {
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y;
        }
        else
            return getLeadingZero(h, 2) + ":" + getLeadingZero(min, 2);

    }

    /* x
        public String getGCObjectFromDateTimeLong(long dt)
        {

    	int y = (int)(dt/100000000L);
    	dt -= y*100000000L;

    	int m = (int)(dt/1000000L);
    	dt -= m*1000000L;

    	int d = (int)(dt/10000L);
    	dt -= d*10000L;

    	int h = (int)(dt/100L);
    	dt -= h*100L;

    	int min = (int)dt;

    	GregorianCalendar gc1 = new GregorianCalendar();
    	//gc1.set(GregorianCalendar.

    	return null;

        }
    */

    public String getDateTimeString(int date, int time)
    {

        return getDateString(date) + " " + getTimeString(time);

    }

    public String getLeadingZero(int number, int places)
    {

        String nn = "" + number;

        while (nn.length() < places)
        {
            nn = "0" + nn;
        }

        return nn;

    }

    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }

    public static void notImplemented(String source)
    {
        System.out.println("Not Implemented: " + source);
        // JOptionPane.showMessageDialog(parent, "Not Implemented: \n" +
        // source);
    }

    public static void notImplemented(java.awt.Component parent, String source)
    {
        System.out.println("Not Implemented: " + source);
        JOptionPane.showMessageDialog(parent, "Not Implemented: \n" + source);
    }

    public JMenu createMenu(String name, String tool_tip, JMenuBar bar)
    {
        JMenu item = new JMenu(ATSwingUtils.i18n_control.getMessageWithoutMnemonic(name));
        item.setMnemonic(i18n_control.getMnemonic(name));

        if (tool_tip != null)
        {
            item.setToolTipText(tool_tip);
        }

        bar.add(item);

        return item;
    }

    public JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command, String icon_small)
    {

        JMenuItem mi = new JMenuItem();

        mi.setText(i18n_control.getMessageWithoutMnemonic(name));
        mi.setActionCommand(action_command);

        if (tip != null)
        {
            mi.setToolTipText(i18n_control.getMessage(tip));
        }

        if (icon_small != null)
        {
            mi.setIcon(new ImageIcon(getClass().getResource(icon_small)));
        }

        if (menu != null)
            menu.add(mi);

        return mi;

    }

    // ********************************************************
    // ******              Swing Components               *****
    // ********************************************************
    
    
    public static JDecimalTextField getNumericTextField(int columns, int decimal_places, Object value, int x, int y, int width, int height, Container cont)
    {
        JDecimalTextField tf = new JDecimalTextField(value, decimal_places);
        tf.setBounds(x, y, width, height);

        cont.add(tf);

        return tf;
    }
    

    public static JLabel getLabel(String text, int x, int y, int width, int height, Container cont)
    {
        return getLabel(text, x, y, width, height, cont, null);
    }

    
    public static JLabel getLabel(String text, int x, int y, int width, int height, Container cont, int font_id)
    {
        return getLabel(text, x, y, width, height, cont, getFont(font_id));
    }
    
    

    public static JLabel getLabel(String text, int x, int y, int width, int height, Container cont, Font font)
    {
        JLabel label_1 = new JLabel();
        
        if (text!=null)
            label_1.setText(text);
        
        label_1.setBounds(x, y, width, height);
        
        if (font!=null)
            label_1.setFont(font);
        
        cont.add(label_1);
        
        return label_1;
    }

    
    
    public static JLabel getTitleLabel(String text, int x, int y, int width, int height, Container cont, int font_id)
    {
        return getTitleLabel(text, x, y, width, height, cont, getFont(font_id));
    }
    
    
    public static JLabel getTitleLabel(String text, int x, int y, int width, int height, Container cont, Font font)
    {
        JLabel label_1 = getLabel(text, x, y, width, height, cont, font);
        label_1.setHorizontalAlignment(JLabel.CENTER);
        return label_1;
    } 
    
    public static JComboBox getComboBox(Vector<?> data, int x, int y, int width, int height, Container cont, int font_id)
    {
        return ATSwingUtils.getComboBox(data, x, y, width, height, cont, getFont(font_id));
        
    }
    

    public static JComboBox getComboBox(Vector<?> data, int x, int y, int width, int height, Container cont, Font font)
    {
        JComboBox cb = new JComboBox(data);
        cb.setBounds(x, y, width, height);
        cb.setFont(font);
        cont.add(cb);
        
        return cb;
    }
    

    public static JComboBox getComboBox(Object[] data, int x, int y, int width, int height, Container cont, int font_id)
    {
        return ATSwingUtils.getComboBox(data, x, y, width, height, cont, getFont(font_id));
        
    }
    

    public static JComboBox getComboBox(Object[] data, int x, int y, int width, int height, Container cont, Font font)
    {
        JComboBox cb = new JComboBox(data);
        cb.setBounds(x, y, width, height);
        cb.setFont(font);
        cont.add(cb);
        
        return cb;
    }
    
    
    public static JButton getButton(String text, int x, int y, int width, int height, Container cont, int font_id, String icon_name, String action_cmd, ActionListener al, ATDataAccessAbstract da)
    {
        return ATSwingUtils.getButton(text, x, y, width, height, cont, getFont(font_id), icon_name, action_cmd, al, da);
    }
    

    public static JButton getButton(String text, int x, int y, int width, int height, Container cont, Font font, String icon_name, String action_cmd, ActionListener al, ATDataAccessAbstract da)
    {
        JButton button = new JButton(text);
        if (icon_name !=null)
            button.setIcon(da.getImageIcon_22x22(icon_name, cont));
        button.setActionCommand(action_cmd);
        button.setFont(font);
        button.setBounds(x, y, width, height);
        button.addActionListener(al);
        cont.add(button);
        
        return button;
    }
    
    
    
    public static JCheckBox getCheckBox(String text, int x, int y, int width, int height, Container cont, int font_id)
    {
        return ATSwingUtils.getCheckBox(text, x, y, width, height, cont, getFont(font_id));
    }
    

    public static JCheckBox getCheckBox(String text, int x, int y, int width, int height, Container cont, Font font)
    {
        JCheckBox chb = new JCheckBox(text);
        chb.setBounds(x, y, width, height); 
        chb.setFont(font);
        chb.setSelected(false);
        
        cont.add(chb);
        
        return chb;
    }
    
    
    public static JPanel getPanel(int x, int y, int width, int height, LayoutManager layout, Border border, Container cont)
    {
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setBorder(border);
        panel.setBounds(x, y, width, height);
        
        if (cont!=null)
            cont.add(panel);
        
        return panel;
    }
    
    
    
    
    public static JTextField getTextField(String text, int x, int y, int width, int height, Container cont)
    {
        JTextField text_1 = new JTextField();
        
        if (text!=null)
            text_1.setText(text);
        
        text_1.setBounds(x, y, width, height);
        
        cont.add(text_1);
        
        return text_1;
    }
    
    

    public static void addComponent(JComponent comp, int posX, int posY, int width, int height, JPanel parent)
    {
        addComponent(comp, posX, posY, width, height, FONT_NORMAL, parent);
    }

    
    public static void addComponent(JComponent comp, int posX, int posY, int width, int height, int font_id, JPanel parent)
    {
        comp.setBounds(posX, posY, width, height);
        comp.setFont(getFont(font_id));
        parent.add(comp);
    }
    
    
    
    
    
    /**
     * Create Menu
     * 
     * @param name
     * @param tool_tip
     * @param ic
     * @return
     */
    public static JMenu createMenu(String name, String tool_tip, I18nControlAbstract ic)
    {
        JMenu item = new JMenu(ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(ic.getMnemonic(name));

        if (tool_tip != null)
        {
            item.setToolTipText(tool_tip);
        }

        return item;
    }
    
    
    /**
     * Create Menu Item 
     * 
     * @param menu
     * @param name
     * @param tip
     * @param action_command
     * @param al
     * @param icon_small
     * @param ic
     * @param da
     * @param c
     */
    public static JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command, ActionListener al, String icon_small, I18nControlAbstract ic, ATDataAccessAbstract da, Container c)
    {
        JMenuItem mi = new JMenuItem(ic.getMessageWithoutMnemonic(name));
        mi.setMnemonic(ic.getMnemonic(name));
        
        mi.setActionCommand(action_command);
        mi.addActionListener(al);
        
        if (tip != null)
        {
            mi.setToolTipText(tip);
        }

        if (icon_small != null)
        {
            mi.setIcon(da.getImageIcon(icon_small, 15, 15, c));
        }

        if (menu != null)
            menu.add(mi);

        return mi;
        // return action;
    }
 
    
    
    
    
    
    
    
    
}