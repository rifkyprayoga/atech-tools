package com.atech.misc.statistics;

import java.util.ArrayList;

import com.atech.misc.converter.DecimalHandler;
import com.atech.utils.ATDataAccessAbstract;

public class StatisticsCollection
{
    
    protected ArrayList<StatisticsObject> stat_objects;
    protected ArrayList<StatisticsItem> items;
    protected StatisticsItem base_item;
    protected ATDataAccessAbstract m_da;
    protected DecimalHandler decimal_handler;
    protected boolean special_processed = false;
    
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
    
    
    public void processFullCollection(ArrayList<? extends StatisticsItem> list)
    {
        this.cleanStatisticsObjects();
        
        for(int i=0; i<list.size(); i++)
        {
            addItem(list.get(i)); 
        }
        
        special_processed = false;
    }
    

    
    
    
    public void addItem(StatisticsItem item)
    {
        processItem(item);
        
        if (this.base_item.weHaveSpecialActions())
            this.items.add(item);

        special_processed = false;
    }
    
    public void processItem(StatisticsItem item)
    {
        for(int i=1; i<this.getMaxStatisticsObject(); i++)
        {
            this.stat_objects.get(i-1).addToSum(item.getValueForItem(i)); 
        }
    }
    
    
    public void createStatisticsObjects()
    {
        for(int i=1; i<this.getMaxStatisticsObject(); i++)
        {
            StatisticsObject so = new StatisticsObject();
            so.operation = this.base_item.getStatisticsAction(i);
            
            this.stat_objects.add(so); //.get(i-1).addToSum(this.base_item.getValueForItem(i)); 
        }
    }
    
    
    
    public int getMaxStatisticsObject()
    {
        return this.base_item.getMaxStatisticsObject();
    }
    
    
    public StatisticsItem getStatisticsItemSample()
    {
        return this.base_item;
    }
    
    
    
    public float getItemStatisticsValue(int index)
    {
        if (!this.special_processed)
            processSpecialStatistics();

        if (!this.base_item.isSpecialAction(index))
            return this.stat_objects.get(index-1).getStatistics();
        else
            return getSpecialActionStatisticsValue(index);
    }
    
    
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
    
    
    public int getItemStatisticValueAsInt(int index)
    {
        return (int)this.getItemStatisticsValue(index);
    }
    
    public String getItemStatisticValueAsStringFloat(int index, int dec_places)
    {
        return this.decimal_handler.getDecimalAsString(this.getItemStatisticsValue(index), dec_places);
    }
    
    
    
}



