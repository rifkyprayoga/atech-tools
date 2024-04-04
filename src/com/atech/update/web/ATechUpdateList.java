package com.atech.update.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class ATechUpdateList extends HttpServlet
{
    /**
     * 
     */
    private static final long serialVersionUID = -7296357160678571419L;

    String application_name = null;
    String application_version = null;
    int application_id = 0;

    int error_id = 0;
    String error_desc = "No error";
    String error_code = "ATUS_NO_ERROR";

    /**
     * doGet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        this.error_id = 0;
        this.error_desc = "No error";
        this.error_code = "ATUS_NO_ERROR";

        this.application_name = getParameter(request, "application");
        this.application_version = getParameter(request, "version");

        response.setContentType("text/html");
        // response.setContentLength(message.length());
        PrintWriter out = response.getWriter();
        out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        out.println("<body>");
        out.println("<h1>ATech Update Server</h1><br>");
        out.println("<br><br>");

        if (this.application_name == null)
        {
            setError(1001);
        }

        if (this.application_version == null)
        {
            setError(1002);
        }

        out.println("<b>Application:</b> " + this.application_name + "<br>");
        out.println("<b>Current Version:</b> " + this.application_version + "<br><br>");

        out.println("<b>Error Description:</b> " + this.error_desc + " (" + this.error_id + ") <br>");
        out.println("<b>Error Code:</b> " + this.error_code + "<br>");

        out.println("</body>");
        out.println("</html>");

        // in.close();
        out.flush();
        out.close();

        /*
         * DataInputStream in =
         * new DataInputStream((InputStream)request.getInputStream());
         * String text = in.readUTF();
         * System.out.println("Data Read: \n" + text);
         * String message;
         * try {
         * message = "100 ok";
         * } catch (Throwable t) {
         * message = "200 " + t.toString();
         * }
         * response.setContentType("text/plain");
         * response.setContentLength(message.length());
         * PrintWriter out = response.getWriter();
         * out.println(message);
         * in.close();
         * out.close();
         * out.flush();
         */
        /*
         * response.setContentType("text/html");
         * PrintWriter out = response.getWriter();
         * String title = "Reading Three Request Parameters";
         * out.println(ServletUtilities.headWithTitle(title) +
         * "<BODY>\n" +
         * "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
         * "<UL>\n" +
         * "  <LI>param1: "
         * + request.getParameter("param1") + "\n" +
         * "  <LI>param2: "
         * + request.getParameter("param2") + "\n" +
         * "  <LI>param3: "
         * + request.getParameter("param3") + "\n" +
         * "</UL>\n" +
         * "</BODY></HTML>");
         */
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

    /**
     * @param _error_id
     */
    public void setError(int _error_id)
    {
        System.out.println("setError (current=" + this.error_id + ",new=" + _error_id);

        if (error_id > 0)
            return;

        this.error_id = _error_id;

        if (error_id == 1001) // no application name
        {
            this.error_desc = "Application name not set";
            this.error_code = "ATUS_APP_NOT_SET";
        }
        else if (error_id == 1002) // no application version
        {
            this.error_desc = "Application version not set";
            this.error_code = "ATUS_APP_NO_VERSION";
        }

        // 1002

        // String error_desc = "No error";
        // String error_code = "ATUS_NO_ERROR";

    }

    /** 
     * doPost
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
