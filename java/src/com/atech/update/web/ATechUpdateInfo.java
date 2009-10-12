package com.atech.update.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atech.update.web.data.Application;
import com.atech.update.web.data.ApplicationVersion;
import com.atech.update.web.db.DataLayerUpdateServlet;
import com.atech.utils.web.ServletUtilities;


/**
 * @author Andy
 *
 */
public class ATechUpdateInfo extends HttpServlet 
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        DataLayerUpdateServlet dl = DataLayerUpdateServlet.getInstance();
        Application app = null;
        ApplicationVersion app_ver = null;
        
        this.error_id = 0;
        this.error_desc = "No error";
        this.error_code = "ATUS_NO_ERROR";
        
        this.application_name = getParameter(request, "application");
        this.application_version = getParameter(request, "version");
        
        response.setContentType("text/html");
        //response.setContentLength(message.length());
        PrintWriter out = response.getWriter();
        out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        out.println("<body>");
        out.println("<h1>ATech Update Server</h1><br>");
        out.println("<br><br>");
        
        if (this.application_name==null)
            setError(1001);
        
        String app_desc = "";
        
        if (this.error_id == 0)
        {
            app = dl.getApplication(this.application_name);
            
            if (app==null)
                setError(1101);
            else
                app_desc = " " + app.desc + " [" + app.id + "]";
        }
        
        if (this.application_version==null)
            setError(1002);
        
        if (this.error_id == 0)
        {
            app_ver = getCurrentVersion(app);
        }
        
        
        out.println("<b>Application:</b> " + this.application_name + "&nbsp;" + app_desc + "<br>");
        out.println("<b>Current Version:</b> " + this.application_version + "<br><br>");
        
        out.println("<b>Error Description:</b> " + this.error_desc + " (" + this.error_id + ") <br>");
        out.println("<b>Error Code:</b> " + this.error_code + "<br>");
        
        boolean latest_ver = false;
        int never_ver = -1;
        boolean new_for_new_db = false;
        
        if ((app!=null) && (app_ver!=null))
        {
            latest_ver = this.isLatestVersion(app_ver, app);
            out.println("<b>Do we have latest version?</b> &nbsp; " + latest_ver + "<br>");
            
            
            if (!latest_ver)
            {
                never_ver = isThereNeverVersionForSameDb(app_ver, app);
                out.println("<b>Is there newer version for our db?</b> " + (never_ver!=-1) + " [" + never_ver + "]"+ "<br>");
                
                new_for_new_db = isThereNeverVersionForOtherDb(app_ver, app);
                out.println("<b>Is there newer version for never db?</b> " + new_for_new_db+ "<br>");
            }
        }
        
        out.println("<!--\n\n");
        out.println("=== APPLICATION REPORT ===\n");
        out.println("<server_report>");
        out.println("  <error_nr>" + this.error_id + "</error_nr>");
        out.println("  <error_code>" + this.error_code + "</error_code>");
        
        if (this.error_id==0)
        {
            out.println("  <versions>");
            out.println("    <latest_version>" + latest_ver+ "</latest_version>\n");
            
            if (!latest_ver)
            {
                
                out.println("  <newer_version_our_db>" + (never_ver!=-1)+ "</newer_version_our_db>");
                out.println("  <newer_version_our_db_number>" + never_ver+ "</newer_version_our_db_number>");
                out.println("  <newer_version_higher_db>" + new_for_new_db+ "</newer_version_higher_db>");
                out.println("  <newer_version_higher_db_number>" + this.getLatestVersion(app).version_num + "</newer_version_higher_db_number>\n");
            }
            out.println("  </versions>");
            
            
        }
        out.println("</server_report>");
        
        out.println("--!>\n\n");
        
        
        
        out.println("</body>");
        out.println("</html>");

        
        //in.close();
        out.flush();
        out.close();
        
        
       
        /*
        DataInputStream in = 
            new DataInputStream((InputStream)request.getInputStream());
    
    String text = in.readUTF();
    
    System.out.println("Data Read: \n" + text);
    
    String message;
    try {
        message = "100 ok";
    } catch (Throwable t) {
        message = "200 " + t.toString();
    }
    response.setContentType("text/plain");
    response.setContentLength(message.length());
    PrintWriter out = response.getWriter();
    out.println(message);
    in.close();
    out.close();
    out.flush();
    */        
        /*
        response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Reading Three Request Parameters";
    out.println(ServletUtilities.headWithTitle(title) +
                "<BODY>\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<UL>\n" +
                "  <LI>param1: "
                + request.getParameter("param1") + "\n" +
                "  <LI>param2: "
                + request.getParameter("param2") + "\n" +
                "  <LI>param3: "
                + request.getParameter("param3") + "\n" +
                "</UL>\n" +
                "</BODY></HTML>");
                */
  }

    /**
     * @param app
     * @return
     */
    public ApplicationVersion getCurrentVersion(Application app)
    {
        for(int i=0; i<app.versions.size(); i++)
        {
            if (app.versions.get(i).version.equals(this.application_version))
                return app.versions.get(i);
        }

        return null;
    }
    
    
    /**
     * @param aver
     * @param app
     * @return
     */
    public boolean isLatestVersion(ApplicationVersion aver, Application app)
    {
        ApplicationVersion av = app.versions.get(app.versions.size()-1);

        if (av.version_num == aver.version_num)
            return true;
        else
            return false;
        
    }

    
    /**
     * @param app
     * @return
     */
    public ApplicationVersion getLatestVersion(Application app)
    {
        ApplicationVersion av = app.versions.get(app.versions.size()-1);
        return av;
    }
    
    
    /**
     * @param aver
     * @param app
     * @return
     */
    public boolean isThereNeverVersionForOtherDb(ApplicationVersion aver, Application app)
    {
        ApplicationVersion av = app.versions.get(app.versions.size()-1);

        return (av.db_version != aver.db_version);
    }
    
    
    
    
    /**
     * Checks if is there never version for same db.
     * 
     * @param aver the aver
     * @param app the app
     * 
     * @return the int
     */
    public int isThereNeverVersionForSameDb(ApplicationVersion aver, Application app)
    {
        @SuppressWarnings("unused")
        boolean newer = false;
        
        for(int i=app.versions.size()-1; i>0; i--)
        {
            ApplicationVersion av_cur = app.versions.get(i);
            
            if ((av_cur.version_num>aver.version_num) && (av_cur.db_version==aver.db_version))
                return (int)av_cur.version_num;
        }
        
        return -1;
    }
    
    
    //public boolean doWeisNewerForDb
    
    
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
    
    
    
    /**
     * @param _error_id
     */
    public void setError(int _error_id)
    {
        System.out.println("setError (current=" + this.error_id + ",new=" + _error_id);
        
        if (error_id >0)
            return;
        
        this.error_id = _error_id;
        
        if (error_id==1001) // no application name
        {
            this.error_desc = "Application name not set";
            this.error_code = "ATUS_APP_NOT_SET";
        }
        else if (error_id==1002) // no application version
        {
            this.error_desc = "Application version not set";
            this.error_code = "ATUS_APP_NO_VERSION";
        }
        else if (error_id==1101)
        {
            this.error_desc = "Application not found";
            this.error_code = "ATUS_APP_NOT_FOUND";
        }
        
        
        //1002
        
        
        //String error_desc = "No error";
        //String error_code = "ATUS_NO_ERROR";
        
        
    }
    
    
    
    
    /** 
     * doPost
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        doGet(request, response);
    }
}
