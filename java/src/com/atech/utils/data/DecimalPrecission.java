package com.atech.utils.data;

/**
 * Created by andy on 22.05.15.
 */
public enum DecimalPrecission
{

    NoDecimal("%d", "%d"), //
    OneDecimal("%3.1f", "%5.2f"), //
    TwoDecimals("%4.2f", "%6.3f"), //
    ThreeDecimals("%5.3f", "%7.4f") //

    ;

    // 4.2f
    private String formatString2;
    private String formatString3;


    DecimalPrecission(String twoNumbers, String threeNumbers)
    {
        formatString2 = twoNumbers;
        formatString3 = threeNumbers;
    }


    // we default to two place numbers, at later time, we can extend this.
    public String getFormatString()
    {
        return formatString2;
    }
}
