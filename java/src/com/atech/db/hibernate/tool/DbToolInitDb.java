package com.atech.db.hibernate.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.atech.db.hibernate.HibernateConfiguration;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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


public abstract class DbToolInitDb
{

    private static Log log = LogFactory.getLog(DbToolInitDb.class); 
    
    
    /**
     * The Constant INIT_TYPE_NONE.
     */
    public static final int INIT_TYPE_NONE = 0;
    
    /**
     * The Constant INIT_TYPE_INIT_DB.
     */
    public static final int INIT_TYPE_INIT_DB = 1;
    
    /**
     * The Constant INIT_TYPE_INIT_DB_AND_BASE_IMPORT.
     */
    public static final int INIT_TYPE_INIT_DB_AND_BASE_IMPORT = 2;
    
    
    /**
     * The init_type.
     */
    int init_type;
    
    /**
     * The hibernate_config.
     */
    protected HibernateConfiguration hibernate_config;
    
    /**
     * The error_code.
     */
    int error_code;
    
    /**
     * The error_desc.
     */
    String error_desc;
    
    /**
     * The error_exception.
     */
    Exception error_exception;
    
    /**
     * Instantiates a new db tool init db.
     * 
     * @param config the config
     * @param init_type_ the init_type_
     */
    public DbToolInitDb(HibernateConfiguration config, int init_type_)
    {
        this.hibernate_config = config;
        this.init_type = init_type_;
    }


    /**
     * Checks if is inits the possible.
     * 
     * @return true, if is inits the possible
     */
    public boolean isInitPossible()
    {
        return (init_type>0);
    }


    /**
     * Db init.
     * 
     * @return true, if successful
     */
    public boolean dbInit()
    {
        System.out.println("dbInit: " + init_type);
        
        if (init_type == INIT_TYPE_NONE)
        {
            return false;
        }
        else if (init_type == INIT_TYPE_INIT_DB)
        {
            return createTables();
        }
        else if (init_type == INIT_TYPE_INIT_DB_AND_BASE_IMPORT)
        {
            boolean res = createTables();
            if (!res)
                return false;
            
            return this.fillData();
        }
        else
        {
            return false;
        }
    }
    
    
    /**
     * Gets the error code.
     * 
     * @return the error code
     */
    public int getErrorCode()
    {
        return this.error_code;
        
    }
    
    
    /**
     * Gets the error description.
     * 
     * @return the error description
     */
    public String getErrorDescription()
    {
        return this.error_desc;
    }
    
    /**
     * Gets the error exception.
     * 
     * @return the error exception
     */
    public Exception getErrorException()
    {
        return this.error_exception;
    }
    
    /**
     * Creates the tables.
     * 
     * @return true, if successful
     */
    public boolean createTables()
    {
        try
        {
            new SchemaExport(this.hibernate_config.getConfiguration()).create(true, true);
        }
        catch(Exception ex)
        {
            log.error("createTables exception: " + ex, ex);
            return false;
        }

        return true;
    }

    
    /**
     * Drop Tables
     * 
     * @return
     */
    public boolean dropTables()
    {
        try
        {
            new SchemaExport(this.hibernate_config.getConfiguration()).drop(true, true);
        }
        catch(Exception ex)
        {
            return false;
        }

        return true;
    }
    

    /**
     * Fill data.
     * 
     * @return true, if successful
     */
    public abstract boolean fillData();
    
    

}
