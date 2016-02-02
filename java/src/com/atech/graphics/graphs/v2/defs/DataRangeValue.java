package com.atech.graphics.graphs.v2.defs;

import java.util.Calendar;

/**
 * Created by andy on 19.12.15.
 */
public enum DataRangeValue
{

    Day(1, Calendar.DAY_OF_YEAR), //
    Week(1, Calendar.WEEK_OF_YEAR), //
    TwoWeeks(2, Calendar.WEEK_OF_YEAR), //
    ThreeWeeks(3, Calendar.WEEK_OF_YEAR), //
    Month(1, Calendar.MONTH), //
    TwoMonths(2, Calendar.MONTH), //
    ThreeMonths(3, Calendar.MONTH), //
    FourMonths(4, Calendar.MONTH), //
    FiveMonths(5, Calendar.MONTH), //
    HalfYear(6, Calendar.MONTH), ;

    int value;
    int calendarType;


    DataRangeValue(int value, int calendarType)
    {
        this.value = value;
        this.calendarType = calendarType;
    }


    public int getValue()
    {
        return this.value;
    }


    public int getCalendarType()
    {
        return this.calendarType;
    }

}
