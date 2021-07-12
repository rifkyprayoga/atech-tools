package com.atech.misc.converter;

/**
 * Created by andy on 22/11/17.
 */
public enum UnitValuePrecision
{
    Long(0), //
    Integer(0), //
    FloatOneDecimalPlace(1), //
    FloatTwoDecimalPlaces(2), //
    FloatThreeDecimalPlaces(3), //
    None(0);

    int precision = 0;


    UnitValuePrecision(int precision)
    {
        this.precision = precision;
    }


    public int getDecimalPlaces()
    {
        return this.precision;
    }
}
