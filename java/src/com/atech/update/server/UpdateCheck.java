package com.atech.update.server;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 *  This file is part of ATech Tools library.
 *  
 *  UpdateCheck - Servlet for update (not working yet)
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



public class UpdateCheck extends HttpServlet 
{

    private static final long serialVersionUID = -4640549667242751028L;


    /**
     * doGet - for starting servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ArrayList<String> vd = getVersionData();

        for(int i=0; i<vd.size(); i++)
        {
            out.println(vd.get(i));
        }

    }

    private ArrayList<String> getVersionData()
    {
        return new ArrayList<String>();
    }


    @SuppressWarnings("unused")
    private void getData()
    {

        // = this or higher
        // != just this version is possible

        // component : id, name, last_version
        // component_version: id, comp_id, version, version_num, file, updatable_version, comment, action, dependencies

//x        String sql = "select c1.id, c1.name, c2.version, c2.version_num, c2.dependencies from component c1, component_version c2 where c1.id = c2.comp_id and c1.last_version=c2.version_num";

        // id, name, version, version_num, dependencies

    }



}