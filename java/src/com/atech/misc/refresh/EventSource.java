package com.atech.misc.refresh;

import java.util.Observable;
 

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


public class EventSource extends Observable //implements Runnable
{

    /**
     * The type.
     */
    int type = 0;
    
    /**
     * The value_int.
     */
    int value_int = 0;
    
    /**
     * The value_str.
     */
    String value_str = null;
    
    /**
     * The value_obj.
     */
    Object value_obj = null;
    
    /**
     * Send change notification.
     * 
     * @param value the value
     */
    public void sendChangeNotification(int value)
    {
        setChanged();
        notifyObservers( value );
    }

    /**
     * Send change notification.
     * 
     * @param value the value
     */
    public void sendChangeNotification(String value)
    {
        setChanged();
        notifyObservers( value );
    }

    /**
     * Send change notification.
     * 
     * @param value the value
     */
    public void sendChangeNotification(Object value)
    {
        setChanged();
        notifyObservers( value );
    }
    
    
    /*
    public void clear()
    {
        type = 0;
        value_int = 0;
        value_str = null;
    }
    
    public void run()
    {
        while( true )
        {
            //System.out.println("Event Source");
            
            if (type>0)
            {
                if (type == 1)
                    notifyObservers(value_int);
                else
                    notifyObservers(value_str);
                
                clear();
                //notifyObservers( response );
                
            }
            
            try
            {
                Thread.sleep(500);
            }
            catch(Exception ex) {}
        
            
        }
    }*/
}
