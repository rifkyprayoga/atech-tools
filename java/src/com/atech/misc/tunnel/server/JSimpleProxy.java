package com.atech.misc.tunnel.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class JSimpleProxy
{

    ServerSocket ss;


    public JSimpleProxy()
    {

        try
        {

            ss = new ServerSocket(4488);

            // Blocks until a connection occurs:
            Socket socket = ss.accept();
            socket.setSoTimeout(5 * 60 * 1000);

            System.out.println("Connection accepted !");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            String str = null;

            // request receive
            try
            {
                str = in.readLine();
            }
            catch (IOException ex)
            {
                System.out.println("Request invalid.");
            }

            System.out.println("Request: " + str);

            // parse request
            int get = str.indexOf("GET");
            int http = str.indexOf("HTTP/");
            String parsed = null;

            if (get < 0)
            {
                System.out.println("Request invalid by parser");
            }
            else
                parsed = str.substring(get + 4, http);

            // System.out.println("Parsed URL: "+parsed);

            URL ur = new URL(parsed);
            // HttpURLs hur = new HttpURLs(ur, false);
            //
            // // URL ur = new URL(parsed);
            //
            // //URLConnection huc = new URLConnection(ur);
            //
            // hur.connect();
            // String code = hur.getResponseMessage();

            BufferedReader in2 = new BufferedReader(new InputStreamReader(ur.openStream()));

            /*
             * PrintWriter out = new PrintWriter(
             * new BufferedWriter(
             * new OutputStreamWriter(ur.openStream())));
             * //(in)));
             */

            // Socket sock = new Socket("10.49.1.95",4488,true);
            /*
             * PrintWriter out = new PrintWriter(
             * new BufferedWriter(
             * new OutputStreamWriter(socket.getOutputStream())));
             * //(in)));
             */
            // out.print(code);
            // System.out.println(code);

            String stream;

            boolean entry = true;
            while (entry == true)
            {
                stream = in2.readLine();
                if (stream == null)
                {
                    entry = false;
                }
                else
                {
                    // System.out.println(stream);

                    out.println(stream);
                    // out.println(stream);

                }
            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }


    public static void main(String args[])
    {
        new JSimpleProxy();
    }

}
