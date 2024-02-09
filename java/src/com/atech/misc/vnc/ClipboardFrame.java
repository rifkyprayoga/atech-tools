//
//  Copyright (C) 1999 AT&T Laboratories Cambridge.  All Rights Reserved.
//
//  This is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This software is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this software; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
//  USA.
//

//
// Clipboard frame.
//
package com.atech.misc.vnc;

import java.awt.*;

class ClipboardFrame extends Frame
{

    TextArea ta;
    Button clear, dismiss;
    String selection;
    VncViewer v;


    //
    // Constructor.
    //

    ClipboardFrame(VncViewer v1)
    {
        super("VNC Clipboard");

        v = v1;

        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        ta = new TextArea(5, 40);
        gridbag.setConstraints(ta, gbc);
        add(ta);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 1;
        clear = new Button("Clear");
        gridbag.setConstraints(clear, gbc);
        add(clear);

        dismiss = new Button("Dismiss");
        gridbag.setConstraints(dismiss, gbc);
        add(dismiss);

        pack();
    }


    //
    // Set the cut text from the RFB server.
    //

    void setCutText(String text)
    {
        selection = text;
        ta.setText(text);
        if (isVisible())
        {
            ta.selectAll();
        }
    }


    //
    // When the focus leaves the window, see if we have new cut text and if so
    // send it to the RFB server.
    //

    public boolean lostFocus(Event evt, Object arg)
    {
        if ((selection != null) && !selection.equals(ta.getText()))
        {
            selection = ta.getText();
            v.setCutText(selection);
        }
        return true;
    }


    //
    // Respond to an action i.e. button press
    //

    public boolean action(Event evt, Object arg)
    {

        if (evt.target == dismiss)
        {
            hide();
            return true;

        }
        else if (evt.target == clear)
        {
            ta.setText("");
            return true;
        }

        return false;
    }
}
