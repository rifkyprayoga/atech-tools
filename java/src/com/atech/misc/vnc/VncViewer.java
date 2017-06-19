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
// VncViewer.java - the VNC viewer applet.  This class mainly just sets up the
// user interface, leaving it to the VncCanvas to do the actual rendering of
// a VNC desktop.
//
package com.atech.misc.vnc;

import java.awt.*;
import java.io.IOException;

public class VncViewer extends java.applet.Applet implements java.lang.Runnable
{

    boolean inAnApplet = true;


    //
    // main() is called when run as a java program from the command line. It
    // simply creates a frame and runs the applet inside it.
    //

    public static void main(String[] argv)
    {
        VncViewer v = new VncViewer();
        v.mainArgs = argv;
        v.inAnApplet = false;

        v.f = new Frame("VNC");
        v.f.add("Center", v);

        v.init();
        v.start();
    }

    Frame f;
    String[] mainArgs;
    String host;
    int port;
    RfbProto rfb;
    Thread rfbThread;
    GridBagLayout gridbag;
    Panel buttonPanel;
    Button disconnectButton;
    Button optionsButton;
    Button clipboardButton;
    Button ctrlAltDelButton;
    OptionsFrame options;
    ClipboardFrame clipboard;
    AuthenticationPanel authenticator;


    //
    // init()
    //

    public void init()
    {

        readParameters();

        options = new OptionsFrame(this);
        clipboard = new ClipboardFrame(this);
        authenticator = new AuthenticationPanel();

        rfbThread = new Thread(this);
        rfbThread.start();
    }


    public void update(Graphics g)
    {
    }


    //
    // run() - executed by the rfbThread to deal with the RFB socket.
    //

    public void run()
    {

        gridbag = new GridBagLayout();
        setLayout(gridbag);

        buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        disconnectButton = new Button("Disconnect");
        disconnectButton.disable();
        buttonPanel.add(disconnectButton);
        optionsButton = new Button("Options");
        buttonPanel.add(optionsButton);
        clipboardButton = new Button("Clipboard");
        clipboardButton.disable();
        buttonPanel.add(clipboardButton);
        ctrlAltDelButton = new Button("Send Ctrl-Alt-Del");
        ctrlAltDelButton.disable();
        buttonPanel.add(ctrlAltDelButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(buttonPanel, gbc);
        add(buttonPanel);

        try
        {
            connectAndAuthenticate();

            doProtocolInitialisation();

            VncCanvas vc = new VncCanvas(this);
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gridbag.setConstraints(vc, gbc);
            add(vc);

            if (!inAnApplet)
            {
                f.setTitle(rfb.desktopName);
                f.pack();
            }
            else
            {
                validate();
            }

            disconnectButton.enable();
            clipboardButton.enable();
            ctrlAltDelButton.enable();

            vc.processNormalProtocol();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            fatalError(e.toString());
        }

    }


    //
    // Connect to the RFB server and authenticate the user.
    //

    void connectAndAuthenticate() throws IOException
    {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 100;
        gbc.ipady = 50;
        gridbag.setConstraints(authenticator, gbc);
        add(authenticator);
        validate();
        if (!inAnApplet)
        {
            f.pack();
            f.show();
        }

        boolean authenticationDone = false;

        while (!authenticationDone)
        {

            synchronized (authenticator)
            {
                try
                {
                    authenticator.wait();
                }
                catch (InterruptedException e)
                {}
            }

            rfb = new RfbProto(host, port, this);

            rfb.readVersionMsg();

            System.out.println("RFB server supports protocol version " + rfb.serverMajor + "." + rfb.serverMinor);

            rfb.writeVersionMsg();

            switch (rfb.readAuthScheme())
            {

                case RfbProto.NoAuth:
                    System.out.println("No authentication needed");
                    authenticationDone = true;
                    break;

                case RfbProto.VncAuth:
                    byte[] challenge = new byte[16];
                    rfb.is.readFully(challenge);

                    String pw = authenticator.password.getText();
                    if (pw.length() > 8)
                        pw = pw.substring(0, 8); // truncate to 8 chars

                    if (pw.length() == 0)
                    {
                        authenticator.retry();
                        break;
                    }

                    byte[] key = new byte[8];
                    pw.getBytes(0, pw.length(), key, 0);

                    for (int i = pw.length(); i < 8; i++)
                    {
                        key[i] = (byte) 0;
                    }

                    DesCipher des = new DesCipher(key);

                    des.encrypt(challenge, 0, challenge, 0);
                    des.encrypt(challenge, 8, challenge, 8);

                    rfb.os.write(challenge);

                    int authResult = rfb.is.readInt();

                    switch (authResult)
                    {
                        case RfbProto.VncAuthOK:
                            System.out.println("VNC authentication succeeded");
                            authenticationDone = true;
                            break;
                        case RfbProto.VncAuthFailed:
                            System.out.println("VNC authentication failed");
                            authenticator.retry();
                            break;
                        case RfbProto.VncAuthTooMany:
                            throw new IOException("VNC authentication failed - " + "too many tries");
                        default:
                            throw new IOException("Unknown VNC authentication result " + authResult);
                    }
                    break;
            }
        }

        remove(authenticator);
    }


    //
    // Do the rest of the protocol initialisation.
    //

    void doProtocolInitialisation() throws IOException
    {
        System.out.println("sending client init");

        rfb.writeClientInit();

        rfb.readServerInit();

        System.out.println("Desktop name is " + rfb.desktopName);
        System.out.println("Desktop size is " + rfb.framebufferWidth + " x " + rfb.framebufferHeight);

        setEncodings();
    }


    //
    // setEncodings() - send the current encodings from the options frame
    // to the RFB server.
    //

    void setEncodings()
    {
        try
        {
            if ((rfb != null) && rfb.inNormalProtocol)
            {
                rfb.writeSetEncodings(options.encodings, options.nEncodings);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //
    // setCutText() - send the given cut text to the RFB server.
    //

    void setCutText(String text)
    {
        try
        {
            if ((rfb != null) && rfb.inNormalProtocol)
            {
                rfb.writeClientCutText(text);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //
    // Respond to an action i.e. button press
    //

    public synchronized boolean action(Event evt, Object what)
    {

        if (evt.target == optionsButton)
        {

            if (options.isVisible())
            {
                options.hide();
            }
            else
            {
                options.show();
            }

        }
        else if (evt.target == disconnectButton)
        {

            System.out.println("disconnect");
            options.dispose();
            clipboard.dispose();

            if (inAnApplet)
            {
                removeAll();
                rfb.close();
                rfb = null;
                Label l = new Label("Disconnected");
                setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
                add(l);
                validate();
                rfbThread.stop();
            }
            else
            {
                System.exit(1);
            }

        }
        else if (evt.target == clipboardButton)
        {

            if (clipboard.isVisible())
            {
                clipboard.hide();
            }
            else
            {
                clipboard.show();
            }

        }
        else if (evt.target == ctrlAltDelButton)
        {

            try
            {
                Event ctrlAltDelEvent = new Event(null, 0, null);

                ctrlAltDelEvent.key = 127;
                ctrlAltDelEvent.modifiers = Event.CTRL_MASK | Event.ALT_MASK;

                ctrlAltDelEvent.id = Event.KEY_PRESS;
                rfb.writeKeyEvent(ctrlAltDelEvent);

                ctrlAltDelEvent.id = Event.KEY_RELEASE;
                rfb.writeKeyEvent(ctrlAltDelEvent);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //
    // Detect when the focus goes in and out of the applet. See
    // VncCanvas.handleEvent() for details of why this is necessary.
    //

    boolean gotFocus = false;


    public boolean gotFocus(Event evt, Object what)
    {
        gotFocus = true;
        return true;
    }


    public boolean lostFocus(Event evt, Object what)
    {
        gotFocus = false;
        return true;
    }


    //
    // encryptBytes() - encrypt some bytes in memory using a password. Note that
    // the mapping from password to key must be the same as that used on the rfb
    // server side.
    //
    // Note also that IDEA encrypts data in 8-byte blocks, so here we will
    // ignore
    // any data beyond the last 8-byte boundary leaving it to the calling
    // function to pad the data appropriately.
    //

    void encryptBytes(byte[] bytes, String passwd)
    {
        byte[] key = new byte[8];
        passwd.getBytes(0, passwd.length(), key, 0);

        for (int i = passwd.length(); i < 8; i++)
        {
            key[i] = (byte) 0;
        }

        DesCipher des = new DesCipher(key);

        des.encrypt(bytes, 0, bytes, 0);
        des.encrypt(bytes, 8, bytes, 8);
    }


    //
    // readParameters() - read parameters from the html source or from the
    // command line. On the command line, the arguments are just a sequence of
    // param_name/param_value pairs where the names and values correspond to
    // those expected in the html applet tag source.
    //

    public void readParameters()
    {
        host = readParameter("HOST", !inAnApplet);
        if (host == null)
        {
            host = getCodeBase().getHost();
            if (host.equals(""))
            {
                fatalError("HOST parameter not specified");
            }
        }

        String s = readParameter("PORT", true);
        port = Integer.parseInt(s);
    }


    public String readParameter(String name, boolean required)
    {
        if (inAnApplet)
        {
            String s = getParameter(name);
            if ((s == null) && required)
            {
                fatalError(name + " parameter not specified");
            }
            return s;
        }

        for (int i = 0; i < mainArgs.length; i += 2)
        {
            if (mainArgs[i].equalsIgnoreCase(name))
            {
                try
                {
                    return mainArgs[i + 1];
                }
                catch (Exception e)
                {
                    if (required)
                    {
                        fatalError(name + " parameter not specified");
                    }
                    return null;
                }
            }
        }
        if (required)
        {
            fatalError(name + " parameter not specified");
        }
        return null;
    }


    //
    // fatalError() - print out a fatal error message.
    //

    public void fatalError(String s)
    {
        System.out.println(s);

        if (inAnApplet)
        {
            removeAll();
            Label l = new Label(s);

            setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
            add(l);
            validate();
            Thread.currentThread().stop();
        }
        else
        {
            System.exit(1);
        }
    }
}
