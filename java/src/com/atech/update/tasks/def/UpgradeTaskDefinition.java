package com.atech.update.tasks.def;

import java.util.HashMap;

/**
 * Created by andy on 23.03.15.
 */
public class UpgradeTaskDefinition extends HashMap<String, Object>
{

    private static final String MAIN_SOURCE_PATH = "MainSourcePath";
    private static final String SOURCE_SUB_PATHS = "SourceSubPaths";
    private static final String MAIN_TARGET_PATH = "MainTargetPath";


    // private static final String SOURCE_PATHS = "SourcePaths";

    public String[] getSourceSubPaths()
    {
        return loadStringArray(SOURCE_SUB_PATHS);
    }


    public void setSourceSubPaths(String[] paths)
    {
        this.put(SOURCE_SUB_PATHS, saveStringArray(paths));
    }


    private String[] loadStringArray(String key)
    {
        String paths = (String) this.get(key);
        return paths.split(";");
    }


    private String saveStringArray(String[] array)
    {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = false;

        for (String path : array)
        {
            if (!first)
            {
                first = true;
            }
            else
            {
                stringBuilder.append(";");
            }

            stringBuilder.append(path);
        }

        return stringBuilder.toString();

    }

}
