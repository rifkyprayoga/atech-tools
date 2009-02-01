package com.atech.misc.statistics;

public interface StatisticsItem
{
    
//    public float getFloatValueForItem();
    
    /**
     * Get Value For Item
     * 
     * @param index index for statistics item 
     * @return 
     */
    public float getValueForItem(int index);

    /**
     * Get Statistics Action - we define how statistic is done (we have several predefined 
     *    types of statistics
     * 
     * @param index index for statistics item 
     * @return
     */
    public int getStatisticsAction(int index);

    /**
     * Is Special Action - tells if selected statistics item has special actions
     * 
     * @param index
     * @return
     */
    public boolean isSpecialAction(int index);
    
    /**
     * Get Max Statistics Object - we can have several Statistic types defined here
     * 
     * @return
     */
    public int getMaxStatisticsObject();
    
    /**
     * If we have any special actions for any of objects
     * 
     * @return
     */
    public boolean weHaveSpecialActions();
    
}





