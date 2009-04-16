package com.atech.utils.logs;

import java.util.GregorianCalendar;



public class LogEntry
{
    
    private long time_ms;
    
    private int type;
    
    private String message;
    
    private Object object;
    
    
    public LogEntry(int type_, String message_)
    {
        this(type_, message_, null);
    }
    
    public LogEntry(int type_, String message_, Object obj)
    {
        this.type = type_;
        this.message = message_;
        this.object = obj;
        this.time_ms = System.currentTimeMillis();
    }
    
    
    

    public void setTimeMs(long time_ms)
    {
        this.time_ms = time_ms;
    }

    public long getTimeMs()
    {
        return time_ms;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setObject(Object object)
    {
        this.object = object;
    }

    public Object getObject()
    {
        return object;
    }
    
    
}
