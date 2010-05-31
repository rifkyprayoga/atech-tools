
package com.atech.web.jsp;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.utils.file.PropertiesFile;

/**
 *  This file is part of ATech Tools library.
 *  
 *  AppContextListener - Context listener for Servlet Server
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


public class AppContextListener implements ServletContextListener 
{

    Hashtable<String,AppContextAbstract> contexts = null;
    private static Log log = LogFactory.getLog(AppContextListener.class);
    
    private String[] available_parameters = { "NAME", "TITLE", "MULTILANGUAGE", "BASE_LANG", 
        "LANGUAGE", "AVAILABLE_LANGUAGES", "DB_TYPE", "MAIN_CLASS", "JDBC_DRIVER", "JDBC_URL", 
        "JDBC_USERNAME", "JDBC_PASSWORD"
    };
    
    
    
    
    public void contextInitialized(ServletContextEvent event) 
    {
        System.out.println("contextInitialized");
        initalizeContexts();
        //HibernatePISUtil.getSessionFactory(); // Just call the static initializer of that class    
    }

    public void contextDestroyed(ServletContextEvent event) 
    {
        if (contexts!=null)
        {
            for(Enumeration<AppContextAbstract> en = this.contexts.elements(); en.hasMoreElements(); )
            {
                en.nextElement().disposeContext();
            }
        }
    }
    
    /**
     * Initalize Contexts
     */
    public void initalizeContexts()
    {
    	System.out.println("initalizeContexts() .1");
        log.debug("initalizeContexts()");
    	System.out.println("initalizeContexts() .2");
        
        File f = new File("../conf/ATechFramework.config");
        
        System.out.println("f: " + f.getAbsolutePath());
        
        if (f.exists())
        {
        	System.out.println("Configuration file found.");
        	log.info("Configuration file found.");
            PropertiesFile pf = new PropertiesFile("../conf/ATechFramework.config");
            pf.readFile();
            
            if (pf.containsKey("CONTEXTS_COUNT"))
            {
                int cntx_cnt = Integer.parseInt(pf.get("CONTEXTS_COUNT"));
                System.out.println("Found " + cntx_cnt + " contexts !");
                Hashtable<String, Hashtable<String,String>> parameters = loadParameters(cntx_cnt, pf);
                
                this.createContexts(parameters);
                
            }
            else
                log.error("Invalid config file !");
            	
        }
        else
        {
        	System.out.println("No configuration file found. No contexts loaded !");
            log.warn("No configuration file found. No contexts loaded !");
        }
    }
    
    
    private Hashtable<String, Hashtable<String,String>> loadParameters(int count, PropertiesFile pf)
    {
        Hashtable<String, Hashtable<String,String>> params = new Hashtable<String,Hashtable<String,String>>();
        
        for(int i=1; i<=count; i++)
        {
            Hashtable<String,String> ht = new Hashtable<String,String>(); 
            
            params.put("" + i, new Hashtable<String,String>());
            
            for(int j=0; j<this.available_parameters.length; j++)
            {
                String key = "CONTEXT_" + i + "_" + this.available_parameters[j];
                
                if (pf.containsKey(key))
                {
                    ht.put(this.available_parameters[j], pf.get(key));
                }
                
            }
            
            log.debug("Found " + pf.size() + " parameters for context " + i + " [" + ht.get("NAME") + "]");
            params.put("" + i, ht);
        }
        
        return params;
        
    }
    
    
    private void createContexts(Hashtable<String, Hashtable<String,String>> params)
    {
        
        for(Enumeration<String> en=params.keys(); en.hasMoreElements(); )
        {
            String key = en.nextElement();
            
            Hashtable<String,String> p1 = params.get(key);
            
            try
            {
                Class<?> cls = Class.forName(p1.get("MAIN_CLASS"));
                
                Method[] mths = cls.getDeclaredMethods();
                
                Method method=null;
                for(int i=0; i<mths.length; i++)
                {
                    if (mths[i].toString().contains("createContext"))
                    {
                        method = mths[i];
                    }
                }
                
                if (method==null)
                {
                    log.error("Main context class [" + p1.get("MAIN_CLASS") + "] doesn't contain required static method createInstance() !!!");
                    continue;
                }
                
                //Method method = cls.getMethod("createInstance", java.util.Hashtable.class); //cls); //new Hashtable<String,String>()); //cls); //new Class[0]);
                method.invoke(null, p1); //cls, new Object[0]);
            }
            catch(Exception ex)
            {
                System.out.println("Problem with init... Ex.: " + ex);
                ex.printStackTrace();
            }
            
        }
        
    }
    
    
    
}
