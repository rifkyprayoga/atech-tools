package com.atech.utils.file.zip;

/**
 * Created by andy on 03/03/17.
 */
public class CommonsPackUtil
{

    // public static void compressZipfile(String sourceDir, String outputFile)
    // throws IOException, FileNotFoundException
    // {
    // ZipOutputStream zipFile = new ZipOutputStream(new
    // FileOutputStream(outputFile));
    // compressDirectoryToZipfile(sourceDir, sourceDir, zipFile);
    // IOUtils.closeQuietly(zipFile);
    // }
    //
    //
    // private static void compressDirectoryToZipfile(String rootDir, String
    // sourceDir, ZipOutputStream out)
    // throws IOException, FileNotFoundException
    // {
    // for (File file : new File(sourceDir).listFiles())
    // {
    // if (file.isDirectory())
    // {
    // compressDirectoryToZipfile(rootDir, sourceDir + File.separator +
    // file.getName(), out);
    // }
    // else
    // {
    // ZipEntry entry = new ZipEntry(sourceDir.replace(rootDir, "") +
    // file.getName());
    // out.putNextEntry(entry);
    //
    // FileInputStream in = new FileInputStream(sourceDir + file.getName());
    // IOUtils.copy(in, out);
    // IOUtils.closeQuietly(in);
    // }
    // }
    // }

}
