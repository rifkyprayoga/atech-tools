package com.atech.graphics.graphs.v2.data.def.ds;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by andy on 28.05.15.
 */
@Root(name = "data_source")
public class GraphDataSource
{

    @Element(name = "type")
    String dataSourceType;

    @ElementList(name = "data_types")
    // @Element(name = "data_type")
    ArrayList<String> dataTypes;
}
