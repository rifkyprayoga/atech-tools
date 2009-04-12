package com.atech.utils;

import java.util.ArrayList;

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


public class TimerThread extends Thread
{

    private boolean running = true;
    ArrayList<TimerControlAbstract> lst_jobs = new ArrayList<TimerControlAbstract>();
    private boolean started = false;


    public TimerThread()
    {
        System.out.println("TimerThread()");
    }


    public void addJob(TimerControlAbstract tca)
    {
        System.out.println("addJob");
        this.lst_jobs.add(tca);
    }

    public void removeJob(TimerControlAbstract tca)
    {
        System.out.println("removeJob");
        this.lst_jobs.remove(tca);
    }

    public void stopTimerThread()
    {
        this.running = false;
    }

    public boolean hasStarted()
    {
    	return this.started;
    }
    
    public void setStarted(boolean started)
    {
    	this.started = started;
    }
    
    
    
    
    @Override
    public void run()
    {
        System.out.println("run");

        while(running)
        {
            System.out.println("run.running");

        	//while (started)
        	{

	            if (this.lst_jobs.size()>0)
	            {
	                for(int i=0; i<this.lst_jobs.size(); i++)
	                {
	                    if (this.lst_jobs.get(i).hasChangeBeenTooLong())
	                    {
	                        System.out.println("to long");
	                        this.lst_jobs.get(i).stopAction();
	                        this.lst_jobs.get(i).setStopTime();
	                        running = false;
	                        started = false;
	                    }
	                    else
	                        System.out.println("too short");
	                }
	            }
        	}
        }  // while

    }

    

}


