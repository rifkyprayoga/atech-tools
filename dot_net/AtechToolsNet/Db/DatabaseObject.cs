using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.Data;


namespace ATechTools.Db
{
    public abstract class DatabaseObject
    {

        /// <summary>
        /// Add To Database
        /// </summary>
        /// <param name="conn">Database Connection</param>
        /// <param name="is_id_set">is ID set</param>
        /// <returns></returns>
        public abstract bool AddDb(SqlConnection conn, bool is_id_set);


        /// <summary>
        /// Add To Database (ID is handled by class depending in type of table)
        /// </summary>
        /// <param name="conn">Database Connection</param>
        /// <param name="is_id_set">is ID set</param>
        /// <returns></returns>
        public abstract bool AddDb(SqlConnection conn);


        public abstract bool EditDb(SqlConnection conn);


        public abstract bool DeleteDb(SqlConnection conn);


        public abstract bool CreateTable(SqlConnection conn);



        public abstract string GetDbColumns(int type);


        public abstract bool GetDb(SqlDataReader reader, int type);


        public abstract bool GetDb(DataRow row, int type);


        public bool IsDbValueNull(object db_value)
        {
            return (db_value == DBNull.Value);
        }

        public Single GetSingleValueNotNull(object db_value, float def_value)
        {
            if (db_value == DBNull.Value)
                return def_value;
            else
                return Convert.ToSingle(db_value);
        }

        public string GetStringValueNotNull(object db_value, string def_value)
        {
            if (db_value == DBNull.Value)
                return def_value;
            else
                return Convert.ToString(db_value);
        }


        public string GetDbStringOrNull(string val)
        {
            if ((val == null) || (val.Length==0))
                return null;
            else
                return val;
        }




        public int GetIntValueNotNull(object db_value, int def_value)
        {
            if (db_value == DBNull.Value)
                return def_value;
            else
                return Convert.ToInt32(db_value);
        }


        public bool IsStringSet(string val)
        {
            if (val == null)
                return false;
            else
                return (val.Length > 0);
        }


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
