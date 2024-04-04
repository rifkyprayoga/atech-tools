package com.atech.utils.data;

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

public class HexUtils extends ByteUtils
{

    /**
     * Reconvert.
     * 
     * @param _strHex the _str hex
     * 
     * @return the byte[]
     */
    @Override
    public byte[] reconvert(String _strHex)
    {

        if (_strHex == null)
        {
            System.out.println("#error HexString: reconvert null?");

            return null;
        }
        String strHex = _strHex.toUpperCase();

        int iLen = strHex.length();

        if (iLen % 2 != 0)
        {

            // System.out.println("#error HexString: iLen="+iLen);
            iLen -= 1;
        }

        byte[] buffer = new byte[iLen / 2];

        for (int i = 0; i < iLen / 2; i++)
        {

            int ic1, ic2;

            char c1 = strHex.charAt(2 * i);
            if (Character.isDigit(c1))
            {
                ic1 = c1 - '0';
            }
            else
            {
                ic1 = c1 - 'A' + 10;
            }

            char c2 = strHex.charAt(2 * i + 1);
            if (Character.isDigit(c2))
            {
                ic2 = c2 - '0';
            }
            else
            {
                ic2 = c2 - 'A' + (char) 10;
            }

            buffer[i] = (byte) (ic1 * 16 + ic2);
        }

        return buffer;

    }

    public String getHex(long l)
    {
        String s = l != -1L ? "0x" : "";
        return s + getHexCompact(l);
    }

    public String getHex(int i)
    {
        String s;

        if (this.short_hex_used)
        {
            s = "";
        }
        else
        {
            s = i != -1 ? "0x" : "";
        }

        return s + getHexCompact(i);
    }

    public String getHex(byte byte0)
    {
        String s = byte0 != -1 ? "0x" : "";
        return s + getHexCompact(byte0);
    }

    public String getHex(byte abyte0[], int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        if (abyte0 != null)
        {
            i = Math.min(i, abyte0.length);
            for (int j = 0; j < i; j++)
            {
                stringbuffer.append(getHex(abyte0[j]));
                if (j < i - 1)
                {
                    stringbuffer.append(" ");
                }
            }

        }
        return new String(stringbuffer);
    }

    public String getHex(int ai[], int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        if (ai != null)
        {
            i = Math.min(i, ai.length);
            for (int j = 0; j < i; j++)
            {
                stringbuffer.append(getHex(ai[j]));
                if (j < i - 1)
                {
                    stringbuffer.append(" ");
                }
            }

        }
        return new String(stringbuffer);
    }

    public String getHex(byte abyte0[])
    {
        return abyte0 != null ? getHex(abyte0, abyte0.length) : null;
    }

    public String getHex(int ai[])
    {
        return ai != null ? getHex(ai, ai.length) : null;
    }

    public String getHexCompact(long l)
    {
        String s = Long.toHexString(l).toUpperCase();
        String s1 = isOdd(s.length()) ? "0" : "";
        return l != -1L ? s1 + s : "-1";
    }

    public String getHexCompact(int i)
    {
        long l = i != -1 ? convertUnsignedIntToLong(i) : i;
        return getHexCompact(l);
    }

    public String getHexCompact(byte byte0)
    {
        int i = byte0 != -1 ? convertUnsignedByteToInt(byte0) : (int) byte0;
        return getHexCompact(i);
    }

    public String getHexCompact(byte abyte0[], int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        if (abyte0 != null)
        {
            i = Math.min(i, abyte0.length);
            for (int j = 0; j < i; j++)
            {
                stringbuffer.append(getHexCompact(abyte0[j]));
                if (j < i - 1)
                {
                    stringbuffer.append(" ");
                }
            }

        }
        return new String(stringbuffer);
    }

    public String getHexCompact(int ai[], int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        if (ai != null)
        {
            i = Math.min(i, ai.length);
            for (int j = 0; j < i; j++)
            {
                stringbuffer.append(getHexCompact(ai[j]));
                if (j < i - 1)
                {
                    stringbuffer.append(" ");
                }
            }

        }
        return new String(stringbuffer);
    }

    public String getHexCompact(byte abyte0[])
    {
        if (abyte0 != null)
            return getHexCompact(abyte0, abyte0.length);
        else
            return null;
    }

    public String getHexCompact(int ai[])
    {
        if (ai != null)
            return getHexCompact(ai, ai.length);
        else
            return null;
    }

    public int convertUnsignedByteToInt(byte byte0)
    {
        return byte0 & 0xff;
    }

    public long convertUnsignedIntToLong(int i)
    {
        return i & 0xffffffffL;
    }

    public boolean isEven(int i)
    {
        return i % 2 == 0;
    }

    public boolean isOdd(int i)
    {
        return !isEven(i);
    }

    public boolean isZeros(int ai[])
    {
        if (ai != null)
        {
            for (int element : ai)
                if (element != 0)
                    return false;

            return true;
        }
        else
            return false;
    }

    public int makeByte(int i, int j)
    {
        int k = i << 4 | j & 0xf;
        return k;
    }

    public long makeLong(int i, int j, int k, int l)
    {
        long l1 = (long) i << 24 | j << 16 | k << 8 | l;
        return l1;
    }

    public int makeInt(int i, int j)
    {
        int k = (i & 0xff) << 8 | j & 0xff;
        return k;
    }

    public int makeInt(int i, int j, int k)
    {
        int l = (i & 0xff) << 16 | (j & 0xff) << 8 | k & 0xff;
        return l;
    }

    public byte[] convertIntsToBytes(int ai[])
    {
        byte abyte0[] = new byte[ai.length];
        for (int i = 0; i < ai.length; i++)
        {
            abyte0[i] = (byte) (ai[i] & 0xff);
        }

        return abyte0;
    }

    public int makeUnsignedShort(int i, int j)
    {
        int k = (i & 0xff) << 8 | j & 0xff;
        return k;
    }

    public byte[] makeByteArray(int ai[])
    {
        if (ai != null)
        {
            byte abyte0[] = new byte[ai.length];
            for (int i = 0; i < ai.length; i++)
            {
                abyte0[i] = (byte) ai[i];
            }

            return abyte0;
        }
        else
            return null;
    }

    public int[] makeIntArray(byte abyte0[])
    {
        int ai[] = new int[abyte0.length];
        for (int i = 0; i < abyte0.length; i++)
        {
            ai[i] = abyte0[i] & 0xff;
        }

        return ai;
    }

    public String makeString(int ai[])
    {
        return makeString(ai, 0, ai.length);
    }

    public String makeString(int ai[], int i, int j)
    {
        if (ai != null)
        {
            StringBuffer stringbuffer = new StringBuffer();
            for (int k = i; k < i + j; k++)
                if (ai[k] != 0)
                {
                    stringbuffer.append((char) ai[k]);
                }

            return new String(stringbuffer);
        }
        else
            return null;
    }

    public int[] concat(int ai[], int ai1[])
    {
        int ai2[] = new int[ai.length + ai1.length];
        System.arraycopy(ai, 0, ai2, 0, ai.length);
        System.arraycopy(ai1, 0, ai2, ai.length, ai1.length);
        return ai2;
    }

    public int[] getIntSubArray(int[] arr, int start, int length)
    {
        try
        {
            int[] arr_out = new int[length];
            // int j=0;

            for (int i = start, j = 0; i < start + length; i++, j++)
            {
                arr_out[j] = arr[i];
                // j++;
            }

            return arr_out;
        }
        catch (Exception ex)
        {
            // log.error("getIntSubArray(). Ex.: " + ex, ex);
            return null;
        }

    }

}
