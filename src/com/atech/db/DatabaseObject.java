package com.atech.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public interface DatabaseObject
{

    /**
     * getObjectUniqueId - get id of object
     * @return unique object id
     */
    String getObjectUniqueId();

    /**
     * DbAdd - Add this object to database
     * 
     * @param conn Connection object 
     * @throws Exception (HibernateException) with error
     * @return id in type of String
     */
    boolean addElement(Connection conn);

    /**
     * editElement - Edit this object in database
     * 
     * @param conn Connection object 
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean editElement(Connection conn);

    /**
     * DbDelete - Delete this object in database
     * 
     * @param conn Connection object 
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean deleteElement(Connection conn);

    /**
     * DbHasChildren - Shows if this entry has any children object, this is needed for delete
     * 
     * @param conn Connection object 
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean hasElementChildren(Connection conn);

    /**
     * DbGet - Loads this object. Id must be set.
     * 
     * @param conn Connection object 
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean getElement(Connection conn);

    /**
     * getObjectName - returns name of DatabaseObject
     * 
     * @return name of object (not Hibernate object)
     */
    String getObjectName();

    /**
     * isDebugMode - returns debug mode of object
     * 
     * @return true if object in debug mode
     */
    boolean isDebugMode();

    public boolean isNewEntry();

    public void getData(ResultSet rs) throws SQLException;

    /**
     * getAction - returns action that should be done on object
     *    0 = no action
     *    1 = add action
     *    2 = edit action
     *    3 = delete action
     *    This is used mainly for objects, contained in lists and dialogs, used for 
     *    processing by higher classes (classes calling selectors, wizards, etc...
     * 
     * @return number of action
     */
    int getAction();

}
