package com.atech.utils.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This file is part of ATech Tools library.
 *
 * BitUtils - Utils for working with Bits (as received from serial devices). Since
 *    this data can be in many forms (as byte, short, char or int arrays), this
 *    class is intended to replace ByteUtils, ShortUtils and HexUtils.
 * Copyright (C) 2014 Andy (Aleksander) Rozman (Atech-Software)
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

public class BitUtils
{

    private static Log log = LogFactory.getLog(ByteUtils.class);

    /**
     * The Constant BIT_ORDER_BIG_ENDIAN.
     */
    public static final int BIT_ORDER_BIG_ENDIAN = 1;

    /**
     * The Constant BIT_ORDER_LITTLE_ENDIAN.
     */
    public static final int BIT_ORDER_LITTLE_ENDIAN = 2;

    /**
     * The byte_order.
     */
    protected int byte_order = BIT_ORDER_BIG_ENDIAN;


    /**
     * The byte_arr.
     */
    // protected byte[] byte_arr = null;

    /**
     * Read byte array.
     *
     * @param array
     *            the array
     */
    // public void readByteArray(byte[] array)
    // {
    // this.byte_arr = array;
    // }

    /**
     * Gets the byte from array.
     *
     * @param offset
     *            the offset
     *
     * @return the byte from array
     */
    // public byte getByteFromArray(int offset)
    // {
    // return getByte(this.byte_arr, offset);
    // }

    /**
     * Gets the byte.
     *
     * @param arr
     *            the arr
     * @param offset
     *            the offset
     *
     * @return the byte
     */
    public byte getByte(byte[] arr, int offset)
    {
        return arr[offset];
    }


    /**
     * Gets the int from array.
     *
     * @param offset
     *            the offset
     *
     * @return the int from array
     */
    // public int getIntFromArray(int offset)
    // {
    // return getInt(this.byte_arr, offset);
    // }

    /**
     * Gets the int.
     *
     * @param arr
     *            the arr
     * @param offset
     *            the offset
     *
     * @return the int
     */
    public int getInt(byte[] arr, int offset)
    {
        int val = 0;

        if (this.byte_order == ByteUtils.BIT_ORDER_BIG_ENDIAN)
        {
            val += arr[offset] << 8 & 0xff00;
            val = val + (arr[offset + 1] & 0xff);
        }
        else
        {
            val += arr[offset + 1] << 8 & 0xff00;
            val = val + (arr[offset] & 0xff);
        }

        return val;

    }


    // endian is here not supported
    public int getByteFromIntsAsInt(int num1, int num2)
    {
        int k = num1 << 4 | num2 & 0xf;
        return k;
    }


    /**
     * Gets the string from array.
     *
     * @param offset
     *            the offset
     * @param size
     *            the size
     *
     * @return the string from array
     */
    // public String getStringFromArray(int offset, int size)
    // {
    // return getString(this.byte_arr, offset, size);
    // }

    /**
     * Gets the string.
     *
     * @param arr
     *            the arr
     * @param offset
     *            the offset
     * @param size
     *            the size
     *
     * @return the string
     */
    public String getString(byte[] arr, int offset, int size)
    {
        try
        {
            char[] destinationArray = new char[size];

            for (int i = 0; i < size; i++)
            {
                destinationArray[i] = (char) arr[offset + i];
            }

            // System.arraycopy(arr, offset, destinationArray, 0, size);
            String str = new String(destinationArray); // , 0, size);
            return str;
        }
        catch (Exception ex)
        {
            System.out.println("Ex: " + ex);
            return null;
        }
    }


    /**
     * Gets the string.
     *
     * @param arr
     *            the arr
     * @param offset
     *            the offset
     * @param size
     *            the size
     *
     * @return the string
     */
    public String getString(short[] arr, int offset, int size)
    {
        try
        {
            char[] destinationArray = new char[size];

            for (int i = 0; i < size; i++)
            {
                destinationArray[i] = (char) arr[offset + i];
            }

            // System.arraycopy(arr, offset, destinationArray, 0, size);
            String str = new String(destinationArray); // , 0, size);
            return str;
        }
        catch (Exception ex)
        {
            System.out.println("Ex: " + ex);
            return null;
        }
    }


    /**
     * Gets the ascii from array.
     *
     * @param offset
     *            the offset
     * @param size
     *            the size
     *
     * @return the ascii from array
     */
    // public String getAsciiFromArray(int offset, int size)
    // {
    // return getAscii(this.byte_arr, offset, size);
    // }

    /**
     * Gets the ascii.
     *
     * @param arr
     *            the arr
     * @param offset
     *            the offset
     * @param size
     *            the size
     *
     * @return the ascii
     */
    public String getAscii(byte[] arr, int offset, int size)
    {
        try
        {
            char[] destinationArray = new char[size];

            for (int i = 0; i < size; i++)
            {
                // byte b = (byte)(arr[offset+i] + 0x41);
                destinationArray[i] = (char) (arr[offset + i] + 0x41);
            }

            // System.arraycopy(arr, offset, destinationArray, 0, size);
            String str = new String(destinationArray); // , 0, size);
            return str;
        }
        catch (Exception ex)
        {
            System.out.println("Ex: " + ex);
            return null;
        }
    }


    /**
     * Gets the byte sub array.
     *
     * @param arr
     *            the arr
     * @param start
     *            the start
     * @param end
     *            the end
     * @param length
     *            the length
     *
     * @return the byte sub array
     */
    public byte[] getByteSubArray(byte[] arr, int start, int end, int length)
    {
        byte[] arr_out = new byte[length];
        int j = 0;

        for (int i = start; i < arr.length - end; i++)
        // for(int i=start; i<start+length; i++)
        {
            arr_out[j] = arr[i];
            j++;
        }

        return arr_out;
    }


    public byte[] getByteSubArray(byte[] arr, int start, int length)
    {
        byte[] arr_out = new byte[length];
        int j = 0;

        // for(int i=start; i<(arr.length-end); i++)
        for (int i = start; i < start + length; i++)
        {
            arr_out[j] = arr[i];
            j++;
        }

        return arr_out;
    }

    protected boolean short_hex_used = false;


    public void setUseShortHex(boolean short_true)
    {
        this.short_hex_used = short_true;
    }


    public int[] getIntSubArray(int[] arr, int start, int end, int length)
    {
        try
        {
            int[] arr_out = new int[end - start];
            // int j=0;

            for (int i = start, j = 0; i < end; i++, j++)
            {
                arr_out[j] = arr[i];
                // j++;
            }

            return arr_out;
        }
        catch (Exception ex)
        {
            log.error("getIntSubArray(). Ex.: " + ex, ex);
            return null;
        }

        /*
         * int[] arr_out = new int[length-end-start]; int j=0;
         * for(int i=start; i<(arr.length-end); i++) { arr_out[j] = arr[i]; j++;
         * }
         * return arr_out;
         */
    }


    /**
     * Gets the correct hex value.
     *
     * @param inp
     *            the inp
     *
     * @return the correct hex value
     */
    public String getCorrectHexValue(byte inp)
    {
        String hx = Integer.toHexString((char) inp);

        if (hx.length() == 0)
            return "00";
        else if (hx.length() == 1)
            return "0" + hx;
        else if (hx.length() == 2)
            return hx;
        else if (hx.length() == 4)
            return hx.substring(2);
        else
        {
            System.out.println("HEX ERROR !!!!!!!!!!!!!!!!");
        }

        // System.out.print(Integer.toHexString((char)arr[i]) + " ");

        return null;
    }


    /**
     * Gets the correct hex value.
     *
     * @param inp
     *            the inp
     *
     * @return the correct hex value
     */
    public String getCorrectHexValue(int inp)
    {
        String hx = Integer.toHexString((char) inp);

        if (hx.length() == 0)
            return "00";
        else if (hx.length() == 1)
            return "0" + hx;
        else if (hx.length() == 2)
            return hx;
        else if (hx.length() == 4)
            return hx.substring(2);
        else
        {
            System.out.println("HEX ERROR !!!!!!!!!!!!!!!!");
        }

        // System.out.print(Integer.toHexString((char)arr[i]) + " ");

        return null;
    }


    /**
     * Show byte array hex.
     *
     * @param arr
     *            the arr
     */
    public void showByteArrayHex(byte[] arr)
    {
        System.out.print("Byte array: ");

        for (byte element : arr)
        {
            System.out.print(getCorrectHexValue(element) + " ");
            // getCorrectHexValue(arr[i]);
            // System.out.print(Integer.toHexString((char)arr[i]) + " ");
        }

        System.out.print("\n");

    }


    public void showIntArrayHex(int[] arr)
    {
        System.out.print("Int array: ");

        for (int element : arr)
        {
            System.out.print(getCorrectHexValue(element) + " ");
            // getCorrectHexValue(arr[i]);
            // System.out.print(Integer.toHexString((char)arr[i]) + " ");
        }

        System.out.print("\n");

    }


    public String getIntArrayShow(int[] arr)
    {
        // System.out.print("Int array: ");
        StringBuffer sb = new StringBuffer();

        for (int element : arr)
        {
            sb.append(element + " ");
        }

        // System.out.print("\n");

        return sb.toString();

    }


    /**
     * Show byte array hex.
     *
     * @param arr
     *            the arr
     */
    public String getIntArrayHex(int[] arr)
    {
        // System.out.print("Byte array: ");

        StringBuffer sb = new StringBuffer();
        sb.append("[ ");

        for (int element : arr)
        {
            sb.append(getCorrectHexValue(element) + " ");
            // getCorrectHexValue(arr[i]);
            // System.out.print(Integer.toHexString((char)arr[i]) + " ");
        }

        sb.append("]");

        return sb.toString();
    }


    /**
     * Show byte array hex.
     *
     * @param arr
     *            the arr
     */
    public String getByteArrayHex(byte[] arr)
    {
        // System.out.print("Byte array: ");

        StringBuffer sb = new StringBuffer();
        sb.append("[ ");

        for (byte element : arr)
        {
            sb.append(getCorrectHexValue(element) + " ");
            // getCorrectHexValue(arr[i]);
            // System.out.print(Integer.toHexString((char)arr[i]) + " ");
        }

        sb.append("]");

        return sb.toString();
    }


    /**
     * Show byte array.
     *
     * @param arr
     *            the arr
     */
    public void showByteArray(byte[] arr)
    {
        System.out.println("Byte array: ");

        for (byte element : arr)
        {
            System.out.print(element + " ");
        }

    }


    public void showByteArrayAsString(byte[] arr)
    {
        System.out.println("Byte array as String: ");

        StringBuffer sb = new StringBuffer();

        for (byte element : arr)
        {
            sb.append((char) element);
            // System.out.print(arr[i] + " ");
        }

        System.out.println(sb.toString());
    }


    public String getDebugByteArrayHex(byte[] arr)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Byte array [Hex]: ");
        // System.out.print("Byte array: ");

        for (byte element : arr)
        {
            sb.append(getCorrectHexValue(element) + " ");
            // System.out.print(getCorrectHexValue(arr[i]) + " ");
            // getCorrectHexValue(arr[i]);
            // System.out.print(Integer.toHexString((char)arr[i]) + " ");
        }

        sb.append("\n");
        // System.out.print("\n");

        return sb.toString();

    }


    public String getDebugByteArrayHexValue(byte[] arr)
    {
        StringBuilder sb = new StringBuilder();

        for (byte element : arr)
        {
            sb.append(getCorrectHexValue(element) + " ");
        }

        return sb.toString();

    }


    public String getDebugByteListHex(List<Byte> list)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Byte array [Hex]: ");
        sb.append(getDebugByteListHexValue(list));

        return sb.toString();
    }


    public String getDebugByteListHexValue(List<Byte> list)
    {
        StringBuilder sb = new StringBuilder();

        for (byte element : list)
        {
            sb.append(getCorrectHexValue(element) + " ");
        }

        return sb.toString();
    }


    /**
     * Show byte array.
     *
     * @param arr
     *            the arr
     */
    public String getDebugByteArray(byte[] arr)
    {
        StringBuilder sb = new StringBuilder();
        // System.out.println("Byte array: ");

        sb.append("Byte array: ");

        for (byte element : arr)
        {
            sb.append(element + " ");
            // System.out.print(arr[i] + " ");
        }

        return sb.toString();
    }


    public String getDebugByteArrayAsString(byte[] arr)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Byte array as Str: ");

        // System.out.println("Byte array as String: ");

        for (byte element : arr)
        {
            sb.append((char) element);
            // System.out.print(arr[i] + " ");
        }

        return sb.toString();
    }


    /**
     * Reconvert.
     *
     * @param _strHex
     *            the _str hex
     *
     * @return the byte[]
     */

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


    public static byte[] cloneByteArray(byte[] ba)
    {
        byte[] b = new byte[ba.length];

        for (int i = 0; i < b.length; i++)
        {
            b[i] = ba[i];
        }

        return b;
    }


    public static int[] cloneIntArray(int[] ba)
    {
        int[] b = new int[ba.length];

        for (int i = 0; i < b.length; i++)
        {
            b[i] = ba[i];
        }

        return b;
    }


    public int[] makePackedBCD(String s)
    {
        // Contract.preNonNull(s, "numericString");
        // Contract.pre(isEven(s.length()),
        // "numericString must have an even number of characters");
        s = s.toLowerCase();

        /*
         * for(int i = 0; i < s.length(); i++) { char c = s.charAt(i);
         * //Contract.pre(Character.isDigit(c) || c >= 'a' && c <= 'f',
         * "numericString '" + s + "' must be hex digits only."); }
         */

        int ai[] = makeIntArray(s);
        int ai1[] = new int[ai.length / 2];
        for (int j = 0; j < ai1.length; j++)
        {
            char c1 = (char) ai[j * 2];
            char c2 = (char) ai[j * 2 + 1];
            int k = c1 - (Character.isDigit(c1) ? 48 : 87);
            int l = c2 - (Character.isDigit(c2) ? 48 : 87);
            ai1[j] = getByteAsInt(k, l);
        }

        return ai1;
    }


    public int[] makeIntArray(String s)
    {
        if (s != null)
        {
            int ai[] = new int[s.length()];
            for (int i = 0; i < ai.length; i++)
            {
                ai[i] = s.charAt(i);
            }

            return ai;
        }
        else
            return null;
    }


    public int[] concatIntArrays(int arr_in1[], int arr_in2[])
    {
        int arr_out[] = new int[arr_in1.length + arr_in2.length];
        System.arraycopy(arr_in1, 0, arr_out, 0, arr_in1.length);
        System.arraycopy(arr_in2, 0, arr_out, arr_in1.length, arr_in2.length);

        // log.debug("Input 1: " + this.getIntArrayHex(arr_in1));
        // log.debug("Input 2: " + this.getIntArrayHex(arr_in2));
        // log.debug("Input Skupaj: " + this.getIntArrayHex(arr_out));

        return arr_out;
    }


    public int getByteAsInt(int i, int j)
    {
        // Contract.pre(i >= 0 && i <= 15, "highNibble value of " + i +
        // " is out of expected range 0.." + 15);
        // Contract.pre(j >= 0 && j <= 15, "lowNibble value of " + j +
        // " is out of expected range 0.." + 15);
        return i << 4 | j & 0xf;
        // Contract.post(k >= 0 && k <= 255, "value of " + k +
        // " is out of expected range 0.." + 255);
    }


    public byte[] convertIntArrayToByteArray(int[] arr_in)
    {
        if (arr_in != null)
        {
            byte arr_out[] = new byte[arr_in.length];

            for (int i = 0; i < arr_in.length; i++)
            {
                arr_out[i] = (byte) arr_in[i];
            }

            return arr_out;
        }
        else
            return null;

    }


    public int convertIntsToUnsignedShort(int i, int j)
    {
        // int k = (i & 0xff) << 8 | j & 0xff;
        return (i & 0xff) << 8 | j & 0xff;
    }


    public int[] getIntArrayFromAL(ArrayList<Integer> list)
    {
        int[] out = new int[list.size()];

        for (int i = 0; i < list.size(); i++)
        {
            out[i] = list.get(i);
        }

        return out;
    }


    public void addIntArrayToAL(ArrayList<Integer> list, int[] array)
    {
        for (int element : array)
        {
            list.add(element);
        }
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


    public byte[] concat(byte[] a, byte[] b)
    {
        if ((a == null) || (a.length == 0))
        {
            return b;
        }

        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

}
