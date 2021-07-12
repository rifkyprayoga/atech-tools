package com.atech.i18n.tool.simple.data;

import java.io.File;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.utils.data.Rounding;
import com.atech.utils.file.PropertiesFile;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  DataListProcessor - Main processor file
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

public class DataListProcessor
{

    DataAccessTT m_da = DataAccessTT.getInstance();
    TranslationData tra_data;
    private static final Logger LOG = LoggerFactory.getLogger(DataListProcessor.class);

    protected String master_file_name = "GGC_en";
    protected String module_id = "";
    protected String master_file_root = "";

    MasterFileReader mfr = null;

    private boolean master_file_read = false;
    // private boolean translation_file_read = false;
    private int current_index;

    private boolean main_configuration_read = false;
    private boolean user_configuration_read = false;

    private PropertiesFile user_config = null;
    // private PropertiesFile master_config = null;
    private int backup_time = 5;
    private boolean isMasterFileMasterFile;


    /**
     * Constructor 
     * 
     * @param _main_config
     */
    public DataListProcessor(String _main_config)
    {

        tra_data = new TranslationData();
        this.m_da.translation_data = tra_data;

        readConfiguration(_main_config);
        readUserConfiguration();

        readMasterFile();
        readMasterFileConfig();

        this.tra_data = m_da.getTranslationData();
        readTranslationTarget();
        readTranslationTargetConfig();
    }


    /**
     * Constructor
     * 
     * @param settings
     */
    public DataListProcessor(Hashtable<String, String> settings)
    {
        tra_data = new TranslationData();
        this.m_da.translation_data = tra_data;

        readDeveloperConfiguration(settings);

        readMasterFile();
        readMasterFileConfig();

        this.tra_data = m_da.getTranslationData();
        readTranslationTarget();
        readTranslationTargetConfig();
    }


    private void readDeveloperConfiguration(Hashtable<String, String> settings)
    {
        /*
         * master_file_root = pp.get("MASTER_FILE_ROOT");
         * master_file_name = master_file_root + "_" +
         * pp.get("MASTER_FILE_LANGUAGE");
         * module_id = pp.get("MODULE_ID");
         */

        user_config = new PropertiesFile(settings);

    }


    /**
     * Was Master File Read 
     * 
     * @return
     */
    public boolean wasMasterFileRead()
    {
        return this.master_file_read;
    }


    /**
     * Read Configuration
     * 
     * @param main_config
     */
    public void readConfiguration(String main_config)
    {
        LOG.debug("Reading application configuration !");

        PropertiesFile pp = new PropertiesFile(main_config);

        master_file_root = pp.get("MASTER_FILE_ROOT");
        master_file_name = master_file_root + "_" + pp.get("MASTER_FILE_LANGUAGE");
        module_id = pp.get("MODULE_ID");

        this.main_configuration_read = pp.wasFileRead();

        if (!this.main_configuration_read)
        {
            LOG.error("Problem reading main configuration (file=" + main_config + ")");
        }
    }


    /**
     * Was Configuration Read
     * 
     * @return
     */
    public boolean wasConfigurationRead()
    {
        return this.main_configuration_read;
    }


    /**
     * Read User Configuration
     */
    public void readUserConfiguration()
    {
        LOG.debug("Reading user configuration !");
        String name = null;

        name = "../config/TranslatorSettings.config";
        if (new File(name).exists())
        {
            LOG.debug("Found TranslatorSettings.config !");
        }
        else
        {
            LOG.debug("Not found TranslatorSettings.config, reading default instead !");
            name = "../config/TranslatorSettings.config_default";
        }

        user_config = new PropertiesFile(name);

        this.user_configuration_read = this.user_config.wasFileRead();

        if (user_config.containsKey("AUTOBACKUP_TIME"))
        {
            try
            {
                int tm = Integer.parseInt(user_config.get("AUTOBACKUP_TIME"));
                this.backup_time = tm;
            }
            catch (Exception ex)
            {}
        }

        LOG.debug("   File was read: " + this.user_configuration_read);

        /*
         * #
         * # Main header (this is main header, which will be used, if we haven't
         * created custom headers for each
         * # module this one will be used, if we haven't created this one (not
         * required), default will be used)
         * #
         * # HEADER_1=
         * # HEADER_2= ***************************************************
         * # HEADER_2= *** ***
         * # HEADER_3= ***************************************************
         * # HEADER_4= ***************************************************
         * # HEADER_4= ***************************************************
         * %$%MODULE_NAME%$%
         * %$%MODULE_VERSION%$%
         * %$%LANGUAGE%$%
         * %$%AUTHOR%$%
         */

    }


    /**
     * Was user config read
     * 
     * @return
     */
    public boolean wasUserConfigRead()
    {
        return this.user_configuration_read;
    }


    /**
     * Read Master File
     */
    public void readMasterFile()
    {
        // readMasterFile(this.master_file_name);
        mfr = readMasterFile(this.master_file_name);
        master_file_read = mfr.wasFileRead();
    }


    /**
     * Read Master File
     * 
     * @param master_file_name 
     * @return 
     */
    public MasterFileReader readMasterFile(String master_file_name)
    {
        LOG.debug("Reading master file: " + master_file_name + " !");
        mfr = new MasterFileReader("../files/master_files/" + master_file_name + ".properties", this);
        // master_file_read = mfr.wasFileRead();

        LOG.debug("   Is Master file: " + mfr.isMasterFile());
        LOG.debug("   Was file read: " + mfr.wasFileRead());

        return mfr;
        // System.out.println("Master File: " + mfr.isMasterFile());
    }


    /**
     * Is file treated as Master file, really master file
     * 
     * @return
     */
    public boolean isMasterFileMasterFile()
    {
        return this.mfr.isMasterFile();
    }


    /**
     * Read Master File Config
     */
    public void readMasterFileConfig()
    {
        // System.out.println("DataListProcessor.readMasterFileConfig() NOT
        // implemented !");
        LOG.debug("Read master file config ");

        PropertiesFile pp = null; // new PropertiesFile("../files/master_files/"
                                  // + master_file_name + ".config");

        // config is optional
        try
        {
            pp = new PropertiesFile("../files/master_files/" + master_file_name + ".config");
        }
        catch (Exception ex)
        {
            return;
        }

        LOG.debug("   Was file read: " + pp.wasFileRead());

        // this.translation_file_read = pp.file_read;
        int idx = -1;
        String key_sub;

        for (String key : pp.keySet())
        {
            //String key = en.nextElement();

            if (key.contains("__DESCRIPTION"))
            {
                idx = key.indexOf("__DESCRIPTION");
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);
                de.description = pp.get(key);
            }
            else if (key.contains("__INVALIDATED"))
            {
                idx = key.indexOf("__INVALIDATED");
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);
                de.invalidated = Long.parseLong(pp.get(key));
            }
            else
            {
                LOG.warn("This key type is unknown !!!");
            }

        }

    }


    private void readTranslationTarget()
    {
        LOG.debug("Read Translation Target");

        PropertiesFile pp = new PropertiesFile("../files/translation/" + getTargetFileRoot() + ".properties");

        // x this.translation_file_read = pp.wasFileRead();

        LOG.debug("   Was file read: " + pp.wasFileRead());

        for (String key : pp.keySet())
        {
            //String key = en.nextElement();

            if (this.tra_data.containsKey(key))
            {
                DataEntry de = this.tra_data.get(key);

                // value = value.replace("\n", "\\n");
                de.target_translation = pp.get(key).replace("\n", "\\n");
                // de.target_translation = pp.get(key);
            }
        }

    }


    /**
     * Get Full Header
     * 
     * @return
     */
    public String getFullHeader()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(getHeader());
        sb.append(getStatus());
        sb.append(getSubHeaderFull());
        sb.append(getCollationRules());

        return sb.toString();
    }


    private String getHeader()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("#\n");
        sb.append("#  ######################################################################\n");
        sb.append("#  ###" + getTextLineCenter("GNU Glucose Control", 64) + "###\n");
        // " GNU Glucose Control ###\n");
        sb.append("#  ######################################################################\n");
        sb.append("#  ###    Language: " + getTextLine(this.user_config.get("TRANSLATION_LANGUAGE"), 50) + "###\n");
        sb.append("#  ###    Created by: "
                + getTextLine(
                    this.user_config.get("TRANSLATOR_NAME") + " (" + this.user_config.get("TRANSLATOR_EMAIL") + ")", 48)
                + "###\n");
        sb.append("#  ###    Version: " + getTextLine(m_da.getTranslationConfig().get("MODULE_VERSION"), 51) + "###\n");
        sb.append("#  ###    Last change: " + getTextLine(m_da.getCurrentDateTimeString(), 47) + "###\n");
        sb.append("#  ######################################################################\n");
        sb.append("#\n");

        return sb.toString();
    }


    private String getStatus()
    {
        int[] st = this.tra_data.getStatuses();

        int all = st[0] + st[1] + st[2];

        StringBuffer sb = new StringBuffer();
        sb.append("#\n");
        sb.append("#  Translation status:\n");
        sb.append("#     Words/expressions:    " + all + "\n");
        sb.append("#     Not translated:       " + st[0] + "  (" + getPercents(st[0], all) + " %)\n");
        sb.append("#     Need to be checked:   " + st[1] + "  (" + getPercents(st[1], all) + " %)\n#\n");
        sb.append("#     Translated:           " + st[2] + "  (" + getPercents(st[2], all) + " %)\n#\n");

        return sb.toString();

    }


    private String getCollationRules()
    {

        StringBuffer sb = new StringBuffer();
        sb.append("#\n#  Collation Rules - In unicode we can create special rules for sorting where we specify\n");
        sb.append("#     sorting order of special characters. This will be used by tree's and special tables.\n");
        sb.append("#     For english this is left empty. For all other (that have non-standard, non english\n");
        sb.append("#     charcters) we need to set this if we want sorting to be done correctly.\n#\n");
        sb.append("COLLATION_RULES=");
        // sb.append(this.user_config.get("COLLATION_RULES") + "\n");
        sb.append(getTranslationEncoded(this.user_config.get("COLLATION_RULES")) + "\n");

        return sb.toString();

    }


    /**
     * Get Translation Encoded
     * 
     * @param str
     * @return
     */
    public String getTranslationEncoded(String str)
    {
        return m_da.unicode_utils.getASCIIFromUnicodeFull(str);
    }


    private String getPercents(int number, int max)
    {
        float f = number * 1.0f / (max * 1.0f);
        f *= 100;

        return Rounding.specialRoundingString(f, "1");
    }


    /**
     * Get Sub Header Full
     * 
     * @return
     */
    public String getSubHeaderFull()
    {
        StringBuffer sb = new StringBuffer();

        String hh = getSubHeader("");

        if (hh != null)
        {
            sb.append(hh);
        }

        hh = getSubHeader(this.module_id + "_");

        if (hh != null)
        {
            sb.append(hh);
        }

        return sb.toString();
    }


    private String getSubHeader(String part)
    {
        boolean end = true;

        int i = 1;
        String header = "";
        while (end)
        {

            if (this.user_config.containsKey("HEADER_COMMENT_" + part + i))
            {
                header += "#  " + this.user_config.get("HEADER_COMMENT_" + part + i) + "\n";
                i++;
            }
            else
            {
                end = false;
            }
        }

        if (header.trim().length() == 0)
            return null;
        else
            return header.trim();

    }


    private String getTextLineCenter(String text, int length)
    {
        int start = text.length() / 2;
        int x = length / 2;

        start = x - start;

        StringBuffer sb = new StringBuffer();
        sb.append(getTextLine("", start));
        sb.append(getTextLine(text, length - start));

        return sb.toString();
    }


    private String getTextLine(String text, int length)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = text.length(); i < length; i++)
        {
            sb.append(" ");
        }

        return text + sb.toString();
    }


    /**
     * Get Target File Root
     * 
     * @return
     */
    public String getTargetFileRoot()
    {
        return this.master_file_root + "_" + this.user_config.get("TRANSLATION_LANGUAGE_SHORT");
    }


    // this.master_file_root + "_" +
    // this.user_config.get("TRANSLATION_LANGUAGE_SHORT")

    private void readTranslationTargetConfig()
    {
        LOG.debug("Read Translation Target Config");

        PropertiesFile pp = new PropertiesFile("../files/translation/" + getTargetFileRoot() + ".config");

        // this.translation_file_read = pp.wasFileRead();
        LOG.debug("   Was file read: " + pp.wasFileRead());

        if (!pp.wasFileRead())
        {
            this.processTranslationData(true);
            return;
        }

        int idx = -1;
        String key_sub = "";

        for (String key : pp.keySet())
        {
            //String key = en.nextElement();

            if (key.contains("__STATUS"))
            {
                idx = key.indexOf("__STATUS");
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);

                if (de != null)
                {
                    // System.out.println("De: "+ de);
                    de.status = Integer.parseInt(pp.get(key));
                }
            }
            else if (key.contains("__INVALIDATED"))
            {
                idx = key.indexOf("__INVALIDATED");
                key_sub = key.substring(0, idx);

                DataEntry de = this.tra_data.get(key_sub);

                if (de != null)
                {
                    long inv = Long.parseLong(pp.get(key));

                    if (de.invalidated != 0 && de.invalidated != inv)
                    {
                        if (de.status != DataEntry.STATUS_UNTRANSLATED)
                        {
                            de.status = DataEntry.STATUS_NEED_CHECK;
                        }
                    }
                }
            }
            else
            {
                LOG.warn("This key type is unknown !!!");
            }
        }

    }


    private void processTranslationData(boolean check_status)
    {
        for (int i = 0; i < this.tra_data.size(); i++)
        {

            DataEntry de = this.tra_data.get(i);

            if (de.status == 0)
            {
                if (de.target_translation.equals(de.master_file_translation))
                {
                    de.status = DataEntry.STATUS_NEED_CHECK;
                }
                else if (!de.target_translation.equals(""))
                {
                    de.status = DataEntry.STATUS_TRANSLATED;
                }
            }

        }

    }


    /**
     * Reset Status
     */
    public void resetStatus()
    {
        this.tra_data.resetStatus();
    }


    /**
     * Get Statuses
     * 
     * @return
     */
    public int[] getStatuses()
    {
        return this.tra_data.getStatuses();
    }


    /**
     * Get Current Entry
     * 
     * @return
     */
    public DataEntry getCurrentEntry()
    {
        if (this.tra_data.size() == 0)
            return null;
        else
            return this.tra_data.get(this.current_index);
    }


    /**
     * Move First
     * 
     * @return
     */
    public boolean moveFirst()
    {
        current_index = 0;
        return !this.tra_data.isEmpty();
    }


    /**
     * Move Next
     * 
     * @return
     */
    public boolean moveNext()
    {
        if (this.tra_data.isEmpty())
            return false;

        if (current_index == this.tra_data.size() - 1)
        {
            this.current_index = 0;
            return true;
        }
        else
        {
            this.current_index++;
            return true;
        }

    }


    /**
     * Move to Next Untranslated
     * 
     * @return
     */
    public boolean moveNextUntranslated()
    {
        if (this.tra_data.isEmpty())
            return false;

        boolean found = false;

        for (int i = this.current_index + 1; i < this.tra_data.size(); i++)
        {
            DataEntry de = this.tra_data.get(i);

            if (de.status == DataEntry.STATUS_NEED_CHECK || de.status == DataEntry.STATUS_UNTRANSLATED)
            {
                this.current_index = i;
                found = true;
                break;
            }
        }

        // no wrap around
        return found;
    }


    /**
     * Move Previous
     * 
     * @return
     */
    public boolean movePrev()
    {
        if (this.tra_data.isEmpty())
            return false;

        if (current_index == 0)
        {
            this.current_index = this.tra_data.size() - 1;
            return true;
        }
        else
        {
            this.current_index--;
            return true;
        }
    }


    /**
     * Get Current Index
     * 
     * @return
     */
    public int getCurrentIndex()
    {
        return this.current_index;
    }


    /**
     * Move to Previous Untranslated
     * 
     * @return
     */
    public boolean movePrevUntranslated()
    {
        if (this.tra_data.isEmpty() || this.current_index == 0)
            return false;

        boolean found = false;

        for (int i = this.current_index - 1; i >= 0; i--)
        {
            DataEntry de = this.tra_data.get(i);

            if (de.status == DataEntry.STATUS_NEED_CHECK || de.status == DataEntry.STATUS_UNTRANSLATED)
            {
                this.current_index = i;
                found = true;
                break;
            }
        }

        // no wrap around
        return found;
    }


    /**
     * Save Translation
     */
    public void saveTranslation()
    {
        this.tra_data.save(this);
    }


    /**
     * Save Translation
     */
    public void saveTranslationBackup()
    {
        this.tra_data.saveBackup(this);
    }


    /**
     * Returns backup time
     * 
     * @return time in minutes for backup action
     */
    public int getBackupTime()
    {
        return this.backup_time;
    }


    public void setIsMasterFileMasterFile(boolean isMasterFileMasterFile)
    {
        this.isMasterFileMasterFile = isMasterFileMasterFile;
    }


    public void setMasterFileMasterFile(boolean isMasterFileMasterFile)
    {
        this.isMasterFileMasterFile = isMasterFileMasterFile;
    }
}
