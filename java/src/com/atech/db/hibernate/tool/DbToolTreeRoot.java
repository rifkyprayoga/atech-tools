package com.atech.db.hibernate.tool;

import java.util.ArrayList;

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

    public static final int ROOT_SINGLE = 1;
    public static final int ROOT_MULTIPLE = 2;

    public int type = ROOT_SINGLE;

    public ArrayList<DbToolApplicationInterface> m_appGroup = null;
    public DbToolApplicationInterface m_app = null;

//    public Hashtable m_appGroup_table = null;
    public ArrayList<DatabaseSettings> m_app_list = null;

    public DbToolAccess m_da = null;


    public DbToolTreeRoot(DbToolAccess da) 
    {

        m_da = da;
	//m_appGroup = m_da.getApplicationDatas();

/*
	    m_foodGroups = db.getFoodGroups();
	    Iterator it = m_foodGroups.iterator();

	    m_foodDescByGroup = new Hashtable();

	    while (it.hasNext())
	    {
		FoodGroup fg = (FoodGroup)it.next();
		m_foodDescByGroup.put(""+fg.getId(), new ArrayList());
	    }

	    
	    ArrayList list = db.getFoodDescriptions();
	    it = list.iterator();

	    while (it.hasNext())
	    {
		FoodDescription fd = (FoodDescription)it.next();

		ArrayList al = (ArrayList)m_foodDescByGroup.get(""+fd.getFood_group_id());
		al.add(fd);
	    }
*/
        
    }

    public void loadData()
    {
        m_appGroup = m_da.getApplicationDatas();
        type = ROOT_MULTIPLE;
    }

    public void loadData(DbToolApplicationInterface intr)
    {
    	//m_appGroup = new ArrayList();
    	//m_appGroup.add(intr);
    
    	m_app = intr;
    	m_app_list = getListOfDatabases(intr);
    
    	type = ROOT_SINGLE;
    }


    public ArrayList<DatabaseSettings> getListOfDatabases(DbToolApplicationInterface intr)
    {
        intr.loadConfig();
        return m_da.getArrayOfDatabaseSettings(intr.getAllDatabases());
            //new ArrayList(intr.getAllDatabases().values());

    	//m_da.loadConfig(intr);
    	//return m_da.getListOfDatabases();
    }


    



    public String toString()
    {
    	if (type==ROOT_SINGLE)
    	    return m_app.getApplicationName();
    	else
                return m_da.m_i18n.getMessage("HIBERNATE_DATABASE_APPLICATION");
    }


}
