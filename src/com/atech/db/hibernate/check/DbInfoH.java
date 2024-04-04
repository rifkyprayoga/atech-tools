package com.atech.db.hibernate.check;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 * @author Andy
 * 
 */

/* * @author Hibernate CodeGenerator */
public class DbInfoH implements Serializable
{

    private static final long serialVersionUID = -6444598004377736014L;

    /** identifier field */
    private long id;

    /** persistent field */
    private String key;

    /** nullable persistent field */
    private String value;

    /** nullable persistent field */
    private int type;

    /** nullable persistent field */
    private String description;

    /** full constructor 
     * @param key 
     * @param value 
     * @param type 
     * @param description */
    public DbInfoH(String key, String value, int type, String description)
    {
        this.key = key;
        this.value = value;
        this.type = type;
        this.description = description;
    }

    /** default constructor */
    public DbInfoH()
    {
    }

    /** 
     * minimal constructor 
     * @param key 
     */
    public DbInfoH(String key)
    {
        this.key = key;
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
     * @param id the new id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Gets the key.
     * 
     * @return the key
     */
    public String getKey()
    {
        return this.key;
    }

    /**
     * Sets the key.
     * 
     * @param key the new key
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public int getType()
    {
        return this.type;
    }

    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /** 
     * toString
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

    /** 
     * equals
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof DbInfoH))
            return false;
        DbInfoH castOther = (DbInfoH) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    /** 
     * hashCode
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

}
