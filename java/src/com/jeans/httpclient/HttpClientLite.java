package com.jeans.httpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import weiss.util.LinkedList;

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
 * HttpClient is the base class of the HttpClient package.  It is used to
 * send an HTTP 1.1 request according to RFC 2616.  It is written in such
 * a way that HTTP extension commands can be used, such as those specified
 * by the WebDAV proposal (RFC 2518).  Redirection and authentication are
 * handled automatically.
 * 
 * 
 * This is changed version of HttpClient, with only basic HTML command, without
 * use of proxies, cookies or authentication.
 * 
 */

public class HttpClientLite
{
    URL HttpUrl;
    String user, pass;
    String method;

    weiss.util.LinkedList ResponseHeaderNames;
    weiss.util.LinkedList ResponseHeaderValues;
    weiss.util.LinkedList RequestHeaderNames;
    weiss.util.LinkedList RequestHeaderValues;

    String StatusText;
    String ResponseText;
    String ResponseHeaders;

    int Status;
    boolean useCookies;

    //HttpProxy proxy;
    //Cookies cookies;

    /**
     * Constructor
     */
    public HttpClientLite() 
    {
        //cookies = new Cookies();
        useCookies = false;
    }


    /**
     * Processes the HTTP response to adjust the request for HTTP
     * authentication
     */
/*
    private void Authenticate() {
        String wwwauthen = "";
        String auth = "";

        if (Status == 401)
            wwwauthen = getResponseHeader("WWW-Authenticate");
        else if (Status == 407)
            wwwauthen = getResponseHeader("Proxy-Authenticate");

        if (wwwauthen == "")
            return;

        if (Status == 401 && (user == null || user.length() == 0))
            return;
        else if (
            Status == 407 && (proxy.ProxyUser == null || proxy.ProxyUser.length() == 0))
            return;

        if (Status == 401)
            removeHeader("Authorization");
        else if (Status == 407)
            removeProxyHeader("Proxy-Authorization");

        java.util.StringTokenizer u = new java.util.StringTokenizer(wwwauthen, " ");
        String authtype = u.nextToken();

        if (authtype.compareTo("Basic") == 0) {
            BasicAuthen ba = new BasicAuthen();
            auth = ba.createHeader(user, pass);
        }
        else if (authtype.compareTo("Digest") == 0) {
            weiss.util.Random rand =
                new weiss.util.Random((int) new java.util.Date().getTime());
            java.util.StringTokenizer v =
                new java.util.StringTokenizer(HttpUrl.getFile(), "?");
            DigestAuthen da = new DigestAuthen(user, pass, method, v.nextToken());
            da.parseHeader(wwwauthen);

            String d = "";

            for (int y = 0; y < 16; y++) {
                String q = Integer.toHexString(rand.nextInt(256));
                if (q.length() == 1)
                    d += "0";
                d += q;
            }

            da.setCNonce(d);
            auth = da.createHeader();
        }

        if (Status == 401) {
            RequestHeaderNames.add(new String("Authorization"));
            RequestHeaderValues.add(auth);
        }
        else if (Status == 407) {
            proxy.ProxyHeader.add(new String("Proxy-Authorization"));
            proxy.ProxyHeaderValues.add(auth);
        }

        return;
    }
*/     
      
    /**
     * Enables/disables cookie support.  Support is enabled by default.
     * This can be toggled before or after a request is opened, but not after
     * the request is sent.
     *
     * @param use True to enable cookie support, false to disable.
     */
/*    public void enableCookies(boolean use) 
    {
        useCookies = use;
    }
*/


    /**
     * Private function used to find a specific header inside the
     * request header table.  Returns -1 if not found, or index
     * if it is.
     *
     * @param header Name of header to find
     * @return Index of header on success, -1 on failure
     */
    @SuppressWarnings("unused")
    private int findRequestHeader(String header) 
    {
        int indx;
        for (indx = 0; indx < RequestHeaderNames.size(); indx++) {
            String u = (String) RequestHeaderNames.get(indx);
            if (u.toLowerCase().equals(header.toLowerCase()) == true)
                return indx;
        }
        return -1;
    }


    /**
     * Private function used to find a specific header inside the
     * response header table.  Returns -1 if not found, or index
     * if it is.
     *
     * @param header Name of header to find
     * @return Index of header on success, -1 on failure
     */

    private int findResponseHeader(String header) {
        int indx;
        for (indx = 0; indx < ResponseHeaderNames.size(); indx++) {
            String u = (String) ResponseHeaderNames.get(indx);
            if (u.toLowerCase().equals(header.toLowerCase()) == true)
                return indx;
        }
        return -1;
    }
    /**
     * Returns all response headers from the previous HTTP request as
     * a string that can be tokenized by a new-line character ('\n').
     *
     * @return All headers from the previous HTTP request
     */

    public String getAllHeaders() {
        return new String(ResponseHeaders);
    }
    /**
     * Returns a specific response header from the last HTTP request.
     * Header names are case-insensitive.  If the header could not
     * be found, a blank string is returned.
     *
     * @param header Header name to retrieve - case-insensitive
     * @return Value of the specified header
     */

    public String getResponseHeader(String header) 
    {
        int indx = findResponseHeader(header);
        if (indx < 0)
            return "";
        else
            return (String) ResponseHeaderValues.get(indx);
    }
    /**
     * Returns the body of the last successful HTTP response.
     *
     * @return Body returned by last request
     */

    public String getResponseText() {
        return ResponseText;
    }
    /**
     * Returns the status code from the last HTTP request, or -1 if
     * there was an internal error.
     *
     * @return HTTP status code of last request
     */

    public int getStatusCode() {
        return Status;
    }


    /**
     * Private function called by both <code>open</code> methods to
     * initialize a few internal variables.
     */
    private void Init() 
    {
        Status = -1;

        ResponseText = null;
        StatusText = null;
        ResponseHeaders = null;
        ResponseHeaderNames = new LinkedList();
        ResponseHeaderValues = new LinkedList();
        RequestHeaderNames = new LinkedList();
        RequestHeaderValues = new LinkedList();
        //proxy = new HttpProxy();
        //proxy.useProxy(false);

        user = null;
        pass = null;
    }


    /**
     * Opens the client for a new connection with a specified URL and
     * method.  Returns true if successful, throws an exception if not.
     *
     * @param m HTTP method or extension method
     * @param url Absolute resource URL to retrieve
     * @return 
     * @throws InvalidProtocolException 
     * @throws MalformedURLException 
     */
    public boolean open(String m, String url)
        throws InvalidProtocolException, MalformedURLException {
        method = m.toUpperCase();
        HttpUrl = new URL(url);

        if (HttpUrl.getProtocol().toLowerCase().compareTo("http") != 0)
            throw new InvalidProtocolException("Protocol must be http");

        Init();

        return true;
    }
    /**
     * Opens the client for a new connection with a specified URL and
     * method.  Returns true if successful, throws an exception if not.
     * This one allows you to specify a username and password if one
     * is available and needed.
     *
     * @param m HTTP method or extension method
     * @param url Absolute resource URL to retrieve
     * @param u Username for authentication
     * @param p Password for authentication
     * @return 
     * @throws InvalidProtocolException 
     * @throws MalformedURLException 
     */

    public boolean open(String m, String url, String u, String p)
        throws InvalidProtocolException, MalformedURLException {
        open(m, url);

        user = new String(u);
        pass = new String(p);

        return true;
    }
    /**
     * Used internally to parse the headers returned from the
     * server into an internal table.
     */

    private void parseHeaders() 
    {
        java.util.StringTokenizer u = new java.util.StringTokenizer(ResponseHeaders, "\n");
        
        while (u.hasMoreTokens()) 
        {
            
            String header = u.nextToken();

//            System.out.println(header);
            
            java.util.StringTokenizer v = new java.util.StringTokenizer(header, ":");
            ResponseHeaderNames.add(v.nextToken().toLowerCase());
            v.nextToken(" ");
            ResponseHeaderValues.add(v.nextToken("").trim());

            //if (header.startsWith("Set-Cookie") && useCookies)
            //    cookies.addCookie(header, HttpUrl.getHost());
        }
    }
    /**
     * Used internally to parse the status line returned from the
     * server.
     */

    private void parseStatusCode() 
    {
        if (StatusText==null) 
            return;
        
        StringTokenizer u = new StringTokenizer(StatusText, " ");
        
        if (u.hasMoreTokens()) 
        {
            u.nextToken();
            if (u.hasMoreTokens()) 
            {
                Status = new Integer(u.nextToken()).intValue();
            }
        }
    }



    /**
     * Processes the HTTP response to adjust the request for a redirection.
     * It also handles the 305 code which tells a client a proxy server is
     * required.
     */
    private void Redirect() 
    {
        String newurl = getResponseHeader("Location");
        removeHeader("Authorization");

        if (Status == 305) 
        {
            //proxy.setProxyServer(newurl);
            //proxy.useProxy(true);
        }
        else {
            try {
                HttpUrl = new URL(newurl);
            }
            catch (MalformedURLException e) {
                HttpUrl = null;
            }
        }
    }


    /**
     * Removes a header from the request header table.
     *
     * @param header Header to remove from request header table
     */
    public void removeHeader(String header) {

        int index = -1;
        for (int indx = 0; indx < RequestHeaderNames.size(); indx++) {
            String u = (String) RequestHeaderNames.get(indx);
            if (u.toLowerCase().equals(header.toLowerCase())) {
                index = indx;
                break;
            }
        }

        if (index < 0)
            return;

        RequestHeaderNames.remove(index);
        RequestHeaderValues.remove(index);
    }


    /**
     * Removes a specified header from the proxy header list.
     *
     * @param header Name of the header to remove
     */
/*    public void removeProxyHeader(String header) 
    {
        proxy.removeProxyHeader(header);
    }
*/

    /**
     * Resets the list of headers sent to the proxy only.
     */
/*    public void resetProxyHeaders() 
    {
        proxy.resetProxyHeaders();
    }
*/

    /**
     * Sends the request with the specified body (or null if a body is
     * not needed).  Returns true on success (2xx code returned) or
     * false on failure or internal error.
     *
     * @param body Body of HTTP request, or <code>null</code> if not
     *             needed
     * @return <code>true</code> on success, <code>false</code> on failure
     */
    public boolean send(String body) 
    {
        boolean auth = false;
        @SuppressWarnings("unused")
        boolean proxyauth = false;
        int redir = 0;
        int code;


        
        for (;;) 
        {
            //if (proxy.useProxy)
            //    code = sendProxyRequest(body);
            //else
            
            code = sendRequest(body);

            Status = code;

            if (code == -1)
                return false;
            if (code / 100 <= 1)
                return false;
            if (code / 100 == 2)
                return true;
            if (code / 100 >= 5)
                return false;
            if (code / 100 == 4 && code != 401 && code != 407)
                return false;
            if (code / 100 == 3 && redir <= 5) {
                Redirect();
                redir++;
                auth = false;
            }
            if (code / 100 == 3 && redir > 5)
                return false;
            if (code == 407)
                return false;
                 
                /*
                && proxy.ProxyUser == null)
                return false;
            if (code == 407 && proxy.ProxyUser.length() == 0)
                return false;
            if (code == 407 && proxyauth)
                return false;
            if (code == 407) {
                //Authenticate();
                //proxyauth = true;
            }     */
            if (code == 401 && user == null)
                return false;
            if (code == 401 && user.length() == 0)
                return false;
            if (code == 401 && auth)
                return false;
            if (code == 401) {
                //Authenticate();
                //auth = true;
                //redir = 0;
            }
        }
    }
    /**
     * Sends the request to the proxy server for processing.  Post processing
     * of the response is performed using the same set of functions that
     * would otherwise process a non-proxy response.
     *
     * @param body Body of the HTTP request
     */
/*
    protected int sendProxyRequest(String body) {
        String request, line;
        int x, port;

        Socket s;
        OutputStream os;
        OutputStreamWriter osw;
        //    PrintStream out;    
        BufferedWriter out;
        InputStream is;
        BufferedReader in;

        // First, assemble all the request

        request = method + " " + HttpUrl.toString() + " HTTP/1.1\r\n";
        request += "Host: " + HttpUrl.getHost() + "\r\n";
        request += "Connection: Close\r\n";

        if (body != null)
            x = body.length();
        else
            x = 0;
        request += "Content-Legnth: " + x + "\r\n";

        // Add the headers

        for (x = 0; x < RequestHeaderNames.size(); x++) {
            String t = (String) RequestHeaderNames.get(x);
            t += ": " + (String) RequestHeaderValues.get(x);
            t += "\r\n";
            request += t;
        }

        // Add any proxy-specific headers

        for (x = 0; x < proxy.ProxyHeader.size(); x++) {
            String t = (String) proxy.ProxyHeader.get(x);
            t += ": " + (String) proxy.ProxyHeaderValues.get(x);
            t += "\r\n";
            request += t;
        }

        if (useCookies)
            request += cookies.getCookies(HttpUrl);

        request += "\r\n";

        // Add the body, if there is one

        if (body != null)
            request += body;

        // Request assembled, open the socket to the proxy server and send it off 

        if (proxy.ProxyServer.getPort() < 0)
            port = 80;
        else
            port = proxy.ProxyServer.getPort();

        try {
            s = new Socket(proxy.ProxyServer.getHost(), port);
        }
        catch (UnknownHostException e) {
            return -1;
        }
        catch (SecurityException msg) {
            System.out.print(msg);

            return -1;
        }
        catch (IOException e) {
            return -1;
        }
        try {
            is = s.getInputStream();
            os = s.getOutputStream();
            in = new BufferedReader(new InputStreamReader(is));
            out = new BufferedWriter(new OutputStreamWriter(os));

        }
        catch (IOException e) {

            return -1;
        }

        try {
            out.write(request);
            out.flush();
        }
        catch (IOException e) 
        {
            System.out.println(e);
        }

        try {
            //  Parse feedback as it comes in.  First line is the status. 

            StatusText = in.readLine();
            //        System.out.print("StatusText=" + StatusText); // sepp

            //  Next few lines until a "" is the header text. 

            line = in.readLine();
            while (line.length() > 0) {
                if (line != null)
                    ResponseHeaders += line + "\n";
                line = in.readLine();
            }
            
            //  Next, the response text.  Read until the input is null. 

            line = in.readLine();
            while (line != null) {
                ResponseText += line + "\n";
                line = in.readLine();
            }

            s.close();

        }
        catch (IOException e) {
            return -1;
        }

        //  Parse the headers and the status code 

        parseHeaders();
        parseStatusCode();

        return Status;
    }
*/     
     
     
/**
 * This function actually sends the request, parses the returned status
 * line and headers, and returns the status code, or -1 if there was
 * an internal error.  Used internally.
 *
 * @param body Body of HTTP request
 * @return Status code from the request, -1 on internal error
 */

private int sendRequest(String body) {
    Socket s;
    OutputStream os;
    @SuppressWarnings("unused")
    OutputStreamWriter osw;
    //    PrintStream out;    
    BufferedWriter out;
    InputStream is;
    BufferedReader in;
    int port;
    String request;
    String line;
    int len;
    int x;

    Status = -1;

    /* Is HttpUrl == null (bad redirect request)? */

    if (HttpUrl == null)
        return -1;

    /* Clear a few variables */

    ResponseHeaderNames.clear();
    ResponseHeaderValues.clear();
    ResponseText = "";
    ResponseHeaders = "";
    StatusText = "";

    /* Is there a request body? */

    if (body != null)
        len = body.length();
    else
        len = 0;

    /* Determine what port to use, 80 being the default */

    port = HttpUrl.getPort();
    if (port < 0)
        port = 80;

    /* Next, piece together the request */

    request = method + " " + HttpUrl.getFile() + " HTTP/1.1\r\n";
    request += "Host: " + HttpUrl.getHost() + "\r\n";
    request += "Content-Length: " + len + "\r\n";
    request += "Connection: Close\r\n";

    for (x = 0; x < RequestHeaderNames.size(); x++) {
        String header = (String) RequestHeaderNames.get(x);
        header += ": ";
        header += (String) RequestHeaderValues.get(x);
        header += "\r\n";
        request += header;
    }

    

    /* Add cookies, if they're used */

    //if (useCookies)
    //    request += cookies.getCookies(HttpUrl);

    //request += "\r\n";

    if (body != null)
        request += body;

    /* Attempt to open the socket.  If it fails, return -1 */

    try {
        s = new Socket(HttpUrl.getHost(), port);
    }
    catch (UnknownHostException e) {
        return -1;
    }
    catch (IOException e) {
        return -1;
    }
    /*
        catch (java.security.AccessControlException e) {
            System.out.println("\nAccesControlException" + e);
            return -1;
     
        }
    
    */
    /* Get the streams necessary to communicate with the server.
     * Again, if an error occurs, return -1.
     */

    @SuppressWarnings("unused")
    InputStreamReader isr;

    try {
        is = s.getInputStream();
        os = s.getOutputStream();
        in = new BufferedReader(isr = new InputStreamReader(is));
        out = new BufferedWriter(new OutputStreamWriter(os));

    }
    catch (IOException e) {
        return -1;
    }

    /* No initial feedback from server, so send the request */

    //System.out.println(request);

    try {
        out.write(request);
        out.flush();
    }
    catch (IOException e) {
        System.out.println(e);
    }

    try {

        /* Parse feedback as it comes in.  First line is the status. */

        StatusText = in.readLine();

//System.out.println("\nSTATUS TEXT=[" + StatusText + "]");

        /* Next few lines until a "" is the header text. */

        line = in.readLine();
        while (line != null) {
			if (line.length() > 0)
			{
				ResponseHeaders += line + "\n";
	            line = in.readLine();
	            continue;
			}
			break;
				
        }
//System.out.println("\nRESPONSE HEADERS=[" + ResponseHeaders +"]");
/*        
        line = in.readLine();
        if (!line) {
            System.out.println("\nhttp response doesn't have valid header");
            s.close();
            return -1;
        }
        while (line.length() > 0) {
            if (line != null)
                ResponseHeaders += line + "\n";
            line = in.readLine();
        }
*/
        line = in.readLine();
        while (line != null) {
            ResponseText += line + "\n";
            line = in.readLine();
        }

//        System.out.println("\nRESPONSE TEXT=[" + ResponseText +"]");

        s.close();

    }
    catch (IOException e) 
    {
        System.out.println("\nXXX" + e);
        e.printStackTrace();
        return -1;
    }

    /* Parse the headers and the status code */

    parseHeaders();
    parseStatusCode();

    /*
    if (m_env.debug) {
        System.out.println("*******REQUEST*******");
        String f = Util.PrintHex(request, 32);
        System.out.print(f);
    }
    */
    return Status;
}

    /**
     * Allows the program to specify a username and password if one
     * is needed.
     *
     * @param u Username
     * @param p Password
     */
/*    public void setAuthentication(String u, String p) 
    {
        user = new String(u);
        pass = new String(p);
    }  
     
*/

    /**
     * Provides the proxy authentication information.
     *
     * @param user Proxy username
     * @param pass Proxy password
     */
/*    public void setProxyAuth(String user, String pass) 
    {
        proxy.setProxyAuth(user, pass);
    }
*/



    /**
     * Provides headers specific to the proxy server.  If the proxy were not
     * used to forward the request, the headers entered through this function
     * would not be sent.
     *
     * @param header Header name
     * @param value Header value
     */
/*
    public void setProxyHeader(String header, String value) {
        proxy.setProxyHeader(header, value);
    }
*/      
     
    /**
     * Allows a proxy server to be set.  The proxy server must be HTTP 1.1
     * compliant or the request will probably fail.
     *
     * @param server Hostname for the proxy server
     * @param port Access port for the server
     */
/*    public void setProxyServer(String server, int port) {
        proxy.setProxyServer(server, port);
    }
*/

    /**
     * Sets the specified request header to the specified value.  Header
     * names are case-insensitive.  If the header is already set, it
     * is overwritten.
     *
     * @param header Header name
     * @param value Value to set the header to
     */
    public void setRequestHeader(String header, String value) 
    {
        int indx;

        /* First, check to see if header is one that is supposed to be
         * set internally.
         */

        if (header.toLowerCase().equals("Content-Length".toLowerCase()))
            return;
        if (header.toLowerCase().equals("Authorization".toLowerCase()))
            return;
        if (header.toLowerCase().equals("Host".toLowerCase()))
            return;
        if (header.toLowerCase().equals("Connection".toLowerCase()))
            return;
        if (header.toLowerCase().equals("Content-MD5".toLowerCase()))
            return;

        /* If the header is already set, remove the original */

        int index = -1;
        for (indx = 0; indx < RequestHeaderNames.size(); indx++) {
            String u = (String) RequestHeaderNames.get(indx);
            if (u.toLowerCase().equals(header.toLowerCase())) {
                index = indx;
                break;
            }
        }

        //        indx = RequestHeaderNames.indexOf(header);

        if (index >= 0) {
            RequestHeaderNames.remove(index);
            RequestHeaderValues.remove(index);
        }

        RequestHeaderNames.add(header);
        RequestHeaderValues.add(value);
    }
    /**
     * Determines if the request should be forwarded through the proxy server
     * or if it should be sent to the HTTP server.
     *
     * @param use True to use the proxy, false to not.
     */
/*    public void useProxyServer(boolean use) 
    {
        proxy.useProxy(use);
    }

*/     
}