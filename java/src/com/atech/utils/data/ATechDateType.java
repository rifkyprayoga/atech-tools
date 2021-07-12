package com.atech.utils.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 29.09.15.
 */
public enum ATechDateType
{
    DateOnly(1, ""), //
    TimeOnlyMin(2, ""), //
    TimeOnlySec(3, ""), //
    TimeOnlyMSec(4, ""), //
    DateAndTimeMin(5, ""), //
    DateAndTimeSec(6, ""), //
    DateAndTimeMsec(7, "") //
    ;

    int code;

    private static Map<Integer, ATechDateType> mapByCode = new HashMap<Integer, ATechDateType>();

    static
    {
        for (ATechDateType adt : values())
        {
            mapByCode.put(adt.code, adt);
        }
    }


    ATechDateType(int code, String desc)
    {
        this.code = code;
    }


    public int getCode()
    {
        return code;
    }


    public static ATechDateType getByType(int code)
    {
        return mapByCode.get(code);
    }


    public boolean isTimeOnly()
    {
        return ((this == TimeOnlyMin) || (this == TimeOnlySec) || (this == TimeOnlyMSec));
    }

}
