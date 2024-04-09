package com.atech.data;

/**
 * Created by andy on 15.10.15.
 */
/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2015  Andy (Aleksander) Rozman (Atech-Software)
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
 *  https://github.com/andyrozman/atech-tools or contact us via this email:
 *  andy@atech-software.com
 *
 *  @author Andy
 *
 */
public class AbstractTimeDataDto implements Comparable<AbstractTimeDataDto>
{

    protected int time;
    protected String timeString;


    public AbstractTimeDataDto(String time)
    {
        setTime(time);
    }


    private void setTime(String time)
    {
        this.timeString = time;
        String s = time.replace(":", "");
        this.time = Integer.parseInt(s);
    }


    public int getTime()
    {
        return time;
    }


    public void setTime(int time)
    {
        this.time = time;
    }


    public String getTimeString()
    {
        return timeString;
    }


    public void setTimeString(String timeString)
    {
        this.timeString = timeString;
    }


    public int compareTo(AbstractTimeDataDto o)
    {
        return this.time - o.time;
    }

}
