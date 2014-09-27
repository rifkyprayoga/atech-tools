package com.atech.update.startup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;

/**
 *  This file is part of ATech Tools library.
 *  
 *  StartupUtil - Util for startup part of atech-tools
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy {andy@atech-software.com}
 *
*/

public class StartupUtil
{

    /**
     * Startup status file
     */
    public static String startup_status = "../data/StartupStatus.txt";

    /**
     * Get Configuration - reads properties file and read all entries
     * 
     * @param filename
     * @return
     */
    public static Hashtable<String, String> getConfiguration(String filename)
    {
        Hashtable<String, String> config_db_values = new Hashtable<String, String>();

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

            for (Enumeration<Object> en = props.keys(); en.hasMoreElements();)
            {
                String key = (String) en.nextElement();
                config_db_values.put(key, props.getProperty(key));
            }
        }

        return config_db_values;

    }

    /**
     * Should Startup Files Be Created
     * 
     * @return
     */
    public static boolean shouldStartupFilesBeCreated()
    {

        File f = new File(startup_status);

        if (!f.exists())
            return true;
        else
        {
            Hashtable<String, String> cfg = StartupUtil.getConfiguration(startup_status);

            String running_os = cfg.get("RUNNING_OS");
            String running_arch = cfg.get("RUNNING_ARCH");
            String running_ver = cfg.get("RUNNING_VERSION");
            // boolean db_check = isOptionEnabled(cfg.get("DB_CHECK"));
            boolean force_rebuild = isOptionEnabled(cfg.get("FORCE_REBUILD"));

            if (force_rebuild)
                return true;

            if (!running_os.equals(System.getProperty("os.name"))
                    || !running_ver.equals(System.getProperty("os.version"))
                    || !running_arch.equals(System.getProperty("os.arch")))
                return true;

        }

        return false;
    }

    /**
     * Should Db Check Done
     * 
     * @return
     */
    public static boolean shouldDbCheckBeDone()
    {

        File f = new File(startup_status);

        if (!f.exists())
            return true;
        else
        {
            Hashtable<String, String> cfg = StartupUtil.getConfiguration(startup_status);

            boolean db_check = isOptionEnabled(cfg.get("DB_CHECK"));

            return db_check;
        }

    }

    /**
     * Write Startup
     * 
     * @param rebuild
     * @param db_check
     */
    public static void writeStartup(boolean rebuild, boolean db_check)
    {
        writeStartup(System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version"),
            db_check, rebuild);

    }

    /**
     * Write Startup With Old Copy 
     */
    public static void writeStartupWithOldCopy()
    {
        File f = new File(startup_status);

        if (!f.exists())
        {
            writeStartup(System.getProperty("os.name"), System.getProperty("os.arch"),
                System.getProperty("os.version"), false, true);
        }
        else
        {
            Hashtable<String, String> cfg = StartupUtil.getConfiguration(startup_status);

            String running_os = cfg.get("RUNNING_OS");
            String running_arch = cfg.get("RUNNING_ARCH");
            String running_ver = cfg.get("RUNNING_VERSION");
            boolean force_rebuild = isOptionEnabled(cfg.get("FORCE_REBUILD"));

            writeStartup(running_os, running_arch, running_ver, false, force_rebuild);
        }

    }

    /**
     * @param type
     */
    public static void writeStartupWithOldCopy(int type)
    {
        File f = new File(startup_status);

        if (!f.exists())
        {
            writeStartup(System.getProperty("os.name"), System.getProperty("os.arch"),
                System.getProperty("os.version"), false, false);
        }
        else
        {
            Hashtable<String, String> cfg = StartupUtil.getConfiguration(startup_status);

            String running_os = cfg.get("RUNNING_OS");
            String running_arch = cfg.get("RUNNING_ARCH");
            String running_ver = cfg.get("RUNNING_VERSION");

            boolean db_check;
            boolean force_rebuild;

            // db check
            if (type == 1)
            {
                db_check = false;
                force_rebuild = isOptionEnabled(cfg.get("FORCE_REBUILD"));
            }
            else
            {
                db_check = isOptionEnabled(cfg.get("DB_CHECK"));
                force_rebuild = false;
            }

            writeStartup(running_os, running_arch, running_ver, db_check, force_rebuild);
        }

    }

    /**
     * Write Startup
     * 
     * @param os_name
     * @param os_arch
     * @param os_version
     * @param db_check
     * @param rebuild
     */
    public static void writeStartup(String os_name, String os_arch, String os_version, boolean db_check, boolean rebuild)
    {

        try
        {
            File f = new File(startup_status);

            // System.out.println("File: " + f.getPath());

            if (!f.exists())
            {
                f.createNewFile();
                db_check = true;
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(";\n");
            bw.write(";  Created by Atech-Tools on " + getCurrentDateTimeString() + "\n");
            bw.write(";\n");
            bw.write("RUNNING_OS=" + os_name + "\n");
            bw.write("RUNNING_ARCH=" + os_arch + "\n");
            bw.write("RUNNING_VERSION=" + os_version + "\n");
            bw.write("DB_CHECK=" + db_check + "\n");
            bw.write("FORCE_REBUILD=" + rebuild + "\n");

            bw.flush();

            bw.close();
        }
        catch (Exception ex)
        {
            System.out.println("writeStartup. Error writing file: " + ex);
        }

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

        if (replace.equals(replacement))
            return input;

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

    /**
     * Get Current DateTime String
     * 
     * @return
     */
    public static String getCurrentDateTimeString()
    {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.get(Calendar.DAY_OF_MONTH) + "." + (gc.get(Calendar.MONTH) + 1) + "." + gc.get(Calendar.YEAR) + "  "
                + gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE) + ":" + gc.get(Calendar.SECOND);
    }

}
