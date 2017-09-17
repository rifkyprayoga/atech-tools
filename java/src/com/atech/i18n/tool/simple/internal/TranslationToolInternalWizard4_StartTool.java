package com.atech.i18n.tool.simple.internal;

import javax.swing.*;

import com.atech.i18n.tool.simple.data.TranslationToolConfigurationDto;
import com.atech.utils.ATDataAccessLMAbstract;

/**
 * Created by andy on 01.07.17.
 */
public class TranslationToolInternalWizard4_StartTool
{

    public TranslationToolInternalWizard4_StartTool(JFrame frame, ATDataAccessLMAbstract dataAccess,
            TranslationToolConfigurationDto configuration)
    {

        System.out.println("START LANGUAGE TOOL");

        //configuration.getSettings().setLastTranslatedLanguage();

        System.out.println("Settings: " + configuration);

        // check if files already copied/created
        // copy/create files
        // save setting
        // start tool
    }
}
