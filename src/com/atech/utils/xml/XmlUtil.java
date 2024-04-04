package com.atech.utils.xml;

import java.io.File;
import java.io.StringReader;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtil
{

    protected Document document;
    private static final Logger LOG = LoggerFactory.getLogger(XmlUtil.class);


    public XmlUtil(String xml)
    {
        try
        {
            openXmlFile(xml);
        }
        catch (Exception ex)
        {
            LOG.error("Error opening Xml file. Ex.: " + ex, ex);
        }

    }


    /**
     * Open Xml File
     * 
     * @param file
     * @return
     * @throws DocumentException
     */
    public Document openXmlFile(File file) throws DocumentException
    {
        SAXReader reader = new SAXReader();

        document = reader.read(file);
        return document;
    }


    /**
     * Open Xml Data
     * 
     * @param xml_data
     *  
     * @return
     * @throws DocumentException
     */
    public Document openXmlData(String xml_data) throws DocumentException
    {
        SAXReader reader = new SAXReader();

        document = reader.read(new StringReader(xml_data));
        return document;
    }


    /**
     * Open Xml File
     * @param xml_text 
     * 
     * @param file
     * @return
     * @throws DocumentException
     */
    public Document openXmlFile(String xml_text) throws DocumentException
    {
        SAXReader reader = new SAXReader();

        document = reader.read(new StringReader(xml_text));
        return document;
    }


    /**
     * Get Node
     * 
     * @param tag_path tag path
     * @return Node object
     */
    public Node getNode(String tag_path)
    {
        return document.selectSingleNode(tag_path);
    }


    /**
     * Get Element
     * 
     * @param tag_path tag path
     * @return Element object
     */
    public Element getElement(String tag_path)
    {
        return (Element) getNode(tag_path);
    }


    /**
     * Return List of nodes from path
     * 
     * @param tag_path tag path
     * @return List<Nodes> instance with nodes
     */
    @SuppressWarnings("unchecked")
    public List<Node> getNodes(String tag_path)
    {
        List<Node> nodes = document.selectNodes(tag_path);
        return nodes;
    }


    public static String getNodeValueString(Node parent, String node_name)
    {
        return getNodeValueString(parent, node_name, "");
    }


    public static String getNodeValueString(Node parent_node, String node_name, String default_value)
    {
        Node nd = parent_node.selectSingleNode(node_name);

        if (nd == null)
            return default_value;
        else
            return nd.getText();
    }


    public static long getNodeValueLong(Node parent, String node_name)
    {
        return getNodeValueLong(parent, node_name, 0L);
    }


    public static long getNodeValueLong(Node parent_node, String node_name, long default_value)
    {
        Node nd = parent_node.selectSingleNode(node_name);

        if (nd == null)
            return default_value;
        else
        {
            try
            {
                return Long.parseLong(nd.getText());
            }
            catch (Exception ex)
            {
                return default_value;
            }
        }
    }

}
