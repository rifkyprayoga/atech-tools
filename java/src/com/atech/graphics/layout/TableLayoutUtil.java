package com.atech.graphics.layout;

import info.clearthought.layout.TableLayout;

/**
 * Created by andy on 19.12.15.
 */
public class TableLayoutUtil
{

    public static TableLayout createVerticalLayout(double... cellSizes)
    {
        return new TableLayout(createSizesForVerticalLayout(cellSizes));
    }


    public static TableLayout createHorizontalLayout(double... cellSizes)
    {
        return new TableLayout(createSizesForHorizontalLayout(cellSizes));
    }


    public static double[][] createSizesForVerticalLayout(double... cellSizes)
    {
        double sizes[][] = { { TableLayout.FILL }, //
                            cellSizes };
        return sizes;
    }


    public static double[][] createSizesForHorizontalLayout(double... cellSizes)
    {
        double sizes[][] = { cellSizes, //
                            { TableLayout.FILL } };

        return sizes;
    }

}
