package com.jeans.httpclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

public class LocalIPAddress
{
    /**
     * Address
     */
    public String address = null;

    /**
     * Constructor
     */
    public LocalIPAddress()
    {

        InetAddress ia;

        try
        {
            ia = InetAddress.getLocalHost();
        }

        catch (UnknownHostException e)
        {
            System.out.print("1::" + e);
            return;
        }
        address = ia.getHostAddress();

        // System.out.print("LocalAddressOK:" + address);

        return;

    }

    /**
     * Get Gateway Address
     * 
     * @param TestURL
     * @param TestPort
     * @return
     */
    public String getGatewayAddress(String TestURL, int TestPort)
    {

        Socket s;

        try
        {
            s = new Socket(TestURL, TestPort);
        }
        catch (IOException e)
        {
            System.out.println("###socket error: " + e);
            return new String("");
        }
        /*
        catch (UnknownHostException e) {
            System.out.println("###unknown host: " + e);
            return null;
        }*/

        InetAddress ia = s.getLocalAddress();

        try
        {
            s.close();
        }
        catch (IOException e)
        {
            System.out.println("###socket error: " + e);
            return new String("");
        }

        return new String(ia.getHostAddress());

    }
}