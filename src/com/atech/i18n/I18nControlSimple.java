package com.atech.i18n;

public class I18nControlSimple extends I18nControlAbstract {


    public I18nControlSimple(String translationFilePrefix, String availableLanguage) {
        this.lang_file_root = translationFilePrefix;
        setLanguage(availableLanguage);
    }



    @Override
    protected String getLanguageConfigFile() {
        return null;
    }

    @Override
    public void init() {

    }
}
