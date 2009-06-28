package com.atech.utils;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy
 *
*/


public class HexUtils
{

    
    
    
    
    
    /**
     * Reconvert.
     * 
     * @param _strHex the _str hex
     * 
     * @return the byte[]
     */
    public byte[] reconvert(String _strHex) {

        if (_strHex == null)
        {
            System.out.println("#error HexString: reconvert null?");
            
            return null;
        }
        String strHex = _strHex.toUpperCase();

        int iLen = strHex.length();

        if ((iLen % 2) != 0)
        {
            
//            System.out.println("#error HexString: iLen="+iLen);
            iLen -=1;
        }

        byte[] buffer = new byte[iLen / 2];

        for (int i = 0; i < iLen/2; i++) {
            
            int ic1, ic2;

            char c1 = strHex.charAt(2 * i);
            if (Character.isDigit(c1))
                ic1 = c1 - '0';
            else
                ic1 = c1 - 'A' + 10;

            char c2 = strHex.charAt(2 * i + 1);
            if (Character.isDigit(c2))
                ic2 = c2 - '0';
            else
                ic2 = c2 - 'A' + (char) 10;

            buffer[i] = (byte)(ic1 * 16 + ic2);
        }

        return buffer;
        
    }
    
    
}
