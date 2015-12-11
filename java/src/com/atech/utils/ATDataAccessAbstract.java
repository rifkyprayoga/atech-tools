package com.atech.utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.db.ext.ExtendedHandler;
import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.hdb_object.User;
import com.atech.db.hibernate.tool.DbToolApplicationAbstract;
import com.atech.db.hibernate.transfer.BackupRestoreCollection;
import com.atech.graphics.dialogs.ErrorDialog;
import com.atech.graphics.graphs.GraphConfigProperties;
import com.atech.gui_fw.config.AbstractConfigurationContext;
import com.atech.help.HelpCapable;
import com.atech.help.HelpContext;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.info.LanguageInfo;
import com.atech.misc.converter.ATechConverter;
import com.atech.misc.converter.DecimalHandler;
import com.atech.plugin.PlugInClient;
import com.atech.update.config.UpdateConfiguration;
import com.atech.utils.data.CodeEnumWithTranslation;
import com.atech.utils.data.ExceptionHandling;

/**
 *  This file is part of ATech Tools library.
 *
 *  ATDataAccessAbstract - This is main class for most of data handling, with lot of
 *      util methods
 *  Copyright (C) 2007-2015  Andy (Aleksander) Rozman (Atech-Software)
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

    private static Logger LOG = LoggerFactory.getLogger(ATDataAccessAbstract.class);

    // GC compare
    public static final int GC_COMPARE_DAY = 1;
    public static final int GC_COMPARE_HOUR = 2;
    public static final int GC_COMPARE_MINUTE = 3;
    public static final int GC_COMPARE_SECOND = 4;

    // config file
    public static final int DIALOG_INFO = 1;
    public static final int DIALOG_WARNING = 2;
    public static final int DIALOG_ERROR = 3;
    public static final int LIST_HT_KEY = 1;
    public static final int LIST_HT_VALUE = 2;

    // The path prefix.
    public static String pathPrefix = ".";

    // contacts
    public static String contact_types[] = null;
    public static ImageIcon contact_icons[] = null;

    // days, months
    public static String days[] = new String[7];
    public static String gcDays[] = new String[7];
    public static String months[] = new String[12];
    public static String[] options_yes_no = null;
    // public Object[] typesAll = null;
    public LineBorder border_line;

    // Configuration Contexts for Application and other application wide
    // settings
    public AbstractConfigurationContext configuration_context = null;
    public DbToolApplicationAbstract db_tool_app = null;
    public Hashtable<String, PlugInClient> plugins;
    private UpdateConfiguration update_configuration = null;
    private ExceptionHandling numberParsingExceptionHandling = ExceptionHandling.CATCH_EXCEPTION_WITH_STACK_TRACE;
    protected LanguageInfo m_lang_info;
    protected I18nControlAbstract m_i18n = null; // ATI18nControl.getInstance();
    protected Collator m_collator = null;
    protected HibernateDb hib_db = null;
    protected DecimalHandler decimal_handler = null;
    protected Hashtable<String, ExtendedHandler> extended_handlers = null;
    protected Hashtable<String, ATechConverter> converters = new Hashtable<String, ATechConverter>();
    protected Hashtable<String, String> sorters = new Hashtable<String, String>();
    protected BackupRestoreCollection backupRestoreCollection = null;

    // Application settings
    public boolean config_loaded = false;
    protected boolean helpEnabled = false;
    protected int current_db_version = 0;
    protected boolean developer_mode = false;
    protected Hashtable<String, String> special_parameters = null;
    private int db_loading_status = 0;

    // user management
    public String[] user_types = null;
    public long current_user_id;
    public boolean demo_version = false;
    protected User logged_user = null;
    protected ArrayList<User> all_users = null;

    // misc settings
    public Hashtable<String, String> m_settings_ht = null;
    public Color color_background;
    public Color color_foreground;

    // parent and component handling
    JDialog m_dialog = null;
    JFrame main_parent = null;
    private long component_id_last = 0L;
    protected ArrayList<Component> components = new ArrayList<Component>();
    protected Container parent = null;
    public int main_parent_type = 1;

    /**
     * The graph_config. (?)
     */
    protected GraphConfigProperties graph_config = null;

    /**
     * Update System v2 (?)
     */
    protected String app_name = null;
    protected int app_version = 0;
    protected int app_db_version = 0;

    HelpContext help_context = null;


    /**
     * This is DataAccess constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.
     * 
     * @param ic
     *
     */
    public ATDataAccessAbstract(I18nControlAbstract ic)
    {
        this.m_i18n = ic;
        loadArraysTranslation();
        checkPrerequisites();

        m_settings_ht = new Hashtable<String, String>();
        plugins = new Hashtable<String, PlugInClient>();

        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! COLATOR " + this);
        m_collator = this.m_i18n.getCollationDefintion();
        loadPlugIns();
        loadBackupRestoreCollection();
        loadExtendedHandlers();
        loadUserTypes();

        this.loadConfigurationContexts();
        this.loadDbApplicationContext();

        this.decimal_handler = new DecimalHandler(this.getMaxDecimalsUsedByDecimalHandler());

        // initSpecial();
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    // ********************************************************
    // ****** Config Loader *****
    // ********************************************************

    /**
     * Not implemented.
     *
     * @param source the source
     */
    public static void notImplemented(String source)
    {
        System.out.println("Not Implemented: " + source);
    }


    // ********************************************************
    // ****** Database Loading Status *****
    // ********************************************************

    /**
     * Not implemented.
     *
     * @param parent the parent
     * @param source the source
     */
    public static void notImplemented(java.awt.Component parent, String source)
    {
        System.out.println("Not Implemented: " + source);
        JOptionPane.showMessageDialog(parent, "Not Implemented: \n" + source);
    }


    /**
     * Gets the leading zero.
     *
     * @param number the number
     * @param places the places
     *
     * @return the leading zero
     */
    public static String getLeadingZero(int number, int places)
    {
        String nn = "" + number;

        while (nn.length() < places)
        {
            nn = "0" + nn;
        }

        return nn;
    }


    public static String getGregorianCalendarAsDateString(GregorianCalendar gc)
    {
        return getLeadingZero(gc.get(Calendar.DAY_OF_MONTH), 2) + "." + getLeadingZero((gc.get(Calendar.MONTH) + 1), 2)
                + "." + gc.get(Calendar.YEAR);
    }


    public static String getGregorianCalendarAsDateTimeString(GregorianCalendar gc)
    {
        return getLeadingZero(gc.get(Calendar.DAY_OF_MONTH), 2) + "." //
                + getLeadingZero((gc.get(Calendar.MONTH) + 1), 2) + "." //
                + gc.get(Calendar.YEAR) + " " //
                + getLeadingZero(gc.get(Calendar.HOUR_OF_DAY), 2) + ":" //
                + getLeadingZero(gc.get(Calendar.MINUTE), 2) + ":" //
                + getLeadingZero(gc.get(Calendar.SECOND), 2);
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
    public static String replaceExpression(String input, String replace, String replacement)
    {

        int idx;
        if ((idx = input.indexOf(replace)) == -1)
            return input;

        boolean finished = false;

        while (!finished)
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

            if ((idx = returning.indexOf(replace)) == -1)
            {
                finished = true;
            }

        }

        return input;

    }


    // ********************************************************
    // ****** Demo stuff *****
    // ********************************************************

    /**
     * Checks if is empty or unset.
     *
     * @param val the val
     *
     * @return true, if is empty or unset
     */
    public static boolean isEmptyOrUnset(String val)
    {
        if (val == null || val.trim().length() == 0)
            return true;
        else
            return false;
    }


    /**
     * Checks if is found.
     *
     * @param text the text
     * @param search_str the search_str
     *
     * @return true, if is found
     */
    public static boolean isFound(String text, String search_str)
    {

        if (search_str.trim().length() == 0 || text.trim().length() == 0)
            return true;

        return text.trim().indexOf(search_str.trim()) != -1;
    }


    /**
     * Is Option Enabled
     *
     * @param value
     * @return
     */
    public static boolean isOptionEnabled(String value)
    {

        if (value == null)
            return false;

        String val = value.toUpperCase();

        if (val.equals("ENABLED") || val.equals("YES") || val.equals("TRUE") || val.equals("1"))
            return true;
        else
            return false;
    }

    // ********************************************************
    // ****** Help stuff *****
    // ********************************************************

    // ********************************************************
    // ****** Fonts *****
    // ********************************************************

    /**
     * The Constant FONT_UPDATE_TREE_HEADER.
     */
    // public static final int FONT_UPDATE_TREE_HEADER = 5;


    /**
     * The Constant FONT_UPDATE_TREE_ITEM.
     */
    // public static final int FONT_UPDATE_TREE_ITEM = 6;

    /**
     * We check (bitwise) if value_we_are looking for is set in bitwise_containing_value
     *
     * @param value
     * @param value_we_check_for
     * @return
     */
    public static boolean isBitwiseSet(int value, int value_we_check_for)
    {
        return (value & value_we_check_for) == value_we_check_for;
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
     * }
     */


    // public ImageIcon getImageIcon(String image)

    public static int getTypeFromDescription(String selectedItem,
            Map<String, CodeEnumWithTranslation> translationMapping)
    {
        if (selectedItem == null)
        {
            return 0;
        }

        if (translationMapping.containsKey(selectedItem))
        {
            return translationMapping.get(selectedItem).getCode();
        }
        else
        {
            return 0;
        }
    }


    public static String getTimeFromMinutes(int minutes)
    {
        int h = minutes / 60;
        int m = minutes - (h * 60);

        return getLeadingZero(h, 2) + ":" + getLeadingZero(m, 2);
    }


    // ********************************************************
    // ****** Sorting algorithms *****
    // ********************************************************

    /**
     * Inits the special.
     */
    public abstract void initSpecial();


    // ********************************************************
    // ****** Colors *****
    // ********************************************************

    /**
     * Gets the hibernate db.
     *
     * @return the hibernate db
     */
    public abstract HibernateDb getHibernateDb();


    /**
     * Load plug ins.
     */
    public abstract void loadPlugIns();


    // INIT

    public void loadArraysTranslation()
    {
        loadArraysTranslation(m_i18n);
    }


    public void loadArraysTranslation(I18nControlAbstract ic)
    {

        if ((months[0] == null) || ("JANUARY".equals(months[0])))
        {
            // months
            months[0] = ic.getMessage("JANUARY");
            months[1] = ic.getMessage("FEBRUARY");
            months[2] = ic.getMessage("MARCH");
            months[3] = ic.getMessage("APRIL");
            months[4] = ic.getMessage("MAY");
            months[5] = ic.getMessage("JUNE");
            months[6] = ic.getMessage("JULY");
            months[7] = ic.getMessage("AUGUST");
            months[8] = ic.getMessage("SEPTEMBER");
            months[9] = ic.getMessage("OCTOBER");
            months[10] = ic.getMessage("NOVEMBER");
            months[11] = ic.getMessage("DECEMBER");
        }

        if ((days[0] == null) || ("MONDAY".equals(days[0])))
        {
            // days
            days[0] = ic.getMessage("MONDAY");
            days[1] = ic.getMessage("TUESDAY");
            days[2] = ic.getMessage("WEDNESDAY");
            days[3] = ic.getMessage("THURSDAY");
            days[4] = ic.getMessage("FRIDAY");
            days[5] = ic.getMessage("SATURDAY");
            days[6] = ic.getMessage("SUNDAY");

            gcDays[0] = ic.getMessage("SUNDAY");
            gcDays[1] = ic.getMessage("MONDAY");
            gcDays[2] = ic.getMessage("TUESDAY");
            gcDays[3] = ic.getMessage("WEDNESDAY");
            gcDays[4] = ic.getMessage("THURSDAY");
            gcDays[5] = ic.getMessage("FRIDAY");
            gcDays[6] = ic.getMessage("SATURDAY");
        }

    }


    /**
     * Gets the current component parent.
     *
     * @return the current component parent
     */
    public Component getCurrentComponentParent()
    {
        // System.out.println("Size: " + this.components.size());

        return this.components.get(this.components.size() - 2);
    }


    /**
     * Gets the current component.
     *
     * @return the current component
     */
    public Component getCurrentComponent()
    {
        return this.components.get(this.components.size() - 1);
    }


    /**
     * Adds the component.
     *
     * @param cmp the cmp
     */
    public void addComponent(Component cmp)
    {
        // System.out.println("Add: " + this.components);
        this.components.add(cmp);
        // System.out.println("Add: " + this.components);
    }


    /**
     * List components.
     */
    public void listComponents()
    {
        System.out.println("Lst: " + this.components);
    }


    /**
     * Removes the component.
     *
     * @param cmp the cmp
     */
    public void removeComponent(Component cmp)
    {
        ArrayList<Component> newComponents = new ArrayList<Component>();

        for (Component c : this.components)
        {
            if (!c.equals(cmp))
            {
                newComponents.add(c);
            }
        }

        this.components = newComponents;
    }


    /**
     * Creates the error dialog.
     *
     * @param module the module
     * @param action the action
     * @param ex the ex
     * @param err_msg1 the err_msg1
     */
    public void createErrorDialog(String module, String action, Exception ex, String err_msg1)
    {
        createErrorDialog(module, action, ex, err_msg1, null);
    }


    /**
     * Creates the error dialog.
     *
     * @param module the module
     * @param action the action
     * @param ex the ex
     * @param errorMessage the err_msg1
     * @param solutionMessage the err_msg2
     */
    public void createErrorDialog(String module, String action, Exception ex, String errorMessage,
            String solutionMessage)
    {

        if (this.getCurrentComponentParent() instanceof JFrame)
        {
            new ErrorDialog((JFrame) this.getCurrentComponentParent(), this, this.getApplicationName(), module, action,
                    ex, errorMessage, solutionMessage);
        }
        else
        {
            new ErrorDialog((JDialog) this.getCurrentComponentParent(), this, this.getApplicationName(), module, action,
                    ex, errorMessage, solutionMessage);
        }
    }


    public void createErrorDialog(Exception ex, String errorMessage, String solutionMessage)
    {

        if (this.getCurrentComponentParent() instanceof JFrame)
        {
            new ErrorDialog((JFrame) this.getCurrentComponentParent(), this, this.getApplicationName(), null, null, ex,
                    errorMessage, solutionMessage);
        }
        else
        {
            new ErrorDialog((JDialog) this.getCurrentComponentParent(), this, this.getApplicationName(), null, null, ex,
                    errorMessage, solutionMessage);
        }
    }


    public void createErrorDialog(Exception ex, String errorMessage, String errorMessageToolTip, String solutionMessage,
            String solutionMessageToolTip)
    {

        if (this.getCurrentComponentParent() instanceof JFrame)
        {
            new ErrorDialog((JFrame) this.getCurrentComponentParent(), this, this.getApplicationName(), null, null, ex,
                    errorMessage, errorMessageToolTip, solutionMessage, solutionMessageToolTip);
        }
        else
        {
            new ErrorDialog((JDialog) this.getCurrentComponentParent(), this, this.getApplicationName(), null, null, ex,
                    errorMessage, errorMessageToolTip, solutionMessage, solutionMessageToolTip);
        }
    }


    public void createErrorDialog(String module, String action, Exception ex, String errorMessage,
            String errorMessageToolTip, String solutionMessage, String solutionMessageToolTip)
    {

        if (this.getCurrentComponentParent() instanceof JFrame)
        {
            new ErrorDialog((JFrame) this.getCurrentComponentParent(), this, this.getApplicationName(), module, action,
                    ex, errorMessage, errorMessageToolTip, solutionMessage, solutionMessageToolTip);
        }
        else
        {
            new ErrorDialog((JDialog) this.getCurrentComponentParent(), this, this.getApplicationName(), module, action,
                    ex, errorMessage, errorMessageToolTip, solutionMessage, solutionMessageToolTip);
        }
    }


    /**
     * Get Application Name
     *
     * @return
     */
    public abstract String getApplicationName();


    /**
     * Get Images Root (Must have ending back-slash)
     *
     * @return images root path
     */
    public abstract String getImagesRoot();


    /**
     * Gets the main parent.
     *
     * @return the main parent
     */
    public JFrame getMainParent()
    {
        return this.main_parent;
    }


    /**
     * Sets the main parent.
     *
     * @param frame the new main parent
     */
    public void setMainParent(JFrame frame)
    {
        this.main_parent = frame;
        this.addComponent(this.main_parent);
    }


    /**
     * Check Prerequisites
     */
    public abstract void checkPrerequisites();


    /**
     * Gets the help context.
     *
     * @return the help context
     */
    public HelpContext getHelpContext()
    {
        return this.help_context;
    }


    /**
     * Sets the help context.
     *
     * @param hc the new help context
     */
    public void setHelpContext(HelpContext hc)
    {
        this.help_context = hc;
    }


    /**
     * Enable help.
     *
     * @param hc the hc
     */
    public void enableHelp(HelpCapable hc)
    {
        if (hc != null && this.helpEnabled)
        {
            try
            {
                if (help_context == null || this.help_context.getMainHelpBroker() == null)
                {
                    LOG.warn("Help Context not available. Help not enabled.");
                }
                else
                {
                    this.help_context.getMainHelpBroker().enableHelpOnButton(hc.getHelpButton(), hc.getHelpId(), null);
                    this.help_context.getMainHelpBroker().enableHelpKey(hc.getComponent(), hc.getHelpId(), null);
                }
            }
            catch (Exception ex)
            {
                LOG.error("Error enabling help. Ex.: " + ex, ex);
            }
        }
    }


    /**
     * Gets the i18n control instance.
     *
     * @return the i18n control instance
     */
    public I18nControlAbstract getI18nControlInstance()
    {
        return this.m_i18n;
    }


    /**
     * Adds the plug in.
     *
     * @param key the key
     * @param plugin the plugin
     */
    public void addPlugIn(String key, PlugInClient plugin)
    {
        this.plugins.put(key, plugin);
    }

    /*
     * public static final int USER_NORMAL = 1; public static final int
     * USER_WORKER = 2; public static final int USER_ADMINISTRATOR = 3; public
     * static final int USER_SUPERADMINISTRATOR = 4;
     * public int user_type = 3;
     */


    /*
     * public int authorizeUser(String username, String password) {
     * System.out.println(username + " " + password);
     * if ((username.equalsIgnoreCase("andy")) && (password.equals("Satja"))) {
     * return DataAccess.USER_SUPERADMINISTRATOR; } else { loginType =
     * m_db.authenticateUser(username,password); return loginType; }
     * }
     */

    /**
     * Gets the plug in.
     *
     * @param key the key
     *
     * @return the plug in
     */
    public PlugInClient getPlugIn(String key)
    {
        return this.plugins.get(key);
    }


    /**
     * Checks if plugin is available
     *
     * @param key
     * @return
     */
    public boolean isPluginAvailable(String key)
    {
        return (this.plugins != null && this.plugins.containsKey(key) && this.plugins.get(key).isPlugInInstalled());
    }


    // **************************************************************************
    // **** String handling Methods ****
    // **************************************************************************

    /**
     * Was config loaded.
     *
     * @return true, if successful
     */
    public boolean wasConfigLoaded()
    {
        return this.config_loaded;
    }


    /**
     * Load property file.
     *
     * @param filename the filename
     *
     * @return the hashtable< string, string>
     */
    public Hashtable<String, String> loadPropertyFile(String filename)
    {

        Hashtable<String, String> config_db_values_ = new Hashtable<String, String>();

        Properties props = new Properties();

        this.config_loaded = true;

        try
        {
            // File f = new File(".");
            // System.out.println("File: " + f.getCanonicalPath());

            FileInputStream in = new FileInputStream(filename);
            props.load(in);
        }
        catch (Exception ex)
        {
            System.out.println("Error loading config file (" + filename + "): " + ex);
            this.config_loaded = false;
        }

        if (this.config_loaded)
        {

            for (Enumeration<Object> en = props.keys(); en.hasMoreElements();)
            {
                String key = (String) en.nextElement();
                config_db_values_.put(key, props.getProperty(key));
            }
        }
        else
            return null;

        return config_db_values_;

    }


    /**
     * Load property file that is codepage specific (this java doesn't do, so we need to do this manually)
     *
     * @param filename the filename
     * @param codepage
     *
     * @return the hashtable< string, string>
     */
    public Hashtable<String, String> loadPropertyFile(String filename, String codepage)
    {

        Hashtable<String, String> config_db_values_ = new Hashtable<String, String>();

        // Properties props = new Properties();

        this.config_loaded = true;

        try
        {
            // File f = new File(".");
            // System.out.println("File: " + f.getCanonicalPath());

            // FileInputStream in = new FileInputStream(new File(filename),
            // "UTF8"));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), codepage));

            String line = null;

            while ((line = br.readLine()) != null)
            {
                line = line.trim();

                if (line.contains("=") && (!line.startsWith(";") || !line.startsWith("#")))
                {
                    config_db_values_.put(line.substring(0, line.indexOf("=")), line.substring(line.indexOf("=") + 1));
                }

            }

        }
        catch (Exception ex)
        {
            System.out.println("Error loading config file (" + filename + "): " + ex);
            this.config_loaded = false;
            ex.printStackTrace();
            return null;
        }

        return config_db_values_;

    }


    /**
     * Gets the db loading status.
     *
     * @return the db loading status
     */
    public int getDbLoadingStatus()
    {
        return this.db_loading_status;
    }


    /**
     * Sets the db loading status.
     *
     * @param status the new db loading status
     */
    public void setDbLoadingStatus(int status)
    {
        this.db_loading_status = status;
    }


    /**
     * Checks if is db loaded for status.
     *
     * @param status the status
     *
     * @return true, if is db loaded for status
     */
    public boolean isDbLoadedForStatus(int status)
    {
        if (this.db_loading_status == status || this.db_loading_status > status)
            return true;
        else
            return false;
    }


    /**
     * Checks if is true
     *
     * @param val the val
     *
     * @return true, if is empty or unset
     */
    /*
     * public static boolean isOptionEnabled(String val)
     * {
     * if ((val == null) || (val.trim().length()==0))
     * {
     * return true;
     * }
     * else
     * return false;
     * }
     */

    /**
     * Gets the opened dialog.
     *
     * @return the opened dialog
     */
    public JDialog getOpenedDialog()
    {
        return this.m_dialog;
    }


    /**
     * Sets the opened dialog.
     *
     * @param dialog the new opened dialog
     */
    public void setOpenedDialog(JDialog dialog)
    {
        this.m_dialog = dialog;
    }


    // ********************************************************
    // ****** Date/Time *****
    // ********************************************************

    /**
     * Center j dialog.
     *
     * @param dialog the dialog
     */
    public void centerJDialog(Component dialog)
    {
        Component cmp = this.getCurrentComponentParent();

        // System.out.println("comps: " + this.components);

        ATSwingUtils.centerJDialog(dialog, cmp);
    }


    /**
     * Gets the parent.
     *
     * @return the parent
     */
    public Component getParent()
    {
        return this.parent;
    }


    /**
     * Sets the parent.
     *
     * @param component the new parent
     */
    public void setParent(Container component)
    {
        this.parent = component;
    }


    /**
     * Compare unicode strings.
     *
     * @param s1 the s1
     * @param s2 the s2
     *
     * @return the int
     */
    public int compareUnicodeStrings(String s1, String s2)
    {
        if (m_collator == null)
        {
            this.m_collator = this.getI18nControlInstance().getCollationDefintion();
            LOG.debug("Created collator again [da=" + this + ", colator=" + m_collator + "]");
        }

        /*
         * if (m_collator==null)
         * {
         * return s1.compareTo(s2);
         * }
         * else
         * {
         */
        return m_collator.compare(s1, s2);
        // }
    }


    /**
     * Load colors.
     */
    public void loadColors()
    {
        ColorUIResource cui = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("textText");
        this.color_foreground = new Color(cui.getRed(), cui.getGreen(), cui.getBlue(), cui.getAlpha());

        ColorUIResource cui2 = (ColorUIResource) UIManager.getLookAndFeel().getDefaults().get("Label.background");
        this.color_background = new Color(cui2.getRed(), cui2.getGreen(), cui2.getBlue(), cui2.getAlpha());

        this.border_line = new LineBorder(this.color_foreground);
    }


    /**
     * This method is intended to load additional Language info. Either special langauge configuration
     * or special data required for real Locale handling.
     */
    public abstract void loadLanguageInfo();


    /**
     * Gets the months array.
     *
     * @return the months array
     */
    public String[] getMonthsArray()
    {
        return months;

        /*
         * String arr[] = new String[12];
         * arr[0] = m_i18n.getMessage("JANUARY"); arr[1] =
         * m_i18n.getMessage("FEBRUARY"); arr[2] = m_i18n.getMessage("MARCH");
         * arr[3] = m_i18n.getMessage("APRIL"); arr[4] =
         * m_i18n.getMessage("MAY"); arr[5] = m_i18n.getMessage("JUNE"); arr[6]
         * = m_i18n.getMessage("JULY"); arr[7] = m_i18n.getMessage("AUGUST");
         * arr[8] = m_i18n.getMessage("SEPTEMBER"); arr[9] =
         * m_i18n.getMessage("OCTOBER"); arr[10] =
         * m_i18n.getMessage("NOVEMBER"); arr[11] =
         * m_i18n.getMessage("DECEMBER");
         * return arr;
         */

    }


    // ********************************************************
    // ****** Get Values From String *****
    // ********************************************************

    /**
     * Gets the current date string.
     *
     * @return the current date string
     */
    public String getCurrentDateString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.get(Calendar.DAY_OF_MONTH) + "." + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR);
    }


    /**
     * Gets the current time string.
     *
     * @return the current time string
     */
    public String getCurrentTimeString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE) + ":" + gc.get(Calendar.SECOND);
    }


    /**
     * Gets the current date time string.
     *
     * @return the current date time string
     */
    public String getCurrentDateTimeString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");

        return sdf.format(gc.getTime());
        // String dom = "";
        // String mon = "";
        // String h = "";
        // String m = "";
        // String s = "";
        //
        // dom += gc.get(Calendar.DAY_OF_MONTH);
        // mon += (gc.get(Calendar.MONTH) + 1);
        // h += gc.get(Calendar.HOUR_OF_DAY);
        // m += gc.get(Calendar.MINUTE);
        // s += gc.get(Calendar.SECOND);
        //
        // if (dom.length() == 1)
        // dom = "0" + dom;
        // if (mon.length() == 1)
        // mon = "0" + mon;
        // if (h.length() == 1)
        // h = "0" + h;
        // if (m.length() == 1)
        // m = "0" + m;
        // if (s.length() == 1)
        // s = "0" + s;
        //
        // return dom + "." + mon + "." + gc.get(Calendar.YEAR) + " " + h + ":"
        // + m + ":" + s;
    }


    /**
     * Gets the start year.
     *
     * @return the start year
     */
    public int getStartYear()
    {
        // FIX set in Db
        return 1800;
    }


    /**
     * Gets the leading zero.
     *
     * @param number the number
     * @param places the places
     *
     * @return the leading zero
     */
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
     * Parses the expression.
     *
     * @param in the in
     * @param expression the expression
     * @param replace the replace
     *
     * @return the string
     */
    public String parseExpression(String in, String expression, String replace)
    {

        StringBuffer buffer;

        int idx = in.indexOf(expression);

        if (replace == null)
        {
            replace = "";
        }

        if (idx == -1)
            return in;

        buffer = new StringBuffer();

        while (idx != -1)
        {
            buffer.append(in.substring(0, idx));
            buffer.append(replace);

            in = in.substring(idx + expression.length());

            idx = in.indexOf(expression);
        }

        buffer.append(in);

        return buffer.toString();

    }


    /**
     * Parses the expression full.
     *
     * @param in the in
     * @param expression the expression
     * @param replace the replace
     *
     * @return the string
     */
    public String parseExpressionFull(String in, String expression, String replace)
    {

        String buffer;

        int idx = in.indexOf(expression);

        if (replace == null)
        {
            replace = "";
        }

        if (idx == -1)
            return in;

        buffer = "";

        if (idx != -1)
        {

            buffer = in.substring(0, idx) + replace + in.substring(idx + expression.length());

            idx = in.indexOf(expression);

            if (idx != -1)
            {
                buffer = parseExpressionFull(buffer, expression, replace);
            }

        }

        return buffer;

    }


    /**
     * Split string.
     *
     * @param input the input
     * @param delimiter the delimiter
     *
     * @return the string[]
     */
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


    /**
     * Gets the gregorian calendar.
     *
     * @param date the date
     *
     * @return the gregorian calendar
     */
    public GregorianCalendar getGregorianCalendar(Date date)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        return gc;
    }


    /**
     * Gets the as localized date string.
     *
     * @param gc_value the gc_value
     * @param years_digits the years_digits
     *
     * @return the as localized date string
     */
    public String getAsLocalizedDateString(GregorianCalendar gc_value, int years_digits)
    {
        if (years_digits == 2)
        {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                getI18nControlInstance().getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
        else
        {
            System.out.println("i18ControlInstance: " + getI18nControlInstance());
            System.out.println("i18ControlInstance: " + getI18nControlInstance().getSelectedLanguageLocale());
            System.out.println("Time: " + gc_value.getTime());

            // TODO: fix this
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                getI18nControlInstance().getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
    }


    /**
     * Compare gregorian calendars.
     *
     * @param type the type
     * @param gc1 the gc1
     * @param gc2 the gc2
     *
     * @return true, if successful
     */
    public boolean compareGregorianCalendars(int type, GregorianCalendar gc1, GregorianCalendar gc2)
    {
        boolean found = false;

        if (gc1.get(Calendar.DAY_OF_MONTH) == gc2.get(Calendar.DAY_OF_MONTH)
                && gc1.get(Calendar.MONTH) == gc2.get(Calendar.MONTH)
                && gc1.get(Calendar.YEAR) == gc2.get(Calendar.YEAR))
        {
            found = true;
        }

        if (type == ATDataAccessAbstract.GC_COMPARE_HOUR || type == ATDataAccessAbstract.GC_COMPARE_MINUTE
                || type == ATDataAccessAbstract.GC_COMPARE_SECOND)
        {
            if (gc1.get(Calendar.HOUR_OF_DAY) != gc2.get(Calendar.HOUR_OF_DAY))
            {
                found = false;
            }
        }

        if (type == ATDataAccessAbstract.GC_COMPARE_MINUTE || type == ATDataAccessAbstract.GC_COMPARE_SECOND)
        {
            if (gc1.get(Calendar.MINUTE) != gc2.get(Calendar.MINUTE))
            {
                found = false;
            }
        }

        if (type == ATDataAccessAbstract.GC_COMPARE_SECOND)
        {
            if (gc1.get(Calendar.SECOND) != gc2.get(Calendar.SECOND))
            {
                found = false;
            }
        }

        return found;
    }


    /**
     * Gets the float value.
     *
     * @param aValue the a value
     *
     * @return the float value
     */
    public float getFloatValue(Object aValue)
    {
        float out = 0.0f;

        // System.out.println("getFloatValue: ");

        if (aValue == null)
            return out;

        // System.out.println("getFloatValue: NOT NULL");

        if (aValue instanceof Number)
        {
            try
            {
                Number f = (Number) aValue;
                out = f.floatValue();
            }
            catch (Exception ex)
            {}
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
                    LOG.error("parse Float Exception [" + s + ": " + ex);
                }
            }
        }

        return out;
    }


    /**
     * Gets the int value.
     *
     * @param aValue the a value
     *
     * @return the int value
     */
    public int getIntValue(Object aValue)
    {
        int out = 0;

        if (aValue == null)
            return out;

        if (aValue instanceof Number)
        {
            try
            {
                Number i = (Number) aValue;
                out = i.intValue();
            }
            catch (Exception ex)
            {}
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
                {}
            }
        }

        return out;
    }


    /**
     * Gets the int value.
     *
     * @param aValue the a value
     *
     * @return the int value
     */
    public boolean getBooleanValue(Object aValue)
    {
        boolean out = false;

        if (aValue == null)
            return out;

        /*
         * if (aValue instanceof Integer)
         * {
         * try
         * {
         * Integer i = (Integer) aValue;
         * out = i.intValue();
         * }
         * catch (Exception ex)
         * {
         * }
         * }
         * else
         */
        if (aValue instanceof String)
        {
            String s = (String) aValue;
            if (s.length() > 0)
            {
                if (s.equals("1") || s.equalsIgnoreCase("true"))
                {
                    out = true;
                }
            }
        }
        else if (aValue instanceof Boolean)
        {
            Boolean b = (Boolean) aValue;
            return b.booleanValue();
        }

        return out;
    }


    // ********************************************************
    // ****** Backup / Restore *****
    // ********************************************************

    /**
     * Gets the long value.
     *
     * @param aValue the a value
     *
     * @return the long value
     */
    public long getLongValue(Object aValue)
    {
        long out = 0L;

        if (aValue == null)
            return out;

        if (aValue instanceof Number)
        {
            try
            {
                Number n = (Number) aValue;
                out = n.longValue();
            }
            catch (Exception ex)
            {
                System.out.println("Exception parsing Object: " + aValue);
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
                    System.out.println("Exception parsing Object: " + aValue);
                }
            }
        }

        return out;
    }


    public ExceptionHandling getNumberParsingExceptionHandling()
    {
        return numberParsingExceptionHandling;
    }


    public void setNumberParsingExceptionHandling(ExceptionHandling numberParsingExceptionHandling)
    {
        this.numberParsingExceptionHandling = numberParsingExceptionHandling;
    }


    /**
     * Gets the float value from string.
     *
     * @param aValue the a value
     *
     * @return the float value from string
     */
    public float getFloatValueFromString(String aValue)
    {
        return this.getFloatValueFromString(aValue, 0.0f);
    }


    /**
     * Gets the float value from string.
     *
     * @param aValue the a value
     * @param def_value the def_value
     *
     * @return the float value from string
     */
    public float getFloatValueFromString(String aValue, float def_value)
    {
        try
        {
            return getFloatValueFromStringWithException(aValue, def_value);
        }
        catch (Exception ex)
        {
            return 0.0f;
        }
    }


    /**
     * Gets the float value from string, can also throw Exception
     *
     * @param aValue the a value
     * @param def_value the def_value
     *
     * @return the float value from string
     */
    public float getFloatValueFromStringWithException(String aValue, float def_value) throws Exception
    {
        float out = def_value;

        try
        {
            if (aValue == null || aValue.trim().length() == 0)
                return def_value;

            // parse float, doesn't look in locale for decimal sign
            aValue = aValue.replace(",", ".");

            out = Float.parseFloat(aValue);
        }
        catch (Exception ex)
        {
            processException(ex, "Error on parsing string to get float [" + aValue + "]:");
        }

        return out;
    }


    // public abstract String getUpdateConfigurationFile();

    private void processException(Exception ex, String errorString) throws Exception
    {
        if (numberParsingExceptionHandling == ExceptionHandling.THROW_EXCEPTION)
        {
            throw ex;
        }
        else if (numberParsingExceptionHandling == ExceptionHandling.CATCH_EXCEPTION_WITH_STACK_TRACE)
        {
            LOG.error(errorString + ex, ex);
        }
        else
        {
            LOG.error(errorString + ex);
        }

    }


    /**
     * Gets the int value from string.
     *
     * @param aValue the a value
     *
     * @return the int value from string
     */
    public int getIntValueFromString(String aValue)
    {
        return this.getIntValueFromString(aValue, 0);
    }


    //
    // Graph Config Properties

    /**
     * Gets the int value from string.
     *
     * @param aValue the a value
     *
     * @return the int value from string
     */
    public int getIntValueFromString(String aValue, int def_value)
    {
        try
        {
            return getIntValueFromStringWithException(aValue, def_value);
        }
        catch (Exception ex)
        {
            return 0;
        }
    }


    /**
     * Gets the int value from string, can also throw Exception
     *
     * @param aValue the a value
     * @param def_value the def_value
     *
     * @return the int value from string
     */
    public int getIntValueFromStringWithException(String aValue, int def_value) throws Exception
    {
        int out = def_value;

        try
        {
            out = Integer.parseInt(aValue);
        }
        catch (Exception ex)
        {
            processException(ex, "Error on parsing string to get int [" + aValue + "]:");
        }

        return out;
    }


    /**
     * Gets the long value from string.
     *
     * @param aValue the a value
     *
     * @return the long value from string
     */
    public long getLongValueFromString(String aValue)
    {
        return this.getLongValueFromString(aValue, 0L);
    }


    /**
     * Gets the long value from string, can also throw Exception
     *
     * @param aValue the a value
     * @param def_value the def_value
     *
     * @return the long value from string
     */
    public long getLongValueFromString(String aValue, long def_value)
    {
        try
        {
            return getLongValueFromStringWithException(aValue, def_value);
        }
        catch (Exception ex)
        {
            return 0L;
        }
    }


    public long getLongValueFromStringWithException(String aValue, long def_value) throws Exception
    {
        long out = def_value;

        try
        {
            out = Long.parseLong(aValue);
        }
        catch (Exception ex)
        {
            processException(ex, "Error on parsing string to get long [" + aValue + "]:");
        }

        return out;
    }


    /**
     * Checks if is value set.
     *
     * @param val the val
     *
     * @return true, if is value set
     */
    public boolean isValueSet(String val)
    {
        if (val == null || val.trim().length() == 0 || val.equals("null"))
            return false;
        else
            return true;
    }


    /**
     * Checks if is backup restore available.
     *
     * @return true, if is backup restore available
     */
    public boolean isBackupRestoreAvailable()
    {
        return this.backupRestoreCollection != null;
    }


    /**
     * Gets the backup restore collection.
     *
     * @return the backup restore collection
     */
    public BackupRestoreCollection getBackupRestoreCollection()
    {
        // TODO: clone
        return this.backupRestoreCollection;
    }


    /**
     * Load Backup Restore Collection
     */
    public abstract void loadBackupRestoreCollection();


    /**
     * Make i18n keyword.
     *
     * @param input the input
     *
     * @return the string
     */
    public String makeI18nKeyword(String input)
    {
        String process = input.replaceAll(" ", "_");
        process = process.toUpperCase();

        return process;
    }


    // ********************************************************
    // ****** Component Id *****
    // ********************************************************

    /**
     * Gets the update configuration.
     *
     * @return the update configuration
     */
    public UpdateConfiguration getUpdateConfiguration()
    {
        if (this.update_configuration == null)
        {
            update_configuration = new UpdateConfiguration();
        }

        if (this.update_configuration != null)
        {
            this.app_db_version = this.update_configuration.db_version_required;
            this.app_name = this.update_configuration.product_id;
            this.app_version = this.update_configuration.version_numeric;
        }

        return this.update_configuration;
    }


    /**
     * Get Configuration - reads properties file and read all entries
     *
     * @param filename
     * @return
     */
    public Hashtable<String, String> getConfiguration(String filename)
    {

        Hashtable<String, String> config_db_values_ = new Hashtable<String, String>();

        Properties props = new Properties();

        config_loaded = true;

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

            for (Enumeration<Object> en = props.keys(); en.hasMoreElements();)
            {
                String key = (String) en.nextElement();
                config_db_values_.put(key, props.getProperty(key));
            }
        }

        return config_db_values_;

    }


    /**
     * Load Graph Config Properties
     */
    public abstract void loadGraphConfigProperties();


    // ********************************************************
    // ****** Extended Handlers *****
    // ********************************************************

    /**
     * Gets the graph config properties.
     *
     * @return the graph config properties
     */
    public GraphConfigProperties getGraphConfigProperties()
    {
        return this.graph_config;
    }


    /**
     * Sets the graph config properties.
     *
     * @param config the new graph config properties
     */
    public void setGraphConfigProperties(GraphConfigProperties config)
    {
        this.graph_config = config;
    }


    /**
     * Load special parameters.
     */
    public abstract void loadSpecialParameters();


    // ********************************************************
    // ****** Convertors *****
    // ********************************************************

    /**
     * Gets the special parameters.
     *
     * @return the special parameters
     */
    public Hashtable<String, String> getSpecialParameters()
    {
        return special_parameters;
    }


    /**
     * Gets the language info.
     *
     * @return the language info
     */
    public LanguageInfo getLanguageInfo()
    {
        return this.m_lang_info;
    }

    // ********************************************************
    // ****** Sorters *****
    // ********************************************************


    // /**
    // * Gets the selected lang index.
    // *
    // * @return the selected lang index
    // */
    // public abstract int getSelectedLangIndex();
    //
    //
    // /**
    // * Sets the selected lang index.
    // *
    // * @param index the new selected lang index
    // */
    // public abstract void setSelectedLangIndex(int index);

    /**
     * Gets the plugins.
     *
     * @return the plugins
     */
    public Hashtable<String, PlugInClient> getPlugins()
    {
        return this.plugins;
    }


    /**
     * Get Max Decimals that will be used by DecimalHandler
     *
     * @return
     */
    public abstract int getMaxDecimalsUsedByDecimalHandler();


    /**
     * Get New Component Id
     *
     * @return
     */
    public String getNewComponentId()
    {
        component_id_last++;
        return "" + this.component_id_last;
    }


    /**
     * Get Decimal Handler
     *
     * @return
     */
    public DecimalHandler getDecimalHandler()
    {
        return this.decimal_handler;
    }


    /**
     * Load Extended Handlers. Database tables can contain extended field, which is of type text and can
     *    contain a lot of other data, stored in this field, this is hanlder for that field. Each table,
     *    would use different handler.
     */
    public void loadExtendedHandlers()
    {
    }


    /**
     * Get Extended Handler. Returns extended handler
     *
     * @param key
     * @return
     */
    public ExtendedHandler getExtendedHandler(String key)
    {
        return this.extended_handlers.get(key);
    }


    /**
     * Add Extended Handler
     *
     * @param key
     * @param eh
     */
    public void addExtendedHandler(String key, ExtendedHandler eh)
    {
        if (this.extended_handlers == null)
        {
            this.extended_handlers = new Hashtable<String, ExtendedHandler>();
        }
        // System.out.println("Key=" + key +", ExtendeedHandler: " + eh);
        this.extended_handlers.put(key, eh);
    }


    /**
     * Load Converters - Loads ATechConverter instances
     */
    public void loadConverters()
    {
    }


    /**
     * Get Extended Handler. Returns extended handler
     *
     * @param key
     * @return
     */
    public ATechConverter getConverter(String key)
    {
        return this.converters.get(key);
    }


    /**
     * Load Sorters
     */
    public void loadSorters()
    {
    }


    /**
     * Get Sort Settings
     *
     * @param key
     * @return
     */
    public String getSortSetting(String key)
    {
        return this.sorters.get(key);
    }


    /**
     * Set Sort Settings
     *
     * @param key
     * @param value
     */
    public void setSortSetting(String key, String value)
    {
        if (this.sorters.containsKey(key))
        {
            this.sorters.remove(key);
        }

        this.sorters.put(key, value);
    }


    /**
     *
     *
     *
     * NEED TO IMPLEMENT IN USING CLASSES
     *
     */
    public void loadConfigurationContexts()
    {
    }


    // ********************************************************
    // ****** Login/Logout *****
    // ********************************************************

    public AbstractConfigurationContext getConfigurationContext()
    {
        return this.configuration_context;
    }


    /**
     *
     *
     *
     * NEED TO IMPLEMENT IN USING CLASSES
     *
     */
    public void loadDbApplicationContext()
    {
        // System.out.println("ATDataAccessAbstract:loadDbApplicationContext:
        // ");
        this.setCurrentDbVersion(0);
    }


    public DbToolApplicationAbstract getDbToolAbstract()
    {
        return this.db_tool_app;
    }


    public long getCurrentUserId()
    {
        return this.current_user_id;
    }


    public void setCurrentUserId(long user_id)
    {
        this.current_user_id = user_id;
    }


    public int getCurrentDbVersion()
    {
        return this.current_db_version;
    }


    /*
     * public int getSelectedType(String value)
     * {
     * // Object array[] = getTypesArray(type);
     * int i=1;
     * boolean found = false;
     * for (i=1; i<events.length ;i++)
     * {
     * if (value.equals(events[i]))
     * {
     * found = true;
     * break;
     * }
     * }
     * System.out.println(value + " found: " + found + " " + i);
     * if (found)
     * return i;
     * else
     * return 0;
     * }
     */

    public void setCurrentDbVersion(int ver)
    {
        this.current_db_version = ver;
    }


    public void debugArray(Object[] arr, String name)
    {
        if (arr == null)
        {
            System.out.println(name + " is null");
        }
        else if (arr.length == 0)
        {
            System.out.println(name + " length is 0");
        }
        else
        {
            for (int i = 0; i < arr.length; i++)
            {
                System.out.println(i + ": " + arr[i]);
            }

        }
    }


    public void loadUserTypes()
    {
        // this.user_types = new String[5];
        //
        // this.user_types[0] = this.m_i18n.getMessage("SELECT");
        // this.user_types[1] = m_i18n.getMessage("USER_NORMAL");
        // this.user_types[2] = m_i18n.getMessage("USER_WORKER");
        // this.user_types[3] = m_i18n.getMessage("USER_ADMINISTRATOR");
        // this.user_types[4] = m_i18n.getMessage("USER_SUPERADMIN");
    }


    public void loadContactTypes()
    {

    }


    /**
     * Is Demo Version
     *
     * @return
     */
    public boolean isDemoVersion()
    {
        return this.demo_version;
    }


    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser()
    {
        return this.logged_user;
    }


    /**
     * Sets the user.
     *
     * @param us the new user
     */
    public void setUser(User us)
    {
        this.logged_user = us;
        this.processLogin();
    }


    /**
     * Gets the all users.
     *
     * @return the all users
     */
    public ArrayList<User> getAllUsers()
    {
        if (this.all_users == null)
        {
            this.all_users = new ArrayList<User>();
        }

        return this.all_users;
    }


    /**
     * Process login.
     */
    public void processLogin()
    {
        // this.m_main.processLogin();
    }


    public int getSelectedContactTypePart(String value)
    {

        int i = 0;

        boolean found = false;

        for (i = 0; i < contact_types.length; i++)
        {
            if (value.startsWith(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        // System.out.println(value + " found: " + found + " " + i);

        if (found)
            return i;
        else
            return 0;

    }


    public int getSelectedConfigTypePart(String value)
    {

        int i = 0;

        boolean found = false;

        for (i = 0; i < contact_types.length; i++)
        {
            if (value.startsWith(contact_types[i]))
            {
                found = true;
                break;
            }

        }

        // System.out.println(value + " found: " + found + " " + i);

        if (found)
            return i;
        else
            return 0;

    }


    public void showDialog(Container cont, int type, String message)
    {
        ATSwingUtils.showDialog(cont, type, message, this.getI18nControlInstance());
    }


    /**
     * Get Developer mode flag
     * @return
     *
     *
     */
    public boolean getDeveloperMode()
    {
        return this.developer_mode;
    }


    /**
     * Get Developer mode flag
     * @return
     *
     *
     */
    public boolean isDeveloperMode()
    {
        return this.developer_mode;
    }


    /**
     * Set Developer mode
     *
     * @param dev_mode
     *
     *
     */
    public void setDeveloperMode(boolean dev_mode)
    {
        this.developer_mode = dev_mode;
    }


    public void initApp()
    {

    }


    public String getAppName()
    {
        return this.app_name;
    }


    public int getCurrentVersion()
    {
        return this.app_version;
    }


    public String createListFromHashtable(Hashtable<Object, Object> ht, int select_type, String delimiter)
    {
        StringBuffer sb = new StringBuffer();

        Enumeration<Object> en = null;

        if (select_type == LIST_HT_KEY)
        {
            en = ht.keys();
        }
        else
        {
            en = ht.elements();
        }

        for (; en.hasMoreElements();)
        {
            sb.append(en.nextElement().toString());

            if (en.hasMoreElements())
            {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }


    public String createStringRepresentationOfCollection(Collection<?> list, String delimiter)
    {
        StringBuffer sb = new StringBuffer();
        boolean first = true;

        for (Object o : list)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                sb.append(delimiter + " ");
            }

            sb.append(o.toString());
        }

        return sb.toString();
    }


    public String createKeyValueString(HashMap<String, String> map, String delimiter, boolean readable)
    {
        return createKeyValueString(map, "", delimiter, readable);
    }


    public String createKeyValueString(HashMap<String, String> map, String equalsSign, String delimiter,
            boolean readable)
    {
        StringBuffer sb = new StringBuffer();

        for (String key : map.keySet())
        {
            sb.append(key + equalsSign + map.get(key));
            sb.append(delimiter);
            if (readable)
                sb.append(" ");
        }

        sb.substring(0, sb.length() - (delimiter.length() + (readable ? 1 : 0)));

        return sb.toString();
    }


    public Map<String, String> createMapFromKeyValueString(String mapped, String equalsSign, String delimiter)
    {
        Map<String, String> outMap = new HashMap<String, String>();

        try
        {
            String[] keyValuePairs = mapped.split(delimiter);

            for (String keyValuePair : keyValuePairs)
            {
                try
                {
                    String[] keyValues = keyValuePair.split(equalsSign);

                    if (keyValues.length != 2)
                    {
                        LOG.warn("Problem with keyValues (not enough parts): original={}, equalsSign={}, parts={}",
                            keyValuePair, equalsSign, keyValues);
                        continue;
                    }

                    outMap.put(keyValues[0].trim(), keyValues[1].trim());
                }
                catch (Exception ex)
                {
                    LOG.warn("Problem with keyValues, exception: {} [original={}, equalsSign={}]", ex.getMessage(),
                        keyValuePair, equalsSign, ex);
                }
            }

        }
        catch (Exception ex)
        {
            LOG.error("Problem spliting expression: expression={}, delimiter={}, equalsSign={}", mapped, delimiter,
                equalsSign);
        }

        return outMap;
    }


    /**
     * This is for action treshold. Sometimes, action happens twice, this method can help with prevention of that. We need to have local variable tipe long
     * set with currentTimeMillis(). we add following code in start of action/item listner (action can have treshiold set directly, while tem listener can't).
     *
     * If true is returned we have passed treshold.
     *
     * @return
     */
    public boolean checkActionTreshold(long last_action, long treshold_ms)
    {
        long diff = System.currentTimeMillis() - last_action;

        if (diff < treshold_ms)
            return false;
        else
            return true;
    }


    public String getDayOfWeekFromGC(GregorianCalendar gc)
    {
        return gcDays[gc.get(GregorianCalendar.DAY_OF_WEEK) - 1];
    }


    //
    // Image root
    //

    public String getDayOfWeekFromGCShorter(GregorianCalendar gc, int length)
    {
        return getDayOfWeekFromGC(gc).substring(0, length);
    }


    /**
     * Gets the image icon.
     *
     * @param root the root
     * @param name the name
     *
     * @return the image icon
     */
    public ImageIcon getImageIcon(String root, String name)
    {
        return new ImageIcon(ATSwingUtils.getImage(root + name, this.getCurrentComponentParent()));
    }


    /**
     * Append To String
     *
     * @param originalString
     * @param stringToAdd
     * @param delimiter
     * @return
     */
    public static String appendToString(String originalString, String stringToAdd, String delimiter)
    {
        if (originalString.length() > 0)
        {
            originalString += delimiter + stringToAdd;
        }
        else
        {
            originalString = stringToAdd;
        }

        return originalString;
    }


    /**
     * Append To StringBuilder
     *
     * @param stringBuilder
     * @param stringToAdd
     * @param delimiter
     * @return
     */
    public static void appendToStringBuilder(StringBuilder stringBuilder, String stringToAdd, String delimiter)
    {
        if (stringBuilder.length() > 0)
        {
            stringBuilder.append(delimiter + stringToAdd);
        }
        else
        {
            stringBuilder.append(stringToAdd);
        }
    }


    public int getDateDifferenceInDays(Calendar from, Calendar to)
    {
        DateTime fromDate = new DateTime(from.getTimeInMillis());
        DateTime toDate = new DateTime(to.getTimeInMillis());

        Days d = Days.daysBetween(fromDate, toDate);
        int days = d.getDays();
        return days;
    }


    public int getDaysInInterval(Calendar from, Calendar to)
    {
        return (getDateDifferenceInDays(from, to) + 1);
    }


    public String getGregorianCalendarAsString(GregorianCalendar gregorianCalendar)
    {
        return getLeadingZero(gregorianCalendar.get(Calendar.DAY_OF_MONTH), 2) + "." //
                + getLeadingZero((gregorianCalendar.get(Calendar.MONTH) + 1), 2) + "." //
                + gregorianCalendar.get(Calendar.YEAR) + " " //
                + getLeadingZero(gregorianCalendar.get(Calendar.HOUR_OF_DAY), 2) + ":" //
                + getLeadingZero(gregorianCalendar.get(Calendar.MINUTE), 2) + ":"
                + getLeadingZero(gregorianCalendar.get(Calendar.SECOND), 2) + ":";

    }
}
