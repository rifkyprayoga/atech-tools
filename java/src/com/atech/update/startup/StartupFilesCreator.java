package com.atech.update.startup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import com.atech.update.config.ComponentCustomApp;
import com.atech.update.config.UpdateConfiguration;
import com.atech.update.startup.files.ApplicationFile;
import com.atech.update.startup.files.DbApplication;
import com.atech.update.startup.files.DbCheck;
import com.atech.update.startup.files.DbImport;
import com.atech.update.startup.files.DbInit;
import com.atech.update.startup.files.DbTool;
import com.atech.update.startup.files.StartupFileAbstract;
import com.atech.update.startup.os.FreeBSD;
import com.atech.update.startup.os.Linux;
import com.atech.update.startup.os.Mac;
import com.atech.update.startup.os.StartupOSAbstract;
import com.atech.update.startup.os.Windows;

/**
 *  This file is part of ATech Tools library.
 *  
 *  StartupFilesCreator - For creating startup files (more)
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


public class StartupFilesCreator
{

	String os_name;
	UpdateConfiguration uc;
	
	String separator = ";";
	String root = "..";
	String extension = "";
	
	StartupOSAbstract os_abstract = null;
	
	
	/**
	 * Constructor
	 * 
	 * @param uc
	 */
	public StartupFilesCreator(UpdateConfiguration uc)
	{
		this.uc = uc;
		init();
	}
	
	
	protected void init()
	{
        this.os_name = System.getProperty("os.name");
        
        //System.out.println("Found OS: " + os_name);
        
        if (os_name.contains("Linux"))
            this.os_abstract = new Linux();
        else if (os_name.contains("FreeBSD"))
            this.os_abstract = new FreeBSD();
        else if (os_name.contains("Win"))
            this.os_abstract = new Windows();
        else if (os_name.contains("Mac"))
            this.os_abstract = new Mac();
        else
        {
            printNotSupported();
            
            //return null;
        }
	    
        /*
        os.name     os.version  os.arch     Comments
        OS/2    20.40   x86     
        Solaris     2.x     sparc   
        SunOS   5.7     sparc   Sun Ultra 5 running Solaris 2.7
        SunOS   5.8     sparc   Sun Ultra 2 running Solaris 8
        SunOS   5.9     sparc   Java(TM) 2 Runtime Environment, Standard Edition (build 1.4.0_01-b03)
        Java HotSpot(TM) Client VM (build 1.4.0_01-b03, mixed mode)
        MPE/iX  C.55.00     PA-RISC     
        HP-UX   B.10.20     PA-RISC     JDK 1.1.x
        HP-UX   B.11.00     PA-RISC     JDK 1.1.x
        HP-UX   B.11.11     PA-RISC     JDK 1.1.x
        HP-UX   B.11.11     PA_RISC     JDK 1.2.x/1.3.x; note Java 2 returns PA_RISC and Java 1 returns PA-RISC
        HP-UX   B.11.00     PA_RISC     JDK 1.2.x/1.3.x
        HP-UX   B.11.23     IA64N   JDK 1.4.x
        HP-UX   B.11.11     PA_RISC2.0  JDK 1.3.x or JDK 1.4.x, when run on a PA-RISC 2.0 system
        HP-UX   B.11.11     PA_RISC     JDK 1.2.x, even when run on a PA-RISC 2.0 system
        HP-UX   B.11.11     PA-RISC     JDK 1.1.x, even when run on a PA-RISC 2.0 system
        AIX     5.2     ppc64   sun.arch.data.model=64
        AIX     4.3     Power   
        AIX     4.1     POWER_RS    
        OS/390  390     02.10.00    J2RE 1.3.1 IBM OS/390 Persistent Reusable VM
        Irix    6.3     mips    
        Digital Unix    4.0     alpha   
        NetWare 4.11    4.11    x86     
        OSF1    V5.1    alpha   Java 1.3.1 on Compaq (now HP) Tru64 Unix V5.1
        OpenVMS     V7.2-1  alpha   Java 1.3.1_1 on OpenVMS 7.2
        
        === DONE ===
        FreeBSD     2.2.2-RELEASE   x86     
        Linux   2.0.31  x86     IBM Java 1.3
        Linux   (*)     i386    Sun Java 1.3.1, 1.4 or Blackdown Java; (*) os.version depends on Linux Kernel version
        Linux   (*)     x86_64  Blackdown Java; note x86_64 might change to amd64; (*) os.version depends on Linux Kernel version
        Linux   (*)     sparc   Blackdown Java; (*) os.version depends on Linux Kernel version
        Linux   (*)     ppc     Blackdown Java; (*) os.version depends on Linux Kernel version
        Linux   (*)     armv41  Blackdown Java; (*) os.version depends on Linux Kernel version
        Linux   (*)     i686    GNU Java Compiler (GCJ); (*) os.version depends on Linux Kernel version
        Linux   (*)     ppc64   IBM Java 1.3; (*) os.version depends on Linux Kernel version
        Mac OS  7.5.1   PowerPC     
        Mac OS  8.1     PowerPC     
        Mac OS  9.0, 9.2.2  PowerPC     MacOS 9.0: java.version=1.1.8, mrj.version=2.2.5; MacOS 9.2.2: java.version=1.1.8 mrj.version=2.2.5
        Mac OS X    10.1.3  ppc     
        Mac OS X    10.2.6  ppc     Java(TM) 2 Runtime Environment, Standard Edition (build 1.4.1_01-39)
        Java HotSpot(TM) Client VM (build 1.4.1_01-14, mixed mode)
        Mac OS X    10.2.8  ppc     using 1.3 JVM: java.vm.version=1.3.1_03-74, mrj.version=3.3.2; using 1.4 JVM: java.vm.version=1.4.1_01-24, mrj.version=69.1
        Mac OS X    10.3.1, 10.3.2, 10.3.3, 10.3.4  ppc     JDK 1.4.x
        Mac OS X    10.3.8  ppc     Mac OS X 10.3.8 Server; using 1.3 JVM: java.vm.version=1.3.1_03-76, mrj.version=3.3.3; using 1.4 JVM: java.vm.version=1.4.2-38; mrj.version=141.3
        Windows 95  4.0     x86     
        Windows 98  4.10    x86     Note, that if you run Sun JDK 1.2.1 or 1.2.2 Windows 98 identifies itself as Windows 95.
        Windows Me  4.90    x86     
        Windows NT  4.0     x86     
        Windows 2000    5.0     x86     
        Windows XP  5.1     x86     Note, that if you run older Java runtimes Windows XP identifies itself as Windows 2000.
        Windows 2003    5.2     x86     java.vm.version=1.4.2_06-b03; Note, that Windows Server 2003 identifies itself only as Windows 2003.
        Windows CE  3.0 build 11171     arm     Compaq iPAQ 3950 (PocketPC 2002)
        
                
        */
	    
	}
	
	
	/**
	 * Get OS Abstract class instance
	 * @return
	 */
	public StartupOSAbstract getOSAbstract()
	{
	    return this.os_abstract;
	}
	
	
	
	/**
	 * Create Files
	 * 
	 * @throws Exception
	 */
	public void createFiles() throws Exception
	{
        //System.out.println("Create Files DbTool: ");

	    
	    if (this.uc.db_app_db_tool)
	        createFile(new DbTool(this.uc, this.os_abstract));
	    
        //System.out.println("Create Files Ch: ");
        
	    if (this.uc.db_app_db_check)
	        createFile(new DbCheck(this.uc, this.os_abstract));
	    
        //System.out.println("Create Files App: ");
	    
        if (this.uc.db_app_db_application)
            createFile(new DbApplication(this.uc, this.os_abstract));
	    
        //System.out.println("Create Files Db Init: ");
        
        
        if (this.uc.db_app_db_init)
            createFile(new DbInit(this.uc, this.os_abstract));

        //System.out.println("Create Files Dm Impo: ");
        
        if (this.uc.db_app_db_import)
            createFile(new DbImport(this.uc, this.os_abstract));

        //System.out.println("Create Files Bef Custom: ");
        
	    if (this.uc.custom_apps.size()>0)
	        createCustomFiles();
	}
	
	
	
    /**
     * Create Custom Files
     * 
     * @throws Exception
     */
    public void createCustomFiles() throws Exception
    {
        for(int i=1; i<=this.uc.custom_apps.size(); i++)
        {
            ComponentCustomApp cca = this.uc.custom_apps.get("" + i);
            createFile(new ApplicationFile(this.uc, this.os_abstract, cca));
        }
        
    }
    
    
	
	/**
	 * Create File 
	 * 
	 * @param file_ab abstract file instance
	 * 
	 * @throws Exception
	 */
	public void createFile(StartupFileAbstract file_ab) throws Exception
	{
	    System.out.println("CreateFile: " + file_ab);
	    
        try
        {
            BufferedWriter br = new BufferedWriter(new FileWriter(new File(file_ab.getFileName())));
            br.write(file_ab.getFileContent());
            br.flush();
            br.close();
        }
        catch(Exception ex)
        {
            System.out.println("createFile failed: " + ex);
            ex.printStackTrace();
            
            throw ex;
        }
	    
	}
	
	
	
	private void printNotSupported()
	{
		System.out.println("This Operating System (" + os_name + ") is not supported " +
        "\nby ATech's Startup/Update Manager.");
		System.out.println("If you wish to help us with support for your OS please contact us");
		System.out.println("on our email (support@atech-software.com).");
		
	}
	
	
}