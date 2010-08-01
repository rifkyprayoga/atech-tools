package com.atech.update.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atech.update.web.db.DataLayerUpdateServlet_v2;
import com.atech.utils.web.ServletUtilities;

public class UpdateSystemGetNextAppVersion extends HttpServlet
{

    
    /**
     * doGet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        DataLayerUpdateServlet_v2 dl = DataLayerUpdateServlet_v2.getInstance();
        
        String product_id = getParameter(request, "product_id");
        int current_version = Integer.parseInt(getParameter(request, "current_version"));
        int current_db = Integer.parseInt(getParameter(request, "current_db"));
        
        response.setContentType("text/html");
        //response.setContentLength(message.length());
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

        
        //in.close();
        out.flush();
        out.close();
        
    }
    
    
    /** 
     * doPost
     */
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
        
        if (param==null || param.equals("null"))
            return null;
        else
            return param;
        
    }
    
    
}
