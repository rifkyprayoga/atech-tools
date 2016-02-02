package com.atech.graphics.graphs.v2.data;

/**
 * Created by andy on 03.01.16.
 */

/**
 * NEW V2 Graph
 */
public class GraphTimeDataDto
{

    // ATechDate format (DateTimeInSec)
    private long dateTime;

    private double value;


    public GraphTimeDataDto(long date, int time, double value)
    {
        this.dateTime = (date * 1000000 + time);
        this.value = value;
    }


    public GraphTimeDataDto(long dateTime, double value)
    {
        this.dateTime = dateTime;
        this.value = value;
    }


    @Override
    public String toString()
    {
        return dateTime + "=" + value;
    }


    public long getDateTime()
    {
        return dateTime;
    }


    public void setDateTime(long dateTime)
    {
        this.dateTime = dateTime;
    }


    public double getValue()
    {
        return value;
    }


    public void setValue(double value)
    {
        this.value = value;
    }
}
