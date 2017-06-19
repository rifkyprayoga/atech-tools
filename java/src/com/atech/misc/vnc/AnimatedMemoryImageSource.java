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
// AnimatedMemoryImageSource.java
//

package com.atech.misc.vnc;

import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;

class AnimatedMemoryImageSource implements ImageProducer
{

    int width;
    int height;
    ColorModel cm;
    byte[] pixels;
    ImageConsumer ic;


    AnimatedMemoryImageSource(int w, int h, ColorModel c, byte[] p)
    {
        width = w;
        height = h;
        cm = c;
        pixels = p;
    }


    public void addConsumer(ImageConsumer c)
    {
        if (ic == c)
            return;

        if (ic != null)
        {
            ic.imageComplete(ImageConsumer.IMAGEERROR);
        }

        ic = c;
        ic.setDimensions(width, height);
        ic.setColorModel(cm);
        ic.setHints(ImageConsumer.RANDOMPIXELORDER);
        ic.setPixels(0, 0, width, height, cm, pixels, 0, width);
        ic.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
    }


    public boolean isConsumer(ImageConsumer c)
    {
        return (ic == c);
    }


    public void removeConsumer(ImageConsumer c)
    {
        if (ic == c)
            ic = null;
    }


    public void requestTopDownLeftRightResend(ImageConsumer c)
    {
    }


    public void startProduction(ImageConsumer c)
    {
        addConsumer(c);
    }


    void newPixels(int x, int y, int w, int h)
    {
        if (ic != null)
        {
            ic.setPixels(x, y, w, h, cm, pixels, width * y + x, width);
            ic.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
        }
    }
}
