package com.atech.db.hibernate.tool.gui.tree;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.atech.db.hibernate.tool.data.DatabaseConfiguration;
import com.atech.db.hibernate.tool.util.DbToolAccess;
import com.atech.utils.ATSwingUtils;

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

public class DbToolTreeCellRenderer extends DefaultTreeCellRenderer
{

    private static final long serialVersionUID = 3294655960419222634L;

    /** Last tree the renderer was painted in. */
    // private JTree tree;

    private Font defFont = null;
    private Font boldFont = null;


    /**
     * Constructor
     */
    public DbToolTreeCellRenderer()
    {
        super();

        DbToolAccess da = DbToolAccess.getInstance();
        ATSwingUtils.initLibrary();

        defFont = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL);
        boldFont = ATSwingUtils.getFont(ATSwingUtils.FONT_NORMAL_BOLD);
        /*
         * defFont = super.getFont();
         * defFont =
         * System.out.println(defFont);
         * boldFont = defFont.deriveFont(Font.BOLD);
         */
    }


    /**
      * Configures the renderer based on the passed in components.
      * The value is set from messaging the tree with
      * <code>convertValueToText</code>, which ultimately invokes
      * <code>toString</code> on <code>value</code>.
      * The foreground color is set based on the selection and the icon
      * is set based on on leaf and expanded.
      */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
            int row, boolean _hasFocus)
    {
        String stringValue = tree.convertValueToText(value, sel, expanded, leaf, row, _hasFocus);
        setFont(this.defFont);

        // System.out.println(value);
        if (value instanceof DatabaseConfiguration)
        {
            DatabaseConfiguration ds = (DatabaseConfiguration) value;

            if (ds.isDefault)
            {
                setFont(this.boldFont);
            }
        }

        // this.tree = tree;
        this.hasFocus = _hasFocus;
        setText(stringValue);
        if (sel)
        {
            setForeground(getTextSelectionColor());
        }
        else
        {
            setForeground(getTextNonSelectionColor());
        }
        // There needs to be a way to specify disabled icons.
        if (!tree.isEnabled())
        {
            setEnabled(false);
            if (leaf)
            {
                setDisabledIcon(getLeafIcon());
            }
            else if (expanded)
            {
                setDisabledIcon(getOpenIcon());
            }
            else
            {
                setDisabledIcon(getClosedIcon());
            }
        }
        else
        {
            setEnabled(true);
            if (leaf)
            {
                setIcon(getLeafIcon());
            }
            else if (expanded)
            {
                setIcon(super.getOpenIcon());
            }
            else
            {
                setIcon(super.getClosedIcon());
            }
        }
        setComponentOrientation(tree.getComponentOrientation());

        selected = sel;

        return this;
    }

}
