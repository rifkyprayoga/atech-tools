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

public class ATechUpdateSystem extends HttpServlet
{

    private static final long serialVersionUID = -5622091320679694087L;


    /**
     * doGet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // action
        String action = getParameter(request, "action");

        System.out.println("doGet(): action=" + action);

        if (action.equals("get_file"))
        {
            System.out.println("GetFile is not implemented yet !");
        }
        else if (action.equals("get_xml"))
        {
            getProductXml(response, getParameter(request, "product_id"),
                Integer.parseInt(getParameter(request, "current_version")));
        }
        else if (action.equals("get_update_list"))
        {
            getDetailedUpdateList(response, getParameter(request, "product_id"),
                Integer.parseInt(getParameter(request, "current_version")),
                Integer.parseInt(getParameter(request, "next_version")));
        }
        else if (action.equals("get_update_status"))
        {
            getNextVersion(response, getParameter(request, "product_id"),
                Integer.parseInt(getParameter(request, "current_version")),
                Integer.parseInt(getParameter(request, "current_db")));
        }
        else
        {
            System.out.println("Unknown action: " + action);
        }

        // System.out.println("AtechUpdateGetFile::doGet");
        // this.doDownload(request, response, "d:/test.jar", "test.jar");
    }


    /**
     * @param request
     * @param parameter
     * @return
     */
    public String getParameter(HttpServletRequest request, String parameter)
    {
        String param = request.getParameter(parameter);

        if (param == null || param.equals("null"))
            return null;
        else
            return param;

    }


    @SuppressWarnings("unused")
    private void getFile(HttpServletRequest request, HttpServletResponse response, long module_id, long version_id)
    {

    }


    private void getDetailedUpdateList(HttpServletResponse response, String product_id, int current_version,
            int next_version) throws ServletException, IOException
    {
        DataLayerUpdateServletV2 dl = DataLayerUpdateServletV2.getInstance();

        String xml = dl.getProductUpdateList(product_id, current_version, next_version);

        response.setContentType("text/html");
        // response.setContentLength(message.length());
        PrintWriter out = response.getWriter();
        out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        out.println("<body>");
        out.println("<h1>ATech Update Server - Get Detailed Update List</h1><br>");

        out.println("<b>This servlet is intended for internal use of applications implementing");
        out.println("ATech-Tools Update System (v2).<b><br><br>");

        // String ret_data = dl.getNextVersionInfo(product_id, current_version,
        // current_db);

        out.println("Returned data:<br>" + xml + "<br>");

        out.println("<!-- RETURN_DATA_START__<br>" + xml + "<br>" + "__RETURN_DATA_END --!>");

        out.println("</body>");
        out.println("</html>");

        // in.close();
        out.flush();
        out.close();

    }


    /**
     * get Next Version
     * 
     * @param response 
     * @param product_id 
     * @param current_version 
     * @param current_db
     *  
     * @throws ServletException 
     * @throws IOException 
     */
    private void getNextVersion(HttpServletResponse response, String product_id, int current_version, int current_db)
            throws ServletException, IOException
    {
        DataLayerUpdateServletV2 dl = DataLayerUpdateServletV2.getInstance();

        response.setContentType("text/html");
        // response.setContentLength(message.length());
        PrintWriter out = response.getWriter();
        out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        out.println("<body>");
        out.println("<h1>ATech Update Server - Get Next Version</h1><br>");

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
     * get Product Xml
     * 
     * @param response 
     * @param product_id 
     * @param current_version
     *  
     * @throws ServletException 
     * @throws IOException 
     */
    public void getProductXml(HttpServletResponse response, String product_id, int current_version)
            throws ServletException, IOException
    {
        DataLayerUpdateServletV2 dl = DataLayerUpdateServletV2.getInstance();

        response.setContentType("text/html");
        // response.setContentLength(message.length());
        PrintWriter out = response.getWriter();
        out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        out.println("<body>");
        out.println("<h1>ATech Update Server - Get Product Xml</h1><br>");

        out.println("<b>This servlet is intended for internal use of applications implementing");
        out.println("ATech-Tools Update System (v2).<b><br><br>");

        String ret_data = dl.getProductXml(product_id, current_version);

        out.println("Returned data:<br>" + ret_data.replace(";", "<br>"));

        out.println("<!-- RETURN_DATA_START__<br>" + ret_data + "<br>" + "__RETURN_DATA_END --!>");

        out.println("</body>");
        out.println("</html>");

        // in.close();
        out.flush();
        out.close();

    }

}
