package com.atech.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.Collator;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.graphics.dialogs.ErrorDialog;
import com.atech.graphics.graphs.GraphConfigProperties;
import com.atech.help.HelpCapable;
import com.atech.help.HelpContext;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.info.LanguageInfo;
import com.atech.plugin.PlugInClient;
import com.atech.update.config.UpdateConfiguration;

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

public abstract class ATDataAccessAbstract
{

    private static Log log = LogFactory.getLog(ATDataAccessAbstract.class);

    // LF
    // Hashtable<String,String> availableLF_full = null;
    // Object[] availableLF = null;
    // Object[] availableLang = null;

    String selectedLF = null;
    String subSelectedLF = null;

    // config file
    Hashtable<String, String> config_db_values = null;
    // public int selected_db = -1;
    // public int selected_lang = 1;
    // public String selected_LF_Class = null; // class
    // public String selected_LF_Name = null; // name
    // public String skinLFSelected = null;
    // String allDbs[] = null;


    public Hashtable<String, String> m_settings_ht = null;

    public static String pathPrefix = ".";

    public Color color_background, color_foreground;

    protected LanguageInfo m_lang_info;
    
    //public boolean printing_plugin_installed = false;

    protected I18nControlAbstract m_i18n = null; // ATI18nControl.getInstance();


    private int db_loading_status = 0;

    public String[] options_yes_no = null;
    // x public Hashtable typesHT = new Hashtable();
    public Object[] typesAll = null;
    public LineBorder border_line;

    
    public Hashtable<String,PlugInClient> plugins;
    public Font fonts[] = null;

    // Configuration icons

    protected Collator m_collator = null;
    protected Container parent = null;

    
    public static char real_decimal;
    public static char false_decimal;
    
    private static boolean decimals_set;
    
    private UpdateConfiguration update_configuration = null;

    protected GraphConfigProperties graph_config = null;
    //private static HibernateDb m_db_hib;
    
    protected Hashtable<String, String> special_parameters = null;
    
    /*
     * ABS REMOVED
     * 
     * public String contact_types[] = { m_i18n.getMessage("SELECT"),
     * m_i18n.getMessage("PHONE"), m_i18n.getMessage("GSM"),
     * m_i18n.getMessage("FAX"), m_i18n.getMessage("EMAIL"),
     * m_i18n.getMessage("WEB_PAGE"), m_i18n.getMessage("ICQ_MSNG"),
     * m_i18n.getMessage("YAHOO_MSNG"), m_i18n.getMessage("AIM_MSNG"),
     * m_i18n.getMessage("JABBER_MSNG"), m_i18n.getMessage("MSN_MSNG"),
     * m_i18n.getMessage("SKYPE_MSNG"), m_i18n.getMessage("OTHER") };
     * 
     * public ImageIcon contact_icons[] = { null, new
     * ImageIcon("images/c_phone.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/c_GSM.gif"), //m_i18n.getMessage("GSM"), new
     * ImageIcon("images/c_fax.gif"), //m_i18n.getMessage("FAX"), new
     * ImageIcon("images/c_email.gif"), //m_i18n.getMessage("EMAIL"), new
     * ImageIcon("images/c_hp.gif"), //m_i18n.getMessage("WEB_PAGE"), new
     * ImageIcon("images/c_icq.gif"), //m_i18n.getMessage("ICQ_MSNG"), new
     * ImageIcon("images/c_yahoo.gif"), //m_i18n.getMessage("YAHOO_MSNG"), new
     * ImageIcon("images/c_aim.gif"), //m_i18n.getMessage("MSN_MSNG") new
     * ImageIcon("images/c_jabber.gif"), new ImageIcon("images/c_msn.gif"),
     * null, null };
     * 
     * 
     * public ImageIcon db_status_load[] = { new
     * ImageIcon("images/dot_red.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/dot_orange.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/dot_blue.gif"), //m_i18n.getMessage("PHONE"), new
     * ImageIcon("images/dot_green.gif"), };
     */

    public String days[] = new String[7];
    /*
     * { m_i18n.getMessage("MONDAY"), m_i18n.getMessage("TUESDAY"),
     * m_i18n.getMessage("WEDNESDAY"), m_i18n.getMessage("THURSDAY"),
     * m_i18n.getMessage("FRIDAY"), m_i18n.getMessage("SATURDAY"),
     * m_i18n.getMessage("SUNDAY") };
     */

    /**
     * 
     */
    public String months[] = new String[12];

    /*
     * { m_i18n.getMessage("JANUARY"), m_i18n.getMessage("FEBRUARY"),
     * m_i18n.getMessage("MARCH"), m_i18n.getMessage("APRIL"),
     * m_i18n.getMessage("MAY"), m_i18n.getMessage("JUNE"),
     * m_i18n.getMessage("JULY"), m_i18n.getMessage("AUGUST"),
     * m_i18n.getMessage("SEPTEMBER"), m_i18n.getMessage("OCTOBER"),
     * m_i18n.getMessage("NOVEMBER"), m_i18n.getMessage("DECEMBER") };
     */
    /*
     * public String[] userTypesBasic = { m_i18n.getMessage("SELECT"),
     * m_i18n.getMessage("USER_EXTERNAL"), m_i18n.getMessage("USER_INTERNAL") };
     */

    // ********************************************************
    // ****** Constructors and Access methods *****
    // ********************************************************

    // Constructor: DataAccess
    /**
     * 
     * This is DataAccess constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.<br>
     * <br>
     * @param ic 
     * 
     */
    public ATDataAccessAbstract(I18nControlAbstract ic)
    {
        this.m_i18n = ic;
        loadArraysTranslation();
        checkPrerequisites();
        loadFonts();
        m_settings_ht = new Hashtable<String, String>();
        plugins = new Hashtable<String,PlugInClient>();
        this.m_collator = this.m_i18n.getCollationDefintion();
        loadPlugIns();
        loadBackupRestoreCollection();
        
        if (!ATDataAccessAbstract.decimals_set)
            initDecimals();


//        initSpecial();
    }

    
    
    public abstract void initSpecial();
    
    
    public abstract HibernateDb getHibernateDb();
    
    
    public abstract void loadPlugIns();
    
    
    // Method: getInstance
    // Author: Andy
    /**
     * 
     * This method returns reference to OmniI18nControl object created, or if no
     * object was created yet, it creates one.<br>
     * <br>
     * 
     * @return Reference to OmniI18nControl object
     * 
     */
    /*
     * static public ATDataAccessAbstract getInstance() { if (m_da == null) m_da
     * = new ATDataAccessAbstract(); return m_da; }
     */

    // Method: deleteInstance
    /**
     * This method sets handle to DataAccess to null and deletes the instance. <br>
     * <br>
     */
   /* public void deleteInstance()
    {
        //m_i18n = null;

    } */

    // INIT

    public void loadArraysTranslation()
    {
        // months
        months[0] = m_i18n.getMessage("JANUARY");
        months[1] = m_i18n.getMessage("FEBRUARY");
        months[2] = m_i18n.getMessage("MARCH");
        months[3] = m_i18n.getMessage("APRIL");
        months[4] = m_i18n.getMessage("MAY");
        months[5] = m_i18n.getMessage("JUNE");
        months[6] = m_i18n.getMessage("JULY");
        months[7] = m_i18n.getMessage("AUGUST");
        months[8] = m_i18n.getMessage("SEPTEMBER");
        months[9] = m_i18n.getMessage("OCTOBER");
        months[10] = m_i18n.getMessage("NOVEMBER");
        months[11] = m_i18n.getMessage("DECEMBER");

        // days
        days[0] = m_i18n.getMessage("MONDAY");
        days[1] = m_i18n.getMessage("TUESDAY");
        days[2] = m_i18n.getMessage("WEDNESDAY");
        days[3] = m_i18n.getMessage("THURSDAY");
        days[4] = m_i18n.getMessage("FRIDAY");
        days[5] = m_i18n.getMessage("SATURDAY");
        days[6] = m_i18n.getMessage("SUNDAY");

    }

    
    
    private void initDecimals()
    {
        DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
        
        ATDataAccessAbstract.real_decimal = dfs.getDecimalSeparator();
        
        
        
        if (dfs.getDecimalSeparator()=='.')
            ATDataAccessAbstract.false_decimal = ',';
        else
            ATDataAccessAbstract.false_decimal = '.';
        
        
        
        ATDataAccessAbstract.decimals_set = true;
    }
    
    
    
    
    // ********************************************************************
    // ****** Compoment managing *****
    // ****** (needed to have current window for displaying dialog) *****
    // ********************************************************************

    protected ArrayList<Component> components = new ArrayList<Component>();

    
    public Component getCurrentComponentParent()
    {
        return this.components.get(this.components.size() - 2);
    }
    
    public Component getCurrentComponent()
    {
        return this.components.get(this.components.size() - 1);
    }

    public void addComponent(Component cmp)
    {
        //System.out.println("Add: " + this.components);
        this.components.add(cmp);
        //System.out.println("Add: " + this.components);
    }

    public void listComponents()
    {
        System.out.println("Lst: " + this.components);
    }
    
    public void removeComponent(Component cmp)
    {
        //int curr = 0;
        ArrayList<Component> cmps_new = new ArrayList<Component>();

        for (int i = 0; i < this.components.size(); i++)
        {
            if (this.components.get(i).equals(cmp))
            {
                break;
            }
            else
            {
                cmps_new.add(this.components.get(i));
            }

        }

        this.components = cmps_new;
        // System.out.println("Remove: " + this.components);
    }

    // ********************************************************
    // ****** Error handling *****
    // ********************************************************

    public void createErrorDialog(String module, String action, Exception ex,
            String err_msg1)
    {
        createErrorDialog(module, action, ex, err_msg1, null);
    }

    public void createErrorDialog(String module, String action, Exception ex,
            String err_msg1, String err_msg2)
    {

        
        if (this.getCurrentComponentParent() instanceof JFrame)
        {
            new ErrorDialog((JFrame)this.getCurrentComponentParent(),
                    this, "GGC", module, action, ex, err_msg1, err_msg2);
        }
        else
        {
         new ErrorDialog((JDialog)this.getCurrentComponentParent(),
                    this, "GGC", module, action, ex, err_msg1, err_msg2);
        }
        
        
        
        /*
        if (this.getC.getLastParentType() == ATDataAccessAbstract.PARENT_FRAME)
        {
            new ErrorDialog(this.getLastParentFrame(),
                    this, "GGC", module, action, ex, err_msg1, err_msg2);
        }
        else
        {
         new ErrorDialog(this.getLastParentDialog(),
                    this, "GGC", module, action, ex, err_msg1, err_msg2);
        }*/
    }

    // ********************************************************
    // ****** Parent Deep Handling *****
    // ********************************************************
/*
    // TODO: Move
    public static final int PARENT_FRAME = 1;
    public static final int PARENT_DIALOG = 2;

    public ArrayList<Container> cnt_list = new ArrayList<Container>();

    public void addContainer(Container cont)
    {
        System.out.println("!!!! addContainer: " + this.cnt_list + "\nDataAccess: " + this);
        this.cnt_list.add(cont);
        System.out.println("!!!! addContainer: " + this.cnt_list + "\nDataAccess: " + this);

    }

    public void removeContainer(Container cont)
    {
        System.out.println("!!!! removeContainer: " + this.cnt_list+ "\nDataAccess: " + this);
        this.cnt_list.remove(cont);
        System.out.println("!!!! removeContainer: " + this.cnt_list+ "\nDataAccess: " + this);
    }

    public int getLastContainerType()
    {
        if ((cnt_list.get(cnt_list.size() - 1)) instanceof JFrame)
        {
            return ATDataAccessAbstract.PARENT_FRAME;
        }
        else
            return ATDataAccessAbstract.PARENT_DIALOG;
    }

    public int getLastParentType()
    {
        if (this.cnt_list.size() < 2)
        {
            return ATDataAccessAbstract.PARENT_FRAME;
        }
        else
        {
            if ((cnt_list.get(cnt_list.size() - 2)) instanceof JFrame)
            {
                return ATDataAccessAbstract.PARENT_FRAME;
            }
            else
                return ATDataAccessAbstract.PARENT_DIALOG;

        }

    }

    public Container getLastParent()
    {
        System.out.println("Conatiners: " + this.cnt_list);
        
        if (this.cnt_list.size() == 1)
        {
            return cnt_list.get(0);
        }
        if (this.cnt_list.size() < 2)
        {
            return this.getMainParent();
        }
        else
        {
            return (cnt_list.get(cnt_list.size() - 2));
        }
    }

    public JFrame getLastParentFrame()
    {
        return (JFrame) this.getLastParent();
    }

    public JDialog getLastParentDialog()
    {
        return (JDialog) this.getLastParent();
    }
*/
    // ********************************************************
    // ****** Application Handling *****
    // ********************************************************

    JFrame main_parent = null;

    /**
     * Get Application Name
     * 
     * @return
     */
    public abstract String getApplicationName();

    /**
     * Get Images Root (Must have ending back-slash)
     * 
     * @return
     */
    public abstract String getImagesRoot();

    public int main_parent_type = 1;

    public void setMainParent(JFrame frame)
    {
        this.main_parent = frame;
        this.addComponent(this.main_parent);
        //this.addContainer((Container)this.main_parent);
    }

    public JFrame getMainParent()
    {
        return this.main_parent;
    }

    /**
     * Check Prerequisites
     */
    public abstract void checkPrerequisites();

    // ********************************************************
    // ****** Help *****
    // ********************************************************

    HelpContext help_context = null;

    public HelpContext getHelpContext()
    {
        return this.help_context;
    }

    public void setHelpContext(HelpContext hc)
    {
        this.help_context = hc;
    }

    public void enableHelp(HelpCapable hc)
    {
        this.help_context.getMainHelpBroker().enableHelpOnButton(hc.getHelpButton(), hc.getHelpId(), null);
        this.help_context.getMainHelpBroker().enableHelpKey(hc.getComponent(), hc.getHelpId(), null);
    }

    public JButton createHelpButtonBySize(int width, int height, Container comp)
    {
        JButton help_button = new JButton("    "
                + this.getI18nControlInstance().getMessage("HELP"));
        help_button.setPreferredSize(new Dimension(width, height));
        help_button.setIcon(this.getImageIcon_22x22("help.png", comp));

        // help_button.setIconTextGap(12);

        return help_button;
    }

    
    public JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp)
    {
        return createHelpButtonByBounds(x, y, width, height, comp, null);
    }
    
    
    public JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp, int font_id)
    {
        return createHelpButtonByBounds(x, y, width, height, comp, this.getFont(font_id));
    }

    public JButton createHelpButtonByBounds(int x, int y, int width, int height, Container comp, Font font)
    {
        JButton help_button = new JButton("    " + this.getI18nControlInstance().getMessage("HELP"));
        help_button.setBounds(x, y, width, height);
        help_button.setIcon(this.getImageIcon_22x22("help.png", comp));
        
        if (font!=null)
            help_button.setFont(font);
        
        return help_button;
    }
    
    
    
    // ********************************************************
    // ****** I18n *****
    // ********************************************************

    public I18nControlAbstract getI18nControlInstance()
    {
        return this.m_i18n;
    }

    /*
     * public void setI18nControlInstance(I18nControlAbstract i18n) { this.i18n
     * = i18n; }
     */

    
    // ********************************************************
    // ******                Plug-ins                     *****
    // ********************************************************
    
    
    public void addPlugIn(String key, PlugInClient plugin)
    {
        this.plugins.put(key, plugin);
    }
    
    public PlugInClient getPlugIn(String key)
    {
        return this.plugins.get(key);
    }
    
    
    
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // ********************************************************
    // ****** Config Loader *****
    // ********************************************************

    public boolean config_loaded = false;

    public boolean wasConfigLoaded()
    {
        return this.config_loaded;
    }

    public Hashtable<String, String> loadPropertyFile(String filename)
    {

        Hashtable<String, String> config_db_values = new Hashtable<String, String>();

        Properties props = new Properties();

        this.config_loaded = true;

        try
        {
            //File f = new File(".");
            //System.out.println("File: " + f.getCanonicalPath());

            FileInputStream in = new FileInputStream(filename);
            props.load(in);
        }
        catch (Exception ex)
        {
            System.out.println("Error loading config file (" + filename + "): "
                    + ex);
            this.config_loaded = false;
        }

        if (this.config_loaded)
        {

            for (Enumeration<Object> en = props.keys(); en.hasMoreElements();)
            {
                String key = (String) en.nextElement();
                config_db_values.put(key, props.getProperty(key));
            }
        }
        else
            return null;

        return config_db_values;

    }

    // ********************************************************
    // ****** Database Loading Status *****
    // ********************************************************

    public void setDbLoadingStatus(int status)
    {
        this.db_loading_status = status;
    }

    public int getDbLoadingStatus()
    {
        return this.db_loading_status;
    }

    public boolean isDbLoadedForStatus(int status)
    {
        if ((this.db_loading_status == status)
                || (this.db_loading_status > status))
            return true;
        else
            return false;
    }

    // ********************************************************
    // ****** Demo stuff *****
    // ********************************************************

    JDialog m_dialog = null;

    public JDialog getOpenedDialog()
    {
        return this.m_dialog;
    }

    public void setOpenedDialog(JDialog dialog)
    {
        this.m_dialog = dialog;
    }

    // ********************************************************
    // ****** Help stuff *****
    // ********************************************************

    // ********************************************************
    // ****** Fonts *****
    // ********************************************************

    public static final int FONT_BIG_BOLD = 0;
    public static final int FONT_NORMAL = 1;
    public static final int FONT_NORMAL_BOLD = 2;
    public static final int FONT_NORMAL_P2 = 3;
    public static final int FONT_NORMAL_BOLD_P2 = 4;

    public static final int FONT_UPDATE_TREE_HEADER = 5;
    public static final int FONT_UPDATE_TREE_ITEM = 6;

    public void loadFonts()
    {

        fonts = new Font[7];
        fonts[0] = new Font("SansSerif", Font.BOLD, 22);
        fonts[1] = new Font("SansSerif", Font.PLAIN, 12);
        fonts[2] = new Font("SansSerif", Font.BOLD, 12);
        fonts[3] = new Font("SansSerif", Font.PLAIN, 14);
        fonts[4] = new Font("SansSerif", Font.BOLD, 14);

        fonts[5] = new Font("SansSerif", Font.BOLD, 12);
        fonts[6] = new Font("SansSerif", Font.PLAIN, 12);
        
    }

    public Font getFont(int font_id)
    {
        return fonts[font_id];
    }

    // ********************************************************
    // ****** Parent handling (for UIs) *****
    // ********************************************************

    /*
     * private void loadIcons() { config_icons = new ImageIcon[7];
     * config_icons[0] = new ImageIcon(getImage("/images/cfg_db.gif", m_main));
     * config_icons[1] = new ImageIcon(getImage("/images/cfg_look.gif",
     * m_main)); config_icons[2] = new
     * ImageIcon(getImage("/images/cfg_myparish.gif", m_main)); config_icons[3]
     * = new ImageIcon(getImage("/images/cfg_masses.gif", m_main));
     * config_icons[4] = new ImageIcon(getImage("/images/cfg_users.gif",
     * m_main)); config_icons[5] = new
     * ImageIcon(getImage("/images/cfg_lang.gif", m_main)); config_icons[6] =
     * null;
     * 
     * }
     */

    // public ImageIcon getImageIcon(String image)

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
        catch (Exception ex)
        {
            System.out.println("Image " + filename + " could not be created.");
            //ex.printStackTrace();
            return null;
        }
        return img;
    }

    public void setParent(Container component)
    {
        this.parent = component;
    }

    public Component getParent()
    {
        return this.parent;
    }

    public ImageIcon getImageIcon_22x22(String name, Container comp)
    {
        return getImageIcon(name, 22, 22, comp);
    }

    public ImageIcon getImageIcon(String name, int width, int height,
            Container comp)
    {
        return getImageIcon(this.getImagesRoot(), name, width, height, comp);
    }

    public ImageIcon getImageIcon(String root, String name, int width,
            int height, Container comp)
    {
        return new ImageIcon(getImage(root + name, comp).getScaledInstance(
                width, height, Image.SCALE_SMOOTH));
    }

    public ImageIcon getImageIcon(String name, Container comp)
    {
        return getImageIcon(this.getImagesRoot(), name);
    }

    public ImageIcon getImageIcon(String root, String name, Component comp)
    {
        return new ImageIcon(getImage(root + name, comp));
    }

    public ImageIcon getImageIcon(String name)
    {
        return getImageIcon(this.getImagesRoot(), name);
    }

    public ImageIcon getImageIcon(String root, String name)
    {
        //File f = new File(".");
        
        //System.out.println("Start path: " + f.getAbsolutePath());
        //System.out.println("Image: " + root + name);
        
        //System.out.println("parent: " + this.getCurrentComponentParent());
        
        return new ImageIcon(getImage(root + name, this.getCurrentComponentParent()));
    }

    // ********************************************************
    // ****** Sorting algorithms *****
    // ********************************************************

    public int compareUnicodeStrings(String s1, String s2)
    {
        return this.m_collator.compare(s1, s2);
    }

    // ********************************************************
    // ****** GUI *****
    // ********************************************************

    
    public void centerJDialog(Component dialog)
    {
        Component cmp = this.getCurrentComponentParent();
        
        //System.out.println("comps: " + this.components);
        
        this.centerJDialog(dialog, cmp);
    }
    
    
    public void centerJDialog(Component dialog, Component /*Container*/ _parent)
    {

        Rectangle rec = _parent.getBounds();

        int x = rec.width / 2;
        x += (rec.x);

        int y = rec.height / 2;
        y += rec.y;

        x -= (dialog.getBounds().width / 2);
        y -= (dialog.getBounds().height / 2);

        if (x<0)
            x=0;
        
        if (y<0)
            y=0;
        
        // dialog.getBounds().x = x;
        // dialog.getBounds().y = y;

        dialog.setBounds(x, y, dialog.getBounds().width,
                dialog.getBounds().height);

        /*
         * if (parent instanceof JDialog) { //centerJDialog(dialog,
         * (JDialog)parent); } else System.out.println("CenterJDialog failed");
         */
    }

    /*
     * public void centerJDialog(JDialog dialog, JComponent parent) {
     * 
     * //System.out.println("centerJDialog: " );
     * 
     * Rectangle rec = parent.getBounds();
     * 
     * int x = rec.width/2; x += (rec.x);
     * 
     * int y = rec.height/2; y += rec.y;
     * 
     * x -= (dialog.getBounds().width/2); y -= (dialog.getBounds().height/2);
     * 
     * //dialog.getBounds().x = x; //dialog.getBounds().y = y;
     * 
     * dialog.setBounds(x, y, dialog.getBounds().width,
     * dialog.getBounds().height);
     * 
     * }
     */

    // ********************************************************
    // ****** Look and Feel *****
    // ********************************************************
    /*
     * public void loadAvailableLFs() {
     * 
     * availableLF_full = new Hashtable<String,String>();
     * UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
     * 
     * availableLF = new Object[info.length+1];
     * 
     * //ring selectedLF = null; //String subSelectedLF = null;
     * 
     * int i; for (i=0; i<info.length; i++) { String name = info[i].getName();
     * String className = info[i].getClassName();
     * 
     * availableLF_full.put(name, className); availableLF[i] = name;
     * 
     * //System.out.println(humanReadableName); }
     * 
     * availableLF_full.put("SkinLF",
     * "com.l2fprod.gui.plaf.skin.SkinLookAndFeel"); availableLF[i] = "SkinLF";
     * 
     * }
     */

    /*
     * public Object[] getAvailableLFs() { return
     * this.m_config_file.getAvailableLFs(); //return availableLF; }
     * 
     * 
     * public static String[] getLFData() {
     * 
     * String out[] = new String[2];
     * 
     * try { Properties props = new Properties();
     * 
     * FileInputStream in = new
     * FileInputStream("../data/PIS_Config.properties"); props.load(in);
     * 
     * out[0] = (String)props.get("LF_CLASS"); out[1] =
     * (String)props.get("SKINLF_SELECTED");
     * 
     * return out;
     * 
     * } catch(Exception ex) {
     * System.out.println("DataAccess::getLFData::Exception> " + ex);
     * ex.printStackTrace(); return null; } }
     */

    // ********************************************************
    // ****** Colors *****
    // ********************************************************
    public void loadColors()
    {
        ColorUIResource cui = (ColorUIResource) UIManager.getLookAndFeel()
                .getDefaults().get("textText");
        this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui
                .getBlue(), cui.getAlpha());

        ColorUIResource cui2 = (ColorUIResource) UIManager.getLookAndFeel()
                .getDefaults().get("Label.background");
        this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2
                .getBlue(), cui2.getAlpha());

        this.border_line = new LineBorder(this.color_foreground);
    }

    // ********************************************************
    // ****** Languages *****
    // ********************************************************

    /**
     * This method is intended to load additional Language info. Either special langauge configuration
     * or special data required for real Locale handling.
     */
    public abstract void loadLanguageInfo();
    
    
    /*
     * public Object[] getAvailableLanguages() { return new Object[] = { "en"
     * };; }
     * 
     * 
     * public int getSelectedLangIndex() { return 1; //return
     * m_lang_info.findInLocale(this.m_config_file.selected_lang); }
     * 
     * 
     * public static String getSelectedLocale() { String locale = "en";
     * 
     * 
     * 
     * try { Properties props = new Properties();
     * 
     * FileInputStream in = new FileInputStream(pathPrefix +
     * "../data/PIS_Config.properties"); props.load(in);
     * 
     * int sel_lang = 1;
     * 
     * if (props.containsKey("SELECTED_LANG")) { sel_lang =
     * Integer.parseInt((String)props.get("SELECTED_LANG"));
     * System.out.println("Sel lang: " + sel_lang); }
     * 
     * 
     * //props = new Properties(); props.clear();
     * 
     * in = null; in = new FileInputStream(pathPrefix +
     * "/data/lang/PIS_Languages.properties"); props.load(in);
     * 
     * if (props.containsKey("LANG_" + sel_lang + "_LOCALE")) { locale =
     * (String)props.get("LANG_" + sel_lang + "_LOCALE"); }
     * 
     * // System.out.println("Locale: " + locale);
     * 
     * } catch(Exception ex) {
     * System.out.println("DataAccess::getSelectedLocale::Exception> " + ex); }
     * 
     * return locale;
     * 
     * }
     */

    // ********************************************************
    // ****** JFormatted Text Field *****
    // ********************************************************

    public int getJFormatedTextValueInt(JFormattedTextField ftf)
    {
        try
        {
            ftf.commitEdit();
        }
        catch (Exception ex)
        {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Integer)
        {
            // System.out.println("Amount(long): " +
            // this.amountField.getValue());
            Integer l = (Integer) o;
            return l.intValue();
        }
        else if (o instanceof Long)
        {
            Long l = (Long) o;
            return l.intValue();
        }
        else if (o instanceof Byte)
        {
            Byte b = (Byte) o;
            return b.intValue();
        }
        else if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.intValue();
        }
        else if (o instanceof Float)
        {
            Float f = (Float) o;
            return f.intValue();
        }
        else
        // if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.intValue();
        }

    }

    public long getJFormatedTextValueLong(JFormattedTextField ftf)
    {
        try
        {
            ftf.commitEdit();
        }
        catch (Exception ex)
        {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Long)
        {
            Long l = (Long) o;
            return l.longValue();
        }
        else if (o instanceof Integer)
        {
            Integer l = (Integer) o;
            return l.longValue();
        }
        else if (o instanceof Byte)
        {
            Byte b = (Byte) o;
            return b.longValue();
        }
        else if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.longValue();
        }
        else if (o instanceof Float)
        {
            Float f = (Float) o;
            return f.longValue();
        }
        else
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

    public byte getJFormatedTextValueByte(JFormattedTextField ftf)
    {
        try
        {
            ftf.commitEdit();
        }
        catch (Exception ex)
        {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Byte)
        {
            Byte b = (Byte) o;
            return b.byteValue();
        }
        else if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.byteValue();
        }
        else if (o instanceof Integer)
        {
            Integer l = (Integer) o;
            return l.byteValue();
        }
        else if (o instanceof Long)
        {
            Long l = (Long) o;
            return l.byteValue();
        }
        else if (o instanceof Float)
        {
            Float f = (Float) o;
            return f.byteValue();
        }
        else
        // if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.byteValue();
        }

    }

    public short getJFormatedTextValueShort(JFormattedTextField ftf)
    {
        try
        {
            ftf.commitEdit();
        }
        catch (Exception ex)
        {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.shortValue();
        }
        else if (o instanceof Byte)
        {
            Byte b = (Byte) o;
            return b.shortValue();
        }
        else if (o instanceof Integer)
        {
            Integer l = (Integer) o;
            return l.shortValue();
        }
        else if (o instanceof Long)
        {
            Long l = (Long) o;
            return l.shortValue();
        }
        else if (o instanceof Float)
        {
            Float f = (Float) o;
            return f.shortValue();
        }
        else
        // if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.shortValue();
        }

    }

    public float getJFormatedTextValueFloat(JFormattedTextField ftf)
    {
        try
        {
            ftf.commitEdit();
        }
        catch (Exception ex)
        {
            System.out.println("Exception on commit value:" + ex + "\nValue:" + ftf.getValue());
            ex.printStackTrace();
        }

        Object o = ftf.getValue();

        if (o instanceof Float)
        {
            Float f = (Float) o;
            return f.floatValue();
        }
        else if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.floatValue();
        }
        else if (o instanceof Long)
        {
            Long l = (Long) o;
            return l.floatValue();
        }
        else if (o instanceof Integer)
        {
            Integer l = (Integer) o;
            return l.floatValue();
        }
        else if (o instanceof Byte)
        {
            Byte b = (Byte) o;
            return b.floatValue();
        }
        else
        // if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.floatValue();
        }

    }

    public double getJFormatedTextValueDouble(JFormattedTextField ftf)
    {
        try
        {
            ftf.commitEdit();
        }
        catch (Exception ex)
        {
            System.out.println("Exception on commit value:" + ex);
        }

        Object o = ftf.getValue();

        if (o instanceof Double)
        {
            Double d = (Double) o;
            return d.doubleValue();
        }
        else if (o instanceof Float)
        {
            Float f = (Float) o;
            return f.doubleValue();
        }
        else if (o instanceof Long)
        {
            Long l = (Long) o;
            return l.doubleValue();
        }
        else if (o instanceof Integer)
        {
            Integer l = (Integer) o;
            return l.doubleValue();
        }
        else if (o instanceof Byte)
        {
            Byte b = (Byte) o;
            return b.doubleValue();
        }
        else
        // if (o instanceof Short)
        {
            Short s = (Short) o;
            return s.doubleValue();
        }

    }

    /*
     * 
     * public String[] getAvailableDbs() { //this.m_config_file. //return
     * allDbs; return this.m_config_file.getAllDatabasesNamesAsArray(); }
     * 
     * 
     * public int getSelectedDbIndex() {
     * 
     * return this.m_config_file.getSelectedDatabaseIndex(); / for (int i=0;
     * i<allDbs.length; i++) { if (allDbs[i].startsWith(this.selected_db +
     * " - ")) return i; } return 0;
     */
    // }

    public String[] getMonthsArray()
    {
        return this.months;

        /*
         * String arr[] = new String[12];
         * 
         * arr[0] = m_i18n.getMessage("JANUARY"); arr[1] =
         * m_i18n.getMessage("FEBRUARY"); arr[2] = m_i18n.getMessage("MARCH");
         * arr[3] = m_i18n.getMessage("APRIL"); arr[4] =
         * m_i18n.getMessage("MAY"); arr[5] = m_i18n.getMessage("JUNE"); arr[6]
         * = m_i18n.getMessage("JULY"); arr[7] = m_i18n.getMessage("AUGUST");
         * arr[8] = m_i18n.getMessage("SEPTEMBER"); arr[9] =
         * m_i18n.getMessage("OCTOBER"); arr[10] =
         * m_i18n.getMessage("NOVEMBER"); arr[11] =
         * m_i18n.getMessage("DECEMBER");
         * 
         * return arr;
         */

    }

    /*
     * public void loadComboOptions() {
     * 
     * yes_no_combo = new Object[2];
     * 
     * yes_no_combo[0] = m_i18n.getMessage("OPTION_YES"); yes_no_combo[1] =
     * m_i18n.getMessage("OPTION_NO");
     * 
     * Hashtable ht = m_db.getProductType(-1);
     * 
     * typesAll = new Object[ht.size()];
     * 
     * int i = 0;
     * 
     * 
     * for(Enumeration en=ht.keys(); en.hasMoreElements(); ) {
     * 
     * String key = (String)en.nextElement();
     * 
     * String key2 = "";
     * 
     * if (key.length()==1) { key2 = "0"+key; } else key2 = key;
     * 
     * 
     * 
     * typesAll[i] = key2 + " - " +
     * ((ProductType)ht.get(key)).path.substring(1); i++;
     * 
     * }
     * 
     * Arrays.sort(typesAll);
     * 
     * 
     * 
     * 
     * }
     */

    /*
     * 
     * 
     * 
     * public void makeNewConfig() {
     * 
     * configStatic.config = new dataConfig();
     * 
     * 
     * configStatic.config.mainDir=""; configStatic.config.shellSelected=1;
     * configStatic.config.shellCommand="cmd /c";
     * configStatic.config.browserEnabled=true;
     * configStatic.config.browserExternal=false;
     * configStatic.config.mailEnabled=false;
     * configStatic.config.ftpEnabled=false;
     * configStatic.config.ftpInternalEnabled=false; saveConfig();
     * 
     * }
     * 
     * 
     * 
     * public void loadConfig() {
     * 
     * try { ObjectInputStream in = new ObjectInputStream( new
     * FileInputStream("../data/config.dat"));
     * configStatic.config=(dataConfig)in.readObject(); in.close(); } catch
     * (IOException ex) { makeNewConfig(); } catch (ClassNotFoundException ex) {
     * } }
     * 
     * public void saveConfig() {
     * 
     * try { ObjectOutputStream out = new ObjectOutputStream( new
     * FileOutputStream("../data/config.dat"));
     * out.writeObject(configStatic.config); out.close(); } catch (IOException
     * ex) { System.out.println("Error saving configuration."); }
     * 
     * }
     * 
     * 
     * / public String getShell() { //return configStatic.browserCommand; return
     * ""; }
     * 
     * public String getBrowserCmd() { // return
     * configStatic.browserPath[configStatic.useBrowserNr]; return "";
     * 
     * }
     * 
     * public String getMailerCmd() { // return configStatic.browserPath[1];
     * return ""; }
     */

    public String getDateString(int date)
    {

        // 20051012

        int year = date / 10000;
        int month = date - (year * 10000);

        month = month / 100;

        int day = date - (year * 10000) - (month * 100);

        if (year == 0)
        {
            return getLeadingZero(day, 2) + "/" + getLeadingZero(month, 2);
        }
        else
            return getLeadingZero(day, 2) + "/" + getLeadingZero(month, 2)
                    + "/" + year;

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

    public long getATDateTimeFromParts(int day, int month, int year, int hour,
            int minute, int type)
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
         * int y = (int)(dt/10000000L); dt -= y10000000L;
         * 
         * int m = (int)(dt/1000000L); dt -= m1000000L;
         * 
         * int d = (int)(dt/10000L); dt -= d10000L;
         * 
         * int h = (int)(dt/100L); dt -= h100L;
         * 
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
        {
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y
                    + "  " + getLeadingZero(h, 2) + ":"
                    + getLeadingZero(min, 2);
        }
        else if (ret_type == DT_DATE)
        {
            return getLeadingZero(d, 2) + "/" + getLeadingZero(m, 2) + "/" + y;
        }
        else
            return getLeadingZero(h, 2) + ":" + getLeadingZero(min, 2);

    }
/*
    public String getGCObjectFromDateTimeLong(long dt)
    {

        int y = (int) (dt / 100000000L);
        dt -= y * 100000000L;

        int m = (int) (dt / 1000000L);
        dt -= m * 1000000L;

        int d = (int) (dt / 10000L);
        dt -= d * 10000L;

        int h = (int) (dt / 100L);
        dt -= h * 100L;

        int min = (int) dt;

        GregorianCalendar gc1 = new GregorianCalendar();
        // gc1.set(GregorianCalendar.

        return null;

    }
*/
    public String getDateTimeString(int date, int time)
    {

        return getDateString(date) + " " + getTimeString(time);

    }

    
    public String getCurrentDateString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.get(Calendar.DAY_OF_MONTH) + "."
                + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR);
    }
    
    
    public String getCurrentTimeString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.get(Calendar.HOUR_OF_DAY) + ":"
                + gc.get(Calendar.MINUTE) + ":" + gc.get(Calendar.SECOND);
    }
    

    public String getCurrentDateTimeString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.get(Calendar.DAY_OF_MONTH) + "."
                + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR) + "  " 
                + gc.get(Calendar.HOUR_OF_DAY) + ":"
                + gc.get(Calendar.MINUTE) + ":" + gc.get(Calendar.SECOND);
    }

    
    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }

    /*
     * public static final int USER_NORMAL = 1; public static final int
     * USER_WORKER = 2; public static final int USER_ADMINISTRATOR = 3; public
     * static final int USER_SUPERADMINISTRATOR = 4;
     * 
     * public int user_type = 3;
     */

    /*
     * public int authorizeUser(String username, String password) {
     * 
     * System.out.println(username + " " + password);
     * 
     * if ((username.equalsIgnoreCase("andy")) && (password.equals("Satja"))) {
     * return DataAccess.USER_SUPERADMINISTRATOR; } else { loginType =
     * m_db.authenticateUser(username,password); return loginType; }
     * 
     * }
     */

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


    // **************************************************************************
    // ****                    String handling Methods                       ****
    // **************************************************************************
    
    
    
    public String getLeadingZero(int number, int places)
    {
        String nn = "" + number;

        while (nn.length() < places)
        {
            nn = "0" + nn;
        }

        return nn;
    }

    public String getLeadingZero(String number, int places)
    {
        number = number.trim();

        while (number.length() < places)
        {
            number = "0" + number;
        }

        return number;
    }
    
    
    
    /**
     * For replacing strings.<br>
     * 
     * @param input   Input String
     * @param replace What to seatch for.
     * @param replacement  What to replace with.
     * 
     * @return Parsed string.
     */
    public String replaceExpression(String input, String replace,
            String replacement)
    {

        int idx;
        if ((idx = input.indexOf(replace)) == -1)
        {
            return input;
        }

        boolean finished = false;
        
        while(!finished)
        {
        
            StringBuffer returning = new StringBuffer();
    
            while (idx != -1)
            {
                returning.append(input.substring(0, idx));
                returning.append(replacement);
                input = input.substring(idx + replace.length());
                idx = input.indexOf(replace);
            }
            returning.append(input);
            
            input = returning.toString();
            
            if ((idx = returning.indexOf(replace))==-1)
            {
                finished = true;
            }

        }

        return input;

    }
    


    public String parseExpression(String in, String expression, String replace)
    {

        StringBuffer buffer;

        int idx=in.indexOf(expression);
        
        if (replace==null)
            replace ="";
        
        if (idx==-1)
            return in;

        buffer = new StringBuffer();
        
        while (idx!=-1)
        {
            buffer.append(in.substring(0,idx));
            buffer.append(replace);

            in = in.substring(idx+expression.length());
            
            idx=in.indexOf(expression);
        }

        buffer.append(in);

        return buffer.toString();

    }



    public String parseExpressionFull(String in, String expression, String replace)
    {

        String buffer;

        int idx=in.indexOf(expression);
        
        if (replace==null)
            replace ="";
        
        if (idx==-1)
            return in;

        buffer = "";
        
        if (idx!=-1)
        {
            
            buffer = in.substring(0,idx) + replace + in.substring(idx+expression.length());
            
            idx=in.indexOf(expression);

            if (idx!=-1) 
                buffer = parseExpressionFull(buffer,expression,replace);

        }

        return buffer;

    }
    
    
    public boolean isEmptyOrUnset(String val)
    {
        if ((val == null) || (val.trim().length()==0))
        {
            return true;
        }
        else
            return false;
    }

    
    public static boolean isFound(String text, String search_str)
    {

        if ((search_str.trim().length() == 0) || (text.trim().length() == 0))
            return true;

        return text.trim().indexOf(search_str.trim()) != -1;
    }


    public String[] splitString(String input, String delimiter)
    {
        String res[] = null;

        if (!input.contains(delimiter))
        {
            res = new String[1];
            res[0] = input;
        }
        else
        {
            StringTokenizer strtok = new StringTokenizer(input, delimiter);

            res = new String[strtok.countTokens()];
            int i = 0;

            while (strtok.hasMoreTokens())
            {
                res[i] = strtok.nextToken().trim();
                i++;
            }
        }

        return res;

    }

    // ********************************************************
    // ****** Date/Time *****
    // ********************************************************
    
    
    public GregorianCalendar getGregorianCalendar(Date date)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        return gc;
    }

    
    public String getAsLocalizedDateString(GregorianCalendar gc_value, int years_digits)
    {
        if (years_digits==2)
        {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, getI18nControlInstance().getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
        else
        {
            // TODO: fix this
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, getI18nControlInstance().getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
    }
    
    
    public static final int GC_COMPARE_DAY = 1;
    public static final int GC_COMPARE_HOUR = 2;
    public static final int GC_COMPARE_MINUTE = 3;

    public boolean compareGregorianCalendars(int type, GregorianCalendar gc1, GregorianCalendar gc2)
    {
        boolean found = false;
        
        if ((gc1.get(GregorianCalendar.DAY_OF_MONTH)==gc2.get(GregorianCalendar.DAY_OF_MONTH)) && 
            (gc1.get(GregorianCalendar.MONTH)==gc2.get(GregorianCalendar.MONTH)) &&
            (gc1.get(GregorianCalendar.YEAR)==gc2.get(GregorianCalendar.YEAR)))
            found = true;
            
        if ((type == ATDataAccessAbstract.GC_COMPARE_HOUR) ||
            (type == ATDataAccessAbstract.GC_COMPARE_MINUTE))
        {
            if (gc1.get(GregorianCalendar.HOUR_OF_DAY)!=gc2.get(GregorianCalendar.HOUR_OF_DAY))
                found = false;
        }

        if (type == ATDataAccessAbstract.GC_COMPARE_MINUTE)
        {
            if (gc1.get(GregorianCalendar.MINUTE)!=gc2.get(GregorianCalendar.MINUTE))
                found = false;
        }
        

        return found;
    }
    
    
    
    // ********************************************************
    // ****** Get Values From Object *****
    // ********************************************************

    public float getFloatValue(Object aValue)
    {
        float out = 0.0f;

        // System.out.println("getFloatValue: ");

        if (aValue == null)
            return out;

        // System.out.println("getFloatValue: NOT NULL");

        if (aValue instanceof Float)
        {
            try
            {
                Float f = (Float) aValue;
                out = f.floatValue();
            }
            catch (Exception ex)
            {
            }
        }
        else if (aValue instanceof Double)
        {
            try
            {
                Double f = (Double) aValue;
                out = f.floatValue();
            }
            catch (Exception ex)
            {
            }
        }
        else if (aValue instanceof Integer)
        {
            try
            {
                Integer f = (Integer) aValue;
                out = f.floatValue();
            }
            catch (Exception ex)
            {
            }
        }
        else if (aValue instanceof Long)
        {
            try
            {
                Long f = (Long) aValue;
                out = f.floatValue();
            }
            catch (Exception ex)
            {
            }
        }
        else if (aValue instanceof String)
        {
            String s = (String) aValue;
            if (s.length() > 0)
            {
                try
                {
                    s = s.replace(",", ".");
                    out = Float.parseFloat(s);
                }
                catch (Exception ex)
                {
                    log.error("parse Float Exception [" + s + ": " + ex);
                }
            }
        }

        return out;
    }

    public int getIntValue(Object aValue)
    {
        int out = 0;

        if (aValue == null)
            return out;

        if (aValue instanceof Integer)
        {
            try
            {
                Integer i = (Integer) aValue;
                out = i.intValue();
            }
            catch (Exception ex)
            {
            }
        }
        else if (aValue instanceof String)
        {
            String s = (String) aValue;
            if (s.length() > 0)
            {
                try
                {
                    out = Integer.parseInt(s);
                }
                catch (Exception ex)
                {
                }
            }
        }

        return out;
    }

    public long getLongValue(Object aValue)
    {
        long out = 0L;

        if (aValue == null)
            return out;

        if (aValue instanceof Long)
        {
            try
            {
                Long i = (Long) aValue;
                out = i.longValue();
            }
            catch (Exception ex)
            {
            }
        }
        else if (aValue instanceof String)
        {
            String s = (String) aValue;
            if (s.length() > 0)
            {
                try
                {
                    out = Long.parseLong(s);
                }
                catch (Exception ex)
                {
                }
            }
        }

        return out;
    }

    // ********************************************************
    // ****** Get Values From String *****
    // ********************************************************

    public float getFloatValueFromString(String aValue)
    {
        return this.getFloatValueFromString(aValue, 0.0f);
    }

    public float getFloatValueFromString(String aValue, float def_value)
    {
        float out = def_value;
        
        // parse float, doesn't look in locale for decimal sign
        aValue = aValue.replace(",", ".");
        
        try
        {
            if ((aValue==null) || (aValue.trim().length()==0))
                return def_value;
            
            out = Float.parseFloat(aValue);
        }
        catch (Exception ex)
        {
            log.error("Error on parsing string to get float [" + aValue + "]:"
                    + ex, ex);
        }

        return out;
    }

    public int getIntValueFromString(String aValue)
    {
        return this.getIntValueFromString(aValue, 0);
    }

    public int getIntValueFromString(String aValue, int def_value)
    {
        int out = def_value;

        try
        {
            out = Integer.parseInt(aValue);
        }
        catch (Exception ex)
        {
            log.error("Error on parsing string to get int [" + aValue + "]:"
                    + ex, ex);
        }

        return out;
    }

    public long getLongValueFromString(String aValue)
    {
        return this.getLongValueFromString(aValue, 0L);
    }

    public long getLongValueFromString(String aValue, long def_value)
    {
        long out = def_value;

        try
        {
            out = Long.parseLong(aValue);
        }
        catch (Exception ex)
        {
            log.error("Error on parsing string to get long [" + aValue + "]:"
                    + ex, ex);
        }

        return out;
    }

    public boolean isValueSet(String val)
    {
        if ((val == null) || (val.trim().length() == 0) || (val.equals("null")))
            return false;
        else
            return true;
    }
    
    
    // ********************************************************
    // ****** Backup / Restore *****
    // ********************************************************

    protected BackupRestoreCollection backup_restore_collection = null;

    public boolean isBackupRestoreAvailable()
    {
        return (this.backup_restore_collection != null);
    }

    public BackupRestoreCollection getBackupRestoreCollection()
    {
        // TODO: clone
        return this.backup_restore_collection;
    }
    
    /**
     * Load Backup Restore Collection
     */
    public abstract void loadBackupRestoreCollection();

    public String makeI18nKeyword(String input)
    {
        String process = input.replaceAll(" ", "_");
        process = process.toUpperCase();

        return process;
    }


    public UpdateConfiguration getUpdateConfiguration()
    {
        if (this.update_configuration==null)
        {
            update_configuration = new UpdateConfiguration();
        }
        
        return this.update_configuration;
    }
    

    //public abstract String getUpdateConfigurationFile();
    
    
    
    /**
     * Get Configuration - reads properties file and read all entries
     * 
     * @param filename
     * @return
     */    
    public Hashtable<String,String> getConfiguration(String filename)
    {

        Hashtable<String,String> config_db_values = new Hashtable<String,String>();

        Properties props = new Properties();

        boolean config_loaded = true;

        try
        {
            FileInputStream in = new FileInputStream(filename);
            props.load(in);
        }
        catch (Exception ex)
        {
            config_loaded = false;
        }


        if (config_loaded)
        {

            for (Enumeration<Object> en = props.keys(); en.hasMoreElements(); )
            {
                String  key = (String)en.nextElement();
                config_db_values.put(key, props.getProperty(key));
            }
        }

        return config_db_values;
        
    }

    
    /**
     * Is Option Enabled
     * 
     * @param value
     * @return
     */
    public boolean isOptionEnabled(String value)
    {
        
        if (value==null)
            return false;
        
        String val = value.toUpperCase();
        
        if (val.equals("ENABLED") || val.equals("YES") || val.equals("TRUE") || val.equals("1"))
            return true;
        else
            return false;
    }    
    
    
    // 
    // Graph Config Properties

    /**
     * Load Graph Config Properties
     */
    public abstract void loadGraphConfigProperties();
    
    public GraphConfigProperties getGraphConfigProperties()
    {
        return this.graph_config;
    }
    
    
    
    
    public abstract void loadSpecialParameters();
    
    
    public Hashtable<String, String> getSpecialParameters()
    {
        return special_parameters;
    }
    
    
    
    public LanguageInfo getLanguageInfo()
    {
        return this.m_lang_info;
    }
 
    
    public abstract int getSelectedLangIndex();

    
    public abstract void setSelectedLangIndex(int index);
 
    
    public Hashtable<String,PlugInClient> getPlugins()
    {
        return this.plugins;
    }
    
    
    
}