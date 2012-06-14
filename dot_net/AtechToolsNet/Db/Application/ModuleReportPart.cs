// -----------------------------------------------------------------------
// <copyright file="ModuleReportPart.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace AtechTools.Core.Db.Application
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using ATechTools.Db.Application;
    using ATechTools.Db;




    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class ModuleReportPart : DatabaseObject
    {
        string parentRowguid;
        string reportPart;
        string reportFileName;
        string targetSubType;
        string cRC;
        string whereCondition;
        private ReportWhereType whereType;

        public string ParentRowguid
        {
            get { return parentRowguid; }
            set { parentRowguid = value; }
        }

        public string ReportPart
        {
            get { return reportPart; }
            set { reportPart = value; }
        }

        public string ReportFileName
        {
            get { return reportFileName; }
            set { reportFileName = value; }
        }

        public string TargetSubType
        {
            get { return targetSubType; }
            set { targetSubType = value; }
        }

        public string CRC
        {
            get { return cRC; }
            set { cRC = value; }
        }

        public ReportWhereType WhereType
        {
            get { return whereType; }
            set { whereType = value; }
        }


        public string WhereCondition
        {
            get { return whereCondition; }
            set { whereCondition = value; }
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
            this.parentRowguid = GetStringValueNotNull(row["ReportRowguid"], "");
            this.reportPart = GetStringValueNotNull(row["ReportPart"], "");
            this.reportFileName = GetStringValueNotNull(row["SubReportFileName"], "");
            this.targetSubType = GetStringValueNotNull(row["TargetSubtype"], "");
            this.cRC = GetStringValueNotNull(row["CRC"], "");
            this.whereCondition = GetStringValueNotNull(row["WhereCondition"], "");

            ProcessWhere();

            return true;
        }


        public void ProcessWhere()
        {
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
