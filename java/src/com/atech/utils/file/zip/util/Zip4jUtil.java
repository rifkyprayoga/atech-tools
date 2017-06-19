package com.atech.utils.file.zip.util;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Created by andy on 25.11.15.
 */
public class Zip4jUtil extends ZipUtilAbstract
{

    private static final Logger LOG = LoggerFactory.getLogger(Zip4jUtil.class);


    public void zipFile(File targetFile, ArrayList<File> filesToZip, Object zipParameters) throws Exception
    {
        ZipFile zipFile = new ZipFile(targetFile);
        zipFile.addFiles(filesToZip,
            zipParameters != null ? (ZipParameters) zipParameters : generateDefaultZipParameters());

    }


    public void unzipFile(String sourceFile, String destinationDirectory, String password) throws Exception
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


    public static ZipParameters generateDefaultZipParameters()
    {
        ZipParameters param = new ZipParameters();
        param.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        return param;
    }

}
