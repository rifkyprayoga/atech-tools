package com.atech.utils.data;

public interface CodeEnumWithTranslation extends CodeEnum
{

    String getI18nKey();


    String getTranslation();


    void setTranslation(String translation);


    String getName();

}
