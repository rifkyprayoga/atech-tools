package com.atech.app.defs;

import java.util.List;

import com.atech.app.data.about.CreditsGroup;
import com.atech.app.data.about.FeaturesGroup;
import com.atech.i18n.I18nControlRunner;
import com.atech.i18n.mgr.LanguageManager;
import com.atech.utils.ATDataAccessAbstract;

/**
 * Created by andy on 18.10.15.
 */
public interface AppPluginDefinition
{

    /**
     * Get CreditsGroups for application/plugin. In most cases we have just one, but sometimes more.
     *
     * @return List of Credit Groups
     */
    List<CreditsGroup> getCredits();


    /**
     * Get About Image path.
     *
     * @return path to about image
     */
    String getAboutImagePath();


    int[] getAboutImageSize();


    /**
     * Get Copyright From
     * @return
     */
    int getCopyrightFrom();


    /**
     * Get Copyright Till
     * @return
     */
    int getCopyrightTill();


    /**
     * Get List of Feature Groups (each group can have 1 or more features)
     *
     * @return List of Feature Groups
     */
    List<FeaturesGroup> getFeatures();


    /**
     * Get Plugin/App Version
     *
     * @return String representation of version
     */
    String getPluginVersion();


    /**
     * Get Name of Plugin (for internal use)
     *
     * @return Name of Plugin/App
     */
    String getPluginName();


    LanguageManager getLanguageManager();


    I18nControlRunner getI18nControlRunner();


    void setDataAccess(ATDataAccessAbstract dataAccess);

}
