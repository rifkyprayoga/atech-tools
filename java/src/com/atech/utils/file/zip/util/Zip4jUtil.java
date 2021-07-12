package com.atech.utils.file.zip.util;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zip4jUtil extends ZipUtilAbstract
{

    private static final Logger LOG = LoggerFactory.getLogger(Zip4jUtil.class);


    public void zipFile(File targetFile, ArrayList<File> filesToZip, Object zipParameters) throws Exception
    {
        ZipFile zipFile = new ZipFile(targetFile);
        zipFile.addFiles(filesToZip, zipParameters != null ? (ZipParameters) zipParameters
                : generateDefaultZipParameters());

    }


    public void unzipFile(String sourceFile, String destinationDirectory, String password) throws Exception
    {
        unzipFile(sourceFile, destinationDirectory, password, true);
    }


    public void unzipFileWithoutException(String sourceFile, String destinationDirectory, String password)
    {
        try
        {
            unzipFile(sourceFile, destinationDirectory, password, false);
        }
        catch (Exception e)
        {}
    }


    public void unzipFile(String sourceFile, String destinationDirectory, String password, boolean throwException)
            throws Exception
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

            if (throwException)
                throw ex;
        }
    }


    public static ZipParameters generateDefaultZipParameters()
    {
        ZipParameters param = new ZipParameters();
        param.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        return param;
    }

}
