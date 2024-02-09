package com.atech.db.hibernate.tool.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.db.hibernate.tool.DbTool;
import com.atech.db.hibernate.tool.app.DbToolApplicationInterface;
import com.atech.db.hibernate.tool.data.DatabaseConfiguration;
import com.atech.db.hibernate.tool.data.management.DbToolDbHandler;
import com.atech.db.hibernate.tool.util.DbToolAccess;
import com.atech.db.hibernate.tool.util.I18nControlDbT;
import com.atech.utils.ATSwingUtils;

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

// WORK IN PROGRESS, PLEASE DO NOT TOUCH
// andyrozman

public class PanelDbAction extends JPanel implements ActionListener
{

    private static final long serialVersionUID = -7332134335611323091L;
    I18nControlDbT ic = I18nControlDbT.getInstance();
    DbToolAccess m_da = null;
    private static final Logger LOG = LoggerFactory.getLogger(PanelDbAction.class);

    DbToolApplicationInterface dbToolApplication = null;

    // Font font_big, font_normal, font_normal_b;
    // JLabel label;
    // JButton button;

    PanelDatabaseSet pds;
    DbTool m_dialog;

    JButton testConnectionButton, dbStatusButton, dbInitButton;

    DbToolDbHandler dbToolDbHandler;

    DatabaseConfiguration databaseConfiguration;

    String versionInDb = null;
    String versionRequired = null;
    DbToolDbHandler.DbInitPreStatus dbStatus;


    /**
     * Constructor
     * 
     * @param pds 
     * @param dia
     */
    public PanelDbAction(PanelDatabaseSet pds, DbTool dia)
    {

        super();

        this.pds = pds;

        m_dialog = dia;
        m_da = DbToolAccess.getInstance();

        // font_big = dataAccess.getFont(DbToolAccess.FONT_BIG_BOLD);
        // font_normal_b = dataAccess.getFont(DbToolAccess.FONT_NORMAL_BOLD);
        // font_normal = dataAccess.getFont(DbToolAccess.FONT_NORMAL);

        ATSwingUtils.initLibrary();

        createPanel();

    }


    private void createPanel()
    {

        this.setSize(700, 70);
        this.setLayout(null);
        this.setBorder(new TitledBorder("Database Tools"));

        /*
         * button = new JButton(i18nControl.getMessage("ADD_"));
         * button.setBounds(110, 190, 170, 25);
         * button.setFont(font_normal);
         * button.addActionListener(this);
         * button.setActionCommand("add_");
         * this.add(button);
         */

        testConnectionButton = ATSwingUtils.getButton(ic.getMessage("TEST_CONNECTION"), 20, 25, 140, 25, this,
            ATSwingUtils.FONT_NORMAL, null, "test_connection", this, m_da);

        dbStatusButton = ATSwingUtils.getButton(ic.getMessage("DB_STATUS"), 170, 25, 140, 25, this,
            ATSwingUtils.FONT_NORMAL, null, "db_status", this, m_da);

        dbInitButton = ATSwingUtils.getButton(ic.getMessage("INIT_DB"), 320, 25, 140, 25, this,
            ATSwingUtils.FONT_NORMAL, null, "init_db", this, m_da);

        resetActions();

    }


    /**
     * Set Data
     * 
     * @param intr
     */
    public void setData(DbToolApplicationInterface intr)
    {
        if (!intr.equals(dbToolApplication))
        {
            dbToolApplication = intr;
            this.dbToolDbHandler = new DbToolDbHandler(dbToolApplication);
        }

        resetActions();
    }


    private void resetActions()
    {
        this.dbStatusButton.setEnabled(false);
        // this.dbInitButton.setEnabled(false);
    }


    /**
     *  Action Listener
     */
    public void actionPerformed(ActionEvent e)
    {

        String action = e.getActionCommand();

        if (action.equals("test_connection"))
        {
            testConnection();
        }
        else if ("db_status".equals(action))
        {
            showStatus();
        }
        else if ("init_db".equals(action))
        {
            initDb();
        }
        else
        {
            System.out.println("PanelDatabaseSet::Unknown command: " + action);
        }

    }


    private void testConnection()
    {
        DatabaseConfiguration ds = this.pds.getDatabaseSettings();

        try
        {
            Class.forName(ds.driver_class);
            Connection con_ = DriverManager.getConnection(this.pds.getJDBCUrl(), ds.username, ds.password);
            LOG.info("Connection successful !");

            showInformationDialog(ic.getMessage("CONNECTION_SUCCESFULL"));

            this.dbStatusButton.setEnabled(true);

        }
        catch (Exception ex)
        {
            showErrorDialog(ic.getMessage("CONNECTION_PROBLEM"));
            LOG.error("Exception: " + ex, ex);
        }
    }


    private void showErrorDialog(String errorMessage)
    {
        JOptionPane.showMessageDialog(m_dialog, errorMessage, ic.getMessage("ERROR"), JOptionPane.ERROR_MESSAGE, null);
    }


    private void showInformationDialog(String infoMessage)
    {
        JOptionPane.showMessageDialog(m_dialog, infoMessage, ic.getMessage("INFORMATION"),
            JOptionPane.INFORMATION_MESSAGE, null);
    }


    private void showStatus()
    {
        // FIXME
        this.dbStatus = this.dbToolDbHandler.getDbStatus(this.databaseConfiguration, this.dbToolApplication);

        System.out.println("Show status: " + dbStatus);

        if (versionInDb == null)
        {
            // status = DbToolDbHandler.DbInitPreStatus.EmptyDatabase;
            showInformationDialog(ic.getMessage("DB_VERSION_UNREADABLE"));
        }
        else if (versionRequired.equals(versionInDb))
        {
            // status =
            // DbToolDbHandler.DbInitPreStatus.ExistingDatabaseWithCorrectVersionNumber;
            showInformationDialog(String.format(ic.getMessage("DB_VERSION_CORRECT"), versionRequired));
        }
        else
        {
            // status =
            // DbToolDbHandler.DbInitPreStatus.ExistingDatabaseWithWrongVersionNumber;
            showInformationDialog(String.format(ic.getMessage("DB_VERSION_INCORRECT"), versionRequired, versionInDb));
        }

        this.dbInitButton.setEnabled(true);
    }


    private void initDb()
    {
        // temporary
        // versionInDb =
        // this.dbToolDbHandler.getDbStatus(this.databaseConfiguration);
        // versionRequired = this.dbToolApplication.getCurrentDatabaseVersion();

        dbStatus = DbToolDbHandler.DbInitPreStatus.EmptyDatabase;

        this.dbToolDbHandler.databaseInit(databaseConfiguration, dbStatus);

        DbInitDialog initDialog = new DbInitDialog((JFrame) this.m_da.getParent(), m_da, null);

        initDialog.setVisible(true);

        // TODO
        System.out.println("InitDb not imlemented. ");
    }


    public void setDatabaseSettings(DatabaseConfiguration databaseConfiguration)
    {
        this.databaseConfiguration = databaseConfiguration;
    }
}
