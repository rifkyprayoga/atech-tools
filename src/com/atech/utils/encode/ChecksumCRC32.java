package com.atech.utils.encode;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class ChecksumCRC32
{

    public static long getChecksumValue(Checksum checksum, String fname)
    {
        try
        {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(fname));
            byte[] bytes = new byte[1024];
            int len = 0;

            while ((len = is.read(bytes)) >= 0)
            {
                checksum.update(bytes, 0, len);
            }
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return checksum.getValue();
    }


    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Usage: java ChecksumCRC32 <file>");
            System.exit(1);
        }
        long cs = getChecksumValue(new CRC32(), args[0]);
        System.out.println("crc32 " + args[0] + " : " + cs);
    }
}
