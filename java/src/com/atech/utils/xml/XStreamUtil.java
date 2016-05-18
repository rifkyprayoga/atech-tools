package com.atech.utils.xml;

import com.thoughtworks.xstream.XStream;

/**
 * Created by andy on 15.02.16.
 */
public class XStreamUtil
{

    private static XStream xStreamInstance = new XStream();


    public static Object getObjectFromString(String xml)
    {
        return xStreamInstance.fromXML(xml);
    }


    public static String getStringFromObject(Object input)
    {
        return xStreamInstance.toXML(input);
    }

}
