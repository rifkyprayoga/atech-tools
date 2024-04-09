package com.atech.data;

/**
 * Created by andy on 22.12.15.
 */
/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2015  Andy (Aleksander) Rozman (Atech-Software)
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
 *  https://github.com/andyrozman/atech-tools or contact us via this email:
 *  andy@atech-software.com
 *
 *  @author Andy
 *
 */
public class NotImplementedException extends RuntimeException
{

    private static final long serialVersionUID = 8770428303886201758L;
    String source;
    String className;
    String description;


    public NotImplementedException(Class clazzz, String source)
    {
        super();
        this.source = source;
        this.className = clazzz.getName();
    }


    public NotImplementedException(Class clazzz, String source, String description)
    {
        super();
        this.source = source;
        this.className = clazzz.getName();
    }


    @Override
    public String getMessage()
    {
        if (className == null)
        {
            return "Method " + source + " not implemented.";
        }
        else if (description == null)
        {
            return "Class: " + this.className + ", Method: " + source + " not implemented.";
        }
        else
        {
            return "Class: " + this.className + ", Method: " + source + " not implemented (" + description + ").";
        }
    }

}
