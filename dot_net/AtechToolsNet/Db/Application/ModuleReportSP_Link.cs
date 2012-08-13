// -----------------------------------------------------------------------
// <copyright file="ModuleReportSP_Link.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace AtechTools.Core.Db.Application
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using ATechTools.Db;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class ModuleReportSP_Link : DatabaseObject
    {
        string reportRowguid;
        string reportPart;
        string sPName;


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

        public string SPName
        {
            get { return sPName; }
            set { sPName = value; }
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
            this.reportRowguid = GetStringValueNotNull(row["ReportRowguid"], "");
            this.reportPart = GetStringValueNotNull(row["ReportPart"], "");
            this.sPName = GetStringValueNotNull(row["SPName"], "");

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
