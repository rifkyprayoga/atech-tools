package com.atech.graphics.graphs.v2.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.atech.graphics.graphs.v2.data.def.GraphDefinition;

/**
 * Created by andy on 28.05.15.
 */
public class ReadWriteDefinitionUTest
{

    @Test
    public void readDefinition()
    {

        try
        {
            Serializer serializer = new Persister();
            File source = new File("target/classes/com/atech/graphics/graphs/v2/data/def/graph_preversion.xml");

            GraphDefinition graphDef = serializer.read(GraphDefinition.class, source);

            Assert.assertNotNull(graphDef);
            Assert.assertNotNull(graphDef.getBaseDefinitionData());
            Assert.assertNotNull(graphDef.getDataSources());
            Assert.assertEquals(graphDef.getDataSources().size(), 3);
            Assert.assertNotNull(graphDef.getBaseDefinitionData());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
