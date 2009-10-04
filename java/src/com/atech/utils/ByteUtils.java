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


public class ByteUtils
{
    
    public static final int BIT_ORDER_BIG_ENDIAN = 1;
    public static final int BIT_ORDER_LITTLE_ENDIAN = 2;
    
    
    protected int byte_order = BIT_ORDER_BIG_ENDIAN;
    protected byte[] byte_arr = null;
    
    public void readByteArray(byte[] array)
    {
        this.byte_arr = array;
    }
    
    
    public byte getByteFromArray(int offset)
    {
        return getByte(this.byte_arr, offset);
    }
    
    
    public byte getByte(byte[] arr, int offset)
    {
        return arr[offset];
    }
    
    
    public int getIntFromArray(int offset)
    {
        return getInt(this.byte_arr, offset);
    }

    
    public int getInt(byte[] arr, int offset)
    {
        int val = 0;
        
        if (this.byte_order==ByteUtils.BIT_ORDER_BIG_ENDIAN)
        {
            val += (arr[offset] << 8) & 0xff00;
            val = (val + (arr[offset+1] & 0xff));
        }
        else
        {
            val += (arr[offset+1] << 8) & 0xff00;
            val = (val + (arr[offset] & 0xff));
        }

        return val;
        
    }
    
    
    public String getStringFromArray(int offset, int size)
    {
        return getString(this.byte_arr, offset, size);
    }
    
    
    
    public String getString(byte[] arr, int offset, int size)
    {
        try
        {
            char[] destinationArray = new char[size];
            
            for(int i=0; i<size; i++)
            {
                destinationArray[i] = (char)arr[offset+i];
            }
            
            
//            System.arraycopy(arr, offset, destinationArray, 0, size);
            String str = new String(destinationArray); //, 0, size);
            return str;
        }
        catch(Exception ex)
        {
            System.out.println("Ex: " + ex);
            return null;
        }
    }
    
    
    public String getAsciiFromArray(int offset, int size)
    {
        return getAscii(this.byte_arr, offset, size);
    }
    
    
    
    public String getAscii(byte[] arr, int offset, int size)
    {
        try
        {
            char[] destinationArray = new char[size];
            
            for(int i=0; i<size; i++)
            {
                //byte b = (byte)(arr[offset+i] +  0x41);
                destinationArray[i] = (char)((arr[offset+i] +  0x41));
            }
            
            
//            System.arraycopy(arr, offset, destinationArray, 0, size);
            String str = new String(destinationArray); //, 0, size);
            return str;
        }
        catch(Exception ex)
        {
            System.out.println("Ex: " + ex);
            return null;
        }
    }
    
    
    
    public byte[] getByteSubArray(byte[] arr, int start, int end, int length)
    {
        byte[] arr_out = new byte[length];
        int j=0;
        
        for(int i=start; i<(arr.length-end); i++)
        {
            arr_out[j] = arr[i];
            j++;
        }
        
        return arr_out;
    }
    
    
    
    public String getCorrectHexValue(byte inp)
    {
        String hx = Integer.toHexString((char)inp);
        
        if (hx.length()==0)
            return "00";
        else if (hx.length()==1)
            return "0" + hx;
        else if (hx.length()==2)
            return hx;
        else if (hx.length()==4)
            return hx.substring(2);
        else
            System.out.println("HEX ERROR !!!!!!!!!!!!!!!!");
        
        //System.out.print(Integer.toHexString((char)arr[i]) + " ");

        
        return null;
    }
    
    
    public void showByteArrayHex(byte[] arr)
    {
        System.out.print("Byte array: ");
        
        for(int i=0; i<arr.length; i++)
        {
            System.out.print(getCorrectHexValue(arr[i]) + " ");
            //getCorrectHexValue(arr[i]);
            //System.out.print(Integer.toHexString((char)arr[i]) + " ");
        }
        
        System.out.print("\n");
        
    }
    
    public void showByteArray(byte[] arr)
    {
        System.out.println("Byte array: ");
        
        for(int i=0; i<arr.length; i++)
        {
            System.out.print(arr[i] + " ");
        }
        
    }
    
    
    
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