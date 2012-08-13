// -----------------------------------------------------------------------
// <copyright file="ModuleReportSP.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ATechTools.Db;

namespace AtechTools.Core.Db.Application
{

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class ModuleReportSP : DatabaseObject
    {
        
        protected string sPName;
        protected string sPDescription;
        protected string parameterDescription;
        protected string parameterName;
        protected string parameterType;
        protected string parameterTypeFenix;
        protected bool required = false;
        protected int orderNumber;






        public string SPName
        {
            get { return sPName; }
            set { sPName = value; }
        }

        public string SPDescription
        {
            get { return sPDescription; }
            set { sPDescription = value; }
        }

        public string ParameterDescription
        {
            get { return parameterDescription; }
            set { parameterDescription = value; }
        }

        public string ParameterName
        {
            get { return parameterName; }
            set { parameterName = value; }
        }

        public string ParameterType
        {
            get { return parameterType; }
            set { parameterType = value; }
        }

        public string ParameterTypeFenix
        {
            get { return parameterTypeFenix; }
            set { parameterTypeFenix = value; }
        }

        public bool Required
        {
            get { return required; }
            set { required = value; }
        }

        public int OrderNumber
        {
            get { return orderNumber; }
            set { orderNumber = value; }
        }



        public override bool AddDb(System.Data.SqlClient.SqlConnection conn, bool is_id_set)
        {
            throw new NotImplementedException();
        }

        public override bool AddDb(System.Data.SqlClient.SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool EditDb(System.Data.SqlClient.SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool DeleteDb(System.Data.SqlClient.SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool CreateTable(System.Data.SqlClient.SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override string GetDbColumns(int type)
        {
            throw new NotImplementedException();
        }

        public override bool GetDb(System.Data.SqlClient.SqlDataReader reader, int type)
        {
            throw new NotImplementedException();
        }

        public override bool GetDb(System.Data.DataRow row, int type)
        {
            this.sPName = GetStringValueNotNull(row["SPName"], ""); 
            this.sPDescription = GetStringValueNotNull(row["SPDescription"], ""); 
            this.parameterDescription = GetStringValueNotNull(row["ParameterDescription"], ""); 
            this.parameterName = GetStringValueNotNull(row["ParameterName"], ""); 
            this.parameterType = GetStringValueNotNull(row["ParameterType"], ""); 
            this.parameterTypeFenix = GetStringValueNotNull(row["ParameterTypeFenix"], ""); 
            this.required = GetBoolValueNotNull(row["Required"], false);
            this.orderNumber = GetIntValueNotNull(row["OrderNumber"], 0);

            return true;
        }

        public override bool ImportDb(string[] elements, int table_version)
        {
            throw new NotImplementedException();
        }

        public override int TableVersion
        {
            get { throw new NotImplementedException(); }
        }

        public override string TableName
        {
            get { throw new NotImplementedException(); }
        }

        public override string ExportDb(int table_version)
        {
            throw new NotImplementedException();
        }
    }
}
