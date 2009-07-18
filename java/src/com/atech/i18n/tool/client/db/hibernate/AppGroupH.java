package com.atech.i18n.tool.client.db.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

// TODO: Auto-generated Javadoc
/* * @author Hibernate CodeGenerator */
/**
 * 
 * The Class AppGroupH.
 */
public class AppGroupH implements Serializable
{

    private static final long serialVersionUID = -6313103663875626809L;

    /** identifier field */
    private long id;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String desc;

    /** persistent field */
    private long module_id;

    /** 
     * full constructor
     *  
     * @param name 
     * @param desc 
     * @param module_id 
     */
    public AppGroupH(String name, String desc, long module_id)
    {
        this.name = name;
        this.desc = desc;
        this.module_id = module_id;
    }

    /** default constructor */
    public AppGroupH()
    {
    }

    /** minimal constructor 
     * @param name 
     * @param module_id */
    public AppGroupH(String name, long module_id)
    {
        this.name = name;
        this.module_id = module_id;
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
     * @param name the new name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the desc.
     * 
     * @return the desc
     */
    public String getDesc()
    {
        return this.desc;
    }

    /**
     * Sets the desc.
     * 
     * @param desc the new desc
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * Gets the module_id.
     * 
     * @return the module_id
     */
    public long getModule_id()
    {
        return this.module_id;
    }

    /**
     * Sets the module_id.
     * 
     * @param module_id the new module_id
     */
    public void setModule_id(long module_id)
    {
        this.module_id = module_id;
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
        if (!(other instanceof AppGroupH))
            return false;
        AppGroupH castOther = (AppGroupH) other;
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
