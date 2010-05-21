package com.atech.update.config;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.w3c.dom.DOMImplementation;

public class UpdateConfigurationXml
{

    UpdateConfiguration config = null;
    
    public UpdateConfigurationXml()
    {
    }
    
    
    
    protected Document document;


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
        return (Element)getNode(tag_path);
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
    
    
    
    
    public void readXml()
    {
        
    }
    
    
    public void writeXml(UpdateConfiguration uconfig)
    {
        //SAXWriter sw = new SAXWriter();
        
        //Document doc = new Document();
        
        //XmlWriter writ = new XmlWriter();
        
        //writ.
        //XMLDocument doc = new XMLDocument();
        
        try
        {
        
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation impl = builder.getDOMImplementation();

            org.w3c.dom.Document doc = impl.createDocument(null,null,null);
        
        
            org.w3c.dom.Element p1, p2, p3, p0;
        
            p0 = doc.createElement("update_file");
            doc.appendChild(p0);
            
            p1 = doc.createElement("product");
            p0.appendChild(p1);
        
            getElementWithValue(doc, p1, "id", "" + uconfig.product_id);
            getElementWithValue(doc, p1, "root_path", "" + uconfig.root);


            p2 = doc.createElement("version");
            p1.appendChild(p2);
            
            getElementWithValue(doc, p2, "id", "" + uconfig.version_numeric);
            getElementWithValue(doc, p2, "name", uconfig.version_name);
            getElementWithValue(doc, p2, "description", uconfig.version_description);
            
            
            if (uconfig.custom_apps.size()>0)
            {
                p1 = doc.createElement("custom_apps");
                p0.appendChild(p1);

                for(Enumeration<String> en = uconfig.custom_apps.keys(); en.hasMoreElements(); )
                {
                    String key = en.nextElement();
                    
                    ComponentCustomApp cca = uconfig.custom_apps.get(key);
                    
                    p2 = doc.createElement("custom_app");
                    p1.appendChild(p2);
                    
                    getElementWithValue(doc, p2, "id", "" + key);
                    getElementWithValue(doc, p2, "id_name", "" + cca.id);
                    getElementWithValue(doc, p2, "filename", cca.filename);
                    getElementWithValue(doc, p2, "main_class", cca.main_class);
                    getElementWithValue(doc, p2, "special_parameters", cca.special_parameters);
                    getElementWithValue(doc, p2, "binary_needed", "" + cca.binary_needed);
                    getElementWithValue(doc, p2, "jdbc_needed", "" + cca.needs_jdbc);
                    
                }
                
                
            }
            
            
            
//            p2 = doc.createElement("custom_apps");
//            p1.appendChild(p2);
            
            
            
            p1 = doc.createElement("db");
            p0.appendChild(p1);

            
            p2 = doc.createElement("db_apps");
            p1.appendChild(p2);
            
            

            p1 = doc.createElement("component_groups");
            p0.appendChild(p1);

            for(Enumeration<ComponentGroup> en = uconfig.groups.elements(); en.hasMoreElements(); )
            {
                p2 = doc.createElement("group");
                p1.appendChild(p2);

                ComponentGroup gr = en.nextElement();
                
                getElementWithValue(doc, p2, "id", "" + gr.id);
                getElementWithValue(doc, p2, "name", gr.name);
            }

            
            
            p1 = doc.createElement("components");
            p0.appendChild(p1);
        

        
        
            for(int i=0; i<uconfig.components.size(); i++)
            {
                org.w3c.dom.Element c = doc.createElement("component");

                ComponentEntry ce = uconfig.components.get(i);

                System.out.println("" + ce.name);
            
                getElementWithValue(doc, c, "id", "" + ce.id);
                getElementWithValue(doc, c, "group", "" + ce.group);
                getElementWithValue(doc, c, "name", ce.name);
                getElementWithValue(doc, c, "version", ce.version);
                getElementWithValue(doc, c, "version_num", "" + ce.version_num);
                getElementWithValue(doc, c, "root_dir", "" + ce.root_dir);
                getElementWithValue(doc, c, "files", "" + ce.files);
                
                if (ce.platform_specific)
                {
                    getElementWithValue(doc, c, "platform_specific", "true");
                    getElementWithValue(doc, c, "platform_specific_type", "" + ce.platform_specific_type);
                    getElementWithValue(doc, c, "platform_supported", "" + ce.platform_supported);
                    
                    
                    if (ce.platform_specific_type==ComponentEntry.PLATFORM_SPECIFIC_FULL)
                    {
                        org.w3c.dom.Element oses = doc.createElement("platform_specific_oses");
                        c.appendChild(oses);
                        
                        
                        for(Enumeration<String> en = ce.files_java_specific.keys(); en.hasMoreElements(); )
                        {
                            String key = en.nextElement();
                            
                            org.w3c.dom.Element os = doc.createElement("platform_specific_os");
                            oses.appendChild(os);
                            
                            getElementWithValue(doc, os, "os_name", key);
                            getElementWithValue(doc, os, "os_files", "" + ce.files_java_specific.get(key));
                        }
                    }
            }
            
            p1.appendChild(c);
            
        }
        
        
        
        
        
        
        
        
        
        //    d.write(new FileWriter("test_create.xml"));
        
        
        
            
             // transform the Document into a String
        DOMSource domSource = new DOMSource(doc);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            //transformer.setOutputProperty
//                (OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            java.io.StringWriter sw = new java.io.StringWriter();
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);
            String xml = sw.toString();
  //          return xml;
            
            
            FileWriter fw = new FileWriter("test_create.xml");
            fw.write(xml);
            fw.close();
            
            
        }
        catch(Exception ex)
        {
            System.out.println("Error writing.");
        }
        
        
        //sw.write(d);
        
        
        
    }
    

    private org.w3c.dom.Element getElementWithValue(org.w3c.dom.Document doc, org.w3c.dom.Element parent, String name, String value)
    {
        org.w3c.dom.Element el = doc.createElement(name);
        //el.setNodeValue(value);
        el.setTextContent(value);
        
        parent.appendChild(el);
        
        return el;
    }
    
    
    public void writeConfig()
    {
        
    }
    
    
    
    
    
    
}
