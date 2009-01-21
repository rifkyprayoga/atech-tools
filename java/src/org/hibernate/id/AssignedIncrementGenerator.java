package org.hibernate.id;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;
import org.hibernate.util.StringHelper;



/**
 *  This file is part of ATech Tools library.
 *  
 *  AssignedIncrementGenerator
 *  
 *  An <tt>IdentifierGenerator</tt> that returns a <tt>long</tt> value. If id was set 
 *  manually then that value is used (it works as assignment), if id is set to 0 or
 *  less than zero, then Increment part is called and we construct id value, as max 
 *  primary key of all entries, incremented by 1.
 * 
 *  Not safe for use in a cluster!<br>
 *  <br>
 *  Mapping parameters supported, but not usually needed: tables, column.
 *  (The tables parameter specified a comma-separated list of table names.)
 * 
 *  This generator is created by fusion of assigned and increment generator.
 * 
 *  
 *  Copyright (C) 2006  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/


public class AssignedIncrementGenerator implements IdentifierGenerator, Configurable
{

    private String entityName;
    private long next;
    private String sql;
    private Class<?> returnClass;
    private static final Log log = LogFactory.getLog(AssignedIncrementGenerator.class);


    /**
     * Constructor
     */
    public AssignedIncrementGenerator()
    {
    }


    
    /**
     * generate - method which needs to be implemented for Id generator to work
     */
    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException
    {
        Serializable id = session.getEntityPersister(entityName, obj).getIdentifier(obj, session.getEntityMode());

        //System.out.println("generate(): curr_id: " + id);

        String ids = null;

        if (id instanceof String)
        {
            String str = (String)id;
            ids = str;
        }
        else if (id instanceof Long)
        {
            Long l = (Long)id;
            ids = "" + l.longValue();
        }
        else if (id instanceof Integer)
        {
            Integer i = (Integer)id;
            ids = "" + i.longValue();
        }


        if ((ids == null) || (ids.equals("-1")) || (ids.equals("0")) )
        {
            log.debug("ID was not found. Creating new ID");
            //System.out.println("ID was not found. Creating new ID");

            if (sql != null)
                getNext(session);
            else
            {
                log.error("SQL Variable was not set. Failed increment.");
                //System.out.println("SQL Variable was not set. Failed increment.");
            }

            return IdentifierGeneratorFactory.createNumber(next++, returnClass);
        } 
        else
        {
            log.debug("ID was already assigned: " + id);
//            System.out.println("ID was already assigned: " + id);
            return id;
        }
    }


    /**
     * configure - method which needs to be implemented for Id generator to work
     */
    public void configure(Type type, Properties params, Dialect dialect) throws MappingException 
    {

        log.debug("configure");

        // for assignment
        entityName = params.getProperty(ENTITY_NAME);
/*
        if (entityName==null) 
        {
            throw new MappingException("no entity name");
	} */

        // for increment
        String tableList = params.getProperty("tables");

        if (tableList==null) 
            tableList = params.getProperty(PersistentIdentifierGenerator.TABLES);

        String[] tables = StringHelper.split(", ", tableList);
        String column = params.getProperty("column");

        if (column==null) 
            column = params.getProperty(PersistentIdentifierGenerator.PK);

        String schema = params.getProperty(PersistentIdentifierGenerator.SCHEMA);
        String catalog = params.getProperty(PersistentIdentifierGenerator.CATALOG);
        returnClass = type.getReturnedClass();
            

        StringBuffer buf = new StringBuffer();
        for ( int i=0; i<tables.length; i++ ) 
        {
                if (tables.length>1) 
                {
                    buf.append("select ").append(column).append(" from ");
                }

                buf.append(Table.qualify(catalog, schema, tables[i]) );

                if ( i<tables.length-1) 
                    buf.append(" union ");
        }

        if (tables.length>1) 
        {
            buf.insert(0, "( ").append(" ) ids_");
            column = "ids_." + column;
        }
            
        sql = "select max(" + column + ") from " + buf.toString();
    }


    // From IncrementGenerator
    private void getNext( SessionImplementor session ) 
    {

        log.debug("fetching initial value: " + sql);
		
        try 
        {
            PreparedStatement st = session.getBatcher().prepareSelectStatement(sql);
            try 
            {
                ResultSet rs = st.executeQuery();
                try 
                {
                    if (rs.next()) 
                     {
                        next = rs.getLong(1) + 1;
                        if (rs.wasNull()) 
                            next = 1;
                    }
                    else 
                    {
                        next = 1;
                    }
                    //sql=null;
                    log.debug("first free id: " + next);
                }
                finally 
                {
                    rs.close();
                }
            }
            finally 
            {
                session.getBatcher().closeStatement(st);
            }
			
        }
        catch (SQLException sqle) 
        {
            throw JDBCExceptionHelper.convert(
                                session.getFactory().getSQLExceptionConverter(),
                                sqle,
                                "could not fetch initial value for AssignedIncrement generator",
                                sql
                        );
        }
    }




}
