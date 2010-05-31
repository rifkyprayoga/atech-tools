package com.atech.update.config;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.w3c.dom.DOMImplementation;

import com.atech.utils.ATDataAccess;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ComponentCustomApp - Custom Application definition class
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/


public class UpdateConfigurationXml
{
    private static Log log = LogFactory.getLog(UpdateConfigurationXml.class);
    UpdateConfiguration config = null;
    ATDataAccess m_da = ATDataAccess.getInstance(); 
    protected Document document;

    
    /**
     * Constructor
     */
    public UpdateConfigurationXml()
    {
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
    
    
    
    
    /**
     * Read Xml File
     * 
     * @param file
     * @param uconfig
     */
    public void readXml(File file, UpdateConfiguration uconfig)
    {
        try
        {
            this.openXmlFile(file);
            actualReadingOfXml(uconfig);
        }
        catch(Exception ex)
        {
            log.error("Error reading Xml file. Ex: " + ex, ex);
        }
    }
    

    /**
     * Actual Reading Of Xml
     * 
     * @param uconfig
     */
    @SuppressWarnings("unchecked")
    public void actualReadingOfXml(UpdateConfiguration uconfig)
    {
        
        uconfig.product_id = this.getNode("update_file/product/id").getText();
        uconfig.root = this.getNode("update_file/product/root_path").getText();
        
        uconfig.version_numeric = Integer.parseInt(this.getNode("update_file/product/version/id").getText()); 
        uconfig.version_name = this.getNode("update_file/product/version/name").getText();
        uconfig.version_description = this.getNode("update_file/product/version/description").getText();
        
//        System.out.println(uconfig.version_numeric);
//        System.out.println(uconfig.version_name);
//        System.out.println(uconfig.version_description);

        List<Node> nodes = this.getNodes("update_file/custom_apps/custom_app");

        for(int i=0; i<nodes.size(); i++)
        {
            Element el = (Element)nodes.get(i);
            
            ComponentCustomApp cca = new ComponentCustomApp();
            
            //cca.id = "" + (i+1); 
            cca.id = el.element("id").getText(); 
            cca.filename = el.element("filename").getText(); 
            cca.main_class = el.element("main_class").getText(); 
            cca.special_parameters = el.element("special_parameters").getText(); 
            cca.binary_needed = m_da.getBooleanValue(el.element("binary_needed").getText()); 
            cca.needs_jdbc = m_da.getBooleanValue(el.element("jdbc_needed").getText()); 

            uconfig.custom_apps.put("" + cca.id, cca);
        }
        
        
        nodes = this.getNodes("update_file/db");
        
        uconfig.db_enabled = m_da.getBooleanValue(this.getNode("update_file/db/enabled").getText());
        uconfig.db_version_required = Integer.parseInt(this.getNode("update_file/db/required_version").getText());
        uconfig.db_config_class = this.getNode("update_file/db/config_class").getText();
        uconfig.db_update_site = this.getNode("update_file/db/update_site").getText();
        uconfig.db_update_site_version = Integer.parseInt(this.getNode("update_file/db/update_site_version").getText());
        
        
        nodes = this.getNodes("update_file/db/db_apps/db_app");

        for(int i=0; i<nodes.size(); i++)
        {
            Element el = (Element)nodes.get(i);
            
            ComponentDbApp dba = new ComponentDbApp();
            
            dba.name = el.element("name").getText(); 
            dba.enabled = m_da.getBooleanValue(el.element("enabled").getText()); 
            dba.app_class = el.element("class").getText(); 

            uconfig.db_apps.put(dba.name, dba);
        
            //System.out.println(dba);
            
        }
        
        
        
        nodes = this.getNodes("update_file/component_groups/group");

        for(int i=0; i<nodes.size(); i++)
        {
            Element el = (Element)nodes.get(i);
            
            ComponentGroup cg = new ComponentGroup();
            
            cg.id = Integer.parseInt(el.element("id").getText()); 
            cg.name = el.element("name").getText(); 
            
            uconfig.groups.put("" + cg.id, cg);
            
            System.out.println(cg);
        }
        
        
        
        nodes = this.getNodes("update_file/components/component");

        for(int i=0; i<nodes.size(); i++)
        {
            Element el = (Element)nodes.get(i);
            
            
            ComponentEntry ce = new ComponentEntry();

            System.out.println("" + nodes.size());

            ce.id = Integer.parseInt(el.element("id").getText());
            ce.group = Integer.parseInt(el.element("group").getText());
            ce.name = el.element("name").getText();
            ce.version = el.element("version").getText();
            ce.version_num = Integer.parseInt(el.element("version_num").getText());
            ce.root_dir = el.element("root_dir").getText();
            ce.files = el.element("files").getText();

            //System.out.println("" + ce.name);
            
            
            if (el.element("platform_specific")!=null)
            {
                ce.platform_specific = m_da.getBooleanValue(el.element("platform_specific").getText());
                
                ce.platform_specific_type = Integer.parseInt(el.element("platform_specific_type").getText());
                ce.platform_supported = el.element("platform_supported").getText();
                
                if (ce.platform_specific_type==ComponentEntry.PLATFORM_SPECIFIC_FULL)
                {
                    Element el2 = el.element("platform_specific_oses");
                    Iterator<Element> it = el2.elementIterator();
                    
                    while(it.hasNext())
                    {
                        Element el3 = it.next();
                        
                        ce.files_java_specific.put(el3.element("os_name").getText(), el3.element("os_files").getText());
                    }
                }
                
            }
            
            
//            System.out.println("" + uconfig.groups + "\n" + uconfig.components + "\n" + uconfig.components_ht);
            
            
            uconfig.groups.get(""+ce.group).addChild(ce);
            uconfig.components.add(ce);
            uconfig.components_ht.put(ce.name, ce);

            
            System.out.println(ce);
            
        }
    }
    
    
    
    /**
     * Write Xml
     * 
     * @param uconfig
     */
    public void writeXml(UpdateConfiguration uconfig)
    {
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
            
            
            p1 = doc.createElement("db");
            p0.appendChild(p1);
            
            getElementWithValue(doc, p1, "enabled", "" + uconfig.db_enabled);
            getElementWithValue(doc, p1, "required_version", "" + uconfig.db_version_required);
            getElementWithValue(doc, p1, "config_class", "" + uconfig.db_config_class);
            getElementWithValue(doc, p1, "update_site", "" + uconfig.db_update_site);
            getElementWithValue(doc, p1, "update_site_version", "" + uconfig.db_update_site_version);
            
            
            p2 = doc.createElement("db_apps");
            p1.appendChild(p2);

            for(Enumeration<ComponentDbApp> en = uconfig.db_apps.elements(); en.hasMoreElements(); )
            {
                p3 = doc.createElement("db_app");
                p2.appendChild(p3);

                ComponentDbApp db_app = en.nextElement();
                
                getElementWithValue(doc, p3, "name", db_app.name);
                getElementWithValue(doc, p3, "enabled", "" + db_app.enabled);
                getElementWithValue(doc, p3, "class", db_app.app_class);
            }
            

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
        
            
            // transform the Document into a String
            DOMSource domSource = new DOMSource(doc);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
                //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            java.io.StringWriter sw = new java.io.StringWriter();
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);
            String xml = sw.toString();
            
            FileWriter fw = new FileWriter("test_create.xml");
            fw.write(xml);
            fw.close();
            
        }
        catch(Exception ex)
        {
            System.out.println("Error writing.");
        }
        
    }
    

    private org.w3c.dom.Element getElementWithValue(org.w3c.dom.Document doc, org.w3c.dom.Element parent, String name, String value)
    {
        org.w3c.dom.Element el = doc.createElement(name);
        el.setTextContent(value);
        parent.appendChild(el);
        return el;
    }
    
    
    
}
