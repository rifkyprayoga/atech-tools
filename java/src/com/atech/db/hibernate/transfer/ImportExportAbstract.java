package com.atech.db.hibernate.transfer;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.utils.ATDataAccess;
import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ImportExportAbstract - Import Export Abstract (used for Export and Import classes
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
 *  @author Andy
 *
*/

public abstract class ImportExportAbstract
{

    Configuration m_cfg = null;
    Session m_session = null;
    protected int status_type = 0;
    protected int status_max_entry;
    protected String path_root = null;
    protected String file_2nd_part = null;
    BackupRestoreWorkGiver work_giver = null;
    HibernateConfiguration hibernate_conf = null;
    protected BufferedWriter bw_file = null;
    protected BufferedReader br_file = null;

    /**
     * The STATU s_ none.
     */
    public static int STATUS_NONE = 0;

    /**
     * The STATU s_ dot.
     */
    public static int STATUS_DOT = 1;

    /**
     * The STATU s_ procent.
     */
    public static int STATUS_PROCENT = 2;

    /**
     * The STATU s_ special.
     */
    public static int STATUS_SPECIAL = 3;


    /**
     * Instantiates a new import export abstract.
     * 
     * @param cfg the cfg
     * @param some the some
     */
    public ImportExportAbstract(Configuration cfg, int some)
    {
        this.m_cfg = cfg;

        SessionFactory sf = m_cfg.buildSessionFactory();
        m_session = sf.openSession();

        // processConfiguration();
    }


    /**
     * Instantiates a new import export abstract.
     * 
     * @param hib_conf the hib_conf
     */
    public ImportExportAbstract(HibernateConfiguration hib_conf)
    {
        this.hibernate_conf = hib_conf;
        this.m_session = this.hibernate_conf.getSession(2);
    }


    /**
     * Instantiates a new import export abstract.
     */
    public ImportExportAbstract()
    {
    }


    /**
     * Gets the session.
     * 
     * @return the session
     */
    public Session getSession()
    {
        m_session.clear();
        return m_session;
    }


    /**
     * Sets the root path.
     * 
     * @param path the new root path
     */
    public void setRootPath(String path)
    {
        this.path_root = path;
    }


    /**
     * Gets the root path.
     * 
     * @return the root path
     */
    public String getRootPath()
    {
        return this.path_root;
    }


    /**
     * Gets the file last part.
     * 
     * @return the file last part
     */
    public String getFileLastPart()
    {
        return this.file_2nd_part;
    }


    /**
     * Sets the file last part.
     * 
     * @param last_part the new file last part
     */
    public void setFileLastPart(String last_part)
    {
        this.file_2nd_part = last_part;
    }


    /**
     * Sets the hibernate configuration.
     * 
     * @param hconf the new hibernate configuration
     */
    public void setHibernateConfiguration(HibernateConfiguration hconf)
    {
        this.hibernate_conf = hconf;
    }


    /**
     * Gets the active session.
     * 
     * @return the active session
     */
    public abstract int getActiveSession();


    /**
     * Println.
     * 
     * @param txt the txt
     */
    public void println(String txt)
    {
        System.out.println(txt);
    }


    /**
     * Open file.
     * 
     * @param file the file
     */
    public void openFile(String file)
    {
        try
        {
            // bw_file = new BufferedWriter(new FileWriter(new File(file)));

            bw_file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file)), "UTF8"));
        }
        catch (Exception ex)
        {
            println("Exception on openFile: " + ex);
        }

    }


    /**
     * Open file for reading.
     * 
     * @param file the file
     */
    public void openFileForReading(File file)
    {
        try
        {
            // br_file = new BufferedReader(new FileReader(file));
            br_file = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        }
        catch (Exception ex)
        {
            println("Exception on openFileReading: " + ex);
        }

    }


    /**
     * Sets the type of status.
     * 
     * @param type the new type of status
     */
    public void setTypeOfStatus(int type)
    {
        this.status_type = type;
    }


    /**
     * Sets the status receiver.
     * 
     * @param giver the new status receiver
     */
    public void setStatusReceiver(BackupRestoreWorkGiver giver)
    {
        this.work_giver = giver;
    }


    /**
     * Status set max entry.
     * 
     * @param max_entry the max_entry
     */
    public void statusSetMaxEntry(int max_entry)
    {
        // System.out.println("max entries: " + max_entry);
        this.status_max_entry = max_entry;
    }


    /**
     * Write status.
     * 
     * @param every_x_entry the every_x_entry
     * @param count the count
     */
    public void writeStatus(int every_x_entry, int count)
    {
        if (this.status_type == ImportExportAbstract.STATUS_NONE)
            return;
        else if (this.status_type == ImportExportAbstract.STATUS_PROCENT)
        {
            writeStatusProcent(every_x_entry, count);
        }
        else if (this.status_type == ImportExportAbstract.STATUS_DOT)
        {
            this.writeStatusDots(every_x_entry, count);
        }
        else if (this.status_type == ImportExportAbstract.STATUS_SPECIAL)
        {
            this.writeStatusSpecial(every_x_entry, count);
        }
    }


    /**
     * Write status dots.
     * 
     * @param every_x_entry the every_x_entry
     * @param count the count
     */
    public void writeStatusDots(int every_x_entry, int count)
    {
        if (count % every_x_entry == 0)
        {
            System.out.println(".");
        }
    }


    /**
     * Write status procent.
     * 
     * @param every_x_entry the every_x_entry
     * @param count the count
     */
    public void writeStatusProcent(int every_x_entry, int count)
    {
        if (count % every_x_entry == 0)
        {
            if (this.status_max_entry <= 0)
                return;
            else
            {
                float val = count * 1.0f / this.status_max_entry;

                if (val > 1)
                {
                    System.out.println("100% (?)");
                }
                else
                {
                    System.out.println((int) (val * 100) + "%");
                }
            }
        }
    }


    /**
     * Write status special.
     * 
     * @param every_x_entry the every_x_entry
     * @param count the count
     */
    public void writeStatusSpecial(int every_x_entry, int count)
    {
        if (this.status_max_entry <= 0)
        {
            System.out.println("Status main entry problem");
            return;
        }
        else
        {
            float val = count * 1.0f / this.status_max_entry;

            if (val > 1)
            {
                System.out.println("Set status 100");
                this.work_giver.setStatus(100);
            }
            else
            {
                // System.out.println("Set Status: " + (int)(val * 100)) ;
                this.work_giver.setStatus((int) (val * 100));
            }
        }

    }


    /**
     * Write to file.
     * 
     * @param bro the bro
     */
    public void writeToFile(BackupRestoreObject bro)
    {
        try
        {
            bw_file.write(bro.dbExport());
            bw_file.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }
    }


    /**
     * Write to file.
     * 
     * @param entry the entry
     */
    public void writeToFile(String entry)
    {
        try
        {
            bw_file.write(entry);
            bw_file.flush();
        }
        catch (Exception ex)
        {
            println("Exception on writeToFile: " + ex);
        }

    }


    /**
     * Close file.
     */
    public void closeFile()
    {
        try
        {
            if (bw_file != null)
            {
                bw_file.close();
            }

            if (br_file != null)
            {
                br_file.close();
            }
        }
        catch (Exception ex)
        {
            println("Exception on closeFile: " + ex);
        }

    }


    /**
     * Gets the current date.
     * 
     * @return the current date
     */
    protected String getCurrentDate()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());

        return gc.get(Calendar.DAY_OF_MONTH) + "/" + (gc.get(Calendar.MONTH) + 1) + "/" + gc.get(Calendar.YEAR) + "  "
                + gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE) + ":" + gc.get(Calendar.SECOND);
    }


    /**
     * Gets the current date for file.
     * 
     * @return the current date for file
     */
    public String getCurrentDateForFile()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(System.currentTimeMillis());

        return gc.get(Calendar.YEAR) + "_" + getLeadingZero(gc.get(Calendar.MONTH) + 1, 2) + "_"
                + getLeadingZero(gc.get(Calendar.DAY_OF_MONTH), 2);

    }


    /**
     * Gets the leading zero.
     * 
     * @param number the number
     * @param places the places
     * 
     * @return the leading zero
     */
    public String getLeadingZero(int number, int places)
    {
        ;
        ATDataAccess.getInstance();
        return ATDataAccessAbstract.getLeadingZero(number, places);
    }

}
