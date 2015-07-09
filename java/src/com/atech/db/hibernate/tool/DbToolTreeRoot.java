package com.atech.db.hibernate.tool;

import java.util.ArrayList;

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

public class DbToolTreeRoot
{

    /**
     * The Constant ROOT_SINGLE.
     */
    public static final int ROOT_SINGLE = 1;

    /**
     * The Constant ROOT_MULTIPLE.
     */
    public static final int ROOT_MULTIPLE = 2;

    /**
     * The type.
     */
    public int type = ROOT_SINGLE;

    /**
     * The m_app group.
     */
    public ArrayList<DbToolApplicationInterface> m_appGroup = null;

    /**
     * The m_app.
     */
    public DbToolApplicationInterface m_app = null;

    // public Hashtable m_appGroup_table = null;
    /**
    * The m_app_list.
    */
    public ArrayList<DatabaseSettings> m_app_list = null;

    /**
     * The dataAccess.
     */
    public DbToolAccess m_da = null;


    /**
     * Instantiates a new db tool tree root.
     * 
     * @param da the da
     */
    public DbToolTreeRoot(DbToolAccess da)
    {

        m_da = da;
        // m_appGroup = dataAccess.getApplicationDatas();

        /*
         * m_foodGroups = db.getFoodGroups();
         * Iterator it = m_foodGroups.iterator();
         * m_foodDescByGroup = new Hashtable();
         * while (it.hasNext())
         * {
         * FoodGroup fg = (FoodGroup)it.next();
         * m_foodDescByGroup.put(""+fg.getId(), new ArrayList());
         * }
         * ArrayList list = db.getFoodDescriptions();
         * it = list.iterator();
         * while (it.hasNext())
         * {
         * FoodDescription fd = (FoodDescription)it.next();
         * ArrayList al =
         * (ArrayList)m_foodDescByGroup.get(""+fd.getFood_group_id());
         * al.add(fd);
         * }
         */

    }


    /**
     * Load data.
     */
    public void loadData()
    {
        m_appGroup = m_da.getApplicationDatas();
        type = ROOT_MULTIPLE;
    }


    /**
     * Load data.
     * 
     * @param intr the intr
     */
    public void loadData(DbToolApplicationInterface intr)
    {
        // m_appGroup = new ArrayList();
        // m_appGroup.add(intr);

        m_app = intr;
        m_app_list = getListOfDatabases(intr);

        type = ROOT_SINGLE;
    }


    /**
     * Gets the list of databases.
     * 
     * @param intr the intr
     * 
     * @return the list of databases
     */
    public ArrayList<DatabaseSettings> getListOfDatabases(DbToolApplicationInterface intr)
    {
        intr.loadConfig();
        return m_da.getArrayOfDatabaseSettings(intr.getAllDatabases());
        // new ArrayList(intr.getAllDatabases().values());

        // dataAccess.loadConfig(intr);
        // return dataAccess.getListOfDatabases();
    }


    /** 
     * toString
     */
    @Override
    public String toString()
    {
        if (type == ROOT_SINGLE)
            return m_app.getApplicationName();
        else
            return m_da.m_i18n.getMessage("HIBERNATE_DATABASE_APPLICATION");
    }

}
