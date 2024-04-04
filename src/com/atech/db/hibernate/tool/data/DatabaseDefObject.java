package com.atech.db.hibernate.tool.data;

// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 */

public class DatabaseDefObject
{

    /**
     * The name.
     */
    public String name = null;

    /**
     * The driver.
     */
    public String driver = null;

    /**
     * The url.
     */
    public String url = null;

    /**
     * The port.
     */
    public String port = null;

    /**
     * The dialect.
     */
    public String dialect = null;

    /**
     * The short_dialect.
     */
    public String short_dialect = null;


    /**
     * Instantiates a new database def object.
     * 
     * @param name the name
     * @param driver the driver
     * @param url the url
     * @param port the port
     * @param dialect the dialect
     * @param sh_dialect the sh_dialect
     */
    public DatabaseDefObject(String name, String driver, String url, String port, String dialect, String sh_dialect)
    {
        this.name = name;
        this.driver = driver;
        this.url = url;
        this.port = port;
        this.dialect = dialect;
        this.short_dialect = sh_dialect;
    }


    /** 
     * toString
     */
    @Override
    public String toString()
    {
        return name;
    }

}
