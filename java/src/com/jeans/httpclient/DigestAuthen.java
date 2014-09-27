package com.jeans.httpclient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
 * Used internally to provide HTTP DigestAuthentication as specified in
 * RFC 2617.  There are two limitations to this class currenly.  This
 * cannot handle a qop of auth-int, nor can it handle both auth and auth-int
 * being specified in the WWW-Authenticate header.
 */

public class DigestAuthen
{
    String user;
    String pass;
    String method;
    String file;

    private String realm;
    private String nonce;
    private String count;
    private String cnonce;
    private String qop;
    private String opaque;
    private String algor;
    @SuppressWarnings("unused")
    private boolean stale;
    private int ncount;

    MessageDigest MD;

    /**
     * Sets up the object for creating a header with the specified username,
     * password, HTTP method, and URL path.
     *
     * @param u Username
     * @param p Password
     * @param m HTTP method
     * @param f URL path
     */

    public DigestAuthen(String u, String p, String m, String f)
    {
        user = u;
        pass = p;
        method = m.toUpperCase();
        file = f;

        algor = "MD5";
        try
        {
            MD = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            MD = null;
        }
    }

    /**
     * Allows the username and password to be overridden.
     *
     * @param u Username
     * @param p Password
     */

    public void setAuthentication(String u, String p)
    {
        user = u;
        pass = p;
    }

    /**
     * @param q
     */
    public void setQop(String q)
    {
        if (q.toLowerCase().equals("auth-info".toLowerCase()))
            return;
        qop = q;
    }

    /**
     * @param c
     */
    public void setCNonce(String c)
    {
        cnonce = c;
    }

    /**
     * @param n
     */
    public void setNonce(String n)
    {
        String c;
        if (nonce == null)
        {
            ncount = 1;
        }
        else if (n.compareTo(nonce) != 0)
        {
            ncount = 1;
        }
        else
        {
            ncount++;
        }
        nonce = n;
        c = Integer.toHexString(ncount);
        count = new String("00000000").substring(c.length()) + c;
    }

    /**
     * Set Method
     * 
     * @param meth
     */
    public void setMethod(String meth)
    {
        method = meth.toUpperCase();
    }

    /**
     * Set URI
     * 
     * @param uri
     */
    public void setURI(String uri)
    {
        file = uri;
    }

    /**
     * Set Realm
     * 
     * @param r
     */
    public void setRealm(String r)
    {
        realm = r;
    }

    /**
     * Set Algorithm
     * 
     * @param algorithm
     * @return
     */
    public boolean setAlgorithm(String algorithm)
    {
        if (algorithm == null)
        {
            algor = "MD5";
        }
        else if (algorithm.toUpperCase().compareTo("MD5") != 0 && algorithm.toUpperCase().compareTo("MD5-SESS") != 0)
            return false;
        else
        {
            algor = algorithm.toUpperCase();
        }
        return true;
    }

    /**
     * Parses the specified WWW-Authenticate header from the last
     * request and sets the appropriate internal values.
     *
     * @param header WWW-Authenticate header to parse
     */

    public void parseHeader(String header)
    {
        java.util.StringTokenizer ST = new java.util.StringTokenizer(header.trim(), " ");
        String R = ST.nextToken(); // "Digest"

        while (ST.hasMoreTokens())
        {
            R = ST.nextToken(",").trim();

            java.util.StringTokenizer T = new java.util.StringTokenizer(R, "=");
            String S = T.nextToken().toLowerCase();

            T.nextToken("\"");

            if (S.compareTo("qop") == 0)
            {
                qop = T.nextToken("\"");
            }
            else if (S.compareTo("nonce") == 0)
            {
                setNonce(T.nextToken("\""));
            }
            else if (S.compareTo("realm") == 0)
            {
                realm = T.nextToken("\"");
            }
            else if (S.compareTo("opaque") == 0)
            {
                opaque = T.nextToken("\"");
            }
            else if (S.compareTo("algorithm") == 0)
            {
                algor = T.nextToken("\"");
            }
        }
    }

    /**
     * Creates an Authorization header to be sent with the next HTTP
     * request from the internal variables.
     *
     * @return Authorization header for next HTTP request
     */

    public String createHeader()
    {
        String Header = "Digest ";

        if (user != null)
        {
            Header = Header + "username=\"" + user + "\", ";
        }

        if (realm != null)
        {
            Header = Header + "realm=\"" + realm + "\", ";
        }

        if (nonce != null)
        {
            Header = Header + "nonce=\"" + nonce + "\", ";
        }

        if (file != null)
        {
            Header = Header + "uri=\"" + file + "\", ";
        }

        if (qop != null)
        {
            Header = Header + "qop=\"" + qop + "\", ";
        }

        if (count != null)
        {
            Header = Header + "nc=" + count + ", ";
        }

        if (algor != null)
        {
            Header = Header + "algorithm=\"" + algor + "\", ";
        }
        else
        {
            Header = Header + "algorithm=\"MD5\", ";
        }

        if (cnonce != null)
        {
            Header = Header + "cnonce=\"" + cnonce + "\", ";
        }

        if (opaque != null)
        {
            Header = Header + "opaque=\"" + opaque + "\", ";
        }

        Header = Header + "response=\"" + Digest() + "\"";

        return Header;
    }

    /**
     * Creates the response digest from the internal variables as directed
     * by RFC 2617.
     *
     * @return Digest created from internal variables
     */

    private String Digest()
    {
        String Digest = new String();
        String A1, A2;

        if (user.length() == 0 || pass.length() == 0 || realm.length() == 0 || file.length() == 0
                || nonce.length() == 0 || cnonce.length() == 0 || method.length() == 0)
            return Digest;

        A1 = getA1();
        A2 = getA2();

        MD.reset();

        if (qop == null)
        {
            Digest = A1 + ":" + nonce + ":" + A2;
        }
        else
        {
            Digest = A1 + ":" + nonce + ":" + count + ":" + cnonce + ":" + qop + ":" + A2;
        }

        MD.update(Digest.getBytes());

        Digest = HexString.convert(MD.digest(), 16);

        return Digest;
    }

    /**
     * Creates the 'A1' value for the digest.
     *
     * @return A1 value
     */

    private String getA1()
    {
        String Digest;

        MD.reset();

        Digest = user + ":" + realm + ":" + pass;

        MD.update(Digest.getBytes());

        Digest = HexString.convert(MD.digest(), 16);

        if (algor.toLowerCase().compareTo("md5-sess") != 0)
            return Digest;

        Digest += ":" + nonce + ":" + cnonce;

        MD.update(Digest.getBytes());

        Digest = HexString.convert(MD.digest(), 16);
        return Digest;
    }

    /**
     * Creates the 'A2' value for the digest.
     *
     * @return A2 value
     */

    private String getA2()
    {
        String Digest;

        MD.reset();

        Digest = method + ":" + file;

        MD.update(Digest.getBytes());

        Digest = HexString.convert(MD.digest(), 16);
        return Digest;
    }
}
