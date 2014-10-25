package com.atech.print.engine;

import java.util.GregorianCalendar;
import java.util.HashMap;

public class PrintParameters extends HashMap<String, Object>
{

    private static final long serialVersionUID = -5506627292101581566L;

    public GregorianCalendar getRangeFrom()
    {
        if (this.containsKey("RANGE_FROM"))
            return (GregorianCalendar) this.get("RANGE_FROM");
        else
            return null;
    }

}
