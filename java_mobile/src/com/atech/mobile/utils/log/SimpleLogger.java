package com.atech.mobile.utils.log;

public class SimpleLogger
{

    
    
    
    
    
    public void info(String message)
    {
        System.out.println("info: " + message);
        
    }


    public void error(String message)
    {
        System.out.println("error: " + message);
        
    }


    public void debug(String message)
    {
        System.out.println("debug: " + message);
    }

    public void trace(String message)
    {
        System.out.println("trace: " + message);
    }
    
    

    public void info(String message, Exception ex)
    {
        System.out.println("info: " + message + ",exception:" + ex);
        
    }


    public void error(String message, Exception ex)
    {
        System.out.println("error: " + message + ",exception:" + ex);
        
    }


    public void debug(String message, Exception ex)
    {
        System.out.println("debug: " + message + ",exception:" + ex);
    }

    public void trace(String message, Exception ex)
    {
        System.out.println("trace: " + message + ",exception:" + ex);
    }
    






}
