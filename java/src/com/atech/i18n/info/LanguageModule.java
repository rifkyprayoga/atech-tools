package com.atech.i18n.info;

import java.util.List;

/**
 * Created by andy on 30/06/17.
 */
public interface LanguageModule
{

    String getLanguageFilePath();


    String getLanguageFileRoot();


    String getModuleDescription();


    String getModuleId();


    List<LanguageModule> getAllModules();


    LanguageModule getMainModule();

}
