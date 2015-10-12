package com.atech.graphics.graphs;

/**
 *  This file is part of ATech Tools library.
 *  
 *  GraphConfigProperties - Properties needed for Graph Configuration
 *  
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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

public interface GraphConfigProperties // extends GGCPropertiesHelper
{

    /** 
     * Get AntiAliasing
     * 
     * @return 
     */
    int getAntiAliasing();


    /** 
     * Get Color Rendering
     * 
     * @return 
     */
    int getColorRendering();


    /** 
     * Get Dithering
     * 
     * @return 
     */
    int getDithering();


    /** 
     * Get Fractional Meetrics
     * 
     * @return 
     */
    int getFractionalMetrics();


    /** 
     * Get Interpolation
     * 
     * @return 
     */
    int getInterpolation();


    /** 
     * Get Rendering
     * 
     * @return 
     */
    int getRendering();


    /** 
     * Get Text Antialiasing
     * 
     * @return 
     */
    int getTextAntiAliasing();

    /*
     * public void setAntiAliasing(int value);
     * public void setColorRendering(int value);
     * public void setDithering(int value);
     * public void setFractionalMetrics(int value);
     * public void setInterpolation(int value);
     * public void setRendering(int value);
     * public void setTextAntiAliasing(int value);
     */

}
