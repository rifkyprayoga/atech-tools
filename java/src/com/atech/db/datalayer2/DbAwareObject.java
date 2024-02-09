package com.atech.db.datalayer2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Map;

import com.atech.utils.data.ATechDate;

/**
 * Created by andy on 08.09.15.
 */
public abstract class DbAwareObject implements DbAwareObjectInterface
{

    protected Long id;
    protected long changed;


    // protected String tableColumns;

    public boolean isInsert()
    {
        return (id == null);
    }


    public PreparedStatement getDeleteStatement(Connection conn) throws SQLException
    {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE " + this.getTableName() + " WHERE id=?");
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }


    public long setGetChanged()
    {
        this.changed = ATechDate.getATDateTimeFromGC(new GregorianCalendar(), ATechDate.FORMAT_DATE_AND_TIME_S);
        return this.changed;
    }


    public java.sql.Date getSqlDate(GregorianCalendar gc)
    {
        if (gc == null)
        {
            return null;
        }

        return new java.sql.Date(gc.getTimeInMillis());
    }


    public Map<String, DbAwareObjectInterface> getChildrenData()
    {
        return null;
    }


    public PreparedStatement getInsertStatement(Connection conn) throws SQLException
    {
        PreparedStatement preparedStatement = conn.prepareStatement(getInsertSql());
        setPreparedStatementParameters(preparedStatement, false);

        return preparedStatement;
    }


    public PreparedStatement getUpdateStatement(Connection conn) throws SQLException
    {
        PreparedStatement preparedStatement = conn.prepareStatement(getUpdateSql());
        setPreparedStatementParameters(preparedStatement, true);

        return preparedStatement;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public abstract void setPreparedStatementParameters(PreparedStatement preparedStatement, boolean isUpdate)
            throws SQLException;


    public abstract String getInsertSql();


    public abstract String getUpdateSql();


    public abstract String getTableColumns();


    protected GregorianCalendar getGregorianCalendarFromDbDate(java.sql.Date date)
    {
        if (date == null)
        {
            return null;
        }

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        return gc;
    }

}
