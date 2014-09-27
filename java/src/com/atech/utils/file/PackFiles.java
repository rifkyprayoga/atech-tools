package com.atech.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/

public class PackFiles
{

    /**
     * The fix directory.
     */
    public String fixDirectory = "";

    private Hashtable<String, FilesList> filesGroups = new Hashtable<String, FilesList>();

    /**
     * Instantiates a new pack files.
     * 
     * @param fixDir the fix dir
     */
    public PackFiles(String fixDir)
    {

        // logger.m_logFile = "FFNetPack.log";
        // log = logger.getInstance();

        try
        {
            this.fixDirectory = fixDir;
            // storiesTable = new Hashtable();

            File f = new File(this.fixDirectory);
            System.setProperty("user.dir", f.getCanonicalPath());

            System.out.println(f.getCanonicalPath());

            zipFromDirectories(new File(this.fixDirectory));
            zipFiles();
        }
        catch (Exception ex)
        {
            System.out.println("ex: " + ex);
            ex.printStackTrace();

        }

    }

    /**
     * Zip files in directory.
     * 
     * @param directory the directory
     * @param outname the outname
     */
    public static void zipFilesInDirectory(File directory, String outname)
    {
        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        try
        {
            // Create the ZIP file
            // String outFilename = getOutName();
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outname));

            File[] files = directory.listFiles();

            // Compress the files
            for (File file : files)
            {
                // String fname = name + "_" + i + ".html";
                FileInputStream in = new FileInputStream(file);

                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(file.getName()));

                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0)
                {
                    out.write(buf, 0, len);
                }

                // Complete the entry
                out.closeEntry();
                in.close();
            }

            // Complete the ZIP file
            out.close();
        }
        catch (IOException ex)
        {
            System.out.println("Exception: " + ex);
            // log.writeLog(logger.ERROR, "Excdptio zip: " + ex);
        }

    }

    /**
     * Zip from directories.
     * 
     * @param dir the dir
     */
    public void zipFromDirectories(File dir)
    {

        File files[] = dir.listFiles();

        System.out.println("Processing directory: " + dir);

        for (File file : files)
        {

            if (file.isDirectory())
            {}
            else
            {
                // System.out.println(files[i].getName());

                try
                {
                    String name = file.getName().substring(0, file.getName().lastIndexOf("_"));

                    // System.out.println("file: '" + name + "'");

                    if (this.filesGroups.containsKey(name))
                    {
                        this.filesGroups.get(name).addFile(file);
                    }
                    else
                    {
                        FilesList fl = new FilesList(name, this.fixDirectory + "/");
                        fl.addFile(file);

                        this.filesGroups.put(name, fl);
                    }
                }
                catch (Exception ex)
                {
                    System.out.println("Exception on parse (" + file + "): " + ex);
                }

            }

        }

    }

    /**
     * Display results.
     */
    public void displayResults()
    {

        // IEnumeration ie = this.filesGroups.keys();

        for (Enumeration<String> ie = this.filesGroups.keys(); ie.hasMoreElements();)
        {
            String s = ie.nextElement();
            System.out.println(" " + s);

        }

    }

    /**
     * Zip files.
     */
    public void zipFiles()
    {

        for (Enumeration<String> ie = this.filesGroups.keys(); ie.hasMoreElements();)
        {
            String s = ie.nextElement();
            System.out.println(" " + s);

            FilesList fl = this.filesGroups.get(s);
            fl.process();
            fl.zip();
        }

    }

    /*
     * public void listFromDirectoriesAuthors(File dir, ArrayList table)
     * {
     * File files[] = dir.listFiles();
     * System.out.println("Processing directory: " + dir);
     * for (int i=0; i<files.length; i++)
     * {
     * //System.out.println(i);
     * if (files[i].isDirectory())
     * {
     * if (!isUnwantedDirectory(files[i].getName()))
     * listFromDirectoriesAuthors(files[i], table);
     * }
     * else
     * {
     * //System.out.println(files[i]);
     * String dd = files[i].getName();
     * //System.out.println(dd);
     * if (Character.isLowerCase(dd.charAt(0)))
     * {
     * char d = Character.toUpperCase(dd.charAt(0));
     * dd = d + dd.substring(1);
     * }
     * //System.out.println(dd);
     * if (dd.indexOf("-")!=-1)
     * {
     * String aut = dd.substring(0, dd.indexOf("-"));
     * table.add(aut);
     * }
     * }
     * }
     * }
     */

    /**
    * The main method.
    * 
    * @param args the arguments
    */
    public static void main(String args[])
    {

        System.exit(0);

    }

    private class FilesList
    {
        private String name;
        private String path;
        private Hashtable<String, File> lst;

        int min;
        int max;

        public FilesList(String name, String path)
        {
            this.path = path;
            this.name = name;
            this.lst = new Hashtable<String, File>();
        }

        public void addFile(File f)
        {
            this.lst.put(f.getName(), f);
        }

        public void process()
        {
            // System.out.println("Name: '" + name + "'  Lst: " +
            // this.lst.size());

            for (int i = 1; i < 255; i++)
            {
                if (this.lst.containsKey(name + "_" + i + ".html"))
                {
                    min = i;
                    break;
                }
            }

            int maxx = min;

            for (int i = min; i < 255; i++)
            {
                if (this.lst.containsKey(name + "_" + i + ".html"))
                {
                    maxx = i;
                }
            }

            this.max = maxx;

            // System.out.println("Name: '" + name + "'  Min: " + min +
            // "  max: " + max);
        }

        public String getOutName()
        {
            if (min == max)
                return name + " [" + min + "].zip";
            else
                return name + " [" + min + "-" + max + "].zip";
        }

        public void zip()
        {
            // Create a buffer for reading the files
            byte[] buf = new byte[1024];

            try
            {
                // Create the ZIP file
                String outFilename = getOutName();
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));

                // Compress the files
                for (int i = min; i <= max; i++)
                {
                    String fname = name + "_" + i + ".html";
                    FileInputStream in = new FileInputStream(path + fname);

                    // Add ZIP entry to output stream.
                    out.putNextEntry(new ZipEntry(fname));

                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0)
                    {
                        out.write(buf, 0, len);
                    }

                    // Complete the entry
                    out.closeEntry();
                    in.close();
                }

                // Complete the ZIP file
                out.close();
            }
            catch (IOException ex)
            {
                System.out.println("Exception: " + ex);
                // log.writeLog(logger.ERROR, "Excdptio zip: " + ex);
            }

        }

    }

    // comandline entries check
    // error checking
    // loger entrues
    // debug control

}
