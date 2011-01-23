package com.atech.utils.data;

import com.atech.misc.converter.DecimalHandler;

/**
 *  This file is part of ATech Tools library.
 *  
 *  Rounding - This is rounding utility, with some special features
 *  thread.
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


public class Rounding
{

    /**
     * 
     */
    public static DecimalHandler decimal_handler = new DecimalHandler(3);
    
    /**
     * Round
     * 
     * @param input_val
     * @param num_places
     * @return
     */
    public static double round(double input_val, int num_places) 
    {
        long f = (long)Math.pow(10, num_places);

        input_val = input_val * f;

        double tmp = Math.round(input_val);

        return (double)tmp / f;
    }
    
    
    /**
     * Round 
     * 
     * @param input_val
     * @param num_places
     * @return
     */
    public static float round(float input_val, int num_places) 
    {
        
        return (float)round((double)input_val, num_places);
    }
    
    
    /**
     * Special Rounding
     * 
     * Supported formats: 2, 1, 0.1, 0.25, 0.1, 0.025 (each format must be implemented separately)
     * 
     * @param input_val
     * @param format_string
     * 
     * @return
     */
    public static double specialRounding(double input_val, String format_string)
    {
        
        if ((format_string.equals("2")) || (format_string.equals("1")))
        {
            int val = (int)input_val;
            
            if (format_string.equals("2"))
            {
                if (val%2==1)
                {
                    if (input_val < val)
                        val--;
                    else
                        val++;
                }
            }
                
            return val;
        }
        else
        {
//            String fr = getInsulinPrecisionString(mode, type);
            
            int val_full = (int)input_val;
            double val_part = input_val - val_full;
            
            
            if (format_string.equals("0.5"))
            {
                if (val_part < 0.25f)
                {
                    return (double)val_full;
                }
                else //if (val_part>=0.5)
                {
                    return ((double)val_full) + 0.5f;
                }
            }
            else if (format_string.equals("0.25"))
            {
                
                double f = (double)val_full;
                
                if (val_part < 0.125f)
                {
                    f += 0.0f;
                }
                else if ((val_part >= 0.125f) && (val_part <0.375f))
                {
                    f += 0.25f;
                }
                else if ((val_part >= 0.375f) && (val_part <0.625f))
                {
                    f += 0.5f;
                }
                else
                {
                    f += 0.75f;
                }
                
                return f;
                
            }
            else if (format_string.equals("0.1"))
            {
                return Rounding.round(input_val, 1);
            }
            else if (format_string.equals("0.025"))
            {
                boolean debug = false;
                
                // FIXME  this part was not tested too much

                if (debug)
                    System.out.println("Input val: " + input_val);
                
                double f = Rounding.round(input_val, 1);
                
                if (debug)
                    System.out.println("f: " + f);
                
                val_part = input_val - f;
                
                if (val_part<0)
                {
                    val_part += 0.1d;
                }
                
                if (debug)
                    System.out.println("Val part: " + val_part);
                
                if (val_part < 0.0125f)
                {
                    if (debug)
                        System.out.println("VP: 0.0");
                    f += 0.0d;
                }
                else if ((val_part >= 0.0125f) && (val_part <0.0375f))
                {
                    if (debug)
                        System.out.println("VP: 0.025");
                    f += 0.025d;
                }
                else if ((val_part >= 0.0375f) && (val_part <0.0625f))
                {
                    if (debug)
                        System.out.println("VP: 0.05");
                    f += 0.050d;
                }
                else
                {
                    if (debug)
                        System.out.println("VP: 0.075");
                    f += 0.075d;
                }
                
                //System.out.println("f: " + f);
                
                return f;
                
            }
            
        }
        
        return 0.0f;
        
    }

    
    /**
     * Special Rounding String
     * 
     * @param input_val
     * @param format_string
     * @return
     */
    public static String specialRoundingString(double input_val, String format_string)
    {
        double f = specialRounding(input_val, format_string);
        
        if ((format_string.equals("2")) || (format_string.equals("1")))
        {
            return Rounding.decimal_handler.getDecimalAsString(f, 0);
        }
        else if ((format_string.equals("0.5")) || (format_string.equals("0.1")))
        {
            return Rounding.decimal_handler.getDecimalAsString(f, 1);
        }
        else if (format_string.equals("0.25"))
        {
            return Rounding.decimal_handler.getDecimalAsString(f, 2);
        }
        else //if (format_string.equals("0.025"))
        {
            return Rounding.decimal_handler.getDecimalAsString(f, 3);
        }
        
    }
    
/*    
    public static final void main(String[] args)
    {
        
        System.out.println("Round (3.1375) 2: " + Rounding.specialRounding(3.1375f, "2"));
        System.out.println("Round (3.1375) 1: " + Rounding.specialRounding(3.1375f, "1"));
        
        // 2, 1, 0.1, 0.25, 0.1, 0.025
        System.out.println("Round (3.1375) 0.5: " + Rounding.specialRounding(3.1375f, "0.5"));
        System.out.println("Round (3.6375) 0.5: " + Rounding.specialRounding(3.6375f, "0.5"));

        System.out.println("Round (3.1375) 0.1: " + Rounding.specialRounding(3.1375f, "0.1"));
        System.out.println("Round (3.6375) 0.1: " + Rounding.specialRounding(3.6375f, "0.1"));
        
        System.out.println("Round (3.1375) 0.25: " + Rounding.specialRounding(3.1375f, "0.25"));
        System.out.println("Round (3.6375) 0.25: " + Rounding.specialRounding(3.6375f, "0.25"));
        
        System.out.println("Round (3.1375) 0.025: " + Rounding.specialRounding(3.1375f, "0.025"));
        System.out.println("Round (3.6375) 0.025: " + Rounding.specialRounding(3.6375f, "0.025"));
        System.out.println("Round (3.1275) 0.025: " + Rounding.specialRounding(3.1275f, "0.025"));
        System.out.println("\n");
        
        System.out.println("Round (3.6120) 0.025: " + Rounding.specialRounding(3.6120f, "0.025"));
        System.out.println("Round (3.6285) 0.025: " + Rounding.specialRounding(3.6285f, "0.025"));
        System.out.println("Round (3.6525) 0.025: " + Rounding.specialRounding(3.6525f, "0.025"));
        System.out.println("Round (3.6761) 0.025: " + Rounding.specialRounding(3.6761f, "0.025"));

        System.out.println("Round (3.1375) 0.025: " + Rounding.specialRoundingString(3.1375f, "0.025"));
        System.out.println("Round (3.6375) 0.025: " + Rounding.specialRoundingString(3.6375f, "0.025"));
        
    }
  */  
    
}
