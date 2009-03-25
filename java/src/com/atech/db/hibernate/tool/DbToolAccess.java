package com.atech.db.hibernate.tool;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import com.atech.db.hibernate.HibernateDb;
import com.atech.utils.ATDataAccessAbstract;

import com.atech.utils.ATDataAccessAbstract;

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


public class DbToolAccess extends ATDataAccessAbstract
{

    // LF
    // Hashtable<String,String> availableLF_full = null;
    Object[] availableLF = null;
    Object[] availableLang = null;

    String selectedLF = null;
    String subSelectedLF = null;

    // Config file
    Hashtable<String, String> config_db_values = null;
    public int selected_db = -1;
    public int selected_lang = 1;
    public String selected_LF_Class = null; // class
    public String selected_LF_Name = null; // name
    public String skinLFSelected = null;
    String allDbs[] = null;

    public static String s_pathPrefix = "./";

    public I18nControlDbT m_i18n = I18nControlDbT.getInstance();

    private static DbToolAccess s_da = null; // This is handle to unique
    // singelton instance

    // public GGCDb m_db = null;
    public Component m_main = null;

    public Font fonts[] = null;

    public DbToolTreeRoot m_databases_treeroot = null;
    // public GGCTreeRoot m_meals_treeroot = null;

    public Date m_date = null;
    // public HbA1cValues m_HbA1c = null;
    // public DailyValues m_dvalues = null;

    public Hashtable<String, DatabaseDefObject> m_tableOfDatabases = new Hashtable<String, DatabaseDefObject>();
    // public ArrayList m_listOfDatabases = null;

    public Object[] m_availableDatabases = null;

    public ArrayList<DbToolApplicationInterface> listOfClasses = new ArrayList<DbToolApplicationInterface>();

    public DatabaseDefinitions m_dataDefs = null;

    // ********************************************************
    // ****** Constructors and Access methods *****
    // ********************************************************

    // Constructor: DataAccess
    /**
     * 
     * This is DbToolAccess constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.<br>
     * <br>
     * 
     */
    private DbToolAccess()
    {
        super(I18nControlDbT.getInstance());
        System.out.println("DbToolsAccess");
        // m_db = db;
        // loadConfig();
        loadFonts();

        m_dataDefs = new DatabaseDefinitions();
        m_databases_treeroot = new DbToolTreeRoot(this);

        // loadApplicationData();

    }

    // Method: getInstance
    // Author: Andy
    /**
     * 
     * This method returns reference to DbToolAccess object created, or if no
     * object was created yet, it creates one.<br>
     * <br>
     * 
     * @return Reference to DbToolAccess object
     * 
     */
    public static DbToolAccess getInstance()
    {
        if (s_da == null)
            s_da = new DbToolAccess();

        return s_da;
    }

    public static DbToolAccess createInstance(Component main)
    {
        if (s_da == null)
        {
            s_da = new DbToolAccess();
            s_da.setParent(main);
        }

        return s_da;
    }

    /*
     * static public DataAccess getInstance() { return m_da; }
     */

    public Object[] getAvailableDatabases()
    {
        // System.out.println("getAvailableDatabases() " +
        // m_availableDatabases);
        return m_availableDatabases;
    }

    public void createAvailableDatabases(int number)
    {
        m_availableDatabases = new Object[number];
    }

    public void addAvailableDatabase(int index, DatabaseDefObject obj)
    {
        m_availableDatabases[index] = obj;
    }

    // Method: deleteInstance
    /**
     * This method sets handle to DataAccess to null and deletes the instance.
     * <br>
     * <br>
     */
    public void deleteInstance()
    {

        m_i18n = null;

    }

    // ********************************************************
    // ****** Fonts *****
    // ********************************************************

    public static final int FONT_BIG_BOLD = 0;
    public static final int FONT_NORMAL = 1;
    public static final int FONT_NORMAL_BOLD = 2;

    public void loadFonts()
    {
        fonts = new Font[3];
        fonts[0] = new Font("SansSerif", Font.BOLD, 22);
        fonts[1] = new Font("SansSerif", Font.PLAIN, 12);
        fonts[2] = new Font("SansSerif", Font.BOLD, 12);
    }

    public Font getFont(int font_id)
    {
        return fonts[font_id];
    }

    // ********************************************************
    // ****** Parent handling (for UIs) *****
    // ********************************************************

    public void setParent(Component main)
    {
        m_main = main;
    }
/*
    public Component getParent()
    {
        return m_main;
    }
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
     *  }
     * 
     * public Object[] getAvailableLFs() { return availableLF; }
     * 
     * 
     * public static String[] getLFData() { String out[] = new String[2];
     * 
     * try { Properties props = new Properties();
     * 
     * FileInputStream in = new FileInputStream(pathPrefix +
     * "/data/PIS_Config.properties"); props.load(in);
     * 
     * out[0] = (String)props.get("LF_CLASS"); out[1] =
     * (String)props.get("SKINLF_SELECTED");
     * 
     * return out;
     *  } catch(Exception ex) {
     * System.out.println("DataAccess::getLFData::Exception> " + ex); return
     * null; } }
     */
    // ********************************************************
    // ****** Languages *****
    // ********************************************************

    public ArrayList<DbToolApplicationInterface> getApplicationDatas()
    {
        return listOfClasses;
    }

    public void loadApplicationData()
    {

        File f = new File(".");

        try
        {
            // System.out.println("Root: " + f.getCanonicalPath());
            processDirectory(f.getCanonicalPath(), f, false);
        } 
        catch (Exception ex)
        {
            System.out.println("Exception: " + ex);
        }

    }

    public void processDirectory(String root, File f, boolean display)
    {
        File fl[] = f.listFiles();

        for (int i = 0; i < fl.length; i++)
        {
            // System.out.println(fl[i]);

            if (fl[i].isDirectory())
            {
                processDirectory(root, fl[i], false);
            } else
            {
                String file = fl[i].getName();

                if (file.endsWith(".class"))
                {
                    try
                    {
                        String can = fl[i].getCanonicalPath();

                        if (can.contains("$"))
                            continue;

                        can = can.substring(root.length() + 1);
                        can = replaceExpression(can, File.separator, ".");
                        can = can.substring(0, can.length() - 6);

                        try
                        {
                            // System.out.println("class: " + can);

                            Class<?> c = Class.forName(can);
                            if (getCorrectInterface(c))
                            {
                                DbToolApplicationInterface obj = (DbToolApplicationInterface) c
                                        .newInstance();
                                listOfClasses.add(obj);
                            }
                        } catch (java.lang.NoClassDefFoundError ex)
                        {
                        } catch (java.lang.ExceptionInInitializerError ex)
                        {
                        } catch (Exception ex)
                        {
                        }

                    } catch (Exception ex)
                    {
                        System.out.println("  Ex:" + ex);
                    }

                } else if (file.endsWith(".jar"))
                {
                    System.out.println("JAR: " + file);// fl[i]);
                }
                // System.out.println("file: " + file);//fl[i]);
            }
        }
    }

    public boolean getCorrectInterface(Class<?> c) // Object o)
    {
        Class<?>[] theInterfaces = c.getInterfaces();
        for (int i = 0; i < theInterfaces.length; i++)
        {
            String interfaceName = theInterfaces[i].getName();

            if (interfaceName
                    .equals("com.atech.db.tool.DbToolApplicationInterface"))
            {
                // System.out.println("Found Interface: " + interfaceName);
                return true;
            } else
                return false;

        }

        return false;
    }


    // ********************************************************
    // ****** Config File Handling *****
    // ********************************************************

    // public void loadCo
    public void loadConfig(/* DbToolApplicationInterface dtai */)
    {

        for (int i = 0; i < this.listOfClasses.size(); i++)
        {
        }

        // listOfClasses

        // dtai.loadConfig();

        /*
         * //Hashtable config_db_values = null; //int selected_db = -1; //String
         * selected_LF_Class = null; // class //String selected_LF_Name = null; //
         * name //String skinLFSelected = null;
         * 
         * config_db_values = new Hashtable<String, String>();
         * 
         * try { Properties props = new Properties();
         * 
         * FileInputStream in = new FileInputStream(s_pathPrefix +
         * dtai.getApplicationDatabaseConfig()); props.load(in);
         * 
         * 
         * for(Enumeration en = props.keys(); en.hasMoreElements(); ) { String
         * str = (String)en.nextElement();
         * 
         * if (str.startsWith("DB")) { config_db_values.put(str,
         * props.getProperty(str)); } else {
         * 
         * if (str.equals("LF_NAME")) { selected_LF_Name =
         * (String)props.get(str); } else if (str.equals("LF_CLASS")) {
         * selected_LF_Class = (String)props.get(str); } else if
         * (str.equals("SKINLF_SELECTED")) { skinLFSelected =
         * (String)props.get(str); } else if (str.equals("SELECTED_DB")) {
         * selected_db = Integer.parseInt((String)props.get(str)); } else if
         * (str.equals("SELECTED_LANG")) { selected_lang =
         * Integer.parseInt((String)props.get(str)); } else
         * System.out.println("DataAccess:loadConfig:: Unknown parameter : '" +
         * str +"'");
         *  }
         *  }
         * 
         * ArrayList<String> list = new ArrayList<String>();
         * 
         * int count_db = 0;
         * 
         * list.add("0 - " + m_i18n.getMessage("INTERNAL_DATABASE")); for (int
         * i=1; i<20; i++) { if
         * (config_db_values.containsKey("DB"+i+"_CONN_NAME")) { count_db++;
         * list.add(i+" - " + config_db_values.get("DB"+i+"_CONN_NAME")); }
         * 
         * if ((count_db*6)>=config_db_values.size()) break;
         *  }
         * 
         * Iterator it = list.iterator();
         * 
         * int j=0; allDbs = new String[list.size()];
         * 
         * while (it.hasNext()) { String val = (String)it.next(); allDbs[j] =
         * val; j++; }
         *  } catch(Exception ex) {
         * System.out.println("DbToolAccess::loadConfig::Exception> " + ex);
         * ex.printStackTrace(); }
         */
    }

    public ArrayList<DatabaseSettings> getListOfDatabases()
    {

        return null;
        /*
         * ArrayList<DatabaseSettings> list = new ArrayList<DatabaseSettings>();
         * int num = config_db_values.size()/7;
         * 
         * for (int i=0; i<num; i++) { DatabaseSettings ds = new
         * DatabaseSettings(); ds.number = i; ds.name =
         * config_db_values.get("DB" +i +"_CONN_NAME"); ds.db_name =
         * config_db_values.get("DB" +i +"_DB_NAME"); ds.driver =
         * config_db_values.get("DB" +i +"_CONN_DRIVER"); ds.url =
         * config_db_values.get("DB" +i +"_CONN_URL"); //ds.port =
         * config_db_values.get("DB" +i +"_CONN_NAME"); ds.dialect =
         * config_db_values.get("DB" +i +"_HIBERNATE_DIALECT");
         * 
         * ds.username = config_db_values.get("DB" +i +"_CONN_USERNAME");
         * ds.password = config_db_values.get("DB" +i +"_CONN_PASSWORD");
         * 
         * if (this.selected_db==i) { ds.isDefault = true; }
         * 
         * list.add(ds); }
         * 
         * return list;
         */
    }

    public void saveConfig()
    {

        /*
         * try {
         * 
         * //Properties props = new Properties(); BufferedWriter bw = new
         * BufferedWriter(new FileWriter(pathPrefix +
         * "/data/PIS_Config.properties"));
         * 
         * 
         * bw.write("#\n" + "# ZISConfig (Settings for ZIS)\n" + "#\n"+ "# Don't
         * edit by hand\n" + "#\n\n"+ "#\n# Databases settings\n#\n");
         * 
         * 
         * int count_db = 0;
         * 
         * for (int i=0; i<20; i++) { if
         * (config_db_values.containsKey("DB"+i+"_CONN_NAME")) { String con_name =
         * config_db_values.get("DB"+i+"_CONN_NAME"); bw.write("\n#\n# Database #" +
         * i +" - " + con_name + "\n#\n"); count_db++; bw.write("DB" + i +
         * "_CONN_NAME=" + con_name +"\n"); bw.write("DB" + i +
         * "_CONN_DRIVER_CLASS=" +
         * config_db_values.get("DB"+i+"_CONN_DRIVER_CLASS") +"\n");
         * bw.write("DB" + i + "_CONN_URL=" +
         * config_db_values.get("DB"+i+"_CONN_URL") +"\n"); bw.write("DB" + i +
         * "_CONN_USERNAME=" + config_db_values.get("DB"+i+"_CONN_USERNAME")
         * +"\n"); bw.write("DB" + i + "_CONN_PASSWORD=" +
         * config_db_values.get("DB"+i+"_CONN_PASSWORD") +"\n"); bw.write("DB" +
         * i + "_HIBERNATE_DIALECT=" +
         * config_db_values.get("DB"+i+"_HIBERNATE_DIALECT") +"\n");
         *  // list.add(i+" - " + config_db_values.get("DB"+i+"_CONN_NAME")); }
         * 
         * if ((count_db*6)>=config_db_values.size()) break;
         *  }
         *  /* for(Enumeration en=config_db_values.keys(); en.hasMoreElements(); ) {
         * String key = (String)en.nextElement(); bw.write(key + "=" +
         * config_db_values.get(key)+"\n"); }
         */
        /*
         * bw.write("\n\n#\n# Look and Feel Settings\n#\n\n");
         * bw.write("LF_NAME=" + selected_LF_Name +"\n");
         * 
         * //props.put("LF_NAME", selected_LF_Name);
         * 
         * selected_LF_Class = availableLF_full.get(selected_LF_Name);
         * 
         * bw.write("LF_CLASS=" + selected_LF_Class +"\n");
         * 
         * //props.put("LF_CLASS", selected_LF_Name);
         * bw.write("SKINLF_SELECTED=" + skinLFSelected +"\n");
         * //props.put("SKINLF_SELECTED", skinLFSelected); bw.write("\n\n#\n# Db
         * Selector\n#\n\n");
         * 
         * bw.write("SELECTED_DB=" + selected_db +"\n");
         * //props.put("SELECTED_DB", ""+selected_db);
         * 
         * bw.write("SELECTED_LANG=" + selected_lang +"\n");
         * 
         *  // FileOutputStream out = new
         * FileOutputStream("./ZISOut.properties");
         * 
         * bw.close(); //props.s
         * 
         * //props.store(out, " Settings for ZIS version 0.2.3 or higher (please
         * DON'T edit this file by hand!!)");
         *  } catch(Exception ex) {
         * System.out.println("DataAccess::saveConfig::Exception> " + ex);
         * ex.printStackTrace(); }
         */

    }

    public String[] getAvailableDbs()
    {
        return allDbs;
    }

    public int getSelectedDbIndex()
    {
        for (int i = 0; i < allDbs.length; i++)
        {
            if (allDbs[i].startsWith(this.selected_db + " - "))
                return i;
        }
        return 0;
    }

    public String[] getMonthsArray()
    {

        String arr[] = new String[12];

        arr[0] = m_i18n.getMessage("JANUARY");
        arr[1] = m_i18n.getMessage("FEBRUARY");
        arr[2] = m_i18n.getMessage("MARCH");
        arr[3] = m_i18n.getMessage("APRIL");
        arr[4] = m_i18n.getMessage("MAY");
        arr[5] = m_i18n.getMessage("JUNE");
        arr[6] = m_i18n.getMessage("JULY");
        arr[7] = m_i18n.getMessage("AUGUST");
        arr[8] = m_i18n.getMessage("SEPTEMBER");
        arr[9] = m_i18n.getMessage("OCTOBER");
        arr[10] = m_i18n.getMessage("NOVEMBER");
        arr[11] = m_i18n.getMessage("DECEMBER");

        return arr;

    }

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
        } else
            return getLeadingZero(days, 2) + "/" + getLeadingZero(months, 2)
                    + "/" + year;

    }

    public String getTimeString(int time)
    {

        int hours = time / 100;

        int min = time - hours * 100;

        return getLeadingZero(hours, 2) + ":" + getLeadingZero(min, 2);

    }



    public static void notImplemented(String source)
    {
        System.out.println("Not Implemented: " + source);
    }


    // ---
    // --- Array Utils
    // ---

    public ArrayList<String> getArrayListFromHashtableValues(
            Hashtable<String, String> table)
    {
        ArrayList<String> al = new ArrayList<String>();

        for (Enumeration<String> en = table.keys(); en.hasMoreElements();)
        {
            al.add(table.get(en.nextElement()));
        }

        return al;
    }

    public ArrayList<DatabaseSettings> getArrayOfDatabaseSettings(
            Hashtable<String, DatabaseSettings> table)
    {
        ArrayList<DatabaseSettings> al = new ArrayList<DatabaseSettings>();

        for (Enumeration<String> en = table.keys(); en.hasMoreElements();)
        {
            al.add(table.get(en.nextElement()));
        }

        Collections.sort(al);
        // Comparable.sort(al);
        // ArrayList.so
        // Collections.s

        // Collections.sort(al, new DatabaseSettings());

        return al;
    }

    @Override
    public void checkPrerequisites()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getApplicationName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HibernateDb getHibernateDb()
    {
        return null;
    }

    @Override
    public String getImagesRoot()
    {
        return null;
    }

    @Override
    public void initSpecial()
    {
    }

    @Override
    public void loadBackupRestoreCollection()
    {
    }

    @Override
    public void loadGraphConfigProperties()
    {
    }

    
    /**
     * Load Special Parameters
     * 
     * @see com.atech.utils.ATDataAccessAbstract#loadSpecialParameters()
     */
    public void loadSpecialParameters()
    {
    }
    

    /**
     * This method is intended to load additional Language info. Either special langauge configuration
     * or special data required for real Locale handling.
     */
    @Override
    public void loadLanguageInfo()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public int getSelectedLangIndex()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSelectedLangIndex(int index)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loadPlugIns()
    {
        // TODO Auto-generated method stub
        
    }
    
    
}
