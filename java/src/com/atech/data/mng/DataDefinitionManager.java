package com.atech.data.mng;

import java.util.HashMap;
import java.util.Map;

import com.atech.db.hibernate.HibernateObject;

/**
 * Created by andy on 14/02/17.
 */
public class DataDefinitionManager
{

    // Map<String, DataDefinitionEntry> mapByKey;
    Map<Class<? extends HibernateObject>, DataDefinitionEntry> mapByClass;


    public DataDefinitionManager()
    {
        mapByClass = new HashMap<Class<? extends HibernateObject>, DataDefinitionEntry>();
    }


    public void addEntry(DataDefinitionEntry entry)
    {
        mapByClass.put(entry.getClazz(), entry);
    }


    public int getColumnCount(Class<? extends HibernateObject> entryKey)
    {
        DataDefinitionEntry entry = getEntry(entryKey);

        return entry == null ? 0 : entry.getColumnCount();
    }


    public String getColumnName(Class<? extends HibernateObject> entryKey, int columnIndex)
    {
        DataDefinitionEntry entry = getEntry(entryKey);

        return entry == null ? "X_" + columnIndex : entry.getColumnName(columnIndex);
    }


    public int getColumnWidth(Class<? extends HibernateObject> entryKey, int columnIndex, int fullWidth)
    {
        DataDefinitionEntry entry = getEntry(entryKey);

        return entry == null ? 20 : entry.getColumnWidth(columnIndex, fullWidth);
    }


    public DataDefinitionEntry getEntry(Class<? extends HibernateObject> clazz)
    {
        return (mapByClass.containsKey(clazz)) ? mapByClass.get(clazz) : null;
    }

}
