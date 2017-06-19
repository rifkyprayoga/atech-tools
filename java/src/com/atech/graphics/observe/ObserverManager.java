package com.atech.graphics.observe;

import java.util.HashMap;
import java.util.Map;

public class ObserverManager
{

    Map<ObservableType, EventSource> observables;
    boolean active;


    public ObserverManager(boolean startsActive)
    {
        observables = new HashMap<ObservableType, EventSource>();
        active = startsActive;
    }


    public void initObserverManager(ObservableType... types)
    {
        for (ObservableType type : types)
        {
            observables.put(type, new EventSource());
        }
    }


    public void addObserver(ObservableType observableType, EventObserverInterface inst)
    {
        observables.get(observableType).addObserver(inst);
    }


    /**
     * Set Change On Event Source
     *
     * @param observableType
     * @param value
     */
    public void setChangeOnEventSource(ObservableType observableType, int value)
    {
        if (this.active)
            observables.get(observableType).sendChangeNotification(value);
    }


    /**
     * Set Change On Event Source
     *
     * @param observableType
     * @param value
     */
    public void setChangeOnEventSource(ObservableType observableType, String value)
    {
        if (this.active)
            observables.get(observableType).sendChangeNotification(value);
    }


    /**
     * Set Change On Event Source
     *
     * @param observableType
     * @param value
     */
    public void setChangeOnEventSource(ObservableType observableType, Object value)
    {
        if (this.active)
            observables.get(observableType).sendChangeNotification(value);
    }


    public void startToObserve()
    {
        this.active = true;
    }


    public void stopToObserve()
    {
        this.active = false;
    }

}
