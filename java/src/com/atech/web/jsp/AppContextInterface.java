
package com.atech.web.jsp;

import com.atech.web.db.AppDbWebAbstract;
import com.atech.web.util.DataAccessWeb;
import com.atech.web.util.I18nWebControl;

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


public interface AppContextInterface  
{

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public int getType(); 
    
    /**
     * Inits the context.
     */
    public void initContext();
    
    /**
     * Gets the db.
     * 
     * @return the db
     */
    public AppDbWebAbstract getDb();
    
    /**
     * Gets the i18n.
     * 
     * @param lang
     *            the lang
     * @return the i18n
     */
    public I18nWebControl getI18n(String lang);
    
    /**
     * Gets the data access web.
     * 
     * @return the data access web
     */
    public DataAccessWeb getDataAccessWeb();
    
    /**
     * Gets the i18n.
     * 
     * @return the i18n
     */
    public I18nWebControl getI18n();
    
    /**
     * Checks for multiple language changer.
     * 
     * @return true, if successful
     */
    public boolean hasMultipleLanguageChanger();
    
    /**
     * Dispose context.
     */
    public void disposeContext();
    
}
