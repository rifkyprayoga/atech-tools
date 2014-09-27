package com.atech.utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class DirectoryCheck
{
    CheckSumUtility csu = null;

    public DirectoryCheck(String path)
    {
        File f = new File(path);

        File[] files = f.listFiles();
        csu = new CheckSumUtility();

        try
        {

            BufferedWriter fw = new BufferedWriter(new FileWriter("n:\\report.txt"));

            for (File ff : files)
            {
                fw.write(ff.getName() + "\t\t" + "Size: " + ff.length() + "\t\tCRC: " + csu.getChecksumValue(ff) + "\n");
            }

            fw.flush();
            fw.close();
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex);
        }

    }

    public static void main(String[] args)
    {

        DirectoryCheck dc = new DirectoryCheck("n:\\ggc\\files_end\\");

    }

}
