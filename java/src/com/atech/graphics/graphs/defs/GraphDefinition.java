package com.atech.graphics.graphs.defs;

// this class is used for decoding/encoding graph definition
public class GraphDefinition
{
    
    public String encoded_definition = null;
    
    public GraphDefinition(String def_data)
    {
        this.encoded_definition = def_data;
        load();
    }
    
    
    public boolean load()
    {
        return false;        
    }
    
    public boolean save()
    {
        return false;
    }
    
}
