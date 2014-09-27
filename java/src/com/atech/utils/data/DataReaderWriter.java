package com.atech.utils.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DataReaderWriter - Reading and writing data to file 
 *  Copyright (C) 2011  Andy (Aleksander) Rozman (Atech-Software)
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

public class DataReaderWriter
{
    String filename = null;

    /**
     * @param filename_
     */
    public DataReaderWriter(String filename_)
    {
        this.filename = filename_;

    }

    /**
     * Write Int Array
     * 
     * @param arr
     */
    public void writeIntArray(int[] arr)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(fos);

            for (int element : arr)
            {
                dos.writeInt(element);
            }

            fos.close();
        }
        catch (Exception ex)
        {
            System.out.println("Error writing data to file. Ex.: " + ex);
        }

    }

    /**
     * Read Int Array
     * 
     * @return
     */
    public int[] readIntArray()
    {
        ArrayList<Integer> lst = new ArrayList<Integer>();
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(filename);
            DataInputStream dis = new DataInputStream(fis);

            int v = -1;

            while (true)
            {
                v = dis.readInt();
                lst.add(v);
            }
        }
        catch (EOFException ex)
        {

        }
        catch (Exception ex)
        {
            System.out.println("Error writing data to file. Ex.: " + ex);
        }
        finally
        {
            try
            {
                fis.close();
            }
            catch (Exception ex)
            {}
        }

        int[] ret = new int[lst.size()];

        for (int i = 0; i < lst.size(); i++)
        {
            ret[i] = lst.get(i);
        }

        return ret;
    }

    /*
     * public static void main(String[] args)
     * {
     * int[] ixa = { 1, 2, 45, 3773, 4433, 223, 255, 280 };
     * HexUtils hu = new HexUtils();
     * System.out.println(" Int Array: " + hu.getIntArrayShow(ixa));
     * DataReaderWriter drw = new DataReaderWriter("test.dta");
     * drw.writeIntArray(ixa);
     * int[] dd = drw.readIntArray();
     * System.out.println(" Int Array OUT: " + hu.getIntArrayShow(dd));
     * }
     */

}
