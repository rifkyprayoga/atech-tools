package com.atech.db.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class DatabaseObjectHibernateAbstract implements DatabaseObjectHibernate
{

    public abstract Object getHibernateObject();

    public abstract String getObjectUniqueId();

    public abstract void setMainId(long id);

    public abstract long getMainId();

    public String DbAdd(Session sess) throws Exception
    {
        try
        {
            Transaction tx = sess.beginTransaction();

            Long id = (Long) sess.save(this.getHibernateObject());
            tx.commit();
            this.setMainId(id.longValue());

            return "" + id.longValue();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public boolean DbEdit(Session sess) throws Exception
    {
        try
        {
            Transaction tx = sess.beginTransaction();

            sess.update(this.getHibernateObject());
            tx.commit();

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean DbDelete(Session sess) throws Exception
    {
        try
        {
            if (this.DbHasChildren(sess))
                return false;

            Transaction tx = sess.beginTransaction();

            sess.delete(this.getHibernateObject());
            tx.commit();
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }

    public abstract boolean DbHasChildren(Session sess) throws Exception;

    public abstract boolean DbGet(Session sess) throws Exception;

    public abstract String getObjectName();

    public abstract boolean isDebugMode();

    public abstract int getAction();

}
