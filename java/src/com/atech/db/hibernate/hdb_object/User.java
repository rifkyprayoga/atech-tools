package com.atech.db.hibernate.hdb_object;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atech.db.hibernate.DatabaseObjectHibernate;
import com.atech.utils.ATDataAccessAbstract;

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



public class User extends UserH implements DatabaseObjectHibernate
{

    private static final long serialVersionUID = 5502992319462010434L;

    /**
     * Variable: debug
     */
    public boolean debug = true;

    /**
     * Variable: default user
     */
    public boolean default_user = false;
    
    
    ATDataAccessAbstract m_da = null;

    /**
     * Constructor
     * 
     * @param da
     */
    public User(ATDataAccessAbstract da)
    {
        this.m_da = da;
    }


    /**
     * Constructor
     * 
     * @param uh
     * @param da
     */
    public User(UserH uh, ATDataAccessAbstract da)
    {
        this.m_da = da;
        this.setId(uh.getId());
        this.setUsername(uh.getUsername());
        this.setPassword(uh.getPassword());
        this.setReal_desc(uh.getReal_desc());
        this.setReal_name(uh.getReal_name());
        this.setUser_type(uh.getUser_type());
        this.setUser_type_id(uh.getUser_type_id());
        this.setUser_access(uh.getUser_access());

        //setSearchContext();

    }

    public String toString()
    {
        return this.getUsername() + "(" + this.getReal_name() + ") = " + m_da.user_types[this.getUser_type()];
    }


    //---
    //---  DatabaseObjectHibernate
    //---


    /**
     * DbAdd - Add this object to database
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return id in type of String
     */
    public String DbAdd(Session sess) throws Exception
    {

        Transaction tx = sess.beginTransaction();

        UserH uh = new UserH();

        uh.setId(this.getId());
        uh.setUsername(this.getUsername());
        uh.setPassword(this.getPassword());
        uh.setReal_desc(this.getReal_desc());
        uh.setReal_name(this.getReal_name());
        uh.setUser_type(this.getUser_type());
        uh.setUser_type_id(this.getUser_type_id());
        uh.setUser_access(this.getUser_access());

        Long id = (Long)sess.save(uh);
        tx.commit();

        return ""+id.longValue();

    }



    /**
     * DbEdit - Edit this object in database
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbEdit(Session sess) throws Exception
    {

        Transaction tx = sess.beginTransaction();

        UserH uh = (UserH)sess.get(UserH.class, this.getId());

        uh.setId(this.getId());
        uh.setPassword(this.getPassword());
        uh.setReal_desc(this.getReal_desc());
        uh.setReal_name(this.getReal_name());
        uh.setUser_type(this.getUser_type());
        uh.setUser_type_id(this.getUser_type_id());
        uh.setUser_access(this.getUser_access());
        uh.setUsername(this.getUsername());

        sess.update(uh);
        tx.commit();

        return true;

    }



    /**
     * DbDelete - Delete this object in database
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbDelete(Session sess) throws Exception
    {

        Transaction tx = sess.beginTransaction();

        UserH uh = (UserH)sess.get(UserH.class, this.getId());
        sess.delete(uh);
        tx.commit();

        return true;

    }



    /**
     * DbHasChildren - Shows if this entry has any children object, this is needed for delete
     * 
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbHasChildren(Session sess) throws Exception
    {
        //DataAccess.notImplemented("Street::DbHasChildren");
        return false;
    }



    /**
     * DbGet - Loads this object. Id must be set.
     * 
     * 
     * @param sess Hibernate Session object
     * @throws Exception (HibernateException) with error
     * @return true if action done or Exception if not
     */
    public boolean DbGet(Session sess) throws Exception
    {

        UserH uh = (UserH)sess.get(UserH.class, new Long(this.getId()));

        this.setId(uh.getId());
        this.setPassword(uh.getPassword());
        this.setReal_desc(uh.getReal_desc());
        this.setReal_name(uh.getReal_name());
        this.setUser_type(uh.getUser_type());
        this.setUsername(uh.getUsername());
        this.setUser_type_id(uh.getUser_type_id());
        this.setUser_access(uh.getUser_access());

        //setSearchContext();

        return true;
    }



    /**
     * getObjectName - returns name of DatabaseObject
     * 
     * @return name of object (not Hibernate object)
     */
    public String getObjectName()
    {
        return "User";
    }



    /**
     * isDebugMode - returns debug mode of object
     * 
     * @return true if object in debug mode
     */
    public boolean isDebugMode()
    {
        return debug;
    }


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
    public int getAction()
    {
        return 0;
    }


    /**
     * getObjectUniqueId - get id of object
     * @return unique object id
     */
    public String getObjectUniqueId()
    {
        // TODO Auto-generated method stub
        return null;
    }


}


