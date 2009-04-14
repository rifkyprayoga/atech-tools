package com.atech.db.hibernate.tool;

import com.atech.db.hibernate.HibernateConfiguration;

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

    public static final int INIT_TYPE_NONE = 0;
    public static final int INIT_TYPE_INIT_DB = 1;
    public static final int INIT_TYPE_INIT_DB_AND_BASE_IMPORT = 2;
    
    
    int init_type;
    protected HibernateConfiguration hibernate_config;
    int error_code;
    String error_desc;
    Exception error_exception;
    
    public DbToolInitDb(HibernateConfiguration config, int init_type_)
    {
        this.hibernate_config = config;
        this.init_type = init_type_;
    }


    public boolean isInitPossible()
    {
        return (init_type>0);
    }


    public boolean dbInit()
    {
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
    
    
    public int getErrorCode()
    {
        return this.error_code;
        
    }
    
    
    public String getErrorDescription()
    {
        return this.error_desc;
    }
    
    public Exception getErrorException()
    {
        return this.error_exception;
    }
    
    public boolean createTables()
    {
        // TODO
        return false;
    }
    

    public abstract boolean fillData();
    
    

}
