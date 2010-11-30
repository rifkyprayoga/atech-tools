package com.atech.db.hibernate.hdb_object;

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

public class SettingsH implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5772880295733204593L;

    /** identifier field. */
    private long id;

    /** persistent field. */
    private String key;

    /** nullable persistent field. */
    private String value;

    /** nullable persistent field. */
    private int type;

    /** nullable persistent field. */
    private String description;

    /** persistent field. */
    private long group_id;

    /** persistent field. */
    private long person_id;

    /** nullable persistent field. */
    private String extended;

    /** nullable persistent field. */
    private String comment;

    /**
     * full constructor.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     * @param type
     *            the type
     * @param description
     *            the description
     * @param group_id
     *            the group_id
     * @param person_id
     *            the person_id
     * @param extended
     *            the extended
     * @param comment
     *            the comment
     */
    public SettingsH(String key, String value, int type, String description, long group_id, long person_id,
            String extended, String comment)
    {
        this.key = key;
        this.value = value;
        this.type = type;
        this.description = description;
        this.group_id = group_id;
        this.person_id = person_id;
        this.extended = extended;
        this.comment = comment;
    }

    /**
     * default constructor.
     */
    public SettingsH()
    {
    }

    /**
     * minimal constructor.
     * 
     * @param key
     *            the key
     * @param group_id
     *            the group_id
     * @param person_id
     *            the person_id
     */
    public SettingsH(String key, long group_id, long person_id)
    {
        this.key = key;
        this.group_id = group_id;
        this.person_id = person_id;
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
     * @param key
     *            the new key
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
     * @param value
     *            the new value
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
     * @param type
     *            the new type
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
     * @param description
     *            the new description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets the group_id.
     * 
     * @return the group_id
     */
    public long getGroup_id()
    {
        return this.group_id;
    }

    /**
     * Sets the group_id.
     * 
     * @param group_id
     *            the new group_id
     */
    public void setGroup_id(long group_id)
    {
        this.group_id = group_id;
    }

    /**
     * Gets the person_id.
     * 
     * @return the person_id
     */
    public long getPerson_id()
    {
        return this.person_id;
    }

    /**
     * Sets the person_id.
     * 
     * @param person_id
     *            the new person_id
     */
    public void setPerson_id(long person_id)
    {
        this.person_id = person_id;
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
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other)
    {
        if (!(other instanceof SettingsH))
            return false;
        SettingsH castOther = (SettingsH) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

}
