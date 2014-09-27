package com.jeans.httpclient;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This file is part of ATech Tools library.
 * 
 * This is imported library HttpClient (See longer/original comment under this
 * one, if available)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 */

/**
 * This class stores the information necessary to access a proxy server.
 * It provides proxy-specific header storage and proxy authentication.
 */

class HttpProxy
{
    URL ProxyServer;
    public String ProxyUser;
    public String ProxyPassword;
    public weiss.util.ArrayList ProxyHeader;
    public weiss.util.ArrayList ProxyHeaderValues;
    // public java.util.Vector ProxyHeader;
    // public java.util.Vector ProxyHeaderValues;
    public boolean useProxy;

    public HttpProxy()
    {
        ProxyHeader = new weiss.util.ArrayList();
        ProxyHeaderValues = new weiss.util.ArrayList();
    }

    /**
     * Returns the port number of the host name for the proxy server.
     * @return 
     */
    public int getProxyPort()
    {
        if (ProxyServer == null)
            return -1;
        return ProxyServer.getPort();
    }

    /**
     * Returns the host name of the proxy server.
     * @return 
     */
    public String getProxyServer()
    {
        if (ProxyServer == null)
            return "";
        return ProxyServer.getHost();
    }

    /**
     * Removes a header from the proxy header list
     *
     * @param header Header name - case sensitive
     */
    public void removeProxyHeader(String header)
    {
        /*
         * int x = ProxyHeader.indexOf(header);
         * ProxyHeader.removeElementAt(x);
         * ProxyHeaderValues.removeElementAt(x);
         */
    }

    /**
     * Clears the proxy header list.
     */
    public void resetProxyHeaders()
    {
        /*
         * ProxyHeader.removeAllElements();
         * ProxyHeaderValues.removeAllElements();
         */
    }

    /**
     * Sets the username and password for proxy authentication
     *
     * @param u Proxy username
     * @param p Proxy password
     */

    public void setProxyAuth(String u, String p)
    {
        ProxyUser = u;
        ProxyPassword = p;
    }

    /**
     * Set headers that are sent to the proxy server or only on a proxy
     * request.
     *
     * @param header Header name
     * @param value Header value
     */

    public void setProxyHeader(String header, String value)
    {
        ProxyHeader.add(header);
        ProxyHeaderValues.add(value);
    }

    /**
     * Sets the proxy server from a URL in typical HTTP URL format.
     * For example, http://proxyserver:8000/
     *
     * @param url URL for proxy server
     */

    public void setProxyServer(String url)
    {
        try
        {
            ProxyServer = new URL(url);
        }
        catch (MalformedURLException e)
        {
            ProxyServer = null;
            return;
        }
    }

    /**
     * Sets the host name and port number for the proxy server.
     *
     * @param host Proxy host name
     * @param p Port number
     */

    public void setProxyServer(String host, int p)
    {
        try
        {
            ProxyServer = new URL("http", host, p, "");
        }
        catch (MalformedURLException e)
        {
            ProxyServer = null;
            return;
        }
    }

    /**
     * Sets whether the proxy server should be used on the request.
     *
     * @param use True if the proxy server is to be used, false otherwise.
     */

    public void useProxy(boolean use)
    {
        if (ProxyServer == null)
        {
            useProxy = false;
        }
        else
        {
            useProxy = use;
        }
    }
}
