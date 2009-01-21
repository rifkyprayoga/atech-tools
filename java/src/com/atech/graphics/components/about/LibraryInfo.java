package com.atech.graphics.components.about;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.atech.i18n.I18nControlAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
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
 *  @author Andy
 *
*/


public class LibraryInfo extends AboutPanel
{

    private static final long serialVersionUID = -3384856095101992059L;
    ArrayList<LibraryInfoEntry> libraries;

    public LibraryInfo(I18nControlAbstract ic, ArrayList<LibraryInfoEntry> lst)
    {
        super(ic);
        this.libraries = lst;
        init();
    }


    public void init()
    {
        this.setLayout(new BorderLayout());

        JEditorPane jEditorPane1 = new JEditorPane();


        JScrollPane jScrollPane1 = new JScrollPane(jEditorPane1);
        //jScrollPane1.setPreferredSize(new java.awt.Dimension(13, 1200));

        jEditorPane1.setEditable(false);
        jEditorPane1.setContentType("text/html");
        jScrollPane1.setViewportView(jEditorPane1);
        jEditorPane1.setText(generatePage());

        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jEditorPane1.select(0,0);

    }


    public String generatePage()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("<html><body>");

        for(int i=0; i<this.libraries.size(); i++)
        {
            sb.append(this.libraries.get(i).getHTMLCode());
        }

        sb.append("</body></html>");

        return sb.toString();
    }



    public String getTabName()
    {
        return this.ic.getMessage("LIBRARIES");
    }

    public JPanel getTabPanel()
    {
        return this;
    }


    
}
