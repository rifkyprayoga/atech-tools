package com.atech.i18n.tool.simple;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.tool.simple.data.DataEntry;
import com.atech.i18n.tool.simple.data.DataListProcessor;
import com.atech.i18n.tool.simple.util.BackupRunner;
import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.i18n.tool.simple.util.TTAboutDialog;
import com.atech.utils.ATSwingUtils;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  TranslationTool - Main class of application
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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

/*
 * TO-DO:
 * // 0.3
 * + read main configuration (0.4)
 * + read user configuration (0.6)
 * + user config: more header types (0.7)
 * + save translation: + in right order (0.5)
 * + with correct header and sub header (0.8)
 * + about (0.9)
 * + auto backup
 * + exit - ask (0.8.5)
 */

// Translation Tool to be called from application (modified version of original
// project)

// 1. not configured, open configuration window
// 1. call language selector (with configured parameters LT_ACTIVE_LANGUAGES if
// empty create one from Enum - GGCLanguage - make interface), Show language
// selector , with last
// edited language preselected (LT_LAST_EDITED_LANGUAGE), ComboBox with all
// languages and last item new Languag
// 2a. go to 3
// 2b. Open New Language Window (has family combo, and all children combo,
// options: Next, Cancel) Next -> Select, save option, create files if they not
// exist
// 3. Open Editor with new files

// checks:
// - source file if in jat newer, update all soources
// - target:
// - if already in project ask (overwrite or not - ASK twice)

// Wizard:
// 1. If there is no configuration open

public class TranslationToolInternal extends TranslationTool
{

    private static final Logger LOG = LoggerFactory.getLogger(TranslationToolInternal.class);
    private static final long serialVersionUID = 8072388083288536444L;
    Hashtable<String, JMenu> menus = null;
    DataAccessTT m_da = DataAccessTT.getInstance();
    I18nControlAbstract m_ic = null;

    /**
     * Version of TT
     */
    public static String m_version = "1.2.2";
    DataListProcessor dlp;

    JLabel module, group, index, keyword, sub_group;
    JLabel[] statuses;
    JButton priority;
    JTextArea jt_source, jt_desc, jt_mine;
    JComboBox cmb_status;


    /**
     * Constructor
     *
     * @param config_filename
     *            main configuration filename
     */
    public TranslationToolInternal(String config_filename)
    {
        super();

        m_da = DataAccessTT.createInstance(this);
        m_ic = m_da.getI18nControlInstance();
        checkPaths();

        init();

        this.dlp = new DataListProcessor(config_filename);
        this.dlp.moveFirst();
        this.readData();

        // config read error

        if (!dlp.wasConfigurationRead())
        {
            showTypesDialog("Configuration file invalid!", JOptionPane.ERROR_MESSAGE);
        }
        else if (!this.dlp.isMasterFileMasterFile())
        {
            showTypesDialog("Master file is not real master file.", JOptionPane.ERROR_MESSAGE);
        }
        else if (!this.dlp.wasMasterFileRead())
        {
            showTypesDialog("Master file was not read correctly.", JOptionPane.ERROR_MESSAGE);
        }
        else if (!this.dlp.wasUserConfigRead())
        {
            showTypesDialog("User config not read correctly.", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            BackupRunner br = new BackupRunner(this.dlp);
            br.start();

            this.readModuleInfo();
            this.setSize(520, 640);
            this.setVisible(true);
        }
    }


    private void showTypesDialog(String msg, int type)
    {
        String type_desc = "";

        if (type == JOptionPane.ERROR_MESSAGE)
        {
            type_desc = "Error";
        }
        else if (type == JOptionPane.WARNING_MESSAGE)
        {
            type_desc = "Warning";
        }
        else if (type == JOptionPane.INFORMATION_MESSAGE)
        {
            type_desc = "Info";
        }

        JOptionPane.showMessageDialog(this, msg, type_desc, type);
    }


    protected void init()
    {
        super.init();

        this.setTitle("Simple Translation Tool (v" + m_version + ")");
    }


    private void checkPaths()
    {
        String[] paths = { "../files/", "../files/master_files/", "../files/translation/",
                           "../files/translation/backup/" };

        for (String path : paths)
        {
            File f = new File(path);
            if (!f.exists())
            {
                f.mkdir();
            }
        }

    }


    /**
     * Read Module Info
     */
    public void readModuleInfo()
    {
        String md = this.m_da.getTranslationConfig().getSetting("MODULE", "Unknown module");
        md += " [";
        md += this.m_da.getTranslationConfig().getSetting("MODULE_VERSION", "???");
        md += "]";

        module.setText(md);
    }


    /**
     * Read Data
     */
    public void readData()
    {
        DataEntry de = this.dlp.getCurrentEntry();

        this.jt_source.setText(de.master_file_translation);

        if (de.description == null)
        {
            this.jt_desc.setText("No description available.");
        }
        else
        {
            this.jt_desc.setText(de.description);
        }

        this.jt_mine.setText(de.target_translation);
        // System.out.println("De Target Translation:" + de.target_translation);

        this.cmb_status.setSelectedIndex(de.status);

        this.index.setText(dlp.getCurrentIndex() + 1 + "");
        this.keyword.setText(de.key);

        this.group.setText(de.getGroupInfo());
        this.sub_group.setText(de.getSubGroupInfo());

        this.priority.setText("" + de.getPriority());

        this.dlp.resetStatus();

        int[] st = this.dlp.getStatuses();

        this.statuses[0].setText("" + st[0]);
        this.statuses[1].setText("" + st[1]);
        this.statuses[2].setText("" + st[2]);
    }


    /**
     * Save Data
     */
    public void saveData()
    {
        DataEntry de = this.dlp.getCurrentEntry();

        System.out.println("Save Data");

        // translation not same
        if (!de.target_translation.equals(this.jt_mine.getText()))
        {
            // changed
            de.target_translation = this.jt_mine.getText();

            // not translated
            if (this.cmb_status.getSelectedIndex() == 0)
            {
                if (de.target_translation.equals(de.master_file_translation))
                {
                    int st = 0;

                    // XXX This makes no sense: status is "Not Translated" and
                    // translation matches master - why should we ask to verify
                    // the "untranslated" status in a way that suggests we
                    // marked it as translated?

                    // st = JOptionPane.showOptionDialog(this,
                    // "Your translation looks same as translation\n"
                    // + "from master file. This indicates that no\n"
                    // + "translation was made. But this also might not\n"
                    // + "be true. Select status for this translation.",
                    // "Change Status",
                    // JOptionPane.OK_CANCEL_OPTION,
                    // JOptionPane.WARNING_MESSAGE, null,
                    // this.dataAccess.status,
                    // this.dataAccess.status[2]);

                    de.status = st;
                    this.dlp.resetStatus();
                }
                else
                {
                    de.status = DataEntry.STATUS_TRANSLATED;
                    this.dlp.resetStatus();
                }
            }
            else
            {
                // status not zero
                de.status = this.cmb_status.getSelectedIndex();
                this.dlp.resetStatus();
            }

        }
        else
        {
            // same
            if (de.status != this.cmb_status.getSelectedIndex())
            {
                de.status = this.cmb_status.getSelectedIndex();
                this.dlp.resetStatus();
            }
        }

    }


    private void initMenus()
    {
        JMenuBar mbar = new JMenuBar();
        menus = new Hashtable<String, JMenu>();

        JMenu menu = ATSwingUtils.createMenu("File", "File", m_ic);

        ATSwingUtils.createMenuItem(menu, "Exit", "Exit Application", "exit", this, null, m_ic, m_da, this,
            ATSwingUtils.FONT_NORMAL);

        menus.put("FILE", menu);
        mbar.add(menu);

        menu = ATSwingUtils.createMenu("Tools", "Tools", m_ic);

        ATSwingUtils.createMenuItem(menu, "Copy translations", "Copy translations", "copy_translations", this, null,
            m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        menu.addSeparator();
        ATSwingUtils.createMenuItem(menu, "Properties...", "Properties", "properties", this, null, m_ic, m_da, this,
            ATSwingUtils.FONT_NORMAL);

        menus.put("TOOLS", menu);
        mbar.add(menu);

        menu = ATSwingUtils.createMenu("Help", "Help", m_ic);

        ATSwingUtils.createMenuItem(menu, "About", "About Software", "about", this, null, m_ic, m_da, this,
            ATSwingUtils.FONT_NORMAL);

        menus.put("HELP", menu);
        mbar.add(menu);

        this.setJMenuBar(mbar);

    }


    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TranslationToolConfigSelector ttcs =
        new TranslationToolConfigSelector();

        /*
         * if (ttcs.wasAction()) { ConfigurationEntry ce =
         * ttcs.getSelectedItem();
         * new TranslationTool(ce.config_file); }
         * System.exit(0);
         */

        /*
         * if (args.length!=1) { System.out.println(
         * "You need to specify one parameter: config file, with full path !");
         * } else { new TranslationTool(args[0]); }
         */
    }


    private void cmdQuit()
    {
        System.exit(0);
    }


    /**
     * Action Performed
     */
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();

        if (cmd.equals("exit"))
        {
            saveAndExit();
        }
        else if (cmd.equals("about"))
        {
            new TTAboutDialog(this);
        }
        else if (cmd.equals("copy_text"))
        {
            jt_mine.setText(jt_source.getText());
            cmb_status.setSelectedIndex(0);
        }
        else if (cmd.equals("next"))
        {
            this.saveData();

            if (this.dlp.moveNext())
            {
                this.readData();
            }
        }
        else if (cmd.equals("prev"))
        {
            this.saveData();

            if (this.dlp.movePrev())
            {
                this.readData();
            }
        }
        else if (cmd.equals("next_untranslated"))
        {
            this.saveData();

            if (this.dlp.moveNextUntranslated())
            {
                this.readData();
            }
        }
        else if (cmd.equals("prev_untranslated"))
        {
            this.saveData();

            if (this.dlp.movePrevUntranslated())
            {
                this.readData();
            }
        }
        else if (cmd.equals("save"))
        {
            this.saveData();
            this.dlp.saveTranslation();
        }
        else if (cmd.equals("show_group_info"))
        {
            showTypesDialog(this.m_da.getTranslationConfig().getPrioritiesLegend(), JOptionPane.INFORMATION_MESSAGE);
        }
        else if (cmd.equals("copy_translations"))
        {
            System.out.println("!!! Copy Translations !!!");
        }
        else if (cmd.equals("big_display"))
        {
            TranslationDetail td = new TranslationDetail(this, this.jt_source.getText(), this.jt_mine.getText());

            // System.out.println("was action: " + td.wasAction());

            if (td.wasAction())
            {
                // System.out.println("was action: " + td.wasAction() +
                // ", data:" + td.getData());

                this.jt_mine.setText(td.getData());
            }

        }
        else
        {
            System.out.println("Unknown command: " + cmd);
        }
    }


    /**
     * Exit, possibly after saving.
     */
    private void saveAndExit()
    {
        // Custom button text
        Object[] options = { "Save and Quit", "Just Quit", };
        int n = JOptionPane.showOptionDialog(this,
            "Would you like to save all translations that were\n" + "changed in this session?", "Exiting",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        LOG.debug("Exit: code=" + n);

        if (n == 0)
        {
            this.saveData();
            this.dlp.saveTranslation();
        }

        cmdQuit();
    }


    public void windowOpened(WindowEvent e)
    {
    }


    public void windowClosing(WindowEvent e)
    {
        saveAndExit();
    }


    public void windowClosed(WindowEvent e)
    {
    }


    public void windowIconified(WindowEvent e)
    {
    }


    public void windowDeiconified(WindowEvent e)
    {
    }


    public void windowActivated(WindowEvent e)
    {
    }


    public void windowDeactivated(WindowEvent e)
    {
    }
}
