package com.atech.i18n;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.mgr.LanguageManager;

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
 *  This is abstract class for controling I18N. You need to extend this class, and set all variables. With setting 
 *  of variables half of work is done. Next half is way to create this class. You need to make constructor. Sample
 *  constructor for Singelton is in this source file.
 */

/**
 * This is I18nControl class used with conjunction with LanguageManager 
 */

public class I18nControlLangMgrDual extends I18nControlLangMgr
{

    private static Log log = LogFactory.getLog(I18nControlLangMgrDual.class);
    /*
     * private Collator langaugeCollator = null;
     * protected LanguageManager language_manager = null;
     * private I18nControlRunner i18ncontrol_runner;
     */

    private I18nControlLangMgr primaryLang = null;
    private boolean debug_language = false;

    // protected abstract String getLanguageConfigFile();

    /**
     * Constructor
     * 
     * @param lm
     * @param icr
     */
    public I18nControlLangMgrDual(LanguageManager lm, I18nControlRunner icr)
    {
        super(lm, icr);
        // this.i18ncontrol_runner = icr;
        // this.language_manager = lm;
        // this.initLibrary();
        initPrimaryLangauge();
    }

    private void initPrimaryLangauge()
    {
        this.primaryLang = new I18nControlLangMgr(this.language_manager, this.i18ncontrol_runner, true);

        if (this.primaryLang != null)
        {
            if (debug_language)
            {
                log.debug("Default language for " + this.i18ncontrol_runner.getLanguageFileRoot() + " was loaded !");
            }
        }
        else
        {
            log.warn("Default language for " + this.i18ncontrol_runner.getLanguageFileRoot() + " COULD NOT BE loaded !");
        }
    }

    public String getMessageFromCatalogSelectedLanaguge(String msg)
    {
        String retString = super.getMessageFromCatalog(msg);
        if (debug_language)
        {
            log.debug("getMessageFromCatalogSelLang: [root=" + this.i18ncontrol_runner.getLanguageFileRoot()
                    + ", lang=" + this.getSelectedLanguage() + ", msg=" + msg + ", translation=" + retString);
        }
        return retString;
    }

    public String getMessageFromCatalogDefaultLangauge(String msg)
    {
        String retString = this.primaryLang.getMessageFromCatalog(msg);
        if (debug_language)
        {
            log.debug("getMessageFromCatalogDefLang: [root="
                    + this.primaryLang.getI18nControlRunner().getLanguageFileRoot() + ", lang="
                    + this.primaryLang.getSelectedLanguage() + ", msg=" + msg + ", translation=" + retString);
        }
        return retString;
    }

    /**
     *  Looks into bundle and returns correct message. This method is syncronized, so only one
     *  message at the time can be returned.
     * 
     *  @param msg id of message in bundle
     *  @return String message from catalog.
     */
    @Override
    public synchronized String getMessageFromCatalog(String msg)
    {
        if (!checkIfValidMessageKey(msg))
            return msg;

        String retString = getMessageFromCatalogSelectedLanaguge(msg);

        // log.debug("getMessageFromCatalog: [msg=" + msg + ",trasnlation=" +
        // retString);

        if (retString.equals(msg))
        {
            if (this.language_manager.findUntraslatedKeys())
                return retString;

            retString = this.getMessageFromCatalogDefaultLangauge(msg);

            // log.debug("getMessageFromCatalog: [msg=" + msg +
            // ",trasnlation_def=" + retString);

            return retString;
        }
        else
            return retString;
    }

    /**
     * Init Aditional
     */
    @Override
    public void initAdditional()
    {
    }

}
