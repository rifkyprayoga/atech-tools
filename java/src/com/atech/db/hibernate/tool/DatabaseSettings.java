package com.atech.db.hibernate.tool;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Hashtable;

// TODO: Auto-generated Javadoc
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

public class DatabaseSettings implements Comparable<DatabaseSettings> // <DatabaseSettings>
{

    /**
     * The number.
     */
    public int number = 0;

    /**
     * The db_name.
     */
    public String db_name = null;

    /**
     * The name.
     */
    public String name = null;

    /**
     * The driver.
     */
    public String driver = null;

    /**
     * The driver_class.
     */
    public String driver_class = null;

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
     * The username.
     */
    public String username = null;

    /**
     * The password.
     */
    public String password = null;

    /**
     * The is default.
     */
    public boolean isDefault = false;

    /**
     * The settings.
     */
    public Hashtable<String, String> settings = null;

    /*
     * public String hostname = null;
     * public String url = null;
     * public String port = null;
     * public String database = null;
     */

    /**
     * Instantiates a new database settings.
     */
    public DatabaseSettings()
    {
        settings = new Hashtable<String, String>();
    }

    /**
     * Instantiates a new database settings.
     * 
     * @param name the name
     * @param driver the driver
     * @param url the url
     * @param port the port
     * @param dialect the dialect
     */
    public DatabaseSettings(String name, String driver, String url, String port, String dialect)
    {
        this.name = name;
        this.driver = driver;
        this.url = url;
        this.port = port;
        this.dialect = dialect;

        settings = new Hashtable<String, String>();
    }

    /**
     * Write.
     * 
     * @param bw the bw
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void write(BufferedWriter bw) throws IOException
    {
        bw.write("\n#\n# Database #" + this.number + " - " + this.name + "\n#\n");
        bw.write("DB" + this.number + "_CONN_NAME=" + this.name + "\n");
        bw.write("DB" + this.number + "_DB_NAME=" + this.db_name + "\n");
        bw.write("DB" + this.number + "_CONN_DRIVER_CLASS=" + this.driver_class + "\n");
        bw.write("DB" + this.number + "_CONN_URL=" + this.url + "\n");
        bw.write("DB" + this.number + "_CONN_USERNAME=" + this.username + "\n");
        bw.write("DB" + this.number + "_CONN_PASSWORD=" + this.password + "\n");
        bw.write("DB" + this.number + "_HIBERNATE_DIALECT=" + this.dialect + "\n");
    }

    /** 
     * toString
     */
    @Override
    public String toString()
    {
        return number + " - " + name;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param   ds the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *		is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */
    public int compareTo(DatabaseSettings ds)
    {
        if (this.number < ds.number)
            return -1;
        else if (this.number == ds.number)
            return 0;
        else
            return 1;

    }

}
