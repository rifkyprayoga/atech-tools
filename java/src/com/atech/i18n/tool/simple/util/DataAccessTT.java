package com.atech.i18n.tool.simple.util;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.tool.DbToolTreeRoot;
import com.atech.i18n.tool.simple.data.TranslationConfiguration;
import com.atech.i18n.tool.simple.data.TranslationData;
import com.atech.utils.ATDataAccessAbstract;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  DataAccessTT - Data Access class
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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


public class DataAccessTT extends ATDataAccessAbstract
{


    // Config file
    /**
     * The config_db_values.
     */
    Hashtable<String, String> config_db_values = null;
    
    

    /**
     * The s_path prefix.
     */
    public static String s_pathPrefix = "./";

    /**
     * The m_i18n.
     */
    public I18nControlTT m_i18n = I18nControlTT.getInstance();

    private static DataAccessTT s_da = null; // This is handle to unique

    /**
     * The m_main.
     */
    public Component m_main = null;


    /**
     * The m_databases_treeroot.
     */
    public DbToolTreeRoot m_databases_treeroot = null;
    // public GGCTreeRoot m_meals_treeroot = null;


    private TranslationConfiguration translation_config = new TranslationConfiguration(); 

    
    /**
     * The status.
     */
    public String[] status = { "Not translated", "Must be checked", "Translated" };
    
    
    
    /**
     * The translation_data.
     */
    public TranslationData translation_data;
    
    

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
    private DataAccessTT()
    {
        super(I18nControlTT.getInstance());
        //System.out.println("DbToolsAccess");
        // m_db = db;
        // loadConfig();
        loadFonts();

        //m_dataDefs = new DatabaseDefinitions();
        //m_databases_treeroot = new DbToolTreeRoot(this);

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
    public static DataAccessTT getInstance()
    {
        if (s_da == null)
            s_da = new DataAccessTT();

        return s_da;
    }

    /**
     * Creates the instance.
     * 
     * @param main the main
     * 
     * @return the data access tt
     */
    public static DataAccessTT createInstance(Component main)
    {
        if (s_da == null)
        {
            s_da = new DataAccessTT();
            s_da.setParent(main);
        }

        return s_da;
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
        DataAccessTT.s_da = null;
    }

    
    
    
    
    
    
    // ********************************************************
    // ****** Fonts *****
    // ********************************************************

    //public static final int FONT_BIG_BOLD = 0;
    //public static final int FONT_NORMAL = 1;
    //public static final int FONT_NORMAL_BOLD = 2;

    /** 
     * loadFonts
     */
    public void loadFonts()
    {
        fonts = new Font[3];
        fonts[0] = new Font("SansSerif", Font.BOLD, 22);
        fonts[1] = new Font("SansSerif", Font.PLAIN, 12);
        fonts[2] = new Font("SansSerif", Font.BOLD, 12);
    }

    /** 
     * getFont
     */
    public Font getFont(int font_id)
    {
        return fonts[font_id];
    }

    // ********************************************************
    // ****** Parent handling (for UIs) *****
    // ********************************************************

    /**
     * Sets the parent.
     * 
     * @param main the new parent
     */
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






    /** 
     * getMonthsArray
     */
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

   

    /** 
     * getTimeString
     */
    public String getTimeString(int time)
    {

        int hours = time / 100;

        int min = time - hours * 100;

        return getLeadingZero(hours, 2) + ":" + getLeadingZero(min, 2);

    }



    /**
     * Not implemented.
     * 
     * @param source the source
     */
    public static void notImplemented(String source)
    {
        System.out.println("Not Implemented: " + source);
    }


    // ---
    // --- Array Utils
    // ---

    /**
     * Gets the array list from hashtable values.
     * 
     * @param table the table
     * 
     * @return the array list from hashtable values
     */
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


    /** 
     * checkPrerequisites
     */
    @Override
    public void checkPrerequisites()
    {
        // TODO Auto-generated method stub
        
    }

    /** 
     * getApplicationName
     */
    @Override
    public String getApplicationName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /** 
     * getHibernateDb
     */
    @Override
    public HibernateDb getHibernateDb()
    {
        return null;
    }

    /** 
     * getImagesRoot
     */
    @Override
    public String getImagesRoot()
    {
        return "/icons/simple_translation_tool/";
    }

    /** 
     * initSpecial
     */
    @Override
    public void initSpecial()
    {
    }

    /** 
     * loadBackupRestoreCollection
     */
    @Override
    public void loadBackupRestoreCollection()
    {
    }

    /** 
     * loadGraphConfigProperties
     */
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
    }

    /** 
     * getSelectedLangIndex
     */
    @Override
    public int getSelectedLangIndex()
    {
        return 0;
    }

    /** 
     * setSelectedLangIndex
     */
    @Override
    public void setSelectedLangIndex(int index)
    {
        // TODO Auto-generated method stub
        
    }

    /** 
     * loadPlugIns
     */
    @Override
    public void loadPlugIns()
    {
    }

    
    
    /**
   * Start db.
   */
    public void startDb()
    {
    }
    
    
    
    /**
     * Gets the translation data.
     * 
     * @return the translation data
     */
    public TranslationData getTranslationData()
    {
        return translation_data;
    }
    

    
    /**
     * Gets the translation configuration.
     * 
     * @return the translation data
     */
    public TranslationConfiguration getTranslationConfig()
    {
        return this.translation_config;
    }
    
    
    private boolean is_master_file_master_file;
    
    
    /**
     * Is Master File really master file
     * 
     * @return
     */
    public boolean isMasterFileMasterFile()
    {
        return this.is_master_file_master_file;
    }
    
    /**
     * Set is Master File really master file
     * 
     * @param val
     */
    public void setIsMasterFileMasterFile(boolean val)
    {
        this.is_master_file_master_file = val; 
    }
    

    /**
     * Get Max Decimals that will be used by DecimalHandler
     * 
     * @return
     */
    public int getMaxDecimalsUsedByDecimalHandler()
    {
        return 1;
    }
    
}
