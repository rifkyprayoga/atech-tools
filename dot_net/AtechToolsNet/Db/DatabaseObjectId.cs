// -----------------------------------------------------------------------
// <copyright file="DatabaseObjectId.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AtechTools.Core.Db
{

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class DatabaseObjectId
    {

        protected string id_column = null;
        protected string id_column_parameter = null;
        protected string id_column_type = null;
        protected string[] id_columns = null;
        protected string[] id_columns_types = null;
        protected bool multiple_columns = false;


        public DatabaseObjectId(string id_column, string id_column_parameter, string id_column_type)
        {
            this.id_column = id_column;
            this.id_column_parameter = id_column_parameter;
            this.id_column_type = id_column_type;
            this.multiple_columns = false;
        }


        /// <summary>
        /// IDColumn for this table  (if HasMultipleIDColumns=false)
        /// </summary>
        public string IDColumn
        {
            get
            {
                return id_column;
            }
        }

        /// <summary>
        /// IDColumns for this table (if HasMultipleIDColumns=true)
        /// </summary>
        public string[] IDColumns
        {
            get { return id_columns; }
        }

        /// <summary>
        /// Tells us if table has multiple columnns for IDs
        /// </summary>
        public bool HasMultipleIDColumns
        {
            get { return multiple_columns; }
        }


        public string IDColumnType
        {
            get { return this.id_column_type; }
        }


        public string[] IDColumnsTypes
        {
            get { return this.id_columns_types; }
        }

        public string IDColumnParameter
        {
            get { return this.id_column_parameter; }
        }



    }
}
