package com.atech.graphics.graphs.v2.data;

import java.awt.*;

/**
 * Created by andy on 06.01.16.
 */

/**
 * NEW V2 Graph
 */
public class GraphMarkerDto
{

    private Color color;
    private double value;


    public GraphMarkerDto(Color color, double value)
    {
        this.color = color;
        this.value = value;
    }


    public double getValue()
    {
        return value;
    }


    public Color getColor()
    {
        return color;
    }
}
