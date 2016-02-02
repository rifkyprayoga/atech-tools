package com.atech.graphics.graphs.v2.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.atech.graphics.graphs.v2.defs.DataRangeMainType;
import com.atech.graphics.graphs.v2.defs.DataRangeValue;
import com.atech.graphics.graphs.v2.defs.GraphValueType;
import com.atech.graphics.graphs.v2.defs.PlotType;

/**
 * Created by andy on 19.12.15.
 */

/**
 * NEW V2 Graph
 */

public class GraphDefinitionDto
{

    private String title;
    private DataRangeMainType dataRangeMainType;
    private DataRangeValue dataRangeValue;
    private Dimension size;
    private PlotType plotType;
    private List<GraphMarkerDto> markers;
    private Integer minimalValue;
    private Integer maximalValue;
    private GraphValueType valueType;
    private String helpId;
    private Integer minimalAllowedValue;
    private Integer maximalAllowedValue;


    public GraphDefinitionDto()
    {
    }


    public GraphDefinitionDto(String title, DataRangeMainType dataRangeMainType, DataRangeValue dataRangeValue,
            Dimension size, PlotType plotType, GraphValueType valueType)
    {
        this(title, dataRangeMainType, dataRangeValue, size, plotType, valueType, null, null, null, null, null);
    }


    public GraphDefinitionDto(String title, DataRangeMainType dataRangeMainType, DataRangeValue dataRangeValue,
            Dimension size, PlotType plotType, GraphValueType valueType, Integer minimalValue, Integer maximalValue,
            Integer minimalAllowedValue, Integer maximalAllowedValue, String helpId)
    {
        this.title = title;
        this.dataRangeMainType = dataRangeMainType;
        this.dataRangeValue = dataRangeValue;
        this.size = size;
        this.plotType = plotType;
        this.minimalValue = minimalValue;
        this.maximalValue = maximalValue;
        this.valueType = valueType;
        this.minimalAllowedValue = minimalAllowedValue;
        this.maximalAllowedValue = maximalAllowedValue;
        this.helpId = helpId;
    }


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public DataRangeMainType getDataRangeMainType()
    {
        return dataRangeMainType;
    }


    public void setDataRangeMainType(DataRangeMainType dataRangeMainType)
    {
        this.dataRangeMainType = dataRangeMainType;
    }


    public DataRangeValue getDataRangeValue()
    {
        return dataRangeValue;
    }


    public void setDataRangeValue(DataRangeValue dataRangeValue)
    {
        this.dataRangeValue = dataRangeValue;
    }


    public Dimension getSize()
    {
        return size;
    }


    public void setSize(Dimension size)
    {
        this.size = size;
    }


    public PlotType getPlotType()
    {
        return plotType;
    }


    public void setPlotType(PlotType plotType)
    {
        this.plotType = plotType;
    }


    public void addMarker(Color color, double value)
    {
        if (this.markers == null)
            this.markers = new ArrayList<GraphMarkerDto>();

        this.markers.add(new GraphMarkerDto(color, value));
    }


    public List<GraphMarkerDto> getMarkers()
    {
        return this.markers;
    }


    public Integer getMinimalValue()
    {
        return minimalValue;
    }


    public Integer getMaximalValue()
    {
        return maximalValue;
    }


    public GraphValueType getValueType()
    {
        return valueType;
    }


    public String getHelpId()
    {
        return helpId;
    }


    public Integer getMinimalAllowedValue()
    {
        return minimalAllowedValue;
    }


    public Integer getMaximalAllowedValue()
    {
        return maximalAllowedValue;
    }
}
