package com.atech.utils.file.zip.repack;

/**
 * Created by andy on 07/02/17.
 */

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import com.atech.utils.file.zip.util.Zip4jUtil;

/**
 * Regular zip in java, has many flaws, which in turn make zip files not readable on some small devices (PocketPC or
 * android). This is utility intended to take all zip files of specified directory and unpack them and repack them
 * with same name.
 */

public class RepackFilesZip4j
{

    protected String inputDirectory;
    protected File temporaryDirectory; // <input>\tmp (as default)
    protected File outDirectory; //// <input>\out (as default)
    Zip4jUtil zipUtil = new Zip4jUtil();


    public RepackFilesZip4j(String inputDirectory)
    {
        this.inputDirectory = inputDirectory;
        this.temporaryDirectory = createDirectory(inputDirectory, File.separator + "tmp");
        this.outDirectory = createDirectory(inputDirectory, File.separator + "out");
    }


    private File createDirectory(String rootDirectory, String s)
    {
        String directory = rootDirectory + s;

        File f = new File(directory);

        if (!f.exists())
        {
            boolean result = f.mkdirs();
            System.out.println("Directory " + f.getAbsolutePath() + " created: " + result);
        }
        else
        {
            cleanDirectory(f);
            System.out.println("Directory " + f.getAbsolutePath() + " cleaned");
        }

        return f;
    }


    private void processFiles()
    {
        File inputDirectory = new File(this.inputDirectory);

        File[] files = inputDirectory.listFiles(new FileFilter()
        {

            public boolean accept(File pathname)
            {
                return pathname.getName().contains(".zip");
            }
        });

        for (File file : files)
        {
            // save name
            String fileName = file.getName();
            System.out.println("Processing file: " + file.getName());

            // unzip into tmp
            try
            {
                zipUtil.unzipFile(file, temporaryDirectory);
            }
            catch (Exception e)
            {
                System.err.println("Error unziping file: " + fileName);
            }

            // zip from tmp into \\out\saved_name

            // delete from tmp
            cleanTemp();
        }
    }


    protected void zipFiles(String fileName)
    {
        try
        {
            File f = new File(outDirectory, fileName);
            zipUtil.zipFile(f, getFilesOfTemp());
        }
        catch (Exception e)
        {
            System.err.println("Error zipping file: " + fileName);
        }
    }


    protected ArrayList<File> getFilesOfTemp()
    {

        File[] files = getAllFilesFrom(temporaryDirectory);

        // new ArrayList<File>().addAll(files);

        ArrayList<File> fileArrayList = new ArrayList<File>();

        for (File f : files)
        {
            fileArrayList.add(f);
        }

        return fileArrayList;
    }


    private File[] getAllFilesFrom(File directory)
    {
        File[] files = directory.listFiles(new FileFilter()
        {

            public boolean accept(File pathname)
            {
                return true;
            }
        });

        return files;
    }


    private void cleanTemp()
    {
        cleanDirectory(temporaryDirectory);
    }


    private void cleanDirectory(File directory)
    {
        File[] files = getAllFilesFrom(directory);

        if (files.length > 0)
        {
            for (File f : files)
            {
                f.delete();
            }
        }

    }


    public static void main(String[] args)
    {
        RepackFilesZip4j repackFiles = new RepackFilesZip4j(
                "/u01/Andy/Creative/Dawson's Creek/_new_sort_all/joey-pacey-fic-new_repack");

        // "/u01/Andy/Creative/zipTmp");
        repackFiles.processFiles();
    }

}
