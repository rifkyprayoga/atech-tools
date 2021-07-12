package com.atech.gui_fw.config;

import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.hdb_object.Settings;
import com.atech.utils.ATDataAccessAbstract;

/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     ConfigurationManager
 *  Description:  Configuration Manager is class for managing configuration settings 
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */

public abstract class AbstractConfigurationManager
{

    private static final Logger LOG = LoggerFactory.getLogger(AbstractConfigurationManager.class);

    private boolean changed = false;

    protected String configurationEntriesString[];

    protected String configurationEntriesInteger[];

    protected String configurationEntriesFloat[];

    protected String configurationEntriesBoolean[];

    protected Hashtable<String, Settings> cfg_values = new Hashtable<String, Settings>();
    protected ATDataAccessAbstract m_da = null;


    /**
     * Constructor
     * 
     * @param da
     */
    public AbstractConfigurationManager(ATDataAccessAbstract da)
    {
        this.m_da = da;
        initData();
    }


    public abstract void initData();


    /**
     * Check Configuration
     * 
     * @param values
     * @param db 
     */
    public void checkConfiguration(Hashtable<String, Settings> values, HibernateDb db)
    {

        this.cfg_values = values;

        System.out.println(configurationEntriesString + " " + configurationEntriesInteger + " "
                + configurationEntriesFloat + " " + configurationEntriesBoolean);

        for (int j = 1; j < 5; j++)
        {
            String[] arr = null;

            if (j == 1)
            {
                arr = configurationEntriesString;
            }
            else if (j == 2)
            {
                arr = configurationEntriesInteger;
            }
            else if (j == 3)
            {
                arr = configurationEntriesFloat;
            }
            else if (j == 4)
            {
                arr = configurationEntriesBoolean;
            }

            for (int i = 0; i < arr.length; i += 2)
            {
                if (!values.containsKey(arr[i]))
                {
                    addNewValue(arr[i], arr[i + 1], j, db);
                }
            }

        }

    }


    public void addNewValue(String name, String def_value, int type, HibernateDb db)
    {
        addNewValue(name, def_value, type, db, true);
    }


    /**
    * Add New configuration value
    *
    * @param name name of configuration parameter
    * @param defaultValue value (presumably def. value if added from this
    class)
    * @param parameterType type of parameter (1=string, 2=int, 3=float,
    4=boolean)
    * @param db db instance
    * @param addToConfigurationValues
    */
    public void addNewValue(String name, String defaultValue, int parameterType, HibernateDb db,
            boolean addToConfigurationValues)
    {
        Settings s = new Settings();

        s.setKey(name);
        s.setDescription(m_da.getI18nControlInstance().getMessage("CFG_" + name));
        s.setType(parameterType);
        s.setValue(defaultValue);
        s.setPersonId((int) m_da.getCurrentUserId());

        db.add(s);

        if (addToConfigurationValues)
        {
            this.cfg_values.put(name, s);
            this.changed = true;
        }
    }


    /**
     * Get Boolean Value
     * 
     * @param key key of variable
     * @return value as boolean
     */
    public boolean getBooleanValue(String key)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);

            try
            {
                return Boolean.parseBoolean(s.getValue());
            }
            catch (Exception ex)
            {
                LOG.warn("Invalid value for key=" + key + " found. It should be boolean.");
            }

        }

        return false;

    }


    /**
     * Set Boolean Value
     * 
     * @param key key of variable
     * @param value as boolean
     */
    public void setBooleanValue(String key, boolean value)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);

            boolean prev_val = false;
            try
            {
                prev_val = Boolean.parseBoolean(s.getValue());
            }
            catch (Exception ex)
            {
                LOG.warn("Invalid value for key=" + key + " found. It should be boolean.");
            }

            // System.out.println("setIntValue: " + value);

            if (prev_val != value)
            {
                s.setValue("" + value);
                s.setElementEdited();
                this.changed = true;
            }

        }

    }


    /**
     * Get Int Value
     * 
     * @param key key of variable
     * @return value as int
     */
    public int getIntValue(String key)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);

            try
            {
                return Integer.parseInt(s.getValue());
            }
            catch (Exception ex)
            {
                LOG.warn("Invalid value for key=" + key + " found. It should be integer.");
            }

        }

        return -1;

    }


    /**
     * Set Int Value
     * 
     * @param key key of variable
     * @param value as int
     */
    public void setIntValue(String key, int value)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);

            int prev_val = 0;
            try
            {
                prev_val = Integer.parseInt(s.getValue());
            }
            catch (Exception ex)
            {
                LOG.warn("Invalid value for key=" + key + " found. It should be integer.");
            }

            // System.out.println("setIntValue: " + value);

            if (prev_val != value)
            {
                s.setValue("" + value);
                s.setElementEdited();
                this.changed = true;
                // System.out.println("setIntValue: Success");
            }

        }

    }


    /**
     * Get Float Value
     * 
     * @param key key of variable
     * @return value as float
     */
    public float getFloatValue(String key)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);

            return m_da.getFloatValueFromString(s.getValue(), 0.0f);
        }

        return 0.0f;

    }


    /**
     * Set Float Value
     * 
     * @param key key of variable
     * @param value as float
     */
    public void setFloatValue(String key, float value)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);

            float prev_val = 0;
            try
            {
                prev_val = Float.parseFloat(s.getValue());
            }
            catch (Exception ex)
            {
                LOG.warn("Invalid value for key=" + key + " found. It should be float.");
            }

            if (prev_val != value)
            {
                s.setValue("" + value + "f");
                s.setElementEdited();
                this.changed = true;
            }
        }
    }


    /**
     * Get String Value
     * 
     * @param key key of variable
     * @return value as string
     */
    public String getStringValue(String key)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);
            return s.getValue();
        }
        else
            return "";

    }


    /**
     * Set String Value
     * 
     * @param key key of variable
     * @param value as string
     */
    public void setStringValue(String key, String value)
    {
        if (checkIfExists(key))
        {
            Settings s = this.cfg_values.get(key);

            if (!s.getValue().equals(value))
            {
                s.setValue("" + value);
                s.setElementEdited();
                this.changed = true;
            }
        }
    }


    private boolean checkIfExists(String key)
    {
        if (this.cfg_values.containsKey(key))
            return true;
        else
        {
            LOG.warn("Configuration key " + key + " doesn't exist.");
            return false;
        }
    }


    /**
     * Save Config
     */
    public void saveConfig()
    {

        // System.out.println("Save Config - Start [changed=" + changed +"]");

        if (!changed)
            return;

        HibernateDb db = this.m_da.getHibernateDb();

        for (Enumeration<String> en = this.cfg_values.keys(); en.hasMoreElements();)
        {
            String key = en.nextElement();

            Settings s = this.cfg_values.get(key);

            if (s.isElementAdded())
            {
                db.add(s);
            }
            else if (s.isElementEdited())
            {
                db.edit(s);
            }
        }

    }

}
