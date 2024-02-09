package com.atech.graphics.graphs.v2.data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by andy on 03.01.16.
 */

/**
 * NEW V2 Graph
 */
public class GraphTimeDataCollection extends HashMap<Long, List<GraphTimeDataDto>>
{

    private static final long serialVersionUID = -5764950770469404827L;


    @Override
    public String toString()
    {
        return "GraphTimeDataCollection [days=" + keySet().size() + ", this=" + super.toString() + "]";
    }
}
