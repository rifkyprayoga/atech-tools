package com.atech.update.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atech.update.web.db.DataLayerUpdateServlet_v2;
import com.atech.utils.web.ServletUtilities;

public class ATechUpdateSystem extends HttpServlet
{

    /**
     * doGet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        
        // action
        String action = getParameter(request, "action");
        
        if (action.equals("get_file"))
        {
            System.out.println("GetFile is not implemented yet !");
        }
        else if (action.equals("get_xml"))
        {
            getProductXml(response, 
                getParameter(request, "product_id"), 
                Integer.parseInt(getParameter(request, "current_version")));
        }
        else if (action.equals("get_update_status"))
        {
            getNextVersion(response, 
                getParameter(request, "product_id"), 
                Integer.parseInt(getParameter(request, "current_version")), 
                Integer.parseInt(getParameter(request, "current_db")));
        }
        else
        {
            System.out.println("Unknown action: " + action);
        }
        
        
        
        
//        System.out.println("AtechUpdateGetFile::doGet");
//        this.doDownload(request, response, "d:/test.jar", "test.jar");
    }
    
    
    /**
     * @param request
     * @param parameter
     * @return
     */
    public String getParameter(HttpServletRequest request, String parameter)
    {
        String param = request.getParameter(parameter);
        
        if (param==null || param.equals("null"))
            return null;
        else
            return param;
        
    }
    
    
    private void getFile(HttpServletRequest request, HttpServletResponse response, long module_id, long version_id)
    {

        
        
        
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
    public void getNextVersion(HttpServletResponse response, String product_id, int current_version, int current_db ) throws ServletException, IOException 
    {
        DataLayerUpdateServlet_v2 dl = DataLayerUpdateServlet_v2.getInstance();
        
        response.setContentType("text/html");
        //response.setContentLength(message.length());
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

        
        //in.close();
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
    public void getProductXml(HttpServletResponse response, String product_id, int current_version) throws ServletException, IOException 
    {
        DataLayerUpdateServlet_v2 dl = DataLayerUpdateServlet_v2.getInstance();
        
        response.setContentType("text/html");
        //response.setContentLength(message.length());
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

        
        //in.close();
        out.flush();
        out.close();
        
    }
    
    
    
}
