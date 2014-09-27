package com.atech.utils;

// TODO: Auto-generated Javadoc
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

public abstract class TimerControlAbstract
{

    /**
     * The allowed_change_time.
     */
    protected int allowed_change_time = 0;

    /**
     * The timer_started.
     */
    protected boolean timer_started = false;

    /**
     * The timer_stopped.
     */
    protected boolean timer_stopped = false;

    /**
     * Sets the start time.
     */
    public void setStartTime()
    {
        this.timer_started = true;
    }

    /**
     * Sets the stop time.
     */
    public void setStopTime()
    {
        System.out.println("setSTopTime");
        // this.timer_started = false;
        if (this.timer_started)
        {
            this.timer_stopped = true;
        }

    }

    /**
     * Checks for timer started.
     * 
     * @return true, if successful
     */
    public boolean hasTimerStarted()
    {
        return this.timer_started;
    }

    /**
     * Sets the allowed change time.
     * 
     * @param time_seconds the new allowed change time
     */
    public void setAllowedChangeTime(int time_seconds)
    {
        this.allowed_change_time = time_seconds * 1000;
    }

    /**
     * Stop action.
     */
    public abstract void stopAction();

    /**
     * Checks for change been too long.
     * 
     * @return true, if successful
     */
    public boolean hasChangeBeenTooLong()
    {
        if (!timer_started)
            return false;

        if (timer_stopped)
            return true;

        // if (!this.timer_started)
        // return true;

        long t = getLastChangedTime();

        long difference = System.currentTimeMillis() - t;

        if (difference > this.allowed_change_time)
            return true;
        else
            return false;
    }

    /**
     * Sets the last changed time.
     */
    public void setLastChangedTime()
    {
        this.timer_started = true;
        workWith(TimerControlAbstract.DATA_SET, System.currentTimeMillis());
    }

    /**
     * Gets the last changed time.
     * 
     * @return the last changed time
     */
    public long getLastChangedTime()
    {
        return workWith(TimerControlAbstract.DATA_GET, 0L);
    }

    /**
     * The Constant DATA_GET.
     */
    public static final int DATA_GET = 1;

    /**
     * The Constant DATA_SET.
     */
    public static final int DATA_SET = 2;

    /**
     * The current_time.
     */
    public long current_time = 0L;

    /**
     * Work with.
     * 
     * @param type the type
     * @param data the data
     * 
     * @return the long
     */
    public synchronized long workWith(int type, long data)
    {
        if (type == TimerControlAbstract.DATA_GET)
            return this.current_time;
        else
        {
            this.current_time = data;
            return 0L;
        }
    }

}
