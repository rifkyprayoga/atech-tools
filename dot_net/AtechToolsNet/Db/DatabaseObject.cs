using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.Data;


namespace ATechTools.Db
{
    public abstract class DatabaseObject
    {

        protected bool is_add_action = false;
        protected bool can_we_detect_add_edit = false;

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


        public Decimal GetDecimalValueNotNull(object db_value, decimal def_value)
        {
            if (db_value == DBNull.Value)
                return def_value;
            else
                return Convert.ToDecimal(db_value);
        }

        public bool IsAddAction()
        { 
            return is_add_action;
        }

        public bool DoWeSupportAddEditDetection()
        {
            return can_we_detect_add_edit;
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


        public DateTime GetDateTimeNotNull(object db_value, DateTime def_value)
        {
            if (db_value == DBNull.Value)
                return def_value;
            else
                return Convert.ToDateTime(db_value);
        }


        public object GetDbStringOrNull(string val)
        {
            if ((val == null) || (val.Length==0))
                return DBNull.Value;
            else
                return val;
        }

        /*
        public Decimal GetDbDecimalOrNull(object db_value)
        {
            if (db_value == DBNull.Value)
                return DBNull.Value;
            else
                return Convert.ToDecimal(db_value);
        }*/


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
