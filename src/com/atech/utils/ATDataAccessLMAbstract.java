package com.atech.utils;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.atech.i18n.I18nControlRunner;
import com.atech.i18n.I18nControlRunnerModule;
import com.atech.i18n.info.LanguageModule;
import com.atech.i18n.mgr.LanguageManager;
import com.atech.i18n.tool.simple.data.TranslationToolConfigurationDto;

// TODO: Auto-generated Javadoc
/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/

/**
 * This is DataAccessAbstract class that uses as main class LangaugeManager, instead of
 * I18nControl.
 */

public abstract class ATDataAccessLMAbstract extends ATDataAccessAbstract
{

    protected LanguageManager languageManager;
    protected I18nControlRunner i18nControlRunner = null;

    static Map<LanguageModule, I18nControlRunnerModule> registeredRunners;

    // ********************************************************
    // ****** Constructors and Access methods *****
    // ********************************************************


    // Constructor: DataAccess
    /**
     * 
     * This is DataAccess constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.<br>
     * <br>
     * @param lm 
     * @param icr 
     *
     */
    public ATDataAccessLMAbstract(LanguageManager lm, I18nControlRunner icr)
    {
        super(lm.getI18nControl(icr));
        // log.debug("Loading Language Manager");
        languageManager = lm;
        this.i18nControlRunner = icr;

        if (icr instanceof I18nControlRunnerModule)
        {
            registerI18nControlRunner((I18nControlRunnerModule) icr);
        }
    }


    public LanguageManager getLanguageManager()
    {
        return this.languageManager;
    }


    public I18nControlRunner getI18nControlRunner()
    {
        return this.i18nControlRunner;
    }


    public void registerI18nControlRunner(I18nControlRunnerModule i18nControlRunnerModule)
    {
        if (registeredRunners == null)
            registeredRunners = new HashMap<LanguageModule, I18nControlRunnerModule>();

        registeredRunners.put(i18nControlRunnerModule.getLanguageModule(), i18nControlRunnerModule);
    }


    public abstract TranslationToolConfigurationDto getTranslationToolConfiguration();


    public abstract void saveTranslationToolConfiguration(TranslationToolConfigurationDto configuration);


    /**
     * Gets the as localized date string.
     *
     * @param gc_value the gc_value
     * @param years_digits the years_digits
     *
     * @return the as localized date string
     */
    public String getAsLocalizedDateString(GregorianCalendar gc_value, int years_digits)
    {
        if (years_digits == 2)
        {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                getI18nControlInstance().getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
        else
        {

            // System.out.println("i18ControlInstance: " +
            // getI18nControlInstance());
            // System.out.println("i18ControlInstance.selectedLocale: " +
            // this.languageManager.getSelectedLanguageLocale());
            // System.out.println("i18ControlInstance.selectedLocale: " +
            // this.languageManager.getSelectedLanguageInstance());
            // System.out.println("i18ControlInstance.selectedLocale:locale: " +
            // this.languageManager.getSelectedLanguageInstance().locale);
            // System.out.println("i18ControlInstance.selectedLocale:real_locale:
            // "
            // +
            // this.languageManager.getSelectedLanguageInstance().real_locale);
            // //System.out.println("i18ControlInstance.selectedLocale: " +
            // this.languageManager.getSelectedLanguageInstance());
            //
            // System.out.println("Time: " + gc_value.getTime());

            // TODO: fix this
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                this.languageManager.getSelectedLanguageLocale());
            return df.format(gc_value.getTime());
        }
    }

}
