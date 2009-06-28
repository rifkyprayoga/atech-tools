package com.atech.misc.statistics;

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


public class StatisticsObject
{
    
    /**
     * The sum.
     */
    public float sum = 0.0f;
    
    /**
     * The count.
     */
    public int count = 0;
    
    /**
     * The operation.
     */
    public int operation = 0;
    
    /**
     * The Constant OPERATION_SUM.
     */
    public static final int OPERATION_SUM = 1;
    
    /**
     * The Constant OPERATION_AVERAGE.
     */
    public static final int OPERATION_AVERAGE = 2;
    
    /**
     * The Constant OPERATION_COUNT.
     */
    public static final int OPERATION_COUNT = 3;
    
    /**
     * The Constant OPERATION_MIN.
     */
    public static final int OPERATION_MIN = 4;
    
    /**
     * The Constant OPERATION_MAX.
     */
    public static final int OPERATION_MAX = 5;
    
    /**
     * The Constant OPERATION_SPECIAL.
     */
    public static final int OPERATION_SPECIAL = -1;
    
    
    /**
     * Sets the sum.
     * 
     * @param _sum the new sum
     */
    public void setSum(float _sum)
    {
        this.sum = _sum;
    }
    
    /**
     * Sets the count.
     * 
     * @param value the new count
     */
    public void setCount(int value)
    {
        this.count = value;
    }
    
    
    /**
     * Adds the to sum.
     * 
     * @param value the value
     */
    public void addToSum(float value)
    {
        if (value==0.0f)
            return;
        
        if ((this.operation == StatisticsObject.OPERATION_SUM) ||
            (this.operation == StatisticsObject.OPERATION_COUNT) ||    
            (this.operation == StatisticsObject.OPERATION_SPECIAL) ||    
            (this.operation == StatisticsObject.OPERATION_AVERAGE))
        {
            sum += value;
            count++;
        }
        else if (this.operation == StatisticsObject.OPERATION_MIN)
        {
            if (value < sum)
            {
                sum = value;
            }
        }
        else if (this.operation == StatisticsObject.OPERATION_MAX)
        {
            if (value > sum)
            {
                sum = value;
            }
        }
            
        
        
    }
    
    
    /**
     * Clean.
     */
    public void clean()
    {
        if ((this.operation == StatisticsObject.OPERATION_SUM) ||
            (this.operation == StatisticsObject.OPERATION_COUNT) ||    
            (this.operation == StatisticsObject.OPERATION_SPECIAL) ||    
            (this.operation == StatisticsObject.OPERATION_AVERAGE))
        {
            sum = 0;
            count = 0;
        }
        else if (this.operation == StatisticsObject.OPERATION_MIN)
        {
            sum = Float.MAX_VALUE;
            count = 0;
        }
        else if (this.operation == StatisticsObject.OPERATION_MAX)
        {
            sum = Float.MIN_VALUE;
            count = 0;
        }
    }

    
    /**
     * Gets the statistics.
     * 
     * @return the statistics
     */
    public float getStatistics()
    {
        if (this.operation == StatisticsObject.OPERATION_SUM)
        {
            return this.sum;
        }
        else if (this.operation == StatisticsObject.OPERATION_AVERAGE)
        {
            if (count!=0)
            {
                return this.sum / this.count;
            }
            else
                return 0.0f;
        }
        else if (this.operation == StatisticsObject.OPERATION_MIN)
        {
            if (sum == Float.MAX_VALUE)
                return 0.0f;
            else
                return sum;
        }
        else if (this.operation == StatisticsObject.OPERATION_MAX)
        {
            if (sum == Float.MIN_VALUE)
                return 0.0f;
            else
                return sum;
        }
        else if (this.operation == StatisticsObject.OPERATION_COUNT)
        {
            return this.count;
        }
        else if (this.operation == StatisticsObject.OPERATION_SPECIAL)
        {
            return this.sum;
        }
        else
            return 0.0f;
            
    }
    
    
    
    
    
}





