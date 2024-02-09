package com.atech.graphics.graphs.v2.data.def.basic;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by andy on 28.05.15.
 */
@Root(name = "basic_definition_data")
public class BasicDefinitionData
{

    @Element
    public String id;

    @Element
    public String title;

    @Element
    public String description;

    @Element(name = "initial_size")
    public String initialSize;

    @Element
    public boolean resizable;

    @Element(name = "show_legend")
    public boolean showLegend;

}
