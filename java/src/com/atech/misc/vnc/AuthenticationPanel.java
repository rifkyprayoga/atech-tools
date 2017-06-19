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
package com.atech.misc.vnc;

import java.awt.*;

//
// The panel which implements the user authentication scheme
//

class AuthenticationPanel extends Panel
{

    Label title, retry, prompt;
    TextField password;
    Button ok;


    //
    // Constructor.
    //

    public AuthenticationPanel()
    {

        title = new Label("VNC Authentication", Label.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 18));

        prompt = new Label("Password:", Label.CENTER);

        password = new TextField(10);
        password.setForeground(Color.black);
        password.setBackground(Color.white);
        password.setEchoCharacter('*');

        ok = new Button("OK");

        retry = new Label("", Label.CENTER);
        retry.setFont(new Font("Courier", Font.BOLD, 16));

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        setLayout(gridbag);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(title, gbc);
        add(title);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(retry, gbc);
        add(retry);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gridbag.setConstraints(prompt, gbc);
        add(prompt);

        gridbag.setConstraints(password, gbc);
        add(password);

        gbc.ipady = 10;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.ipadx = 40;
        gridbag.setConstraints(ok, gbc);
        add(ok);

        password.requestFocus();
    }


    //
    // action() is called when a button is pressed or return is pressed in the
    // password text field.
    //

    public synchronized boolean action(Event evt, Object arg)
    {
        if ((evt.target == password) || (evt.target == ok))
        {
            notify();
            return true;
        }
        return false;
    }


    //
    // retry().
    //

    public void retry()
    {
        retry.setText("Sorry. Try again.");
        password.setText("");
    }

}
