package com.atech.gui_fw.user;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.hdb_object.UserH;

/**
 * Created by andy on 17/05/17.
 */
public interface UserManagementDataInterface
{

    UserManagementCapableInterface getUserManagementCapableInstance();


    /**
     * Gets the user.
     *
     * @return the user
     */
    UserH getUser();


    void setUser(UserH us);


    /**
     * Process login.
     */
    void processLogin();


    HibernateDb getHibernateDb();

}
