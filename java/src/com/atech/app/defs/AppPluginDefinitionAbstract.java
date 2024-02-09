package com.atech.app.defs;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.atech.app.data.about.FeaturesEntry;
import com.atech.app.data.about.FeaturesGroup;
import com.atech.app.data.about.LibraryInfoEntry;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.I18nControlRunner;
import com.atech.i18n.mgr.LanguageManager;
import com.atech.utils.ATDataAccessAbstract;

/**
 * Created by andy on 18.10.15.
 */
public abstract class AppPluginDefinitionAbstract implements AppPluginDefinition
{

    protected ATDataAccessAbstract dataAccess;
    protected I18nControlAbstract i18nControl;

    protected LanguageManager languageManager;
    protected I18nControlRunner i18nControlRunner = null;


    @Deprecated
    public AppPluginDefinitionAbstract()
    {
    }


    @Deprecated
    public AppPluginDefinitionAbstract(ATDataAccessAbstract dataAccess)
    {
        setDataAccess(dataAccess);
    }


    public AppPluginDefinitionAbstract(LanguageManager languageManager, I18nControlRunner i18nControlRunner)
    {
        this.languageManager = languageManager;
        this.i18nControlRunner = i18nControlRunner;
    }


    public void setDataAccess(ATDataAccessAbstract dataAccess)
    {
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();
    }


    public int getCopyrightTill()
    {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.get(Calendar.YEAR);
    }


    public List<LibraryInfoEntry> getPluginLibraries()
    {
        return null;
    }


    public LanguageManager getLanguageManager()
    {
        return languageManager;
    }


    public void setLanguageManager(LanguageManager languageManager)
    {
        this.languageManager = languageManager;
    }


    public I18nControlRunner getI18nControlRunner()
    {
        return i18nControlRunner;
    }


    public void setI18nControlRunner(I18nControlRunner i18nControlRunner)
    {
        this.i18nControlRunner = i18nControlRunner;
    }


    protected void addFeatureGroup(String name, List<FeaturesEntry> featuresList, List<FeaturesGroup> featuresGroupList)
    {
        if (CollectionUtils.isEmpty(featuresList))
        {
            return;
        }

        FeaturesGroup fg = new FeaturesGroup(this.i18nControl.getMessage(name));

        for (FeaturesEntry featuresEntry : featuresList)
        {
            fg.addFeaturesEntry(featuresEntry);
        }

        featuresGroupList.add(fg);
    }

}
