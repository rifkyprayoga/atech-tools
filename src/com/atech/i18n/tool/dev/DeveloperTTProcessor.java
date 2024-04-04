package com.atech.i18n.tool.dev;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.atech.i18n.tool.dev.data.TTLanguageInfo;
import com.atech.i18n.tool.simple.TranslationToolConfigSelector;
import com.atech.i18n.tool.simple.data.ConfigurationEntry;

public class DeveloperTTProcessor
{

    // 1 - read processor configuration (languages to process)
    // 2 - read available configs
    // 3 - translate all configs for all langugaes

    public static final int ACTION_TRANSLATE_ALL = 1;

    public static final int ACTION_TRANSLATE_SELECTED_SHORT = 2;

    public static final int ACTION_TRANSLATE_SELECTED_DESCRIPTION = 3;

    public static final int ACTION_LIST_AVAILABLE_LANGUAGES = 4;

    public static final int ACTION_LIST_MODULES_TO_TRANSLATE = 5;

    Vector<ConfigurationEntry> configuration_files = null;
    Hashtable<String, TTLanguageInfo> languages = null;
    Hashtable<String, String> languages_desc = null;

    public DeveloperTTProcessor(int action, String param)
    {
        checkPaths();
        readProcessorConfiguration();
        readAvailableConfigs();

        if (!this.preliminaryChecks())
            return;

        if (action == ACTION_TRANSLATE_ALL)
        {
            this.processAllLangauges();
        }
        else if (action == ACTION_TRANSLATE_SELECTED_SHORT)
        {
            this.processAllFilesForLanguage(param);
        }
        else if (action == ACTION_TRANSLATE_SELECTED_DESCRIPTION)
        {
            String p1 = resolveDescription(param);

            if (p1 == null)
            {
                System.out.println(" Unknown langauge: " + param);
            }
            else
            {
                this.processAllFilesForLanguage(param);
            }
        }
        else if (action == ACTION_LIST_AVAILABLE_LANGUAGES)
        {
            listAvailableLanguages();
        }
        else if (action == ACTION_LIST_MODULES_TO_TRANSLATE)
        {
            listModulesToTranslate();
        }
        else
        {
            System.out.println("  Unknown action: " + action);
        }

    }

    public boolean preliminaryChecks()
    {

        if (this.languages == null || this.languages.size() == 0)
        {
            System.out.println(" There are no langauges to translate. Check your configuration file !");
            return false;
        }

        if (this.configuration_files == null || this.configuration_files.size() == 0)
        {
            System.out.println(" There are no module configuration files available. Check your run directory !");
            return false;
        }

        return true;

    }

    private void readProcessorConfiguration()
    {
        System.out.println("readProcessorConfiguration() not implemented !!");
    }

    private void readAvailableConfigs()
    {
        TranslationToolConfigSelector ttcs = new TranslationToolConfigSelector(true);
        this.configuration_files = ttcs.getConfigurationFiles();
    }

    private void processAllLangauges()
    {
        for (Enumeration<TTLanguageInfo> en = this.languages.elements(); en.hasMoreElements();)
        {
            processAllFilesForLanguage(en.nextElement());
        }
    }

    private void processAllFilesForLanguage(String language)
    {
        if (!this.languages.containsKey(language))
        {
            System.out.println(" Selected language " + language + " NOT found !");
            return;
        }

        processAllFilesForLanguage(this.languages.get(language));
    }

    private void processAllFilesForLanguage(TTLanguageInfo language)
    {
        for (int i = 0; i < this.configuration_files.size(); i++)
        {
            ConfigurationEntry ce = this.configuration_files.get(i);
            processModuleForLanguage(ce, language);
        }
    }

    private void processModuleForLanguage(ConfigurationEntry module, TTLanguageInfo language)
    {
        Hashtable<String, String> data = new Hashtable<String, String>();

        data.put("", "");
        // MASTER_FILE_ROOT, MASTER_FILE_LANGUAGE, MODULE_ID
        // FIXME
        System.out.println("processModuleForLanguage() not implemented !!");
    }

    private String resolveDescription(String desc)
    {
        if (this.languages_desc.containsKey(desc))
            return this.languages_desc.get(desc);
        else
            return null;
    }

    private void listAvailableLanguages()
    {
        // FIXME
        System.out.println("listAvailableLanguages() not implemented !!");
    }

    private void listModulesToTranslate()
    {
        // FIXME
        System.out.println("listModulesToTranslate() not implemented !!");

    }

    private void checkPaths()
    {
        // FIXME
        System.out.println("checkPaths() not implemented !!");

        String[] paths = { "../files/", "../files/master_files/", "../files/translation/",
                          "../files/translation/backup/" };

        for (String path : paths)
        {
            File f = new File(path);
            if (!f.exists())
            {
                f.mkdir();
            }
        }

    }

}
