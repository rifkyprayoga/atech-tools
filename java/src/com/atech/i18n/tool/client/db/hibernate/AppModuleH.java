package com.atech.i18n.tool.client.db.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AppModuleH implements Serializable {

    /** identifier field */
    private long id;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String desc;

    /** persistent field */
    private long app_id;

    /** full constructor */
    public AppModuleH(String name, String desc, long app_id) {
        this.name = name;
        this.desc = desc;
        this.app_id = app_id;
    }

    /** default constructor */
    public AppModuleH() {
    }

    /** minimal constructor */
    public AppModuleH(String name, long app_id) {
        this.name = name;
        this.app_id = app_id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getApp_id() {
        return this.app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AppModuleH) ) return false;
        AppModuleH castOther = (AppModuleH) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
