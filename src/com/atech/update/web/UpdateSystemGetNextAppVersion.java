package com.atech.update.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atech.update.web.db.DataLayerUpdateServletV2;
import com.atech.utils.web.ServletUtilities;

/**
 *  This file is part of ATech Tools library.
 *  
 *  ComponentCustomApp - Custom Application definition class
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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

public class UpdateSystemGetNextAppVersion extends HttpServlet
{

    private static final long serialVersionUID = -2760238837962215561L;


    /**
     * doGet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DataLayerUpdateServletV2 dl = DataLayerUpdateServletV2.getInstance();

        String product_id = getParameter(request, "product_id");
        int current_version = Integer.parseInt(getParameter(request, "current_version"));
        int current_db = Integer.parseInt(getParameter(request, "current_db"));

        response.setContentType("text/html");
        // response.setContentLength(message.length());
        PrintWriter out = response.getWriter();
        out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        out.println("<body>");
        out.println("<h1>ATech Update Server</h1><br>");

        out.println("<b>This servlet is intended for internal use of applications implementing");
        out.println("ATech-Tools Update System (v2).<b><br><br>");

        String ret_data = dl.getNextVersionInfo(product_id, current_version, current_db);

        out.println("Returned data:<br>" + ret_data.replace(";", "<br>"));

        out.println("<!-- RETURN_DATA_START__<br>" + ret_data + "<br>" + "__RETURN_DATA_END --!>");

        out.println("</body>");
        out.println("</html>");

        // in.close();
        out.flush();
        out.close();

    }


    /** 
     * doPost
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }


    /**
     * Gets the parameter.
     * 
     * @param request the request
     * @param parameter the parameter
     * 
     * @return the parameter
     */
    public String getParameter(HttpServletRequest request, String parameter)
    {
        String param = request.getParameter(parameter);

        if (param == null || param.equals("null"))
            return null;
        else
            return param;

    }

}
