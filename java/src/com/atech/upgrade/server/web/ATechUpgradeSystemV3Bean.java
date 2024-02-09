package com.atech.upgrade.server.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.upgrade.server.db.DataLayerUpdateServletV3;
import com.atech.upgrade.server.defs.ErrorResponseDef;
import com.atech.upgrade.server.defs.UpdateAction;
import com.atech.upgrade.server.dto.ApplicationNoteDTO;
import com.atech.upgrade.server.dto.ApplicationUpdateDTO;
import com.atech.upgrade.server.dto.UpdateNotesDTO;
import com.atech.upgrade.server.xml.UpdateServerResponse;
import com.atech.utils.web.ServletUtilities;

/**
 * Created by andy on 13.03.15.
 */
public class ATechUpgradeSystemV3Bean extends HttpServlet
{

    private static final long serialVersionUID = 1517318150512783211L;

    private static final Logger LOG = LoggerFactory.getLogger(ATechUpgradeSystemV3Bean.class);


    /**
     * doGet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        UpdateServerResponse updateServerResponse = new UpdateServerResponse();

        // check if parameters present
        String applicationName = getParameter(request, "application");
        String versionStr = getParameter(request, "version");
        String action = getParameter(request, "action");

        if ((!checkIfParameterPresent(applicationName, ErrorResponseDef.ApplicationNotSet, response,
            updateServerResponse)) || //
                (!checkIfParameterPresent(versionStr, ErrorResponseDef.VersionNotSet, response, updateServerResponse))
                || //
                (!checkIfParameterPresent(action, ErrorResponseDef.ActionNotSet, response, updateServerResponse)))
        {
            return;
        }

        // check if application is present
        boolean appThere = dl.isApplicationPresent(applicationName);

        if (!appThere)
        {
            writeErrorResponse(ErrorResponseDef.ApplicationNotAvailable, response, updateServerResponse);
            return;
        }

        updateServerResponse.setApplicationName(applicationName);

        int version = Integer.parseInt(versionStr);

        // check if version is present
        boolean versionThere = dl.isVersionPresent(applicationName, version);

        if (!versionThere)
        {
            writeErrorResponse(ErrorResponseDef.VersionNotAvailable, response, updateServerResponse);
            return;
        }

        updateServerResponse.setVersion(version);

        UpdateAction updateAction = UpdateAction.getUrlAction(action);

        if (updateAction == UpdateAction.None)
        {
            writeErrorResponse(ErrorResponseDef.ActionNotAvailable, response, updateServerResponse);
            return;
        }

        updateServerResponse.setAction(updateAction);

        if (updateAction == UpdateAction.GetNotes)
        {
            processGetNotes(updateServerResponse, request, response);
        }
        else if (updateAction == UpdateAction.GetXmlData)
        {
            processGetXmlData(updateServerResponse, request, response);
        }
        else if (updateAction == UpdateAction.GetUpgradeFile)
        {
            processGetUpgradeFile(updateServerResponse, request, response);
        }
        else if (updateAction == UpdateAction.CheckIfUpgradeAvailable)
        {
            processCheckIfUpgradeAvailable(updateServerResponse, request, response);
        }
        else
        {
            processGetInfo(updateServerResponse, request, response);
        }
    }


    private void processCheckIfUpgradeAvailable(UpdateServerResponse updateServerResponse, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        // TODO implement
        // TODO
        PrintWriter out = notImplementedYet(response, "CheckIfUpgradeAvailable");

        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        ApplicationUpdateDTO dto = dl.getApplicationUpdateForVersion(updateServerResponse.applicationName,
            updateServerResponse.version);
    }


    private void processGetNotes(UpdateServerResponse updateServerResponse, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        List<ApplicationNoteDTO> notes = dl.getNotes(updateServerResponse.applicationName,
            updateServerResponse.version);

        UpdateNotesDTO dto = new UpdateNotesDTO();
        dto.notes = notes;

        String xml = serializeUpdateServerResponse(dto);

        if (notes.size() == 0)
        {
            PrintWriter pw = response.getWriter();

            writeHTMLHeader(pw);

            pw.write("\n\n<b>No notes found.</b>");

            writeHiddenXml(pw, xml);
        }
        else
        {
            displayXmlData(response, xml);
        }

    }


    private void processGetXmlData(UpdateServerResponse updateServerResponse, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter(); // notImplementedYet(response,
                                                // "GetXmlData");

        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        ApplicationUpdateDTO dto = dl.getApplicationUpdateForVersion(updateServerResponse.applicationName,
            updateServerResponse.version);

        if (dto.xmlData == null)
        {
            this.writeErrorResponse(ErrorResponseDef.NoXmlDataForUpdate, response, updateServerResponse);
        }
        else
        {
            displayXmlData(response, dto.xmlData);
        }

    }


    private void processGetUpgradeFile(UpdateServerResponse updateServerResponse, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        // TODO implement
        // TODO
        PrintWriter out = notImplementedYet(response, "GetUpgradeFile");

        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        ApplicationUpdateDTO dto = dl.getApplicationUpdateForVersion(updateServerResponse.applicationName,
            updateServerResponse.version);

    }


    private void processGetInfo(UpdateServerResponse updateServerResponse, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        ApplicationUpdateDTO dto = dl.getApplicationUpdateLatest(updateServerResponse.applicationName);

        updateServerResponse.createSuccessResponse((dto.versionNumber != updateServerResponse.version), dto);

        PrintWriter out = response.getWriter();

        writeHTMLHeader(out);

        writeParameter(out, "Version", "" + updateServerResponse.version);
        writeParameter(out, "Is Upgradable", "" + updateServerResponse.isFoundNewVersion());

        ApplicationUpdateDTO appUpdate = updateServerResponse.getApplicationUpdate();
        updateServerResponse.notesCount = dl.getNotesCount(updateServerResponse.applicationName,
            updateServerResponse.version);

        if (appUpdate != null)
        {
            writeParameter(out, "New Version Number", "" + appUpdate.versionNumber);
            writeParameter(out, "New Version Name", "" + appUpdate.versionName);
        }

        writeParameter(out, "Notes Count", "" + updateServerResponse.notesCount);

        writeHiddenXml(out, updateServerResponse);
    }


    private PrintWriter notImplementedYet(HttpServletResponse response, String action) throws IOException
    {
        PrintWriter out = response.getWriter();

        writeHTMLHeader(out);

        out.write("\n\n<h2>" + action + " [not implemented yet]</h2>\n\n");

        return out;
    }


    private boolean checkIfParameterPresent(String parameter, ErrorResponseDef errorResponseDef,
            HttpServletResponse response, UpdateServerResponse updateServerResponse) throws IOException
    {
        if (parameter == null)
        {
            writeErrorResponse(errorResponseDef, response, updateServerResponse);
            return false;
        }
        else
            return true;
    }


    private void writeHTMLHeader(PrintWriter out)
    {
        GregorianCalendar gc = new GregorianCalendar();

        out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        out.println("<body>");
        out.println("<h1>ATech Update Server V3 [" + gc.getTime() + "]</h1><br>");
        out.println("<br><br>");
    }


    private void displayXmlData(HttpServletResponse response, String xml) throws IOException
    {
        response.setContentType("text/xml;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        out.println(xml);
    }


    private void writeErrorResponse(ErrorResponseDef errorResponseDef, HttpServletResponse httpServletResponse,
            UpdateServerResponse updateServerResponse) throws IOException
    {
        PrintWriter out = httpServletResponse.getWriter();

        writeHTMLHeader(out);

        out.write("There was an error while processing request.<br><br>\n");
        writeParameter(out, "Error Code", "" + errorResponseDef.getCommandCode());
        writeParameter(out, "Error Description", errorResponseDef.getCommandDescription());

        updateServerResponse.setErrorResponse(errorResponseDef);

        // String serObject =
        // serializeUpdateServerResponse(updateServerResponse);

        writeHiddenXml(out, updateServerResponse);
    }


    private void writeParameter(PrintWriter out, String name, String value)
    {
        out.write("<b>" + name + ":</b>&nbsp;&nbsp;" + value + "<br>\n");
    }


    private void writeHiddenXml(PrintWriter out, Object serObject)
    {
        out.write("<!-- \n");

        String xmlToWrite = null;

        if (serObject instanceof String)
        {
            xmlToWrite = (String) serObject;
        }
        else
        {
            xmlToWrite = serializeUpdateServerResponse(serObject);
        }

        out.write(xmlToWrite);
        out.write("\n -->\n\n");
    }


    private String serializeUpdateServerResponse(Object response)
    {

        try
        {
            Serializer serializer = new Persister();
            // Example example = new Example("Example message", 123);
            // File result = new File("example.xml");

            StringWriter stringWriter = new StringWriter();

            serializer.write(response, stringWriter);

            String xml = stringWriter.toString();

            System.out.println("Serialized:\n" + xml);

            return xml;
        }
        catch (Exception ex)
        {
            System.out.println("Ex: " + ex);
            ex.printStackTrace();

            // LOG.error("serialize: " + ex, ex);

            return null;
        }
    }


    private boolean isNotSet(String parameter)
    {
        return (parameter == null || parameter.trim().length() == 0);
    }


    private void processRequestToCheckForNewVersion(String applicationName, String currentVersion, //
            HttpServletRequest request, HttpServletResponse response)
    {
        // Application app = null;
        // ApplicationVersion app_ver = null;
        //
        // this.error_id = 0;
        // this.error_desc = "No error";
        // this.error_code = "ATUS_NO_ERROR";
        //
        // this.application_name = getParameter(request, "application");
        // this.application_version = getParameter(request, "version");
        //
        // response.setContentType("text/html");
        // // response.setContentLength(message.length());
        // PrintWriter out = response.getWriter();
        // out.println(ServletUtilities.headWithTitle("ATech Update Server"));
        // out.println("<body>");
        // out.println("<h1>ATech Update Server</h1><br>");
        // out.println("<br><br>");
        //
        // if (this.application_name == null)
        // {
        // setError(1001);
        // }
        //
        // String app_desc = "";
        //
        // if (this.error_id == 0)
        // {
        // app = dl.getApplication(this.application_name);
        //
        // if (app == null)
        // {
        // setError(1101);
        // }
        // else
        // {
        // app_desc = " " + app.desc + " [" + app.id + "]";
        // }
        // }
        //
        // if (this.application_version == null)
        // {
        // setError(1002);
        // }
        //
        // if (this.error_id == 0)
        // {
        // app_ver = getCurrentVersion(app);
        // }
        //
        // out.println("<b>Application:</b> " + this.application_name + "&nbsp;"
        // + app_desc + "<br>");
        // out.println("<b>Current Version:</b> " + this.application_version +
        // "<br><br>");
        //
        // out.println("<b>Error Description:</b> " + this.error_desc + " (" +
        // this.error_id + ") <br>");
        // out.println("<b>Error Code:</b> " + this.error_code + "<br>");
        //
        // boolean latest_ver = false;
        // int never_ver = -1;
        // boolean new_for_new_db = false;
        //
        // if (app != null && app_ver != null)
        // {
        // latest_ver = this.isLatestVersion(app_ver, app);
        // out.println("<b>Do we have latest version?</b> &nbsp; " + latest_ver
        // + "<br>");
        //
        // if (!latest_ver)
        // {
        // never_ver = isThereNeverVersionForSameDb(app_ver, app);
        // out.println("<b>Is there newer version for our db?</b> " + (never_ver
        // != -1) + " [" + never_ver + "]"
        // + "<br>");
        //
        // new_for_new_db = isThereNeverVersionForOtherDb(app_ver, app);
        // out.println("<b>Is there newer version for never db?</b> " +
        // new_for_new_db + "<br>");
        // }
        // }
        //
        // out.println("<!--\n\n");
        // out.println("=== APPLICATION REPORT ===\n");
        // out.println("<server_report>");
        // out.println(" <error_nr>" + this.error_id + "</error_nr>");
        // out.println(" <error_code>" + this.error_code + "</error_code>");
        //
        // if (this.error_id == 0)
        // {
        // out.println(" <versions>");
        // out.println(" <latest_version>" + latest_ver +
        // "</latest_version>\n");
        //
        // if (!latest_ver)
        // {
        //
        // out.println(" <newer_version_our_db>" + (never_ver != -1) +
        // "</newer_version_our_db>");
        // out.println(" <newer_version_our_db_number>" + never_ver +
        // "</newer_version_our_db_number>");
        // out.println(" <newer_version_higher_db>" + new_for_new_db +
        // "</newer_version_higher_db>");
        // out.println(" <newer_version_higher_db_number>" +
        // this.getLatestVersion(app).version_num
        // + "</newer_version_higher_db_number>\n");
        // }
        // out.println(" </versions>");
        //
        // }
        // out.println("</server_report>");
        //
        // out.println("--!>\n\n");
        //
        // out.println("</body>");
        // out.println("</html>");
        //
        // // in.close();
        // out.flush();
        // out.close();

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

        if (param == null || param.equals("null") || param.trim().length() == 0)
            return null;
        else
            return param;
    }


    /**
     * @param _error_id
     */
    public void setError(int _error_id)
    {
        // System.out.println("setError (current=" + this.error_id + ",new=" +
        // _error_id);
        //
        // if (error_id > 0)
        // return;
        //
        // this.error_id = _error_id;
        //
        // if (error_id == 1001) // no application name
        // {
        // this.error_desc = "Application name not set";
        // this.error_code = "ATUS_APP_NOT_SET";
        // }
        // else if (error_id == 1002) // no application version
        // {
        // this.error_desc = "Application version not set";
        // this.error_code = "ATUS_APP_NO_VERSION";
        // }
        // else if (error_id == 1101)
        // {
        // this.error_desc = "Application not found";
        // this.error_code = "ATUS_APP_NOT_FOUND";
        // }
        //
        // // 1002
        //
        // // String error_desc = "No error";
        // // String error_code = "ATUS_NO_ERROR";

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
