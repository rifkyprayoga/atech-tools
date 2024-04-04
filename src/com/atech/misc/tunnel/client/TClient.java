
package com.atech.misc.tunnel.client;

import java.io.*;
import java.net.Socket;

public class TClient
{

    Socket sockTun;
    BufferedReader inStream;
    BufferedWriter outStream;


    public TClient()
    {
        connect();
        send();
        // receive();

    }


    public void connect()
    {

        try
        {

            sockTun = new Socket("localhost", 4489);

            sockTun.setKeepAlive(false);

            inStream = new BufferedReader(new InputStreamReader(sockTun.getInputStream()));

            outStream = new BufferedWriter(new OutputStreamWriter(sockTun.getOutputStream()));

            System.out.println("Connecting to server.");

        }
        catch (IOException ex)
        {
            System.out.println("Error connecting to server ! " + ex);
        }

    }


    public void disconnect()
    {
        // sockTun.

    }


    public void send()
    {

        System.out.println("Send to Server.");

        try
        {

            println(outStream, "This is just a test\n");
            println(outStream, "Vse\n");
            sockTun.close();
        }
        catch (IOException ex)
        {
            System.out.println("Exception can be thrown.");
        }

    }


    public void receive()
    {

        try
        {

            String stream;

            boolean entry = true;
            while (entry == true)
            {
                stream = inStream.readLine();
                if (stream == null)
                {
                    entry = false;
                }
                else
                {

                    System.out.println(stream);
                }
            }

        }
        catch (IOException ex)
        {
            System.out.println("Exception reading...");
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
        new TClient();
    }

}
