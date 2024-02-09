
package com.atech.misc.tunnel.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TServer
{

    ServerSocket ss;
    Socket actSock;


    public TServer()
    {
        activateListener();

        try
        {

            actSock = ss.accept();
            actSock.setSoTimeout(5 * 60 * 1000);
        }
        catch (IOException ex)
        {
            System.out.println("Exception thrown " + ex);
        }

        // while (true)
        // {
        receive();
        // }

    }


    public void activateListener() // throws IOException
    {
        try
        {
            ss = new ServerSocket(4489);
            System.out.println("Server is now listening on port 4489");
            System.out.println("====================================");
        }
        catch (IOException ex)
        {
            System.out.println("Error activting listener");
        }

    }


    public void receive()
    {
        String wholeStr = "";
        String whole = "";

        try
        {

            InputStream is = actSock.getInputStream();

            BufferedReader inStream = new BufferedReader(new InputStreamReader(is));

            String stream = null;

            // boolean entry=true;
            // while (entry==true)
            while (is.available() == 0)
            {
                // if (is.available()==0)
                {
                    stream = inStream.readLine();
                    wholeStr = wholeStr + stream;
                    System.out.println(stream);
                    whole.concat(stream);
                }
                // else
                // entry=false;

                /*
                 * //stream=inStream.readLine();
                 * System.out.println(stream);
                 * if (stream==null)
                 * {
                 * entry=false;
                 * }
                 * else
                 * wholeStr = wholeStr+stream;
                 */
            }

            // System.out.println("READ STRING: "+wholeStr);

            /*
             * while ( is.available()==0)
             * {
             * char buf[] = new char[1024];
             * inStream.read(buf,0,1024);
             * stream = new String(buf);
             * System.out.println(stream);
             * wholeStr.concat(stream);
             * //System.out.println(stream); String
             * }
             * System.out.println("READ STRING: "+wholeStr);
             * // outStream.println(wholeStr+" OK! ");
             */
        }
        catch (IOException ex)
        {
            System.out.println("Request invalid." + ex);
        }

        System.out.println("READ STRING wholeStr: " + wholeStr);
        System.out.println("READ STRING whole: " + whole);

    }


    public void send()
    {

        try
        {

            OutputStream is = actSock.getOutputStream();

            BufferedWriter outStream = new BufferedWriter(new OutputStreamWriter(is));

            System.out.println("Sent back to Client.");

            println(outStream, "This is just a test\n");
            println(outStream, "Vse\n");
        }
        catch (IOException ex)
        {
            System.out.println("Exception can be thrown.");
        }

    }


    static void println(BufferedWriter bw, String s) throws IOException
    {

        bw.write(s);
        bw.newLine();
        bw.flush();

    } // println


    public static void main(String args[])
    {

        new TServer();
    }

}
