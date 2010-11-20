package com.atech.update.client.panel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

public class UpdateProgressPanel extends UpdateProgressPanelAbstract
{

    JProgressBar progress_bar;
    String base_status = null;
    String sub_status = null;
    JLabel label_status_1, label_status_2;
    String job_status = null;
    
    public UpdateProgressPanel()
    {
        super();
        this.job_status = "Waiting";
        initGUI();
    }
    
    
    public void initGUI()
    {
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.BLACK));
        
        label_status_1 = new JLabel("");
        label_status_1.setBounds(22, 15, 355, 15);
        add(label_status_1);
        
        progress_bar = new JProgressBar();
        progress_bar.setBounds(22, 40, 355, 25);
        add(progress_bar);

        label_status_2 = new JLabel("");
        label_status_2.setBounds(22, 70, 355, 15);
        add(label_status_2);
        
        
    }


    
    
    public void setStatus(String status)
    {
        this.sub_status = status;
        this.label_status_2.setText(this.sub_status);
    }


    public void setProgress(int proc)
    {
        this.progress_bar.setValue(proc);
    }


    public void setBaseStatus(String status)
    {
        this.base_status = status;
        this.label_status_1.setText(this.base_status + " - " + this.job_status);
    }
    

    public void setJobStatus(String status)
    {
        this.job_status = status;
        this.label_status_1.setText(this.base_status + " - " + this.job_status);
    }
    
    
}
