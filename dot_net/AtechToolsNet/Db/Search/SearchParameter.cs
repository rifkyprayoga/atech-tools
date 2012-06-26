using System;
using System.Collections.Generic;
using System.Text;

namespace ATechTools.Db.Search
{
    public class SearchParameter
    {

        public const int COMMAND_EQUALS = 1;

        public const int COMMAND_LIKE = 2;


        public const int SQLTYPE_STRING = 1;

        public const int SQLTYPE_LONG = 2;

        public const int SQLTYPE_INT = 3;

        public const int SQLTYPE_DECIMAL = 4;

        public const int SQLTYPE_OTHER = 5;


        protected string caption = "";
        protected string sqlParameter = "";
        protected long index = 0;
        protected int commandType = 0;
        protected bool enabled = false;
        protected int sqlType = 0;

        private SearchSubParameter _subParameter = null;

        public SearchSubParameter SqlSubParameter
        {
            get { return _subParameter; }
            set { _subParameter = value; }
        }

        public string Caption
        {
            get { return caption; }
            set { caption = value; }
        }


        public string SqlParameter
        {
            get { return sqlParameter; }
            set { sqlParameter = value; }
        }


        public long Index
        {
            get { return index; }
            set { index = value; }
        }


        public int CommandType
        {
            get { return commandType; }
            set { commandType = value; }
        }


        public bool Enabled
        {
            get { return enabled; }
            set { enabled = value; }
        }


        public int SqlType
        {
            get { return sqlType; }
            set { sqlType = value; }
        }

        public bool HasSubSearch
        {
            get
            {
                if (this._subParameter == null) return false;
                return true;
            }
        }


        public SearchParameter(string caption, string sqlParam, long index, int command_ty, bool enabled, int sqlType)
        { 
            this.caption = caption;
            this.sqlParameter = sqlParam;
            this.index = index;
            this.commandType = command_ty;
            this.enabled = enabled;
            this.sqlType = sqlType;
        }

        public SearchParameter(string caption, string sqlParam, SearchSubParameter subparam, long index, int command_ty, bool enabled, int sqlType)
        {
            this.caption = caption;
            this.sqlParameter = sqlParam;
            this.index = index;
            this.commandType = command_ty;
            this.enabled = enabled;
            this.sqlType = sqlType;
            this._subParameter = subparam;
        }

        public string GetSubSearch(string primarySelectTable, string condition)
        {
            if (_subParameter == null) 
                return null;
            
            if (_subParameter.HasTableSpecified)
                return _subParameter.GetSubQuery(this.sqlParameter, condition);
            else
                return _subParameter.GetSubQuery(primarySelectTable, this.sqlParameter, condition);
        }


    }

    public class SearchSubParameter
    {
        private string _FieldName;
        private string _SubTableName;
        private bool _ExcludeFromPrimary = false;
        private string _tablename;
        private bool hasTableSpecified = false;

        public bool HasTableSpecified
        {
            get { return hasTableSpecified; }
            set { hasTableSpecified = value; }
        }

        // {Tablename} - primary table that the select is performed on
        // {SubTablename} - sub table for sub query
        // {Field} - primary tble field name
        // {SubField} - sub table name
        // ((SELECT COUNT(ItemBarCode.BarCode) FROM ItemBarCode WHERE ItemBarCode.IDItem = Item.IDItem AND BarCode  LIKE '%3333333%') > 0)
        private const string _sql = "((SELECT COUNT({SubTablename}SubFilter.{Field}) FROM {SubTablename} as {SubTablename}SubFilter WHERE {SubTablename}SubFilter.{SubField} = {Tablename}.{SubField} AND {SubTablename}SubFilter.{Field} {Condition}) > 0)";

        public bool ExcludeFromPrimary
        {
            get { return _ExcludeFromPrimary; }
        }

        /// <summary>
        /// Creates a new sub search query in the where clause.
        /// </summary>
        /// <param name="subtablename">Subquery table name.</param>
        /// <param name="fieldname">Field name for subquery join.</param>
        public SearchSubParameter(string subtablename, string fieldname) : this(subtablename, fieldname, false)
        {

        }


        /// <summary>
        /// Creates a new sub search query in the where clause.
        /// </summary>
        /// <param name="subtablename">Subquery table name.</param>
        /// <param name="fieldname">Field name for subquery join.</param>
        public SearchSubParameter(string tablename, string subtablename, string fieldname)
            : this(subtablename, fieldname, false)
        {
            this._tablename = tablename;
            this.hasTableSpecified = true;
        }


        /// <summary>
        /// Creates a new sub search query in the where clause.
        /// </summary>
        /// <param name="subtablename">Subquery table name.</param>
        /// <param name="fieldname">Field name for subquery join.</param>
        /// <param name="excludeFromPrimary">Exclude parameter from primary where clause.</param>
        public SearchSubParameter(string subtablename, string fieldname, bool excludeFromPrimary)
        {
            _FieldName = fieldname;
            _SubTableName = subtablename;
            _ExcludeFromPrimary = excludeFromPrimary;
        }

        
        public string GetSubQuery(string tablename, string subfieldname, string condition)
        {
            string s = _sql;
            s = s.Replace("{Tablename}", tablename);
            s = s.Replace("{SubTablename}", _SubTableName);
            s = s.Replace("{SubField}", _FieldName);
            s = s.Replace("{Field}", subfieldname);
            s = s.Replace("{Condition}", condition);
            return s;
        }

        public string GetSubQuery(string subfieldname, string condition)
        {
            string s = _sql;
            s = s.Replace("{Tablename}", _tablename);
            s = s.Replace("{SubTablename}", _SubTableName);
            s = s.Replace("{SubField}", _FieldName);
            s = s.Replace("{Field}", subfieldname);
            s = s.Replace("{Condition}", condition);
            return s;
        }


    }
}
