package com.atech.db.hibernate.hdb_object;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SettingsGroupH implements Serializable {

    /** identifier field */
    private long id;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String extended;

    /** nullable persistent field */
    private String comment;

    /** full constructor */
    public SettingsGroupH(String name, String description, String extended, String comment) {
        this.name = name;
        this.description = description;
        this.extended = extended;
        this.comment = comment;
    }

    /** default constructor */
    public SettingsGroupH() {
    }

    /** minimal constructor */
    public SettingsGroupH(String name) {
        this.name = name;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtended() {
        return this.extended;
    }

    public void setExtended(String extended) {
        this.extended = extended;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SettingsGroupH) ) return false;
        SettingsGroupH castOther = (SettingsGroupH) other;
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
