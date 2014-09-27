package com.atech.utils.java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;

public class CreateMinimalJavaRuntime
{

    private Hashtable<String, ArrayList<ClassfileEntry>> fileListTable;

    public CreateMinimalJavaRuntime(String fileList)
    {
        parseFileList(fileList);
    }

    private void parseFileList(String fileList)
    {
        try
        {
            InputStream fis;
            BufferedReader br;
            String line;

            fis = new FileInputStream(fileList);
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

            // new InputStreamReader(fis, Charset.forName("UTF-8")));

            fileListTable = new Hashtable<String, ArrayList<ClassfileEntry>>();

            while ((line = br.readLine()) != null)
            {
                // Deal with the line

                if (line.startsWith("[Loaded ") && line.contains("jre/lib/rt.jar]"))
                {
                    String className = line.substring("[Loaded ".length(), line.indexOf(" from "));

                    // if (!className.contains("$"))
                    {
                        ClassfileEntry cfe = new ClassfileEntry(className);

                        if (fileListTable.containsKey(cfe.classPackage))
                        {
                            fileListTable.get(cfe.classPackage).add(cfe);
                        }
                        else
                        {
                            ArrayList<ClassfileEntry> list = new ArrayList<ClassfileEntry>();
                            list.add(cfe);

                            fileListTable.put(cfe.classPackage, list);
                        }

                        System.out.println(cfe);
                    }
                }

            }

            // Done with the file
            br.close();
            br = null;
            fis = null;

        }
        catch (Exception ex)
        {

        }
    }

    private void createEntry(String className)
    {

    }

    public static void main(String[] args)
    {

        new CreateMinimalJavaRuntime("/mnt/e/test_ggc/classes_short.txt");

    }

    private class ClassfileEntry
    {
        String full;
        String className;
        String classPackage;
        String path;

        public ClassfileEntry(String entryString)
        {
            this.full = entryString;
            int idx = entryString.lastIndexOf(".");

            this.className = entryString.substring(idx + 1);
            this.classPackage = entryString.substring(0, idx);
            this.path = entryString.substring(0, idx + 1).replace(".", "/");
        }

        @Override
        public String toString()
        {
            return "[classPackege=" + this.classPackage + ", className=" + this.className + ", dir=" + this.path + "]";
        }

    }

}
