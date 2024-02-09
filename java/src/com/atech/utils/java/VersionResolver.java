package com.atech.utils.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by andy on 01.01.17.
 */
public class VersionResolver
{

    private static final Logger LOG = LoggerFactory.getLogger(VersionResolver.class);


    public static String getVersion(String className, String calledFrom)
    {
        VersionInterface versionInterface = getVersionInterface(className, calledFrom);

        if (versionInterface != null)
            return versionInterface.getVersion();
        else
            return "Unknown";
    }


    public static VersionInterface getVersionInterface(String className, String calledFrom)
    {
        try
        {
            Class<?> clazz = Class.forName(className);
            VersionInterface v = (VersionInterface) clazz.newInstance();

            return v;
        }
        catch (Exception ex)
        {
            LOG.warn("Version information could not be loaded [from={}, versionClass={}]. Exception: {}", calledFrom,
                className, ex.getMessage(), ex);
        }

        return null;
    }

}
