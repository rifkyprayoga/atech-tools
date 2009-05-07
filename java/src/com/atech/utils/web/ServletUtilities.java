package com.atech.utils.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *  Some simple time savers. Part of tutorial on servlets and JSP that appears
 * at http://www.apl.jhu.edu/~hall/java/Servlet-Tutorial/ 1999 Marty Hall; may
 * be freely used or adapted.
 */

public class ServletUtilities
{
    public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";

    public static String headWithTitle(String title)
    {
        return (DOCTYPE + "\n" + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
    }

    /**
     * Read a parameter with the specified name, convert it to an int,
     * and return it. Return the designated default value if the parameter
     * doesn't exist or if it is an illegal integer format.
     * 
     * @param request 
     * @param paramName 
     * @param defaultValue 
     * @return 
     */
    public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue)
    {
        String paramString = request.getParameter(paramName);
        int paramValue;
        try
        {
            paramValue = Integer.parseInt(paramString);
        }
        catch (NumberFormatException nfe)
        { // Handles null and bad format
            paramValue = defaultValue;
        }
        return (paramValue);
    }

    public static String getCookieValue(Cookie[] cookies, String cookieName, String defaultValue)
    {
        for (int i = 0; i < cookies.length; i++)
        {
            Cookie cookie = cookies[i];
            if (cookieName.equals(cookie.getName()))
                return (cookie.getValue());
        }
        return (defaultValue);
    }

    // Approximate values are fine.

    public static final int SECONDS_PER_MONTH = 60 * 60 * 24 * 30;
    public static final int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;
}
