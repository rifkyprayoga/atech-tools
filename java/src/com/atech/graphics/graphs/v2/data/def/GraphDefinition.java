package com.atech.graphics.graphs.v2.data.def;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.atech.graphics.graphs.v2.data.def.basic.BasicDefinitionData;
import com.atech.graphics.graphs.v2.data.def.ds.GraphDataSource;

/**
 * Created by andy on 28.05.15.
 */

@Root(name = "graph_definition")
public class GraphDefinition
{

    @Element(name = "basic_definition_data")
    private BasicDefinitionData baseDefinitionData;

    @ElementList(name = "data_sources")
    private ArrayList<GraphDataSource> dataSources;


    public BasicDefinitionData getBaseDefinitionData()
    {
        return baseDefinitionData;
    }


    public void setBaseDefinitionData(BasicDefinitionData baseDefinitionData)
    {
        this.baseDefinitionData = baseDefinitionData;
    }


    public ArrayList<GraphDataSource> getDataSources()
    {
        return dataSources;
    }


    public void setDataSources(ArrayList<GraphDataSource> dataSources)
    {
        this.dataSources = dataSources;
    }
}
