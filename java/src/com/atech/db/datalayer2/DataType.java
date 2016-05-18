package com.atech.db.datalayer2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by andy on 08.09.15.
 */
public class DataType extends DbAwareObject
{

    String key;
    String sourceTable;
    String sourceColumn;
    String description;
    String primitiveDataType;
    String dataMask;

    private static String insertSql = "INSERT INTO data_types (key, source_table, source_column, description, primitive_data_type, data_mask, changed)"
            + " VALUES ( ?, ?, ?, ?, ?, ?, ?)";

    private static String updateSql = "UPDATE data_types "
            + "   SET key=?, source_table=?, source_column=?, description=?, "
            + "       primitive_data_type=?, data_mask=?, changed=?" + " WHERE id=?";

    private static String columns = "id, key, source_table, source_column, description, primitive_data_type, data_mask, changed";


    public String getTableName()
    {
        return "data_types";
    }


    public void setPreparedStatementParameters(PreparedStatement preparedStatement, boolean isUpdate)
            throws SQLException
    {
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, sourceTable);
        preparedStatement.setString(3, sourceColumn);
        preparedStatement.setString(4, description);
        preparedStatement.setString(5, primitiveDataType);
        preparedStatement.setString(6, dataMask);
        preparedStatement.setLong(7, setGetChanged());

        if (isUpdate)
        {
            preparedStatement.setLong(8, getId());
        }
    }


    @Override
    public String getInsertSql()
    {
        return insertSql;
    }


    @Override
    public String getUpdateSql()
    {
        return updateSql;
    }


    @Override
    public String getTableColumns()
    {
        return columns;
    }


    public void getDbObject(ResultSet resultSet) throws SQLException
    {
        this.id = resultSet.getLong("id");
        this.key = resultSet.getString("key");
        this.sourceTable = resultSet.getString("source_table");
        this.sourceColumn = resultSet.getString("source_column");
        this.description = resultSet.getString("description");
        this.primitiveDataType = resultSet.getString("primitive_data_type");
        this.dataMask = resultSet.getString("data_mask");
        this.changed = resultSet.getLong("changed");
    }
}
