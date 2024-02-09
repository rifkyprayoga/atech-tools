using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;


namespace ATechTools.Db
{
    public abstract class DatabaseObjectMobile
    {

        public abstract bool AddDb(SqlConnection conn, bool is_id_set);


        public abstract bool EditDb(SqlConnection conn);


        public abstract bool DeleteDb(SqlConnection conn);


        public abstract bool CreateTable(SqlConnection conn);



        public abstract string GetDbColumns(int type);


        public abstract bool GetDb(SqlDataReader reader, int type);




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
