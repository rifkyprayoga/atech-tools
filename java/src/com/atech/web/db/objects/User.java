package com.atech.web.db.objects;

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


public class User 
{
    
    /** The id. */
    public long id;
    
    /** The username. */
    public String username;
    
    /** The password. */
    public String password;
    
    /** The real_name. */
    public String real_name;
    
    /** The description. */
    public String description;
    
    /** The user_type. */
    public int user_type;
    
    /** The user_access. */
    public long user_access;
    
    /** The user_group. */
    public int user_group;
    
    /** The debug. */
    public boolean debug = true;

    /** The default_user. */
    public boolean default_user = false;

    
    /**
     * Instantiates a new user.
     */
    public User()
    {
    }


    /*
    public User(UserH uh)
    {

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
*/
    
    




}


