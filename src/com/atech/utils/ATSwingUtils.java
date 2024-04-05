package com.atech.utils;

import com.atech.graphics.components.JDecimalTextField;
import com.atech.i18n.I18nControlAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.*;

// TODO: Auto-generated Javadoc

/**
 * This file is part of ATech Tools library.
 * <p>
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007-22  Andy (Aleksander) Rozman (Atech-Software)
 * <p>
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * <p>
 * <p>
 * For additional information about this project please visit our project site on
 * http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 *
 * @author Andy
 */

public class ATSwingUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ATSwingUtils.class);

    /**
     * The Constant FONT_BIG_BOLD.
     */
    public static final int FONT_BIG_BOLD = 0;
    public static final int FONT_BIG_NORMAL = 6;

    /**
     * The Constant FONT_NORMAL.
     */
    public static final int FONT_NORMAL = 1;
    /**
     * The Constant FONT_NORMAL_BOLD.
     */
    public static final int FONT_NORMAL_BOLD = 2;
    /**
     * The Constant FONT_NORMAL_P2.
     */
    public static final int FONT_NORMAL_P2 = 3;
    /**
     * The Constant FONT_NORMAL_BOLD_P2.
     */
    public static final int FONT_NORMAL_BOLD_P2 = 4;

    // LF
    /**
     * The Constant FONT_NORMAL_BOLD_P2.
     */
    public static final int FONT_NORMAL_SMALLER = 5;

    // Object[] availableLF = null;
    // Object[] availableLang = null;
    // private LanguageInfo m_lang_info = null;

    // String selectedLF = null;
    // String subSelectedLF = null;
    /**
     * The Constant DATE_TIME_ATECH_DATETIME.
     */
    public static final int DATE_TIME_ATECH_DATETIME = 1;
    /**
     * The Constant DATE_TIME_ATECH_DATE.
     */
    public static final int DATE_TIME_ATECH_DATE = 2;

    // ********************************************************
    // ****** Fonts *****
    // ********************************************************
    /**
     * The Constant DATE_TIME_ATECH_TIME.
     */
    public static final int DATE_TIME_ATECH_TIME = 3;
    /**
     * The Constant DT_DATETIME.
     */
    public final static int DT_DATETIME = 1;
    /**
     * The Constant DT_DATE.
     */
    public final static int DT_DATE = 2;
    /**
     * The Constant DT_TIME.
     */
    public final static int DT_TIME = 3;
    public static final int DIALOG_INFO = 1;
    public static final int DIALOG_WARNING = 2;
    public static final int DIALOG_ERROR = 3;
    /**
     * The fonts.
     */
    static Font[] fonts;
    private static I18nControlAbstract i18n_control = null;

    // ********************************************************
    // ****** GUI *****
    // ********************************************************
    /**
     * The color_background.
     */
    public Color color_background;

    // ********************************************************
    // ****** Look and Feel *****
    // ********************************************************
    /*
     * public void loadAvailableLFs()
     * {
     * availableLF_full = new Hashtable<String, String>();
     * UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
     * availableLF = new Object[info.length + 1];
     * // ring selectedLF = null;
     * // String subSelectedLF = null;
     * int i;
     * for (i = 0; i < info.length; i++)
     * {
     * String name = info[i].getName();
     * String className = info[i].getClassName();
     * availableLF_full.put(name, className);
     * availableLF[i] = name;
     * // System.out.println(humanReadableName);
     * }
     * availableLF_full.put("SkinLF",
     * "com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
     * availableLF[i] = "SkinLF";
     * }
     * public Object[] getAvailableLFs()
     * {
     * return availableLF;
     * }
     */
    /*
     * XX
     * public static String[] getLFData()
     * {
     * String out[] = new String[2];
     * try
     * {
     * Properties props = new Properties();
     * FileInputStream in = new FileInputStream(pathPrefix +
     * "/data/PIS_Config.properties");
     * props.load(in);
     * out[0] = (String)props.get("LF_CLASS");
     * out[1] = (String)props.get("SKINLF_SELECTED");
     * return out;
     * }
     * catch(Exception ex)
     * {
     * System.out.println("DataAccess::getLFData::Exception> " + ex);
     * return null;
     * }
     * }
     */

    // ********************************************************
    // ****** Colors *****
    // ********************************************************
    /**
     * The color_foreground.
     */
    public Color color_foreground;

    /*
     * public void loadComboOptions()
     * {
     * yes_no_combo = new Object[2];
     * yes_no_combo[0] = ATSwingUtils.i18n_control.getMessage("OPTION_YES");
     * yes_no_combo[1] = ATSwingUtils.i18n_control.getMessage("OPTION_NO");
     * Hashtable ht = m_db.getProductType(-1);
     * typesAll = new Object[ht.size()];
     * int i = 0;
     * for(Enumeration en=ht.keys(); en.hasMoreElements(); )
     * {
     * String key = (String)en.nextElement();
     * String key2 = "";
     * if (key.length()==1)
     * {
     * key2 = "0"+key;
     * }
     * else
     * key2 = key;
     * typesAll[i] = key2 + " - " +
     * ((ProductType)ht.get(key)).path.substring(1);
     * i++;
     * }
     * Arrays.sort(typesAll);
     * }
     */
    /**
     * The border_line.
     */
    LineBorder border_line;
    /**
     * The available l f_full.
     */
    Hashtable<String, String> availableLF_full = null;


    /**
     * Sets the i18n control.
     *
     * @param ic the new i18n control
     */
    public static void setI18nControl(I18nControlAbstract ic) {
        ATSwingUtils.i18n_control = ic;
    }


    /**
     * Inits the library.
     */
    public static void initLibrary() {
        loadFonts();

    }


    /**
     * Load fonts.
     */
    public static void loadFonts() {
        if (fonts != null)
            return;

        JLabel label = new JLabel();
        Font f = label.getFont();
        fonts = new Font[7];

        fonts[0] = f.deriveFont(Font.BOLD, 22);
        fonts[1] = f.deriveFont(Font.PLAIN, 12);
        fonts[2] = f.deriveFont(Font.BOLD, 12);
        fonts[3] = f.deriveFont(Font.PLAIN, 14);
        fonts[4] = f.deriveFont(Font.BOLD, 14);
        fonts[5] = f.deriveFont(Font.PLAIN, 11);
        fonts[6] = f.deriveFont(Font.PLAIN, 22);

        /*
         * fonts[0] = new Font("SansSerif", Font.BOLD, 22);
         * fonts[1] = new Font("SansSerif", Font.PLAIN, 12);
         * fonts[2] = new Font("SansSerif", Font.BOLD, 12);
         * fonts[3] = new Font("SansSerif", Font.PLAIN, 14);
         * fonts[4] = new Font("SansSerif", Font.BOLD, 14);
         * fonts[5] = new Font("SansSerif", Font.PLAIN, 11);
         */

    }


    /**
     * Gets the font.
     *
     * @param font_id the font_id
     * @return the font
     */
    public static Font getFont(int font_id) {
        return fonts[font_id];
    }


    /**
     * Returns image. Used for extracting images from JAR files.
     *
     * @param filename
     * @param cmp
     * @return
     */
    public static Image getImage(String filename, Component cmp) {
        Image img;

        //System.out.println("Component: " + cmp);
        //System.out.println("Filename: " + filename);

        //InputStream is = cmp.getClass().getResourceAsStream(filename);
        InputStream is = ATSwingUtils.class.getResourceAsStream(filename);

        if (is == null) {
            System.out.println("Error reading image: " + filename);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int c;
            while ((c = is.read()) >= 0) {
                baos.write(c);
            }

            // JDialog.getT
            // JFrame.getToolkit();
            img = cmp.getToolkit().createImage(baos.toByteArray());
        } catch (Exception ex) {
            LOG.error("Error loading image: [filename={}, component={}]: {}", filename, cmp.getClass().getSimpleName(),
                    ex.getMessage(), ex);
            // ex.printStackTrace();
            return null;
        }
        return img;
    }


    /**
     * Center j dialog.
     *
     * @param dialog the dialog
     * @param parent the parent
     */
    public static void centerJDialog(JDialog dialog, Container parent) {

        // System.out.println("centerJDialog: ");

        Rectangle rec = parent.getBounds();

        int x = rec.width / 2;
        x += rec.x;

        int y = rec.height / 2;
        y += rec.y;

        // System.out.println("x=" + x + ", y=" + y);

        x -= dialog.getBounds().width / 2;
        y -= dialog.getBounds().height / 2;

        // System.out.println("x=" + x + ", y=" + y);

        dialog.getBounds().x = x;
        dialog.getBounds().y = y;

        dialog.setBounds(x, y, dialog.getBounds().width, dialog.getBounds().height);

    }


    /**
     * Not implemented.
     *
     * @param source the source
     */
    public static void notImplemented(String source) {
        System.out.println("Not Implemented: " + source);
        // JOptionPane.showMessageDialog(parent, "Not Implemented: \n" +
        // source);
    }


    /**
     * Not implemented.
     *
     * @param parent the parent
     * @param source the source
     */
    public static void notImplemented(java.awt.Component parent, String source) {
        System.out.println("Not Implemented: " + source);
        JOptionPane.showMessageDialog(parent, "Not Implemented: \n" + source);
    }


    /**
     * Creates the menu.
     *
     * @param name     the name
     * @param tool_tip the tool_tip
     * @param bar      the bar
     * @return the j menu
     */
    public static JMenu createMenu(String name, String tool_tip, JMenuBar bar) {
        JMenu item = new JMenu(ATSwingUtils.i18n_control.getMessageWithoutMnemonic(name));
        item.setMnemonic(i18n_control.getMnemonic(name));

        if (tool_tip != null) {
            item.setToolTipText(tool_tip);
        }

        bar.add(item);

        return item;
    }


    // ret_type = 1 (Date and time)
    // ret_type = 2 (Date)
    // ret_type = 3 (Time)

    public static JMenu createMenu(String name, String tool_tip, JMenuBar bar, I18nControlAbstract ic) {
        return createMenu(name, tool_tip, bar, ic, -1);
    }


    /**
     * Creates the menu.
     *
     * @param name     the name
     * @param tool_tip the tool_tip
     * @param bar      the bar
     * @param ic
     * @param font_id
     * @return the j menu
     */
    public static JMenu createMenu(String name, String tool_tip, JMenuBar bar, I18nControlAbstract ic, int font_id) {
        JMenu item = new JMenu(ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(ic.getMnemonic(name));

        if (font_id != -1) {
            item.setFont(getFont(font_id));
        }

        if (tool_tip != null && tool_tip.trim().length() > 0) {
            item.setToolTipText(ic.getMessage(tool_tip));
        }

        if (bar != null) {
            bar.add(item);
        }

        return item;
    }


    /**
     * Creates the menu.
     *
     * @param name     the name
     * @param tool_tip the tool_tip
     * @param menu
     * @return the j menu
     */
    public static JMenu createMenu(String name, String tool_tip, JMenu menu) {
        return ATSwingUtils.createMenu(name, tool_tip, menu, ATSwingUtils.i18n_control);
    }


    /**
     * Creates the menu.
     *
     * @param name     the name
     * @param tool_tip the tool_tip
     * @param menu
     * @param ic
     * @return the j menu
     */
    public static JMenu createMenu(String name, String tool_tip, JMenu menu, I18nControlAbstract ic) {
        JMenu item = new JMenu(ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(ic.getMnemonic(name));

        if (tool_tip != null && tool_tip.trim().length() > 0) {
            item.setToolTipText(ic.getMessage(tool_tip));
        }

        // bar.add(item);

        menu.add(item);
        return item;
    }


    /*
     * x
     * public String getGCObjectFromDateTimeLong(long dt)
     * {
     * int y = (int)(dt/100000000L);
     * dt -= y*100000000L;
     * int m = (int)(dt/1000000L);
     * dt -= m*1000000L;
     * int d = (int)(dt/10000L);
     * dt -= d*10000L;
     * int h = (int)(dt/100L);
     * dt -= h*100L;
     * int min = (int)dt;
     * GregorianCalendar gc1 = new GregorianCalendar();
     * //gc1.set(GregorianCalendar.
     * return null;
     * }
     */

    /**
     * Gets the numeric text field.
     *
     * @param decimal_places the decimal_places
     * @param value          the value
     * @param x              the x
     * @param y              the y
     * @param width          the width
     * @param height         the height
     * @param cont           the cont
     * @return the numeric text field
     */
    public static JDecimalTextField getNumericTextField(int decimal_places, Object value, int x, int y, int width,
                                                        int height, Container cont) {
        return getNumericTextField(decimal_places, value, x, y, width, height, cont, null);
    }


    public static JDecimalTextField getNumericTextField(int decimal_places, Object value, int x, int y, int width,
                                                        int height, Container cont, int fontId) {
        return getNumericTextField(decimal_places, value, x, y, width, height, cont, getFont(fontId));
    }


    public static JDecimalTextField getNumericTextField(int decimal_places, Object value, int x, int y, int width,
                                                        int height, Container cont, Font font) {
        JDecimalTextField tf = new JDecimalTextField(value, decimal_places);
        tf.setBounds(x, y, width, height);

        if (font != null) {
            tf.setFont(font);
        }

        cont.add(tf);

        return tf;
    }


    /**
     * Gets the label.
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @return the label
     */
    public static JLabel getLabel(String text, int x, int y, int width, int height, Container cont) {
        return getLabel(text, x, y, width, height, cont, null);
    }


    /**
     * Gets the label.
     *
     * @param text    the text
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param cont    the cont
     * @param font_id the font_id
     * @return the label
     */
    public static JLabel getLabel(String text, int x, int y, int width, int height, Container cont, int font_id) {
        return getLabel(text, x, y, width, height, cont, getFont(font_id));
    }


    /**
     * Gets the label.
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @param font   the font
     * @return the label
     */
    public static JLabel getLabel(String text, Integer x, Integer y, Integer width, Integer height, Container cont,
                                  Font font) {
        JLabel label_1 = new JLabel();

        if (text != null) {
            label_1.setText(text);
        }

        if (x != null)
            label_1.setBounds(x, y, width, height);

        if (font != null) {
            label_1.setFont(font);
        }

        if (cont != null)
            cont.add(label_1);

        return label_1;
    }


    public static JLabel getLabel(String text, Container cont, Font font) {
        JLabel label_1 = new JLabel();

        if (text != null) {
            label_1.setText(text);
        }

        if (font != null) {
            label_1.setFont(font);
        }

        cont.add(label_1);

        return label_1;
    }

    public static JLabel getLabel(String text, Container cont, Integer fontId) {
        JLabel label_1 = new JLabel();

        if (text != null) {
            label_1.setText(text);
        }

        if (fontId != null) {
            label_1.setFont(getFont(fontId));
        }

        if (cont != null) {
            cont.add(label_1);
        }

        return label_1;
    }


    public static JLabel getLabel(String text, Container cont, String layoutConstraints, int fontId) {
        return getLabel(text, cont, layoutConstraints, getFont(fontId));
    }


    public static JLabel getLabel(String text, Container cont, String layoutConstraints, Font font) {
        JLabel label_1 = new JLabel();

        if (text != null) {
            label_1.setText(text);
        }

        if (font != null) {
            label_1.setFont(font);
        }

        cont.add(label_1, layoutConstraints);

        return label_1;
    }


    /**
     * Gets the title label.
     *
     * @param text    the text
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param cont    the cont
     * @param font_id the font_id
     * @return the title label
     */
    public static JLabel getTitleLabel(String text, int x, int y, int width, int height, Container cont, int font_id) {
        return getTitleLabel(text, x, y, width, height, cont, getFont(font_id));
    }


    /**
     * Gets the title label.
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @param font   the font
     * @return the title label
     */
    public static JLabel getTitleLabel(String text, int x, int y, int width, int height, Container cont, Font font) {
        JLabel label_1 = getLabel(text, x, y, width, height, cont, font);
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        return label_1;
    }


    public static JLabel getTitleLabel(String text, int fontId) {
        JLabel label_1 = getLabel(text, null, null, null, null, null, getFont(fontId));
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setVerticalAlignment(SwingConstants.CENTER);
        return label_1;
    }


    public static JLabel getTitleLabel(String text, Font font) {
        JLabel label_1 = getLabel(text, null, null, null, null, null, font);
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setVerticalAlignment(SwingConstants.CENTER);
        return label_1;
    }


    /**
     * Gets the combo box.
     *
     * @param data    the data
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param cont    the cont
     * @param font_id the font_id
     * @return the combo box
     */
    public static JComboBox getComboBox(Vector<?> data, int x, int y, int width, int height, Container cont, int font_id) {
        return ATSwingUtils.getComboBox(data, x, y, width, height, cont, getFont(font_id));

    }


    /**
     * Gets the combo box.
     *
     * @param data   the data
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @param font   the font
     * @return the combo box
     */
    public static JComboBox getComboBox(Vector<?> data, int x, int y, int width, int height, Container cont, Font font) {
        JComboBox cb = new JComboBox(data);
        cb.setBounds(x, y, width, height);
        cb.setFont(font);
        cont.add(cb);

        return cb;
    }


    /**
     * Gets the combo box.
     *
     * @param data    the data
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param cont    the cont
     * @param font_id the font_id
     * @return the combo box
     */
    public static JComboBox getComboBox(Object[] data, int x, int y, int width, int height, Container cont, int font_id) {
        return ATSwingUtils.getComboBox(data, x, y, width, height, cont, getFont(font_id));
    }


    // public static <E extends Object> JComboBox<E> getComboBox(Vector<E> data, Class<E> clazz, int
    // x, int y, int width,
    // int height, Container cont, int font_id)
    // {
    // return ATSwingUtils.getComboBox(data, clazz, x, y, width, height, cont, getFont(font_id));
    // }
    //
    //
    // public static <E extends Object> JComboBox<E> getComboBox(Vector<E> data, Class<E> clazz,
    // Container cont,
    // Object layoutConstraints, int font_id)
    // {
    // return ATSwingUtils.getComboBox(data, clazz, cont, layoutConstraints, getFont(font_id));
    // }
    //
    //
    //
    //
    // public static <E extends Object> JComboBox<E> getComboBox(Vector<E> data, Class<E> clazz, int
    // x, int y, int width,
    // int height, Container cont, Font font)
    // {
    // JComboBox<E> cb = new JComboBox<E>(data);
    // cb.setBounds(x, y, width, height);
    // cb.setFont(font);
    // cont.add(cb);
    //
    // return cb;
    // }
    //
    //
    // public static <E extends Object> JComboBox<E> getComboBox(Vector<E> data, Class<E> clazz,
    // Container cont,
    // Object layoutConstraints, Font font)
    // {
    // JComboBox<E> cb = new JComboBox<E>(data);
    // cb.setFont(font);
    //
    // addToContainer(cb, cont, layoutConstraints);
    //
    // return cb;
    // }

    // ********************************************************
    // ****** Swing Components *****
    // ********************************************************

    /**
     * Gets the combo box.
     *
     * @param data   the data
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @param font   the font
     * @return the combo box
     */
    public static JComboBox getComboBox(Object[] data, int x, int y, int width, int height, Container cont, Font font) {
        JComboBox cb = new JComboBox(data);
        cb.setBounds(x, y, width, height);
        cb.setFont(font);
        cont.add(cb);

        return cb;
    }


    /**
     * Gets the button.
     *
     * @param text       the text
     * @param x          the x
     * @param y          the y
     * @param width      the width
     * @param height     the height
     * @param cont       the cont
     * @param font_id    the font_id
     * @param icon_name  the icon_name
     * @param action_cmd the action_cmd
     * @param al         the al
     * @param da         the da
     * @return the button
     */
    public static JButton getButton(String text, int x, int y, int width, int height, Container cont, int font_id,
                                    String icon_name, String action_cmd, ActionListener al, ATDataAccessAbstract da) {
        return ATSwingUtils.getButton(text, x, y, width, height, cont, getFont(font_id), icon_name, action_cmd, al, da);
    }


    public static JButton getButton(String text, int x, int y, int width, int height, Container cont, int font_id,
                                    String icon_name, String action_cmd, ActionListener al, ATDataAccessAbstract da, Container containerForImage) {
        return ATSwingUtils.getButton(text, x, y, width, height, cont, getFont(font_id), icon_name, action_cmd, al, da,
                containerForImage);
    }


    /**
     * Gets the button.
     *
     * @param text       the text
     * @param x          the x
     * @param y          the y
     * @param width      the width
     * @param height     the height
     * @param cont       the cont
     * @param font       the font
     * @param icon_name  the icon_name
     * @param action_cmd the action_cmd
     * @param al         the al
     * @param da         the da
     * @return the button
     */
    public static JButton getButton(String text, int x, int y, int width, int height, Container cont, Font font,
                                    String icon_name, String action_cmd, ActionListener al, ATDataAccessAbstract da) {
        return ATSwingUtils.getButton(text, x, y, width, height, cont, null, font, icon_name, action_cmd, al, da, null,
                cont);
    }


    public static JButton getButton(String text, int x, int y, int width, int height, Container cont, Font font,
                                    String icon_name, String action_cmd, ActionListener al, ATDataAccessAbstract da, Container containerForImage) {
        return ATSwingUtils.getButton(text, x, y, width, height, cont, null, font, icon_name, action_cmd, al, da, null,
                containerForImage);
    }


    public static JButton getButton(String text, Font font, String icon_name, String action_cmd, ActionListener al,
                                    ATDataAccessAbstract da) {
        return ATSwingUtils.getButton(text, null, null, null, null, null, null, font, icon_name, action_cmd, al, da,
                null, null);
    }


    public static JButton getButton(String text, int font_id, String icon_name, String action_cmd, ActionListener al,
                                    ATDataAccessAbstract da) {
        return ATSwingUtils.getButton(text, null, null, null, null, null, null, getFont(font_id), icon_name,
                action_cmd, al, da, null, null);
    }

    public static JButton getButton(String text, int font_id, String icon_name, String action_cmd, ActionListener al,
                                    ATDataAccessAbstract da, Container container) {
        return ATSwingUtils.getButton(text, null, null, null, null, null, null, getFont(font_id), icon_name,
                action_cmd, al, da, null, container);
    }


    public static JButton getButton(String text, Container container, Object layoutConstraints, int font_id,
                                    String icon_name, ATDataAccessAbstract da) {
        return ATSwingUtils.getButton(text, null, null, null, null, container, layoutConstraints, getFont(font_id),
                icon_name, null, null, da, null, container);
    }


    /**
     * getButton - for Apps without ATDataAccessAbstract and with TableLayout
     *
     * @param text
     * @param container
     * @param layoutConstraints
     * @param font_id
     * @param icon_name
     * @return
     */
    public static JButton getButton(String text, Container container, Object layoutConstraints, int font_id,
                                    String icon_name, String actionCommand, ActionListener al) {
        JButton button = null;
        int[] icon_size = null; // FIXME

        Font font = getFont(font_id);


        if (text == null || text.trim().length() == 0) {
            button = new JButton();
        } else {
            button = new JButton(text);
        }

        if (icon_name != null) {
            if (icon_size == null) {
                button.setIcon(getImageIcon_22x22("./", icon_name, container));
            } else {
                //button.setIcon(getImageIcon(icon_name, icon_size[0], icon_size[1], containerForImage, da));
            }
        }
        button.setActionCommand(actionCommand);
        button.setFont(font);

//        if (x != null) {
//            button.setBounds(x, y, width, height);
//        }

        button.addActionListener(al);

        addToContainer(button, container, layoutConstraints);

        return button;
    }


    public static JButton getIconButton(int x, int y, int width, int height, String tooltip, String icon_name,
                                        int icon_width, int icon_height, String action_cmd, ActionListener al, Container container,
                                        ATDataAccessAbstract da) {
        JButton button = new JButton(getImageIcon(da.getImagesRoot(), icon_name, icon_width, icon_height, container));
        button.addActionListener(al);
        button.setActionCommand(action_cmd);
        button.setToolTipText(tooltip);
        button.setBounds(x, y, width, height);

        addToContainer(button, container, null);

        container.add(button, null);

        return button;
    }


    /**
     * @param text
     * @param x
     * @param y
     * @param width
     * @param height
     * @param cont
     * @param font_id
     * @param icon_name
     * @param action_cmd
     * @param al
     * @param da
     * @param icon_size
     * @return
     */
    public static JButton getButton(String text, int x, int y, int width, int height, Container cont, int font_id,
                                    String icon_name, String action_cmd, ActionListener al, ATDataAccessAbstract da, int[] icon_size) {
        return ATSwingUtils.getButton(text, x, y, width, height, cont, null, getFont(font_id), icon_name, action_cmd,
                al, da, icon_size, cont);
    }


    /**
     * Gets the button.
     *
     * @param text       the text
     * @param x          the x
     * @param y          the y
     * @param width      the width
     * @param height     the height
     * @param container  the cont
     * @param font       the font
     * @param icon_name  the icon_name
     * @param action_cmd the action_cmd
     * @param al         the al
     * @param da         the da
     * @param icon_size
     * @return the button
     */
    public static JButton getButton(String text, Integer x, Integer y, Integer width, Integer height,
                                    Container container, Object layoutConstraints, Font font, String icon_name, String action_cmd,
                                    ActionListener al, ATDataAccessAbstract da, int[] icon_size, Container containerForImage) {
        JButton button = null;

        if (text == null || text.trim().isEmpty()) {
            button = new JButton();
        } else {
            button = new JButton(text);
        }

        if (icon_name != null) {
            if (icon_size == null) {
                button.setIcon(getImageIcon_22x22(icon_name, containerForImage, da));
            } else {
                button.setIcon(getImageIcon(icon_name, icon_size[0], icon_size[1], containerForImage, da));
            }
        }
        button.setActionCommand(action_cmd);
        button.setFont(font);

        if (x != null) {
            button.setBounds(x, y, width, height);
        }

        button.addActionListener(al);

        addToContainer(button, container, layoutConstraints);

        return button;
    }


    /**
     * Gets the check box.
     *
     * @param text    the text
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param cont    the cont
     * @param font_id the font_id
     * @return the check box
     */
    public static JCheckBox getCheckBox(String text, int x, int y, int width, int height, Container cont, int font_id) {
        return ATSwingUtils.getCheckBox(text, x, y, width, height, cont, getFont(font_id));
    }


    /**
     * Gets the check box.
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @param font   the font
     * @return the check box
     */
    public static JCheckBox getCheckBox(String text, int x, int y, int width, int height, Container cont, Font font) {
        JCheckBox chb = new JCheckBox(text);
        chb.setBounds(x, y, width, height);
        chb.setFont(font);
        chb.setSelected(false);

        cont.add(chb);

        return chb;
    }


    /**
     * Gets the panel.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param layout the layout
     * @param border the border
     * @param cont   the cont
     * @return the panel
     */
    public static JPanel getPanel(int x, int y, int width, int height, LayoutManager layout, Border border,
                                  Container cont) {
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setBorder(border);
        panel.setBounds(x, y, width, height);

        if (cont != null) {
            cont.add(panel);
        }

        return panel;
    }


    /**
     * Gets the text field.
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @return the text field
     */
    public static JTextField getTextField(String text, int x, int y, int width, int height, Container cont) {
        JTextField text_1 = new JTextField();

        if (text != null) {
            text_1.setText(text);
        }

        text_1.setBounds(x, y, width, height);

        cont.add(text_1);

        return text_1;
    }


    /**
     * Gets the text field.
     *
     * @param text    the text
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param cont    the cont
     * @param font_id font id
     * @return the text field
     */
    public static JTextField getTextField(String text, int x, int y, int width, int height, Container cont, int font_id) {
        return getTextField(text, x, y, width, height, cont, null, getFont(font_id));
    }

    public static JTextField getTextField(String text, int x, int y, int width, int height, Container cont, Font font) {
        return getTextField(text, x, y, width, height, cont, null, font);
    }

    public static JTextField getTextField(String text, int fontId) {
        return getTextField(text, null, null, null, null, null, null, getFont(fontId));
    }


    public static JTextField getTextField(String text, Container container, Object layoutConstraints, int fontId) {
        return getTextField(text, null, null, null, null, container, layoutConstraints, getFont(fontId));
    }


    /**
     * Gets the text field.
     *
     * @param text      the text
     * @param x         the x
     * @param y         the y
     * @param width     the width
     * @param height    the height
     * @param container the cont
     * @param font      font instance
     * @return the text field
     */
    public static JTextField getTextField(String text, Integer x, Integer y, Integer width, Integer height,
                                          Container container, Object layoutConstraints, Font font) {
        JTextField text_1 = new JTextField();

        if (text != null) {
            text_1.setText(text);
        }

        if (x != null)
            text_1.setBounds(x, y, width, height);

        if (font != null) {
            text_1.setFont(font);
        }

        addToContainer(text_1, container, layoutConstraints);

        return text_1;
    }


    protected static void addToContainer(Component component, Container container, Object layoutConstraints) {
        if (container != null) {
            if (layoutConstraints == null)
                container.add(component);
            else
                container.add(component, layoutConstraints);
        }
    }


    /**
     * Get Scroll Pane
     *
     * @param element component to add to scroll pane
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param cont    container
     * @return the scroll pane
     */
    public static JScrollPane getScrollPane(Component element, int x, int y, int width, int height, Container cont) {
        JScrollPane scr2 = new JScrollPane(element);
        scr2.setBounds(x, y, width, height);
        cont.add(scr2);

        return scr2;
    }


    /**
     * Adds the component.
     *
     * @param comp   the comp
     * @param posX   the pos x
     * @param posY   the pos y
     * @param width  the width
     * @param height the height
     * @param parent the parent
     */
    public static void addComponent(JComponent comp, int posX, int posY, int width, int height, JPanel parent) {
        addComponent(comp, posX, posY, width, height, FONT_NORMAL, parent);
    }


    /**
     * Adds the component.
     *
     * @param comp    the comp
     * @param posX    the pos x
     * @param posY    the pos y
     * @param width   the width
     * @param height  the height
     * @param font_id the font_id
     * @param parent  the parent
     */
    public static void addComponent(JComponent comp, int posX, int posY, int width, int height, int font_id,
                                    JPanel parent) {
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
    public static JMenu createMenu(String name, String tool_tip, I18nControlAbstract ic) {
        JMenu item = new JMenu(ic.getMessageWithoutMnemonic(name));
        item.setMnemonic(ic.getMnemonic(name));

        if (tool_tip != null) {
            item.setToolTipText(ic.getMessage(tool_tip));
        }

        return item;
    }


    public static JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command,
                                           ActionListener al, String icon_small, I18nControlAbstract ic, ATDataAccessAbstract da, Container c) {
        return createMenuItem(menu, name, tip, action_command, al, icon_small, ic, da, c, -1);
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
     * @return
     */
    public static JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command,
                                           ActionListener al, String icon_small, I18nControlAbstract ic, ATDataAccessAbstract da, Container c,
                                           int font_id) {
        JMenuItem mi = new JMenuItem(ic.getMessageWithoutMnemonic(name));
        mi.setMnemonic(ic.getMnemonic(name));

        mi.setActionCommand(action_command);
        mi.addActionListener(al);

        if (font_id != -1) {
            mi.setFont(getFont(font_id));
        }

        if (tip != null) {
            mi.setToolTipText(ic.getMessage(tip));
        }

        if (icon_small != null) {
            mi.setIcon(getImageIcon(da.getImagesRoot(), icon_small, 15, 15, c));
        }

        if (menu != null) {
            menu.add(mi);
        }

        return mi;
        // return action;
    }


    /**
     * Gets the numeric text field.
     *
     * @param value
     * @param min
     * @param max
     * @param step
     * @param x
     * @param y
     * @param width
     * @param height
     * @param cont
     * @return the numeric text field
     */
    public static JSpinner getJSpinner(float value, int min, int max, float step, int x, int y, int width, int height,
                                       Container cont) {
        JSpinner sp = new JSpinner();
        sp.setBounds(x, y, width, height);

        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        sp.setModel(model);

        cont.add(sp);

        return sp;
    }


    /**
     * Gets the numeric text field.
     *
     * @param value
     * @param min
     * @param max
     * @param step
     * @param x
     * @param y
     * @param width
     * @param height
     * @param cont
     * @return the numeric text field
     */
    public static JSpinner getJSpinner(float value, float min, float max, float step, int x, int y, int width,
                                       int height, Container cont) {
        JSpinner sp = new JSpinner();
        sp.setBounds(x, y, width, height);

        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        sp.setModel(model);

        cont.add(sp);

        return sp;
    }


    /**
     * Show Error Dialog
     *
     * @param cont
     * @param message
     * @param ic
     */
    public static void showErrorDialog(Container cont, String message, I18nControlAbstract ic) {
        JOptionPane.showMessageDialog(cont, ic.getMessage(message), ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Show Warning Dialog
     *
     * @param cont
     * @param message
     * @param ic
     */
    public static void showWarningDialog(Container cont, String message, I18nControlAbstract ic) {
        JOptionPane.showMessageDialog(cont, ic.getMessage(message), ic.getMessage("WARNING"),
                JOptionPane.WARNING_MESSAGE);
    }


    /**
     * Gets the text area (is JScrollPane)
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @return the text field
     */
    public static JTextArea getTextArea(String text, int x, int y, int width, int height, Container cont) {
        return getTextArea(text, x, y, width, height, cont, null, null);
    }


    public static JTextArea getTextArea(String text, Container cont, Object layoutConstraints, int fontId) {
        return getTextArea(text, null, null, null, null, cont, layoutConstraints, getFont(fontId));
    }


    /**
     * Gets the text area (is JScrollPane)
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @return the text field
     */
    public static JTextArea getTextArea(String text, int x, int y, int width, int height, Container cont, int fontId) {
        return getTextArea(text, x, y, width, height, cont, null, getFont(fontId));
    }


    /**
     * Gets the text area (is JScrollPane)
     *
     * @param text   the text
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param cont   the cont
     * @return the text field
     */
    public static JTextArea getTextArea(String text, Integer x, Integer y, Integer width, Integer height,
                                        Container cont, Object layoutConstraints, Font font) {
        JTextArea jta = new JTextArea();

        if (text != null) {
            jta.setText(text);
        }

        JScrollPane scp = new JScrollPane(jta);

        if (x != null)
            scp.setBounds(x, y, width, height);

        if (font != null) {
            jta.setFont(font);
        }

        addToContainer(scp, cont, layoutConstraints);

        return jta;
    }


    /**
     * Gets the j formated text value int.
     *
     * @param ftf the ftf
     * @return the j formated text value int
     */
    public static int getJFormatedTextValueInt(JFormattedTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (Exception ex) {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Integer) {
            // System.out.println("Amount(long): " +
            // this.amountField.getValue());
            Integer l = (Integer) o;
            return l.intValue();
        } else if (o instanceof Long) {
            Long l = (Long) o;
            return l.intValue();
        } else if (o instanceof Byte) {
            Byte b = (Byte) o;
            return b.intValue();
        } else if (o instanceof Short) {
            Short s = (Short) o;
            return s.intValue();
        } else if (o instanceof Float) {
            Float f = (Float) o;
            return f.intValue();
        } else
        // if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.intValue();
        }

    }


    public static int getJDecimalTextValueInt(JDecimalTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (Exception ex) {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Integer) {
            // System.out.println("Amount(long): " +
            // this.amountField.getValue());
            Integer l = (Integer) o;
            return l.intValue();
        } else if (o instanceof Long) {
            Long l = (Long) o;
            return l.intValue();
        } else if (o instanceof Byte) {
            Byte b = (Byte) o;
            return b.intValue();
        } else if (o instanceof Short) {
            Short s = (Short) o;
            return s.intValue();
        } else if (o instanceof Float) {
            Float f = (Float) o;
            return f.intValue();
        } else
        // if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.intValue();
        }

    }


    /**
     * Gets the j formated text value long.
     *
     * @param ftf the ftf
     * @return the j formated text value long
     */
    public static long getJFormatedTextValueLong(JFormattedTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (Exception ex) {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Long) {
            Long l = (Long) o;
            return l.longValue();
        } else if (o instanceof Integer) {
            Integer l = (Integer) o;
            return l.longValue();
        } else if (o instanceof Byte) {
            Byte b = (Byte) o;
            return b.longValue();
        } else if (o instanceof Short) {
            Short s = (Short) o;
            return s.longValue();
        } else if (o instanceof Float) {
            Float f = (Float) o;
            return f.longValue();
        } else
        // if (o instanceof Double)
        {
            // System.out.println("Amount(double): " +
            // this.amountField.getValue());
            Double d = (Double) o;
            return d.longValue();
        }

        // java.lang.Byte;
        // java.lang.Double
        // java.lang.Float;
        // java.lang.Integer;
        // java.lang.Long;
        // java.lang.Short;

    }


    /**
     * Gets the j formated text value byte.
     *
     * @param ftf the ftf
     * @return the j formated text value byte
     */
    public static byte getJFormatedTextValueByte(JFormattedTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (Exception ex) {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Byte) {
            Byte b = (Byte) o;
            return b.byteValue();
        } else if (o instanceof Short) {
            Short s = (Short) o;
            return s.byteValue();
        } else if (o instanceof Integer) {
            Integer l = (Integer) o;
            return l.byteValue();
        } else if (o instanceof Long) {
            Long l = (Long) o;
            return l.byteValue();
        } else if (o instanceof Float) {
            Float f = (Float) o;
            return f.byteValue();
        } else
        // if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.byteValue();
        }

    }


    /**
     * Gets the j formated text value short.
     *
     * @param ftf the ftf
     * @return the j formated text value short
     */
    public static short getJFormatedTextValueShort(JFormattedTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (Exception ex) {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Short) {
            Short s = (Short) o;
            return s.shortValue();
        } else if (o instanceof Byte) {
            Byte b = (Byte) o;
            return b.shortValue();
        } else if (o instanceof Integer) {
            Integer l = (Integer) o;
            return l.shortValue();
        } else if (o instanceof Long) {
            Long l = (Long) o;
            return l.shortValue();
        } else if (o instanceof Float) {
            Float f = (Float) o;
            return f.shortValue();
        } else
        // if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.shortValue();
        }

    }


    /**
     * Gets the j formated text value float.
     *
     * @param ftf the ftf
     * @return the j formated text value float
     */
    public static float getJFormatedTextValueFloat(JFormattedTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (Exception ex) {
            System.out.println("Exception on commit value:" + ex + "\nValue:" + ftf.getValue());
            ex.printStackTrace();
        }

        Object o = ftf.getValue();

        if (o instanceof Float) {
            Float f = (Float) o;
            return f.floatValue();
        } else if (o instanceof Double) {
            Double d = (Double) o;
            return d.floatValue();
        } else if (o instanceof Long) {
            Long l = (Long) o;
            return l.floatValue();
        } else if (o instanceof Integer) {
            Integer l = (Integer) o;
            return l.floatValue();
        } else if (o instanceof Byte) {
            Byte b = (Byte) o;
            return b.floatValue();
        } else
        // if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.floatValue();
        }

    }


    /**
     * Gets the j formated text value double.
     *
     * @param ftf the ftf
     * @return the j formated text value double
     */
    public static double getJFormatedTextValueDouble(JFormattedTextField ftf) {
        try {
            ftf.commitEdit();
        } catch (Exception ex) {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Double) {
            Double d = (Double) o;
            return d.doubleValue();
        } else if (o instanceof Float) {
            Float f = (Float) o;
            return f.doubleValue();
        } else if (o instanceof Long) {
            Long l = (Long) o;
            return l.doubleValue();
        } else if (o instanceof Integer) {
            Integer l = (Integer) o;
            return l.doubleValue();
        } else if (o instanceof Byte) {
            Byte b = (Byte) o;
            return b.doubleValue();
        } else
        // if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.doubleValue();
        }

    }


    /**
     * Center j dialog.
     *
     * @param dialog  the dialog
     * @param _parent the _parent
     */
    public static void centerJDialog(Component dialog, Component /* Container */_parent) {

        Rectangle rec = _parent.getBounds();

        int x = rec.width / 2;
        x += rec.x;

        int y = rec.height / 2;
        y += rec.y;

        x -= dialog.getBounds().width / 2;
        y -= dialog.getBounds().height / 2;

        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

        // dialog.getBounds().x = x;
        // dialog.getBounds().y = y;

        dialog.setBounds(x, y, dialog.getBounds().width, dialog.getBounds().height);

        /*
         * if (parent instanceof JDialog) { //centerJDialog(dialog,
         * (JDialog)parent); } else System.out.println("CenterJDialog failed");
         */
    }


    public static JButton createHelpButtonBySize(int width, int height, Container comp, ATDataAccessAbstract dataAccess) {
        return createHelpButtonBySize(width, height, comp, dataAccess.getImagesRoot(),
                dataAccess.getI18nControlInstance());
    }


    public static JButton createHelpButton(Container comp, ATDataAccessAbstract dataAccess) {
        return createHelpButtonBySize(null, null, comp, dataAccess.getImagesRoot(), dataAccess.getI18nControlInstance());
    }


    public static JButton createHelpButton(Container comp, ATDataAccessAbstract dataAccess, String helpIconFilename) {
        return createHelpButtonBySize(null, null, comp, dataAccess.getImagesRoot(),
                dataAccess.getI18nControlInstance(), helpIconFilename);
    }


    public static JButton createHelpButtonBySize(Integer width, Integer height, Container comp, String imagesRoot,
                                                 I18nControlAbstract ic) {
        return createHelpButtonBySize(width, height, comp, imagesRoot, ic, null);
    }


    /**
     * Creates the help button by size.
     *
     * @param width  the width
     * @param height the height
     * @param comp   the comp
     * @return the j button
     */
    public static JButton createHelpButtonBySize(Integer width, Integer height, Container comp, String imagesRoot,
                                                 I18nControlAbstract ic, String helpIconFilename) {
        JButton help_button = new JButton(" " + ic.getMessage("HELP"));

        if (width != null) {
            help_button.setPreferredSize(new Dimension(width, height));
        }

        String iconFilename = helpIconFilename != null ? helpIconFilename : "help.png";

        help_button.setIcon(getImageIcon_22x22(imagesRoot, iconFilename, comp));

        return help_button;
    }


    /**
     * Creates the help button by size.
     *
     * @param width  the width
     * @param height the height
     * @param comp   the comp
     * @return the j button
     */
    public static JButton createHelpIconBySize(int width, int height, Container comp, String imagesRoot) {
        JButton help_button = new JButton();
        help_button.setPreferredSize(new Dimension(width, height));
        help_button.setIcon(getImageIcon_22x22(imagesRoot, "help.png", comp));
        return help_button;
    }


    public static JButton createHelpIconBySize(int width, int height, Container comp, ATDataAccessAbstract dataAccess) {
        JButton help_button = new JButton();
        help_button.setPreferredSize(new Dimension(width, height));
        help_button.setIcon(getImageIcon_22x22(dataAccess.getImagesRoot(), "help.png", comp));
        return help_button;
    }


    // ********************************************************
    // ****** JFormatted Text Field *****
    // ********************************************************

    /**
     * Creates the help button by bounds.
     *
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param comp    the comp
     * @param font_id the font_id
     * @return the j button
     */
    public static JButton createHelpIconByBounds(int x, int y, int width, int height, Container comp, int font_id,
                                                 ATDataAccessAbstract dataAccess) {
        return createHelpIconByBounds(x, y, width, height, comp, getFont(font_id), dataAccess.getImagesRoot());
    }


    /**
     * Creates the help button by bounds.
     *
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param comp    the comp
     * @param font_id the font_id
     * @return the j button
     */
    public static JButton createHelpIconByBounds(int x, int y, int width, int height, Container comp, int font_id,
                                                 String imagesRoot) {
        return createHelpIconByBounds(x, y, width, height, comp, getFont(font_id), imagesRoot);
    }


    /**
     * Creates the help button by bounds.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param comp   the comp
     * @param font   the font
     * @return the j button
     */
    public static JButton createHelpIconByBounds(int x, int y, int width, int height, Container comp, Font font,
                                                 String imagesRoot) {
        JButton help_button = new JButton();
        help_button.setBounds(x, y, width, height);
        help_button.setIcon(getImageIcon(imagesRoot, "help.png", (int) (height * 0.88), (int) (height * 0.88), comp));

        if (font != null) {
            help_button.setFont(font);
        }

        return help_button;
    }


    public static JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp, int fontId,
                                                   ATDataAccessAbstract da) {
        return createHelpButtonByBounds(x, y, width, height, comp, getFont(fontId), da.getImagesRoot(),
                da.getI18nControlInstance());
    }


    public static JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp, int fontId,
                                                   String imagesRoot, I18nControlAbstract ic) {
        return createHelpButtonByBounds(x, y, width, height, comp, getFont(fontId), imagesRoot, ic);
    }


    /**
     * Creates the help button by bounds.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param comp   the comp
     * @param font   the font
     * @return the j button
     */
    public static JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp, Font font,
                                                   String imagesRoot, I18nControlAbstract ic) {
        JButton help_button = new JButton("  " + ic.getMessage("HELP"));
        help_button.setBounds(x, y, width, height);
        help_button.setIcon(getImageIcon_22x22(imagesRoot, "help.png", comp));

        if (font != null) {
            help_button.setFont(font);
        }

        return help_button;
    }


    /**
     * Gets the image icon_22x22.
     *
     * @param name the name
     * @param comp the comp
     * @return the image icon_22x22
     */
    public static ImageIcon getImageIcon_22x22(String name, Container comp, ATDataAccessAbstract dataAccess) {
        return ATSwingUtils.getImageIcon(dataAccess.getImagesRoot(), name, 22, 22, comp);
    }


    // ********************************************************
    // ****** GUI *****
    // ********************************************************

    /**
     * Gets the image icon_22x22.
     *
     * @param name the name
     * @param comp the comp
     * @return the image icon_22x22
     */
    public static ImageIcon getImageIcon_22x22(String root, String name, Container comp) {
        return getImageIcon(root, name, 22, 22, comp);
    }


    //
    // HELP
    //

    /**
     * Gets the image icon.
     *
     * @param root   the root
     * @param name   the name
     * @param width  the width
     * @param height the height
     * @param comp   the comp
     * @return the image icon
     */
    public static ImageIcon getImageIcon(String root, String name, int width, int height, Container comp) {

        return new ImageIcon(getImage(root + name, comp).getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }


    /**
     * Gets the image icon.
     *
     * @param root the root
     * @param name the name
     * @param comp the comp
     * @return the image icon
     */
    public static ImageIcon getImageIcon(String root, String name, Component comp) {
        Image i = getImage(root + name, comp);
        return new ImageIcon(i);
    }


    /**
     * Gets the image icon.
     *
     * @param name   the name
     * @param width  the width
     * @param height the height
     * @param comp   the comp
     * @return the image icon
     */
    public static ImageIcon getImageIcon(String name, int width, int height, Container comp,
                                         ATDataAccessAbstract dataAccess) {
        return ATSwingUtils.getImageIcon(dataAccess.getImagesRoot(), name, width, height, comp);
    }


    /**
     * Gets the image icon.
     *
     * @param name the name
     * @param comp the comp
     * @return the image icon
     */
    public static ImageIcon getImageIcon(String name, Container comp, ATDataAccessAbstract dataAccess) {
        return ATSwingUtils.getImageIcon(dataAccess.getImagesRoot(), name, comp);
    }


    /**
     * @Deprecated Use showMessageDialog instead of this one.
     */
    @Deprecated
    public static void showDialog(Container cont, int type, String message, I18nControlAbstract ic) {
        if (type == DIALOG_INFO) {
            JOptionPane.showMessageDialog(cont, message, ic.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
        } else if (type == DIALOG_WARNING) {
            JOptionPane.showMessageDialog(cont, message, ic.getMessage("WARNING"), JOptionPane.WARNING_MESSAGE);
        } else
        // if (type == DIALOG_ERROR)
        {
            JOptionPane.showMessageDialog(cont, message, ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void showMessageDialog(Container cont, DialogType dialogType, String message, I18nControlAbstract ic) {
        JOptionPane.showMessageDialog(cont, message, " " + ic.getMessage(dialogType.i18nKey), dialogType.paneType);
    }


    public static int showConfirmDialog(Container parent, DialogType dialogType, String message,
                                        ConfirmDialogType confirmDialogType, I18nControlAbstract i18nControl) {
        return JOptionPane.showConfirmDialog(parent, //
                i18nControl.getMessage(message), //
                " " + i18nControl.getMessage(dialogType.i18nKey), //
                confirmDialogType.paneType);

    }


    public static String createHtmlText(String message) {
        return "<html><body>" + message + "</body></html>";
    }


    public static String createHtmlTextWithWidth(String message, int width) {
        return "<html><body>" + "<table width=" + width + "><tr><td>" + message + "</td></tr></table>"
                + "</body></html>";
    }


    public static String createHtmlTextWithWidth(String message, int width, int leftPadding, int rightPadding) {
        return "<html><body>" + "<table width=" + width + "><tr><td width=\"" + leftPadding + "\" /><td>" + message
                + "</td></tr></table>" + "<td width=\"" + rightPadding + "\" /></body></html>";
    }


    public static Dimension calculateJLabelSizeWithText(JLabel label, String text) {
        View view = (View) javax.swing.plaf.basic.BasicHTML.createHTMLView(label, text);
        int width = (int) view.getPreferredSpan(View.X_AXIS);
        int height = (int) view.getPreferredSpan(View.Y_AXIS);

        // System.out.println("Width: " + width + ", height: " + height);
        int insetsSum = (label.getInsets().top + label.getInsets().bottom);

        System.out.println("Width: " + label.getInsets().top + ", height: " + label.getInsets().bottom);

        return new Dimension(width, height + label.getInsets().top);
    }


    public static Dimension calculateJLabelSizeWithText(JLabel label, String text, int widthInput) {
        View view = (View) javax.swing.plaf.basic.BasicHTML.createHTMLView(label, text);
        int width = (int) view.getPreferredSpan(View.X_AXIS);
        int height = (int) view.getPreferredSpan(View.Y_AXIS);

        System.out.println("Width: " + width + ", height: " + height);

        float ff = width / (widthInput * 1.0f);

        System.out.println("Width: " + width + ", height: " + height + "ff: " + ff);

        int ff2 = (int) Math.ceil(ff);

        System.out.println("Width: " + width + ", height: " + height + "ff2: " + ff2);

        height = ff2 * (height);

        int insetsSum = (label.getInsets().top + label.getInsets().bottom);

        System.out.println("Width: " + width + ", height: " + height + "ff: " + ff + "insets: " + insetsSum);

        return new Dimension(width, height + insetsSum + 5);
    }


    /**
     * Load colors.
     */
    public void loadColors() {
        ColorUIResource cui = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("textText");
        this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui.getBlue(), cui.getAlpha());

        ColorUIResource cui2 = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("Label.background");
        this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2.getBlue(), cui2.getAlpha());

        this.border_line = new LineBorder(this.color_foreground);
    }


    /**
     * Gets the date string.
     *
     * @param date the date
     * @return the date string
     */
    public String getDateString(int date) {

        // 20051012

        int year = date / 10000;
        int months = date - year * 10000;

        months = months / 100;

        int days = date - year * 10000 - months * 100;

        if (year == 0)
            return getLeadingZero(days, 2) + "/" + getLeadingZero(months, 2);
        else
            return getLeadingZero(days, 2) + "/" + getLeadingZero(months, 2) + "/" + year;

    }


    //
    // Images
    //

    /**
     * Gets the time string.
     *
     * @param time the time
     * @return the time string
     */
    public String getTimeString(int time) {

        int hours = time / 100;

        int min = time - hours * 100;

        return getLeadingZero(hours, 2) + ":" + getLeadingZero(min, 2);

    }


    /**
     * Gets the date time string.
     *
     * @param date the date
     * @return the date time string
     */
    public String getDateTimeString(long date) {
        return getDateTimeString(date, 1);
    }


    /**
     * Gets the date time as date string.
     *
     * @param date the date
     * @return the date time as date string
     */
    public String getDateTimeAsDateString(long date) {
        return getDateTimeString(date, 2);
    }


    /**
     * Gets the aT date time from gc.
     *
     * @param gc   the gc
     * @param type the type
     * @return the aT date time from gc
     */
    public long getATDateTimeFromGC(GregorianCalendar gc, int type) {
        long dt = 0L;

        if (type == DATE_TIME_ATECH_DATETIME) {
            dt += gc.get(Calendar.YEAR) * 100000000L;
            dt += (gc.get(Calendar.MONTH) + 1) * 1000000L;
            dt += gc.get(Calendar.DAY_OF_MONTH) * 10000L;
            dt += gc.get(Calendar.HOUR_OF_DAY) * 100L;
            dt += gc.get(Calendar.MINUTE);
        } else if (type == DATE_TIME_ATECH_DATE) {
            dt += gc.get(Calendar.YEAR) * 10000L;
            dt += (gc.get(Calendar.MONTH) + 1) * 100L;
            dt += gc.get(Calendar.DAY_OF_MONTH);
        } else if (type == DATE_TIME_ATECH_TIME) {
            dt += gc.get(Calendar.HOUR_OF_DAY) * 100L;
            dt += gc.get(Calendar.MINUTE);
        }

        return dt;
    }


    /**
     * Gets the aT date time from parts.
     *
     * @param day    the day
     * @param month  the month
     * @param year   the year
     * @param hour   the hour
     * @param minute the minute
     * @param type   the type
     * @return the aT date time from parts
     */
    public long getATDateTimeFromParts(int day, int month, int year, int hour, int minute, int type) {
        long dt = 0L;

        if (type == DATE_TIME_ATECH_DATETIME) {
            dt += year * 100000000L;
            dt += month * 1000000L;
            dt += day * 10000L;
            dt += hour * 100L;
            dt += minute;
        } else if (type == DATE_TIME_ATECH_DATE) {
            dt += year * 10000L;
            dt += month * 100L;
            dt += day;
        } else if (type == DATE_TIME_ATECH_TIME) {
            dt += hour * 100L;
            dt += minute;
        }

        return dt;
    }


    /**
     * Gets the date from at date.
     *
     * @param data the data
     * @return the date from at date
     */
    public long getDateFromATDate(long data) {
        // 200701011222
        int d2 = (int) (data / 10000);

        // long dd = data%10000;
        // data -= dd;

        // System.out.println("D2: " +d2);

        // System.out.println(data);
        return d2;
    }


    /**
     * Gets the date time as time string.
     *
     * @param date the date
     * @return the date time as time string
     */
    public String getDateTimeAsTimeString(long date) {
        return getDateTimeString(date, 3);
    }


    /**
     * Gets the date time string.
     *
     * @param dt       the dt
     * @param ret_type the ret_type
     * @return the date time string
     */
    public String getDateTimeString(long dt, int ret_type) {

        // System.out.println("DT process: " + dt);
        /*
         * int y = (int)(dt/10000000L);
         * dt -= y*10000000L;
         * int m = (int)(dt/1000000L);
         * dt -= m*1000000L;
         * int d = (int)(dt/10000L);
         * dt -= d*10000L;
         * int h = (int)(dt/100L);
         * dt -= h*100L;
         * int min = (int)dt;
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
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y + "  " + getLeadingZero(h, 2) + ":"
                    + getLeadingZero(min, 2);
        else if (ret_type == DT_DATE)
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y;
        else
            return getLeadingZero(h, 2) + ":" + getLeadingZero(min, 2);

    }


    /**
     * Gets the date time string.
     *
     * @param date the date
     * @param time the time
     * @return the date time string
     */
    public String getDateTimeString(int date, int time) {

        return getDateString(date) + " " + getTimeString(time);

    }


    /**
     * Gets the leading zero.
     *
     * @param number the number
     * @param places the places
     * @return the leading zero
     */
    public String getLeadingZero(int number, int places) {

        String nn = "" + number;

        while (nn.length() < places) {
            nn = "0" + nn;
        }

        return nn;

    }


    /**
     * Gets the start year.
     *
     * @return the start year
     */
    public int getStartYear() {
        // FIX set in Db
        return 1800;
    }


    /**
     * Creates the menu item.
     *
     * @param menu           the menu
     * @param name           the name
     * @param tip            the tip
     * @param action_command the action_command
     * @param icon_small     the icon_small
     * @return the j menu item
     */
    public JMenuItem createMenuItem(JMenu menu, String name, String tip, String action_command, String icon_small) {

        JMenuItem mi = new JMenuItem();

        mi.setText(i18n_control.getMessageWithoutMnemonic(name));
        mi.setActionCommand(action_command);

        if (tip != null) {
            mi.setToolTipText(i18n_control.getMessage(tip));
        }

        if (icon_small != null) {
            mi.setIcon(new ImageIcon(getClass().getResource(icon_small)));
        }

        if (menu != null) {
            menu.add(mi);
        }

        return mi;

    }

    public enum DialogType {
        Info("INFO", JOptionPane.INFORMATION_MESSAGE), //
        Warning("WARNING", JOptionPane.WARNING_MESSAGE), //
        Error("ERROR", JOptionPane.ERROR_MESSAGE), //

        ; //

        String i18nKey;
        int paneType;


        DialogType(String i18nKey, int paneType) {
            this.i18nKey = i18nKey;
            this.paneType = paneType;
        }

    }

    public enum ConfirmDialogType {
        ClosedOption(JOptionPane.CLOSED_OPTION), //
        DefaultOption(JOptionPane.DEFAULT_OPTION), //
        YesNoOption(JOptionPane.YES_NO_OPTION), //
        YesNoCancelOption(JOptionPane.YES_NO_CANCEL_OPTION), //
        OkCancelOption(JOptionPane.OK_CANCEL_OPTION), //

        ; //

        int paneType;


        ConfirmDialogType(int paneType) {
            this.paneType = paneType;
        }
    }


    public static <E extends Object> void populateJListExtended(JList listComponent, List<?> inputList, Class<E> clazz) {
        DefaultListModel newListModel = new DefaultListModel();

        for (Object item : inputList) {
            // instanceof clazz.getClass())

            if (clazz.isAssignableFrom(item.getClass())) {
                newListModel.addElement(item);
            } else {
                LOG.debug("populateJListExtended: Unsupported item: " + item);
            }
        }

        listComponent.setModel(newListModel);
    }

}
