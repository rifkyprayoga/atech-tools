package com.jeans.httpclient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *  This file is part of ATech Tools library.
 *  
 *  This is imported library HttpClient 
 *  (See longer/original comment under this one, if available) 
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *
 */


public class Util
{
    /**
     * Address
     */
    public String address = null;

    /**
     * Constructor
     * 
     * @param testURL
     * @param port
     */
    public Util(String testURL, int port)
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

        /*		
        		Socket s;
        
        		try {
        			s = new Socket(testURL, port);
        		}
        
        		catch (UnknownHostException e) {
        			System.out.print("1::"+e);
        			return;
        		}
        		catch (SecurityException e) {
        			System.out.print("2::"+e);
        
        			return;
        		}
        		catch (IOException e) {
        			System.out.print("3::"+e);
        
        			return;
        		}
        
        		InetAddress ia = s.getLocalAddress();
        
        		
        		address = new String(ia.getHostAddress());
        */

        System.out.print("LocalAddressOK:" + address);

        return;

    }
    
    
    /**
     * Print Hex
     * 
     * @param str
     * @param alignment
     * @return
     */
    public static String PrintHex(String str, int alignment)
    {
        final char hex[] =
            {
                '0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                'A',
                'B',
                'C',
                'D',
                'E',
                'F' };
        int len = str.length();
        int lines = str.length() / alignment;
         StringBuffer result = new StringBuffer();
        char line[] = null;

        for (int l = 0; l < lines + 1; l++)
        {

            //	        line = new StringBuffer((int)(3 * alignment + 2));
            line = new char[3 * alignment + 1];
            for (int j = 0; j < 3 * alignment + 1; j++)
                line[j] = ' ';
            line[2 * alignment] = '\t';

            for (int i = 0; i < alignment; i++)
            {

                int pos = l * alignment + i;

                if (pos >= len)
                	break;
                	
                
                char c = str.charAt(pos);
                int upper = (int) c / 16;
                int lower = (int) c % 16;

                line[2 * i] = hex[upper];
                line[(2 * i) + 1] = hex[lower];

                if (Character.isISOControl(c))
                    line[2 * alignment + i + 1] = '.';
                else
	                line[2 * alignment + i + 1] = new Character(c).toString().charAt(0);

            }

            result.append(line);
            result.append('\n');
        }

        return result.toString();

    }
}