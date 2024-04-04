package com.atech.utils.xml;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * Created by andy on 22/02/17.
 */
public class XmlPrettyPrintUtil
{

    public static String prettyPrint(final String xml)
    {
        return prettyPrintFormat(xml, true);
    }


    public static String unPrettyPrint(final String xml)
    {
        return prettyPrintFormat(xml, false);
    }


    private static String prettyPrintFormat(String xml, boolean prettyPrintEnable)
    {

        if (StringUtils.isBlank(xml))
            return null;

        final StringWriter sw;

        try
        {
            final OutputFormat format = prettyPrintEnable ? OutputFormat.createPrettyPrint()
                    : OutputFormat.createCompactFormat();
            final org.dom4j.Document document = DocumentHelper.parseText(xml);
            sw = new StringWriter();
            final XMLWriter writer = new XMLWriter(sw, format);
            writer.write(document);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error on prettyPrintFormat: \n" + xml, e);
        }

        String xmlAsString = sw.toString();

        if (!prettyPrintEnable)
        {
            xmlAsString = xmlAsString.replaceAll("\n", "");
            xmlAsString = xmlAsString.replaceAll("\r", "");
        }

        return xmlAsString;
    }

}
