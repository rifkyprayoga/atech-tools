package com.atech.graphics.graphs.v2.data;

import java.util.GregorianCalendar;

/**
 * Created by andy on 03.01.16.
 */

/**
 * NEW V2 Graph
 */
public interface GraphDbDataRetriever
{

    GraphTimeDataCollection getGraphTimeData(GregorianCalendar gcFrom, GregorianCalendar gcTill,
            GraphDefinitionDto definitionDto);

}
