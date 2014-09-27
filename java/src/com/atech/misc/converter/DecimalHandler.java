package com.atech.misc.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *  This file is part of ATech Tools library.
 *  
 *  DecimalHandler - Decimal converter
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

public class DecimalHandler
{

    ArrayList<DecimalFormat> formaters = null;

    /**
     * Constructor
     * 
     * @param max_decimal
     */
    public DecimalHandler(int max_decimal)
    {
        this.formaters = new ArrayList<DecimalFormat>();
        this.formaters.add(new DecimalFormat("#0"));

        String decs = "#0.";

        for (int i = 1; i <= max_decimal; i++)
        {
            decs += "0";
            this.formaters.add(new DecimalFormat(decs));
        }

    }

    /**
     * Get Decimal As String
     * 
     * @param dec_value
     * @param decimal_places
     * @return
     */
    public String getDecimalAsString(float dec_value, int decimal_places)
    {
        return formaters.get(decimal_places).format(dec_value);
    }

    /**
     * Get Decimal As String
     * 
     * @param dec_value
     * @param decimal_places
     * @return
     */
    public String getDecimalAsString(double dec_value, int decimal_places)
    {
        return formaters.get(decimal_places).format(dec_value);
    }

}
