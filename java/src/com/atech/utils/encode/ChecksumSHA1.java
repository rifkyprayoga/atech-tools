package com.atech.utils.encode;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.commons.lang.CharEncoding;

/**
 * http://www.rgagnon.com/javadetails/java-0416.html
 */

public class ChecksumSHA1
{

    public byte[] createChecksum(ChecksumSettings source, String filename) throws Exception
    {
        if (source == ChecksumSettings.ChecksumSourceFile)
        {
            return createChecksumFromFile(filename);
        }
        else
        {
            return createChecksumFromContent(filename);
        }
    }


    private byte[] createChecksumFromContent(String content) throws Exception
    {
        InputStream stream = new ByteArrayInputStream(content.getBytes(CharEncoding.UTF_8));
        return createChecksumFromInputStream(stream);
    }


    private byte[] createChecksumFromFile(String filename) throws Exception
    {
        InputStream fis = new FileInputStream(filename);
        return createChecksumFromInputStream(fis);
    }


    public byte[] createChecksumFromInputStream(InputStream fis) throws Exception
    {
        // nputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA1");
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


    // see this How-to for a faster way to convert
    // a byte array to a HEX string
    public String getChecksum(ChecksumSettings source, String data) throws Exception
    {
        byte[] b = createChecksum(source, data);
        String result = "";
        for (int i = 0; i < b.length; i++)
        {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }


    public static void main(String args[])
    {
        try
        {
            ChecksumSHA1 checksum = new ChecksumSHA1();

            System.out.println(
                checksum.getChecksum(ChecksumSettings.ChecksumSourceFile, "c:/temp/isapi_redirect-1.2.30.dll"));
            // output :
            // cca9176f72ff56beb1f76c21b1d7daa6be192890.
            // ref :
            // http://tomcat.apache.org/
            // dev/dist/tomcat-connectors/
            // jk/binaries/win32/jk-1.2.30/
            // isapi_redirect-1.2.30.dll.sha1
            //
            // cca9176f72ff56beb1f76c21b1d7daa6be192890
            // *isapi_redirect-1.2.30.dll

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
