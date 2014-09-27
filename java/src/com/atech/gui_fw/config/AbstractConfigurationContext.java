package com.atech.gui_fw.config;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.atech.app.AbstractApplicationContext;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;

public abstract class AbstractConfigurationContext
{

    protected ConfigurationDialog config_dialog;
    protected ImageIcon config_icons[] = null;
    protected String config_options[] = null;

    protected ArrayList<JPanel> panels;
    protected Hashtable<String, String> panels_id;
    protected AbstractApplicationContext app_ctx = null;
    protected I18nControlAbstract ic = null;

    public abstract ATDataAccessAbstract getDataAccessInstance();

    public AbstractConfigurationContext(AbstractApplicationContext app_ctx_)
    {
        this.app_ctx = app_ctx_;

        this.ic = app_ctx.getI18nControl();
        initConfigContext();
    }

    public abstract void initConfigContext();

    public void setConfigurationDialog(ConfigurationDialog cfg_dialog)
    {
        this.config_dialog = cfg_dialog;
    }

    public ConfigurationDialog getConfigurationDialog()
    {
        return this.config_dialog;
    }

    public String[] getConfigOptions()
    {
        return this.config_options;
    }

    public ImageIcon[] getConfigIcons()
    {
        return this.config_icons;
    }

    public abstract void createPanels(ArrayList<JPanel> panels, Hashtable<String, String> panels_id);

    protected void addPanel(String name, int id, JPanel panel)
    {
        panels.add(panel);
        panels_id.put(name, "" + id);
    }

    public abstract void saveSettings();

    public abstract void resetSettings();

}
