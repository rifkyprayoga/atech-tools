package com.atech.utils.file;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by andy on 25.11.15.
 */
public class Zip4jUtil
{

    private static final Logger LOG = LoggerFactory.getLogger(Zip4jUtil.class);


    public static void unzipFile(File sourceFile, File destination) throws ZipException
    {
        unzipFile(sourceFile, destination, null);
    }


    public static void unzipFile(File sourceFile, File destination, String password) throws ZipException
    {
        unzipFile(sourceFile.getAbsolutePath(), destination.getAbsolutePath(), password);
    }


    public static void unzipFile(String sourceFile, String destinationDirectory) throws ZipException
    {
        unzipFile(sourceFile, destinationDirectory, null);
    }


    public static void unzipFile(String sourceFile, String destinationDirectory, String password) throws ZipException
    {
        try
        {
            ZipFile zipFile = new ZipFile(sourceFile);

            if (zipFile.isEncrypted())
                zipFile.setPassword(password);

            zipFile.extractAll(destinationDirectory);
        }
        catch (ZipException ex)
        {
            LOG.error("Error unzipping file [name={}]. Exception: {}", sourceFile, ex, ex);
            throw ex;
        }
    }

}
