package com.atech.db.datalayer2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by andy on 08.09.15.
 */
public interface DbAwareObjectInterface
{

    boolean isInsert();


    PreparedStatement getDeleteStatement(Connection conn) throws SQLException;


    PreparedStatement getInsertStatement(Connection conn) throws SQLException;


    PreparedStatement getUpdateStatement(Connection conn) throws SQLException;


    Map<String, DbAwareObjectInterface> getChildrenData();


    String getTableName();


    String getTableColumns();


    void getDbObject(ResultSet resultSet) throws SQLException;
}
