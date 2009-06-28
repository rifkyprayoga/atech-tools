package com.atech.graphics.components.scroll;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
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

public abstract class ScrollableText
{

    /**
     * The texts.
     */
    ArrayList<String> texts; 
    
    /**
     * The scroll_enabled.
     */
    boolean scroll_enabled = true;
    
    /**
     * The scroll_active.
     */
    boolean scroll_active = false;
    
    /**
     * The scroll_lines.
     */
    int scroll_lines = 0;

    /**
     * The index_texts.
     */
    ArrayList<String> index_texts;
    
    /**
     * The starting_number.
     */
    int starting_number = -1;

    /**
     * Checks if is scroll enabled.
     * 
     * @return true, if is scroll enabled
     */
    public boolean isScrollEnabled()
    {
        return scroll_enabled;
    }

    /**
     * Sets the scrolling texts.
     * 
     * @param texts the new scrolling texts
     */
    public void setScrollingTexts(ArrayList<String> texts)
    {
        this.texts = texts;
    }

    /**
     * Sets the scrolling lines.
     * 
     * @param lines the new scrolling lines
     */
    public void setScrollingLines(int lines)
    {
        this.scroll_lines = lines;
        this.checkScrollingAction();
    }

    private void checkScrollingAction()
    {
        if ((this.scroll_lines == 0) || (this.texts ==null) || (this.texts.size()==0))
            this.scroll_active = false;
        else
        {
            if (this.scroll_lines > this.texts.size())
                this.scroll_active = true;
            else
                this.scroll_active = false;
        }
    }


    /**
     * Scroll.
     */
    public void scroll()
    {
        this.starting_number++;

        if (this.starting_number == this.texts.size())
            this.starting_number = 0;

        //int col_idx = 0;
        int entries = 0;

        this.index_texts.clear();

        boolean filled = false;

        for (int i=this.starting_number; i<this.texts.size(); i++)
        {
            this.index_texts.add("" + i);
            entries++;

            if (entries == this.scroll_lines)
            {
                filled = true;
                break;
            }
        }

        if (!filled)
        {
            for (int i=0; i<this.texts.size(); i++)
            {
                this.index_texts.add("" + i);
                entries++;

                if (entries == this.scroll_lines)
                {
                    break;
                }
            }
        }

        paintScrollElements();

    }


    /**
     * Gets the scroll text nr.
     * 
     * @param number the number
     * 
     * @return the scroll text nr
     */
    protected String getScrollTextNr(int number)
    {
        String index = this.index_texts.get(number);

        return this.texts.get(Integer.parseInt(index));
    }


    /**
     * Paint scroll elements.
     */
    public abstract void paintScrollElements();

    //void scrollAction();

}


