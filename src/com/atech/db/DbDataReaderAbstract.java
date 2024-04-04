package com.atech.db;

/**
  *  This file is part of ATech Tools library.
  *  
  *  DbDataReaderAbstract - Abstract class for reading data from database in separate
  *  thread.
  *  
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
public abstract class DbDataReaderAbstract extends Thread
{

    /**
     * Status - None
     */
    public static final int STATUS_NONE = 0;

    /**
     * Status Ready - ready for writing  
     */
    public static final int STATUS_READY = 1;

    /**
     * Status Reading - we are reading from db 
     */
    public static final int STATUS_READING = 2;

    /**
     * Status Finished Reading - reading from db was stopped 
     */
    public static final int STATUS_FINISHED_READING = 3;

    /**
     * Status Finished Reading with error - reading from db was stopped by error 
     */
    public static final int STATUS_FINISHED_READING_ERROR = 4;

    protected int current_status = 0;
    DbDataReadingFinishedInterface finished_interface;

    /**
     * Get Type Of Data - returns type of data
     * 
     * @return int as type
     */
    public abstract int getTypeOfData();

    /**
     * Get Data - returns data
     * 
     * @return data as Object
     */
    public abstract Object getData();

    /**
     * Get Status - returns status of current reading
     * 
     * @return status of reading
     */
    public int getStatus()
    {
        return this.current_status;
    }

    /**
     * Set Status - sets status of reading
     * 
     * @param status as int
     */
    public void setStatus(int status)
    {
        this.current_status = status;

        if (status > 2 && this.finished_interface != null)
        {
            this.finished_interface.readingFinished();
        }

    }

    /**
     * Is Finished - queries if reading is finished
     * 
     * @return true if operation is finished
     */
    public boolean isFinished()
    {
        return this.current_status > 2;
    }

    /**
     * setReadingFinishedObject - we can set object which will be notified if reading is finished
     * 
     * @param data instance of DbDataReadingFinishedInterface 
     */
    public void setReadingFinishedObject(DbDataReadingFinishedInterface data)
    {
        this.finished_interface = data;
    }

    /** 
     * Run - method for running thread
     */
    @Override
    public abstract void run();

}
