package com.atech.graphics.graphs.v2.data;

import java.util.HashMap;
import java.util.List;

import com.atech.graphics.graphs.v2.defs.GraphDataSourceTarget;
import com.atech.graphics.graphs.v2.defs.GraphDataSourceType;

/**
 * Created by andy on 30.04.15.
 */
public class GraphContextAbstract implements GraphContext
{

    // public void addDataSources(HashMap<GraphDataSourceType, GraphDataSource>
    // dataSources)
    // {
    //
    // }

    public HashMap<GraphDataSourceType, GraphDataSourceTarget> getDataSourceTargets()
    {
        return null;
    }


    public List<GraphDataSourceType> getDataSources()
    {
        return null;
    }


    public void addContext(GraphContext graphContext)
    {

    }
}
