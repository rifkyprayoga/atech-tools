package com.atech.data.mng;

import com.atech.db.hibernate.HibernateObject;
import com.atech.db.hibernate.tool.data.DatabaseTableConfiguration;
import com.atech.graphics.dialogs.selector.SelectorConfiguration;

public class DataDefinitionEntry
{

    private Class<? extends HibernateObject> clazz;
    private String columnNames;
    private String columnSizes;
    private String backupTargetName;
    private int tableVersion;

    String[] columnNamesArray;
    int[] columnSizesArray;
    int fullSize = 0;

    boolean prepareData = false;
    private String dbColumns;
    private boolean cleanDb = true;
    private DatabaseTableConfiguration databaseTableConfiguration;
    private SelectorConfiguration selectorConfiguration;


    public DataDefinitionEntry(Class<? extends HibernateObject> clazz, SelectorConfiguration selectorConfiguration,
            DatabaseTableConfiguration databaseTableConfiguration)
    {

        this.clazz = clazz;
        this.selectorConfiguration = selectorConfiguration;
        this.databaseTableConfiguration = databaseTableConfiguration;

        if (selectorConfiguration != null)
        {
            this.columnNames = selectorConfiguration.getColumnNames();
            this.columnSizes = selectorConfiguration.getColumnSizes();

            if (this.columnNames != null)
                prepare();
        }

        if (databaseTableConfiguration != null)
        {
            this.backupTargetName = databaseTableConfiguration.getBackupTargetName();
            this.tableVersion = databaseTableConfiguration.getTableVersion();
            this.dbColumns = databaseTableConfiguration.getColumns();
        }

    }

    // public DataDefinitionEntry(Class<? extends HibernateObject> clazz, String
    // columnNames, String columnSizes,
    // DatabaseTableConfiguration databaseTableConfiguration)
    // {
    // this(clazz, columnNames, columnSizes, //
    // databaseTableConfiguration.getBackupTargetName(), //
    // databaseTableConfiguration.getTableVersion(), //
    // databaseTableConfiguration.getColumns(), //
    // databaseTableConfiguration.doWeNeedToCleanDbForImport(), //
    // databaseTableConfiguration);
    // }

    // public DataDefinitionEntry(Class<? extends HibernateObject> clazz, String
    // columnNames, String columnSizes,
    // String backupTargetName, int tableVersion, String dbColumns)
    // {
    // this(clazz, columnNames, columnSizes, backupTargetName, tableVersion,
    // dbColumns, true, null);
    // }


    // public DataDefinitionEntry(Class<? extends HibernateObject> clazz, String
    // columnNames, String columnSizes,
    // String backupTargetName, int tableVersion, String dbColumns, boolean
    // cleanDb,
    // DatabaseTableConfiguration databaseTableConfiguration)
    // {
    // this.clazz = clazz;
    // // this.key = clazz.getSimpleName();
    // this.columnNames = columnNames;
    // this.columnSizes = columnSizes;
    // this.backupTargetName = backupTargetName;
    // this.tableVersion = tableVersion;
    // this.dbColumns = dbColumns;
    // this.databaseTableConfiguration = databaseTableConfiguration;
    // prepare();
    // }

    private void prepare()
    {
        if (prepareData)
            return;

        columnNamesArray = getColumnNames().split(",");

        columnSizesArray = new int[columnNamesArray.length];

        String[] arr = getColumnSizes().split(",");

        for (int i = 0; i < columnNamesArray.length; i++)
        {
            columnSizesArray[i] = Integer.parseInt(arr[i].trim());
            fullSize += columnSizesArray[i];
        }

        prepareData = true;
    }


    public int getColumnCount()
    {
        return columnNamesArray.length;
    }


    public String getColumnName(int num)
    {
        return columnNamesArray[num].trim();
    }


    public int getColumnWidth(int num, int width)
    {
        float mult = width / this.fullSize * (1.0f);

        int sizeColumn = (int) (columnSizesArray[num] * mult);

        // System.out.println("getColumnWidth: [mult=" + mult + ", fullsize=" +
        // this.fullSize + ", width=" + width
        // + ", columnSize=" + sizeColumn);

        return sizeColumn;
    }


    public String getBackupTargetName()
    {
        return backupTargetName;
    }


    public int getTableVersion()
    {
        return tableVersion;
    }


    public String getColumnNames()
    {
        return columnNames;
    }


    public String getColumnSizes()
    {
        return columnSizes;
    }


    public String getDbColumns()
    {
        return dbColumns;
    }


    public boolean hasToBeCleaned()
    {
        return cleanDb;
    }


    public Class<? extends HibernateObject> getClazz()
    {
        return clazz;
    }


    public DatabaseTableConfiguration getDatabaseTableConfiguration()
    {
        return databaseTableConfiguration;
    }


    public SelectorConfiguration getSelectorConfiguration()
    {
        return selectorConfiguration;
    }
}
