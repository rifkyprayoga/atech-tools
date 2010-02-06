package com.atech.misc.statistics;

import java.util.ArrayList;

import com.atech.misc.converter.DecimalHandler;
import com.atech.utils.ATDataAccessAbstract;
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

public class StatisticsCollection
{
    
    /**
     * The stat_objects.
     */
    protected ArrayList<StatisticsObject> stat_objects;
    
    /**
     * The items.
     */
    protected ArrayList<StatisticsItem> items;
    
    /**
     * The base_item.
     */
    protected StatisticsItem base_item;
    
    /**
     * The m_da.
     */
    protected ATDataAccessAbstract m_da;
    
    /**
     * The decimal_handler.
     */
    protected DecimalHandler decimal_handler;
    
    /**
     * The special_processed.
     */
    protected boolean special_processed = false;
    
    /**
     * Instantiates a new statistics collection.
     * 
     * @param da the da
     * @param base_item_in the base_item_in
     */
    public StatisticsCollection(ATDataAccessAbstract da, StatisticsItem base_item_in)
    {
        stat_objects = new ArrayList<StatisticsObject>();
        this.base_item = base_item_in;
        this.m_da = da;
        this.decimal_handler = new DecimalHandler(2);
        
        if (this.base_item.weHaveSpecialActions())
        {
            this.items = new ArrayList<StatisticsItem>();
        }
        
        createStatisticsObjects();
    }
    
    /**
     * Clean statistics objects.
     */
    public void cleanStatisticsObjects()
    {
        for(int i=0; i<this.stat_objects.size(); i++)
        {
            stat_objects.get(i).clean();
        }

        if (this.base_item.weHaveSpecialActions())
            this.items.clear();
        
        this.special_processed = false;
    }
    
    
    /**
     * Process full collection.
     * 
     * @param list the list
     */
    public void processFullCollection(ArrayList<? extends StatisticsItem> list)
    {
        this.cleanStatisticsObjects();
        
        for(int i=0; i<list.size(); i++)
        {
            addItem(list.get(i)); 
        }
        
        special_processed = false;
    }
    

    
    
    
    /**
     * Adds the item.
     * 
     * @param item the item
     */
    public void addItem(StatisticsItem item)
    {
        processItem(item);
        
        if (this.base_item.weHaveSpecialActions())
            this.items.add(item);

        special_processed = false;
    }
    
    /**
     * Process item.
     * 
     * @param item the item
     */
    public void processItem(StatisticsItem item)
    {
        for(int i=1; i<this.getMaxStatisticsObject(); i++)
        {
            this.stat_objects.get(i-1).addToSum(item.getValueForItem(i)); 
        }
    }
    
    
    /**
     * Creates the statistics objects.
     */
    public void createStatisticsObjects()
    {
        for(int i=1; i<this.getMaxStatisticsObject(); i++)
        {
            StatisticsObject so = new StatisticsObject();
            so.operation = this.base_item.getStatisticsAction(i);
            
            this.stat_objects.add(so); //.get(i-1).addToSum(this.base_item.getValueForItem(i)); 
        }
    }
    
    
    
    /**
     * Gets the max statistics object.
     * 
     * @return the max statistics object
     */
    public int getMaxStatisticsObject()
    {
        return this.base_item.getMaxStatisticsObject();
    }
    
    
    /**
     * Gets the statistics item sample.
     * 
     * @return the statistics item sample
     */
    public StatisticsItem getStatisticsItemSample()
    {
        return this.base_item;
    }
    
    
    
    /**
     * Gets the item statistics value.
     * 
     * @param index the index
     * 
     * @return the item statistics value
     */
    public float getItemStatisticsValue(int index)
    {
        if (!this.special_processed)
            processSpecialStatistics();

        if (!this.base_item.isSpecialAction(index))
            return this.stat_objects.get(index-1).getStatistics();
        else
            return getSpecialActionStatisticsValue(index);
    }
    
    
    /**
     * Gets the special action statistics value.
     * 
     * @param index the index
     * 
     * @return the special action statistics value
     */
    public float getSpecialActionStatisticsValue(int index)
    {
//        if (!this.special_processed)
//            processSpecialStatistics();
        
        return this.stat_objects.get(index-1).getStatistics();
    }

    /**
     * This method needs to be reimplemented. 
     */
    public void processSpecialStatistics()
    {
        this.special_processed = true;
    }
    
    
    /**
     * Gets the item statistic value as int.
     * 
     * @param index the index
     * 
     * @return the item statistic value as int
     */
    public int getItemStatisticValueAsInt(int index)
    {
        return (int)this.getItemStatisticsValue(index);
    }
    
    /**
     * Gets the item statistic value as string float.
     * 
     * @param index the index
     * @param dec_places the dec_places
     * 
     * @return the item statistic value as string float
     */
    public String getItemStatisticValueAsStringFloat(int index, int dec_places)
    {
        return this.decimal_handler.getDecimalAsString(this.getItemStatisticsValue(index), dec_places);
    }
    
    /**
     * Gets the item statistic value as int.
     * 
     * @param index the index
     * 
     * @return the item statistic value as int
     */
    public String getItemStatisticValueAsStringInt(int index)
    {
        return "" + (int)this.getItemStatisticsValue(index);
    }
    
    
}



