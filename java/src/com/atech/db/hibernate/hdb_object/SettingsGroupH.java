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

public class SettingsGroupH implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8603144341725985328L;

    /** identifier field. */
    private long id;

    /** persistent field. */
    private String name;

    /** nullable persistent field. */
    private String description;

    /** nullable persistent field. */
    private String extended;

    /** nullable persistent field. */
    private String comment;

    /**
     * full constructor.
     * 
     * @param name
     *            the name
     * @param description
     *            the description
     * @param extended
     *            the extended
     * @param comment
     *            the comment
     */
    public SettingsGroupH(String name, String description, String extended, String comment)
    {
        this.name = name;
        this.description = description;
        this.extended = extended;
        this.comment = comment;
    }

    /**
     * default constructor.
     */
    public SettingsGroupH()
    {
    }

    /**
     * minimal constructor.
     * 
     * @param name
     *            the name
     */
    public SettingsGroupH(String name)
    {
        this.name = name;
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
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name)
    {
        this.name = name;
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
        if (!(other instanceof SettingsGroupH))
            return false;
        SettingsGroupH castOther = (SettingsGroupH) other;
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
