package com.atech.utils.file.zip.util;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by andy on 08/03/17.
 */
public abstract class ZipUtilAbstract
{

    public void zipFile(File targetFile, ArrayList<File> filesToZip) throws Exception
    {
        zipFile(targetFile, filesToZip, null);
    }


    public abstract void zipFile(File targetFile, ArrayList<File> filesToZip, Object zipParameters) throws Exception;


    public void unzipFile(File sourceFile, File destination) throws Exception
    {
        unzipFile(sourceFile, destination, null);
    }


    public void unzipFile(File sourceFile, File destination, String password) throws Exception
    {
        unzipFile(sourceFile.getAbsolutePath(), destination.getAbsolutePath(), password);
    }


    public void unzipFile(String sourceFile, String destinationDirectory) throws Exception
    {
        unzipFile(sourceFile, destinationDirectory, null);
    }


    abstract void unzipFile(String sourceFile, String destinationDirectory, String password) throws Exception;

}
