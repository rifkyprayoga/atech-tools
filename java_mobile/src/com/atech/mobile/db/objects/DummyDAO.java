package com.atech.mobile.db.objects;

import java.sql.Connection;
import java.sql.ResultSet;

public class DummyDAO extends DatabaseAccessObject
{
    

    /*  full constructor */
    public DummyDAO()
    {
    }


    public String toString()
    {
        return "DummyDAO";
    }


    @Override
    public boolean dbCreate(Connection conn) throws Exception
    {
        return false;
    }

    
    public boolean dbHasChildren(Connection conn) throws Exception
    {
        return false;
    }
    
    
    
    public String getObjectName()
    {
        return "DummyDAO";
    }
    
    
    
    public String getTableName()
    {
        return "none";
    }
    
    

    @Override
    public String getObjectUniqueId()
    {
        return "fldjksfjdsfjhsdjfkhsd";
    }

    
    public String getIdColumnName()
    {
        return "id";
    }

    @Override
    public boolean dbAdd(Connection conn) throws Exception
    {
        return false;
    }

    @Override
    public boolean dbEdit(Connection conn) throws Exception
    {
        return false;
    }

    @Override
    public void dbGet(ResultSet rs) throws Exception
    {
    }
    
}
