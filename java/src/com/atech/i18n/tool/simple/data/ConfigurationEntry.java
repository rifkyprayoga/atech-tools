package com.atech.i18n.tool.simple.data;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigurationEntry.
 */
public class ConfigurationEntry 
{
	
	/** The config_file. */
	public String config_file = null;
	
	/** The description. */
	public String description = null;
	
	/** The file_name. */
	public String file_name = null;
	
	/** The def_lang. */
	public String def_lang = null;

	
	
	/**
     * Instantiates a new configuration entry.
     * 
     * @param file the file
     * @param description_  the description
     * @param file_name_ the file_name
     * @param def_lang_ the def_lang
     */
	public ConfigurationEntry(String file, String description_, String file_name_, String def_lang_)
	{
		this.config_file= file;
		this.description = description_;
		this.file_name = file_name_;
		this.def_lang = def_lang_;
	}
	
	
	/**
     * Gets the master file.
     * 
     * @return the master file
     */
	public String getMasterFile()
	{
		return file_name + "_" + def_lang + ".properties";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.description;
	}

}
