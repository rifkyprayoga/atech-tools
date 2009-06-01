package com.atech.i18n.tool.client.db.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AppTranslationTemplateH implements Serializable {

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
    private long changed;

    /** full constructor */
    public AppTranslationTemplateH(long module_id, long app_id, long lang_id, String keyword, String value, long changed) {
        this.module_id = module_id;
        this.app_id = app_id;
        this.lang_id = lang_id;
        this.keyword = keyword;
        this.value = value;
        this.changed = changed;
    }

    /** default constructor */
    public AppTranslationTemplateH() {
    }

    /** minimal constructor */
    public AppTranslationTemplateH(long module_id, long app_id, long lang_id) {
        this.module_id = module_id;
        this.app_id = app_id;
        this.lang_id = lang_id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModule_id() {
        return this.module_id;
    }

    public void setModule_id(long module_id) {
        this.module_id = module_id;
    }

    public long getApp_id() {
        return this.app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public long getLang_id() {
        return this.lang_id;
    }

    public void setLang_id(long lang_id) {
        this.lang_id = lang_id;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getChanged() {
        return this.changed;
    }

    public void setChanged(long changed) {
        this.changed = changed;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AppTranslationTemplateH) ) return false;
        AppTranslationTemplateH castOther = (AppTranslationTemplateH) other;
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
