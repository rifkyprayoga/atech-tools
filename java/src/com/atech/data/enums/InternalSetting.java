package com.atech.data.enums;

/**
 * Created by andy on 19/05/17.
 */
public enum InternalSetting implements InternalSettingInterface
{
    Help_Settings_UserAddEdit("Help.Settings.UserAddEdit") //
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
