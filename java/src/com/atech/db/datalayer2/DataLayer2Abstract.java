package com.atech.db.datalayer2;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataLayer2Abstract
{

    private static final Logger LOG = LoggerFactory.getLogger(DataLayer2Abstract.class);

    protected Connection connection;


    protected DataLayer2Abstract()
    {
        String url = null;

        try
        {
            Class.forName(getDbDriverClassName()); // "org.postgresql.Driver");

            url = getDbUrl();
            // "jdbc:postgresql://localhost/cdc_server?user=cdc&password=cdc";
            connection = DriverManager.getConnection(url);

        }
        catch (Exception ex)
        {
            LOG.error("Error creating connection to Server [url=" + url + "]. Exception: " + ex.getMessage(), ex);
        }

    }


    // public static CDCDataLayer getInstance()
    // {
    // if (sCDCDataLayer == null)
    // {
    // sCDCDataLayer = new CDCDataLayer();
    // }
    //
    // return sCDCDataLayer;
    // }

    public Connection getConnection()
    {
        if (connection == null)
        {
            LOG.error("Connection is null. Throwing exception.");
            throw new RuntimeException("Connection is null.");
        }

        return connection;
    }


    public abstract String getDbUrl();


    public abstract String getDbDriverClassName();

    // Users

    // public User getUser(long userId)
    // {
    // return (User) getObjectInternal(new User(), " WHERE id=" + userId, "[id="
    // + userId + "]");
    // }
    //
    // public User getUser(String userName)
    // {
    // return (User) getObjectInternal(new User(), " WHERE username='" +
    // userName + "'", "[username=" + userName + "]");
    // }
    //
    // public Long addEditUser(User user)
    // {
    // addEditObject(user);
    //
    // if (user.getId() == null)
    // {
    // user = getUser(user.getUsername());
    //
    // if (user == null)
    // {
    // return null;
    // }
    // }
    //
    // return user.getId();
    // }

    //
    // public void deleteAnimal(long animalId)
    // {
    // Animal a = new Animal();
    // a.setId(animalId);
    //
    // deleteObject(a);
    // }

    // // FIXME
    // private List<Animal> getAnimalsInternal(String sqlWhere, String
    // debugParameter)
    // {
    // List<Animal> list = new ArrayList<Animal>();
    // String sql = null;
    // try
    // {
    // Animal animal = new Animal();
    //
    // sql = "select " + animal.getTableColumns() + " from " +
    // animal.getTableName() + " a " + sqlWhere;
    //
    // ResultSet resultSet =
    // getConnection().createStatement().executeQuery(sql);
    //
    // while (resultSet.next())
    // {
    // Animal a = new Animal();
    //
    // a.getDbObject(resultSet);
    // list.add(a);
    // }
    // }
    // catch (SQLException ex)
    // {
    // LOG.debug("Sql: " + sql);
    // LOG.error("Error getting Animals " + debugParameter + ". Exception: " +
    // ex.getMessage(), ex);
    // }
    //
    // return list;
    // }


    // helper methods

    private void addEditObject(DbAwareObject object)
    {
        PreparedStatement ps = null;

        try
        {

            if (object.isInsert())
            {
                ps = object.getInsertStatement(getConnection());
                ps.execute();

                LOG.debug(String.format("Object %s was added.", object.getClass().getSimpleName()));

            }
            else
            {
                ps = object.getUpdateStatement(getConnection());
                ps.execute();

                LOG.debug(
                    String.format("Object %s was edited [id=%s].", object.getClass().getSimpleName(), object.getId()));
            }

        }
        catch (Exception ex)
        {
            logError(object, ps, ex, "add/edit");
        }

    }


    private void logError(DbAwareObject object, PreparedStatement ps, Exception ex, String operation)
    {
        String className = object.getClass().getSimpleName();
        if (operation.equals("add/edit"))
        {
            operation = object.isInsert() ? "add" : "edit";
        }

        if (ps != null)
        {
            LOG.debug("Prepared statement: " + ps.toString());
        }
        else
        {
            LOG.debug("Prepared statement is null for " + className);
        }

        LOG.error("Error " + operation + " of object " + className + //
                (object.getId() != null ? " [id=" + object.getId() + "]" : "") + //
                ". Exception: " + ex,
            ex);
    }


    private void deleteObject(DbAwareObject object)
    {
        PreparedStatement ps = null;

        try
        {
            ps = object.getDeleteStatement(getConnection());
            ps.execute();

        }
        catch (Exception ex)
        {
            logError(object, ps, ex, "delete");
        }
    }

    // private Animal getUserInternal(String sqlWhere, String debugParameter)
    // {
    // try
    // {
    // User animal = new User();
    //
    // String sql = "select " + animal.getTableColumns() + " from " +
    // animal.getTableName() + " a " + sqlWhere;
    //
    // ResultSet resultSet =
    // getConnection().createStatement().executeQuery(sql);
    //
    // if (resultSet.next())
    // {
    // animal.getDbObject(resultSet);
    // return animal;
    // }
    // } catch (SQLException ex)
    // {
    // LOG.error("Error getting Animal " + debugParameter + ". Exception: " +
    // ex.getMessage(), ex);
    // }
    //
    // return null;
    // }


    private DbAwareObject getObjectInternal(DbAwareObject object, String sqlWhere, String debugParameter)
    {
        String sql = null;
        try
        {
            sql = "select " + object.getTableColumns() + " from " + object.getTableName() + " a " + sqlWhere;

            ResultSet resultSet = getConnection().createStatement().executeQuery(sql);

            if (resultSet.next())
            {
                object.getDbObject(resultSet);
                return object;
            }
        }
        catch (SQLException ex)
        {
            LOG.debug("Sql: " + sql);
            LOG.error("Error getting " + object.getClass().getSimpleName() + " " + debugParameter + ". Exception: "
                    + ex.getMessage(),
                ex);
        }

        return null;
    }

    // private Animal getAnimalInternal(String sqlWhere, String debugParameter)
    // {
    // try
    // {
    // Animal animal = new Animal();
    //
    // String sql = "select " + animal.getTableColumns() + " from " +
    // animal.getTableName() + " a " + sqlWhere;
    //
    // ResultSet resultSet =
    // getConnection().createStatement().executeQuery(sql);
    //
    // if (resultSet.next())
    // {
    // animal.getDbObject(resultSet);
    // return animal;
    // }
    // } catch (SQLException ex)
    // {
    // LOG.error("Error getting Animal " + debugParameter + ". Exception: " +
    // ex.getMessage(), ex);
    // }
    //
    // return null;
    // }

}
