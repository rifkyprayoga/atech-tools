package com.atech.utils.data;

// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * 
 * ByteUtils - Utils for working with bytes and bytes array Copyright (C) 2009
 * Andy (Aleksander) Rozman (Atech-Software)
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
 * 
 * @author Andy
 *
 */

public class ShortUtils extends HexUtils
{

    public String getDebugShortArray(short[] arr)
    {
        StringBuilder sb = new StringBuilder();
        // System.out.println("Byte array: ");

        sb.append("Byte array: ");

        for (short element : arr)
        {
            sb.append(element + " ");
            // System.out.print(arr[i] + " ");
        }

        return sb.toString();
    }

    public short[] getShortSubArray(short[] arr, int start, int length)
    {
        short[] arr_out = new short[length];
        int j = 0;

        for (int i = start; i < start + length; i++)
        {
            arr_out[j] = arr[i];
            j++;
        }

        return arr_out;
    }

}
