package com.atech.i18n.tool.simple.data;

import java.util.List;

import com.atech.data.enums.LanguageISO639;
import com.atech.i18n.info.LanguageModule;
import com.atech.i18n.info.SupportedLanguage;

/**
 * Created by andy on 30.06.17.
 */
public class TranslationToolConfigurationDto
{

    private List<LanguageModule> modules;

    private LanguageModule mainModule;

    private List<SupportedLanguage> supportedLanguages;

    private SupportedLanguage masterLanguage;

    private TranslationToolSettingsDto settings;

    private LanguageModule selectedModule;

    private LanguageISO639 selectedLanguage;


    public List<LanguageModule> getModules()
    {
        return modules;
    }


    public void setModules(List<LanguageModule> modules)
    {
        this.modules = modules;
    }


    public TranslationToolSettingsDto getSettings()
    {
        return settings;
    }


    public void setSettings(TranslationToolSettingsDto settings)
    {
        this.settings = settings;
    }


    public List<SupportedLanguage> getSupportedLanguages()
    {
        return supportedLanguages;
    }


    public void setSupportedLanguages(List<SupportedLanguage> supportedLanguages)
    {
        this.supportedLanguages = supportedLanguages;
    }


    public SupportedLanguage getMasterLanguage()
    {
        return masterLanguage;
    }


    public void setMasterLanguage(SupportedLanguage masterLanguage)
    {
        this.masterLanguage = masterLanguage;
    }


    public LanguageModule getMainModule()
    {
        return mainModule;
    }


    public void setMainModule(LanguageModule mainModule)
    {
        this.mainModule = mainModule;
    }


    public LanguageModule getSelectedModule()
    {
        return selectedModule;
    }


    public void setSelectedModule(LanguageModule selectedModule)
    {
        this.selectedModule = selectedModule;
    }


    public LanguageISO639 getSelectedLanguage()
    {
        return selectedLanguage;
    }


    public void setSelectedLanguage(LanguageISO639 selectedLanguage)
    {
        this.selectedLanguage = selectedLanguage;
        this.settings.setLastTranslatedLanguage(this.selectedLanguage.getCode1());

        this.settings.addEditedLanguage(this.selectedLanguage.getCode1());
    }


    @Override
    public String toString()
    {
        return "TranslationToolConfigurationDto [" + "settings=" + settings + //
                ", selectedModule=" + selectedModule + //
                ", selectedLanguage=" + selectedLanguage + ']';
    }
}
