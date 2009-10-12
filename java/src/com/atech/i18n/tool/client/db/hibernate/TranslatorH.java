package com.atech.i18n.tool.client.db.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

// TODO: Auto-generated Javadoc
/** @author Hibernate CodeGenerator */
public class TranslatorH implements Serializable
{

    private static final long serialVersionUID = 8159339847319776317L;

    /** identifier field */
    private long id;

    /** persistent field */
    private String username;

    /** nullable persistent field */
    private String password;

    /** nullable persistent field */
    private int special_tag;

    /** full constructor 
     * @param username 
     * @param password 
     * @param special_tag */
    public TranslatorH(String username, String password, int special_tag)
    {
        this.username = username;
        this.password = password;
        this.special_tag = special_tag;
    }

    /** default constructor */
    public TranslatorH()
    {
    }

    /** minimal constructor 
     * @param username */
    public TranslatorH(String username)
    {
        this.username = username;
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
     * @param username the new username
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
     * @param password the new password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets the special_tag.
     * 
     * @return the special_tag
     */
    public int getSpecial_tag()
    {
        return this.special_tag;
    }

    /**
     * Sets the special_tag.
     * 
     * @param special_tag the new special_tag
     */
    public void setSpecial_tag(int special_tag)
    {
        this.special_tag = special_tag;
    }

    /** 
     * toString
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

    /** 
     * equals
     */
    public boolean equals(Object other)
    {
        if (!(other instanceof TranslatorH))
            return false;
        TranslatorH castOther = (TranslatorH) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    /** 
     * hashCode
     */
    public int hashCode()
    {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

}
