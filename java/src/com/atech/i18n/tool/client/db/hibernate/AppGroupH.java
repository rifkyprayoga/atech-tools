package com.atech.i18n.tool.client.db.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AppGroupH implements Serializable {

    /** identifier field */
    private long id;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String desc;

    /** persistent field */
    private long module_id;

    /** full constructor */
    public AppGroupH(String name, String desc, long module_id) {
        this.name = name;
        this.desc = desc;
        this.module_id = module_id;
    }

    /** default constructor */
    public AppGroupH() {
    }

    /** minimal constructor */
    public AppGroupH(String name, long module_id) {
        this.name = name;
        this.module_id = module_id;
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

    public long getModule_id() {
        return this.module_id;
    }

    public void setModule_id(long module_id) {
        this.module_id = module_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AppGroupH) ) return false;
        AppGroupH castOther = (AppGroupH) other;
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
