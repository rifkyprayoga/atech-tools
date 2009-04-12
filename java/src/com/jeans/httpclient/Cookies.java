package com.jeans.httpclient;

import java.net.URL;

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
 * The cookie support provided in this class is limited.  The expirations of
 * the cookies is currently not handled because whenever the HttpClient
 * object is destroyed, the cookie list goes with it.  So, no matter what
 * the expiration on the cookie is, once the object is destroyed, all cookies,
 * regardless of expiration, go with it.  For this reason, this feature should
 * not be used on any client software that will be used as a persistent
 * client, such as a web browser.
 * <p>
 * This class is used internally by HttpClient and does not currently allow
 * retrieval of the cookie list or adding saved cookies to the list.
 */

class Cookies
{
    LinkedList cookies;

    public Cookies()
    {
        cookies = new LinkedList();
    }

    /**
     * Takes cookies valid for the specified URL and puts them into a
     * preformatted header string.
     *
     * @param uri URL object for the current request URI.
     * @return 
     */
    public String getCookies(URL uri)
    {
        CookieNode l;
        @SuppressWarnings("unused")
        String temp, temp2;
        String header = "Cookie: ";

        temp = uri.getHost();
        
        for(int x = 0; x < cookies.size(); x++)
        {
            l = (CookieNode)cookies.get(x);

            if(!temp.endsWith(l.domain)) continue;
            if(l.path.length() > uri.getFile().length()) continue;
            if(!l.path.startsWith(uri.getFile())) continue;

            if(x > 0 && header.length() != 8) header += "; ";
            header += l.value;
        }

        if(header == "Cookie: ") header = "";
        else header += "\r\n";
        return header;
    }

    /**
     * Adds a specified cookie to the list, assuming it's valid for the
     * specified domain.
     *
     * @param header Set-Cookie header from the server's response
     * @param origdom Host name for the current request URI
     */

    public void addCookie(String header, String origdom)
    {
        CookieNode h = new CookieNode();
        @SuppressWarnings("unused")
        String temp, temp2, cookie;

        temp = origdom;
        java.util.StringTokenizer token = new java.util.StringTokenizer(header, ": ");
        token.nextToken();
        cookie = token.nextToken();

        token = new java.util.StringTokenizer(cookie, ";");
        h.value = token.nextToken();

        while(token.hasMoreTokens())
        {
            String name, value;
            String o = token.nextToken().trim();

            java.util.StringTokenizer y = new java.util.StringTokenizer(o, "=");
            name = y.nextToken();
            value = y.nextToken();

            if(name.toLowerCase().equals("domain") == true)
            {
                h.domain = value;
                if(value.charAt(0) != '.') return;
                if(!origdom.endsWith(value)) return;
                temp2 = origdom.substring(0, origdom.length() - value.length());
                if(temp2.indexOf('.') < 0) return;
            }
            else if(name.toLowerCase().equals("path") == true)
            {
                h.path = value;
            }
            else if(name.toLowerCase().equals("max-age") == true)
                h.maxage = Integer.valueOf(value).intValue();
        }

        cookies.add(h);

    }
}
