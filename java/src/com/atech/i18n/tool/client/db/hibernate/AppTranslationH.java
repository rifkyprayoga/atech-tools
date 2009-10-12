package com.atech.i18n.tool.client.db.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

// TODO: Auto-generated Javadoc
/** @author Hibernate CodeGenerator */
public class AppTranslationH implements Serializable
{

    private static final long serialVersionUID = -7187510250961007165L;

    /** identifier field */
    private long id;

    /** persistent field */
    private long module_id;

    /** persistent field */
    private long app_id;

    /** persistent field */
    private long lang_id;

    /** nullable persistent field */
    private String keyword;

    /** nullable persistent field */
    private String value;

    /** nullable persistent field */
    private int status;

    /** nullable persistent field */
    private long changed;

    /** full constructor 
     * @param module_id 
     * @param app_id 
     * @param lang_id 
     * @param keyword 
     * @param value 
     * @param status 
     * @param changed */
    public AppTranslationH(long module_id, long app_id, long lang_id, String keyword, String value, int status, long changed)
    {
        this.module_id = module_id;
        this.app_id = app_id;
        this.lang_id = lang_id;
        this.keyword = keyword;
        this.value = value;
        this.status = status;
        this.changed = changed;
    }

    /** default constructor */
    public AppTranslationH()
    {
    }

    /** minimal constructor 
     * @param module_id 
     * @param app_id 
     * @param lang_id */
    public AppTranslationH(long module_id, long app_id, long lang_id)
    {
        this.module_id = module_id;
        this.app_id = app_id;
        this.lang_id = lang_id;
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
     * Gets the app_id.
     * 
     * @return the app_id
     */
    public long getApp_id()
    {
        return this.app_id;
    }

    /**
     * Sets the app_id.
     * 
     * @param app_id the new app_id
     */
    public void setApp_id(long app_id)
    {
        this.app_id = app_id;
    }

    /**
     * Gets the lang_id.
     * 
     * @return the lang_id
     */
    public long getLang_id()
    {
        return this.lang_id;
    }

    /**
     * Sets the lang_id.
     * 
     * @param lang_id the new lang_id
     */
    public void setLang_id(long lang_id)
    {
        this.lang_id = lang_id;
    }

    /**
     * Gets the keyword.
     * 
     * @return the keyword
     */
    public String getKeyword()
    {
        return this.keyword;
    }

    /**
     * Sets the keyword.
     * 
     * @param keyword the new keyword
     */
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
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
     * Gets the status.
     * 
     * @return the status
     */
    public int getStatus()
    {
        return this.status;
    }

    /**
     * Sets the status.
     * 
     * @param status the new status
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * Gets the changed.
     * 
     * @return the changed
     */
    public long getChanged()
    {
        return this.changed;
    }

    /**
     * Sets the changed.
     * 
     * @param changed the new changed
     */
    public void setChanged(long changed)
    {
        this.changed = changed;
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
        if (!(other instanceof AppTranslationH))
            return false;
        AppTranslationH castOther = (AppTranslationH) other;
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
