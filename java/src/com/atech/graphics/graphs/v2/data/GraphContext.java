package com.atech.graphics.graphs.v2.data;

import java.util.HashMap;
import java.util.List;

import com.atech.graphics.graphs.v2.defs.GraphDataSourceTarget;
import com.atech.graphics.graphs.v2.defs.GraphDataSourceType;

/**
 * Created by andy on 29.04.15.
 */

// perhaps interface
public interface GraphContext
{

    // HashMap<GraphDataSourceType, GraphDataSource> getDataSources();

    HashMap<GraphDataSourceType, GraphDataSourceTarget> getDataSourceTargets();


    List<GraphDataSourceType> getDataSources();

}
