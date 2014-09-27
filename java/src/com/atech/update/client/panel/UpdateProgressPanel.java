package com.atech.update.client.panel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

/**
 *  This file is part of ATech Tools library.
 *  
 *  UpdateDialog - Dialog for updates
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
 *  @author Andy {andy@atech-software.com}
 *
*/

public class UpdateProgressPanel extends UpdateProgressPanelAbstract
{

    private static final long serialVersionUID = 2882384592751807875L;
    JProgressBar progress_bar;
    JLabel label_status_1, label_status_2;
    String job_status = null;
    String base_status = null;
    String sub_status = null;

    /**
     * Constructor
     */
    public UpdateProgressPanel()
    {
        super();
        this.job_status = "Waiting";
        initGUI();
    }

    private void initGUI()
    {
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.BLACK));

        label_status_1 = new JLabel("");
        label_status_1.setBounds(22, 10, 355, 15);
        add(label_status_1);

        progress_bar = new JProgressBar();
        progress_bar.setStringPainted(true);
        progress_bar.setBounds(22, 35, 425, 25);
        add(progress_bar);

        label_status_2 = new JLabel("");
        label_status_2.setBounds(22, 65, 355, 15);
        add(label_status_2);

        this.setSize(490, 90);

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
