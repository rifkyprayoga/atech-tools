package com.atech.update.startup.data;

import java.util.HashMap;
import java.util.Map;

import com.atech.utils.data.CodeEnum;

/**
 * Created by andy on 11/12/17.
 */
public enum StartupTypeDefinition implements CodeEnum
{

    LegacyStartup(1), // all startup files in \bin
    ExtendedStartup(2), // startup files in \bin\ext
    ExtendedStartupWithDataDirectory(3); // startup files in \bin\ext and data directory is in user
                                         // space (except for USB configuration)

    private int code;
    static Map<Integer, StartupTypeDefinition> codeMapping = new HashMap<Integer, StartupTypeDefinition>();

    static
    {
        for (StartupTypeDefinition el : values())
        {
            codeMapping.put(el.getCode(), el);
        }
    }


    StartupTypeDefinition(int code)
    {
        this.code = code;
    }


    public int getCode()
    {
        return code;
    }


    public static StartupTypeDefinition getByCode(int code)
    {
        if (codeMapping.containsKey(code))
        {
            return codeMapping.get(code);
        }
        else
        {
            return StartupTypeDefinition.LegacyStartup;
        }
    }
}
