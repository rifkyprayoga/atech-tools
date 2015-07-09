package com.atech.graphics.graphs.v2.data.def.basic;

import org.simpleframework.xml.Root;

import com.atech.graphics.graphs.v2.defs.DataRangeMainType;
import com.atech.graphics.graphs.v2.defs.DataRangeType;

/**
 * Created by andy on 28.05.15.
 */

@Root(name = "data_range")
public class DataRange
{

    DataRangeMainType rangeType;
    DataRangeType range;

    // <datarange>
    // <type>PART-FIXED</type>
    // <!-- FIXED, PART-FIXED, SELECTABLE -->
    // <range>LAST_WEEK</range>
    // <!-- if used with FIXED last weeks if with PART-FIXED then last week
    // displayed with specific start date -->
    //
    // </datarange>

}
