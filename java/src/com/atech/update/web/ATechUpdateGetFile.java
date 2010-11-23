package com.atech.update.web;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atech.utils.file.CheckSumUtility;

public class ATechUpdateGetFile extends HttpServlet 
{

    private int BUFSIZE = 1024;
    
    
    /**
     * doGet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        System.out.println("AtechUpdateGetFile::doGet");
        this.doDownload(request, response, "d:/IMG_3968.JPG", "test.jar");
    }
    
    
    /**
     *  Sends a file to the ServletResponse output stream.  Typically
     *  you want the browser to receive a different name than the
     *  name the file has been saved in your local database, since
     *  your local names need to be unique.
     *
     *  @param req The request
     *  @param resp The response
     *  @param filename The name of the file you want to download.
     *  @param original_filename The name the browser should receive.
     */
    private void doDownload( HttpServletRequest req, HttpServletResponse resp,
                             String filename, String original_filename )
        throws IOException
    {
        System.out.println("AtechUpdateGetFile::doDownload");
        File                f        = new File(filename);
        int                 length   = 0;
        ServletOutputStream op       = resp.getOutputStream();
        //ServletContext      context  = getServletConfig().getServletContext();
        String              mimetype = null; //context.getMimeType( filename );

        
        try
        {
            CheckSumUtility csu = new CheckSumUtility();
            System.out.println("Checksum: " + csu.getChecksumValue(filename));
        }
        catch(Exception ex)
        {
            System.out.println("Exception CheckSum. Ex: " + ex);
        }
        
        
        //
        //  Set the response and go!
        //
        //
        resp.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
        resp.setContentLength( (int)f.length() );
        resp.setHeader( "Content-Disposition", "attachment; filename=\"" + original_filename + "\"" );

        //
        //  Stream to the requester.
        //
        byte[] bbuf = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(f));

        while ((in != null) && ((length = in.read(bbuf)) != -1))
        {
            op.write(bbuf,0,length);
        }

        in.close();
        op.flush();
        op.close();
    }
    
    
    
    
    
}
