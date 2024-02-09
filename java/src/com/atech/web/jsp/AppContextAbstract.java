package com.atech.web.jsp;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//import org.hibernate.Query;
//import org.hibernate.Session;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.web.db.AppDbWebAbstract;
import com.atech.web.util.DataAccessWeb;
import com.atech.web.util.I18nWebControl;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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

public abstract class AppContextAbstract implements AppContextInterface
{

    // Implemented Context must contain two static method and Context must be
    // static class.
    // Static method are: createInstance(Hashtable<String,String>) and
    // getInstance()

    /**
     * App Context: Db
     */
    public static final int APP_CONTEXT_DB = 1;

    /**
     * App Context: Hibernate
     */
    public static final int APP_CONTEXT_DB_HIBERNATE = 2;

    private static final Logger LOG = LoggerFactory.getLogger(AppContextAbstract.class);

    protected AppDbWebAbstract dbWeb;
    protected Hashtable<String, I18nWebControl> possible_i18n;
    protected String default_lang = "si";

    protected DataAccessWeb daw;
    protected int type = 1;

    protected Hashtable<String, String> params = null;
    protected boolean multiple_langauges = false;

    /*
     * static
     * {
     * initContext();
     * }
     */


    /*
     * public void initContext()
     * {
     * try
     * {
     * System.out.println("HibernatePISUtil static");
     * // Create the SessionFactory from hibernate.cfg.xml
     * sessionFactory = new Configuration().configure().buildSessionFactory();
     * daw = DataAccessWeb.getInstance();
     * dbWeb = new PISDbWeb(sessionFactory, daw);
     * i18n = new I18nWebControl(dbWeb.getWebLanguage());
     * daw.setI18nWebControl(i18n);
     * dbWeb.setI18nWebControl(i18n);
     * }
     * catch (Throwable ex)
     * {
     * // Make sure you LOG the exception, as it might be swallowed
     * System.err.println("Initial SessionFactory creation failed." + ex);
     * throw new ExceptionInInitializerError(ex);
     * }
     * }
     */

    public int getType()
    {
        return this.type;
    }


    // public void initContext();

    public AppDbWebAbstract getDb()
    {
        return this.dbWeb;
    }


    public I18nWebControl getI18n(String lang)
    {
        if (this.possible_i18n.containsKey(lang))
            return this.possible_i18n.get(lang);
        else
            return this.possible_i18n.get(this.default_lang);
    }


    public I18nWebControl getI18n()
    {
        return this.possible_i18n.get(this.default_lang);
    }


    public DataAccessWeb getDataAccessWeb()
    {
        if (daw == null)
        {
            daw = DataAccessWeb.createInstance(this.possible_i18n, this.default_lang);
        }

        return daw;
    }


    public boolean hasMultipleLanguageChanger()
    {
        return this.multiple_langauges;
    }


    protected void initLanguage()
    {
        // FIXME - single support for now
        possible_i18n = new Hashtable<String, I18nWebControl>();

        if (this.params.containsKey("LANGUAGE"))
        {
            this.default_lang = this.params.get("LANGUAGE");
        }
        else
        {
            LOG.error("Context: Default language parameter not found. Possible problem in configuration !");
        }

        if (this.params.containsKey("BASE_LANG"))
        {
            possible_i18n.put(default_lang, new I18nWebControl(default_lang, this.params.get("BASE_LANG")));
        }
        else
        {
            LOG.error("Context: Base Lang parameter not found. Possible problem in configuration !");
        }

    }


    public void disposeContext()
    {
        this.dbWeb.destroyDb();
    }

}
