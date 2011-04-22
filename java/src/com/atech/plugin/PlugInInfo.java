package com.atech.plugin;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

public class PlugInInfo extends JPanel
{

    public String class_name = "";
    public String name = "";
    public boolean is_demo_mode = true;
    public int id;
    ATDataAccessAbstract m_da;
    JLabel lbl_name_status, lbl_licence = null;
    I18nControlAbstract m_ic = null;
    
    
    public PlugInInfo(ATDataAccessAbstract da, int id_, String class_name_, String name_)
    {
        super();
        
        this.m_da = da;
        this.class_name = class_name_;
        this.name = name_;
        this.id = id_;
        
        this.setDataAccess(da);
    }
    

    public PlugInInfo(int id_, String class_name_, String name_)
    {
        this(null, id_, class_name_, name_);
    }
    
    
    
    public void init()
    {
        ATSwingUtils.initLibrary();
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.black));
        
        lbl_name_status = ATSwingUtils.getLabel("" , 10, 5, 250, 25, this, ATSwingUtils.FONT_NORMAL);
        this.setStatus("PLUGIN_NA");

        lbl_licence = ATSwingUtils.getLabel("" , 10, 25, 250, 25, this, ATSwingUtils.FONT_NORMAL);
        this.setLicence("LICENCE_DEMO");
        
        this.setBounds(0, 0, 200, 50);
        //this.setSize(200, 50);
    }
    
    public void setDataAccess(ATDataAccessAbstract da)
    {
        if (da==null)
            return;
        
        this.m_da = da;
        this.m_ic = da.getI18nControlInstance();
        init();
    }
    
    public void setStatus(String status)
    {
        lbl_name_status.setText(this.m_ic.getMessage(name) + "   (" + this.m_ic.getMessage(status) + ")");
    }
    
    
    public void setLicence(String licence)
    {
        this.lbl_licence.setText(this.m_ic.getMessage("LICENCE") + ":   " + this.m_ic.getMessage(licence));
    }
    
    
    public Dimension getPreferredSize() 
    {
        return new Dimension(200, 50);
      }    
    
}
