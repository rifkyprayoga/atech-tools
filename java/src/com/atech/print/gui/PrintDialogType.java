package com.atech.print.gui;

public enum PrintDialogType
{
    /**
     * Dialog Options: Year and Month Option
     */
    YEAR_MONTH(1),

    /**
     * Dialog Options: Range with day option
     */
    RANGE_DAY(2);

    private int typeValue;

    private PrintDialogType(int type)
    {
        this.typeValue = type;
    }

    public int getTypeValue()
    {
        return typeValue;
    }

    public void setTypeValue(int typeValue)
    {
        this.typeValue = typeValue;
    }

}
