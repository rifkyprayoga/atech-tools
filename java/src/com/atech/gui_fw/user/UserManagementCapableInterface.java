package com.atech.gui_fw.user;

import com.atech.db.hibernate.hdb_object.UserH;

/**
 * Created by andy on 17/05/17.
 */
public interface UserManagementCapableInterface
{

    void quitApplication();


    String getHelpKeyword(String key, String defaultIfNotfound);


    void processLogin(UserH user);

}
