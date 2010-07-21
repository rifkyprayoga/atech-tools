package com.atech.db.hibernate.hdb_object;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserH implements Serializable {

    /** identifier field */
    private long id;

    /** persistent field */
    private String username;

    /** persistent field */
    private String password;

    /** nullable persistent field */
    private String real_name;

    /** nullable persistent field */
    private String real_desc;

    /** persistent field */
    private int user_type;

    /** nullable persistent field */
    private long user_type_id;

    /** nullable persistent field */
    private int user_access;

    /** nullable persistent field */
    private String extended;

    /** nullable persistent field */
    private String comment;

    /** full constructor */
    public UserH(String username, String password, String real_name, String real_desc, int user_type, long user_type_id, int user_access, String extended, String comment) {
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.real_desc = real_desc;
        this.user_type = user_type;
        this.user_type_id = user_type_id;
        this.user_access = user_access;
        this.extended = extended;
        this.comment = comment;
    }

    /** default constructor */
    public UserH() {
    }

    /** minimal constructor */
    public UserH(String username, String password, int user_type) {
        this.username = username;
        this.password = password;
        this.user_type = user_type;
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

    public String getReal_name() {
        return this.real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_desc() {
        return this.real_desc;
    }

    public void setReal_desc(String real_desc) {
        this.real_desc = real_desc;
    }

    public int getUser_type() {
        return this.user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public long getUser_type_id() {
        return this.user_type_id;
    }

    public void setUser_type_id(long user_type_id) {
        this.user_type_id = user_type_id;
    }

    public int getUser_access() {
        return this.user_access;
    }

    public void setUser_access(int user_access) {
        this.user_access = user_access;
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
        if ( !(other instanceof UserH) ) return false;
        UserH castOther = (UserH) other;
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
