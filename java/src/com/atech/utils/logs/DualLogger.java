package com.atech.utils.logs;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;



public class DualLogger
{
//    private static Log log = LogFactory.getLog(DualLogger.class);
    static Logger logger = Logger.getLogger(DualLogger.class);
    LogSettings settings = null;

    public DualLogger()
    {
        settings = new LogSettings();
        //logger.log("com.atech.utils.logs.DualLogger.", level, message, t)
        //logger.lo//
    }
    
    
    public void trace(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.TRACE, msg, t);
    }
    
    public void trace(LogSenderInterface lsi, String msg)
    {
        this.trace(lsi, msg, null);
    }
    
    

    
    public void info(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.INFO, msg, t);
    }
    
    public void info(LogSenderInterface lsi, String msg)
    {
        this.info(lsi, msg, null);
    }

    
    
    public void debug(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.DEBUG, msg, t);
    }
    
    public void debug(LogSenderInterface lsi, String msg)
    {
        this.debug(lsi, msg, null);
    }
    

    
    public void error(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.ERROR, msg, t);
    }
    
    public void error(LogSenderInterface lsi, String msg)
    {
        this.error(lsi, msg, null);
    }
    
    
    public void warn(LogSenderInterface lsi, String msg, Throwable t)
    {
        this.log(lsi, LogEntryType.WARNING, msg, t);
    }
    
    public void warn(LogSenderInterface lsi, String msg)
    {
        this.warn(lsi, msg, null);
    }
    
    
    
    
    public void log(LogSenderInterface lsi, int type, String msg, Throwable t)
    {
        logger.log("com.atech.utils.logs.DualLogger.", getPriority(type), msg, t);
        
        
        
        
        
        // TODO my logger
        
    }
    
    
    
    private Priority getPriority(int type)
    {
        
        return null;
    }
    
    
    
    
    
}

