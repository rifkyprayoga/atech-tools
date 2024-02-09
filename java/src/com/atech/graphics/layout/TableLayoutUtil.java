package com.atech.graphics.layout;

import info.clearthought.layout.TableLayout;

import java.util.List;

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

    public static double[] getDoubleArrayFromList(List<Double> list) {
        double[] out = new double[list.size()];

        int index =0;
        for (Double aDouble : list) {
            out[index] = aDouble;
            index++;
        }

        return out;
    }

}
