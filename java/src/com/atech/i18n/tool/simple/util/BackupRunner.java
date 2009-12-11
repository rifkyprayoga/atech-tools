package com.atech.i18n.tool.simple.util;

import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.tool.simple.data.DataListProcessor;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  BackupRunner - it's used for running backup in application
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


public class BackupRunner extends Thread
{

    private boolean running = true;
    private boolean started = true;
    private static Log log = LogFactory.getLog(BackupRunner.class);
    

    long next_time = 0L;
    int backup_time = 0;
    
    DataListProcessor dlp;
    DataAccessTT m_da = DataAccessTT.getInstance();

    /**
     * Instantiates a new timer thread.
     */
    public BackupRunner(DataListProcessor _dlp)
    {
        this.backup_time = _dlp.getBackupTime();
        this.setNextTime();
        this.dlp = _dlp;
        
        log.debug("Backup Job started");
        
/*        
        if (debug)
        {
            System.out.println("ThreadStart(): ");
            this.writeCurrentTime();
            System.out.print("\n");
        }*/
    }

    
    private void setNextTime()
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(GregorianCalendar.MINUTE, this.backup_time);
        
        this.next_time = gc.getTimeInMillis();
    }
    

    private boolean isBackupTimeReached()
    {
        return (System.currentTimeMillis() > this.next_time);
    }
    

    
    /**
     * Stop timer thread.
     */
    public void stopTimerThread()
    {
        this.running = false;
    }

    /**
     * Checks for started.
     * 
     * @return true, if successful
     */
    public boolean hasStarted()
    {
    	return this.started;
    }
    
    /**
     * Sets the started.
     * 
     * @param started the new started
     */
    public void setStarted(boolean started)
    {
    	this.started = started;
    }
    
    
    
    
    /** 
     * run
     */
    @Override
    public void run()
    {
        while(running)
        {
            
            if (this.isBackupTimeReached())
            {
                log.debug("Backup Started");
                this.dlp.saveTranslationBackup();
                this.setNextTime();
            }
            
            try
            {
                Thread.sleep(30000); // 30s
            }
            catch(Exception ex) {}
            
            
        
        }

    }

    

}


