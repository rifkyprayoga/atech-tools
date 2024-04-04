package com.atech.db.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class HibernateObject implements Serializable
{

    private static final long serialVersionUID = -6481206879274837142L;


    public abstract long getId();


    public abstract void setId(long id);


    /**
     * Create Hash Code
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }


    /**
     * To String
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " [id=" + getId() + "]";
    }

}
