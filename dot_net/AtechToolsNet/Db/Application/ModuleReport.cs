using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using AtechToolsNet.GUI.Selector;
using System.Data.SqlClient;
using AtechTools.Core.Db.Application;

namespace ATechTools.Db.Application
{

    public enum ReportWhereType
    {
        NoWhere = 1,
        SpecifiedWhere = 2,
        ObjectWhere = 3
    }



    public class ModuleReport : DatabaseObject
    {
        private string rowguid;

        private string moduleID;
        private string reportName;
        private string reportFile;
        private string reportParameters;
        string cRC;

        private string moduleSub;

        private string preRunSP;
        string whereCondition;
        private ReportWhereType whereType;
        private bool isTemplate;

        public bool IsTemplate
        {
            get { return isTemplate; }
            set { isTemplate = value; }
        }
        private string parameterType;

        public string ParameterType
        {
            get { return parameterType; }
            set { parameterType = value; }
        }

        //Dictionary<string, List<ModuleReportPart>> report_parts_by_sub = null;

        Dictionary<string, ModuleReportPart> reportParts = null;

        public Dictionary<string, ModuleReportPart> ReportParts
        {
            get { return reportParts; }
            set { reportParts = value; }
        }



        public string Rowguid
        {
            get { return rowguid; }
            set { rowguid = value; }
        }

        public string ModuleID
        {
            get { return moduleID; }
            set { moduleID = value; }
        }

        public string ModuleSub
        {
            get { return moduleSub; }
            set { moduleSub = value; }
        }


        public string ReportName
        {
            get { return reportName; }
            set { reportName = value; }
        }


        public string ReportFile
        {
            get { return reportFile; }
            set { reportFile = value; }
        }


        public string ReportParameters
        {
            get { return reportParameters; }
            set { reportParameters = value; }
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


        public void AddReportPart(ModuleReportPart mrp)
        {
            /*
            if (this.report_parts_by_sub == null)
                this.report_parts_by_sub = new Dictionary<string, List<ModuleReportPart>>();

            if (this.report_parts_by_sub.ContainsKey(mrp.TargetSubType))
            {

            }
            else
            { 
            }*/

            if (this.reportParts == null)
                this.reportParts = new Dictionary<string, ModuleReportPart>();

            this.reportParts.Add(mrp.ReportPart, mrp);

        }




        public override bool AddDb(SqlConnection conn, bool is_id_set)
        {
            throw new NotImplementedException();
        }

        public override bool AddDb(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool EditDb(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool DeleteDb(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool CreateTable(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override string GetDbColumns(int type)
        {
            throw new NotImplementedException();
        }

        public override bool GetDb(SqlDataReader reader, int type)
        {
            throw new NotImplementedException();
        }

        public override bool GetDb(DataRow row, int type)
        {
            this.rowguid = GetStringValueNotNull(row["rowguid"], "");
            this.moduleID = GetStringValueNotNull(row["ModuleID"], "");
            this.reportName = GetStringValueNotNull(row["ReportName"], "");
            this.reportFile = GetStringValueNotNull(row["ReportFile"], "");
            this.reportParameters = GetStringValueNotNull(row["ReportParameters"], "");
            this.cRC = GetStringValueNotNull(row["CRC"], "");
            this.moduleSub = GetStringValueNotNull(row["ModuleSub"], "");
            this.preRunSP = GetStringValueNotNull(row["PreRunSP"], "");
            this.whereCondition = GetStringValueNotNull(row["WhereCondition"], "");

            this.isTemplate = GetBoolValueNotNull(row["IsTemplate"], false);
            this.parameterType = GetStringValueNotNull(row["ParameterType"], "");

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

        public override string ToString()
        {
            return reportName;
        }
    }
}
