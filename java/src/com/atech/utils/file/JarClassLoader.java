
package com.atech.utils.file;

/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	Copyright (c) Non, Inc. 1999 -- All Rights Reserved

PACKAGE:	JavaWorld
FILE:		JarClassLoader.java

AUTHOR:		John D. Mitchell, Mar  3, 1999

REVISION HISTORY:
	Name	Date		Description
	----	----		-----------
	JDM	99.03.03   	Initial version.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/

/**
 ** JarClassLoader provides a minimalistic ClassLoader which shows how to
 * instantiate a class which resides in a .jar file. <br>
 * <br>
 ** 
 ** @author John D. Mitchell, Non, Inc., Mar 3, 1999
 ** 
 ** @version 0.5
 ** 
 **/


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


public class JarClassLoader extends MultiClassLoader
{
    private JarResources jarResources;

    public JarClassLoader(String jarName)
    {
        // Create the JarResource and suck in the .jar file.
        jarResources = new JarResources(jarName);
    }

    protected byte[] loadClassBytes(String className)
    {
        // Support the MultiClassLoader's class name munging facility.
        className = formatClassName(className);

        // Attempt to get the class data from the JarResource.
        return (jarResources.getResource(className));
    }

    /*
     * Internal Testing application.
     */
    public static class Test
    {
        public static void main(String[] args) throws Exception
        {
            if (args.length != 2)
            {
                System.err.println("Usage: java JarClassLoader "
                        + "<jar file name> <class name>");
                System.exit(1);
            }

            /*
             * Create the .jar class loader and use the first argument passed in
             * from the command line as the .jar file to use.
             */
            JarClassLoader jarLoader = new JarClassLoader(args[0]);

            /* Load the class from the .jar file and resolve it. */
            Class<?> c = jarLoader.loadClass(args[1], true);

            /*
             * Create an instance of the class.
             * 
             * Note that created object's constructor-taking-no-arguments will
             * be called as part of the objects creation.
             */
            Object o = c.newInstance();

            /* Are we using a class that we specifically know about? */
            if (o instanceof PackFiles)
            {
                // Yep, lets call a method that we know about. */
                /*PackFiles tc = (PackFiles) o; */
                //tc.doSomething();
            }
        }
    } // End of nested Class Test.

} // End of Class JarClassLoader.
