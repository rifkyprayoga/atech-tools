package com.atech.i18n.tool.client.db.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TranslatorH implements Serializable {

    /** identifier field */
    private long id;

    /** persistent field */
    private String username;

    /** nullable persistent field */
    private String password;

    /** nullable persistent field */
    private int special_tag;

    /** full constructor */
    public TranslatorH(String username, String password, int special_tag) {
        this.username = username;
        this.password = password;
        this.special_tag = special_tag;
    }

    /** default constructor */
    public TranslatorH() {
    }

    /** minimal constructor */
    public TranslatorH(String username) {
        this.username = username;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSpecial_tag() {
        return this.special_tag;
    }

    public void setSpecial_tag(int special_tag) {
        this.special_tag = special_tag;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TranslatorH) ) return false;
        TranslatorH castOther = (TranslatorH) other;
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
