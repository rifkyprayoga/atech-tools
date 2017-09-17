package com.atech.i18n.info;

import java.util.List;

import com.atech.data.enums.LanguageISO639;

/**
 * Created by andy on 30/06/17.
 */
public interface SupportedLanguage
{

    boolean isDefaultLanguage();


    LanguageISO639 getLanguageDefinition();


    /**
     * Only the ones that are supported.
     * 
     * @return
     */
    List<SupportedLanguage> getLanguagesToTranslate();


    SupportedLanguage getMasterLanguage();

}
