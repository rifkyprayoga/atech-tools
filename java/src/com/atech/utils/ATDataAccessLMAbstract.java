package com.atech.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.I18nControlRunner;
import com.atech.i18n.mgr.LanguageManager;

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

    private static Log log = LogFactory.getLog(ATDataAccessLMAbstract.class);

    protected LanguageManager lang_mgr;
    protected I18nControlRunner m_icr = null; 
    
    
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
     * @param ic 
     * 
     */
    public ATDataAccessLMAbstract(LanguageManager lm, I18nControlRunner icr)
    {
        super(lm.getI18nControl(icr));
        log.debug("Loading Language Manager");
        lang_mgr = lm;
        this.m_icr = icr;
    }

    
    
    public LanguageManager getLanguageManager()
    {
        return this.lang_mgr;
    }
    
     
    
    
}