package com.atech.utils;

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

    protected int allowed_change_time = 0;
    protected boolean timer_started = false;
    protected boolean timer_stopped = false;

    public void setStartTime()
    {
        this.timer_started = true;
    }

    public void setStopTime()
    {
        System.out.println("setSTopTime");
        //this.timer_started = false;
        if (this.timer_started)
            this.timer_stopped = true;
        
    }


    public boolean hasTimerStarted()
    {
        return this.timer_started;
    }
    
    
    public void setAllowedChangeTime(int time_seconds)
    {
        this.allowed_change_time = time_seconds * 1000;
    }


    public abstract void stopAction();

    
    public boolean hasChangeBeenTooLong()
    {
        if (!timer_started)
            return false;
        
        if (timer_stopped)
            return true;
            
        
//        if (!this.timer_started)
//            return true;

        long t = getLastChangedTime();

        long difference = System.currentTimeMillis() - t;

        if (difference > this.allowed_change_time)
            return true;
        else
            return false;
    }




	public void setLastChangedTime()
	{
        this.timer_started = true;
		workWith(TimerControlAbstract.DATA_SET, System.currentTimeMillis());
	}
	
	
	public long getLastChangedTime()
	{
		return workWith(TimerControlAbstract.DATA_GET, 0L);
	}
	
	public static final int DATA_GET = 1;
	public static final int DATA_SET = 2;
	
	public long current_time = 0L;
	
	public synchronized long workWith(int type, long data)
	{
		if (type == TimerControlAbstract.DATA_GET)
		{
			return this.current_time; 
		}
		else
		{
			this.current_time = data;
			return 0L;
		}
	}
    
    

    

}


