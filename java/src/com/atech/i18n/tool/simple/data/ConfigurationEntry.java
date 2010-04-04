package com.atech.i18n.tool.simple.data;

public class ConfigurationEntry 
{
	public String config_file = null;
	public String description = null;
	public String file_name = null;
	public String def_lang = null;

	
	
	public ConfigurationEntry(String file, String description_, String file_name_, String def_lang_)
	{
		this.config_file= file;
		this.description = description_;
		this.file_name = file_name_;
		this.def_lang = def_lang_;
	}
	
	
	public String getMasterFile()
	{
		return file_name + "_" + def_lang + ".properties";
	}
	
	public String toString()
	{
		return this.description;
	}

}
