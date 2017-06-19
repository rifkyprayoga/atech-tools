package com.atech.utils.encode;

import java.io.*;
import java.security.MessageDigest;

public class ChecksumUtil
{

    //
    // returns 0 error
    // 1 ok (create)
    // 1 same (check)
    // 2 different (check)
    //
    public static void main(String args[])
    {
        if (args.length == 2)
        {
            if (args[0].equals("create"))
            {
                System.exit(new ChecksumUtil().create(args[1]));
            }
            else if (args[0].equals("check"))
            {
                System.exit(new ChecksumUtil().check(args[1]));
            }
        }
        else
        {
            System.out.println(
                "Usage : java ChecksumUtil create [filename]\n" + "        java ChecksumUtil check  [filename]");
        }
    }


    public int create(String filename)
    {
        try
        {
            byte[] chk = createChecksum(filename);
            File f = new File(filename + ".chk");
            OutputStream os = new FileOutputStream(f);
            os.write(chk);
            os.close();
            return 1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }


    public int check(String filename)
    {
        int rc = 0;
        try
        {
            byte[] chk1 = createChecksum(filename);
            byte[] chk2 = new byte[chk1.length];
            File f = new File(filename + ".chk");
            InputStream is = new FileInputStream(f);

            is.read(chk2);

            if (new String(chk2).equals(new String(chk1)))
            {
                System.out.println("Same!");
                rc = 1;
            }
            else
            {
                System.out.println("Different!");
                rc = 2;
            }
            is.close();
            return rc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return rc;
        }
    }


    public byte[] createChecksum(String filename) throws Exception
    {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do
        {
            numRead = fis.read(buffer);
            if (numRead > 0)
            {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }
}
