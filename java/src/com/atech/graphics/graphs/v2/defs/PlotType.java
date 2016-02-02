package com.atech.graphics.graphs.v2.defs;

/**
 * Created by andy on 06.01.16.
 */

/**
 * NEW V2 Graph
 */
public enum PlotType
{
    Unknown(PlotDataType.None), //
    Scatter(PlotDataType.XYDataset), //
    ScatterTime(PlotDataType.TimeDataset), //

    ;

    PlotDataType dataType;


    PlotType(PlotDataType dataType)
    {
        this.dataType = dataType;
    }


    public PlotDataType getPlotDataType()
    {
        return this.dataType;
    }

}
