package com.atech.misc.refresh;

import java.util.Observable;
 
public class EventSource extends Observable //implements Runnable
{

    int type = 0;
    int value_int = 0;
    String value_str = null;
    
    public void sendChangeNotification(int value)
    {
        setChanged();
        notifyObservers( value );
    }

    public void sendChangeNotification(String value)
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