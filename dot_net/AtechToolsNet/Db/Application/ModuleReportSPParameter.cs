// -----------------------------------------------------------------------
// <copyright file="ModuleReportSPParameter.cs" company="">
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
    public class ModuleReportSPParameter : DatabaseObject
    {
        string reportRowguid;
        string reportPart;
        string parameterName;
        string parameterValue;



        public string ReportRowguid
        {
            get { return reportRowguid; }
            set { reportRowguid = value; }
        }

        public string ReportPart
        {
            get { return reportPart; }
            set { reportPart = value; }
        }


        public string ParameterName
        {
          get { return parameterName; }
          set { parameterName = value; }
        }

        public string ParameterValue
        {
          get { return parameterValue; }
          set { parameterValue = value; }
        }



    
        public override bool  AddDb(System.Data.SqlClient.SqlConnection conn, bool is_id_set)
        {
 	        throw new NotImplementedException();
        }

        public override bool  AddDb(System.Data.SqlClient.SqlConnection conn)
        {
 	        throw new NotImplementedException();
        }

        public override bool  EditDb(System.Data.SqlClient.SqlConnection conn)
        {
 	        throw new NotImplementedException();
        }

        public override bool  DeleteDb(System.Data.SqlClient.SqlConnection conn)
        {
 	        throw new NotImplementedException();
        }

        public override bool  CreateTable(System.Data.SqlClient.SqlConnection conn)
        {
 	        throw new NotImplementedException();
        }

        public override string  GetDbColumns(int type)
        {
 	        throw new NotImplementedException();
        }

        public override bool  GetDb(System.Data.SqlClient.SqlDataReader reader, int type)
        {
 	        throw new NotImplementedException();
        }

        public override bool  GetDb(System.Data.DataRow row, int type)
        {
            this.reportRowguid = GetStringValueNotNull(row["ReportRowguid"], "");
            this.reportPart = GetStringValueNotNull(row["ReportPart"], "");
            this.parameterName = GetStringValueNotNull(row["ParameterName"], ""); 
            this.parameterValue = GetStringValueNotNull(row["ParameterValue"], ""); 

            return true;
        }

        public override bool  ImportDb(string[] elements, int table_version)
        {
 	        throw new NotImplementedException();
        }

        public override int  TableVersion
        {
	        get { throw new NotImplementedException(); }
        }

        public override string  TableName
        {
	        get { throw new NotImplementedException(); }
        }

        public override string  ExportDb(int table_version)
        {
 	        throw new NotImplementedException();
        }
    }
}
