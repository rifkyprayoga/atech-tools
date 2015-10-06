package com.atech.update.startup;

import com.atech.update.config.ComponentEntry;
import com.atech.update.config.UpdateConfiguration;

/**
 *  This file is part of ATech Tools library.
 *  
 *  StartupFileCreator - For creating startup file (one) - deprecated
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy {andy@atech-software.com}
 *
*/

public class StartupFileCreator
{

    String os_name;
    UpdateConfiguration uc;

    String separator = ";";
    String root = "..";
    String extension = "";


    /**
     * Constructor
     * 
     * @param uc
     */
    public StartupFileCreator(UpdateConfiguration uc)
    {
        this.uc = uc;
    }


    /**
     * Get Startup File Body
     * 
     * @return get body of file
     */
    public String[] getStartupFileBody()
    {

        this.os_name = System.getProperty("os.name");

        // System.out.println("Found OS: " + os_name);

        if (os_name.contains("Linux") || os_name.contains("FreeBSD"))
            return createShScript();
        else if (os_name.contains("Mac"))
            return createBashScript();
        else if (os_name.contains("Win"))
            return createWindowsScript();
        else
        {
            printNotSupported();

            return null;
        }

        /*
         * OS/2 20.40 x86
         * Solaris 2.x sparc
         * SunOS 5.7 sparc Sun Ultra 5 running Solaris 2.7
         * SunOS 5.8 sparc Sun Ultra 2 running Solaris 8
         * SunOS 5.9 sparc Java(TM) 2 Runtime Environment, Standard Edition
         * (build 1.4.0_01-b03)
         * Java HotSpot(TM) Client VM (build 1.4.0_01-b03, mixed mode)
         * MPE/iX C.55.00 PA-RISC
         * HP-UX B.10.20 PA-RISC JDK 1.1.x
         * HP-UX B.11.00 PA-RISC JDK 1.1.x
         * HP-UX B.11.11 PA-RISC JDK 1.1.x
         * HP-UX B.11.11 PA_RISC JDK 1.2.x/1.3.x; note Java 2 returns PA_RISC
         * and Java 1 returns PA-RISC
         * HP-UX B.11.00 PA_RISC JDK 1.2.x/1.3.x
         * HP-UX B.11.23 IA64N JDK 1.4.x
         * HP-UX B.11.11 PA_RISC2.0 JDK 1.3.x or JDK 1.4.x, when run on a
         * PA-RISC 2.0 system
         * HP-UX B.11.11 PA_RISC JDK 1.2.x, even when run on a PA-RISC 2.0
         * system
         * HP-UX B.11.11 PA-RISC JDK 1.1.x, even when run on a PA-RISC 2.0
         * system
         * AIX 5.2 ppc64 sun.arch.data.model=64
         * AIX 4.3 Power
         * AIX 4.1 POWER_RS
         * OS/390 390 02.10.00 J2RE 1.3.1 IBM OS/390 Persistent Reusable VM
         * FreeBSD 2.2.2-RELEASE x86
         * Irix 6.3 mips
         * Digital Unix 4.0 alpha
         * NetWare 4.11 4.11 x86
         * OSF1 V5.1 alpha Java 1.3.1 on Compaq (now HP) Tru64 Unix V5.1
         * OpenVMS
         * === Done ===
         * Windows 95 4.0 x86
         * Windows 98 4.10 x86 Note, that if you run Sun JDK 1.2.1 or 1.2.2
         * Windows 98 identifies itself as Windows 95.
         * Windows Me 4.90 x86
         * Windows NT 4.0 x86
         * Windows 2000 5.0 x86
         * Windows XP 5.1 x86 Note, that if you run older Java runtimes Windows
         * XP identifies itself as Windows 2000.
         * Windows 2003 5.2 x86 java.vm.version=1.4.2_06-b03; Note, that Windows
         * Server 2003 identifies itself only as Windows 2003.
         * Windows CE 3.0 build 11171 arm Compaq iPAQ 3950 (PocketPC 2002)
         * Mac OS 7.5.1 PowerPC
         * Mac OS 8.1 PowerPC
         * Mac OS 9.0, 9.2.2 PowerPC MacOS 9.0: java.version=1.1.8,
         * mrj.version=2.2.5; MacOS 9.2.2: java.version=1.1.8 mrj.version=2.2.5
         * Mac OS X 10.1.3 ppc
         * Mac OS X 10.2.6 ppc Java(TM) 2 Runtime Environment, Standard Edition
         * (build 1.4.1_01-39)
         * Mac OS X 10.2.8 ppc using 1.3 JVM: java.vm.version=1.3.1_03-74,
         * mrj.version=3.3.2; using 1.4 JVM: java.vm.version=1.4.1_01-24,
         * mrj.version=69.1
         * Mac OS X 10.3.1, 10.3.2, 10.3.3, 10.3.4 ppc JDK 1.4.x
         * Mac OS X 10.3.8 ppc Mac OS X 10.3.8 Server; using 1.3 JVM:
         * java.vm.version=1.3.1_03-76, mrj.version=3.3.3; using 1.4 JVM:
         * java.vm.version=1.4.2-38; mrj.version=141.3
         */

    }


    private void printNotSupported()
    {
        System.out.println("This Operating System (" + os_name + ") is not supported "
                + "\nby ATech's Startup/Update Manager.");
        System.out.println("If you wish to help us with support for your OS please contact us");
        System.out.println("on our email (support@atech-software.com).");

    }


    private String[] createShScript()
    {

        return createShellScript("sh");

        /*
         * this.separator = ":";
         * this.root = this.uc.root;
         * StringBuffer sb = new StringBuffer();
         * sb.append("#!/bin/sh\n\n");
         * String del_db = uc.root + "/data/db/pis_int.lck";
         * sb.append("#   Delete Db Lock File\n\n");
         * sb.append("if [ -f " + del_db + "]; then " +
         * "   rm " + del_db +
         * "fi\n\n");
         * sb.append("#   Run Application\n");
         * sb.append(this.uc.java_exe);
         * sb.append(" -classpath ");
         * sb.append("." + this.separator);
         * sb.append(this.getFileList());
         * sb.append(" ");
         * sb.append(this.uc.main_class);
         * return getReturnValue("sh", sb.toString());
         */
    }


    private String[] createBashScript()
    {

        return createShellScript("bash");

        /*
         * this.separator = ":";
         * this.root = this.uc.root;
         * StringBuffer sb = new StringBuffer();
         * sb.append("#!/bin/bash\n\n");
         * String del_db = uc.root + "/data/db/pis_int.lck";
         * sb.append("#   Delete Db Lock File\n\n");
         * sb.append("if [ -f " + del_db + "]; then " +
         * "   rm " + del_db +
         * "fi\n\n");
         * sb.append("#   Run Application\n");
         * sb.append(this.uc.java_exe);
         * sb.append(" -classpath ");
         * sb.append("." + this.separator);
         * sb.append(this.getFileList());
         * sb.append(" ");
         * sb.append(this.uc.main_class);
         * return getReturnValue("sh", sb.toString());
         */
    }


    private String[] createShellScript(String shell)
    {

        this.separator = ":";
        this.root = this.uc.root;

        StringBuffer sb = new StringBuffer();

        sb.append("#!/bin/" + shell + "\n\n");

        String del_db = uc.root + "/data/db/pis_int.lck";

        sb.append("#   Delete Db Lock File\n\n");
        sb.append("if [ -f " + del_db + "]; then \n" + "   rm " + del_db + "\n" + "fi\n\n");

        sb.append("#   Run Application\n");

        sb.append(this.uc.java_exe);
        sb.append(" -classpath ");
        sb.append("." + this.separator);
        sb.append(this.getFileList());
        sb.append(" ");
        sb.append(this.uc.main_class);

        return getReturnValue("sh", sb.toString());

    }


    private String[] createWindowsScript()
    {
        this.separator = ";";
        this.root = this.uc.root;

        StringBuffer sb = new StringBuffer();

        sb.append("@echo off\n\n");

        String del_db = uc.root + "/data/db/pis_int.lck";

        sb.append("rem   Delete Db Lock File\n");
        sb.append("if exist " + del_db + " del " + del_db + "\n\n");

        sb.append("rem   Run Application\n");

        sb.append(this.uc.java_exe);
        sb.append(" -classpath ");
        sb.append("." + this.separator);
        sb.append(this.getFileList());
        sb.append(" ");
        sb.append(this.uc.main_class);

        return getReturnValue("cmd", sb.toString());

    }


    private String[] getReturnValue(String ext, String body)
    {
        String[] ret_value = new String[2];
        ret_value[0] = ext;
        ret_value[1] = body;
        return ret_value;
    }


    private String getFileList()
    {
        StringBuffer files = new StringBuffer();

        int count = this.uc.getComponents().size() - 1;

        for (int i = 0; i <= count; i++)
        {
            ComponentEntry ce = this.uc.getComponents().get(i);
            String path = root + ce.root_dir;

            files.append(parseRoot(path, ce.files));

            if (count != i)
            {
                files.append(this.separator);
            }
        }

        return files.toString();

    }


    /**
     * Parse Root
     * 
     * @param path path for file
     * @param full_string full_string for parsing
     * 
     * @return resolved root
     */
    public String parseRoot(String path, String full_string)
    {
        full_string = full_string.replaceAll(";", this.separator);
        return full_string.replaceAll("%ROOT%", path);
    }

}
