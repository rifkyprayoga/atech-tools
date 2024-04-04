package com.atech.i18n.mgr;

public abstract class LanguageManagerRunner
{

    /**
     * Is Translation Tool Languages supported.
     * 
     * @return
     */
    public abstract boolean isTranslationToolLanguageSupported();

    /**
     * Gets the language config file. This is file containing definitions for all languages supported
     * by application
     * 
     * @return the language config file
     */
    public abstract String getLanguageConfigFile();

    /**
     * Gets the language selection file. This is probably application configuration file. It needs to contain 
     * only one setting, which will be used to load correct language (SELECTED_LANG). 
     * 
     * @return the language config file
     */
    public abstract String getLanguageSelectionConfigFile();

    /**
     * Gets the Translation Tool Config file. If TT is enabled this setting is required. 
     * 
     * @return the language config file
     */
    public abstract String getTranslationToolConfigFile();

    public abstract String getDefaultLanguage();

    /**
     * With some applications, some of the configurations can be staticaly put in
     * application. If this is the case, this method needs to be overwritten.
     * @return false by default
     */
    public boolean isLanguageConfigStatic() {
        return false;
    }

}
