using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.Data;
using System.ComponentModel;
using System.Globalization;


namespace ATechTools.Db
{
    public abstract class DatabaseObject
    {

        protected bool is_add_action = false;
        protected bool can_we_detect_add_edit = false;
        protected static NumberFormatInfo numberFormatInfo = null;

        public DatabaseObject()
        {
            if (numberFormatInfo == null)
            {
                System.Globalization.CultureInfo ci = System.Globalization.CultureInfo.CurrentCulture;
                numberFormatInfo = (System.Globalization.NumberFormatInfo)ci.NumberFormat.Clone();
            }
        }



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
            if ((db_value == DBNull.Value) || ((db_value == null) || (string.IsNullOrWhiteSpace(db_value.ToString())) || (db_value.ToString().Equals("<null>"))))
                return def_value;
            else
            {
                return GetFormatedSingle(db_value);
            }
        }

        private Single GetFormatedSingle(object db_value)
        {
            string val = db_value.ToString();

            if (numberFormatInfo.CurrencyDecimalSeparator == ".")
            {
                val = val.Replace(",", ".");
                return Convert.ToSingle(val);
            }
            else
            {
                val = val.Replace(".", ",");
                return Convert.ToSingle(val);
            }
        }


        public string GetStringValueNotNull(object db_value, string def_value)
        {
            if (db_value == DBNull.Value)
                return def_value;
            else
                return Convert.ToString(db_value);
        }


        public bool GetBoolValueNotNull(object db_value, bool def_value)
        {
            if (db_value == DBNull.Value)
                return def_value;
            else
                return Convert.ToBoolean(db_value);
        }


        public DateTime GetDateTimeNotNull(object db_value, DateTime def_value)
        {
            if ((db_value == DBNull.Value) || (string.IsNullOrWhiteSpace(db_value as string)))
                return def_value;
            else
                return Convert.ToDateTime(db_value);
        }


        public DateTime? GetDateTimeNull(object db_value, DateTime? def_value)
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


        public object GetDbSingleOrNull(float val)
        {
            if (val == 0.0f)
                return DBNull.Value;
            else
                return val;
        }


        public object GetDbIntegerOrNull(int val)
        {
            if (val == 0)
                return DBNull.Value;
            else
                return val;
        }


        public object GetDbDateTimeNOrNull(DateTime? val)
        {
            if (val == null)
                return DBNull.Value;
            else
                return Convert.ToDateTime(val);
        }

        public object GetDbDateTimeNOrNull(DateTime val)
        {
            if (val == DateTime.MinValue)
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
            if ((db_value == DBNull.Value) || (string.IsNullOrWhiteSpace(db_value as string)))
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


        [Browsable(false)]
        public abstract int TableVersion
        { 
            get;
        }


        [Browsable(false)]
        public abstract string TableName
        {
            get;
        }


        public abstract string ExportDb(int table_version);




    }
}
