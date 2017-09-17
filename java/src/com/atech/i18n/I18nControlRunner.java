package com.atech.i18n;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public abstract class I18nControlRunner
{

    private static final Logger LOG = LoggerFactory.getLogger(I18nControlRunner.class);


    /**
     * Get Name of root file for application translation 
     * 
     * @return
     */
    public abstract String getLanguageFileRoot();


    public String exportLanguageFile(String language, String outputDirectory) throws Exception
    {
        return exportResource("/" + getLanguageFileRoot() + "_" + language + ".properties", outputDirectory);
    }


    public String exportResource(String resourceName, String outputDirectory) throws Exception
    {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String outputFolder;
        try
        {
            stream = getClass().getResourceAsStream(resourceName);
            // note that each / is a directory down in the "jar tree" been the
            // jar the root of the tree
            if (stream == null)
            {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];

            if (outputDirectory == null)
            {
                outputFolder = new File(
                        getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
                                .getParentFile().getPath().replace('\\', '/');
            }
            else
            {
                outputFolder = outputDirectory.replace('\\', '/');
            }

            resStreamOut = new FileOutputStream(outputFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0)
            {
                resStreamOut.write(buffer, 0, readBytes);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
            throw ex;
        }
        finally
        {
            if (stream != null)
                stream.close();

            if (resStreamOut != null)
                resStreamOut.close();
        }

        return outputFolder + resourceName;
    }

}
