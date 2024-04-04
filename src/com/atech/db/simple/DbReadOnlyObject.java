package com.atech.db.simple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 24.9.2020.
 */
public abstract class DbReadOnlyObject<T> {

    public abstract T getObject(ResultSet resultSet) throws SQLException;


    public List<T> getObjectList(ResultSet resultSet) throws SQLException {

        List<T> outList = new ArrayList<>();

        while (resultSet.next()) {
            outList.add(getObject(resultSet));
        }

        return outList;
    }

} 