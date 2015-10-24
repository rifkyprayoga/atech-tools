package com.atech.gui_fw;

import com.atech.app.AbstractApplicationContext;
import com.atech.app.gui.about.AboutDialog;
import com.atech.utils.ATDataAccessAbstract;

/**
 *    Moj s.p. 
 *   ==========
 * 
 *  <b>Name:</b>         ----<br>
 *  <b>Description:</b>  ----<br>
 *  <b>Copyright:</b>    Copyright (c) 2006-2010 by Andy<br>
 *  <b>Licence:</b>      Commercial<br>
 *
 *  <br><br>
 *    This software is used for handling data for companies (bills, projects, project
 *    data, clocking, ...) 
 * 
 *  @author              Andy (Aleksander) Rozman {andy@atech-software.com}
 *  @version             0.3
 */

public abstract class AboutDialogApp extends AboutDialog
{

    private static final long serialVersionUID = 700501667084363317L;
    ATDataAccessAbstract m_da = null;


    /**
     * Constructor
     * @param aac 
     */
    public AboutDialogApp(AbstractApplicationContext aac)
    {
        super(aac.getFrame(), true, aac.getI18nControl());
        initAbout();
    }


    public abstract void initAbout();

}
