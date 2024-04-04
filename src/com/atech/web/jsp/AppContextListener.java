package com.atech.web.jsp;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    Hashtable<String, AppContextAbstract> contexts = null;
    private static final Logger LOG = LoggerFactory.getLogger(AppContextListener.class);

    private String[] available_parameters = { "NAME", "TITLE", "MULTILANGUAGE", "BASE_LANG", "LANGUAGE",
                                              "AVAILABLE_LANGUAGES", "DB_TYPE", "MAIN_CLASS", "JDBC_DRIVER", "JDBC_URL",
                                              "JDBC_USERNAME", "JDBC_PASSWORD" };


    public void contextInitialized(ServletContextEvent event)
    {
        initalizeContexts();
    }


    public void contextDestroyed(ServletContextEvent event)
    {
        LOG.debug("contextDestroyed");

        if (contexts != null)
        {
            for (Enumeration<AppContextAbstract> en = this.contexts.elements(); en.hasMoreElements();)
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
        LOG.debug("initalizeContexts()");

        File f = new File("../conf/ATechFramework.config");

        if (f.exists())
        {
            LOG.info("Configuration file found.");
            PropertiesFile pf = new PropertiesFile("../conf/ATechFramework.config");
            pf.readFile();

            if (pf.containsKey("CONTEXTS_COUNT"))
            {
                int cntx_cnt = Integer.parseInt(pf.get("CONTEXTS_COUNT"));
                LOG.debug("Found " + cntx_cnt + " contexts !");
                Hashtable<String, Hashtable<String, String>> parameters = loadParameters(cntx_cnt, pf);

                this.createContexts(parameters);

            }
            else
            {
                LOG.error("Invalid config file !");
            }

        }
        else
        {
            LOG.warn("No configuration file found. No contexts loaded !");
        }
    }


    private Hashtable<String, Hashtable<String, String>> loadParameters(int count, PropertiesFile pf)
    {
        Hashtable<String, Hashtable<String, String>> params = new Hashtable<String, Hashtable<String, String>>();

        for (int i = 1; i <= count; i++)
        {
            Hashtable<String, String> ht = new Hashtable<String, String>();

            params.put("" + i, new Hashtable<String, String>());

            for (String available_parameter : this.available_parameters)
            {
                String key = "CONTEXT_" + i + "_" + available_parameter;

                if (pf.containsKey(key))
                {
                    ht.put(available_parameter, pf.get(key));
                }

            }

            LOG.debug("Found " + pf.size() + " parameters for context " + i + " [" + ht.get("NAME") + "]");
            params.put("" + i, ht);
        }

        return params;

    }


    private void createContexts(Hashtable<String, Hashtable<String, String>> params)
    {
        contexts = new Hashtable<String, AppContextAbstract>();

        for (Enumeration<String> en = params.keys(); en.hasMoreElements();)
        {
            String key = en.nextElement();

            Hashtable<String, String> p1 = params.get(key);

            try
            {

                Class<?> cls = Class.forName(p1.get("MAIN_CLASS"));

                Method[] mths = cls.getDeclaredMethods();

                Method method = null;
                for (Method mth : mths)
                {
                    if (mth.toString().contains("createContext"))
                    {
                        method = mth;
                    }
                }

                if (method == null)
                {
                    LOG.error("Main context class [" + p1.get("MAIN_CLASS")
                            + "] doesn't contain required static method createInstance() !!!");
                    continue;
                }

                // Method method = cls.getMethod("createInstance",
                // java.util.Hashtable.class); //cls); //new
                // Hashtable<String,String>()); //cls); //new Class[0]);
                AppContextAbstract aca = (AppContextAbstract) method.invoke(null, p1); // cls,
                                                                                       // new
                                                                                       // Object[0]);

                this.contexts.put(key, aca);
            }
            catch (Exception ex)
            {
                LOG.error("Problem with init... Ex.: " + ex, ex);
            }
        }
    }

}
