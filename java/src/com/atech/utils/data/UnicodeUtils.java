package com.atech.utils.data;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  ByteUtils - Utils for working with bytes and bytes array
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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

public class UnicodeUtils
{

    public String getASCIIFromUnicodeFull(String value)
    {
        value = value.replace("\n", "\\n");
        value = value.replace("\r", " ");

        return unicodeToASCII(value);
    }

    public String unicodeToASCII(String value)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < value.length(); i++)
        {
            // if ((!Character.isUnicodeIdentifierPart(bb[i])) && (bb[i] < 0)
            // )//|| (Character.i)
            if (isNotRegularAscii(value.charAt(i)))
            {
                sb.append("\\u" + charToHex(value.charAt(i)).toUpperCase());
            }
            else
            {
                sb.append(value.charAt(i));
            }
        }

        return sb.toString();

    }

    public String byteToHex(byte b)
    {
        // Returns hex String representation of byte b
        char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] array = { hexDigit[b >> 4 & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
    }

    public boolean isNotRegularAscii(char c)
    {
        byte hi = (byte) (c >>> 8);

        /*
         * if (debug_write)
         * {
         * //Character cc = new Character(c);
         * System.out.println("Char: " + c);
         * System.out.println("Is Letter: " + Character.isLetter(c));
         * System.out.println("Get Numeric: " + Character.getNumericValue(c));
         * System.out.println("Hi: " + hi + " Is not reg: " + (hi!=0) + "\n");
         * System.out.println("Returned: " + ((Character.getNumericValue(c)==-1)
         * && Character.isLetter(c)));
         * }
         */

        return hi != 0 || Character.getNumericValue(c) == -1 && Character.isLetter(c);
    }

    public String charToHex(char c)
    {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }

    /*
     * private String decode_v2(String str)
     * {
     * byte[] b = str.getBytes();
     * Charset def = Charset.defaultCharset(); // default encoding
     * Charset cs = Charset.forName( "ASCII"); // encoding
     * ByteBuffer bb = ByteBuffer.wrap( b );
     * CharBuffer cb = cs.decode( bb );
     * String s = cb.toString();
     * return s;
     * }
     * private String decode_v1( String str )
     * {
     * System.out.println(str);
     * byte[] input = str.getBytes();
     * char[] output = new char[input.length];
     * // index input[]
     * int i = 0;
     * // index output[]
     * int j = 0;
     * while ( i < input.length )
     * {
     * // get next byte unsigned
     * int b = input[ i++ ] & 0xff;
     * // classify based on the high order 3 bits
     * switch ( b >>> 5 )
     * {
     * default:
     * // one byte encoding
     * // 0xxxxxxx
     * // use just low order 7 bits
     * // 00000000 0xxxxxxx
     * output[ j++ ] = ( char ) ( b & 0x7f );
     * break;
     * case 6:
     * // two byte encoding
     * // 110yyyyy 10xxxxxx
     * // use low order 6 bits
     * int y = b & 0x1f;
     * // use low order 6 bits of the next byte
     * // It should have high order bits 10, which we don't check.
     * int x = input[ i++ ] & 0x3f;
     * // 00000yyy yyxxxxxx
     * output[ j++ ] = ( char ) ( y << 6 | x );
     * break;
     * case 7:
     * // three byte encoding
     * // 1110zzzz 10yyyyyy 10xxxxxx
     * assert ( b & 0x10 )
     * == 0 : "UTF8Decoder does not handle 32-bit characters";
     * // use low order 4 bits
     * int z = b & 0x0f;
     * // use low order 6 bits of the next byte
     * // It should have high order bits 10, which we don't check.
     * y = input[ i++ ] & 0x3f;
     * // use low order 6 bits of the next byte
     * // It should have high order bits 10, which we don't check.
     * x = input[ i++ ] & 0x3f;
     * // zzzzyyyy yyxxxxxx
     * int asint = ( z << 12 | y << 6 | x );
     * output[ j++ ] = ( char ) asint;
     * break;
     * }// end switch
     * }// end while
     * return new String( output, 0
     * // offset
     * , j
     * // count
     * );
     * }
     */

}
