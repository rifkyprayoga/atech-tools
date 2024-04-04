package com.atech.db.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.atech.graphics.dialogs.selector.ColumnSorter;
import com.atech.graphics.dialogs.selector.SelectableInterface;

public abstract class DatabaseObjectHibernateSelectableAbstract implements DatabaseObjectHibernate, SelectableInterface
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

    // ---
    // --- Column sorting
    // ---

    private ColumnSorter columnSorter = null;

    /**
     * setColumnSorter - sets class that will help with column sorting
     * 
     * @param cs ColumnSorter instance
     */
    public void setColumnSorter(ColumnSorter cs)
    {
        this.columnSorter = cs;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *      is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */
    public int compareTo(SelectableInterface o)
    {
        return this.columnSorter.compareObjects(this, o);
    }

}
