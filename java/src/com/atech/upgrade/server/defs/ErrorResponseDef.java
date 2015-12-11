package com.atech.upgrade.server.defs;

/**
 * Created by andy on 12.03.15.
 */
public enum ErrorResponseDef
{

    ApplicationNotSet(1001, "ATUS_ApplicationNotSet", "Parameter for Aplication Not Set"), //
    VersionNotSet(1002, "ATUS_VersionNotSet", "Parameter for Version not set"), //
    ActionNotSet(1003, "ATUS_ActionNotSet", "Action not set"),

    ApplicationNotAvailable(1101, "ATUS_ApplicationNotAvailable", "Application not available"), //

    // SecondParameterNotSet(1003, "Second parameter not set", null),

    VersionNotAvailable(1003, "ATUS_VersionNotAvailable", "Version not available"), //
    ActionNotAvailable(1003, "ATUS_ActionNotAvailable", "Action not available"), //
    NoXmlDataForUpdate(1003, "ATUS_NoXmlDataForUpdate", "No Xml Data for Update");

    int code;
    String desctiption;
    String descriptionKey;


    ErrorResponseDef()
    {

    }


    ErrorResponseDef(int code, String i18nKey, String description)
    {
        this.code = code;
        this.desctiption = description;
        this.descriptionKey = i18nKey;
    }


    public int getCommandCode()
    {
        return code;
    }


    public String getCommandDescription()
    {
        return this.desctiption;
    }


    public String getCommandDescriptionKey()
    {
        return this.descriptionKey;
    }

}
