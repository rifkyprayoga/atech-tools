package com.jeans.httpclient;


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
 * This is a class used internally by the package to provide support for
 * HTTP Basic Authentication as specified in RFC 2617.
 */

class BasicAuthen
{
    /**
     * This takes a username and password and creates a header that can
     * be added directly to the request header table.
     *
     * @param user Username
     * @param pass Password
     * @return Authorization header for a request
     */

    public String createHeader(String user, String pass)
    {
        String d = user + ":" + pass;
        String header = "Basic " + encode(d);
        return header;
    }

    /**
     * Encodes the provided string into Base64
     *
     * @param d String to encode
     * @return Base64 encoded string
     */

    private String encode(String d)
    {
        String c = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                   "abcdefghijklmnopqrstuvwxyz" +
                   "0123456789+/";

        byte [] code = c.getBytes();
        byte [] s = d.getBytes();

        int x;
        int y = d.length() - (d.length() % 3);

        byte [] coded = new byte[4];
        String dest = "";

        for(x = 0; x < y; x += 3)
        {
            coded[3] = code[s[x + 2] % 64];
            coded[0] = code[s[x] >> 2];

            coded[1] = new Integer((s[x] % 4) << 4).byteValue();
            coded[1] += s[x + 1] >> 4;
            coded[1] = code[coded[1]];

            coded[2] = new Integer((s[x + 1] % 16) << 2).byteValue();
            coded[2] += s[x + 2] / 64;
            coded[2] = code[coded[2]];

            dest += new String(coded);
        }

        x = y;

        if(s.length % 3 == 0) return dest;

        if(s.length % 3 == 1)
        {
            coded[2] = (byte)'=';
            coded[3] = (byte)'=';

            coded[0] = code[s[x] >> 2];
            coded[1] = code[new Integer((s[x] % 4) << 4).byteValue()];

            dest += new String(coded);
        }

        if(s.length % 3 == 2)
        {
            coded[3] = (byte)'=';

            coded[0] = code[s[x] >> 2];
            coded[1] = new Integer((s[x] % 4) << 4).byteValue();
            coded[1] += s[x + 1] >> 4;
            coded[1] = code[coded[1]];

            coded[2] = code[new Integer((s[x + 1] % 16) << 2).byteValue()];

            dest += new String(coded);
        }

        return dest;
    }
}


