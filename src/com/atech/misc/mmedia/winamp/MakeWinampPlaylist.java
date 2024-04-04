package com.atech.misc.mmedia.winamp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MakeWinampPlaylist
{

    File globalFile;


    MakeWinampPlaylist(String filet)
    {
        System.out.println("Starting to process root directory " + filet + " :");
        globalFile = new File(filet);
    }


    public void processDir(File in)
    {

        System.out.println("Processing sub-dir " + in);
        FileOutputStream m_file; // file to which we write
        File test = new File(in.getAbsolutePath()); // instance of this dir
        File files[] = test.listFiles(); // list of files
        String writeFile = new String(globalFile.getAbsolutePath() + "\\" + in.getName() + "_.m3u"); // playlist

        try
        {
            m_file = new FileOutputStream(writeFile, true);
        }
        catch (IOException e)
        {
            System.out.println("Could not open.");
            return;
        }

        String prefix = in.getName() + "\\";

        for (int j = 0; j < files.length; j++)
        {

            try
            {
                if (files[j].isFile())
                {
                    String full = prefix + files[j].getName() + "\n";
                    m_file.write(full.getBytes());
                    m_file.flush();
                }
            }
            catch (IOException e2)
            {
                System.out.println("Error writing to file!");
            }
        }

        try
        {
            m_file.close();
        }
        catch (IOException e)
        {
            System.out.println("File could not be closed.");
            return;
        }

    }


    public void work()
    {
        File list[] = globalFile.listFiles();
        for (int i = 0; i < list.length; i++)
        {
            if (list[i].isDirectory())
                processDir(list[i]);
        }
        return;
    }


    public static void main(String args[])
    {

        if (args.length == 0)
        {
            System.out.println("Please enter root directory of mp3 directory.");
        }

        MakeWinampPlaylist list = new MakeWinampPlaylist(args[0]);
        list.work();
        System.exit(0);

    }

}
