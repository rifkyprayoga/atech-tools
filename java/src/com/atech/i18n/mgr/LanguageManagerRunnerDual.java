package com.atech.i18n.mgr;

public abstract class LanguageManagerRunnerDual extends LanguageManagerRunner
{

    /**
     * Find untraslated Keys. If you need to determine which keywords are NOT translated in your language, set this to true, else
     * set to false.
     * 
     * @return
     */
    public abstract boolean findUntraslatedKeysInLanguage();
    
}
