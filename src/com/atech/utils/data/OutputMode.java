package com.atech.utils.data;

/**
 * Created by andy on 20.06.17.
 */
public enum OutputMode
{
    // result format
    Plain(false, false), //
    PlainDelimitedSpace(false, true), //
    DelimitedSpaceInBrackets(true, true), //
    InBrackets(true, false), //

    // value Type
    AsChar, // 'x'
    AsHex, // 0x00
    AsNumber, // 0
    AsShortHex, // 00
    ;

    private boolean delimitedWithSpace;
    private boolean inBrackets;


    OutputMode()
    {

    }


    OutputMode(boolean inBrackets, boolean delimitedSpace)
    {
        this.inBrackets = inBrackets;
        delimitedWithSpace = delimitedSpace;
    }


    public boolean isDelimitedWithSpace()
    {
        return delimitedWithSpace;
    }


    public boolean isInBrackets()
    {
        return this.inBrackets;
    }
}
