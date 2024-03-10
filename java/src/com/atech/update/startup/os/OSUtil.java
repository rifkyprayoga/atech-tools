package com.atech.update.startup.os;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 *  This file is part of ATech Tools library.
 *  
 *  OSUtil - Util for creating startup files
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
 *  @author Andy
 *
*/

public class OSUtil
{

    /**
     * Constructor
     */
    public OSUtil()
    {

    }


    /**
     * Get Short OS Name
     * @return
     */
    public String getShortOSName()
    {
        String os_name = System.getProperty("os.name");
        String short_name = "unknown";

        // System.out.println("Found OS: " + os_name);

        if (os_name.contains("Linux"))
        {
            short_name = "linux";
        }
        else if (os_name.contains("Mac"))
        {
            short_name = "mac";
        }
        else if (os_name.contains("Win"))
        {
            short_name = "win";
        }
        else if (os_name.contains("FreeBSD"))
        {
            short_name = "freebsd";
        }

        return short_name;
    }


    public OSType getStartupOS(boolean printNotSupported)
    {

        OSType type = OSType.getOSByType();

        return type;

        // String os_name = System.getProperty("os.name");
        //
        // // System.out.println("Found OS: " + os_name);
        //
        // if (os_name.contains("Linux"))
        // {
        // return new LinuxStartupOS();
        // }
        // else if (os_name.contains("FreeBSD"))
        // {
        // return new FreeBSDStartupOS();
        // }
        // else if (os_name.contains("Win"))
        // {
        // return new WindowsStartupOS();
        // }
        // else if (os_name.contains("Mac"))
        // {
        // return new MacStartupOS();
        // }
        // else
        // {
        // if (printNotSupported)
        // printNotSupported(os_name);
        //
        // return null;
        // }

    }


    /*
     * os.name os.version os.arch Comments
     * OS/2 20.40 x86
     * Solaris 2.x sparc
     * SunOS 5.7 sparc Sun Ultra 5 running Solaris 2.7
     * SunOS 5.8 sparc Sun Ultra 2 running Solaris 8
     * SunOS 5.9 sparc Java(TM) 2 Runtime Environment, Standard Edition (build 1.4.0_01-b03)
     * Java HotSpot(TM) Client VM (build 1.4.0_01-b03, mixed mode)
     * MPE/iX C.55.00 PA-RISC
     * HP-UX B.10.20 PA-RISC JDK 1.1.x
     * HP-UX B.11.00 PA-RISC JDK 1.1.x
     * HP-UX B.11.11 PA-RISC JDK 1.1.x
     * HP-UX B.11.11 PA_RISC JDK 1.2.x/1.3.x; note Java 2 returns PA_RISC and Java 1 returns PA-RISC
     * HP-UX B.11.00 PA_RISC JDK 1.2.x/1.3.x
     * HP-UX B.11.23 IA64N JDK 1.4.x
     * HP-UX B.11.11 PA_RISC2.0 JDK 1.3.x or JDK 1.4.x, when run on a PA-RISC 2.0 system
     * HP-UX B.11.11 PA_RISC JDK 1.2.x, even when run on a PA-RISC 2.0 system
     * HP-UX B.11.11 PA-RISC JDK 1.1.x, even when run on a PA-RISC 2.0 system
     * AIX 5.2 ppc64 sun.arch.data.model=64
     * AIX 4.3 Power
     * AIX 4.1 POWER_RS
     * OS/390 390 02.10.00 J2RE 1.3.1 IBM OS/390 Persistent Reusable VM
     * Irix 6.3 mips
     * Digital Unix 4.0 alpha
     * NetWare 4.11 4.11 x86
     * OSF1 V5.1 alpha Java 1.3.1 on Compaq (now HP) Tru64 Unix V5.1
     * OpenVMS V7.2-1 alpha Java 1.3.1_1 on OpenVMS 7.2
     * === DONE ===
     * FreeBSD 2.2.2-RELEASE x86
     * Linux 2.0.31 x86 IBM Java 1.3
     * Linux (*) i386 Sun Java 1.3.1, 1.4 or Blackdown Java; (*) os.version
     * depends on Linux Kernel version
     * Linux (*) x86_64 Blackdown Java; note x86_64 might change to amd64;
     * (*) os.version depends on Linux Kernel version
     * Linux (*) sparc Blackdown Java; (*) os.version depends on Linux
     * Kernel version
     * Linux (*) ppc Blackdown Java; (*) os.version depends on Linux Kernel
     * version
     * Linux (*) armv41 Blackdown Java; (*) os.version depends on Linux
     * Kernel version
     * Linux (*) i686 GNU Java Compiler (GCJ); (*) os.version depends on
     * Linux Kernel version
     * Linux (*) ppc64 IBM Java 1.3; (*) os.version depends on Linux Kernel
     * version
     * Mac OS 7.5.1 PowerPC
     * Mac OS 8.1 PowerPC
     * Mac OS 9.0, 9.2.2 PowerPC MacOS 9.0: java.version=1.1.8,
     * mrj.version=2.2.5; MacOS 9.2.2: java.version=1.1.8 mrj.version=2.2.5
     * Mac OS X 10.1.3 ppc
     * Mac OS X 10.2.6 ppc Java(TM) 2 Runtime Environment, Standard Edition
     * (build 1.4.1_01-39)
     * Java HotSpot(TM) Client VM (build 1.4.1_01-14, mixed mode)
     * Mac OS X 10.2.8 ppc using 1.3 JVM: java.vm.version=1.3.1_03-74,
     * mrj.version=3.3.2; using 1.4 JVM: java.vm.version=1.4.1_01-24,
     * mrj.version=69.1
     * Mac OS X 10.3.1, 10.3.2, 10.3.3, 10.3.4 ppc JDK 1.4.x
     * Mac OS X 10.3.8 ppc Mac OS X 10.3.8 Server; using 1.3 JVM:
     * java.vm.version=1.3.1_03-76, mrj.version=3.3.3; using 1.4 JVM:
     * java.vm.version=1.4.2-38; mrj.version=141.3
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
     */

    /**
     * Get OS Specific Configuration File
     * 
     * @return filename of configuration file
     */
    public String getOSSpecificConfigurationFile()
    {
        String defaultConfigFilename = "StartupConfig.properties";

        String os_name = getShortOSName();
        String osConfigFilename = "StartupConfig_" + os_name + ".properties";

        //File f = new File(osConfigFilename);

        String[] files = new String[] { osConfigFilename, //
                                       "./ext/" + osConfigFilename, //
                                       defaultConfigFilename, //
                                       "./ext/" + defaultConfigFilename };

        for (String fileName : files)
        {
            System.out.println("Searching for " + fileName);
            if (fileExists(fileName))
            {
                return fileName;
            }
        }

        // StartupConfig might have been added as resource
        String filename = checkIfStartupIsResource(osConfigFilename, defaultConfigFilename);

        return filename==null ? defaultConfigFilename : filename;

//
//        URL resource = OSUtil.class.getClass().getClassLoader().getResource("file.txt");
//        if (resource == null) {
//            throw new IllegalArgumentException("file not found!");
//        } else {
//
//            // failed if files have whitespaces or special characters
//            //return new File(resource.getFile());
//
//            return new File(resource.toURI());
//        }
//
//
//        return defaultConfigFilename;

    }

    public String checkIfStartupIsResource(String...files) {
        //Object o = new Object();
        for (String file : files) {
            //URL resource = o.getClass().getClassLoader().getResource(file);
            InputStream is = getClass().getClassLoader().getResourceAsStream(file);

            if (is == null) {
                System.out.println("File NOT found as resource: " + file);
                //throw new IllegalArgumentException("file not found!");
            } else {
                System.out.println("File found as resource: " + file);
                return "RESOURCE/" + file;
            }
        }

        return null;

    }


    public boolean fileExists(String fileName)
    {
        File f = new File(fileName);

        return f.exists();
    }


    public OSArchitecture getOSArchitecture()
    {
        String arch = System.getProperty("os.arch");

        OSArchitecture archType = OSArchitecture.getByDefinitionName(arch);

        if (archType == OSArchitecture.Unknown)
        {
            System.out.println("Unknown architecture: " + arch);
        }

        return archType;
    }


    private void printNotSupported(String osName)
    {
        System.out.println("This Operating System (" + osName + ") is not yet supported "
                + "\nby ATech's Startup/Update Manager.");
        System.out.println("If you wish to help us with support for your OS, please contact support");
        System.out.println("for your application and supply them with following information: ");
        System.out.println("  OS Name: " + System.getProperty("os.name"));
        System.out.println("  OS Version: " + System.getProperty("os.version"));
        System.out.println("  OS Architecture: " + System.getProperty("os.arch"));
    }

}
