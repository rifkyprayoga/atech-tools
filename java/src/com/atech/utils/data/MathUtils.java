package com.atech.utils.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class MathUtils
{

    public static MathData calculateQuartilesAndAverges(ArrayList<Double> values) throws Exception
    {
        MathData data = new MathData();

        quartiles(values, data);
        calcAvgAndStdDev(values, data);

        return data;
    }

    public static void calcAvgAndStdDev(ArrayList<Double> values, MathData data)
    {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0.0d;

        for (double d : values)
        {
            sum += d;

            if (min > d)
            {
                min = d;
            }

            if (max < d)
            {
                max = d;
            }
        }

        double avg = sum / values.size();

        double stdDevA = avg - min;
        double stdDevB = max - avg;

        data.stdDeviation = stdDevA > stdDevB ? stdDevA : stdDevB;
        data.average = avg;

    }

    public static double roundTo2Decimals(double d)
    {
        BigDecimal bd = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static void quartiles(ArrayList<Double> values, MathData data) throws Exception
    {
        if (values.size() < 3)
            throw new Exception("This method is not designed to handle lists with fewer than 3 elements.");

        double median = median(values);

        ArrayList<Double> lowerHalf = getValuesLessThan(values, median, true);
        ArrayList<Double> upperHalf = getValuesGreaterThan(values, median, true);

        data.quartileMin = median(lowerHalf);
        data.quartileMedian = median;
        data.quartileMax = median(upperHalf);

        // return new double[] { median(lowerHalf), median, median(upperHalf) };
    }

    public static ArrayList<Double> getValuesGreaterThan(ArrayList<Double> values, double limit, boolean orEqualTo)
    {
        ArrayList<Double> modValues = new ArrayList<Double>();

        for (double value : values)
        {
            if (value > limit || value == limit && orEqualTo)
            {
                modValues.add(value);
            }
        }

        return modValues;
    }

    public static ArrayList<Double> getValuesLessThan(ArrayList<Double> values, double limit, boolean orEqualTo)
    {
        ArrayList<Double> modValues = new ArrayList<Double>();

        for (double value : values)
        {
            if (value < limit || value == limit && orEqualTo)
            {
                modValues.add(value);
            }
        }

        return modValues;
    }

    public static double median(ArrayList<Double> values)
    {
        Collections.sort(values);

        if (values.size() % 2 == 1)
            return values.get((values.size() + 1) / 2 - 1);
        else
        {
            double lower = values.get(values.size() / 2 - 1);
            double upper = values.get(values.size() / 2);

            return (lower + upper) / 2.0;
        }
    }

    public static class MathData
    {
        public double getQuartileMin()
        {
            return quartileMin;
        }

        public void setQuartileMin(double quartileMin)
        {
            this.quartileMin = quartileMin;
        }

        public double getQuartileMax()
        {
            return quartileMax;
        }

        public void setQuartileMax(double quartileMax)
        {
            this.quartileMax = quartileMax;
        }

        public double getQuartileMedian()
        {
            return quartileMedian;
        }

        public void setQuartileMedian(double quartileMedian)
        {
            this.quartileMedian = quartileMedian;
        }

        public double getAverage()
        {
            return average;
        }

        public void setAverage(double average)
        {
            this.average = average;
        }

        public double getStdDeviation()
        {
            return stdDeviation;
        }

        public void setStdDeviation(double stdDeviation)
        {
            this.stdDeviation = stdDeviation;
        }

        public double[] getQuartiles()
        {
            return quartiles;
        }

        private double[] quartiles;

        private double quartileMin;
        private double quartileMax;
        private double quartileMedian;

        private double average;
        private double stdDeviation;

        public void setQuartiles(double[] values)
        {
            this.quartiles = values;
            quartileMin = values[0];
            quartileMax = values[2];
            quartileMedian = values[1];
        }

    }

}
