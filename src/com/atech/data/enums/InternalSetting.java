package com.atech.data.enums;

/**
 * Created by andy on 19/05/17.
 */
public enum InternalSetting implements InternalSettingInterface
{
    Help_Settings_UserAddEdit("Help.Settings.UserAddEdit"), //
    Help_TranslationTool_Wizard1("Help.TranslationTool.Wizard1"), //
    Help_TranslationTool_Wizard2("Help.TranslationTool.Wizard2"), //
    Help_TranslationTool_Wizard3("Help.TranslationTool.Wizard3"), //
    Help_TranslationTool_Configuration("Help.TranslationTool.Configuration"), //
    ;

    String key;


    InternalSetting(String key)
    {

    }


    public String getKey()
    {
        return key;
    }

}
