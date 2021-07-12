package com.atech.update.startup.os;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 12/12/17.
 */
public enum OSArchitecture
{
    x32("x86", "i386", "i486", "i586", "i686"), //
    x64("x86_64", "amd64"), //
    PPC("powerpc"), //
    Unknown;

    String[] possibleArchDefinitions;
    static Map<String, OSArchitecture> archDefinitionsMap;

    static
    {
        archDefinitionsMap = new HashMap<String, OSArchitecture>();

        for (OSArchitecture osArchitecture : values())
        {
            for (String possibleArchDefinition : osArchitecture.possibleArchDefinitions)
            {
                archDefinitionsMap.put(possibleArchDefinition, osArchitecture);
            }
        }
    }


    OSArchitecture(String... possibleArchDefinitions)
    {
        this.possibleArchDefinitions = possibleArchDefinitions;
    }


    public static OSArchitecture getByDefinitionName(String arch)
    {
        if (archDefinitionsMap.containsKey(arch))
        {
            return archDefinitionsMap.get(arch);
        }
        else
        {
            return OSArchitecture.Unknown;
        }

    }
}
