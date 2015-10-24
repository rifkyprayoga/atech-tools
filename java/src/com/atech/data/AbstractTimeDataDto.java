package com.atech.data;

/**
 * Created by andy on 15.10.15.
 */
public class AbstractTimeDataDto implements Comparable<AbstractTimeDataDto>
{

    protected int time;
    protected String timeString;


    public AbstractTimeDataDto(String time)
    {
        setTime(time);
    }


    private void setTime(String time)
    {
        this.timeString = time;
        String s = time.replace(":", "");
        this.time = Integer.parseInt(s);
    }


    public int getTime()
    {
        return time;
    }


    public void setTime(int time)
    {
        this.time = time;
    }


    public String getTimeString()
    {
        return timeString;
    }


    public void setTimeString(String timeString)
    {
        this.timeString = timeString;
    }


    public int compareTo(AbstractTimeDataDto o)
    {
        return this.time - o.time;
    }

}
