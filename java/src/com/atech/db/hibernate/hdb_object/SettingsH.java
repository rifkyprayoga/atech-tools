package com.atech.db.hibernate.hdb_object;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SettingsH implements Serializable 
{

    private static final long serialVersionUID = -386629412670531476L;

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

    /** persistent field */
    private int person_id;

    /** full constructor 
     * @param key 
     * @param value 
     * @param type 
     * @param description 
     * @param person_id */
    public SettingsH(String key, String value, int type, String description, int person_id) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.description = description;
        this.person_id = person_id;
    }

    /** default constructor */
    public SettingsH() {
    }

    /** minimal constructor 
     * @param key 
     * @param person_id */
    public SettingsH(String key, int person_id) {
        this.key = key;
        this.person_id = person_id;
    }

    /**
     * Get Id
     * 
     * @return
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Set Id
     * 
     * @param id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Get Key 
     * 
     * @return key value
     */
    public String getKey() 
    {
        return this.key;
    }

    /**
     * Set Key
     *  
     * @param key parameter
     */
    public void setKey(String key) 
    {
        this.key = key;
    }

    /**
     * Get Value 
     * 
     * @return value value
     */
    public String getValue() 
    {
        return this.value;
    }

    /**
     * Set Extended
     *  
     * @param value parameter
     */
    public void setValue(String value) 
    {
        this.value = value;
    }

    /**
     * Get Type 
     * 
     * @return type value
     */
    public int getType() 
    {
        return this.type;
    }

    /**
     * Set Type
     *  
     * @param type parameter
     */
    public void setType(int type) 
    {
        this.type = type;
    }

    /**
     * Get Description
     * 
     * @return description parameter
     */
    public String getDescription() 
    {
        return this.description;
    }

    /**
     * Set Description
     * 
     * @param description parameter
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }

    /**
     * Get Person Id
     * 
     * @return person id parameter
     */
    public int getPerson_id()
    {
        return this.person_id;
    }

    /**
     * Set Person Id
     * 
     * @param person_id parameter
     */
    public void setPerson_id(int person_id)
    {
        this.person_id = person_id;
    }

    /** 
     * Custom equals implementation
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) 
    {
        if ( !(other instanceof SettingsH) ) return false;
        SettingsH castOther = (SettingsH) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    /**
     * To String
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() 
    {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
    
    
    /**
     * Create Hash Code
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() 
    {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

}
