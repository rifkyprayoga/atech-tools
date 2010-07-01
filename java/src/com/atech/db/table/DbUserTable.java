package com.atech.db.table;

import java.util.ArrayList;

import com.atech.db.hibernate.hdb_object.User;

public interface DbUserTable
{
    
    /**
     * Get Users
     * 
     * @return
     */
    public ArrayList<User> getUsers();
    
}
