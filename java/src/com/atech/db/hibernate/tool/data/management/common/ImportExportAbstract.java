package com.atech.db.hibernate.tool.data.management.common;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.HibernateUtil;
import com.atech.db.hibernate.transfer.BackupRestoreObject;
import com.atech.db.hibernate.transfer.BackupRestoreWorkGiver;
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

    // protected Configuration configuration = null;
    // Session session = null;
    protected ImportExportStatusType statusType = ImportExportStatusType.None;
    protected int statusMaxEntry;
    protected String pathRoot = null;
    protected String file2NdPart = null;
    BackupRestoreWorkGiver workGiver = null;
    // HibernateConfiguration hibernateConfiguration = null;
    protected BufferedWriter bufferedWriter = null;
    protected BufferedReader bufferedReader = null;
    protected ImportExportContext importExportContext;
    protected HibernateUtil hibernateUtil;


    /**
     * Instantiates a new import export abstract.
     *
     * @param hibernateUtil hibernateUtil
     */
    public ImportExportAbstract(HibernateUtil hibernateUtil)
    {
        this.hibernateUtil = hibernateUtil;
        this.hibernateUtil.setSession(getActiveSession());
    }


    /**
     * Instantiates a new import export abstract.
     * 
     * @param cfg the cfg
     */
    public ImportExportAbstract(Configuration cfg, ImportExportContext importExportContext)
    {
        this.importExportContext = importExportContext;
        createHibernateUtil(cfg);
    }


    /**
     * Instantiates a new import export abstract.
     * 
     * @param hibernateConfiguration the hibernateConfiguration
     */
    public ImportExportAbstract(HibernateConfiguration hibernateConfiguration, ImportExportContext importExportContext)
    {
        this.importExportContext = importExportContext;
        createHibernateUtil(hibernateConfiguration);
    }


    /**
     * Instantiates a new import export abstract.
     *
     * @param hibernateConfiguration the hibernateConfiguration
     */
    public ImportExportAbstract(HibernateConfiguration hibernateConfiguration)
    {
        this(hibernateConfiguration, null);
    }


    /**
     * Instantiates a new import export abstract.
     *
     * @param hibernateConfiguration the hibernateConfiguration
     */
    public ImportExportAbstract()
    {
    }


    /**
     * Instantiates a new import export abstract.
     */
    public ImportExportAbstract(ImportExportContext importExportContext)
    {
        this.importExportContext = importExportContext;
    }


    public void createHibernateUtil(HibernateConfiguration hibernateConfiguration)
    {
        createHibernateUtil(hibernateConfiguration, null);
    }


    public void createHibernateUtil(Configuration configuration)
    {
        createHibernateUtil(null, configuration);
    }


    /**
     * Creates the hibernate util.
     */
    public void createHibernateUtil(HibernateConfiguration hibernateConfiguration, Configuration configuration)
    {
        if (hibernateConfiguration != null)
        {
            this.hibernateUtil = new HibernateUtil(hibernateConfiguration, HibernateConfiguration.DB_CONTEXT_FULL,
                    false);
        }
        else
        {
            this.hibernateUtil = new HibernateUtil(configuration);
        }

        this.hibernateUtil.setSession(this.getActiveSession());
    }


    /**
     * Gets the session.
     * 
     * @return the session
     */
    public Session getSession()
    {
        Session session = this.hibernateUtil.getSession();
        session.clear();
        return session;
    }


    /**
     * Sets the root path.
     * 
     * @param path the new root path
     */
    public void setRootPath(String path)
    {
        this.pathRoot = path;
    }


    /**
     * Gets the root path.
     * 
     * @return the root path
     */
    public String getRootPath()
    {
        return this.pathRoot;
    }


    /**
     * Gets the file last part.
     * 
     * @return the file last part
     */
    public String getFileLastPart()
    {
        return this.file2NdPart;
    }


    /**
     * Sets the file last part.
     * 
     * @param last_part the new file last part
     */
    public void setFileLastPart(String last_part)
    {
        this.file2NdPart = last_part;
    }


    // /**
    // * Sets the hibernate configuration.
    // *
    // * @param hconf the new hibernate configuration
    // */
    // public void setHibernateConfiguration(HibernateConfiguration hconf)
    // {
    // this.hibernateConfiguration = hconf;
    // }

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
            // bufferedWriter = new BufferedWriter(new FileWriter(new
            // File(file)));

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file)), "UTF8"));
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
            // bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
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
    public void setTypeOfStatus(ImportExportStatusType type)
    {
        this.statusType = type;
    }


    /**
     * Sets the status receiver.
     * 
     * @param giver the new status receiver
     */
    public void setStatusReceiver(BackupRestoreWorkGiver giver)
    {
        this.workGiver = giver;
    }


    /**
     * Status set max entry.
     * 
     * @param max_entry the max_entry
     */
    public void statusSetMaxEntry(int max_entry)
    {
        // System.out.println("max entries: " + max_entry);
        this.statusMaxEntry = max_entry;
    }


    /**
     * Write status.
     * 
     * @param statusDensity statusDensity (when status needs to be printed, example: after every 5th entry we write status)
     * @param count the count
     */
    public void writeStatus(int statusDensity, int count)
    {
        switch (this.statusType)
        {
            case Dot:
                this.writeStatusDots(statusDensity, count);
                break;

            case Percent:
                writeStatusPercent(statusDensity, count);
                break;

            case Special:
                this.writeStatusSpecial(count);
                break;

            case None:
            default:
                break;
        }
    }


    /**
     * Write status dots.
     *
     * @param statusDensity statusDensity (when status needs to be printed, example: after every 5th entry we write status)
     * @param count the count
     */
    public void writeStatusDots(int statusDensity, int count)
    {
        if (count % statusDensity == 0)
        {
            System.out.println(".");
        }
    }


    /**
     * Write status percent.
     *
     * @param statusDensity statusDensity (when status needs to be printed, example: after every 5th entry we write status)
     * @param count the count
     */
    public void writeStatusPercent(int statusDensity, int count)
    {
        if (count % statusDensity == 0)
        {
            if (this.statusMaxEntry <= 0)
                return;
            else
            {
                float val = count * 1.0f / this.statusMaxEntry;

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
     * @param count the count
     */
    public void writeStatusSpecial(int count)
    {
        if (this.statusMaxEntry <= 0)
        {
            System.out.println("Status max entry problem.");
            return;
        }
        else
        {
            float val = count * 1.0f / this.statusMaxEntry;

            if (val > 1)
            {
                System.out.println("Set status 100");
                this.workGiver.setStatus(100);
            }
            else
            {
                // System.out.println("Set Status: " + (int)(val * 100)) ;
                this.workGiver.setStatus((int) (val * 100));
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
            bufferedWriter.write(bro.dbExport());
            bufferedWriter.flush();
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
            bufferedWriter.write(entry);
            bufferedWriter.flush();
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
            if (bufferedWriter != null)
            {
                bufferedWriter.close();
            }

            if (bufferedReader != null)
            {
                bufferedReader.close();
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
        return ATDataAccessAbstract.getLeadingZero(number, places);
    }

}
