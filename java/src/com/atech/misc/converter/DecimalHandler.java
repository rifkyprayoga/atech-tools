package com.atech.misc.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class DecimalHandler
{

    ArrayList<DecimalFormat> formaters = null;
    
    public DecimalHandler(int max_decimal)
    {
        this.formaters = new ArrayList<DecimalFormat>();
        this.formaters.add(new DecimalFormat("#0"));
        
        String decs = "#0.";
        
        for(int i=1; i<max_decimal; i++)
        {
            decs += "0";
            this.formaters.add(new DecimalFormat(decs));
        }
        
    }
    
    public String getDecimalAsString(float dec_value, int decimal_places)
    {
        return formaters.get(decimal_places).format(dec_value);
    }
    
    public String getDecimalAsString(double dec_value, int decimal_places)
    {
        return formaters.get(decimal_places).format(dec_value);
    }
    
    
    
}

