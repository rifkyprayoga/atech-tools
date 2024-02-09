package com.atech.data;

/**
 * Created by andy on 15.10.15.
 */
public class SimpleTimeValueDataDto extends AbstractTimeDataDto
{

    private String value;


    public SimpleTimeValueDataDto(String time, String value)
    {
        super(time);
        this.value = value;
    }


    public String getValue()
    {
        return value;
    }


    public void setValue(String value)
    {
        this.value = value;
    }


    public int compareTo(SimpleTimeValueDataDto o)
    {
        return o.time - this.time;
    }

}
