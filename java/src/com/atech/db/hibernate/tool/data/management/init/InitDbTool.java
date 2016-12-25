package com.atech.db.hibernate.tool.data.management.init;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.Query;

import com.atech.db.hibernate.HibernateConfiguration;
import com.atech.db.hibernate.HibernateUtil;
import com.atech.db.hibernate.tool.data.management.common.ImportExportAbstract;
import com.atech.db.hibernate.transfer.RestoreFileInfo;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ImportTool - InitDbTool
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

// this one should be extended, we have several variables that need to be filled

public abstract class InitDbTool extends ImportExportAbstract
{

    DbToolInitDbRunner runner;

    /**
     * The restore_file.
     */
    protected File restore_file = null;


    /*
     * public ImportTool(Configuration cfg, int i)
     * {
     * super(cfg,i);
     * }
     */

    public InitDbTool(HibernateUtil hibernateUtil)
    {
        super(hibernateUtil);
        // this.hibernateUtil = hibernateUtil;
    }


    /**
    * Instantiates a new import tool.
    *
    * @param hib_conf the hib_conf
    */
    public InitDbTool(HibernateConfiguration hib_conf)
    {
        super(hib_conf);
        // createHibernateUtil();
    }


    /**
     * Instantiates a new import tool.
     *
     * @param hibernateConfiguration the hibernateConfiguration
     * @param res the res
     */
    public InitDbTool(HibernateConfiguration hibernateConfiguration, RestoreFileInfo res)
    {
        super(hibernateConfiguration);
        // createHibernateUtil();

        setRestoreFileInfo(res);
    }


    /**
     * Sets the restore file info.
     *
     * @param res the new restore file info
     */
    public void setRestoreFileInfo(RestoreFileInfo res)
    {
        this.statusSetMaxEntry(res.element_count);
        this.restore_file = res.file;
    }


    /**
     * Instantiates a new import tool.
     * @param runner
     */
    public InitDbTool(DbToolInitDbRunner runner)
    {
        super(runner.getHibernateUtil());
        this.runner = runner;
        // this.hibernateUtil = runner.getHibernateUtil();
        System.out.println("InitDbTool:cnstr() ");
    }


    // /**
    // * Creates the hibernate util.
    // */
    // public void createHibernateUtil()
    // {
    // this.hibernateUtil = new HibernateUtil(this.hibernate_conf,
    // HibernateConfiguration.DB_CONTEXT_FULL, false);
    // this.hibernateUtil.setSession(this.getActiveSession());
    // }

    /**
     * Gets the data from column for object.
     * 
     * @param obj the obj
     * @param column_name the column_name
     * 
     * @return the data from column for object
     */
    public String getDataFromColumnForObject(Object obj, String column_name)
    {

        String method_name = "get" + column_name.substring(0, 1).toUpperCase() + column_name.substring(1);

        // String result = null;
        Object res2 = null;
        Class<?> c = obj.getClass();
        Method method;

        try
        {
            method = c.getMethod(method_name);
            res2 = method.invoke(obj);

            if (res2 == null)
                // System.out.println("We got null");
                return "null";
        }
        catch (NoSuchMethodException e)
        {
            System.out.println(e);
        }
        catch (IllegalAccessException e)
        {
            System.out.println(e);
        }
        catch (InvocationTargetException e)
        {
            System.out.println(e);
        }

        String rr = res2.toString();

        if (rr.contains("|"))
            return rr.replaceAll("|", "$#|#$");
        else
            return rr;

    }


    /**
     * Clear existing data.
     * 
     * @param class_name the class_name
     */
    public void clearExistingData(String class_name)
    {
        Query q = getSession().createQuery("delete from " + class_name);
        q.executeUpdate();
    }

    /*
     * public static void main(String args[]) { GGCDb db = new GGCDb();
     * ExportTool tool = new ExportTool(db.getConfiguration()); tool.export(); }
     */

}
