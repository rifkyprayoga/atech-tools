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
//

// RfbProto.java
//

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
class myInputStream extends FilterInputStream {
  public myInputStream(InputStream in) {
    super(in);
  }
  public int read(byte[] b, int off, int len) throws IOException {
    System.out.println("read(byte[] b, int off, int len) called");
    return super.read(b, off, len);
  }
}
*/

public class RfbProto
{

    static final String versionMsg = "RFB 003.003\n";
    static final int ConnFailed = 0, NoAuth = 1, VncAuth = 2;
    static final int VncAuthOK = 0, VncAuthFailed = 1, VncAuthTooMany = 2;

    static final int FramebufferUpdate = 0, SetColourMapEntries = 1, Bell = 2, ServerCutText = 3;

    static final int SetPixelFormat = 0, FixColourMapEntries = 1, SetEncodings = 2, FramebufferUpdateRequest = 3,
            KeyEvent = 4, PointerEvent = 5, ClientCutText = 6;

    static final int EncodingRaw = 0, EncodingCopyRect = 1, EncodingRRE = 2, EncodingCoRRE = 4, EncodingHextile = 5;

    static final int HextileRaw = (1 << 0);
    static final int HextileBackgroundSpecified = (1 << 1);
    static final int HextileForegroundSpecified = (1 << 2);
    static final int HextileAnySubrects = (1 << 3);
    static final int HextileSubrectsColoured = (1 << 4);

    String host;
    int port;
    Socket sock;
    DataInputStream is;
    OutputStream os;
    boolean inNormalProtocol = false;
    VncViewer v;


    //
    // Constructor. Just make TCP connection to RFB server.
    //

    RfbProto(String h, int p, VncViewer v1) throws IOException
    {
        v = v1;
        host = h;
        port = p;
        sock = new Socket(host, port);
        is = new DataInputStream(new BufferedInputStream(sock.getInputStream(), 16384));
        os = sock.getOutputStream();
    }


    void close()
    {
        try
        {
            sock.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //
    // Read server's protocol version message
    //

    int serverMajor, serverMinor;


    void readVersionMsg() throws IOException
    {

        byte[] b = new byte[12];

        is.readFully(b);

        if ((b[0] != 'R') || (b[1] != 'F') || (b[2] != 'B') || (b[3] != ' ') || (b[4] < '0') || (b[4] > '9')
                || (b[5] < '0') || (b[5] > '9') || (b[6] < '0') || (b[6] > '9') || (b[7] != '.') || (b[8] < '0')
                || (b[8] > '9') || (b[9] < '0') || (b[9] > '9') || (b[10] < '0') || (b[10] > '9') || (b[11] != '\n'))
        {
            throw new IOException("Host " + host + " port " + port + " is not an RFB server");
        }

        serverMajor = (b[4] - '0') * 100 + (b[5] - '0') * 10 + (b[6] - '0');
        serverMinor = (b[8] - '0') * 100 + (b[9] - '0') * 10 + (b[10] - '0');
    }


    //
    // Write our protocol version message
    //

    void writeVersionMsg() throws IOException
    {
        byte[] b = new byte[12];
        versionMsg.getBytes(0, 12, b, 0);
        os.write(b);
    }


    //
    // Find out the authentication scheme.
    //

    int readAuthScheme() throws IOException
    {
        int authScheme = is.readInt();

        switch (authScheme)
        {

            case ConnFailed:
                int reasonLen = is.readInt();
                byte[] reason = new byte[reasonLen];
                is.readFully(reason);
                throw new IOException(new String(reason, 0));

            case NoAuth:
            case VncAuth:
                return authScheme;

            default:
                throw new IOException("Unknown authentication scheme from RFB " + "server " + authScheme);

        }
    }


    //
    // Write the client initialisation message
    //

    void writeClientInit() throws IOException
    {
        if (v.options.shareDesktop)
        {
            os.write(1);
        }
        else
        {
            os.write(0);
        }
        v.options.disableShareDesktop();
    }

    //
    // Read the server initialisation message
    //

    String desktopName;
    int framebufferWidth, framebufferHeight;
    int bitsPerPixel, depth;
    boolean bigEndian, trueColour;
    int redMax, greenMax, blueMax, redShift, greenShift, blueShift;


    void readServerInit() throws IOException
    {
        framebufferWidth = is.readUnsignedShort();
        framebufferHeight = is.readUnsignedShort();
        bitsPerPixel = is.readUnsignedByte();
        depth = is.readUnsignedByte();
        bigEndian = (is.readUnsignedByte() != 0);
        trueColour = (is.readUnsignedByte() != 0);
        redMax = is.readUnsignedShort();
        greenMax = is.readUnsignedShort();
        blueMax = is.readUnsignedShort();
        redShift = is.readUnsignedByte();
        greenShift = is.readUnsignedByte();
        blueShift = is.readUnsignedByte();
        byte[] pad = new byte[3];
        is.read(pad);
        int nameLength = is.readInt();
        byte[] name = new byte[nameLength];
        is.readFully(name);
        desktopName = new String(name, 0);

        inNormalProtocol = true;
    }


    //
    // Read the server message type
    //

    int readServerMessageType() throws IOException
    {
        return is.read();
    }

    //
    // Read a FramebufferUpdate message
    //

    int updateNRects;


    void readFramebufferUpdate() throws IOException
    {
        is.readByte();
        updateNRects = is.readUnsignedShort();
    }

    // Read a FramebufferUpdate rectangle header

    int updateRectX, updateRectY, updateRectW, updateRectH, updateRectEncoding;


    void readFramebufferUpdateRectHdr() throws IOException
    {
        updateRectX = is.readUnsignedShort();
        updateRectY = is.readUnsignedShort();
        updateRectW = is.readUnsignedShort();
        updateRectH = is.readUnsignedShort();
        updateRectEncoding = is.readInt();

        if ((updateRectX + updateRectW > framebufferWidth) || (updateRectY + updateRectH > framebufferHeight))
        {
            throw new IOException("Framebuffer update rectangle too large: " + updateRectW + "x" + updateRectH + " at ("
                    + updateRectX + "," + updateRectY + ")");
        }
    }

    // Read CopyRect source X and Y.

    int copyRectSrcX, copyRectSrcY;


    void readCopyRect() throws IOException
    {
        copyRectSrcX = is.readUnsignedShort();
        copyRectSrcY = is.readUnsignedShort();
    }


    //
    // Read a ServerCutText message
    //

    String readServerCutText() throws IOException
    {
        byte[] pad = new byte[3];
        is.read(pad);
        int len = is.readInt();
        byte[] text = new byte[len];
        is.readFully(text);
        return new String(text, 0);
    }


    //
    // Write a FramebufferUpdateRequest message
    //

    void writeFramebufferUpdateRequest(int x, int y, int w, int h, boolean incremental) throws IOException
    {
        byte[] b = new byte[10];

        b[0] = (byte) FramebufferUpdateRequest;
        b[1] = (byte) (incremental ? 1 : 0);
        b[2] = (byte) ((x >> 8) & 0xff);
        b[3] = (byte) (x & 0xff);
        b[4] = (byte) ((y >> 8) & 0xff);
        b[5] = (byte) (y & 0xff);
        b[6] = (byte) ((w >> 8) & 0xff);
        b[7] = (byte) (w & 0xff);
        b[8] = (byte) ((h >> 8) & 0xff);
        b[9] = (byte) (h & 0xff);

        os.write(b);
    }


    //
    // Write a SetPixelFormat message
    //

    void writeSetPixelFormat(int bitsPerPixel, int depth, boolean bigEndian, boolean trueColour, int redMax,
            int greenMax, int blueMax, int redShift, int greenShift, int blueShift) throws IOException
    {
        byte[] b = new byte[20];

        b[0] = (byte) SetPixelFormat;
        b[4] = (byte) bitsPerPixel;
        b[5] = (byte) depth;
        b[6] = (byte) (bigEndian ? 1 : 0);
        b[7] = (byte) (trueColour ? 1 : 0);
        b[8] = (byte) ((redMax >> 8) & 0xff);
        b[9] = (byte) (redMax & 0xff);
        b[10] = (byte) ((greenMax >> 8) & 0xff);
        b[11] = (byte) (greenMax & 0xff);
        b[12] = (byte) ((blueMax >> 8) & 0xff);
        b[13] = (byte) (blueMax & 0xff);
        b[14] = (byte) redShift;
        b[15] = (byte) greenShift;
        b[16] = (byte) blueShift;

        os.write(b);
    }


    //
    // Write a FixColourMapEntries message. The values in the red, green and
    // blue arrays are from 0 to 65535.
    //

    void writeFixColourMapEntries(int firstColour, int nColours, int[] red, int[] green, int[] blue) throws IOException
    {
        byte[] b = new byte[6 + nColours * 6];

        b[0] = (byte) FixColourMapEntries;
        b[2] = (byte) ((firstColour >> 8) & 0xff);
        b[3] = (byte) (firstColour & 0xff);
        b[4] = (byte) ((nColours >> 8) & 0xff);
        b[5] = (byte) (nColours & 0xff);

        for (int i = 0; i < nColours; i++)
        {
            b[6 + i * 6] = (byte) ((red[i] >> 8) & 0xff);
            b[6 + i * 6 + 1] = (byte) (red[i] & 0xff);
            b[6 + i * 6 + 2] = (byte) ((green[i] >> 8) & 0xff);
            b[6 + i * 6 + 3] = (byte) (green[i] & 0xff);
            b[6 + i * 6 + 4] = (byte) ((blue[i] >> 8) & 0xff);
            b[6 + i * 6 + 5] = (byte) (blue[i] & 0xff);
        }

        os.write(b);
    }


    //
    // Write a SetEncodings message
    //

    void writeSetEncodings(int[] encs, int len) throws IOException
    {
        byte[] b = new byte[4 + 4 * len];

        b[0] = (byte) SetEncodings;
        b[2] = (byte) ((len >> 8) & 0xff);
        b[3] = (byte) (len & 0xff);

        for (int i = 0; i < len; i++)
        {
            b[4 + 4 * i] = (byte) ((encs[i] >> 24) & 0xff);
            b[5 + 4 * i] = (byte) ((encs[i] >> 16) & 0xff);
            b[6 + 4 * i] = (byte) ((encs[i] >> 8) & 0xff);
            b[7 + 4 * i] = (byte) (encs[i] & 0xff);
        }

        os.write(b);
    }


    //
    // Write a ClientCutText message
    //

    void writeClientCutText(String text) throws IOException
    {
        byte[] b = new byte[8 + text.length()];

        b[0] = (byte) ClientCutText;
        b[4] = (byte) ((text.length() >> 24) & 0xff);
        b[5] = (byte) ((text.length() >> 16) & 0xff);
        b[6] = (byte) ((text.length() >> 8) & 0xff);
        b[7] = (byte) (text.length() & 0xff);

        text.getBytes(0, text.length(), b, 8);

        os.write(b);
    }

    //
    // A buffer for putting pointer and keyboard events before being sent. This
    // is to ensure that multiple RFB events generated from a single Java Event
    // will all be sent in a single network packet. The maximum possible
    // length is 4 modifier down events, a single key event followed by 4
    // modifier up events i.e. 9 key events or 72 bytes.
    //

    byte[] eventBuf = new byte[72];
    int eventBufLen;

    //
    // Write a pointer event message. We may need to send modifier key events
    // around it to set the correct modifier state. Also buttons 2 and 3 are
    // represented as having ALT and META modifiers respectively.
    //

    int pointerMask = 0;


    void writePointerEvent(Event evt) throws IOException
    {
        byte[] b = new byte[6];

        if (evt.id == Event.MOUSE_DOWN)
        {
            pointerMask = 1;
            if ((evt.modifiers & Event.ALT_MASK) != 0)
            {
                if (v.options.reverseMouseButtons2And3)
                    pointerMask = 4;
                else
                    pointerMask = 2;
            }
            if ((evt.modifiers & Event.META_MASK) != 0)
            {
                if (v.options.reverseMouseButtons2And3)
                    pointerMask = 2;
                else
                    pointerMask = 4;
            }
        }
        else if (evt.id == Event.MOUSE_UP)
        {
            pointerMask = 0;
        }

        evt.modifiers &= ~(Event.ALT_MASK | Event.META_MASK);

        eventBufLen = 0;

        writeModifierKeyEvents(evt.modifiers);

        if (evt.x < 0)
            evt.x = 0;
        if (evt.y < 0)
            evt.y = 0;

        eventBuf[eventBufLen++] = (byte) PointerEvent;
        eventBuf[eventBufLen++] = (byte) pointerMask;
        eventBuf[eventBufLen++] = (byte) ((evt.x >> 8) & 0xff);
        eventBuf[eventBufLen++] = (byte) (evt.x & 0xff);
        eventBuf[eventBufLen++] = (byte) ((evt.y >> 8) & 0xff);
        eventBuf[eventBufLen++] = (byte) (evt.y & 0xff);

        //
        // Always release all modifiers after an "up" event
        //

        if (pointerMask == 0)
        {
            writeModifierKeyEvents(0);
        }

        os.write(eventBuf, 0, eventBufLen);
    }


    //
    // Write a key event message. We may need to send modifier key events
    // around it to set the correct modifier state. Also we need to translate
    // from the Java key values to the X keysym values used by the RFB protocol.
    //

    void writeKeyEvent(Event evt) throws IOException
    {
        int key = evt.key;
        boolean down = false;

        if ((evt.id == Event.KEY_PRESS) || (evt.id == Event.KEY_ACTION))
            down = true;

        if ((evt.id == Event.KEY_ACTION) || (evt.id == Event.KEY_ACTION_RELEASE))
        {

            //
            // A KEY_ACTION event should be one of the following. If not then
            // just
            // ignore the event.
            //

            switch (key)
            {
                case Event.HOME:
                    key = 0xff50;
                    break;
                case Event.LEFT:
                    key = 0xff51;
                    break;
                case Event.UP:
                    key = 0xff52;
                    break;
                case Event.RIGHT:
                    key = 0xff53;
                    break;
                case Event.DOWN:
                    key = 0xff54;
                    break;
                case Event.PGUP:
                    key = 0xff55;
                    break;
                case Event.PGDN:
                    key = 0xff56;
                    break;
                case Event.END:
                    key = 0xff57;
                    break;
                case Event.F1:
                    key = 0xffbe;
                    break;
                case Event.F2:
                    key = 0xffbf;
                    break;
                case Event.F3:
                    key = 0xffc0;
                    break;
                case Event.F4:
                    key = 0xffc1;
                    break;
                case Event.F5:
                    key = 0xffc2;
                    break;
                case Event.F6:
                    key = 0xffc3;
                    break;
                case Event.F7:
                    key = 0xffc4;
                    break;
                case Event.F8:
                    key = 0xffc5;
                    break;
                case Event.F9:
                    key = 0xffc6;
                    break;
                case Event.F10:
                    key = 0xffc7;
                    break;
                case Event.F11:
                    key = 0xffc8;
                    break;
                case Event.F12:
                    key = 0xffc9;
                    break;
                default:
                    return;
            }

        }
        else
        {

            //
            // A "normal" key press. Ordinary ASCII & Latin-1 characters go
            // straight
            // through. For CTRL-<letter>, CTRL is sent separately so just send
            // <letter>. Backspace, tab, return, escape and delete have special
            // keysyms. Anything else we ignore.
            //

            if (key < 32)
            {
                if ((evt.modifiers & Event.CTRL_MASK) != 0)
                {
                    key += 96;
                    if (key == 127) // CTRL-_
                        key = 95;
                }
                else
                {
                    switch (key)
                    {
                        case 8:
                            key = 0xff08;
                            break;
                        case 9:
                            key = 0xff09;
                            break;
                        case 10:
                            key = 0xff0d;
                            break;
                        case 27:
                            key = 0xff1b;
                            break;
                    }
                }

            }
            else if (key < 256)
            {

                // For Latin-1, Unicode and X keysyms should be the same...

                if (key == 127) // except delete
                    key = 0xffff;

            }
            else
            {

                // Not Latin-1 or a control character - ignore, except that...

                // JDK1.1 on X incorrectly passes some keysyms straight through,
                // so
                // we do too. JDK1.1.4 seems to have fixed this.
                if ((key < 0xff00) || (key > 0xffff))
                    return;
            }
        }

        eventBufLen = 0;

        writeModifierKeyEvents(evt.modifiers);

        writeKeyEvent(key, down);

        //
        // Always release all modifiers after an "up" event
        //

        if (!down)
        {
            writeModifierKeyEvents(0);
        }

        os.write(eventBuf, 0, eventBufLen);
    }


    //
    // Add a raw key event with the given X keysym to eventBuf.
    //

    void writeKeyEvent(int keysym, boolean down) throws IOException
    {
        eventBuf[eventBufLen++] = (byte) KeyEvent;
        eventBuf[eventBufLen++] = (byte) (down ? 1 : 0);
        eventBuf[eventBufLen++] = (byte) 0;
        eventBuf[eventBufLen++] = (byte) 0;
        eventBuf[eventBufLen++] = (byte) ((keysym >> 24) & 0xff);
        eventBuf[eventBufLen++] = (byte) ((keysym >> 16) & 0xff);
        eventBuf[eventBufLen++] = (byte) ((keysym >> 8) & 0xff);
        eventBuf[eventBufLen++] = (byte) (keysym & 0xff);
    }

    //
    // Write key events to set the correct modifier state.
    //

    int oldModifiers;


    void writeModifierKeyEvents(int newModifiers) throws IOException
    {
        if ((newModifiers & Event.CTRL_MASK) != (oldModifiers & Event.CTRL_MASK))
            writeKeyEvent(0xffe3, (newModifiers & Event.CTRL_MASK) != 0);

        if ((newModifiers & Event.SHIFT_MASK) != (oldModifiers & Event.SHIFT_MASK))
            writeKeyEvent(0xffe1, (newModifiers & Event.SHIFT_MASK) != 0);

        if ((newModifiers & Event.META_MASK) != (oldModifiers & Event.META_MASK))
            writeKeyEvent(0xffe7, (newModifiers & Event.META_MASK) != 0);

        if ((newModifiers & Event.ALT_MASK) != (oldModifiers & Event.ALT_MASK))
            writeKeyEvent(0xffe9, (newModifiers & Event.ALT_MASK) != 0);

        oldModifiers = newModifiers;
    }
}
