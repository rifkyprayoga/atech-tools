package com.atech.i18n.tool.simple.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Application: Simple Translation Tool
 *  TranslationData - Collection of translation entries
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

public class TranslationData
{

    private static final Logger LOG = LoggerFactory.getLogger(TranslationData.class);

    /**
     * The list_tra.
     */
    public ArrayList<DataEntry> list_tra = null;

    /**
     * The dt_tra.
     */
    public Hashtable<String, DataEntry> dt_tra = null;

    /**
     * The statuses_info.
     */
    public int statuses_info[] = { 0, 0, 0 };

    DataAccessTT m_da = DataAccessTT.getInstance();

    /**
     * Instantiates a new translation data.
     */
    public TranslationData()
    {
        list_tra = new ArrayList<DataEntry>();
        dt_tra = new Hashtable<String, DataEntry>();

    }

    /**
     * Adds the translation data.
     * 
     * @param de the de
     */
    public void addTranslationData(DataEntry de)
    {
        this.list_tra.add(de);
        this.dt_tra.put(de.key, de);
    }

    /**
     * Checks if is empty.
     * 
     * @return true, if is empty
     */
    public boolean isEmpty()
    {
        return this.list_tra.size() == 0;
    }

    /**
     * Gets the.
     * 
     * @param key the key
     * 
     * @return the data entry
     */
    public DataEntry get(String key)
    {
        return this.dt_tra.get(key);
    }

    /**
     * Elements.
     * 
     * @return the enumeration< data entry>
     */
    public Enumeration<DataEntry> elements()
    {
        return this.dt_tra.elements();

    }

    /**
     * Contains key.
     * 
     * @param key the key
     * 
     * @return true, if successful
     */
    public boolean containsKey(String key)
    {
        return this.dt_tra.containsKey(key);
    }

    /**
     * Gets the.
     * 
     * @param index the index
     * 
     * @return the data entry
     */
    public DataEntry get(int index)
    {
        return this.list_tra.get(index);
    }

    /**
     * Size.
     * 
     * @return the int
     */
    public int size()
    {
        return this.list_tra.size();
    }

    /**
     * Save.
     */
    /*
     * public void save()
     * {
     * saveTranslation();
     * saveSettings();
     * }
     */

    /**
     * Save
     * 
     * @param dlp 
     */
    public void save(DataListProcessor dlp)
    {
        saveTranslation(dlp, false, null);
        saveSettings(dlp, false, null);
    }

    /**
     * Save Backup
     * 
     * @param dlp 
     */
    public void saveBackup(DataListProcessor dlp)
    {
        GregorianCalendar gc = new GregorianCalendar();
        saveTranslation(dlp, true, gc);
        saveSettings(dlp, true, gc);
    }

    /**
     * Save translation.
     */
    public void saveTranslation_Oldxxx()
    {
        try
        {
            // BufferedWriter bw = new BufferedWriter(new
            // FileWriter("./files/translation/GGC_si.properties"));

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
                    "../files/translation/GGC_si.properties")), "ASCII"));
            // String unicode = "Unicode: \u30e6\u30eb\u30b3\u30fc\u30c9";
            // byte[] bytes = String.getBytes("US_ASCII");

            for (int i = 0; i < this.list_tra.size(); i++)
            {
                DataEntry de = this.list_tra.get(i);

                bw.write(de.key + "=");

                // TODO: if no translation - English
                bw.write(getTranslationEncoded(de.target_translation));

                /*
                 * byte bb[] = de.target_translation.getBytes("ASCII");
                 * for(int j=0; j<bb.length; j++)
                 * {
                 * bw.write(bb[j]);
                 * }
                 */
                bw.newLine();
                bw.flush();
                // bw.write(de.target_translation.getBytes("US_ASCII"));

                // + de.target_translation + "\n");

            }

            bw.close();

        }
        catch (Exception ex)
        {
            LOG.error("TranslationData.saveTranslation(). Exception: " + ex, ex);
        }
    }

    private boolean debug_write = false;

    /**
     * Save Translation
     * 
     * @param dlp
     * @param backup
     * @param gc 
     */
    public void saveTranslation(DataListProcessor dlp, boolean backup, GregorianCalendar gc)
    {

        String add_path = "";
        String filename = dlp.getTargetFileRoot();

        if (backup)
        {
            add_path = "backup/";
            filename += "_" + getDateTimeFilename(gc);
            LOG.debug("Save Translation (backup: " + m_da.getCurrentDateTimeString() + ")");
        }
        else
        {
            LOG.debug("Save Translation");
        }

        try
        {
            // BufferedWriter bw = new BufferedWriter(new
            // FileWriter("./files/translation/GGC_si.properties"));

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
                    "../files/translation/" + add_path + filename + ".properties")), "ASCII"));
            // String unicode = "Unicode: \u30e6\u30eb\u30b3\u30fc\u30c9";
            // byte[] bytes = String.getBytes("US_ASCII");

            MasterFileReader mfr = dlp.mfr;

            // dlp.getHeader();

            for (int i = 0; i < mfr.size(); i++)
            {
                DataEntryRaw der = mfr.get(i);

                String v = der.getValue();

                if (v == null)
                {
                    if (der.type == DataEntryRaw.DATA_ENTRY_HEADER)
                    {
                        bw.write(dlp.getFullHeader());
                        // bw.write("#\n#\n#  Header is still missing !!!\n#\n#\n#\n");
                    }
                    else if (der.type == DataEntryRaw.DATA_ENTRY_TRANSLATION)
                    {
                        String key = der.value_str;

                        // if (key.equals("TIME"))
                        // this.debug_write = true;

                        DataEntry de = this.get(key);

                        if (debug_write)
                        {
                            System.out.println("De: " + de);
                        }

                        bw.write(de.key + "=");

                        if (de.target_translation == null || de.target_translation.trim().length() == 0)
                        {
                            if (debug_write)
                            {
                                System.out.println("Trage Translation is null: " + de);
                            }
                            bw.write(getTranslationEncoded(de.master_file_translation));
                        }
                        else
                        {
                            if (debug_write)
                            {
                                System.out.println("Transtaltion: " + de);
                                System.out.println(de.target_translation);
                            }
                            bw.write(getTranslationEncoded(de.target_translation));
                        }

                        bw.newLine();
                        bw.flush();
                    }

                }
                else
                {
                    bw.write(v);
                }

                this.debug_write = false;

            }

            bw.close();

        }
        catch (Exception ex)
        {
            LOG.error("TranslationData.saveTranslation(). Exception: " + ex, ex);
        }
    }

    private String getDateTimeFilename(GregorianCalendar gc)
    {
        return "" + gc.get(Calendar.YEAR) + "_" + ATDataAccessAbstract.getLeadingZero(gc.get(Calendar.MONTH), 2) + "_"
                + ATDataAccessAbstract.getLeadingZero(gc.get(Calendar.DAY_OF_MONTH), 2) + "_"
                + ATDataAccessAbstract.getLeadingZero(gc.get(Calendar.HOUR_OF_DAY), 2) + "_"
                + ATDataAccessAbstract.getLeadingZero(gc.get(Calendar.MINUTE), 2);

    }

    /**
     * Save settings
     * 
     * @param dlp 
     * @param backup 
     * @param gc 
     */
    public void saveSettings(DataListProcessor dlp, boolean backup, GregorianCalendar gc)
    {

        String add_path = "";
        String filename = dlp.getTargetFileRoot();

        if (backup)
        {
            add_path = "backup/";
            filename += "_" + getDateTimeFilename(gc);
            LOG.debug("Save Settings (backup: " + m_da.getCurrentDateTimeString() + ")");
        }
        else
        {
            LOG.debug("Save Settings");
        }

        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
                    "../files/translation/" + add_path + filename + ".config")), "ASCII"));

            for (int i = 0; i < this.list_tra.size(); i++)
            {
                DataEntry de = this.list_tra.get(i);

                if (de.invalidated > 0)
                {
                    bw.write(de.key + "__INVALIDATED=" + de.invalidated + "\n");
                }

                bw.write(de.key + "__STATUS=" + de.status);
                bw.newLine();
                bw.flush();
            }

            bw.close();

        }
        catch (Exception ex)
        {
            LOG.error("TranslationData.saveTranslation(). Exception: " + ex, ex);
        }
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

    /**
     * Reset status.
     */
    public void resetStatus()
    {
        this.statuses_info[0] = 0;
        this.statuses_info[1] = 0;
        this.statuses_info[2] = 0;

        for (int i = 0; i < this.list_tra.size(); i++)
        {
            DataEntry de = this.list_tra.get(i);
            this.statuses_info[de.status] = this.statuses_info[de.status] + 1;
        }
    }

    /**
     * Gets the statuses.
     * 
     * @return the statuses
     */
    public int[] getStatuses()
    {
        return this.statuses_info;
    }

}
