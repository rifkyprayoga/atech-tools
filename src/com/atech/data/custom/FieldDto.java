package com.atech.data.custom;

/**
 * Created by andy on 13/01/17.
 */
public class FieldDto
{

    String name;
    String type;

    String value;


    public FieldDto()
    {
    }


    public FieldDto(String name, String type)
    {
        this.name = name;
        this.type = type;
    }


    public FieldDto clone()
    {
        FieldDto f = new FieldDto();
        f.name = name;
        f.type = type;

        return f;
    }

}
