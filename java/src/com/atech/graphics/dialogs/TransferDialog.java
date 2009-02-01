package com.atech.graphics.dialogs;


import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFrame;

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

public abstract class TransferDialog extends JDialog
{

    private static final long serialVersionUID = 6150106762052989231L;
    Component parent = null;

    
    /**
     * Constructor
     * 
     * @param parent
     */
    public TransferDialog(JFrame parent)
    {
        super(parent, "", true);
        this.parent = parent;
    }

    
    /**
     * Constructor
     * 
     * @param parent
     */
    public TransferDialog(JDialog parent)
    {
        super(parent, "", true);
        this.parent = parent;
    }
    
    
    /**
     * Get Empty Input Parameters
     * 
     * @return
     */
    public Object[] getEmptyInputParameters()
    {
        return new Object[this.getInputParametersCount()];
        
    }
    
    
    /**
     * Get Input Parameters Count
     * 
     * @return
     */
    public abstract int getInputParametersCount();
    
    
    /**
     * Set Input Paramters
     * 
     * @param ip
     */
    public abstract void setInputParameters(Object[] ip);
    
    
    /**
     * Show Dialog
     * 
     * @param visible
     */
    public abstract void showDialog(boolean visible);
    
    
    /**
     * Was Action
     * 
     * @return
     */
    public abstract boolean wasAction();
    
    
    /**
     * Get Result Values
     * 
     * @return
     */
    public abstract Object[] getResultValues();

    
    /**
     * Get Result Values String
     * 
     * @return
     */
    public abstract String[] getResultValuesString();

    
}
