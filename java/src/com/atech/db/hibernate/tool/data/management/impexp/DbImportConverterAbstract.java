package com.atech.db.hibernate.tool.data.management.impexp;

import com.atech.data.mng.DataDefinitionEntry;
import com.atech.db.hibernate.HibernateDb;
import com.atech.db.hibernate.HibernateObject;
import com.atech.db.hibernate.tool.data.dto.DbTableExportInfoDto;
import com.atech.utils.ATDataAccessAbstract;

/**
 * Created by andy on 19/12/16.
 */
public abstract class DbImportConverterAbstract implements DbImportConverter
{

    protected ATDataAccessAbstract dataAccess;
    protected HibernateDb database;


    public DbImportConverterAbstract(ATDataAccessAbstract dataAccess)
    {
        this.dataAccess = dataAccess;
        this.database = dataAccess.getHibernateDb();
    }


    public HibernateObject convert(DataDefinitionEntry dataDefinitionEntry, String dataLine,
            DbTableExportInfoDto exportInfoDto)
    {
        return convert(dataDefinitionEntry.getDatabaseTableConfiguration(), dataLine, exportInfoDto);
    }


    /**
     * Gets the int.
     *
     * @param input the input
     *
     * @return the int
     */
    public int getInt(String input)
    {
        input = removeExtraSigns(input);

        if (input.length() == 0)
            return 0;
        else
            return Integer.parseInt(input);

    }


    /**
     * Gets the short.
     *
     * @param input the input
     *
     * @return the short
     */
    public short getShort(String input)
    {
        input = removeExtraSigns(input);

        if (input.length() == 0)
            return 0;
        else
            return Short.parseShort(input);
    }


    /**
     * Gets the long.
     *
     * @param input the input
     *
     * @return the long
     */
    public long getLong(String input)
    {
        input = removeExtraSigns(input);

        if (input.length() == 0)
            return 0;
        else
            return Long.parseLong(input);
    }


    /**
     * Gets the float.
     *
     * @param input the input
     *
     * @return the float
     */
    public float getFloat(String input)
    {
        input = removeExtraSigns(input);

        if (input.length() == 0)
            return 0;
        else
            return (float) Double.parseDouble(input.replace(',', '.'));
    }


    /**
     * Gets the string.
     *
     * @param input the input
     *
     * @return the string
     */
    public String getString(String input)
    {
        input = removeExtraSigns(input);

        if (input.trim().length() == 0)
            return null;

        if (input.equals("null"))
            return null;

        return input;
    }


    private String removeExtraSigns(String input)
    {
        if (input.startsWith("~"))
            return input.substring(1, input.length() - 1);
        else
            return input;
    }


    /**
     * Get Splitted Values for Import
     *
     * @param valueEntry value Line
     * @return array of String
     */
    protected String[] getSplittedValues(String valueEntry)
    {
        valueEntry = ATDataAccessAbstract.replaceExpression(valueEntry,
            com.atech.db.hibernate.transfer.ExportTool.DELIMITER + com.atech.db.hibernate.transfer.ExportTool.DELIMITER,
            com.atech.db.hibernate.transfer.ExportTool.DELIMITER + " "
                    + com.atech.db.hibernate.transfer.ExportTool.DELIMITER);
        return ATDataAccessAbstract.splitString(valueEntry, com.atech.db.hibernate.transfer.ExportTool.DELIMITER);
    }


    protected void setId(String idString, HibernateObject hibernateObject)
    {
        long id = getLong(idString);

        if (id != 0)
            hibernateObject.setId(id);
    }


    public <E extends HibernateObject> E getCachedObject(Class<E> clazz, String idString)
    {
        long id = getLong(idString);

        return getCachedObject(clazz, id);
    }


    public <E extends HibernateObject> E getCachedObject(Class<E> clazz, long id)
    {
        if (id == 0)
            return null;
        else
            return database.getCachedObject(clazz, id);
    }

}
