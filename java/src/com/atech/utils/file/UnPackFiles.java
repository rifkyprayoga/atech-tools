package com.atech.utils.file;


/*
 * UNPACKING ZIP FILES
 * 
 * Zip files are used widely for packaging and distributing software. ZipFile is
 * a handy tool for doing the following: reading the contents of .zip and .jar
 * files, obtaining a list of the files they contain, and reading the contents
 * of individual files. Jar files, used in Java software distribution, employ
 * the Zip file format as well. For more information on Jar files, see JDC Tech
 * Tips No. 1 (September 3, 1997) at the following address:
 * 
 * http://developer.javasoft.com/developer/technicalArticles/TechTips/
 * 
 * java.util.ZipFile is a class used to represent a Zip file. You construct a
 * ZipFile object, and then iterate over the entries in the file, each of which
 * is a ZipEntry. Entries can be checked to determine if they are directories
 * and read from as if they were individual files, in addition to other
 * functions. The ZipFile class is found in both JDK 1.1 and JDK 1.2 beta2.
 * 
 * Here is an example of using ZipFile to dump out the entries and contents of a
 * .zip file. The program accepts a single file argument, along with an optional
 * -contents flag that indicates the contents should also be displayed (note
 * that printing a binary file to screen does not work well).
 * 
 * 
 * This program includes a workaround for a bug in JDK versions 1.1 and 1.2 beta
 * 2, that is, bug #4040920 in the JDC Bug Parade database. Normally, when you
 * read from a low-level file input stream, you read chunks of, say, 1024 bytes
 * at a time, and you don't worry whether that many bytes are actually left to
 * read. This approach fails with the input stream returned by getInputStream
 * for a given Zip file entry, and the workaround is to never try to read more
 * bytes from the entry than are actually there.
 */

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


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


public class UnPackFiles
{
    
    String output_path;
    
    
    public UnPackFiles(String file, boolean dump_contents, String output_path)
    {
        
        ZipFile zf = null;
        this.output_path = output_path;

        try
        {
            zf = new ZipFile(file);
        }
        catch (ZipException e1)
        {
            System.err.println("exception: " + e1);
        }
        catch (IOException e2)
        {
            System.err.println("exception: " + e2);
        }

        Enumeration<? extends ZipEntry> list = zf.entries();
        while (list.hasMoreElements())
        {
            ZipEntry ze = (ZipEntry) list.nextElement();
            if (!dump_contents || ze.isDirectory())
            {
                System.out.println(ze.getName());
                continue;
            }
            
            try
            {
                dumpFile(zf, ze);
            }
            catch (IOException e)
            {
                System.err.println("exception: " + e);
            }
        }

        
    }
    
    public void dumpFile(ZipFile zf, ZipEntry ze) throws IOException
    {
//        System.out.println(">>>>> " + ze.getName());
        InputStream istr = zf.getInputStream(ze);
        BufferedInputStream bis = new BufferedInputStream(istr);
        //FileDescriptor out = FileDescriptor.out;
        FileOutputStream fos = new FileOutputStream(this.output_path + "/" + ze.getName());
        int sz = (int) ze.getSize();
        final int N = 1024;
        byte buf[] = new byte[N];
        int ln = 0;
        while (sz > 0 && // workaround for bug
                (ln = bis.read(buf, 0, Math.min(N, sz))) != -1)
        {
            fos.write(buf, 0, ln);
            sz -= ln;
        }
        bis.close();
        fos.flush();
    }

    
    
    
    public static void main(String args[])
    {
        new UnPackFiles("d:/backup 25.6.2008  13_27_00.zip", true, "./bin");
    }
}
