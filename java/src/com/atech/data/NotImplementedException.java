package com.atech.data;

/**
 * Created by andy on 22.12.15.
 */
public class NotImplementedException extends RuntimeException
{

    private static final long serialVersionUID = 8770428303886201758L;
    String source;
    String className;


    public NotImplementedException(Class clazzz, String source)
    {
        super();
        this.source = source;
        this.className = clazzz.getName();
    }


    @Override
    public String getMessage()
    {
        if (className == null)
        {
            return "Method " + source + " not implemented.";
        }
        else
        {
            return "Class: " + this.className + ", Method: " + source + " not implemented.";
        }
    }

}
