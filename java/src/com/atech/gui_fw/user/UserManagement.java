package com.atech.gui_fw.user;

import java.util.ArrayList;
import java.util.List;

import com.atech.db.hibernate.hdb_object.UserH;
import com.atech.utils.ATDataAccessAbstract;

/**
 * Created by andy on 17/05/17.
 */
public class UserManagement
{

    ATDataAccessAbstract dataAccess;
    UserManagementDataInterface userManagementData;

    List<UserH> allUsers = null;
    boolean isEnabled;
    private boolean enabled;


    public UserManagement(UserManagementDataInterface userManagementData, boolean isEnabled)
    {
        this.dataAccess = (ATDataAccessAbstract) userManagementData;
        this.userManagementData = userManagementData;
    }


    /**
     * Gets the all users.
     *
     * @return the all users
     */
    public List<UserH> getAllUsers()
    {
        if (this.allUsers == null)
        {
            this.allUsers = new ArrayList<UserH>();
        }

        // load users
        List<UserH> allTypedHibernateData = userManagementData.getHibernateDb().getAllTypedHibernateData(UserH.class);

        // add default if missing

        return this.allUsers;
    }


    public void loginAction()
    {
        LoginDialog loginDialog = new LoginDialog(dataAccess, userManagementData.getUserManagementCapableInstance());
    }


    public void logoutAction()
    {
        userManagementData.setUser(null);
        userManagementData.processLogin();

        try
        {
            Thread.sleep(4000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        userManagementData.setUser(new UserH("ggc", "ggc", 1));

        // LoginDialog loginDialog = new LoginDialog(dataAccess,
        // userManagementData.getUserManagementCapableInstance());
    }


    public boolean isEnabled()
    {
        return enabled;
    }


    public boolean isAutoLoginEnabled()
    {
        return false;
    }
}
