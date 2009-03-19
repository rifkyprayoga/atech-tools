package com.atech.misc.refresh;
 
import java.util.Observable;
import java.util.Observer;  /* this is Event Handler */
 
public interface EventObserverInterface extends Observer
{
    public abstract void update (Observable obj, Object arg);
}
