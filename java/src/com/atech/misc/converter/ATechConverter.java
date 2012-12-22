package com.atech.misc.converter;

import java.text.DecimalFormat;
import java.util.Hashtable;


/**
 *  This file is part of ATech Tools library.
 *  
 *  ATechConverter - Base class for converter
 *  Copyright (C) 2009  Andy (Aleksander) Rozman (Atech-Software)
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

public abstract class ATechConverter
{

    boolean m_debug = true;
    
    /**
     * The decimal_formaters.
     */
    public static Hashtable<Integer, DecimalFormat> decimal_formaters = null;
    
    private Hashtable<Integer,String> units = null;

    private int unit_1 = 1;
    private int unit_2 = 2;

    private int unit1_type = 1;
    private int unit2_type = 1;

    private int unit1_type_precission = 0;
    private int unit2_type_precission = 1;
    
    
    private float unit1_to_unit2_factor = 0.0f;
    private float unit2_to_unit1_factor = 0.0f;
    
    
    /**
     * The Constant BASETYPE_INT.
     */
    public final static int BASETYPE_INT = 1;
    
    /**
     * The Constant BASETYPE_FLOAT.
     */
    public final static int BASETYPE_FLOAT = 2;
    
    
    
    /**
     * The configured_type.
     */
    public int configured_type = 1;
    
    
    
    
    /**
     * Instantiates a new a tech converter.
     * @param U1 
     * @param U2 
     * @param U1_type 
     * @param U2_type 
     * @param U1_unit 
     * @param U2_unit 
     * @param convert_1_to_2 
     * @param convert_2_to_1 
     * @param dec_precission_U1 
     * @param dec_precission_U2 
     */
    public ATechConverter(int U1, int U2, int U1_type, int U2_type, String U1_unit, String U2_unit, float convert_1_to_2, float convert_2_to_1, int dec_precission_U1, int dec_precission_U2)
    {
        units = new Hashtable<Integer,String>();
        this.unit_1 = U1;
        this.unit_2 = U2;
        this.unit1_type = U1_type;
        this.unit2_type = U2_type;
        this.units.put(U1, U1_unit);
        this.units.put(U2, U2_unit);
        this.unit1_to_unit2_factor = convert_1_to_2;
        this.unit2_to_unit1_factor = convert_2_to_1;
        this.unit1_type_precission = dec_precission_U1;
        this.unit2_type_precission = dec_precission_U2;
        
        createDecimalFormaters(Math.max(this.unit1_type_precission, this.unit2_type_precission));
    }
    
    
    /**
     * Get BG Value By Type
     * 
     * @param input_type
     * @param output_type
     * @param value
     * @return
     */
    public float getValueByType(int input_type, int output_type, float value)
    {

        if (input_type == output_type)
            return value;
        

        float factor_to_source = 0.0f;
        float factor_to_target = 0.0f;
        @SuppressWarnings("unused")
        int source_type = 0;
        int source_precission = 0;
        int target_precission = 0;
        int target_type = 0;
        
        if (output_type == this.unit_1)
        {
            target_type = this.unit1_type;
            target_precission = unit1_type_precission;
            source_type = this.unit_2;
            source_precission = unit2_type_precission; 
            factor_to_target = unit2_to_unit1_factor;
            factor_to_source = unit1_to_unit2_factor;
        }
        else
        {
            target_type = this.unit2_type;
            target_precission = unit2_type_precission;
            source_type = this.unit_1;
            source_precission = unit1_type_precission; 
            factor_to_target = unit1_to_unit2_factor;
            factor_to_source = unit2_to_unit1_factor;
        }
        
        String input_v = getFormatedFloat(value, source_precission);
        
        debug("input_v: " + input_v);
        
        float val = value * factor_to_target;
        float val_source = Float.parseFloat(input_v);
        
        if (target_type== BASETYPE_INT)
        {
            int vali = (int)val;
            
            float step = getStep(target_type, target_precission);
            float last_step = 0.0f; //vali-step;
            
            for(float i=vali-step; i<(vali+20); i+=step)
            {
                if (last_step == 0.0f)
                    last_step = i;

                float v2 = i *  factor_to_source;
                //v2 = Math.round(v2);
                String s = getFormatedFloat(v2, source_precission);
                
                debug("Compare[s]: i=" + i + " val: " + input_v + " ?= " + s);
                debug("Compare[f]: i=" + i + " val: " + vali + " ?= " + v2);
                
                if (s.equals(input_v))
                    return i;
                
                if (v2 > val_source)
                {
                    debug("Calculated value has overriden. New value: " + v2 + " source value: " + val_source);
                    return last_step;
                }
                
                last_step = i;
            }
            
            return 0.0f;
        }
        else // UNIT_FLOAT
        {
            float vali = (float)val;
            
            float step = getStep(target_type, target_precission);
            float last_step = 0.0f; //getDecimaledFloat(vali, target_precission)-step;
            //float val_source = Float.parseFloat(input_v);
            
            for(float i=vali-step; i<(vali+20); i+=step)
            {
                if (last_step == 0.0f)
                    last_step = i;
                
                i = getDecimaledFloat(i, target_precission);
                float v2 = i *  factor_to_source;

                String s = getFormatedFloat(v2, source_precission);
                
                debug("Compare[s]: i=" + i + " val: " + input_v + " ?= " + s);
                debug("Compare[f]: i=" + i + " val: " + vali + " ?= " + v2);
                
                if (s.equals(input_v))
                {
                    return i;
                }
                
                if (v2 > val_source)
                {
                    debug("Calculated value has overriden. New value: " + v2 + " source value: " + val_source);
                    return last_step;
                }
                
                last_step = i;

                
                
            }
            return 0.0f;
        }
           

    }

    private void debug(String value)
    {
        if (m_debug)
            System.out.println(value);
    }
    
    
    /**
     * Get Decimaled Float
     * 
     * @param num
     * @param precission
     * @return
     */
    public float getDecimaledFloat(float num, int precission)
    {
        return Float.parseFloat(getFormatedFloat(num, precission).replace(',', '.'));
    }
    
    
    
    /**
     * Get Step
     * 
     * @param unit_type
     * @param precission
     * @return
     */
    public float getStep(int unit_type, int precission)
    {
        if (unit_type== BASETYPE_INT)
        {
            return 1;
        }
        else if (unit_type== BASETYPE_FLOAT)
        {
            if (precission==0)
                return 1;
            else
            {   
                String s = "0.";
                for(int i=0; i<precission; i++)
                {
                    if (i==(precission-1))
                        s += "1";
                    else
                        s += "0";
                }
                
                
                return Float.parseFloat(s);
            }
                
                
                //return Float.parseFloat(ATechConverter.getFormatedFloat(0.0000000000001f, precission));
                //1.0f / (1.0f * precission * precission);
        }
        else
            return 1;
    }
    

    /*
    public float getBGValueByTypeXAX(int input_type, int output_type, float value)
    {

        if (input_type == output_type)
            return value;
        else
        {

            int precission = 0;
            float factor = 0.0f;
            int source_type = 0;
            int source_type_precission = 0;
            
            if (output_type == this.unit_1)
            {
                precission = unit1_type_precission;
                factor = unit2_to_unit1_factor;
                source_type = this.unit_2;
                source_type_precission = unit2_type_precission; 
            }
            else
            {
                precission = unit2_type_precission;
                factor = unit1_to_unit2_factor;
                
            }
            
            
            
            if (output_type == this.unit_1)
            {
                // get formated value for unit_1
                String input_v = getFormatedFloat(value, this.unit1_type_precission);
                
                float val = value * this.unit2_to_unit1_factor;
                
                
                if (this.unit1_type== BASETYPE_INT)
                {
                    int vali = (int)val;
                    
                    float step = getStep(this.unit1_type, this.unit1_type_precission);
                    
                    for(float i=vali-step; i<(vali+20); i+=step)
                    {
                        
                        float v2 = i *  this.unit1_to_unit2_factor;
                        //int v2i = (int)v2;
                        String s = getFormatedFloat(v2, this.unit2_type_precission);
                        
                        System.out.println("Compare[s]: i=" + i + "val: " + input_v + " ?= " + s);
                        System.out.println("Compare[f]: i=" + i + "val: " + vali + " ?= " + v2);
                        
                        if (input_v.equals(s))
                            return i;

                        //if (v2==vali)

                        //System.out.println("Compare: i=" + i + "val: " + vali + " ?= " + v2);
                    }
                    
                    return 0.0f;
                }
                else // UNIT_FLOAT
                {
                    return 0.0f;
                }
            }
            else  // unit_2
            {
                System.out.println("Error: N/A");
                //return bg_value * DataAccess.MMOL_TO_MGDL_FACTOR;
                return 0.0f;
            }
        }
        //return value;

    } */
    
    
    private int getDifferentType(int type)
    {
        if (type==this.unit_1)
            return this.unit_2;
        else
            return this.unit_2;
    }
    
    
    /**
     * Get Unit
     * 
     * @param unit
     * @return
     */
    public String getUnit(int unit)
    {
        if (this.units.contains(unit))
            return this.units.get(unit);
        else
            return "";
    }
    
    
    /**
     * Get Value Different
     * 
     * @param type
     * @param value
     * @return
     */
    public float getValueDifferent(int type, float value)
    {
        return this.getValueByType(type, getDifferentType(type), value); 
    }

    /**
     * Creates the decimal formaters.
     * 
     * @param max_decs the max_decs
     */
    public static void createDecimalFormaters(int max_decs)
    {
        decimal_formaters = new Hashtable<Integer, DecimalFormat>();

        decimal_formaters.put(0, new DecimalFormat("#0"));
        
        String frm = "#0.";
        for(int i=1; i<=max_decs; i++)
        {
            frm += "0";
            decimal_formaters.put(i, new DecimalFormat(frm));
        }
        
    }

    
    /**
     * Gets the formated float.
     * 
     * @param value the value
     * @param decimals the decimals
     * 
     * @return the formated float
     */
    public static String getFormatedFloat(float value, int decimals)
    {
        return decimal_formaters.get(decimals).format(value);
    }
    
    /**
     * Gets the formated double.
     * 
     * @param value the value
     * @param decimals the decimals
     * 
     * @return the formated double
     */
    public static String getFormatedDouble(double value, int decimals)
    {
        return decimal_formaters.get(decimals).format(value);
    }
    
    /**
     * Gets the configured type.
     * 
     * @return the configured type
     */
    public int getConfiguredType()
    {
        return this.configured_type;
    }
    
    /**
     * Sets the configured type.
     * 
     * @param type the new configured type
     */
    public void setConfiguredType(int type)
    {
        this.configured_type = type;
    }
    
    
    
}
