package com.atech.misc.converter;

import java.text.DecimalFormat;
import java.util.Hashtable;

import com.atech.utils.ATDataAccess;


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

    ATDataAccess m_da = ATDataAccess.getInstance();
    
    /**
     * BG: mg/dL
     */
    public static final int BG_MGDL = 1;

    /**
     * BG: mmol/L
     */
    public static final int BG_MMOL = 2;

    /**
     * Which BG unit is used: BG_MGDL = mg/dl, BG_MMOL = mmol/l
     */
    public int m_BG_unit = BG_MGDL;
    

    /**
     * BG Units
     */
    public String[] bg_units = { "mg/dl", "mmol/l" };
    
    
    public static Hashtable<Integer, DecimalFormat> decimal_formaters = null;


    private int TYPE_1 = 1;
    private int TYPE_2 = 2;

    private int TYPE_1_TYPE = 1;
    private int TYPE_2_TYPE = 1;

    private int TYPE_1_TYPE_PRECISSION = 0;
    private int TYPE_2_TYPE_PRECISSION = 1;
    
    
    private float TYPE1_TO_TYPE2_FACTOR = 0.0f;
    private float TYPE2_TO_TYPE1_FACTOR = 0.0f;
    
    public final static int BASETYPE_INT = 1;
    public final static int BASETYPE_FLOAT = 2;
    
    
    
    public int configured_type = 1;
    
    
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

    
    public static String getFormatedFloat(float value, int decimals)
    {
        return decimal_formaters.get(decimals).format(value);
    }
    
    public static String getFormatedDouble(double value, int decimals)
    {
        return decimal_formaters.get(decimals).format(value);
    }
    
    public int getConfiguredType()
    {
        return this.configured_type;
    }
    
    public void setConfiguredType(int type)
    {
        this.configured_type = type;
    }
    
    
    public ATechConverter(int type1_type, int type2_type, float type1_2_type2, float type2_2_type1)
    {
        this.TYPE_1_TYPE = type1_type;
        this.TYPE_2_TYPE = type2_type;
        this.TYPE1_TO_TYPE2_FACTOR = type1_2_type2;
        this.TYPE2_TO_TYPE1_FACTOR = type2_2_type1;
        
        createDecimalFormaters(2);
    }
    
    
    
    
    /**
     * Depending on the return value of <code>getBGMeasurmentType()</code>,
     * either return the mg/dl or the mmol/l value of the database's value.
     * Default is mg/dl.
     * 
     * @param dbValue
     *            - The database's value (in float)
     * @return the BG in either mg/dl or mmol/l
     */
    public float getDisplayedBG(float dbValue)
    {
        /*switch (this.configured_type)
        {
        case TYPE1:
            // this POS should return a float rounded to 3 decimal places,
            // if I understand the docu correctly
            return (new BigDecimal(dbValue * MGDL_TO_MMOL_FACTOR, new MathContext(3, RoundingMode.HALF_UP))
                    .floatValue());
        case TYPE2:
        default:
            return dbValue;
        }*/
        return 0.0f;
    }

    /**
     * Get BG Value
     * 
     * @param bg_value
     * @return
     */
    public float getBGValue(float bg_value)
    {
/*        switch (this.m_BG_unit)
        {
        case BG_MMOL:
            return (bg_value * MGDL_TO_MMOL_FACTOR);
        case BG_MGDL:
        default:
            return bg_value;
        } */
        
        return 0.0f;

    }

    /**
     * Get BG Value By Type
     * 
     * @param type
     * @param bg_value
     * @return
     */
    public float getBGValueByType(int input_type, float bg_value)
    {
        /*switch (input_type)
        {
        case BG_MMOL:
            return (bg_value * MGDL_TO_MMOL_FACTOR);
        case BG_MGDL:
        default:
            return bg_value;
        }*/
        return 0.0f;
    }

    /**
     * Get BG Value By Type
     * 
     * @param input_type
     * @param output_type
     * @param bg_value
     * @return
     */
    public float getBGValueByType(int input_type, int output_type, float value)
    {

        if (input_type == output_type)
            return value;
        else
        {
            if (output_type == TYPE_1)
            {
                String input_v = getFormatedFloat(value, TYPE_2_TYPE_PRECISSION);
                
                float val = value * this.TYPE2_TO_TYPE1_FACTOR;
                
                if (TYPE_1_TYPE== BASETYPE_INT)
                {
                    int vali = (int)val;
                    
                    float step = 0.001f;
                    
                    //if (TYPE_2_TYPE== BASETYPE_INT)
                    {
                        step = 1;
                    }
                    
                    for(float i=vali-step; i<(vali+20); i+=step)
                    {
                        
                        float v2 = i *  this.TYPE1_TO_TYPE2_FACTOR;
                        //int v2i = (int)v2;
                        String s = getFormatedFloat(v2, TYPE_2_TYPE_PRECISSION);
                        
                        System.out.println("Compare[s]: i=" + i + "val: " + input_v + " ?= " + s);
                        System.out.println("Compare[f]: i=" + i + "val: " + vali + " ?= " + v2);
                        
                        if (input_v.equals(s))
                            return i;

                        //if (v2==vali)

                        //System.out.println("Compare: i=" + i + "val: " + vali + " ?= " + v2);
                    }
                    
                    return 0.0f;
                }
                else
                {
                    return 0.0f;
                }
            }
            else
            {
                System.out.println("Error: N/A");
                //return bg_value * DataAccess.MMOL_TO_MGDL_FACTOR;
                return 0.0f;
            }
        }
        //return value;

    }

    /**
     * Get BG Value Different
     * 
     * @param type
     * @param bg_value
     * @return
     */
    /*public float getBGValueDifferent(int type, float bg_value)
    {

        if (type == DataAccess.BG_MGDL)
        {
            return bg_value * DataAccess.MGDL_TO_MMOL_FACTOR;
        }
        else
        {
            return bg_value * DataAccess.MMOL_TO_MGDL_FACTOR;
        }

    }*/
    
    
    
    
    
    
    
}
