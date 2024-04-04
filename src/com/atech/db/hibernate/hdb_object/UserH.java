package com.atech.db.hibernate.hdb_object;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.atech.db.hibernate.HibernateObject;

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

public class UserH extends HibernateObject
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2052793622522047440L;

    /** identifier field. */
    private long id;

    /** persistent field. */
    private String username;

    /** persistent field. */
    private String password;

    /** nullable persistent field. */
    private String real_name;

    /** nullable persistent field. */
    private String real_desc;

    /** persistent field. */
    private int user_type;

    /** nullable persistent field. */
    private long user_type_id;

    /** nullable persistent field. */
    private int user_access;

    /** nullable persistent field. */
    private String extended;

    /** nullable persistent field. */
    private String comment;


    /**
     * full constructor.
     * 
     * @param username
     *            the username
     * @param password
     *            the password
     * @param real_name
     *            the real_name
     * @param real_desc
     *            the real_desc
     * @param user_type
     *            the user_type
     * @param user_type_id
     *            the user_type_id
     * @param user_access
     *            the user_access
     * @param extended
     *            the extended
     * @param comment
     *            the comment
     */
    public UserH(String username, String password, String real_name, String real_desc, int user_type, long user_type_id,
            int user_access, String extended, String comment)
    {
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.real_desc = real_desc;
        this.user_type = user_type;
        this.user_type_id = user_type_id;
        this.user_access = user_access;
        this.extended = extended;
        this.comment = comment;
    }


    /**
     * default constructor.
     */
    public UserH()
    {
    }


    /**
     * minimal constructor.
     * 
     * @param username
     *            the username
     * @param password
     *            the password
     * @param user_type
     *            the user_type
     */
    public UserH(String username, String password, int user_type)
    {
        this.username = username;
        this.password = password;
        this.user_type = user_type;
    }


    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId()
    {
        return this.id;
    }


    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(long id)
    {
        this.id = id;
    }


    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername()
    {
        return this.username;
    }


    /**
     * Sets the username.
     * 
     * @param username
     *            the new username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }


    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword()
    {
        return this.password;
    }


    /**
     * Sets the password.
     * 
     * @param password
     *            the new password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }


    /**
     * Gets the real_name.
     * 
     * @return the real_name
     */
    public String getReal_name()
    {
        return this.real_name;
    }


    /**
     * Sets the real_name.
     * 
     * @param real_name
     *            the new real_name
     */
    public void setReal_name(String real_name)
    {
        this.real_name = real_name;
    }


    /**
     * Gets the real_desc.
     * 
     * @return the real_desc
     */
    public String getReal_desc()
    {
        return this.real_desc;
    }


    /**
     * Sets the real_desc.
     * 
     * @param real_desc
     *            the new real_desc
     */
    public void setReal_desc(String real_desc)
    {
        this.real_desc = real_desc;
    }


    /**
     * Gets the user_type.
     * 
     * @return the user_type
     */
    public int getUser_type()
    {
        return this.user_type;
    }


    /**
     * Sets the user_type.
     * 
     * @param user_type
     *            the new user_type
     */
    public void setUser_type(int user_type)
    {
        this.user_type = user_type;
    }


    /**
     * Gets the user_type_id.
     * 
     * @return the user_type_id
     */
    public long getUser_type_id()
    {
        return this.user_type_id;
    }


    /**
     * Sets the user_type_id.
     * 
     * @param user_type_id
     *            the new user_type_id
     */
    public void setUser_type_id(long user_type_id)
    {
        this.user_type_id = user_type_id;
    }


    /**
     * Gets the user_access.
     * 
     * @return the user_access
     */
    public int getUser_access()
    {
        return this.user_access;
    }


    /**
     * Sets the user_access.
     * 
     * @param user_access
     *            the new user_access
     */
    public void setUser_access(int user_access)
    {
        this.user_access = user_access;
    }


    /**
     * Gets the extended.
     * 
     * @return the extended
     */
    public String getExtended()
    {
        return this.extended;
    }


    /**
     * Sets the extended.
     * 
     * @param extended
     *            the new extended
     */
    public void setExtended(String extended)
    {
        this.extended = extended;
    }


    /**
     * Gets the comment.
     * 
     * @return the comment
     */
    public String getComment()
    {
        return this.comment;
    }


    /**
     * Sets the comment.
     * 
     * @param comment
     *            the new comment
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof UserH))
            return false;
        UserH castOther = (UserH) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

}
