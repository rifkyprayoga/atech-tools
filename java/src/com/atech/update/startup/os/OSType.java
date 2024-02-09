package com.atech.update.startup.os;

/**
 * Created by andy on 12/12/17.
 */
public enum OSType
{
    Unix("unix", "$", "sh", ":", null, true, //
            "#!/bin/sh\n\n" + "#  Run Application\n"), //

    Linux("linux", Unix), //

    Windows("unix", "$", "sh", ":", null, false, //
            "#!/bin/sh\n\n" + "#  Run Application\n"), //

    Mac("mac", "$", "bash", ":", "Library", false, //
            "#!/bin/bash\n\n" + "#  Run Application\n"), //

    PosixUnix("unix", Unix) //

    ;

    String shortOsName;
    String customParameter;
    String batchExtension; //
    String pathSeparator;
    String userDirectory;
    boolean prefixDotToApplicationName; //
    String header;

    OSType baseType;


    OSType(String shortOsName, String customParameter, String batchExtension, //
            String pathSeparator, String userDirectory, boolean prefixDotToApplicationName, //
            String header)
    {
        this.shortOsName = shortOsName;
        this.customParameter = customParameter;
        this.batchExtension = batchExtension;
        this.pathSeparator = pathSeparator;
        this.userDirectory = userDirectory;
        this.prefixDotToApplicationName = prefixDotToApplicationName;
        this.header = header;
    }


    OSType(String shortOsName, OSType baseType)
    {
        this.shortOsName = shortOsName;
        this.baseType = baseType;
    }


    /**
     * Get Header for batch file
     *
     * @return header as string
     */
    public String getHeader()
    {
        if (baseType != null)
            return baseType.header;
        else
            return header;
    }


    /**
     * Get Path Separator - this is path separator
     *
     * @return separator as string
     */
    public String getPathSeparator()
    {
        if (baseType != null)
            return baseType.pathSeparator;
        else
            return header;
    }


    /**
     * Get Batch File Extension
     *
     * @return extension of batch file in this OS
     */
    public String getBatchFileExtension()
    {
        if (baseType != null)
            return baseType.batchExtension;
        else
            return batchExtension;
    }


    /**
     * Get Short OS Name (this is not official name, but name we use)
     *
     * @return short os name
     */
    public String getShortOSName()
    {
        return this.shortOsName;
    }


    /**
     * Get How Parameter is constructed (%1 in windows, $1 in unix)
     *
     * @return custom parameter
     */
    public String getCustomParameter()
    {
        if (baseType != null)
            return baseType.customParameter;
        else
            return customParameter;
    }


    public String getOSSpecificDataDirectory()
    {
        if (baseType != null)
            return baseType.userDirectory;
        else
            return userDirectory;
    }


    public boolean isApplicationNamePrefixedWithDot()
    {
        if (baseType != null)
            return baseType.prefixDotToApplicationName;
        else
            return prefixDotToApplicationName;
    }


    public static OSType getOSByType()
    {
        String osNameFull = System.getProperty("os.name");

        if (osNameFull == null)
        {
            System.err.println("os.name not found");
            return null;
        }

        String osName = osNameFull.toLowerCase();

        if (osName.contains("windows"))
        {
            return Windows;
        }
        else if (osName.contains("linux"))
        {
            return Linux;
        }
        else if (osName.contains("mpe/ix") || osName.contains("freebsd") || osName.contains("irix")
                || osName.contains("digital unix") || osName.contains("unix"))
        {
            return Unix;
        }
        else if (osName.contains("mac os"))
        {
            return Mac;
        }
        else if (osName.contains("sun os") || osName.contains("sunos") || osName.contains("solaris") || //
                osName.contains("hp-ux") || osName.contains("aix"))
        {
            return PosixUnix;
        }
        else
        {
            System.err.println("os.name not recognized: " + osNameFull);
            return null;
        }
    }

}
