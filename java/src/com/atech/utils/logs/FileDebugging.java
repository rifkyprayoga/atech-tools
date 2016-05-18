package com.atech.utils.logs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by andy on 09.03.15.
 */
public class FileDebugging
{

    private static final Logger LOG = LoggerFactory.getLogger(FileDebugging.class);
    BufferedWriter bw;
    String filename;

    static FileDebugging staticInstance;


    private FileDebugging()
    {
    }


    public static FileDebugging getInstance()
    {
        if (staticInstance == null)
        {
            staticInstance = new FileDebugging();
        }

        return staticInstance;
    }


    public void startFileDebugging(String file)
    {
        try
        {
            this.filename = file;
            bw = new BufferedWriter(new FileWriter(file));

        }
        catch (IOException ex)
        {
            LOG.debug("Error openning file: " + ex);
        }
    }


    public void endFileDebugging()
    {
        try
        {
            bw.flush();
            bw.close();

            bw = null;
            this.filename = null;
        }
        catch (IOException ex)
        {
            LOG.debug("Error closing file: " + ex);
        }
    }


    public void writeToFile(String stringToWrite)
    {
        if (bw == null)
        {
            LOG.warn("FileDebugging instance not ready !");
            return;
        }

        try
        {
            bw.write(stringToWrite);
            bw.newLine();
            bw.flush();

        }
        catch (IOException ex)
        {
            LOG.error("Error writing to file: " + ex);
        }

    }

}
