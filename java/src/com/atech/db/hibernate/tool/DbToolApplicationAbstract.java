package com.atech.db.hibernate.tool;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.UIManager;

/*
 New methods :
 public String[] getAllDatabasesNamesPlusAsArray()
 public String[] getAllDatabasesNamesAsArray()
 public int getSelectedDatabaseIndex()

 //setChanged();
 //getChanged();

 hasChanged();
 setSelectedDatabaseIndex(int);

 */

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
 *  Filename:     DbToolApplicationGGC  
 *  Description:  DbTool Application Interface for GGC
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

public abstract class DbToolApplicationAbstract implements DbToolApplicationInterface
{

    protected int selected_db = 0;
    protected String selected_lang = "en";

    protected Hashtable<String, String> config_db_values = null;

    // LF
    protected String selected_LF_Class = "com.l2fprod.gui.plaf.skin.SkinLookAndFeel"; // class
    protected String selected_LF_Name = "SkinLF"; // name
    protected String skinLFSelected = "blueMetalthemepack.zip";

    protected Object[] availableLF = null;
    protected Object[] availableLFClass = null;
    protected Hashtable<String, String> availableLF_full = null;
    protected int skinlf_LF = 0;

    protected Hashtable<String, DatabaseSettings> staticDatabases;
    protected Hashtable<String, DatabaseSettings> customDatabases;
    protected Hashtable<String, DatabaseSettings> allDatabases;

    protected boolean m_changed = false;
    protected boolean use_skin_lf = false;

    /**
     * Constuctor
     * @param use_skin_lf_ 
     */
    public DbToolApplicationAbstract(boolean use_skin_lf_)
    {
        this.use_skin_lf = use_skin_lf_;
        this.staticDatabases = new Hashtable<String, DatabaseSettings>();
        this.customDatabases = new Hashtable<String, DatabaseSettings>();
        this.allDatabases = new Hashtable<String, DatabaseSettings>();
        initDefaults();
        initStaticDbs();
        loadAvailableLFs();
    }

    /**
     * init Defaults - set default language, default SkinLF skin, default selected LF Class
     */
    public abstract void initDefaults();

    /**
     * Get Selected Language
     * 
     * @return
     */
    public String getSelectedLanguage()
    {
        return this.selected_lang;
    }

    /**
     * Set Selected Language
     * 
     * @param lang
     */
    public void setSelectedLanguage(String lang)
    {
        this.selected_lang = lang;
    }

    /**
     * Load Available LFs
     */
    public void loadAvailableLFs()
    {

        availableLF_full = new Hashtable<String, String>();
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

        availableLF = new Object[info.length + 1];
        availableLFClass = new Object[info.length + 1];

        int i;
        for (i = 0; i < info.length; i++)
        {
            String name = info[i].getName();
            String className = info[i].getClassName();

            availableLF_full.put(name, className);
            availableLF[i] = name;
            availableLFClass[i] = className;
        }

        if (this.use_skin_lf)
        {
            availableLF_full.put("SkinLF", "com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
            availableLF[i] = "SkinLF";
            availableLFClass[i] = "com.l2fprod.gui.plaf.skin.SkinLookAndFeel";
            skinlf_LF = i;
        }
    }

    /**
     * Get Avilable LFs
     * 
     * @return
     */
    public Object[] getAvailableLFs()
    {
        return availableLF;
    }

    /**
     * Get Available LFs Class
     * @return
     */
    public Object[] getAvailableLFsClass()
    {
        return this.availableLFClass;
    }

    /**
     * Get LF Data
     * 
     * @return
     */
    public String[] getLFData()
    {
        this.loadConfig();

        String out[] = new String[2];

        out[0] = this.selected_LF_Class;
        out[1] = this.skinLFSelected;

        return out;
    }

    /**
     * Get Selected LF Index
     * @return
     */
    public int getSelectedLFIndex()
    {
        for (int i = 0; i < this.availableLFClass.length; i++)
        {
            if (this.availableLFClass[i].equals(this.selected_LF_Class))
                return i;
        }

        return this.skinlf_LF;

    }

    /**
     * Set Selected LF
     * 
     * @param index
     * @param skin
     */
    public void setSelectedLF(int index, String skin)
    {
        System.out.println("Selected LF SelectedLFIndex: " + this.getSelectedLFIndex());

        if (this.getSelectedLFIndex() != index) // .getSkinLFIndex()
        {
            this.selected_LF_Class = (String) this.availableLFClass[index]; // class
            this.selected_LF_Name = (String) this.availableLF[index]; // name
            this.m_changed = true;
        }

        System.out.println("Selected LF Class: " + this.selected_LF_Class);

        if (!skin.equals(this.skinLFSelected))
        {
            this.skinLFSelected = skin;
            this.m_changed = true;
        }

    }

    /**
     * Get Selected LF Skin
     * @return
     */
    public String getSelectedLFSkin()
    {
        return this.skinLFSelected;
    }

    /**
     * Get SkinLF Index
     * 
     * @return
     */
    public int getSkinLFIndex()
    {
        return this.skinlf_LF;
    }

    /**
     * Is SkinLF Selected
     * 
     * @return
     */
    public boolean isSkinLFSelected()
    {
        return isSkinLFSelected(getSelectedLFIndex());
    }

    /**
     * Is SkinLF Selected
     * 
     * @param index
     * @return
     */
    public boolean isSkinLFSelected(int index)
    {
        return this.skinlf_LF == index;
    }

    /*
     * private void setDefaultLF()
     * {
     * this.selected_LF_Class = "com.l2fprod.gui.plaf.skin.SkinLookAndFeel"; //
     * class
     * this.selected_LF_Name = "SkinLF"; // name
     * this.skinLFSelected = "blueMetalthemepack.zip";
     * this.m_changed = true;
     * }
     */

    /**
     * Init Static Dbs
     */
    public void initStaticDbs()
    {
        // load all static database info
    }

    /**
     * Get Application Name
     * 
     * @return
     */
    public abstract String getApplicationName();

    /**
     * Get Application Database Config
     * 
     * @return
     */
    public abstract String getApplicationDatabaseConfig();

    /**
     * Load Config
     */
    public void loadConfig()
    {

        config_db_values = new Hashtable<String, String>();

        Properties props = new Properties();

        boolean config_loaded = true;

        try
        {
            FileInputStream in = new FileInputStream(getApplicationDatabaseConfig());
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
                String str = (String) en.nextElement();

                if (str.startsWith("DB"))
                {
                    addDatabaseSetting(str, (String) props.get(str));
                    // config_db_values.put(str, (String)props.get(str));
                }
                else
                {

                    if (str.equals("LF_NAME"))
                    {
                        selected_LF_Name = (String) props.get(str);
                    }
                    else if (str.equals("LF_CLASS"))
                    {
                        selected_LF_Class = (String) props.get(str);
                    }
                    else if (str.equals("SKINLF_SELECTED"))
                    {
                        // System.out.println("!!!!!!!!!!!!!!!!! " +
                        // (String)props.get(str));
                        skinLFSelected = (String) props.get(str);
                    }
                    else if (str.equals("SELECTED_DB"))
                    {
                        selected_db = Integer.parseInt((String) props.get(str));
                    }
                    else if (str.equals("SELECTED_LANG"))
                    {
                        selected_lang = (String) props.get(str);
                    }
                    else
                    {
                        loadApplicationSpecific(str, (String) props.get(str));
                    }
                    // System.out.println("DbToolApplicationAbstract:loadConfig:: Unknown parameter : '"
                    // + str +"'");

                }

            }

        }
        else
        {

            // we don't have config, we try to create basic one

            System.out
                    .println("DbToolApplicationAbstract: Config file not found. Creating new config file with default settings.");

            try
            {
                addDefaultApplicationDatabase();
            }
            catch (Exception ex)
            {
                System.out.println("Exception on create default config: " + ex);
            }

            selected_db = 0;
            selected_lang = "en";

            saveConfig();

        }

    }

    /**
     * Add Default Application Database (you need to add all settings).
     * 
     * <pre>
     *    addDatabaseSetting("DB0_CONN_NAME", "Internal Database");
     *    addDatabaseSetting("DB0_DB_NAME", "HypersonicSQL File");
     *    addDatabaseSetting("DB0_CONN_DRIVER_CLASS", "org.hsqldb.jdbcDriver");
     *    addDatabaseSetting("DB0_CONN_URL", "jdbc:hsqldb:file:../data/ggc_db");
     *    addDatabaseSetting("DB0_CONN_USERNAME", "sa");
     *    addDatabaseSetting("DB0_CONN_PASSWORD", "");
     *    addDatabaseSetting("DB0_HIBERNATE_DIALECT", "org.hibernate.dialect.HSQLDialect");
     * </pre>
     */
    public abstract void addDefaultApplicationDatabase();

    /**
     * Load Application Specific Settings
     * 
     * @param key
     * @param value
     */
    public abstract void loadApplicationSpecific(String key, String value);

    /**
     * Save Application Specific Settings
     * 
     * @param bw
     */
    public abstract void saveApplicationSpecific(BufferedWriter bw);

    private String getCurrentTimeAsUserReadableString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());

        return gc.get(Calendar.DAY_OF_MONTH) + "." + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR) + "  "
                + gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE) + ":" + gc.get(Calendar.SECOND);

    }

    /**
     * Get Config File Comment - Comment in configuration, stating which application it is
     *   for example: "GGC_Config (Settings for GGC)"
     * @return
     */
    public abstract String getConfigFileComment();

    /**
     * Save Config
     */
    public void saveConfig()
    {

        // System.out.println("SAVEEEEEEEEEEEEE !!!");

        try
        {

            // Properties props = new Properties();
            BufferedWriter bw = new BufferedWriter(new FileWriter(getApplicationDatabaseConfig()));

            bw.write("#\n" + "# " + getConfigFileComment() + "\n" + "#" + getCurrentTimeAsUserReadableString() + "\n"
                    + "#\n" + "# Don't edit by hand\n"
                    + "# Only settings need for application startup are written here. All other info\n"
                    + "#    is stored in database\n" + "#\n\n" + "#\n# Databases settings\n#\n");

            // int count_db = 0;

            // for (int i=0; i<this.allDatabases.size(); i++) fix, only
            // non-static db data should be written
            for (int i = 0; i < this.allDatabases.size(); i++)
            {
                DatabaseSettings dbs = this.allDatabases.get("" + i);
                dbs.write(bw);
            }

            bw.write("\n\n#\n# Look and Feel Settings\n#\n\n");
            bw.write("LF_NAME=" + selected_LF_Name + "\n");

            // props.put("LF_NAME", selected_LF_Name);

            selected_LF_Class = availableLF_full.get(selected_LF_Name);

            bw.write("LF_CLASS=" + selected_LF_Class + "\n");

            // props.put("LF_CLASS", selected_LF_Name);
            bw.write("SKINLF_SELECTED=" + skinLFSelected + "\n");
            // props.put("SKINLF_SELECTED", skinLFSelected);

            bw.write("\n\n#\n# Db Selector\n#\n");
            bw.write("SELECTED_DB=" + selected_db + "\n");

            bw.write("\n\n#\n# Language Selector\n#\n");
            bw.write("SELECTED_LANG=" + selected_lang + "\n");

            saveApplicationSpecific(bw);

            bw.close();

        }
        catch (Exception ex)
        {
            System.out.println("DataAccess::saveConfig::Exception> " + ex);
            ex.printStackTrace();
        }

    }

    /**
     * Get First Available Database
     * 
     * @return get number of first database (this can be either 0 or 1)
     */
    public int getFirstAvailableDatabase()
    {
        return 1;
    }

    /**
     * Get Static Databases
     * 
     * @return
     */
    public Hashtable<String, DatabaseSettings> getStaticDatabases()
    {
        return this.staticDatabases;
    }

    /**
     * Get Custom Databases
     * 
     * @return
     */
    public Hashtable<String, DatabaseSettings> getCustomDatabases()
    {
        return this.customDatabases;
    }

    /**
     * Get All Databases
     * 
     * @return
     */
    public Hashtable<String, DatabaseSettings> getAllDatabases()
    {
        return this.allDatabases;
    }

    // NEW
    /**
     * Get All Databases Names As Array
     * @return
     */
    public String[] getAllDatabasesNamesAsArray()
    {
        String[] arr = new String[this.allDatabases.size()];

        for (int i = 0; i < this.allDatabases.size(); i++)
        {
            arr[i] = this.allDatabases.get("" + i).name;
        }

        return arr;
    }

    // NEW
    /**
     * Get All Databases Names Plus As Array
     * @return
     */
    public String[] getAllDatabasesNamesPlusAsArray()
    {
        String[] arr = new String[this.allDatabases.size()];

        for (int i = 0; i < this.allDatabases.size(); i++)
        {
            arr[i] = i + " - " + this.allDatabases.get("" + i).name;
        }

        return arr;
    }

    /**
     * Get Database
     * 
     * @param index
     * @return
     */
    public DatabaseSettings getDatabase(int index)
    {
        return this.allDatabases.get("" + index);
    }

    /**
     * Get Selected Database
     * 
     * @return
     */
    public DatabaseSettings getSelectedDatabase()
    {
        return this.allDatabases.get("" + this.selected_db);
    }

    // NEW
    /**
     * Get Selected Database Index
     * @return
     */
    public int getSelectedDatabaseIndex()
    {
        return this.selected_db;
    }

    // NEW
    /**
     * Add Database Settings
     * 
     * @param setting
     * @param value
     */
    public void addDatabaseSetting(String setting, String value)
    {
        int dbnum = Integer.parseInt(setting.substring(2, 3));

        // if (dbnum<this.getFirstAvailableDatabase())
        // return;

        if (this.customDatabases.containsKey("" + dbnum))
        {
            // we have database
            DatabaseSettings dbs = this.customDatabases.get("" + dbnum);
            addDatabaseSetting(dbs, setting, value);
        }
        else
        {
            // new database
            DatabaseSettings dbs = new DatabaseSettings();
            dbs.number = dbnum;
            addDatabaseSetting(dbs, setting, value);
            this.customDatabases.put("" + dbnum, dbs);
            this.allDatabases.put("" + dbnum, dbs);
        }

        // System.out.println(dbnum);

    }

    // NEW
    /**
     * Add Database Settings
     * 
     * @param ds
     * @param setting
     * @param value
     */
    public void addDatabaseSetting(DatabaseSettings ds, String setting, String value)
    {
        String sett = setting.substring(setting.indexOf("_") + 1);

        // System.out.println(sett);

        if (sett.equals("CONN_NAME"))
        {
            ds.name = value;
        }
        else if (sett.equals("DB_NAME"))
        {
            ds.db_name = value;
        }
        else if (sett.equals("CONN_DRIVER"))
        {
            ds.driver = value;
        }
        else if (sett.equals("CONN_URL"))
        {
            ds.url = value;
        }
        else if (sett.equals("HIBERNATE_DIALECT"))
        {
            ds.dialect = value;
        }
        else if (sett.equals("CONN_USERNAME"))
        {
            ds.username = value;
        }
        else if (sett.equals("CONN_PASSWORD"))
        {
            ds.password = value;
        }
        else if (sett.equals("CONN_DRIVER_CLASS"))
        {
            ds.driver_class = value;
        }
        else
        {
            System.out.println("Unknown DB keyword in config: " + sett);
        }

    }

    // NEW
    /**
     * 
     */
    public void test()
    {
        /*
         * ArrayList list = new ArrayList();
         * int num = (int)(config_db_values.size()/7);
         * for (int i=0; i<num; i++)
         * {
         * DatabaseSettings ds = new DatabaseSettings();
         * ds.number = i;
         * ds.name = (String)config_db_values.get("DB" +i +"_CONN_NAME");
         * ds.db_name = (String)config_db_values.get("DB" +i +"_DB_NAME");
         * ds.driver = (String)config_db_values.get("DB" +i +"_CONN_DRIVER");
         * ds.url = (String)config_db_values.get("DB" +i +"_CONN_URL");
         * //ds.port = config_db_values.get("DB" +i +"_CONN_NAME");
         * ds.dialect = (String)config_db_values.get("DB" +i
         * +"_HIBERNATE_DIALECT");
         * ds.username = (String)config_db_values.get("DB" +i
         * +"_CONN_USERNAME");
         * ds.password = (String)config_db_values.get("DB" +i
         * +"_CONN_PASSWORD");
         * if (this.selected_db==i)
         * {
         * ds.isDefault = true;
         * }
         * list.add(ds);
         * }
         * return list;
         */
    }

    // NEW
    /**
     * Has Changed
     * 
     * @return
     */
    public boolean hasChanged()
    {
        return this.m_changed;
    }

    // NEW
    /**
     * Set Selected Database Index
     * 
     * @param index
     */
    public void setSelectedDatabaseIndex(int index)
    {
        if (this.selected_db != index)
        {
            this.selected_db = index;
            this.m_changed = true;
        }

    }

    /**
     * To String
     */
    @Override
    public String toString()
    {
        return getApplicationName();
    }

}
