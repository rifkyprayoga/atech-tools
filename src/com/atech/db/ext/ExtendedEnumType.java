package com.atech.db.ext;

public interface ExtendedEnumType<E extends ExtendedEnumType> // Enum<E>> //
                                                              // extends
// CodeEnumWithTranslation
{

    E getEnumTypeByKey(String key);


    E getEnumTypeByName(String key);


    E[] getAllValues();


    String getI18nKey();


    String getTranslation();


    void setTranslation(String translation);


    String getName();


    boolean useI18nKey();
}
