package com.atech.misc.statistics;

public class StatisticsObject
{
    public float sum = 0.0f;
    public int count = 0;
    public int operation = 0;
    
    public static final int OPERATION_SUM = 1;
    public static final int OPERATION_AVERAGE = 2;
    public static final int OPERATION_COUNT = 3;
    public static final int OPERATION_MIN = 4;
    public static final int OPERATION_MAX = 5;
    
    public static final int OPERATION_SPECIAL = -1;
    
    
    public void setSum(float _sum)
    {
        this.sum = _sum;
    }
    
    public void setCount(int value)
    {
        this.count = value;
    }
    
    
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





