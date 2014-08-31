package com.atech.utils.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BytesFileUtil
{

    /**
     * Read bytes from a File into a byte[].
     * 
     * @param file
     *            The File to read.
     * @return A byte[] containing the contents of the File.
     * @throws IOException
     *             Thrown if the File is too long to read or couldn't be read
     *             fully.
     */
    public static byte[] readBytesFromFile(File file) throws IOException
    {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE)
        {
            throw new IOException("Could not completely read file " + file.getName() + " as it is too long (" + length
                    + " bytes, max supported " + Integer.MAX_VALUE + ")");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
        {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length)
        {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    /**
     * Writes the specified byte[] to the specified File path.
     * 
     * @param theFile
     *            File Object representing the path to write to.
     * @param bytes
     *            The byte[] of data to write to the File.
     * @throws IOException
     *             Thrown if there is problem creating or writing the File.
     */
    public static void writeBytesToFile(File theFile, byte[] bytes) throws IOException
    {
        BufferedOutputStream bos = null;

        try
        {
            FileOutputStream fos = new FileOutputStream(theFile);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    // flush and close the BufferedOutputStream
                    bos.flush();
                    bos.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }
    
    
    /**
     * Writes the specified byte[] to the specified File path.
     * 
     * @param theFile
     *            File Object representing the path to write to.
     * @param bytes
     *            The byte[] of data to write to the File.
     * @throws IOException
     *             Thrown if there is problem creating or writing the File.
     */
    public static void writeBytesToFile(File theFile, short[] bytes) throws IOException
    {
        BufferedOutputStream bos = null;

        try
        {
            FileOutputStream fos = new FileOutputStream(theFile);
            bos = new BufferedOutputStream(fos);
            
            for(short element : bytes)
            {
                bos.write(element);
            }
            //bos.write(bytes);
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    // flush and close the BufferedOutputStream
                    bos.flush();
                    bos.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}
