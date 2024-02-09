using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlServerCe;


namespace AtechToolsNetMobile.Db
{
    public abstract class DatabaseObjectMobile
    {

        public abstract bool AddDb(SqlCeConnection conn, bool is_id_set);


        public abstract bool EditDb(SqlCeConnection conn);


        public abstract bool DeleteDb(SqlCeConnection conn);

        
        public abstract bool CreateTable(SqlCeConnection conn);



        public abstract string GetDbColumns(int type);


        public abstract bool GetDb(SqlCeDataReader reader, int type);




        public abstract bool ImportDb(string[] elements, int table_version);

        public string ExportDb()
        {
            return ExportDb(TableVersion);
        }


        public abstract int TableVersion
        { 
            get;
        }


        public abstract string TableName
        {
            get;
        }


        public abstract string ExportDb(int table_version);

    }
}
