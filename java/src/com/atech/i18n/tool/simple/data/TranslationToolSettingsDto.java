package com.atech.i18n.tool.simple.data;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by andy on 30.06.17.
 */
public class TranslationToolSettingsDto
{

    private Set<String> editedLanguages = new HashSet<String>();

    private String translatorName;

    private String translatorEmail;

    private int autobackupTime;

    private String lastTranslatedLanguage;


    public Set<String> getEditedLanguages()
    {
        return editedLanguages;
    }


    public void setEditedLanguages(Set<String> editedLanguages)
    {
        this.editedLanguages = editedLanguages;
    }


    public String getTranslatorName()
    {
        return translatorName;
    }


    public void setTranslatorName(String translatorName)
    {
        this.translatorName = translatorName;
    }


    public String getTranslatorEmail()
    {
        return translatorEmail;
    }


    public void setTranslatorEmail(String translatorEmail)
    {
        this.translatorEmail = translatorEmail;
    }


    public int getAutobackupTime()
    {
        return autobackupTime;
    }


    public void setAutobackupTime(int autobackupTime)
    {
        this.autobackupTime = autobackupTime;
    }


    public String getLastTranslatedLanguage()
    {
        return lastTranslatedLanguage;
    }


    public void setLastTranslatedLanguage(String lastTranslatedLanguage)
    {
        this.lastTranslatedLanguage = lastTranslatedLanguage;
    }


    public void addEditedLanguage(String languageCode)
    {
        if (!editedLanguages.contains(languageCode))
        {
            editedLanguages.add(languageCode);
        }
    }


    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("editedLanguages", editedLanguages)
                .append("translatorName", translatorName).append("translatorEmail", translatorEmail)
                .append("autobackupTime", autobackupTime).append("lastTranslatedLanguage", lastTranslatedLanguage)
                .toString();
    }
}
